package com.jsunwoo.gradechecker;

import android.app.Application;
import android.util.Log;

import androidx.room.Room;

import com.jsunwoo.gradechecker.manager.GradeCheckerDB;


// 이 어플리케이션이 실행될 때 사용되는 인스턴스
// 어플리케이션 [레시피 - 스테틱(근본적) , 음식 - 힙 (유동적-상황에 따라)]
// 어플리케이션 키면, 레시피대로 실행시켜서 메모리상에 옮기는게 화면에 나옴
// 책들만 모아논곳이 static, class memory /// 실제로 처리하는 곳은 heap
public class GradeCheckerResources extends Application {
    public GradeCheckerDB db;
    @Override
    public void onCreate() {
        super.onCreate();
        // setting 화면 들어올때 앱내에서 데이터 폴더 안에서 자동으로 db 파일이 만들어짐 "grade_checker_db_file"
        // 이 파일은 테이블들이 다 들어간거. grade_checker_db_file = schema
        try{
            db = Room.databaseBuilder(getApplicationContext(), GradeCheckerDB.class, "grade_checker_db_file").build();
        }catch (Exception e ) {
            e.printStackTrace();
            Log.e(GradeCheckerResources.class.getSimpleName(), "onCreate: "+e.getMessage());
        }
    }
}


