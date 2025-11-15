import com.culturarte.logica.controllers.SeguimientoController;
import com.culturarte.logica.clases.Colaborador;
import com.culturarte.logica.clases.Proponente;
import com.culturarte.logica.clases.Seguimiento;
import com.culturarte.logica.clases.Usuario;
import com.culturarte.logica.dtos.DTOSeguimiento;
import com.culturarte.logica.dtos.DTOUsuario;
import com.culturarte.persistencia.ColaboradorDAO;
import com.culturarte.persistencia.ProponenteDAO;
import com.culturarte.persistencia.SeguimientoDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SeguimientoControllerTest {

    static class FakeSeguimientoDAO extends SeguimientoDAO {
        private final Map<Integer, Seguimiento> data = new HashMap<>();
        private int idCounter = 1;

        @Override
        public void guardar(Seguimiento s) {
            try {
                Field f = Seguimiento.class.getDeclaredField("Id");
                f.setAccessible(true);
                f.set(s, idCounter++);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            data.put(s.getId(), s);
        }

        @Override
        public void eliminar(Seguimiento s) {
            data.remove(s.getId());
        }

        @Override
        public List<Seguimiento> obtenerTodos() {
            return new ArrayList<>(data.values());
        }

        @Override
        public Seguimiento buscarPorId(int id) {
            return data.get(id);
        }

        @Override
        public boolean existe(String nickSeguidor, String nickSeguido) {
            return data.values().stream()
                    .anyMatch(s -> s.getUsuarioSeguidor().getNick().equals(nickSeguidor)
                            && s.getUsuarioSeguido().equals(nickSeguido));
        }

        @Override
        public int conseguirId(String nickSeguidor, String nickSeguido) {
            return data.values().stream()
                    .filter(s -> s.getUsuarioSeguidor().getNick().equals(nickSeguidor)
                            && s.getUsuarioSeguido().equals(nickSeguido))
                    .map(Seguimiento::getId)
                    .findFirst().orElse(0);
        }

        public List<Seguimiento> obtenerTodosDeNick(String nick) {
            List<Seguimiento> res = new ArrayList<>();
            for (Seguimiento s : data.values()) {
                if (s.getUsuarioSeguidor().getNick().equals(nick)) res.add(s);
            }
            return res;
        }

        public List<Seguimiento> obtenerSeguidoresDeNick(String nick) {
            List<Seguimiento> res = new ArrayList<>();
            for (Seguimiento s : data.values()) {
                if (s.getUsuarioSeguido().equals(nick)) res.add(s);
            }
            return res;
        }
    }

    static class FakeColaboradorDAO extends ColaboradorDAO {
        Map<String, Colaborador> data = new HashMap<>();

        @Override
        public Colaborador buscarPorNick(String nick) {
            return data.get(nick);
        }

        @Override
        public void guardar(Colaborador c) {
            data.put(c.getNick(), c);
        }
    }

    static class FakeProponenteDAO extends ProponenteDAO {
        Map<String, Proponente> data = new HashMap<>();

        @Override
        public Proponente buscarPorNick(String nick) {
            return data.get(nick);
        }

        @Override
        public void guardar(Proponente p) {
            data.put(p.getNick(), p);
        }
    }

    SeguimientoController controller;
    FakeSeguimientoDAO seguimientoDAO;
    FakeColaboradorDAO colaboradorDAO;
    FakeProponenteDAO proponenteDAO;

    Colaborador colaborador;
    Proponente proponente;

    @BeforeEach
    void setUp() throws Exception {
        controller = new SeguimientoController();

        seguimientoDAO = new FakeSeguimientoDAO();
        colaboradorDAO = new FakeColaboradorDAO();
        proponenteDAO = new FakeProponenteDAO();

        inject("seguimientoDAO", seguimientoDAO);
        inject("colaboradorDAO", colaboradorDAO);
        inject("proponenteDAO", proponenteDAO);

        colaborador = crearColaborador("colab1");
        proponente = crearProponente("prop1");

        colaboradorDAO.guardar(colaborador);
        proponenteDAO.guardar(proponente);
    }

    private void inject(String field, Object value) throws Exception {
        Field f = SeguimientoController.class.getDeclaredField(field);
        f.setAccessible(true);
        f.set(controller, value);
    }

    private Colaborador crearColaborador(String nick) throws Exception {
        Constructor<Colaborador> cons = Colaborador.class.getDeclaredConstructor();
        cons.setAccessible(true);
        Colaborador col = cons.newInstance();

        Field fNick = Colaborador.class.getSuperclass().getDeclaredField("nickname");
        fNick.setAccessible(true);
        fNick.set(col, nick);

        return col;
    }

    private Proponente crearProponente(String nick) throws Exception {
        Constructor<Proponente> cons = Proponente.class.getDeclaredConstructor();
        cons.setAccessible(true);
        Proponente prop = cons.newInstance();

        Field fNick = Proponente.class.getSuperclass().getDeclaredField("nickname");
        fNick.setAccessible(true);
        fNick.set(prop, nick);

        return prop;
    }


    @Test
    void testRegistrarSeguimiento() {
        DTOSeguimiento dto = new DTOSeguimiento();
        dto.setUsuarioSeguidor(new DTOUsuario(colaborador.getNick(), "Nombre", "Apellido",
                "pass", "correo@email.com", LocalDate.of(1990, 1, 1), null));
        dto.setUsuarioSeguido(proponente.getNick());

        assertDoesNotThrow(() -> controller.registrarSeguimiento(dto));

        List<String> seguidos = controller.listarSeguidosDeNick(colaborador.getNick());
        assertTrue(seguidos.contains(proponente.getNick()));
    }

    @Test
    void testCancelarSeguimiento() {
        Seguimiento s = new Seguimiento(colaborador, proponente.getNick());
        seguimientoDAO.guardar(s);

        assertDoesNotThrow(() -> controller.cancelarSeguimiento(s.getId()));

        List<String> seguidos = controller.listarSeguidosDeNick(colaborador.getNick());
        assertFalse(seguidos.contains(proponente.getNick()));
    }

    @Test
    void testConseguirId() {
        Seguimiento s = new Seguimiento(colaborador, proponente.getNick());
        seguimientoDAO.guardar(s);

        int id = controller.conseguirId(colaborador.getNick(), proponente.getNick());
        assertEquals(s.getId(), id);
    }

    @Test
    void testListarSeguidosYSeguidores() {
        Seguimiento s = new Seguimiento(colaborador, proponente.getNick());
        seguimientoDAO.guardar(s);

        List<String> seguidos = controller.listarSeguidosDeNick(colaborador.getNick());
        List<String> seguidores = controller.listarSeguidoresDeNick(proponente.getNick());

        assertTrue(seguidos.contains(proponente.getNick()));
        assertTrue(seguidores.contains(colaborador.getNick()));
    }

    @Test
    void testListarSeguimientos() {
        Seguimiento s = new Seguimiento(colaborador, proponente.getNick());
        seguimientoDAO.guardar(s);

        List<DTOSeguimiento> lista = controller.listarSeguimientos();
        assertFalse(lista.isEmpty());
        assertEquals(colaborador.getNick(), lista.get(0).getUsuarioSeguidor().getNick());
        assertEquals(proponente.getNick(), lista.get(0).getUsuarioSeguido());
    }
}
