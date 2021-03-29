package com.cabezasfive.truekapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class PublicacionViewHolder extends RecyclerView.ViewHolder {
    private final TextView tituloItemView;
    private final TextView descripcionItemView;

    private PublicacionViewHolder(View itemView){
        super(itemView);
        tituloItemView = itemView.findViewById(R.id.textViewTitulo);
        descripcionItemView = itemView.findViewById(R.id.textViewDescripcion);
    }

    public void bind(String titulo, String descripcion){
        tituloItemView.setText(titulo);
        descripcionItemView.setText(descripcion);
    }

    static PublicacionViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.publicacion_item, parent, false);
        return new PublicacionViewHolder(view);
    }
}
