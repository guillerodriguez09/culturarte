package com.culturarte.persistencia;

import com.culturarte.logica.clases.Colaborador;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDate;
import java.util.List;

public class ColaboradorDAO {

    public void guardar(Colaborador c) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(c);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    public Colaborador buscarPorNick(String nick) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.find(Colaborador.class, nick);
        } finally {
            em.close();
        }
    }

    public Colaborador buscarPorCorreo(String correo) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT col FROM Colaborador col WHERE col.correo = :correo", Colaborador.class)
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

    public List<Colaborador> obtenerTodos() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT col FROM Colaborador col WHERE col.eliminado = false", Colaborador.class).getResultList();
        } finally {
            em.close();
        }
    }

    public List<Object[]> obtenerTodColConPropu(String nick) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT col, c, p FROM Colaborador col INNER JOIN col.colaboraciones c INNER JOIN c.propuesta p WHERE col.nickname = :nick AND col.eliminado = false", Object[].class)
                    .setParameter("nick", nick).getResultList();
        } finally {
            em.close();
        }
    }

//agregue este metodo para poder actualizar la lista de colaboraciones
    public void actualizar(Colaborador colaborador) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(colaborador);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void eliminarColaborador(String nick, LocalDate fechaEliminacion){
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            em.createQuery("UPDATE Colaborador col SET col.eliminado = true, col.fechaEliminacion = :fechaEliminacion WHERE col.nickname = :nick")
                    .setParameter("nick", nick).setParameter("fechaEliminacion", fechaEliminacion).executeUpdate();

            em.getTransaction().commit();
        }catch(Exception e){
            if(em.getTransaction().isActive()){ em.getTransaction().rollback();}
            throw e;
        } finally{
            em.close();
        }
    }

}
