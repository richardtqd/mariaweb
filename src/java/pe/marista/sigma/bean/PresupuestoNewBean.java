package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

public class PresupuestoNewBean implements Serializable {

    private Integer idPresupuesto;
    private UnidadNegocioBean unidadNegocioBean;
    private Integer anio;
    private Double presupuestoProg;
    private PlanContableBean planContableBean;
    private String tipoCuenta;
    private Boolean flgTieneCr;
    private CentroResponsabilidadBean centroResponsabilidadBean;
    private CodigoBean tipoMonedaBean;
    private String obs;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
//    private List<PlanContableBean> listaPlanContableBean;
    private Integer cuentaInicie;
    private Integer egreIng = 0;
    private Integer tipoFiltro;

    private Integer tipoReporte;
    private Date fechaInicio;
    private Date fechaFin;

    //ayuda   
    private Double presupuestoEje;
    private Double porcEje;
    private Double saldo;
    private Double porcSaldo;

    //Ayuda
    private String uniNeg;
    private Integer cr;
    private String nombreCr;
    private String tipocuenta;
    private Integer idipomoneda;
    private Integer cuenta;
    private String nombreCuenta;

    //Ayuda
    private Double presupuestoProgramadoVista = 0.00;
    private Double presupuestoEjeVista = 0.00;
    private Double porcSaldoVista = 0.00;
    private Double saldoVista = 0.00;
    private Double porcEjeVista = 0.00;

    public Integer getIdPresupuesto() {
        return idPresupuesto;
    }

    public void setIdPresupuesto(Integer idPresupuesto) {
        this.idPresupuesto = idPresupuesto;
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

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Double getPresupuestoProg() {
        return presupuestoProg;
    }

    public void setPresupuestoProg(Double presupuestoProg) {
        this.presupuestoProg = presupuestoProg;
    }

    public PlanContableBean getPlanContableBean() {
        if (planContableBean == null) {
            planContableBean = new PlanContableBean();
        }
        return planContableBean;
    }

    public void setPlanContableBean(PlanContableBean planContableBean) {
        this.planContableBean = planContableBean;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
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

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
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

    public CentroResponsabilidadBean getCentroResponsabilidadBean() {
        if (centroResponsabilidadBean == null) {
            centroResponsabilidadBean = new CentroResponsabilidadBean();
        }
        return centroResponsabilidadBean;
    }

    public void setCentroResponsabilidadBean(CentroResponsabilidadBean centroResponsabilidadBean) {
        this.centroResponsabilidadBean = centroResponsabilidadBean;
    }

//    public List<PlanContableBean> getListaPlanContableBean() {
//        if (listaPlanContableBean==null) {
//            listaPlanContableBean= new ArrayList<>();
//        }
//        return listaPlanContableBean;
//    }
//
//    public void setListaPlanContableBean(List<PlanContableBean> listaPlanContableBean) {
//        this.listaPlanContableBean = listaPlanContableBean;
//    }
    public Integer getCuentaInicie() {
        return cuentaInicie;
    }

    public void setCuentaInicie(Integer cuentaInicie) {
        this.cuentaInicie = cuentaInicie;
    }

    public Boolean getFlgTieneCr() {
        return flgTieneCr;
    }

    public void setFlgTieneCr(Boolean flgTieneCr) {
        this.flgTieneCr = flgTieneCr;
    }

    public Integer getEgreIng() {
        return egreIng;
    }

    public void setEgreIng(Integer egreIng) {
        this.egreIng = egreIng;
    }

    public Integer getTipoFiltro() {
        return tipoFiltro;
    }

    public void setTipoFiltro(Integer tipoFiltro) {
        this.tipoFiltro = tipoFiltro;
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

    public Integer getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(Integer tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    public Double getPresupuestoEje() {
        return presupuestoEje;
    }

    public void setPresupuestoEje(Double presupuestoEje) {
        this.presupuestoEje = presupuestoEje;
    }

    public Double getPorcEje() {
        return porcEje;
    }

    public void setPorcEje(Double porcEje) {
        this.porcEje = porcEje;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Double getPorcSaldo() {
        return porcSaldo;
    }

    public void setPorcSaldo(Double porcSaldo) {
        this.porcSaldo = porcSaldo;
    }

    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
    }

    public Integer getCr() {
        return cr;
    }

    public void setCr(Integer cr) {
        this.cr = cr;
    }

    public String getNombreCr() {
        return nombreCr;
    }

    public void setNombreCr(String nombreCr) {
        this.nombreCr = nombreCr;
    }

    public String getTipocuenta() {
        return tipocuenta;
    }

    public void setTipocuenta(String tipocuenta) {
        this.tipocuenta = tipocuenta;
    }

    public Integer getIdipomoneda() {
        return idipomoneda;
    }

    public void setIdipomoneda(Integer idipomoneda) {
        this.idipomoneda = idipomoneda;
    }

    public Integer getCuenta() {
        return cuenta;
    }

    public void setCuenta(Integer cuenta) {
        this.cuenta = cuenta;
    }

    public String getNombreCuenta() {
        return nombreCuenta;
    }

    public void setNombreCuenta(String nombreCuenta) {
        this.nombreCuenta = nombreCuenta;
    }

    public Double getPresupuestoProgramadoVista() {
        return presupuestoProgramadoVista;
    }

    public void setPresupuestoProgramadoVista(Double presupuestoProgramadoVista) {
        this.presupuestoProgramadoVista = presupuestoProgramadoVista;
    }

    public Double getPresupuestoEjeVista() {
        return presupuestoEjeVista;
    }

    public void setPresupuestoEjeVista(Double presupuestoEjeVista) {
        this.presupuestoEjeVista = presupuestoEjeVista;
    }

    public Double getPorcSaldoVista() {
        return porcSaldoVista;
    }

    public void setPorcSaldoVista(Double porcSaldoVista) {
        this.porcSaldoVista = porcSaldoVista;
    }

    public Double getSaldoVista() {
        return saldoVista;
    }

    public void setSaldoVista(Double saldoVista) {
        this.saldoVista = saldoVista;
    }

    public Double getPorcEjeVista() {
        return porcEjeVista;
    }

    public void setPorcEjeVista(Double porcEjeVista) {
        this.porcEjeVista = porcEjeVista;
    }

}
