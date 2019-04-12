package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;
import pe.marista.sigma.MaristaConstantes;

/**
 *
 * @author Administrador
 */
public class CuentaBancoBean implements Serializable {

    private UnidadNegocioBean unidadNegocioBean;//uniNeg
    private EntidadBean entidadBancoBean; //rucBanco
    private String numCuenta;//numCuenta
    private CodigoBean tipoMonedaBean;
    private CodigoBean tipoCuentaBancoBean;
    private String descripcion;
    private Date fechaApertura;
    private Date fechaCierre;
    private Boolean flgCobranza;//1=ing and 0=egre
    private Boolean flgEgreso;//si la cuenta es de ingreso.
    private Boolean flgCtaCongre;
    private String codUniNeg;
    private Integer ctaContBco;
    private Boolean status;
    private String creaPor;
    private String modiPor;
    private Date creaFecha;
    
    //ayuda
    private String flgCobranzaVista;
    private String flgEgresoVista;
  
    private String statusVista;

    public CuentaBancoBean() {
        this.flgCobranza=false;
        this.status=false; 
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

    public EntidadBean getEntidadBancoBean() {
        if (entidadBancoBean == null) {
            entidadBancoBean = new EntidadBean();
        }
        return entidadBancoBean;
    }

    public void setEntidadBancoBean(EntidadBean entidadBancoBean) {
        this.entidadBancoBean = entidadBancoBean;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public Date getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(Date creaFecha) {
        this.creaFecha = creaFecha;
    }

    public Boolean getFlgCobranza() {
        return flgCobranza;
    }

    public void setFlgCobranza(Boolean flgCobranza) {
        this.flgCobranza = flgCobranza;
    }

    public String getCodUniNeg() {
        return codUniNeg;
    }

    public void setCodUniNeg(String codUniNeg) {
        this.codUniNeg = codUniNeg;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getFlgCobranzaVista() {
        if(flgCobranza!=null){
            if (flgCobranza==true) {
                flgCobranzaVista=MaristaConstantes.SI;
            }
            if (flgCobranza==false) {
                flgCobranzaVista=MaristaConstantes.NO;
            }
        }
        return flgCobranzaVista;
    }

    public void setFlgCobranzaVista(String flgCobranzaVista) {
        this.flgCobranzaVista = flgCobranzaVista;
    }

    public String getStatusVista() {
          if(status!=null){
            if (status==true) {
                statusVista=MaristaConstantes.Activo;
            }
            if (status==false) {
                statusVista=MaristaConstantes.Inactivo;
            }
        }
        return statusVista;
    }

    public void setStatusVista(String statusVista) {
        this.statusVista = statusVista;
    }

    public CodigoBean getTipoCuentaBancoBean() {
        if (tipoCuentaBancoBean==null) {
            tipoCuentaBancoBean= new CodigoBean();
        }
        return tipoCuentaBancoBean;
    }

    public void setTipoCuentaBancoBean(CodigoBean tipoCuentaBancoBean) {
        this.tipoCuentaBancoBean = tipoCuentaBancoBean;
    }

    public Integer getCtaContBco() {
        return ctaContBco;
    }

    public void setCtaContBco(Integer ctaContBco) {
        this.ctaContBco = ctaContBco;
    }

    public String getNumCuenta() {
        return numCuenta;
    }

    public void setNumCuenta(String numCuenta) {
        this.numCuenta = numCuenta;
    }

    public Boolean getFlgCtaCongre() {
        return flgCtaCongre;
    }

    public void setFlgCtaCongre(Boolean flgCtaCongre) {
        this.flgCtaCongre = flgCtaCongre;
    }

    public Boolean getFlgEgreso() {
        return flgEgreso;
    }

    public void setFlgEgreso(Boolean flgEgreso) {
        this.flgEgreso = flgEgreso;
    }

    public String getFlgEgresoVista() {
        if(flgEgreso!=null){
            if (flgEgreso==true) {
                flgEgresoVista=MaristaConstantes.SI;
            }
            if (flgEgreso==false) {
                flgEgresoVista=MaristaConstantes.NO;
            }
        }
        return flgEgresoVista;
    }

    public void setFlgEgresoVista(String flgEgresoVista) {
        this.flgEgresoVista = flgEgresoVista;
    } 
}
