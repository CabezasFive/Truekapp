package com.cabezasfive.truekapp.ui.registroUsuario;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cabezasfive.truekapp.R;

public class RegistroFragment02 extends Fragment {

    public static RegistroFragment02 newInstance(String param1, String param2) {
        RegistroFragment02 fragment = new RegistroFragment02();

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
        View view = inflater.inflate(R.layout.fragment_registro02, container, false);



        return view;
    }
}