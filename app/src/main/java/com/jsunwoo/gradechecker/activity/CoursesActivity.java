package com.jsunwoo.gradechecker.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jsunwoo.gradechecker.R;

public class CoursesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        Button addCourseBtn = findViewById(R.id.add_course_button);
        Button checkMarkBtn = findViewById(R.id.check_marks_button);

        addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveIntent = new Intent(CoursesActivity.this, CourseWorksActivity.class);
                startActivity(moveIntent);
            }
        });

        checkMarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveIntent = new Intent(CoursesActivity.this, CheckMarksActivity.class);
                startActivity(moveIntent);
            }
        });
    }
}