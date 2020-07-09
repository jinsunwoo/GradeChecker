package com.jsunwoo.gradechecker.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jsunwoo.gradechecker.GradeCheckerResources;
import com.jsunwoo.gradechecker.R;
import com.jsunwoo.gradechecker.adapter.LetterGradeAdapter;
import com.jsunwoo.gradechecker.entity.GradeScale;
import com.jsunwoo.gradechecker.manager.GradeCheckerDB;

import java.util.List;

public class SettingActivity extends AppCompatActivity {
    private GradeCheckerResources APP;
    private LetterGradeAdapter lga;
    // 안드로이드 4대 구성요소는 디바이스 context 항상 가지고 있음.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Button saveBtn = findViewById(R.id.save_button);
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        APP = ((GradeCheckerResources)getApplication());
        // 메인쓰레드랑 백그라운드쓰레드랑 따로 움직이는 거기 때문에 메인에서 쓰던 lga 가 변하지 않도록 final 상수 처리를 해줘야 백그라운드에서 꼬이지 않고 잘 받을 수 있어서.
        new AsyncTask<Object, Object, Object>(){
            List<GradeScale> gradeScales;
            @Override
            protected Object doInBackground(Object[] objects) {
                gradeScales = APP.db.gsdao().getAll();
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                lga = new LetterGradeAdapter(SettingActivity.this, gradeScales);
                recyclerView.setAdapter(lga);
            }
        }.execute();

        // 쓰레드 (메인쓰레드, 그냥쓰레드)
        // 메인- 어플리케이션 실행하면 메니페스트 처음 가서 런처 - 세팅 -> 이런식으로 쭉 가는게 메인쓰레드가 하는일
        // 백그라운드 쓰레드에서 db 처리 하라는 말
        // 데이터 입력하는 것
        /*
        new AsyncTask<Object, Object, Object>(){

            @Override
            protected Object doInBackground(Object[] objects) {
                APP.db.gsdao().insertAll(lga.getGradescales());
                return null;
            }
        }.execute();

         */

        // 데이터 가져오는 것
//        db.gsdao().getAll();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                v.setClickable(false);
                new AsyncTask<Object, Object, Object>(){

                    @Override
                    protected Object doInBackground(Object[] objects) {
                        // 리스트로 안에 있는 데이터 다 가져오는
                        List<GradeScale> scales = APP.db.gsdao().getAll();
                        if(scales.size()<1){
                            APP.db.gsdao().insertAll(lga.getGradescales());
                        }else{
                            APP.db.gsdao().update(lga.getGradescales());
                        }
                        List<GradeScale> gs = APP.db.gsdao().getAll();
                        for (GradeScale gradeScale:gs) {
                            Log.d(SettingActivity.class.getSimpleName(), "doInBackground  "+gradeScale.gid+" : "+gradeScale.lowerCut+", "+gradeScale.upperCut);
                        }
                        return null;
                    }
                    // 백그라운드 쓰레드가 끝났을때 액티비티 실행시켜 주라는 말
                    @Override
                    protected void onPostExecute(Object o) {
                        super.onPostExecute(o);
                        v.setClickable(true);
                        Intent moveIntent = new Intent(SettingActivity.this, TermsActivity.class);
                        startActivity(moveIntent);

                    }
                }.execute();

            }
        });
    }
}