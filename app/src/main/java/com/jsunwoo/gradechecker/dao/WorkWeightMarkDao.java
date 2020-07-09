package com.jsunwoo.gradechecker.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.jsunwoo.gradechecker.entity.WorkWeightMark;
@Dao
public interface WorkWeightMarkDao {
    @Insert
    void insertAll(WorkWeightMark... workWeightMarks);
}
