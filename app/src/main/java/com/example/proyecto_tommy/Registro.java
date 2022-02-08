package com.example.proyecto_tommy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class Registro extends AppCompatActivity {
    TextInputLayout usuario, contrasena, confirmContrasena, txtTipo, txtCurso;
    Button registrar, iniciar;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        usuario = findViewById(R.id.txtUsuReg);
        contrasena = findViewById(R.id.txtContraReg);
        confirmContrasena = findViewById(R.id.txtConfContra);
        txtTipo = findViewById(R.id.txtTipo);
        txtCurso = findViewById(R.id.txtCursoP);
        registrar = findViewById(R.id.btnRegistrar);
        iniciar = findViewById(R.id.btnRegistrar2);
        DB = new DBHelper(this);

        AutoCompleteTextView tipoUsuarios = findViewById(R.id.dropDownTipos);
        String[] tipos = getResources().getStringArray(R.array.tipoUsuario);

        //Creamos y establecemos el ArrayAdapter del dropdown con sus valores
        ArrayAdapter<String> arrayAdapterOrdenar = new ArrayAdapter<>(getApplicationContext(), R.layout.item_dropdown, tipos);
        tipoUsuarios.setAdapter(arrayAdapterOrdenar);


        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario.setError(null);
                contrasena.setError(null);
                confirmContrasena.setError(null);
                txtTipo.setError(null);
                txtCurso.setError(null);
                String email = Objects.requireNonNull(usuario.getEditText()).getText().toString().trim();
                String contra = Objects.requireNonNull(contrasena.getEditText()).getText().toString().trim();
                String confirmContra = Objects.requireNonNull(confirmContrasena.getEditText()).getText().toString().trim();
                String tipo = Objects.requireNonNull(txtTipo.getEditText()).getText().toString().trim();
                String curso = Objects.requireNonNull(txtCurso.getEditText()).getText().toString().trim();

                if (email.isEmpty())
                    usuario.setError("Por favor, introduzca un usuario");
                else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
                    usuario.setError("Por favor, introduzca un email válido");
                else if (tipo.isEmpty())
                    txtTipo.setError("Por favor, introduzca el tipo de usuario");
                else if (curso.isEmpty())
                    txtCurso.setError("Por favor, introduzca su curso (número)");
                else if (contra.isEmpty())
                    contrasena.setError("Por favor, introduzca una contraseña");
                else if (confirmContra.isEmpty())
                    confirmContrasena.setError("Por favor, introduzca la contraseña");
                else {
                    if (Integer.parseInt(curso) <= 0)
                        txtCurso.setError("Por favor, introduzca un número superior a 0");
                    else {
                        if (contra.equals(confirmContra)) {
                            Boolean comprobarUsuario = DB.comprobarUsuario(email);
                            if (!comprobarUsuario) {
                                Boolean insertar = DB.insertarDatosUsuario(email, contra, tipo, Integer.parseInt(curso));
                                if (insertar) {
                                    Login.email = email;
                                    Toast.makeText(Registro.this, "Registrado correctamente", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), ListaOrdenadores.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
                                } else {
                                    Toast.makeText(Registro.this, "Registro fallido", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(Registro.this, "Este usuario ya existe. Por favor, inicie sesión", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Registro.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                            contrasena.setError("Asegúrese de que coinciden las contraseñas");
                            confirmContrasena.setError("Asegúrese de que coinciden las contraseñas");
                        }
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
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
    }
}