package entidades;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Datos de vivienda embebidos en la tabla "formulario".
 * Al usar @Embeddable no se crea tabla separada —
 * las columnas quedan dentro del mismo registro del formulario.
 */
@Embeddable
public class DatosVivienda {

    @Column(name = "tipo_vivienda")
    private String tipoVivienda;

    @Column(name = "tenencia")
    private String tenencia;

    @Column(name = "condicion_vivienda")
    private String condicionVivienda;

    public DatosVivienda() {}

    public String getTipoVivienda() { return tipoVivienda; }
    public void setTipoVivienda(String tipoVivienda) { this.tipoVivienda = tipoVivienda; }

    public String getTenencia() { return tenencia; }
    public void setTenencia(String tenencia) { this.tenencia = tenencia; }

    public String getCondicionVivienda() { return condicionVivienda; }
    public void setCondicionVivienda(String condicionVivienda) { this.condicionVivienda = condicionVivienda; }
}