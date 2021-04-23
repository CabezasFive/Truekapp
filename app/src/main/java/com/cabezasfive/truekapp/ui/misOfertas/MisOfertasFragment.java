package com.cabezasfive.truekapp.ui.misOfertas;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.adapters.AdapterListarPublicaciones;
import com.cabezasfive.truekapp.adapters.AdapterMisPublicaciones;
import com.cabezasfive.truekapp.models.Publicacion;
import com.cabezasfive.truekapp.ui.listadoPublicaciones.ListadoPublicacionesFragment;

import java.util.ArrayList;


public class MisOfertasFragment extends Fragment {

    private ListView listView;
    private ArrayList<Publicacion> publicaciones;
    private AdapterMisPublicaciones adapterMisPublicaciones;

    MisOfertasViewModel misOfertasViewModel;


    public MisOfertasFragment() {
        // Required empty public constructor
    }


    public static MisOfertasFragment newInstance(String param1, String param2) {
        MisOfertasFragment fragment = new MisOfertasFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        misOfertasViewModel = new ViewModelProvider(this).get(MisOfertasViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_mis_ofertas, container, false);

        listView = view.findViewById(R.id.listviewMisPublicaciones);

        // *************************************
        // Obtener las publicaciones del usuario
        // *************************************
        publicaciones = misOfertasViewModel.getAllPublicaciones();

//        adapterMisPublicaciones = new AdapterMisPublicaciones(getContext(), publicaciones);
        listView.setAdapter(adapterMisPublicaciones);


        TareaAsyncTask tareaAsyncTask = new TareaAsyncTask();
        tareaAsyncTask.execute();

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                Publicacion publicacion = publicaciones.get(position);
//
//                Toast.makeText(getContext(), "Selecciono: " + publicacion.getTitulo(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        InputMethodManager imm =(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        return view;
    }



    private class TareaAsyncTask extends AsyncTask<Void, Integer, ArrayList<Publicacion>> {

        @Override
        protected void onPreExecute(){

        }

        @Override
        protected ArrayList<Publicacion> doInBackground(Void... voids) {
            publicaciones = misOfertasViewModel.getAllPublicaciones();
            return publicaciones;
        }

        @Override
        protected void onProgressUpdate(Integer... values){

        }

        @Override
        protected void onPostExecute(ArrayList<Publicacion> resultado){
            adapterMisPublicaciones = new AdapterMisPublicaciones(getContext(), publicaciones);

            listView.setAdapter(adapterMisPublicaciones);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Publicacion publicacion = publicaciones.get(position);

                    Toast.makeText(getContext(), "Selecciono: " + publicacion.getTitulo(), Toast.LENGTH_SHORT).show();
                }
            });


        }
    }

}