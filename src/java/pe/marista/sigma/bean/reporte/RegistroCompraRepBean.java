package pe.marista.sigma.bean.reporte;

import java.io.Serializable;
import java.util.List;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class RegistroCompraRepBean implements Serializable {

    private String nombreColegio;
    private String rucColegio;
    private String idRegistroCompra;
    private Integer idfacturacompra;
    private String nombreProveedor;
    private String rucProveedor;
    private String idOrdenCompra;
    private String descripcion;
    private String direccionColegio;
    private String nombreDistrito;
    private String nombrePais;
    private String telefonoColegio;
    private String correoColegio;
    private String webColegio;
    private String serieNroDoc;
    private String moneda;
    private String importe;
    private String igv;
    private String montoPago;
    private String igvResultado;
    private String detraccion;
    private String valordetraccion;
    private String tipoDoc;
    private JRBeanCollectionDataSource listaDetalle;
    private JRBeanCollectionDataSource listaDetalle2;
    private String nroRegistro;
    private String rutaImagen;

    public String getNombreColegio() {
        return nombreColegio;
    }

    public void setNombreColegio(String nombreColegio) {
        this.nombreColegio = nombreColegio;
    }

    public String getRucColegio() {
        return rucColegio;
    }

    public void setRucColegio(String rucColegio) {
        this.rucColegio = rucColegio;
    }

    public String getIdRegistroCompra() {
        return idRegistroCompra;
    }

    public void setIdRegistroCompra(String idRegistroCompra) {
        this.idRegistroCompra = idRegistroCompra;
    }

    public Integer getIdfacturacompra() {
        return idfacturacompra;
    }

    public void setIdfacturacompra(Integer idfacturacompra) {
        this.idfacturacompra = idfacturacompra;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getIdOrdenCompra() {
        return idOrdenCompra;
    }

    public void setIdOrdenCompra(String idOrdenCompra) {
        this.idOrdenCompra = idOrdenCompra;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccionColegio() {
        return direccionColegio;
    }

    public void setDireccionColegio(String direccionColegio) {
        this.direccionColegio = direccionColegio;
    }

    public String getNombreDistrito() {
        return nombreDistrito;
    }

    public void setNombreDistrito(String nombreDistrito) {
        this.nombreDistrito = nombreDistrito;
    }

    public String getNombrePais() {
        return nombrePais;
    }

    public void setNombrePais(String nombrePais) {
        this.nombrePais = nombrePais;
    }

    public String getTelefonoColegio() {
        return telefonoColegio;
    }

    public void setTelefonoColegio(String telefonoColegio) {
        this.telefonoColegio = telefonoColegio;
    }

    public String getCorreoColegio() {
        return correoColegio;
    }

    public void setCorreoColegio(String correoColegio) {
        this.correoColegio = correoColegio;
    }

    public String getWebColegio() {
        return webColegio;
    }

    public void setWebColegio(String webColegio) {
        this.webColegio = webColegio;
    }

    public String getSerieNroDoc() {
        return serieNroDoc;
    }

    public void setSerieNroDoc(String serieNroDoc) {
        this.serieNroDoc = serieNroDoc;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getIgv() {
        return igv;
    }

    public void setIgv(String igv) {
        this.igv = igv;
    }

    public String getMontoPago() {
        return montoPago;
    }

    public void setMontoPago(String montoPago) {
        this.montoPago = montoPago;
    }

    public String getIgvResultado() {
        return igvResultado;
    }

    public void setIgvResultado(String igvResultado) {
        this.igvResultado = igvResultado;
    }

    public String getDetraccion() {
        return detraccion;
    }

    public void setDetraccion(String detraccion) {
        this.detraccion = detraccion;
    }

    public String getValordetraccion() {
        return valordetraccion;
    }

    public void setValordetraccion(String valordetraccion) {
        this.valordetraccion = valordetraccion;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public JRBeanCollectionDataSource getListaDetalle() {
        return listaDetalle;
    }

    public void setListaDetalle(List<RegistroCompraRepBean> listaDetalle) {
        this.listaDetalle = new JRBeanCollectionDataSource(listaDetalle);
    }

    public JRBeanCollectionDataSource getListaDetalle2() {
        return listaDetalle2;
    }

    public void setListaDetalle2(List<DetRegistroCompraRepBean> listaDetalle2) {
        this.listaDetalle2 = new JRBeanCollectionDataSource(listaDetalle2);
    }

    public String getRucProveedor() {
        return rucProveedor;
    }

    public void setRucProveedor(String rucProveedor) {
        this.rucProveedor = rucProveedor;
    }

    public String getNroRegistro() {
        return nroRegistro;
    }

    public void setNroRegistro(String nroRegistro) {
        this.nroRegistro = nroRegistro;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }
}
