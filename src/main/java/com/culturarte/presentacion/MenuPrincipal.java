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
        JMenuItem itemConsultaPropuestasEstado = new JMenuItem("Consulta Propuestas por Estado");
        JMenuItem itemModificarPropuesta = new JMenuItem("Modificar Propuesta");

        menuPropuesta.add(itemAltaPropuesta);
        menuPropuesta.add(itemConsultaPropuesta);
        menuPropuesta.add(itemConsultaPropuestasEstado);
        menuPropuesta.add(itemModificarPropuesta);
        itemAltaPropuesta.addActionListener(e -> abrirAltaPropuesta());
        itemConsultaPropuesta.addActionListener(e -> abrirConsultaPropuesta());
        itemConsultaPropuestasEstado.addActionListener(e -> abrirConsultaPropuestasEstado());
        itemModificarPropuesta.addActionListener(e -> abrirModificarPropuesta());

        //Menu de usuarios
        JMenu menuUsuario = new JMenu("Usuario");
        JMenuItem itemAltaUsuario = new JMenuItem("Alta de Usuario");
        JMenuItem itemConsultaProponente = new JMenuItem("Consulta de Proponente");
        JMenuItem itemConsultarColaborador = new JMenuItem("Consulta de Colaborador");
        JMenuItem itemSeguimientoUsuario = new JMenuItem("Seguimiento de Usuarios");

        itemAltaUsuario.addActionListener(e -> abrirAltaUsuario());
        itemConsultaProponente.addActionListener(e -> abrirConsultaProponente());
        itemConsultarColaborador.addActionListener(e -> abrirConsultaColaborador());
        itemSeguimientoUsuario.addActionListener(e -> abrirSeguimientoUsuario());


        menuUsuario.add(itemAltaUsuario);
        menuUsuario.add(itemConsultaProponente);
        menuUsuario.add(itemConsultarColaborador);
        menuUsuario.add(itemSeguimientoUsuario);

        //menu categorias
        JMenu menuCategoria = new JMenu("Categoria");
        JMenuItem itemAltaCategoria = new JMenuItem("Alta de Categoria");

        itemAltaCategoria.addActionListener(e -> abrirAltaCategoria());

        menuCategoria.add(itemAltaCategoria);

        //menu colaboraciones
        JMenu menuColab = new JMenu("Colaboraciones");
        JMenuItem itemRegistrarColaboracion = new JMenuItem("Registrar Colaboracion");
        JMenuItem itemConsultarColaboracion = new JMenuItem("Consultar Colaboracion");
        JMenuItem  itemCancelarColaboracion = new JMenuItem("Cancelar Colaboracion");

        menuColab.add(itemRegistrarColaboracion);
        menuColab.add(itemCancelarColaboracion);
        menuColab.add(itemConsultarColaboracion);

        itemRegistrarColaboracion.addActionListener(e -> abrirRegistrarColaboracion());
        itemConsultarColaboracion.addActionListener(e -> abrirConsultaColaboracionPropuesta());
        itemCancelarColaboracion.addActionListener(e -> abrirCancelarColaboracion());

        JMenu menuDDP = new JMenu("Datos de Prueba");
        JMenuItem itemCargarDatosDePrueba = new JMenuItem("Cargar Datos de Prueba");

        menuDDP.add(itemCargarDatosDePrueba);

        itemCargarDatosDePrueba.addActionListener(e -> cargarDatosDePrueba());

        menuBar.add(menuPropuesta);
        menuBar.add(menuUsuario);
        menuBar.add(menuCategoria);
        menuBar.add(menuColab);
        menuBar.add(menuDDP);

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

    private void abrirConsultaProponente() {
        ConsultaProponenteForm consProp = new ConsultaProponenteForm();
        JInternalFrame frame = new JInternalFrame("Consulta de Proponente", true, true, true, true);
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

    private void abrirConsultaColaborador() {
        ConsultaColaboradorFrom consCol = new ConsultaColaboradorFrom();
        JInternalFrame frame = new JInternalFrame("Consulta de Colaborador", true, true, true, true);
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
    /*
    private void abrirRegistrarColaboracion() {
       RegistrarColaboracion regColab = new RegistrarColaboracion();
        JInternalFrame frame = new JInternalFrame("Registrar Colaboracion", true, true, true, true);
        frame.setContentPane(regColab.traerPanel());
        frame.pack();
        frame.setVisible(true);
        desktopPane.add(frame);
        try {
            frame.setSelected(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    private void abrirCancelarColaboracion() {
        CancelarColaboración canceColab = new CancelarColaboración();
        JInternalFrame frame = new JInternalFrame("Cancelar Colaboracion", true, true, true, true);
        frame.setContentPane(canceColab.traerPanel());
        frame.pack();
        frame.setVisible(true);
        desktopPane.add(frame);
        try {
            frame.setSelected(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void abrirSeguimientoUsuario() {
        SeguimientoUsuarioForm seguiUsr = new SeguimientoUsuarioForm();
        JInternalFrame frame = new JInternalFrame("Seguimiento Usuario", true, true, true, true);
        frame.setContentPane(seguiUsr.getMainPanel());
        frame.pack();
        frame.setVisible(true);
        desktopPane.add(frame);
        try {
            frame.setSelected(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarDatosDePrueba() {
        DatosDePrueba DDP = new DatosDePrueba();
        try {
            DDP.crearDatosPrueba();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void abrirRegistrarColaboracion() {
        RegistrarColaboracion form = new RegistrarColaboracion();
        JInternalFrame frame = new JInternalFrame("Registrar Colaboración", true, true, true, true);
        frame.setContentPane(form.getMainPanel());
        frame.pack();
        frame.setVisible(true);
        desktopPane.add(frame);
        try {
            frame.setSelected(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void abrirConsultaColaboracionPropuesta() {
        ConsultaColaboracionPropuesta form = new ConsultaColaboracionPropuesta();
        JInternalFrame frame = new JInternalFrame("Consulta de Colaboraciones", true, true, true, true);
        frame.setContentPane(form.getMainPanel());
        frame.pack();
        frame.setVisible(true);
        desktopPane.add(frame);
        try {
            frame.setSelected(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void abrirConsultaPropuestasEstado() {
        ConsultaPropuestasEstado form = new ConsultaPropuestasEstado();
        JInternalFrame frame = new JInternalFrame("Consulta de Propuestas por Estado", true, true, true, true);
        frame.setContentPane(form.getMainPanel());
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

