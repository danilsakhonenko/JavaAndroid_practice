package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.example.lab4.db.MuseumsDBManager;

public class AddActivity extends AppCompatActivity {

    private MuseumsDBManager manager;
    private int editflag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getIntent().getStringExtra("id")!=null)
            editflag=1;
        else
            editflag=0;
        setContentView(R.layout.activity_add);
        Button button=findViewById(R.id.button);
        TextView header;
        EditText museum,city,year,phone,exhibits;
        Switch priv;
        header=findViewById(R.id.header);
        museum=findViewById(R.id.Museum);
        city=findViewById(R.id.City);
        year=findViewById(R.id.Year);
        phone=findViewById(R.id.Phone);
        exhibits=findViewById(R.id.Exhibits);
        priv=findViewById(R.id.Private);
        manager=new MuseumsDBManager(getApplicationContext());
        if(editflag==1){
            museum.setText(getIntent().getStringExtra("museum"));
            city.setText(getIntent().getStringExtra("city"));
            year.setText(getIntent().getStringExtra("year"));
            phone.setText(getIntent().getStringExtra("phone"));
            exhibits.setText(getIntent().getStringExtra("exhibits"));
            priv.setChecked(
                    (Integer.parseInt(getIntent().getStringExtra("priv")))>0
                    );
            header.setText("Редактирование записи");
            button.setText("Сохранить");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    manager.StartDB();
                    manager.update(new String[] {museum.getText().toString(),city.getText().toString(),
                            year.getText().toString(),phone.getText().toString(),
                            exhibits.getText().toString(),priv.isChecked() ? "1":"0", getIntent().getStringExtra("id")});
                    manager.closeDB();
                    onBackPressed();
                }
            });
        }
        else {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    manager.StartDB();
                    manager.insert(museum.getText().toString(), city.getText().toString(),
                            Integer.parseInt(year.getText().toString()), phone.getText().toString(),
                            Integer.parseInt(exhibits.getText().toString()), priv.isChecked() ? 1 : 0);
                    manager.closeDB();
                    onBackPressed();
                }
            });
        }
    }
}