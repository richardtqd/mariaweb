package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;
import pe.marista.sigma.MaristaConstantes;

public class LiquidacionBean implements Serializable{

    private DocEgresoBean2 idDocEgresoBean; // idDocEgreso
    private Integer idTipoCanalPago;
    private CodigoBean IdTipoDoc; // idTipoDoc
    private Date fecLiquidacion;
    private String concepto;
    private CodigoBean IdTipoUnidadMedida; // idtipoUnidadMedida
    private Integer cantidad;
    private Double preUnitario;
    private Double subTotal;
    
    private Double montoLiquidacion;
    
    private Double diferencia; //montoPagoEgreso - (menos) montoLiquidacion
    private String referencia;

    public CodigoBean getIdTipoDoc() {
        if (IdTipoDoc == null) {
            IdTipoDoc = new CodigoBean();
        }
        return IdTipoDoc;
    }
    
    public void setIdTipoDoc(CodigoBean IdTipoDoc) {
        this.IdTipoDoc = IdTipoDoc;
    }

    public CodigoBean getIdTipoUnidadMedida() {
        if (IdTipoUnidadMedida == null) {
            IdTipoUnidadMedida = new CodigoBean();
        }
        return IdTipoUnidadMedida;
    }

    public void setIdTipoUnidadMedida(CodigoBean IdTipoUnidadMedida) {
        this.IdTipoUnidadMedida = IdTipoUnidadMedida;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getDiferencia() {
        return diferencia;
    }

    public void setDiferencia(Double diferencia) {
        this.diferencia = diferencia;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Date getFecLiquidacion() {
        return fecLiquidacion;
    }

    public void setFecLiquidacion(Date fecLiquidacion) {
        this.fecLiquidacion = fecLiquidacion;
    }

    public Double getPreUnitario() {
        return preUnitario;
    }

    public void setPreUnitario(Double preUnitario) {
        this.preUnitario = preUnitario;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public DocEgresoBean2 getIdDocEgresoBean() {
        return idDocEgresoBean;
    }

    public void setIdDocEgresoBean(DocEgresoBean2 idDocEgresoBean) {
        this.idDocEgresoBean = idDocEgresoBean;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Double getMontoLiquidacion() {
        return montoLiquidacion;
    }

    public void setMontoLiquidacion(Double montoLiquidacion) {
        this.montoLiquidacion = montoLiquidacion;
    }

    public String getTipoCanalPagoVista() {
        if (idTipoCanalPago == 1) {
            return MaristaConstantes.CANAL_PAGO_BAN;
        }
        if (idTipoCanalPago == 2) {
            return MaristaConstantes.CANAL_PAGO_CA;
        }
        return MaristaConstantes.SIN_ESTADO;

    }

    public void setIdTipoCanalPago(Integer idTipoCanalPago) {
        this.idTipoCanalPago = idTipoCanalPago;
    }

}
