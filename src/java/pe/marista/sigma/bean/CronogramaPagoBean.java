/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import pe.marista.sigma.MaristaConstantes;

/**
 *
 * @author MS002
 */
public class CronogramaPagoBean implements Serializable {

    private Integer idCronogramaPago;
    private UnidadNegocioBean unidadNegocioBean;
    private Integer anio;
    private Integer mes;
    private TipoConceptoBean tipoConceptoBean;
    private Date fechaVenc;
    private String nomMes;
    private BigDecimal tasaInteres;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private String mideVer;
    private Integer idTipoCodigo;
    private String uniNeg;
    private String fechaVencimiento;
    private String fecha;
    private String fechaInicial;
    private String desMes;

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Date getFechaVenc() {
        return fechaVenc;
    }

    public void setFechaVenc(Date fechaVenc) {
        this.fechaVenc = fechaVenc;
    }

    public TipoConceptoBean getTipoConceptoBean() {
        if (tipoConceptoBean == null) {
            tipoConceptoBean = new TipoConceptoBean();
        }
        return tipoConceptoBean;
    }

    public void setTipoConceptoBean(TipoConceptoBean tipoConceptoBean) {
        this.tipoConceptoBean = tipoConceptoBean;
    }

    public String getNomMes() {
        if (mes == 1) {
            nomMes = MaristaConstantes.ENERO;
            return nomMes;
        }
        if (mes == 2) {
            nomMes = MaristaConstantes.FEBRERO;
            return nomMes;
        }
        if (mes == 3) {
            nomMes = MaristaConstantes.MARZO;
            return nomMes;
        }
        if (mes == 4) {
            nomMes = MaristaConstantes.ABRIL;
            return nomMes;
        }
        if (mes == 5) {
            nomMes = MaristaConstantes.MAYO;
            return nomMes;
        }
        if (mes == 6) {
            nomMes = MaristaConstantes.JUNIO;
            return nomMes;
        }
        if (mes == 7) {
            nomMes = MaristaConstantes.JULIO;
            return nomMes;
        }
        if (mes == 8) {
            nomMes = MaristaConstantes.AGOSTO;
            return nomMes;
        }
        if (mes == 9) {
            nomMes = MaristaConstantes.SETIEMBRE;
            return nomMes;
        }
        if (mes == 10) {
            nomMes = MaristaConstantes.OCTUBRE;
            return nomMes;
        }
        if (mes == 11) {
            nomMes = MaristaConstantes.NOVIEMBRE;
            return nomMes;
        }
        if (mes == 12) {
            nomMes = MaristaConstantes.DICIEMBRE;
            return nomMes;
        }

        return nomMes;
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

    public BigDecimal getTasaInteres() {
        return tasaInteres;
    }

    public void setTasaInteres(BigDecimal tasaInteres) {
        this.tasaInteres = tasaInteres;
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

    public String getMideVer() {
        return mideVer;
    }

    public void setMideVer(String mideVer) {
        this.mideVer = mideVer;
    }

    public Integer getIdCronogramaPago() {
        return idCronogramaPago;
    }

    public void setIdCronogramaPago(Integer idCronogramaPago) {
        this.idCronogramaPago = idCronogramaPago;
    }

    public Integer getIdTipoCodigo() {
        return idTipoCodigo;
    }

    public void setIdTipoCodigo(Integer idTipoCodigo) {
        this.idTipoCodigo = idTipoCodigo;
    }

    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(String fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public String getDesMes() {
        return desMes;
    }

    public void setDesMes(String desMes) {
        this.desMes = desMes;
    }

}
