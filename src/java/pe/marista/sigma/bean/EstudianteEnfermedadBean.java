package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date; 

/**
 *
 * @author MS002
 */
public class EstudianteEnfermedadBean implements Serializable {

//    private Integer idEstudianteEnfermedad;
    private EstudianteBean estudianteBean;//idEstudiante
    private EnfermedadBean enfermedadBean;//idEmfermedad
    private Integer edadInicio;
    private Integer status;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;

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

    public EstudianteBean getEstudianteBean() {
        if (estudianteBean == null) {
            estudianteBean = new EstudianteBean();
        }
        return estudianteBean;
    }

    public void setEstudianteBean(EstudianteBean estudianteBean) {
        this.estudianteBean = estudianteBean;
    }

    public EnfermedadBean getEnfermedadBean() {
        if (enfermedadBean == null) {
            enfermedadBean = new EnfermedadBean();
        }
        return enfermedadBean;
    }

    public void setEnfermedadBean(EnfermedadBean enfermedadBean) {
        this.enfermedadBean = enfermedadBean;
    }

    public Integer getEdadInicio() {
        return edadInicio;
    }

    public void setEdadInicio(Integer edadInicio) {
        this.edadInicio = edadInicio;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(Date creaFecha) {
        this.creaFecha = creaFecha;
    }
}
