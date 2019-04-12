package pe.marista.sigma.bean.reporte;

import java.util.Date;

public class DetRegistroCompraRepBean {

    private String fecha;
    private Integer cantidadDetOrden;
    private String item;
    private Integer catalogo;
    private String tipoUniMed;
    private Double importe;

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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    } 

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

}
