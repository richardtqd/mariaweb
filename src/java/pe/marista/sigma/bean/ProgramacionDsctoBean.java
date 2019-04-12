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
public class ProgramacionDsctoBean implements Serializable {

    private UnidadNegocioBean unidadNegocioBean;//uniNeg
    private Integer idProgramacionDscto;
    private CodigoBean tipoDsctoProgramacionBean;
    private String descripcion;
    private Integer anio;
    private Integer cantProgramaciones;
    private CodigoBean tipoValorBean;
    private Double valor;
    private Double valorUnitario;
    private Boolean status = Boolean.FALSE;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private Integer flgDelete;
    private Integer mes;
    private Boolean flgEstudiante;

    public ProgramacionDsctoBean() {
    }

    public ProgramacionDsctoBean(UnidadNegocioBean unidadNegocioBean, Integer idProgramacionDscto, CodigoBean tipoDsctoProgramacionBean, String descripcion, Integer anio, Integer cantProgramaciones, CodigoBean tipoValorBean, Double valor, Double valorUnitario, String creaPor, Date creaFecha, String modiPor, Integer flgDelete, Integer mes, Boolean flgEstudiante) {
        this.unidadNegocioBean = unidadNegocioBean;
        this.idProgramacionDscto = idProgramacionDscto;
        this.tipoDsctoProgramacionBean = tipoDsctoProgramacionBean;
        this.descripcion = descripcion;
        this.anio = anio;
        this.cantProgramaciones = cantProgramaciones;
        this.tipoValorBean = tipoValorBean;
        this.valor = valor;
        this.valorUnitario = valorUnitario;
        this.creaPor = creaPor;
        this.creaFecha = creaFecha;
        this.modiPor = modiPor;
        this.flgDelete = flgDelete;
        this.mes = mes;
        this.flgEstudiante = flgEstudiante;
    }
    

    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean == null) {
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public Integer getIdProgramacionDscto() {
        return idProgramacionDscto;
    }

    public void setIdProgramacionDscto(Integer idProgramacionDscto) {
        this.idProgramacionDscto = idProgramacionDscto;
    }

    public CodigoBean getTipoDsctoProgramacionBean() {
        if (tipoDsctoProgramacionBean == null) {
            tipoDsctoProgramacionBean = new CodigoBean();
        }
        return tipoDsctoProgramacionBean;
    }

    public void setTipoDsctoProgramacionBean(CodigoBean tipoDsctoProgramacionBean) {
        this.tipoDsctoProgramacionBean = tipoDsctoProgramacionBean;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getCantProgramaciones() {
        return cantProgramaciones;
    }

    public void setCantProgramaciones(Integer cantProgramaciones) {
        this.cantProgramaciones = cantProgramaciones;
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

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
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

    public Integer getFlgDelete() {
        return flgDelete;
    }

    public void setFlgDelete(Integer flgDelete) {
        this.flgDelete = flgDelete;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Boolean getFlgEstudiante() {
        return flgEstudiante;
    }

    public void setFlgEstudiante(Boolean flgEstudiante) {
        this.flgEstudiante = flgEstudiante;
    }

}
