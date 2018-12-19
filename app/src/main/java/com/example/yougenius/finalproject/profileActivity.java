package com.example.yougenius.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class profileActivity extends AppCompatActivity {

    Button validate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        validate = (Button)findViewById(R.id.validate);

        validate.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent validateIntent = new Intent(profileActivity.this, StudentActivity.class);
                startActivity(validateIntent);
            }
        });
    }


}
