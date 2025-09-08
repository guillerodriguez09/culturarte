package com.culturarte.logica.controllers;

import com.culturarte.logica.dtos.DTOColabConsulta;
import com.culturarte.logica.dtos.DTOColaboracion;

import java.util.List;

public interface IColaboracionController {
    public void registrarColaboracion (DTOColaboracion dtoColaboracion);
    public void cancelarColaboracion(int idColaboracion);
    public List<DTOColabConsulta> listarColaboraciones();

}