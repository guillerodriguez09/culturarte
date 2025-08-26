package com.culturarte.logica.controllers;

import com.culturarte.logica.clases.Usuario;
import com.culturarte.logica.clases.Proponente;
import com.culturarte.logica.dtos.DTOProponente;

import com.culturarte.persistencia.ProponenteDAO;

import java.time.LocalDate;
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
    public List<Object[]> obtenerTodPropConPropu (String nick){

        List<Object[]> Tuti = proponenteDAO.obtenerTodPropConPropu(nick);

        return Tuti;

    }

}
