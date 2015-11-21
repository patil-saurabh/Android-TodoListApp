package com.codepath.saurabh.basictodoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditTaskActivity extends AppCompatActivity {

    private EditText editTask;
    private int position;
    public static String EDITED_TASK_TEXT = "EDITED_TASK_TEXT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        this.setTitle("Edit Task");

        editTask = (EditText)findViewById(R.id.etEditTask);
        editTask.setText(getIntent().getStringExtra(MainActivity.TASK_TEXT));
        position = Integer.parseInt(getIntent().getStringExtra(MainActivity.TASK_POSITION));
    }

    public void onSaveClick(View view){
        Intent intent = new Intent();
        intent.putExtra(EDITED_TASK_TEXT, editTask.getText().toString());
        intent.putExtra(MainActivity.TASK_POSITION, String.valueOf(position));
        setResult(RESULT_OK, intent);
        this.finish();

    }


}
