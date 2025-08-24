package com.culturarte.presentacion;

import com.culturarte.logica.clases.*;
import com.culturarte.logica.controllers.*;
import com.culturarte.persistencia.*;
import com.culturarte.logica.dtos.*;
import com.culturarte.logica.fabrica.Fabrica;
import com.culturarte.logica.enums.EEstadoPropuesta;
import com.culturarte.logica.enums.ETipoRetorno;

import java.time.LocalDate;
import java.util.List;

public class Culturarte {
    public static void main(String[] args) {

        Categoria cat = new Categoria("diversion");

        Usuario u = new Usuario("Crepi", "Luca", "Crespi", "CrepsiPerrera@gmail.com", LocalDate.of(2004, 10, 28), "C:\\Users\\Chorizo-Cosmico\\Pictures\\Hellmo.jpg");

        System.out.println(u.getNick() + " " + u.getNombre() + " " + u.getApellido() + " " + u.getCorreo() + " " + u.getFechaNac() + " " + u.getDirImagen());

        Proponente p = new Proponente("Andre", "Andres", "Ferreira", "AndreFerreteria@gmail.com", LocalDate.of(2004, 1, 12), "C:\\Users\\Chorizo-Cosmico\\Pictures\\KeeperOoh.png", "Abajo de un puente", "El mate es la fuente de su vida", "Nose");

        // System.out.println(p.getNick() + " " + p.getNombre() + " " +  p.getApellido() + " " + p.getCorreo() + " " + p.getFechaNac() + " " + p.getDirImagen() + " " + p.getDireccion() + " " + p.getBiografia() + " " + p.getLink());

        Colaborador c = new Colaborador("Fede", "Federico", "Valdez", "FedeBaldes@gmail.com", LocalDate.of(2003, 7, 10), "C:\\Users\\Chorizo-Cosmico\\Pictures\\sesi.jpg");

        // System.out.println(c.getNick() + " " + c.getNombre() + " " +  c.getApellido() + " " + c.getCorreo() + " " + c.getFechaNac() + " " + c.getDirImagen());


        Colaborador juan = new Colaborador("juan", "Juan", "Pérez", "juan@mail.com", LocalDate.of(2000, 5, 12), "C:\\Users\\blabla\\Pictures\\sesi.jpg");
        //Colaboracion c1 = new Colaboracion(2000, ETipoRetorno.ENTRADAS_GRATIS, LocalDate.now(), prop, juan);

        //prop.addColaboracion(c1);


        DTOPropuesta dto = new DTOPropuesta(); // este dto va a venir desde swing en futuro
        dto.titulo = "abc";
        dto.descripcion = "Gjee";
        dto.lugar = "Montevideo";
        dto.fecha = LocalDate.of(2025, 9, 10);
        dto.precioEntrada = 500;
        dto.montoAReunir = 1000;
        dto.imagen = null; //falta implementar tema imagenes
        dto.categoriaNombre = cat.getNombre();
        dto.proponenteNick = p.getNick();
        dto.retornos = List.of(ETipoRetorno.ENTRADAS_GRATIS, ETipoRetorno.PORCENTAJE_GANANCIAS);

        IPropuestaController controllerP = Fabrica.getInstancia().getPropuestaController();//trae una interfaz del controlador de propuesta
        controllerP.altaPropuesta(dto); //llama al caso de uso

        System.out.println("Propuesta dada de alta correctamente.");

        // lista propuestas con estado ingresada
        EEstadoPropuesta estadoBuscado = EEstadoPropuesta.INGRESADA;
        List<DTOPropuesta> propuestas = controllerP.listarPorEstado(estadoBuscado);

        System.out.println("Propuestas con estado: " + estadoBuscado);

        if (propuestas.isEmpty()) {
            System.out.println("no hay propuestas en ese estado " + estadoBuscado);
        } else {
            for (DTOPropuesta pr : propuestas) {
                System.out.println("Título: " + pr.getTitulo());
                System.out.println("Lugar: " + pr.getLugar());
                System.out.println("Fecha: " + pr.getFecha());
                System.out.println("Categoría: " + pr.getCategoria());
                System.out.println("-----------------");
            }
        }
    }
}




