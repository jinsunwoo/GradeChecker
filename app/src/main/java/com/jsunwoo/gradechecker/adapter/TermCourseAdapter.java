package com.jsunwoo.gradechecker.adapter;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Entity;
import com.jsunwoo.gradechecker.GradeCheckerResources;
import com.jsunwoo.gradechecker.R;
import com.jsunwoo.gradechecker.activity.CourseWorksActivity;
import com.jsunwoo.gradechecker.activity.CoursesActivity;
import com.jsunwoo.gradechecker.activity.SettingActivity;
import com.jsunwoo.gradechecker.activity.TermsActivity;
import com.jsunwoo.gradechecker.entity.Course;
import com.jsunwoo.gradechecker.entity.GradeScale;
import com.jsunwoo.gradechecker.entity.Term;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class TermCourseAdapter extends RecyclerView.Adapter<TermCourseAdapter.ViewHolder> {
    private List<Term> terms;
    private List<Course> courses;
    private ArrayList<Boolean> editable_list;
    private GradeCheckerResources APP;
    private Context context;

    /**
     * Term and Course 전용 어댑터
     * @param terms
     * term에 해당하는 데이터가 들어와야 함.<br>
     *     course인 경우에는 null을 입력<br>
     *         <br>
     * @param courses
     * course에 해당하는 데이터가 들어와야 함.<br>
     *     term인 경우에는 null을 입력
     *     <br><br>
     */

    public TermCourseAdapter(List<Term> terms, List<Course> courses, Context context) {
        this.terms = terms;
        this.courses = courses;
        this.editable_list = new ArrayList<>();
        // 컨택스가 있어야 application 정보 가져 올 수 있어서 & 어댑터는 context 가 없으니까 (4대 구성요소가 아니라서) 이렇게 받아줘야 함
        this.APP = (GradeCheckerResources) context.getApplicationContext();
        this.context = context;
        int size = 0;
        // term 에서 쓰거나 course 에서 쓸거니까, 여기서 골라서 둘 중하나로 사이즈
        if(terms !=null){
            size = terms.size();
        } else{
            size = courses.size();
        }
        for (int i=0;i<size;i++){
            editable_list.add(false);
        }

    }

    @NonNull
    @Override
    // 8개 아이템 만드는게 얘
    public TermCourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_term_or_course, parent, false);
        return new TermCourseAdapter.ViewHolder(itemView);
    }
    // 데이터랑 연결 시키는
    // 포지션은 데이터 만큼 있는

    // 재활용 되니까 (item) 이 , 그래서, position 이용해서 위치 기억.
    // onBindview holder = 맨 처음 데이터 입혀주는거 / 스크롤할때마다 포지션에 맞게 데이터를 입혀주는
    @Override
    public void onBindViewHolder(@NonNull final TermCourseAdapter.ViewHolder holder, final int position) {
        Term term=null;
        Course course=null;
        if(terms != null){
            term = terms.get(position);
        }else if(courses != null) {
            course = courses.get(position);
        }
        if(term!=null){
            holder.name.setText(term.termName);
        }else if(course!=null){
            holder.name.setText(course.courseName);
        }
        // depends on editable_list's element, set "setEnabled" either true or false
        holder.name.setEnabled(editable_list.get(position));
        // a ? b : c => a 가 true 면은 b, false 면 c (3항연산자)
        holder.edit.setText(editable_list.get(position) ? "SAVE":"EDIT");
        // 주소값 바꾸면 안되서 final 사용 (null point issue 방지 위해서)
        final Term finalTerm = term;
        final Course finalCourse = course;
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EditableAsync(position, holder, finalTerm, finalCourse).execute();
            }

        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeleteAsync(position, finalTerm, finalCourse).execute();
            }

        });
        // itemView (edit, delete, name 다 들어가있는 해당 뷰 자체)
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(terms!=null) {
                    Intent moveIntent = new Intent(context, CoursesActivity.class);
                    moveIntent.putExtra("selectedTermID",terms.get(position).tid);
                    moveIntent.putExtra("selectedTermName",terms.get(position).termName);
                    // context 받으려면 APP 가져온거 연결해서 받으려고
                    context.startActivity(moveIntent);
                }else{
                    Intent moveIntent = new Intent(context, CourseWorksActivity.class);
                    moveIntent.putExtra("selectedCourseID",courses.get(position).cid);
                    moveIntent.putExtra("selectedCourseName",courses.get(position).courseName);
                    context.startActivity(moveIntent);

                }
            }
        });

    }

    // 스크롤 끝까지 했을때 값 (몇개인지) 보여주는 함수.
    @Override
    public int getItemCount() {
        if(terms !=null)
            return terms.size();
        else
            return courses.size();
    }

    // termsActivity 에서 addterm 했을 때 새로고침 하기 전에 tca 랑 term 이랑 연결해 주는 거
    // 새로 받아와서 사용 editable list 도 다시 디폴트값
    public void setListTerm(List<Term> terms) {
        this.terms=terms;
        this.editable_list = new ArrayList<>();
        for (int i=0;i<terms.size();i++)
            editable_list.add(false);
    }

    public void setListCourse(List<Course> courses) {
        this.courses=courses;
        this.editable_list = new ArrayList<>();
        for(int i=0;i<courses.size();i++)
            editable_list.add(false);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView delete;
        TextView edit;
        EditText name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            delete = itemView.findViewById(R.id.delete_textView);
            edit = itemView.findViewById(R.id.edit_textView);
            name = itemView.findViewById(R.id.course_name_editText);
        }
    }

    private class DeleteAsync extends  AsyncTask<Object,Object,Object> {
        private final int position;
        private final Term finalTerm;
        private final Course finalCourse;

        public DeleteAsync(int position, Term finalTerm, Course finalCourse) {
            this.position = position;
            this.finalTerm = finalTerm;
            this.finalCourse = finalCourse;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // 여기선 할 것 없음
        }

        @Override
        protected Object doInBackground(Object... objects) {
            if (finalTerm != null) {
                // 여기에서 수정된 내용이 있는 entity 를 올려줌
                APP.db.tdao().deleteAll(finalTerm);
                terms.remove(position);
            } else {
                APP.db.cdao().deleteAll(finalCourse);
                courses.remove(position);
            }
            editable_list.remove(position);
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            // 어댑터가 새로고침 되는
            notifyDataSetChanged();
        }
    }

    private class EditableAsync extends AsyncTask<Term, Object, Object> {
        private final int position;
        private final ViewHolder holder;
        private final Term finalTerm;
        private final Course finalCourse;

        public EditableAsync(int position, ViewHolder holder, Term finalTerm, Course finalCourse) {
            this.position = position;
            this.holder = holder;
            this.finalTerm = finalTerm;
            this.finalCourse = finalCourse;
        }

        // doinbackground 시작 전에 홀더 에 있는 이름 객체에 집어넣음 (수정)
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(editable_list.get(position)) {
                if (finalTerm != null) {
                    // entity 에 있는 termName 을 수정된거(현재 입력된거)로 바꿔 주고
                    finalTerm.termName = holder.name.getText().toString();
                } else {
                    finalCourse.courseName = holder.name.getText().toString();
                }
            }
        }
        // db 작업은 doinbackground 에서 (db는 어씽크에서)
        // false 가 default 니까 true 일 때만 새로 입력된 내용으로 바꿔줌 (db)
        @Override
        protected Object doInBackground(Term[] objects) {
            if(editable_list.get(position)) {
                if (finalTerm != null) {
                    // 여기에서 수정된 내용이 있는 entity 를 올려줌
                    APP.db.tdao().insertAll(finalTerm);
                } else {
                    APP.db.cdao().insertAll(finalCourse);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            // 버튼을 누를때마다, 해당 포지션에 있는 t / f 를 반대 껄로 set 해줌
            editable_list.set(position, !editable_list.get(position));
            holder.name.setEnabled(editable_list.get(position));
            // a ? b : c => a 가 true 면은 b, false 면 c (3항연산자)
            holder.edit.setText(editable_list.get(position) ? "SAVE":"EDIT");
        }
    }
}
