package com.culturarte.logica.clases;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Colaborador extends Usuario {

    @OneToMany(mappedBy = "colaborador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Colaboracion> colaboraciones = new ArrayList<>();


    public Colaborador(String nickname, String nombre, String apellido, String correo, LocalDate fechaNac, String dirImagen){
        super(nickname, nombre, apellido, correo, fechaNac, dirImagen);
    }
    public Colaborador(){};

    public void addColaboracion(Colaboracion c) {
        if (!colaboraciones.contains(c)) {
            colaboraciones.add(c);
            c.setColaborador(this);
        }
    }

    public List<Colaboracion> getColaboraciones() {
        return colaboraciones;
    }

    public void setColaboraciones(List<Colaboracion> colaboraciones) {
        this.colaboraciones = colaboraciones;
    }





}
