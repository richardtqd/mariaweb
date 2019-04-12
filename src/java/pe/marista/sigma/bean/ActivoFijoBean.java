package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

public class ActivoFijoBean implements Serializable {

    private Integer idInventario;
    private Integer idUnidadNegocio;
    private String unidadNegocio;
    private Integer idUnidadOrganica;
    private String unidadOrganica;
    private Integer idDetRegistroCompra;
    private String codigo;
    private Integer idCatalogo;
    private String catalogo;
    private Date fechaCompra;
    private Date fechaUltimaAsignacion;
    private Integer idEntidadSede;
    private String entidadSede;
    private Integer cuenta;
    private Integer idTipoBien;
    private String tipoBien;
    private String titulo;
    private String marca;
    private String modelo;
    private Integer idTipoUniMed;
    private String tipoUniMed;
    private Integer idTipoMoneda;
    private String tipoMoneda;
    private Double precioCompra;
    private Integer idTipoStatusBien;
    private String tipoStatusBien;
    private Integer idTipoStatusOpera;
    private String tipoStatusOpera;
    private String ubicacion;
    private Integer idResponsable;
    private String responsable;
    private String creaPor;
    private Date creaFecha;
    private String alerta;

    public ActivoFijoBean() {
    }

    public ActivoFijoBean(Integer idInventario, String unidadNegocio, String unidadOrganica, String catalogo, String tipoUniMed, String tipoMoneda, Double precioCompra) {
        this.idInventario = idInventario;
        this.unidadNegocio = unidadNegocio;
        this.unidadOrganica = unidadOrganica;
        this.catalogo = catalogo;
        this.tipoUniMed = tipoUniMed;
        this.tipoMoneda = tipoMoneda;
        this.precioCompra = precioCompra;
    }

    public Integer getIdUnidadNegocio() {
        return idUnidadNegocio;
    }

    public void setIdUnidadNegocio(Integer idUnidadNegocio) {
        this.idUnidadNegocio = idUnidadNegocio;
    }

    public String getUnidadNegocio() {
        return unidadNegocio;
    }

    public void setUnidadNegocio(String unidadNegocio) {
        this.unidadNegocio = unidadNegocio;
    }

    public Integer getIdUnidadOrganica() {
        return idUnidadOrganica;
    }

    public void setIdUnidadOrganica(Integer idUnidadOrganica) {
        this.idUnidadOrganica = idUnidadOrganica;
    }

    public String getUnidadOrganica() {
        return unidadOrganica;
    }

    public void setUnidadOrganica(String unidadOrganica) {
        this.unidadOrganica = unidadOrganica;
    }

    public Integer getIdCatalogo() {
        return idCatalogo;
    }

    public void setIdCatalogo(Integer idCatalogo) {
        this.idCatalogo = idCatalogo;
    }

    public String getCatalogo() {
        return catalogo;
    }

    public void setCatalogo(String catalogo) {
        this.catalogo = catalogo;
    }

    public Integer getIdEntidadSede() {
        return idEntidadSede;
    }

    public void setIdEntidadSede(Integer idEntidadSede) {
        this.idEntidadSede = idEntidadSede;
    }

    public String getEntidadSede() {
        return entidadSede;
    }

    public void setEntidadSede(String entidadSede) {
        this.entidadSede = entidadSede;
    }

    public Integer getIdResponsable() {
        return idResponsable;
    }

    public void setIdResponsable(Integer idResponsable) {
        this.idResponsable = idResponsable;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public Integer getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(Integer idInventario) {
        this.idInventario = idInventario;
    }

    public Integer getIdDetRegistroCompra() {
        return idDetRegistroCompra;
    }

    public void setIdDetRegistroCompra(Integer idDetRegistroCompra) {
        this.idDetRegistroCompra = idDetRegistroCompra;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public Date getFechaUltimaAsignacion() {
        return fechaUltimaAsignacion;
    }

    public void setFechaUltimaAsignacion(Date fechaUltimaAsignacion) {
        this.fechaUltimaAsignacion = fechaUltimaAsignacion;
    }

    public Integer getCuenta() {
        return cuenta;
    }

    public void setCuenta(Integer cuenta) {
        this.cuenta = cuenta;
    }

    public Integer getIdTipoBien() {
        return idTipoBien;
    }

    public void setIdTipoBien(Integer idTipoBien) {
        this.idTipoBien = idTipoBien;
    }

    public String getTipoBien() {
        return tipoBien;
    }

    public void setTipoBien(String tipoBien) {
        this.tipoBien = tipoBien;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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

    public Integer getIdTipoUniMed() {
        return idTipoUniMed;
    }

    public void setIdTipoUniMed(Integer idTipoUniMed) {
        this.idTipoUniMed = idTipoUniMed;
    }

    public String getTipoUniMed() {
        return tipoUniMed;
    }

    public void setTipoUniMed(String tipoUniMed) {
        this.tipoUniMed = tipoUniMed;
    }

    public Integer getIdTipoMoneda() {
        return idTipoMoneda;
    }

    public void setIdTipoMoneda(Integer idTipoMoneda) {
        this.idTipoMoneda = idTipoMoneda;
    }

    public String getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(String tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public Double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(Double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public Integer getIdTipoStatusBien() {
        return idTipoStatusBien;
    }

    public void setIdTipoStatusBien(Integer idTipoStatusBien) {
        this.idTipoStatusBien = idTipoStatusBien;
    }

    public String getTipoStatusBien() {
        return tipoStatusBien;
    }

    public void setTipoStatusBien(String tipoStatusBien) {
        this.tipoStatusBien = tipoStatusBien;
    }

    public Integer getIdTipoStatusOpera() {
        return idTipoStatusOpera;
    }

    public void setIdTipoStatusOpera(Integer idTipoStatusOpera) {
        this.idTipoStatusOpera = idTipoStatusOpera;
    }

    public String getTipoStatusOpera() {
        return tipoStatusOpera;
    }

    public void setTipoStatusOpera(String tipoStatusOpera) {
        this.tipoStatusOpera = tipoStatusOpera;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
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

    public String getAlerta() {
        return alerta;
    }

    public void setAlerta(String alerta) {
        this.alerta = alerta;
    }

}
