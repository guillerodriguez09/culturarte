package com.culturarte.presentacion;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.culturarte.logica.fabrica.Fabrica;
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
    private JTextField txtImagen;
    private JLabel lblImagen;

    public AltaUsuario(){

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rbtnProponente);
        grupo.add(rbtnColaborador);

        pnlInfoProp.setVisible(false);

        rbtnProponente.addActionListener(e -> {
            pnlInfoProp.setVisible(true);
            pnlInfoProp.revalidate();
            pnlInfoProp.repaint();
        });

        rbtnColaborador.addActionListener(e -> {
            pnlInfoProp.setVisible(false);
            pnlInfoProp.revalidate();
            pnlInfoProp.repaint();
        });

        btnSelImagen.addActionListener(e -> { seleccionarImagen(); });

        btnAceptar.addActionListener(e -> {
            String nick = txtNickname.getText();
            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            String correo = txtCorreo.getText();

            String fechaNac = ftxtFechaNacimiento.getText();

            String tipoUsuario = rbtnProponente.isSelected() ? "Proponente" : "Colaborador";

            if (nick.isBlank() || nombre.isBlank() || apellido.isBlank() || correo.isBlank() || fechaNac.isEmpty()){
                JOptionPane.showMessageDialog(mainPanel,
                        "Por favor completa todos los campos",
                        "Faltan datos",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            LocalDate fechaNacimiento = null;
            try {
                fechaNacimiento = LocalDate.parse(fechaNac);
            }catch(DateTimeParseException ex){
                JOptionPane.showMessageDialog(mainPanel, "Formato inválido. Usa yyyy-mm-dd", "Fecha Incorrecta",  JOptionPane.ERROR_MESSAGE);
            }

            String dir = "";
            if(tipoUsuario.equals("Proponente")){
                dir = txtDir.getText();
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
                dtoC.setFechaNac(fechaNacimiento);
                dtoC.setDirImagen(txtImagen.getText());

                IColaboradorController controllerCol = Fabrica.getInstancia().getColaboradorController();
                controllerCol.altaColaborador(dtoC);
                JOptionPane.showMessageDialog(mainPanel, "Colaborador creado");
                limpiarCampos();

            }else if(tipoUsuario.equals("Proponente")){
                DTOProponente dtoP = new DTOProponente();
                dtoP.setNick(nick);
                dtoP.setNombre(nombre);
                dtoP.setApellido(apellido);
                dtoP.setCorreo(correo);
                dtoP.setFechaNac(fechaNacimiento); // el formato es yyyy-mm-dd
                dtoP.setDirImagen(txtImagen.getText());
                dtoP.setDireccion(txtDir.getText());
                dtoP.setBiografia(txtBio.getText());
                dtoP.setLink(txtLink.getText());

                IProponenteController controllerPro = Fabrica.getInstancia().getProponenteController();
                controllerPro.altaProponente(dtoP);
                JOptionPane.showMessageDialog(mainPanel, "Proponente creado");
                limpiarCampos();
            }

        });

        btnCancelar.addActionListener(e ->{
            SwingUtilities.getWindowAncestor(mainPanel).dispose();
        });

    }

    private void limpiarCampos() {
        txtNickname.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtCorreo.setText("");
        ftxtFechaNacimiento.setText("");
        txtBio.setText("");
        txtDir.setText("");
        txtLink.setText("");
        txtImagen.setText("");

    }

    private void seleccionarImagen() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imágenes JPG y PNG", "jpg", "jpeg", "png"));

        //agregue esto para q guarde en la carpeta imagenes
        int resultado = chooser.showOpenDialog(mainPanel);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            java.io.File archivoOriginal = chooser.getSelectedFile();

            java.io.File carpetaDestino = new java.io.File("imagenes");
            if (!carpetaDestino.exists()) {
                carpetaDestino.mkdirs();
            }

            // Crear archivo destino
            java.io.File archivoDestino = new java.io.File(carpetaDestino, archivoOriginal.getName());

            try {
                java.nio.file.Files.copy(
                        archivoOriginal.toPath(),
                        archivoDestino.toPath(),
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING
                );

                // Guardar ruta en el campo imagen
                txtImagen.setText(archivoDestino.getPath());

            } catch (java.io.IOException e) {
                JOptionPane.showMessageDialog(mainPanel, "Error al copiar la imagen " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public JPanel getMainPanel() { return mainPanel; }

}
