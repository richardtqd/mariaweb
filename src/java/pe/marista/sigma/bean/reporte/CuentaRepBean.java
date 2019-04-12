package pe.marista.sigma.bean.reporte;

import java.io.Serializable;

public class CuentaRepBean implements Serializable {

    private String nombreUniNeg;
    private String rucUniNeg;
    private String fecha;
    private String uniNeg;
    private String cuenta;
    private String nomCuenta;
    private String ejecutado;
    private String presupuestado;
    private String precuentad;
    private String monto;
    private String porcentaje;
    private String saldo;
    private String dato;
    private String nombreCr;
    private String cr;
    private String titulo;
    private String totalExec;
    private String totalPres;
    private String totalSaldo;
    private String porcentajeSald;
    private String porcentajeExec;

    //AYUDA
    private Integer cuentaIni;
    private Integer cuentaFin;
    private Integer flgFiltro;
    private Integer crDigit;
    private Integer crDigitFin;
    private Integer digit;
    private String montoPresVista;
    private String montoExecVista;
    private String montoSaldoVista;
    private Integer cuentaExec;
    private Integer anio;

    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getNomCuenta() {
        return nomCuenta;
    }

    public void setNomCuenta(String nomCuenta) {
        this.nomCuenta = nomCuenta;
    }

    public String getEjecutado() {
        return ejecutado;
    }

    public void setEjecutado(String ejecutado) {
        this.ejecutado = ejecutado;
    }

    public String getPresupuestado() {
        return presupuestado;
    }

    public void setPresupuestado(String presupuestado) {
        this.presupuestado = presupuestado;
    }

    public String getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(String porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public Integer getCuentaIni() {
        return cuentaIni;
    }

    public void setCuentaIni(Integer cuentaIni) {
        this.cuentaIni = cuentaIni;
    }

    public Integer getCuentaFin() {
        return cuentaFin;
    }

    public void setCuentaFin(Integer cuentaFin) {
        this.cuentaFin = cuentaFin;
    }

    public String getPrecuentad() {
        return precuentad;
    }

    public void setPrecuentad(String precuentad) {
        this.precuentad = precuentad;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public Integer getFlgFiltro() {
        return flgFiltro;
    }

    public void setFlgFiltro(Integer flgFiltro) {
        this.flgFiltro = flgFiltro;
    }

    public Integer getCrDigit() {
        return crDigit;
    }

    public void setCrDigit(Integer crDigit) {
        this.crDigit = crDigit;
    }

    public Integer getDigit() {
        return digit;
    }

    public void setDigit(Integer digit) {
        this.digit = digit;
    }

    public String getNombreCr() {
        return nombreCr;
    }

    public void setNombreCr(String nombreCr) {
        this.nombreCr = nombreCr;
    }

    public String getCr() {
        return cr;
    }

    public void setCr(String cr) {
        this.cr = cr;
    }

    public String getNombreUniNeg() {
        return nombreUniNeg;
    }

    public void setNombreUniNeg(String nombreUniNeg) {
        this.nombreUniNeg = nombreUniNeg;
    }

    public String getRucUniNeg() {
        return rucUniNeg;
    }

    public void setRucUniNeg(String rucUniNeg) {
        this.rucUniNeg = rucUniNeg;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTotalExec() {
        return totalExec;
    }

    public void setTotalExec(String totalExec) {
        this.totalExec = totalExec;
    }

    public String getTotalPres() {
        return totalPres;
    }

    public void setTotalPres(String totalPres) {
        this.totalPres = totalPres;
    }

    public String getPorcentajeSald() {
        return porcentajeSald;
    }

    public void setPorcentajeSald(String porcentajeSald) {
        this.porcentajeSald = porcentajeSald;
    }

    public String getPorcentajeExec() {
        return porcentajeExec;
    }

    public void setPorcentajeExec(String porcentajeExec) {
        this.porcentajeExec = porcentajeExec;
    }

    public Integer getCrDigitFin() {
        return crDigitFin;
    }

    public void setCrDigitFin(Integer crDigitFin) {
        this.crDigitFin = crDigitFin;
    }

    public String getMontoPresVista() {
        return montoPresVista;
    }

    public void setMontoPresVista(String montoPresVista) {
        this.montoPresVista = montoPresVista;
    }

    public String getMontoExecVista() {
        return montoExecVista;
    }

    public void setMontoExecVista(String montoExecVista) {
        this.montoExecVista = montoExecVista;
    }

    public String getMontoSaldoVista() {
        return montoSaldoVista;
    }

    public void setMontoSaldoVista(String montoSaldoVista) {
        this.montoSaldoVista = montoSaldoVista;
    }

    public String getTotalSaldo() {
        return totalSaldo;
    }

    public void setTotalSaldo(String totalSaldo) {
        this.totalSaldo = totalSaldo;
    }

    public Integer getCuentaExec() {
        return cuentaExec;
    }

    public void setCuentaExec(Integer cuentaExec) {
        this.cuentaExec = cuentaExec;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }
 
}
