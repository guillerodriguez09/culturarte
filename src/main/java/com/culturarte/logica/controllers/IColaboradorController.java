package com.culturarte.logica.controllers;

import com.culturarte.logica.dtos.DTOColPropu;
import com.culturarte.logica.dtos.DTOColaborador;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

import java.util.List;
@WebService
public interface IColaboradorController {

    @WebMethod
    public void altaColaborador(DTOColaborador dtoColaborador);
    @WebMethod
    List<String> listarColaboradores();
    @WebMethod
    List<DTOColPropu> obtenerTodColConPropu(String nick);
    @WebMethod
    DTOColaborador obtenerColaborador(String nick);
    @WebMethod
    DTOColaborador obtenerColaboradorCorreo(String correo);
    @WebMethod
    List<DTOColaborador> listarTodos();
}
