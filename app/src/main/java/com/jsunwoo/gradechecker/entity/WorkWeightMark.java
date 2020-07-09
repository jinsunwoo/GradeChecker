package com.jsunwoo.gradechecker.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class WorkWeightMark {
    @PrimaryKey(autoGenerate = true)
    public int wid;
    @ColumnInfo(name ="grade_item")
    public String gradeItem;
    @ColumnInfo
    public int weight;
    @ColumnInfo
    public int mark;
}









