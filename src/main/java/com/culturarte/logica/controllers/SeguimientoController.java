package com.culturarte.logica.controllers;

import com.culturarte.logica.clases.Colaborador;
import com.culturarte.logica.clases.Proponente;
import com.culturarte.logica.clases.Seguimiento;
import com.culturarte.logica.clases.Usuario;
import com.culturarte.logica.dtos.DTOSeguimiento;

import com.culturarte.logica.dtos.DTOUsuario;
import com.culturarte.persistencia.SeguimientoDAO;
import com.culturarte.persistencia.ColaboradorDAO;
import com.culturarte.persistencia.ProponenteDAO;
import jakarta.jws.WebService;

import java.util.ArrayList;
import java.util.List;
@WebService(endpointInterface = "com.culturarte.logica.controllers.ISeguimientoController")
public class SeguimientoController implements ISeguimientoController{

    private final SeguimientoDAO seguimientoDAO = new SeguimientoDAO();
    private final ColaboradorDAO colaboradorDAO = new ColaboradorDAO();
    private final ProponenteDAO proponenteDAO = new ProponenteDAO();



    @Override
    public void registrarSeguimiento(DTOSeguimiento dtoSegui){

        if (dtoSegui == null) {
            throw new IllegalArgumentException("Datos de seguimiento no provistos.");
        }

        if (dtoSegui.getUsuarioSeguidor() == null) {
            throw new IllegalArgumentException("Debe seleccionar un usario seguidoor");
        }

        if (dtoSegui.getUsuarioSeguido() == null) {
            throw new IllegalArgumentException("Debe seleccionar un usuario a seguir.");
        }

        if (seguimientoDAO.existe(dtoSegui.getUsuarioSeguidor().getNick(), dtoSegui.getUsuarioSeguido())) {
            throw new IllegalArgumentException("El usuario " + dtoSegui.getUsuarioSeguidor() + " ya sigue a " + dtoSegui.getUsuarioSeguido());
        }

        //Tendria que poner la funcion existe en Usuario en vez de en Proponente/Colaborador
        Usuario juliano;
        Colaborador colaborador = colaboradorDAO.buscarPorNick(dtoSegui.getUsuarioSeguidor().getNick());
        Proponente proponente = proponenteDAO.buscarPorNick(dtoSegui.getUsuarioSeguidor().getNick());

        if(colaborador != null){
            juliano = colaborador;
        }else if(proponente != null){
            juliano = proponente;
        }else{
            throw new IllegalArgumentException("El usuario no existe");
        }

        Seguimiento segui = new Seguimiento(
                juliano,
                dtoSegui.getUsuarioSeguido()
        );

        seguimientoDAO.guardar(segui);

    }

    @Override
    public int conseguirId(String nick, String nicky){

        int existeId = seguimientoDAO.conseguirId(nick, nicky);

        if(existeId != 0){
            return existeId;
        }else{
            throw new IllegalArgumentException("Este seguimiento no existe");
        }
    }

    @Override
    public void cancelarSeguimiento(int idSeguimiento){

        // Busca el seguimiento por el id
        Seguimiento segui = seguimientoDAO.buscarPorId(idSeguimiento);
        if (segui == null) {
            throw new IllegalArgumentException("No existe un seguimiento con la Id " + idSeguimiento);
        }

        // Eliminar de la bd
        seguimientoDAO.eliminar(segui);

    }

    @Override
    public List<DTOSeguimiento> listarSeguimientos(){

        List<Seguimiento> seguim = seguimientoDAO.obtenerTodos();
        //Muchos dtoSeguimiento
        List<DTOSeguimiento> dtoSeguim = new ArrayList<>();

        for (Seguimiento s : seguim) {
            DTOSeguimiento dtoSegui = new DTOSeguimiento(
                    s.getUsuarioSeguidor(),
                    s.getUsuarioSeguido()
            );
            dtoSeguim.add(dtoSegui);
        }

        return dtoSeguim;

    }

    @Override
    public List<String> listarSeguidosDeNick(String nick){

        return seguimientoDAO.obtenerTodosDeNick(nick)
                .stream()
                .map(Seguimiento::getUsuarioSeguido)
                .toList();

    }

    @Override
    public List<String> listarSeguidoresDeNick(String nick){

        List<Seguimiento> seguidoores = seguimientoDAO.obtenerSeguidoresDeNick(nick);
        List<String> seguidoresNick = new ArrayList<>();
        for (Seguimiento s : seguidoores) {
            Usuario usuarioSeguidoor = s.getUsuarioSeguidor();
            seguidoresNick.add(usuarioSeguidoor.getNick());
        }
        return seguidoresNick;

    }
}
