package com.dam.pruebafirebaseauth.rv.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.pruebafirebaseauth.R;

import java.util.ArrayList;

public class AnimalesAdapter extends RecyclerView.Adapter<AnimalesAdapter.AnimalVH> {

    @NonNull
    @Override
    public AnimalVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.animal_layout, parent, false);
        AnimalVH vh = new AnimalVH(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalVH holder, int position) {
        holder.tvAnimal.setText(listaAnimales.get(position));
    }

    @Override
    public int getItemCount() {
        return listaAnimales.size();
    }

    public static class AnimalVH extends RecyclerView.ViewHolder {
        TextView tvAnimal;

        public AnimalVH(@NonNull View itemView) {
            super(itemView);
            tvAnimal = itemView.findViewById(R.id.tv_animales);
        }
    }

    ArrayList<String> listaAnimales;
    public AnimalesAdapter(ArrayList<String> listaAnimales){
        this.listaAnimales = listaAnimales;
    }

}
