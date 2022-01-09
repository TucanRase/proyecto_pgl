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
    //crear las variables
    ArrayList<Componente> listaComponentes;
    RecyclerView recyclerComponentes;
    Componente cpu,ram;
    TextView titulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_componentes);

        titulo=(TextView)findViewById(R.id.componente);
        titulo.setText("Tarjetas gráficas: ");

        //recogemos los valores pasados de las actividades anteriores para poder crear el recibo
        if (getIntent().getExtras() != null) {
            cpu = getIntent().getExtras().getParcelable("cpu");
            ram = getIntent().getExtras().getParcelable("ram");
        }

        //se crea y añaden los componentes al arraylist además se crea el adapter y se establece
        listaComponentes=new ArrayList<>();
        recyclerComponentes=(RecyclerView) findViewById(R.id.recycler);
        recyclerComponentes.setLayoutManager(new LinearLayoutManager(this));

        listaComponentes.add(new Componente(22001,R.drawable.rtx_2060_evga,"Nvidia RTX 2060 EVGA KO","GPU", 525.00,"Memoria 6GB GDDR6 \nNúcleos CUDA: 1920\nVelocidad del reloj de la memoria:14000 MHz"));
        listaComponentes.add(new Componente(22002,R.drawable.rtx_2060_giga,"Nvidia RTX 2060 Gigabyte Windforce","GPU", 600.00,"Memoria 6GB GDDR6 \nNúcleos CUDA: 1920\nVelocidad del reloj de la memoria:1830 MHz"));
        listaComponentes.add(new Componente(22003,R.drawable.rtx_2060_msi,"Nvidia RTX 2060 MSI Gaming Z 6G","GPU", 600.00,"Memoria 6GB GDDR6 \nNúcleos CUDA: 1920\nVelocidad del reloj de la memoria:1830 MHz"));
        listaComponentes.add(new Componente(22004,R.drawable.rtx_2060_asus,"Nvidia RTX 2060 Asus Dual Evo","GPU", 550.00,"Memoria 6GB GDDR6"+" \nNúcleos CUDA: 1920\nVelocidad del reloj de la memoria:1725 MHz"));

        AdaptadorComponentes adapter=new AdaptadorComponentes(this,listaComponentes);

        /**
         * Al clickar uno de los componentes en la lista se añade al bundle y se envía a la siguiente actividad junto a los componentes que llevemos de otras actividades
         * **/
        adapter.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Gpu.this, Psu.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("cpu",cpu);
                bundle.putParcelable("ram",ram);
                bundle.putParcelable("gpu",listaComponentes.get(recyclerComponentes.getChildAdapterPosition(view)));

                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        recyclerComponentes.setAdapter(adapter);
    }
}