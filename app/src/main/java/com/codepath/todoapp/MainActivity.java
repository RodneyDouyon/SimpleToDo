package com.codepath.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> todoItems;
    ArrayAdapter<String> aToDoAdapter;
    ListView lvItems;
    EditText etEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aToDoAdapter);
        etEditText = (EditText) findViewById(R.id.etEditText);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                todoItems.remove(position);
                aToDoAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openEditItemActivity();
            }
        });
    }

    public void openEditItemActivity(){
        //First parameter is context, second is class of the second activity
        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
        //Putting "Extras"
       i.putExtra("lvItems","editItemText");
       startActivity(i);
    }

    private final int REQUEST_CODE = 20;
    //Launching activity for a result.
    public  void onClick (View view){
        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
        i.putExtra("mode", 2);//This passes data to second activity.
        startActivityForResult(i,REQUEST_CODE);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode,Intent data){
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            //Extracting Name value from result extras
            String name = data.getExtras().getString("name");
            int code = data.getExtras().getInt("code", 0);
            //Temporary display of name on screen by using toast...
            Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
        }
    }

    public void populateArrayItems(){
        todoItems = new ArrayList<String>();
        readItems();
        aToDoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
    }

    private void readItems(){
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try{
            todoItems = new ArrayList<String>(FileUtils.readLines(file));
        }
        catch (IOException e){
        }
    }

    private void writeItems(){
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try{
            FileUtils.writeLines(file,todoItems);
        }
        catch (IOException e){
        }
    }

    public void onAddItem (View view){
        aToDoAdapter.add(etEditText.getText().toString());
        etEditText.setText("");
        writeItems();
    }

}
