package com.cabezasfive.truekapp.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.fragments.AyudaCategoriasFragment;
import com.cabezasfive.truekapp.fragments.AyudaDescripcionFragment;
import com.cabezasfive.truekapp.fragments.AyudaDestacadosFragment;
import com.cabezasfive.truekapp.fragments.AyudaFAQFragment;
import com.cabezasfive.truekapp.fragments.AyudaLoginRegisterFragment;
import com.cabezasfive.truekapp.fragments.AyudaMasVistosFragment;
import com.cabezasfive.truekapp.fragments.AyudaPublicarMisOfertasFragment;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

    public static Fragment newInstance(int index) {
        // Devuelve fragments
        Fragment fragment = null;

        switch (index){
            case 1:fragment=new AyudaDescripcionFragment(); break;
            case 2:fragment=new AyudaLoginRegisterFragment(); break;
            case 3:fragment=new AyudaPublicarMisOfertasFragment(); break;
            case 4:fragment=new AyudaCategoriasFragment(); break;
            case 5:fragment=new AyudaDestacadosFragment(); break;
            case 6:fragment=new AyudaMasVistosFragment(); break;
            case 7:fragment=new AyudaFAQFragment(); break;
        }

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ayuda, container, false);
        final TextView textView = root.findViewById(R.id.section_label);
        pageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}