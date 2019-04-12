package pe.marista.sigma.bean.reporte;

import java.io.Serializable;

public class NotiMasivaRepBean implements Serializable {

    private String uniNeg;
    private String codigo;
    private String monto;
    private String fechaPago;
    private String montoPalabras;
    private String concepto;
    private String anio;
    private String nomBanco;
    private String nombres;
    private String grado;
    private String referenciaCuenta;

    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getMontoPalabras() {
        return montoPalabras;
    }

    public void setMontoPalabras(String montoPalabras) {
        this.montoPalabras = montoPalabras;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getNomBanco() {
        return nomBanco;
    }

    public void setNomBanco(String nomBanco) {
        this.nomBanco = nomBanco;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getReferenciaCuenta() {
        return referenciaCuenta;
    }

    public void setReferenciaCuenta(String referenciaCuenta) {
        this.referenciaCuenta = referenciaCuenta;
    }

}
