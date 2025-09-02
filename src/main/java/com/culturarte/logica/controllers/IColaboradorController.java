package com.culturarte.logica.controllers;

import com.culturarte.logica.dtos.DTOColaborador;

import java.util.List;

public interface IColaboradorController {

    public void altaColaborador(DTOColaborador dtoColaborador);
    List<String> listarColaboradores();
    List<Object[]> obtenerTodColConPropu(String nick);
    public DTOColaborador obtenerColaborador(String nick);
}
