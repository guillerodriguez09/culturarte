package com.culturarte.logica.controllers;

import com.culturarte.logica.clases.Colaborador;
import com.culturarte.logica.clases.Propuesta;
import com.culturarte.logica.dtos.DTOColaborador;
import com.culturarte.logica.dtos.DTOPropuesta;
import com.culturarte.persistencia.ColaboradorDAO;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<String> listarColaboradores(){
        List<Colaborador> colaboradores = colaboradorDAO.obtenerTodos();
        return colaboradores.stream()
                .map(Colaborador::getNick)
                .toList();
    }

    @Override
    public List<Object[]> obtenerTodColConPropu(String nick) {
        List<Object[]> Tuti = colaboradorDAO.obtenerTodColConPropu(nick);
        List<Object[]> Fruti = new ArrayList<>();
        for(Object[] fila : Tuti) {

            Colaborador col = (Colaborador) fila[0];
            Propuesta p = (Propuesta) fila[2];

            DTOColaborador dtoCol = new DTOColaborador();
            DTOPropuesta dtoCP = new DTOPropuesta();

            dtoCol.setNick(col.getNick());
            dtoCol.setNombre(col.getNombre());
            dtoCol.setApellido(col.getApellido());
            dtoCol.setCorreo(col.getCorreo());
            dtoCol.setFechaNac(col.getFechaNac());
            dtoCol.setDirImagen(col.getDirImagen());

            dtoCP.titulo = p.getTitulo();
            dtoCP.proponenteNick = p.getProponente().getNick();
            if (p.getEstadoActual() != null) {
                dtoCP.estadoActual = p.getEstadoActual().getNombre().toString();
            }
            dtoCP.montoRecaudado = (double) p.getMontoRecaudado();

            Object[] filaJuan = new Object[] {dtoCol, dtoCP};
            Fruti.add(filaJuan);

        }

        return Fruti;

    }

    public DTOColaborador obtenerColaborador(String nick){

        Colaborador col = colaboradorDAO.buscarPorNick(nick);

        if(col == null){return null;}

        DTOColaborador dtoCol = new DTOColaborador();

        dtoCol.setNick(col.getNick());
        dtoCol.setNombre(col.getNombre());
        dtoCol.setApellido(col.getApellido());
        dtoCol.setCorreo(col.getCorreo());
        dtoCol.setFechaNac(col.getFechaNac());
        dtoCol.setDirImagen(col.getDirImagen());

        return dtoCol;

    }

}
