package com.example.proyecto_tommy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;

public class Perfil extends AppCompatActivity {
    DBHelper DB;
    Button btnCancelar, btnAceptar, btnEditar, btnEliminar, btnVolver;
    TextInputLayout txtEmail, txtContrasenaP, txtContrasena2, txtTipoP, txtCursoP;
    String correo,tipo,curso,contrasena1,contrasena2;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        DB = new DBHelper(this);
/*
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        userId=fAuth.getUid();

        DocumentReference documentReference=fStore.collection("usuarios").document(userId);*/



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

       recogerUsuario();
/*
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                txtEmail.getEditText().setText(value.getString("email"));
                txtTipoP.getEditText().setText(value.getString("tipo"));
                txtCursoP.getEditText().setText(value.getString("curso"));
            }
        });*/

        tipo = Objects.requireNonNull(txtTipoP.getEditText()).getText().toString().trim();


        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnEditar.setVisibility(View.GONE);
                btnAceptar.setVisibility(View.VISIBLE);
                btnCancelar.setVisibility(View.VISIBLE);
                btnEliminar.setVisibility(View.GONE);
                txtContrasenaP.setVisibility(View.VISIBLE);
                txtContrasena2.setVisibility(View.VISIBLE);
                txtCursoP.setEnabled(true);
                if (tipo.equals("Profesor")){
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
                Intent intent = new Intent(Perfil.this, ListaOrdenadores.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correo = Objects.requireNonNull(txtEmail.getEditText()).getText().toString().trim();
                tipo = Objects.requireNonNull(txtTipoP.getEditText()).getText().toString().trim();
                curso = Objects.requireNonNull(txtCursoP.getEditText()).getText().toString();
                contrasena1 = (txtContrasenaP.getEditText()).getText().toString();
                contrasena2 = Objects.requireNonNull(txtContrasena2.getEditText()).getText().toString();
                txtContrasenaP.setError(null);
                txtContrasena2.setError(null);
                txtCursoP.setError(null);
                Boolean insertado;
                if (!curso.isEmpty() && Integer.parseInt(curso) > 0)
                    if (!contrasena1.isEmpty())
                        if (!contrasena2.isEmpty())
                            if (contrasena1.equals(contrasena2)) {
                                insertado = DB.updateUsuario(correo, contrasena1, tipo, Integer.parseInt(curso));
                                if (insertado)
                                    Toast.makeText(getApplicationContext(), "Los datos en la cuenta seleccionada han sido actualizados", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(getApplicationContext(), "Ha habido un problema al actualizar los datos. Inténtelo de nuevo", Toast.LENGTH_SHORT).show();
                            } else {
                                txtContrasenaP.setError("Por favor, compruebe que las contraseñas sean iguales");
                                txtContrasena2.setError("Por favor, compruebe que las contraseñas sean iguales");
                            }
                        else
                            txtContrasena2.setError("Por favor, introduzca una contraseña válida");
                    else
                        txtContrasenaP.setError("Por favor, introduzca una contraseña válida");
                else
                    txtCursoP.setError("Por favor, introduzca el curso y asegúrese de que es superior a 0");

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
                        if (DB.borrarUsuario(correo)) {
                            Toast.makeText(getApplicationContext(), "Su usuario ha sido eliminado correctamente de la base de datos", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Perfil.this, Login.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        } else
                            Toast.makeText(getApplicationContext(), "No se ha podido eliminar al usuario. Inténtelo de nuevo más tarde", Toast.LENGTH_SHORT).show();
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
        Intent intent;
        if (item.getItemId() == R.id.home) {
            intent = new Intent(Perfil.this, Portada.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void recogerUsuario() {
        SQLiteDatabase db = DB.getWritableDatabase();
        Cursor res = db.rawQuery("select * from usuarios where usuario='" + Login.email + "'", null);
        String email = "";
        String tipoU = "";
        int curso = 0;
        while (res.moveToNext()) {
            email = res.getString(0);
            tipoU = res.getString(2);
            curso = res.getInt(3);

        }

        Objects.requireNonNull(txtEmail.getEditText()).setText(email);
        Objects.requireNonNull(txtTipoP.getEditText()).setText(tipoU);
        Objects.requireNonNull(txtCursoP.getEditText()).setText(String.valueOf(curso));

    }
}