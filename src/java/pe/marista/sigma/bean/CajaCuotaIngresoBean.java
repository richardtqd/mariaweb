package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

public class CajaCuotaIngresoBean implements Serializable{
    
    private UnidadNegocioBean unidadNegocioBean;
    private Integer idCajaCuotaIngreso;
    private String usuario;
    private Integer anio;
    private PersonalBean supervizaBean;
    private Date fechaApertura;
    private String  fechaAperturaView;
    private String fechaCierreView;
    private Date fechaCierre;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private Double ingresoEfectivoSol;
    private CuentaBancoBean numeroCuentaBean;
    private EntidadBean rucBancoCongregacionBean;

    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean==null) {
            unidadNegocioBean= new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public Integer getIdCajaCuotaIngreso() {
        return idCajaCuotaIngreso;
    }

    public void setIdCajaCuotaIngreso(Integer idCajaCuotaIngreso) {
        this.idCajaCuotaIngreso = idCajaCuotaIngreso;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public PersonalBean getSupervizaBean() {
        if (supervizaBean==null) {
            supervizaBean= new PersonalBean();
        }
        return supervizaBean;
    }

    public void setSupervizaBean(PersonalBean supervizaBean) {
        this.supervizaBean = supervizaBean;
    }

    public Date getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(Date fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
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

    public Double getIngresoEfectivoSol() {
        return ingresoEfectivoSol;
    }

    public void setIngresoEfectivoSol(Double ingresoEfectivoSol) {
        this.ingresoEfectivoSol = ingresoEfectivoSol;
    }

    public CuentaBancoBean getNumeroCuentaBean() {
        if (numeroCuentaBean==null) {
            numeroCuentaBean= new CuentaBancoBean();
        }
        return numeroCuentaBean;
    }

    public void setNumeroCuentaBean(CuentaBancoBean numeroCuentaBean) {
        this.numeroCuentaBean = numeroCuentaBean;
    }

    public EntidadBean getRucBancoCongregacionBean() {
        if (rucBancoCongregacionBean== null) {
            rucBancoCongregacionBean= new EntidadBean();
        }
        return rucBancoCongregacionBean;
    }

    public void setRucBancoCongregacionBean(EntidadBean rucBancoCongregacionBean) {
        this.rucBancoCongregacionBean = rucBancoCongregacionBean;
    }

    public String getFechaAperturaView() {
        return fechaAperturaView;
    }

    public void setFechaAperturaView(String fechaAperturaView) {
        this.fechaAperturaView = fechaAperturaView;
    }

    public String getFechaCierreView() {
        return fechaCierreView;
    }

    public void setFechaCierreView(String fechaCierreView) {
        this.fechaCierreView = fechaCierreView;
    }
    
    
}
