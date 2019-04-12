package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

public class FamiliarEstudianteBean implements Serializable {

    private EstudianteBean estudianteBean;//idEstudiante,personaBean.uniNeg
    private FamiliarBean familiarBean;//idFamiliar
    private CodigoBean tipoParentescoBean;//idTipoParentesco
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private Boolean status=false;
    
    private String dniEstudiante;
    private String dniFamiliar;

    public FamiliarEstudianteBean(EstudianteBean estudianteBean, FamiliarBean familiarBean, Date creaFecha) {
        this.estudianteBean = estudianteBean;
        this.familiarBean = familiarBean;
        this.creaFecha = creaFecha;
    }

    public FamiliarEstudianteBean() {
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

    public FamiliarBean getFamiliarBean() {
        if (familiarBean == null) {
            familiarBean = new FamiliarBean();
        }
        return familiarBean;
    }

    public void setFamiliarBean(FamiliarBean familiarBean) {
        this.familiarBean = familiarBean;
    }

    public CodigoBean getTipoParentescoBean() {
        if (tipoParentescoBean == null) {
            tipoParentescoBean = new CodigoBean();
        }
        return tipoParentescoBean;
    }

    public void setTipoParentescoBean(CodigoBean tipoParentescoBean) {
        this.tipoParentescoBean = tipoParentescoBean;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getDniEstudiante() {
        return dniEstudiante;
    }

    public void setDniEstudiante(String dniEstudiante) {
        this.dniEstudiante = dniEstudiante;
    }

    public String getDniFamiliar() {
        return dniFamiliar;
    }

    public void setDniFamiliar(String dniFamiliar) {
        this.dniFamiliar = dniFamiliar;
    }

   
    
    
}
