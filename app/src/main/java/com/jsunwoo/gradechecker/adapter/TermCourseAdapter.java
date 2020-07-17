package com.jsunwoo.gradechecker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Entity;

import com.jsunwoo.gradechecker.R;
import com.jsunwoo.gradechecker.entity.Course;
import com.jsunwoo.gradechecker.entity.Term;

import java.util.List;

public class TermCourseAdapter extends RecyclerView.Adapter<TermCourseAdapter.ViewHolder> {
    private List<Term> terms;
    private List<Course> courses;

    public TermCourseAdapter(List<Term> terms, List<Course> courses) {
        this.terms = terms;
        this.courses = courses;
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
    @Override
    public void onBindViewHolder(@NonNull TermCourseAdapter.ViewHolder holder, int position) {
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
    }

    // 스크롤 끝까지 했을때 값 (몇개인지) 보여주는 함수.
    @Override
    public int getItemCount() {
        if(terms !=null)
            return terms.size();
        else
            return courses.size();
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
}
