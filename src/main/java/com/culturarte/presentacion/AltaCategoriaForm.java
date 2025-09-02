package com.culturarte.presentacion;

import com.culturarte.logica.clases.Categoria;
import com.culturarte.logica.controllers.ICategoriaController;
import com.culturarte.logica.controllers.IProponenteController;
import com.culturarte.logica.dtos.DTOCategoria;
import com.culturarte.logica.enums.ETipoRetorno;
import com.culturarte.logica.fabrica.Fabrica;

import javax.swing.*;
import java.util.List;

public class AltaCategoriaForm {

    private JPanel mainPanel;
    private JTextField txtNombre;
    private JTextField textField2;
    private JLabel lblNomCat;
    private JLabel lblCatPadre;
    private JButton btnCancelar;
    private JButton btnAceptar;
    private JLabel arrozconleche;
    private JLabel luis;
    private JComboBox<Categoria> cbxCategorias;

    private final ICategoriaController controllerCat = Fabrica.getInstancia().getCategoriaController();

    public AltaCategoriaForm(){

        List<Categoria> categorias = controllerCat.listarCategoriasC();
        for (Categoria cat : categorias) cbxCategorias.addItem(cat);

        btnAceptar.addActionListener(e ->{
            String nomCat = txtNombre.getText();

            if (nomCat.isEmpty()){
                JOptionPane.showMessageDialog(mainPanel,
                        "Por favor ingrese un nombre de categoria",
                        "Faltan datos",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Categoria catPadreC = (Categoria) cbxCategorias.getSelectedItem();
            String catPadre;
            if (catPadreC.getCatPadre() == null) {
                catPadre = "taque";
            }else{
                catPadre = catPadreC.getCatPadre().getNombre();
            }

            DTOCategoria dtoCat = new DTOCategoria();

            if (catPadre.isEmpty() || catPadre.equals("Categoria")){
                Categoria cat = new Categoria();
                cat.setNombre("Categoria");
                cat.setCatPadre(null);

                dtoCat.setNombre(nomCat);
                dtoCat.setCatPadre(cat);
            }else{
                dtoCat.setNombre(nomCat);
                dtoCat.setCatPadre((Categoria) cbxCategorias.getSelectedItem());
            }

            controllerCat.altaCategoria(dtoCat);
            JOptionPane.showMessageDialog(mainPanel, "Categoria creada");

        });

        btnCancelar.addActionListener(e ->{
            SwingUtilities.getWindowAncestor(mainPanel).dispose();
        });


    }
    public JPanel getMainPanel(){
        return mainPanel;
    }

}
