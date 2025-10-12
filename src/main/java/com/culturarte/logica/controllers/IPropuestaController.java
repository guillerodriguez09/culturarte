package com.culturarte.logica.controllers;

import com.culturarte.logica.clases.Propuesta;
import com.culturarte.logica.dtos.DTOPropuesta;

import com.culturarte.logica.enums.EEstadoPropuesta;

import java.time.LocalDate;
import java.util.List;

public interface IPropuestaController {

    void altaPropuesta(DTOPropuesta dtoPropuesta);
    DTOPropuesta consultarPropuesta(String titulo);
    List<String> listarPropuestas();
    List<DTOPropuesta> listarPorEstado(EEstadoPropuesta estado);
    void modificarPropuesta(String titulo, DTOPropuesta dtoPropuesta);
    public List<DTOPropuesta> listarPropuestasConProponente();
    void asignarEstado(String tituloPropuesta, EEstadoPropuesta estado, LocalDate fecha);
    public List<DTOPropuesta> listarPropuestasIngresadas();
    public void evaluarPropuesta(String tituloPropuesta, boolean publicar);
    public void cancelarPropuesta(String tituloPropuesta);
    public List<DTOPropuesta> buscarPropuestas(String filtro);
}
