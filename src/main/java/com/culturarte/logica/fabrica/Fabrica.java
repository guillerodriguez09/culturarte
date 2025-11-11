package com.culturarte.logica.fabrica;

import com.culturarte.logica.controllers.*;

public class Fabrica {

    private static Fabrica instancia;

    // controllers a usar
    private final IColaboracionController colaboracionController;
    private final IPropuestaController propuestaController;
    private final IProponenteController proponenteController;
    private final IColaboradorController colaboradorController;
    private final ICategoriaController categoriaController;
    private final ISeguimientoController seguimientoController;
    private final IAccesoController accesoController;

    private Fabrica() {
        this.accesoController = new AccesoController();
        this.colaboracionController = new ColaboracionController();
        this.propuestaController = new PropuestaController();
        this.proponenteController = new ProponenteController();
        this.colaboradorController = new ColaboradorController();
        this.categoriaController = new CategoriaController();
        this.seguimientoController = new SeguimientoController();
    }

    public static Fabrica getInstancia() {
        if (instancia == null) {
            instancia = new Fabrica();
        }
        return instancia;
    }


    public IColaboracionController getColaboracionController() { return colaboracionController; };

    public IPropuestaController getPropuestaController() {
        return propuestaController;
    }

    public IProponenteController getProponenteController() { return proponenteController;}

    public IColaboradorController getColaboradorController() { return colaboradorController;}

    public ICategoriaController getCategoriaController() { return categoriaController;}

    public ISeguimientoController getSeguimientoController(){return seguimientoController;}

    public Object getAccesoController() { return accesoController; }
}
