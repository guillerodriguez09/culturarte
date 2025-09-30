package com.culturarte.logica.dtos;

import com.culturarte.logica.clases.Propuesta;

import java.time.LocalDate;
import java.util.List;

public class DTOProponente extends DTOUsuario{

    String direccion;
    String biografia;
    String link;

    List<Propuesta> propuestas;

    public DTOProponente(){}

    public DTOProponente(String nickname, String nombre, String apellido, String contrasenia, String correo, LocalDate fechaNac, String dirImagen, String direccion, String biografia, String link){
        super(nickname, nombre, apellido, contrasenia, correo, fechaNac, dirImagen);
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
