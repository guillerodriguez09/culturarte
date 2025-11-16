package com.culturarte.logica.controllers;

import com.culturarte.logica.clases.*;
import com.culturarte.logica.dtos.DTOPropuesta;
import com.culturarte.logica.enums.EEstadoPropuesta;

import com.culturarte.persistencia.CategoriaDAO;
import com.culturarte.persistencia.ColaboradorDAO;
import com.culturarte.persistencia.ProponenteDAO;
import com.culturarte.persistencia.PropuestaDAO;
import jakarta.jws.WebService;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@WebService(endpointInterface = "com.culturarte.logica.controllers.IPropuestaController")
public class PropuestaController implements IPropuestaController {

    private final PropuestaDAO  propuestaDAO  = new PropuestaDAO(); // para acceso a bd
    private final CategoriaDAO  categoriaDAO  = new CategoriaDAO();
    private final ProponenteDAO proponenteDAO = new ProponenteDAO();
    private final ColaboradorDAO colaboradorDAO = new ColaboradorDAO();

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

        // Armar el DTO
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
        } else {
            dto.estadoActual = "DESCONOCIDO";
        }

        // Colaboradores
        List<String> colaboradores = new ArrayList<>();
        for (Colaboracion colab : propuesta.getColaboraciones()) {
            if (colab.getColaborador() != null) {
                colaboradores.add(colab.getColaborador().getNick());
            }
        }
        dto.colaboradores = colaboradores;

        //Monto recaudado actualizado
        dto.montoRecaudado = (double) propuesta.getMontoRecaudado();

        return dto;
    }


    @Override
    public void modificarPropuesta(String titulo, DTOPropuesta dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Datos de propuesta no provistos.");
        }

        if (dto.fecha == null) {
            throw new IllegalArgumentException("La fecha prevista es obligatoria.");
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

        // 🔑 Actualizar estado actual si viene en el DTO
        if (dto.estadoActual != null && !dto.estadoActual.isBlank()) {
            EEstadoPropuesta nuevoEstado = EEstadoPropuesta.valueOf(dto.estadoActual);

            Estado estado = new Estado(nuevoEstado, LocalDate.now());
            estado.setPropuesta(propuesta);

            propuesta.setEstadoActual(estado);
            propuesta.getHistorialEstados().add(estado);
        }

        // ✅ Guardar todo en un único UPDATE
        propuestaDAO.actualizar(propuesta);
    }


    @Override
    public List<DTOPropuesta> listarPorEstado(EEstadoPropuesta estado) {
        List<Propuesta> propuestas = propuestaDAO.listarPorEstado(estado);
        List<DTOPropuesta> dtos = new ArrayList<>();

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
            dto.estadoActual = p.getEstadoActual().getNombre().toString();
            dto.montoRecaudado = (double)p.getMontoRecaudado();
            dto.estadoActual = p.getEstadoActual().getNombre().toString();
            dto.montoRecaudado = (double)p.getMontoRecaudado();
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public List<DTOPropuesta> listarPropuestasConProponente() {
        List<Propuesta> propuestas = propuestaDAO.obtenerTodas();
        List<DTOPropuesta> dtos = new ArrayList<>();

        for (Propuesta p : propuestas) {
            DTOPropuesta dto = new DTOPropuesta();
            dto.setTitulo(p.getTitulo());
            dto.setDescripcion(p.getDescripcion());
            dto.setLugar(p.getLugar());
            dto.setFecha(p.getFecha());
            dto.setPrecioEntrada(p.getPrecioEntrada());
            dto.setMontoAReunir(p.getMontoAReunir());
            dto.setMontoRecaudado(Double.valueOf(p.getMontoRecaudado()));
            dto.setProponenteNick(p.getProponente().getNick());
            dto.setCategoriaNombre(p.getCategoria().getNombre());
            dto.setRetornos(p.getRetornos());
            dto.setEstadoActual(p.getEstadoActual() != null ? p.getEstadoActual().toString() : "Desconocido");

            dtos.add(dto);
        }

        return dtos;
    }


    @Override
    public void asignarEstado(String tituloPropuesta, EEstadoPropuesta estado, LocalDate fecha) {
        Propuesta prop = propuestaDAO.buscarPorTitulo(tituloPropuesta);
        if (prop == null) {
            throw new IllegalArgumentException("Propuesta no encontrada: " + tituloPropuesta);
        }

        Estado nuevoEstado = new Estado();
        nuevoEstado.setFecha(fecha);
        nuevoEstado.setNombre(estado);
        nuevoEstado.setPropuesta(prop);

        prop.setEstadoActual(nuevoEstado);
        prop.getHistorialEstados().add(nuevoEstado);

        propuestaDAO.actualizar(prop);
    }


    public List<DTOPropuesta> listarPropuestasIngresadas() {
        return listarPorEstado(EEstadoPropuesta.INGRESADA);
    }


    public void evaluarPropuesta(String tituloPropuesta, boolean publicar) {
        Propuesta prop = propuestaDAO.buscarPorTitulo(tituloPropuesta);
        if (prop == null) {
            throw new IllegalArgumentException("Propuesta no encontrada: " + tituloPropuesta);
        }

        if (prop.getEstadoActual() == null || prop.getEstadoActual().getNombre() != EEstadoPropuesta.INGRESADA) {
            throw new IllegalStateException("Solo se pueden evaluar propuestas en estado INGRESADA.");
        }

        EEstadoPropuesta nuevoEstado = publicar ?
                EEstadoPropuesta.PUBLICADA : EEstadoPropuesta.CANCELADA;

        Estado estado = new Estado(nuevoEstado, LocalDate.now());
        estado.setPropuesta(prop);

        prop.setEstadoActual(estado);
        prop.getHistorialEstados().add(estado);

        propuestaDAO.actualizar(prop);
    }

    public void cancelarPropuesta(String tituloPropuesta) {
        Propuesta prop = propuestaDAO.buscarPorTitulo(tituloPropuesta);
        if (prop == null) {
            throw new IllegalArgumentException("Propuesta no encontrada: " + tituloPropuesta);
        }

        EEstadoPropuesta estadoActual = prop.getEstadoActual().getNombre();

        if (estadoActual == EEstadoPropuesta.FINANCIADA) {
            Estado nuevoEstado = new Estado(EEstadoPropuesta.CANCELADA, LocalDate.now());
            nuevoEstado.setPropuesta(prop);

            prop.setEstadoActual(nuevoEstado);
            prop.getHistorialEstados().add(nuevoEstado);

            propuestaDAO.actualizar(prop);
        } else {
            throw new IllegalStateException(
                    "No se puede cancelar una propuesta en estado " + estadoActual
            );
        }
    }

    @Override
    public List<DTOPropuesta> buscarPropuestas(String filtro) {
        List<Propuesta> entidades;

        if (filtro == null || filtro.isBlank()) {
            entidades = propuestaDAO.obtenerTodas(); // devuelve List<Propuesta>
        } else {
            entidades = propuestaDAO.buscarPorTexto(filtro);
        }

        // Convertir las entidades a DTOPropuesta
        List<DTOPropuesta> dtos = new ArrayList<>();
        for (Propuesta p : entidades) {
            DTOPropuesta dto = new DTOPropuesta();
            dto.setTitulo(p.getTitulo());
            dto.setDescripcion(p.getDescripcion());
            dto.setLugar(p.getLugar());
            dto.setFecha(p.getFecha());
            dto.setMontoAReunir(p.getMontoAReunir());
            dto.setMontoRecaudado((double) p.getMontoRecaudado());
            dto.setPrecioEntrada(p.getPrecioEntrada());
            dto.setImagen(p.getImagen());
            dto.setEstadoActual(p.getEstadoActual().getNombre().toString());
            dto.setProponenteNick(p.getProponente().getNick());
            dto.setColaboradores(
                    p.getColaboraciones().stream()
                            .map(c -> c.getColaborador().getNick())
                            .toList()
            );
            dtos.add(dto);
        }

        return dtos;
    }

    @Override
    public List<DTOPropuesta> recomendarPropuestas(String nicknameColaborador) {

        Colaborador targetColaborador = colaboradorDAO.buscarPorNick(nicknameColaborador);
        if (targetColaborador == null) {
            throw new IllegalArgumentException("Colaborador no encontrado: " + nicknameColaborador);
        }

        Set<String> propuestasYaColaboradas = targetColaborador.getColaboraciones().stream()
                .map(colab -> colab.getPropuesta().getTitulo())
                .collect(Collectors.toSet());

        Set<Propuesta> propuestasCandidatas = new HashSet<>();

        for (Seguimiento seg : targetColaborador.getUsuariosSeguidos()) {

            String nickSeguido = seg.getUsuarioSeguido();

            Usuario usuarioSeguido = null;
            Colaborador colab = colaboradorDAO.buscarPorNick(nickSeguido);

            if (colab != null) {
                usuarioSeguido = colab;
            } else {
                Proponente prop = proponenteDAO.buscarPorNick(nickSeguido);
                if (prop != null) {
                    usuarioSeguido = prop;
                }
            }

            if (usuarioSeguido != null && usuarioSeguido instanceof Colaborador) {
                Colaborador colabSeguido = (Colaborador) usuarioSeguido;

                for (Colaboracion colabDeSeguido : colabSeguido.getColaboraciones()) {
                    propuestasCandidatas.add(colabDeSeguido.getPropuesta());
                }
            }
        }
        for (Colaboracion miColab : targetColaborador.getColaboraciones()) {
            Propuesta p = miColab.getPropuesta();

            for (Colaboracion otraColab : p.getColaboraciones()) {
                Colaborador coColaborador = otraColab.getColaborador();

                if (coColaborador.getNick().equals(targetColaborador.getNick())) {
                    continue;
                }

                for (Colaboracion colabDeCoColaborador : coColaborador.getColaboraciones()) {
                    propuestasCandidatas.add(colabDeCoColaborador.getPropuesta());
                }
            }
        }

        List<PropuestaConPuntaje> propuestasPuntuadas = new ArrayList<>();

        for (Propuesta p : propuestasCandidatas) {

            if (propuestasYaColaboradas.contains(p.getTitulo())) {
                continue;
            }

            if (p.getEstadoActual() == null) {
                continue;
            }

            EEstadoPropuesta estadoActual = p.getEstadoActual().getNombre();

            if (estadoActual != EEstadoPropuesta.PUBLICADA && estadoActual != EEstadoPropuesta.EN_FINANCIACION) {
                continue;
            }

            int puntajeColaboradores = p.getColaboraciones().size();

            int puntajeFinanciacion = calcularPuntajeFinanciacion(p);

            int puntajeTotal = puntajeColaboradores + puntajeFinanciacion;
            propuestasPuntuadas.add(new PropuestaConPuntaje(p, puntajeTotal));
        }

        propuestasPuntuadas.sort(Comparator.comparingInt(PropuestaConPuntaje::puntaje).reversed());

        return propuestasPuntuadas.stream()
                .limit(10)
                .map(puntuada -> {
                    DTOPropuesta dto = convertirPropuestaADTO(puntuada.propuesta());
                    // agrega puntaje calculado
                    dto.setPuntaje(puntuada.puntaje());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    private record PropuestaConPuntaje(Propuesta propuesta, int puntaje) {}

    private int calcularPuntajeFinanciacion(Propuesta p) {
        if (p.getMontoAReunir() == null || p.getMontoAReunir() <= 0) {
            return 0;
        }

        double porcentaje = (double) p.getMontoRecaudado() / p.getMontoAReunir();

        if (porcentaje <= 0.25) return 1;
        if (porcentaje <= 0.50) return 2;
        if (porcentaje <= 0.75) return 3;
        return 4;
    }

    private DTOPropuesta convertirPropuestaADTO(Propuesta p) {
        DTOPropuesta dto = new DTOPropuesta();
        dto.setTitulo(p.getTitulo());
        dto.setDescripcion(p.getDescripcion());
        dto.setLugar(p.getLugar());
        dto.setFecha(p.getFecha());
        dto.setMontoAReunir(p.getMontoAReunir());
        dto.setMontoRecaudado((double) p.getMontoRecaudado());
        dto.setPrecioEntrada(p.getPrecioEntrada());
        dto.setImagen(p.getImagen());

        if (p.getEstadoActual() != null) {
            dto.setEstadoActual(p.getEstadoActual().getNombre().toString());
        }
        if (p.getProponente() != null) {
            dto.setProponenteNick(p.getProponente().getNick());
        }
        if (p.getCategoria() != null) {
            dto.setCategoriaNombre(p.getCategoria().getNombre());
        }
        if (p.getColaboraciones() != null) {
            dto.setColaboradores(
                    p.getColaboraciones().stream()
                            .map(c -> c.getColaborador().getNick())
                            .toList()
            );
        }
        dto.setRetornos(p.getRetornos());
        return dto;
    }


}