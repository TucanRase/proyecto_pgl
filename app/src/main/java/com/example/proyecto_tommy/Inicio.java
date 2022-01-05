package com.example.proyecto_tommy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class Inicio extends AppCompatActivity {
    ArrayList<Componente> listaComponentes;
    RecyclerView recyclerComponentes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_componentes);

        listaComponentes=new ArrayList<>();
        recyclerComponentes=(RecyclerView) findViewById(R.id.recycler);
        recyclerComponentes.setLayoutManager(new LinearLayoutManager(this));

        listaComponentes.add(new Componente(1,R.drawable.mag_b550m,"primero","1", 10.00,"cabeas"));
        listaComponentes.add(new Componente(2,R.drawable.mag_b550m,"segundo","1", 150.00,"cabeas"));
        listaComponentes.add(new Componente(3,R.drawable.mag_b550m,"penultimo","1", 420.00,"cabeas"));
        listaComponentes.add(new Componente(4,R.drawable.mag_b550m,"ultimo","2", 15.00,"cabeas"));
        AdaptadorComponentes adapter=new AdaptadorComponentes(this,listaComponentes);

        adapter.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creamos el intent para la proxima actividad, acontinuación el bundle para guardar el objeto y por ultimo lo añadimos para poder enviarlo
                Intent intent =new Intent(Inicio.this, Cpu.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("placa",listaComponentes.get(recyclerComponentes.getChildAdapterPosition(view)));

                intent.putExtras(bundle);
                startActivity(intent);

        }
        });

        recyclerComponentes.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opciones_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.acerca:
                intent =new Intent(Inicio.this, Acerca.class);
                startActivity(intent);
                return true;
            case R.id.add:
                intent =new Intent(Inicio.this, Cpu.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}