package pe.marista.sigma.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Administrador
 */
public class ConceptoUniNegBean implements Serializable {

    private UnidadNegocioBean unidadNegocioBean;//uniNeg
    private ConceptoBean conceptoBean;//idConcepto
    private BigDecimal importe;
    private BigDecimal importeConDscto;
    private Boolean flgDsctoEstudiante;
    private Boolean flgDsctoExAlumno;
    private Boolean flgDsctoExterno;
    private Boolean flgDsctoHijoExAlumno;
    private Boolean flgDsctoHijoEmpleado;
    private Boolean flgDsctoBeca;
    private Double dsctoAlumno;//
    private Double dsctoEmpleado;
    private Double dsctoExAlumno;//
    private Double dsctoExterno;
    private Boolean status;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private CodigoBean idTipoMoneda;
    private String flgDisable;
    
    public Boolean getFlgDsctoHijoExAlumno() {
        return flgDsctoHijoExAlumno;
    }

    public void setFlgDsctoHijoExAlumno(Boolean flgDsctoHijoExAlumno) {
        this.flgDsctoHijoExAlumno = flgDsctoHijoExAlumno;
    }

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean == null) {
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public ConceptoBean getConceptoBean() {
        if (conceptoBean == null) {
            conceptoBean = new ConceptoBean();
        }
        return conceptoBean;
    }

    public void setConceptoBean(ConceptoBean conceptoBean) {
        this.conceptoBean = conceptoBean;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public Boolean getFlgDsctoExAlumno() {
        return flgDsctoExAlumno;
    }

    public void setFlgDsctoExAlumno(Boolean flgDsctoExAlumno) {
        this.flgDsctoExAlumno = flgDsctoExAlumno;
    }

    public Boolean getFlgDsctoExterno() {
        return flgDsctoExterno;
    }

    public void setFlgDsctoExterno(Boolean flgDsctoExterno) {
        this.flgDsctoExterno = flgDsctoExterno;
    }

    public Boolean getFlgDsctoBeca() {
        return flgDsctoBeca;
    }

    public void setFlgDsctoBeca(Boolean flgDsctoBeca) {
        this.flgDsctoBeca = flgDsctoBeca;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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

    public Boolean getFlgDsctoEstudiante() {
        return flgDsctoEstudiante;
    }

    public void setFlgDsctoEstudiante(Boolean flgDsctoEstudiante) {
        this.flgDsctoEstudiante = flgDsctoEstudiante;
    }

    public Boolean getFlgDsctoHijoEmpleado() {
        return flgDsctoHijoEmpleado;
    }

    public void setFlgDsctoHijoEmpleado(Boolean flgDsctoHijoEmpleado) {
        this.flgDsctoHijoEmpleado = flgDsctoHijoEmpleado;
    }

    public Boolean getflgDsctoHijoExAlumno() {
        return flgDsctoHijoExAlumno;
    }

    public void setflgDsctoHijoExAlumno(Boolean flgDsctoHijoExAlumno) {
        this.flgDsctoHijoExAlumno = flgDsctoHijoExAlumno;
    }

    public BigDecimal getImporteConDscto() {
        return importeConDscto;
    }

    public void setImporteConDscto(BigDecimal importeConDscto) {
        this.importeConDscto = importeConDscto;
    }

    public Double getDsctoAlumno() {
        return dsctoAlumno;
    }

    public void setDsctoAlumno(Double dsctoAlumno) {
        this.dsctoAlumno = dsctoAlumno;
    }

    public Double getDsctoEmpleado() {
        return dsctoEmpleado;
    }

    public void setDsctoEmpleado(Double dsctoEmpleado) {
        this.dsctoEmpleado = dsctoEmpleado;
    }

    public Double getDsctoExAlumno() {
        return dsctoExAlumno;
    }

    public void setDsctoExAlumno(Double dsctoExAlumno) {
        this.dsctoExAlumno = dsctoExAlumno;
    }

    public Double getDsctoExterno() {
        return dsctoExterno;
    }

    public void setDsctoExterno(Double dsctoExterno) {
        this.dsctoExterno = dsctoExterno;
    }

    public CodigoBean getIdTipoMoneda() {
        if (idTipoMoneda == null) {
            idTipoMoneda = new CodigoBean();
        }
        return idTipoMoneda;
    }

    public void setIdTipoMoneda(CodigoBean idTipoMoneda) {
        this.idTipoMoneda = idTipoMoneda;
    }

    public String getFlgDisable() {
        return flgDisable;
    }

    public void setFlgDisable(String flgDisable) {
        this.flgDisable = flgDisable;
    } 
}
