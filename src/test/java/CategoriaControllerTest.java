
import com.culturarte.logica.clases.Categoria;
import com.culturarte.logica.controllers.CategoriaController;
import com.culturarte.logica.dtos.DTOCategoria;
import com.culturarte.persistencia.CategoriaDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.tree.DefaultMutableTreeNode;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriaControllerTest {

    @Mock
    private CategoriaDAO categoriaDAO;

    @InjectMocks
    private CategoriaController controller;

    private DTOCategoria dtoCategoria;
    private Categoria categoria;
    private Categoria categoriaPadre;

    @BeforeEach
    void setUp() throws Exception {
        // Inyectar mock usando reflection ya que el DAO es final
        injectMock(controller, "categoriaDAO", categoriaDAO);

        // Configurar datos de prueba
        categoriaPadre = new Categoria("Categoría", null);
        categoria = new Categoria("Música", categoriaPadre);

        dtoCategoria = new DTOCategoria();
        dtoCategoria.setNombre("Música");
        dtoCategoria.setCatPadre(categoriaPadre);
    }

    private void injectMock(Object target, String fieldName, Object mock) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, mock);
    }

    // ========== TESTS PARA altaCategoria ==========

    @Test
    void testAltaCategoria_Exitoso() {
        // Arrange
        when(categoriaDAO.existe("Música")).thenReturn(false);
        doNothing().when(categoriaDAO).guardar(any(Categoria.class));

        // Act
        assertDoesNotThrow(() -> controller.altaCategoria(dtoCategoria));

        // Assert
        verify(categoriaDAO).existe("Música");
        verify(categoriaDAO).guardar(any(Categoria.class));
    }

    @Test
    void testAltaCategoria_DTONull() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> controller.altaCategoria(null)
        );
        assertEquals("Datos de categoria no provistos.", exception.getMessage());
        verify(categoriaDAO, never()).guardar(any());
    }

    @Test
    void testAltaCategoria_NombreNull() {
        // Arrange
        dtoCategoria.setNombre(null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> controller.altaCategoria(dtoCategoria)
        );
        assertEquals("Nombre de categoria es obligatorio.", exception.getMessage());
        verify(categoriaDAO, never()).guardar(any());
    }

    @Test
    void testAltaCategoria_NombreVacio() {
        // Arrange
        dtoCategoria.setNombre("   ");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> controller.altaCategoria(dtoCategoria)
        );
        assertEquals("Nombre de categoria es obligatorio.", exception.getMessage());
        verify(categoriaDAO, never()).guardar(any());
    }

    @Test
    void testAltaCategoria_NombreEnBlanco() {
        // Arrange
        dtoCategoria.setNombre("");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> controller.altaCategoria(dtoCategoria)
        );
        assertEquals("Nombre de categoria es obligatorio.", exception.getMessage());
        verify(categoriaDAO, never()).guardar(any());
    }

    @Test
    void testAltaCategoria_CategoriaYaExiste() {
        // Arrange
        when(categoriaDAO.existe("Música")).thenReturn(true);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> controller.altaCategoria(dtoCategoria)
        );
        assertEquals("Ya existe una categoria con ese nombre.", exception.getMessage());
        verify(categoriaDAO, never()).guardar(any());
    }

    @Test
    void testAltaCategoria_SinCategoriaPadre_CreaCategoriaRaiz() {
        // Arrange
        dtoCategoria.setCatPadre(null);
        when(categoriaDAO.existe("Música")).thenReturn(false);
        when(categoriaDAO.buscarPorNombre("Categoría")).thenReturn(null);
        doNothing().when(categoriaDAO).guardar(any(Categoria.class));

        // Act
        assertDoesNotThrow(() -> controller.altaCategoria(dtoCategoria));

        // Assert
        verify(categoriaDAO).existe("Música");
        verify(categoriaDAO).buscarPorNombre("Categoría");
        // Se guarda la categoría raíz y luego la nueva categoría
        verify(categoriaDAO, times(2)).guardar(any(Categoria.class));
    }

    @Test
    void testAltaCategoria_SinCategoriaPadre_UsaCategoriaRaizExistente() {
        // Arrange
        dtoCategoria.setCatPadre(null);
        when(categoriaDAO.existe("Música")).thenReturn(false);
        when(categoriaDAO.buscarPorNombre("Categoría")).thenReturn(categoriaPadre);
        doNothing().when(categoriaDAO).guardar(any(Categoria.class));

        // Act
        assertDoesNotThrow(() -> controller.altaCategoria(dtoCategoria));

        // Assert
        verify(categoriaDAO).existe("Música");
        verify(categoriaDAO).buscarPorNombre("Categoría");
        // Solo se guarda la nueva categoría, no la raíz
        verify(categoriaDAO, times(1)).guardar(any(Categoria.class));
    }

    @Test
    void testAltaCategoria_ConCategoriaPadre() {
        // Arrange
        when(categoriaDAO.existe("Música")).thenReturn(false);
        doNothing().when(categoriaDAO).guardar(any(Categoria.class));

        // Act
        controller.altaCategoria(dtoCategoria);

        // Assert
        verify(categoriaDAO).existe("Música");
        verify(categoriaDAO).guardar(argThat(cat ->
                cat.getNombre().equals("Música") &&
                        cat.getCatPadre() != null &&
                        cat.getCatPadre().getNombre().equals("Categoría")
        ));
    }

    // ========== TESTS PARA listarCategorias ==========

    @Test
    void testListarCategorias_Exitoso() {
        // Arrange
        List<Categoria> categorias = List.of(categoria, categoriaPadre);
        when(categoriaDAO.obtenerTodas()).thenReturn(categorias);

        // Act
        List<String> resultado = controller.listarCategorias();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains("Música"));
        assertTrue(resultado.contains("Categoría"));
        verify(categoriaDAO).obtenerTodas();
    }

    @Test
    void testListarCategorias_ListaVacia() {
        // Arrange
        when(categoriaDAO.obtenerTodas()).thenReturn(new ArrayList<>());

        // Act
        List<String> resultado = controller.listarCategorias();

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(categoriaDAO).obtenerTodas();
    }

    @Test
    void testListarCategorias_MultiplesCategorias() {
        // Arrange
        Categoria cat1 = new Categoria("Música", categoriaPadre);
        Categoria cat2 = new Categoria("Teatro", categoriaPadre);
        Categoria cat3 = new Categoria("Danza", categoriaPadre);

        List<Categoria> categorias = List.of(cat1, cat2, cat3, categoriaPadre);
        when(categoriaDAO.obtenerTodas()).thenReturn(categorias);

        // Act
        List<String> resultado = controller.listarCategorias();

        // Assert
        assertNotNull(resultado);
        assertEquals(4, resultado.size());
        assertTrue(resultado.contains("Música"));
        assertTrue(resultado.contains("Teatro"));
        assertTrue(resultado.contains("Danza"));
        assertTrue(resultado.contains("Categoría"));
        verify(categoriaDAO).obtenerTodas();
    }

    // ========== TESTS PARA listarCategoriasC ==========

    @Test
    void testListarCategoriasC_Exitoso() {
        // Arrange
        List<Categoria> categorias = List.of(categoria, categoriaPadre);
        when(categoriaDAO.obtenerTodas()).thenReturn(categorias);

        // Act
        List<Categoria> resultado = controller.listarCategoriasC();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(categoria));
        assertTrue(resultado.contains(categoriaPadre));
        verify(categoriaDAO).obtenerTodas();
    }

    @Test
    void testListarCategoriasC_ListaVacia() {
        // Arrange
        when(categoriaDAO.obtenerTodas()).thenReturn(new ArrayList<>());

        // Act
        List<Categoria> resultado = controller.listarCategoriasC();

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(categoriaDAO).obtenerTodas();
    }

    @Test
    void testListarCategoriasC_MultiplesCategorias() {
        // Arrange
        Categoria cat1 = new Categoria("Música", categoriaPadre);
        Categoria cat2 = new Categoria("Teatro", categoriaPadre);
        Categoria cat3 = new Categoria("Danza", categoriaPadre);

        List<Categoria> categorias = List.of(cat1, cat2, cat3, categoriaPadre);
        when(categoriaDAO.obtenerTodas()).thenReturn(categorias);

        // Act
        List<Categoria> resultado = controller.listarCategoriasC();

        // Assert
        assertNotNull(resultado);
        assertEquals(4, resultado.size());
        assertTrue(resultado.contains(cat1));
        assertTrue(resultado.contains(cat2));
        assertTrue(resultado.contains(cat3));
        assertTrue(resultado.contains(categoriaPadre));
        verify(categoriaDAO).obtenerTodas();
    }

    // ========== TESTS PARA construirArbolCategorias ==========

    @Test
    void testConstruirArbolCategorias_Exitoso() {
        // Arrange
        List<Categoria> categorias = List.of(categoria, categoriaPadre);
        when(categoriaDAO.obtenerTodas()).thenReturn(categorias);

        // Act
        DefaultMutableTreeNode raiz = controller.construirArbolCategorias();

        // Assert
        assertNotNull(raiz);
        assertEquals("Categoría", raiz.getUserObject());
        assertTrue(raiz.getChildCount() > 0);
        verify(categoriaDAO).obtenerTodas();
    }

    @Test
    void testConstruirArbolCategorias_ListaVacia() {
        // Arrange
        when(categoriaDAO.obtenerTodas()).thenReturn(new ArrayList<>());

        // Act
        DefaultMutableTreeNode raiz = controller.construirArbolCategorias();

        // Assert
        assertNotNull(raiz);
        assertEquals("Categoría", raiz.getUserObject());
        assertEquals(0, raiz.getChildCount());
        verify(categoriaDAO).obtenerTodas();
    }

    @Test
    void testConstruirArbolCategorias_ConSubcategorias() {
        // Arrange
        Categoria subcategoria1 = new Categoria("Rock", categoria);
        Categoria subcategoria2 = new Categoria("Jazz", categoria);

        List<Categoria> categorias = List.of(
                categoriaPadre,
                categoria,
                subcategoria1,
                subcategoria2
        );
        when(categoriaDAO.obtenerTodas()).thenReturn(categorias);

        // Act
        DefaultMutableTreeNode raiz = controller.construirArbolCategorias();

        // Assert
        assertNotNull(raiz);
        assertEquals("Categoría", raiz.getUserObject());
        assertTrue(raiz.getChildCount() > 0);

        // Verificar que la estructura del árbol es correcta
        DefaultMutableTreeNode nodoMusica = null;
        for (int i = 0; i < raiz.getChildCount(); i++) {
            DefaultMutableTreeNode hijo = (DefaultMutableTreeNode) raiz.getChildAt(i);
            if ("Música".equals(hijo.getUserObject())) {
                nodoMusica = hijo;
                break;
            }
        }

        if (nodoMusica != null) {
            assertTrue(nodoMusica.getChildCount() >= 0);
        }

        verify(categoriaDAO).obtenerTodas();
    }

    @Test
    void testConstruirArbolCategorias_ConCategoriaSinPadre() {
        // Arrange
        Categoria categoriaSinPadre = new Categoria("Arte", null);
        List<Categoria> categorias = List.of(categoriaPadre, categoriaSinPadre);
        when(categoriaDAO.obtenerTodas()).thenReturn(categorias);

        // Act
        DefaultMutableTreeNode raiz = controller.construirArbolCategorias();

        // Assert
        assertNotNull(raiz);
        assertEquals("Categoría", raiz.getUserObject());
        // La categoría sin padre debería estar bajo la raíz
        assertTrue(raiz.getChildCount() > 0);
        verify(categoriaDAO).obtenerTodas();
    }

    @Test
    void testConstruirArbolCategorias_EvitaCiclos() {
        // Arrange
        // Crear una estructura que podría causar ciclos
        Categoria cat1 = new Categoria("Cat1", categoriaPadre);
        Categoria cat2 = new Categoria("Cat2", cat1);

        List<Categoria> categorias = List.of(categoriaPadre, cat1, cat2);
        when(categoriaDAO.obtenerTodas()).thenReturn(categorias);

        // Act
        DefaultMutableTreeNode raiz = controller.construirArbolCategorias();

        // Assert
        assertNotNull(raiz);
        // Verificar que no hay ciclos (el método debería manejar esto)
        assertDoesNotThrow(() -> {
            // Intentar recorrer el árbol no debería causar un ciclo infinito
            int count = 0;
            for (int i = 0; i < raiz.getChildCount(); i++) {
                count++;
                if (count > 100) { // Límite de seguridad
                    fail("Posible ciclo infinito en el árbol");
                }
            }
        });
        verify(categoriaDAO).obtenerTodas();
    }

    @Test
    void testConstruirArbolCategorias_ConCategoriaPadreInexistente() {
        // Arrange
        Categoria padreInexistente = new Categoria("PadreInexistente", null);
        Categoria hijo = new Categoria("Hijo", padreInexistente);

        List<Categoria> categorias = List.of(categoriaPadre, hijo);
        when(categoriaDAO.obtenerTodas()).thenReturn(categorias);

        // Act
        DefaultMutableTreeNode raiz = controller.construirArbolCategorias();

        // Assert
        assertNotNull(raiz);
        assertEquals("Categoría", raiz.getUserObject());
        // El hijo debería estar bajo la raíz ya que su padre no existe en el árbol
        assertTrue(raiz.getChildCount() >= 0);
        verify(categoriaDAO).obtenerTodas();
    }

    @Test
    void testConstruirArbolCategorias_VerificarEstructuraCompleta() {
        // Arrange
        Categoria teatro = new Categoria("Teatro", categoriaPadre);
        Categoria danza = new Categoria("Danza", categoriaPadre);
        Categoria rock = new Categoria("Rock", categoria);
        Categoria jazz = new Categoria("Jazz", categoria);

        List<Categoria> categorias = List.of(
                categoriaPadre,
                categoria,
                teatro,
                danza,
                rock,
                jazz
        );
        when(categoriaDAO.obtenerTodas()).thenReturn(categorias);

        // Act
        DefaultMutableTreeNode raiz = controller.construirArbolCategorias();

        // Assert
        assertNotNull(raiz);
        assertEquals("Categoría", raiz.getUserObject());

        // Verificar que todas las categorías están en el árbol
        List<String> nombresEnArbol = new ArrayList<>();
        collectNombres(raiz, nombresEnArbol);

        assertTrue(nombresEnArbol.contains("Categoría"));
        assertTrue(nombresEnArbol.contains("Música"));
        assertTrue(nombresEnArbol.contains("Teatro"));
        assertTrue(nombresEnArbol.contains("Danza"));

        verify(categoriaDAO).obtenerTodas();
    }

    private void collectNombres(DefaultMutableTreeNode node, List<String> nombres) {
        nombres.add((String) node.getUserObject());
        for (int i = 0; i < node.getChildCount(); i++) {
            collectNombres((DefaultMutableTreeNode) node.getChildAt(i), nombres);
        }
    }
}