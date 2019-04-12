

package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;


public class MotivoMovimientoBean implements Serializable
{
    private Integer idMovimientoMotivo;
    private String motivo;
    private CodigoBean tipoMovimientoActivoBean;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private String modiVer;
    private CodigoBean tipoDuracionBean;

    public Integer getIdMovimientoMotivo() {
        return idMovimientoMotivo;
    }

    public void setIdMovimientoMotivo(Integer idMovimientoMotivo) {
        this.idMovimientoMotivo = idMovimientoMotivo;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public CodigoBean getTipoMovimientoActivoBean() {
        if(tipoMovimientoActivoBean == null)
        { tipoMovimientoActivoBean = new CodigoBean(); }
        return tipoMovimientoActivoBean;
    }

    public void setTipoMovimientoActivoBean(CodigoBean tipoMovimientoActivoBean) {
        this.tipoMovimientoActivoBean = tipoMovimientoActivoBean;
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

    public String getModiVer() {
        return modiVer;
    }

    public void setModiVer(String modiVer) {
        this.modiVer = modiVer;
    }

    public CodigoBean getTipoDuracionBean() {
        if (tipoDuracionBean == null) {
            tipoDuracionBean= new CodigoBean();
        }
        return tipoDuracionBean;
    }

    public void setTipoDuracionBean(CodigoBean tipoDuracionBean) {
        this.tipoDuracionBean = tipoDuracionBean;
    }

   
    
    
}
