/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;
import pe.marista.sigma.MaristaConstantes;

/**
 *
 * @author Administrador
 */
public class EstudianteSeguroBean implements Serializable {

    private EstudianteBean estudianteBean;//idEstudiante 
    private Integer idEstudianteSeguro;
    private CodigoBean codigoBean;//idTipoSeguro
    private EntidadBean entidadBean;//ruc
    private String clinica;
    private int prioridad;
    private Integer codigo;
    private Integer anio;
    private boolean status;
    private Date fecIni;
    private Date fecFin;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private String modiVer;
    //ayuda
    private String prioridadVista;
    private String statusVista;

    public EstudianteSeguroBean() {
        this.prioridad=1;
    }

    
    
    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public Integer getIdEstudianteSeguro() {
        return idEstudianteSeguro;
    }

    public void setIdEstudianteSeguro(Integer idEstudianteSeguro) {
        this.idEstudianteSeguro = idEstudianteSeguro;
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

    public CodigoBean getCodigoBean() {
        if (codigoBean == null) {
            codigoBean = new CodigoBean();
        }
        return codigoBean;
    }

    public void setCodigoBean(CodigoBean codigoBean) {
        this.codigoBean = codigoBean;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getModiVer() {
        return modiVer;
    }

    public void setModiVer(String modiVer) {
        this.modiVer = modiVer;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public EntidadBean getEntidadBean() {
        if (entidadBean == null) {
            entidadBean = new EntidadBean();
        }
        return entidadBean;
    }

    public void setEntidadBean(EntidadBean entidadBean) {
        this.entidadBean = entidadBean;
    }

    public String getClinica() {
        return clinica;
    }

    public void setClinica(String clinica) {
        this.clinica = clinica;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public String getPrioridadVista() {
        if (prioridad == 1) {
            prioridadVista = "1";
        }
        if (prioridad == 2) {
            prioridadVista = "2";
        }
        if (prioridad == 3) {
            prioridadVista = "3";
        }
        if (prioridad == 4) {
            prioridadVista = "4";
        }
        if (prioridad == 5) {
            prioridadVista = "5";
        }
        return prioridadVista;
    }

    public void setPrioridadVista(String prioridadVista) {
        this.prioridadVista = prioridadVista;
    }

    public String getStatusVista() {
        if (status == true) {
            statusVista = MaristaConstantes.ESTADO_ACTIVO_DES;
        }
        if (status == false) {
            statusVista = MaristaConstantes.ESTADO_INACTIVO_DES;
        }
        return statusVista;
    }

    public void setStatusVista(String statusVista) {
        this.statusVista = statusVista;
    }

}
