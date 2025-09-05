package com.culturarte.presentacion;

import com.culturarte.logica.clases.Categoria;
import com.culturarte.logica.controllers.ICategoriaController;
import com.culturarte.logica.dtos.DTOCategoria;
import com.culturarte.logica.fabrica.Fabrica;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

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
    private JTree arbolCats;
    private JScrollPane scrollCats;

    private final ICategoriaController controllerCat = Fabrica.getInstancia().getCategoriaController();

    public AltaCategoriaForm(){
     /*
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

*/
            // Cargar árbol de cats
            DefaultMutableTreeNode raiz = Fabrica.getInstancia()
                    .getCategoriaController()
                    .construirArbolCategorias();

            arbolCats.setModel(new javax.swing.tree.DefaultTreeModel(raiz)); //carga el arbol con la raiz categoria
            scrollCats.setPreferredSize(new Dimension(200, 150));


            btnAceptar.addActionListener(e -> {
                String nombre = txtNombre.getText();

                if (nombre.isEmpty()) {
                    JOptionPane.showMessageDialog(mainPanel, "Debe ingresar un nombre.", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Obtener nodo seleccionado
                DefaultMutableTreeNode nodoSeleccionado = (DefaultMutableTreeNode) arbolCats.getLastSelectedPathComponent();
                String padreNombre = (nodoSeleccionado != null) ? nodoSeleccionado.getUserObject().toString() : "Categoría";

                DTOCategoria dto = new DTOCategoria();
                dto.setNombre(nombre);

                // Busca categoría padre en bd (o null si es la raíz)
                Categoria padre = Fabrica.getInstancia().getCategoriaController()
                        .listarCategoriasC()
                        .stream()
                        .filter(c -> c.getNombre().equals(padreNombre))
                        .findFirst()
                        .orElse(null);

                dto.setCatPadre(padre);

                try {
                    controllerCat.altaCategoria(dto);
                    JOptionPane.showMessageDialog(mainPanel, "Categoría creada exitosamente.");
                    txtNombre.setText("");
                    arbolCats.clearSelection();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(mainPanel, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

        btnCancelar.addActionListener(e -> {
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

    }
    public JPanel getMainPanel(){
        return mainPanel;
    }

}
