package com.culturarte.persistencia;

import com.culturarte.logica.clases.Categoria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class CategoriaDAO {
//fue para probar alta propuesta
    public Categoria buscarPorNombre(String nombre) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.find(Categoria.class, nombre);
        } finally {
            em.close();
        }
    }

    public void guardar(Categoria c) {
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

    public boolean existe(String nombre) {
        return buscarPorNombre(nombre) != null;
    }

    public List<Categoria> obtenerTodas() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Categoria c", Categoria.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

}
