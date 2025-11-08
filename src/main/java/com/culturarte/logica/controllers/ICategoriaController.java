package com.culturarte.logica.controllers;

import com.culturarte.logica.clases.Categoria;
import com.culturarte.logica.dtos.DTOCategoria;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.persistence.Entity;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.List;

@WebService
public interface ICategoriaController {

    @WebMethod
    public void altaCategoria(DTOCategoria dtoCategoria);
    @WebMethod
    public List<String> listarCategorias();
    @WebMethod
    public List<Categoria> listarCategoriasC();
    @WebMethod
    public DefaultMutableTreeNode construirArbolCategorias();
}
