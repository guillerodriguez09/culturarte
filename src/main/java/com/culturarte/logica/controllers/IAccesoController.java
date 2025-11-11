package com.culturarte.logica.controllers;

import com.culturarte.logica.dtos.DTOAcceso;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

import java.util.List;

@WebService
public interface IAccesoController {
    @WebMethod
    void registrarAcceso(DTOAcceso acceso);

    @WebMethod
    List<DTOAcceso> listarAccesos();
}
