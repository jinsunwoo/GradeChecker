package com.jsunwoo.gradechecker.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

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
import com.jsunwoo.gradechecker.adapter.WorkWeightMarkAdapter;
import com.jsunwoo.gradechecker.entity.Course;
import com.jsunwoo.gradechecker.entity.Term;
import com.jsunwoo.gradechecker.entity.WorkWeightMark;

import java.util.ArrayList;
import java.util.List;

public class CourseWorksActivity extends AppCompatActivity {
    private GradeCheckerResources APP;
    private WorkWeightMarkAdapter tca;
    List<WorkWeightMark> wwms ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_works);
        Intent intent = getIntent();
        APP = (GradeCheckerResources) getApplication();
        final RecyclerView rv = findViewById(R.id.recyclerView4);
        TextView tv = findViewById(R.id.courseName);
        Button addRow = findViewById(R.id.add_wwm);
        final int courseId=intent.getIntExtra("selectedCourseID", -1);
        String courseName=intent.getStringExtra("selectedCourseName");
        tv.setText(courseName);

        addRow.setOnClickListener(new View.OnClickListener() {
            @Override
            // termName 이 "Edit Term Name" 인 Term 객체 하나 만들고
            public void onClick(View v) {
                // Async 쓸때는 메인 쓰레드, 백그라운드 쓰레드 따로 쓰다보니까, 백그라운드 쓰레드에서 쓰는 변수는 final 처리를 해줘야 주소값 변경도 안되고 null point 안뜸
                final WorkWeightMark newwwm = new WorkWeightMark();
                newwwm.gradeItem = "Press to edit";
                newwwm.weight = 0;
                newwwm.mark = 0;
                // 코스랑 연결해야 하니까
                newwwm.cid = courseId;
                wwms.add(newwwm);
                tca = new WorkWeightMarkAdapter(CourseWorksActivity.this, wwms);
                rv.setAdapter(tca);
            }
        });

        new AsyncTask<Object, Object, Object>(){

            @Override
            protected Object doInBackground(Object[] objects) {
                // 데이터를 가지고 왔고 (아래는) 데이터가 없는 경우
                int maxWWMid = APP.db.wwmdao().getCountAll();
                wwms = APP.db.wwmdao().getCourseIDforBridge(courseId);
                // 포인터도 없는 경우랑 포인터는 있는데 데이터가 없는 경우 두가지 다 고려. 오류 있는 경우는 -1 도 나와서 0이랑 음수 포함 (안전성 위해서)
                if (wwms==null||wwms.size()<=0) {
                    wwms = new ArrayList<>();
                    for (int i =0;i<5;i++) {
                        WorkWeightMark wwm = new WorkWeightMark();
                        wwm.wid = ++maxWWMid;
                        wwm.gradeItem = "Press to edit";
                        wwm.mark = 0;
                        wwm.weight = 0;
                        wwm.cid=courseId;
                        wwms.add(wwm);
                    }
                    WorkWeightMark[] wwmArray = new WorkWeightMark[wwms.size()];
                    // termArray 타입으로 받으려고 가로안에 (termArray) 씀. 배열의 개수가 필요해서. 그래서 변수에 넣어줌.
                    wwmArray = wwms.toArray(wwmArray);
                    APP.db.wwmdao().insertAll(wwmArray);
                }
                return null;
            }


            @SuppressLint("StaticFieldLeak")
            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                // TermsActivity.this 안쓰는게 어댑터에서 context 안받았으니까// (수정됨 아래)
                // 컨택스트 자리에 TermsAcitivity 넣어주면 액태비티는 어차피 컨택스트 가지고 있으니까 이걸로 대신해도 됨
                tca = new WorkWeightMarkAdapter(CourseWorksActivity.this, wwms);
                rv.setAdapter(tca);
            }
        }.execute();
    }

    @Override
    protected void onPause() {
        // appcompatactivity 를 상속받은걸 실행해줘야 해서 async 포함 하면 안됨
        super.onPause();
        new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object... objects) {
                WorkWeightMark[] wwmArray = new WorkWeightMark[wwms.size()];
                // termArray 타입으로 받으려고 가로안에 (termArray) 씀. 배열의 개수가 필요해서. 그래서 변수에 넣어줌.
                wwmArray = wwms.toArray(wwmArray);
                APP.db.wwmdao().insertAll(wwmArray);

                return null;
            }
        }.execute();


        // TODO 현재 화면 꺼졌을때. 여기서 저장하는거 == 텍스트와처 -- 어댑터에서 --
        // FIXME 고칠내용.
    }
}