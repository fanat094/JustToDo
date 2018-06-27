package com.yamschikov.dima.justtodo.room_db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class JustToDoStructureTable {

    @PrimaryKey
    public long id;

    public int id_task;

    public String task_title;

    public String task_content;

    public String task_date;

    public String task_category;

    public String task_user_id;

    public JustToDoStructureTable(long id, int id_task, String task_title, String task_content,
                                  String task_date, String task_category, String task_user_id) {
        this.id = id;
        this.id_task = id_task;
        this.task_title = task_title;
        this.task_content = task_content;
        this.task_date = task_date;
        this.task_category = task_category;
        this.task_user_id = task_user_id;
    }
}