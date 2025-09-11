package com.culturarte.logica.dtos;

import com.culturarte.logica.clases.Seguimiento;
import com.culturarte.logica.clases.Usuario;

public class DTOSeguimiento {

    private int Id;
    private Usuario usuarioSeguidor;
    private String usuarioSeguido;

    public DTOSeguimiento(){}

    public DTOSeguimiento(Usuario usuarioSeguidor, String usuarioSeguido){
        this.usuarioSeguidor = usuarioSeguidor;
        this.usuarioSeguido = usuarioSeguido;
    }

    public int getId(){return Id;}
    public Usuario getUsuarioSeguidor(){return usuarioSeguidor;}
    public String getUsuarioSeguido(){return usuarioSeguido;}

    public void setUsuarioSeguidor(Usuario usuarioSeguidor){this.usuarioSeguidor = usuarioSeguidor;}
    public void setUsuarioSeguido(String usuarioSeguido){this.usuarioSeguido = usuarioSeguido;}

}
