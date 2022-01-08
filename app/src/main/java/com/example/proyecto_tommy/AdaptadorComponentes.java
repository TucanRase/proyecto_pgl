package com.example.proyecto_tommy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorComponentes extends RecyclerView.Adapter<AdaptadorComponentes.ViewholderComponentes> implements View.OnClickListener {
    private ArrayList<Componente> listaComponentes;
    private LayoutInflater mInflater;
    private View.OnClickListener listener;

    public AdaptadorComponentes(Context context,ArrayList<Componente> listaComponentes) {
        this.mInflater = LayoutInflater.from(context);
        this.listaComponentes = listaComponentes;
    }

    @Override
    public ViewholderComponentes onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= mInflater.inflate(R.layout.item_listas,parent,false);
        view.setOnClickListener(this);
        return new ViewholderComponentes(view);
    }
    //escribir el valor del item
    @Override
    public void onBindViewHolder(ViewholderComponentes holder, int position) {
        holder.ivImagen.setImageResource(listaComponentes.get(position).getImagen());
        holder.txtNombre.setText(listaComponentes.get(position).getNombre());
        holder.txtDescripcion.setText(listaComponentes.get(position).getCaracteristicas());
        holder.txtPrecio.setText(listaComponentes.get(position).getPrecio().toString()+" €");
        holder.itemView.setTag(position);

    }

    //2 metodos para poder activar el onclick en el item
    public void setOnclickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    //metodo para hallar el numero de componentes
    @Override
    public int getItemCount() {
        return listaComponentes.size();
    }

    //recuperar los elementos del item en viewholder
    public class ViewholderComponentes extends RecyclerView.ViewHolder{
        TextView txtNombre,txtDescripcion,txtPrecio;
        ImageView ivImagen;
        public ViewholderComponentes(View itemView) {
            super(itemView);
            ivImagen=(ImageView) itemView.findViewById(R.id.ivImagen);
            txtNombre=(TextView) itemView.findViewById(R.id.txtNombre);
            txtDescripcion=(TextView) itemView.findViewById(R.id.txtDescripcion);
            txtPrecio=(TextView) itemView.findViewById(R.id.txtPrecio);

        }
    }
}
