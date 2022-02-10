package com.example.proyecto_tommy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListaOrdenadores extends AppCompatActivity {
    //crear las variables
    ArrayList<Ordenador> ordenadores;
    RecyclerView recyclerOrdenador;
    AdaptadorOrdenador adapter;
    TextView placeholder;
    FloatingActionButton fab;
    Intent intent;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ordenadores);

        setTitle("Mis ordenadores");

        DB = new DBHelper(this);

        recyclerOrdenador = findViewById(R.id.recyclerOrdenador);
        placeholder = findViewById(R.id.placeHolder);
        recyclerOrdenador.setLayoutManager(new LinearLayoutManager(this));
        //se añaden los ordenadores al arraylist
        ordenadores = DB.getOrdenadores(Login.email);
        if (ordenadores.size() > 0) {
            //Establecer el adaptador
            adapter = new AdaptadorOrdenador(this, ordenadores);
            recyclerOrdenador.setAdapter(adapter);
            //Establecer los escuchadores para onclick y longclick
            adapter.setOnItemClickListener(new AdaptadorOrdenador.onClickListner() {
                @Override
                public void onItemClick(int position, View v) {
                    Intent i = new Intent(getApplicationContext(), ComponentesPC.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("pc", ordenadores.get(position));
                    i.putExtras(bundle);
                    startActivity(i);
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
                }

                @Override
                public void onItemLongClick(int position, View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(ListaOrdenadores.this);
                    alert.setTitle("Eliminar ordenador");
                    alert.setMessage("¿Está seguro de querer eliminar el ordenador con el ID: " + ordenadores.get(position).getId() + "?");
                    alert.setPositiveButton("Sí", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (DB.borrarPc(ordenadores.get(position).getId())) {
                                System.out.println(ordenadores.size());
                                ordenadores.remove(position);
                                Toast.makeText(ListaOrdenadores.this, "El ordenador se ha eliminado de su lista de ordenadores.", Toast.LENGTH_SHORT).show();
                                adapter.notifyItemRemoved(position);
                            } else
                                Toast.makeText(ListaOrdenadores.this, "No se ha podido eliminar el ordenador de su lista.Lo sentimos, inténtelo de nuevo más tarde.", Toast.LENGTH_SHORT).show();

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
        } else {
            //establecer aviso de no hay ordenadores
            recyclerOrdenador.setVisibility(View.GONE);
            placeholder.setVisibility(View.VISIBLE);
        }
        // TODO: 13/01/2022 Establecer animación al fab
        fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(ListaOrdenadores.this, Cpu.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }


    /**
     * Creamos el menú
     **/

    // TODO: 13/01/2022 Cambiar el tooltip del menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opciones_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Establecemos las acciones al pulsar las opciones del menú
     **/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cerrarSesion:
                intent = new Intent(ListaOrdenadores.this, Login.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
            case R.id.acerca:
                intent = new Intent(ListaOrdenadores.this, Acerca.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
            case R.id.add:
                intent = new Intent(ListaOrdenadores.this, Cpu.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
            case R.id.perfil:
                intent = new Intent(ListaOrdenadores.this, Perfil.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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