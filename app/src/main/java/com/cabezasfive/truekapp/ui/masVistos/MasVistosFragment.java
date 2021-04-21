package com.cabezasfive.truekapp.ui.masVistos;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.adapters.AdapterListarPublicaciones;
import com.cabezasfive.truekapp.models.Publicacion;

import java.util.ArrayList;

public class MasVistosFragment extends Fragment {

    // Referencia al adaptador y recyclerView
    private AdapterListarPublicaciones adapter;
    private RecyclerView rvPublicaciones;
    private LinearLayoutManager layoutManager;

    private ArrayList<Publicacion> publicaciones;

    private MasVistosViewModel masVistosViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        masVistosViewModel = new ViewModelProvider(this).get(MasVistosViewModel.class);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_mas_vistos, container, false);


        rvPublicaciones = view.findViewById(R.id.rv_MasVistos);
        layoutManager = new LinearLayoutManager(getContext());
        rvPublicaciones.setLayoutManager(layoutManager);
        rvPublicaciones.setHasFixedSize(true);

        publicaciones = new ArrayList<>();

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
            publicaciones = masVistosViewModel.getAllPublicaciones();
            return publicaciones;
        }

        @Override
        protected void onProgressUpdate(Integer... values){

        }

        @Override
        protected void onPostExecute(ArrayList<Publicacion> resultado){
            adapter = new AdapterListarPublicaciones(publicaciones, R.layout.publicacion_item);

            adapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "Publicacion: " +
                            publicaciones.get(rvPublicaciones.getChildAdapterPosition(view)).getTitulo(), Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(getView()).navigate(R.id.verPublicacion);
                }
            });

            rvPublicaciones.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

}