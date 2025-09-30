package com.culturarte.logica.dtos;

import java.time.LocalDate;

public class DTOUsuario {

    String nickname;
    String nombre;
    String apellido;
    String contrasenia;
    String confContrasenia;
    String correo;
    LocalDate fechaNac;
    String dirImagen;


    public DTOUsuario(){}

    public DTOUsuario(String nickname, String nombre, String apellido, String contrasenia, String correo, LocalDate fechaNac, String dirImagen) {
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
    public String getCorreo(){return correo;}
    public LocalDate getFechaNac(){
        return  fechaNac;
    }
    public String getDirImagen(){
        return dirImagen;
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

}
