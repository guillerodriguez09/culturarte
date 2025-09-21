package com.culturarte.logica.dtos;

import com.culturarte.logica.enums.ETipoRetorno;

import java.time.LocalDateTime;

public class DTOColabConsulta {
    private int id;
    private String colaboradorNick;
    private Integer monto;
    private ETipoRetorno retorno;
    private LocalDateTime fecha;

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

    // Setters
    public void setId(int id) { this.id = id; }
    public void setColaboradorNick(String colaboradorNick) { this.colaboradorNick = colaboradorNick; }
    public void setMonto(Integer monto) { this.monto = monto; }
    public void setRetorno(ETipoRetorno retorno) { this.retorno = retorno; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }


    @Override
    public String toString() {
        return "ID: " + id + " | " + colaboradorNick + " | $" + monto + " | " + fecha;
    }

}

