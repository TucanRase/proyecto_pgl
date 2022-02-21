package com.example.proyecto_tommy;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Clase ordenador para guardar los componentes
 **/
public class Ordenador implements Parcelable {
    private String id;
    private String fecha;
    private double precio;
    private int cpu;
    private int ram;
    private int gpu;
    private int psu;
    private int almacenamiento;
    private String UID;

    public Ordenador(String id, String fecha, double precio, int cpu, int ram, int gpu, int psu, int almacenamiento, String UID) {
        this.id = id;
        this.fecha = fecha;
        this.precio = precio;
        this.cpu = cpu;
        this.ram = ram;
        this.gpu = gpu;
        this.psu = psu;
        this.almacenamiento = almacenamiento;
        this.UID = UID;
    }

    public Ordenador(){}

    public Ordenador(String fecha, double precio, int cpu, int ram, int gpu, int psu, int almacenamiento, String UID) {
        this.fecha = fecha;
        this.precio = precio;
        this.cpu = cpu;
        this.ram = ram;
        this.gpu = gpu;
        this.psu = psu;
        this.almacenamiento = almacenamiento;
        this.UID = UID;
    }

    protected Ordenador(Parcel in) {
        id = in.readString();
        fecha = in.readString();
        precio = in.readDouble();
        cpu = in.readInt();
        ram = in.readInt();
        gpu = in.readInt();
        psu = in.readInt();
        almacenamiento = in.readInt();
        UID = in.readString();
    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCpu() {
        return cpu;
    }

    public void setCpu(int cpu) {
        this.cpu = cpu;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public int getGpu() {
        return gpu;
    }

    public void setGpu(int gpu) {
        this.gpu = gpu;
    }

    public int getPsu() {
        return psu;
    }

    public void setPsu(int psu) {
        this.psu = psu;
    }

    public int getAlmacenamiento() {
        return almacenamiento;
    }

    public void setAlmacenamiento(int almacenamiento) {
        this.almacenamiento = almacenamiento;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(fecha);
        parcel.writeDouble(precio);
        parcel.writeInt(cpu);
        parcel.writeInt(ram);
        parcel.writeInt(gpu);
        parcel.writeInt(psu);
        parcel.writeInt(almacenamiento);
        parcel.writeString(UID);
    }
}
