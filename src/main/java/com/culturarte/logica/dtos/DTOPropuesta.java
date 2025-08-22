package com.culturarte.logica.dtos;


import com.culturarte.logica.enums.ETipoRetorno;

import java.time.LocalDate;
import java.util.ArrayList;

public class DTOPropuesta {
    public String proponenteNick;
    public String categoriaNombre;
    public String titulo, descripcion, lugar;
    public LocalDate fecha;
    public Integer precioEntrada, montoAReunir;
}


