package com.culturarte.logica.dtos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.time.LocalDateTime;

import com.culturarte.logica.utiles.LocalDateTimeAdaptador;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DTOConstanciaPago {

    private String plataforma;

    @XmlJavaTypeAdapter(LocalDateTimeAdaptador.class)
    private LocalDateTime fechaEmision;

    private String colaboradorNick;
    private String colaboradorNombre;
    private String colaboradorEmail;
    private String propuestaNombre;
    private Integer monto;

    @XmlJavaTypeAdapter(LocalDateTimeAdaptador.class)
    private LocalDateTime fechaColaboracion;

    public DTOConstanciaPago() {}

    public DTOConstanciaPago(String plataforma, LocalDateTime fechaEmision, String colaboradorNick,
                             String colaboradorNombre, String colaboradorEmail,
                             String propuestaNombre, Integer monto, LocalDateTime fechaColaboracion) {
        this.plataforma = plataforma;
        this.fechaEmision = fechaEmision;
        this.colaboradorNick = colaboradorNick;
        this.colaboradorNombre = colaboradorNombre;
        this.colaboradorEmail = colaboradorEmail;
        this.propuestaNombre = propuestaNombre;
        this.monto = monto;
        this.fechaColaboracion = fechaColaboracion;
    }

    public String getPlataforma() { return plataforma; }
    public void setPlataforma(String plataforma) { this.plataforma = plataforma; }

    public LocalDateTime getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(LocalDateTime fechaEmision) { this.fechaEmision = fechaEmision; }

    public String getColaboradorNick() { return colaboradorNick; }
    public void setColaboradorNick(String colaboradorNick) { this.colaboradorNick = colaboradorNick; }

    public String getColaboradorNombre() { return colaboradorNombre; }
    public void setColaboradorNombre(String colaboradorNombre) { this.colaboradorNombre = colaboradorNombre; }

    public String getColaboradorEmail() { return colaboradorEmail; }
    public void setColaboradorEmail(String colaboradorEmail) { this.colaboradorEmail = colaboradorEmail; }

    public String getPropuestaNombre() { return propuestaNombre; }
    public void setPropuestaNombre(String propuestaNombre) { this.propuestaNombre = propuestaNombre; }

    public Integer getMonto() { return monto; }
    public void setMonto(Integer monto) { this.monto = monto; }

    public LocalDateTime getFechaColaboracion() { return fechaColaboracion; }
    public void setFechaColaboracion(LocalDateTime fechaColaboracion) { this.fechaColaboracion = fechaColaboracion; }
}
