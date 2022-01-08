package com.example.proyecto_tommy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class Cpu extends AppCompatActivity {
    ArrayList<Componente> listaComponentes;
    RecyclerView recyclerComponentes;
    TextView titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_componentes);

        titulo=(TextView)findViewById(R.id.componente);
        titulo.setText("Procesadores: ");

        listaComponentes=new ArrayList<>();
        recyclerComponentes=(RecyclerView) findViewById(R.id.recycler);
        recyclerComponentes.setLayoutManager(new LinearLayoutManager(this));

        listaComponentes.add(new Componente(1,R.drawable.ryzen_5600,"cpu1","cpu", 10.00,"cabeas"));
        listaComponentes.add(new Componente(2,R.drawable.ryzen_5600,"cpu2","cpu", 150.00,"cabeas"));
        listaComponentes.add(new Componente(3,R.drawable.ryzen_5600,"cpu3","cpu", 420.00,"cabeas"));
        listaComponentes.add(new Componente(4,R.drawable.ryzen_5600,"cpu4","cpu", 15.00,"cabeas"));
        AdaptadorComponentes adapter=new AdaptadorComponentes(this,listaComponentes);

        adapter.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Cpu.this,Ram.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("cpu", listaComponentes.get(recyclerComponentes.getChildAdapterPosition(view)));

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        recyclerComponentes.setAdapter(adapter);
    }
}