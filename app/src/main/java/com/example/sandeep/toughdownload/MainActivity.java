package com.example.sandeep.toughdownload;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    Button download;
    Intent intent;
    /*public  static final String DOWNLOAD_IN_PROGRESS = "download in progress";
    public  static final String DOWNLOAD_COMPLETED = "downloaded";
    IntentFilter intentFilter;

    NotificationManager notificationManager;
    NotificationCompat.Builder builder;
*/
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        download = (Button)findViewById(R.id.download);
        download.setOnClickListener(this);
       /* intentFilter = new IntentFilter();
        intentFilter.addAction(DOWNLOAD_IN_PROGRESS);
        intentFilter.addAction(DOWNLOAD_COMPLETED);
    */}

  /*  @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
*/
    @Override
    public void onClick(View v)
    {
        intent = new Intent(this,Download_Data.class);
       // intent.putExtra("url","https://www.planwallpaper.com/static/images/HD-Wallpaer.jpg");
        intent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
        startService(intent);
        /*notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(this);
        builder.setContentText("Downloading in progress");
        builder.setContentTitle("Download");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setProgress(100, 0 , false);
        notificationManager.notify(1,builder.build());
*/

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause()
    {
        super.onPause();
        stopService(intent);
    }


/* @Override
    protected void onStop() {
        Intent intent = new Intent();
        intent.setAction("ACTION_POWER_CONNECTED");
        sendBroadcast(intent);

    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*
        Download_Data.builder.setContentText("Downloading paused");
        Download_Data.notificationManager.notify(Download_Data.id,Download_Data.builder.build());*/
        Toast.makeText(this, "on destroy called", Toast.LENGTH_LONG).show();
    }
}

     /* BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action)
            {
                case DOWNLOAD_IN_PROGRESS:
                    int progress = intent.getIntExtra("progress",0);
                    builder.setProgress(100,progress,false);
                    builder.setContentText("downloading:"+progress+"/100");
                    builder.setOngoing(true);
                    notificationManager.notify(1,builder.build());
                    break;
                case DOWNLOAD_COMPLETED:
                    builder.setProgress(0,0,false);
                    builder.setOngoing(false);
                    builder.setContentText("Downloaded");
                    notificationManager.notify(1,builder.build());
                    break;
            }
        }
    };
*/