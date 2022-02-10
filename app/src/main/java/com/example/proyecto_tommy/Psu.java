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

public class Psu extends AppCompatActivity {
    //crear las variables
    ArrayList<Componente> listaComponentes;
    RecyclerView recyclerComponentes;
    Componente cpu, ram, gpu;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_componentes);

        setTitle("Fuentes de alimentación");

        //recogemos los valores pasados de las actividades anteriores para poder crear el recibo
        if (getIntent().getExtras() != null) {
            cpu = getIntent().getExtras().getParcelable("cpu");
            ram = getIntent().getExtras().getParcelable("ram");
            gpu = getIntent().getExtras().getParcelable("gpu");
        }

        //se crea y añaden los componentes al arraylist además se crea el adapter y se establece
        listaComponentes = new ArrayList<>();
        recyclerComponentes = findViewById(R.id.recycler);
        recyclerComponentes.setLayoutManager(new LinearLayoutManager(this));
        AutoCompleteTextView textOrdenar = findViewById(R.id.dropDownOrdenar);
        String[] ordenaciones = getResources().getStringArray(R.array.ordenarPor);
        DB = new DBHelper(this);

        DB.insertarComponentes(28001, R.drawable.psu_corsair_850x, "Corsair RM850X psu", "PSU", 110.00, "Potencia de la fuente:850W\nTipo de cableado: Modular\nEficiencia de la fuente:80+Gold\nMarca: Corsair");
        DB.insertarComponentes(28002, R.drawable.psu_evga_850, "EVGA 850G+ psu", "PSU", 95.00, "Potencia de la fuente:850W\nTipo de cableado: Modular\nEficiencia de la fuente:80+Gold\nMarca: EVGA");
        DB.insertarComponentes(28003, R.drawable.psu_rog, "ASUS Rog strix 850G+", "PSU", 120.00, "Potencia de la fuente:850W\nTipo de cableado: Modular\nEficiencia de la fuente:80+Gold\nMarca: Asus Strix");
        DB.insertarComponentes(28004, R.drawable.psu_evga_850_no, "EVGA 850BQ", "PSU", 70.00, "Potencia de la fuente:850W\nTipo de cableado: No modular\nEficiencia de la fuente:80+Bronze\nMarca: EVGA");
        listaComponentes = DB.getComponentes("PSU");

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
                        adapter.notifyDataSetChanged();
                        break;
                    case "Precio descendente":
                        listaComponentes.sort(Comparator.comparing(Componente::getPrecio));
                        Collections.reverse(listaComponentes);
                        adapter.notifyDataSetChanged();
                        break;
                    case "Nombre A-Z":
                        listaComponentes.sort(Comparator.comparing(Componente::getNombre));
                        adapter.notifyDataSetChanged();
                        break;
                    case "Nombre Z-A":
                        listaComponentes.sort(Comparator.comparing(Componente::getNombre));
                        Collections.reverse(listaComponentes);
                        adapter.notifyDataSetChanged();
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
        if (item.getItemId() == R.id.home) {
            intent = new Intent(Psu.this, Portada.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}