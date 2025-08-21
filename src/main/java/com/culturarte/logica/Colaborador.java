package com.culturarte.logica;

import java.time.LocalDate;

public class Colaborador extends Usuario {

    public Colaborador(String nickame, String nombre, String apellido, String correo, LocalDate fechaNac, String dirImagen){
        super(nickame, nombre, apellido, correo, fechaNac, dirImagen);
    }

}
