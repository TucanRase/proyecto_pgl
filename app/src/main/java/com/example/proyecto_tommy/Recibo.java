package com.example.proyecto_tommy;

import static java.lang.Math.round;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class Recibo extends AppCompatActivity {
    //creamos las variables
    ArrayList<Componente> listaComponentes;
    RecyclerView recyclerRecibo;
    Componente cpu, ram, gpu, psu, almacenamiento;
    TextView subtotal, igic, total;
    Button btnComprar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recibo);

        subtotal = (TextView) findViewById(R.id.recSubtotal);
        igic = (TextView) findViewById(R.id.recIgic);
        total = (TextView) findViewById(R.id.recTotal);
        btnComprar = (Button) findViewById(R.id.btnComprar);
        //recogemos los valores pasados de las actividades anteriores para poder crear el recibo
        if (getIntent().getExtras() != null) {
            cpu = getIntent().getExtras().getParcelable("cpu");
            ram = getIntent().getExtras().getParcelable("ram");
            gpu = getIntent().getExtras().getParcelable("gpu");
            psu = getIntent().getExtras().getParcelable("psu");
            almacenamiento = getIntent().getExtras().getParcelable("almacenamiento");
        }

        listaComponentes = new ArrayList<>();
        recyclerRecibo = (RecyclerView) findViewById(R.id.recyclerRecibo);
        recyclerRecibo.setLayoutManager(new LinearLayoutManager(this));
        //se añaden los componentes al arraylist
        listaComponentes.add(cpu);
        listaComponentes.add(ram);
        listaComponentes.add(gpu);
        listaComponentes.add(psu);
        listaComponentes.add(almacenamiento);
        //Establecer el adaptador
        AdaptadorRecibo adapter = new AdaptadorRecibo(this, listaComponentes);
        recyclerRecibo.setAdapter(adapter);
        //Cambiar el valor de la tabla además de asegurarnos de que este número solo tenga 2 decimales
        DecimalFormat df = new DecimalFormat("0.00");
        total.setText(df.format(calcularTotal()));
        subtotal.setText(df.format(calcularTotal() * 0.93));
        igic.setText(df.format(calcularTotal() * 0.07));

        /**
         * Establecemos el onclick del botón
         */
        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Inicio.class);
                Toast.makeText(getApplicationContext(), "Su ordenador ha sido añadido a su lista", Toast.LENGTH_SHORT).show();
                //cuando esté conectada a la base de datos se comprobará que no esté ya establecido el id del ordenador
                Random random = new Random();
                int y = random.nextInt(10000);
                Ordenador pc = new Ordenador(String.valueOf(y), cpu, ram, gpu, psu, almacenamiento);
                Bundle bundle = new Bundle();
                bundle.putParcelable("pc", pc);

                System.out.println(String.valueOf(y));
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    /**
     * Método para establecer la animación al pulsar el botón "atrás"
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    /**
     * Método simple para sumar el precio de todos los componentes dentro del array
     **/
    public double calcularTotal() {
        double total = 0;
        for (Componente item : listaComponentes) {
            total += item.getPrecio();
        }
        return total;
    }
}