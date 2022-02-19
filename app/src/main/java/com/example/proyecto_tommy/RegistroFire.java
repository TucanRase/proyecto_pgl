package com.example.proyecto_tommy;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegistroFire extends AppCompatActivity {
    TextInputLayout usuario, contrasena, confirmContrasena, txtTipo, txtCurso;
    Button registrar, iniciar;
    DBHelper DB;
    ProgressBar progreso;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    String userID;

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
        progreso = findViewById(R.id.progressBar2);

        mAuth = FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        DB = new DBHelper(this);

        /*if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), Portada.class));
            overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
            finish();
        }*/

        AutoCompleteTextView tipoUsuarios = findViewById(R.id.dropDownTipos);
        String[] tipos = getResources().getStringArray(R.array.tipoUsuario);

        //Creamos y establecemos el ArrayAdapter del dropdown con sus valores
        ArrayAdapter<String> arrayAdapterOrdenar = new ArrayAdapter<>(getApplicationContext(), R.layout.item_dropdown, tipos);
        tipoUsuarios.setAdapter(arrayAdapterOrdenar);


        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Objects.requireNonNull(usuario.getEditText()).getText().toString().trim();
                String contra = Objects.requireNonNull(contrasena.getEditText()).getText().toString().trim();
                String confirmContra = Objects.requireNonNull(confirmContrasena.getEditText()).getText().toString().trim();
                String tipo = Objects.requireNonNull(txtTipo.getEditText()).getText().toString().trim();
                String curso = Objects.requireNonNull(txtCurso.getEditText()).getText().toString().trim();
                progreso.setVisibility(View.VISIBLE);
                if (validarFormulario()) {
                    mAuth.createUserWithEmailAndPassword(email, contra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegistroFire.this, "Usuario creado satisfactoriamente", Toast.LENGTH_SHORT).show();
                                userID = mAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = fStore.collection("usuarios").document(userID);
                                Map<String,Object> usuario=new HashMap<>();
                                usuario.put("email",email);
                                usuario.put("tipo",tipo);
                                usuario.put("curso",curso);
                                documentReference.set(usuario).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d(TAG,"onSuccess: perfil de usuario creado para "+userID);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG,"onFailure: "+e.toString());
                                    }
                                });
                                progreso.setVisibility(View.GONE);
                                startActivity(new Intent(getApplicationContext(), Portada.class));
                                overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
                            } else {
                                Toast.makeText(RegistroFire.this, "¡Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progreso.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }
        });
        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginFirebase.class);
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

    public Boolean validarFormulario() {
        Boolean valido = true;
        String email = Objects.requireNonNull(usuario.getEditText()).getText().toString().trim();
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

        String contra = Objects.requireNonNull(contrasena.getEditText()).getText().toString().trim();
        if (contra.isEmpty()) {
            contrasena.setError("Por favor, introduzca una contraseña");
            valido = false;
        } else
            contrasena.setError(null);

        String confirmContra = Objects.requireNonNull(confirmContrasena.getEditText()).getText().toString().trim();
        if (confirmContra.isEmpty()) {
            confirmContrasena.setError("Por favor, vuelva a introducir la contraseña");
            valido = false;
        } else
            confirmContrasena.setError(null);

        String tipo = Objects.requireNonNull(txtTipo.getEditText()).getText().toString().trim();
        if (tipo.isEmpty()) {
            txtTipo.setError("Por favor, introduzca una contraseña");
            valido = false;
        } else
            txtTipo.setError(null);

        String curso = Objects.requireNonNull(txtCurso.getEditText()).getText().toString().trim();
        if (curso.isEmpty() && Integer.parseInt(curso) > 0) {
            txtCurso.setError("Por favor, introduzca el curso y asegúrese de que es mayor a 0");
            valido = false;
        } else
            txtCurso.setError(null);
        if (!confirmContra.equals(contra)) {
            confirmContrasena.setError("Ambas contraseñas tienen que ser iguales");
            contrasena.setError("Ambas contraseñas tienen que ser iguales");
            valido = false;
        } else {
            confirmContrasena.setError(null);
            contrasena.setError(null);
        }
        if (contra.length() < 6) {
            contrasena.setError("La contraseña tiene que tener mínimo 6 caracteres");
            valido = false;
        } else
            contrasena.setError(null);

        return valido;
    }
}