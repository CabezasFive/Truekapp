package com.cabezasfive.truekapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.models.Publicacion;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterListarPublicaciones extends RecyclerView.Adapter<AdapterListarPublicaciones.ViewHolder> implements View.OnClickListener {

    private int resource;
    private ArrayList<Publicacion> publicaciones;
    Context context;


    // escucha de click sobre un item del recycler
    private View.OnClickListener listener;

    public  AdapterListarPublicaciones(ArrayList<Publicacion> publicaciones, int resource, Context context){
        this.publicaciones = publicaciones;
        this.resource = resource;
        this.context = context;
    }



    @NonNull
    @Override
    public AdapterListarPublicaciones.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);

        view.setOnClickListener(this);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //Publicacion publicacion = publicaciones.get(position);
        holder.titulo.setText(publicaciones.get(position).getTitulo());
        holder.descripcion.setText(publicaciones.get(position).getDescripcion());
        if(publicaciones.get(position).getImagen01() != null){
            String url = publicaciones.get(position).getImagen01();
            Picasso.get()
                    .load(url)
                    .into(holder.foto);
        }

        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Se dio en share", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                String shareBody = "http://www.store-a-descarga-de-app-Tuekapp/";
                String shareSubject = "Mira esta publicación que encontré en Truekapp\n" + publicaciones.get(position).getTitulo()
                        + "\n Truekapp – Una aplicación donde podés intercambiar lo que desees sin gastar un peso";
                i.putExtra(Intent.EXTRA_SUBJECT,shareSubject);
                i.putExtra(Intent.EXTRA_TEXT, shareBody);
                context.startActivity(Intent.createChooser(i,"Truekapp Link"));
                Toast.makeText(context, "Share!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return publicaciones.size();
    }

    // Metodos para el clikListener

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView titulo;
        private TextView descripcion;
        private ImageView foto;
        private ImageButton btnShare;

        public ViewHolder(@NonNull View view){
            super(view);

            this.titulo = (TextView) view.findViewById(R.id.textViewTitulo);
            this.descripcion = (TextView) view.findViewById(R.id.textViewDescripcion);
            this.foto = (ImageView) view.findViewById(R.id.imViewCardPublicacion);
            this.btnShare = (ImageButton) view.findViewById(R.id.sharePubli);
        }
    }


}
