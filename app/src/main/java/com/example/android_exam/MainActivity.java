package com.example.android_exam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android_exam.rowCall.PersonalInfo;
import com.example.android_exam.views.PersonalInfoActivity;

public class MainActivity extends AppCompatActivity {

    Button personInfoButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        personInfoButton = findViewById(R.id.personalInfoButton);

        personInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launch = new Intent(MainActivity.this, PersonalInfoActivity.class);
                startActivity(launch);
            }
        });
    }

}