package com.example.proyecto_tommy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.common.util.ArrayUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ComponentesPC extends AppCompatActivity {
    RecyclerView recycler;
    ArrayList<Componente> listaComponentes = new ArrayList<>();
    Ordenador pc1;
    FirebaseDatabase database;
    DatabaseReference myRef;
    AdaptadorComponentes adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_componentes_pc);

        if (getIntent().getExtras() != null) {
            pc1 = getIntent().getExtras().getParcelable("pc");
        }

        setTitle("Componentes del ordenador");

        recycler = findViewById(R.id.recyclerComponentes);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        int[] listaIds = new int[5];
        listaIds[0] = pc1.getCpu();
        listaIds[1] = pc1.getAlmacenamiento();
        listaIds[2] = pc1.getGpu();
        listaIds[3] = pc1.getRam();
        listaIds[4] = pc1.getPsu();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("componentes");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                listaComponentes.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Componente componente = postSnapshot.getValue(Componente.class);
                    if (ArrayUtils.contains(listaIds, componente.getId()))
                        listaComponentes.add(componente);
                }
                adapter = new AdaptadorComponentes(getApplicationContext(), listaComponentes);
                recycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("La lectura de ordenadores falló" + databaseError.getMessage());
            }
        });
        recycler.setAdapter(adapter);

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
            intent = new Intent(ComponentesPC.this, Portada.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * Método para establecer la animación al pulsar el botón "atrás"
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
    }


}
