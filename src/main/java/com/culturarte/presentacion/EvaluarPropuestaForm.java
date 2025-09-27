package com.culturarte.presentacion;

import com.culturarte.logica.dtos.DTOPropuesta;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EvaluarPropuestaForm {
    private JPanel panel1;
    private JComboBox comboProp;
    private JButton botonConfirmar;
    private JRadioButton publicarRadioButton;
    private JRadioButton cancelarRadioButton;


    public EvaluarPropuestaForm() {

        //esto se hace para que solo puedan elegir una opcion
        ButtonGroup group = new ButtonGroup();
        group.add(publicarRadioButton);
        group.add(cancelarRadioButton);

        var controller = com.culturarte.logica.fabrica.Fabrica.getInstancia().getPropuestaController();

        // llena el combobox con los titulos-proponentes
        for (DTOPropuesta dto : controller.listarPropuestasIngresadas()) {
            comboProp.addItem(dto);
        }

        botonConfirmar.addActionListener(e -> {
            DTOPropuesta seleccion = (DTOPropuesta) comboProp.getSelectedItem();

            if (seleccion == null) {
                JOptionPane.showMessageDialog(panel1, "Debe seleccionar una propuesta");
                return;
            }
            if (!publicarRadioButton.isSelected() && !cancelarRadioButton.isSelected()) {
                JOptionPane.showMessageDialog(panel1, "Debe elegir Publicar o Cancelar");
                return;
            }

            boolean publicar = publicarRadioButton.isSelected();


            controller.evaluarPropuesta(seleccion.titulo, publicar);

            JOptionPane.showMessageDialog(panel1,
                    "Propuesta '" + seleccion.titulo + "' del proponente '" + seleccion.proponenteNick +
                            "' fue " + (publicar ? "Publicada" : "Cancelada"));

            // Sacar del combobox porque ya se evaluo y cambio de estado
            comboProp.removeItem(seleccion);
        });
    }


    public JPanel getPanel() {
        return panel1;
    }
}
