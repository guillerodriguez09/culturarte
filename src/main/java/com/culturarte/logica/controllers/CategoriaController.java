package com.culturarte.logica.controllers;

import com.culturarte.logica.clases.Categoria;
import com.culturarte.logica.dtos.DTOCategoria;
import com.culturarte.persistencia.CategoriaDAO;

import java.util.List;

public class CategoriaController implements ICategoriaController {

    private final CategoriaDAO categoriaDAO = new CategoriaDAO();

    @Override
    public void altaCategoria(DTOCategoria dtoCat){

        if (dtoCat == null) {
            throw new IllegalArgumentException("Datos de categoria no provistos.");
        }
        if (dtoCat.getNombre() == null || dtoCat.getNombre().isBlank() ){
            throw new IllegalArgumentException("Nombre de categoria es obligatorio.");
        }
        if (categoriaDAO.existe(dtoCat.getNombre())) {
            throw new IllegalArgumentException("Ya existe una categoria con ese nombre.");
        }

        Categoria cat = new Categoria(
                dtoCat.getNombre(),
                dtoCat.getCatPadre()
        );

        categoriaDAO.guardar(cat);

    }

    @Override
    public List<String> listarCategorias() {
        return categoriaDAO.obtenerTodas()
                .stream()
                .map(Categoria::getNombre)
                .toList();
    }

    @Override
    public List<Categoria> listarCategoriasC() {
        return categoriaDAO.obtenerTodas();
    }
}


