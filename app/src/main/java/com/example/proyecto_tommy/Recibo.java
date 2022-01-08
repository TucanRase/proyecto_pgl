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

public class Recibo extends AppCompatActivity {
    ArrayList<Componente> listaComponentes;
    RecyclerView recyclerRecibo;
    Componente cpu,ram,gpu,psu,almacenamiento;
    TextView subtotal,igic,total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recibo);

        subtotal=(TextView) findViewById(R.id.recSubtotal);
        igic=(TextView) findViewById(R.id.recIgic);
        total=(TextView) findViewById(R.id.recTotal);

        if (getIntent().getExtras() != null) {
            cpu = getIntent().getExtras().getParcelable("cpu");
            ram = getIntent().getExtras().getParcelable("ram");
            gpu = getIntent().getExtras().getParcelable("gpu");
            psu = getIntent().getExtras().getParcelable("psu");
            almacenamiento = getIntent().getExtras().getParcelable("almacenamiento");
        }

        listaComponentes=new ArrayList<>();
        recyclerRecibo=(RecyclerView) findViewById(R.id.recyclerRecibo);
        recyclerRecibo.setLayoutManager(new LinearLayoutManager(this));

        listaComponentes.add(cpu);
        listaComponentes.add(ram);
        listaComponentes.add(gpu);
        listaComponentes.add(psu);
        listaComponentes.add(almacenamiento);

        AdaptadorRecibo adapter=new AdaptadorRecibo(this,listaComponentes);


        recyclerRecibo.setAdapter(adapter);

        total.setText(String.valueOf(calcularTotal()));
        subtotal.setText(String.valueOf(calcularTotal()*0.93));
        igic.setText(String.valueOf(calcularTotal()*0.07));
    }

    public double calcularTotal(){
        double total=0;
        for(Componente item: listaComponentes){
            total+=item.getPrecio();
        }
        return total;
    }
}