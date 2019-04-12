/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.marista.sigma.bean;

import java.io.Serializable;

/**
 *
 * @author MS002
 */
public class ComprasBean implements Serializable
{
    private String nroOrden;
    private String Fec1;
    private String Fec2;
    private String concepto;
    private String cantidad;
    private Double precio;
    private String subTotal;
    private String igv;
    private String montoTotal;
    private String proveedor;
    private String stockMin;
    private String StockMax;
    private String StockActual;
    private String destino ;
    private String detalle;
    private String can1;
    private String valorUni1;
    private String valorTot1;
    private String can2;
    private String valorUni2;
    private String valorTot2;
    private String can3;
    private String valorUni3;
    private String valorTo3;
    private String tipoActFijo;
    private String marca;
    private String modelo;
    private String nroSerie;
    private String categ;
    private String clasif;
    private String uniNeg;
    private String unidadMedida;
    private PostulanteBean postulanteBean;

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }
   
    public String getNroOrden() {
        return nroOrden;
    }

    public void setNroOrden(String nroOrden) {
        this.nroOrden = nroOrden;
    }

    public String getFec1() {
        return Fec1;
    }

    public void setFec1(String Fec1) {
        this.Fec1 = Fec1;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public String getIgv() {
        return igv;
    }

    public void setIgv(String igv) {
        this.igv = igv;
    }

    public String getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(String montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getFec2() {
        return Fec2;
    }

    public void setFec2(String Fec2) {
        this.Fec2 = Fec2;
    }

    public ComprasBean() {
    }

    public ComprasBean(String nroOrden, String concepto, String cantidad, Double precio,String unidadMedida, String subTotal, String igv) {
        this.nroOrden = nroOrden;
        this.concepto = concepto;
        this.cantidad = cantidad;
        this.precio = precio;
        this.unidadMedida = unidadMedida;
        this.subTotal = subTotal;
        this.igv = igv;
    }

    public ComprasBean(String nroOrden, String Fec1, String montoTotal, String proveedor) {
        this.nroOrden = nroOrden;
        this.Fec1 = Fec1;
        this.montoTotal = montoTotal;
        this.proveedor = proveedor;
    }

    public String getStockMin() {
        return stockMin;
    }

    public void setStockMin(String stockMin) {
        this.stockMin = stockMin;
    }

    public String getStockMax() {
        return StockMax;
    }

    public void setStockMax(String StockMax) {
        this.StockMax = StockMax;
    }

    public String getStockActual() {
        return StockActual;
    }

    public void setStockActual(String StockActual) {
        this.StockActual = StockActual;
    }

    public ComprasBean(String nroOrden, String Fec1, String concepto,Double precio,String unidadMedidad, String proveedor, String stockMin, String StockMax, String StockActual) {
        this.nroOrden = nroOrden;
        this.Fec1 = Fec1;
        this.concepto = concepto;
        this.precio = precio;
        this.unidadMedida = unidadMedidad;
        this.proveedor = proveedor;
        this.stockMin = stockMin;
        this.StockMax = StockMax;
        this.StockActual = StockActual;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getCan1() {
        return can1;
    }

    public void setCan1(String can1) {
        this.can1 = can1;
    }

    public String getValorUni1() {
        return valorUni1;
    }

    public void setValorUni1(String valorUni1) {
        this.valorUni1 = valorUni1;
    }

    public String getValorTot1() {
        return valorTot1;
    }

    public void setValorTot1(String valorTot1) {
        this.valorTot1 = valorTot1;
    }

    public String getCan2() {
        return can2;
    }

    public void setCan2(String can2) {
        this.can2 = can2;
    }

    public String getValorUni2() {
        return valorUni2;
    }

    public void setValorUni2(String valorUni2) {
        this.valorUni2 = valorUni2;
    }

    public String getValorTot2() {
        return valorTot2;
    }

    public void setValorTot2(String valorTot2) {
        this.valorTot2 = valorTot2;
    }

    public String getCan3() {
        return can3;
    }

    public void setCan3(String can3) {
        this.can3 = can3;
    }

    public String getValorUni3() {
        return valorUni3;
    }

    public void setValorUni3(String valorUni3) {
        this.valorUni3 = valorUni3;
    }

    public String getValorTo3() {
        return valorTo3;
    }

    public void setValorTo3(String valorTo3) {
        this.valorTo3 = valorTo3;
    }

    public ComprasBean(String nroOrden, String Fec1,String concepto, String destino, String detalle, String can1, String valorUni1, String valorTot1, String can2, String valorUni2, String valorTot2, String can3, String valorUni3, String valorTo3) {
        this.nroOrden = nroOrden;
        this.Fec1 = Fec1;
        this.concepto = concepto;
        this.destino = destino;
        this.detalle = detalle;
        this.can1 = can1;
        this.valorUni1 = valorUni1;
        this.valorTot1 = valorTot1;
        this.can2 = can2;
        this.valorUni2 = valorUni2;
        this.valorTot2 = valorTot2;
        this.can3 = can3;
        this.valorUni3 = valorUni3;
        this.valorTo3 = valorTo3;
    }

    public String getTipoActFijo() {
        return tipoActFijo;
    }

    public void setTipoActFijo(String tipoActFijo) {
        this.tipoActFijo = tipoActFijo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getNroSerie() {
        return nroSerie;
    }

    public void setNroSerie(String nroSerie) {
        this.nroSerie = nroSerie;
    }

    public String getCateg() {
        return categ;
    }

    public void setCateg(String categ) {
        this.categ = categ;
    }

    public String getClasif() {
        return clasif;
    }

    public void setClasif(String clasif) {
        this.clasif = clasif;
    }

    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
    }

    public ComprasBean(String nroOrden, String Fec1, String montoTotal,  String marca, String modelo, String categ, String clasif, String uniNeg, String concepto) {
        this.nroOrden = nroOrden;
        this.Fec1 = Fec1;
        this.montoTotal = montoTotal;
       
        this.marca = marca;
        this.modelo = modelo;
        this.categ = categ;
        this.clasif = clasif;
        this.uniNeg = uniNeg;
        this.concepto = concepto;
    }

    public PostulanteBean getPostulanteBean() {
        return postulanteBean;
    }

    public void setPostulanteBean(PostulanteBean postulanteBean) {
        this.postulanteBean = postulanteBean;
    }
}
