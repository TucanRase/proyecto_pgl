package com.example.proyecto_tommy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorRecibo extends RecyclerView.Adapter<AdaptadorRecibo.ViewholderRecibo> {
    //Crear las variables
    private ArrayList<Componente> listaComponentes;
    private LayoutInflater mInflater;

    /**
     * Constructor del adaptador
     */
    public AdaptadorRecibo(Context context, ArrayList<Componente> listaComponentes) {
        this.mInflater = LayoutInflater.from(context);
        this.listaComponentes = listaComponentes;
    }

    /**
     * Metodo que establece el layout a utilizar cuando se cree la vista con los items
     */
    @Override
    public ViewholderRecibo onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_recibo, parent, false);
        return new ViewholderRecibo(view);
    }

    /**
     * Metodo para escribir el valor del item
     */
    @Override
    public void onBindViewHolder(ViewholderRecibo holder, int position) {
        holder.txtID.setText(String.valueOf(listaComponentes.get(position).getId()));
        holder.txtNombre.setText(listaComponentes.get(position).getNombre());
        holder.txtPrecio.setText(listaComponentes.get(position).getPrecio().toString());
        holder.itemView.setTag(position);

    }

    /**
     * Metodo para devolver el tamaño de la lista
     */
    @Override
    public int getItemCount() {
        return listaComponentes.size();
    }

    /**
     * recuperar los elementos del item en viewholder
     */
    public class ViewholderRecibo extends RecyclerView.ViewHolder {
        TextView txtID, txtNombre, txtPrecio;

        public ViewholderRecibo(View itemView) {
            super(itemView);
            txtID = (TextView) itemView.findViewById(R.id.idProducto);
            txtNombre = (TextView) itemView.findViewById(R.id.nombreProducto);
            txtPrecio = (TextView) itemView.findViewById(R.id.precioProducto);

        }
    }
}

