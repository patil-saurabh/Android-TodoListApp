package com.codepath.saurabh.basictodoapp;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by saupatil on 11/21/2015.
 */
@Table(name = "TodoTask")
public class TodoTask extends Model {

    @Column(name = "taskId")
    private int taskId;

    @Column(name = "title")
    private String title;

    public TodoTask(){
        super();
    }

    public TodoTask(int taskId, String title) {
        super();
        this.taskId = taskId;
        this.title = title;
    }

    public String toString(){
        return this.title;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
