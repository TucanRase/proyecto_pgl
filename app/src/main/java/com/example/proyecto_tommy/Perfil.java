package com.example.proyecto_tommy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class Perfil extends AppCompatActivity {
    DBHelper DB;
    Button btnCancelar,btnAceptar,btnEditar,btnEliminar,btnVolver;
    TextInputLayout txtEmail,txtContrasenaP,txtContrasena2,txtTipoP,txtCursoP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        //Inicializamos los botones y los inputs
        btnAceptar=(Button) findViewById(R.id.btnAceptar);
        btnCancelar=(Button) findViewById(R.id.btnCancelar);
        btnEditar=(Button) findViewById(R.id.btnEditar);
        btnEliminar=(Button) findViewById(R.id.btnEliminar);
        btnVolver=(Button) findViewById(R.id.btnVolver);
        txtEmail=(TextInputLayout) findViewById(R.id.txtEmail);
        txtContrasenaP=(TextInputLayout) findViewById(R.id.txtContrasenaP);
        txtContrasena2=(TextInputLayout) findViewById(R.id.txtContrasena2);
        txtTipoP=(TextInputLayout) findViewById(R.id.txtTipoP);
        txtCursoP=(TextInputLayout) findViewById(R.id.txtCursoP);


    }
}