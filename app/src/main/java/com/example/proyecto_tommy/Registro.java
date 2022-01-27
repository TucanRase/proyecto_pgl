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
        registrar = (Button) findViewById(R.id.btnRegistrar);
        iniciar = (Button) findViewById(R.id.btnRegistrar2);
        DB = new DBHelper(this);

        AutoCompleteTextView tipoUsuarios = (AutoCompleteTextView) findViewById(R.id.dropDownTipos);
        String[] tipos = getResources().getStringArray(R.array.tipoUsuario);

        //Creamos y establecemos el ArrayAdapter del dropdown con sus valores
        ArrayAdapter<String> arrayAdapterOrdenar = new ArrayAdapter<>(getApplicationContext(), R.layout.item_dropdown, tipos);
        tipoUsuarios.setAdapter(arrayAdapterOrdenar);

        // TODO: 14/01/2022 añadir campos de datos y correo electronico 

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario.setError(null);
                contrasena.setError(null);
                confirmContrasena.setError(null);
                txtTipo.setError(null);
                txtCurso.setError(null);
                String email = usuario.getEditText().getText().toString().trim();
                String contra = contrasena.getEditText().getText().toString().trim();
                String confirmContra = confirmContrasena.getEditText().getText().toString().trim();
                String tipo = txtTipo.getEditText().getText().toString().trim();
                String curso = txtCurso.getEditText().getText().toString().trim();

                System.out.println(tipo);

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