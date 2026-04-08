package entidades;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Entidad principal del formulario de asistencia social.
 * Contiene datos del registro, datos parroquiales/generales,
 * vivienda, asistencia solicitada e información final.
 *
 * Las personas participantes se gestionan en la entidad
 * separada PersonaParticipante (relación OneToMany).
 */
@Entity
@Table(name = "formulario")
public class Formulario {

    // ─────────────────────────────────────────────
    // 1. DATOS DEL REGISTRO
    // ─────────────────────────────────────────────

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_formulario")
    private Integer idFormulario;   // ← CORREGIDO: Integer (objeto), no int (primitivo)
                                    //   Con int=0 JPA asume que ya existe y falla el INSERT

    @Column(name = "ficha_numero", length = 30)  // Suficiente para "CD-" + cédula (ej. 20 dígitos)
    private String fichaNumero;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_inicio")
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_conclusion")
    private Date fechaConclusion;

    // ─────────────────────────────────────────────
    // 2. PROLONGACIONES
    // ─────────────────────────────────────────────

    @Temporal(TemporalType.DATE)
    @Column(name = "prolongacion_1")
    private Date prolongacion1;

    @Temporal(TemporalType.DATE)
    @Column(name = "prolongacion_2")
    private Date prolongacion2;

    @Temporal(TemporalType.DATE)
    @Column(name = "prolongacion_3")
    private Date prolongacion3;

    @Temporal(TemporalType.DATE)
    @Column(name = "prolongacion_4")
    private Date prolongacion4;

    // ─────────────────────────────────────────────
    // 3. DATOS PARROQUIALES
    // ─────────────────────────────────────────────

    @Column(name = "parroquia")
    private String parroquia;

    @Column(name = "sector_filial")
    private String sectorFilial;

    // ─────────────────────────────────────────────
    // 4. DATOS GENERALES
    // ─────────────────────────────────────────────

    @Column(name = "direccion", length = 500)
    private String direccion;

    @Column(name = "telefono_contacto")
    private String telefonoContacto;

    // ─────────────────────────────────────────────
    // 5. PERSONAS PARTICIPANTES (relación 1 a N)
    // ─────────────────────────────────────────────

    @OneToMany(
        mappedBy = "formulario",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    @OrderBy("numeroPersona ASC")
    private List<PersonaParticipante> personas = new ArrayList<>();

    // ─────────────────────────────────────────────
    // 6. DATOS DE LA VIVIENDA (embebidos)
    // ─────────────────────────────────────────────

    @Embedded
    private DatosVivienda vivienda = new DatosVivienda();

    // ─────────────────────────────────────────────
    // 7. ASISTENCIA SOLICITADA (embebida)
    // ─────────────────────────────────────────────

    @Embedded
    private AsistenciaSolicitada asistencia = new AsistenciaSolicitada();

    // ─────────────────────────────────────────────
    // 8. INFORMACIÓN FINAL
    // ─────────────────────────────────────────────

    @Column(name = "nombre_entrevistador")
    private String nombreEntrevistador;

    @Column(name = "observaciones", length = 1000)
    private String observaciones;

    // ─────────────────────────────────────────────
    // CONSTRUCTOR VACÍO (requerido por JPA)
    // ─────────────────────────────────────────────

    public Formulario() {}

    // ─────────────────────────────────────────────
    // HELPER: agregar persona
    // ─────────────────────────────────────────────

    public void agregarPersona(PersonaParticipante persona) {
        persona.setFormulario(this);
        this.personas.add(persona);
    }

    // ─────────────────────────────────────────────
    // GETTERS Y SETTERS
    // ─────────────────────────────────────────────

    public Integer getIdFormulario() { return idFormulario; }
    public void setIdFormulario(Integer idFormulario) { this.idFormulario = idFormulario; }

    public String getFichaNumero() { return fichaNumero; }
    public void setFichaNumero(String fichaNumero) { this.fichaNumero = fichaNumero; }

    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }

    public Date getFechaConclusion() { return fechaConclusion; }
    public void setFechaConclusion(Date fechaConclusion) { this.fechaConclusion = fechaConclusion; }

    public Date getProlongacion1() { return prolongacion1; }
    public void setProlongacion1(Date prolongacion1) { this.prolongacion1 = prolongacion1; }

    public Date getProlongacion2() { return prolongacion2; }
    public void setProlongacion2(Date prolongacion2) { this.prolongacion2 = prolongacion2; }

    public Date getProlongacion3() { return prolongacion3; }
    public void setProlongacion3(Date prolongacion3) { this.prolongacion3 = prolongacion3; }

    public Date getProlongacion4() { return prolongacion4; }
    public void setProlongacion4(Date prolongacion4) { this.prolongacion4 = prolongacion4; }

    public String getParroquia() { return parroquia; }
    public void setParroquia(String parroquia) { this.parroquia = parroquia; }

    public String getSectorFilial() { return sectorFilial; }
    public void setSectorFilial(String sectorFilial) { this.sectorFilial = sectorFilial; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefonoContacto() { return telefonoContacto; }
    public void setTelefonoContacto(String telefonoContacto) { this.telefonoContacto = telefonoContacto; }

    public List<PersonaParticipante> getPersonas() { return personas; }
    public void setPersonas(List<PersonaParticipante> personas) { this.personas = personas; }

    public DatosVivienda getVivienda() { return vivienda; }
    public void setVivienda(DatosVivienda vivienda) { this.vivienda = vivienda; }

    public AsistenciaSolicitada getAsistencia() { return asistencia; }
    public void setAsistencia(AsistenciaSolicitada asistencia) { this.asistencia = asistencia; }

    public String getNombreEntrevistador() { return nombreEntrevistador; }
    public void setNombreEntrevistador(String nombreEntrevistador) { this.nombreEntrevistador = nombreEntrevistador; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}