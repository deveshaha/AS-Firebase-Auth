package com.dam.pruebafirebaseauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Account deleted!";

    EditText et_mail, et_password;
    Button btn_login, btn_register, btn_delete;

    private FirebaseAuth fba;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_mail = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_passowrd);

        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        btn_delete = findViewById(R.id.btn_delete);

        btn_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_delete.setOnClickListener(this);

        fba = FirebaseAuth.getInstance();

        //Comprobamos si hay usuario conectado
        user = fba.getCurrentUser();
        if (user == null){
            btn_register.setEnabled(true);
            btn_delete.setVisibility(View.GONE);
        } else {
            et_mail.setText(user.getEmail());
            btn_register.setEnabled(false);
            btn_delete.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        //TODO: Diferenciar entre btnLogin y btnRegister
        if (view.getId() == R.id.btn_login){
            login();
        } else if (view.getId() == R.id.btn_register){
            register();
        } else if (view.getId() == R.id.btn_delete){
            delete();
        }
    }

    private void delete() {
        fba.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) Log.d(TAG, "User deleted");
            }
        });

        btn_register.setEnabled(true);
        btn_delete.setVisibility(View.GONE);
        et_mail.setText("");
        et_password.setText("");
    }

    private void register() {
        String email = et_mail.getText().toString().trim();
        String pwd = et_password.getText().toString().trim();

        if (email.isEmpty() || pwd.isEmpty()) {
            Snackbar.make(et_mail, R.string.no_datos, Snackbar.LENGTH_SHORT).show();
        } else if (pwd.length() < 8) {
            Snackbar.make(et_mail, R.string.pwd_corta, Snackbar.LENGTH_SHORT).show();
        } else {
            fba.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        user = fba.getCurrentUser();
                        Snackbar.make(btn_register, getString(R.string.msj_registrado), Snackbar.LENGTH_SHORT).show();
                    } else {
                        Snackbar.make(btn_register, getString(R.string.msj_no_registrado), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void login() {

        String email = et_mail.getText().toString().trim();
        String pwd = et_password.getText().toString().trim();

        if (email.isEmpty() || pwd.isEmpty()) {
            Snackbar.make(et_mail, R.string.no_datos, Snackbar.LENGTH_SHORT).show();
        } else {
            fba.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        user = fba.getCurrentUser();
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Snackbar.make(et_password, R.string.pwd_incorrecta, Snackbar.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}