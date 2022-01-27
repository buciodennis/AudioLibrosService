package net.ivanvega.fragmentosdinamicos;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.MediaController;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.io.IOException;

public class ServicioPrimerPlano extends Service implements
        MediaPlayer.OnPreparedListener{

    MediaPlayer mediaPlayer;

    private final IBinder binder= new MiBinder();

    public class MiBinder extends Binder{
        ServicioPrimerPlano getService(){
            return ServicioPrimerPlano.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("ServicioPrimerPlano", intent.getStringExtra("url"));

        mediaPlayer = new MediaPlayer();
       // mediaController = new MediaController(getActivity());
        mediaPlayer.setOnPreparedListener(this);
        try {
            mediaPlayer.setDataSource(
                    String.valueOf(Uri.parse(intent.getStringExtra("url"))));
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification= new NotificationCompat.Builder(this, "canalServicio")
                .setContentTitle("Notificacion Servicio")
                .setContentText("Notificacion")
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);



        return START_STICKY;
    }
    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {

        mediaPlayer.start();


    }


}
