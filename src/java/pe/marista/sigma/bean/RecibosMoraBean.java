package pe.marista.sigma.bean;

import java.util.Date;

public class RecibosMoraBean {

    private Integer idRecibosMora;
    private UnidadNegocioBean unidadNegocioBean;  
    private EstudianteBean estudianteBean;
    private String serieMora;
    private Integer nroDocMora;
    private Integer mes;
    private Integer anio;
    private Double mora;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private Boolean flgImpresionMora;
    private Date fechaImpresionMora;
    private DocIngresoBean docIngresoBean;

    public Integer getIdRecibosMora() {
        return idRecibosMora;
    }

    public void setIdRecibosMora(Integer idRecibosMora) {
        this.idRecibosMora = idRecibosMora;
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
    
    public EstudianteBean getEstudianteBean() {
        if (estudianteBean == null) {
            estudianteBean = new EstudianteBean();
        }
        return estudianteBean;
    }

    public void setEstudianteBean(EstudianteBean estudianteBean) {
        this.estudianteBean = estudianteBean;
    }

    public String getSerieMora() {
        return serieMora;
    }

    public void setSerieMora(String serieMora) {
        this.serieMora = serieMora;
    }

    public Integer getNroDocMora() {
        return nroDocMora;
    }

    public void setNroDocMora(Integer nroDocMora) {
        this.nroDocMora = nroDocMora;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Double getMora() {
        return mora;
    }

    public void setMora(Double mora) {
        this.mora = mora;
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

    public Boolean getFlgImpresionMora() {
        return flgImpresionMora;
    }

    public void setFlgImpresionMora(Boolean flgImpresionMora) {
        this.flgImpresionMora = flgImpresionMora;
    }

    public Date getFechaImpresionMora() {
        return fechaImpresionMora;
    }

    public void setFechaImpresionMora(Date fechaImpresionMora) {
        this.fechaImpresionMora = fechaImpresionMora;
    }

    public DocIngresoBean getDocIngresoBean() {
        if (docIngresoBean==null) {
            docIngresoBean= new DocIngresoBean();
        }
        return docIngresoBean;
    }

    public void setDocIngresoBean(DocIngresoBean docIngresoBean) {
        this.docIngresoBean = docIngresoBean;
    }

}
