package com.culturarte.logica.controllers;

import com.culturarte.logica.clases.Seguimiento;
import com.culturarte.logica.dtos.DTOSeguimiento;
import com.culturarte.persistencia.SeguimientoDAO;

import java.util.ArrayList;
import java.util.List;

public class SeguimientoController implements ISeguimientoController{

    private final SeguimientoDAO seguimientoDAO = new SeguimientoDAO();

    @Override
    public void registrarSeguimiento(DTOSeguimiento dtoSegui){

        if (dtoSegui == null) {
            throw new IllegalArgumentException("Datos de seguimiento no provistos.");
        }

        if (dtoSegui.getUsuarioSeguidor() == null) {
            throw new IllegalArgumentException("Debe seleccionar un usario seguidoor");
        }

        if (dtoSegui.getUsuarioSeguido() == null) {
            throw new IllegalArgumentException("Debe seleccionar un usuario a seguir.");
        }

        if (seguimientoDAO.existe(dtoSegui.getUsuarioSeguidor().getNick(), dtoSegui.getUsuarioSeguido())) {
            throw new IllegalArgumentException("El usuario " + dtoSegui.getUsuarioSeguidor().getNick() + " ya sigue a " + dtoSegui.getUsuarioSeguido());
        }

        //Tendria que poner la funcion existe en Usuario en vez de en Proponente/Colaborador

        Seguimiento segui = new Seguimiento(
                dtoSegui.getUsuarioSeguidor(),
                dtoSegui.getUsuarioSeguido()
        );

        seguimientoDAO.guardar(segui);

    }

    @Override
    public void cancelarSeguimiento(int idSeguimiento){

        // Busca el seguimiento por el id
        Seguimiento segui = seguimientoDAO.buscarPorId(idSeguimiento);
        if (segui == null) {
            throw new IllegalArgumentException("No existe un seguimiento con la Id " + idSeguimiento);
        }

        // Eliminar de la bd
        seguimientoDAO.eliminar(segui);

    }

    @Override
    public List<DTOSeguimiento> listarSeguimientos(){

        List<Seguimiento> seguim = seguimientoDAO.obtenerTodos();
        //Muchos dtoSeguimiento
        List<DTOSeguimiento> dtoSeguim = new ArrayList<>();

        for (Seguimiento s : seguim) {
            DTOSeguimiento dtoSegui = new DTOSeguimiento(
                    s.getUsuarioSeguidor(),
                    s.getUsuarioSeguido()
            );
            dtoSeguim.add(dtoSegui);
        }

        return dtoSeguim;

    }


}
