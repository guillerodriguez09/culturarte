package com.culturarte.presentacion;

import com.culturarte.logica.controllers.IProponenteController;
import com.culturarte.logica.controllers.IPropuestaController;
import com.culturarte.logica.dtos.DTOProponente;
import com.culturarte.logica.dtos.DTOPropuesta;
import com.culturarte.logica.enums.EEstadoPropuesta;
import com.culturarte.logica.fabrica.Fabrica;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConsultaProponenteForm {
    private JPanel mainPanel;
    private JComboBox <String> cbxPropEleg;
    private JLabel txtProEleg;
    private JLabel lblNickname;
    private JLabel lblNombre;
    private JLabel lblApellido;
    private JLabel lblCorreo;
    private JLabel lblFechaNacimiento;
    private JLabel lblImagen;
    private JLabel lblImagenShow;
    private JButton btnAceptar;
    private JButton btnCancelar;
    private JTextField txtNickname;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtCorreo;
    private JTextField txtFechaNacimiento;
    private JLabel lblBio;
    private JLabel lblLink;
    private JTextPane txtBio;
    private JTextField txtLink;
    private JComboBox <EEstadoPropuesta> cbxEstado;
    private JList listPropuXEstado;
    private JLabel Franchesco;

    private final IProponenteController controllerProp = Fabrica.getInstancia().getProponenteController();
    private final IPropuestaController controllerPropuesta = Fabrica.getInstancia().getPropuestaController();

    public ConsultaProponenteForm(){

        List<String> proponentes = controllerProp.listarProponentes();
        for (String nick : proponentes) cbxPropEleg.addItem(nick);

        cbxEstado.addItem(EEstadoPropuesta.INGRESADA);
        cbxEstado.addItem(EEstadoPropuesta.PUBLICADA);
        cbxEstado.addItem(EEstadoPropuesta.EN_FINANCIACION);
        cbxEstado.addItem(EEstadoPropuesta.FINANCIADA);
        cbxEstado.addItem(EEstadoPropuesta.NO_FINANCIADA);
        cbxEstado.addItem(EEstadoPropuesta.CANCELADA);

        btnAceptar.addActionListener(e ->{
            mostrarDatosProponente();
            EEstadoPropuesta estado = (EEstadoPropuesta)cbxEstado.getSelectedItem();
            String prop = (String)cbxPropEleg.getSelectedItem();
            llamarPropuEstados(estado, prop);
        });

        btnCancelar.addActionListener(e ->{
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

        cbxEstado.addActionListener(e ->{

        });

    }

    private void mostrarDatosProponente(){

        String prop = (String) cbxPropEleg.getSelectedItem();
        if (prop == null){
            return;
        }

        DTOProponente dtoProp = controllerProp.obtenerProponente(prop);
        //List<Object[]> propConPropu = controllerProp.obtenerTodPropConPropu(prop);

        txtNickname.setText(dtoProp.getNick());
        txtNombre.setText(dtoProp.getNombre());
        txtApellido.setText(dtoProp.getApellido());
        txtCorreo.setText(dtoProp.getCorreo());
        txtFechaNacimiento.setText(dtoProp.getFechaNac().toString());

        if (dtoProp.getDirImagen() != null && !dtoProp.getDirImagen().isBlank()) {
            File file = new File(System.getProperty("user.dir"), dtoProp.getDirImagen());
            if (file.exists()) {
                ImageIcon icon = new ImageIcon(file.getAbsolutePath());
                Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                lblImagenShow.setIcon(new ImageIcon(img));
            } else {
                System.out.println("Imagen de proponente no encontrada: " + file.getAbsolutePath());
                lblImagenShow.setIcon(null);
            }
        } else {
            lblImagenShow.setIcon(null);
        }


        txtBio.setText(dtoProp.getBiografia());
        txtLink.setText(dtoProp.getLink());

    }

    public JPanel getMainPanel(){

        return mainPanel;

    }

    private void llamarPropuEstados(EEstadoPropuesta estado, String prop){

        List<Object[]> colConPopuYEstado = controllerProp.obtenerPropConPropuYEstado(estado, prop);

        List<String> elem = new ArrayList<>();

        for(Object[] fila : colConPopuYEstado) {
            DTOProponente dtoCol = (DTOProponente) fila[0];
            DTOPropuesta dtoCP = (DTOPropuesta) fila[1];

            // Llama consultarPropuesta para traer el DTO completo, porque en el dtocp no trae directo la lista de colaboradores
            DTOPropuesta dtoCompleto = controllerPropuesta.consultarPropuesta(dtoCP.titulo);

            String colaboradores = String.join(", ", dtoCompleto.colaboradores);
            String linea = dtoCompleto.titulo + " - " + colaboradores + " - Monto: " + dtoCompleto.montoRecaudado;
            elem.add(linea);
        }

            listPropuXEstado.setListData(elem.toArray(new String[0]));
        }

    }

