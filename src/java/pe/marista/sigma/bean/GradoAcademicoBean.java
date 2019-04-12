

package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

public class GradoAcademicoBean implements Serializable
{
    private Integer idGradoAcademico;
    private String codigo;
    private String nombre; 
    private NivelAcademicoBean nivelAcademicoBean;
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

    public Integer getIdGradoAcademico() {
        return idGradoAcademico;
    }

    public void setIdGradoAcademico(Integer idGradoAcademico) {
        this.idGradoAcademico = idGradoAcademico;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public NivelAcademicoBean getNivelAcademicoBean() {
        if(nivelAcademicoBean == null)
        { nivelAcademicoBean = new NivelAcademicoBean(); }
        return nivelAcademicoBean;
    }

    public void setNivelAcademicoBean(NivelAcademicoBean nivelAcademicoBean) {
        this.nivelAcademicoBean = nivelAcademicoBean;
    }

    public Date getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(Date creaFecha) {
        this.creaFecha = creaFecha;
    }
 
}
