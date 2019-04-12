package pe.marista.sigma.bean;

import java.util.Date;

public class CuentaFacturaBean {

    private UnidadNegocioBean unidadNegocioBean;
    private Integer idCuentaFact;
    private FacturaCompraBean facturaCompraBean;
    private PlanContableBean planContableDBean;
    private PlanContableBean planContableHBean;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private ConceptoUniNegBean conceptoUniNegBean;
    private CentroResponsabilidadBean centroResponsabilidadBean;
    //ayuda
    private String idCuenta;
    private Double valor;
    private Double valorCuenta;
    private CodigoBean tipoDistribucion; 
    private ConceptoBean conceptoBean;

    //Ayuda para grabar el idFacturaCompra
    private Integer nroFact;

    public CuentaFacturaBean() {
        this.valorCuenta = new Double(0.0);
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

    public Integer getIdCuentaFact() {
        return idCuentaFact;
    }

    public void setIdCuentaFact(Integer idCuentaFact) {
        this.idCuentaFact = idCuentaFact;
    }

    public FacturaCompraBean getFacturaCompraBean() {
        if (facturaCompraBean == null) {
            facturaCompraBean = new FacturaCompraBean();
        }
        return facturaCompraBean;
    }

    public void setFacturaCompraBean(FacturaCompraBean facturaCompraBean) {
        this.facturaCompraBean = facturaCompraBean;
    }

    public PlanContableBean getPlanContableDBean() {
        if (planContableDBean == null) {
            planContableDBean = new PlanContableBean();
        }
        return planContableDBean;
    }

    public void setPlanContableDBean(PlanContableBean planContableDBean) {
        this.planContableDBean = planContableDBean;
    }

    public PlanContableBean getPlanContableHBean() {
        if (planContableHBean == null) {
            planContableHBean = new PlanContableBean();
        }
        return planContableHBean;
    }

    public void setPlanContableHBean(PlanContableBean planContableHBean) {
        this.planContableHBean = planContableHBean;
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

    public ConceptoUniNegBean getConceptoUniNegBean() {
        if (conceptoUniNegBean == null) {
            conceptoUniNegBean = new ConceptoUniNegBean();
        }
        return conceptoUniNegBean;
    }

    public void setConceptoUniNegBean(ConceptoUniNegBean conceptoUniNegBean) {
        this.conceptoUniNegBean = conceptoUniNegBean;
    }

    public Integer getNroFact() {
        return nroFact;
    }

    public void setNroFact(Integer nroFact) {
        this.nroFact = nroFact;
    }

    public CentroResponsabilidadBean getCentroResponsabilidadBean() {
        if (centroResponsabilidadBean == null) {
            centroResponsabilidadBean = new CentroResponsabilidadBean();
        }
        return centroResponsabilidadBean;
    }

    public void setCentroResponsabilidadBean(CentroResponsabilidadBean centroResponsabilidadBean) {
        this.centroResponsabilidadBean = centroResponsabilidadBean;
    }

    public String getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(String idCuenta) {
        this.idCuenta = idCuenta;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getValorCuenta() {
        return valorCuenta;
    }

    public void setValorCuenta(Double valorCuenta) {
        this.valorCuenta = valorCuenta;
    }
    
    public CodigoBean getTipoDistribucion() {
        if (tipoDistribucion==null) {
            tipoDistribucion= new CodigoBean();
        }
        return tipoDistribucion;
    }

    public void setTipoDistribucion(CodigoBean tipoDistribucion) {
        this.tipoDistribucion = tipoDistribucion;
    }

    public ConceptoBean getConceptoBean() {
        if (conceptoBean== null) {
            conceptoBean= new ConceptoBean();
        }
        return conceptoBean;
    }

    public void setConceptoBean(ConceptoBean conceptoBean) {
        this.conceptoBean = conceptoBean;
    }

}
