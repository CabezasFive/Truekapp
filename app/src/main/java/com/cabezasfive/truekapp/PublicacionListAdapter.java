package com.cabezasfive.truekapp;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.cabezasfive.truekapp.entities.Publicacion;

public class PublicacionListAdapter extends ListAdapter<Publicacion, PublicacionViewHolder> {
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
        holder.bind(publicacionActual.getTitulo());
    }

    static class PublicacionDiff extends DiffUtil.ItemCallback<Publicacion>{
        @Override
        public boolean areItemsTheSame(@NonNull Publicacion oldItem, @NonNull Publicacion newItem) {
            return oldItem.getId()== newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Publicacion oldItem, @NonNull Publicacion newItem) {
            return oldItem.getTitulo().equals(newItem.getTitulo());
        }
    }
}
