package com.culturarte.logica.controllers;

import com.culturarte.logica.dtos.DTOColabConsulta;
import com.culturarte.logica.dtos.DTOColaboracion;
import com.culturarte.logica.dtos.DTOConstanciaPago;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

import java.util.List;
@WebService
public interface IColaboracionController {
    @WebMethod
    public void registrarColaboracion (DTOColaboracion dtoColaboracion);
    @WebMethod
    public void cancelarColaboracion(int idColaboracion);
    @WebMethod
    public List<DTOColabConsulta> listarColaboraciones();
    @WebMethod
    public List<DTOColabConsulta> consultarColaboracionesPorColaborador(String colaboradorNick);
    @WebMethod
    DTOConstanciaPago emitirConstanciaPago(int idColaboracion);
}
