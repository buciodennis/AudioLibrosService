package net.ivanvega.fragmentosdinamicos;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Bitmap;
import android.os.Build;

import androidx.collection.LruCache;

import java.util.Vector;

public class Aplicacion extends Application{

    private Vector<Libro> vectorLibros;
    boolean novedad = false;
    boolean leido = false;

    @Override
    public void onCreate() {
        super.onCreate();
        vectorLibros = Libro.ejemplosLibros();

    }



    public void setNovedad(boolean novedad) {
        this.novedad = novedad;
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
    }

    public void recalcularFiltro() {
        Vector<Libro> libros = new Vector<>();
        Vector<Integer> indiceFiltro = new Vector<>();

        int size = Libro.ejemplosLibros().size();

        for(int i=0;i<size; i++){
            Libro l = Libro.ejemplosLibros().get(i);
            if(
                    ( !novedad || (!novedad || !l.getNovedad()) )
                            && ( !leido || (leido && l.getLeido()))
            ){
                libros.add(l);
                indiceFiltro.add(i);
            }
        }
        Libro.libros=libros;
    }






}
