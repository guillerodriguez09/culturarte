package com.culturarte.logica.controllers;

import com.culturarte.logica.clases.Categoria;
import com.culturarte.logica.dtos.DTOCategoria;
import com.culturarte.persistencia.CategoriaDAO;

import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.*;

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
        Categoria padre = dtoCat.getCatPadre();
        if (padre == null) {
            padre = categoriaDAO.buscarPorNombre("Categoría");
            if (padre == null) {
                padre = new Categoria("Categoría", null); //creamos la cat raiz "categoria"
                categoriaDAO.guardar(padre);
            }
        }

        Categoria cat = new Categoria(
                dtoCat.getNombre(),
                padre
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


    public DefaultMutableTreeNode construirArbolCategorias() {
        List<Categoria> categorias = categoriaDAO.obtenerTodas();

        Map<String, DefaultMutableTreeNode> nodos = new HashMap<>();
        DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("Categoría");
        nodos.put("Categoría", raiz); //pone a categoria en la raiz del arbol

        // Crear nodos sin agregarlos al árbol aún
        for (Categoria cat : categorias) {
            nodos.putIfAbsent(cat.getNombre(), new DefaultMutableTreeNode(cat.getNombre()));
        }

        // Enlazar nodos padre-hijo
        for (Categoria cat : categorias) {
            String nombre = cat.getNombre();
            String padreNombre = (cat.getCatPadre() != null) ? cat.getCatPadre().getNombre() : "Categoría";

            DefaultMutableTreeNode hijo = nodos.get(nombre);
            DefaultMutableTreeNode padre = nodos.getOrDefault(padreNombre, raiz);

            // esto hace que no entre en un ciclo, me estaba dando error antes
            if (padre != null && hijo != null && !hijo.isNodeAncestor(padre)) {
                padre.add(hijo);
            }
        }

        return raiz;
    }


}


