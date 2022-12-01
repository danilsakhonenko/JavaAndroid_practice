package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.LayoutInflater;
import android.widget.RemoteViews;
import android.widget.Toast;
import android.app.NotificationChannel;

public class MainActivity extends AppCompatActivity {

    private Toast toast;
    private static final int NOTIFY_ID = 1;
    private static final String CHANNEL_ID = "channel1";
    private static NotificationManager manager;
    private NotificationCompat.Builder notificationBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1, button2;
        button1 = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        LayoutInflater inflater = getLayoutInflater();
        View toast_layout = inflater.inflate(R.layout.toastnotification_layout, findViewById(R.id.toast_container));
        toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(toast_layout);
        button1.setOnClickListener(view -> toast.show());

        RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.statusnotification_layout);
        manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationBuilder =
                        new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                                .setAutoCancel(false)
                                .setSmallIcon(R.drawable.ic_launcher_foreground)
                                .setWhen(System.currentTimeMillis())
                                .setCustomContentView(remoteViews);
                createChanelIfNeeded(manager);
                manager.notify(NOTIFY_ID, notificationBuilder.build());
            }
        });
    }

    public static void createChanelIfNeeded(NotificationManager manager){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel nc = new NotificationChannel(CHANNEL_ID,CHANNEL_ID,NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(nc);
        }
    }
}