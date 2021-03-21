package com.cabezasfive.truekapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class PublicacionViewHolder extends RecyclerView.ViewHolder {
    private final TextView publicacionItemView;

    private PublicacionViewHolder(View itemView){
        super(itemView);
        publicacionItemView = itemView.findViewById(R.id.textViewTitulo);
    }

    public void bind(String texto){
        publicacionItemView.setText(texto);
    }

    static PublicacionViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.publicacion_item, parent, false);
        return new PublicacionViewHolder(view);
    }
}
