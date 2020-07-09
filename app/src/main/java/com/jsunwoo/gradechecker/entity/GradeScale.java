package com.jsunwoo.gradechecker.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class GradeScale {
    @PrimaryKey(autoGenerate = true)
    public int gid;
    @ColumnInfo
    public String alphabet;
    @ColumnInfo(name = "lower_cut")
    public int lowerCut;
    @ColumnInfo(name = "upper_cut")
    public int upperCut;
}
