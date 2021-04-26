package com.cabezasfive.truekapp.ui.listadoPublicaciones;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.adapters.AdapterListarPublicaciones;
import com.cabezasfive.truekapp.models.Publicacion;

import java.io.Serializable;
import java.util.ArrayList;

public class ListadoPublicacionesFragment extends Fragment {

    // Referencia al adaptador y recyclerView
    private AdapterListarPublicaciones adapter;
    private RecyclerView rvPublicaciones;
    private LinearLayoutManager layoutManager;

    private ArrayList<Publicacion> publicaciones;

    private ListadoPublicacionesViewModel listadoPublicacionesViewModel;

    private String findText;

    private Handler handler;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listadoPublicacionesViewModel = new ViewModelProvider(this).get(ListadoPublicacionesViewModel.class);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null){
            findText = getArguments().getString("text");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_listado_publicaciones, container, false);

        rvPublicaciones = view.findViewById(R.id.rv_MasVistos);
        layoutManager = new LinearLayoutManager(getContext());
        rvPublicaciones.setLayoutManager(layoutManager);
        rvPublicaciones.setHasFixedSize(true);

        publicaciones = new ArrayList<>();

//        TareaAsyncTask tareaAsyncTask = new TareaAsyncTask();
//        tareaAsyncTask.execute();

        handler = new Handler(){
            @Override
            public void handleMessage(Message message){
                Bundle bundle= message.getData();
                publicaciones = (ArrayList<Publicacion>) bundle.getSerializable("pub");

                adapter = new AdapterListarPublicaciones(publicaciones, R.layout.publicacion_item, getContext());

                adapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // ************************************************************** //
                        //   Pasa a verPublicacion el uid de la publicacion seleccionada //

                        Publicacion pub = publicaciones.get(rvPublicaciones.getChildAdapterPosition(view));

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("publicacion", pub );
                        //bundle.putString("uid", uidPublicacion);
                        Navigation.findNavController(getView()).navigate(R.id.verPublicacion, bundle);
                    }
                });
                rvPublicaciones.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        };

        Thread thread = new Thread(new HiloPedidoDatos());
        thread.start();


        return view;
    }


    class HiloPedidoDatos implements Runnable{
        @Override
        public void run(){
            Message message = new Message();
            Bundle bundle = new Bundle();

            ArrayList<Publicacion> pubs;

            if (findText != null){
                pubs = listadoPublicacionesViewModel.searchPublicacion(findText);
            }else {
                pubs = listadoPublicacionesViewModel.getAllPublicaciones();
            }

            bundle.putSerializable("pub", pubs);
            message.setData(bundle);
            try {
                Thread.sleep(1500);
            }catch (InterruptedException e){

            }
            handler.sendMessage(message);
        }
    }
//
//    private class TareaAsyncTask extends AsyncTask<Void, Integer, ArrayList<Publicacion>> {
//
//        @Override
//        protected void onPreExecute(){
//
//        }
//
//        @Override
//        protected ArrayList<Publicacion> doInBackground(Void... voids) {
//            if (findText != null){
//                publicaciones = listadoPublicacionesViewModel.searchPublicacion(findText);
//                return publicaciones;
//            }else {
//                publicaciones = listadoPublicacionesViewModel.getAllPublicaciones();
//                return publicaciones;
//            }
//
//        }
//
//        @Override
//        protected void onProgressUpdate(Integer... values){
//
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<Publicacion> resultado){
//            adapter = new AdapterListarPublicaciones(publicaciones, R.layout.publicacion_item, getContext());
//
//            adapter.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    // ************************************************************** //
//                    //   Pasa a verPublicacion el uid de la publicacion seleccionada //
//
//                    Publicacion pub = publicaciones.get(rvPublicaciones.getChildAdapterPosition(view));
//
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("publicacion", pub );
//                    //bundle.putString("uid", uidPublicacion);
//                    Navigation.findNavController(getView()).navigate(R.id.verPublicacion, bundle);
//                }
//            });
//            rvPublicaciones.setAdapter(adapter);
//            adapter.notifyDataSetChanged();
//        }
//    }

}