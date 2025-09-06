package com.culturarte.logica.controllers;

import com.culturarte.logica.dtos.DTOColaboracion;
import com.culturarte.logica.dtos.DTOColaborador;
import com.culturarte.logica.dtos.DTOPropuesta;

import java.time.LocalDate;

public class ColaboracionController implements IColaboracionController {

    private final IPropuestaController propuestaController;
    private final IColaboradorController colaboradorController;

    public ColaboracionController(IPropuestaController propCtrl, IColaboradorController colCtrl) {
        this.propuestaController = propCtrl;
        this.colaboradorController = colCtrl;
    }

    @Override
    public void registrarColaboracion(String tituloPropuesta, DTOColaboracion dto) {

        DTOPropuesta propuesta = propuestaController.consultarPropuesta(tituloPropuesta);
        DTOColaborador colaborador = colaboradorController.obtenerColaborador(dto.getColaboradorNick());

        if (propuesta == null) {
            throw new IllegalArgumentException("La propuesta no existe.");
        }
        if (colaborador == null) {
            throw new IllegalArgumentException("El colaborador no existe.");
        }
        if (dto.getMonto() <= 0) {
            throw new IllegalArgumentException("El monto de la colaboración debe ser mayor que 0.");
        }
        if (dto.getTipoRetorno() == null) {
            throw new IllegalArgumentException("Debe indicarse un tipo de retorno.");
        }

        dto.setFecha(LocalDate.now());

        System.out.println("Colaboración registrada:");
        System.out.println("- Propuesta: " + propuesta.getTitulo());
        System.out.println("- Colaborador: " + colaborador.getNick());
        System.out.println("- Monto: " + dto.getMonto());
        System.out.println("- Tipo retorno: " + dto.getTipoRetorno());
        System.out.println("- Fecha: " + dto.getFecha());
    }
}

