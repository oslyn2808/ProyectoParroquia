package entidades;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Datos de asistencia solicitada, embebidos en "formulario".
 */
@Embeddable
public class AsistenciaSolicitada {

    @Column(name = "alimentos")       private Boolean alimentos;
    @Column(name = "higiene_limpieza")private Boolean higieneLimpieza;
    @Column(name = "indumentaria")    private Boolean indumentaria;
    @Column(name = "alquiler")        private Boolean alquiler;
    @Column(name = "servicios")       private Boolean servicios;
    @Column(name = "medicamentos")    private Boolean medicamentos;
    @Column(name = "aparatos_ortopedicos") private Boolean aparatosOrtopedicos;
    @Column(name = "otros_1")         private String otros1;
    @Column(name = "otros_2")         private String otros2;
    @Column(name = "modalidad")       private String modalidad;
    @Column(name = "frecuencia")      private String frecuencia;
    @Column(name = "duracion")        private String duracion;
    @Column(name = "valor")           private Double valor;

    public AsistenciaSolicitada() {}

    public Boolean getAlimentos() { return alimentos; }
    public void setAlimentos(Boolean alimentos) { this.alimentos = alimentos; }

    public Boolean getHigieneLimpieza() { return higieneLimpieza; }
    public void setHigieneLimpieza(Boolean higieneLimpieza) { this.higieneLimpieza = higieneLimpieza; }

    public Boolean getIndumentaria() { return indumentaria; }
    public void setIndumentaria(Boolean indumentaria) { this.indumentaria = indumentaria; }

    public Boolean getAlquiler() { return alquiler; }
    public void setAlquiler(Boolean alquiler) { this.alquiler = alquiler; }

    public Boolean getServicios() { return servicios; }
    public void setServicios(Boolean servicios) { this.servicios = servicios; }

    public Boolean getMedicamentos() { return medicamentos; }
    public void setMedicamentos(Boolean medicamentos) { this.medicamentos = medicamentos; }

    public Boolean getAparatosOrtopedicos() { return aparatosOrtopedicos; }
    public void setAparatosOrtopedicos(Boolean aparatosOrtopedicos) { this.aparatosOrtopedicos = aparatosOrtopedicos; }

    public String getOtros1() { return otros1; }
    public void setOtros1(String otros1) { this.otros1 = otros1; }

    public String getOtros2() { return otros2; }
    public void setOtros2(String otros2) { this.otros2 = otros2; }

    public String getModalidad() { return modalidad; }
    public void setModalidad(String modalidad) { this.modalidad = modalidad; }

    public String getFrecuencia() { return frecuencia; }
    public void setFrecuencia(String frecuencia) { this.frecuencia = frecuencia; }

    public String getDuracion() { return duracion; }
    public void setDuracion(String duracion) { this.duracion = duracion; }

    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }
}