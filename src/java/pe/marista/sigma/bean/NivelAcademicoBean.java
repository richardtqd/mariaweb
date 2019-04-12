
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;


public class NivelAcademicoBean implements Serializable
{
    private Integer idNivelAcademico;
    private String nombre;
    private TipoFormacionBean tipoFormacionBean;
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

    public NivelAcademicoBean() {
    }

    public NivelAcademicoBean(Integer idNivelAcademico, String nombre, TipoFormacionBean tipoFormacionBean) {
        this.idNivelAcademico = idNivelAcademico;
        this.nombre = nombre;
        this.tipoFormacionBean = tipoFormacionBean;
    }
   
    public Integer getIdNivelAcademico() {
        return idNivelAcademico;
    }

    public void setIdNivelAcademico(Integer idNivelAcademico) {
        this.idNivelAcademico = idNivelAcademico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoFormacionBean getTipoFormacionBean() {
        if(tipoFormacionBean == null)
        { tipoFormacionBean = new TipoFormacionBean(); 
        }
        return tipoFormacionBean;
    }

    public void setTipoFormacionBean(TipoFormacionBean tipoFormacionBean) {
        this.tipoFormacionBean = tipoFormacionBean;
    }

    public Date getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(Date creaFecha) {
        this.creaFecha = creaFecha;
    }
    
    
    
}
