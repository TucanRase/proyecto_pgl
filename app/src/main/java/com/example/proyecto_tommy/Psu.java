package com.example.proyecto_tommy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class Psu extends AppCompatActivity {
    ArrayList<Componente> listaComponentes;
    RecyclerView recyclerComponentes;
    Componente cpu;
    Componente ram;
    Componente gpu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_componentes);
        if (getIntent().getExtras() != null) {
            cpu = getIntent().getExtras().getParcelable("cpu");
            ram = getIntent().getExtras().getParcelable("ram");
            gpu = getIntent().getExtras().getParcelable("pgu");
        }

        listaComponentes=new ArrayList<>();
        recyclerComponentes=(RecyclerView) findViewById(R.id.recycler);
        recyclerComponentes.setLayoutManager(new LinearLayoutManager(this));

        listaComponentes.add(new Componente(1,R.drawable.psu_750_evga,"psu1","psu", 10.00,"cabeas"));
        listaComponentes.add(new Componente(2,R.drawable.psu_750_evga,"psu2","psu", 150.00,"cabeas"));
        listaComponentes.add(new Componente(3,R.drawable.psu_750_evga,"psu3","psu", 420.00,"cabeas"));
        listaComponentes.add(new Componente(4,R.drawable.psu_750_evga,"psu4","psu", 15.00,"cabeas"));
        AdaptadorComponentes adapter=new AdaptadorComponentes(this,listaComponentes);

        adapter.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creamos el intent para la proxima actividad, acontinuación el bundle para guardar el objeto y por ultimo lo añadimos para poder enviarlo
                Intent intent =new Intent(Psu.this, Almacenamiento.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("cpu",cpu);
                bundle.putParcelable("ram",ram);
                bundle.putParcelable("gpu",gpu);
                bundle.putParcelable("psu",listaComponentes.get(recyclerComponentes.getChildAdapterPosition(view)));

                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        recyclerComponentes.setAdapter(adapter);
    }
}