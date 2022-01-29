package net.ivanvega.fragmentosdinamicos;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetalleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetalleFragment extends Fragment
    implements
        View.OnTouchListener,
        MediaPlayer.OnPreparedListener

{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static String ARG_INDEX_LIBRO = "idLibro";
    private TextView lblTitulo;
    private TextView lblAutor;
    private ImageView imvPortada;

    MediaPlayer mediaPlayer;
    MediaController mediaController;

    String libroUrl;

    public DetalleFragment() {
        // Required empty public constructor
    }

    ServicioPrimerPlano servicioP = null;
    private ServiceConnection mConnection = new ServiceConnection() {
        //@RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("Conexion", "Servicio conectado");
            servicioP = ((ServicioPrimerPlano.MiBinder)iBinder).getService();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ServicioPrimerPlano.MiBinder binder= (ServicioPrimerPlano.MiBinder) iBinder;
                servicioP=binder.getService();
                servicioP.crearMediaPlayer(DetalleFragment.this, libroUrl);


            }

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetalleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetalleFragment newInstance(String param1, String param2) {
        DetalleFragment fragment = new DetalleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout =
                inflater.inflate(R.layout.fragment_detalle_layout,
                        container, false);

        layout.setOnTouchListener(this);

        Spinner spinner =
                layout.findViewById(R.id.spnGeneros);

        String[] generos
                =  getResources().getStringArray(R.array.generos);

        ArrayAdapter<String> adapter =
                new ArrayAdapter(getActivity(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1, generos
                        );

        spinner.setAdapter(adapter);

          Bundle args = getArguments();

          if(args != null){
               int idLibro =
                       args.getInt(DetalleFragment.ARG_INDEX_LIBRO);
               setInfoLibro(idLibro,layout );
          }else{
              setInfoLibro(0, layout);
          }


        Intent intent = new Intent(getContext(), ServicioPrimerPlano.class);
        getActivity().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

        return layout;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setInfoLibro(int idLibro, View layout) {
        Libro libro = Libro.ejemplosLibros().elementAt(idLibro);

        lblTitulo = layout.findViewById(R.id.titulo);
        lblAutor = layout.findViewById(R.id.autor);
        imvPortada = layout.findViewById(R.id.portada);

        lblTitulo.setText(libro.getTitulo());
        lblAutor.setText(libro.getAutor());
        imvPortada.setImageResource(libro.getRecursoImagen());
        libroUrl = libro.getUrl();




    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setInfoLibro(int pos) {

        this.setInfoLibro(pos,getView()    );
    }

    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.d("MediaController", "Se creo");
        mediaController.setMediaPlayer((MediaController.MediaPlayerControl) servicioP);
        mediaController.setAnchorView(
                getView().findViewById(R.id.fragment_detalle_layout_root));
        mediaController.setEnabled(true);
        mediaController.show();
        mediaPlayer.start();

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onStop() {
        super.onStop();
    }


}