package com.culturarte.logica.dtos;

import com.culturarte.logica.clases.Seguimiento;
import com.culturarte.logica.clases.Usuario;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class DTOSeguimiento {

    private int Id;
    private DTOUsuario usuarioSeguidor;
    private String usuarioSeguido;

    public DTOSeguimiento(){}

    public DTOSeguimiento(DTOUsuario usuarioSeguidor, String usuarioSeguido){
        this.usuarioSeguidor = usuarioSeguidor;
        this.usuarioSeguido = usuarioSeguido;
    }

    public int getId(){return Id;}
    public DTOUsuario getUsuarioSeguidor(){return usuarioSeguidor;}
    public String getUsuarioSeguido(){return usuarioSeguido;}

    public void setUsuarioSeguidor(DTOUsuario usuarioSeguidor){this.usuarioSeguidor = usuarioSeguidor;}
    public void setUsuarioSeguido(String usuarioSeguido){this.usuarioSeguido = usuarioSeguido;}

}
