package accesoDatos;

import entidades.Formulario;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class FormularioDAO extends GenericDAO<Formulario> {

    public FormularioDAO() {
        super(Formulario.class);
    }

    /**
     * Busca formularios por número de ficha.
     */
    public List<Formulario> buscarPorFicha(Integer fichaNumero) {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            String jpql = "SELECT f FROM Formulario f WHERE f.fichaNumero = :ficha";

            TypedQuery<Formulario> query = em.createQuery(jpql, Formulario.class);
            query.setParameter("ficha", fichaNumero);

            return query.getResultList();

        } finally {
            em.close();
        }
    }

    /**
     * Busca formularios por parroquia.
     */
    public List<Formulario> buscarPorParroquia(String parroquia) {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            String jpql = "SELECT f FROM Formulario f WHERE LOWER(f.parroquia) LIKE LOWER(:p)";

            TypedQuery<Formulario> query = em.createQuery(jpql, Formulario.class);
            query.setParameter("p", "%" + parroquia + "%");

            return query.getResultList();

        } finally {
            em.close();
        }
    }
}