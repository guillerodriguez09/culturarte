package com.culturarte.presentacion;

import com.culturarte.logica.clases.*;
import com.culturarte.logica.controllers.*;
import com.culturarte.persistencia.*;
import com.culturarte.logica.dtos.*;
import com.culturarte.logica.fabrica.Fabrica;
import com.culturarte.logica.enums.EEstadoPropuesta;
import com.culturarte.logica.enums.ETipoRetorno;

import java.net.StandardSocketOptions;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.swing.*;

public class Culturarte {
    public static void main(String[] args) {
 /*
        Colaborador juan = new Colaborador("juan", "Juan", "Pérez", "juan@mail.com", LocalDate.of(2000, 5, 12), "C:\\Users\\blabla\\Pictures\\sesi.jpg");
        //Colaboracion c1 = new Colaboracion(2000, ETipoRetorno.ENTRADAS_GRATIS, LocalDate.now(), prop, juan);

        //prop.addColaboracion(c1);

        //Creo un proponente
        /*
        DTOProponente dtoP = new DTOProponente();
        dtoP.setNick("Andre");
        dtoP.setNombre("Andres");
        dtoP.setApellido("Ferreira");
        dtoP.setCorreo("AndreFerreteria@gmail.com");
        dtoP.setFechaNac(LocalDate.of(2004, 1, 12));
        dtoP.setDirImagen("C:\\Users\\Chorizo-Cosmico\\Pictures\\KeeperOoh.png");
        dtoP.setDireccion("Abajo de un puente");
        dtoP.setBiografia("El mate es la fuente de su vida");
        dtoP.setLink("Nose");

        IProponenteController controllerPro = Fabrica.getInstancia().getProponenteController();
        controllerPro.altaProponente(dtoP);
    */
     /*
        //Creo un colaborador
        DTOColaborador dtoC = new DTOColaborador();
        dtoC.setNick("Fede");
        dtoC.setNombre("Federico");
        dtoC.setApellido("Valdez");
        dtoC.setCorreo("FedeBaldes@gmail.com");
        dtoC.setFechaNac(LocalDate.of(2003, 7, 10));
        dtoC.setDirImagen("C:\\Users\\Chorizo-Cosmico\\Pictures\\sesi.jpg");

        DTOPropuesta dtoProp = new DTOPropuesta();
        dtoProp.setTitulo("Festival de Música");
        dtoProp.setDescripcion("Evento anual con artistas internacionales.");
        dtoProp.setLugar("Teatro Solís");
        dtoProp.setFecha(LocalDate.of(2025, 10, 15));
        dtoProp.setPrecioEntrada(500);
        dtoProp.setMontoAReunir(10000);
        dtoProp.setImagen("imagenes/festival.png");
        dtoProp.setProponenteNick("Andre");
        dtoProp.setCategoriaNombre("Música");
        dtoProp.setRetornos(List.of(ETipoRetorno.ENTRADAS_GRATIS, ETipoRetorno.PORCENTAJE_GANANCIAS));
        dtoProp.setFechaPublicacion(LocalDate.now());

        Fabrica.getInstancia().getPropuestaController().altaPropuesta(dtoProp);


        IColaboradorController controllerCol = Fabrica.getInstancia().getColaboradorController();
        controllerCol.altaColaborador(dtoC);

        IColaboracionController controllerColab = Fabrica.getInstancia().getColaboracionController();
        DTOColaboracion dto = new DTOColaboracion();
        dto.setPropuestaTitulo("Festival de Música");
        dto.setColaboradorNick("Fede");
        dto.setRetorno(ETipoRetorno.ENTRADAS_GRATIS);
        dto.setMonto(1200);
        dto.setFecha(LocalDateTime.now());

        DTOColaboracion dto1 = new DTOColaboracion();
        dto1.setPropuestaTitulo("Festival de Música");
        dto1.setColaboradorNick("Fede");
        dto1.setRetorno(ETipoRetorno.PORCENTAJE_GANANCIAS);
        dto1.setMonto(120);
        dto1.setFecha(LocalDateTime.now());

       controllerColab.registrarColaboracion(dto1);
       controllerColab.registrarColaboracion(dto);


        SwingUtilities.invokeLater(() -> {
            MenuPrincipal menu = new MenuPrincipal();
            menu.setVisible(true);
        });

    }
}
