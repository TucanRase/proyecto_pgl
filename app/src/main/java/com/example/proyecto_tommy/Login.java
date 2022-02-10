package com.example.proyecto_tommy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class Login extends AppCompatActivity {
    TextInputLayout usuario, contrasena;
    Button btnIniciar, btnRegistrar;
    DBHelper DB;
    public static String email;

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
         * Al clickar en el botón se procede a hacer la comprobación de los datos introducidos en los campos de login con
         * los datos disponibles en la base de datos. En el caso de que haya errores se notifica por medio de errores en
         * el input. Además, se notificará con un mensaje por pantalla.
         * **/
        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario.setError(null);
                contrasena.setError(null);
                email = Objects.requireNonNull(usuario.getEditText()).getText().toString().trim();
                String contra = Objects.requireNonNull(contrasena.getEditText()).getText().toString().trim();

                if (email.isEmpty())
                    usuario.setError("Por favor, introduzca un usuario");
                else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
                    usuario.setError("Por favor, introduzca un email válido");
                else if (contra.isEmpty())
                    contrasena.setError("Por favor, introduzca una contraseña");
                else if (!DB.comprobarUsuario(email))
                    usuario.setError("Revise el usuario");
                else {
                    Boolean checkuserpass = DB.comprobarContrasenaUsuario(email, contra);
                    if (checkuserpass) {
                        Toast.makeText(Login.this, "Sesión iniciada correctamente", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Portada.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
                    } else {
                        contrasena.setError("Revise la contraseña");
                        Toast.makeText(getApplicationContext(), "Credenciales incorrectos", Toast.LENGTH_SHORT).show();
                    }
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