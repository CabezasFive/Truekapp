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

import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;
import android.app.FragmentManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cabezasfive.truekapp.ui.main.SectionsPagerAdapter;

public class AyudaActivity extends AppCompatActivity implements AyudaDescripcionFragment.OnFragmentInteractionListener,
        AyudaLoginRegisterFragment.OnFragmentInteractionListener, AyudaPublicarMisOfertasFragment.OnFragmentInteractionListener,
        AyudaCategoriasFragment.OnFragmentInteractionListener, AyudaDestacadosFragment.OnFragmentInteractionListener,
        AyudaMasVistosFragment.OnFragmentInteractionListener, AyudaFAQFragment.OnFragmentInteractionListener {

    ViewPager viewPager;
    private LinearLayout linearPuntos;
    private TextView[] puntosSlide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        linearPuntos = findViewById(R.id.idLinearPuntos);
        agregaIndicadorPuntos(0);

        viewPager.addOnPageChangeListener(viewListener);
    }

    private void agregaIndicadorPuntos(int pos) {
        puntosSlide =new TextView[7];
        linearPuntos.removeAllViews();

        for (int i=0; i< puntosSlide.length; i++){
            puntosSlide[i]=new TextView(this);
            puntosSlide[i].setText(Html.fromHtml("&#8226;"));
            puntosSlide[i].setTextSize(35);
            puntosSlide[i].setTextColor(getResources().getColor(R.color.colorBlancoTransparente));
            linearPuntos.addView(puntosSlide[i]);
        }

        if(puntosSlide.length>0){
            puntosSlide[pos].setTextColor(getResources().getColor(R.color.black));
        }

    }

    ViewPager.OnPageChangeListener viewListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int pos) {
            agregaIndicadorPuntos(pos);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

}