package com.culturarte.logica.controllers;

import com.culturarte.logica.dtos.DTOColaborador;

import java.util.List;

public interface IColaboradorController {

    public void altaColaborador(DTOColaborador dtoColaborador);

    List<Object[]> obtenerTodColConPropu(String nick);
}
