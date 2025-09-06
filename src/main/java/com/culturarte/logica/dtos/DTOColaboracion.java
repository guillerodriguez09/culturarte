package com.culturarte.logica.dtos;

import com.culturarte.logica.enums.ETipoRetorno;

import java.time.LocalDate;

public class DTOColaboracion {

    private String colaboradorNick;
    private double monto;
    private ETipoRetorno tipoRetorno;
    private LocalDate fecha;

    public DTOColaboracion() {}

    public String getColaboradorNick() { return colaboradorNick; }
    public void setColaboradorNick(String colaboradorNick) { this.colaboradorNick = colaboradorNick; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public ETipoRetorno getTipoRetorno() { return tipoRetorno; }
    public void setTipoRetorno(ETipoRetorno tipoRetorno) { this.tipoRetorno = tipoRetorno; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
}