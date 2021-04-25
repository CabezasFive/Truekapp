package com.cabezasfive.truekapp.ui.misPublicaciones;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.adapters.AdapterMisPublicaciones;
import com.cabezasfive.truekapp.models.Publicacion;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class MisPublicacionesFragment extends Fragment {

    private ListView listView;
    private ArrayList<Publicacion> publicaciones;
    private AdapterMisPublicaciones adapterMisPublicaciones;

    MisPublicacionesViewModel misPublicacionesViewModel;

    String userId;
    private FirebaseAuth mAuth;



    public MisPublicacionesFragment() {
        // Required empty public constructor
    }


    public static MisPublicacionesFragment newInstance(String param1, String param2) {
        MisPublicacionesFragment fragment = new MisPublicacionesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        misPublicacionesViewModel = new ViewModelProvider(this).get(MisPublicacionesViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_mis_publicaciones, container, false);

        listView = view.findViewById(R.id.listviewMisPublicaciones);

        // *************************************
        // Obtener las publicaciones del usuario
        // *************************************
        //publicaciones = misOfertasViewModel.getAllPublicaciones();

        listView.setAdapter(adapterMisPublicaciones);
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
            publicaciones = misPublicacionesViewModel.getAllPublicacionesUser(userId);
            return publicaciones;
        }

        @Override
        protected void onProgressUpdate(Integer... values){

        }

        @Override
        protected void onPostExecute(ArrayList<Publicacion> resultado){
            adapterMisPublicaciones = new AdapterMisPublicaciones(getActivity().getApplication() ,getContext(), resultado);

            listView.setAdapter(adapterMisPublicaciones);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Publicacion publicacion = resultado.get(position);

                    Toast.makeText(getContext(), "A editar Publicacion: " + publicacion.getTitulo(), Toast.LENGTH_SHORT).show();
                }
            });


        }
    }

}