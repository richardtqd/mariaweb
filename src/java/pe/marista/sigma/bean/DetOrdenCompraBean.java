
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

public class DetOrdenCompraBean implements Serializable{
    private UnidadNegocioBean unidadNegocioBean;
    private Integer idDetOrdenCompra;
    private OrdenCompraBean ordenCompraBean;
    private SolicitudBean solicitudBean;
    private Integer idDetRequerimiento;
    private CodigoBean tipoMonedaBean;
    private Integer idtipounimed;
    private Float cantidad;
    private Integer idtipomoneda;
    private Float importe;
    private CatalogoBean catalogoBean;
    private String creaPor;
    private Date creaFecha;

  

    public OrdenCompraBean getOrdenCompraBean() {
        return ordenCompraBean;
    }

    public void setOrdenCompraBean(OrdenCompraBean ordenCompraBean) {
        this.ordenCompraBean = ordenCompraBean;
    }

    public SolicitudBean getSolicitudBean() {
        return solicitudBean;
    }

    public void setSolicitudBean(SolicitudBean solicitudBean) {
        this.solicitudBean = solicitudBean;
    }

    public Integer getIdDetRequerimiento() {
        return idDetRequerimiento;
    }

    public void setIdDetRequerimiento(Integer idDetRequerimiento) {
        this.idDetRequerimiento = idDetRequerimiento;
    }

    public Integer getIdtipounimed() {
        return idtipounimed;
    }

    public void setIdtipounimed(Integer idtipounimed) {
        this.idtipounimed = idtipounimed;
    }

    public Float getCantidad() {
        return cantidad;
    }

    public void setCantidad(Float cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getIdtipomoneda() {
        return idtipomoneda;
    }

    public void setIdtipomoneda(Integer idtipomoneda) {
        this.idtipomoneda = idtipomoneda;
    }

    public Float getImporte() {
        return importe;
    }

    public void setImporte(Float importe) {
        this.importe = importe;
    }

    public CatalogoBean getCatalogoBean() {
        return catalogoBean;
    }

    public void setCatalogoBean(CatalogoBean catalogoBean) {
        this.catalogoBean = catalogoBean;
    }

    public String getCreapor() {
        return creaPor;
    }

    public void setCreapor(String creaPor) {
        this.creaPor = creaPor;
    }

    public Date getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(Date creaFecha) {
        this.creaFecha = creaFecha;
    }

    public UnidadNegocioBean getUnidadNegocioBean() {
        if(unidadNegocioBean==null)
        {
            unidadNegocioBean=new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public String getCreaPor() {
        return creaPor;
    }

    public void setCreaPor(String creaPor) {
        this.creaPor = creaPor;
    }

    public Integer getIdDetOrdenCompra() {
        return idDetOrdenCompra;
    }

    public void setIdDetOrdenCompra(Integer idDetOrdenCompra) {
        this.idDetOrdenCompra = idDetOrdenCompra;
    }

    public CodigoBean getTipoMonedaBean() {
        if (tipoMonedaBean==null) {
            tipoMonedaBean= new CodigoBean();
        }
        return tipoMonedaBean;
    }

    public void setTipoMonedaBean(CodigoBean tipoMonedaBean) {
        this.tipoMonedaBean = tipoMonedaBean;
    }

   
}
