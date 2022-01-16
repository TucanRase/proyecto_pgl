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
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class Cpu extends AppCompatActivity {
    //crear las variables
    ArrayList<Componente> listaComponentes;
    RecyclerView recyclerComponentes;
    TextView titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_componentes);

        titulo = (TextView) findViewById(R.id.componente);
        titulo.setText("Procesadores: ");
        AutoCompleteTextView textOrdenar=(AutoCompleteTextView) findViewById(R.id.dropDownOrdenar);
        String[] ordenaciones = getResources().getStringArray(R.array.ordenarPor);

        //Creamos y establecemos el ArrayAdapter del dropdown con sus valores
        ArrayAdapter<String> arrayAdapterOrdenar=new ArrayAdapter<>(getApplicationContext(),R.layout.item_dropdown,ordenaciones);
        textOrdenar.setAdapter(arrayAdapterOrdenar);

        textOrdenar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String item = arrayAdapterOrdenar.getItem(position);
                Toast.makeText(getApplicationContext(), item, Toast.LENGTH_SHORT).show();
                switch (item){
                    case "Precio descendente":

                        break;
                    case "Precio ascendente":
                        break;
                    case "Nombre A-Z":
                        break;
                    case "Nombre Z-A":
                        break;
                    default:
                        break;
                }
            }
        });

        //se crea y añaden los componentes al arraylist además se crea el adapter y se establece
        listaComponentes = new ArrayList<>();
        recyclerComponentes = (RecyclerView) findViewById(R.id.recycler);
        recyclerComponentes.setLayoutManager(new LinearLayoutManager(this));

        listaComponentes.add(new Componente(25001, R.drawable.ryzen_3600, "AMD Ryzen 5 3600", "CPU", 225.00, "Velocidad del procesador: 3.6 GHz \nVelocidad máx procesador: 4.2 GHz \nNúmero de nucleos: 6\nNúmero de hilos:12"));
        listaComponentes.add(new Componente(25002, R.drawable.ryzen_5600, "AMD Ryzen 5 5600X", "CPU", 295.00, "Velocidad del procesador: 3.7 GHz \nVelocidad máx procesador: 4.4 GHz\nNúmero de nucleos: 6\nNúmero de hilos:12"));
        listaComponentes.add(new Componente(25003, R.drawable.ryzen_3700x, "AMD Ryzen 7 3700X", "CPU", 320.00, "Velocidad del procesador: 3.7 GHz \nVelocidad máx procesador: 4.4 GHz\nNúmero de nucleos: 8\nNúmero de hilos:16"));
        listaComponentes.add(new Componente(25004, R.drawable.ryzen_5700g, "AMD Ryzen 5 5700G", "CPU", 340.00, "Velocidad del procesador: 3.8 GHz \nVelocidad máx procesador: 4.6 GHz\nNúmero de nucleos: 8\nNúmero de hilos:16"));
        AdaptadorComponentes adapter = new AdaptadorComponentes(this, listaComponentes);



        /**
         * Al clickar uno de los componentes en la lista se añade al bundle y se envía a la siguiente actividad junto a los componentes que llevemos de otras actividades
         * **/
        adapter.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Cpu.this, Ram.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("cpu", listaComponentes.get(recyclerComponentes.getChildAdapterPosition(view)));

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
                intent = new Intent(Cpu.this, Inicio.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}