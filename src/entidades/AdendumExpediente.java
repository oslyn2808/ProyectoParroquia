package entidades;

import java.time.LocalDate;
import javax.persistence.*;

/**
 * Entidad que representa un Adendum asociado a una Entrevista.
 *
 * Extiende la información del expediente con:
 * - Información adicional
 * - Observaciones
 * - Recomendación de ayuda
 * - Registro estructurado de gastos mensuales
 *
 * Se utilizan objetos embebidos (@Embeddable) para representar
 * los pagos mensuales y evitar duplicación de estructura (DRY).
 */
	@NamedQuery(
	    name = "AdendumExpediente.findByFormulario",
	    query = "SELECT a FROM AdendumExpediente a WHERE a.formulario.idFormulario = :idFormulario"
	)
@Entity
@Table(name = "adendum_expediente")
public class AdendumExpediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_adendum")
    private int idAdendum;

    /** Fecha en que se registra el adendum */
    @Column(name = "fecha_adendum")
    private LocalDate fechaAdendum;

    /** Información adicional relevante del expediente */
    @Column(name = "informacion_adicional", columnDefinition = "TEXT")
    private String informacionAdicional;

    /** Observaciones generales del adendum */
    @Column(name = "observaciones_adicionales", columnDefinition = "TEXT")
    private String observaciones_adicionales;

    /** Recomendación de ayuda derivada del análisis */
    @Column(name = "recomendacion_ayuda", columnDefinition = "TEXT")
    private String recomendacionAyuda;

    /** Estado del adendum */
    @Column(name = "estado")
    private String estado;

    /**
     * Relación con el formulario (entrevista) base.
     * Un adendum pertenece a una entrevista.
     */
    @ManyToOne
    @JoinColumn(name = "id_formulario", nullable = false) //cambio realizado debido a la eliminaciond de la entidad Entrevista
    private Formulario formulario;

    /**
     * Bloques de gastos mensuales del adendum.
     * CAMBIO: No se usan montons, unicamente las fechas como en el PDF
     * Se usa @Embedded para reutilizar estructura.
     */

    /** FECHAS de electricidad */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "fecha1", column = @Column(name = "elec_fecha1")),
            @AttributeOverride(name = "fecha2", column = @Column(name = "elec_fecha2")),
            @AttributeOverride(name = "fecha3", column = @Column(name = "elec_fecha3"))
    })
    private PagoMensualDetalle electricidad;

    /** FECHAS de agua */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "fecha1", column = @Column(name = "agua_fecha1")),
            @AttributeOverride(name = "fecha2", column = @Column(name = "agua_fecha2")),
            @AttributeOverride(name = "fecha3", column = @Column(name = "agua_fecha3"))
    })
    private PagoMensualDetalle agua;

    /** FECHAS de teléfono */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "fecha1", column = @Column(name = "tel_fecha1")),
            @AttributeOverride(name = "fecha2", column = @Column(name = "tel_fecha2")),
            @AttributeOverride(name = "fecha3", column = @Column(name = "tel_fecha3"))
    })
    private PagoMensualDetalle telefono;

    /** FECHAS de internet */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "fecha1", column = @Column(name = "int_fecha1")),
            @AttributeOverride(name = "fecha2", column = @Column(name = "int_fecha2")),
            @AttributeOverride(name = "fecha3", column = @Column(name = "int_fecha3"))
    })
    private PagoMensualDetalle internet;

    /** FECHAS de cable */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "fecha1", column = @Column(name = "cab_fecha1")),
            @AttributeOverride(name = "fecha2", column = @Column(name = "cab_fecha2")),
            @AttributeOverride(name = "fecha3", column = @Column(name = "cab_fecha3"))
    })
    private PagoMensualDetalle cable;

    /** FECHAS de alquiler o préstamo */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "fecha1", column = @Column(name = "alq_fecha1")),
            @AttributeOverride(name = "fecha2", column = @Column(name = "alq_fecha2")),
            @AttributeOverride(name = "fecha3", column = @Column(name = "alq_fecha3"))
    })
    private PagoMensualDetalle alquilerPrestamo;

    /** FECHAS de alimentación */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "fecha1", column = @Column(name = "alim_fecha1")),
            @AttributeOverride(name = "fecha2", column = @Column(name = "alim_fecha2")),
            @AttributeOverride(name = "fecha3", column = @Column(name = "alim_fecha3"))
    })
    private PagoMensualDetalle alimentacion;

    /** FECHAS en medicamentos */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "fecha1", column = @Column(name = "med_fecha1")),
            @AttributeOverride(name = "fecha2", column = @Column(name = "med_fecha2")),
            @AttributeOverride(name = "fecha3", column = @Column(name = "med_fecha3"))
    })
    private PagoMensualDetalle medicamentoMensual;

    public AdendumExpediente() {
        this.electricidad = new PagoMensualDetalle();
        this.agua = new PagoMensualDetalle();
        this.telefono = new PagoMensualDetalle();
        this.internet = new PagoMensualDetalle();
        this.cable = new PagoMensualDetalle();
        this.alquilerPrestamo = new PagoMensualDetalle();
        this.alimentacion = new PagoMensualDetalle();
        this.medicamentoMensual = new PagoMensualDetalle();
    }

    public int getIdAdendum() {
        return idAdendum;
    }

    public void setIdAdendum(int idAdendum) {
        this.idAdendum = idAdendum;
    }

    public LocalDate getFechaAdendum() {
        return fechaAdendum;
    }

    public void setFechaAdendum(LocalDate fechaAdendum) {
        this.fechaAdendum = fechaAdendum;
    }

    public String getInformacionAdicional() {
        return informacionAdicional;
    }

    public void setInformacionAdicional(String informacionAdicional) {
        this.informacionAdicional = informacionAdicional;
    }

    public String getObservaciones_adicionales() {
        return observaciones_adicionales;
    }

    public void setObservaciones_adicionales(String observaciones_adicionales) {
        this.observaciones_adicionales = observaciones_adicionales;
    }

    public String getRecomendacionAyuda() {
        return recomendacionAyuda;
    }

    public void setRecomendacionAyuda(String recomendacionAyuda) {
        this.recomendacionAyuda = recomendacionAyuda;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public Formulario getFormulario() {
    	return formulario;
    }
    
    public void setFormulario(Formulario formulario) {
    	this.formulario = formulario;
    }

    // Agrega estos métodos al final de AdendumExpediente.java
    //ELECTRICIDAD
    public PagoMensualDetalle getElectricidad() {
        return electricidad;
    }
    public void setElectricidad(PagoMensualDetalle electricidad) {
        this.electricidad = electricidad;
    }
    
    //AGUA
    public PagoMensualDetalle getAgua() {
        return agua;
    }
    public void setAgua(PagoMensualDetalle agua) {
        this.agua = agua;
    }

    // TELÉFONO
    public PagoMensualDetalle getTelefono() {
        return telefono;
    }

    public void setTelefono(PagoMensualDetalle telefono) {
        this.telefono = telefono;
    }

    // INTERNET
    public PagoMensualDetalle getInternet() {
        return internet;
    }

    public void setInternet(PagoMensualDetalle internet) {
        this.internet = internet;
    }

    // CABLE
    public PagoMensualDetalle getCable() {
        return cable;
    }

    public void setCable(PagoMensualDetalle cable) {
        this.cable = cable;
    }

    // ALQUILER
    public PagoMensualDetalle getAlquilerPrestamo() {
        return alquilerPrestamo;
    }

    public void setAlquilerPrestamo(PagoMensualDetalle alquilerPrestamo) {
        this.alquilerPrestamo = alquilerPrestamo;
    }

    // ALIMENTACIÓN
    public PagoMensualDetalle getAlimentacion() {
        return alimentacion;
    }

    public void setAlimentacion(PagoMensualDetalle alimentacion) {
        this.alimentacion = alimentacion;
    }

    // MEDICAMENTOS
    public PagoMensualDetalle getMedicamentoMensual() {
        return medicamentoMensual;
    }

    public void setMedicamentoMensual(PagoMensualDetalle medicamentoMensual) {
        this.medicamentoMensual = medicamentoMensual;
    }
    
}