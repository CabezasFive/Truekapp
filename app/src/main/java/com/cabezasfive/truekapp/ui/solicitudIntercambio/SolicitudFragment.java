package com.cabezasfive.truekapp.ui.solicitudIntercambio;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cabezasfive.truekapp.R;


public class SolicitudFragment extends Fragment {


    public SolicitudFragment() {
        // Required empty public constructor
    }


    public static SolicitudFragment newInstance(String param1, String param2) {
        SolicitudFragment fragment = new SolicitudFragment();

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
        View view = inflater.inflate(R.layout.fragment_solicitud, container, false);



        return view;
    }
}