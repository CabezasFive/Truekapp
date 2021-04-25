package com.cabezasfive.truekapp.adapters;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
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
        TextView tvEstado = view.findViewById(R.id.tvEstadoMisPublicaciones);


        ImageButton btnPausarReanudar = view.findViewById(R.id.btnPausarMiPub);
        ImageButton btnEditar = view.findViewById(R.id.btnEditarMiPub);
        ImageButton btnEliminar = view.findViewById(R.id.btnEliminarMiPub);
        TextView tvPausarReanudar = view.findViewById(R.id.tePausarMiPub);

        RelativeLayout rlPublicacion = view.findViewById(R.id.misPublicacionesItem);

        tvTitulo.setText(publicacion.getTitulo());
        if(publicaciones.get(i).getImagen01() != null){
            String url = publicaciones.get(i).getImagen01();
            Picasso.get()
                    .load(url)
                    .into(imagenV);
        }

        if (state.equals(publicacion.getActivo())){
            rlPublicacion.setBackgroundColor(Color.parseColor("#99CBCBCB"));
            tvEstado.setText("Estado - ACTIVO");
            tvEstado.setTextColor(Color.parseColor("#FFFFFF"));
            tvEstado.setBackgroundColor(Color.parseColor("#FF03DAC5"));
            tvPausarReanudar.setText("PAUSAR");
            tvPausarReanudar.setTextColor(Color.parseColor("#000000"));
            btnPausarReanudar.setImageResource(R.drawable.ic_pause);

        }else{
            rlPublicacion.setBackgroundColor(Color.parseColor("#66E81443"));
            tvEstado.setText("Estado - PAUSADO");
            tvEstado.setTextColor(Color.parseColor("#FF000000"));
            tvEstado.setBackgroundColor(Color.parseColor("#FFFFFF"));
            tvPausarReanudar.setText("REANUDAR");
            tvPausarReanudar.setTextColor(Color.parseColor("#000000"));
            btnPausarReanudar.setImageResource(R.drawable.ic_play);

        }

        btnPausarReanudar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (state.equals(publicacion.getActivo())){
                    publicacionRepository.cambiarEstado(publicacion.getUid(),userId, "false");
                    Navigation.findNavController(view).navigate(R.id.misOfertasFragment);
                }else{
                    publicacionRepository.cambiarEstado(publicacion.getUid(),userId, "true");
                    Navigation.findNavController(view).navigate(R.id.misOfertasFragment);
                }
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Eliminar Publicación");
                builder.setMessage("¿Quieres eliminar la publicación: \n" + publicacion.getTitulo() + " ?\nAtención esta acción no se podrá revertir!")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                publicacionRepository.deletePublicacion(publicacion.getUid(), userId);
                                Navigation.findNavController(view).navigate(R.id.publicacionBorradaFragment);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setCancelable(false)
                        .show();
            }

        });


        return view;
    }


}
