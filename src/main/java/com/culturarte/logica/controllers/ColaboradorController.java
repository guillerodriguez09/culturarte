package com.culturarte.logica.controllers;

import com.culturarte.logica.clases.Colaboracion;
import com.culturarte.logica.clases.Colaborador;
import com.culturarte.logica.clases.Propuesta;
import com.culturarte.logica.dtos.DTOColPropu;
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
    public List<DTOColPropu> obtenerTodColConPropu(String nick) {
        List<Object[]> filas = colaboradorDAO.obtenerTodColConPropu(nick);
        List<DTOColPropu> resultado = new ArrayList<>();

        if (filas == null || filas.isEmpty()) {
            return resultado;
        }

        DTOColaborador dtoCol = null;
        DTOColPropu pack = null;

        for (Object[] fila : filas) {
            if (fila.length < 3 || !(fila[0] instanceof Colaborador) || !(fila[2] instanceof Propuesta)) {
                System.err.println("Fila inválida: " + Arrays.toString(fila));
                continue;
            }

            Colaborador col = (Colaborador) fila[0];
            Propuesta p = (Propuesta) fila[2];

            if (dtoCol == null) {
                dtoCol = new DTOColaborador();
                dtoCol.setNick(col.getNick());
                dtoCol.setNombre(col.getNombre());
                dtoCol.setApellido(col.getApellido());
                dtoCol.setContrasenia(col.getContrasenia());
                dtoCol.setCorreo(col.getCorreo());
                dtoCol.setFechaNac(col.getFechaNac());
                dtoCol.setDirImagen(col.getDirImagen());

                pack = new DTOColPropu(dtoCol);
            }

            DTOPropuesta dtoCP = new DTOPropuesta();
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
            dtoCP.estadoActual = (p.getEstadoActual() != null)
                    ? p.getEstadoActual().getNombre().toString()
                    : "DESCONOCIDO";

            // Colaboradores
            dtoCP.colaboradores = new ArrayList<>();
            for (Colaboracion colab : p.getColaboraciones()) {
                if (colab.getColaborador() != null)
                    dtoCP.colaboradores.add(colab.getColaborador().getNick());
            }

            // Monto recaudado
            dtoCP.montoRecaudado = (double) p.getMontoRecaudado();

            pack.addPropuesta(dtoCP);
        }

        if (pack != null) {
            resultado.add(pack);
        }

        return resultado;
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
