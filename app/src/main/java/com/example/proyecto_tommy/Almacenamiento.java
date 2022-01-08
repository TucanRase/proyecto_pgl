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

public class Almacenamiento extends AppCompatActivity {
    ArrayList<Componente> listaComponentes;
    RecyclerView recyclerComponentes;
    Componente cpu;
    Componente ram;
    Componente gpu;
    Componente psu;
    TextView titulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_componentes);

        titulo=(TextView)findViewById(R.id.componente);
        titulo.setText("Sistema de almacenamiento: ");

        if (getIntent().getExtras() != null) {
            cpu = getIntent().getExtras().getParcelable("cpu");
            ram = getIntent().getExtras().getParcelable("ram");
            gpu = getIntent().getExtras().getParcelable("gpu");
            psu = getIntent().getExtras().getParcelable("psu");
        }

        listaComponentes=new ArrayList<>();
        recyclerComponentes=(RecyclerView) findViewById(R.id.recycler);
        recyclerComponentes.setLayoutManager(new LinearLayoutManager(this));

        listaComponentes.add(new Componente(1,R.drawable.ssd_nvme_1tb,"almacenamiento1","almacenamiento", 10.00,"cabeas"));
        listaComponentes.add(new Componente(2,R.drawable.ssd_nvme_1tb,"almacenamiento2","almacenamiento", 150.00,"cabeas"));
        listaComponentes.add(new Componente(3,R.drawable.ssd_nvme_1tb,"almacenamiento3","almacenamiento", 420.00,"cabeas"));
        listaComponentes.add(new Componente(4,R.drawable.ssd_nvme_1tb,"almacenamiento4","almacenamiento", 15.00,"cabeas"));
        AdaptadorComponentes adapter=new AdaptadorComponentes(this,listaComponentes);

        adapter.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creamos el intent para la proxima actividad, acontinuación el bundle para guardar el objeto y por ultimo lo añadimos para poder enviarlo
                Intent intent =new Intent(Almacenamiento.this, Recibo.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("cpu",cpu);
                bundle.putParcelable("ram",ram);
                bundle.putParcelable("gpu",gpu);
                bundle.putParcelable("psu",psu);
                bundle.putParcelable("almacenamiento",listaComponentes.get(recyclerComponentes.getChildAdapterPosition(view)));

               /* Toast.makeText(Almacenamiento.this, cpu.getNombre(), Toast.LENGTH_SHORT).show();
                Toast.makeText(Almacenamiento.this, ram.getNombre(), Toast.LENGTH_SHORT).show();
                Toast.makeText(Almacenamiento.this, gpu.getNombre(), Toast.LENGTH_SHORT).show();
                Toast.makeText(Almacenamiento.this, psu.getNombre(), Toast.LENGTH_SHORT).show();
                Toast.makeText(Almacenamiento.this, listaComponentes.get(recyclerComponentes.getChildAdapterPosition(view)).getNombre(), Toast.LENGTH_SHORT).show();*/

                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        recyclerComponentes.setAdapter(adapter);
    }
}