package com.cabezasfive.truekapp.ui.misPublicaciones;

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
import com.cabezasfive.truekapp.adapters.AdapterMisPublicaciones;
import com.cabezasfive.truekapp.models.Publicacion;
import com.cabezasfive.truekapp.ui.listadoPublicaciones.ListadoPublicacionesFragment;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class MisPublicacionesFragment extends Fragment {

    private Button btnVolver;
    private ListView listView;
    private ArrayList<Publicacion> publicaciones;
    private AdapterMisPublicaciones adapterMisPublicaciones;

    MisPublicacionesViewModel misPublicacionesViewModel;

    private String userId;
    private FirebaseAuth mAuth;

    private Handler handler;



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

        btnVolver = view.findViewById(R.id.btnVolverDeMisPub);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });


        listView = view.findViewById(R.id.listviewMisPublicaciones);

        // *************************************
        // Obtener las publicaciones del usuario
        // *************************************

        listView.setAdapter(adapterMisPublicaciones);
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();



        handler = new Handler(){
            @Override
            public void handleMessage(Message message){
                Bundle bundle = message.getData();
                publicaciones = (ArrayList<Publicacion>) bundle.getSerializable("pub");

                adapterMisPublicaciones = new AdapterMisPublicaciones(getActivity().getApplication() ,getContext(), publicaciones);

                listView.setAdapter(adapterMisPublicaciones);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                        Toast.makeText(getContext(), "A editar Publicacion: " + publicaciones.get(position).getTitulo(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        Thread thread = new Thread(new HiloObtenerDatos());
        thread.start();


        return view;
    }



    class HiloObtenerDatos implements Runnable{
        @Override
        public void run(){
            Message message = new Message();
            Bundle bundle = new Bundle();

            ArrayList<Publicacion> pubs;

            pubs = misPublicacionesViewModel.getAllPublicacionesUser(userId);

            bundle.putSerializable("pub", pubs);
            message.setData(bundle);
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){

            }
            handler.sendMessage(message);
        }
    }

}