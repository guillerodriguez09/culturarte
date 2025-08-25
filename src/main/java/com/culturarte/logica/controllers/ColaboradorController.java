package com.culturarte.logica.controllers;

import com.culturarte.logica.clases.Colaborador;
import com.culturarte.logica.dtos.DTOColaborador;
import com.culturarte.persistencia.ColaboradorDAO;

public class ColaboradorController implements IColaboradorController {

    private final ColaboradorDAO colaboradorDAO = new ColaboradorDAO();

    @Override
    public void altaColaborador(DTOColaborador dtoC){

        if (dtoC == null) {
            throw new IllegalArgumentException("Datos de colaborador no provistos.");
        }
        if (dtoC.getNick() == null || dtoC.getNick().isBlank() ){
            throw new IllegalArgumentException("Nickname de colaborador es obligatorio.");
        }
        if (dtoC.getNombre() == null || dtoC.getNombre().isBlank() ){
            throw new IllegalArgumentException("Nombre de colaborador es obligatorio.");
        }
        if (dtoC.getApellido() == null || dtoC.getApellido().isBlank() ){
            throw new IllegalArgumentException("Apellido de colaborador es obligatorio.");
        }
        if (dtoC.getCorreo() == null || dtoC.getCorreo().isBlank() ){
            throw new IllegalArgumentException("Correo de colaborador es obligatorio.");
        }
        if (dtoC.getFechaNac() == null){
            throw new IllegalArgumentException("Fecha de nacimiento de colaborador es obligatoria.");
        }
        if (dtoC.getDirImagen() == null){
            dtoC.setDirImagen("No tiene");
        }

        if (colaboradorDAO.existe(dtoC.getNick())) {
            throw new IllegalArgumentException("Ya existe un colaborador con ese nickname.");
        }

        Colaborador col = new Colaborador(
                dtoC.getNick(),
                dtoC.getNombre(),
                dtoC.getApellido(),
                dtoC.getCorreo(),
                dtoC.getFechaNac(),
                dtoC.getDirImagen()
        );

        colaboradorDAO.guardar(col);


    }

}
