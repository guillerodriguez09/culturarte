package com.culturarte.logica.controllers;

import com.culturarte.logica.clases.*;
import com.culturarte.logica.dtos.DTOPropuesta;
import com.culturarte.logica.enums.EEstadoPropuesta;

import com.culturarte.persistencia.CategoriaDAO;
import com.culturarte.persistencia.ProponenteDAO;
import com.culturarte.persistencia.PropuestaDAO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PropuestaController implements IPropuestaController {

    private final PropuestaDAO  propuestaDAO  = new PropuestaDAO(); // para acceso a bd
    private final CategoriaDAO  categoriaDAO  = new CategoriaDAO();
    private final ProponenteDAO proponenteDAO = new ProponenteDAO();

    @Override
    public void altaPropuesta(DTOPropuesta dto) {
        //hace las validaciones
        if (dto == null) {
            throw new IllegalArgumentException("Datos de propuesta no provistos.");
        }
        if (dto.titulo == null || dto.titulo.isBlank()) {
            throw new IllegalArgumentException("El título es obligatorio.");
        }
        if (dto.fecha == null) {
            throw new IllegalArgumentException("La fecha prevista es obligatoria.");
        }
        if(dto.fecha.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha prevista no puede ser anterior a la fecha actual.");
        }
        if (dto.precioEntrada <= 0) {
            throw new IllegalArgumentException("El precio de la entrada no puede ser cero ni negativa.");
        }
        if (dto.montoAReunir == null || dto.montoAReunir <= 0) {
            throw new IllegalArgumentException("El monto a reunir debe ser mayor que 0.");
        }
        if (dto.proponenteNick == null || dto.proponenteNick.isBlank()) {
            throw new IllegalArgumentException("Debe indicarse un proponente.");
        }
        if (dto.categoriaNombre == null || dto.categoriaNombre.isBlank()) {
            throw new IllegalArgumentException("Debe indicarse una categoría.");
        }
        if (propuestaDAO.existePropuesta(dto.titulo)) {
            throw new IllegalArgumentException("Ya existe una propuesta con ese título.");
        }

        Proponente pro = proponenteDAO.buscarPorNick(dto.proponenteNick);
        if (pro == null) {
            throw new IllegalArgumentException("El proponente '" + dto.proponenteNick + "' no existe.");
        }

        Categoria cat = categoriaDAO.buscarPorNombre(dto.categoriaNombre);
        if (cat == null) {
            throw new IllegalArgumentException("La categoría '" + dto.categoriaNombre + "' no existe.");
        }


        //  arma la propuesta en si
        Propuesta p = new Propuesta(
                cat,
                pro,
                dto.titulo,
                dto.descripcion,
                dto.lugar,
                dto.fecha,
                dto.precioEntrada,
                dto.montoAReunir,
                LocalDate.now(),
                dto.retornos,
                dto.imagen
        );

        //setea el estado inicial a ingresada con la fecha de hoy
        Estado estadoInicial = new Estado(EEstadoPropuesta.INGRESADA, LocalDate.now());
        estadoInicial.setPropuesta(p); //le setea al estado la prop

        p.setEstadoActual(estadoInicial);
        p.getHistorialEstados().add(estadoInicial);

        // guarda en bd
        propuestaDAO.guardar(p);
    }

    @Override
    public List<String> listarPropuestas() {
        List<Propuesta> propuestas = propuestaDAO.obtenerTodas();
        return propuestas.stream()
                .map(Propuesta::getTitulo)
                .toList();
    }

    @Override
    public DTOPropuesta consultarPropuesta(String titulo) {
            Propuesta propuesta = propuestaDAO.buscarPorTitulo(titulo);

            if (propuesta == null) {
                throw new IllegalArgumentException("La propuesta con título '" + titulo + "' no existe.");
            }

            // Armo el DTO con la info básica
            DTOPropuesta dto = new DTOPropuesta();
            dto.titulo = propuesta.getTitulo();
            dto.descripcion = propuesta.getDescripcion();
            dto.lugar = propuesta.getLugar();
            dto.fecha = propuesta.getFecha();
            dto.precioEntrada = propuesta.getPrecioEntrada();
            dto.montoAReunir = propuesta.getMontoAReunir();
            dto.imagen = propuesta.getImagen();
            dto.categoriaNombre = propuesta.getCategoria().getNombre();
            dto.proponenteNick = propuesta.getProponente().getNick();
            dto.fechaPublicacion = propuesta.getFechaPublicacion();
            dto.retornos = propuesta.getRetornos();

            // Estado actual
            if (propuesta.getEstadoActual() != null) {
                dto.estadoActual = propuesta.getEstadoActual().getNombre().toString();
            }

            // Colaboradores (nicknames)
            List<String> colaboradores = new ArrayList<>();
            for (Colaboracion colab : propuesta.getColaboraciones()) {
                if (colab.getColaborador() != null) {
                    colaboradores.add(colab.getColaborador().getNick());
                }
            }
            dto.colaboradores = colaboradores;

            // Monto total recaudado
            dto.montoRecaudado = (double) propuesta.getMontoRecaudado();

            return dto;
        }

    @Override
    public void modificarPropuesta(String titulo, DTOPropuesta dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Datos de propuesta no provistos.");
        }

        // validaciones básicas (reutilizadas de altaPropuesta)
        if (dto.fecha == null) {
            throw new IllegalArgumentException("La fecha prevista es obligatoria.");
        }
        if(dto.fecha.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha no puede ser anterior a la fecha actual.");
        }
        if (dto.precioEntrada <= 0) {
            throw new IllegalArgumentException("El precio de la entrada no puede ser negativo.");
        }
        if (dto.montoAReunir == null || dto.montoAReunir <= 0) {
            throw new IllegalArgumentException("El monto a reunir debe ser mayor que 0.");
        }
        if (dto.categoriaNombre == null || dto.categoriaNombre.isBlank()) {
            throw new IllegalArgumentException("Debe indicarse una categoría.");
        }

        // Buscar la propuesta existente
        Propuesta propuesta = propuestaDAO.buscarPorTitulo(titulo);
        if (propuesta == null) {
            throw new IllegalArgumentException("No existe una propuesta con el título '" + titulo + "'.");
        }

        // Buscar y setear nueva categoría
        Categoria categoria = categoriaDAO.buscarPorNombre(dto.categoriaNombre);
        if (categoria == null) {
            throw new IllegalArgumentException("La categoría '" + dto.categoriaNombre + "' no existe.");
        }

        // Setear nuevos datos (no se modifica el título ni el proponente)
        propuesta.setDescripcion(dto.descripcion);
        propuesta.setLugar(dto.lugar);
        propuesta.setFecha(dto.fecha);
        propuesta.setPrecioEntrada(dto.precioEntrada);
        propuesta.setMontoAReunir(dto.montoAReunir);
        propuesta.setImagen(dto.imagen);
        propuesta.setCategoria(categoria);
        propuesta.setRetornos(dto.retornos);

        // Persistir cambios
        propuestaDAO.actualizar(propuesta);
    }

    @Override
    public List<DTOPropuesta> listarPorEstado(EEstadoPropuesta estado) {
        List<Propuesta> propuestas = propuestaDAO.listarPorEstado(estado);
        List<DTOPropuesta> dtos = new ArrayList<>();

        for (Propuesta p : propuestas) {
            DTOPropuesta dto = new DTOPropuesta();

            dto.setTitulo(p.getTitulo());
            dto.setDescripcion(p.getDescripcion());
            dto.setLugar(p.getLugar());
            dto.setFecha(p.getFecha());
            dto.setPrecioEntrada(p.getPrecioEntrada());
            dto.setMontoAReunir(p.getMontoAReunir());
            dto.setImagen(p.getImagen());
            dto.setCategoriaNombre(p.getCategoria().getNombre());
            dto.setProponenteNick(p.getProponente().getNick());
            dto.setRetornos(p.getRetornos());
            dto.setFechaPublicacion(p.getFechaPublicacion());

            if (p.getEstadoActual() != null) {
                dto.setEstadoActual(p.getEstadoActual().getNombre().toString());
            }

            List<String> colaboradores = new ArrayList<>();
            for (Colaboracion colab : p.getColaboraciones()) {
                if (colab.getColaborador() != null) {
                    colaboradores.add(colab.getColaborador().getNick());
                }
            }
            dto.setColaboradores(colaboradores);

            dto.setMontoRecaudado((double) p.getMontoRecaudado());

            dtos.add(dto);
        }

        return dtos;
    }
}



