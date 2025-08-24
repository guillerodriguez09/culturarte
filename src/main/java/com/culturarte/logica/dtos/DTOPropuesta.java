package com.culturarte.logica.dtos;

import com.culturarte.logica.clases.Categoria;
import com.culturarte.logica.clases.Estado;
import com.culturarte.logica.clases.Proponente;
import com.culturarte.logica.enums.ETipoRetorno;
import java.time.LocalDate;
import java.util.ArrayList;

public class DTOPropuesta {
    private Proponente proponenteNick;
    private Categoria categoriaNombre;
    private String titulo;
    private String descripcion;
    private String lugar;
    private LocalDate fecha;
    private Integer precioEntrada;
    private Integer montoAReunir;
    private LocalDate fechaPublicacion;
    private ETipoRetorno tipoRetorno;

    // Constructor

    public DTOPropuesta(Proponente proponente, Categoria categoria, String titulo, String descripcion,
                        String lugar, LocalDate fecha, Integer precioEntrada, Integer montoAReunir,
                        LocalDate fechaPublicacion, ETipoRetorno tipoRetorno) {
        this.proponenteNick = proponente;
        this.categoriaNombre = categoria;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.lugar = lugar;
        this.fecha = fecha;
        this.precioEntrada = precioEntrada;
        this.montoAReunir = montoAReunir;
        this.fechaPublicacion = fechaPublicacion;
        this.tipoRetorno = tipoRetorno;
    }

    public Proponente getProponente() { return proponenteNick; }
    public Categoria getCategoria() { return categoriaNombre; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public String getLugar() { return lugar; }
    public LocalDate getFecha() { return fecha; }
    public Integer getPrecioEntrada() { return precioEntrada; }
    public Integer getMontoAReunir() { return montoAReunir; }
    public LocalDate getFechaPublicacion() { return fechaPublicacion; }
    public ETipoRetorno getTipoRetorno() { return tipoRetorno; }
}