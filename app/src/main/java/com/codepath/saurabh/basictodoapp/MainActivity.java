package com.codepath.saurabh.basictodoapp;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvItems;
    private EditText etNewTask;

    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;

    private static String fileName = "tasks.txt";
    private static int REQUEST_CODE = 8192;
    public static String TASK_TEXT = "TaskText";
    public static String TASK_POSITION = "TaskPosition";
    public static String TASK_DELETED_TOAST = "Task deleted!";
    public static String TASK_EDITED_TOAST = "Task edited.";
    public static String TASK_ADDED_TOAST = "New task added!";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get the text entered in the new task field & current items
        etNewTask = (EditText) findViewById(R.id.etNewTask);
        lvItems = (ListView) findViewById(R.id.lvItems);

        readTasksFromFile();
        //create itemsAdapter to attach to the List View.
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,items );

        lvItems.setAdapter(itemsAdapter);

        setUpListVIewListener();
    }

    public void onAddTask(View view){
        //get the new task entered in the edit text box
        EditText etNewTask = (EditText) findViewById(R.id.etNewTask);
        String newTask = etNewTask.getText().toString();

        //and add the new task to the adapter.
        // Okay so you add new things to List Adapter and not directly to the ListView.
        itemsAdapter.add(newTask);

        //erase the new task entered from the New Task field.
        etNewTask.setText("");
        Toast.makeText(this, TASK_ADDED_TOAST, Toast.LENGTH_LONG).show();
        writeTasksToFile();
    }

    private void setUpListVIewListener(){
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                        items.remove(position);
                        itemsAdapter.notifyDataSetChanged();
                        Toast.makeText(view.getContext(), TASK_DELETED_TOAST, Toast.LENGTH_SHORT).show();
                        writeTasksToFile();
                        return true;
                    }
                }

        );

        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        lauchEditTextActivity(view, position);
                    }

                }
        );


    }

    private void lauchEditTextActivity(View view, int position){
        Intent editTaskIntent = new Intent(this,EditTaskActivity.class);
        editTaskIntent.putExtra(TASK_TEXT, ((TextView) view).getText().toString());
        editTaskIntent.putExtra(TASK_POSITION, String.valueOf(position));
        startActivityForResult(editTaskIntent, REQUEST_CODE);
    }


    private void writeTasksToFile(){
        File filesDir = getFilesDir();
        File tasksFile = new File(filesDir, "tasks.txt");
        try{
            FileUtils.writeLines(tasksFile, items);
        }catch(IOException ioEx){
            ioEx.printStackTrace();
        }
    }

    private void readTasksFromFile(){
        File filesDir = getFilesDir();
        File tasksFile = new File( filesDir,"tasks.txt");

        try{
            items = new ArrayList<String>(FileUtils.readLines(tasksFile));
        }catch(IOException ioEx){
            items = new ArrayList<>();
            ioEx.printStackTrace();
        }
    }

//not an overridden method, just a callback method that the system calls when the Dest.Activity finishes.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_CODE && resultCode==RESULT_OK ){
            String newText = data.getStringExtra(EditTaskActivity.EDITED_TASK_TEXT);
            int position = Integer.parseInt(data.getStringExtra(TASK_POSITION));
            /*TextView task = (TextView)lvItems.getChildAt(position);
            task.setText(newText);*/

            String oldText = items.get(position);
            items.set(position, newText);

            itemsAdapter.notifyDataSetChanged();
            if(!oldText.equals(newText)){
                Toast.makeText(this, TASK_EDITED_TOAST, Toast.LENGTH_LONG).show();
            }
            writeTasksToFile();
        }

    }
}
