package com.jsunwoo.gradechecker.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.jsunwoo.gradechecker.entity.Course;
import com.jsunwoo.gradechecker.entity.GradeScale;
import com.jsunwoo.gradechecker.entity.Term;

import java.util.List;

@Dao
public interface TermDao {
    @Query("SELECT * FROM Term")
    List<Term> getAll();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Term... terms);
    @Update
    int update(Term... terms);
    @Delete
    void deleteAll(Term... terms);
}
