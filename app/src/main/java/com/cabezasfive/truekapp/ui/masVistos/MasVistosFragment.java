package com.cabezasfive.truekapp.ui.masVistos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.adapters.AdapterListarPublicaciones;
import com.cabezasfive.truekapp.models.Publicacion;
import com.cabezasfive.truekapp.ui.registroUsuario.RegistroViewModel;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MasVistosFragment extends Fragment {

    // Referencia al recyclerView
    private RecyclerView rvPublicaciones;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<Publicacion> publicaciones = new ArrayList<>();

    private MAsVistosViewModel mAsVistosViewModel;

    private AdapterListarPublicaciones adapter;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_mas_vistos, container, false);

        mAsVistosViewModel = new ViewModelProvider(this).get(MAsVistosViewModel.class);

        rvPublicaciones = view.findViewById(R.id.rv_MasVistos);
        layoutManager = new LinearLayoutManager(getActivity());
        rvPublicaciones.setLayoutManager(layoutManager);

    //    obtenerPublicaciones
        publicaciones = mAsVistosViewModel.getAllPublicaciones();

        adapter = new AdapterListarPublicaciones(publicaciones, R.layout.publicacion_item);
        rvPublicaciones.setAdapter(adapter);

        return view;
    }


/*
    private void obtenerPublicacionesFirebase() {
        databaseReference.child("Publicacion").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Valido que el nodo "Publicaciones" existe en la bd
                if(snapshot.exists()){
                    // limpia la lista por datos anteriores
                    publicaciones.clear();

                    // recorrer cada uno de los objetos en el nodo
                    for(DataSnapshot ds: snapshot.getChildren()){

                        Publicacion p = ds.getValue(Publicacion.class);

                        publicaciones.add(p);
                    }

                    adapter = new AdapterListarPublicaciones(publicaciones, R.layout.publicacion_item);

                    // Evento de escucha si se da un click sobre el item
                    adapter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            // Datos a pasar al Fragment editar
                            Bundle bundle = new Bundle();

                            String titulo = publicaciones.get(rvPublicaciones.getChildAdapterPosition(view)).getTitulo();
                            String descr = publicaciones.get(rvPublicaciones.getChildAdapterPosition(view)).getDescripcion();
                            String uid = publicaciones.get(rvPublicaciones.getChildAdapterPosition(view)).getUid();
                            bundle.putString("titulo", titulo );
                            bundle.putString("descripcion", descr );
                            bundle.putString("uid", uid );

                            EditPublicacionFragment editPublicacionFragment = new EditPublicacionFragment();
                            editPublicacionFragment.setArguments(bundle);
                            FragmentManager fm = getActivity().getSupportFragmentManager();
                            fm.beginTransaction().replace(R.id.contenido, editPublicacionFragment)
                                    .addToBackStack(null)
                                    .commit();
                        }
                    }); // Fin evento clickListener

                    rvPublicaciones.setAdapter(adapter);
                }else{
                    Toast.makeText(getActivity(), "no encontro nada", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
*/

}