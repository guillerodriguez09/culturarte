package com.culturarte.persistencia;

import com.culturarte.logica.clases.Proponente;
import com.culturarte.logica.clases.Propuesta;
import com.culturarte.logica.dtos.DTOPropoPropu;
import com.culturarte.logica.enums.EEstadoPropuesta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDate;
import java.util.List;

public class ProponenteDAO {

    public void guardar(Proponente p) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(p);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    public Proponente buscarPorNick(String nick) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.find(Proponente.class, nick);
        } finally {
            em.close();
        }
    }

    public Proponente buscarPorCorreo(String correo) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT pro FROM Proponente pro WHERE pro.correo = :correo", Proponente.class)
                    .setParameter("correo", correo).getSingleResult();
        } finally {
            em.close();
        }
    }

    public boolean existe(String nick) {
        return buscarPorNick(nick) != null;
    }

    public boolean existeCorreo(String correo) {
        return buscarPorCorreo(correo) != null;
    }

    public List<Proponente> obtenerTodos() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT pro FROM Proponente pro WHERE pro.eliminado = false", Proponente.class).getResultList();
        } finally {
            em.close();
        }
    }

    public List<Object[]> obtenerTodPropConPropu(String nick) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT pro, p FROM Proponente pro INNER JOIN pro.propuestas p WHERE pro.nickname = :nick AND pro.eliminado = false", Object[].class)
                    .setParameter("nick", nick).getResultList();
        } finally {
            em.close();
        }
    }

    public List<Object[]> obtenerPropConPropuYEstado(EEstadoPropuesta extado, String nick) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT pro, p FROM Proponente pro INNER JOIN pro.propuestas p WHERE pro.nickname = :nick AND p.estadoActual.nombre = :estado AND pro.eliminado = false", Object[].class)
                    .setParameter("nick", nick).setParameter("estado", extado).getResultList();
        } finally {
            em.close();
        }
    }

    public void eliminarProponente(String nick, LocalDate fechaEliminacion){
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            em.createQuery("UPDATE Proponente pro SET pro.eliminado = true, pro.fechaEliminacion = :fechaEliminacion WHERE pro.nickname = :nick")
                    .setParameter("nick", nick).setParameter("fechaEliminacion", fechaEliminacion).executeUpdate();

            em.getTransaction().commit();
        }catch(Exception e){
            if(em.getTransaction().isActive()){ em.getTransaction().rollback();}
            throw e;
        } finally{
            em.close();
        }
    }

    public List<Object[]> obtenerTodPropConPropuDeEli(String nick) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT pro, p FROM Proponente pro INNER JOIN pro.propuestas p WHERE pro.nickname = :nick AND pro.eliminado = true", Object[].class)
                    .setParameter("nick", nick).getResultList();
        } finally {
            em.close();
        }
    }

    public List<Proponente> obtenerTodosElim() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT pro FROM Proponente pro WHERE pro.eliminado = true", Proponente.class).getResultList();
        } finally {
            em.close();
        }
    }

}
