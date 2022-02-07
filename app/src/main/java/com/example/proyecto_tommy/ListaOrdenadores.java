package com.example.proyecto_tommy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListaOrdenadores extends AppCompatActivity {
    //crear las variables
    ArrayList<Ordenador> ordenadores;
    RecyclerView recyclerOrdenador;
    TextView placeholder;
    FloatingActionButton fab;
    Intent intent;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ordenadores);

        DB = new DBHelper(this);

        recyclerOrdenador = (RecyclerView) findViewById(R.id.recyclerOrdenador);
        placeholder = (TextView) findViewById(R.id.placeHolder);
        recyclerOrdenador.setLayoutManager(new LinearLayoutManager(this));
        //se añaden los ordenadores al arraylist
        ordenadores = DB.getOrdenadores(Login.email);
        if (ordenadores.size() > 0) {
            //Establecer el adaptador
            AdaptadorOrdenador adapter = new AdaptadorOrdenador(this, ordenadores);
            recyclerOrdenador.setAdapter(adapter);
        } else {
            //establecer aviso de no hay ordenadores
            recyclerOrdenador.setVisibility(View.GONE);
            placeholder.setVisibility(View.VISIBLE);
        }
        // TODO: 13/01/2022 Establecer animación al fab
        fab = (FloatingActionButton) findViewById(R.id.fabAdd);
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
            case R.id.perfil:/*
                intent = new Intent(ListaOrdenadores.this, Perfil.class);
                startActivity(intent);*/
                intent = new Intent(ListaOrdenadores.this, ComponentesPC.class);
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
/*
    public ArrayList<Componente> getComponentesPc() {
        Cursor res = DB.rawQuery("select * from componentes where ID='" + idComponente + "'", null);

        while (res.moveToNext()) {
            int id = res.getInt(0);
            int imagen = res.getInt(1);
            String nombreComponente = res.getString(2);
            String tipo = res.getString(3);
            double precio = res.getDouble(4);
            String caracteristicas = res.getString(5);


            Componente nuevoComponente = new Componente(id, imagen, nombreComponente, tipo, precio, caracteristicas);
            listaComponentes.add(nuevoComponente);
        }
        return listaComponentes;

    }
*/
}