package com.culturarte.logica.clases;

import java.util.ArrayList;


public class Categoria {
    String nombre;
    String catPadre;
    ArrayList<Categoria> subcategorias;

    public Categoria(String nombre) {
        this.nombre = nombre;
        this.subcategorias = new ArrayList<>();
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getCatPadre() {
        return catPadre;
    }
    public void setCatPadre(String catPadre) {
        this.catPadre = catPadre;
    }
}
