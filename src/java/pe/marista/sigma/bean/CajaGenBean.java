/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import pe.marista.sigma.MaristaConstantes;

/**
 *
 * @author MS002
 */
public class CajaGenBean implements Serializable {

    private Integer idCajaGen;
    private UnidadNegocioBean uniNeg;//uniNeg --"uniNeg"    
    private CajaBean cajaBean;//idCaja -- "idCaja"
    private UsuarioBean usuarioBean;//usuario -- "usuario"
    private PersonalBean idSupervisa;//idPersona -"idSupervisa"
    private Integer anio;
    private Double aperturaSol;
    private Double aperturaSolAnt;
    private Double aperturaDolAnt;
    private Double aperturaDol;
    private Double ingresoSol;
    private Double ingresoDol;
    private Double egresoSol;
    private Double egresoDol;
    private Double saldoSol;
    private Double saldoDol;
    private Double diferenciaSol;
    private Double diferenciaDol;
    private Date fecApertura;
    private String fecAperturaAnio;
    private String fecAperturaMes;
    private String fecAperturaDia;
    private Date fecCierre;
    private Date fecDeposito;
    private String rucBanco;
    private String nombreBanco;
    private String rucBancoCongre;
    private String nombreBancoCongre;
    private String numCuentaSol;
    private String numCuentaDol;
    private String numOperacionSol;
    private String numOperacionDol;
    private Double montoDepositoSol;
    private Double montoDepositoDol;
    private String creaPor;
    private Date creaFecha;
    private Date modiPor;

    private Double ingresoPos1;
    private Double ingresoPos2;

    //congregacion
    private Double ingresoCongreEfectivoSol;
    private Double ingresoCongreEfectivoDol;
    private String numCuentaCongreSol;
    private String numCuentaCongreDol;
    private String numOperacionCongreSol;
    private String numOperacionCongreDol;
    private Double montoDepositoCongreSol;
    private Double montoDepositoCongreDol;

    private String fechaCierreVista;
    private String fechaCierreView;
    private String fechaAperturaView;
    private String fechaDepositoView;

    private Boolean ayudaBanco;//1=colegio 0=congre
    private Boolean verArqueo;
    private Boolean flgTipoCajaGen;

    private Date fechaInicio;
    private Date fechaFin;

    //TIPO DE CAJA GEN
    private CodigoBean tipoCajaGen;

    //FLG TIPO CAJA GEN
//    private Integer flgTipoCajaGen;

    public UnidadNegocioBean getUniNeg() {
        if (uniNeg == null) {
            uniNeg = new UnidadNegocioBean();
        }
        return uniNeg;
    }

    public void setUniNeg(UnidadNegocioBean uniNeg) {
        this.uniNeg = uniNeg;
    }

    public Integer getIdCajaGen() {
        return idCajaGen;
    }

    public void setIdCajaGen(Integer idCajaGen) {
        this.idCajaGen = idCajaGen;
    }

    public CajaBean getCajaBean() {
        if (cajaBean == null) {
            cajaBean = new CajaBean();
        }
        return cajaBean;
    }

    public void setCajaBean(CajaBean cajaBean) {
        this.cajaBean = cajaBean;
    }

    public UsuarioBean getUsuarioBean() {
        if (usuarioBean == null) {
            usuarioBean = new UsuarioBean();
        }
        return usuarioBean;
    }

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public PersonalBean getIdSupervisa() {
        if (idSupervisa == null) {
            idSupervisa = new PersonalBean();
        }
        return idSupervisa;
    }

    public void setIdSupervisa(PersonalBean idSupervisa) {
        this.idSupervisa = idSupervisa;
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

    public Double getIngresoSol() {
        if (ingresoSol == null) {
            ingresoSol = new Double("0.00");
        }
        return ingresoSol;
    }

    public void setIngresoSol(Double ingresoSol) {
        this.ingresoSol = ingresoSol;
    }

    public Double getIngresoDol() {
        if (ingresoDol == null) {
            ingresoDol = new Double("0.00");
        }
        return ingresoDol;
    }

    public void setIngresoDol(Double ingresoDol) {
        this.ingresoDol = ingresoDol;
    }

    public Double getEgresoSol() {
        if (egresoSol == null) {
            egresoSol = new Double("0.00");
        }
        return egresoSol;
    }

    public void setEgresoSol(Double egresoSol) {
        this.egresoSol = egresoSol;
    }

    public Double getEgresoDol() {
        if (egresoDol == null) {
            egresoDol = new Double("0.00");
        }
        return egresoDol;
    }

    public void setEgresoDol(Double egresoDol) {
        this.egresoDol = egresoDol;
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

    public Date getFecApertura() {
        return fecApertura;
    }

    public void setFecApertura(Date fecApertura) {
        this.fecApertura = fecApertura;
    }

    public Date getFecCierre() {
        return fecCierre;
    }

    public void setFecCierre(Date fecCierre) {
        this.fecCierre = fecCierre;
    }

    public Date getFecDeposito() {
        return fecDeposito;
    }

    public void setFecDeposito(Date fecDeposito) {
        this.fecDeposito = fecDeposito;
    }

    public String getRucBanco() {
        return rucBanco;
    }

    public void setRucBanco(String rucBanco) {
        this.rucBanco = rucBanco;
    }

    public String getNumCuentaSol() {
        return numCuentaSol;
    }

    public void setNumCuentaSol(String numCuentaSol) {
        this.numCuentaSol = numCuentaSol;
    }

    public String getNumCuentaDol() {
        return numCuentaDol;
    }

    public void setNumCuentaDol(String numCuentaDol) {
        this.numCuentaDol = numCuentaDol;
    }

    public Double getMontoDepositoSol() {
        return montoDepositoSol;
    }

    public void setMontoDepositoSol(Double montoDepositoSol) {
        this.montoDepositoSol = montoDepositoSol;
    }

    public Double getMontoDepositoDol() {
        return montoDepositoDol;
    }

    public void setMontoDepositoDol(Double montoDepositoDol) {
        this.montoDepositoDol = montoDepositoDol;
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

    public Date getModiPor() {
        return modiPor;
    }

    public void setModiPor(Date modiPor) {
        this.modiPor = modiPor;
    }

    public String getFecAperturaAnio() {
        return fecAperturaAnio;
    }

    public void setFecAperturaAnio(String fecAperturaAnio) {
        this.fecAperturaAnio = fecAperturaAnio;
    }

    public String getFecAperturaMes() {
        return fecAperturaMes;
    }

    public void setFecAperturaMes(String fecAperturaMes) {
        this.fecAperturaMes = fecAperturaMes;
    }

    public String getFecAperturaDia() {
        return fecAperturaDia;
    }

    public void setFecAperturaDia(String fecAperturaDia) {
        this.fecAperturaDia = fecAperturaDia;
    }

    public String getNumOperacionSol() {
        return numOperacionSol;
    }

    public void setNumOperacionSol(String numOperacionSol) {
        this.numOperacionSol = numOperacionSol;
    }

    public String getNumOperacionDol() {
        return numOperacionDol;
    }

    public void setNumOperacionDol(String numOperacionDol) {
        this.numOperacionDol = numOperacionDol;
    }

    public Double getIngresoPos1() {
        if (ingresoPos1 == null) {
            ingresoPos1 = new Double("0.00");
        }
        return ingresoPos1;
    }

    public void setIngresoPos1(Double ingresoPos1) {
        this.ingresoPos1 = ingresoPos1;
    }

    public Double getIngresoPos2() {
        if (ingresoPos2 == null) {
            ingresoPos2 = new Double("0.00");
        }
        return ingresoPos2;
    }

    public void setIngresoPos2(Double ingresoPos2) {
        this.ingresoPos2 = ingresoPos2;
    }

    //congregacion
    public Double getIngresoCongreEfectivoSol() {
        if (ingresoCongreEfectivoSol == null) {
            ingresoCongreEfectivoSol = new Double("0.00");
        }
        return ingresoCongreEfectivoSol;
    }

    public void setIngresoCongreEfectivoSol(Double ingresoCongreEfectivoSol) {
        this.ingresoCongreEfectivoSol = ingresoCongreEfectivoSol;
    }

    public Double getIngresoCongreEfectivoDol() {
        if (ingresoCongreEfectivoDol == null) {
            ingresoCongreEfectivoDol = new Double("0.00");
        }
        return ingresoCongreEfectivoDol;
    }

    public void setIngresoCongreEfectivoDol(Double ingresoCongreEfectivoDol) {
        this.ingresoCongreEfectivoDol = ingresoCongreEfectivoDol;
    }

    public String getNumCuentaCongreSol() {
        return numCuentaCongreSol;
    }

    public void setNumCuentaCongreSol(String numCuentaCongreSol) {
        this.numCuentaCongreSol = numCuentaCongreSol;
    }

    public String getNumOperacionCongreSol() {
        return numOperacionCongreSol;
    }

    public void setNumOperacionCongreSol(String numOperacionCongreSol) {
        this.numOperacionCongreSol = numOperacionCongreSol;
    }

    public String getNumOperacionCongreDol() {
        return numOperacionCongreDol;
    }

    public void setNumOperacionCongreDol(String numOperacionCongreDol) {
        this.numOperacionCongreDol = numOperacionCongreDol;
    }

    public Double getMontoDepositoCongreSol() {
        return montoDepositoCongreSol;
    }

    public void setMontoDepositoCongreSol(Double montoDepositoCongreSol) {
        this.montoDepositoCongreSol = montoDepositoCongreSol;
    }

    public Double getMontoDepositoCongreDol() {
        return montoDepositoCongreDol;
    }

    public void setMontoDepositoCongreDol(Double montoDepositoCongreDol) {
        this.montoDepositoCongreDol = montoDepositoCongreDol;
    }

    public String getNumCuentaCongreDol() {
        return numCuentaCongreDol;
    }

    public void setNumCuentaCongreDol(String numCuentaCongreDol) {
        this.numCuentaCongreDol = numCuentaCongreDol;
    }

    public String getRucBancoCongre() {
        return rucBancoCongre;
    }

    public void setRucBancoCongre(String rucBancoCongre) {
        this.rucBancoCongre = rucBancoCongre;
    }

    public Boolean getAyudaBanco() {
        return ayudaBanco;
    }

    public void setAyudaBanco(Boolean ayudaBanco) {
        this.ayudaBanco = ayudaBanco;
    }

    public String getNombreBanco() {
        return nombreBanco;
    }

    public void setNombreBanco(String nombreBanco) {
        this.nombreBanco = nombreBanco;
    }

    public String getNombreBancoCongre() {
        return nombreBancoCongre;
    }

    public void setNombreBancoCongre(String nombreBancoCongre) {
        this.nombreBancoCongre = nombreBancoCongre;
    }

    public String getFechaCierreVista() {
        if (fecCierre != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String date = sdf.format(fecCierre);
            fechaCierreVista = date;
        }
        if (fecCierre == null) {
            fechaCierreVista = MaristaConstantes.SIN_FEC_CIERRE;
        }
        return fechaCierreVista;

    }

    public void setFechaCierreVista(String fechaCierreVista) {
        this.fechaCierreVista = fechaCierreVista;
    }

    public Double getAperturaSolAnt() {
        return aperturaSolAnt;
    }

    public void setAperturaSolAnt(Double aperturaSolAnt) {
        this.aperturaSolAnt = aperturaSolAnt;
    }

    public Double getAperturaDolAnt() {
        return aperturaDolAnt;
    }

    public void setAperturaDolAnt(Double aperturaDolAnt) {
        this.aperturaDolAnt = aperturaDolAnt;
    }

    public Boolean getVerArqueo() {
        return verArqueo;
    }

    public void setVerArqueo(Boolean verArqueo) {
        this.verArqueo = verArqueo;
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

    public String getFechaCierreView() {
        return fechaCierreView;
    }

    public void setFechaCierreView(String fechaCierreView) {
        this.fechaCierreView = fechaCierreView;
    }

    public String getFechaAperturaView() {
        return fechaAperturaView;
    }

    public void setFechaAperturaView(String fechaAperturaView) {
        this.fechaAperturaView = fechaAperturaView;
    }

    public String getFechaDepositoView() {
        return fechaDepositoView;
    }

    public void setFechaDepositoView(String fechaDepositoView) {
        this.fechaDepositoView = fechaDepositoView;
    }

    public CodigoBean getTipoCajaGen() {
        if (tipoCajaGen == null) {
            tipoCajaGen = new CodigoBean();
        }
        return tipoCajaGen;
    }

    public void setTipoCajaGen(CodigoBean tipoCajaGen) {
        this.tipoCajaGen = tipoCajaGen;
    }
 
    public Boolean getFlgTipoCajaGen() {
        return flgTipoCajaGen;
    }

    public void setFlgTipoCajaGen(Boolean flgTipoCajaGen) {
        this.flgTipoCajaGen = flgTipoCajaGen;
    }
 
}
