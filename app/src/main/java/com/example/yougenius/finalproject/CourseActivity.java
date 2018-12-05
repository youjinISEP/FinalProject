package com.example.yougenius.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CourseActivity extends AppCompatActivity {

    Button exam,addCourse,back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        exam = (Button)findViewById(R.id.examButton);
        addCourse = (Button)findViewById(R.id.addButton);
        back = (Button)findViewById(R.id.back_to_list);
/*
        exam.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent examIntent = new Intent(CourseActivity.this,QUIZ1.class);
                startActivity(examIntent);
            }
        });
*/
        addCourse.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add the course name to my course list. and show the dialog.
            }
        });

        back.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(CourseActivity.this, S_courselist.class);
                startActivity(backIntent);
            }
        });
    }
}
