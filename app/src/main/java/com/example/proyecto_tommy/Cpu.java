package com.example.proyecto_tommy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class Cpu extends AppCompatActivity {
    ArrayList<Componente> listaComponentes;
    RecyclerView recyclerComponentes;
    Componente placa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_componentes);
        if (getIntent().getExtras() != null) {
            placa = getIntent().getExtras().getParcelable("placa");
        }


        listaComponentes=new ArrayList<>();
        recyclerComponentes=(RecyclerView) findViewById(R.id.recycler);
        recyclerComponentes.setLayoutManager(new LinearLayoutManager(this));

        listaComponentes.add(new Componente(1,R.drawable.rtx_2060,"primero","1", 10.00,"cabeas"));
        listaComponentes.add(new Componente(2,R.drawable.rtx_2060,"segundo","1", 150.00,"cabeas"));
        listaComponentes.add(new Componente(3,R.drawable.rtx_2060,"penultimo","1", 420.00,"cabeas"));
        listaComponentes.add(new Componente(4,R.drawable.rtx_2060,"ultimo","2", 15.00,"cabeas"));
        AdaptadorComponentes adapter=new AdaptadorComponentes(this,listaComponentes);

        adapter.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Cpu.this,Prueba.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("placa", placa);
                bundle.putParcelable("gpu", listaComponentes.get(recyclerComponentes.getChildAdapterPosition(view)));

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        recyclerComponentes.setAdapter(adapter);
    }
}