package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

public class SolicitudLogDetalleBean implements Serializable {

    private Integer idDetRequerimiento;
    private Integer idRequerimiento;
    private SolicitudLogisticoBean solicitudLogisticoBean;
    private CodigoBean tipoUnidadMedidaBean;
    private UnidadNegocioBean unidadNegocioBean;
    private CatalogoBean catalogoBean;
    private Integer cantidadEntregada = 0;
    private Integer cantidadEntregadaParcial = 0;
    private Integer cantidadSolicitada = 0;
    private Integer cantidadSolicitadaServicio = 1;
    private Integer cantidadSolicitadaAnterior = 0;
    private CodigoBean tipoMonedaBean;
    private String creaPor;
    private Date creaFecha;
    private Double sumaImporte = 0.0;
    private Double montoRef = 0.0;
    private MaterialBean materialBean;
    private Integer idestado;
    private CodigoBean tipoEstadoBean;
    private String estado;
    private PlanContableBean planContableCuentaDSoliBean;
    private PlanContableBean planContableCuentaHSoliBean;
    private ConceptoBean conceptoBean;
    private ConceptoUniNegBean conceptoUniNegBean;
    private InventarioAlmacenBean inventarioAlmacenBean;
    private InventarioActivoBean inventarioActivoBean;
    private Integer stockAyuda;
    private Integer cantidadMovi;

    //Para Servicio
    private Date fechaSalida;
    private String horaSalida;
    private String horaRegreso;
    private String destinoServicio;
    //ayuda
    private Boolean validarCatalogo;
    private Boolean flgComprado;
    private Double montoDetalle;

    public Integer getCantidadSolicitadaAnterior() {
        return cantidadSolicitadaAnterior;
    }

    public void setCantidadSolicitadaAnterior(Integer cantidadSolicitadaAnterior) {
        this.cantidadSolicitadaAnterior = cantidadSolicitadaAnterior;
    }

    public CodigoBean getTipoEstadoBean() {
        if (tipoEstadoBean == null) {
            tipoEstadoBean = new CodigoBean();
        }
        return tipoEstadoBean;
    }

    public void setTipoEstadoBean(CodigoBean tipoEstadoBean) {
        this.tipoEstadoBean = tipoEstadoBean;
    }

    public Integer getIdestado() {
        return idestado;
    }

    public void setIdestado(Integer idestado) {
        this.idestado = idestado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public MaterialBean getMaterialBean() {
        if (materialBean == null) {
            materialBean = new MaterialBean();
        }
        return materialBean;
    }

    public void setMaterialBean(MaterialBean materialBean) {
        this.materialBean = materialBean;
    }

    public Double getSumaImporte() {
        return sumaImporte;
    }

    public void setSumaImporte(Double sumaImporte) {
        this.sumaImporte = sumaImporte;
    }

    public CodigoBean getTipoUnidadMedidaBean() {
        if (tipoUnidadMedidaBean == null) {
            tipoUnidadMedidaBean = new CodigoBean();
        }
        return tipoUnidadMedidaBean;
    }

    public void setTipoUnidadMedidaBean(CodigoBean tipoUnidadMedidaBean) {
        this.tipoUnidadMedidaBean = tipoUnidadMedidaBean;
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

    public Integer getIdRequerimiento() {
        return idRequerimiento;
    }

    public void setIdRequerimiento(Integer idRequerimiento) {
        this.idRequerimiento = idRequerimiento;
    }

    public Integer getIdDetRequerimiento() {
        return idDetRequerimiento;
    }

    public void setIdDetRequerimiento(Integer idDetRequerimiento) {
        this.idDetRequerimiento = idDetRequerimiento;
    }

    public SolicitudLogisticoBean getSolicitudLogisticoBean() {
        if (solicitudLogisticoBean == null) {
            solicitudLogisticoBean = new SolicitudLogisticoBean();
        }
        return solicitudLogisticoBean;
    }

    public void setSolicitudLogisticoBean(SolicitudLogisticoBean solicitudLogisticoBean) {
        this.solicitudLogisticoBean = solicitudLogisticoBean;
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

    public Integer getCantidadEntregada() {
        return cantidadEntregada;
    }

    public void setCantidadEntregada(Integer cantidadEntregada) {
        this.cantidadEntregada = cantidadEntregada;
    }

    public Integer getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public void setCantidadSolicitada(Integer cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
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

    public PlanContableBean getPlanContableCuentaDSoliBean() {
        if (planContableCuentaDSoliBean == null) {
            planContableCuentaDSoliBean = new PlanContableBean();

        }
        return planContableCuentaDSoliBean;
    }

    public void setPlanContableCuentaDSoliBean(PlanContableBean planContableCuentaDSoliBean) {
        this.planContableCuentaDSoliBean = planContableCuentaDSoliBean;
    }

    public PlanContableBean getPlanContableCuentaHSoliBean() {
        if (planContableCuentaHSoliBean == null) {
            planContableCuentaHSoliBean = new PlanContableBean();
        }
        return planContableCuentaHSoliBean;
    }

    public void setPlanContableCuentaHSoliBean(PlanContableBean planContableCuentaHSoliBean) {
        this.planContableCuentaHSoliBean = planContableCuentaHSoliBean;
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

    public ConceptoUniNegBean getConceptoUniNegBean() {
        if (conceptoUniNegBean == null) {
            conceptoUniNegBean = new ConceptoUniNegBean();
        }
        return conceptoUniNegBean;
    }

    public void setConceptoUniNegBean(ConceptoUniNegBean conceptoUniNegBean) {
        this.conceptoUniNegBean = conceptoUniNegBean;
    }

    public InventarioAlmacenBean getInventarioAlmacenBean() {
        if (inventarioAlmacenBean == null) {
            inventarioAlmacenBean = new InventarioAlmacenBean();
        }
        return inventarioAlmacenBean;
    }

    public void setInventarioAlmacenBean(InventarioAlmacenBean inventarioAlmacenBean) {
        this.inventarioAlmacenBean = inventarioAlmacenBean;
    }

    public InventarioActivoBean getInventarioActivoBean() {
        if (inventarioActivoBean == null) {
            inventarioActivoBean = new InventarioActivoBean();
        }
        return inventarioActivoBean;
    }

    public void setInventarioActivoBean(InventarioActivoBean inventarioActivoBean) {
        this.inventarioActivoBean = inventarioActivoBean;
    }

    public Double getMontoRef() {
        return montoRef;
    }

    public void setMontoRef(Double montoRef) {
        this.montoRef = montoRef;
    }

    public Integer getCantidadSolicitadaServicio() {
        return cantidadSolicitadaServicio;
    }

    public void setCantidadSolicitadaServicio(Integer cantidadSolicitadaServicio) {
        this.cantidadSolicitadaServicio = cantidadSolicitadaServicio;
    }

    public Double getMontoDetalle() {
        return montoDetalle;
    }

    public void setMontoDetalle(Double montoDetalle) {
        this.montoDetalle = montoDetalle;
    }

    public UnidadNegocioBean getUnidadNegocioBean() {
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public Integer getStockAyuda() {
        return stockAyuda;
    }

    public void setStockAyuda(Integer stockAyuda) {
        this.stockAyuda = stockAyuda;
    }

    public Integer getCantidadEntregadaParcial() {
        return cantidadEntregadaParcial;
    }

    public void setCantidadEntregadaParcial(Integer cantidadEntregadaParcial) {
        this.cantidadEntregadaParcial = cantidadEntregadaParcial;
    }

    public Integer getCantidadMovi() {
        return cantidadMovi;
    }

    public void setCantidadMovi(Integer cantidadMovi) {
        this.cantidadMovi = cantidadMovi;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getHoraRegreso() {
        return horaRegreso;
    }

    public void setHoraRegreso(String horaRegreso) {
        this.horaRegreso = horaRegreso;
    }

    public String getDestinoServicio() {
        return destinoServicio;
    }

    public void setDestinoServicio(String destinoServicio) {
        this.destinoServicio = destinoServicio;
    }

    public Boolean getValidarCatalogo() {
        return validarCatalogo;
    }

    public void setValidarCatalogo(Boolean validarCatalogo) {
        this.validarCatalogo = validarCatalogo;
    }

    public Boolean getFlgComprado() {
        return flgComprado;
    }

    public void setFlgComprado(Boolean flgComprado) {
        this.flgComprado = flgComprado;
    }

}
