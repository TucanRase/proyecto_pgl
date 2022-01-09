package com.example.proyecto_tommy;
/**
 * Clase ordenador para guardar los componentes
 * **/
public class Ordenador {
    private String id;
    private Componente cpu;
    private Componente ram;
    private Componente gpu;
    private Componente psu;
    private Componente almacenamiento;

    public Ordenador(String id, Componente cpu, Componente ram, Componente gpu, Componente psu, Componente almacenamiento) {
        this.id = id;
        this.cpu = cpu;
        this.ram = ram;
        this.gpu = gpu;
        this.psu = psu;
        this.almacenamiento = almacenamiento;
    }

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
}
