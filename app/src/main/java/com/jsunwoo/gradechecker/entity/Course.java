package com.jsunwoo.gradechecker.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Course {
    @PrimaryKey(autoGenerate = true)
    public int cid;
    @ColumnInfo(name="course_name")
    public String courseName;
    @ColumnInfo(name="term_id")
    public int termId;
}
