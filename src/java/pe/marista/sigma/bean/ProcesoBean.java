package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;
import pe.marista.sigma.MaristaConstantes;
import static pe.marista.sigma.MaristaConstantes.ESTADO_ACTIVO_DES;

/**
 *
 * @author Administrador
 */
public class ProcesoBean implements Serializable {

    private UnidadNegocioBean unidadNegocioBean;
    private Integer idProceso;
    private String descripcion;
    private Integer anio;
    private CodigoBean codigoBean;//idTipoProceso
    private Date fecIni;
    private Date fecFin;
    private int status;
    private boolean estado;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private String statusVista;

    //ayuda
    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public Integer getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(Integer idProceso) {
        this.idProceso = idProceso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean == null) {
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public CodigoBean getCodigoBean() {
        if (codigoBean == null) {
            codigoBean = new CodigoBean();
        }
        return codigoBean;
    }

    public void setCodigoBean(CodigoBean codigoBean) {
        this.codigoBean = codigoBean;
    }

    public Date getFecIni() {
        return fecIni;
    }

    public void setFecIni(Date fecIni) {
        this.fecIni = fecIni;
    }

    public Date getFecFin() {
        return fecFin;
    }

    public void setFecFin(Date fecFin) {
        this.fecFin = fecFin;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public boolean isEstado() {
        if (status == 0) {
            estado = false;
            return false;
        }
        if (status == 1) {
            estado = true;
            return true;
        }
        return estado;
    }

    public boolean getEstadoBoolean() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getStatusVista() {
        if (status == 0) {
            statusVista = MaristaConstantes.ESTADO_INACTIVO_DES; 
        }
        else {
            statusVista = MaristaConstantes.ESTADO_ACTIVO_DES;
        }

        return statusVista;
    }

    public void setStatusVista(String statusVista) {
        this.statusVista = statusVista;
    }

}
