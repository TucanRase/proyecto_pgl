package com.example.proyecto_tommy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class Inicio extends AppCompatActivity {
    ArrayList<Componente> listaComponentes;
    RecyclerView recyclerComponentes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        listaComponentes=new ArrayList<>();
        recyclerComponentes=(RecyclerView) findViewById(R.id.recylcer);
        recyclerComponentes.setLayoutManager(new LinearLayoutManager(this));

        listaComponentes.add(new Componente(1,R.drawable.mag_b550m,"dsadasd","dsadsa", 10.00,"cabeas"));
        listaComponentes.add(new Componente(2,R.drawable.mag_b550m,"dsadasd","dsadsa", 150.00,"cabeas"));
        listaComponentes.add(new Componente(3,R.drawable.mag_b550m,"dsadasd","dsadsa", 420.00,"cabeas"));
        listaComponentes.add(new Componente(4,R.drawable.mag_b550m,"dsadagfdgfdgfdgdfgfdgfdgfdsd","dsafads", 15.00,"cabeas"));

        System.out.println(listaComponentes);
        AdaptadorComponentes adapter=new AdaptadorComponentes(this,listaComponentes);

        adapter.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"seleccion: "+listaComponentes.get(recyclerComponentes.getChildAdapterPosition(view)).getId(),Toast.LENGTH_SHORT).show();
            }
        });

        recyclerComponentes.setAdapter(adapter);
    }
}