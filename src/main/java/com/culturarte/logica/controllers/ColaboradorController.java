package com.culturarte.logica.controllers;

import com.culturarte.logica.clases.Colaboracion;
import com.culturarte.logica.clases.Colaborador;
import com.culturarte.logica.clases.Propuesta;
import com.culturarte.logica.dtos.DTOColaborador;
import com.culturarte.logica.dtos.DTOPropuesta;
import com.culturarte.persistencia.ColaboradorDAO;
import jakarta.jws.WebService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@WebService(endpointInterface = "com.culturarte.logica.controllers.IColaboradorController")
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
        if(dtoC.getContrasenia() == null || dtoC.getContrasenia().isBlank() ){
            throw new IllegalArgumentException("Contraseña de colaborador es obligatoria.");
        }
        if (dtoC.getCorreo() == null || dtoC.getCorreo().isBlank() ){
            throw new IllegalArgumentException("Correo de colaborador es obligatorio.");
        }
        if (dtoC.getFechaNac() == null){
            throw new IllegalArgumentException("Fecha de nacimiento de colaborador es obligatoria.");
        }
        if (dtoC.getDirImagen() == null){
            dtoC.setDirImagen("");
        }

        if (colaboradorDAO.existe(dtoC.getNick())) {
            throw new IllegalArgumentException("Ya existe un colaborador con ese nickname.");
        }

        Colaborador col = new Colaborador(
                dtoC.getNick(),
                dtoC.getNombre(),
                dtoC.getApellido(),
                dtoC.getContrasenia(),
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
    public List<DTOColaborador> listarTodos(){
        List<Colaborador> cola = colaboradorDAO.obtenerTodos();
        List<DTOColaborador> dtoCol = new ArrayList<>();

        for (Colaborador col : cola) {
            DTOColaborador dtoCola = new DTOColaborador();

            dtoCola.setNick(col.getNick());
            dtoCola.setNombre(col.getNombre());
            dtoCola.setApellido(col.getApellido());
            dtoCola.setContrasenia(col.getContrasenia());
            dtoCola.setCorreo(col.getCorreo());
            dtoCola.setFechaNac(col.getFechaNac());
            dtoCola.setDirImagen(col.getDirImagen());

            dtoCol.add(dtoCola);

        }

        return dtoCol;
    }

    @Override
    public List<Object[]> obtenerTodColConPropu(String nick) {
        List<Object[]> Tuti = colaboradorDAO.obtenerTodColConPropu(nick);
        if(Tuti == null){return null;}
        List<Object[]> Fruti = new ArrayList<>();
        for(Object[] fila : Tuti) {

            Colaborador col = (Colaborador) fila[0];
            Propuesta p = (Propuesta) fila[2];

            DTOColaborador dtoCol = new DTOColaborador();
            DTOPropuesta dtoCP = new DTOPropuesta();

            dtoCol.setNick(col.getNick());
            dtoCol.setNombre(col.getNombre());
            dtoCol.setApellido(col.getApellido());
            dtoCol.setContrasenia(col.getContrasenia());
            dtoCol.setCorreo(col.getCorreo());
            dtoCol.setFechaNac(col.getFechaNac());
            dtoCol.setDirImagen(col.getDirImagen());

            dtoCP.titulo = p.getTitulo();
            dtoCP.descripcion = p.getDescripcion();
            dtoCP.lugar = p.getLugar();
            dtoCP.fecha = p.getFecha();
            dtoCP.precioEntrada = p.getPrecioEntrada();
            dtoCP.montoAReunir = p.getMontoAReunir();
            dtoCP.imagen = p.getImagen();
            dtoCP.categoriaNombre = p.getCategoria().getNombre();
            dtoCP.proponenteNick = p.getProponente().getNick();
            dtoCP.fechaPublicacion = p.getFechaPublicacion();
            dtoCP.retornos = p.getRetornos();

            // Estado actual
            if (p.getEstadoActual() != null) {
                dtoCP.estadoActual = p.getEstadoActual().getNombre().toString();
            } else {
                dtoCP.estadoActual = "DESCONOCIDO";
            }

            // Colaboradores
            List<String> colaboradores = new ArrayList<>();
            for (Colaboracion colab : p.getColaboraciones()) {
                if (colab.getColaborador() != null) {
                    colaboradores.add(colab.getColaborador().getNick());
                }
            }
            dtoCP.colaboradores = colaboradores;

            //Monto recaudado actualizado
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
        dtoCol.setContrasenia(col.getContrasenia());
        dtoCol.setCorreo(col.getCorreo());
        dtoCol.setFechaNac(col.getFechaNac());
        dtoCol.setDirImagen(col.getDirImagen());

        return dtoCol;

    }

    @Override
    public DTOColaborador obtenerColaboradorCorreo(String correo){

        DTOColaborador dtoCol = new DTOColaborador();

        try {

            Colaborador col = colaboradorDAO.buscarPorCorreo(correo);

            if (col == null) {return null;}


            dtoCol.setNick(col.getNick());
            dtoCol.setNombre(col.getNombre());
            dtoCol.setApellido(col.getApellido());
            dtoCol.setContrasenia(col.getContrasenia());
            dtoCol.setCorreo(col.getCorreo());
            dtoCol.setFechaNac(col.getFechaNac());
            dtoCol.setDirImagen(col.getDirImagen());

        }catch(Exception e){
            return null;
        }

        return dtoCol;

    }


}
