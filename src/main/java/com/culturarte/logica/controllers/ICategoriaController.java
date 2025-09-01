package com.culturarte.logica.controllers;

import com.culturarte.logica.clases.Categoria;
import com.culturarte.logica.dtos.DTOCategoria;

import java.util.List;

public interface ICategoriaController {

    public void altaCategoria(DTOCategoria dtoCategoria);
    public List<String> listarCategorias();
    public List<Categoria> listarCategoriasC();
}
