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
import java.util.List;
import javax.swing.*;

public class Culturarte {
    public static void main(String[] args) {

        Colaborador juan = new Colaborador("juan", "Juan", "Pérez", "juan@mail.com", LocalDate.of(2000, 5, 12), "C:\\Users\\blabla\\Pictures\\sesi.jpg");
        //Colaboracion c1 = new Colaboracion(2000, ETipoRetorno.ENTRADAS_GRATIS, LocalDate.now(), prop, juan);

        //prop.addColaboracion(c1);

        //Creo un proponente
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

        //Creo un colaborador
        DTOColaborador dtoC = new DTOColaborador();
        dtoC.setNick("Fede");
        dtoC.setNombre("Federico");
        dtoC.setApellido("Valdez");
        dtoC.setCorreo("FedeBaldes@gmail.com");
        dtoC.setFechaNac(LocalDate.of(2003, 7, 10));
        dtoC.setDirImagen("C:\\Users\\Chorizo-Cosmico\\Pictures\\sesi.jpg");

        IColaboradorController controllerCol = Fabrica.getInstancia().getColaboradorController();
        controllerCol.altaColaborador(dtoC);

        SwingUtilities.invokeLater(() -> {
            MenuPrincipal menu = new MenuPrincipal();
            menu.setVisible(true);
        });

        /*
        // Consulta de perfil de Proponente
        // Esta comentado para que no large el chorizo de información con cada ejecución
        // Tengo que hipermega refinarlo, se ve bien qlero. Historial de estados y Tipo de Retornos probablemente precisen sus propios fors para mostrar la información correctamente
        List<Object[]> Tuti = controllerPro.obtenerTodPropConPropu("Andre");
        for(Object[] fila : Tuti){
            Proponente pro = (Proponente)fila[0];
            Propuesta p = (Propuesta)fila[1];
            System.out.println("Nickname: " + pro.getNick() + " " + "Nombre: " + pro.getNombre() + " " + "Apellido: " + pro.getApellido()
            + " " + "Correo: " + pro.getCorreo() + " " + "Fecha de Nacimiento: " + pro.getFechaNac() + " " + "Direccion Imagen: " + pro.getDirImagen()
            + " " + "Direccion: " + pro.getDireccion() + " " + "Biografia: " +  pro.getBiografia() + " " + "Link pagina personal: " + pro.getLink());
            System.out.println("Titulo: " + p.getTitulo() + " " + "Descripcion: " + p.getDescripcion() + " " + "Direccion Imagen: " + p.getImagen()
            + " " + "Lugar: " + p.getLugar() + " " + "Fecha: " + p.getFecha() + " " + "Precio Entrada: " + " " + p.getPrecioEntrada() + " " + "Monto a reunir: " + p.getMontoAReunir()
            + " " + "Fecha Publicacion: " + p.getFechaPublicacion() + " " + "Categoria: " + p.getCategoria() + " " + "Estado Actual: " + p.getEstadoActual()
            + " " + "Historia Estados: " +p.getHistorialEstados() + " " + "Proponente: " + p.getProponente() + " " + "Tipo de Retornos: " + p.getRetornos()
            );
        }
        */

        /*
        // Consulta de Perfil de Colaborador
        // Esta comentado para que no large el chorizo de información con cada ejecución
        //Esta terminado, falta refinarlo
        List<Object[]> Fruti = controllerCol.obtenerTodColConPropu("Fede");
        for(Object[] fila : Fruti){
            Colaborador col = (Colaborador)fila[0];
            Colaboracion c = (Colaboracion)fila[1];
            Propuesta p = (Propuesta)fila[2];

            System.out.println("Nickname: " + col.getNick() + " " + "Nombre: " + col.getNombre() + " " + "Apellido: " + col.getApellido()
                    + " " + "Correo: " + col.getCorreo() + " " + "Fecha de Nacimiento: " + col.getFechaNac() + " " + "Direccion Imagen: " + col.getDirImagen());
            System.out.println("Id: " + c.getId() + " " + "Nickname Proponente a Cargo: " + p.getProponente() + " " + "Dinero Recaudado: " + c.getMonto()
                    + " " + "Estado Actual: " + p.getEstadoActual());
        }
        */

    }
}


