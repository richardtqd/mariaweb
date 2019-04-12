package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

public class DetRegistroCompraBean implements Serializable {

    private UnidadNegocioBean unidadNegocioBean;
    private Integer idDetRegistroCompra;
    private RegistroCompraBean registroCompraBean;
    private Integer idRegistroCompra;
    private Integer anio;
    private EntidadBean entidadBean;
    private CatalogoBean catalogoBean;
    private OrdenCompraBean ordenCompraBean;
//    private DetOrdenCompraBean detOrdenCompraBean;
    private OrdenCompraDetalleBean ordenCompraDetalleBean;
    private CodigoBean tipoUniMedBean;
    private Integer cantidad;
    private Double precio= 0.0;
    private Double importe = 0.0;
    private Float montoCadaUnoMate;
    

    private String descripcion;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private FacturaCompraBean facturaCompraBean;
    private Integer cantidadRecibida;
    private PlanContableBean planContableCuentaDSoliBean;
    private PlanContableBean planContableCuentaHSoliBean;
    private ConceptoBean conceptoBean;
    private ConceptoUniNegBean conceptoUniNegBean;
    
    //ayuda reporte
    private Float montoTotalDocE;
    private Float montoCadaUno;
    private String docRef;
//    private Boolean flgRecibido;
private String cuentaD;
    public DetRegistroCompraBean() {
//    this.flgRecibido= false;
    }

    public CatalogoBean getCatalogoBean() {
        if (catalogoBean == null) {
            catalogoBean = new CatalogoBean();
        }
        return catalogoBean;
    }

    public void setCatalogoBean(CatalogoBean catalogoBean) {
        this.catalogoBean = catalogoBean;
    }

//    public DetOrdenCompraBean getDetOrdenCompraBean() {
//        if (detOrdenCompraBean == null) {
//            detOrdenCompraBean = new DetOrdenCompraBean();
//        }
//        return detOrdenCompraBean;
//    }
//
//    public void setDetOrdenCompraBean(DetOrdenCompraBean detOrdenCompraBean) {
//        this.detOrdenCompraBean = detOrdenCompraBean;
//    } 

    public String getCreapor() {
        return creaPor;
    }

    public void setCreapor(String creaPor) {
        this.creaPor = creaPor;
    }

    public Date getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(Date creaFecha) {
        this.creaFecha = creaFecha;
    }

    public Integer getIdDetRegistroCompra() {
        return idDetRegistroCompra;
    }

    public void setIdDetRegistroCompra(Integer idDetRegistroCompra) {
        this.idDetRegistroCompra = idDetRegistroCompra;
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

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public EntidadBean getEntidadBean() {
        if (entidadBean == null) {
            entidadBean = new EntidadBean();
        }
        return entidadBean;
    }

    public void setEntidadBean(EntidadBean entidadBean) {
        this.entidadBean = entidadBean;
    }

    public OrdenCompraBean getOrdenCompraBean() {
        if (ordenCompraBean == null) {
            ordenCompraBean = new OrdenCompraBean();
        }
        return ordenCompraBean;
    }

    public void setOrdenCompraBean(OrdenCompraBean ordenCompraBean) {
        this.ordenCompraBean = ordenCompraBean;
    }

    public CodigoBean getTipoUniMedBean() {
        if (tipoUniMedBean == null) {
            tipoUniMedBean = new CodigoBean();
        }
        return tipoUniMedBean;
    }

    public void setTipoUniMedBean(CodigoBean tipoUniMedBean) {
        this.tipoUniMedBean = tipoUniMedBean;
    } 

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCreaPor() {
        return creaPor;
    }

    public void setCreaPor(String creaPor) {
        this.creaPor = creaPor;
    }

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public Integer getIdRegistroCompra() {
        return idRegistroCompra;
    }

    public void setIdRegistroCompra(Integer idRegistroCompra) {
        this.idRegistroCompra = idRegistroCompra;
    }

    public RegistroCompraBean getRegistroCompraBean() {
        if (registroCompraBean == null) {
            registroCompraBean = new RegistroCompraBean();
        }
        return registroCompraBean;
    }

    public void setRegistroCompraBean(RegistroCompraBean registroCompraBean) {
        this.registroCompraBean = registroCompraBean;
    }

//    public Boolean getFlgRecibido() {
//        return flgRecibido;
//    }
//
//    public void setFlgRecibido(Boolean flgRecibido) {
//        this.flgRecibido = flgRecibido;
//    }
    public FacturaCompraBean getFacturaCompraBean() {
        if (facturaCompraBean == null) {
            facturaCompraBean = new FacturaCompraBean();
        }
        return facturaCompraBean;
    }

    public void setFacturaCompraBean(FacturaCompraBean facturaCompraBean) {
        this.facturaCompraBean = facturaCompraBean;
    }

    public OrdenCompraDetalleBean getOrdenCompraDetalleBean() {
        if (ordenCompraDetalleBean == null) {
            ordenCompraDetalleBean = new OrdenCompraDetalleBean();
        }
        return ordenCompraDetalleBean;
    }

    public void setOrdenCompraDetalleBean(OrdenCompraDetalleBean ordenCompraDetalleBean) {
        this.ordenCompraDetalleBean = ordenCompraDetalleBean;
    }

    public Float getMontoCadaUnoMate() {
        return montoCadaUnoMate;
    }

    public void setMontoCadaUnoMate(Float montoCadaUnoMate) {
        this.montoCadaUnoMate = montoCadaUnoMate;
    }

    public Integer getCantidadRecibida() {
        return cantidadRecibida;
    }

    public void setCantidadRecibida(Integer cantidadRecibida) {
        this.cantidadRecibida = cantidadRecibida;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public PlanContableBean getPlanContableCuentaDSoliBean() {
        if (planContableCuentaDSoliBean== null) {
            planContableCuentaDSoliBean = new PlanContableBean();
        }
        return planContableCuentaDSoliBean;
    }

    public void setPlanContableCuentaDSoliBean(PlanContableBean planContableCuentaDSoliBean) {
        this.planContableCuentaDSoliBean = planContableCuentaDSoliBean;
    }

    public PlanContableBean getPlanContableCuentaHSoliBean() {
        if (planContableCuentaHSoliBean==null) {
            planContableCuentaHSoliBean= new PlanContableBean();
        }
        return planContableCuentaHSoliBean;
    }

    public void setPlanContableCuentaHSoliBean(PlanContableBean planContableCuentaHSoliBean) {
        this.planContableCuentaHSoliBean = planContableCuentaHSoliBean;
    }

    public ConceptoBean getConceptoBean() {
        if (conceptoBean==null) {
            conceptoBean= new ConceptoBean();
                   
        }
        return conceptoBean;
    }

    public void setConceptoBean(ConceptoBean conceptoBean) {
        this.conceptoBean = conceptoBean;
    }

    public ConceptoUniNegBean getConceptoUniNegBean() {
        if (conceptoUniNegBean==null) {
            conceptoUniNegBean= new ConceptoUniNegBean();
        }
        return conceptoUniNegBean;
    }

    public void setConceptoUniNegBean(ConceptoUniNegBean conceptoUniNegBean) {
        this.conceptoUniNegBean = conceptoUniNegBean;
    }

    public Float getMontoTotalDocE() {
        return montoTotalDocE;
    }

    public void setMontoTotalDocE(Float montoTotalDocE) {
        this.montoTotalDocE = montoTotalDocE;
    }

    public Float getMontoCadaUno() {
        return montoCadaUno;
    }

    public void setMontoCadaUno(Float montoCadaUno) {
        this.montoCadaUno = montoCadaUno;
    }

    public String getDocRef() {
        return docRef;
    }

    public void setDocRef(String docRef) {
        this.docRef = docRef;
    }

    public String getCuentaD() {
        return cuentaD;
    }

    public void setCuentaD(String cuentaD) {
        this.cuentaD = cuentaD;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    
}
