package com.culturarte.logica.clases;

import com.culturarte.logica.enums.ETipoRetorno;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Colaboracion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; //identificador para la colab
    private Integer monto;
    private ETipoRetorno retorno;
    private LocalDate fecha;

    @ManyToOne
    private Propuesta propuesta;
    @ManyToOne
    private Colaborador colaborador;

    // Constructor
    public Colaboracion(Integer monto, ETipoRetorno retorno, LocalDate fecha,
                        Propuesta propuesta, Colaborador colaborador) {
        this.monto = monto;
        this.retorno = retorno;
        this.fecha = fecha;
        this.propuesta = propuesta;
        this.colaborador = colaborador;
    }

    public Colaboracion() {};

    public Integer getId() {return id; }
    public Integer getMonto() { return monto; }
    public ETipoRetorno getRetorno() { return retorno; }
    public LocalDate getFecha() { return fecha; }
    public Propuesta getPropuesta() { return propuesta; }
    public Colaborador getColaborador() { return colaborador; }


    public void setMonto(Integer monto) { this.monto = monto; }
    public void setRetorno(ETipoRetorno retorno) { this.retorno = retorno; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public void setPropuesta(Propuesta propuesta) { this.propuesta = propuesta; }
    public void setColaborador(Colaborador colaborador) { this.colaborador = colaborador; }

}

