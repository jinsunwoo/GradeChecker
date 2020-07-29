package com.jsunwoo.gradechecker.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class TermRelation {

    @Embedded
    public Term term;

    @Relation(
            parentColumn = "tid",
            entityColumn = "bridgetid"
    )

    public List<TCBridge> bridgeList;


}
