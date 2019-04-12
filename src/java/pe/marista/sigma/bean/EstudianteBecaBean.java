/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author MS002
 */
public class EstudianteBecaBean implements Serializable {

    private Integer idEstudianteBeca;
    private EstudianteBean estudianteBean;//idEstudiante
    private BecaBean becaBean;//idBeca
    private CodigoBean idTipoMotivoBeca;//idCodigo
    private Integer anio;
    private Date fechaBeca;
    private String motivoBeca;
    private String docBeca;
    private String creaPor;//creaPor
    private Date creaFecha;//fechaCrea
//    private String creadoPor;
//    private Date fechaCrea;
    private CronogramaPagoBean cronogramaPagoBean;
    private String uniNegUsu;
    private UnidadNegocioBean unidadNegocioBean;
    private BigDecimal descuento;

    //cambio
    private Integer mesFin;
    private String strMesIni;
    private String strMesFin;
    private Boolean status; 

    public CodigoBean getIdTipoMotivoBeca() {
        if (idTipoMotivoBeca == null) {
            idTipoMotivoBeca = new CodigoBean();
        }
        return idTipoMotivoBeca;
    }

    public void setIdTipoMotivoBeca(CodigoBean idTipoMotivoBeca) {
        this.idTipoMotivoBeca = idTipoMotivoBeca;
    }

    public Integer getIdEstudianteBeca() {
        return idEstudianteBeca;
    }

    public void setIdEstudianteBeca(Integer idEstudianteBeca) {
        this.idEstudianteBeca = idEstudianteBeca;
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

    public BecaBean getBecaBean() {
        if (becaBean == null) {
            becaBean = new BecaBean();
        }
        return becaBean;
    }

    public void setBecaBean(BecaBean becaBean) {
        this.becaBean = becaBean;
    }

    public Date getFechaBeca() {
        return fechaBeca;
    }

    public void setFechaBeca(Date fechaBeca) {
        this.fechaBeca = fechaBeca;
    }

    public String getMotivoBeca() {
        return motivoBeca;
    }

    public void setMotivoBeca(String motivoBeca) {
        this.motivoBeca = motivoBeca;
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

    public String getUniNegUsu() {
        return uniNegUsu;
    }

    public void setUniNegUsu(String uniNegUsu) {
        this.uniNegUsu = uniNegUsu;
    }

    public String getDocBeca() {
        return docBeca;
    }

    public void setDocBeca(String docBeca) {
        this.docBeca = docBeca;
    }

    public CronogramaPagoBean getCronogramaPagoBean() {
        if (cronogramaPagoBean == null) {
            cronogramaPagoBean = new CronogramaPagoBean();
        }
        return cronogramaPagoBean;
    }

    public void setCronogramaPagoBean(CronogramaPagoBean cronogramaPagoBean) {
        this.cronogramaPagoBean = cronogramaPagoBean;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }
//
//    public String getCreadoPor() {
//        return creadoPor;
//    }
//
//    public void setCreadoPor(String creadoPor) {
//        this.creadoPor = creadoPor;
//    }
//
//    public Date getFechaCrea() {
//        return fechaCrea;
//    }
//
//    public void setFechaCrea(Date fechaCrea) {
//        this.fechaCrea = fechaCrea;
//    }

    public UnidadNegocioBean getUnidadNegocioBean() {

        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public Integer getMesFin() {
        return mesFin;
    }

    public void setMesFin(Integer mesFin) {
        this.mesFin = mesFin;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public String getStrMesIni() {
        return strMesIni;
    }

    public void setStrMesIni(String strMesIni) {
        this.strMesIni = strMesIni;
    }

    public String getStrMesFin() {
        return strMesFin;
    }

    public void setStrMesFin(String strMesFin) {
        this.strMesFin = strMesFin;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
