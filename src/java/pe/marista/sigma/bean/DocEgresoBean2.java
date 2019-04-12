/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author MS001
 */
public class DocEgresoBean2 implements Serializable {

    private Integer idDocEgreso;
    private String nroDoc;
    private String acreedor; // solicitante 
    private PersonalBean personalIdDestinatarioBean; // destinatario

    private CargoBean cargoBean; // idcargo
    private UnidadNegocioBean unidadNegocioBean;//idUniNeg
    private CentroCostoBean centroCostoBean; // idCentroCosto
    private CodigoBean codigoTipoDocEgresoBean;

    private Double monto; //monto a rendir, contra pago
    private Date fecDocEgreso;
    private Date fecVencimiento; //fecha a rendir, fecha contra pago   
    private String descripcion;

    //
    private String uniNeg;
    private String centroCosto;
    private String tipoDocEgreso;
    private String Fec1;
    private String Fec2;

    public CodigoBean getCodigoTipoDocEgresoBean() {
        if (codigoTipoDocEgresoBean == null) {
            codigoTipoDocEgresoBean = new CodigoBean();
        }
        return codigoTipoDocEgresoBean;
    }

    public void setCodigoTipoDocEgresoBean(CodigoBean codigoTipoDocEgresoBean) {
        this.codigoTipoDocEgresoBean = codigoTipoDocEgresoBean;
    }

    public PersonalBean getPersonalIdDestinatarioBean() {
        if (personalIdDestinatarioBean == null) {
            personalIdDestinatarioBean = new PersonalBean();
        }
        return personalIdDestinatarioBean;
    }

    public void setPersonalIdDestinatarioBean(PersonalBean personalIdDestinatarioBean) {
        this.personalIdDestinatarioBean = personalIdDestinatarioBean;
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

    public CentroCostoBean getCentroCostoBean() {
        return centroCostoBean;
    }

    public void setCentroCostoBean(CentroCostoBean centroCostoBean) {
        this.centroCostoBean = centroCostoBean;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public CargoBean getCargoBean() {
        return cargoBean;
    }

    public void setCargoBean(CargoBean cargoBean) {
        this.cargoBean = cargoBean;
    }

    public Integer getIdDocEgreso() {
        return idDocEgreso;
    }

    public void setIdDocEgreso(Integer idDocEgreso) {
        this.idDocEgreso = idDocEgreso;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Date getFecDocEgreso() {
        return fecDocEgreso;
    }

    public void setFecDocEgreso(Date fecDocEgreso) {
        this.fecDocEgreso = fecDocEgreso;
    }

    public String getNroDoc() {
        return nroDoc;
    }

    public void setNroDoc(String nroDoc) {
        this.nroDoc = nroDoc;
    }

    public String getAcreedor() {
        return acreedor;
    }

    public void setAcreedor(String acreedor) {
        this.acreedor = acreedor;
    }

    public Date getFecVencimiento() {
        return fecVencimiento;
    }

    public void setFecVencimiento(Date fecVencimiento) {
        this.fecVencimiento = fecVencimiento;
    }

    public DocEgresoBean2() {
    }

    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
    }

    public String getCentroCosto() {
        return centroCosto;
    }

    public void setCentroCosto(String centroCosto) {
        this.centroCosto = centroCosto;
    }

    public String getTipoDocEgreso() {
        return tipoDocEgreso;
    }

    public void setTipoDocEgreso(String tipoDocEgreso) {
        this.tipoDocEgreso = tipoDocEgreso;
    }

    public String getFec1() {
        return Fec1;
    }

    public void setFec1(String Fec1) {
        this.Fec1 = Fec1;
    }

    public String getFec2() {
        return Fec2;
    }

    public void setFec2(String Fec2) {
        this.Fec2 = Fec2;
    }

    public DocEgresoBean2(String nroDoc, String acreedor, Double monto, String uniNeg, String centroCosto, String tipoDocEgreso, String Fec1, String Fec2) {
        this.nroDoc = nroDoc;
        this.acreedor = acreedor;
        this.monto = monto;
        this.uniNeg = uniNeg;
        this.centroCosto = centroCosto;
        this.tipoDocEgreso = tipoDocEgreso;
        this.Fec1 = Fec1;
        this.Fec2 = Fec2;
    }

}
