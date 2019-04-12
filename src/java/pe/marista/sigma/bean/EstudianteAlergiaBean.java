package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

public class EstudianteAlergiaBean implements Serializable {

    private UnidadNegocioBean unidadNegocioBean; 
    private Integer idEstudianteAlergia;
    private EstudianteBean estudianteBean;
    private CodigoBean tipoAlergiaBean;
    private String alergia;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private String modiVer;

    public String getCreaPor() {
        return creaPor;
    }

    public void setCreaPor(String creaPor) {
        this.creaPor = creaPor;
    } 
    
    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public Integer getIdEstudianteAlergia() {
        return idEstudianteAlergia;
    }

    public void setIdEstudianteAlergia(Integer idEstudianteAlergia) {
        this.idEstudianteAlergia = idEstudianteAlergia;
    }

    public EstudianteBean getEstudianteBean() {
        if (estudianteBean == null) {
            estudianteBean = new EstudianteBean();
        }
        return estudianteBean;
    }

    public void setEstudianteBean(EstudianteBean estudianteBean) {
        this.estudianteBean = estudianteBean;
    }

    public CodigoBean getTipoAlergiaBean() {
        if (tipoAlergiaBean == null) {
            tipoAlergiaBean = new CodigoBean();
        }
        return tipoAlergiaBean;
    }

    public void setTipoAlergiaBean(CodigoBean tipoAlergiaBean) {
        this.tipoAlergiaBean = tipoAlergiaBean;
    }

    public String getAlergia() {
        return alergia;
    }

    public void setAlergia(String alergia) {
        this.alergia = alergia;
    }

    public UnidadNegocioBean getUnidadNegocioBean() {
        if(unidadNegocioBean == null){
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public String getModiVer() {
        return modiVer;
    }

    public void setModiVer(String modiVer) {
        this.modiVer = modiVer;
    }

    public Date getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(Date creaFecha) {
        this.creaFecha = creaFecha;
    }
    
}
