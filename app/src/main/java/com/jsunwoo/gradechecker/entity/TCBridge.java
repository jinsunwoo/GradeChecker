package com.jsunwoo.gradechecker.entity;

import androidx.room.Entity;
@Entity(primaryKeys = {"bridgetid","bridgecid"})
public class TCBridge {
    public int bridgetid;
    public int bridgecid;

}
