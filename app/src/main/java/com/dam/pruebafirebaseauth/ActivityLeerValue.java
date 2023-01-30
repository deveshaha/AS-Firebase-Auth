package com.dam.pruebafirebaseauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.dam.pruebafirebaseauth.rv.util.AnimalesAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityLeerValue extends AppCompatActivity {

    RecyclerView rv;
    LinearLayoutManager llm;
    AnimalesAdapter adapter;
    ArrayList<String> listaAnimales;
    private DatabaseReference dbref;
    ValueEventListener vel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leer_value);
        dbref = FirebaseDatabase.getInstance().getReference("Animales");
        rv = findViewById(R.id.rvAnimales);
    }

    private void configurarRV() {
        llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        adapter = new AnimalesAdapter(listaAnimales);

        rv.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        addValueEventListener();
    }

    private void addValueEventListener() {
        if (vel == null){
            vel = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String animal;
                    listaAnimales = new ArrayList<>();
                    for (DataSnapshot nodo : snapshot.getChildren()){
                        animal = nodo.getValue(String.class);
                        listaAnimales.add(animal);
                    }
                    configurarRV();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.w("LECTURA FIREBASE", "Lectura Cancelada: " + error.getMessage());
                }

            };
        }
        dbref.addValueEventListener(vel);
    }

    @Override
    protected void onPause() {
        super.onPause();
        dbref.removeEventListener(vel);
        if (vel != null) {
            vel = null;
        }
    }
}