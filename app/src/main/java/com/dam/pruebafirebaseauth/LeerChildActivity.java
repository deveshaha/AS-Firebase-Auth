package com.dam.pruebafirebaseauth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LeerChildActivity extends AppCompatActivity {

    TextView tvDatosChild;
    DatabaseReference dbref;
    ChildEventListener cel;
    String resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leer_child);

        tvDatosChild = findViewById(R.id.tvDatosChild);
        dbref = FirebaseDatabase.getInstance().getReference("Animales");
        resultado = "";
    }

    @Override
    protected void onResume() {
        super.onResume();
        addChildEventListener();
    }

    private void addChildEventListener() {
        if (cel == null) {
            cel = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    String animal= snapshot.getValue(String.class);
                    resultado += animal + "\n";
                    tvDatosChild.setText(resultado);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };
        }
        dbref.addChildEventListener(cel);

    }

    @Override
    protected void onPause() {
        super.onPause();
        dbref.removeEventListener(cel);
        if (cel != null){
            dbref.removeEventListener(cel);
        }
    }
}