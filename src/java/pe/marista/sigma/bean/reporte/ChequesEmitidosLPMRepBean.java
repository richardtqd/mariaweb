package pe.marista.sigma.bean.reporte;

import java.util.Date;
import java.util.List;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ChequesEmitidosLPMRepBean {

    private String numCheque;
    private String fechaOperacion;
    private String titulo;
    private String nombreBanco;
    private String codigoMoneda;
    private String numcuenta;
    private String nombreUniNeg;
    private String rucColegio;
    private String tipoMedioPago;
    private JRBeanCollectionDataSource listaDetalle;
    private JRBeanCollectionDataSource listaSubDetalle;
    private String razonSocial;
    private String descripcion;
    private String documento;
    private Double monto;
    private Double devuelto;
    private Double montopagado;
    private Integer idSolicitudCajaCH;
    private String anio;
    private String mes;

    public String getNumCheque() {
        return numCheque;
    }

    public void setNumCheque(String numCheque) {
        this.numCheque = numCheque;
    } 

    public JRBeanCollectionDataSource getListaDetalle() {
        return listaDetalle;
    }

    public void setListaDetalle(List<ChequesEmitidosLPMRepBean> listaDetalle) {
        this.listaDetalle = new JRBeanCollectionDataSource(listaDetalle);
    }

    public JRBeanCollectionDataSource getListaSubDetalle() {
        return listaSubDetalle;
    }

    public void setListaSubDetalle(List<ChequesEmitidosLPMRepBean> listaSubDetalle) {
        this.listaSubDetalle = new JRBeanCollectionDataSource(listaSubDetalle);
    }

    public String getFechaOperacion() {
        return fechaOperacion;
    }

    public void setFechaOperacion(String fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNombreBanco() {
        return nombreBanco;
    }

    public void setNombreBanco(String nombreBanco) {
        this.nombreBanco = nombreBanco;
    }

    public String getCodigoMoneda() {
        return codigoMoneda;
    }

    public void setCodigoMoneda(String codigoMoneda) {
        this.codigoMoneda = codigoMoneda;
    }

    public String getNumcuenta() {
        return numcuenta;
    }

    public void setNumcuenta(String numcuenta) {
        this.numcuenta = numcuenta;
    }

    public String getNombreUniNeg() {
        return nombreUniNeg;
    }

    public void setNombreUniNeg(String nombreUniNeg) {
        this.nombreUniNeg = nombreUniNeg;
    }

    public String getRucColegio() {
        return rucColegio;
    }

    public void setRucColegio(String rucColegio) {
        this.rucColegio = rucColegio;
    }

    public String getTipoMedioPago() {
        return tipoMedioPago;
    }

    public void setTipoMedioPago(String tipoMedioPago) {
        this.tipoMedioPago = tipoMedioPago;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Double getDevuelto() {
        return devuelto;
    }

    public void setDevuelto(Double devuelto) {
        this.devuelto = devuelto;
    }

    public Double getMontopagado() {
        return montopagado;
    }

    public void setMontopagado(Double montopagado) {
        this.montopagado = montopagado;
    }

    public Integer getIdSolicitudCajaCH() {
        return idSolicitudCajaCH;
    }

    public void setIdSolicitudCajaCH(Integer idSolicitudCajaCH) {
        this.idSolicitudCajaCH = idSolicitudCajaCH;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }
    
}
