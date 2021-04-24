package com.cabezasfive.truekapp.adapters;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.cabezasfive.truekapp.MainActivity;
import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.models.Publicacion;
import com.cabezasfive.truekapp.repositories.PublicacionRepository;
import com.cabezasfive.truekapp.ui.misOfertas.MisOfertasViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterMisPublicaciones extends BaseAdapter {

    private Context context;
    private ArrayList<Publicacion> publicaciones;
    private Application application;
    PublicacionRepository publicacionRepository;


    public AdapterMisPublicaciones(Application application, Context context, ArrayList<Publicacion> publicaciones) {
        this.context = context;
        this.publicaciones = publicaciones;
        this.application = application;
    }

    @Override
    public int getCount() {
        return publicaciones.size();
    }

    @Override
    public Object getItem(int i) {
        return publicaciones.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null){
            view = View.inflate(context, R.layout.mis_publicaciones_list_item, null);
        }


        publicacionRepository = new PublicacionRepository(application);

        Publicacion publicacion = publicaciones.get(i);

        String state = "true";
        String userId = publicacion.getIdUser();

        ImageView imagenV = view.findViewById(R.id.ivMisPublicacionesCard);
        TextView tvTitulo = view.findViewById(R.id.tituloMisPublicacionesCard);
        TextView tvDescr = view.findViewById(R.id.descripcionMisPublicaciones);
        TextView tvEstado = view.findViewById(R.id.tvEstadoMisPublicaciones);
        Button btnEstado = view.findViewById(R.id.btnEstadoMisPublicaciones);
        RelativeLayout rlPublicacion = view.findViewById(R.id.misPublicacionesItem);


        if (state.equals(publicacion.getActivo())){
            rlPublicacion.setBackgroundColor(Color.parseColor("#994C7D4C"));
            tvEstado.setText("Estado - ACTIVO");
            tvEstado.setTextColor(Color.parseColor("#FFFFFF"));
            btnEstado.setBackgroundColor(Color.parseColor("#DB3E3E"));
            btnEstado.setText("PAUSAR");
        }else{
            rlPublicacion.setBackgroundColor(Color.parseColor("#66E81443"));
            tvEstado.setText("Estado - PAUSADO");
            tvEstado.setTextColor(Color.parseColor("#FF000000"));
            btnEstado.setBackgroundColor(Color.parseColor("#54C74C"));
            btnEstado.setText("REANUDAR");
        }

        btnEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (state.equals(publicacion.getActivo())){
                    Toast.makeText(context, "Se Pausa " + publicacion.getTitulo(), Toast.LENGTH_SHORT).show();
                    publicacionRepository.cambiarEstado(publicacion.getUid(),userId, "false");
                    Navigation.findNavController(view).navigate(R.id.misOfertasFragment);
                }else{
                    Toast.makeText(context, "Se Reanuda + " + publicacion.getTitulo(), Toast.LENGTH_SHORT).show();
                    publicacionRepository.cambiarEstado(publicacion.getUid(),userId, "true");
                    Navigation.findNavController(view).navigate(R.id.misOfertasFragment);
                }
            }
        });

        tvTitulo.setText(publicacion.getTitulo());
        tvDescr.setText(publicacion.getDescripcion());
        if(publicaciones.get(i).getImagen01() != null){
            String url = publicaciones.get(i).getImagen01();
            Picasso.get()
                    .load(url)
                    .into(imagenV);
        }


        return view;
    }

}
