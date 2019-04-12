package pe.marista.sigma.bean;

import java.util.Date;

public class EventoTipoPaganteBean {

    private UnidadNegocioBean unidadNegocioBean;
    private TipoPaganteBean tipoPaganteBean;
    private EventoBean eventoBean;
    private Integer nroAsignaciones;
    private Integer nroIni;
    private Integer nroFin;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private Date modiFecha;
    private String modiVer;

    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean == null) {
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public TipoPaganteBean getTipoPaganteBean() {
        if (tipoPaganteBean == null) {
            tipoPaganteBean = new TipoPaganteBean();
        }
        return tipoPaganteBean;
    }

    public void setTipoPaganteBean(TipoPaganteBean tipoPaganteBean) {
        this.tipoPaganteBean = tipoPaganteBean;
    }

    public Integer getNroAsignaciones() {
        return nroAsignaciones;
    }

    public void setNroAsignaciones(Integer nroAsignaciones) {
        this.nroAsignaciones = nroAsignaciones;
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

    public Date getModiFecha() {
        return modiFecha;
    }

    public void setModiFecha(Date modiFecha) {
        this.modiFecha = modiFecha;
    }

    public String getModiVer() {
        return modiVer;
    }

    public void setModiVer(String modiVer) {
        this.modiVer = modiVer;
    }

    public EventoBean getEventoBean() {
        if (eventoBean == null) {
            eventoBean = new EventoBean();
        }
        return eventoBean;
    }

    public void setEventoBean(EventoBean eventoBean) {
        this.eventoBean = eventoBean;
    }

    public Integer getNroIni() {
        return nroIni;
    }

    public void setNroIni(Integer nroIni) {
        this.nroIni = nroIni;
    }

    public Integer getNroFin() {
        return nroFin;
    }

    public void setNroFin(Integer nroFin) {
        this.nroFin = nroFin;
    }

}
