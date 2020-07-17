package com.jsunwoo.gradechecker.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
/*GradeScale java 변수에 맞게 DB 테이블 column name 작성 & 연결 **테이블의 구조** */
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
