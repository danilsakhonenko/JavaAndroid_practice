package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TableLayout;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab4.db.MuseumsDBManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    public MuseumsDBManager dbManager;
    private TableLayout tableLayout;
    TableRow checkedRow;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        dbManager=new MuseumsDBManager(this);
        Button addButt,deleteButt,editButt;
        addButt=findViewById(R.id.addButton);
        deleteButt=findViewById(R.id.deleteButton);
        editButt=findViewById(R.id.editButton);
        addButt.setOnClickListener(view -> startAddActivity());
        editButt.setOnClickListener(view -> {
            if(checkedRow==null){
                showToast();
            }else {startEditActivity();}
        });
        deleteButt.setOnClickListener(view -> {
            if(checkedRow==null){
                showToast();
            }else {
                dbManager.delete(((TextView) checkedRow.getChildAt(0)).getText().toString());
                LoadTable();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbManager.StartDB();
        LoadTable();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.closeDB();
    }

    public void LoadTable(){
        tableLayout.removeAllViews();
        ArrayList<String> list = dbManager.selectAll();
        for (int i=0;i<list.size();){
            TableRow tableRow = new TableRow(this);
            tableRow.setOnClickListener(view -> {
                if(checkedRow!=null)
                    checkedRow.setBackgroundColor(tableLayout.getDrawingCacheBackgroundColor());
                checkedRow=tableRow;
                tableRow.setBackgroundColor(Color.rgb(204,204,204));
            });
            tableRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            for (int j = 0; j < 7; j++,i++) {
                TextView tv = new TextView(this);
                tv.setGravity(Gravity.CENTER);
                LayoutParams params = new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.14f);
                tv.setLayoutParams(params);
                tv.setText(list.get(i));
                tableRow.addView(tv);
            }
            tableLayout.addView(tableRow);
        }
        checkedRow=null;
    }

    public void startAddActivity(){
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
        LoadTable();
    }

    public void showToast() {
        Toast toast = Toast.makeText(getApplicationContext(),
                "Ни одна запись не выбрана",
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void startEditActivity(){
        Intent intent = new Intent(this, AddActivity.class);
        intent.putExtra("id",((TextView)checkedRow.getChildAt(0)).getText().toString());
        intent.putExtra("museum",((TextView)checkedRow.getChildAt(1)).getText().toString());
        intent.putExtra("city",((TextView)checkedRow.getChildAt(2)).getText().toString());
        intent.putExtra("year",((TextView)checkedRow.getChildAt(3)).getText().toString());
        intent.putExtra("phone",((TextView)checkedRow.getChildAt(4)).getText().toString());
        intent.putExtra("exhibits",((TextView)checkedRow.getChildAt(5)).getText().toString());
        intent.putExtra("priv",((TextView)checkedRow.getChildAt(6)).getText().toString());
        startActivity(intent);
        LoadTable();
    }
}