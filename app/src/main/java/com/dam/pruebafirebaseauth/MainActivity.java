package com.dam.pruebafirebaseauth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth fba;
    private FirebaseUser user;
    private FirebaseDatabase fbd;
    private DatabaseReference dbref;
    TextView tv_user;
    Button btn_logout, btnSetValue, btnPush;
    int cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_user = findViewById(R.id.tv_user);
        btn_logout = findViewById(R.id.btn_logout);
        btnSetValue = findViewById(R.id.btnSetValue);
        btnPush = findViewById(R.id.btnPush);

        fbd = FirebaseDatabase.getInstance();
        dbref = fbd.getReference();
        cont = 1;


        fba = FirebaseAuth.getInstance();
        user = fba.getCurrentUser();
        tv_user.setText(user.getEmail());

        btn_logout.setOnClickListener(this);
        btnSetValue.setOnClickListener(this);
        btnPush.setOnClickListener(this);
    }

    private void logout() {
        fba.signOut();
        System.out.println("User disconected");

        Intent i  = new Intent(this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_logout){
            logout();
        } else if (view.getId() == R.id.btnSetValue){
            probarSetValue();
        } else if (view.getId() == R.id.btnPush){
            probarPush();
        }
    }

    private void probarPush() {
        String clave = dbref.push().getKey();
        dbref.child("Colores").child(clave).setValue("Rojo");
    }

    private void probarSetValue() {
        dbref.child("Ciudades").child("ciudad" + cont).setValue("Madrid");
        cont++;
    }
}