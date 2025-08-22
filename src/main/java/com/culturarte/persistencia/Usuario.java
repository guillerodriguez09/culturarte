package com.culturarte.persistencia;

import jakarta.persistence.*;
import java.sql.Date; // Importa el tipo Date de sql

@Entity
@Table
public class Usuario {

        @Id private String nickname;
        private String nombre;
        private String apellido;
        private String correo;
        private Date fechaNacimiento;
        private String dirImagen;

        public Usuario(){}
        public Usuario(String nickname, String nombre, String apellido, String correo, Date fechaNacimiento, String dirImagen) {
            this.nickname = nickname;
            this.nombre = nombre;
            this.apellido = apellido;
            this.correo = correo;
            this.fechaNacimiento = fechaNacimiento;
            this.dirImagen = dirImagen;
        }

        public String getNickname() {
            return nickname;
        }
        public String getNombre() {
            return nombre;
        }
        public String getApellido() {
            return apellido;
        }
        public String getCorreo() {
            return correo;
        }
        public Date getFechaNacimiento() {
            return fechaNacimiento;
        }
        public String getDirImagen() {
            return dirImagen;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
        public void setApellido(String apellido) {
            this.apellido = apellido;
        }
        public void setCorreo(String correo) {
            this.correo = correo;
        }
        public void setFechaNacimiento(Date fechaNacimiento) {
            this.fechaNacimiento = fechaNacimiento;
        }
        public void setDirImagen(String dirImagen) {
            this.dirImagen = dirImagen;
        }

}
