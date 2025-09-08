package com.culturarte.logica.controllers;

import com.culturarte.logica.clases.Seguimiento;
import com.culturarte.logica.dtos.DTOSeguimiento;

import java.util.List;


public interface ISeguimientoController {

    void registrarSeguimiento(DTOSeguimiento dtoSegui);
    void cancelarSeguimiento(int idSeguimiento);
    public List<DTOSeguimiento> listarSeguimientos();


}
