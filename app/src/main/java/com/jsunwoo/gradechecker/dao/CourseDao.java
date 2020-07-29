package com.jsunwoo.gradechecker.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.jsunwoo.gradechecker.entity.Course;
import com.jsunwoo.gradechecker.entity.Term;

import java.util.List;

@Dao
public interface CourseDao {
    @Query("SELECT * from Course")
    List<Course> getAll();
    // term 에 해당하는 코스아이디 여러개를 어레이에 넣어서 한번에 가져와서 couseIds 에 넣는 것
    @Query("SELECT * from Course WHERE cid IN(:courseIds)")
    List<Course> getAll(int...courseIds);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Course...courses);
    @Update
    int update(Course... courses);
    @Delete
    void deleteAll(Course... courses);
}
