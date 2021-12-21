package com.example.proyecto_tommy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {
    TextInputLayout usuario,contrasena;
    Button btnIniciar,btnRegistrar;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuario=findViewById(R.id.txtUsu);
        contrasena=findViewById(R.id.txtContra);
        btnIniciar=findViewById(R.id.btnInicio);
        btnRegistrar=findViewById(R.id.btnRegistro);
        DB = new DBHelper(this);

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = usuario.getEditText().getText().toString().trim();
                String contra = contrasena.getEditText().getText().toString().trim();

                if(user.isEmpty())
                    usuario.setError("Por favor introduzca un usuario");
                else if (contra.isEmpty())
                    contrasena.setError("Por favor introduzca una contraseña");
                else{
                    Boolean checkuserpass = DB.comprobarContrasenaUsuario(user, contra);
                    if(checkuserpass==true){
                        Toast.makeText(Login.this, "Sesión iniciada correctamente", Toast.LENGTH_SHORT).show();
                        Intent intent  = new Intent(getApplicationContext(), Inicio.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(Login.this, "Credenciales incorrectos", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), Registro.class);
                startActivity(intent);
            }
        });
    }


}