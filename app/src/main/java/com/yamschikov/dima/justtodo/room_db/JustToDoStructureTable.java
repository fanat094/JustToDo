package com.yamschikov.dima.justtodo.room_db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class JustToDoStructureTable {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String task_title;

    public String task_content;

    public String task_date;

    public String task_category;

    public String task_user_id;

}