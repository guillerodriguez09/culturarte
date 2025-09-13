package com.culturarte.logica.clases;

import com.culturarte.logica.enums.EEstadoPropuesta;
import jakarta.persistence.*;

import java.time.LocalDate;
@Entity
public class Estado {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EEstadoPropuesta nombre;  // ingresada, publicada, etc
    private LocalDate fecha;    // fecha en que pasó a este estado

    @ManyToOne
    private Propuesta propuesta;

    // Constructor
    public Estado(EEstadoPropuesta nombre, LocalDate fecha) {
        this.nombre = nombre;
        this.fecha = fecha;
    }
    public Estado() {
    };

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

    public Propuesta getPropuesta() {
        return propuesta;
    }

    public void setPropuesta(Propuesta propuesta) {
        this.propuesta = propuesta;
    }

    @Override
    public String toString() {
        return nombre + " (" + fecha + ")";
    }
}

