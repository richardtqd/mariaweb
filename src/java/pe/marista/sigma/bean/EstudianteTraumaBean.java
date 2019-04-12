package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

public class EstudianteTraumaBean implements Serializable {

    private UnidadNegocioBean unidadNegocioBean;
    private Integer idEstudianteTrauma;
    private EstudianteBean estudianteBean;
    private Date fecha;
    private String suceso;
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
    
    public Integer getIdEstudianteTrauma() {
        return idEstudianteTrauma;
    }

    public void setIdEstudianteTrauma(Integer idEstudianteTrauma) {
        this.idEstudianteTrauma = idEstudianteTrauma;
    }

    public EstudianteBean getEstudianteBean() {
        if(estudianteBean==null){
            estudianteBean = new EstudianteBean();
        }
        return estudianteBean;
    }

    public void setEstudianteBean(EstudianteBean estudianteBean) {
        this.estudianteBean = estudianteBean;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getSuceso() {
        return suceso;
    }

    public void setSuceso(String suceso) {
        this.suceso = suceso;
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
