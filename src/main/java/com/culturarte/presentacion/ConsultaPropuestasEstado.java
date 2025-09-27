package com.culturarte.presentacion;

import com.culturarte.logica.controllers.IPropuestaController;
import com.culturarte.logica.dtos.DTOPropuesta;
import com.culturarte.logica.enums.EEstadoPropuesta;
import com.culturarte.logica.fabrica.Fabrica;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ConsultaPropuestasEstado {

    private JPanel mainPanel;
    private JComboBox<EEstadoPropuesta> estados;
    private JButton buscar;
    private JTable listado;
    private JButton verDetalles;
    private JButton cancelar;
    private JButton aceptar;
    private JLabel VerDatos;

    private final IPropuestaController propuestaController;
    private List<DTOPropuesta> propuestas;

    public ConsultaPropuestasEstado() {
        this.propuestaController = Fabrica.getInstancia().getPropuestaController();

        cargarEstados();

        buscar.addActionListener(e -> cargarPropuestas());
        verDetalles.addActionListener(e -> mostrarDetallesSeleccion());
        cancelar.addActionListener(e -> {
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
        aceptar.addActionListener(e -> limpiar());
    }

    private void cargarEstados() {
        estados.removeAllItems();
        for (EEstadoPropuesta estado : EEstadoPropuesta.values()) {
            estados.addItem(estado);
        }
        estados.setSelectedIndex(-1);
    }

    private void cargarPropuestas() {
        EEstadoPropuesta estadoSeleccionado = (EEstadoPropuesta) estados.getSelectedItem();
        if (estadoSeleccionado == null) {
            JOptionPane.showMessageDialog(mainPanel, "Seleccione un estado de propuesta");
            return;
        }

        propuestas = propuestaController.listarPorEstado(estadoSeleccionado);

        String[] columnas = {"Título", "Proponente", "Monto Recaudado", "Estado"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);

        if (propuestas != null && !propuestas.isEmpty()) {
            for (DTOPropuesta p : propuestas) {
                model.addRow(new Object[]{
                        p.getTitulo(),
                        p.getProponenteNick(),
                        p.getMontoRecaudado(),
                        p.getEstadoActual()
                });
            }
        } else {
            JOptionPane.showMessageDialog(mainPanel, "No hay propuestas en ese estado");
        }

        listado.setModel(model);
    }

    private void mostrarDetallesSeleccion() {
        int fila = listado.getSelectedRow();
        if (fila < 0 || propuestas == null || propuestas.isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "Seleccione una propuesta de la lista");
            return;
        }

        DTOPropuesta p = propuestas.get(fila);

        StringBuilder detalles = new StringBuilder();
        detalles.append("Título: ").append(p.getTitulo()).append("\n");
        detalles.append("Descripción: ").append(p.getDescripcion()).append("\n");
        detalles.append("Lugar: ").append(p.getLugar()).append("\n");
        detalles.append("Fecha: ").append(p.getFecha()).append("\n");
        detalles.append("Monto a Reunir: ").append(p.getMontoAReunir()).append("\n");
        detalles.append("Monto Recaudado: ").append(p.getMontoRecaudado()).append("\n");
        detalles.append("Proponente: ").append(p.getProponenteNick()).append("\n");
        detalles.append("Categoría: ").append(p.getCategoria()).append("\n");
        detalles.append("Retornos: ").append(p.getRetornos()).append("\n");
        detalles.append("Estado Actual: ").append(p.getEstadoActual());

        JOptionPane.showMessageDialog(mainPanel, detalles.toString(),
                "Detalles de la propuesta", JOptionPane.INFORMATION_MESSAGE);
    }

    private void limpiar() {
        listado.setModel(new DefaultTableModel());
        estados.setSelectedIndex(-1);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
