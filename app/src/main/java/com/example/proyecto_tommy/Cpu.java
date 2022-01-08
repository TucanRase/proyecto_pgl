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

        listaComponentes.add(new Componente(25001,R.drawable.ryzen_3600,"AMD Ryzen 5 3600","CPU", 225.00,"Velocidad del procesador: 3.6 GHz \nVelocidad máx procesador: 4.2 GHz \nNúmero de nucleos: 6\nNúmero de hilos:12"));
        listaComponentes.add(new Componente(25002,R.drawable.ryzen_5600,"AMD Ryzen 5 5600X","CPU", 295.00,"Velocidad del procesador: 3.7 GHz \nVelocidad máx procesador: 4.4 GHz\nNúmero de nucleos: 6\nNúmero de hilos:12"));
        listaComponentes.add(new Componente(25003,R.drawable.ryzen_3700x,"AMD Ryzen 7 3700X","CPU", 320.00,"Velocidad del procesador: 3.7 GHz \nVelocidad máx procesador: 4.4 GHz\nNúmero de nucleos: 8\nNúmero de hilos:16"));
        listaComponentes.add(new Componente(25004,R.drawable.ryzen_5700g,"AMD Ryzen 5 5700G","CPU", 340.00,"Velocidad del procesador: 3.8 GHz \nVelocidad máx procesador: 4.6 GHz\nNúmero de nucleos: 8\nNúmero de hilos:16"));
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