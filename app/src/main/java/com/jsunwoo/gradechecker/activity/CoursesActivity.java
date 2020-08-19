package com.jsunwoo.gradechecker.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jsunwoo.gradechecker.GradeCheckerResources;
import com.jsunwoo.gradechecker.R;
import com.jsunwoo.gradechecker.adapter.TermCourseAdapter;
import com.jsunwoo.gradechecker.entity.Course;
import com.jsunwoo.gradechecker.entity.Term;
import java.util.ArrayList;
import java.util.List;

public class CoursesActivity extends AppCompatActivity {
    private GradeCheckerResources APP;
    private TermCourseAdapter tca;
    List<Course> courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        Button addCourseBtn = findViewById(R.id.add_course_button);
        Button checkMarkBtn = findViewById(R.id.check_marks_button);
        TextView tv = findViewById(R.id.termTitle);
        final RecyclerView rv = findViewById(R.id.recyclerView3);
        // 두번째 파라미터는 값이 없을 경우 어떻게 할지 => return -1
        // 해당되는 termTitle 이 course 페이지에 위에 뜨게
        Intent intent = getIntent();
        final int termId=intent.getIntExtra("selectedTermID", -1);
        String termName=intent.getStringExtra("selectedTermName");
        tv.setText(termName);
        Log.i("TAG", "onCreate term id : "+termId);

        APP = (GradeCheckerResources) getApplication();

        addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Course newcourse = new Course();
                newcourse.courseName = "Edit Course Name";

                new AsyncTask<Object,Object,Object>(){
                    @Override
                    protected Object doInBackground(Object... objects) {
                        // 새로운 Term 객체를 DB 에 밀어넣고
                        APP.db.cdao().insertAll(newcourse);
                        // 추가된 Term 객체 포함한 전체 Terms 들을 DB 에서 가져와서 terms 에 할당 (UPDATE)
                        courses = APP.db.cdao().getAll();
                        // 업데이트 된 terms 를 어댑터 tca 와 연결
                        tca.setListCourse(courses);
                        Log.i("doInBackground: course size", courses.size()+"");
                        return null;
                    }
                    @Override
                    protected void onPostExecute(Object o) {
                        super.onPostExecute(o);
                        tca.notifyDataSetChanged();
                    }
                }.execute();
            }
        });

        checkMarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveIntent = new Intent(CoursesActivity.this, CheckMarksActivity.class);
                startActivity(moveIntent);
            }
        });

        new AsyncTask<Object, Object, Object>(){
            List<Course> courses ;
            @Override
            protected Object doInBackground(Object[] objects) {
                // 데이터를 가지고 왔고 (아래는) 데이터가 없는 경우
                int maxCid = APP.db.cdao().getCountAll();
                courses = APP.db.cdao().getTermIDforBridge(termId);
                Log.i("courseId Check", "doInBackground: "+ courses);

                // 포인터도 없는 경우랑 포인터는 있는데 데이터가 없는 경우 두가지 다 고려. 오류 있는 경우는 -1 도 나와서 0이랑 음수 포함 (안전성 위해서)
                if (courses==null||courses.size()<=0) {
                    courses = new ArrayList<>();
                    for (int i =0;i<3;i++) {
                        Course course = new Course();
                        course.cid = ++maxCid;
                        course.courseName = "Course "+(i+1);
                        course.termId=termId;
                        courses.add(course);
                    }
                    Course[] courseArray = new Course[courses.size()];
                    // termArray 타입으로 받으려고 가로안에 (termArray) 씀. 배열의 개수가 필요해서. 그래서 변수에 넣어줌.
                    courseArray = courses.toArray(courseArray);
                    APP.db.cdao().insertAll(courseArray);
                }
                return null;
            }

            @SuppressLint("StaticFieldLeak")
            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                // TermsActivity.this 안쓰는게 어댑터에서 context 안받았으니까// (수정됨 아래)
                // 컨택스트 자리에 TermsAcitivity 넣어주면 액태비티는 어차피 컨택스트 가지고 있으니까 이걸로 대신해도 됨
                tca = new TermCourseAdapter(null, courses, CoursesActivity.this);
                rv.setAdapter(tca);
            }
        }.execute();
    }
}