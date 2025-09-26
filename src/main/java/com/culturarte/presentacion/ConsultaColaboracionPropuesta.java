package com.culturarte.presentacion;

import com.culturarte.logica.controllers.IColaboracionController;
import com.culturarte.logica.dtos.DTOColabConsulta;
import com.culturarte.logica.fabrica.Fabrica;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ConsultaColaboracionPropuesta {
    private JPanel mainPanel;
    private JComboBox<String> colaboradores;
    private JTable listaColaboraciones;
    private JButton buscar;
    private JTable detallesColaboracion;
    private JButton cancelar;
    private JButton aceptar;

    private final IColaboracionController colaboracionController;
    private List<DTOColabConsulta> todasColaboraciones;
    private List<DTOColabConsulta> colaboracionesFiltradas;

    public ConsultaColaboracionPropuesta() {
        this.colaboracionController = Fabrica.getInstancia().getColaboracionController();

        todasColaboraciones = colaboracionController.listarColaboraciones();

        cargarColaboradores();

        buscar.addActionListener(e -> cargarColaboraciones());
        listaColaboraciones.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                mostrarDetallesSeleccion();
            }
        });
        aceptar.addActionListener(e -> limpiar());

        cancelar.addActionListener(e ->{JInternalFrame internal = (JInternalFrame) SwingUtilities.getAncestorOfClass(JInternalFrame.class, mainPanel);
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

    private void cargarColaboradores() {
        colaboradores.removeAllItems();

        Set<String> colaboradoresUnicos = new HashSet<>();
        for (DTOColabConsulta c : todasColaboraciones) {
            colaboradoresUnicos.add(c.getColaboradorNick());
        }

        for (String nick : colaboradoresUnicos) {
            colaboradores.addItem(nick);
        }

        colaboradores.setSelectedIndex(-1);
    }

    private void cargarColaboraciones() {
        String colaboradorSeleccionado = (String) colaboradores.getSelectedItem();
        if (colaboradorSeleccionado == null) {
            JOptionPane.showMessageDialog(mainPanel, "Seleccione un colaborador");
            return;
        }

        // Filtrar colaboraciones del colaborador seleccionado
        colaboracionesFiltradas = new ArrayList<>();
        for (DTOColabConsulta c : todasColaboraciones) {
            if (c.getColaboradorNick().equals(colaboradorSeleccionado)) {
                colaboracionesFiltradas.add(c);
            }
        }

        // Columnas de la tabla con el nombre de la propuesta
        String[] columnas = {"Propuesta", "Monto", "Retorno", "Fecha"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);

        if (!colaboracionesFiltradas.isEmpty()) {
            for (DTOColabConsulta c : colaboracionesFiltradas) {
                model.addRow(new Object[]{
                        c.getPropuestaNombre(), // <-- nombre de la propuesta
                        c.getMonto(),
                        c.getRetorno(),
                        c.getFecha()
                });
            }
        } else {
            JOptionPane.showMessageDialog(mainPanel, "El colaborador no tiene colaboraciones registradas");
        }

        listaColaboraciones.setModel(model);
        detallesColaboracion.setModel(new DefaultTableModel());
    }

    private void mostrarDetallesSeleccion() {
        int fila = listaColaboraciones.getSelectedRow();
        if (fila < 0 || colaboracionesFiltradas == null || colaboracionesFiltradas.isEmpty()) {
            return;
        }

        DTOColabConsulta c = colaboracionesFiltradas.get(fila);

        String[] columnas = {"Campo", "Valor"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);

        model.addRow(new Object[]{"Propuesta", c.getPropuestaNombre()});
        model.addRow(new Object[]{"Colaborador", c.getColaboradorNick()});
        model.addRow(new Object[]{"Monto", c.getMonto()});
        model.addRow(new Object[]{"Retorno", c.getRetorno()});
        model.addRow(new Object[]{"Fecha", c.getFecha()});

        detallesColaboracion.setModel(model);
    }

    private void limpiar() {
        listaColaboraciones.setModel(new DefaultTableModel());
        detallesColaboracion.setModel(new DefaultTableModel());
        colaboradores.setSelectedIndex(-1);
    }

    private void cerrar() {
        SwingUtilities.getWindowAncestor(mainPanel).dispose();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
