package com.culturarte.logica.controllers;

import com.culturarte.logica.clases.Seguimiento;
import com.culturarte.logica.dtos.DTOSeguimiento;

import java.util.List;


public interface ISeguimientoController {

    void registrarSeguimiento(DTOSeguimiento dtoSegui);
    int conseguirId(String nick, String nicky);
    void cancelarSeguimiento(int idSeguimiento);
    List<DTOSeguimiento> listarSeguimientos();


}
