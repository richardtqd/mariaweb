/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.util.Date;

/**
 *
 * @author Administrador
 */
public class DetRegistroCompraCRBean {

    private UnidadNegocioBean unidadNegocioBean;
    private RegistroCompraBean registroCompraBean;
    private DetRegistroCompraBean detRegistroCompraBean;
    private OrdenCompraBean ordenCompraBean;
    
    private CentroResponsabilidadBean centroResponsabilidadBean;
    private CodigoBean tipoDistribucion;
    private Double valor;
    private Double valorD;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    //NUEVO
    private FacturaCompraBean facturaCompraBean;

    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean==null) {
            unidadNegocioBean= new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public RegistroCompraBean getRegistroCompraBean() {
        if (registroCompraBean==null) {
            registroCompraBean= new RegistroCompraBean();
        }
        return registroCompraBean;
    }

    public void setRegistroCompraBean(RegistroCompraBean registroCompraBean) {
        this.registroCompraBean = registroCompraBean;
    }

    public DetRegistroCompraBean getDetRegistroCompraBean() {
        if (detRegistroCompraBean== null) {
            detRegistroCompraBean= new DetRegistroCompraBean();
        }
        return detRegistroCompraBean;
    }

    public void setDetRegistroCompraBean(DetRegistroCompraBean detRegistroCompraBean) {
        this.detRegistroCompraBean = detRegistroCompraBean;
    }

    public CentroResponsabilidadBean getCentroResponsabilidadBean() {
        if (centroResponsabilidadBean==null) {
            centroResponsabilidadBean= new CentroResponsabilidadBean();
        }
        return centroResponsabilidadBean;
    }

    public void setCentroResponsabilidadBean(CentroResponsabilidadBean centroResponsabilidadBean) {
        this.centroResponsabilidadBean = centroResponsabilidadBean;
    }

    public CodigoBean getTipoDistribucion() {
        if (tipoDistribucion==null) {
            tipoDistribucion= new CodigoBean();
        }
        return tipoDistribucion;
    }

    public void setTipoDistribucion(CodigoBean tipoDistribucion) {
        this.tipoDistribucion = tipoDistribucion;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getValorD() {
        return valorD;
    }

    public void setValorD(Double valorD) {
        this.valorD = valorD;
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

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public FacturaCompraBean getFacturaCompraBean() {
        if (facturaCompraBean ==null ) {
            facturaCompraBean = new FacturaCompraBean();
        }
        return facturaCompraBean;
    }

    public void setFacturaCompraBean(FacturaCompraBean facturaCompraBean) {
        this.facturaCompraBean = facturaCompraBean;
    }

    public OrdenCompraBean getOrdenCompraBean() {
        if (ordenCompraBean==null) {
            ordenCompraBean= new OrdenCompraBean();
        }
        return ordenCompraBean;
    }

    public void setOrdenCompraBean(OrdenCompraBean ordenCompraBean) {
        this.ordenCompraBean = ordenCompraBean;
    }

    
}
