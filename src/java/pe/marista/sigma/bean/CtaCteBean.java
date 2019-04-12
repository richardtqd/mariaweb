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
public class CtaCteBean implements Serializable{
    
    private Integer idCtaCte;
    private ConceptoBean conceptoBean; // idConcepto
    private EstudianteBean estudianteBean; //idDiscente  
    private BigDecimal monto;
    private BigDecimal deuda;
    private BigDecimal mora;
    private BigDecimal interes;
    private Integer idTipoMoneda;
    private Integer idTipoDocProvision;
    private Integer nroDoc;
    private Date fechaVenc;
    private Date fecha;
    
    private BigDecimal subTotal; // Comodin
    private String concepto;

    public CtaCteBean() {
    }

    public CtaCteBean(String nombreEstudianteBean, String nombreConceptoBean, BigDecimal monto, BigDecimal deuda, BigDecimal mora, BigDecimal interes, Integer idTipoMoneda, Integer idTipoDocProvision, Integer nroDoc, Date fechaVenc) {
        EstudianteBean eb=new EstudianteBean();
//        eb.setNomEstudiante(nombreEstudianteBean);
        ConceptoBean cb=new ConceptoBean();
        cb.setNombre(nombreConceptoBean);
        this.estudianteBean = eb;
        this.conceptoBean = cb;
        this.monto = monto;
        this.deuda = deuda;
        this.mora = mora;
        this.interes = interes;
        this.idTipoMoneda = idTipoMoneda;
        this.idTipoDocProvision = idTipoDocProvision;
        this.nroDoc = nroDoc;
        this.fechaVenc = fechaVenc;
    }
    


    public CtaCteBean(Integer idCtaCte, BigDecimal monto, String concepto) {
        this.idCtaCte = idCtaCte;
        this.monto = monto;
        this.concepto = concepto; } 
        
    public Integer getIdCtaCte() {
        return idCtaCte;
    }

    public void setIdCtaCte(Integer idCtaCte) {
        this.idCtaCte = idCtaCte;
    }

    public EstudianteBean getEstudianteBean() {
        if(estudianteBean == null)
        {
            estudianteBean = new EstudianteBean();
        }
        return estudianteBean;
    }

    public void setEstudianteBean(EstudianteBean estudianteBean) {
        this.estudianteBean = estudianteBean;
    }

    public ConceptoBean getConceptoBean() {
        if(conceptoBean == null)
        {
            conceptoBean = new ConceptoBean();
        }
        return conceptoBean;
    }

    public void setConceptoBean(ConceptoBean conceptoBean) {
        this.conceptoBean = conceptoBean;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public BigDecimal getDeuda() {
        return deuda;
    }

    public void setDeuda(BigDecimal deuda) {
        this.deuda = deuda;
    }

    public BigDecimal getMora() {
        return mora;
    }

    public void setMora(BigDecimal mora) {
        this.mora = mora;
    }

    public BigDecimal getInteres() {
        return interes;
    }

    public void setInteres(BigDecimal interes) {
        this.interes = interes;
    }

    public String getTipoMonedaVista() {
        if (idTipoMoneda == 1) {
            return MaristaConstantes.CANAL_PAGO_BAN;
        }
        if (idTipoMoneda == 2) {
            return MaristaConstantes.CANAL_PAGO_CA;
        }
        return MaristaConstantes.SIN_ESTADO;
    }
    
    public Integer getIdTipoMoneda() {
        return idTipoMoneda;
    }

    public void setIdTipoMoneda(Integer idTipoMoneda) {
        this.idTipoMoneda = idTipoMoneda;
    }

    public Integer getIdTipoDocProvision() {
        return idTipoDocProvision;
    }

    public void setIdTipoDocProvision(Integer idTipoDocProvision) {
        this.idTipoDocProvision = idTipoDocProvision;
    }

    public Integer getNroDoc() {
        return nroDoc;
    }

    public void setNroDoc(Integer nroDoc) {
        this.nroDoc = nroDoc;
    }

    public Date getFechaVenc() {
        return fechaVenc;
    }

    public void setFechaVenc(Date fechaVenc) {
        this.fechaVenc = fechaVenc;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }
    
    
}
