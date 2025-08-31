package com.culturarte.presentacion;

import com.culturarte.logica.controllers.IPropuestaController;
import com.culturarte.logica.enums.ETipoRetorno;
import com.culturarte.logica.fabrica.Fabrica;

import javax.swing.*;

public class AltaPropuestaForm {
    private JPanel panel1;
    private JComboBox<String> proponente;
    private JComboBox<String> categoria;
    private JTextField titulo;
    private JTextField descripcion;
    private JTextField lugar;
    private JTextField fecha;
    private JTextField precioEntrada;
    private JTextField montoArecaudar;
    private JTextField imagen;
    private JButton seleccionarButton;
    private JList<ETipoRetorno> retornos;

    private final IPropuestaController controller = Fabrica.getInstancia().getPropuestaController();
}
