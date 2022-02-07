package com.example.proyecto_tommy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class ComponentesPC extends AppCompatActivity {
    RecyclerView recycler;
    ArrayList<Componente> listaComponentes=new ArrayList<>();
    DBHelper DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_componentes_pc);

        DB=new DBHelper(this);

        Ordenador pc1=new Ordenador(1,"2022-02-07",1189.0,25002,23001,22002,28003,26001,"a@a.es");


        listaComponentes=DB.getComponentesPc("1");
        recycler=(RecyclerView) findViewById(R.id.recyclerComponentes);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        AdaptadorComponentes adapter = new AdaptadorComponentes(this, listaComponentes);
        recycler.setAdapter(adapter);

    }

}
