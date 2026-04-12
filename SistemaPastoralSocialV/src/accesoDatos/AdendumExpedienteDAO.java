package accesoDatos;

import java.util.List;
import javax.persistence.EntityManager;

import entidades.AdendumExpediente;
import entidades.Formulario;

public class AdendumExpedienteDAO extends GenericDAO<AdendumExpediente> {

    public AdendumExpedienteDAO() {
        super(AdendumExpediente.class);
    }

    // USANDO EL NAMED QUERY
    public List<AdendumExpediente> findByFormulario(int idFormulario) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createNamedQuery("AdendumExpediente.findByFormulario", AdendumExpediente.class)
                     .setParameter("idFormulario", idFormulario)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    // VALIDAR SI EXISTE ACTIVO
    public boolean existeActivoPorFormulario(Formulario formulario) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT COUNT(a) FROM AdendumExpediente a WHERE a.formulario = :f AND a.estado = 'ACTIVO'";
            Long count = em.createQuery(jpql, Long.class)
                           .setParameter("f", formulario)
                           .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }

    // LISTAR POR FORMULARIO
    public List<AdendumExpediente> listarPorFormulario(Formulario formulario) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT a FROM AdendumExpediente a WHERE a.formulario = :f ORDER BY a.fechaAdendum DESC";
            return em.createQuery(jpql, AdendumExpediente.class)
                     .setParameter("f", formulario)
                     .getResultList();
        } finally {
            em.close();
        }
    }
}