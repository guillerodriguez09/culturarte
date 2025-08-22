package com.culturarte.logica.clases;

import java.time.LocalDate;

public class Colaborador extends Usuario {

    public Colaborador(String nickname, String nombre, String apellido, String correo, LocalDate fechaNac, String dirImagen){
        super(nickname, nombre, apellido, correo, fechaNac, dirImagen);
    }


}
