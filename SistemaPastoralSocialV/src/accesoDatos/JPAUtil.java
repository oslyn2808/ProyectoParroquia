package accesoDatos;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
	
	///
	/// Se organiza de esta manera para que se cree el EntityManager cada que se deba de crear,
	/// ya que la forma en la que lo vimos, era un EntityManager global y eso puede genera errores mas adelante.
	/// 

	private static EntityManagerFactory entityManagerFactory = null;

    public static void startEntityManagerFactory() {

        if (entityManagerFactory == null) {
            try {
               System.out.println("INICIANDO ENTITYMANAGERFACTORY");
                entityManagerFactory =
                        Persistence.createEntityManagerFactory("SistemaPastoralSocial");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void stopEntityManagerFactory() {

        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            try {
                entityManagerFactory.close();
                System.out.println("FINALIZANDO ENTITYMANAGERFACTORY");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static EntityManager getEntityManager() {

        if (entityManagerFactory == null) {
            startEntityManagerFactory();
        }
        return entityManagerFactory.createEntityManager();
    }
}

///
/// Esto genera un flujo del sistema como el siguiente: 
/// LoginFrame -> SistemaService -> UsuarioDAO -> GenericDao -> EntityManager -> Base de Datos
/// 
/// Y en cada operacion se crea un EntityManager -> transaccion -> commit -> close
/// 
