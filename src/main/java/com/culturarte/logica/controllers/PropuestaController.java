package com.culturarte.logica.controllers;

import com.culturarte.logica.clases.*;
import com.culturarte.logica.dtos.DTOPropuesta;
import com.culturarte.logica.enums.EEstadoPropuesta;
import java.util.ArrayList;
import java.time.LocalDate;

public class PropuestaController implements IPropuestaController {

    private ArrayList<Propuesta> propuestas = new ArrayList<>();

    public void altaPropuesta(DTOPropuesta dtoPropuesta) {
        Propuesta nueva = new Propuesta(
                dtoPropuesta.getCategoria(),
                dtoPropuesta.getProponente(),
                dtoPropuesta.getTitulo(),
                dtoPropuesta.getDescripcion(),
                dtoPropuesta.getLugar(),
                dtoPropuesta.getFecha(),
                dtoPropuesta.getPrecioEntrada(),
                dtoPropuesta.getMontoAReunir(),
                dtoPropuesta.getFechaPublicacion()
        );
        propuestas.add(nueva);
    }

    public ArrayList<Propuesta> listarPropuestasPorEstado(EEstadoPropuesta estado) {
        ArrayList<Propuesta> resultado = new ArrayList<>();
        for (Propuesta p : propuestas) {
            if (p.getEstadoActual().getNombre().equals(estado)) {
                resultado.add(p);
            }
        }
        return resultado;
    }
}
