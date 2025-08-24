package com.culturarte.persistencia;

import com.culturarte.logica.clases.Propuesta;
import com.culturarte.logica.enums.EEstadoPropuesta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class PropuestaDAO {

    //guarda nueva prop en la bd
    public void guardar(Propuesta propuesta) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction(); //arranca la transaccion
        try {
            tx.begin();
            em.persist(propuesta); //persist guarda el dato
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close(); //cierra la transac
        }
    }

    //busca
    public Propuesta buscarPorTitulo(String titulo) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.find(Propuesta.class, titulo);
        } finally {
            em.close();
        }
    }

    //busca
    public boolean existePropuesta(String titulo) {
        return buscarPorTitulo(titulo) != null;
    }

    //lista
    public List<Propuesta> listarPorEstado(EEstadoPropuesta estado) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("""
                SELECT p FROM Propuesta p
                WHERE p.estadoActual.nombre = :estado
                """, Propuesta.class)
                    .setParameter("estado", estado)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    //devuelve todas las prop
    public List<Propuesta> obtenerTodas() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT p FROM Propuesta p", Propuesta.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

        //modificar para prox caso de uso
    public void modificar(Propuesta propuesta) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(propuesta);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}


