package com.culturarte.persistencia;

import com.culturarte.logica.clases.Colaborador;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

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

    public boolean existe(String nick) {
        return buscarPorNick(nick) != null;
    }

    public List<Colaborador> obtenerNomTodos() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT col.nickname FROM Colaborador col", Colaborador.class).getResultList();
        } finally {
            em.close();
        }
    }

    public List<Colaborador> obtenerTodos() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT col FROM Colaborador col", Colaborador.class).getResultList();
        } finally {
            em.close();
        }
    }

    public List<Object[]> obtenerTodColConPropu(String nick) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT col, c, p FROM Colaborador col INNER JOIN col.colaboraciones c INNER JOIN c.propuesta p WHERE col.nickname = :nick", Object[].class)
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


}
