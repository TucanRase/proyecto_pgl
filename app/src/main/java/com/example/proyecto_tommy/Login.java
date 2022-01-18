package com.example.proyecto_tommy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class Login extends AppCompatActivity {
    static ArrayList<Ordenador> listaOrdenadores = new ArrayList<Ordenador>();
    TextInputLayout usuario, contrasena;
    Button btnIniciar, btnRegistrar;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuario = findViewById(R.id.txtUsu);
        contrasena = findViewById(R.id.txtContra);
        btnIniciar = findViewById(R.id.btnInicio);
        btnRegistrar = findViewById(R.id.btnRegistro);
        DB = new DBHelper(this);
        /**
         * Al clickar en el boton se procede a hacer la comprobación de los datos introducidos en los campos de login con
         * los datos disponibles en la base de datos. En el caso de que haya errores se notifica por medio de errores en
         * el input además se notificará con un mensaje por pantalla.
         * **/
        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario.setError(null);
                contrasena.setError(null);
                String email = usuario.getEditText().getText().toString().trim();
                String contra = contrasena.getEditText().getText().toString().trim();

                if (email.isEmpty())
                    usuario.setError("Por favor introduzca un usuario");
                else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
                    usuario.setError("Por favor introduzca un email válido");
                else if (contra.isEmpty())
                    contrasena.setError("Por favor introduzca una contraseña");
                else {
                    Boolean checkuserpass = DB.comprobarContrasenaUsuario(email, contra);
                    if (checkuserpass == true) {
                        Toast.makeText(Login.this, "Sesión iniciada correctamente", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Inicio.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
                    } else if (!DB.comprobarUsuario(email)) {
                        usuario.setError("Revise el usuario");
                    } else
                        contrasena.setError("Revise la contraseña");
                    Toast.makeText(getApplicationContext(), "Credenciales incorrectos", Toast.LENGTH_SHORT).show();
                }

            }
        });
        /**
         * Establecemos el onclick del botón
         */
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Registro.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
            }
        });
    }

    /**
     * Método para establecer la animación al pulsar el botón "atrás"
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
    }


}