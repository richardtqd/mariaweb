package pe.marista.sigma.bean.reporte;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DocIngresoTallerRepBean implements Serializable {

    private String uniNeg;
    private Integer idPagoBanco;
    private String idDiscente;
    private String discente;
    private Integer idTipoDoc;
    private String tipoDoc;
    private Integer idTipoMoneda;
    private String tipoMoneda;
    private Integer idTipoModo;
    private String tipoModo;
    private Integer idTipoLugar;
    private String tipoLugar;
    private Integer idEstado;
    private String estado;
    private String montoPagado;
    private String fechaPago;
    private Date fechaIni;
    private Date fechaFin;
    private String nombreRecaudo;
    private String codRecaudo;
    private String referencia;

    //AYUDA
    private String obs;
    private BigDecimal monto;
    private BigDecimal dscto;
    private BigDecimal pagado;
    private String modiPor;
    private Boolean flgImpresion;
    private String serie;
    private Integer nroDoc;

    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
    }

    public String getIdDiscente() {
        return idDiscente;
    }

    public void setIdDiscente(String idDiscente) {
        this.idDiscente = idDiscente;
    }

    public String getDiscente() {
        return discente;
    }

    public void setDiscente(String discente) {
        this.discente = discente;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(String tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public String getTipoModo() {
        return tipoModo;
    }

    public void setTipoModo(String tipoModo) {
        this.tipoModo = tipoModo;
    }

    public String getTipoLugar() {
        return tipoLugar;
    }

    public void setTipoLugar(String tipoLugar) {
        this.tipoLugar = tipoLugar;
    }

    public String getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(String montoPagado) {
        this.montoPagado = montoPagado;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Integer getIdTipoDoc() {
        return idTipoDoc;
    }

    public void setIdTipoDoc(Integer idTipoDoc) {
        this.idTipoDoc = idTipoDoc;
    }

    public Integer getIdTipoMoneda() {
        return idTipoMoneda;
    }

    public void setIdTipoMoneda(Integer idTipoMoneda) {
        this.idTipoMoneda = idTipoMoneda;
    }

    public Integer getIdTipoModo() {
        return idTipoModo;
    }

    public void setIdTipoModo(Integer idTipoModo) {
        this.idTipoModo = idTipoModo;
    }

    public Integer getIdTipoLugar() {
        return idTipoLugar;
    }

    public void setIdTipoLugar(Integer idTipoLugar) {
        this.idTipoLugar = idTipoLugar;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(Date fechaIni) {
        this.fechaIni = fechaIni;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getIdPagoBanco() {
        return idPagoBanco;
    }

    public void setIdPagoBanco(Integer idPagoBanco) {
        this.idPagoBanco = idPagoBanco;
    }

    public String getNombreRecaudo() {
        return nombreRecaudo;
    }

    public void setNombreRecaudo(String nombreRecaudo) {
        this.nombreRecaudo = nombreRecaudo;
    }

    public String getCodRecaudo() {
        return codRecaudo;
    }

    public void setCodRecaudo(String codRecaudo) {
        this.codRecaudo = codRecaudo;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public BigDecimal getDscto() {
        return dscto;
    }

    public void setDscto(BigDecimal dscto) {
        this.dscto = dscto;
    }

    public BigDecimal getPagado() {
        return pagado;
    }

    public void setPagado(BigDecimal pagado) {
        this.pagado = pagado;
    }

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public Boolean getFlgImpresion() {
        return flgImpresion;
    }

    public void setFlgImpresion(Boolean flgImpresion) {
        this.flgImpresion = flgImpresion;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public Integer getNroDoc() {
        return nroDoc;
    }

    public void setNroDoc(Integer nroDoc) {
        this.nroDoc = nroDoc;
    }

}
