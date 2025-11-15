import com.culturarte.logica.controllers.ColaboracionController;
import com.culturarte.logica.clases.Colaboracion;
import com.culturarte.logica.clases.Colaborador;
import com.culturarte.logica.clases.Estado;
import com.culturarte.logica.clases.Propuesta;
import com.culturarte.logica.dtos.DTOColabConsulta;
import com.culturarte.logica.dtos.DTOColaboracion;
import com.culturarte.logica.dtos.DTOConstanciaPago;
import com.culturarte.logica.enums.EEstadoPropuesta;
import com.culturarte.logica.enums.ETipoRetorno;
import com.culturarte.persistencia.ColaboracionDAO;
import com.culturarte.persistencia.ColaboradorDAO;
import com.culturarte.persistencia.PropuestaDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ColaboracionControllerTest {

    //  FAKES DAO (para no usar bd real)
    static class FakeColaboracionDAO extends ColaboracionDAO {
        Map<Integer, Colaboracion> data = new HashMap<>();
        int id = 1;

        @Override
        public void guardar(Colaboracion c) {
            try {
                Field f = Colaboracion.class.getDeclaredField("id");
                f.setAccessible(true);
                f.set(c, id++);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            data.put(c.getId(), c);
        }

        @Override
        public boolean existe(String nick, String titulo) {
            return data.values().stream().anyMatch(c ->
                    c.getColaborador().getNick().equals(nick) &&
                            c.getPropuesta().getTitulo().equals(titulo)
            );
        }

        @Override
        public List<Colaboracion> obtenerTodas() {
            return new ArrayList<>(data.values());
        }

        @Override
        public Colaboracion buscarPorId(int id) {
            return data.get(id);
        }

        @Override
        public void eliminar(Colaboracion c) {
            data.remove(c.getId());
        }

        @Override
        public void actualizar(Colaboracion c) {
            data.put(c.getId(), c);
        }
    }

    static class FakePropuestaDAO extends PropuestaDAO {
        Map<String, Propuesta> data = new HashMap<>();

        @Override
        public Propuesta buscarPorTitulo(String t) {
            return data.get(t);
        }

        @Override
        public void actualizar(Propuesta p) {
            data.put(p.getTitulo(), p);
        }
    }

    static class FakeColaboradorDAO extends ColaboradorDAO {
        Map<String, Colaborador> data = new HashMap<>();

        @Override
        public Colaborador buscarPorNick(String n) {
            return data.get(n);
        }

        @Override
        public void actualizar(Colaborador c) {
            data.put(c.getNick(), c);
        }
    }

    ColaboracionController controller;

    FakeColaboracionDAO fakeColabDAO;
    FakePropuestaDAO fakePropuestaDAO;
    FakeColaboradorDAO fakeColaboradorDAO;

    @BeforeEach
    void setup() throws Exception {
        controller = new ColaboracionController();

        fakeColabDAO = new FakeColaboracionDAO();
        fakePropuestaDAO = new FakePropuestaDAO();
        fakeColaboradorDAO = new FakeColaboradorDAO();

        inject("colaboracionDAO", fakeColabDAO);
        inject("propuestaDAO", fakePropuestaDAO);
        inject("colaboradorDAO", fakeColaboradorDAO);
    }

    private void inject(String field, Object value) throws Exception {
        Field f = ColaboracionController.class.getDeclaredField(field);
        f.setAccessible(true);
        f.set(controller, value);
    }

    private Propuesta crearPropuesta(String titulo, EEstadoPropuesta estadoEnum) throws Exception {
        Constructor<Propuesta> cons = Propuesta.class.getDeclaredConstructor();
        cons.setAccessible(true);
        Propuesta p = cons.newInstance();

        Field fTitulo = Propuesta.class.getDeclaredField("titulo");
        fTitulo.setAccessible(true);
        fTitulo.set(p, titulo);

        Estado est = new Estado(estadoEnum, LocalDate.now());
        p.setEstadoActual(est);

        Field fColabs = Propuesta.class.getDeclaredField("colaboraciones");
        fColabs.setAccessible(true);
        fColabs.set(p, new ArrayList<>());

        return p;
    }

    private Colaborador crearColaborador(String nick) throws Exception {
        Constructor<Colaborador> cons = Colaborador.class.getDeclaredConstructor();
        cons.setAccessible(true);
        Colaborador col = cons.newInstance();

        Field fNick = Colaborador.class.getSuperclass().getDeclaredField("nickname");
        fNick.setAccessible(true);
        fNick.set(col, nick);

        Field fColabs = Colaborador.class.getDeclaredField("colaboraciones");
        fColabs.setAccessible(true);
        fColabs.set(col, new ArrayList<>());

        return col;
    }


    @Test
    void testRegistrarColaboracionCorrecta() throws Exception {
        Propuesta p = crearPropuesta("TituloX", EEstadoPropuesta.PUBLICADA);
        fakePropuestaDAO.data.put("TituloX", p);

        Colaborador col = crearColaborador("nick1");
        fakeColaboradorDAO.data.put("nick1", col);

        DTOColaboracion dto = new DTOColaboracion();
        dto.setColaboradorNick("nick1");
        dto.setPropuestaTitulo("TituloX");
        dto.setMonto(500);
        dto.setRetorno(ETipoRetorno.ENTRADAS_GRATIS);

        controller.registrarColaboracion(dto);

        assertEquals(1, fakeColabDAO.data.size());
        assertEquals(1, col.getColaboraciones().size());
        assertEquals(EEstadoPropuesta.EN_FINANCIACION, p.getEstadoActual().getNombre());
    }

    @Test
    void testRegistrarColaboracionMontoInvalido() {
        DTOColaboracion dto = new DTOColaboracion();
        dto.setMonto(0);

        assertThrows(IllegalArgumentException.class, () -> controller.registrarColaboracion(dto));
    }

    @Test
    void testRegistrarColaboracionPropuestaNoExiste() {
        DTOColaboracion dto = new DTOColaboracion();
        dto.setMonto(100);
        dto.setRetorno(ETipoRetorno.ENTRADAS_GRATIS);
        dto.setPropuestaTitulo("NoExiste");

        assertThrows(IllegalArgumentException.class, () -> controller.registrarColaboracion(dto));
    }

    @Test
    void testListarColaboraciones() throws Exception {
        Propuesta p = crearPropuesta("Test", EEstadoPropuesta.PUBLICADA);
        fakePropuestaDAO.data.put("Test", p);

        Colaborador col = crearColaborador("u");
        fakeColaboradorDAO.data.put("u", col);

        Colaboracion c = new Colaboracion(100, ETipoRetorno.ENTRADAS_GRATIS, LocalDateTime.now(), p, col);
        Field fId = Colaboracion.class.getDeclaredField("id");
        fId.setAccessible(true);
        fId.set(c, 1);

        fakeColabDAO.data.put(1, c);

        List<DTOColabConsulta> lista = controller.listarColaboraciones();
        assertEquals(1, lista.size());
        Field fNick = DTOColabConsulta.class.getDeclaredField("colaboradorNick");
        fNick.setAccessible(true);
        assertEquals("u", fNick.get(lista.get(0)));
    }

    @Test
    void testConsultarColaboracionesPorColaborador() throws Exception {
        Colaborador col = crearColaborador("u");
        fakeColaboradorDAO.data.put("u", col);

        Propuesta p = crearPropuesta("PP", EEstadoPropuesta.PUBLICADA);
        fakePropuestaDAO.data.put("PP", p);

        Colaboracion c = new Colaboracion(50, ETipoRetorno.ENTRADAS_GRATIS, LocalDateTime.now(), p, col);
        Field fId = Colaboracion.class.getDeclaredField("id");
        fId.setAccessible(true);
        fId.set(c, 1);

        col.addColaboracion(c);

        List<DTOColabConsulta> res = controller.consultarColaboracionesPorColaborador("u");
        assertEquals(1, res.size());

        Field fProp = DTOColabConsulta.class.getDeclaredField("propuestaNombre");
        fProp.setAccessible(true);
        assertEquals("PP", fProp.get(res.get(0)));
    }

    @Test
    void testCancelarColaboracion() throws Exception {
        Colaborador col = crearColaborador("u");
        fakeColaboradorDAO.data.put("u", col);

        Propuesta p = crearPropuesta("PP", EEstadoPropuesta.PUBLICADA);
        fakePropuestaDAO.data.put("PP", p);

        Colaboracion c = new Colaboracion(10, ETipoRetorno.ENTRADAS_GRATIS, LocalDateTime.now(), p, col);
        Field fId = Colaboracion.class.getDeclaredField("id");
        fId.setAccessible(true);
        fId.set(c, 1);

        col.addColaboracion(c);
        p.addColaboracion(c);
        fakeColabDAO.data.put(1, c);

        controller.cancelarColaboracion(1);

        assertTrue(fakeColabDAO.data.isEmpty());
        assertTrue(p.getColaboraciones().isEmpty());
        assertTrue(col.getColaboraciones().isEmpty());
    }

    @Test
    void testEmitirConstanciaPagoCorrecta() throws Exception {
        Colaborador col = crearColaborador("u");
        fakeColaboradorDAO.data.put("u", col);

        Propuesta p = crearPropuesta("PP", EEstadoPropuesta.PUBLICADA);
        fakePropuestaDAO.data.put("PP", p);

        Colaboracion c = new Colaboracion(20, ETipoRetorno.ENTRADAS_GRATIS, LocalDateTime.now(), p, col);
        Field fId = Colaboracion.class.getDeclaredField("id");
        fId.setAccessible(true);
        fId.set(c, 1);

        fakeColabDAO.data.put(1, c);

        DTOConstanciaPago dto = controller.emitirConstanciaPago(1);
        assertEquals("u", dto.getColaboradorNick());
        assertTrue(c.getConstanciaEmitida());
    }

    @Test
    void testEmitirConstanciaPagoYaEmitida() throws Exception {
        Colaborador col = crearColaborador("u");
        fakeColaboradorDAO.data.put("u", col);

        Propuesta p = crearPropuesta("PP", EEstadoPropuesta.PUBLICADA);
        fakePropuestaDAO.data.put("PP", p);

        Colaboracion c = new Colaboracion(20, ETipoRetorno.ENTRADAS_GRATIS, LocalDateTime.now(), p, col);
        Field fId = Colaboracion.class.getDeclaredField("id");
        fId.setAccessible(true);
        fId.set(c, 1);

        c.setConstanciaEmitida(true);
        fakeColabDAO.data.put(1, c);

        DTOConstanciaPago dto = controller.emitirConstanciaPago(1);
        assertEquals("u", dto.getColaboradorNick());
        assertTrue(c.getConstanciaEmitida());
    }
}
