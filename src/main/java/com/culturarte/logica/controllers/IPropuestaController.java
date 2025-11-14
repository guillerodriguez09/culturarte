package com.culturarte.logica.controllers;

import com.culturarte.logica.clases.Propuesta;
import com.culturarte.logica.dtos.DTOPropuesta;

import com.culturarte.logica.enums.EEstadoPropuesta;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

import java.time.LocalDate;
import java.util.List;

@WebService
public interface IPropuestaController {

    @WebMethod
    void altaPropuesta(DTOPropuesta dtoPropuesta);
    @WebMethod
    DTOPropuesta consultarPropuesta(String titulo);
    @WebMethod
    List<String> listarPropuestas();
    @WebMethod
    List<DTOPropuesta> listarPorEstado(EEstadoPropuesta estado);
    @WebMethod
    void modificarPropuesta(String titulo, DTOPropuesta dtoPropuesta);
    @WebMethod
    public List<DTOPropuesta> listarPropuestasConProponente();
    @WebMethod
    void asignarEstado(String tituloPropuesta, EEstadoPropuesta estado, LocalDate fecha);
    @WebMethod
    public List<DTOPropuesta> listarPropuestasIngresadas();
    @WebMethod
    public void evaluarPropuesta(String tituloPropuesta, boolean publicar);
    @WebMethod
    public void cancelarPropuesta(String tituloPropuesta);
    @WebMethod
    public List<DTOPropuesta> buscarPropuestas(String filtro);
    @WebMethod
    List<DTOPropuesta> recomendarPropuestas(String nicknameColaborador);
}
