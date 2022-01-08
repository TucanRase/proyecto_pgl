package com.example.proyecto_tommy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Recibo extends AppCompatActivity {
    ArrayList<Componente> listaComponentes;
    RecyclerView recyclerRecibo;
    Componente cpu;
    Componente ram;
    Componente gpu;
    Componente psu;
    Componente almacenamiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recibo);

        if (getIntent().getExtras() != null) {
            cpu = getIntent().getExtras().getParcelable("cpu");
            ram = getIntent().getExtras().getParcelable("ram");
            gpu = getIntent().getExtras().getParcelable("gpu");
            psu = getIntent().getExtras().getParcelable("psu");
            almacenamiento = getIntent().getExtras().getParcelable("almacenamiento");
        }

        listaComponentes=new ArrayList<>();
        recyclerRecibo=(RecyclerView) findViewById(R.id.recyclerRecibo);
        recyclerRecibo.setLayoutManager(new LinearLayoutManager(this));

        listaComponentes.add(cpu);
        listaComponentes.add(ram);
        listaComponentes.add(gpu);
        listaComponentes.add(psu);
        listaComponentes.add(almacenamiento);

        AdaptadorRecibo adapter=new AdaptadorRecibo(this,listaComponentes);


        recyclerRecibo.setAdapter(adapter);
    }
}