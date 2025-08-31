package com.culturarte.logica.controllers;

import com.culturarte.logica.clases.Proponente;
import com.culturarte.logica.dtos.DTOProponente;
import java.util.List;

public interface IProponenteController {

    void altaProponente(DTOProponente dtoProponente);

    List<Object[]> obtenerTodPropConPropu (String nick);

    List<String> listarProponentes();
}
