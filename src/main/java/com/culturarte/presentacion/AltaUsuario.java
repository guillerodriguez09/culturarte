package com.culturarte.presentacion;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.culturarte.logica.fabrica.Fabrica;
import com.culturarte.presentacion.Culturarte;
import com.culturarte.logica.dtos.DTOUsuario;
import com.culturarte.logica.dtos.DTOProponente;
import com.culturarte.logica.dtos.DTOColaborador;
import com.culturarte.logica.controllers.IProponenteController;
import com.culturarte.logica.controllers.IColaboradorController;

public class AltaUsuario {
    private JPanel mainPanel;
    private JButton btnAceptar;
    private JRadioButton rbtnProponente;
    private JRadioButton rbtnColaborador;
    private JLabel lblNickname;
    private JLabel lblNombre;
    private JLabel lblApellido;
    private JLabel lblCorreo;
    private JLabel lblFechaNacimiento;
    private JTextField txtNickname;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtCorreo;
    private JFormattedTextField ftxtFechaNacimiento;
    private JButton btnCancelar;
    private JButton btnSelImagen;
    private JLabel lblDir;
    private JLabel lblBio;
    private JLabel lblLink;
    private JTextField txtDir;
    private JTextField txtBio;
    private JTextField txtLink;
    private JLabel lblInformaDoor;
    private JPanel pnlInfoProp;

    public AltaUsuario(){

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rbtnProponente);
        grupo.add(rbtnColaborador);

        pnlInfoProp.setVisible(false);

        rbtnProponente.addActionListener(e -> {
            pnlInfoProp.setVisible(true);
            pnlInfoProp.revalidate();
            pnlInfoProp.repaint();
            SwingUtilities.getWindowAncestor(mainPanel).pack();
            System.out.println("Seleccionaste Proponente");
        });

        rbtnColaborador.addActionListener(e -> {
            pnlInfoProp.setVisible(false);
            pnlInfoProp.revalidate();
            pnlInfoProp.repaint();
            SwingUtilities.getWindowAncestor(mainPanel).pack();
            System.out.println("Seleccionaste Colaborador");
        });

        btnAceptar.addActionListener(e -> {
            String nick = txtNickname.getText();
            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            String correo = txtCorreo.getText();

            String fechaNacimiento = ftxtFechaNacimiento.getText();
            LocalDate fecha = LocalDate.of(2001, 9, 11);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            try {
                fecha = LocalDate.parse(fechaNacimiento, formatter);
                JOptionPane.showMessageDialog(mainPanel, "Fecha ingresada: " + fecha);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(mainPanel, "Formato inválido. Usa dd/MM/yyyy",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

            String tipoUsuario = rbtnProponente.isSelected() ? "Proponente" : "Colaborador";

            if (nick.isBlank() || nombre.isBlank() || apellido.isBlank() || correo.isBlank() || fechaNacimiento.isBlank()){
                JOptionPane.showMessageDialog(mainPanel,
                        "Por favor completa todos los campos",
                        "Faltan datos",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            String dir = "";
            String bio = "";
            String link = "";
            if(tipoUsuario.equals("Proponente")){
                dir = txtDir.getText();
                bio = txtBio.getText();
                link = txtLink.getText();

                if(dir.isBlank()){
                    JOptionPane.showMessageDialog(mainPanel,
                            "Por favor completa todos los campos",
                            "Faltan datos",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

            }

            if(tipoUsuario.equals("Colaborador")){
                DTOColaborador dtoC = new DTOColaborador();
                dtoC.setNick(nick);
                dtoC.setNombre(nombre);
                dtoC.setApellido(apellido);
                dtoC.setCorreo(correo);
                dtoC.setFechaNac(fecha);
                dtoC.setDirImagen("C:\\Users\\Chorizo-Cosmico\\Pictures\\sesi.jpg");

                IColaboradorController controllerCol = Fabrica.getInstancia().getColaboradorController();
                controllerCol.altaColaborador(dtoC);

            }else if(tipoUsuario.equals("Proponente")){
                DTOProponente dtoP = new DTOProponente();
                dtoP.setNick(nick);
                dtoP.setNombre(nombre);
                dtoP.setApellido(apellido);
                dtoP.setCorreo(correo);
                dtoP.setFechaNac(fecha);
                dtoP.setDirImagen("C:\\Users\\Chorizo-Cosmico\\Pictures\\sesi.png");
                dtoP.setDireccion(dir);
                dtoP.setBiografia(bio);
                dtoP.setLink(link);

                IProponenteController controllerPro = Fabrica.getInstancia().getProponenteController();
                controllerPro.altaProponente(dtoP);
            }

        });

        btnCancelar.addActionListener(e ->{
            SwingUtilities.getWindowAncestor(mainPanel).dispose();
        });

    }

    public JPanel getMainPanel() { return mainPanel; }

}
