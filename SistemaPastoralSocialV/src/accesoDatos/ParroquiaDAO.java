package accesoDatos;

import entidades.Parroquia;
import java.util.List;
import javax.persistence.EntityManager;

public class ParroquiaDAO extends GenericDAO<Parroquia> {

    public ParroquiaDAO() {
        super(Parroquia.class);
    }

    //interactua con la base de datos para traer el nombre de la parroquia
    public List<Parroquia> obtenerTodas() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createNamedQuery("Parroquia.findAll", Parroquia.class) //NamedQuery de la entidad Parroquia
                    .getResultList();
        } finally {
            em.close();
        }

    }
}
