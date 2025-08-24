package com.culturarte.logica.dtos;

import com.culturarte.logica.enums.ETipoRetorno;

import java.time.LocalDate;
import java.util.List;

public class DTOPropuesta {

    public String titulo;
    public String descripcion;
    public String lugar;
    public LocalDate fecha;
    public Integer precioEntrada;
    public Integer montoAReunir;
    public LocalDate fechaPublicacion;
    public String imagen;

    public String proponenteNick;
    public String categoriaNombre;

    public List<ETipoRetorno> retornos;

    public String estadoActual;

        public DTOPropuesta() {}

        // getters
        public String getTitulo() { return titulo; }
        public String getDescripcion() { return descripcion; }
        public String getLugar() { return lugar; }
        public LocalDate getFecha() { return fecha; }
        public Integer getPrecioEntrada() { return precioEntrada; }
        public Integer getMontoAReunir() { return montoAReunir; }
        public String getImagen() { return imagen; }
        public String getProponenteNick() { return proponenteNick; }
        public String getCategoria() { return categoriaNombre; }
        public List<ETipoRetorno> getRetornos() { return retornos; }
        public LocalDate getFechaPublicacion() { return fechaPublicacion; }
        public String getEstadoActual() { return estadoActual; }

        // setters
        public void setTitulo(String titulo) { this.titulo = titulo; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
        public void setLugar(String lugar) { this.lugar = lugar; }
        public void setFecha(LocalDate fecha) { this.fecha = fecha; }
        public void setPrecioEntrada(Integer precioEntrada) { this.precioEntrada = precioEntrada; }
        public void setMontoAReunir(Integer montoAReunir) { this.montoAReunir = montoAReunir; }
        public void setImagen(String imagen) { this.imagen = imagen; }
        public void setProponenteNick(String proponenteNick) { this.proponenteNick = proponenteNick; }
        public void setCategoriaNombre(String categoriaNombre) { this.categoriaNombre = categoriaNombre; }
        public void setRetornos(List<ETipoRetorno> retornos) { this.retornos = retornos; }
        public void setFechaPublicacion(LocalDate fechaPublicacion) { this.fechaPublicacion = fechaPublicacion; }
        public void setEstadoActual(String estadoActual) { this.estadoActual = estadoActual; }
    }





