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
import com.jsunwoo.gradechecker.entity.Term;

import java.util.ArrayList;
import java.util.List;

public class TermsActivity extends AppCompatActivity {
    private List<Term> terms ;
    private GradeCheckerResources APP;
    private TermCourseAdapter tca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        TextView setting = findViewById(R.id.setting_textView);
        final Button addTermBtn = findViewById(R.id.addTerm_button);
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
            // 어씽크 쓸때는 메인이랑 백그라운드 쓰레드 따로 쓰다보니까 백그라운드 쓰레드에서 쓰는 변수는 final 처리 해야지 주소값 변경 안되고 null point 안뜸
            public void onClick(View v) {
                final Term newterm = new Term();
                newterm.termName = "Edit term name";

                new AsyncTask<Object,Object,Object>(){
                    @Override
                    protected Object doInBackground(Object... objects) {
                        APP.db.tdao().insertAll(newterm);
                        // 바뀐걸로 다시 가져오는
                        terms = APP.db.tdao().getAll();
                        // 어댑터랑 terms이랑 연결
                        tca.setList(terms);
                        Log.i("doInBackground: term size", terms.size()+"");
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

        new AsyncTask<Object, Object, Object>(){

            @Override
            protected Object doInBackground(Object[] objects) {
                // 데이터를 가지고 왔고 (아래는) 데이터가 없는 경우
                terms = APP.db.tdao().getAll();
                // 포인터도 없는 경우랑 포인터는 있는데 데이터가 없는 경우 두가지 다 고려. 오류 있는 경우는 -1 도 나와서 0이랑 음수 포함 (안전성 위해서)
                if (terms==null||terms.size()<=0) {
                    terms = new ArrayList<>();
                     for (int i =0;i<3;i++) {
                        Term term = new Term();
                        term.tid = i+1;
                        term.termName = "Term "+(i+1);
                        terms.add(term);
                    }
                    Term[] termArray = new Term[terms.size()];
                    // termArray 타입으로 받으려고 가로안에 (termArray) 씀. 배열의 개수가 필요해서. 그래서 변수에 넣어줌.
                    termArray = terms.toArray(termArray);
                    APP.db.tdao().insertAll(termArray);
                }
                return null;
            }

            @SuppressLint("StaticFieldLeak")
            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                // TermsActivity.this 안쓰는게 어댑터에서 context 안받았으니까// (수정됨 아래)
                // 컨택스트 자리에 TermsAcitivity 넣어주면 액태비티는 어차피 컨택스트 가지고 있으니까 이걸로 대신해도 됨
                tca = new TermCourseAdapter(terms, null, TermsActivity.this);
                rv.setAdapter(tca);
            }
        }.execute();
    }
}