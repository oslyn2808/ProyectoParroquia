package entidades;

import javax.persistence.*;

/**
 * Persona participante del formulario.
 * Reemplaza los 110 campos planos (persona_1 ... persona_10)
 * con una tabla hija relacionada al formulario.
 *
 * Tabla DB: persona_participante
 * Relación: muchas personas → un formulario (ManyToOne)
 */
@Entity
@Table(name = "persona_participante")
public class PersonaParticipante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona")
    private Integer idPersona;

    /** Orden dentro del formulario (1-10) */
    @Column(name = "numero_persona", nullable = false)
    private int numeroPersona;

    // ─── Relación con el formulario ───────────────────

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_formulario", nullable = false)
    private Formulario formulario;

    // ─── Datos personales ─────────────────────────────

    @Column(name = "nombre_completo")
    private String nombreCompleto;

    @Column(name = "documento_identidad")
    private String documentoIdentidad;

    @Column(name = "sexo")
    private String sexo;

    @Column(name = "jefatura")
    private String jefatura;

    @Column(name = "relacion")
    private String relacion;

    @Column(name = "edad")
    private Integer edad;

    @Column(name = "pais")
    private String pais;

    @Column(name = "migracion")
    private String migracion;

    @Column(name = "educacion")
    private String educacion;

    @Column(name = "salud")
    private String salud;

    @Column(name = "seguro")
    private String seguro;

    // ─── Datos laborales / ingreso ────────────────────

    @Column(name = "ocupacion")
    private String ocupacion;

    @Column(name = "trabaja")
    private Boolean trabaja;

    @Column(name = "ingreso_mensual")
    private Double ingresoMensual;

    // ─────────────────────────────────────────────────
    // CONSTRUCTOR VACÍO (requerido por JPA)
    // ─────────────────────────────────────────────────

    public PersonaParticipante() {}

    public PersonaParticipante(int numeroPersona) {
        this.numeroPersona = numeroPersona;
    }

    // ─────────────────────────────────────────────────
    // GETTERS Y SETTERS
    // ─────────────────────────────────────────────────

    public Integer getIdPersona() { return idPersona; }
    public void setIdPersona(Integer idPersona) { this.idPersona = idPersona; }

    public int getNumeroPersona() { return numeroPersona; }
    public void setNumeroPersona(int numeroPersona) { this.numeroPersona = numeroPersona; }

    public Formulario getFormulario() { return formulario; }
    public void setFormulario(Formulario formulario) { this.formulario = formulario; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getDocumentoIdentidad() { return documentoIdentidad; }
    public void setDocumentoIdentidad(String documentoIdentidad) { this.documentoIdentidad = documentoIdentidad; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public String getJefatura() { return jefatura; }
    public void setJefatura(String jefatura) { this.jefatura = jefatura; }

    public String getRelacion() { return relacion; }
    public void setRelacion(String relacion) { this.relacion = relacion; }

    public Integer getEdad() { return edad; }
    public void setEdad(Integer edad) { this.edad = edad; }

    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }

    public String getMigracion() { return migracion; }
    public void setMigracion(String migracion) { this.migracion = migracion; }

    public String getEducacion() { return educacion; }
    public void setEducacion(String educacion) { this.educacion = educacion; }

    public String getSalud() { return salud; }
    public void setSalud(String salud) { this.salud = salud; }

    public String getSeguro() { return seguro; }
    public void setSeguro(String seguro) { this.seguro = seguro; }

    public String getOcupacion() { return ocupacion; }
    public void setOcupacion(String ocupacion) { this.ocupacion = ocupacion; }

    public Boolean getTrabaja() { return trabaja; }
    public void setTrabaja(Boolean trabaja) { this.trabaja = trabaja; }

    public Double getIngresoMensual() { return ingresoMensual; }
    public void setIngresoMensual(Double ingresoMensual) { this.ingresoMensual = ingresoMensual; }
}