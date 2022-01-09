package com.example.proyecto_tommy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Inicio extends AppCompatActivity {
    //crear las variables
    ArrayList<Ordenador> listaOrdenadores;
    RecyclerView recyclerOrdenador;
    Ordenador ordenador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        //recogemos los valores pasados de las actividades anteriores para poder crear el recibo
        if (getIntent().getExtras() != null) {
            ordenador = getIntent().getExtras().getParcelable("pc");
        }

        listaOrdenadores=new ArrayList<>();
        recyclerOrdenador=(RecyclerView) findViewById(R.id.recyclerOrdenador);
        recyclerOrdenador.setLayoutManager(new LinearLayoutManager(this));
        //se añade el ordenador al arraylist
        listaOrdenadores.add(ordenador);
        //Establecer el adaptador
        AdaptadorOrdenador adapter=new AdaptadorOrdenador(this,listaOrdenadores);
        recyclerOrdenador.setAdapter(adapter);
    }
    /**Creamos el menú**/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opciones_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    /**Establecemos las acciones al pulsar las opciones del menú**/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.cerrarSesion:
                intent =new Intent(Inicio.this, Login.class);
                startActivity(intent);
                return true;
            case R.id.acerca:
                intent =new Intent(Inicio.this, Acerca.class);
                startActivity(intent);
                return true;
            case R.id.add:
                intent =new Intent(Inicio.this, Cpu.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}