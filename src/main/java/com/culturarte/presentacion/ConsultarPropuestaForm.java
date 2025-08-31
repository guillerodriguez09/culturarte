package com.culturarte.presentacion;

import javax.swing.*;
import com.culturarte.logica.controllers.IPropuestaController;
import com.culturarte.logica.dtos.DTOConsultaPropuesta;
import com.culturarte.logica.fabrica.Fabrica;

import java.awt.*;
import java.util.List;


public class ConsultarPropuestaForm {
    private JPanel mainPanel;
    private JPanel pan1;
    private JPanel pan2;
    private JComboBox <String> comboPropuestas;
    private JTextField titulo;
    private JTextField descripcion;
    private JTextField lugar;
    private JTextField fecha;
    private JTextField montoE;
    private JTextField montoR;
    private JTextField estadoActual;
    private JList <String> colaboradores;
    private JTextField montoRecaudado;
    private JLabel imagendir;
    private JButton cancelarButton;
    private JButton aceptarButton;

    private final IPropuestaController controller = Fabrica.getInstancia().getPropuestaController();

    public ConsultarPropuestaForm() {
        //llenar combo con propuestas q existen
        List<String> propuestas = controller.listarPropuestas();
        for (String titulo : propuestas) comboPropuestas.addItem(titulo);
        //al seleccionar una muestra los datos
        // Botón Aceptar → carga datos
        aceptarButton.addActionListener(e -> mostrarDatosPropuesta());
        cancelarButton.addActionListener(e -> SwingUtilities.getWindowAncestor(mainPanel).dispose());
    }

    private void mostrarDatosPropuesta() {
        String seleccionado = (String) comboPropuestas.getSelectedItem();
        if (seleccionado == null) return;

        DTOConsultaPropuesta dto = controller.consultarPropuesta(seleccionado);

        titulo.setText(dto.titulo);
        descripcion.setText(dto.descripcion);
        lugar.setText(dto.lugar);
        fecha.setText(dto.fecha.toString());
        montoE.setText(dto.precioEntrada.toString());
        montoR.setText(dto.montoAReunir.toString());
        estadoActual.setText(dto.estado);
        montoRecaudado.setText(dto.montoRecaudado.toString());

        colaboradores.setListData(dto.colaboradores.toArray(new String[0]));

        if (dto.imagen != null && !dto.imagen.isBlank()) {
            ImageIcon icon = new ImageIcon(dto.imagen);
            Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            imagendir.setIcon(new ImageIcon(img));
        } else {
            imagendir.setIcon(null);
        }
    }

    public JPanel traerPanel() {
        return mainPanel;
    }

}
