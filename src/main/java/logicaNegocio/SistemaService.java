package logicaNegocio;

import entidades.AdendumExpediente;
import entidades.Formulario;
import entidades.PersonaParticipante;  // ← Asegúrate de importar PersonaParticipante
import entidades.Usuario;
import accesoDatos.UsuarioDAO;
import accesoDatos.FormularioDAO;
import accesoDatos.AdendumExpedienteDAO;
import accesoDatos.ParroquiaDAO;
import entidades.Parroquia;

import java.util.Comparator;
import java.util.List;

	
public class SistemaService {

	///
	///////////////////////////////////////////////
	/**
	 * Logica del Login y Usuarios
	*/
	///////////////////////////////////////////////
	/// 
    private UsuarioDAO usuarioDAO;
    private Usuario usuarioActual;

    public SistemaService() {
        usuarioDAO = new UsuarioDAO();
    }

    // METODO DEL LOGIN //
    public Usuario login(String email, String password) {
        try {
            Usuario usuario = usuarioDAO.login(email, password);
            if (usuario != null) {
                usuarioActual = usuario;
            }
            return usuario;
        } catch (Exception e) {
            System.out.println("Error en login: " + e.getMessage());
            return null;
        }
    }

    // METODO DEL LOGOUT //
    public void logout() {
        usuarioActual = null;
    }
    
    // METODO PARA OBTENER EL USUARIO ACTUAL (futura implementacion de roles)
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }
    
    ///
    ///////////////////////////////////////////////
    /**
     * Lógica de los Formularios
     */
    ///////////////////////////////////////////////
    /// 
    private FormularioDAO formularioDAO = new FormularioDAO();

    // REGISTRAR FORMULARIO //
    public String registrarFormulario(Formulario f) {
        if (f == null) return "Datos inválidos.";
        try {
            formularioDAO.guardar(f);
            return null;
        } catch (Exception e) {
            e.printStackTrace();  
            return "Error al guardar el formulario: " + e.getMessage();
        }
    }

    // LISTAR FORMULARIOS //
    public List<Formulario> listarFormularios() {
        return formularioDAO.listar();
    }

    // BUSCAR FORMULARIOS POR ID //
    public Formulario buscarFormularioPorId(int id) {
        return formularioDAO.buscar(id);
    }

    // ACTUALIZAR FORMULARIO //
    public String actualizarFormulario(Formulario f) {
        if (f == null) return "Datos inválidos.";
        try {
            // Si se actualiza, no regeneramos el número de ficha (se mantiene el existente)
            // pero si por algún motivo se desea regenerar, se puede agregar una condicion.
            // Por ahora, solo se guarda sin cambios en fichaNumero.
            formularioDAO.actualizar(f);
            return null;
        } catch (Exception e) {
            return "Error al actualizar el formulario: " + e.getMessage();
        }
    }

    // ELIMINAR FORMULARIO //
 // ELIMINAR FORMULARIO //
    public String eliminarFormulario(Formulario f) {
        if (f == null) return "Formulario inválido.";
        try {
            // Primero eliminar los adendums asociados
            List<AdendumExpediente> adendums = adendumExpedienteDAO.findByFormulario(f.getIdFormulario());
            for (AdendumExpediente a : adendums) {
                adendumExpedienteDAO.eliminar(a);
            }
            formularioDAO.eliminar(f);
            return null;
        } catch (Exception e) {
            return "Error al eliminar el formulario: " + e.getMessage();
        }
    }

    //ENLISTAR PARROQUIA EN COMBO BOX

    private ParroquiaDAO parroquiaDAO = new ParroquiaDAO();

    public List<Parroquia> listarParroquias() {
        return parroquiaDAO.obtenerTodas();
    }



    ///
    ///////////////////////////////////////////////
    /**
     * Lógica del AndendumExpediente
     */
    ///////////////////////////////////////////////
    /// 
    
    private AdendumExpedienteDAO adendumExpedienteDAO = new AdendumExpedienteDAO();

    // METODO PARA GUARDAR ADENDUM //
    public void guardarAdendum(AdendumExpediente a) {
        adendumExpedienteDAO.guardar(a);
    }
    
    // METODO PARA VERIFICAR SI EXISTE UN ADENDUM ACTIVO //
	public boolean existeAdendumActivo(Formulario formulario) {
		return adendumExpedienteDAO.existeActivoPorFormulario(formulario);
	}
	
	// METODO PARA LISTAR TODOS LOS ADENDUMS //
	public List<AdendumExpediente> listarTodosAdendums() {
		return adendumExpedienteDAO.listar();
	}
	
	// METODO PARA LISTAR ADENDUMS POR FORMULARIOS //
	public List<AdendumExpediente> listarAdendumPorFormulario(Formulario formulario) {
		return adendumExpedienteDAO.listarPorFormulario(formulario);
	}
	
	// USAR NAMED QUERY (por ID)
	public List<AdendumExpediente> listarAdendumPorFormularioId(int idFormulario) {
	    return adendumExpedienteDAO.findByFormulario(idFormulario);
	}
	
	// METODO PARA ACTUALIZAR ADENDUM //
	public void actualizarAdendum(AdendumExpediente a) {
		adendumExpedienteDAO.actualizar(a);
	}
}