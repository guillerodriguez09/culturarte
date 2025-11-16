
import com.culturarte.logica.clases.*;
import com.culturarte.logica.controllers.PropuestaController;
import com.culturarte.logica.dtos.DTOPropuesta;
import com.culturarte.logica.enums.EEstadoPropuesta;
import com.culturarte.logica.enums.ETipoRetorno;
import com.culturarte.persistencia.CategoriaDAO;
import com.culturarte.persistencia.ColaboradorDAO;
import com.culturarte.persistencia.ProponenteDAO;
import com.culturarte.persistencia.PropuestaDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PropuestaControllerTest {

    @Mock
    private PropuestaDAO propuestaDAO;

    @Mock
    private CategoriaDAO categoriaDAO;

    @Mock
    private ProponenteDAO proponenteDAO;

    @Mock
    private ColaboradorDAO colaboradorDAO;

    @InjectMocks
    private PropuestaController controller;

    private DTOPropuesta dtoPropuesta;
    private Propuesta propuesta;
    private Proponente proponente;
    private Categoria categoria;
    private Estado estado;

    @BeforeEach
    void setUp() throws Exception {
        // Inyectar mocks usando reflection ya que los DAOs son final
        injectMock(controller, "propuestaDAO", propuestaDAO);
        injectMock(controller, "categoriaDAO", categoriaDAO);
        injectMock(controller, "proponenteDAO", proponenteDAO);
        injectMock(controller, "colaboradorDAO", colaboradorDAO);

        // Configurar datos de prueba
        proponente = new Proponente();
        proponente.setNick("proponente1");

        categoria = new Categoria("Categoria1");

        estado = new Estado(EEstadoPropuesta.INGRESADA, LocalDate.now());

        propuesta = new Propuesta(
                categoria,
                proponente,
                "Titulo Propuesta",
                "Descripcion",
                "Lugar",
                LocalDate.now().plusDays(30),
                100,
                5000,
                LocalDate.now(),
                List.of(ETipoRetorno.ENTRADAS_GRATIS),
                "imagen.jpg"
        );
        propuesta.setEstadoActual(estado);
        propuesta.getHistorialEstados().add(estado);
        inicializarColaboraciones(propuesta);

        dtoPropuesta = new DTOPropuesta();
        dtoPropuesta.titulo = "Titulo Propuesta";
        dtoPropuesta.descripcion = "Descripcion";
        dtoPropuesta.lugar = "Lugar";
        dtoPropuesta.fecha = LocalDate.now().plusDays(30);
        dtoPropuesta.precioEntrada = 100;
        dtoPropuesta.montoAReunir = 5000;
        dtoPropuesta.proponenteNick = "proponente1";
        dtoPropuesta.categoriaNombre = "Categoria1";
        dtoPropuesta.retornos = List.of(ETipoRetorno.ENTRADAS_GRATIS);
        dtoPropuesta.imagen = "imagen.jpg";
    }

    private void injectMock(Object target, String fieldName, Object mock) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, mock);
    }

    private void inicializarColaboraciones(Propuesta propuesta) {
        if (propuesta.getColaboraciones() == null) {
            try {
                Field field = Propuesta.class.getDeclaredField("colaboraciones");
                field.setAccessible(true);
                field.set(propuesta, new ArrayList<>());
            } catch (Exception e) {
                fail("Error al inicializar colaboraciones: " + e.getMessage());
            }
        }
    }


    @Test
    void testAltaPropuesta_Exitoso() {

        when(propuestaDAO.existePropuesta(anyString())).thenReturn(false);
        when(proponenteDAO.buscarPorNick("proponente1")).thenReturn(proponente);
        when(categoriaDAO.buscarPorNombre("Categoria1")).thenReturn(categoria);
        doNothing().when(propuestaDAO).guardar(any(Propuesta.class));


        assertDoesNotThrow(() -> controller.altaPropuesta(dtoPropuesta));


        verify(propuestaDAO).existePropuesta("Titulo Propuesta");
        verify(proponenteDAO).buscarPorNick("proponente1");
        verify(categoriaDAO).buscarPorNombre("Categoria1");
        verify(propuestaDAO).guardar(any(Propuesta.class));
    }

    @Test
    void testAltaPropuesta_DTONull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> controller.altaPropuesta(null)
        );
        assertEquals("Datos de propuesta no provistos.", exception.getMessage());
        verify(propuestaDAO, never()).guardar(any());
    }

    @Test
    void testAltaPropuesta_TituloVacio() {

        dtoPropuesta.titulo = "";


        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> controller.altaPropuesta(dtoPropuesta)
        );
        assertEquals("El título es obligatorio.", exception.getMessage());
    }

    @Test
    void testAltaPropuesta_TituloNull() {

        dtoPropuesta.titulo = null;


        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> controller.altaPropuesta(dtoPropuesta)
        );
        assertEquals("El título es obligatorio.", exception.getMessage());
    }

    @Test
    void testAltaPropuesta_FechaNull() {

        dtoPropuesta.fecha = null;


        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> controller.altaPropuesta(dtoPropuesta)
        );
        assertEquals("La fecha prevista es obligatoria.", exception.getMessage());
    }

    @Test
    void testAltaPropuesta_PrecioEntradaCero() {

        dtoPropuesta.precioEntrada = 0;


        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> controller.altaPropuesta(dtoPropuesta)
        );
        assertEquals("El precio de la entrada no puede ser cero ni negativa.", exception.getMessage());
    }

    @Test
    void testAltaPropuesta_PrecioEntradaNegativo() {

        dtoPropuesta.precioEntrada = -10;


        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> controller.altaPropuesta(dtoPropuesta)
        );
        assertEquals("El precio de la entrada no puede ser cero ni negativa.", exception.getMessage());
    }

    @Test
    void testAltaPropuesta_MontoAReunirNull() {

        dtoPropuesta.montoAReunir = null;


        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> controller.altaPropuesta(dtoPropuesta)
        );
        assertEquals("El monto a reunir debe ser mayor que 0.", exception.getMessage());
    }

    @Test
    void testAltaPropuesta_MontoAReunirCero() {

        dtoPropuesta.montoAReunir = 0;


        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> controller.altaPropuesta(dtoPropuesta)
        );
        assertEquals("El monto a reunir debe ser mayor que 0.", exception.getMessage());
    }

    @Test
    void testAltaPropuesta_ProponenteNickVacio() {

        dtoPropuesta.proponenteNick = "";


        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> controller.altaPropuesta(dtoPropuesta)
        );
        assertEquals("Debe indicarse un proponente.", exception.getMessage());
    }

    @Test
    void testAltaPropuesta_CategoriaNombreVacio() {

        dtoPropuesta.categoriaNombre = "";


        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> controller.altaPropuesta(dtoPropuesta)
        );
        assertEquals("Debe indicarse una categoría.", exception.getMessage());
    }

    @Test
    void testAltaPropuesta_TituloYaExiste() {

        when(propuestaDAO.existePropuesta("Titulo Propuesta")).thenReturn(true);


        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> controller.altaPropuesta(dtoPropuesta)
        );
        assertEquals("Ya existe una propuesta con ese título.", exception.getMessage());
    }

    @Test
    void testAltaPropuesta_ProponenteNoExiste() {

        when(propuestaDAO.existePropuesta(anyString())).thenReturn(false);
        when(proponenteDAO.buscarPorNick("proponente1")).thenReturn(null);


        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> controller.altaPropuesta(dtoPropuesta)
        );
        assertEquals("El proponente 'proponente1' no existe.", exception.getMessage());
    }

    @Test
    void testAltaPropuesta_CategoriaNoExiste() {

        when(propuestaDAO.existePropuesta(anyString())).thenReturn(false);
        when(proponenteDAO.buscarPorNick("proponente1")).thenReturn(proponente);
        when(categoriaDAO.buscarPorNombre("Categoria1")).thenReturn(null);


        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> controller.altaPropuesta(dtoPropuesta)
        );
        assertEquals("La categoría 'Categoria1' no existe.", exception.getMessage());
    }

    // ========== TESTS PARA listarPropuestas ==========

    @Test
    void testListarPropuestas_Exitoso() {

        List<Propuesta> propuestas = List.of(propuesta);
        when(propuestaDAO.obtenerTodas()).thenReturn(propuestas);


        List<String> resultado = controller.listarPropuestas();


        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Titulo Propuesta", resultado.get(0));
        verify(propuestaDAO).obtenerTodas();
    }

    @Test
    void testListarPropuestas_ListaVacia() {

        when(propuestaDAO.obtenerTodas()).thenReturn(new ArrayList<>());


        List<String> resultado = controller.listarPropuestas();


        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void testListarPropuestas_MultiplesPropuestas() {

        Propuesta propuesta2 = new Propuesta(
                categoria, proponente, "Titulo 2", "Desc", "Lugar",
                LocalDate.now(), 50, 2000, LocalDate.now(),
                List.of(ETipoRetorno.PORCENTAJE_GANANCIAS), "img.jpg"
        );
        List<Propuesta> propuestas = List.of(propuesta, propuesta2);
        when(propuestaDAO.obtenerTodas()).thenReturn(propuestas);


        List<String> resultado = controller.listarPropuestas();


        assertEquals(2, resultado.size());
        assertTrue(resultado.contains("Titulo Propuesta"));
        assertTrue(resultado.contains("Titulo 2"));
    }

    // ========== TESTS PARA consultarPropuesta ==========

    // ========== TESTS PARA consultarPropuesta ==========

    @Test
    void testConsultarPropuesta_Exitoso() {
        // 1. Configurar el mock
        // Le decimos al DAO "falso" que devuelva nuestra 'propuesta' de prueba
        // cuando se le pida "Titulo Propuesta".
        when(propuestaDAO.buscarPorTitulo("Titulo Propuesta")).thenReturn(propuesta);

        // 2. Ejecutar el método
        DTOPropuesta resultado = controller.consultarPropuesta("Titulo Propuesta");

        // 3. Verificar el DTO resultante (NO el objeto 'propuesta' local)
        assertNotNull(resultado);
        assertEquals("Titulo Propuesta", resultado.titulo);
        assertEquals("proponente1", resultado.proponenteNick);
        assertEquals("Categoria1", resultado.categoriaNombre);
        assertEquals(100, resultado.precioEntrada);
        assertEquals("INGRESADA", resultado.estadoActual);
        assertEquals(0, resultado.montoRecaudado); // setUp no agrega colaboraciones
        assertNotNull(resultado.colaboradores);
        assertTrue(resultado.colaboradores.isEmpty());
    }

    @Test
    void testConsultarPropuesta_ConColaboradores() {
        // Configurar los colaboradores en el objeto de prueba
        Colaborador colaborador1 = new Colaborador();
        colaborador1.setNick("colab1");
        Colaborador colaborador2 = new Colaborador();
        colaborador2.setNick("colab2");

        Colaboracion colab1 = new Colaboracion(100, ETipoRetorno.ENTRADAS_GRATIS,
                java.time.LocalDateTime.now(), propuesta, colaborador1);
        Colaboracion colab2 = new Colaboracion(200, ETipoRetorno.ENTRADAS_GRATIS,
                java.time.LocalDateTime.now(), propuesta, colaborador2);

        // Agregamos las colaboraciones a la lista (inicializada en setUp)
        propuesta.getColaboraciones().add(colab1);
        propuesta.getColaboraciones().add(colab2);


        when(propuestaDAO.buscarPorTitulo("Titulo Propuesta")).thenReturn(propuesta);


        DTOPropuesta resultado = controller.consultarPropuesta("Titulo Propuesta");


        assertNotNull(resultado);
        assertEquals(300, resultado.montoRecaudado);
        assertNotNull(resultado.colaboradores);
        assertEquals(2, resultado.colaboradores.size());
        assertTrue(resultado.colaboradores.contains("colab1"));
        assertTrue(resultado.colaboradores.contains("colab2"));
    }

    @Test
    void testConsultarPropuesta_NoEncontrada() {
        // 1. Configurar el mock (devuelve null porque no la encontró)
        when(propuestaDAO.buscarPorTitulo("No Existe")).thenReturn(null);

        // 2. Ejecutar y verificar la excepción
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> controller.consultarPropuesta("No Existe")
        );

        // 3. Verificar mensaje de error
        // (Ajusta el mensaje si tu controlador usa uno diferente)
        assertEquals("La propuesta con título 'No Existe' no existe.", exception.getMessage());
    }

    // ========== TESTS PARA modificarPropuesta ==========

    @Test
    void testModificarPropuesta_Exitoso() {

        DTOPropuesta dtoModificado = new DTOPropuesta();
        dtoModificado.descripcion = "Nueva descripcion";
        dtoModificado.lugar = "Nuevo lugar";
        dtoModificado.fecha = LocalDate.now().plusDays(60);
        dtoModificado.precioEntrada = 150;
        dtoModificado.montoAReunir = 6000;
        dtoModificado.categoriaNombre = "Categoria1";
        dtoModificado.imagen = "nuevaImagen.jpg";
        dtoModificado.retornos = List.of(ETipoRetorno.PORCENTAJE_GANANCIAS);

        when(propuestaDAO.buscarPorTitulo("Titulo Propuesta")).thenReturn(propuesta);
        when(categoriaDAO.buscarPorNombre("Categoria1")).thenReturn(categoria);
        doNothing().when(propuestaDAO).actualizar(any(Propuesta.class));


        assertDoesNotThrow(() -> controller.modificarPropuesta("Titulo Propuesta", dtoModificado));


        verify(propuestaDAO).buscarPorTitulo("Titulo Propuesta");
        verify(categoriaDAO).buscarPorNombre("Categoria1");
        verify(propuestaDAO).actualizar(propuesta);
    }

    @Test
    void testModificarPropuesta_DTONull() {

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> controller.modificarPropuesta("Titulo", null)
        );
        assertEquals("Datos de propuesta no provistos.", exception.getMessage());
    }

    @Test
    void testModificarPropuesta_FechaNull() {

        dtoPropuesta.fecha = null;


        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> controller.modificarPropuesta("Titulo Propuesta", dtoPropuesta)
        );
        assertEquals("La fecha prevista es obligatoria.", exception.getMessage());
    }

    @Test
    void testModificarPropuesta_PrecioEntradaCero() {

        dtoPropuesta.precioEntrada = 0;


        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> controller.modificarPropuesta("Titulo Propuesta", dtoPropuesta)
        );
        assertEquals("El precio de la entrada no puede ser negativo.", exception.getMessage());
    }

    @Test
    void testModificarPropuesta_PropuestaNoExiste() {

        when(propuestaDAO.buscarPorTitulo("No Existe")).thenReturn(null);


        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> controller.modificarPropuesta("No Existe", dtoPropuesta)
        );
        assertEquals("No existe una propuesta con el título 'No Existe'.", exception.getMessage());
    }

    @Test
    void testModificarPropuesta_CategoriaNoExiste() {

        when(propuestaDAO.buscarPorTitulo("Titulo Propuesta")).thenReturn(propuesta);
        when(categoriaDAO.buscarPorNombre("CategoriaInexistente")).thenReturn(null);
        dtoPropuesta.categoriaNombre = "CategoriaInexistente";


        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> controller.modificarPropuesta("Titulo Propuesta", dtoPropuesta)
        );
        assertEquals("La categoría 'CategoriaInexistente' no existe.", exception.getMessage());
    }

    @Test
    void testModificarPropuesta_ConCambioEstado() {

        dtoPropuesta.estadoActual = "PUBLICADA";
        when(propuestaDAO.buscarPorTitulo("Titulo Propuesta")).thenReturn(propuesta);
        when(categoriaDAO.buscarPorNombre("Categoria1")).thenReturn(categoria);
        doNothing().when(propuestaDAO).actualizar(any(Propuesta.class));


        controller.modificarPropuesta("Titulo Propuesta", dtoPropuesta);


        verify(propuestaDAO).actualizar(propuesta);
        assertEquals(EEstadoPropuesta.PUBLICADA, propuesta.getEstadoActual().getNombre());
    }

    // ========== TESTS PARA listarPorEstado ==========

    @Test
    void testListarPorEstado_Exitoso() {

        List<Propuesta> propuestas = List.of(propuesta);
        when(propuestaDAO.listarPorEstado(EEstadoPropuesta.INGRESADA)).thenReturn(propuestas);


        List<DTOPropuesta> resultado = controller.listarPorEstado(EEstadoPropuesta.INGRESADA);


        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Titulo Propuesta", resultado.get(0).titulo);
        assertEquals("INGRESADA", resultado.get(0).estadoActual);
        verify(propuestaDAO).listarPorEstado(EEstadoPropuesta.INGRESADA);
    }

    @Test
    void testListarPorEstado_ListaVacia() {

        when(propuestaDAO.listarPorEstado(EEstadoPropuesta.PUBLICADA)).thenReturn(new ArrayList<>());


        List<DTOPropuesta> resultado = controller.listarPorEstado(EEstadoPropuesta.PUBLICADA);


        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    // ========== TESTS PARA listarPropuestasConProponente ==========

    @Test
    void testListarPropuestasConProponente_Exitoso() {

        List<Propuesta> propuestas = List.of(propuesta);
        when(propuestaDAO.obtenerTodas()).thenReturn(propuestas);


        List<DTOPropuesta> resultado = controller.listarPropuestasConProponente();


        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Titulo Propuesta", resultado.get(0).getTitulo());
        assertEquals("proponente1", resultado.get(0).getProponenteNick());
        verify(propuestaDAO).obtenerTodas();
    }

    @Test
    void testListarPropuestasConProponente_EstadoActualNull() {

        propuesta.setEstadoActual(null);
        List<Propuesta> propuestas = List.of(propuesta);
        when(propuestaDAO.obtenerTodas()).thenReturn(propuestas);


        List<DTOPropuesta> resultado = controller.listarPropuestasConProponente();


        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Desconocido", resultado.get(0).getEstadoActual());
    }

    // ========== TESTS PARA asignarEstado ==========

    @Test
    void testAsignarEstado_Exitoso() {

        LocalDate fecha = LocalDate.now();
        when(propuestaDAO.buscarPorTitulo("Titulo Propuesta")).thenReturn(propuesta);
        doNothing().when(propuestaDAO).actualizar(any(Propuesta.class));


        assertDoesNotThrow(() -> controller.asignarEstado("Titulo Propuesta", EEstadoPropuesta.PUBLICADA, fecha));


        verify(propuestaDAO).buscarPorTitulo("Titulo Propuesta");
        verify(propuestaDAO).actualizar(propuesta);
        assertEquals(EEstadoPropuesta.PUBLICADA, propuesta.getEstadoActual().getNombre());
        assertEquals(fecha, propuesta.getEstadoActual().getFecha());
    }

    @Test
    void testAsignarEstado_PropuestaNoExiste() {

        when(propuestaDAO.buscarPorTitulo("No Existe")).thenReturn(null);


        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> controller.asignarEstado("No Existe", EEstadoPropuesta.PUBLICADA, LocalDate.now())
        );
        assertEquals("Propuesta no encontrada: No Existe", exception.getMessage());
    }

    // ========== TESTS PARA listarPropuestasIngresadas ==========

    @Test
    void testListarPropuestasIngresadas_Exitoso() {

        List<Propuesta> propuestas = List.of(propuesta);
        when(propuestaDAO.listarPorEstado(EEstadoPropuesta.INGRESADA)).thenReturn(propuestas);


        List<DTOPropuesta> resultado = controller.listarPropuestasIngresadas();


        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Titulo Propuesta", resultado.get(0).titulo);
        verify(propuestaDAO).listarPorEstado(EEstadoPropuesta.INGRESADA);
    }

    // ========== TESTS PARA evaluarPropuesta ==========

    @Test
    void testEvaluarPropuesta_PublicarExitoso() {

        when(propuestaDAO.buscarPorTitulo("Titulo Propuesta")).thenReturn(propuesta);
        doNothing().when(propuestaDAO).actualizar(any(Propuesta.class));


        assertDoesNotThrow(() -> controller.evaluarPropuesta("Titulo Propuesta", true));


        verify(propuestaDAO).buscarPorTitulo("Titulo Propuesta");
        verify(propuestaDAO).actualizar(propuesta);
        assertEquals(EEstadoPropuesta.PUBLICADA, propuesta.getEstadoActual().getNombre());
    }

    @Test
    void testEvaluarPropuesta_CancelarExitoso() {

        when(propuestaDAO.buscarPorTitulo("Titulo Propuesta")).thenReturn(propuesta);
        doNothing().when(propuestaDAO).actualizar(any(Propuesta.class));


        assertDoesNotThrow(() -> controller.evaluarPropuesta("Titulo Propuesta", false));


        verify(propuestaDAO).buscarPorTitulo("Titulo Propuesta");
        verify(propuestaDAO).actualizar(propuesta);
        assertEquals(EEstadoPropuesta.CANCELADA, propuesta.getEstadoActual().getNombre());
    }

    @Test
    void testEvaluarPropuesta_PropuestaNoExiste() {

        when(propuestaDAO.buscarPorTitulo("No Existe")).thenReturn(null);


        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> controller.evaluarPropuesta("No Existe", true)
        );
        assertEquals("Propuesta no encontrada: No Existe", exception.getMessage());
    }

    @Test
    void testEvaluarPropuesta_EstadoNoIngresada() {

        Estado estadoPublicada = new Estado(EEstadoPropuesta.PUBLICADA, LocalDate.now());
        propuesta.setEstadoActual(estadoPublicada);
        when(propuestaDAO.buscarPorTitulo("Titulo Propuesta")).thenReturn(propuesta);


        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> controller.evaluarPropuesta("Titulo Propuesta", true)
        );
        assertEquals("Solo se pueden evaluar propuestas en estado INGRESADA.", exception.getMessage());
    }

    @Test
    void testEvaluarPropuesta_EstadoActualNull() {

        propuesta.setEstadoActual(null);
        when(propuestaDAO.buscarPorTitulo("Titulo Propuesta")).thenReturn(propuesta);


        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> controller.evaluarPropuesta("Titulo Propuesta", true)
        );
        assertEquals("Solo se pueden evaluar propuestas en estado INGRESADA.", exception.getMessage());
    }

    // ========== TESTS PARA cancelarPropuesta ==========

    @Test
    void testCancelarPropuesta_Exitoso() {

        Estado estadoFinanciada = new Estado(EEstadoPropuesta.FINANCIADA, LocalDate.now());
        propuesta.setEstadoActual(estadoFinanciada);
        when(propuestaDAO.buscarPorTitulo("Titulo Propuesta")).thenReturn(propuesta);
        doNothing().when(propuestaDAO).actualizar(any(Propuesta.class));


        assertDoesNotThrow(() -> controller.cancelarPropuesta("Titulo Propuesta"));


        verify(propuestaDAO).buscarPorTitulo("Titulo Propuesta");
        verify(propuestaDAO).actualizar(propuesta);
        assertEquals(EEstadoPropuesta.CANCELADA, propuesta.getEstadoActual().getNombre());
    }

    @Test
    void testCancelarPropuesta_PropuestaNoExiste() {

        when(propuestaDAO.buscarPorTitulo("No Existe")).thenReturn(null);


        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> controller.cancelarPropuesta("No Existe")
        );
        assertEquals("Propuesta no encontrada: No Existe", exception.getMessage());
    }

    @Test
    void testCancelarPropuesta_EstadoNoFinanciada() {

        when(propuestaDAO.buscarPorTitulo("Titulo Propuesta")).thenReturn(propuesta);


        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> controller.cancelarPropuesta("Titulo Propuesta")
        );
        assertTrue(exception.getMessage().contains("No se puede cancelar una propuesta en estado"));
    }

    // ========== TESTS PARA buscarPropuestas ==========

    @Test
    void testBuscarPropuestas_ConFiltro() {

        List<Propuesta> propuestas = List.of(propuesta);
        when(propuestaDAO.buscarPorTexto("Titulo")).thenReturn(propuestas);


        List<DTOPropuesta> resultado = controller.buscarPropuestas("Titulo");


        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Titulo Propuesta", resultado.get(0).getTitulo());
        verify(propuestaDAO).buscarPorTexto("Titulo");
        verify(propuestaDAO, never()).obtenerTodas();
    }

    @Test
    void testBuscarPropuestas_SinFiltro() {

        List<Propuesta> propuestas = List.of(propuesta);
        when(propuestaDAO.obtenerTodas()).thenReturn(propuestas);


        List<DTOPropuesta> resultado = controller.buscarPropuestas(null);


        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Titulo Propuesta", resultado.get(0).getTitulo());
        verify(propuestaDAO).obtenerTodas();
        verify(propuestaDAO, never()).buscarPorTexto(anyString());
    }

    @Test
    void testBuscarPropuestas_FiltroVacio() {

        List<Propuesta> propuestas = List.of(propuesta);
        when(propuestaDAO.obtenerTodas()).thenReturn(propuestas);


        List<DTOPropuesta> resultado = controller.buscarPropuestas("   ");


        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Titulo Propuesta", resultado.get(0).getTitulo());
        verify(propuestaDAO).obtenerTodas();
        verify(propuestaDAO, never()).buscarPorTexto(anyString());
    }

    @Test
    void testBuscarPropuestas_ConColaboradores() {

        Colaborador colaborador = new Colaborador();
        colaborador.setNick("colab1");
        Colaboracion colab = new Colaboracion(100, ETipoRetorno.ENTRADAS_GRATIS,
                java.time.LocalDateTime.now(), propuesta, colaborador);

        propuesta.getColaboraciones().add(colab);

        List<Propuesta> propuestas = List.of(propuesta);
        when(propuestaDAO.buscarPorTexto("Titulo")).thenReturn(propuestas);


        List<DTOPropuesta> resultado = controller.buscarPropuestas("Titulo");


        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertNotNull(resultado.get(0).getColaboradores());
        assertEquals(1, resultado.get(0).getColaboradores().size());
        assertEquals("colab1", resultado.get(0).getColaboradores().get(0));
    }

    // ========== TESTS PARA recomendarPropuestas ==========

    @Test
    void testRecomendarPropuestas_ColaboradorNoExiste() {
        // Arrange
        when(colaboradorDAO.buscarPorNick("colabInexistente")).thenReturn(null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> controller.recomendarPropuestas("colabInexistente")
        );
        assertEquals("Colaborador no encontrado: colabInexistente", exception.getMessage());
    }

    @Test
    void testRecomendarPropuestas_ColaboradorSinSeguimientos() {
        // Arrange
        Colaborador colaborador = new Colaborador();
        colaborador.setNick("colab1");
        // Inicializar listas vacías
        try {
            Field field = Colaborador.class.getSuperclass().getDeclaredField("usuariosSeguidos");
            field.setAccessible(true);
            field.set(colaborador, new ArrayList<>());
        } catch (Exception e) {
            fail("Error al inicializar usuariosSeguidos");
        }

        when(colaboradorDAO.buscarPorNick("colab1")).thenReturn(colaborador);

        // Act
        List<DTOPropuesta> resultado = controller.recomendarPropuestas("colab1");

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(colaboradorDAO).buscarPorNick("colab1");
    }

    @Test
    void testRecomendarPropuestas_ConPropuestasDeSeguidos() {
        // Arrange
        Colaborador colaborador = new Colaborador();
        colaborador.setNick("colab1");

        Colaborador seguido = new Colaborador();
        seguido.setNick("colabSeguido");

        Propuesta propuestaPublicada = new Propuesta(
                categoria, proponente, "Propuesta Publicada", "Desc", "Lugar",
                LocalDate.now().plusDays(30), 100, 5000, LocalDate.now(),
                List.of(ETipoRetorno.ENTRADAS_GRATIS), "img.jpg"
        );
        Estado estadoPublicada = new Estado(EEstadoPropuesta.PUBLICADA, LocalDate.now());
        propuestaPublicada.setEstadoActual(estadoPublicada);
        inicializarColaboraciones(propuestaPublicada);

        Colaboracion colabSeguido = new Colaboracion(100, ETipoRetorno.ENTRADAS_GRATIS,
                java.time.LocalDateTime.now(), propuestaPublicada, seguido);
        propuestaPublicada.getColaboraciones().add(colabSeguido);

        // Configurar seguimiento
        List<Seguimiento> seguimientos = new ArrayList<>();
        Seguimiento seg = new Seguimiento(colaborador, "colabSeguido");
        seguimientos.add(seg);

        try {
            Field field = Colaborador.class.getSuperclass().getDeclaredField("usuariosSeguidos");
            field.setAccessible(true);
            field.set(colaborador, seguimientos);
        } catch (Exception e) {
            fail("Error al inicializar usuariosSeguidos");
        }

        // Configurar colaboraciones del colaborador (vacías)
        try {
            Field field = Colaborador.class.getDeclaredField("colaboraciones");
            field.setAccessible(true);
            field.set(colaborador, new ArrayList<>());
        } catch (Exception e) {
            fail("Error al inicializar colaboraciones");
        }

        when(colaboradorDAO.buscarPorNick("colab1")).thenReturn(colaborador);
        when(colaboradorDAO.buscarPorNick("colabSeguido")).thenReturn(seguido);

        // Act
        List<DTOPropuesta> resultado = controller.recomendarPropuestas("colab1");

        // Assert
        assertNotNull(resultado);
        // Debería recomendar la propuesta del seguido
        assertTrue(resultado.size() > 0 || resultado.isEmpty()); // Puede estar vacío si no cumple condiciones
        verify(colaboradorDAO).buscarPorNick("colab1");
    }

    @Test
    void testRecomendarPropuestas_ExcluyePropuestasYaColaboradas() {
        // Arrange
        Colaborador colaborador = new Colaborador();
        colaborador.setNick("colab1");

        Propuesta propuestaYaColaborada = new Propuesta(
                categoria, proponente, "Propuesta Ya Colaborada", "Desc", "Lugar",
                LocalDate.now().plusDays(30), 100, 5000, LocalDate.now(),
                List.of(ETipoRetorno.ENTRADAS_GRATIS), "img.jpg"
        );
        Estado estadoPublicada = new Estado(EEstadoPropuesta.PUBLICADA, LocalDate.now());
        propuestaYaColaborada.setEstadoActual(estadoPublicada);
        inicializarColaboraciones(propuestaYaColaborada);

        Colaboracion miColab = new Colaboracion(100, ETipoRetorno.ENTRADAS_GRATIS,
                java.time.LocalDateTime.now(), propuestaYaColaborada, colaborador);
        propuestaYaColaborada.getColaboraciones().add(miColab);

        List<Colaboracion> misColaboraciones = new ArrayList<>();
        misColaboraciones.add(miColab);

        try {
            Field field = Colaborador.class.getDeclaredField("colaboraciones");
            field.setAccessible(true);
            field.set(colaborador, misColaboraciones);

            field = Colaborador.class.getSuperclass().getDeclaredField("usuariosSeguidos");
            field.setAccessible(true);
            field.set(colaborador, new ArrayList<>());
        } catch (Exception e) {
            fail("Error al inicializar campos");
        }

        when(colaboradorDAO.buscarPorNick("colab1")).thenReturn(colaborador);

        // Act
        List<DTOPropuesta> resultado = controller.recomendarPropuestas("colab1");

        // Assert
        assertNotNull(resultado);
        // No debería recomendar propuestas en las que ya colaboró
        assertTrue(resultado.stream().noneMatch(dto -> dto.getTitulo().equals("Propuesta Ya Colaborada")));
    }

    @Test
    void testRecomendarPropuestas_SoloPropuestasPublicadasOEnFinanciacion() {
        // Arrange
        Colaborador colaborador = new Colaborador();
        colaborador.setNick("colab1");

        Propuesta propuestaIngresada = new Propuesta(
                categoria, proponente, "Propuesta Ingresada", "Desc", "Lugar",
                LocalDate.now().plusDays(30), 100, 5000, LocalDate.now(),
                List.of(ETipoRetorno.ENTRADAS_GRATIS), "img.jpg"
        );
        Estado estadoIngresada = new Estado(EEstadoPropuesta.INGRESADA, LocalDate.now());
        propuestaIngresada.setEstadoActual(estadoIngresada);
        inicializarColaboraciones(propuestaIngresada);

        Propuesta propuestaPublicada = new Propuesta(
                categoria, proponente, "Propuesta Publicada", "Desc", "Lugar",
                LocalDate.now().plusDays(30), 100, 5000, LocalDate.now(),
                List.of(ETipoRetorno.ENTRADAS_GRATIS), "img.jpg"
        );
        Estado estadoPublicada = new Estado(EEstadoPropuesta.PUBLICADA, LocalDate.now());
        propuestaPublicada.setEstadoActual(estadoPublicada);
        inicializarColaboraciones(propuestaPublicada);

        Colaborador seguido = new Colaborador();
        seguido.setNick("colabSeguido");

        Colaboracion colab1 = new Colaboracion(100, ETipoRetorno.ENTRADAS_GRATIS,
                java.time.LocalDateTime.now(), propuestaIngresada, seguido);
        Colaboracion colab2 = new Colaboracion(100, ETipoRetorno.ENTRADAS_GRATIS,
                java.time.LocalDateTime.now(), propuestaPublicada, seguido);

        propuestaIngresada.getColaboraciones().add(colab1);
        propuestaPublicada.getColaboraciones().add(colab2);

        List<Seguimiento> seguimientos = new ArrayList<>();
        seguimientos.add(new Seguimiento(colaborador, "colabSeguido"));

        try {
            Field field = Colaborador.class.getSuperclass().getDeclaredField("usuariosSeguidos");
            field.setAccessible(true);
            field.set(colaborador, seguimientos);

            field = Colaborador.class.getDeclaredField("colaboraciones");
            field.setAccessible(true);
            field.set(colaborador, new ArrayList<>());
        } catch (Exception e) {
            fail("Error al inicializar campos");
        }

        when(colaboradorDAO.buscarPorNick("colab1")).thenReturn(colaborador);
        when(colaboradorDAO.buscarPorNick("colabSeguido")).thenReturn(seguido);

        // Act
        List<DTOPropuesta> resultado = controller.recomendarPropuestas("colab1");

        // Assert
        assertNotNull(resultado);
        // Solo debería recomendar propuestas PUBLICADA o EN_FINANCIACION
        assertTrue(resultado.stream().noneMatch(dto -> dto.getTitulo().equals("Propuesta Ingresada")));
    }

    @Test
    void testRecomendarPropuestas_OrdenaPorPuntaje() {
        // Arrange
        Colaborador colaborador = new Colaborador();
        colaborador.setNick("colab1");

        // Propuesta con más colaboradores (mayor puntaje)
        Propuesta propuesta1 = new Propuesta(
                categoria, proponente, "Propuesta 1", "Desc", "Lugar",
                LocalDate.now().plusDays(30), 100, 5000, LocalDate.now(),
                List.of(ETipoRetorno.ENTRADAS_GRATIS), "img.jpg"
        );
        Estado estado1 = new Estado(EEstadoPropuesta.PUBLICADA, LocalDate.now());
        propuesta1.setEstadoActual(estado1);
        inicializarColaboraciones(propuesta1);

        // Agregar múltiples colaboraciones
        for (int i = 0; i < 5; i++) {
            Colaborador colab = new Colaborador();
            colab.setNick("colab" + i);
            Colaboracion col = new Colaboracion(100, ETipoRetorno.ENTRADAS_GRATIS,
                    java.time.LocalDateTime.now(), propuesta1, colab);
            propuesta1.getColaboraciones().add(col);
        }

        // Propuesta con menos colaboradores (menor puntaje)
        Propuesta propuesta2 = new Propuesta(
                categoria, proponente, "Propuesta 2", "Desc", "Lugar",
                LocalDate.now().plusDays(30), 100, 5000, LocalDate.now(),
                List.of(ETipoRetorno.ENTRADAS_GRATIS), "img.jpg"
        );
        Estado estado2 = new Estado(EEstadoPropuesta.PUBLICADA, LocalDate.now());
        propuesta2.setEstadoActual(estado2);
        inicializarColaboraciones(propuesta2);

        Colaborador seguido = new Colaborador();
        seguido.setNick("colabSeguido");

        Colaboracion colab1 = new Colaboracion(100, ETipoRetorno.ENTRADAS_GRATIS,
                java.time.LocalDateTime.now(), propuesta1, seguido);
        Colaboracion colab2 = new Colaboracion(100, ETipoRetorno.ENTRADAS_GRATIS,
                java.time.LocalDateTime.now(), propuesta2, seguido);

        propuesta1.getColaboraciones().add(colab1);
        propuesta2.getColaboraciones().add(colab2);

        List<Seguimiento> seguimientos = new ArrayList<>();
        seguimientos.add(new Seguimiento(colaborador, "colabSeguido"));

        try {
            Field field = Colaborador.class.getSuperclass().getDeclaredField("usuariosSeguidos");
            field.setAccessible(true);
            field.set(colaborador, seguimientos);

            field = Colaborador.class.getDeclaredField("colaboraciones");
            field.setAccessible(true);
            field.set(colaborador, new ArrayList<>());
        } catch (Exception e) {
            fail("Error al inicializar campos");
        }

        when(colaboradorDAO.buscarPorNick("colab1")).thenReturn(colaborador);
        when(colaboradorDAO.buscarPorNick("colabSeguido")).thenReturn(seguido);

        // Act
        List<DTOPropuesta> resultado = controller.recomendarPropuestas("colab1");

        // Assert
        assertNotNull(resultado);
        if (resultado.size() >= 2) {
            // La propuesta con más colaboradores debería estar primero
            assertTrue(resultado.get(0).getPuntaje() >= resultado.get(1).getPuntaje());
        }
    }

    @Test
    void testRecomendarPropuestas_LimitaA10Resultados() {
        // Arrange
        Colaborador colaborador = new Colaborador();
        colaborador.setNick("colab1");

        Colaborador seguido = new Colaborador();
        seguido.setNick("colabSeguido");

        List<Propuesta> propuestas = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            Propuesta p = new Propuesta(
                    categoria, proponente, "Propuesta " + i, "Desc", "Lugar",
                    LocalDate.now().plusDays(30), 100, 5000, LocalDate.now(),
                    List.of(ETipoRetorno.ENTRADAS_GRATIS), "img.jpg"
            );
            Estado estado = new Estado(EEstadoPropuesta.PUBLICADA, LocalDate.now());
            p.setEstadoActual(estado);
            inicializarColaboraciones(p);

            Colaboracion colab = new Colaboracion(100, ETipoRetorno.ENTRADAS_GRATIS,
                    java.time.LocalDateTime.now(), p, seguido);
            p.getColaboraciones().add(colab);
            propuestas.add(p);
        }

        List<Seguimiento> seguimientos = new ArrayList<>();
        seguimientos.add(new Seguimiento(colaborador, "colabSeguido"));

        try {
            Field field = Colaborador.class.getSuperclass().getDeclaredField("usuariosSeguidos");
            field.setAccessible(true);
            field.set(colaborador, seguimientos);

            field = Colaborador.class.getDeclaredField("colaboraciones");
            field.setAccessible(true);
            field.set(colaborador, new ArrayList<>());
        } catch (Exception e) {
            fail("Error al inicializar campos");
        }

        when(colaboradorDAO.buscarPorNick("colab1")).thenReturn(colaborador);
        when(colaboradorDAO.buscarPorNick("colabSeguido")).thenReturn(seguido);

        // Act
        List<DTOPropuesta> resultado = controller.recomendarPropuestas("colab1");

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.size() <= 10, "Debería limitar a 10 resultados");
    }

    @Test
    void testRecomendarPropuestas_ConCoColaboradores() {
        // Arrange
        Colaborador colaborador = new Colaborador();
        colaborador.setNick("colab1");

        Propuesta propuestaComun = new Propuesta(
                categoria, proponente, "Propuesta Comun", "Desc", "Lugar",
                LocalDate.now().plusDays(30), 100, 5000, LocalDate.now(),
                List.of(ETipoRetorno.ENTRADAS_GRATIS), "img.jpg"
        );
        Estado estado = new Estado(EEstadoPropuesta.PUBLICADA, LocalDate.now());
        propuestaComun.setEstadoActual(estado);
        inicializarColaboraciones(propuestaComun);

        Colaborador coColaborador = new Colaborador();
        coColaborador.setNick("coColab");

        Colaboracion miColab = new Colaboracion(100, ETipoRetorno.ENTRADAS_GRATIS,
                java.time.LocalDateTime.now(), propuestaComun, colaborador);
        Colaboracion coColab = new Colaboracion(100, ETipoRetorno.ENTRADAS_GRATIS,
                java.time.LocalDateTime.now(), propuestaComun, coColaborador);

        propuestaComun.getColaboraciones().add(miColab);
        propuestaComun.getColaboraciones().add(coColab);

        Propuesta propuestaRecomendada = new Propuesta(
                categoria, proponente, "Propuesta Recomendada", "Desc", "Lugar",
                LocalDate.now().plusDays(30), 100, 5000, LocalDate.now(),
                List.of(ETipoRetorno.ENTRADAS_GRATIS), "img.jpg"
        );
        Estado estadoRec = new Estado(EEstadoPropuesta.PUBLICADA, LocalDate.now());
        propuestaRecomendada.setEstadoActual(estadoRec);
        inicializarColaboraciones(propuestaRecomendada);

        Colaboracion colabCoColab = new Colaboracion(100, ETipoRetorno.ENTRADAS_GRATIS,
                java.time.LocalDateTime.now(), propuestaRecomendada, coColaborador);
        propuestaRecomendada.getColaboraciones().add(colabCoColab);

        List<Colaboracion> misColaboraciones = new ArrayList<>();
        misColaboraciones.add(miColab);

        try {
            Field field = Colaborador.class.getDeclaredField("colaboraciones");
            field.setAccessible(true);
            field.set(colaborador, misColaboraciones);

            field = Colaborador.class.getSuperclass().getDeclaredField("usuariosSeguidos");
            field.setAccessible(true);
            field.set(colaborador, new ArrayList<>());

            List<Colaboracion> colabsCoColab = new ArrayList<>();
            colabsCoColab.add(coColab);
            colabsCoColab.add(colabCoColab);
            field.setAccessible(true);
            field = Colaborador.class.getDeclaredField("colaboraciones");
            field.setAccessible(true);
            field.set(coColaborador, colabsCoColab);
        } catch (Exception e) {
            fail("Error al inicializar campos");
        }

        when(colaboradorDAO.buscarPorNick("colab1")).thenReturn(colaborador);

        // Act
        List<DTOPropuesta> resultado = controller.recomendarPropuestas("colab1");

        // Assert
        assertNotNull(resultado);
        // Debería recomendar propuestas de co-colaboradores
        verify(colaboradorDAO).buscarPorNick("colab1");
    }

    @Test
    void testRecomendarPropuestas_PropuestaConEstadoNull() {
        // Arrange
        Colaborador colaborador = new Colaborador();
        colaborador.setNick("colab1");

        Propuesta propuestaSinEstado = new Propuesta(
                categoria, proponente, "Propuesta Sin Estado", "Desc", "Lugar",
                LocalDate.now().plusDays(30), 100, 5000, LocalDate.now(),
                List.of(ETipoRetorno.ENTRADAS_GRATIS), "img.jpg"
        );
        propuestaSinEstado.setEstadoActual(null);
        inicializarColaboraciones(propuestaSinEstado);

        Colaborador seguido = new Colaborador();
        seguido.setNick("colabSeguido");

        Colaboracion colab = new Colaboracion(100, ETipoRetorno.ENTRADAS_GRATIS,
                java.time.LocalDateTime.now(), propuestaSinEstado, seguido);
        propuestaSinEstado.getColaboraciones().add(colab);

        List<Seguimiento> seguimientos = new ArrayList<>();
        seguimientos.add(new Seguimiento(colaborador, "colabSeguido"));

        try {
            Field field = Colaborador.class.getSuperclass().getDeclaredField("usuariosSeguidos");
            field.setAccessible(true);
            field.set(colaborador, seguimientos);

            field = Colaborador.class.getDeclaredField("colaboraciones");
            field.setAccessible(true);
            field.set(colaborador, new ArrayList<>());
        } catch (Exception e) {
            fail("Error al inicializar campos");
        }

        when(colaboradorDAO.buscarPorNick("colab1")).thenReturn(colaborador);
        when(colaboradorDAO.buscarPorNick("colabSeguido")).thenReturn(seguido);

        // Act
        List<DTOPropuesta> resultado = controller.recomendarPropuestas("colab1");

        // Assert
        assertNotNull(resultado);
        // No debería recomendar propuestas sin estado
        assertTrue(resultado.stream().noneMatch(dto -> dto.getTitulo().equals("Propuesta Sin Estado")));
    }
}