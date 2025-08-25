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

//cuando le pasa un padre
    public Categoria(String nombre, Categoria catPadre) {
        this.nombre = nombre;
        this.catPadre = catPadre;
    }

   //hay q ver el tema de que le ponga la categoria por defecto "categoria".| Podriamos hacer que al crear una categoria tenga la variable catPadre ya definida como categoria y que si el usuario ingresa algo la sobreescriba
    public Categoria(String nombre) {
        this.nombre = nombre;
        this.catPadre = null;
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
}
