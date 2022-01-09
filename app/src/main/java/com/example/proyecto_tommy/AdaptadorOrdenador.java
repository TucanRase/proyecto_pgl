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
        holder.txtId.setText("ID:"+listaOrdenadores.get(position).getId());
        holder.txtCpu.setText(listaOrdenadores.get(position).getCpu().getNombre());
        holder.txtRam.setText(listaOrdenadores.get(position).getRam().getNombre());
        holder.txtGpu.setText(listaOrdenadores.get(position).getGpu().getNombre());
        holder.txtPsu.setText(listaOrdenadores.get(position).getPsu().getNombre());
        holder.txtSSD.setText(listaOrdenadores.get(position).getAlmacenamiento().getNombre());

        holder.imagenPc.setImageResource(R.drawable.ordenador);
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
        TextView txtCpu,txtRam,txtGpu,txtPsu,txtSSD,txtId;
        ImageView imagenPc;
        public ViewholderOrdenador(View itemView) {
            super(itemView);
            txtId=(TextView) itemView.findViewById(R.id.txtId);
            txtCpu=(TextView) itemView.findViewById(R.id.txtCpu);
            txtRam=(TextView) itemView.findViewById(R.id.txtRam);
            txtGpu=(TextView) itemView.findViewById(R.id.txtGpu);
            txtPsu=(TextView) itemView.findViewById(R.id.txtPsu);
            txtSSD=(TextView) itemView.findViewById(R.id.txtSSD);
            imagenPc=(ImageView) itemView.findViewById(R.id.imagenPc);


        }
    }
}

