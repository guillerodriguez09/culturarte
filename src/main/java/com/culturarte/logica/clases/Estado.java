package com.culturarte.logica.clases;

import com.culturarte.logica.enums.EEstadoPropuesta;

import java.time.LocalDate;

public class Estado {
    private EEstadoPropuesta nombre;  // ingresada, publicada, etc.
    private LocalDate fecha;    // fecha en que pasó a este estado

    // Constructor
    public Estado(EEstadoPropuesta nombre, LocalDate fecha) {
        this.nombre = nombre;
        this.fecha = fecha;
    }
//no se si esta bien
    public Estado() {
        this.nombre = EEstadoPropuesta.INGRESADA;
        this.fecha = LocalDate.now();
    }

    public EEstadoPropuesta getNombre() {
        return nombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setNombre(EEstadoPropuesta nombre) {
        this.nombre = nombre;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return nombre + " (" + fecha + ")";
    }
}

