package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sms_btn=findViewById(R.id.sms_btn);
        Button mms_btn=findViewById(R.id.mms_btn);
        Button add_btn=findViewById(R.id.add_contacts);
        sms_btn.setOnClickListener(view -> {
            Intent intent = new Intent(this, SMSActivity.class);
            startActivity(intent);
            //LoadTable();
        });
        mms_btn.setOnClickListener(view -> {
            Intent intent = new Intent(this, MMSActivity.class);
            startActivity(intent);
            //LoadTable();
        });
        add_btn.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_INSERT);
            intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
            startActivity(intent);
        });
    }
}