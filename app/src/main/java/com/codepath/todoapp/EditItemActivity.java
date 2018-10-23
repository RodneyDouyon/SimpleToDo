package com.codepath.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class EditItemActivity extends AppCompatActivity {

    //Activity two (Subactivity) Which accesses the data passed and modifies it.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        TextView myTextView;
        myTextView = findViewById(R.id.featureTitle);
        String lvItems = getIntent().getStringExtra("lvItems");
        int code = getIntent().getIntExtra("code", 0);
    }


    //Returning data to parent activity
    public void onSubmit(View v){
        EditText etName = (EditText) findViewById(R.id.editItemText);
        //Preparing data intent
        Intent data = new Intent();
        //Passing given data back as result
        data.putExtra("name", etName.getText().toString());
        data.putExtra("code", 200);
        //End of activity, returning data.
        setResult(RESULT_OK, data);
        this.finish();//Ends activity and passes data along to previous activity.
    }

}
