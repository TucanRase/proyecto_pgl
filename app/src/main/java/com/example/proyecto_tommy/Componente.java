package com.example.proyecto_tommy;

import android.os.Parcel;
import android.os.Parcelable;
//utilizo parcelable porque es una implementación de serializable que me permite facilitar el movimiento del objeto entre actividades
public class Componente implements Parcelable {
    private int id;
    private int imagen;
    private String nombre;
    private String tipo;
    private Double precio;
    private String caracteristicas;

    public Componente(int id, int imagen, String nombre, String tipo, Double precio, String caracteristicas) {
        this.id = id;
        this.imagen = imagen;
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
        this.caracteristicas = caracteristicas;
    }
//constructor parcelable del objeto
    protected Componente(Parcel in) {
        id = in.readInt();
        imagen = in.readInt();
        nombre = in.readString();
        tipo = in.readString();
        if (in.readByte() == 0) {
            precio = null;
        } else {
            precio = in.readDouble();
        }
        caracteristicas = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(imagen);
        dest.writeString(nombre);
        dest.writeString(tipo);
        if (precio == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(precio);
        }
        dest.writeString(caracteristicas);
    }

    public static final Creator<Componente> CREATOR = new Creator<Componente>() {
        @Override
        public Componente createFromParcel(Parcel in) {
            return new Componente(in);
        }

        @Override
        public Componente[] newArray(int size) {
            return new Componente[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }
}
