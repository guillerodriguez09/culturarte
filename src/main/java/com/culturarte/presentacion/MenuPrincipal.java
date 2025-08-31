package com.culturarte.presentacion;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {

    private final JDesktopPane desktopPane = new JDesktopPane();

    public MenuPrincipal() {
        setTitle("Estacion de trabajo - Culturarte");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(desktopPane, BorderLayout.CENTER);

        //barrita menu
        JMenuBar menuBar = new JMenuBar();

        //Menu de propuestas
        JMenu menuPropuesta = new JMenu("Propuesta");
        JMenuItem itemAltaPropuesta = new JMenuItem("Alta de Propuesta");
        JMenuItem itemConsultaPropuesta = new JMenuItem("Consulta de Propuestas");
        JMenuItem itemModificarPropuesta = new JMenuItem("Modificar Propuesta");

        menuPropuesta.add(itemAltaPropuesta);
        menuPropuesta.add(itemConsultaPropuesta);
        menuPropuesta.add(itemModificarPropuesta);
        itemAltaPropuesta.addActionListener(e -> abrirAltaPropuesta());
        itemConsultaPropuesta.addActionListener(e -> abrirConsultaPropuesta());
        itemModificarPropuesta.addActionListener(e -> abrirModificarPropuesta());

        //Menu de usuarios
        JMenu menuUsuario = new JMenu("Usuario");
        JMenuItem itemAltaUsuario = new JMenuItem("Alta de Usuario");
        JMenuItem itemConsultaProponente = new JMenuItem("Consulta de Proponente");
        JMenuItem itemConsultarColaborador = new JMenuItem("Consulta de Colaborador");

        menuUsuario.add(itemAltaUsuario);
        menuUsuario.add(itemConsultaProponente);
        menuUsuario.add(itemConsultarColaborador);

        //menu categorias
        JMenu menuCategoria = new JMenu("Categoria");

        menuBar.add(menuPropuesta);
        menuBar.add(menuUsuario);


        setJMenuBar(menuBar);
    }

    private void abrirAltaPropuesta() {
        AltaPropuestaForm form = new AltaPropuestaForm();
        JInternalFrame frame = new JInternalFrame("Alta de Propuesta", true, true, true, true);
        frame.setContentPane(form.traerPanel());
        frame.pack();
        frame.setVisible(true);
        desktopPane.add(frame);
        try {
            frame.setSelected(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void abrirConsultaPropuesta() {
        ConsultarPropuestaForm form = new ConsultarPropuestaForm();
        JInternalFrame frame = new JInternalFrame("Consulta de Propuesta", true, true, true, true);
        frame.setContentPane(form.traerPanel());
        frame.pack();
        frame.setVisible(true);
        desktopPane.add(frame);
        try {
            frame.setSelected(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void abrirModificarPropuesta() {
        ModificarPropuestaForm form = new ModificarPropuestaForm();
        JInternalFrame frame = new JInternalFrame("Modificar Propuesta", true, true, true, true);
        frame.setContentPane(form.traerPanel());
        frame.pack();
        frame.setVisible(true);
        desktopPane.add(frame);
        try {
            frame.setSelected(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

