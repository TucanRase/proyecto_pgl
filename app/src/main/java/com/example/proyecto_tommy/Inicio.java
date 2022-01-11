package com.example.proyecto_tommy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Inicio extends AppCompatActivity {
    //crear las variables
    RecyclerView recyclerOrdenador;
    Ordenador ordenador;
    TextView placeholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        //recogemos los valores pasados de las actividades anteriores para poder crear el recibo
        if (getIntent().getExtras() != null) {
            ordenador = getIntent().getExtras().getParcelable("pc");
        }

        Login.listaOrdenadores=new ArrayList<>();
        recyclerOrdenador=(RecyclerView) findViewById(R.id.recyclerOrdenador);
        placeholder=(TextView)findViewById(R.id.placeHolder);
        recyclerOrdenador.setLayoutManager(new LinearLayoutManager(this));
        //se añade el ordenador al arraylist
        if(ordenador!=null){
            Login.listaOrdenadores.add(ordenador);
            //Establecer el adaptador
            AdaptadorOrdenador adapter=new AdaptadorOrdenador(this,Login.listaOrdenadores);
            recyclerOrdenador.setAdapter(adapter);
        }else{
            recyclerOrdenador.setVisibility(View.GONE);
            placeholder.setVisibility(View.VISIBLE);
        }
    }
    /**Creamos el menú**/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opciones_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    /**Establecemos las acciones al pulsar las opciones del menú**/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.cerrarSesion:
                intent =new Intent(Inicio.this, Login.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
            case R.id.acerca:
                intent =new Intent(Inicio.this, Acerca.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
            case R.id.add:
                intent =new Intent(Inicio.this, Cpu.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /**
     * Método para establecer la animación al pulsar el botón "atrás"
     * */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
    }

}