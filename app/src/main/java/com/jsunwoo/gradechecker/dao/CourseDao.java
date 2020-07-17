package com.jsunwoo.gradechecker.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.jsunwoo.gradechecker.entity.Course;

import java.util.List;

@Dao
public interface CourseDao {
    @Query("SELECT * from Course")
    List<Course> getAll();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Course...courses);
}
