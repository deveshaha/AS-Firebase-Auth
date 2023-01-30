package com.dam.pruebafirebaseauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.dam.pruebafirebaseauth.model.Coche;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LeerObjActivity extends AppCompatActivity {

    TextView tvDatosObj;
    DatabaseReference dbref;
    ValueEventListener vel;
    String resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leer_obj);

        tvDatosObj = findViewById(R.id.tvDatosObj);
        dbref = FirebaseDatabase.getInstance().getReference("Coches");
        resultado = "";
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
                    Coche coche;
                    for (DataSnapshot nodo : snapshot.getChildren()){
                        coche = nodo.getValue(Coche.class);
                        resultado += coche.toString() + "\n";
                    }
                    tvDatosObj.setText(resultado);
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