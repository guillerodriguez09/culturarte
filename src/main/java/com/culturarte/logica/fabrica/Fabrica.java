package com.culturarte.logica.fabrica;

import com.culturarte.logica.controllers.*;

public class Fabrica {

    private static Fabrica instancia;

    // controllers a usar
    private final IPropuestaController propuestaController;
    private final IProponenteController proponenteController;
    private final IColaboradorController colaboradorController;
    private final ICategoriaController categoriaController;

    private Fabrica() {
        // instanciar los controllers aca
        this.propuestaController = new PropuestaController();
        this.proponenteController = new ProponenteController();
        this.colaboradorController = new ColaboradorController();
        this.categoriaController = new CategoriaController();
    }

    public static Fabrica getInstancia() {
        if (instancia == null) {
            instancia = new Fabrica();
        }
        return instancia;
    }

    // getters de controllers
    public IPropuestaController getPropuestaController() {
        return propuestaController;
    }

    public IProponenteController getProponenteController() { return proponenteController;}

    public IColaboradorController getColaboradorController() { return colaboradorController;}

    public ICategoriaController getCategoriaController() { return categoriaController;}
}

