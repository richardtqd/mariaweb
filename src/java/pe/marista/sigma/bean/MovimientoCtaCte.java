/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.marista.sigma.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author MS002
 */
public class MovimientoCtaCte implements Serializable{
    
    private Integer idMovimientoCtaCte;
    private CtaCteBean ctaCteBean; // idCtaCte
    private BigDecimal monto;
    private DetDocIngresoBean detDocIngresoBean; // idDetDocIngreso

    public Integer getIdMovimientoCtaCte() {
        return idMovimientoCtaCte;
    }

    public void setIdMovimientoCtaCte(Integer idMovimientoCtaCte) {
        this.idMovimientoCtaCte = idMovimientoCtaCte;
    }

    public CtaCteBean getCtaCteBean() {
        if(ctaCteBean == null)
        {
            ctaCteBean = new CtaCteBean();
        }
        return ctaCteBean;
    }

    public void setCtaCteBean(CtaCteBean ctaCteBean) {
        this.ctaCteBean = ctaCteBean;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public DetDocIngresoBean getDetDocIngresoBean() {
        if(detDocIngresoBean == null)
        {
            detDocIngresoBean = new DetDocIngresoBean();
        }
        return detDocIngresoBean;
    }

    public void setDetDocIngresoBean(DetDocIngresoBean detDocIngresoBean) {
        this.detDocIngresoBean = detDocIngresoBean;
    }
    
}
