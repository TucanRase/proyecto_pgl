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

public class Gpu extends AppCompatActivity {
    //crear las variables
    ArrayList<Componente> listaComponentes;
    RecyclerView recyclerComponentes;
    Componente cpu, ram;
    TextView titulo;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_componentes);

        titulo = (TextView) findViewById(R.id.componente);
        titulo.setText("Tarjetas gráficas: ");

        //recogemos los valores pasados de las actividades anteriores para poder crear el recibo
        if (getIntent().getExtras() != null) {
            cpu = getIntent().getExtras().getParcelable("cpu");
            ram = getIntent().getExtras().getParcelable("ram");
        }

        //se crea y añaden los componentes al arraylist además se crea el adapter y se establece
        recyclerComponentes = (RecyclerView) findViewById(R.id.recycler);
        recyclerComponentes.setLayoutManager(new LinearLayoutManager(this));
        DB = new DBHelper(this);
        // TODO: 22/01/2022 Controlar errores al introducir y limpiar comentarios
        DB.insertarComponentes(22001, R.drawable.rtx_2060_evga, "Nvidia RTX 2060 EVGA KO", "GPU", 525.00, "Memoria 6GB GDDR6 \nNúcleos CUDA: 1920\nVelocidad del reloj de la memoria:14000 MHz");
        DB.insertarComponentes(22002, R.drawable.rtx_2060_giga, "Nvidia RTX 2060 Gigabyte Windforce", "GPU", 600.00, "Memoria 6GB GDDR6 \nNúcleos CUDA: 1920\nVelocidad del reloj de la memoria:1830 MHz");
        DB.insertarComponentes(22003, R.drawable.rtx_2060_msi, "Nvidia RTX 2060 MSI Gaming Z 6G", "GPU", 600.00, "Memoria 6GB GDDR6 \nNúcleos CUDA: 1920\nVelocidad del reloj de la memoria:1830 MHz");
        DB.insertarComponentes(22004, R.drawable.rtx_2060_asus, "Nvidia RTX 2060 Asus Dual Evo", "GPU", 550.00, "Memoria 6GB GDDR6" + " \nNúcleos CUDA: 1920\nVelocidad del reloj de la memoria:1725 MHz");
        listaComponentes = DB.getComponentes("GPU");
        //Creamos y establecemos el ArrayAdapter del dropdown con sus valores
        AutoCompleteTextView textOrdenar = (AutoCompleteTextView) findViewById(R.id.dropDownOrdenar);
        String[] ordenaciones = getResources().getStringArray(R.array.ordenarPor);
        ArrayAdapter<String> arrayAdapterOrdenar = new ArrayAdapter<>(getApplicationContext(), R.layout.item_dropdown, ordenaciones);
        textOrdenar.setAdapter(arrayAdapterOrdenar);

        /*
        listaComponentes.add(new Componente(22001, R.drawable.rtx_2060_evga, "Nvidia RTX 2060 EVGA KO", "GPU", 525.00, "Memoria 6GB GDDR6 \nNúcleos CUDA: 1920\nVelocidad del reloj de la memoria:14000 MHz"));
        listaComponentes.add(new Componente(22002, R.drawable.rtx_2060_giga, "Nvidia RTX 2060 Gigabyte Windforce", "GPU", 600.00, "Memoria 6GB GDDR6 \nNúcleos CUDA: 1920\nVelocidad del reloj de la memoria:1830 MHz"));
        listaComponentes.add(new Componente(22003, R.drawable.rtx_2060_msi, "Nvidia RTX 2060 MSI Gaming Z 6G", "GPU", 600.00, "Memoria 6GB GDDR6 \nNúcleos CUDA: 1920\nVelocidad del reloj de la memoria:1830 MHz"));
        listaComponentes.add(new Componente(22004, R.drawable.rtx_2060_asus, "Nvidia RTX 2060 Asus Dual Evo", "GPU", 550.00, "Memoria 6GB GDDR6" + " \nNúcleos CUDA: 1920\nVelocidad del reloj de la memoria:1725 MHz"));
        */
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
                Intent intent = new Intent(Gpu.this, Psu.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("cpu", cpu);
                bundle.putParcelable("ram", ram);
                bundle.putParcelable("gpu", listaComponentes.get(recyclerComponentes.getChildAdapterPosition(view)));

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
                intent = new Intent(Gpu.this, ListaOrdenadores.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}