
package pe.marista.sigma.bean.reporte;

import java.io.Serializable;

/**
 *
 * @author LIZBETH
 */
public class ReciboDevolucionRepBean implements Serializable{
    private String nombre;
    private String importe;
    private String fecha;
    private String tipoMoneda;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(String tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }
    
}
