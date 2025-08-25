package com.culturarte.persistencia;

import com.culturarte.logica.clases.Colaborador;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

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

}
