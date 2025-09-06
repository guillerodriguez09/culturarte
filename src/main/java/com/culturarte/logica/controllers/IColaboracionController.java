package com.culturarte.logica.controllers;

import com.culturarte.logica.dtos.DTOColaboracion;
import java.util.List;

public interface IColaboracionController {
    void registrarColaboracion(String propuestaTitulo, DTOColaboracion dtoColaboracion);
    List<String> listarColaboradoresConColaboraciones();
    List<DTOColaboracion> listarColaboracionesDeColaborador(String colaboradorNick);
    DTOColaboracion consultarColaboracion(String colaboradorNick, String propuestaTitulo);
}