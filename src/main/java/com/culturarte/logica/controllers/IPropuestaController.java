package com.culturarte.logica.controllers;

import com.culturarte.logica.clases.Propuesta;
import com.culturarte.logica.dtos.DTOPropuesta;

import com.culturarte.logica.enums.EEstadoPropuesta;

import java.util.List;

public interface IPropuestaController {

    void altaPropuesta(DTOPropuesta dtoPropuesta);

    List<DTOPropuesta> listarPorEstado(EEstadoPropuesta estado); //luego sera un DTO el estado si no entiendo mal
}
