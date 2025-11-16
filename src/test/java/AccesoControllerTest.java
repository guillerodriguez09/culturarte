
import com.culturarte.logica.clases.Acceso;
import com.culturarte.logica.controllers.AccesoController;
import com.culturarte.logica.dtos.DTOAcceso;
import com.culturarte.persistencia.AccesoDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccesoControllerTest {

    @Mock
    private AccesoDAO accesoDAO;

    @InjectMocks
    private AccesoController controller;

    private DTOAcceso dtoAcceso;
    private Acceso acceso;

    @BeforeEach
    void setUp() throws Exception {
        // Inyectar mock usando reflection ya que el DAO es final
        injectMock(controller, "accesoDao", accesoDAO);

        // Configurar datos de prueba
        LocalDateTime fecha = LocalDateTime.now();

        acceso = new Acceso(
                "192.168.1.1",
                "http://example.com",
                "Chrome",
                "Windows",
                fecha
        );

        dtoAcceso = new DTOAcceso(
                "192.168.1.1",
                "http://example.com",
                "Chrome",
                "Windows",
                fecha
        );
    }

    private void injectMock(Object target, String fieldName, Object mock) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, mock);
    }

    // ========== TESTS PARA listarAccesos ==========

    @Test
    void testListarAccesos_Exitoso() {
        // Arrange
        List<Acceso> accesos = List.of(acceso);
        when(accesoDAO.obtenerAccesos()).thenReturn(accesos);

        // Act
        List<DTOAcceso> resultado = controller.listarAccesos();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("192.168.1.1", resultado.get(0).getIp());
        assertEquals("http://example.com", resultado.get(0).getUrl());
        assertEquals("Chrome", resultado.get(0).getBrowser());
        assertEquals("Windows", resultado.get(0).getSo());
        assertNotNull(resultado.get(0).getFecha());
        verify(accesoDAO).obtenerAccesos();
    }

    @Test
    void testListarAccesos_ListaVacia() {
        // Arrange
        when(accesoDAO.obtenerAccesos()).thenReturn(new ArrayList<>());

        // Act
        List<DTOAcceso> resultado = controller.listarAccesos();

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(accesoDAO).obtenerAccesos();
    }

    @Test
    void testListarAccesos_MultiplesAccesos() {
        // Arrange
        Acceso acceso2 = new Acceso(
                "192.168.1.2",
                "http://example2.com",
                "Firefox",
                "Linux",
                LocalDateTime.now().minusHours(1)
        );
        Acceso acceso3 = new Acceso(
                "192.168.1.3",
                "http://example3.com",
                "Safari",
                "macOS",
                LocalDateTime.now().minusHours(2)
        );

        List<Acceso> accesos = List.of(acceso, acceso2, acceso3);
        when(accesoDAO.obtenerAccesos()).thenReturn(accesos);

        // Act
        List<DTOAcceso> resultado = controller.listarAccesos();

        // Assert
        assertNotNull(resultado);
        assertEquals(3, resultado.size());

        // Verificar primer acceso
        assertEquals("192.168.1.1", resultado.get(0).getIp());
        assertEquals("http://example.com", resultado.get(0).getUrl());
        assertEquals("Chrome", resultado.get(0).getBrowser());
        assertEquals("Windows", resultado.get(0).getSo());

        // Verificar segundo acceso
        assertEquals("192.168.1.2", resultado.get(1).getIp());
        assertEquals("http://example2.com", resultado.get(1).getUrl());
        assertEquals("Firefox", resultado.get(1).getBrowser());
        assertEquals("Linux", resultado.get(1).getSo());

        // Verificar tercer acceso
        assertEquals("192.168.1.3", resultado.get(2).getIp());
        assertEquals("http://example3.com", resultado.get(2).getUrl());
        assertEquals("Safari", resultado.get(2).getBrowser());
        assertEquals("macOS", resultado.get(2).getSo());

        verify(accesoDAO).obtenerAccesos();
    }

    @Test
    void testListarAccesos_ConFechaNull() {
        // Arrange
        Acceso accesoConFechaNull = new Acceso(
                "192.168.1.1",
                "http://example.com",
                "Chrome",
                "Windows",
                null
        );
        List<Acceso> accesos = List.of(accesoConFechaNull);
        when(accesoDAO.obtenerAccesos()).thenReturn(accesos);

        // Act
        List<DTOAcceso> resultado = controller.listarAccesos();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertNull(resultado.get(0).getFecha());
        verify(accesoDAO).obtenerAccesos();
    }

    // ========== TESTS PARA registrarAcceso ==========

    @Test
    void testRegistrarAcceso_Exitoso() {
        // Arrange
        doNothing().when(accesoDAO).guardar(any(Acceso.class));

        // Act
        assertDoesNotThrow(() -> controller.registrarAcceso(dtoAcceso));

        // Assert
        verify(accesoDAO).guardar(any(Acceso.class));
    }

    @Test
    void testRegistrarAcceso_ConTodosLosCampos() {
        // Arrange
        DTOAcceso dtoCompleto = new DTOAcceso(
                "10.0.0.1",
                "https://culturarte.com/propuestas",
                "Edge",
                "Windows 11",
                LocalDateTime.now()
        );
        doNothing().when(accesoDAO).guardar(any(Acceso.class));

        // Act
        controller.registrarAcceso(dtoCompleto);

        // Assert
        verify(accesoDAO).guardar(argThat(acceso ->
                acceso.getIp().equals("10.0.0.1") &&
                        acceso.getUrl().equals("https://culturarte.com/propuestas") &&
                        acceso.getBrowser().equals("Edge") &&
                        acceso.getSo().equals("Windows 11") &&
                        acceso.getFecha() != null
        ));
    }

    @Test
    void testRegistrarAcceso_ConIpNull() {
        // Arrange
        dtoAcceso.setIp(null);
        doNothing().when(accesoDAO).guardar(any(Acceso.class));

        // Act
        assertDoesNotThrow(() -> controller.registrarAcceso(dtoAcceso));

        // Assert
        verify(accesoDAO).guardar(argThat(acceso -> acceso.getIp() == null));
    }

    @Test
    void testRegistrarAcceso_ConUrlNull() {
        // Arrange
        dtoAcceso.setUrl(null);
        doNothing().when(accesoDAO).guardar(any(Acceso.class));

        // Act
        assertDoesNotThrow(() -> controller.registrarAcceso(dtoAcceso));

        // Assert
        verify(accesoDAO).guardar(argThat(acceso -> acceso.getUrl() == null));
    }

    @Test
    void testRegistrarAcceso_ConBrowserNull() {
        // Arrange
        dtoAcceso.setBrowser(null);
        doNothing().when(accesoDAO).guardar(any(Acceso.class));

        // Act
        assertDoesNotThrow(() -> controller.registrarAcceso(dtoAcceso));

        // Assert
        verify(accesoDAO).guardar(argThat(acceso -> acceso.getBrowser() == null));
    }

    @Test
    void testRegistrarAcceso_ConSoNull() {
        // Arrange
        dtoAcceso.setSo(null);
        doNothing().when(accesoDAO).guardar(any(Acceso.class));

        // Act
        assertDoesNotThrow(() -> controller.registrarAcceso(dtoAcceso));

        // Assert
        verify(accesoDAO).guardar(argThat(acceso -> acceso.getSo() == null));
    }

    @Test
    void testRegistrarAcceso_ConFechaNull() {
        // Arrange
        dtoAcceso.setFecha(null);
        doNothing().when(accesoDAO).guardar(any(Acceso.class));

        // Act
        assertDoesNotThrow(() -> controller.registrarAcceso(dtoAcceso));

        // Assert
        verify(accesoDAO).guardar(argThat(acceso -> acceso.getFecha() == null));
    }

    @Test
    void testRegistrarAcceso_DTONull() {
        // Arrange
        // Act & Assert
        // El método no valida null, así que debería lanzar NullPointerException
        assertThrows(NullPointerException.class, () -> controller.registrarAcceso(null));
        verify(accesoDAO, never()).guardar(any());
    }

    @Test
    void testRegistrarAcceso_ConCamposVacios() {
        // Arrange
        DTOAcceso dtoVacio = new DTOAcceso("", "", "", "", LocalDateTime.now());
        doNothing().when(accesoDAO).guardar(any(Acceso.class));

        // Act
        assertDoesNotThrow(() -> controller.registrarAcceso(dtoVacio));

        // Assert
        verify(accesoDAO).guardar(argThat(acceso ->
                acceso.getIp().equals("") &&
                        acceso.getUrl().equals("") &&
                        acceso.getBrowser().equals("") &&
                        acceso.getSo().equals("")
        ));
    }

    @Test
    void testRegistrarAcceso_ConDiferentesNavegadores() {
        // Arrange
        String[] navegadores = {"Chrome", "Firefox", "Safari", "Edge", "Opera"};
        doNothing().when(accesoDAO).guardar(any(Acceso.class));

        // Act & Assert
        for (String navegador : navegadores) {
            dtoAcceso.setBrowser(navegador);
            controller.registrarAcceso(dtoAcceso);
        }

        // Assert
        verify(accesoDAO, times(navegadores.length)).guardar(any(Acceso.class));
    }

    @Test
    void testRegistrarAcceso_ConDiferentesSistemasOperativos() {
        // Arrange
        String[] sistemas = {"Windows", "Linux", "macOS", "Android", "iOS"};
        doNothing().when(accesoDAO).guardar(any(Acceso.class));

        // Act & Assert
        for (String sistema : sistemas) {
            dtoAcceso.setSo(sistema);
            controller.registrarAcceso(dtoAcceso);
        }

        // Assert
        verify(accesoDAO, times(sistemas.length)).guardar(any(Acceso.class));
    }

    @Test
    void testRegistrarAcceso_ConURLsDiferentes() {
        // Arrange
        String[] urls = {
                "http://example.com",
                "https://example.com",
                "http://example.com/propuestas",
                "https://culturarte.com/colaboraciones",
                "http://localhost:8080"
        };
        doNothing().when(accesoDAO).guardar(any(Acceso.class));

        // Act & Assert
        for (String url : urls) {
            dtoAcceso.setUrl(url);
            controller.registrarAcceso(dtoAcceso);
        }

        // Assert
        verify(accesoDAO, times(urls.length)).guardar(any(Acceso.class));
    }

    @Test
    void testRegistrarAcceso_ConIPsDiferentes() {
        // Arrange
        String[] ips = {
                "192.168.1.1",
                "10.0.0.1",
                "172.16.0.1",
                "127.0.0.1",
                "::1"
        };
        doNothing().when(accesoDAO).guardar(any(Acceso.class));

        // Act & Assert
        for (String ip : ips) {
            dtoAcceso.setIp(ip);
            controller.registrarAcceso(dtoAcceso);
        }

        // Assert
        verify(accesoDAO, times(ips.length)).guardar(any(Acceso.class));
    }

    @Test
    void testRegistrarAcceso_VerificarMapeoCorrecto() {
        // Arrange
        LocalDateTime fechaEspecifica = LocalDateTime.of(2024, 1, 15, 10, 30, 0);
        DTOAcceso dto = new DTOAcceso(
                "192.168.1.100",
                "https://culturarte.com",
                "Chrome",
                "Windows",
                fechaEspecifica
        );
        doNothing().when(accesoDAO).guardar(any(Acceso.class));

        // Act
        controller.registrarAcceso(dto);

        // Assert
        verify(accesoDAO).guardar(argThat(acceso ->
                acceso.getIp().equals("192.168.1.100") &&
                        acceso.getUrl().equals("https://culturarte.com") &&
                        acceso.getBrowser().equals("Chrome") &&
                        acceso.getSo().equals("Windows") &&
                        acceso.getFecha().equals(fechaEspecifica)
        ));
    }
}