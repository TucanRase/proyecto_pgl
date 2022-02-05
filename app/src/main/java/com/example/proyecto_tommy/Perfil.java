package com.example.proyecto_tommy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class Perfil extends AppCompatActivity {
    DBHelper DB;
    Button btnCancelar, btnAceptar, btnEditar, btnEliminar, btnVolver;
    TextInputLayout txtEmail, txtContrasenaP, txtContrasena2, txtTipoP, txtCursoP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        DB = new DBHelper(this);

        //Inicializamos los botones y los inputs
        btnAceptar = (Button) findViewById(R.id.btnAceptar);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnEditar = (Button) findViewById(R.id.btnEditar);
        btnEliminar = (Button) findViewById(R.id.btnEliminar);
        btnVolver = (Button) findViewById(R.id.btnVolver);
        txtEmail = (TextInputLayout) findViewById(R.id.txtEmail);
        txtContrasenaP = (TextInputLayout) findViewById(R.id.txtContrasenaP);
        txtContrasena2 = (TextInputLayout) findViewById(R.id.txtContrasena2);
        txtTipoP = (TextInputLayout) findViewById(R.id.txtTipoP);
        txtCursoP = (TextInputLayout) findViewById(R.id.txtCursoP);

        recogerUsuario();

        String correo = txtEmail.getEditText().getText().toString().trim();
        String tipo = txtTipoP.getEditText().getText().toString().trim();
        String curso = txtCursoP.getEditText().getText().toString();
        String contrasena1 = txtContrasenaP.getEditText().getText().toString();
        String contrasena2 = txtContrasena2.getEditText().getText().toString();

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
                txtContrasenaP.setError(null);
                txtContrasena2.setError(null);
                txtCursoP.setError(null);
                Boolean insertado;
                if (!curso.isEmpty() && Integer.valueOf(curso) > 0)
                    if (!contrasena1.isEmpty())
                        if (!contrasena2.isEmpty())
                            if (contrasena1.equals(contrasena2)) {
                                insertado = DB.updateUsuario(correo, contrasena1, tipo, Integer.valueOf(curso));
                                if (insertado)
                                    Toast.makeText(getApplicationContext(), "Sus datos han sido actualizados correctamente", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void recogerUsuario() {
        SQLiteDatabase db = DB.getWritableDatabase();
        Cursor res = db.rawQuery("select * from usuarios where usuario='" + Login.email + "'", null);
        String email = "";
        String contrasena = "";
        String tipo = "";
        int curso = 0;
        while (res.moveToNext()) {
            email = res.getString(0);
            contrasena = res.getString(1);
            tipo = res.getString(2);
            curso = res.getInt(3);

        }

        txtEmail.getEditText().setText(email);
        txtTipoP.getEditText().setText(tipo);
        txtCursoP.getEditText().setText(String.valueOf(curso));

    }
}