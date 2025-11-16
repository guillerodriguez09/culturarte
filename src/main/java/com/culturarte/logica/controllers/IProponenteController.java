package com.culturarte.logica.controllers;

import com.culturarte.logica.clases.Proponente;
import com.culturarte.logica.dtos.DTOPropoPropu;
import com.culturarte.logica.dtos.DTOProponente;
import com.culturarte.logica.enums.EEstadoPropuesta;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

import java.time.LocalDate;
import java.util.List;
@WebService
public interface IProponenteController {

    @WebMethod
    void altaProponente(DTOProponente dtoProponente);

    @WebMethod
    List<DTOPropoPropu> obtenerTodPropConPropu (String nick);

    @WebMethod
    List<String> listarProponentes();

    @WebMethod
    DTOProponente obtenerProponente(String nick);
    @WebMethod
    List<DTOProponente> listarTodosProponente();
    @WebMethod
    DTOProponente obtenerProponenteCorreo(String correo);

    @WebMethod
    List<Object[]> obtenerPropConPropuYEstado (EEstadoPropuesta estado, String nick);

    @WebMethod
    String eliminarProponente(String nick);

    List<Object[]> obtenerTodPropConPropuDeEli(String nick);

    List<String> listarProponentesElim();

    @WebMethod
    boolean existeProponente(String nickOMail);


}
