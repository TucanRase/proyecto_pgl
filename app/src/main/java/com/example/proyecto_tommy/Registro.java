package com.example.proyecto_tommy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class Registro extends AppCompatActivity {
    TextInputLayout usuario,contrasena,confirmContrasena;
    Button registrar,iniciar;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        usuario=findViewById(R.id.txtUsuReg);
        contrasena=findViewById(R.id.txtContraReg);
        confirmContrasena=findViewById(R.id.txtConfContra);
        registrar=(Button) findViewById(R.id.btnRegistrar);
        iniciar=(Button) findViewById(R.id.btnRegistrar2);
        DB=new DBHelper(this);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario.setError(null);
                contrasena.setError(null);
                confirmContrasena.setError(null);
                String user=usuario.getEditText().getText().toString().trim();
                String contra=contrasena.getEditText().getText().toString().trim();
                String confirmContra=confirmContrasena.getEditText().getText().toString().trim();

                if(user.isEmpty())
                    usuario.setError("Por favor introduzca un usuario");
                else if(contra.isEmpty())
                    contrasena.setError("Por favor introduzca una contraseña");
                else if(confirmContra.isEmpty())
                    confirmContrasena.setError("Por favor introduzca la contraseña");
                else{
                    if(contra.equals(confirmContra)){
                        Boolean comprobarUsuario = DB.comprobarUsuario(user);
                        if(comprobarUsuario==false){
                            Boolean insertar=DB.insertarDatos(user,contra);
                            if(insertar==true) {
                                Toast.makeText(Registro.this, "Registrado correctamente", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
                            }else{
                                Toast.makeText(Registro.this, "Registro fallido", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(Registro.this, "Este usuario ya existe, por favor inicie sesión", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(Registro.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                        contrasena.setError("Asegurese de que coinciden las contraseñas");
                        confirmContrasena.setError("Asegurese de que coinciden las contraseñas");

                    }
                }

            }
        });
        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
            }
        });
    }
    /**
     * Método para establecer la animación al pulsar el botón "atrás"
     * */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
    }
}