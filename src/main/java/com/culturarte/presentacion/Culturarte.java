package com.culturarte.presentacion;

import com.culturarte.logica.clases.*;
import com.culturarte.logica.controllers.*;
import com.culturarte.persistencia.*;
import com.culturarte.logica.fabrica.Fabrica;
import com.culturarte.logica.enums.EEstadoPropuesta;
import com.culturarte.logica.enums.ETipoRetorno;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.ArrayList;

public class Culturarte {
    public static void main(String[] args) {

        /*System.out.println("Hello and welcome!");
        System.out.println(" ");
        System.out.println("Chivito");

        Categoria cat = new Categoria("musical");

        Usuario u = new Usuario("Crepi", "Luca", "Crespi", "CrepsiPerrera@gmail.com", LocalDate.of(2004, 10, 28), "C:\\Users\\Chorizo-Cosmico\\Pictures\\Hellmo.jpg");

        System.out.println(u.getNick() + " " + u.getNombre() + " " +  u.getApellido() + " " + u.getCorreo() + " " + u.getFechaNac() + " " + u.getDirImagen());

        Proponente p = new Proponente("Andre", "Andres", "Ferreira", "AndreFerreteria@gmail.com", LocalDate.of(2004, 1, 12), "C:\\Users\\Chorizo-Cosmico\\Pictures\\KeeperOoh.png", "Abajo de un puente", "El mate es la fuente de su vida", "Nose");

        System.out.println(p.getNick() + " " + p.getNombre() + " " +  p.getApellido() + " " + p.getCorreo() + " " + p.getFechaNac() + " " + p.getDirImagen() + " " + p.getDireccion() + " " + p.getBiografia() + " " + p.getLink());

        Colaborador c = new Colaborador("Fede", "Federico", "Valdez", "FedeBaldes@gmail.com", LocalDate.of(2003, 7, 10), "C:\\Users\\Chorizo-Cosmico\\Pictures\\sesi.jpg");

        System.out.println(c.getNick() + " " + c.getNombre() + " " +  c.getApellido() + " " + c.getCorreo() + " " + c.getFechaNac() + " " + c.getDirImagen());


        Colaborador juan = new Colaborador("juan", "Juan", "Pérez", "juan@mail.com", LocalDate.of(2000, 5, 12), "C:\\Users\\blabla\\Pictures\\sesi.jpg");
        Colaboracion c1 = new Colaboracion(2000, ETipoRetorno.ENTRADAS_GRATIS, LocalDate.now(), prop, juan);

        prop.addColaboracion(c1);

        System.out.println("Total colaboraciones en propuesta: " + prop.getColaboraciones().size());
        System.out.println("Recaudado: " + c1.getMonto());

        */
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CulturartePU");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Usuario u = new Usuario();
            u.setNick("guillerod");
            u.setNombre("Guille");
            u.setApellido("Rodriguez");
            u.setCorreo("rodgui@gmail.com");
            u.setFechaNac(LocalDate.of(2004, 05, 25));
            u.setDirImagen("kit.png");

            em.persist(u);
            em.getTransaction().commit();

            System.out.println("Usuario guardado en la BD");

            Usuario encontrado = em.find(Usuario.class, "guillerod");
            System.out.println("Recuperado: " + encontrado.getNombre() + " " + encontrado.getApellido());
        } finally {
            em.close();
            emf.close();
        }
    }


    }
