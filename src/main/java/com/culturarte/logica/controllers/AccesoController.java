package com.culturarte.logica.controllers;

import com.culturarte.logica.clases.Acceso;
import com.culturarte.logica.dtos.DTOAcceso;
import com.culturarte.persistencia.AccesoDAO;
import jakarta.jws.WebService;

import java.util.List;
import java.util.stream.Collectors;

@WebService(endpointInterface = "com.culturarte.logica.controllers.IAccesoController")
public class AccesoController implements IAccesoController {

    private AccesoDAO accesoDao = new AccesoDAO();

    @Override
    public List<DTOAcceso> listarAccesos() {
        return accesoDao.obtenerAccesos().stream()
                .map(a -> new DTOAcceso(a.getIp(), a.getUrl(), a.getBrowser(), a.getSo(), a.getFecha()))
                .collect(Collectors.toList());
    }

    @Override
    public void registrarAcceso(DTOAcceso dto) {
        Acceso a = new Acceso(dto.getIp(), dto.getUrl(), dto.getBrowser(), dto.getSo(), dto.getFecha());
        accesoDao.guardar(a);
    }
}

