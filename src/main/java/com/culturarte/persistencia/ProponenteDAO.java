package com.culturarte.persistencia;

import com.culturarte.logica.clases.Proponente;
import com.culturarte.logica.clases.Propuesta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class ProponenteDAO {
//fue para probar altapropuesta
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

    public boolean existe(String nick) {
        return buscarPorNick(nick) != null;
    }

    public List<Proponente> obtenerNomTodos() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT pro.nickname FROM Proponente pro", Proponente.class).getResultList();
        } finally {
            em.close();
        }
    }

    public List<Proponente> obtenerTodos() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT pro FROM Proponente pro", Proponente.class).getResultList();
        } finally {
            em.close();
        }
    }

    public List<Object[]> obtenerTodPropConPropu(String nick) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT pro, p FROM Proponente pro INNER JOIN pro.propuestas p WHERE pro.nickname = :nick", Object[].class)
                    .setParameter("nick", nick).getResultList();
        } finally {
            em.close();
        }
    }

}
