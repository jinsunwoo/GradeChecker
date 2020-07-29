package com.jsunwoo.gradechecker.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

public class CourseRelation {
    @Embedded
    public Course course;

    @Relation(
            parentColumn = "cid",
            entityColumn = "bridgecid"
    )

    public TCBridge bridge;
}
