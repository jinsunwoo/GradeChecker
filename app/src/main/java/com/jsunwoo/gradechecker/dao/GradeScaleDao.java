package com.jsunwoo.gradechecker.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.jsunwoo.gradechecker.entity.GradeScale;

import java.util.List;

// dao : data access object
// entity 는 테이블의 구조
// dao 는 그 테이블에 접근해서 요청한대로 보여주거나 동작하는 것
@Dao
public interface GradeScaleDao {
    // 쿼리 뒤에 써놓은 대로 실행해서 해당 내용을 리스트 형태로 만들어서 리턴하는 메쏘드 getAll(); // getAll 은 dao 안에 만들어져 있는 함수
    @Query("SELECT * FROM GradeScale")
    List<GradeScale> getAll();
    // (GradeScale... gradescales) => GradeScale 의 객체 이거나 객체의 배열이거나
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    // GradeScale 타입의 애를 하나 넣거나 여러개 넣는, 변수가 gradescales
    // 위에 insert 라고 쓴 커맨드를 행한 결과가 이 메쏘드 insertAll (이건 내가 만드는 것. dao ㅇㅔ 없음)
    // annotation 이 있으면 위에, 그 아래에 있는 함수가 그 위에 있는 @ 대로 행동하게 되는것 (같이 움직임)
    void insertAll(GradeScale... gradescales);
    @Update
    int update(GradeScale... gradescales);
}
