package com.culturarte.logica.controllers;

import com.culturarte.logica.clases.Proponente;
import com.culturarte.logica.dtos.DTOProponente;
import com.culturarte.logica.enums.EEstadoPropuesta;

import java.util.List;

public interface IProponenteController {

    void altaProponente(DTOProponente dtoProponente);

    List<Object[]> obtenerTodPropConPropu (String nick);

    List<String> listarProponentes();

    DTOProponente obtenerProponente(String nick);

    List<Object[]> obtenerPropConPropuYEstado (EEstadoPropuesta estado, String nick);

}
