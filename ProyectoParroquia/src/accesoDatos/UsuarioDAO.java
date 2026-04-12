package accesoDatos;

import entidades.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class UsuarioDAO extends GenericDAO<Usuario> {

    public UsuarioDAO() {
        super(Usuario.class);
    }

    public Usuario login(String email, String password) {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            String jpql = "SELECT u FROM Usuario u WHERE u.email = :email AND u.password = :password";

            TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
            query.setParameter("email", email);
            query.setParameter("password", password);

            List<Usuario> resultado = query.getResultList();

            if (resultado.isEmpty()) {
                return null;
            }

            return resultado.get(0);

        } finally {
            em.close();
        }
    }
}