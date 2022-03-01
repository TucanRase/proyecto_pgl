package com.example.proyecto_tommy;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Cpu extends AppCompatActivity {
    //crear las variables
    ArrayList<Componente> listaComponentes = new ArrayList<>();
    RecyclerView recyclerComponentes;
    FirebaseDatabase database;
    DatabaseReference myRef;
    AdaptadorComponentes adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_componentes);

        setTitle("Procesadores");

        AutoCompleteTextView textOrdenar = findViewById(R.id.dropDownOrdenar);
        String[] ordenaciones = getResources().getStringArray(R.array.ordenarPor);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("componentes");

        myRef.orderByChild("tipo").equalTo("CPU").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                listaComponentes.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Componente componente = postSnapshot.getValue(Componente.class);
                    listaComponentes.add(componente);
                }
                recyclerComponentes = findViewById(R.id.recycler);
                recyclerComponentes.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new AdaptadorComponentes(getApplicationContext(), listaComponentes);
                recyclerComponentes.setAdapter(adapter);
                activarOnclick();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("La lectura de componentes falló" + databaseError.getMessage());
                Toast.makeText(getApplicationContext(), "Ha habido un problema al leer la base de datos", Toast.LENGTH_SHORT).show();
            }
        });

        //Creamos y establecemos el ArrayAdapter del dropdown con sus valores
        ArrayAdapter<String> arrayAdapterOrdenar = new ArrayAdapter<>(getApplicationContext(), R.layout.item_dropdown, ordenaciones);
        textOrdenar.setAdapter(arrayAdapterOrdenar);

        //se crea y añaden los componentes al arraylist además se crea el adapter y se establece
        /**
         *Este método lo que hace es que al clickar en el dropdown podemos organizar los valores alfabeticamente o por precios
         * */
        textOrdenar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position,
                                    long l) {
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
        if (item.getItemId() == R.id.home) {
            startActivity(new Intent(getApplicationContext(), Portada.class));
            overridePendingTransition(R.anim.right_in, R.anim.right_out);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void activarOnclick() {
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
    }
}