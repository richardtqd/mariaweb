package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Administrador
 */
public class CajaChicaSaldoBean implements Serializable {

    private CajaChicaBean cajaChicaBean;
    private Integer idCajaChicaSaldo;
    private CodigoBean tipoMonedaBean;
    private Integer idTipoValorMoneda;
    private Integer cantidad;
    private Double importe;
    private Boolean flgFalso;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;

    public CajaChicaBean getCajaChicaBean() {
        if (cajaChicaBean == null) {
            cajaChicaBean = new CajaChicaBean();
        }
        return cajaChicaBean;
    }

    public void setCajaChicaBean(CajaChicaBean cajaChicaBean) {
        this.cajaChicaBean = cajaChicaBean;
    }

    public Integer getIdCajaChicaSaldo() {
        return idCajaChicaSaldo;
    }

    public void setIdCajaChicaSaldo(Integer idCajaChicaSaldo) {
        this.idCajaChicaSaldo = idCajaChicaSaldo;
    }

    public Integer getIdTipoValorMoneda() {
        return idTipoValorMoneda;
    }

    public void setIdTipoValorMoneda(Integer idTipoValorMoneda) {
        this.idTipoValorMoneda = idTipoValorMoneda;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Boolean getFlgFalso() {
        return flgFalso;
    }

    public void setFlgFalso(Boolean flgFalso) {
        this.flgFalso = flgFalso;
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

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public CodigoBean getTipoMonedaBean() {
        if(tipoMonedaBean==null){
            tipoMonedaBean = new CodigoBean();
        }
        return tipoMonedaBean;
    }

    public void setTipoMonedaBean(CodigoBean tipoMonedaBean) {
        this.tipoMonedaBean = tipoMonedaBean;
    }
    
}
