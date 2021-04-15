package com.cabezasfive.truekapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cabezasfive.truekapp.R;
import com.cabezasfive.truekapp.models.Publicacion;

import java.util.ArrayList;

public class AdapterListarPublicaciones extends RecyclerView.Adapter<AdapterListarPublicaciones.ViewHolder> implements View.OnClickListener {

    private int resource;
    private ArrayList<Publicacion> publicaciones;

    // escucha de click sobre un item del recycler
    private View.OnClickListener listener;

    public  AdapterListarPublicaciones(ArrayList<Publicacion> publicaciones, int resource){
        this.publicaciones = publicaciones;
        this.resource = resource;
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

        public ViewHolder(@NonNull View view){
            super(view);

            this.titulo = (TextView) view.findViewById(R.id.textViewTitulo);
            this.descripcion = (TextView) view.findViewById(R.id.textViewDescripcion);
        }
    }


}
