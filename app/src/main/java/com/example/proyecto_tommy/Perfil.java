package com.example.proyecto_tommy;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Perfil extends AppCompatActivity {
    Button btnCancelar, btnAceptar, btnEditar, btnEliminar, btnVolver;
    TextInputLayout txtEmail, txtContrasenaP, txtContrasena2, txtTipoP, txtCursoP;
    String correo, tipo, curso, contrasena1;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    FirebaseUser user;
    String userId;
    ProgressBar progreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();
        userId = user.getUid();
        progreso=findViewById(R.id.progressBar3);

        DocumentReference documentReference = fStore.collection("usuarios").document(userId);


        //Inicializamos los botones y los inputs
        btnAceptar = findViewById(R.id.btnAceptar);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnEditar = findViewById(R.id.btnEditar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnVolver = findViewById(R.id.btnVolver);
        txtEmail = findViewById(R.id.txtEmail);
        txtContrasenaP = findViewById(R.id.txtContrasenaP);
        txtContrasena2 = findViewById(R.id.txtContrasena2);
        txtTipoP = findViewById(R.id.txtTipoP);
        txtCursoP = findViewById(R.id.txtCursoP);


        AutoCompleteTextView tipoUsuarios = findViewById(R.id.dropTipo);
        String[] tipos = getResources().getStringArray(R.array.tipoUsuario);
        //Creamos y establecemos el ArrayAdapter del dropdown con sus valores
        ArrayAdapter<String> arrayAdapterOrdenar = new ArrayAdapter<>(getApplicationContext(), R.layout.item_dropdown, tipos);
        tipoUsuarios.setAdapter(arrayAdapterOrdenar);

        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                try {
                    txtEmail.getEditText().setText(value.getString("email"));
                    txtTipoP.getEditText().setText(value.getString("tipo"));
                    txtCursoP.getEditText().setText(value.getString("curso"));
                }catch (NullPointerException e){
                    Log.d(TAG,"El usuario está vacío");
                }
            }
        });


        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tipo = Objects.requireNonNull(txtTipoP.getEditText()).getText().toString().trim();
                btnEditar.setVisibility(View.GONE);
                btnAceptar.setVisibility(View.VISIBLE);
                btnCancelar.setVisibility(View.VISIBLE);
                btnEliminar.setVisibility(View.GONE);
                txtContrasenaP.setVisibility(View.VISIBLE);
                txtContrasena2.setVisibility(View.VISIBLE);
                txtCursoP.setEnabled(true);
                if (tipo.equals("Profesor")) {
                    txtTipoP.setEnabled(true);
                    txtTipoP.getEditText().setText("");
                    txtEmail.setEnabled(true);
                }
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnEditar.setVisibility(View.VISIBLE);
                btnAceptar.setVisibility(View.GONE);
                btnCancelar.setVisibility(View.GONE);
                btnEliminar.setVisibility(View.VISIBLE);
                txtContrasenaP.setVisibility(View.GONE);
                txtContrasena2.setVisibility(View.GONE);
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validarFormulario()){
                    insertarDatos();
                }
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Perfil.this);
                alert.setTitle("Eliminar usuario");
                alert.setMessage("¿Está seguro de querer eliminar su usuario y cerrar sesión?");
                alert.setPositiveButton("Sí", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eliminarUsuario();
                    }
                });

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alert.show();
            }
        });
    }

    /**
     * Creamos el menú
     **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.volver_inicio, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Establecemos las acciones al pulsar las opciones del menú
     **/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            finish();
            overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public boolean validarFormulario() {
        Boolean valido = true;
        String email = Objects.requireNonNull(txtEmail.getEditText()).getText().toString().trim();
        if (email.isEmpty()) {
            txtEmail.setError("Por favor, introduzca un usuario");
            valido = false;
        } else
            txtEmail.setError(null);
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txtEmail.setError("Por favor, introduzca un email válido");
            valido = false;
        } else
            txtEmail.setError(null);

        String contra = Objects.requireNonNull(txtContrasenaP.getEditText()).getText().toString().trim();
        if (contra.isEmpty()) {
            txtContrasenaP.setError("Por favor, introduzca una contraseña");
            valido = false;
        } else
            txtContrasenaP.setError(null);

        String confirmContra = Objects.requireNonNull(txtContrasena2.getEditText()).getText().toString().trim();
        if (confirmContra.isEmpty()) {
            txtContrasena2.setError("Por favor, vuelva a introducir la contraseña");
            valido = false;
        } else
            txtContrasena2.setError(null);

        String tipo = Objects.requireNonNull(txtTipoP.getEditText()).getText().toString().trim();
        if (tipo.isEmpty()) {
            txtTipoP.setError("Por favor, introduzca una contraseña");
            valido = false;
        } else
            txtTipoP.setError(null);

        String curso = Objects.requireNonNull(txtCursoP.getEditText()).getText().toString().trim();
        if (curso.isEmpty() && Integer.parseInt(curso) > 0) {
            txtCursoP.setError("Por favor, introduzca el curso y asegúrese de que es mayor a 0");
            valido = false;
        } else
            txtCursoP.setError(null);
        if (!confirmContra.equals(contra)) {
            txtContrasena2.setError("Ambas contraseñas tienen que ser iguales");
            txtContrasenaP.setError("Ambas contraseñas tienen que ser iguales");
            valido = false;
        } else {
            txtContrasena2.setError(null);
            txtContrasenaP.setError(null);
        }
        if (contra.length() < 6) {
            txtContrasenaP.setError("La contraseña tiene que tener mínimo 6 caracteres");
            valido = false;
        } else
            txtContrasenaP.setError(null);

        return valido;
    }

    public void insertarDatos() {
        progreso.setVisibility(View.VISIBLE);
        correo = Objects.requireNonNull(txtEmail.getEditText()).getText().toString().trim();
        tipo = Objects.requireNonNull(txtTipoP.getEditText()).getText().toString().trim();
        curso = Objects.requireNonNull(txtCursoP.getEditText()).getText().toString();
        contrasena1 = (txtContrasenaP.getEditText()).getText().toString();

        userId = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("usuarios").document(userId);
        Map<String, Object> usuario = new HashMap<>();
        usuario.put("email", correo);
        usuario.put("tipo", tipo);
        usuario.put("curso", curso);

        user.updatePassword(contrasena1).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG,"no se ha podido actualizar los datos"+e.getMessage());
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                documentReference.set(usuario).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "onSuccess: perfil de usuario actualizado para " + userId);
                        Toast.makeText(Perfil.this, "Sus datos han sido actualizados correctamente", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: " + e.getMessage());
                        Toast.makeText(Perfil.this, "Los datos del usuario no se han podido actualizar."+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        progreso.setVisibility(View.GONE);
    }

    public void eliminarUsuario(){
        progreso.setVisibility(View.VISIBLE);
        DocumentReference documentReference = fStore.collection("usuarios").document(userId);
        fAuth.getCurrentUser().delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                documentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Perfil.this, "El usuario se ha eliminado correctamente.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Perfil.this, LoginFirebase.class);
                        fAuth.signOut();
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Perfil.this, "No se ha podido eliminar el usuario", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Perfil.this, "No se ha podido eliminar el usuario", Toast.LENGTH_SHORT).show();
            }
        });
        progreso.setVisibility(View.GONE);
    }
}