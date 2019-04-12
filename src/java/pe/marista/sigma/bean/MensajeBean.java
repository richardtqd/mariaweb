package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;
import pe.marista.sigma.MaristaConstantes;

/**
 *
 * @author Administrador
 */
public class MensajeBean implements Serializable {

    private UnidadNegocioBean unidadNegocioBean;//uniNeg
    private Integer idMensaje;
    private PersonalBean personalBean;//idOwner
    private CodigoBean tipoStatusMensajeBean;//idTipoStatusMsj
    private PersonalBean solicitanteBean;//idSolicitante
    private String idSoli;//idSolicitante
    private PersonalBean gestorBean;//idGestor
    private String asunto;
    private Date fecMsje;
    private Date fecAccion;
    private CodigoBean tipoPrioridad;//idTipoPrioridad
    private String objeto;
    private Integer idObjeto;
    private String idObjetoStr;
    private Integer nivelAutoriza;
    private Boolean flgAutoriza;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;

    //ayuda solicitud 
    private Boolean flgAutoriza1;
    private Boolean flgAutoriza2;
    private Boolean flgAutoriza3;

    private Date fechaInicio;
    private Date fechaFin;
    private Integer mensajesRecibidos;
    private Integer mensajesAtendidos;

    private String flgAutorizaVista;
    private String nombreSolicitante;
    private String nombreRespCheque;
    private Double montoVista;
    private Double montoAprobadoVista;

    private String nivelAutorizaVista;
    private String objetoVista;
    private Integer flgPagado;

    private CodigoBean tipoModoPago;//idtipomodopago
    private String rucBanco;
    private String numCuenta;

    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean == null) {
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public Integer getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(Integer idMensaje) {
        this.idMensaje = idMensaje;
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

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public Date getFecMsje() {
        return fecMsje;
    }

    public void setFecMsje(Date fecMsje) {
        this.fecMsje = fecMsje;
    }

    public Date getFecAccion() {
        return fecAccion;
    }

    public void setFecAccion(Date fecAccion) {
        this.fecAccion = fecAccion;
    }

    public CodigoBean getTipoPrioridad() {
        if (tipoPrioridad == null) {
            tipoPrioridad = new CodigoBean();
        }
        return tipoPrioridad;
    }

    public void setTipoPrioridad(CodigoBean tipoPrioridad) {
        this.tipoPrioridad = tipoPrioridad;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public Integer getIdObjeto() {
        return idObjeto;
    }

    public void setIdObjeto(Integer idObjeto) {
        this.idObjeto = idObjeto;
    }

    public PersonalBean getSolicitanteBean() {
        if (solicitanteBean == null) {
            solicitanteBean = new PersonalBean();
        }
        return solicitanteBean;
    }

    public void setSolicitanteBean(PersonalBean solicitanteBean) {
        this.solicitanteBean = solicitanteBean;
    }

    public PersonalBean getGestorBean() {
        if (gestorBean == null) {
            gestorBean = new PersonalBean();
        }
        return gestorBean;
    }

    public void setGestorBean(PersonalBean gestorBean) {
        this.gestorBean = gestorBean;
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

    public CodigoBean getTipoStatusMensajeBean() {
        if (tipoStatusMensajeBean == null) {
            tipoStatusMensajeBean = new CodigoBean();
        }
        return tipoStatusMensajeBean;
    }

    public void setTipoStatusMensajeBean(CodigoBean tipoStatusMensajeBean) {
        this.tipoStatusMensajeBean = tipoStatusMensajeBean;
    }

    public Integer getNivelAutoriza() {
        return nivelAutoriza;
    }

    public void setNivelAutoriza(Integer nivelAutoriza) {
        this.nivelAutoriza = nivelAutoriza;
    }

    public Boolean getFlgAutoriza1() {
        return flgAutoriza1;
    }

    public void setFlgAutoriza1(Boolean flgAutoriza1) {
        this.flgAutoriza1 = flgAutoriza1;
    }

    public Boolean getFlgAutoriza2() {
        return flgAutoriza2;
    }

    public void setFlgAutoriza2(Boolean flgAutoriza2) {
        this.flgAutoriza2 = flgAutoriza2;
    }

    public Boolean getFlgAutoriza3() {
        return flgAutoriza3;
    }

    public void setFlgAutoriza3(Boolean flgAutoriza3) {
        this.flgAutoriza3 = flgAutoriza3;
    }

    public Boolean getFlgAutoriza() {
        return flgAutoriza;
    }

    public void setFlgAutoriza(Boolean flgAutoriza) {
        this.flgAutoriza = flgAutoriza;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getFlgAutorizaVista() {
        if (flgAutoriza != null) {
            if (flgAutoriza == true) {
                flgAutorizaVista = MaristaConstantes.COD_SOL_AUTORIZADO;
            }
            if (flgAutoriza == false) {
                flgAutorizaVista = MaristaConstantes.COD_SOL_NO_AUTORIZADO;
            }

        }
        return flgAutorizaVista;
    }

    public void setFlgAutorizaVista(String flgAutorizaVista) {
        this.flgAutorizaVista = flgAutorizaVista;
    }

    public Integer getMensajesRecibidos() {
        return mensajesRecibidos;
    }

    public void setMensajesRecibidos(Integer mensajesRecibidos) {
        this.mensajesRecibidos = mensajesRecibidos;
    }

    public Integer getMensajesAtendidos() {
        return mensajesAtendidos;
    }

    public void setMensajesAtendidos(Integer mensajesAtendidos) {
        this.mensajesAtendidos = mensajesAtendidos;
    }

    public String getNivelAutorizaVista() {
        if (nivelAutoriza != null) {
            if (nivelAutoriza == 1) {
                nivelAutorizaVista = MaristaConstantes.NIVEL_AUTO_1;
            } else if (nivelAutoriza == 2) {
                nivelAutorizaVista = MaristaConstantes.NIVEL_AUTO_2;
            } else {
                nivelAutorizaVista = MaristaConstantes.NIVEL_AUTO_3;
            }
        }
        return nivelAutorizaVista;
    }

    public void setNivelAutorizaVista(String nivelAutorizaVista) {
        this.nivelAutorizaVista = nivelAutorizaVista;
    }

    public String getObjetoVista() {
        if (objeto != null) {
            if (objeto.equals(MaristaConstantes.OBJ_SOL_CAJACH)) {
                objetoVista = MaristaConstantes.OBJ_SOL_CAJACH_VISTA;
            } else if (objeto.equals(MaristaConstantes.OBJ_SOL_REG_COMPRA)) {
                objetoVista = MaristaConstantes.OBJ_SOL_REG_COMPRA_VISTA;
            } else if (objeto.equals(MaristaConstantes.OBJ_SOL_FACT_COMPRA)) {
                objetoVista = MaristaConstantes.OBJ_FACT_COMPRA_VISTA;
            } else {
                objetoVista = MaristaConstantes.OBJ_SOL_LOG_VISTA;
            }
        }
        return objetoVista;
    }

    public void setObjetoVista(String objetoVista) {
        this.objetoVista = objetoVista;
    }

    public String getNombreSolicitante() {
        return nombreSolicitante;
    }

    public void setNombreSolicitante(String nombreSolicitante) {
        this.nombreSolicitante = nombreSolicitante;
    }

    public String getIdSoli() {
        return idSoli;
    }

    public void setIdSoli(String idSoli) {
        this.idSoli = idSoli;
    }

    public String getNombreRespCheque() {
        return nombreRespCheque;
    }

    public void setNombreRespCheque(String nombreRespCheque) {
        this.nombreRespCheque = nombreRespCheque;
    }

    public Double getMontoVista() {
        return montoVista;
    }

    public void setMontoVista(Double montoVista) {
        this.montoVista = montoVista;
    }

    public Double getMontoAprobadoVista() {
        return montoAprobadoVista;
    }

    public void setMontoAprobadoVista(Double montoAprobadoVista) {
        this.montoAprobadoVista = montoAprobadoVista;
    }

    public Integer getFlgPagado() {
        return flgPagado;
    }

    public void setFlgPagado(Integer flgPagado) {
        this.flgPagado = flgPagado;
    }

    public CodigoBean getTipoModoPago() {
        if (tipoModoPago == null) {
            tipoModoPago = new CodigoBean();
        }
        return tipoModoPago;
    }

    public void setTipoModoPago(CodigoBean tipoModoPago) {
        this.tipoModoPago = tipoModoPago;
    }
 
    public String getNumCuenta() {
        return numCuenta;
    }

    public void setNumCuenta(String numCuenta) {
        this.numCuenta = numCuenta;
    }

    public String getRucBanco() {
        return rucBanco;
    }

    public void setRucBanco(String rucBanco) {
        this.rucBanco = rucBanco;
    }

    public String getIdObjetoStr() {
        return idObjetoStr;
    }

    public void setIdObjetoStr(String idObjetoStr) {
        this.idObjetoStr = idObjetoStr;
    }

}
