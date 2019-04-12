package pe.marista.sigma.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Administrador
 */
public class CajaChicaBean implements Serializable {

    private UnidadNegocioBean unidadNegocioBean;//uniNeg
    private Integer idCajaChica;
    private Integer anio;
    private PersonalBean personalCajeroBean;//idCajero
    private PersonalBean personalBean;//idSuperviza
//    private TipoCambioBean tipoCambioBean;//idTipoMoneda
    private Double aperturaSol = 0d;
    private Double aperturaDol = 0d;
    private Double utilizadoSol = 0d;
    private Double utilizadoDol = 0d;
    private Double saldoSol = 0d;
    private Double saldoDol = 0d;
    private Double diferenciaSol = 0d;
    private Double diferenciaDol = 0d;
    private Double montoMaxMovSol = 0d;
    private Double montoMaxMovDol = 0d;
    private Double devueltoSol = 0d;
    private Double devueltoDol = 0d;
    private Date fecApertura;
    private Date fecCierre;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private Boolean flgRendicionCajaCh;
    private SolicitudCajaCHBean solicitudCajaCHBean;//idSolRep
    private CodigoBean tipoCajaChica;

    //Filtro 
    private Date inicioFecApertura;
    private Date finFecApertura;
    private Date inicioFecCierre;
    private Date finFecCierre;
    private Double montoTotalSol;
    private Date fecCierreVista;

    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean == null) {
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public Integer getIdCajaChica() {
        return idCajaChica;
    }

    public void setIdCajaChica(Integer idCajaChica) {
        this.idCajaChica = idCajaChica;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
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

    public Date getFecApertura() {
        return fecApertura;
    }

    public String getFecAperturaVista() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return sdf.format(fecApertura);
    }

    public String getFecAperturaFormat() {
        String date = null;
        if (fecApertura != null) {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            date = formato.format(fecApertura);
        }
        return date;
    }

    public void setFecApertura(Date fecApertura) {
        this.fecApertura = fecApertura;
    }

    public Date getFecCierre() {
        return fecCierre;
    }

    public String getFecCierreVista() {
        if (fecCierre != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            return sdf.format(fecCierre);
        }
        return "";
    }

    public String getFecCierreFormat() {
        String date = null;
        if (fecApertura != null) {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            date = formato.format(fecCierre);
        }
        return date;
    }

    public void setFecCierre(Date fecCierre) {
        this.fecCierre = fecCierre;
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

    public PersonalBean getPersonalCajeroBean() {
        if (personalCajeroBean == null) {
            personalCajeroBean = new PersonalBean();
        }
        return personalCajeroBean;
    }

    public void setPersonalCajeroBean(PersonalBean personalCajeroBean) {
        this.personalCajeroBean = personalCajeroBean;
    }

    public Double getAperturaSol() {
        return aperturaSol;
    }

    public void setAperturaSol(Double aperturaSol) {
        this.aperturaSol = aperturaSol;
    }

    public Double getAperturaDol() {
        return aperturaDol;
    }

    public void setAperturaDol(Double aperturaDol) {
        this.aperturaDol = aperturaDol;
    }

    public Double getUtilizadoSol() {
        return utilizadoSol;
    }

    public void setUtilizadoSol(Double utilizadoSol) {
        this.utilizadoSol = utilizadoSol;
    }

    public Double getUtilizadoDol() {
        return utilizadoDol;
    }

    public void setUtilizadoDol(Double utilizadoDol) {
        this.utilizadoDol = utilizadoDol;
    }

    public Double getSaldoSol() {
        return saldoSol;
    }

    public void setSaldoSol(Double saldoSol) {
        this.saldoSol = saldoSol;
    }

    public Double getSaldoDol() {
        return saldoDol;
    }

    public void setSaldoDol(Double saldoDol) {
        this.saldoDol = saldoDol;
    }

    public Double getDiferenciaSol() {
        return diferenciaSol;
    }

    public void setDiferenciaSol(Double diferenciaSol) {
        this.diferenciaSol = diferenciaSol;
    }

    public Double getDiferenciaDol() {
        return diferenciaDol;
    }

    public void setDiferenciaDol(Double diferenciaDol) {
        this.diferenciaDol = diferenciaDol;
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

    public Date getInicioFecApertura() {
        return inicioFecApertura;
    }

    public void setInicioFecApertura(Date inicioFecApertura) {
        this.inicioFecApertura = inicioFecApertura;
    }

    public Date getFinFecApertura() {
        return finFecApertura;
    }

    public void setFinFecApertura(Date finFecApertura) {
        this.finFecApertura = finFecApertura;
    }

    public Date getInicioFecCierre() {
        return inicioFecCierre;
    }

    public void setInicioFecCierre(Date inicioFecCierre) {
        this.inicioFecCierre = inicioFecCierre;
    }

    public Date getFinFecCierre() {
        return finFecCierre;
    }

    public void setFinFecCierre(Date finFecCierre) {
        this.finFecCierre = finFecCierre;
    }

    public Double getDevueltoSol() {
        return devueltoSol;
    }

    public Double getMontoTotalSol() {
        return devueltoSol + aperturaSol;
    }

    public Double getMontoTotalDol() {
        return devueltoDol + aperturaDol;
    }

    public void setDevueltoSol(Double devueltoSol) {
        this.devueltoSol = devueltoSol;
    }

    public Double getDevueltoDol() {
        return devueltoDol;
    }

    public void setDevueltoDol(Double devueltoDol) {
        this.devueltoDol = devueltoDol;
    }

    public Boolean getFlgRendicionCajaCh() {
        return flgRendicionCajaCh;
    }

    public void setFlgRendicionCajaCh(Boolean flgRendicionCajaCh) {
        this.flgRendicionCajaCh = flgRendicionCajaCh;
    }

    public void setFecCierreVista(Date fecCierreVista) {
        this.fecCierreVista = fecCierreVista;
    }

    public SolicitudCajaCHBean getSolicitudCajaCHBean() {
        if (solicitudCajaCHBean == null) {
            solicitudCajaCHBean = new SolicitudCajaCHBean();
        }
        return solicitudCajaCHBean;
    }

    public void setSolicitudCajaCHBean(SolicitudCajaCHBean solicitudCajaCHBean) {
        this.solicitudCajaCHBean = solicitudCajaCHBean;
    }

    public CodigoBean getTipoCajaChica() {
        if (tipoCajaChica == null) {
            tipoCajaChica = new CodigoBean();
        }
        return tipoCajaChica;
    }

    public void setTipoCajaChica(CodigoBean tipoCajaChica) {
        this.tipoCajaChica = tipoCajaChica;
    }

}
