package com.culturarte.logica.clases;

import jakarta.persistence.*;
import com.culturarte.logica.clases.Seguimiento;
import jakarta.xml.bind.annotation.XmlSeeAlso;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "USUARIO", uniqueConstraints = @UniqueConstraint(columnNames = "nickname"))
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Usuario {

    @Id
    String nickname;
    String nombre;
    String apellido;
    String contrasenia;
    String correo;
    LocalDate fechaNac;
    String dirImagen;
    boolean eliminado;
    LocalDate fechaEliminacion;

    @OneToMany(mappedBy = "usuarioSeguidor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seguimiento> usuariosSeguidos = new ArrayList<>();

    public Usuario(){}

    public Usuario(String nickname, String nombre, String apellido,String contrasenia, String correo, LocalDate fechaNac, String dirImagen) {
        this.nickname = nickname;
        this.nombre = nombre;
        this.apellido = apellido;
        this.contrasenia = contrasenia;
        this.correo = correo;
        this.fechaNac = fechaNac;
        this.dirImagen = dirImagen;
    }

    public String getNick(){
        return nickname;
    }
    public String getNombre(){
        return nombre;
    }
    public String getApellido(){
        return apellido;
    }
    public String getContrasenia(){ return contrasenia; }
    public String getCorreo(){
        return correo;
    }
    public LocalDate getFechaNac(){
        return  fechaNac;
    }
    public String getDirImagen(){
        return dirImagen;
    }
    public boolean getEliminado(){ return eliminado; }
    public LocalDate getFechaEliminacion(){ return fechaEliminacion; }
    public List<Seguimiento> getUsuariosSeguidos() {
        return usuariosSeguidos;
    }

    public void setNick(String nickname){
        this.nickname = nickname;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public void setApellido(String apellido){
        this.apellido = apellido;
    }
    public void setContrasenia(String contrasenia){ this.contrasenia = contrasenia; }
    public void setCorreo(String correo){
        this.correo = correo;
    }
    public void setFechaNac(LocalDate fechaNac){
        this.fechaNac = fechaNac;
    }
    public void setDirImagen(String dirImagen){
        this.dirImagen = dirImagen;
    }
    public void setEliminado(boolean eliminado){ this.eliminado = eliminado; }
    public void setFechaEliminacion(LocalDate fechaEliminacion){ this.fechaEliminacion = fechaEliminacion; }

    @Override
    public String toString() {
        return this != null ? this.getNick() : getNick();
    }

}
