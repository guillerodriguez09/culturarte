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

        itemAltaUsuario.addActionListener(e -> abrirAltaUsuario());
        //itemConsultaProponente.addActionListener(e -> abrirConsultaProponente());
        //itemConsultaColaborador.addActionListener(e -> abrirConsultaColaborador());


        menuUsuario.add(itemAltaUsuario);
        menuUsuario.add(itemConsultaProponente);
        menuUsuario.add(itemConsultarColaborador);

        //menu categorias
        JMenu menuCategoria = new JMenu("Categoria");
        JMenuItem itemAltaCategoria = new JMenuItem("Alta de Categoria");

        itemAltaCategoria.addActionListener(e -> abrirAltaCategoria());

        menuCategoria.add(itemAltaCategoria);

        menuBar.add(menuPropuesta);
        menuBar.add(menuUsuario);
        menuBar.add(menuCategoria);

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
        frame.setSize(Math.max(frame.getWidth(), 600), Math.max(frame.getHeight(), 600));
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

    private void abrirAltaUsuario() {
        AltaUsuario altaUsr = new AltaUsuario();
        JInternalFrame frame = new JInternalFrame("Alta de Usuario", true, true, true, true);
        frame.setContentPane(altaUsr.getMainPanel());
        frame.pack();
        frame.setVisible(true);
        desktopPane.add(frame);
        try {
            frame.setSelected(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void abrirAltaCategoria() {
        AltaCategoriaForm altaCat = new AltaCategoriaForm();
        JInternalFrame frame = new JInternalFrame("Alta de Categoria", true, true, true, true);
        frame.setContentPane(altaCat.getMainPanel());
        frame.pack();
        frame.setVisible(true);
        desktopPane.add(frame);
        try {
            frame.setSelected(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/*
    private void abrirConsultaProponente() {
        AltaUsuario consProp = new AltaUsuario();
        JInternalFrame frame = new JInternalFrame("Alta de Usuario", true, true, true, true);
        frame.setContentPane(consProp.getMainPanel());
        frame.pack();
        frame.setVisible(true);
        desktopPane.add(frame);
        try {
            frame.setSelected(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*//*
    private void abrirConsultaColaborador() {
        AltaUsuario consCol = new AltaUsuario();
        JInternalFrame frame = new JInternalFrame("Alta de Usuario", true, true, true, true);
        frame.setContentPane(consCol.getMainPanel());
        frame.pack();
        frame.setVisible(true);
        desktopPane.add(frame);
        try {
            frame.setSelected(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/

}

