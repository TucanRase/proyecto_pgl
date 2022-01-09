package com.example.proyecto_tommy;

import static java.lang.Math.round;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Recibo extends AppCompatActivity {
    //creamos las variables
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
        //recogemos los valores pasados de las actividades anteriores para poder crear el recibo
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
        //se añaden los componentes al arraylist
        listaComponentes.add(cpu);
        listaComponentes.add(ram);
        listaComponentes.add(gpu);
        listaComponentes.add(psu);
        listaComponentes.add(almacenamiento);
        //Establecer el adaptador
        AdaptadorRecibo adapter=new AdaptadorRecibo(this,listaComponentes);
        recyclerRecibo.setAdapter(adapter);
        //Cambiar el valor de la tabla además de asegurarnos de que este número solo tenga 2 decimales
        DecimalFormat df = new DecimalFormat("0.00");
        total.setText(df.format(calcularTotal()));
        subtotal.setText(df.format(calcularTotal()*0.93));
        igic.setText(df.format(calcularTotal()*0.07));
    }
    /**
     * Método simple para sumar el precio de todos los componentes dentro del array**/
    public double calcularTotal(){
        double total=0;
        for(Componente item: listaComponentes){
            total+=item.getPrecio();
        }
        return total;
    }
}