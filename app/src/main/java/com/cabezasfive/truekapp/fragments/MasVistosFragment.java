package com.cabezasfive.truekapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.RoomDatabase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cabezasfive.truekapp.MainActivity;
import com.cabezasfive.truekapp.PublicacionFactory;
import com.cabezasfive.truekapp.PublicacionListAdapter;
import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.entities.Publicacion;
import com.cabezasfive.truekapp.models.PublicacionViewModel;

import java.util.List;


public class MasVistosFragment extends Fragment {

    private RecyclerView rvPublicaciones;
    private RecyclerView.LayoutManager layoutManager;

    PublicacionViewModel publicacionViewModel;

    public MasVistosFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_mas_vistos, container, false);

        rvPublicaciones = view.findViewById(R.id.rv_MasVistos);
        layoutManager = new LinearLayoutManager(getActivity());
        rvPublicaciones.setLayoutManager(layoutManager);

        final PublicacionListAdapter adapter = new PublicacionListAdapter(new PublicacionListAdapter.PublicacionDiff());
        rvPublicaciones.setAdapter(adapter);
        rvPublicaciones.setLayoutManager(new LinearLayoutManager(getContext())); // o getActivity a corregir

        publicacionViewModel = new ViewModelProvider(this, new PublicacionFactory(getActivity().getApplication())).get(PublicacionViewModel.class);

        publicacionViewModel.getPublicaciones().observe(this, publicaciones -> {
            adapter.submitList(publicaciones);
        } );

        return view;
    }
}