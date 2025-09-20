package com.culturarte.presentacion;

import com.culturarte.logica.clases.Colaboracion;
import com.culturarte.logica.clases.Propuesta;
import com.culturarte.logica.controllers.IColaboracionController;
import com.culturarte.logica.dtos.DTOColabConsulta;
import com.culturarte.logica.fabrica.Fabrica;
import com.culturarte.persistencia.ColaboracionDAO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CancelarColaboración {
    private JPanel mainPanel;
    private JPanel p1;
    private JPanel p2;
    private JComboBox <Integer> comboColab;
    private JTextField nickColab;
    private JTextField fecha;
    private JTextField monto;
    private JTextField retorno;
    private JButton mostrarDatosButton;
    private JButton aceptarButton;
    private JButton cancelarButton;

    public CancelarColaboración() {
        IColaboracionController controller = Fabrica.getInstancia().getColaboracionController();

        List<DTOColabConsulta> colaboraciones = controller.listarColaboraciones();
        for (DTOColabConsulta dto : colaboraciones) {
            comboColab.addItem(dto.getId()); //lista los id de las colaboraciones
        }

        mostrarDatosButton.addActionListener(e -> {
            Integer idSeleccionado = (Integer) comboColab.getSelectedItem();
            if (idSeleccionado == null) return;

            DTOColabConsulta seleccionada = colaboraciones.stream()
                    .filter(c -> c.getId() == idSeleccionado)
                    .findFirst()
                    .orElse(null);

            if (seleccionada != null) { //carga los datos de la colaboracion elegida
                nickColab.setText(seleccionada.getColaboradorNick());
                monto.setText(seleccionada.getMonto().toString());
                retorno.setText(seleccionada.getRetorno().toString());
                fecha.setText(seleccionada.getFecha().toString());
            }
        });

        aceptarButton.addActionListener(e -> {
            Integer idSeleccionado = (Integer) comboColab.getSelectedItem();
            if (idSeleccionado == null) return;

            try {
                controller.cancelarColaboracion(idSeleccionado);
                JOptionPane.showMessageDialog(mainPanel, "Colaboración cancelada con éxito.");
                limpiarCampos();
                comboColab.removeItem(idSeleccionado);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainPanel, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelarButton.addActionListener(e -> {
            JInternalFrame internal = (JInternalFrame) SwingUtilities.getAncestorOfClass(JInternalFrame.class, mainPanel);
            if (internal != null) {
                internal.dispose();
            } else {
                // Por si se ejecuta fuera de un InternalFrame, solo oculta la ventana padre
                Window ventana = SwingUtilities.getWindowAncestor(mainPanel);
                if (ventana != null) {
                    ventana.setVisible(false);
                }
            }
        });
    }

    private void limpiarCampos() {
        nickColab.setText("");
        monto.setText("");
        retorno.setText("");
        fecha.setText("");
    }

    public JPanel traerPanel() {
        return mainPanel;
    }
}


