package com.culturarte.logica.dtos;

import java.time.LocalDate;

public class DTOColaborador extends DTOUsuario {

    public DTOColaborador(String nickname, String nombre, String apellido, String contrasenia, String confContrasenia, String correo, LocalDate fechaNac, String dirImagen){
        super(nickname, nombre, apellido, contrasenia, confContrasenia, correo, fechaNac, dirImagen);
    }
    public DTOColaborador(){};

}
