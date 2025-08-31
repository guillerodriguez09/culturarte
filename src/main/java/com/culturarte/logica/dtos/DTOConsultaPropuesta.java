package com.culturarte.logica.dtos;

import java.time.LocalDate;
import java.util.List;

public class DTOConsultaPropuesta {

    public String titulo;
    public String descripcion;
    public String lugar;
    public LocalDate fecha;
    public Integer precioEntrada;
    public Integer montoAReunir;
    public String imagen;

    public String estado;
    public List<String> colaboradores;
    public Double montoRecaudado;


    public String categoriaNombre;
    public String proponenteNick;

    public DTOConsultaPropuesta() {
    }

    public DTOConsultaPropuesta(String titulo, String descripcion, String lugar,
                                LocalDate fecha, Integer precioEntrada, Integer montoAReunir,
                                String imagen, String estado, List<String> colaboradores,
                                Double montoRecaudado, String categoriaNombre, String proponenteNick) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.lugar = lugar;
        this.fecha = fecha;
        this.precioEntrada = precioEntrada;
        this.montoAReunir = montoAReunir;
        this.imagen = imagen;
        this.estado = estado;
        this.colaboradores = colaboradores;
        this.montoRecaudado = montoRecaudado;
        this.categoriaNombre = categoriaNombre;
        this.proponenteNick = proponenteNick;
    }
}

