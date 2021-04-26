package com.cabezasfive.truekapp.ui.confirmarEnvioSolicitud;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.models.Publicacion;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class ConfirmarEnvioSoliditud extends Fragment {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private Publicacion pubSeleccionada, pubIntercambio;

    private String idUserPub, idInterPub;

    public ConfirmarEnvioSoliditud() {
        // Required empty public constructor
    }


    public static ConfirmarEnvioSoliditud newInstance(String param1, String param2) {
        ConfirmarEnvioSoliditud fragment = new ConfirmarEnvioSoliditud();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirmar_envio_soliditud, container, false);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        if (getArguments() != null) {
            // Publicacion que el usuario selecciono para intercambiar
            pubSeleccionada = (Publicacion) getArguments().getSerializable("miPub");
            // Publicacion por la cual se envia la solicitud
            pubIntercambio = (Publicacion) getArguments().getSerializable("xPub");

            idUserPub = pubSeleccionada.getUid();
            idInterPub = pubIntercambio.getUid();
        }

        ImageView ivMiPub, ivXPub;
        TextView tvMiPub, tvXPub;
        Button btnVolver, btnConfirmar;

        /** Declaracion de las Vistas */
        ivMiPub = view.findViewById(R.id.ivMiPubIntercambio);
        ivXPub = view.findViewById(R.id.ivPubXIntercambio);
        tvMiPub = view.findViewById(R.id.tvMiPubIntercambio);
        tvXPub = view.findViewById(R.id.tvPubXIntercambio);

        /** Se asigna los valores a las vistas */
        tvMiPub.setText(pubSeleccionada.getTitulo());
        tvXPub.setText(pubIntercambio.getTitulo());

        if (pubSeleccionada.getImagen01() != null){
            String url = pubSeleccionada.getImagen01();
            Picasso.get().load(url).into(ivMiPub);
        }else {
            ivMiPub.setImageResource(R.drawable.sin_imagen);
        }

        if (pubIntercambio.getImagen01() != null){
            String url = pubIntercambio.getImagen01();
            Picasso.get().load(url).into(ivXPub);
        }else {
            ivXPub.setImageResource(R.drawable.sin_imagen);
        }


        /** Boton Volver - Regresa a la seleccion de publicacion para intercambio */
        btnVolver = view.findViewById(R.id.btnVolverSolicitud);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        btnConfirmar = view.findViewById(R.id.btnConfirmacionSolicitud);
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Se guarda en la BD un intercambio pendiente con los id de las publicaciones y la publicacion solicitante */
                DatabaseReference reference= databaseReference.child("users").child(pubIntercambio.getIdUser())
                                                .child("int_pendiente").child(idInterPub);
                reference.child(idUserPub).setValue(pubSeleccionada);

                DatabaseReference userOwner = databaseReference.child("users").child(pubIntercambio.getIdUser())
                                                .child("publicaciones").child(pubIntercambio.getUid());
                Integer intPend = Integer.parseInt(pubIntercambio.getInt_pendiente());
                intPend = intPend + 1;
                userOwner.child("int_pendiente").setValue(intPend.toString());

                DatabaseReference userPub = databaseReference.child("Publicacion").child(pubIntercambio.getUid());
                userPub.child("int_pendiente").setValue(intPend.toString());

                // Confirmacion de solicitud enviada
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Envio de solicitud");
                builder.setMessage("Se ha enviado una solicitud de intercambio al dueño de la publicacion \nTe avisaremos si el usuario lo acepta!!")
                        .setPositiveButton("Entendido - llevame al listado", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Navigation.findNavController(view).navigate(R.id.listadoPublicacionesFragment);
                            }
                        })
                        .setCancelable(false)
                        .show();
            }
        });

        return view;
    }
}