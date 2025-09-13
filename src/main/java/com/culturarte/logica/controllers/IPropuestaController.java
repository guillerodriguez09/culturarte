package com.culturarte.logica.controllers;

import com.culturarte.logica.dtos.DTOPropuesta;

import com.culturarte.logica.enums.EEstadoPropuesta;

import java.time.LocalDate;
import java.util.List;

public interface IPropuestaController {

    void altaPropuesta(DTOPropuesta dtoPropuesta);
    DTOPropuesta consultarPropuesta(String titulo);
    List<String> listarPropuestas();
    List<DTOPropuesta> listarPorEstado(EEstadoPropuesta estado); //luego sera un DTO el estado si no entiendo mal
    void modificarPropuesta(String titulo, DTOPropuesta dtoPropuesta);
    void asignarEstado(String tituloPropuesta, EEstadoPropuesta estado, LocalDate fecha);


}
