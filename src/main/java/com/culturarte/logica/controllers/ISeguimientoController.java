package com.culturarte.logica.controllers;

import com.culturarte.logica.clases.Seguimiento;
import com.culturarte.logica.clases.Usuario;
import com.culturarte.logica.dtos.DTOSeguimiento;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

import java.util.List;

@WebService
public interface ISeguimientoController {

    @WebMethod
    void registrarSeguimiento(DTOSeguimiento dtoSegui);
    @WebMethod
    int conseguirId(String nick, String nicky);
    @WebMethod
    void cancelarSeguimiento(int idSeguimiento);
    @WebMethod
    List<DTOSeguimiento> listarSeguimientos();
    @WebMethod
    List<String> listarSeguidosDeNick(String nick);
    @WebMethod
    List<String> listarSeguidoresDeNick(String nick);


}
