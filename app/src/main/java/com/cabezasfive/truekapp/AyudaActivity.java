package com.cabezasfive.truekapp;

import android.net.Uri;
import android.os.Bundle;

import com.cabezasfive.truekapp.fragments.AyudaCategoriasFragment;
import com.cabezasfive.truekapp.fragments.AyudaDescripcionFragment;
import com.cabezasfive.truekapp.fragments.AyudaDestacadosFragment;
import com.cabezasfive.truekapp.fragments.AyudaFAQFragment;
import com.cabezasfive.truekapp.fragments.AyudaLoginRegisterFragment;
import com.cabezasfive.truekapp.fragments.AyudaMasVistosFragment;
import com.cabezasfive.truekapp.fragments.AyudaPublicarMisOfertasFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;
import android.app.FragmentManager;

import com.cabezasfive.truekapp.ui.main.SectionsPagerAdapter;

public class AyudaActivity extends AppCompatActivity implements AyudaDescripcionFragment.OnFragmentInteractionListener,
        AyudaLoginRegisterFragment.OnFragmentInteractionListener, AyudaPublicarMisOfertasFragment.OnFragmentInteractionListener,
        AyudaCategoriasFragment.OnFragmentInteractionListener, AyudaDestacadosFragment.OnFragmentInteractionListener,
        AyudaMasVistosFragment.OnFragmentInteractionListener, AyudaFAQFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}