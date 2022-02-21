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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Almacenamiento extends AppCompatActivity {
    ArrayList<Componente> listaComponentes;
    RecyclerView recyclerComponentes;
    Componente cpu, ram, gpu, psu;
    FirebaseDatabase database;
    DatabaseReference myRef;
    AdaptadorComponentes adapter;

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

        listaComponentes = new ArrayList<>();
        recyclerComponentes = findViewById(R.id.recycler);
        recyclerComponentes.setLayoutManager(new LinearLayoutManager(this));
        AutoCompleteTextView textOrdenar = findViewById(R.id.dropDownOrdenar);
        String[] ordenaciones = getResources().getStringArray(R.array.ordenarPor);

        //Creamos y establecemos el ArrayAdapter del dropdown con sus valores
        ArrayAdapter<String> arrayAdapterOrdenar = new ArrayAdapter<>(getApplicationContext(), R.layout.item_dropdown, ordenaciones);
        textOrdenar.setAdapter(arrayAdapterOrdenar);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("componentes");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                listaComponentes.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Componente componente = postSnapshot.getValue(Componente.class);
                    if (componente.getTipo().equals("ALMACENAMIENTO"))
                        listaComponentes.add(componente);
                }
                adapter = new AdaptadorComponentes(getApplicationContext(), listaComponentes);
                recyclerComponentes.setAdapter(adapter);
                activarOnClick();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("La lectura de componentes falló" + databaseError.getMessage());
            }
        });

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
            intent = new Intent(Almacenamiento.this, Portada.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void activarOnClick(){
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
    }
}