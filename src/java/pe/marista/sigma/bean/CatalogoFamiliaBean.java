
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;


public class CatalogoFamiliaBean implements Serializable
{
    private Integer idCatalogoFamilia;
    private String nombre;
    private String creaPor;
    private Date creaFecha;

    public Integer getIdCatalogoFamilia() {
        return idCatalogoFamilia;
    }

    public void setIdCatalogoFamilia(Integer idCatalogoFamilia) {
        this.idCatalogoFamilia = idCatalogoFamilia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
    
    
}
