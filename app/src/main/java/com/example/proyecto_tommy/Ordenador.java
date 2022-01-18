package com.example.proyecto_tommy;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Clase ordenador para guardar los componentes
 **/
public class Ordenador implements Parcelable {
    private String id;
    private Componente cpu;
    private Componente ram;
    private Componente gpu;
    private Componente psu;
    private Componente almacenamiento;

    /**
     * Contructor del objeto
     */
    public Ordenador(String id, Componente cpu, Componente ram, Componente gpu, Componente psu, Componente almacenamiento) {
        this.id = id;
        this.cpu = cpu;
        this.ram = ram;
        this.gpu = gpu;
        this.psu = psu;
        this.almacenamiento = almacenamiento;
    }

    /**
     * constructor parcelable del objeto
     */
    protected Ordenador(Parcel in) {
        id = in.readString();
        cpu = in.readParcelable(Componente.class.getClassLoader());
        ram = in.readParcelable(Componente.class.getClassLoader());
        gpu = in.readParcelable(Componente.class.getClassLoader());
        psu = in.readParcelable(Componente.class.getClassLoader());
        almacenamiento = in.readParcelable(Componente.class.getClassLoader());
    }

    /**
     * Creator parcelable
     */
    public static final Creator<Ordenador> CREATOR = new Creator<Ordenador>() {
        @Override
        public Ordenador createFromParcel(Parcel in) {
            return new Ordenador(in);
        }

        @Override
        public Ordenador[] newArray(int size) {
            return new Ordenador[size];
        }
    };

    //Getters y setters de la clase
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Componente getCpu() {
        return cpu;
    }

    public void setCpu(Componente cpu) {
        this.cpu = cpu;
    }

    public Componente getRam() {
        return ram;
    }

    public void setRam(Componente ram) {
        this.ram = ram;
    }

    public Componente getGpu() {
        return gpu;
    }

    public void setGpu(Componente gpu) {
        this.gpu = gpu;
    }

    public Componente getPsu() {
        return psu;
    }

    public void setPsu(Componente psu) {
        this.psu = psu;
    }

    public Componente getAlmacenamiento() {
        return almacenamiento;
    }

    public void setAlmacenamiento(Componente almacenamiento) {
        this.almacenamiento = almacenamiento;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Método para utilizar el parcelable
     */
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeParcelable(cpu, i);
        parcel.writeParcelable(ram, i);
        parcel.writeParcelable(gpu, i);
        parcel.writeParcelable(psu, i);
        parcel.writeParcelable(almacenamiento, i);
    }
}
