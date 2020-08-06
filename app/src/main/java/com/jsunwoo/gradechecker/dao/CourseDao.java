package com.jsunwoo.gradechecker.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.jsunwoo.gradechecker.entity.Course;
import com.jsunwoo.gradechecker.entity.Term;

import java.util.List;

@Dao
public interface CourseDao {
    @Query ("SELECT MAX(cid) from Course")
    int getCountAll();

    @Query("SELECT * from Course")
    List<Course> getAll();
    // term 에 해당하는 코스아이디 여러개를 어레이에 넣어서 한번에 가져와서 couseIds 에 넣는 것 // IN 은 전부다 검색
    @Query("SELECT * from Course WHERE cid IN(:courseIds)")
    List<Course> getAll(int...courseIds);
    // term_id 해당하는 행들 중에서 패러미터에 들어온 terId와 일치하는 것(중복인 경우 여러개 ex: 1 1 1) // 위에 IN 으로 할 필요 없이 = 쓰면 해당되는것 만 나옴
    @Query("SELECT * from Course WHERE term_id =:termId")
    List<Course> getTermIDforBridge(int termId);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Course...courses);
    @Update
    int update(Course... courses);
    @Delete
    void deleteAll(Course... courses);
}
