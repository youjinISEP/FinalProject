package com.example.yougenius.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class S_courselist extends AppCompatActivity {

    Button back;
    ListView courseList;
    public ArrayList<String> coursecontent = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_courselist);

        back = (Button)findViewById(R.id.back_to_student);
        courseList = (ListView) findViewById(R.id.courselist);

        coursecontent.add("C++");
        coursecontent.add("JAVA");
        coursecontent.add("HTML");
        coursecontent.add("SQL");
        coursecontent.add("PYTHON");

        back.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(S_courselist.this, StudentActivity.class );
                startActivity(backIntent);
            }
        });

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,coursecontent);

        courseList.setAdapter(arrayAdapter);
        courseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(),CourseActivity.class);
                intent.putExtra("CourseName",coursecontent.get(position));
                startActivity(intent);
            }
        });


    }
}
