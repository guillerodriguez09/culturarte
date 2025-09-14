package com.culturarte.logica.clases;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Proponente extends Usuario {

    String direccion;

    @Lob
    String biografia;

    String link;
    @OneToMany(mappedBy = "proponente") // importante el mappedBy
    List<Propuesta> propuestas;


    public Proponente(){}

    public Proponente(String nickname, String nombre, String apellido, String correo, LocalDate fechaNac, String dirImagen, String direccion, String biografia, String link){
        super(nickname, nombre, apellido, correo, fechaNac, dirImagen);
        this.direccion = direccion;
        this.biografia = biografia;
        this.link = link;
    }

    public String getDireccion() {
        return direccion;
    }
    public String getBiografia() {
        return biografia;
    }
    public String getLink() {
        return link;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }
    public void setLink(String link) {
        this.link = link;
    }

}
