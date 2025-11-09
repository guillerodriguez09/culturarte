package com.culturarte.logica.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DTOPropoPropu implements Serializable {
    private DTOProponente proponente;
    private List<DTOPropuesta> propuestas = new ArrayList<>();

    public DTOPropoPropu() {}

    public DTOPropoPropu(DTOProponente proponente) {
        this.proponente = proponente;
    }

    public DTOProponente getProponente() {
        return proponente;
    }

    public void setProponente(DTOProponente proponente) {
        this.proponente = proponente;
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

