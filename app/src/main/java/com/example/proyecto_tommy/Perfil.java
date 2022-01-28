package com.example.proyecto_tommy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
        DB=new DBHelper(this);

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

        recogerUsuario();

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnEditar.setVisibility(View.GONE);
                btnAceptar.setVisibility(View.VISIBLE);
                btnCancelar.setVisibility(View.VISIBLE);
                btnEliminar.setVisibility(View.GONE);
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnEditar.setVisibility(View.VISIBLE);
                btnAceptar.setVisibility(View.GONE);
                btnCancelar.setVisibility(View.GONE);
                btnEliminar.setVisibility(View.VISIBLE);
            }
        });

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {/*
                if (email.isEmpty())
                    usuario.setError("Por favor introduzca un usuario");
                else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
                    usuario.setError("Por favor introduzca un email válido");
                else if (tipo.isEmpty())
                    txtTipo.setError("Por favor introduzca el tipo de usuario");
                else if (curso.isEmpty())
                    txtCurso.setError("Por favor introduzca su curso(número)");
                else if (contra.isEmpty())
                    contrasena.setError("Por favor introduzca una contraseña");
                else if (confirmContra.isEmpty())
                    confirmContrasena.setError("Por favor introduzca la contraseña");
                else {
                    if (Integer.valueOf(curso) <= 0)
                        txtCurso.setError("Por favor introduzca un número superior a 0");
                    else{
                        System.out.println("csadasdasdasabezaaa");
                        if (contra.equals(confirmContra)) {
                            System.out.println("cabezaaasewqewqeqwewqeqwewqwqeqwa");
                            Boolean comprobarUsuario = DB.comprobarUsuario(email);
                            if (comprobarUsuario == false) {
                                Boolean insertar = DB.insertarDatosUsuario(email, contra, tipo, Integer.valueOf(curso));
                                if (insertar) {
                                    System.out.println("cabezaaa");
                                    Login.email = email;
                                    Toast.makeText(Registro.this, "Registrado correctamente", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), Inicio.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
                                } else {
                                    Toast.makeText(Registro.this, "Registro fallido", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(Registro.this, "Este usuario ya existe, por favor inicie sesión", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Registro.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                            contrasena.setError("Asegurese de que coinciden las contraseñas");
                            confirmContrasena.setError("Asegurese de que coinciden las contraseñas");

                        }}
                }*/
            }
        });
    }

    public void recogerUsuario(){
        SQLiteDatabase db = DB.getWritableDatabase();
        Cursor res = db.rawQuery("select * from usuarios where usuario='" + Login.email + "'", null);
        String email="";
        String contrasena="";
        String tipo="";
        int curso=0;
        while (res.moveToNext()) {
            email= res.getString(0);
            contrasena = res.getString(1);
            tipo = res.getString(2);
            curso = res.getInt(3);

        }

        txtEmail.getEditText().setText(email);
        txtTipoP.getEditText().setText(tipo);
        txtCursoP.getEditText().setText(String.valueOf(curso));

    }
}