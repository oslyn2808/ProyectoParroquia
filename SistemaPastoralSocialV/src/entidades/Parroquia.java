package entidades;

import javax.persistence.*;

@Entity
@Table(name = "parroquia")
public class Parroquia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_parroquia")
    private int idParroquia;
    private String nombre;
    private String provincia;
    private String canton;
    private String direccion;
    private String telefono;
    @Column(name = "email")
    private String correo;
    @Column(name = "codigo_acceso")
    private String codigoAcceso;

    public Parroquia() {
    }

    public Parroquia(int idParroquia, String nombre, String provincia, String canton,
                     String direccion, String telefono, String correo, String codigoAcceso) {
        this.idParroquia = idParroquia;
        this.nombre = nombre;
        this.provincia = provincia;
        this.canton = canton;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.codigoAcceso = codigoAcceso;
    }

    public int getIdParroquia() {
        return idParroquia;
    }

    public void setIdParroquia(int idParroquia) {
        this.idParroquia = idParroquia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCanton() {
        return canton;
    }

    public void setCanton(String canton) {
        this.canton = canton;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCodigoAcceso() {
        return codigoAcceso;
    }

    public void setCodigoAcceso(String codigoAcceso) {
        this.codigoAcceso = codigoAcceso;
    }

	//Muestra los verdaderos nombres de las parroquias y no sus entidades
    @Override
    public String toString() {
        return nombre;
    }
}
