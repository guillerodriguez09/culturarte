package com.culturarte.logica.dtos;
import com.culturarte.logica.enums.EEstadoPropuesta;
import com.culturarte.logica.enums.ETipoRetorno;
import com.culturarte.logica.utiles.LocalDateTimeAdaptador;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.time.LocalDateTime;
@XmlAccessorType(XmlAccessType.FIELD)
public class DTOColaboracion {
    public String propuestaTitulo;
    public String colaboradorNick;
    public Integer monto;
    public ETipoRetorno retorno;
    @XmlJavaTypeAdapter(LocalDateTimeAdaptador.class)
    public LocalDateTime fecha;

    public DTOColaboracion() {
    }

    public void setColaboradorNick(String colaboradorNick) {
        this.colaboradorNick = colaboradorNick;
    }
    public void setMonto(Integer monto) {
        this.monto = monto;
    }
    public void setRetorno(ETipoRetorno retorno) {
        this.retorno = retorno;
    }
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
    public void setPropuestaTitulo(String propuestaTitulo) {
        this.propuestaTitulo = propuestaTitulo;
    }



}

