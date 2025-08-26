package com.culturarte.logica.clases;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Colaborador extends Usuario {

    @OneToMany(mappedBy = "colaborador")
    List<Colaboracion> colaboraciones;

    public Colaborador(String nickname, String nombre, String apellido, String correo, LocalDate fechaNac, String dirImagen){
        super(nickname, nombre, apellido, correo, fechaNac, dirImagen);
    }
    public Colaborador(){};


}
