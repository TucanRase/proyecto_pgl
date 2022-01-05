package com.example.proyecto_tommy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class Ram extends AppCompatActivity {
    ArrayList<Componente> listaComponentes;
    RecyclerView recyclerComponentes;
    Componente cpu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_componentes);

        if (getIntent().getExtras() != null) {
            cpu = getIntent().getExtras().getParcelable("cpu");
        }

        listaComponentes=new ArrayList<>();
        recyclerComponentes=(RecyclerView) findViewById(R.id.recycler);
        recyclerComponentes.setLayoutManager(new LinearLayoutManager(this));

        listaComponentes.add(new Componente(1,R.drawable.ram_corsair,"ram1","ram", 10.00,"cabeas"));
        listaComponentes.add(new Componente(2,R.drawable.ram_corsair,"ram2","ram", 150.00,"cabeas"));
        listaComponentes.add(new Componente(3,R.drawable.ram_corsair,"ram3","ram", 420.00,"cabeas"));
        listaComponentes.add(new Componente(4,R.drawable.ram_corsair,"ram4","ram", 15.00,"cabeas"));
        AdaptadorComponentes adapter=new AdaptadorComponentes(this,listaComponentes);

        adapter.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creamos el intent para la proxima actividad, acontinuación el bundle para guardar el objeto y por ultimo lo añadimos para poder enviarlo
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
}