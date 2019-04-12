package pe.marista.sigma.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class DescuentoTallerBean implements Serializable {
    private UnidadNegocioBean unidadNegocioBean;
    private Integer idDsctoTaller;
    private Integer cantidad;
    private Integer porcentaje;
    private Boolean flgExterno;
    private Boolean flgEstudiante;
    private TipoConceptoBean tipoConceptoBean;
    private Boolean status;
    private Integer anio;

    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean== null) {
            unidadNegocioBean= new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public Integer getIdDsctoTaller() {
        return idDsctoTaller;
    }

    public void setIdDsctoTaller(Integer idDsctoTaller) {
        this.idDsctoTaller = idDsctoTaller;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Integer porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Boolean getFlgExterno() {
        return flgExterno;
    }

    public void setFlgExterno(Boolean flgExterno) {
        this.flgExterno = flgExterno;
    }

    public Boolean getFlgEstudiante() {
        return flgEstudiante;
    }

    public void setFlgEstudiante(Boolean flgEstudiante) {
        this.flgEstudiante = flgEstudiante;
    }

    public TipoConceptoBean getTipoConceptoBean() {
        if (tipoConceptoBean== null) {
            tipoConceptoBean= new TipoConceptoBean();
        }
        return tipoConceptoBean;
    }

    public void setTipoConceptoBean(TipoConceptoBean tipoConceptoBean) {
        this.tipoConceptoBean = tipoConceptoBean;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }
    
    
    
    
}
