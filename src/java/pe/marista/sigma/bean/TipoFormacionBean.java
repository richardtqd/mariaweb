
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;


public class TipoFormacionBean implements Serializable
{
    private Integer idTipoFormacion;
    private String nombre;
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
 
    public TipoFormacionBean(String nombre) {
      this.nombre = nombre;
    }
 
    public TipoFormacionBean(Integer idTipoFormacion, String nombre) {
        this.idTipoFormacion = idTipoFormacion;
        this.nombre = nombre;
    }
    
    public TipoFormacionBean() {
    }    
     
    public Integer getIdTipoFormacion() {
        return idTipoFormacion;
    }

    public void setIdTipoFormacion(Integer idTipoFormacion) {
        this.idTipoFormacion = idTipoFormacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(Date creaFecha) {
        this.creaFecha = creaFecha;
    }
 
}
