package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;
import pe.marista.sigma.MaristaConstantes;

/**
 *
 * @author Administrador
 */
public class CajaChicaMovBean implements Serializable {

    private CajaChicaBean cajaChicaBean;//uniNeg,idCajaChica,idCajero
    private Integer idCajaChicaMov;
    private String motivo;
    private Integer flgMov = 0;
    private Integer anio;
    private SolicitudCajaCHBean solicitudCajaCHBean;//idSolicitudCajaCH,idTipoSolicitud
    private Date fecOrden;
    private Date fecPago = new Date();
    private CodigoBean tipoMonedaBean;//idTipoMoneda
    private Double monto;
    private Date fecLiquida;
    private Double montoDevuelto;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private Boolean flgRendicion;
    private Boolean flgRendicionDis;
    private Integer idDevolucion;
    private Integer idCajaChicaMov2;
//    private Boolean flgRendicionVista;
    private String nombreSolicitante;
    
    private Boolean extorno;

    public Integer getIdCajaChicaMov() {
        return idCajaChicaMov;
    }

    public void setIdCajaChicaMov(Integer idCajaChicaMov) {
        this.idCajaChicaMov = idCajaChicaMov;
    }

    public CajaChicaBean getCajaChicaBean() {
        if (cajaChicaBean == null) {
            cajaChicaBean = new CajaChicaBean();
        }
        return cajaChicaBean;
    }

    public void setCajaChicaBean(CajaChicaBean cajaChicaBean) {
        this.cajaChicaBean = cajaChicaBean;
    }

    public Integer getFlgMov() {
        return flgMov;
    }

    public String getFlgMovVista() {
        if (flgMov == 1) {
            return MaristaConstantes.NOM_ENT;
        } else if (flgMov == 0) {
            return MaristaConstantes.NOM_SAL;
        }
        return null;
    }

    public void setFlgMov(Integer flgMov) {
        this.flgMov = flgMov;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public SolicitudCajaCHBean getSolicitudCajaCHBean() {
        if (solicitudCajaCHBean == null) {
            solicitudCajaCHBean = new SolicitudCajaCHBean();
        }
        return solicitudCajaCHBean;
    }

    public void setSolicitudCajaCHBean(SolicitudCajaCHBean solicitudCajaCHBean) {
        this.solicitudCajaCHBean = solicitudCajaCHBean;
    }

    public Date getFecOrden() {
        return fecOrden;
    }

    public void setFecOrden(Date fecOrden) {
        this.fecOrden = fecOrden;
    }

    public Date getFecPago() {
        return fecPago;
    }

    public void setFecPago(Date fecPago) {
        this.fecPago = fecPago;
    }

    public Date getFecLiquida() {
        return fecLiquida;
    }

    public void setFecLiquida(Date fecLiquida) {
        this.fecLiquida = fecLiquida;
    }

    public Double getMontoDevuelto() {
        return montoDevuelto;
    }

    public void setMontoDevuleto(Double montoDevuelto) {
        this.montoDevuelto = montoDevuelto;
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

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public CodigoBean getTipoMonedaBean() {
        if (tipoMonedaBean == null) {
            tipoMonedaBean = new CodigoBean();
        }
        return tipoMonedaBean;
    }

    public void setTipoMonedaBean(CodigoBean tipoMonedaBean) {
        this.tipoMonedaBean = tipoMonedaBean;
    }

    public Boolean getFlgRendicion() {
        return flgRendicion;
    }

    public String getFlgRendicionVista() {
        if (flgRendicion != null && flgRendicion) {
            return MaristaConstantes.SI;
        } else {
            return MaristaConstantes.NO;
        }
    }

    public void setFlgRendicion(Boolean flgRendicion) {
        this.flgRendicion = flgRendicion;
    }

    public Integer getIdDevolucion() {
        return idDevolucion;
    }

    public void setIdDevolucion(Integer idDevolucion) {
        this.idDevolucion = idDevolucion;
    }

    public Integer getIdCajaChicaMov2() {
        return idCajaChicaMov2;
    }

    public void setIdCajaChicaMov2(Integer idCajaChicaMov2) {
        this.idCajaChicaMov2 = idCajaChicaMov2;
    }

    public String getNombreSolicitante() {
        return nombreSolicitante;
    }

    public void setNombreSolicitante(String nombreSolicitante) {
        this.nombreSolicitante = nombreSolicitante;
    }

    public Boolean getFlgRendicionDis() {
        return flgRendicionDis;
    }

    public void setFlgRendicionDis(Boolean flgRendicionDis) {
        this.flgRendicionDis = flgRendicionDis;
    }

    public Boolean getExtorno() {
        return extorno;
    }

    public void setExtorno(Boolean extorno) {
        this.extorno = extorno;
    }

    
}
