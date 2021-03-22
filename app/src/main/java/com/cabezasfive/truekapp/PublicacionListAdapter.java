package com.cabezasfive.truekapp;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.cabezasfive.truekapp.entities.Publicacion;

public class PublicacionListAdapter extends ListAdapter<Publicacion, PublicacionViewHolder> {

    public onItemClickListener listener;

    public PublicacionListAdapter(@NonNull DiffUtil.ItemCallback<Publicacion> diffCallback){
        super(diffCallback);
    }

    @NonNull
    @Override
    public PublicacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return PublicacionViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull PublicacionViewHolder holder, int position) {
        Publicacion publicacionActual = getItem(position);
        holder.bind(publicacionActual.getTitulo(), publicacionActual.getDescripcion());

        // obtener la referencia del boton de borrar
        ImageButton deleteButton = holder.itemView.findViewById(R.id.buttonDelete);

        // Listener de onClick
        deleteButton.setOnClickListener(view ->{
            if(listener!=null){
                listener.onItemDelete(publicacionActual);
            }
        });

        // Click en un item de la lista - Edita
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    listener.onItemClick(publicacionActual);
                }
            }
        });
    }

    public static class PublicacionDiff extends DiffUtil.ItemCallback<Publicacion>{
        @Override
        public boolean areItemsTheSame(@NonNull Publicacion oldItem, @NonNull Publicacion newItem) {
            return oldItem.getId()== newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Publicacion oldItem, @NonNull Publicacion newItem) {
            return oldItem.getTitulo().equals(newItem.getTitulo()) && oldItem.getDescripcion().equals(newItem.getDescripcion());
        }
    }

    public interface onItemClickListener{
        void onItemDelete(Publicacion publicacion);
        void onItemClick(Publicacion publicacion);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }
}
