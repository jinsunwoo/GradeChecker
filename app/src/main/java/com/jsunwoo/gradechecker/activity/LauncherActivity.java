package com.jsunwoo.gradechecker.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.jsunwoo.gradechecker.R;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent moveIntent = new Intent();
                moveIntent.setClass(LauncherActivity.this, SettingActivity.class);
                startActivity(moveIntent);
            }
        }, 2000);

    }
}