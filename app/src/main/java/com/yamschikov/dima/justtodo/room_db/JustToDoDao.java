package com.yamschikov.dima.justtodo.room_db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.nio.CharBuffer;
import java.util.List;

@Dao
public interface JustToDoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(JustToDoStructureTable justToDoStructureTable);

    @Update
    void update(JustToDoStructureTable justToDoStructureTable);

    @Delete
    void delete(JustToDoStructureTable justToDoStructureTable);

    @Query("SELECT * FROM JustToDoStructureTable")
    LiveData<List<JustToDoStructureTable>> getAllLiveData();

    // query category //

    //AllTasks
    @Query("SELECT * FROM JustToDoStructureTable WHERE task_user_id LIKE :task_user_id")
    LiveData<List<JustToDoStructureTable>> getAllTasksLiveData(String task_user_id);

    //Today
    @Query("SELECT * FROM JustToDoStructureTable WHERE task_date LIKE :task_date AND task_user_id LIKE :task_user_id")
    LiveData<List<JustToDoStructureTable>> getTasksTodayLiveData(String task_date, String task_user_id);

    //Work
    @Query("SELECT * FROM JustToDoStructureTable WHERE task_category LIKE :task_category AND task_user_id LIKE :task_user_id")
    LiveData<List<JustToDoStructureTable>> getTasksWorkLiveData(String task_category, String task_user_id);



    //Home
    @Query("SELECT * FROM JustToDoStructureTable WHERE task_category LIKE :task_category AND task_user_id LIKE :task_user_id")
    LiveData<List<JustToDoStructureTable>> getTasksHomeLiveData(String task_category, String task_user_id);

    //Personal
    @Query("SELECT * FROM JustToDoStructureTable WHERE task_category LIKE :task_category AND task_user_id LIKE :task_user_id")
    LiveData<List<JustToDoStructureTable>> getTasksPersonalLiveData(String task_category, String task_user_id);

    //Store
    @Query("SELECT * FROM JustToDoStructureTable WHERE task_category LIKE :task_category AND task_user_id LIKE :task_user_id")
    LiveData<List<JustToDoStructureTable>> getTasksStoreLiveData(String task_category, String task_user_id);
}