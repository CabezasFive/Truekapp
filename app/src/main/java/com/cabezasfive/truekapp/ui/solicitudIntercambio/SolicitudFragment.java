package com.cabezasfive.truekapp.ui.solicitudIntercambio;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.adapters.AdapterListaPublicacionesIntercambio;
import com.cabezasfive.truekapp.models.Publicacion;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class SolicitudFragment extends Fragment {

    private ListView listView;
    private ArrayList<Publicacion> publicaciones;
    private AdapterListaPublicacionesIntercambio adapterPublicaciones;

    private Publicacion pubXIntercambio;
    private ImageView ivImagenPub;
    private TextView tvTituloPub;

    private Button btnVolver;

    SolicitudViewModel solicitudViewModel;

    private String userId;
    private FirebaseAuth mAuth;


    public static SolicitudFragment newInstance(String param1, String param2) {
        SolicitudFragment fragment = new SolicitudFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        solicitudViewModel = new ViewModelProvider(this).get(SolicitudViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_solicitud, container, false);

        Bundle getPublicacion = getArguments();
        if (getPublicacion != null){
            pubXIntercambio = (Publicacion) getPublicacion.getSerializable("publicacion");
        }

        /** Boton Volver al Listado */
        btnVolver = view.findViewById(R.id.btnVolverAListado);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });


        listView = view.findViewById(R.id.lvMisArticulosSolicitud);
        listView.setAdapter(adapterPublicaciones);

        ivImagenPub = view.findViewById(R.id.ivArticuloSolicitud);
        tvTituloPub = view.findViewById(R.id.tvTituloArtSolicitud);

        tvTituloPub.setText(pubXIntercambio.getTitulo());
        if (pubXIntercambio.getImagen01() != null){
            String url = pubXIntercambio.getImagen01();
            Picasso.get().load(url).into(ivImagenPub);
        }else {
            ivImagenPub.setImageResource(R.drawable.sin_imagen);
        }

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();


        TareaAsyncTask tareaAsyncTask = new TareaAsyncTask();
        tareaAsyncTask.execute();


        return view;
    }






    private class TareaAsyncTask extends AsyncTask<Void, Integer, ArrayList<Publicacion>> {

        @Override
        protected void onPreExecute(){

        }

        @Override
        protected ArrayList<Publicacion> doInBackground(Void... voids) {
            publicaciones = solicitudViewModel.getAllPublicacionesUser(userId);
            return publicaciones;
        }

        @Override
        protected void onProgressUpdate(Integer... values){

        }

        @Override
        protected void onPostExecute(ArrayList<Publicacion> resultado){
            adapterPublicaciones = new AdapterListaPublicacionesIntercambio(getContext(), resultado,getActivity().getApplication() );

            listView.setAdapter(adapterPublicaciones);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Publicacion publicacion = resultado.get(position);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("miPub", publicacion);
                    bundle.putSerializable("xPub", pubXIntercambio);
                    Navigation.findNavController(view).navigate(R.id.confirmarEnvioSoliditud,bundle);
                }
            });


        }
    }

}