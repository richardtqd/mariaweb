package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author administrator
 */
public class DetSolicitudCajaChCRBean implements Serializable{

    private SolicitudCajaCHBean solicitudCajaCHBean;
    private CentroResponsabilidadBean centroResponsabilidadBean;
    private CodigoBean tipoDistribucion;
    private Double valor;
    private Double valorD;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;

    public SolicitudCajaCHBean getSolicitudCajaCHBean() {
        if(solicitudCajaCHBean==null){
            solicitudCajaCHBean = new SolicitudCajaCHBean();
        }
        return solicitudCajaCHBean;
    }

    public void setSolicitudCajaCHBean(SolicitudCajaCHBean solicitudCajaCHBean) {
        this.solicitudCajaCHBean = solicitudCajaCHBean;
    }

    public CentroResponsabilidadBean getCentroResponsabilidadBean() {
        if(centroResponsabilidadBean==null){
            centroResponsabilidadBean = new CentroResponsabilidadBean();
        }
        return centroResponsabilidadBean;
    }

    public void setCentroResponsabilidadBean(CentroResponsabilidadBean centroResponsabilidadBean) {
        this.centroResponsabilidadBean = centroResponsabilidadBean;
    }

    public CodigoBean getTipoDistribucion() {
        if(tipoDistribucion==null){
            tipoDistribucion = new CodigoBean();
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

    public Double getValorD() {
        return valorD;
    }

    public void setValorD(Double valorD) {
        this.valorD = valorD;
    }
    
}
