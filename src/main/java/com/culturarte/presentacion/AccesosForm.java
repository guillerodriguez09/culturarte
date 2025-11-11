package com.culturarte.presentacion;

import com.culturarte.logica.clases.Acceso;
import com.culturarte.logica.controllers.AccesoController;
import com.culturarte.logica.dtos.DTOAcceso;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AccesosForm extends JInternalFrame {

    private AccesoController ctrl;
    private JTable tabla;
    private DefaultTableModel modelo;

    public AccesosForm(AccesoController ctrl) {
        super("Registro de Accesos al Sitio", true, true, true, true);
        this.ctrl = ctrl;

        setSize(900, 400);
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel(new String[]{"Fecha", "IP", "URL", "Browser", "SO"}, 0);
        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> cargarDatos());

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnActualizar);

        add(scroll, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        cargarDatos();
    }

    private void cargarDatos() {
        modelo.setRowCount(0);
        List<DTOAcceso> accesos = ctrl.listarAccesos();
        for (DTOAcceso a : accesos) {
            modelo.addRow(new Object[]{
                    a.getFecha(),
                    a.getIp(),
                    a.getUrl(),
                    a.getBrowser(),
                    a.getSo()
            });
        }
    }
}

