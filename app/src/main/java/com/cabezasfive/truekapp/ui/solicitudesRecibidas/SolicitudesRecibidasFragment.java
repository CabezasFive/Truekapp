package com.cabezasfive.truekapp.ui.solicitudesRecibidas;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.adapters.AdapterListaPublicacionesIntercambio;
import com.cabezasfive.truekapp.adapters.AdapterListarPublicaciones;
import com.cabezasfive.truekapp.adapters.AdapterMisPublicaciones;
import com.cabezasfive.truekapp.adapters.AdapterPublicacionesUser;
import com.cabezasfive.truekapp.models.Publicacion;
import com.cabezasfive.truekapp.ui.listadoPublicaciones.ListadoPublicacionesFragment;
import com.cabezasfive.truekapp.ui.misPublicaciones.MisPublicacionesFragment;
import com.cabezasfive.truekapp.ui.misPublicaciones.MisPublicacionesViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class SolicitudesRecibidasFragment extends Fragment {

    private ListView listView;
    private ArrayList<Publicacion> publicaciones;

    private AdapterPublicacionesUser adapter;
    private SolicitudesRecibidasViewModel viewModel;

    private Button btnVolver;

    private String userId;
    private FirebaseAuth mAuth;

    private Handler handler;

    public static SolicitudesRecibidasFragment newInstance(String param1, String param2) {
        SolicitudesRecibidasFragment fragment = new SolicitudesRecibidasFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SolicitudesRecibidasViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_solicitudes_recibidas, container, false);

        listView = view.findViewById(R.id.listviewSolicitudesRec);

        btnVolver = view.findViewById(R.id.btnVolverDeSolicitudesRec);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();


//        TareaAsyncTask tareaAsyncTask = new TareaAsyncTask();
//        tareaAsyncTask.execute();


        handler = new Handler(){
            @Override
            public void handleMessage(Message message){
                Bundle bundle= message.getData();
                publicaciones = (ArrayList<Publicacion>) bundle.getSerializable("pub");

                adapter = new AdapterPublicacionesUser(getContext(), publicaciones,getActivity().getApplication());

                listView.setAdapter(adapter);


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

            pubs = viewModel.getAllPublicacionesUser(userId);

            bundle.putSerializable("pub", pubs);
            message.setData(bundle);
            try {
                Thread.sleep(1500);
            }catch (InterruptedException e){

            }
            handler.sendMessage(message);
        }
    }



//    private class TareaAsyncTask extends AsyncTask<Void, Integer, ArrayList<Publicacion>> {
//
//        @Override
//        protected void onPreExecute(){
//
//        }
//
//        @Override
//        protected ArrayList<Publicacion> doInBackground(Void... voids) {
//            publicaciones = viewModel.getAllPublicacionesUser(userId);
//            return publicaciones;
//        }
//
//        @Override
//        protected void onProgressUpdate(Integer... values){
//
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<Publicacion> resultado){
//            adapter = new AdapterPublicacionesUser(getContext(), resultado,getActivity().getApplication());
//
//            listView.setAdapter(adapter);
//
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                    Publicacion publicacion = resultado.get(position);
//
//                    Toast.makeText(getContext(), "Ver solicitudes de: " + publicacion.getTitulo(), Toast.LENGTH_SHORT).show();
//                }
//            });
//
//
//        }
//    }
}