package com.culturarte.logica.dtos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

import java.time.LocalDate;
@XmlAccessorType(XmlAccessType.FIELD)
public class DTOColaborador extends DTOUsuario {

    public DTOColaborador(String nickname, String nombre, String apellido, String contrasenia, String correo, LocalDate fechaNac, String dirImagen){
        super(nickname, nombre, apellido, contrasenia, correo, fechaNac, dirImagen);
    }
    public DTOColaborador(){};

}
