package com.jsunwoo.gradechecker.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jsunwoo.gradechecker.R;

public class TermsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        TextView setting = findViewById(R.id.setting_textView);
        Button addTermBtn = findViewById(R.id.addTerm_button);

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveIntent = new Intent(TermsActivity.this, SettingActivity.class);
                startActivity(moveIntent);
            }
        });

        addTermBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveIntent = new Intent(TermsActivity.this, CoursesActivity.class);
                startActivity(moveIntent);
            }
        });
    }
}