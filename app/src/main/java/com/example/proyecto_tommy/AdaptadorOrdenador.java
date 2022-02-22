package com.example.proyecto_tommy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorOrdenador extends RecyclerView.Adapter<AdaptadorOrdenador.ViewholderOrdenador> {
    //Crear las variables
    private ArrayList<Ordenador> listaOrdenadores;
    private LayoutInflater mInflater;
    private static onClickListner onclicklistner;

    /**
     * Constructor del adaptador
     */
    public AdaptadorOrdenador(Context context, ArrayList<Ordenador> listaOrdenadores) {
        this.mInflater = LayoutInflater.from(context);
        this.listaOrdenadores = listaOrdenadores;
    }

    /**
     * Metodo que establece el layout a utilizar cuando se cree la vista con los items
     */
    @Override
    public ViewholderOrdenador onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_ordenador, parent, false);
        return new ViewholderOrdenador(view);
    }

    /**
     * Metodo para escribir el valor del item
     */
    @Override
    public void onBindViewHolder(ViewholderOrdenador holder, int position) {
        holder.txtId.setText("E-mail:" + listaOrdenadores.get(position).getUID());
        holder.txtPrecio.setText("Precio total: "+listaOrdenadores.get(position).getPrecio() + " €");
        holder.txtFecha.setText("Fecha de compra: "+listaOrdenadores.get(position).getFecha());

        holder.imagenPc.setImageResource(R.drawable.ordenador);
    }


    /**
     * Metodo para devolver el tamaño de la lista
     */
    @Override
    public int getItemCount() {
        return listaOrdenadores.size();
    }

    /**
     * recuperar los elementos del item en viewholder
     */
    public class ViewholderOrdenador extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView txtPrecio, txtFecha, txtId;
        ImageView imagenPc;

        public ViewholderOrdenador(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            txtId = itemView.findViewById(R.id.txtID);
            txtFecha = itemView.findViewById(R.id.txtFecha);
            txtPrecio = itemView.findViewById(R.id.txtPrecioPC);
            imagenPc = itemView.findViewById(R.id.imagenPc);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            onclicklistner.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public boolean onLongClick(View v) {
            onclicklistner.onItemLongClick(getAdapterPosition(), v);
            return true;
        }
    }

    public interface onClickListner {
        void onItemClick(int position, View v);

        void onItemLongClick(int position, View v);
    }

    public void setOnItemClickListener(onClickListner onclicklistner) {
        AdaptadorOrdenador.onclicklistner = onclicklistner;
    }
}

