package com.culturarte.persistencia;

import com.culturarte.logica.clases.Colaboracion;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ColaboracionDAO {

        public void guardar(Colaboracion colaboracion) {
            EntityManager em = JpaUtil.getEntityManager();
            try {
                em.getTransaction().begin();
                em.persist(colaboracion);
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

        public List<Colaboracion> obtenerTodas() {
            EntityManager em = JpaUtil.getEntityManager();
            try {
                return em.createQuery("SELECT c FROM Colaboracion c", Colaboracion.class).getResultList();
            } finally {
                em.close();
            }
        }

    public Colaboracion buscarPorId(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.find(Colaboracion.class, id);
        } finally {
            em.close();
        }
    }

    public void eliminar(Colaboracion colab) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Colaboracion colabMergeada = em.merge(colab);
            em.remove(colabMergeada);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

}

