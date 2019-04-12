package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

public class CarreraAreaBean implements Serializable{

    private Integer idCarreraArea;
    private String area;
    private GradoAcademicoBean gradoAcademicoBean;//id
    private String creaPor;
    private Date creaFecha;
    private Integer idNivelAcademico;//id
    

    public Integer getIdCarreraArea() {
        return idCarreraArea;
    }

    public void setIdCarreraArea(Integer idCarreraArea) {
        this.idCarreraArea = idCarreraArea;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public GradoAcademicoBean getGradoAcademicoBean() {
        if (gradoAcademicoBean == null) {
            gradoAcademicoBean = new GradoAcademicoBean();
        }
        return gradoAcademicoBean;
    }

    public void setGradoAcademicoBean(GradoAcademicoBean gradoAcademicoBean) {
        this.gradoAcademicoBean = gradoAcademicoBean;
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

    public Integer getIdNivelAcademico() {
        return idNivelAcademico;
    }

    public void setIdNivelAcademico(Integer idNivelAcademico) {
        this.idNivelAcademico = idNivelAcademico;
    }
 
    
    
}
