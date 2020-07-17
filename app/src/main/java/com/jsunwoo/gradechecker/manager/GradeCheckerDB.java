package com.jsunwoo.gradechecker.manager;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.jsunwoo.gradechecker.dao.CourseDao;
import com.jsunwoo.gradechecker.dao.GradeScaleDao;
import com.jsunwoo.gradechecker.dao.TermDao;
import com.jsunwoo.gradechecker.dao.WorkWeightMarkDao;
import com.jsunwoo.gradechecker.entity.Course;
import com.jsunwoo.gradechecker.entity.GradeScale;
import com.jsunwoo.gradechecker.entity.Term;
import com.jsunwoo.gradechecker.entity.WorkWeightMark;

/*
외부에서 dao 를 가져올 수 있게 하는 역할
 */
// 상속받았지만 무조건 써야 하는 함수 안쓰려고 abstract.
// 직접 써주던가 매뉴얼대로 room data base 문법대로 쓰던가...
@Database(entities = {GradeScale.class, WorkWeightMark.class, Term.class, Course.class},version = 1)
public abstract class GradeCheckerDB extends RoomDatabase {
    // GradeScaleDao 에 해당하는 getAll, insertAll, update 을 모두 실행? gsdao 라는 메소드 이름으로?
    public abstract GradeScaleDao gsdao();
    public abstract WorkWeightMarkDao wwmdao();
    public abstract TermDao tdao();
    public abstract CourseDao cdao();
}




