package com.cabezasfive.truekapp.fragmentsAyuda;

import android.net.Uri;
import android.os.Bundle;

import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.fragmentsAyuda.AyudaCategoriasFragment;
import com.cabezasfive.truekapp.fragmentsAyuda.AyudaDescripcionFragment;
import com.cabezasfive.truekapp.fragmentsAyuda.AyudaDestacadosFragment;
import com.cabezasfive.truekapp.fragmentsAyuda.AyudaFAQFragment;
import com.cabezasfive.truekapp.fragmentsAyuda.AyudaLoginRegisterFragment;
import com.cabezasfive.truekapp.fragmentsAyuda.AyudaMasVistosFragment;
import com.cabezasfive.truekapp.fragmentsAyuda.AyudaPublicarMisOfertasFragment;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Html;
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
            puntosSlide[i].setTextColor(getResources().getColor(R.color.blancotransparente));
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