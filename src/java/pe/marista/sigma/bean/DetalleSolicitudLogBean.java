
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;


public class DetalleSolicitudLogBean implements Serializable
{
    private SolicitudLogisticoBean solicitudLogisticoBean;
    private Integer idDetRequerimiento;
    private CodigoBean tipoUnidadMedida;
    private CatalogoBean catalogoBean;
    private Double cantidadSolicitada;
    private Double cantidadEntregada;
    private Integer cuenta;
    private String creaPor;
    private Date creaFecha;

    public SolicitudLogisticoBean getSolicitudLogisticoBean() {
        return solicitudLogisticoBean;
    }

    public void setSolicitudLogisticoBean(SolicitudLogisticoBean solicitudLogisticoBean) {
        this.solicitudLogisticoBean = solicitudLogisticoBean;
    }

    public Integer getIdDetRequerimiento() {
        return idDetRequerimiento;
    }

    public void setIdDetRequerimiento(Integer idDetRequerimiento) {
        this.idDetRequerimiento = idDetRequerimiento;
    }

    public CodigoBean getTipoUnidadMedida() {
        return tipoUnidadMedida;
    }

    public void setTipoUnidadMedida(CodigoBean tipoUnidadMedida) {
        this.tipoUnidadMedida = tipoUnidadMedida;
    }

    public CatalogoBean getCatalogoBean() {
        return catalogoBean;
    }

    public void setCatalogoBean(CatalogoBean catalogoBean) {
        this.catalogoBean = catalogoBean;
    }

    public Double getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public void setCantidadSolicitada(Double cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }

    public Double getCantidadEntregada() {
        return cantidadEntregada;
    }

    public void setCantidadEntregada(Double cantidadEntregada) {
        this.cantidadEntregada = cantidadEntregada;
    }

    public Integer getCuenta() {
        return cuenta;
    }

    public void setCuenta(Integer cuenta) {
        this.cuenta = cuenta;
    }

    public String getCreaPor() {
        return creaPor;
    }

    public void setCreaPor(String creaPor) {
        this.creaPor = creaPor;
    }

    public Date getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(Date creaFecha) {
        this.creaFecha = creaFecha;
    }
    
    
}
