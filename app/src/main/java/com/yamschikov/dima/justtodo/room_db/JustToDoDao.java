package com.yamschikov.dima.justtodo.room_db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;

import java.nio.CharBuffer;

@Dao
public interface JustToDoDao {

    @Insert
    void insert(JustToDoStructureTable justToDoStructureTable);

    @Update
    void update(JustToDoStructureTable justToDoStructureTable);

    @Delete
    void delete(JustToDoStructureTable justToDoStructureTable);

}