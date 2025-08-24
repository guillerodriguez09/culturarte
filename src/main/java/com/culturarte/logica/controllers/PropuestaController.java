package com.culturarte.logica.controllers;

import com.culturarte.logica.clases.Categoria;
import com.culturarte.logica.clases.Estado;
import com.culturarte.logica.clases.Proponente;
import com.culturarte.logica.clases.Propuesta;
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
        if (dto.precioEntrada < 0) {
            throw new IllegalArgumentException("El precio de la entrada no puede ser negativo.");
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
                dto.retornos
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
    public List<DTOPropuesta> listarPorEstado(EEstadoPropuesta estado) {
        List<Propuesta> propuestas = propuestaDAO.listarPorEstado(estado);
        List<DTOPropuesta> dtos = new ArrayList<>(); //la lista de dtos que van a la presentacion

        for (Propuesta p : propuestas) {
            DTOPropuesta dto = new DTOPropuesta();
            dto.titulo = p.getTitulo();
            dto.descripcion = p.getDescripcion();
            dto.lugar = p.getLugar();
            dto.fecha = p.getFecha();
            dto.precioEntrada = p.getPrecioEntrada();
            dto.montoAReunir = p.getMontoAReunir();
            dto.categoriaNombre = p.getCategoria().getNombre();
            dto.proponenteNick = p.getProponente().getNick();
            dto.retornos = p.getRetornos();
            dtos.add(dto);
        }

        return dtos;
    }



}


