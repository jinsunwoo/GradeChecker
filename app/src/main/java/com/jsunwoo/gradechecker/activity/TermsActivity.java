package com.jsunwoo.gradechecker.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jsunwoo.gradechecker.GradeCheckerResources;
import com.jsunwoo.gradechecker.R;
import com.jsunwoo.gradechecker.adapter.TermCourseAdapter;
import com.jsunwoo.gradechecker.entity.Term;

import java.util.ArrayList;
import java.util.List;

public class TermsActivity extends AppCompatActivity {

    private GradeCheckerResources APP;
    private TermCourseAdapter tca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        TextView setting = findViewById(R.id.setting_textView);
        Button addTermBtn = findViewById(R.id.addTerm_button);
        final RecyclerView rv = findViewById(R.id.terms_recyclerView);

        APP = ((GradeCheckerResources)getApplication());

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

        new AsyncTask<Object, Object, Object>(){
            List<Term> terms ;
            @Override
            protected Object doInBackground(Object[] objects) {
                // 데이터를 가지고 왔고 (아래는) 데이터가 없는 경우
                terms = APP.db.tdao().getAll();
                // 포인터도 없는 경우랑 포인터는 있는데 데이터가 없는 경우 두가지 다 고려. 오류 있는 경우는 -1 도 나와서 0이랑 음수 포함 (안전성 위해서)
                if (terms==null||terms.size()<=0) {
                    terms = new ArrayList<>();
                    for (int i =0;i<3;i++){
                        Term term = new Term();
                        term.tid = i+1;
                        term.termName = "Term "+(i+1);
                        terms.add(term);
                    }

                }
                return null;
            }
            //
            @SuppressLint("StaticFieldLeak")
            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                // TermsActivity.this 안쓰는게 어댑터에서 context 안받았으니까
                tca = new TermCourseAdapter(terms, null);
                rv.setAdapter(tca);
            }
        }.execute();





    }
}