package com.culturarte.persistencia;

import com.culturarte.logica.clases.Seguimiento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

import java.util.List;

public class SeguimientoDAO {

    public void guardar(Seguimiento seguimiento) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(seguimiento);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Seguimiento> obtenerTodos() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT s FROM Seguimiento s", Seguimiento.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Seguimiento buscarPorId(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.find(Seguimiento.class, id);
        } finally {
            em.close();
        }
    }

    public void eliminar(Seguimiento seguimiento) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Seguimiento segui = em.merge(seguimiento);
            em.remove(segui);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Seguimiento buscarPorNickYNicky(String nick, String nicky) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT s FROM Seguimiento s WHERE s.usuarioSeguidor.nickname = :nick AND s.usuarioSeguido = :nicky", Seguimiento.class)
                    .setParameter("nick", nick).setParameter("nicky", nicky).getSingleResult();
        }catch(NoResultException e){
            return null;
        }finally {
            em.close();
        }

    }

    public List<Seguimiento> obtenerTodosDeNick(String nick) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT s FROM Seguimiento s WHERE s.usuarioSeguidor.nickname = :nick", Seguimiento.class)
                    .setParameter("nick", nick).getResultList();
        } finally {
            em.close();
        }
    }

    public int conseguirId(String nick, String nicky){
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT s.Id FROM Seguimiento s WHERE s.usuarioSeguidor.nickname = :nick AND s.usuarioSeguido = :nicky", Integer.class)
                    .setParameter("nick", nick).setParameter("nicky", nicky).getSingleResult();
        }catch(NoResultException e){
            return 0;
        }finally {
            em.close();
        }
    }

    public boolean existe(String nick, String nicky) {
        return buscarPorNickYNicky(nick, nicky) != null;
    }

}
