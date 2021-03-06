package com.example.proyecto_tommy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Recibo extends AppCompatActivity {
    //creamos las variables
    ArrayList<Componente> listaComponentes;
    RecyclerView recyclerRecibo;
    Componente cpu, ram, gpu, psu, almacenamiento;
    TextView subtotal, igic, total;
    Button btnComprar;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recibo);

        subtotal = findViewById(R.id.recSubtotal);
        igic = findViewById(R.id.recIgic);
        total = findViewById(R.id.recTotal);
        btnComprar = findViewById(R.id.btnComprar);
        //recogemos los valores pasados de las actividades anteriores para poder crear el recibo
        if (getIntent().getExtras() != null) {
            cpu = getIntent().getExtras().getParcelable("cpu");
            ram = getIntent().getExtras().getParcelable("ram");
            gpu = getIntent().getExtras().getParcelable("gpu");
            psu = getIntent().getExtras().getParcelable("psu");
            almacenamiento = getIntent().getExtras().getParcelable("almacenamiento");
        }

        listaComponentes = new ArrayList<>();
        recyclerRecibo = findViewById(R.id.recyclerRecibo);
        recyclerRecibo.setLayoutManager(new LinearLayoutManager(this));
        //se a??aden los componentes al arraylist
        listaComponentes.add(cpu);
        listaComponentes.add(ram);
        listaComponentes.add(gpu);
        listaComponentes.add(psu);
        listaComponentes.add(almacenamiento);
        //Establecer el adaptador
        AdaptadorRecibo adapter = new AdaptadorRecibo(this, listaComponentes);
        recyclerRecibo.setAdapter(adapter);
        //Cambiar el valor de la tabla adem??s de asegurarnos de que este n??mero solo tenga 2 decimales
        DecimalFormat df = new DecimalFormat("0.00");
        total.setText(df.format(calcularTotal()));
        subtotal.setText(df.format(calcularTotal() * 0.93));
        igic.setText(df.format(calcularTotal() * 0.07));

        /**
         * Establecemos el onclick del bot??n
         */
        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListaOrdenadores.class);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String fechaCompra = sdf.format(new Date());
                database = FirebaseDatabase.getInstance();
                String id =database.getReference().child("ordenadores").push().getKey();
                Ordenador ordenador=new Ordenador(id,fechaCompra, calcularTotal(), cpu.getId(), ram.getId(), gpu.getId(), psu.getId(), almacenamiento.getId(), LoginFirebase.email);
                database.getReference().child("ordenadores").child(id).setValue(ordenador).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "Su ordenador ha sido a??adido a su lista", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Algo ha ido mal en la compra, no se ha podido finalizar. Int??ntelo de nuevo", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    /**
     * Creamos el men??
     **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.volver_inicio, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Establecemos las acciones al pulsar las opciones del men??
     **/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        if (item.getItemId() == R.id.home) {
            intent = new Intent(Recibo.this, Portada.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * M??todo para establecer la animaci??n al pulsar el bot??n "atr??s"
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }

    /**
     * M??todo simple para sumar el precio de todos los componentes dentro del array
     **/
    public double calcularTotal() {
        double total = 0;
        for (Componente item : listaComponentes) {
            total += item.getPrecio();
        }
        return total;
    }
}