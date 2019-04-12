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
 * @author JC
 */
public class PaganteBean implements Serializable {

    private UnidadNegocioBean unidadNegocioBean;
    private String idPagante;
    private TipoPaganteBean tipoPaganteBean;
    private EventoBean eventoBean;
    private String nomPagante;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private Date modiFecha;
    private String modiVer;

    //CAMPOS DE UNION IDPAGANTE
    private PersonaBean personaBean;
    private EntidadBean entidadBean;
    private MatriculaBean matriculaBean;
    private PersonalBean personalBean;

    private Integer nroFicha;

    //AYUDA
    private String nroDoc;
    private String data;

    private String idPagantePru;
    private String idTipoPagantePru;

    private Integer idTipoEstado;
    private Integer idTipoModoPago;

    //CAMPOS GRAFO
    private BigDecimal monto;
    private String grafo;
    private Integer dato;

    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean == null) {
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public String getIdPagante() {
        return idPagante;
    }

    public void setIdPagante(String idPagante) {
        this.idPagante = idPagante;
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

    public EventoBean getEventoBean() {
        if (eventoBean == null) {
            eventoBean = new EventoBean();
        }
        return eventoBean;
    }

    public void setEventoBean(EventoBean eventoBean) {
        this.eventoBean = eventoBean;
    }

    public String getNomPagante() {
        return nomPagante;
    }

    public void setNomPagante(String nomPagante) {
        this.nomPagante = nomPagante;
    }

    public PersonaBean getPersonaBean() {
        if (personaBean == null) {
            personaBean = new PersonaBean();
        }
        return personaBean;
    }

    public void setPersonaBean(PersonaBean personaBean) {
        this.personaBean = personaBean;
    }

    public EntidadBean getEntidadBean() {
        if (entidadBean == null) {
            personaBean = new PersonaBean();
        }
        return entidadBean;
    }

    public void setEntidadBean(EntidadBean entidadBean) {
        this.entidadBean = entidadBean;
    }

    public MatriculaBean getMatriculaBean() {
        if (matriculaBean == null) {
            matriculaBean = new MatriculaBean();
        }
        return matriculaBean;
    }

    public void setMatriculaBean(MatriculaBean matriculaBean) {
        this.matriculaBean = matriculaBean;
    }

    public PersonalBean getPersonalBean() {
        if (personalBean == null) {
            personalBean = new PersonalBean();
        }
        return personalBean;
    }

    public void setPersonalBean(PersonalBean personalBean) {
        this.personalBean = personalBean;
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

    public String getNroDoc() {
        return nroDoc;
    }

    public void setNroDoc(String nroDoc) {
        this.nroDoc = nroDoc;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getNroFicha() {
        return nroFicha;
    }

    public void setNroFicha(Integer nroFicha) {
        this.nroFicha = nroFicha;
    }

    public String getIdPagantePru() {
        return idPagantePru;
    }

    public void setIdPagantePru(String idPagantePru) {
        this.idPagantePru = idPagantePru;
    }

    public String getIdTipoPagantePru() {
        return idTipoPagantePru;
    }

    public void setIdTipoPagantePru(String idTipoPagantePru) {
        this.idTipoPagantePru = idTipoPagantePru;
    }

    public Integer getIdTipoEstado() {
        return idTipoEstado;
    }

    public void setIdTipoEstado(Integer idTipoEstado) {
        this.idTipoEstado = idTipoEstado;
    }

    public Integer getIdTipoModoPago() {
        return idTipoModoPago;
    }

    public void setIdTipoModoPago(Integer idTipoModoPago) {
        this.idTipoModoPago = idTipoModoPago;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getGrafo() {
        return grafo;
    }

    public void setGrafo(String grafo) {
        this.grafo = grafo;
    }

    public Integer getDato() {
        return dato;
    }

    public void setDato(Integer dato) {
        this.dato = dato;
    }

}
