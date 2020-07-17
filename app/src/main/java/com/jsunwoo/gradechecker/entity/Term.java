package com.jsunwoo.gradechecker.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class Term {
    @PrimaryKey(autoGenerate = true)
    public int tid;
    @ColumnInfo(name ="term_name")
    public String termName;
}
