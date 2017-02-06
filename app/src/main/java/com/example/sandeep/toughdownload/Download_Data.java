package com.example.sandeep.toughdownload;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by sandeep on 02-02-2017.
 */

public class Download_Data extends Service {

    private static final String TAG = "Download_Data";
    public static NotificationManager notificationManager;
    public static NotificationCompat.Builder builder;
    public static int id = 1;
    String path = "";
    /*  NotificationManager notificationManager;
      NotificationCompat.Builder builder;*/
    File file_name;
    File file_output;
    InputStream inputStream;
    FileOutputStream fos;
    // public static final String url_link="";
    //
    //
    Context context;

//    @Override
//    public void onCreate() {
//        super.onCreate();
//        Intent notificationIntent = new Intent(this, MainActivity.class);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
//                notificationIntent, 0);
//
//        Notification notification = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle("My Awesome App")
//                .setContentText("Doing some work...")
//                .setContentIntent(pendingIntent).build();
//
//        startForeground(1337, notification);
//    }

    @Override
    public int onStartCommand(final Intent intent, final int flags, int startId) {
//        Log.d("tough", "onStartCommand: ");
//        //String url_link = intent.getStringExtra("url");
       context = this;
//        BackgroundWorker backgroundWorker = new BackgroundWorker();
//        backgroundWorker.execute();
//
//
//        return START_NOT_STICKY;


        if (intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)) {
            Log.i(TAG, "Received Start Foreground Intent ");
            Intent notificationIntent = new Intent(this, MainActivity.class);
            notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                    notificationIntent, 0);


            Bitmap icon = BitmapFactory.decodeResource(getResources(),
                    R.mipmap.ic_launcher);

            Notification notification = new NotificationCompat.Builder(this)
                    .setContentTitle("Truiton Music Player")
                    .setTicker("Truiton Music Player")
                    .setContentText("My Music")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(
                            Bitmap.createScaledBitmap(icon, 128, 128, false))
                    .setContentIntent(pendingIntent)
                    .setOngoing(true)
                    .build();
            startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE,
                    notification);
            BackgroundWorker backgroundWorker = new BackgroundWorker();
        backgroundWorker.execute();

        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    class BackgroundWorker extends AsyncTask<Void, Integer, Integer> {

        // Downloading_Pause downloading_pause ;
    /*NotificationManager notificationManager;
    NotificationCompat.Builder builder;*/
        //String url_link = "https://www.planwallpaper.com/static/images/HD-Wallpaer.jpg";
        String url_link = "http://smp3dl.com/fileDownload/Songs/0/29142.mp3";


        //Context context;
            /*BackgroundWorker(Context context)
            {
                this.context = context;
            }*/
        @Override
        protected void onPreExecute() {
            notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            builder = new NotificationCompat.Builder(context);
            builder.setContentText("Downloading");
            builder.setContentTitle("Download Something");
            builder.setProgress(100, 0, false);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            notificationManager.notify(id, builder.build());
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Void... params) {
            //String url_link = intent.getStringExtra("url");
            URL url = null;
            File file;
            File output_file;
            InputStream inputStream = null;
            FileOutputStream fos = null;
            try {
                url = new URL(url_link);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                file = new File(url_link);
                String name = file.getName();
                output_file = new File(Environment.getExternalStorageDirectory(), name);
                fos = new FileOutputStream(output_file.getPath());
                inputStream = httpURLConnection.getInputStream();
                path = output_file.getAbsolutePath();
                // output_file = new File(file.getPath());
                //fos = new FileOutputStream(Environment.getExternalStorageDirectory(),output_file);
                int count = 0;
                int total = 0;
                int filelength = httpURLConnection.getContentLength();
                byte bytes[] = new byte[filelength];
                while ((count = inputStream.read(bytes, 0, filelength)) != -1) {
                    total += count;
                    publishProgress((total * 100) / filelength);

                    fos.write(bytes, 0, count);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            //super.onProgressUpdate(values);
            int i = values[0];
            builder.setProgress(100, values[0], false);
            builder.setContentText("Downloading:" + i + "/100");
            // builder.setOngoing(true);
            notificationManager.notify(id, builder.build());
            //  downloading_pause.whenStop(id);
        }

        @Override
        protected void onPostExecute(Integer aVoid) {
            //super.onPostExecute(aVoid);

            builder.setProgress(0, 0, false);
            builder.setOngoing(false);
            builder.setContentText("Downloaded");
            notificationManager.notify(id, builder.build());
            MediaScannerConnection.scanFile(context, new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                @Override
                public void onScanCompleted(String path, Uri uri) {

                }
            });
            // MediaScannerConnection.scanFile();

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("ToughDownload" , "onDestroy: ");
    }


    /*BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equalsIgnoreCase("ACTION_POWER_CONNECTED"))
            {
                builder.setContentText("pause");
                notificationManager.notify(1,builder.build());
            }
        }
    };*/

}