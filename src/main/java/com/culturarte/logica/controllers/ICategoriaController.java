package com.culturarte.logica.controllers;

import com.culturarte.logica.clases.Categoria;
import com.culturarte.logica.dtos.DTOCategoria;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.List;

public interface ICategoriaController {

    public void altaCategoria(DTOCategoria dtoCategoria);
    public List<String> listarCategorias();
    public List<Categoria> listarCategoriasC();
    public DefaultMutableTreeNode construirArbolCategorias();
}
