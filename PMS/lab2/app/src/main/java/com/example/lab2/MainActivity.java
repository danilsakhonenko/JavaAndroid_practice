package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1,button2,button3,button4;
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button1.setOnClickListener(view -> openActivity(Activity2.class));
        button2.setOnClickListener(view -> openActivity(Activity3.class));
        button3.setOnClickListener(view -> openActivity(Activity4.class));
        button4.setOnClickListener(view -> finish());
    }

    public void openActivity(Class activity){
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}