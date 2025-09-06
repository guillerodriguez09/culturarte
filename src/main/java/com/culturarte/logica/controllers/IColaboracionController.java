package com.culturarte.logica.controllers;

import com.culturarte.logica.dtos.DTOColaboracion;
import java.util.List;

public interface IColaboracionController {
    void registrarColaboracion(String propuestaTitulo, DTOColaboracion dtoColaboracion);

}