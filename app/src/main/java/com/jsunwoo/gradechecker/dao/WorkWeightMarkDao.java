package com.jsunwoo.gradechecker.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.jsunwoo.gradechecker.entity.Course;
import com.jsunwoo.gradechecker.entity.WorkWeightMark;

import java.util.List;

@Dao
public interface WorkWeightMarkDao {
    // 값을 입력할때 (대체 되는, 업데이트 시 wid기준) 아래 어노테이션 및 가로 적어줘야함
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(WorkWeightMark... workWeightMarks);
    @Query("SELECT * from WorkWeightMark WHERE course_id =:courseId")
    List<WorkWeightMark> getCourseIDforBridge(int courseId);
    @Query ("SELECT MAX(wid) from WorkWeightMark")
    int getCountAll();
}
