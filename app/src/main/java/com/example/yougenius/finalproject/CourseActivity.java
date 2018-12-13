package com.example.yougenius.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CourseActivity extends AppCompatActivity {

    private Button exam,addCourse,back;
    private String courseName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        back = findViewById(R.id.back_to_list);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseActivity.this,StudentActivity.class);
                startActivity(intent);
            }
        });

        addCourse=findViewById(R.id.addCourse);
        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCourseToMyCourses();
                }

            private void addCourseToMyCourses(){


            }

        });





}}







