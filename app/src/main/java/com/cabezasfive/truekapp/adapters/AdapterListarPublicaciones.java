package com.cabezasfive.truekapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.entities.Publicacion;

import java.util.ArrayList;

public class AdapterListarPublicaciones extends RecyclerView.Adapter<AdapterListarPublicaciones.ViewHolder> {

    private int resource;
    private ArrayList<Publicacion> publicaciones;

    public  AdapterListarPublicaciones(ArrayList<Publicacion> publicaciones, int resource){
        this.publicaciones = publicaciones;
        this.resource = resource;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Publicacion publicacion = publicaciones.get(position);

        holder.titulo.setText(publicacion.getTitulo());
        holder.descripcion.setText(publicacion.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return publicaciones.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView titulo;
        private TextView descripcion;
        private View view;

        public ViewHolder(View view){
            super(view);

            this.view = view;
            this.titulo = (TextView) view.findViewById(R.id.textViewTitulo);
            this.descripcion = (TextView) view.findViewById(R.id.textViewDescripcion);
        }
    }


}
