package com.example.proyecto_tommy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Psu extends AppCompatActivity {
    //crear las variables
    ArrayList<Componente> listaComponentes;
    RecyclerView recyclerComponentes;
    Componente cpu, ram, gpu;
    TextView titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_componentes);

        titulo = (TextView) findViewById(R.id.componente);
        titulo.setText("Fuentes de\nalimentación: ");

        //recogemos los valores pasados de las actividades anteriores para poder crear el recibo
        if (getIntent().getExtras() != null) {
            cpu = getIntent().getExtras().getParcelable("cpu");
            ram = getIntent().getExtras().getParcelable("ram");
            gpu = getIntent().getExtras().getParcelable("gpu");
        }

        //se crea y añaden los componentes al arraylist además se crea el adapter y se establece
        listaComponentes = new ArrayList<>();
        recyclerComponentes = (RecyclerView) findViewById(R.id.recycler);
        recyclerComponentes.setLayoutManager(new LinearLayoutManager(this));
        AutoCompleteTextView textOrdenar=(AutoCompleteTextView) findViewById(R.id.dropDownOrdenar);
        String[] ordenaciones = getResources().getStringArray(R.array.ordenarPor);

        //Creamos y establecemos el ArrayAdapter del dropdown con sus valores
        ArrayAdapter<String> arrayAdapterOrdenar=new ArrayAdapter<>(getApplicationContext(),R.layout.item_dropdown,ordenaciones);
        textOrdenar.setAdapter(arrayAdapterOrdenar);

        listaComponentes.add(new Componente(28001, R.drawable.psu_corsair_850x, "Corsair RM850X psu", "PSU", 110.00, "Potencia de la fuente:850W\nTipo de cableado: Modular\nEficiencia de la fuente:80+Gold\nMarca: Corsair"));
        listaComponentes.add(new Componente(28002, R.drawable.psu_evga_850, "EVGA 850G+ psu", "PSU", 95.00, "Potencia de la fuente:850W\nTipo de cableado: Modular\nEficiencia de la fuente:80+Gold\nMarca: EVGA"));
        listaComponentes.add(new Componente(28003, R.drawable.psu_rog, "ASUS Rog strix 850G+", "PSU", 120.00, "Potencia de la fuente:850W\nTipo de cableado: Modular\nEficiencia de la fuente:80+Gold\nMarca: Asus Strix"));
        listaComponentes.add(new Componente(28004, R.drawable.psu_evga_850_no, "EVGA 850BQ", "PSU", 70.00, "Potencia de la fuente:850W\nTipo de cableado: No modular\nEficiencia de la fuente:80+Bronze\nMarca: EVGA"));
        AdaptadorComponentes adapter = new AdaptadorComponentes(this, listaComponentes);
        /**
         * Al clickar uno de los componentes en la lista se añade al bundle y se envía a la siguiente actividad junto a los componentes que llevemos de otras actividades
         * **/
        adapter.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Psu.this, Almacenamiento.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("cpu", cpu);
                bundle.putParcelable("ram", ram);
                bundle.putParcelable("gpu", gpu);
                bundle.putParcelable("psu", listaComponentes.get(recyclerComponentes.getChildAdapterPosition(view)));

                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);

            }
        });

        recyclerComponentes.setAdapter(adapter);

    }

    /**
     * Método para establecer la animación al pulsar el botón "atrás"
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }

    /**
     * Creamos el menú
     **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.volver_inicio, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Establecemos las acciones al pulsar las opciones del menú
     **/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.home:
                intent = new Intent(Psu.this, Inicio.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}