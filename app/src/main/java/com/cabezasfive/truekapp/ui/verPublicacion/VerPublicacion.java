package com.cabezasfive.truekapp.ui.verPublicacion;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.models.Publicacion;
import com.squareup.picasso.Picasso;

public class VerPublicacion extends Fragment {


    private Button btnVolver;

    private TextView tvTitulo, tvDescripcion;
    private ImageView ivVerPublicacion;

    private ImageButton btnShare;

    private Publicacion publicacion;



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ver_publicacion, container, false);


        Bundle getPublicaicon = getArguments();
        if (getPublicaicon != null){
            publicacion = (Publicacion) getPublicaicon.getSerializable("publicacion");
        }

        btnVolver = view.findViewById(R.id.btnVolverVerPublicacion);

        tvTitulo = view.findViewById(R.id.tituloVerPublicacion);
        tvDescripcion = view.findViewById(R.id.descripcionVerPublicacion);
        ivVerPublicacion = view.findViewById(R.id.imagenVerPublicacion);
        btnShare = view.findViewById(R.id.shareVerPublicacion);


        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                String shareBody = "http://www.store-a-descarga-de-app-Tuekapp/";
                String shareSubject = "Mira esta publicación que encontré en Truekapp\n" + publicacion.getTitulo() + "\n Truekapp – Una aplicación donde podés intercambiar lo que desees sin gastar un peso";
                i.putExtra(Intent.EXTRA_SUBJECT,shareSubject);
                i.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(i,"Truekapp Link"));
                Toast.makeText(getContext(), "Share!!", Toast.LENGTH_SHORT).show();
            }
        });

        tvTitulo.setText(publicacion.getTitulo());
        tvDescripcion.setText(publicacion.getDescripcion());

        if (publicacion.getImagen01() != null){
            String url = publicacion.getImagen01();
            Picasso.get().load(url).into(ivVerPublicacion);
        }else {
            ivVerPublicacion.setImageResource(R.drawable.sin_imagen);
        }


        return view;
    }

}