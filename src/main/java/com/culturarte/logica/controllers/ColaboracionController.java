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
        if (dto == null) throw new IllegalArgumentException("Datos de colaboración no provistos.");
        if (dto.monto == null || dto.monto <= 0) throw new IllegalArgumentException("El monto debe ser mayor a cero.");
        if (dto.retorno == null) throw new IllegalArgumentException("Debe seleccionarse un tipo de retorno.");

        Propuesta propuesta = propuestaDAO.buscarPorTitulo(dto.propuestaTitulo);
        if (propuesta == null) throw new IllegalArgumentException("Propuesta no encontrada.");

        Colaborador colaborador = colaboradorDAO.buscarPorNick(dto.colaboradorNick);
        if (colaborador == null) throw new IllegalArgumentException("Colaborador no encontrado.");

        Colaboracion nueva = new Colaboracion(
                dto.monto,
                dto.retorno,
                LocalDateTime.now(),
                propuesta,
                colaborador
        );

        // Vincula
        propuesta.addColaboracion(nueva);
        colaborador.addColaboracion(nueva);

        // Persiste
        colaboracionDAO.guardar(nueva);
        propuestaDAO.actualizar(propuesta);
        colaboradorDAO.actualizar(colaborador);
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

    public List<DTOColabConsulta> consultarColaboracionesPorColaborador(String colaboradorNick) {
        Colaborador col = colaboradorDAO.buscarPorNick(colaboradorNick);
        if (col == null) throw new IllegalArgumentException("Colaborador no encontrado.");

        List<DTOColabConsulta> dtos = new ArrayList<>();
        if (col.getColaboraciones() != null) {
            for (Colaboracion c : col.getColaboraciones()) {
                DTOColabConsulta dto = new DTOColabConsulta(
                        c.getId(),
                        col.getNick(),
                        c.getMonto(),
                        c.getRetorno(),
                        c.getFecha()
                );
                dtos.add(dto);
            }
        }
        return dtos;
    }

    public void cancelarColaboracion(int id) {
        Colaboracion colab = colaboracionDAO.buscarPorId(id);
        if (colab == null) throw new IllegalArgumentException("No existe una colaboración con ID " + id);

        // Eliminar de la propuesta
        Propuesta propuesta = colab.getPropuesta();
        if (propuesta != null) {
            propuesta.getColaboraciones().remove(colab);
            propuestaDAO.actualizar(propuesta);
        }

        // Eliminar del colaborador
        Colaborador colaborador = colab.getColaborador();
        if (colaborador != null && colaborador.getColaboraciones() != null) {
            colaborador.getColaboraciones().remove(colab);
            colaboradorDAO.actualizar(colaborador);
        }

        // Eliminar de BD
        colaboracionDAO.eliminar(colab);
    }
}
