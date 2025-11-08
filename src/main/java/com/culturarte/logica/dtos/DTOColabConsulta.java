package com.culturarte.logica.dtos;

import com.culturarte.logica.enums.ETipoRetorno;
import com.culturarte.logica.utiles.LocalDateTimeAdaptador;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.time.LocalDateTime;
@XmlAccessorType(XmlAccessType.FIELD)
public class DTOColabConsulta {
    private int id;
    private String colaboradorNick;
    private Integer monto;
    private ETipoRetorno retorno;
    @XmlJavaTypeAdapter(LocalDateTimeAdaptador.class)
    private LocalDateTime fecha;
    private String propuestaNombre;

    public DTOColabConsulta() {
    }

    public DTOColabConsulta(int id, String colaboradorNick, Integer monto, ETipoRetorno retorno, LocalDateTime fecha) {
        this.id = id;
        this.colaboradorNick = colaboradorNick;
        this.monto = monto;
        this.retorno = retorno;
        this.fecha = fecha;
    }

    // Getters
    public int getId() { return id; }
    public String getColaboradorNick() { return colaboradorNick; }
    public Integer getMonto() { return monto; }
    public ETipoRetorno getRetorno() { return retorno; }
    public LocalDateTime getFecha() { return fecha; }
    public String getPropuestaNombre() { return propuestaNombre; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setColaboradorNick(String colaboradorNick) { this.colaboradorNick = colaboradorNick; }
    public void setMonto(Integer monto) { this.monto = monto; }
    public void setRetorno(ETipoRetorno retorno) { this.retorno = retorno; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public void setPropuestaNombre(String propuestaNombre) { this.propuestaNombre = propuestaNombre; }

    @Override
    public String toString() {
        return "ID: " + id + " | " + colaboradorNick + " | $" + monto + " | " + fecha;
    }

}

