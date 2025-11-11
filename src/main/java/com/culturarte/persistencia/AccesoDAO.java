package com.culturarte.persistencia;

import com.culturarte.logica.clases.Acceso;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;

public class AccesoDAO {

    public void guardar(Acceso acceso) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(acceso);
        em.getTransaction().commit();
    }

    public List<Acceso> obtenerAccesos() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query q = em.createQuery("SELECT a FROM Acceso a ORDER BY a.fecha DESC");
            q.setMaxResults(10000);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

}

