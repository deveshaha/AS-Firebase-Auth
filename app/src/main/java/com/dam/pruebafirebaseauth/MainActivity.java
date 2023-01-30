package com.dam.pruebafirebaseauth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dam.pruebafirebaseauth.model.Coche;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth fba;
    private FirebaseUser user;
    private FirebaseDatabase fbd;
    private DatabaseReference dbref;
    TextView tv_user;
    Button btn_logout, btnSetValue, btnPush, btnUpdateChildren, btnSetValueObj,
            btnUpdateChildrenObj, btnModifAtr, btnRemove, btnSetValueNull,
            btnLeerValue, btnLeerObj, btnLeerChild;
    int cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_user = findViewById(R.id.tv_user);
        btn_logout = findViewById(R.id.btn_logout);
        btnSetValue = findViewById(R.id.btnSetValue);
        btnPush = findViewById(R.id.btnPush);
        btnUpdateChildren = findViewById(R.id.btnUpdateChildern);
        btnSetValueObj = findViewById(R.id.btnSetValueObj);
        btnUpdateChildrenObj = findViewById(R.id.btnUpdateChildrenObj);
        btnModifAtr = findViewById(R.id.btnModifAtr);
        btnRemove = findViewById(R.id.btnRemove);
        btnSetValueNull = findViewById(R.id.btnSetValueNull);
        btnLeerValue = findViewById(R.id.btnLeerValue);
        btnLeerObj = findViewById(R.id.btnLeerObj);
        btnLeerChild = findViewById(R.id.btnLeerChild);

        fbd = FirebaseDatabase.getInstance();
        dbref = fbd.getReference();
        cont = 1;


        fba = FirebaseAuth.getInstance();
        user = fba.getCurrentUser();
        if (user != null) {
            tv_user.setText(user.getEmail());
        }

        btn_logout.setOnClickListener(this);
        btnSetValue.setOnClickListener(this);
        btnPush.setOnClickListener(this);
        btnUpdateChildren.setOnClickListener(this);
        btnSetValueObj.setOnClickListener(this);
        btnUpdateChildrenObj.setOnClickListener(this);
        btnModifAtr.setOnClickListener(this);
        btnRemove.setOnClickListener(this);
        btnSetValueNull.setOnClickListener(this);
        btnLeerValue.setOnClickListener(this);
        btnLeerObj.setOnClickListener(this);
        btnLeerChild.setOnClickListener(this);
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
        } else if (view.getId() == R.id.btnUpdateChildern){
            probarUpdateChildren();
        } else if (view.getId() == R.id.btnSetValueObj){
            probarSetValueObj();
        } else if (view.getId() == R.id.btnUpdateChildrenObj){
            probarUpdateChildrenObj();
        } else if (view.getId() == R.id.btnModifAtr){
            probarModifAtr();
        } else if (view.getId() == R.id.btnRemove){
            probarRemove();
        } else if (view.getId() == R.id.btnSetValueNull){
            probarSetValueNull();
        } else if (view.getId() == R.id.btnLeerValue){
            probarLeerValue();
        } else if (view.getId() == R.id.btnLeerObj){
            probarLeerObj();
        } else if (view.getId() == R.id.btnLeerChild){
            probarLeerChild();
        }
    }

    private void probarLeerChild() {
        Intent intent  = new Intent(this, LeerChildActivity.class);
        startActivity(intent);
    }

    private void probarLeerObj() {
        Intent intent  = new Intent(this, LeerObjActivity.class);
        startActivity(intent);
    }

    private void probarLeerValue() {
        Intent intent  = new Intent(this, ActivityLeerValue.class);
        startActivity(intent);
    }

    private void probarSetValueNull() {
        dbref.child("Animales/animal3").setValue(null);
    }

    private void probarRemove() {
        // Se puede poner asi y te ahorras poner más childs
        dbref.child("Coches/coche11").removeValue();
    }

    private void probarModifAtr() {
        //Se puede poner asi o
        dbref.child("Coches").child("coche11/modelo").setValue("LEON");
    }

    private void probarUpdateChildrenObj() {
        Map<String, Object> estructuraCoches = new HashMap<String, Object>();
        estructuraCoches.put("coche" + (cont + (cont++ + 10)), new Coche("2345CDF", "MERCEDES BENZ", "CLASE A"));
        estructuraCoches.put("coche" + (cont + (cont++ + 10)), new Coche("3456DFG", "BMW", "X5"));
        estructuraCoches.put("coche" + (cont + (cont++ + 10)), new Coche("4567FGH", "AUDI", "A3"));

        dbref.child("Coches").updateChildren(estructuraCoches);

    }

    private void probarSetValueObj() {
        dbref.child("Coches").child("coche" + (cont++ + 10)).setValue(new Coche("1234BCD", "SEAT", "IBIZA"));
    }


    private void probarUpdateChildren() {
        Map<String, Object> estructura = new HashMap<String, Object>();
        estructura.put("animal1", "Perro");
        estructura.put("animal2", "Gato");
        estructura.put("animal3", "Raton");
        estructura.put("animal4", "Conejo");
        estructura.put("animal5", "Tortuga");
        estructura.put("animal6", "Pez");
        estructura.put("animal7", "Araña");
        estructura.put("animal8", "Mosca");
        estructura.put("animal9", "Mosquito");

        dbref.child("Animales").updateChildren(estructura);
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