/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author MS001
 */
public class DetProgramacionDsctoBean implements Serializable {

    private UnidadNegocioBean unidadNegocioBean;//uniNeg
//    private Integer idProgramacionDscto;
    private ProgramacionDsctoBean programacionDsctoBean;
    private ProgramacionBean programacionBean;
    private CodigoBean tipoValorBean;
    private Double valor;
    private Boolean status;
    private String creaPor;
    private Date creaFecha;
    private String modiPor; 
    
    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean == null) {
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    } 

    public ProgramacionBean getProgramacionBean() {
        if (programacionBean == null) {
            programacionBean = new ProgramacionBean();
        }
        return programacionBean;
    }

    public void setProgramacionBean(ProgramacionBean programacionBean) {
        this.programacionBean = programacionBean;
    }

    public CodigoBean getTipoValorBean() {
        if (tipoValorBean == null) {
            tipoValorBean = new CodigoBean();
        }
        return tipoValorBean;
    }

    public void setTipoValorBean(CodigoBean tipoValorBean) {
        this.tipoValorBean = tipoValorBean;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public ProgramacionDsctoBean getProgramacionDsctoBean() {
        if (programacionDsctoBean==null) {
            programacionDsctoBean= new ProgramacionDsctoBean();
        }
        return programacionDsctoBean;
    }

    public void setProgramacionDsctoBean(ProgramacionDsctoBean programacionDsctoBean) {
        this.programacionDsctoBean = programacionDsctoBean;
    }
  
}
