package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Administrador
 */
public class CajaChicaLiquidacionBean implements Serializable {

    private CajaChicaMovBean cajaChicaMovBean;//uniNeg,idCajaChicaMov,idSolicitudCajaCH
    private Integer idCajaChicaLiquidacion;
    private String proveedor;
    private String ruc;
    private CodigoBean tipoDoc;
    private String nroDoc;
    private Double monto = 0d;
    private Double descuento  = 0d;
    private Double impuesto = 0d;
    private Double montoTotal = 0d;
    private Double montoDevuelto;
    private CentroResponsabilidadBean centroResponsabilidadBean;
    private ConceptoBean conceptoBean;
    private Integer cuentaD;
    private Integer cuentaH;
    private String creaPor;
    private Date creaFecha; 
    private String modiPor;
    private DocEgresoBean docEgresoBean;
    private Integer codDistribucion;
     private List<AsientoBean> listaCajaChicaLiquidacionCRBean;
     private CodigoBean tipoDistribucion;
     
     //ayuda
      private Integer idSolCajaCh;
      private Date fechaDoc;

      private String descripcion;
    //Getter y Setter
    public CajaChicaMovBean getCajaChicaMovBean() {
        if (cajaChicaMovBean == null) {
            cajaChicaMovBean = new CajaChicaMovBean();
        }
        return cajaChicaMovBean;
    }

    public void setCajaChicaMovBean(CajaChicaMovBean cajaChicaMovBean) {
        this.cajaChicaMovBean = cajaChicaMovBean;
    }

    public Integer getIdCajaChicaLiquidacion() {
        return idCajaChicaLiquidacion;
    }

    public void setIdCajaChicaLiquidacion(Integer idCajaChicaLiquidacion) {
        this.idCajaChicaLiquidacion = idCajaChicaLiquidacion;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public CodigoBean getTipoDoc() {
        if (tipoDoc == null) {
            tipoDoc = new CodigoBean();
        }
        return tipoDoc;
    }

    public void setTipoDoc(CodigoBean tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getNroDoc() {
        return nroDoc;
    }

    public void setNroDoc(String nroDoc) {
        this.nroDoc = nroDoc;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public Double getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(Double impuesto) {
        this.impuesto = impuesto;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Double getMontoDevuelto() {
        return montoDevuelto;
    }

    public void setMontoDevuelto(Double montoDevuelto) {
        this.montoDevuelto = montoDevuelto;
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

    public ConceptoBean getConceptoBean() {
        if (conceptoBean == null) {
            conceptoBean = new ConceptoBean();
        }
        return conceptoBean;
    }

    public void setConceptoBean(ConceptoBean conceptoBean) {
        this.conceptoBean = conceptoBean;
    }

    public Integer getCuentaD() {
        return cuentaD;
    }

    public void setCuentaD(Integer cuentaD) {
        this.cuentaD = cuentaD;
    }

    public Integer getCuentaH() {
        return cuentaH;
    }

    public void setCuentaH(Integer cuentaH) {
        this.cuentaH = cuentaH;
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

    public DocEgresoBean getDocEgresoBean() {
        if(docEgresoBean==null){
            docEgresoBean = new DocEgresoBean();
        }
        return docEgresoBean;
    }

    public void setDocEgresoBean(DocEgresoBean docEgresoBean) {
        this.docEgresoBean = docEgresoBean;
    }

    public Integer getCodDistribucion() {
        return codDistribucion;
    }

    public void setCodDistribucion(Integer codDistribucion) {
        this.codDistribucion = codDistribucion;
    }

    public List<AsientoBean> getListaCajaChicaLiquidacionCRBean() {
          if (listaCajaChicaLiquidacionCRBean == null) {
            listaCajaChicaLiquidacionCRBean = new ArrayList<>();
        }
        return listaCajaChicaLiquidacionCRBean;
    }

    public void setListaCajaChicaLiquidacionCRBean(List<AsientoBean> listaCajaChicaLiquidacionCRBean) {
        this.listaCajaChicaLiquidacionCRBean = listaCajaChicaLiquidacionCRBean;
    }

    public CodigoBean getTipoDistribucion() {
        if (tipoDistribucion==null) {
            tipoDistribucion=new CodigoBean();
        }
        return tipoDistribucion;
    }

    public void setTipoDistribucion(CodigoBean tipoDistribucion) {
        this.tipoDistribucion = tipoDistribucion;
    }

    public Integer getIdSolCajaCh() {
        return idSolCajaCh;
    }

    public void setIdSolCajaCh(Integer idSolCajaCh) {
        this.idSolCajaCh = idSolCajaCh;
    }

    public Date getFechaDoc() {
        return fechaDoc;
    }

    public void setFechaDoc(Date fechaDoc) {
        this.fechaDoc = fechaDoc;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    } 
   
}
