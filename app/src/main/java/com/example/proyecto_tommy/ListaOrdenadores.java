package com.example.proyecto_tommy;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

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
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;
    DatabaseReference myRef;
    String userId,tipo,email;
    CardView cargando;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ordenadores);

        setTitle("Mis ordenadores");

        recyclerOrdenador = findViewById(R.id.recyclerOrdenador);
        cargando=findViewById(R.id.cargando);
        placeholder = findViewById(R.id.placeHolder);
        recyclerOrdenador.setLayoutManager(new LinearLayoutManager(this));
        //se añaden los ordenadores al arraylist
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("ordenadores");

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();
        userId = user.getUid();
        email = user.getEmail();

        DocumentReference documentReference = fStore.collection("usuarios").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                cargando.setVisibility(View.VISIBLE);
                try {
                    tipo = value.getString("tipo");
                } catch (NullPointerException e) {
                    Log.d(TAG, "El usuario está vacío");
                }
            }
        });
        try {
            cargando.setVisibility(View.VISIBLE);
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ordenadores.clear();
                if (tipo.equals("Profesor")) {
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        Ordenador ordenador = postSnapshot.getValue(Ordenador.class);
                        ordenadores.add(ordenador);
                    }
                }else{
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        Ordenador ordenador = postSnapshot.getValue(Ordenador.class);
                        if(ordenador.getUID().equals(email))
                            ordenadores.add(ordenador);
                    }
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
                cargando.setVisibility(View.GONE);
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
                        database.getReference("ordenadores").child(ordenadores.get(position).getId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
//                                Toast.makeText(ListaOrdenadores.this, "Ordenador eliminado correctamente de la lista", Toast.LENGTH_SHORT).show();
//                                ordenadores.remove(position);
//                                adapter.notifyItemRemoved(position);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ListaOrdenadores.this, "No se ha podido eliminar el ordenador de la lista", Toast.LENGTH_SHORT).show();
                            }
                        });
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