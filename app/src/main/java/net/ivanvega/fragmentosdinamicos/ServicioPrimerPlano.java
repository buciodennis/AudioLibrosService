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
        MediaPlayer.OnPreparedListener,
        MediaController.MediaPlayerControl{

    MediaPlayer mediaPlayer;

    private final IBinder binder= new MiBinder();


    public class MiBinder extends Binder{
        ServicioPrimerPlano getService(){
            return ServicioPrimerPlano.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification= new NotificationCompat.Builder(this, "canalServicio")
                .setContentTitle("Notificacion Servicio")
                .setContentText("Reproduciendo audio libro")
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void crearMediaPlayer( MediaPlayer.OnPreparedListener preparedListener, String url) {
        Log.d("Se creo mediaPlayer", url);

        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
        }
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        try {
            mediaPlayer.setOnPreparedListener(preparedListener);
            mediaPlayer.setDataSource(
                    String.valueOf(Uri.parse(url)));
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }

    @Override
    public void start() {
        mediaPlayer.start();
    }

    @Override
    public void pause() {
        mediaPlayer.pause();
    }

    @Override
    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    @Override
    public void seekTo(int i) {
        mediaPlayer.seekTo(i);
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }


}
