package com.example.proyecto_tommy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AdaptadorComponentes extends RecyclerView.Adapter<AdaptadorComponentes.ViewholderComponentes> implements View.OnClickListener {
    //crear las variables
    private ArrayList<Componente> listaComponentes;
    private LayoutInflater mInflater;
    private View.OnClickListener listener;

    /**
     * Constructor del adaptador
     * */
    public AdaptadorComponentes(Context context,ArrayList<Componente> listaComponentes) {
        this.mInflater = LayoutInflater.from(context);
        this.listaComponentes = listaComponentes;
    }
    /**
     * Metodo que establece el layout a utilizar cuando se cree la vista con los items
     * */
    @Override
    public ViewholderComponentes onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= mInflater.inflate(R.layout.item_listas,parent,false);
        view.setOnClickListener(this);
        return new ViewholderComponentes(view);
    }
    /**
     * Metodo para escribir el valor del item
     * */
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

    /**
     * Metodo para devolver el tamaño de la lista
     * */
    @Override
    public int getItemCount() {
        return listaComponentes.size();
    }

    /**
     * recuperar los elementos del item en viewholder
     * */
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

    public static ArrayList<Componente> ordenarPrecioAsc(ArrayList<Componente> lista){
        lista.sort(Comparator.comparing(Componente::getPrecio));
        return lista;
    }
    public static ArrayList<Componente> ordenarPrecioDesc(ArrayList<Componente> lista){
        lista.sort(Comparator.comparing(Componente::getPrecio));
        Collections.reverse(lista);
        return lista;
    }
    public static ArrayList<Componente> ordenarNombreAsc(ArrayList<Componente> lista){
        lista.sort(Comparator.comparing(Componente::getNombre));
        return lista;
    }
    public static ArrayList<Componente> ordenarNombreDesc(ArrayList<Componente> lista){
        lista.sort(Comparator.comparing(Componente::getNombre));
        Collections.reverse(lista);
        return lista;
    }
}
