package com.cabezasfive.truekapp.ui.verSolicitudesDeIntercambios;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.adapters.AdapterPubXInt;
import com.cabezasfive.truekapp.models.Publicacion;
import com.cabezasfive.truekapp.ui.solicitudIntercambio.SolicitudViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VerSolicitudesDeIntercambioFragment extends Fragment {

    private Button btnVolver;
    private Publicacion mPublicacion;
    private ArrayList<Publicacion> publicaciones;

    private TextView tvTitulo;
    private ImageView ivMipub;
    private ListView listView;

    private String userId;
    private FirebaseAuth mAuth;

    VerSolicitudesViewModel solicitudesViewModel;
    private AdapterPubXInt adapterPub;
    private Handler handler;
    private String pubId;

    public static VerSolicitudesDeIntercambioFragment newInstance(String param1, String param2) {
        VerSolicitudesDeIntercambioFragment fragment = new VerSolicitudesDeIntercambioFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        solicitudesViewModel = new ViewModelProvider(this).get(VerSolicitudesViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ver_solicitudes_de_intercambio, container, false);

        Bundle getPublicacion = getArguments();
        if (getPublicacion != null){
            mPublicacion = (Publicacion) getPublicacion.getSerializable("publicacion");
        }


        btnVolver = view.findViewById(R.id.btnVolverDeVerSolicitudesRec);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        tvTitulo = view.findViewById(R.id.tvTituloMiPubxInt);
        ivMipub = view.findViewById(R.id.ivMiPubXInt);

        tvTitulo.setText("Tu Publicacion\n" + mPublicacion.getTitulo());
        if(mPublicacion.getImagen01() != null){
            String url = mPublicacion.getImagen01();
            Picasso.get()
                    .load(url)
                    .into(ivMipub);
        }

        /** llenar el listView con las publicaciones que pidieron Int */
        listView = view.findViewById(R.id.lvPubParaInt);
        listView.setAdapter(adapterPub);

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        pubId = mPublicacion.getUid();
//        Toast.makeText(getContext(), "Id Pub" + mPublicacion.getUid(), Toast.LENGTH_SHORT).show();


        handler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                Bundle bundle = message.getData();
                publicaciones = (ArrayList<Publicacion>) bundle.getSerializable("pub");

                adapterPub = new AdapterPubXInt(getContext(), publicaciones, getActivity().getApplication(), mPublicacion);

                listView.setAdapter(adapterPub);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        Toast.makeText(getContext(), "Pub" + publicaciones.get(i).getTitulo(), Toast.LENGTH_SHORT).show();
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

            pubs = solicitudesViewModel.getPublicacionesInter(userId, pubId);

            bundle.putSerializable("pub", pubs);
            message.setData(bundle);
            try {
                Thread.sleep(1050);
            }catch (InterruptedException e){

            }
            handler.sendMessage(message);
        }
    }
}