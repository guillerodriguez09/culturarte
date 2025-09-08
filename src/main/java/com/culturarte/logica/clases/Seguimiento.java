package com.culturarte.logica.clases;

import jakarta.persistence.*;

@Entity
@Table
public class Seguimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int Id;

    @ManyToOne
    @JoinColumn(name = "USUARIO_NICKNAME")  // fk
    private Usuario usuarioSeguidor;

    private String usuarioSeguido;

    public Seguimiento(){}

    public Seguimiento(Usuario usarioSeguidoor, String usuarioSeguido){
        this.usuarioSeguidor = usarioSeguidoor;
        this.usuarioSeguido = usuarioSeguido;
    }

    public int getId(){return Id;}
    public Usuario getUsuarioSeguidor(){
        return usuarioSeguidor;
    }
    public String getUsuarioSeguido(){
        return usuarioSeguido;
    }

    public void setId(int id){this.Id = id;}
    public void setUsuarioSeguidor(Usuario  usuarioSeguidor){
        this.usuarioSeguidor = usuarioSeguidor;
    }
    public void setUsuarioSeguido(String usuarioSeguido){
        this.usuarioSeguido = usuarioSeguido;
    }

}
