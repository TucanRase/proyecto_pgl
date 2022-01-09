package com.example.proyecto_tommy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorOrdenador extends RecyclerView.Adapter<AdaptadorOrdenador.ViewholderOrdenador> {
    //Crear las variables
    private ArrayList<Ordenador> listaOrdenadores;
    private LayoutInflater mInflater;
    /**
     * Constructor del adaptador
     * */
    public AdaptadorOrdenador(Context context, ArrayList<Ordenador> listaOrdenadores) {
        this.mInflater = LayoutInflater.from(context);
        this.listaOrdenadores = listaOrdenadores;
    }

    /**
     * Metodo que establece el layout a utilizar cuando se cree la vista con los items
     * */
    @Override
    public ViewholderOrdenador onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= mInflater.inflate(R.layout.item_ordenador,parent,false);
        return new ViewholderOrdenador(view);
    }
    /**
     * Metodo para escribir el valor del item
     * */
    @Override
    public void onBindViewHolder(ViewholderOrdenador holder, int position) {

    }

    /**
     * Metodo para devolver el tamaño de la lista
     * */
    @Override
    public int getItemCount() {
        return listaOrdenadores.size();
    }

    /**
     * recuperar los elementos del item en viewholder
     * */
    public class ViewholderOrdenador extends RecyclerView.ViewHolder{
        TextView txtID,txtNombre,txtPrecio;
        public ViewholderOrdenador(View itemView) {
            super(itemView);
            txtID=(TextView) itemView.findViewById(R.id.idProducto);
            txtNombre=(TextView) itemView.findViewById(R.id.nombreProducto);
            txtPrecio=(TextView) itemView.findViewById(R.id.precioProducto);

        }
    }
}

