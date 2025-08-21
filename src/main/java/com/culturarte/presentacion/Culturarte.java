package com.culturarte.presentacion;

import com.culturarte.logica.Colaborador;
import com.culturarte.logica.Proponente;
import com.culturarte.logica.Usuario;

import java.time.LocalDate;

public class Culturarte {
    public static void main(String[] args) {

        System.out.println("Hello and welcome!");
        System.out.println(" ");
        System.out.println("Chivito");

        Usuario u = new Usuario("Crepi", "Luca", "Crespi", "CrepsiPerrera@gmail.com", LocalDate.of(2004, 10, 28), "C:\\Users\\Chorizo-Cosmico\\Pictures\\Hellmo.jpg");

        System.out.println(u.getNick() + " " + u.getNombre() + " " +  u.getApellido() + " " + u.getCorreo() + " " + u.getFechaNac() + " " + u.getDirImagen());

        Proponente p = new Proponente("Andre", "Andres", "Ferreira", "AndreFerreteria@gmail.com", LocalDate.of(2004, 1, 12), "C:\\Users\\Chorizo-Cosmico\\Pictures\\KeeperOoh.png", "Abajo de un puente", "El mate es la fuente de su vida", "Nose");

        System.out.println(p.getNick() + " " + p.getNombre() + " " +  p.getApellido() + " " + p.getCorreo() + " " + p.getFechaNac() + " " + p.getDirImagen() + " " + p.getDireccion() + " " + p.getBiografia() + " " + p.getLink());

        Colaborador c = new Colaborador("Fede", "Federico", "Valdez", "FedeBaldes@gmail.com", LocalDate.of(2003, 07, 10), "C:\\Users\\Chorizo-Cosmico\\Pictures\\sesi.jpg");

        System.out.println(c.getNick() + " " + c.getNombre() + " " +  c.getApellido() + " " + c.getCorreo() + " " + c.getFechaNac() + " " + c.getDirImagen());

    }
}