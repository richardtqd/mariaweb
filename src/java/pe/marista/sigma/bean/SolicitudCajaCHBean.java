package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import pe.marista.sigma.MaristaConstantes;

/**
 *
 * @author Administrador
 */
public class SolicitudCajaCHBean implements Serializable {

    private Integer idSolicitudCajaCh;//idSolicitudCajaCH 
    private UnidadNegocioBean unidadNegocioBean;//uniNeg 
    //idsolicitante
    private PersonalBean personalBean;//idPersonal
    private PersonaBean personaBean;//idPersona
    private EntidadBean entidadBean;//ruc 
    private String idTipoSolicitante;//PRO:PROVEEDOR,PER:PERSONA,COL:COLABORADOR-TRABAJADOR-PERSONAL
    private Integer idTipoSol;//PRO:PROVEEDOR,PER:PERSONA,COL:COLABORADOR-TRABAJADOR-PERSONAL
    private String idPersonalSol;//PRO:PROVEEDOR,PER:PERSONA,COL:COLABORADOR-TRABAJADOR-PERSONAL
    //ayuda
    private String nombreSolicitante;
    private String fechaSolView;
    private UniNegUniOrgBean uniNegUniOrgBean;//idUniOrg
    private ConceptoUniNegBean conceptoUniNegBean;//idConcepto
    private CodigoBean tipoMonedaBean;
    private Double monto;
    private Double montoAprobado;
    private Date fechaSol;
    private CentroResponsabilidadBean centroResponsabilidadBean;//cr
    private String motivo;
    private String obs;
    private CodigoBean tipoStatusSolCajaChBean; //idTipoStatusSolCajaCh
    private CodigoBean tipoPrioridadBean; //idTipoPrioridad

    private TipoSolicitudBean tipoSolicitudBean;
    //idAutoriza1
    private PersonalBean idAutorizaPer1Bean;
//    private UniNegUniOrgBean idAutorizaUO1Bean;
    private Boolean flgAutoriza1;
    private Date fecAutoriza1;
    //idAutoriza2
    private PersonalBean idAutorizaPer2Bean;
//    private UniNegUniOrgBean idAutorizaUO2Bean;
    private Boolean flgAutoriza2;
    private Date fecAutoriza2;
    //idAutoriza3
    private PersonalBean idAutorizaPer3Bean;
//    private UniNegUniOrgBean idAutorizaUO3Bean;
    private Boolean flgAutoriza3;
    private Date fecAutoriza3;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;

    private Integer nivelAutoriza;

    private String idTipoRespCheque;//PRO:PROVEEDOR,PER:PERSONA,COL:COLABORADOR-TRABAJADOR-PERSONAL
    private String idRespCheque;
    private String nomRespCheque;

    //ayuda
    private String idAutoriza1Vista;
    private String idAutoriza2Vista;
    private String idAutoriza3Vista;
    private String flgAutoriza1Vista;
    private String flgAutoriza2Vista;
    private String flgAutoriza3Vista;
    private String montoAprobadoVista;
    private String flgAutorizado1Vista;
    private String flgAutorizado2Vista;
    private String flgAutorizado3Vista;

    private Date fechaInicio;
    private Date fechaFin;

    private PersonalBean resChequeBean;

    //ayuda 
    private String objeto;
    private String montoAproVista;
    //CR Multi
    private List<DetSolicitudCajaChCRBean> listaDetSolicitudCajaChCRBean;
    private Integer codDistribucion;

    private Double montoMaxMovSol = 0d;
    private Double montoMaxMovDol = 0d;
    private Integer flgSoles;
    private Double monto2;
    //Ayuda Rendicion
    private CodigoBean tipoEstRend;
    private Double montoPagado;
    private Double sumaLiquidacion;
    private Double diferenciaAdevolver;
    //docegreso

    private DetraccionBean detraccionBean;
    private Double montoDetraccion;
    private Integer idSolCaja;
    private String glosaDoc;
    private String referencia;
    private CodigoBean tipoDocBean;
    private String docRefAyuda;//nrodoc doc egreso
    private String serie; //serio doc egreso

    //ayuda
    private Integer idTipoModoPago;
    private String codModoPago;
    private Double garantia;
    private String rucBanco;
    private String numCuenta;
    private List<CuentaBancoBean> listaCuentaBancoBean;
    private Double montoTotalSoles = 0.00;
    private Double montoTotalDolares = 0.00;

    private Boolean flgTieneCr = false;

    //Nota de Credito
    private Double dsctoNotCred;
    private String nroNotaCredito;

    public SolicitudCajaCHBean() {
//        this.fechaSol = new Date();
        CodigoBean cod = new CodigoBean();
        cod.setIdCodigo(MaristaConstantes.COD_SOLES);
        this.tipoMonedaBean = cod;
    }

    public Integer getIdSolicitudCajaCh() {
        return idSolicitudCajaCh;
    }

    public void setIdSolicitudCajaCh(Integer idSolicitudCajaCh) {
        this.idSolicitudCajaCh = idSolicitudCajaCh;
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

    public PersonalBean getPersonalBean() {
        if (personalBean == null) {
            personalBean = new PersonalBean();
        }
        return personalBean;
    }

    public void setPersonalBean(PersonalBean personalBean) {
        this.personalBean = personalBean;
    }

    public UniNegUniOrgBean getUniNegUniOrgBean() {
        if (uniNegUniOrgBean == null) {
            uniNegUniOrgBean = new UniNegUniOrgBean();
        }
        return uniNegUniOrgBean;
    }

    public void setUniNegUniOrgBean(UniNegUniOrgBean uniNegUniOrgBean) {
        this.uniNegUniOrgBean = uniNegUniOrgBean;
    }

    public ConceptoUniNegBean getConceptoUniNegBean() {
        if (conceptoUniNegBean == null) {
            conceptoUniNegBean = new ConceptoUniNegBean();
        }
        return conceptoUniNegBean;
    }

    public void setConceptoUniNegBean(ConceptoUniNegBean conceptoUniNegBean) {
        this.conceptoUniNegBean = conceptoUniNegBean;
    }

    public CodigoBean getTipoMonedaBean() {
        if (tipoMonedaBean == null) {
            tipoMonedaBean = new CodigoBean();
        }
        return tipoMonedaBean;
    }

    public void setTipoMonedaBean(CodigoBean tipoMonedaBean) {
        this.tipoMonedaBean = tipoMonedaBean;
    }

    public Date getFechaSol() {
        return fechaSol;
    }

    public void setFechaSol(Date fechaSol) {
        this.fechaSol = fechaSol;
    }

    public CentroResponsabilidadBean getCentroResponsabilidadBean() {
        if (centroResponsabilidadBean == null) {
            centroResponsabilidadBean = new CentroResponsabilidadBean();
        }
        return centroResponsabilidadBean;
    }

    public void setCentroResponsabilidadBean(CentroResponsabilidadBean centroResponsabilidadBean) {
        this.centroResponsabilidadBean = centroResponsabilidadBean;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public CodigoBean getTipoStatusSolCajaChBean() {
        if (tipoStatusSolCajaChBean == null) {
            tipoStatusSolCajaChBean = new CodigoBean();
        }
        return tipoStatusSolCajaChBean;
    }

    public void setTipoStatusSolCajaChBean(CodigoBean tipoStatusSolCajaChBean) {
        this.tipoStatusSolCajaChBean = tipoStatusSolCajaChBean;
    }

    public CodigoBean getTipoPrioridadBean() {
        if (tipoPrioridadBean == null) {
            tipoPrioridadBean = new CodigoBean();
        }
        return tipoPrioridadBean;
    }

    public void setTipoPrioridadBean(CodigoBean tipoPrioridadBean) {
        this.tipoPrioridadBean = tipoPrioridadBean;
    }

    public PersonalBean getIdAutorizaPer1Bean() {
        if (idAutorizaPer1Bean == null) {
            idAutorizaPer1Bean = new PersonalBean();
        }
        return idAutorizaPer1Bean;
    }

    public void setIdAutorizaPer1Bean(PersonalBean idAutorizaPer1Bean) {
        this.idAutorizaPer1Bean = idAutorizaPer1Bean;
    }

    public Boolean getFlgAutoriza1() {
        return flgAutoriza1;
    }

    public void setFlgAutoriza1(Boolean flgAutoriza1) {
        this.flgAutoriza1 = flgAutoriza1;
    }

    public PersonalBean getIdAutorizaPer2Bean() {
        if (idAutorizaPer2Bean == null) {
            idAutorizaPer2Bean = new PersonalBean();
        }
        return idAutorizaPer2Bean;
    }

    public void setIdAutorizaPer2Bean(PersonalBean idAutorizaPer2Bean) {
        this.idAutorizaPer2Bean = idAutorizaPer2Bean;
    }

    public Boolean getFlgAutoriza2() {
        return flgAutoriza2;
    }

    public void setFlgAutoriza2(Boolean flgAutoriza2) {
        this.flgAutoriza2 = flgAutoriza2;
    }

    public PersonalBean getIdAutorizaPer3Bean() {
        if (idAutorizaPer3Bean == null) {
            idAutorizaPer3Bean = new PersonalBean();
        }
        return idAutorizaPer3Bean;
    }

    public void setIdAutorizaPer3Bean(PersonalBean idAutorizaPer3Bean) {
        this.idAutorizaPer3Bean = idAutorizaPer3Bean;
    }

    public Boolean getFlgAutoriza3() {
        return flgAutoriza3;
    }

    public void setFlgAutoriza3(Boolean flgAutoriza3) {
        this.flgAutoriza3 = flgAutoriza3;
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

//    public UniNegUniOrgBean getIdAutorizaUO1Bean() {
//        if (idAutorizaUO1Bean == null) {
//            idAutorizaUO1Bean = new UniNegUniOrgBean();
//        }
//        return idAutorizaUO1Bean;
//    }
//
//    public void setIdAutorizaUO1Bean(UniNegUniOrgBean idAutorizaUO1Bean) {
//        this.idAutorizaUO1Bean = idAutorizaUO1Bean;
//    }
//
//    public UniNegUniOrgBean getIdAutorizaUO2Bean() {
//        if (idAutorizaUO2Bean == null) {
//            idAutorizaUO2Bean = new UniNegUniOrgBean();
//        }
//        return idAutorizaUO2Bean;
//    }
//    public void setIdAutorizaUO2Bean(UniNegUniOrgBean idAutorizaUO2Bean) {
//        this.idAutorizaUO2Bean = idAutorizaUO2Bean;
//    }
//
//    public UniNegUniOrgBean getIdAutorizaUO3Bean() {
//        if (idAutorizaUO3Bean == null) {
//            idAutorizaUO3Bean = new UniNegUniOrgBean();
//        }
//        return idAutorizaUO3Bean;
//    }
//
//    public void setIdAutorizaUO3Bean(UniNegUniOrgBean idAutorizaUO3Bean) {
//        this.idAutorizaUO3Bean = idAutorizaUO3Bean;
//    }
    public Date getFecAutoriza1() {
        return fecAutoriza1;
    }

    public void setFecAutoriza1(Date fecAutoriza1) {
        this.fecAutoriza1 = fecAutoriza1;
    }

    public Date getFecAutoriza2() {
        return fecAutoriza2;
    }

    public void setFecAutoriza2(Date fecAutoriza2) {
        this.fecAutoriza2 = fecAutoriza2;
    }

    public Date getFecAutoriza3() {
        return fecAutoriza3;
    }

    public void setFecAutoriza3(Date fecAutoriza3) {
        this.fecAutoriza3 = fecAutoriza3;
    }

    public TipoSolicitudBean getTipoSolicitudBean() {
        if (tipoSolicitudBean == null) {
            tipoSolicitudBean = new TipoSolicitudBean();
        }
        return tipoSolicitudBean;
    }

    public void setTipoSolicitudBean(TipoSolicitudBean tipoSolicitudBean) {
        this.tipoSolicitudBean = tipoSolicitudBean;
    }

    public String getIdAutoriza1Vista() {
        if (idAutorizaPer1Bean.getIdPersonal() != null) {
            idAutoriza1Vista = idAutorizaPer1Bean.getNombreCompleto();
        }
        if (idAutorizaPer1Bean.getIdPersonal() == null) {
            idAutoriza1Vista = MaristaConstantes.SIN_AUTORIZADOR;
        }
        return idAutoriza1Vista;
    }

    public void setIdAutoriza1Vista(String idAutoriza1Vista) {
        this.idAutoriza1Vista = idAutoriza1Vista;
    }

    public String getIdAutoriza2Vista() {
        if (idAutorizaPer2Bean.getIdPersonal() != null) {
            idAutoriza2Vista = idAutorizaPer2Bean.getNombreCompleto();
        }
        if (idAutorizaPer2Bean.getIdPersonal() == null) {
            idAutoriza2Vista = MaristaConstantes.SIN_AUTORIZADOR;
        }
        return idAutoriza2Vista;
    }

    public void setIdAutoriza2Vista(String idAutoriza2Vista) {
        this.idAutoriza2Vista = idAutoriza2Vista;
    }

    public String getIdAutoriza3Vista() {
        if (idAutorizaPer3Bean.getIdPersonal() != null) {
            idAutoriza3Vista = idAutorizaPer3Bean.getNombreCompleto();
        }
        if (idAutorizaPer3Bean.getIdPersonal() == null) {
            idAutoriza3Vista = MaristaConstantes.SIN_AUTORIZADOR;
        }
        return idAutoriza3Vista;
    }

    public void setIdAutoriza3Vista(String idAutoriza3Vista) {
        this.idAutoriza3Vista = idAutoriza3Vista;
    }

    public String getFlgAutoriza1Vista() {
        if (idAutorizaPer1Bean.getIdPersonal() == null) {
            flgAutoriza1Vista = MaristaConstantes.SIN_AUTORIZA;
        } else {
            if (flgAutoriza1 == null) {
                flgAutoriza1Vista = MaristaConstantes.COD_SOL_PENDIENTE;
            }
            if (flgAutoriza1 != null) {
                if (flgAutoriza1 == true || flgAutoriza1 == false) {
                    flgAutoriza1Vista = MaristaConstantes.COD_ATENTIDO;
                }
            }
        }
        return flgAutoriza1Vista;
    }

    public void setFlgAutoriza1Vista(String flgAutoriza1Vista) {
        this.flgAutoriza1Vista = flgAutoriza1Vista;
    }

    public String getFlgAutoriza2Vista() {
        if (idAutorizaPer2Bean.getIdPersonal() == null) {
            flgAutoriza2Vista = MaristaConstantes.SIN_AUTORIZA;
        } else {
            if (flgAutoriza2 == null) {
                flgAutoriza2Vista = MaristaConstantes.COD_SOL_PENDIENTE;
            }
            if (flgAutoriza2 != null) {
                if (flgAutoriza2 == true || flgAutoriza2 == false) {
                    flgAutoriza2Vista = MaristaConstantes.COD_ATENTIDO;
                }
            }
        }
        return flgAutoriza2Vista;
    }

    public void setFlgAutoriza2Vista(String flgAutoriza2Vista) {
        this.flgAutoriza2Vista = flgAutoriza2Vista;
    }

    public String getFlgAutoriza3Vista() {
        if (idAutorizaPer3Bean.getIdPersonal() == null) {
            flgAutoriza3Vista = MaristaConstantes.SIN_AUTORIZA;
        } else {
            if (flgAutoriza3 == null) {
                flgAutoriza3Vista = MaristaConstantes.COD_SOL_PENDIENTE;
            }
            if (flgAutoriza3 != null) {
                if (flgAutoriza3 == true || flgAutoriza3 == false) {
                    flgAutoriza3Vista = MaristaConstantes.COD_ATENTIDO;
                }
            }
        }
        return flgAutoriza3Vista;
    }

    public void setFlgAutoriza3Vista(String flgAutoriza3Vista) {
        this.flgAutoriza3Vista = flgAutoriza3Vista;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Double getMontoAprobado() {
        return montoAprobado;
    }

    public void setMontoAprobado(Double montoAprobado) {
        this.montoAprobado = montoAprobado;
    }

    public String getMontoAprobadoVista() {
        if (montoAprobado == null) {
            montoAprobadoVista = MaristaConstantes.SIN_AUTORIZA;
        }
        if (montoAprobado != null) {
            montoAprobadoVista = montoAprobado.toString();
        }
        return montoAprobadoVista;
    }

    public void setMontoAprobadoVista(String montoAprobadoVista) {
        this.montoAprobadoVista = montoAprobadoVista;
    }

    public Integer getNivelAutoriza() {
        return nivelAutoriza;
    }

    public void setNivelAutoriza(Integer nivelAutoriza) {
        this.nivelAutoriza = nivelAutoriza;
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

//    public String getIdAutoriza3Vista() {
//        if (idAutorizaPer3Bean.getIdPersonal() != null) {
//            idAutoriza3Vista = idAutorizaPer3Bean.getNombreCompleto();
//        }
//        if (idAutorizaPer3Bean.getIdPersonal() == null) {
//            idAutoriza3Vista = MaristaConstantes.SIN_AUTORIZADOR;
//        }
//        return idAutoriza3Vista;
//    }
    public String getFlgAutorizado1Vista() {
        if (flgAutoriza1 == null) {
            flgAutorizado1Vista = MaristaConstantes.COD_SOL_PENDIENTE;
        }
        if (flgAutoriza1 != null) {
            if (flgAutoriza1 == true) {
                flgAutorizado1Vista = MaristaConstantes.COD_SOL_AUTORIZADO;
            } else {
                flgAutorizado1Vista = MaristaConstantes.COD_SOL_NO_AUTORIZADO;
            }
        }
        return flgAutorizado1Vista;
    }

    public void setFlgAutorizado1Vista(String flgAutorizado1Vista) {
        this.flgAutorizado1Vista = flgAutorizado1Vista;
    }

    public String getFlgAutorizado2Vista() {
        if (flgAutoriza2 == null) {
            flgAutorizado2Vista = MaristaConstantes.COD_SOL_PENDIENTE;
        }
        if (flgAutoriza2 != null) {
            if (flgAutoriza2 == true) {
                flgAutorizado2Vista = MaristaConstantes.COD_SOL_AUTORIZADO;
            } else {
                flgAutorizado2Vista = MaristaConstantes.COD_SOL_NO_AUTORIZADO;
            }
        }
        return flgAutorizado2Vista;
    }

    public void setFlgAutorizado2Vista(String flgAutorizado2Vista) {
        this.flgAutorizado2Vista = flgAutorizado2Vista;
    }

    public String getFlgAutorizado3Vista() {
        if (flgAutoriza3 == null) {
            flgAutorizado3Vista = MaristaConstantes.COD_SOL_PENDIENTE;
        }
        if (flgAutoriza3 != null) {
            if (flgAutoriza3 == true) {
                flgAutorizado3Vista = MaristaConstantes.COD_SOL_AUTORIZADO;
            } else {
                flgAutorizado3Vista = MaristaConstantes.COD_SOL_NO_AUTORIZADO;
            }
        }
        return flgAutorizado3Vista;
    }

    public void setFlgAutorizado3Vista(String flgAutorizado3Vista) {
        this.flgAutorizado3Vista = flgAutorizado3Vista;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public PersonalBean getResChequeBean() {
        if (resChequeBean == null) {
            resChequeBean = new PersonalBean();
        }
        return resChequeBean;
    }

    public void setResChequeBean(PersonalBean resChequeBean) {
        this.resChequeBean = resChequeBean;
    }

    public List<DetSolicitudCajaChCRBean> getListaDetSolicitudCajaChCRBean() {
        if (listaDetSolicitudCajaChCRBean == null) {
            listaDetSolicitudCajaChCRBean = new ArrayList<>();
        }
        return listaDetSolicitudCajaChCRBean;
    }

    public void setListaDetSolicitudCajaChCRBean(List<DetSolicitudCajaChCRBean> listaDetSolicitudCajaChCRBean) {
        this.listaDetSolicitudCajaChCRBean = listaDetSolicitudCajaChCRBean;
    }

    public Integer getCodDistribucion() {
        return codDistribucion;
    }

    public void setCodDistribucion(Integer codDistribucion) {
        this.codDistribucion = codDistribucion;
    }

    public String getMontoAproVista() {
        if (montoAprobado != null) {
            montoAproVista = montoAprobado.toString();
        } else {
            montoAproVista = monto.toString();
        }
        return montoAproVista;
    }

    public void setMontoAproVista(String montoAproVista) {
        this.montoAproVista = montoAproVista;
    }

    public Double getMontoMaxMovSol() {
        return montoMaxMovSol;
    }

    public void setMontoMaxMovSol(Double montoMaxMovSol) {
        this.montoMaxMovSol = montoMaxMovSol;
    }

    public Double getMontoMaxMovDol() {
        return montoMaxMovDol;
    }

    public void setMontoMaxMovDol(Double montoMaxMovDol) {
        this.montoMaxMovDol = montoMaxMovDol;
    }

    public Integer getFlgSoles() {
        return flgSoles;
    }

    public void setFlgSoles(Integer flgSoles) {
        this.flgSoles = flgSoles;
    }

    public Double getMonto2() {
        return monto2;
    }

    public void setMonto2(Double monto2) {
        this.monto2 = monto2;
    }

    public CodigoBean getTipoEstRend() {
        if (tipoEstRend == null) {
            tipoEstRend = new CodigoBean();
        }
        return tipoEstRend;
    }

    public void setTipoEstRend(CodigoBean tipoEstRend) {
        this.tipoEstRend = tipoEstRend;
    }

    public Double getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(Double montoPagado) {
        this.montoPagado = montoPagado;
    }

    public Double getSumaLiquidacion() {
        return sumaLiquidacion;
    }

    public void setSumaLiquidacion(Double sumaLiquidacion) {
        this.sumaLiquidacion = sumaLiquidacion;
    }

    public Double getDiferenciaAdevolver() {
        return diferenciaAdevolver;
    }

    public void setDiferenciaAdevolver(Double diferenciaAdevolver) {
        this.diferenciaAdevolver = diferenciaAdevolver;
    }

    public String getIdTipoRespCheque() {
        return idTipoRespCheque;
    }

    public void setIdTipoRespCheque(String idTipoRespCheque) {
        this.idTipoRespCheque = idTipoRespCheque;
    }

    public String getIdRespCheque() {
        return idRespCheque;
    }

    public void setIdRespCheque(String idRespCheque) {
        this.idRespCheque = idRespCheque;
    }

    public String getNomRespCheque() {
        return nomRespCheque;
    }

    public void setNomRespCheque(String nomRespCheque) {
        this.nomRespCheque = nomRespCheque;
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
            entidadBean = new EntidadBean();
        }
        return entidadBean;
    }

    public void setEntidadBean(EntidadBean entidadBean) {
        this.entidadBean = entidadBean;
    }

    public String getIdTipoSolicitante() {
        return idTipoSolicitante;
    }

    public void setIdTipoSolicitante(String idTipoSolicitante) {
        this.idTipoSolicitante = idTipoSolicitante;
    }

    public String getNombreSolicitante() {
        return nombreSolicitante;
    }

    public void setNombreSolicitante(String nombreSolicitante) {
        this.nombreSolicitante = nombreSolicitante;
    }

    public Integer getIdTipoSol() {
        return idTipoSol;
    }

    public void setIdTipoSol(Integer idTipoSol) {
        this.idTipoSol = idTipoSol;
    }

    public String getIdPersonalSol() {
        return idPersonalSol;
    }

    public void setIdPersonalSol(String idPersonalSol) {
        this.idPersonalSol = idPersonalSol;
    }

    public String getDocRefAyuda() {
        return docRefAyuda;
    }

    public void setDocRefAyuda(String docRefAyuda) {
        this.docRefAyuda = docRefAyuda;
    }

    public DetraccionBean getDetraccionBean() {
        if (detraccionBean == null) {
            detraccionBean = new DetraccionBean();
        }
        return detraccionBean;
    }

    public void setDetraccionBean(DetraccionBean detraccionBean) {
        this.detraccionBean = detraccionBean;
    }

    public Double getMontoDetraccion() {
        return montoDetraccion;
    }

    public void setMontoDetraccion(Double montoDetraccion) {
        this.montoDetraccion = montoDetraccion;
    }

    public Integer getIdSolCaja() {
        return idSolCaja;
    }

    public void setIdSolCaja(Integer idSolCaja) {
        this.idSolCaja = idSolCaja;
    }

    public String getGlosaDoc() {
        return glosaDoc;
    }

    public void setGlosaDoc(String glosaDoc) {
        this.glosaDoc = glosaDoc;
    }

    public String getFechaSolView() {
        return fechaSolView;
    }

    public void setFechaSolView(String fechaSolView) {
        this.fechaSolView = fechaSolView;
    }

    public Integer getIdTipoModoPago() {
        return idTipoModoPago;
    }

    public void setIdTipoModoPago(Integer idTipoModoPago) {
        this.idTipoModoPago = idTipoModoPago;
    }

    public String getCodModoPago() {
        return codModoPago;
    }

    public void setCodModoPago(String codModoPago) {
        this.codModoPago = codModoPago;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public CodigoBean getTipoDocBean() {
        if (tipoDocBean == null) {
            tipoDocBean = new CodigoBean();
        }
        return tipoDocBean;
    }

    public void setTipoDocBean(CodigoBean tipoDocBean) {
        this.tipoDocBean = tipoDocBean;
    }

    public Double getGarantia() {
        return garantia;
    }

    public void setGarantia(Double garantia) {
        this.garantia = garantia;
    }

    public String getRucBanco() {
        return rucBanco;
    }

    public void setRucBanco(String rucBanco) {
        this.rucBanco = rucBanco;
    }

    public String getNumCuenta() {
        return numCuenta;
    }

    public void setNumCuenta(String numCuenta) {
        this.numCuenta = numCuenta;
    }

    public List<CuentaBancoBean> getListaCuentaBancoBean() {
        if (listaCuentaBancoBean == null) {
            listaCuentaBancoBean = new ArrayList<>();
        }
        return listaCuentaBancoBean;
    }

    public void setListaCuentaBancoBean(List<CuentaBancoBean> listaCuentaBancoBean) {
        this.listaCuentaBancoBean = listaCuentaBancoBean;
    }

    public Double getMontoTotalSoles() {
        return montoTotalSoles;
    }

    public void setMontoTotalSoles(Double montoTotalSoles) {
        this.montoTotalSoles = montoTotalSoles;
    }

    public Double getMontoTotalDolares() {
        return montoTotalDolares;
    }

    public void setMontoTotalDolares(Double montoTotalDolares) {
        this.montoTotalDolares = montoTotalDolares;
    }

    public Boolean getFlgTieneCr() {
        return flgTieneCr;
    }

    public void setFlgTieneCr(Boolean flgTieneCr) {
        this.flgTieneCr = flgTieneCr;
    }

    public Double getDsctoNotCred() {
        return dsctoNotCred;
    }

    public void setDsctoNotCred(Double dsctoNotCred) {
        this.dsctoNotCred = dsctoNotCred;
    }

    public String getNroNotaCredito() {
        return nroNotaCredito;
    }

    public void setNroNotaCredito(String nroNotaCredito) {
        this.nroNotaCredito = nroNotaCredito;
    }

}
