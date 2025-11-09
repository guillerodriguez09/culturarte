package com.culturarte.logica.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class DTOColPropu implements Serializable {
    private DTOColaborador colaborador;
    private List<DTOPropuesta> propuestas = new ArrayList<>();

    public DTOColPropu() {
    }

    public DTOColPropu(DTOColaborador colaborador) {
        this.colaborador = colaborador;
    }

    public DTOColaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(DTOColaborador colaborador) {
        this.colaborador = colaborador;
    }

    public List<DTOPropuesta> getPropuestas() {
        return propuestas;
    }

    public void setPropuestas(List<DTOPropuesta> propuestas) {
        this.propuestas = propuestas;
    }

    public void addPropuesta(DTOPropuesta p) {
        this.propuestas.add(p);
    }
}

