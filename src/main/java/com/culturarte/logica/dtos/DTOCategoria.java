package com.culturarte.logica.dtos;

import com.culturarte.logica.clases.Categoria;
import jakarta.persistence.ManyToOne;

import java.util.ArrayList;
import java.util.List;

public class DTOCategoria {

    String nombre;
    Categoria catPadre;
    private List<Categoria> subcategorias = new ArrayList<>();

    public DTOCategoria() {};

    //hay q ver el tema de que le ponga la categoria por defecto "categoria".| Podriamos hacer que al crear una categoria tenga la variable catPadre ya definida como categoria y que si el usuario ingresa algo la sobreescriba
    public DTOCategoria(String nombre) {
        this.nombre = nombre;
        this.catPadre = null;
        this.subcategorias = new ArrayList<>();
    }

    public DTOCategoria(String nombre, Categoria catPadre) {
        this.nombre = nombre;
        this.catPadre = catPadre;
    }

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
