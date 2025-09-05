package com.culturarte.logica.clases;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Categoria {
    @Id
    String nombre;
    @ManyToOne
    Categoria catPadre;

    @OneToMany(mappedBy = "catPadre", cascade = CascadeType.ALL)
    private List<Categoria> subcategorias = new ArrayList<>();


    public Categoria(String nombre, Categoria catPadre) {
        this.nombre = nombre;
        this.catPadre = catPadre;
    }

    public Categoria(String nombre) {
        this.nombre = nombre;
        this.catPadre = null; // el default lo asigna en el controller
        this.subcategorias = new ArrayList<>();
    }

    public Categoria() {
    };

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Categoria getCatPadre() {
        return catPadre;
    }
    public void setCatPadre(Categoria catPadre) {
        this.catPadre = catPadre;
    }

    @Override
    public String toString() {
        return this != null ? this.getNombre() : getNombre();
    }
}
