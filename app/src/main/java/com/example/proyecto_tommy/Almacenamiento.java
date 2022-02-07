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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Almacenamiento extends AppCompatActivity {
    ArrayList<Componente> listaComponentes;
    RecyclerView recyclerComponentes;
    Componente cpu, ram, gpu, psu;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_componentes);

        setTitle("Almacenamiento");

        if (getIntent().getExtras() != null) {
            cpu = getIntent().getExtras().getParcelable("cpu");
            ram = getIntent().getExtras().getParcelable("ram");
            gpu = getIntent().getExtras().getParcelable("gpu");
            psu = getIntent().getExtras().getParcelable("psu");
        }

        DB=new DBHelper(this);

        listaComponentes = new ArrayList<>();
        recyclerComponentes = (RecyclerView) findViewById(R.id.recycler);
        recyclerComponentes.setLayoutManager(new LinearLayoutManager(this));
        AutoCompleteTextView textOrdenar = (AutoCompleteTextView) findViewById(R.id.dropDownOrdenar);
        String[] ordenaciones = getResources().getStringArray(R.array.ordenarPor);

        //Creamos y establecemos el ArrayAdapter del dropdown con sus valores
        ArrayAdapter<String> arrayAdapterOrdenar = new ArrayAdapter<>(getApplicationContext(), R.layout.item_dropdown, ordenaciones);
        textOrdenar.setAdapter(arrayAdapterOrdenar);

        DB.insertarComponentes(26001, R.drawable.ssd_wb_blue, "Western Digital SSD 1tb", "ALMACENAMIENTO", 85.00, "Tipo de disco duro: SSD\nCapacidad: 1Tb\nVelocidad de lectura:560mb/s\nVelociad de escritura: 530mb/s");
        DB.insertarComponentes(26002, R.drawable.ssd_wd_m2, "Western Digital SSD M.2 1tb ", "ALMACENAMIENTO", 100.00, "Tipo de disco duro: SSD\nCapacidad: 1Tb\nVelocidad de lectura:2000mb/s\nVelociad de escritura: 1700mb/s");
        DB.insertarComponentes(26003, R.drawable.ssd_samsung, "Samsung 970 EVO Plus", "ALMACENAMIENTO", 150.00, "Tipo de disco duro: SSD NVME\nCapacidad: 1Tb\nVelocidad de lectura:3500mb/s\nVelociad de escritura: 3200mb/s");
        DB.insertarComponentes(26004, R.drawable.ssd_aorus, "Gigabyte AORUS 7000s", "ALMACENAMIENTO", 190.00, "Tipo de disco duro: SSD NVME\nCapacidad: 1Tb\nVelocidad de lectura:7000mb/s\nVelociad de escritura: 5500mb/s");
        listaComponentes = DB.getComponentes("ALMACENAMIENTO");

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
                //creamos el intent para la proxima actividad, acontinuación el bundle para guardar el objeto y por ultimo lo añadimos para poder enviarlo
                Intent intent = new Intent(Almacenamiento.this, Recibo.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("cpu", cpu);
                bundle.putParcelable("ram", ram);
                bundle.putParcelable("gpu", gpu);
                bundle.putParcelable("psu", psu);
                bundle.putParcelable("almacenamiento", listaComponentes.get(recyclerComponentes.getChildAdapterPosition(view)));

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
                intent = new Intent(Almacenamiento.this, ListaOrdenadores.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}