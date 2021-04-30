package com.cabezasfive.truekapp.ui.publicar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.models.Publicacion;

public class PublicarFragment04 extends Fragment {


    private byte [] thumb_byte;

    public static PublicarFragment04 newInstance(String param1, String param2) {
        PublicarFragment04 fragment = new PublicarFragment04();

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
        View view = inflater.inflate(R.layout.fragment_publicar04, container, false);

        Bundle getPictByte = getArguments();
        if (getPictByte != null){
            thumb_byte = getPictByte.getByteArray("img");
            Log.e("L04", thumb_byte.toString());
        }


        return view;
    }
}