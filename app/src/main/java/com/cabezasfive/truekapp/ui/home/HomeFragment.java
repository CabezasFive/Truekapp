package com.cabezasfive.truekapp.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cabezasfive.truekapp.MainActivity;
import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.fragmentsAyuda.AyudaActivity;
import com.cabezasfive.truekapp.ui.login.LoginViewModel;


public class HomeFragment extends Fragment {


    //Referencia a los cardViews que funcionaran como botones
    CardView cardCategorias, cardPublicar, cardDestacados, cardMisOfertas, cardMasVistos, cardAyuda;

    HomeViewModel homeViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        return view;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cardCategorias=view.findViewById(R.id.cardCategorias);
        cardAyuda=view.findViewById(R.id.cardAyuda);
        cardMasVistos=view.findViewById(R.id.cardMasVistos);
        cardDestacados=view.findViewById(R.id.cardDestacados);
        cardPublicar=view.findViewById(R.id.cardPublicar);
        cardMisOfertas=view.findViewById(R.id.cardMisOfertas);

        // Referencia al navController
        final NavController navController = Navigation.findNavController(view);

        // escucha si se da click en alguno de los cardView del menu
        // se comunica con la interface IComunicacionFragment para ejecutar desde el mainActivity y no desde este fragemnt
        cardPublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.publicarFragment);
            }
        });

        cardCategorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.categoriasFragment);
            }
        });

        cardDestacados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //******************************** //
                //   Pasa a Login de usuario solo a modo de pruba
                //********************************//
                navController.navigate(R.id.loginFragment);
            }
        });

        cardMisOfertas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //******************************** //
                //   Pasa a Registro de usuario solo a modo de pruba
                //********************************//
                navController.navigate(R.id.registroFragment);
            }
        });

        cardMasVistos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.masVistosFragment);
            }
        });

        cardAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

}

