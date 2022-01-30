package net.ivanvega.fragmentosdinamicos;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

public class App extends Aplicacion{

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();

    }



    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel serviceChannel = new NotificationChannel(
                    "10",
                    "Canal Servicio",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
        Log.d("Canal:", "Se creo canal");




    }
}
