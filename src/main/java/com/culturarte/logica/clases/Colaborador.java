package com.culturarte.logica.clases;

import jakarta.persistence.Entity;

import java.time.LocalDate;
@Entity
public class Colaborador extends Usuario {

    public Colaborador(String nickname, String nombre, String apellido, String correo, LocalDate fechaNac, String dirImagen){
        super(nickname, nombre, apellido, correo, fechaNac, dirImagen);
    }
    public Colaborador(){};


}
