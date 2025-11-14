package com.culturarte.logica.controllers;

import com.culturarte.logica.clases.Colaboracion;
import com.culturarte.logica.clases.Colaborador;
import com.culturarte.logica.clases.Estado;
import com.culturarte.logica.clases.Propuesta;
import com.culturarte.logica.dtos.DTOColabConsulta;
import com.culturarte.logica.dtos.DTOColaboracion;
import com.culturarte.logica.dtos.DTOConstanciaPago;
import com.culturarte.logica.enums.EEstadoPropuesta;
import com.culturarte.persistencia.ColaboracionDAO;
import com.culturarte.persistencia.ColaboradorDAO;
import com.culturarte.persistencia.PropuestaDAO;
import jakarta.jws.WebService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@WebService(endpointInterface = "com.culturarte.logica.controllers.IColaboracionController")
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


        EEstadoPropuesta estado = propuesta.getEstadoActual().getNombre();
        switch (estado) {
            case INGRESADA, CANCELADA, FINANCIADA, NO_FINANCIADA -> {
                throw new IllegalArgumentException(
                        "No se puede colaborar en una propuesta con estado: " + estado
                );
            }
            case PUBLICADA, EN_FINANCIACION -> {
                // permitido hacer colab
            }
        }

        // Busca colaborador
        Colaborador colaborador = colaboradorDAO.buscarPorNick(dto.colaboradorNick);
        if (colaborador == null) {
            throw new IllegalArgumentException("Colaborador no encontrado.");
        }

        if (colaboracionDAO.existe(dto.colaboradorNick, dto.propuestaTitulo)) {
            throw new IllegalArgumentException("El usuario " + dto.colaboradorNick +
                    " ya colaboró en la propuesta " + dto.propuestaTitulo);
        }

        // Crear y persistir la colaboración
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
        propuestaDAO.actualizar(propuesta);
        colaboradorDAO.actualizar(colaborador);

        // para que cuando se agrega una colab cambie el estado a enfinanciacion
        if (estado == EEstadoPropuesta.PUBLICADA) {
            Estado nuevoEstado = new Estado(EEstadoPropuesta.EN_FINANCIACION, LocalDate.now());
            nuevoEstado.setPropuesta(propuesta);
            propuesta.setEstadoActual(nuevoEstado);
            propuesta.getHistorialEstados().add(nuevoEstado);
            propuestaDAO.actualizar(propuesta);
        }
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
            dto.setPropuestaNombre(c.getPropuesta().getTitulo());
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


              //anade el nom de la propuesta al dto
                if (c.getPropuesta() != null) {
                    dto.setPropuestaNombre(c.getPropuesta().getTitulo());
                } else {
                    dto.setPropuestaNombre("Propuesta no disponible"); // Por si acaso
                }

                dtos.add(dto);
            }
        }
        return dtos;
    }

// ...

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
    public DTOConstanciaPago emitirConstanciaPago(int idColaboracion) {
        Colaboracion colab = colaboracionDAO.buscarPorId(idColaboracion);
        if(colab == null) throw new IllegalArgumentException("Colaboración no encontrada.");

        if(Boolean.TRUE.equals(colab.getConstanciaEmitida())) {
            throw new IllegalArgumentException("La constancia ya fue emitida.");
        }

        colab.setConstanciaEmitida(true);
        colaboracionDAO.guardar(colab);

        return new DTOConstanciaPago(
                "Culturarte",
                LocalDateTime.now(),
                colab.getColaborador().getNick(),
                colab.getColaborador().getNombre() + " " + colab.getColaborador().getApellido(),
                colab.getColaborador().getCorreo(),
                colab.getPropuesta().getTitulo(),
                colab.getMonto(),
                colab.getFecha()
        );
    }
}
