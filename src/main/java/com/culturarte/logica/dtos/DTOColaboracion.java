package com.culturarte.logica.dtos;
import com.culturarte.logica.enums.EEstadoPropuesta;
import com.culturarte.logica.enums.ETipoRetorno;

import java.time.LocalDateTime;

public class DTOColaboracion {
    public String propuestaTitulo;
    public String colaboradorNick;
    public Integer monto;
    public ETipoRetorno retorno;
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