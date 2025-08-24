package com.culturarte.logica.controllers;

import com.culturarte.logica.clases.Propuesta;
import com.culturarte.logica.dtos.DTOPropuesta;
import java.util.ArrayList;
import com.culturarte.logica.enums.EEstadoPropuesta;
public interface IPropuestaController {

    void altaPropuesta(DTOPropuesta dtoPropuesta);
    ArrayList<Propuesta> listarPropuestasPorEstado(EEstadoPropuesta estado);
}
