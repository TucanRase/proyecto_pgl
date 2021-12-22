package com.example.proyecto_tommy;

public class Componente {
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
