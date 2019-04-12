package pe.marista.sigma.bean.reporte;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class PresupuestoUniOrgRepBean implements Serializable {

    private Integer idUniOrg;
    private BigDecimal importe;
    private String nombreUniOrg;
    private Integer numActividad;
    private Integer numSubActividad;
    private String fecha;
    private String hora;
    
    //Lista Detalle
    private JRBeanCollectionDataSource listaDetalle;

    public Integer getIdUniOrg() {
        return idUniOrg;
    }

    public void setIdUniOrg(Integer idUniOrg) {
        this.idUniOrg = idUniOrg;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public String getNombreUniOrg() {
        return nombreUniOrg;
    }

    public void setNombreUniOrg(String nombreUniOrg) {
        this.nombreUniOrg = nombreUniOrg;
    }

    public Integer getNumActividad() {
        return numActividad;
    }

    public void setNumActividad(Integer numActividad) {
        this.numActividad = numActividad;
    }

    public Integer getNumSubActividad() {
        return numSubActividad;
    }

    public void setNumSubActividad(Integer numSubActividad) {
        this.numSubActividad = numSubActividad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public JRBeanCollectionDataSource getListaDetalle() {
        return listaDetalle;
    }

    public void setListaDetalle(List<ActividadUniOrgRepBean> listaDetalle) {
        this.listaDetalle = new JRBeanCollectionDataSource(listaDetalle);
    }

}
