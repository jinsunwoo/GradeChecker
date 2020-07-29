package com.jsunwoo.gradechecker.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.jsunwoo.gradechecker.entity.TCBridge;

import java.util.List;

@Dao
public interface BridgeTCDao {
    @Transaction
    @Query("SELECT * FROM TCBridge")
    public List<TCBridge> getTCBridge();
    // 여기서 termID 는 그냥 parameter 이름이고, term entity 에서 tid 넣어주는거는 소스에서 하니까 상관 없음
    @Transaction
    @Query("SELECT * FROM TCBridge where bridgetid = :termID")
    public List<TCBridge> getTCBridge(int termID);


}
