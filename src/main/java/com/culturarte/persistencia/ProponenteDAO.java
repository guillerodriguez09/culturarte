package com.culturarte.persistencia;

import com.culturarte.logica.clases.Proponente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

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
}
