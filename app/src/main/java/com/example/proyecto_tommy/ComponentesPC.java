package com.example.proyecto_tommy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class ComponentesPC extends AppCompatActivity {
    RecyclerView recycler;
    ArrayList<Componente> listaComponentes = new ArrayList<>();
    DBHelper DB;
    Ordenador pc1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_componentes_pc);

        if (getIntent().getExtras() != null) {
            pc1 = getIntent().getExtras().getParcelable("pc");
        }

        setTitle("Componentes del ordenador " + pc1.getId());

        DB = new DBHelper(this);

        listaComponentes = DB.getComponentesPc("1");
        recycler = findViewById(R.id.recyclerComponentes);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        AdaptadorComponentes adapter = new AdaptadorComponentes(this, listaComponentes);
        recycler.setAdapter(adapter);

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
            intent = new Intent(ComponentesPC.this, ListaOrdenadores.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
