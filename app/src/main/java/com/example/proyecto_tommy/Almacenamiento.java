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
    Componente cpu,ram,gpu,psu;
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

        listaComponentes.add(new Componente(26001,R.drawable.ssd_wb_blue,"Western Digital SSD 1tb","Almacenamiento", 85.00,"Tipo de disco duro: SSD\nCapacidad: 1Tb\nVelocidad de lectura:560mb/s\nVelociad de escritura: 530mb/s"));
        listaComponentes.add(new Componente(26002,R.drawable.ssd_wd_m2,"Western Digital SSD M.2 1tb ","Almacenamiento", 100.00,"Tipo de disco duro: SSD\nCapacidad: 1Tb\nVelocidad de lectura:2000mb/s\nVelociad de escritura: 1700mb/s"));
        listaComponentes.add(new Componente(26003,R.drawable.ssd_samsung,"Samsung 970 EVO Plus","Almacenamiento", 150.00,"Tipo de disco duro: SSD NVME\nCapacidad: 1Tb\nVelocidad de lectura:3500mb/s\nVelociad de escritura: 3200mb/s"));
        listaComponentes.add(new Componente(26004,R.drawable.ssd_aorus,"Gigabyte AORUS 7000s","Almacenamiento", 190.00,"Tipo de disco duro: SSD NVME\nCapacidad: 1Tb\nVelocidad de lectura:7000mb/s\nVelociad de escritura: 5500mb/s"));

        AdaptadorComponentes adapter=new AdaptadorComponentes(this,listaComponentes);

        /**
         * Al clickar uno de los componentes en la lista se añade al bundle y se envía a la siguiente actividad junto a los componentes que llevemos de otras actividades
         * **/
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

                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        recyclerComponentes.setAdapter(adapter);
    }
}