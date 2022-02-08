package com.example.proyecto_tommy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Ram extends AppCompatActivity {
    //crear las variables
    ArrayList<Componente> listaComponentes;
    RecyclerView recyclerComponentes;
    Componente cpu;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_componentes);

        setTitle("Memorias RAM");

        //recogemos los valores pasados de las actividades anteriores para poder crear el recibo
        if (getIntent().getExtras() != null) {
            cpu = getIntent().getExtras().getParcelable("cpu");
        }
        //se crea y añaden los componentes al arraylist además se crea el adapter y se establece
        recyclerComponentes = findViewById(R.id.recycler);
        recyclerComponentes.setLayoutManager(new LinearLayoutManager(this));
        AutoCompleteTextView textOrdenar = findViewById(R.id.dropDownOrdenar);
        String[] ordenaciones = getResources().getStringArray(R.array.ordenarPor);
        DB = new DBHelper(this);

        DB.insertarComponentes(23001, R.drawable.ram_corsair, "RAM Corsair Vengeance RGB PRO", "RAM", 89.00, "Tamaño de memoria: 2x8GB\nVelocidad de memoria:3200MHz\nTipo:DDR4 SDRAM\nMarca: Corsair");
        DB.insertarComponentes(23002, R.drawable.ram_tridentz, "RAM GSkill Trident Z RGB", "RAM", 99.00, "Tamaño de memoria: 2x8GB\nVelocidad de memoria:3200MHz\nTipo:DDR4 SDRAM\nMarca: Corsair");
        DB.insertarComponentes(23003, R.drawable.ram_corsair, "RAM Kingston Hyperx Fury Black", "RAM", 87.00, "Tamaño de memoria: 2x8GB\nVelocidad de memoria:2933MHz\nTipo:DDR4 SDRAM\nMarca: Kingston");
        DB.insertarComponentes(23004, R.drawable.ram_corsair_dominator, "RAM Corsair Dominator White RGB", "RAM", 120.00, "Tamaño de memoria: 2x8GB\nVelocidad de memoria:3200MHz\nTipo:DDR4 SDRAM\nMarca: Corsair");

        listaComponentes = DB.getComponentes("RAM");

        //Creamos y establecemos el ArrayAdapter del dropdown con sus valores
        ArrayAdapter<String> arrayAdapterOrdenar = new ArrayAdapter<>(getApplicationContext(), R.layout.item_dropdown, ordenaciones);
        textOrdenar.setAdapter(arrayAdapterOrdenar);

        AdaptadorComponentes adapter = new AdaptadorComponentes(this, listaComponentes);

        /**
         *Este método lo que hace es que al clickar en el dropdown podemos organizar los valores alfabeticamente o por precios
         * */
        textOrdenar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String item = arrayAdapterOrdenar.getItem(position);
                // Toast.makeText(getApplicationContext(), item, Toast.LENGTH_SHORT).show();
                switch (item) {
                    case "Precio ascendente":
                        listaComponentes.sort(Comparator.comparing(Componente::getPrecio));
                        recyclerComponentes.removeAllViews();
                        break;
                    case "Precio descendente":
                        listaComponentes.sort(Comparator.comparing(Componente::getPrecio));
                        Collections.reverse(listaComponentes);
                        recyclerComponentes.removeAllViews();
                        break;
                    case "Nombre A-Z":
                        listaComponentes.sort(Comparator.comparing(Componente::getNombre));
                        recyclerComponentes.removeAllViews();
                        break;
                    case "Nombre Z-A":
                        listaComponentes.sort(Comparator.comparing(Componente::getNombre));
                        Collections.reverse(listaComponentes);
                        recyclerComponentes.removeAllViews();
                        break;
                    default:
                        break;
                }
            }
        });
        /**
         * Al clickar uno de los componentes en la lista se añade al bundle y se envía a la siguiente actividad junto a los componentes que llevemos de otras actividades
         * **/
        adapter.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Ram.this, Gpu.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("cpu", cpu);
                bundle.putParcelable("ram", listaComponentes.get(recyclerComponentes.getChildAdapterPosition(view)));

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
        if (item.getItemId() == R.id.home) {
            intent = new Intent(Ram.this, ListaOrdenadores.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}