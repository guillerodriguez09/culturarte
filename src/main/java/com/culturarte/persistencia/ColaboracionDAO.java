package com.culturarte.persistencia;

import com.culturarte.logica.clases.Colaboracion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

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

        public Colaboracion buscarPorNickYTitulo(String nick, String titulo) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Colaboracion c WHERE c.colaborador.nickname = :nick AND c.propuesta.titulo = :titulo", Colaboracion.class)
                    .setParameter("nick", nick).setParameter("titulo", titulo).getSingleResult();
        }catch(NoResultException e){
            return null;
        }finally {
            em.close();
        }

    }

        public boolean existe(String nick, String titulo){
            return buscarPorNickYTitulo(nick, titulo) != null;
        }

        public List<Colaboracion> obtenerTodas() {
            EntityManager em = JpaUtil.getEntityManager();
            try {
                return em.createQuery("SELECT c FROM Colaboracion c WHERE c.propuesta.proponente.eliminado = false", Colaboracion.class).getResultList();
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

