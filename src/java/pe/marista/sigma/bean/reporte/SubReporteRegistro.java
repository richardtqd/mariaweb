package pe.marista.sigma.bean.reporte;

import java.sql.Timestamp;

public class SubReporteRegistro {
    
    private Timestamp fechaemision;
    private Integer cantidadDetOrden;
    private String item;
    private Integer catalogo;
    private String tipoUniMed;

    public Timestamp getFechaemision() {
        return fechaemision;
    }

    public void setFechaemision(Timestamp fechaemision) {
        this.fechaemision = fechaemision;
    }

    public Integer getCantidadDetOrden() {
        return cantidadDetOrden;
    }

    public void setCantidadDetOrden(Integer cantidadDetOrden) {
        this.cantidadDetOrden = cantidadDetOrden;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Integer getCatalogo() {
        return catalogo;
    }

    public void setCatalogo(Integer catalogo) {
        this.catalogo = catalogo;
    }

    public String getTipoUniMed() {
        return tipoUniMed;
    }

    public void setTipoUniMed(String tipoUniMed) {
        this.tipoUniMed = tipoUniMed;
    }
    
}
