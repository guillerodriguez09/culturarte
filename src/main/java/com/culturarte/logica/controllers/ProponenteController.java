package com.culturarte.logica.controllers;

import com.culturarte.logica.clases.*;
import com.culturarte.logica.dtos.DTOProponente;

import com.culturarte.logica.dtos.DTOPropuesta;
import com.culturarte.logica.enums.EEstadoPropuesta;
import com.culturarte.persistencia.ProponenteDAO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProponenteController implements IProponenteController {

    private final ProponenteDAO proponenteDAO = new ProponenteDAO();

    @Override
    public void altaProponente(DTOProponente dtoP) {

        if (dtoP == null) {
            throw new IllegalArgumentException("Datos de proponente no provistos.");
        }
        if (dtoP.getNick() == null || dtoP.getNick().isBlank() ){
            throw new IllegalArgumentException("Nickname de proponente es obligatorio.");
        }
        if (dtoP.getNombre() == null || dtoP.getNombre().isBlank() ){
            throw new IllegalArgumentException("Nombre de proponente es obligatorio.");
        }
        if (dtoP.getApellido() == null || dtoP.getApellido().isBlank() ){
            throw new IllegalArgumentException("Apellido de proponente es obligatorio.");
        }
        if (dtoP.getContrasenia() == null || dtoP.getContrasenia().isBlank()){
            throw new IllegalArgumentException("Contraseña de proponente es obligatoria");
        }
        if (dtoP.getCorreo() == null || dtoP.getCorreo().isBlank() ){
            throw new IllegalArgumentException("Correo de proponente es obligatorio.");
        }
        if (dtoP.getFechaNac() == null){
            throw new IllegalArgumentException("Fecha de nacimiento de proponente es obligatoria.");
        }
        if (dtoP.getDirImagen() == null){
            dtoP.setDirImagen("No tiene");
        }
        if (dtoP.getDireccion() == null || dtoP.getDireccion().isBlank() ){
            throw new IllegalArgumentException("Direccion de proponente es obligatorio.");
        }
        if (dtoP.getBiografia() == null){
            dtoP.setBiografia("No tiene");
        }
        if (dtoP.getLink() == null){
            dtoP.setLink("No tiene");
        }
        if (proponenteDAO.existe(dtoP.getNick())) {
            throw new IllegalArgumentException("Ya existe un proponente con ese nickname.");
        }

        Proponente pro = new Proponente(
                dtoP.getNick(),
                dtoP.getNombre(),
                dtoP.getApellido(),
                dtoP.getContrasenia(),
                dtoP.getCorreo(),
                dtoP.getFechaNac(),
                dtoP.getDirImagen(),
                dtoP.getDireccion(),
                dtoP.getBiografia(),
                dtoP.getLink()
        );

        proponenteDAO.guardar(pro);

    }

    @Override
    public List<String> listarProponentes() {
        return proponenteDAO.obtenerTodos()
                .stream()
                .map(Proponente::getNick)
                .toList();
    }

    @Override
    public List<DTOProponente> listarTodos(){
        List<Proponente> prop = proponenteDAO.obtenerTodos();
        List<DTOProponente> dtoProp = new ArrayList<>();

        for (Proponente pro : prop) {
            DTOProponente dtoPropo = new DTOProponente();

            dtoPropo.setNick(pro.getNick());
            dtoPropo.setNombre(pro.getNombre());
            dtoPropo.setApellido(pro.getApellido());
            dtoPropo.setContrasenia(pro.getContrasenia());
            dtoPropo.setCorreo(pro.getCorreo());
            dtoPropo.setFechaNac(pro.getFechaNac());
            dtoPropo.setDirImagen(pro.getDirImagen());
            dtoPropo.setDireccion(pro.getDireccion());
            dtoPropo.setBiografia(pro.getBiografia());
            dtoPropo.setLink(pro.getLink());

            dtoProp.add(dtoPropo);

        }

        return dtoProp;
    }

    @Override
    public DTOProponente obtenerProponente(String nick){

        Proponente prop = proponenteDAO.buscarPorNick(nick);

        if(prop == null){return null;}

        DTOProponente dtoProp = new DTOProponente();

        dtoProp.setNick(prop.getNick());
        dtoProp.setNombre(prop.getNombre());
        dtoProp.setApellido(prop.getApellido());
        dtoProp.setContrasenia(prop.getContrasenia());
        dtoProp.setCorreo(prop.getCorreo());
        dtoProp.setFechaNac(prop.getFechaNac());
        dtoProp.setDirImagen(prop.getDirImagen());
        dtoProp.setBiografia(prop.getBiografia());
        dtoProp.setDireccion(prop.getDireccion());
        dtoProp.setLink(prop.getLink());

        return dtoProp;

    }

    @Override
    public DTOProponente obtenerProponenteCorreo(String correo){

        DTOProponente dtoProp = new DTOProponente();

        try {
            Proponente prop = proponenteDAO.buscarPorCorreo(correo);

            if (prop == null) {return null;}

            dtoProp.setNick(prop.getNick());
            dtoProp.setNombre(prop.getNombre());
            dtoProp.setApellido(prop.getApellido());
            dtoProp.setContrasenia(prop.getContrasenia());
            dtoProp.setCorreo(prop.getCorreo());
            dtoProp.setFechaNac(prop.getFechaNac());
            dtoProp.setDirImagen(prop.getDirImagen());
            dtoProp.setBiografia(prop.getBiografia());
            dtoProp.setDireccion(prop.getDireccion());
            dtoProp.setLink(prop.getLink());

        }catch(Exception e){
            return null;
        }

        return dtoProp;
    }


    @Override
    public List<Object[]> obtenerTodPropConPropu (String nick){
        List<Object[]> Marco = proponenteDAO.obtenerTodPropConPropu(nick);
        List<Object[]> Polo = new ArrayList<>();
        for(Object[] fila : Marco) {

            Proponente prop = (Proponente) fila[0];
            Propuesta p = (Propuesta) fila[1];

            DTOProponente dtoProp = new DTOProponente();
            DTOPropuesta dtoPropu = new DTOPropuesta();

            dtoProp.setNick(prop.getNick());
            dtoProp.setNombre(prop.getNombre());
            dtoProp.setApellido(prop.getApellido());
            dtoProp.setContrasenia(prop.getContrasenia());
            dtoProp.setCorreo(prop.getCorreo());
            dtoProp.setFechaNac(prop.getFechaNac());
            dtoProp.setDirImagen(prop.getDirImagen());
            dtoProp.setDireccion(prop.getDireccion());
            dtoProp.setBiografia(prop.getBiografia());
            dtoProp.setLink(prop.getLink());

            dtoPropu.titulo = p.getTitulo();
            if (p.getEstadoActual() != null) {
                dtoPropu.estadoActual = p.getEstadoActual().getNombre().toString();
            }
            dtoPropu.montoRecaudado = (double) p.getMontoRecaudado();

            Object[] filaJuan = new Object[] {dtoProp, dtoPropu};
            Polo.add(filaJuan);

        }

        return Polo;

    }

    @Override
    public List<Object[]> obtenerPropConPropuYEstado (EEstadoPropuesta estado, String nick){
        List<Object[]> MeteCuchillo = proponenteDAO.obtenerPropConPropuYEstado(estado, nick);
        List<Object[]> SacaTripa = new ArrayList<>();
        for(Object[] fila : MeteCuchillo) {

            Proponente prop = (Proponente) fila[0];
            Propuesta p = (Propuesta) fila[1];

            DTOProponente dtoProp = new DTOProponente();
            DTOPropuesta dtoPropu = new DTOPropuesta();

            dtoProp.setNick(prop.getNick());
            dtoProp.setNombre(prop.getNombre());
            dtoProp.setApellido(prop.getApellido());
            dtoProp.setContrasenia(prop.getContrasenia());
            dtoProp.setCorreo(prop.getCorreo());
            dtoProp.setFechaNac(prop.getFechaNac());
            dtoProp.setDirImagen(prop.getDirImagen());
            dtoProp.setDireccion(prop.getDireccion());
            dtoProp.setBiografia(prop.getBiografia());
            dtoProp.setLink(prop.getLink());

            dtoPropu.titulo = p.getTitulo();
            if (p.getEstadoActual() != null) {
                dtoPropu.estadoActual = p.getEstadoActual().getNombre().toString();
            }
            dtoPropu.montoRecaudado = (double) p.getMontoRecaudado();

            List<String> colaboradores = new ArrayList<>();
            for (Colaboracion colab : p.getColaboraciones()) {
                if (colab.getColaborador() != null) {
                    colaboradores.add(colab.getColaborador().getNick());
                }
            }
            dtoPropu.colaboradores = colaboradores;

            Object[] filaJuan = new Object[] {dtoProp, dtoPropu};
            SacaTripa.add(filaJuan);

        }

        return SacaTripa;

    }

}
