package com.example.proyecto_tommy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginFirebase extends AppCompatActivity {
    TextInputLayout usuario, contrasena;
    Button btnIniciar, btnRegistrar;
    DBHelper DB;
    ProgressBar progreso;
    public static String email;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuario = findViewById(R.id.txtUsu);
        contrasena = findViewById(R.id.txtContra);
        btnIniciar = findViewById(R.id.btnInicio);
        btnRegistrar = findViewById(R.id.btnRegistro);
        progreso=findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
        DB = new DBHelper(this);
        /**
         * Al clickar en el botón se procede a hacer la comprobación de los datos introducidos en los campos de login con
         * los datos disponibles en la base de datos. En el caso de que haya errores se notifica por medio de errores en
         * el input. Además, se notificará con un mensaje por pantalla.
         * **/
        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progreso.setVisibility(View.VISIBLE);
                email = Objects.requireNonNull(usuario.getEditText()).getText().toString().trim();
                String contra = Objects.requireNonNull(contrasena.getEditText()).getText().toString().trim();
                if(validarFormulario()){
                    mAuth.signInWithEmailAndPassword(email,contra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(LoginFirebase.this, "Inicio de sesión correcto", Toast.LENGTH_SHORT).show();
                                progreso.setVisibility(View.GONE);
                                startActivity(new Intent(getApplicationContext(),Portada.class));
                                overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
                            }else{
                                Toast.makeText(LoginFirebase.this, "¡Error!"+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                usuario.setError("Compruebe el usuario");
                                contrasena.setError("Compruebe la contraseña");
                            }
                        }
                    });
                }
                progreso.setVisibility(View.GONE);
            }
        });
        /**
         * Establecemos el onclick del botón
         */
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegistroFire.class);
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

    public boolean validarFormulario() {
        Boolean valido = true;
        email = Objects.requireNonNull(usuario.getEditText()).getText().toString().trim();
        String contra = Objects.requireNonNull(contrasena.getEditText()).getText().toString().trim();
        if (email.isEmpty()) {
            usuario.setError("Por favor, introduzca un usuario");
            valido = false;
        } else
            usuario.setError(null);
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            usuario.setError("Por favor, introduzca un email válido");
            valido = false;
        } else
            usuario.setError(null);
        if (contra.isEmpty()) {
            contrasena.setError("Por favor, introduzca una contraseña");
            valido = false;
        } else
            contrasena.setError(null);
        if(contra.length()<6){
            contrasena.setError("La contraseña tiene que tener mínimo 6 caracteres");
            valido=false;
        }else
            contrasena.setError(null);

        return valido;
    }


}