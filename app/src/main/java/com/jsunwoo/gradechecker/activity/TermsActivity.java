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
    private List<Term> terms;
    private GradeCheckerResources APP;
    private TermCourseAdapter tca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        TextView setting = findViewById(R.id.setting_textView);
        final Button addTermBtn = findViewById(R.id.addTerm_button);
        final RecyclerView rv = findViewById(R.id.terms_recyclerView);
        // DB 작업 할 수 있는 수단
        APP = ((GradeCheckerResources)getApplication());
        // setting 버튼 누르면 현 페이지에서 SettingActivity.class 해당하는 페이지로 이동
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveIntent = new Intent(TermsActivity.this, SettingActivity.class);
                startActivity(moveIntent);
            }
        });
        // Add new Term 버튼 누르면
        addTermBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            // termName 이 "Edit Term Name" 인 Term 객체 하나 만들고
            public void onClick(View v) {
                // Async 쓸때는 메인 쓰레드, 백그라운드 쓰레드 따로 쓰다보니까, 백그라운드 쓰레드에서 쓰는 변수는 final 처리를 해줘야 주소값 변경도 안되고 null point 안뜸
                final Term newterm = new Term();
                newterm.termName = "Edit Term Name";

                new AsyncTask<Object,Object,Object>(){
                    @Override
                    protected Object doInBackground(Object... objects) {
                        // 새로운 Term 객체를 DB 에 밀어넣고
                        APP.db.tdao().insertAll(newterm);
                        // 추가된 Term 객체 포함한 전체 Terms 들을 DB 에서 가져와서 terms 에 할당 (UPDATE)
                        terms = APP.db.tdao().getAll();
                        // 업데이트 된 terms 를 어댑터 tca 와 연결
                        tca.setListTerm(terms);
                        Log.i("doInBackground: term size", terms.size()+"");
                        return null;
                    }
                    // tca 어댑터가 변화된(추가된) 데이터 감지하게
                    @Override
                    protected void onPostExecute(Object o) {
                        super.onPostExecute(o);
                        tca.notifyDataSetChanged();
                    }
                }.execute();
            }
        });

        /*
            현재 DB 에 User 에 의해 저장된 Term 정보가 없을 때 (초기설정상태)
            임의로 Term1, Term2, Term3 를 만들어서 DB 에 넣어줌
             */
        new AsyncTask<Object, Object, Object>(){
            @Override
            protected Object doInBackground(Object[] objects) {
                // DB 에 저장되있는 Term 객체들을 가져와 terms 에 넣어줌
                terms = APP.db.tdao().getAll();
                // DB 에서 가져온 Term 객체가 없는 경우 임의로 term 1, 2, 3 만들어서 terms 에 넣어줌
                // 포인터도 없는 경우, 포인터는 있는데 데이터가 없는 경우, 오류가 있는 경우(-1) 모두 체크
                if (terms==null||terms.size()<=0) {
                    terms = new ArrayList<>();
                     for (int i =0;i<3;i++) {
                        Term term = new Term();
                        term.tid = i+1;
                        term.termName = "Term "+(i+1);
                        terms.add(term);
                    }
                     // 사이즈 3 짜리 Term 타입의 어레이 termArray 를 만들어서
                    Term[] termArray = new Term[terms.size()];
                    // 아까 만든 어레이리스트 terms 를 어레이로 변환 후 위에서 만든 termArray 에 할당해줌
                    // termArray 타입으로 받으러고 가로안에 (termArray) 넣어줌
                    termArray = terms.toArray(termArray);
                    // 새로만든 임의의 termArray DB 에 넣어줌
                    APP.db.tdao().insertAll(termArray);
                }
                return null;
            }

            @SuppressLint("StaticFieldLeak")
            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                // context 자리에 TermsActivity 넣어줘도 어차피 activity 는 context 가지고 있으므로 이걸로 대체 됨
                tca = new TermCourseAdapter(terms, null, TermsActivity.this);
                rv.setAdapter(tca);
            }
        }.execute();
    }
}