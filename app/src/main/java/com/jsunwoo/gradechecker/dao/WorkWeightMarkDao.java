package com.jsunwoo.gradechecker.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.jsunwoo.gradechecker.entity.Course;
import com.jsunwoo.gradechecker.entity.WorkWeightMark;

import java.util.List;

@Dao
public interface WorkWeightMarkDao {
    @Insert
    void insertAll(WorkWeightMark... workWeightMarks);
    @Query("SELECT * from WorkWeightMark WHERE course_id =:courseId")
    List<WorkWeightMark> getCourseIDforBridge(int courseId);
    @Query ("SELECT MAX(wid) from WorkWeightMark")
    int getCountAll();
}
