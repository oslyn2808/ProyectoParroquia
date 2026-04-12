package accesoDatos;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

////
//Clase abstracta para la reutilizacion del codigo y asi evitar repeticion de DAO
////

public abstract class GenericDAO<T> implements DAO<T>{

	protected EntityManager em;
	private Class<T> clase;
	
	public GenericDAO(Class<T> clase) {
		this.clase = clase;
		this.em = JPAUtil.getEntityManager();
	}
	
	@Override
	public void guardar(T entidad) {
	    EntityManager em = JPAUtil.getEntityManager();
	    try {
	        em.getTransaction().begin();
	        em.persist(entidad);
	        em.getTransaction().commit();
	    } catch (Exception e) {
	        if (em.getTransaction().isActive()) {
	            em.getTransaction().rollback();
	        }

	        Throwable cause = e;
	        while (cause != null) {
	            String msg = cause.getMessage();
	            if (msg != null) {
	                if (msg.contains("Ya existe una ayuda activa o pendiente para este nucleo familiar.")) {
	                    throw new RuntimeException("No se puede registrar la ayuda porque el núcleo familiar ya tiene una ayuda activa o pendiente.");
	                }
	                if (msg.contains("Ya existe un beneficiario principal para este nucleo familiar.")) {
	                    throw new RuntimeException("No se puede registrar más de un beneficiario principal para el mismo núcleo familiar.");
	                }
	            }
	            cause = cause.getCause();
	        }

	        throw new RuntimeException("Error al guardar en base de datos.", e);
	    } finally {
	        em.close();
	    }
	}
	
	@Override
	public void actualizar(T entidad) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(entidad);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
	}
	
	@Override
	public void eliminar(T entidad) {
	    EntityManager em = JPAUtil.getEntityManager();
	    try {
	        em.getTransaction().begin();
	        T managed = em.contains(entidad) ? entidad : em.merge(entidad);
	        em.remove(managed);
	        em.getTransaction().commit();
	    } catch (Exception e) {
	        if (em.getTransaction().isActive()) em.getTransaction().rollback();
	        throw new RuntimeException("Error al eliminar.", e);
	    } finally {
	        em.close();
	    }
	}
	
	@Override
	public T buscar(int id) {
		EntityManager em = JPAUtil.getEntityManager();
		
		try {
			return em.find(clase, id);
		} finally {
			em.close();
		}
	}
	
	@Override
	public List<T> listar() {
		EntityManager em = JPAUtil.getEntityManager();
		try {
		String jpql = "FROM " + clase.getSimpleName();
		TypedQuery<T> query = em.createQuery(jpql, clase);
		return query.getResultList();
		} finally {
			em.close();
		}
	}
}
