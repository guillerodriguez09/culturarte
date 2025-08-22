package com.culturarte.logica.clases;

import com.culturarte.logica.enums.ETipoRetorno;

import java.time.LocalDate;
import java.util.ArrayList;


public class Propuesta {

    private String titulo;
    private String descripcion;
    private String imagen; // opcional
    private String lugar;
    private LocalDate fecha;
    private Integer precioEntrada;
    private Integer montoAReunir;
    private LocalDate fechaPublicacion;

    private Categoria categoria;
    private ArrayList<Colaboracion> colaboraciones;
    private Estado estadoActual;
    private ArrayList<Estado> historialEstados;
    private Proponente proponente;
    private ArrayList<ETipoRetorno> retornos;

    // Constructor
    public Propuesta(Categoria categoria, Proponente proponente, String titulo, String descripcion, String lugar, LocalDate fecha,
                     Integer precioEntrada, Integer montoAReunir, LocalDate fechaPublicacion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.lugar = lugar;
        this.fecha = fecha;
        this.precioEntrada = precioEntrada;
        this.montoAReunir = montoAReunir;
        this.fechaPublicacion = fechaPublicacion;
        this.categoria = categoria;
        this.historialEstados = new ArrayList<>();
        this.colaboraciones = new ArrayList<>();
        this.estadoActual = new Estado();
        this.proponente = proponente;
    }

    // getters
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public String getImagen() { return imagen; }
    public String getLugar() { return lugar; }
    public LocalDate getFecha() { return fecha; }
    public Integer getPrecioEntrada() { return precioEntrada; }
    public Integer getMontoAReunir() { return montoAReunir; }
    public LocalDate getFechaPublicacion() { return fechaPublicacion; }
    public Categoria getCategoria() { return categoria; }
    public ArrayList<Colaboracion> getColaboraciones() { return colaboraciones; }
    public Estado getEstadoActual() { return estadoActual; }
    public ArrayList<Estado> getHistorialEstados() { return historialEstados; }
    public Proponente getProponente() { return proponente; }
    //public ArrayList<ETipoRetorno> getRetornos() { return retornos; }


    //setters
    public void setImagen(String imagen) { this.imagen = imagen; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setLugar(String lugar) { this.lugar = lugar; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public void setPrecioEntrada(Integer precioEntrada) { this.precioEntrada = precioEntrada; }
    public void setMontoAReunir(Integer montoAReunir) { this.montoAReunir = montoAReunir; }
    public void setFechaPublicacion(LocalDate fechaPublicacion) { this.fechaPublicacion = fechaPublicacion; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
    public void setProponente(Proponente proponente) { this.proponente = proponente; }
    //public void setRetornos(ArrayList<ETipoRetorno> retornos) {  this.retornos = retornos; }

    public void addColaboracion(Colaboracion colab) {
        colaboraciones.add(colab);
    }

    public void cambiarEstado(Estado nuevoEstado) {
        this.estadoActual = nuevoEstado;
        this.historialEstados.add(nuevoEstado);
    }
}
