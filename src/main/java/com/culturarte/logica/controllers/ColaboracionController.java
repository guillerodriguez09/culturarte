package com.culturarte.logica.controllers;

import com.culturarte.logica.clases.Colaboracion;
import com.culturarte.logica.clases.Colaborador;
import com.culturarte.logica.clases.Propuesta;
import com.culturarte.logica.dtos.DTOColabConsulta;
import com.culturarte.logica.dtos.DTOColaboracion;
import com.culturarte.persistencia.ColaboracionDAO;
import com.culturarte.persistencia.ColaboradorDAO;
import com.culturarte.persistencia.PropuestaDAO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ColaboracionController implements IColaboracionController {

    private final ColaboracionDAO colaboracionDAO = new ColaboracionDAO();
    private final PropuestaDAO propuestaDAO = new PropuestaDAO();
    private final ColaboradorDAO colaboradorDAO = new ColaboradorDAO();

    public void registrarColaboracion(DTOColaboracion dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Datos de colaboración no provistos.");
        }

        if (dto.monto == null || dto.monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero.");
        }

        if (dto.retorno == null) {
            throw new IllegalArgumentException("Debe seleccionarse un tipo de retorno.");
        }

        // Busca propuesta
        Propuesta propuesta = propuestaDAO.buscarPorTitulo(dto.propuestaTitulo);
        if (propuesta == null) {
            throw new IllegalArgumentException("Propuesta no encontrada.");
        }

        // Busca colaborador
        Colaborador colaborador = colaboradorDAO.buscarPorNick(dto.colaboradorNick);
        if (colaborador == null) {
            throw new IllegalArgumentException("Colaborador no encontrado.");
        }

        // Crear y persiste la colab
        Colaboracion nueva = new Colaboracion(
                dto.monto,
                dto.retorno,
                LocalDateTime.now(),
                propuesta,
                colaborador
        );

        propuesta.addColaboracion(nueva);
        colaborador.addColaboracion(nueva);
        colaboracionDAO.guardar(nueva);
        propuestaDAO.actualizar(propuesta); //para q guarde el colaborador sino no se ve al consultar propuesta

    }

    public List<DTOColabConsulta> listarColaboraciones() {
        List<Colaboracion> colabs = colaboracionDAO.obtenerTodas();
        List<DTOColabConsulta> dtos = new ArrayList<>();

        for (Colaboracion c : colabs) {
            DTOColabConsulta dto = new DTOColabConsulta(
                    c.getId(),
                    c.getColaborador().getNick(),
                    c.getMonto(),
                    c.getRetorno(),
                    c.getFecha()
            );
            dtos.add(dto);
        }

        return dtos;
    }

    public void cancelarColaboracion(int id) {
        // Busca la colab por el id
        Colaboracion colab = colaboracionDAO.buscarPorId(id);
        if (colab == null) {
            throw new IllegalArgumentException("No existe una colaboración con ID " + id);
        }

        // Eliminarla de la lista de la propuesta
        Propuesta propuesta = colab.getPropuesta();
        if (propuesta != null) {
            propuesta.getColaboraciones().remove(colab);
            propuestaDAO.actualizar(propuesta); // importante para mostrar bien los colaboradores
        }

        // Eliminarla de la lista del colaborador
        Colaborador colaborador = colab.getColaborador();
        if (colaborador != null && colaborador.getColaboraciones() != null) {
            colaborador.getColaboraciones().remove(colab);
            colaboradorDAO.actualizar(colaborador);
        }

        // Eliminar de la bd
        colaboracionDAO.eliminar(colab);
    }

}