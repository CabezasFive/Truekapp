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
import android.widget.EditText;
import android.widget.ImageButton;

import com.cabezasfive.truekapp.MainActivity;
import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.fragmentsAyuda.AyudaActivity;

import com.cabezasfive.truekapp.ui.login.LoginViewModel;
import com.cabezasfive.truekapp.repositories.UserAccountRepository;
import com.google.firebase.auth.FirebaseAuth;



public class HomeFragment extends Fragment {


    //Referencia a los cardViews que funcionaran como botones
    CardView cardListado, cardPublicar, cardDestacados, cardMisOfertas, cardMasVistos, cardAyuda;

    HomeViewModel homeViewModel;

    UserAccountRepository userRepository;
    private FirebaseAuth firebaseAuth;


    private ImageButton btnSearch;
    private EditText textSearch;
    private String search;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        userRepository = new UserAccountRepository(getActivity().getApplication());
        firebaseAuth = FirebaseAuth.getInstance();

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

        cardListado =view.findViewById(R.id.cardListado);
        cardAyuda=view.findViewById(R.id.cardAyuda);
        cardMasVistos=view.findViewById(R.id.cardMasVistos);
        cardDestacados=view.findViewById(R.id.cardDestacados);
        cardPublicar=view.findViewById(R.id.cardPublicar);
        cardMisOfertas=view.findViewById(R.id.cardMisOfertas);

        // Referencia al navController
        final NavController navController = Navigation.findNavController(view);


        // escucha si se da click en alguno de los cardView del menu

        boolean logueado = userRepository.isUserLoged();

        // se comunica con la interface IComunicacionFragment para ejecutar desde el mainActivity y no desde este fragemnt
        cardPublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLoged()){
                    navController.navigate(R.id.publicarFragment);
                }else{
                    navController.navigate(R.id.loginFragment);
                }

            }
        });

        cardListado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.listadoPublicacionesFragment);
            }
        });

        cardDestacados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //******************************** //
                //   Debe ir a un listado con las publicaciones marcadas como destacadas
                //********************************//
            }
        });

        cardMisOfertas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLoged()){
                    navController.navigate(R.id.misOfertasFragment);
                }else{
                    navController.navigate(R.id.loginFragment);
                }

            }
        });

        cardMasVistos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //******************************** //
                //   Debe ir a un listado con las publicaciones ordenadas por las mas visitadas
                //********************************//
            }
        });

        cardAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (getContext(), AyudaActivity.class);
                startActivityForResult(intent, 0);

            }
        });
    }

    private boolean isLoged(){
        if(firebaseAuth.getCurrentUser() != null){
            return true;
        } else {
            return false;
        }
    }

}

