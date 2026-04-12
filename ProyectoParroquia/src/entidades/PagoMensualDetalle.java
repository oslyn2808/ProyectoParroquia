package entidades;

import java.time.LocalDate;
import javax.persistence.Embeddable;

/**
 * Representa un conjunto de hasta tres pagos mensuales asociados
 * a un tipo de gasto dentro del Adendum.
 * 
 * Se utiliza como clase embebida (@Embeddable) para reutilizar estructura
 * y evitar duplicación de código (principio DRY).
 */
@Embeddable
public class PagoMensualDetalle {


    /** Fecha del primer monto */
    private LocalDate fecha1;

    /** Fecha del segundo monto */
    private LocalDate fecha2;

    /** Fecha del tercer monto */
    private LocalDate fecha3;

    public LocalDate getFecha1() {
        return fecha1;
    }

    public void setFecha1(LocalDate fecha1) {
        this.fecha1 = fecha1;
    }

    public LocalDate getFecha2() {
        return fecha2;
    }

    public void setFecha2(LocalDate fecha2) {
        this.fecha2 = fecha2;
    }


    public LocalDate getFecha3() {
        return fecha3;
    }

    public void setFecha3(LocalDate fecha3) {
        this.fecha3 = fecha3;
    }
}
