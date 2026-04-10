package accesoDatos;

import java.util.List;


////
//Interface DAO para implementarla con la GenericDAO
////

public interface DAO<T> {
	
	public void guardar(T entidad);
	public void actualizar(T entidad);
	public void eliminar(T entidad);
	T buscar(int id);
	List<T> listar();
}
