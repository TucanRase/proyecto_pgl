package com.example.proyecto_tommy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListaOrdenadores extends AppCompatActivity {
    //crear las variables
    ArrayList<Ordenador> ordenadores = new ArrayList<>();
    RecyclerView recyclerOrdenador;
    AdaptadorOrdenador adapter;
    TextView placeholder;
    FloatingActionButton fab;
    Intent intent;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ordenadores);

        setTitle("Mis ordenadores");

        recyclerOrdenador = findViewById(R.id.recyclerOrdenador);
        placeholder = findViewById(R.id.placeHolder);
        recyclerOrdenador.setLayoutManager(new LinearLayoutManager(this));
        //se añaden los ordenadores al arraylist
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("ordenadores");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ordenadores.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Ordenador ordenador = postSnapshot.getValue(Ordenador.class);
                    ordenadores.add(ordenador);
                }
                if (ordenadores.size() > 0) {
                    //Establecer el adaptador
                    adapter = new AdaptadorOrdenador(getApplicationContext(), ordenadores);
                    recyclerOrdenador.setAdapter(adapter);
                    activarOnClick();

                } else {
                    //establecer aviso de no hay ordenadores
                    recyclerOrdenador.setVisibility(View.GONE);
                    placeholder.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("La lectura de ordendadores falló" + databaseError.getMessage());
            }
        });
        fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(ListaOrdenadores.this, Cpu.class);
                startActivity(intent);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
            }
        });
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
            intent = new Intent(ListaOrdenadores.this, Portada.class);
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

    public void activarOnClick() {
        //Establecer los escuchadores para onclick y longclick
        adapter.setOnItemClickListener(new AdaptadorOrdenador.onClickListner() {
            @Override
            public void onItemClick(int position, View v) {
                Intent i = new Intent(getApplicationContext(), ComponentesPC.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("pc", ordenadores.get(position));
                System.out.println(ordenadores.get(position).getId());
                i.putExtras(bundle);
                startActivity(i);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
            }

            @Override
            public void onItemLongClick(int position, View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ListaOrdenadores.this);
                alert.setTitle("Eliminar ordenador");
                alert.setMessage("¿Está seguro de querer eliminar el ordenador con el ID: " + ordenadores.get(position).getId() + "?");
                alert.setPositiveButton("Sí", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //borrar pc en firebase
                    }
                });

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alert.show();
            }
        });
    }
}