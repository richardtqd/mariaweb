package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Administrador
 */
public class ProcesoAdmisionBean implements Serializable{

    private Integer idProcesoAdmision;
    private String nombre;
    private Integer anio;
    private Date fecIni;
    private Date fecFin;

    public ProcesoAdmisionBean() {
    }

    public Integer getIdProcesoAdmision() {
        return idProcesoAdmision;
    }

    public void setIdProcesoAdmision(Integer idProcesoAdmision) {
        this.idProcesoAdmision = idProcesoAdmision;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

     public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
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

}
