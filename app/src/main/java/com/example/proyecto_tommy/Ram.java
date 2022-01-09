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
import android.widget.Toast;

import java.util.ArrayList;

public class Ram extends AppCompatActivity {
    //crear las variables
    ArrayList<Componente> listaComponentes;
    RecyclerView recyclerComponentes;
    Componente cpu;
    TextView titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_componentes);

        titulo=(TextView)findViewById(R.id.componente);
        titulo.setText("Memoria RAM: ");
        //recogemos los valores pasados de las actividades anteriores para poder crear el recibo
        if (getIntent().getExtras() != null) {
            cpu = getIntent().getExtras().getParcelable("cpu");
        }
        //se crea y añaden los componentes al arraylist además se crea el adapter y se establece
        listaComponentes=new ArrayList<>();
        recyclerComponentes=(RecyclerView) findViewById(R.id.recycler);
        recyclerComponentes.setLayoutManager(new LinearLayoutManager(this));

        listaComponentes.add(new Componente(23001,R.drawable.ram_corsair,"RAM Corsair Vengeance RGB PRO","RAM", 89.00,"Tamaño de memoria: 2x8GB\nVelocidad de memoria:3200MHz\nTipo:DDR4 SDRAM\nMarca: Corsair"));
        listaComponentes.add(new Componente(23002,R.drawable.ram_tridentz,"RAM GSkill Trident Z RGB","RAM", 99.00,"Tamaño de memoria: 2x8GB\nVelocidad de memoria:3200MHz\nTipo:DDR4 SDRAM\nMarca: Corsair"));
        listaComponentes.add(new Componente(23003,R.drawable.ram_corsair,"RAM Kingston Hyperx Fury Black","RAM", 87.00,"Tamaño de memoria: 2x8GB\nVelocidad de memoria:2933MHz\nTipo:DDR4 SDRAM\nMarca: Kingston"));
        listaComponentes.add(new Componente(23004,R.drawable.ram_corsair_dominator,"RAM Corsair Dominator White RGB","RAM", 120.00,"Tamaño de memoria: 2x8GB\nVelocidad de memoria:3200MHz\nTipo:DDR4 SDRAM\nMarca: Corsair"));

        AdaptadorComponentes adapter=new AdaptadorComponentes(this,listaComponentes);
        /**
         * Al clickar uno de los componentes en la lista se añade al bundle y se envía a la siguiente actividad junto a los componentes que llevemos de otras actividades
         * **/
        adapter.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Ram.this, Gpu.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("cpu",cpu);
                bundle.putParcelable("ram",listaComponentes.get(recyclerComponentes.getChildAdapterPosition(view)));

                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        recyclerComponentes.setAdapter(adapter);
    }
    /**Creamos el menú**/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.volver_inicio,menu);
        return super.onCreateOptionsMenu(menu);
    }
    /**Establecemos las acciones al pulsar las opciones del menú**/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.home:
                intent =new Intent(Ram.this, Inicio.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}