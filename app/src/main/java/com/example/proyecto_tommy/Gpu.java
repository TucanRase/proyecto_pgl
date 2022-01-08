package com.example.proyecto_tommy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class Gpu extends AppCompatActivity {
    ArrayList<Componente> listaComponentes;
    RecyclerView recyclerComponentes;
    Componente cpu;
    Componente ram;
    TextView titulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_componentes);

        titulo=(TextView)findViewById(R.id.componente);
        titulo.setText("Tarjetas gráficas: ");

        if (getIntent().getExtras() != null) {
            cpu = getIntent().getExtras().getParcelable("cpu");
            ram = getIntent().getExtras().getParcelable("ram");
        }

        listaComponentes=new ArrayList<>();
        recyclerComponentes=(RecyclerView) findViewById(R.id.recycler);
        recyclerComponentes.setLayoutManager(new LinearLayoutManager(this));

        listaComponentes.add(new Componente(281624,R.drawable.rtx_2060_evga,"Nvidia RTX 2060 EVGA KO","GPU", 525.00,"Memoria 6GB GDDR6 \nNúcleos CUDA: 1920\nVelocidad del reloj de la memoria:14000 MHz"));
        listaComponentes.add(new Componente(281624,R.drawable.rtx_2060_giga,"Nvidia RTX 2060 Gigabyte Windforce","GPU", 600.00,"Memoria 6GB GDDR6 \nNúcleos CUDA: 1920\nVelocidad del reloj de la memoria:1830 MHz"));
        listaComponentes.add(new Componente(281624,R.drawable.rtx_2060_msi,"Nvidia RTX 2060 MSI Gaming Z 6G","GPU", 600.00,"Memoria 6GB GDDR6 \nNúcleos CUDA: 1920\nVelocidad del reloj de la memoria:1830 MHz"));
        listaComponentes.add(new Componente(281624,R.drawable.rtx_2060_asus,"Nvidia RTX 2060 Asus Dual Evo","GPU", 550.00,"Memoria 6GB GDDR6"+" \nNúcleos CUDA: 1920\nVelocidad del reloj de la memoria:1725 MHz"));

        AdaptadorComponentes adapter=new AdaptadorComponentes(this,listaComponentes);

        adapter.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creamos el intent para la proxima actividad, a continuación el bundle para guardar el objeto y por ultimo lo añadimos para poder enviarlo
                Intent intent =new Intent(Gpu.this, Psu.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("cpu",cpu);
                bundle.putParcelable("ram",ram);
                bundle.putParcelable("gpu",listaComponentes.get(recyclerComponentes.getChildAdapterPosition(view)));

                //Toast.makeText(Gpu.this, cpu.getNombre(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(Gpu.this, ram.getNombre(), Toast.LENGTH_SHORT).show();

                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        recyclerComponentes.setAdapter(adapter);
    }
}