package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

public class InventarioActivoBean implements Serializable {

    private Integer idInventarioActivo;
    private String unineg;
    private DetRegistroCompraBean detRegistroCompraBean;
    private String codigo;
    private CatalogoBean catalogoBean;
    private Date fechacompra;
    private Date fechaultimaasignacion;
    private EntidadBean entidadBean;
    private UnidadOrganicaBean unidadOrganicaBean;
    private PlanContableBean planContableBean;
    private Integer idtipoActivo;
    private String titulo;
    private String marca;
    private String modelo;
    private Integer stockActual;
    private Integer idtipounimed;
    private Integer idtipomoneda;
    private Double preciocompra;
    private Integer idstatusbien;
    private Integer idtipostatusopera;
    private String ubicacion;
    private Integer idresponsable;
    private String creaPor;
    private String modiPor;
    private Date creaFecha;
    private boolean verPanel;
    private String ruc;
    private CodigoBean tipoUniMedBean;
    private Integer idTipoUnidadMedida;
    private String TipoUnidadMedida;
    private CodigoBean tipoMonedaBean;
    private Integer idTipoMoneda;
    private String TipoMoneda;
    private CodigoBean tipoCategoriaBean;
    private Integer idTipoCategoria;
    private String tipoCategoria;
    private UnidadNegocioBean unidadNegocioBean;
    private RegistroCompraBean registroCompraBean;
    private boolean flgDonacion;
    private Date fechaInicio;
    private Date fechaFin;
    private String alerta;
    private String estado;

//    public InventarioActivoBean(Integer idInventarioActivo, String uniNeg, DetRegistroCompraBean detRegistroCompraBean, String codigo, CatalogoBean catalogoBean, Date fechacompra, Date fechaultimacompra, EntidadBean entidadBean, EntidadSedeBean entidadSedeBean, UnidadOrganicaBean unidadOrganicaBean, PlanContableBean planContableBean, Integer idtipoactivo, String titulo, String marca, String modelo, Integer idtipounimed, Integer idtipomoneda, Float preciocompra, Integer idstatusbien, Integer idtipostatusopera, String ubicacion, Integer idresponsable, String creaPor, Date creaFecha) {
//        this.idInventarioActivo = idInventarioActivo;
//        this.uniNeg = uniNeg;
//        this.detRegistroCompraBean = detRegistroCompraBean;
//        this.codigo = codigo;
//        this.catalogoBean = catalogoBean;
//        this.fechacompra = fechacompra;
//        this.fechaultimacompra = fechaultimacompra;
//        this.entidadBean = entidadBean;
//        this.entidadSedeBean = entidadSedeBean;
//        this.unidadOrganicaBean = unidadOrganicaBean;
//        this.planContableBean = planContableBean;
//        this.idtipoactivo = idtipoactivo;
//        this.titulo = titulo;
//        this.marca = marca;
//        this.modelo = modelo;
//        this.idtipounimed = idtipounimed;
//        this.idtipomoneda = idtipomoneda;
//        this.preciocompra = preciocompra;
//        this.idstatusbien = idstatusbien;
//        this.idtipostatusopera = idtipostatusopera;
//        this.ubicacion = ubicacion;
//        this.idresponsable = idresponsable;
//        this.creaPor = creaPor;
//        this.creaFecha = creaFecha;
//    }
    public InventarioActivoBean() {
        this.fechacompra = new Date();
    }

    public Integer getIdInventarioActivo() {
        return idInventarioActivo;
    }

    public void setIdInventarioActivo(Integer idInventarioActivo) {
        this.idInventarioActivo = idInventarioActivo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public Date getFechacompra() {
        return fechacompra;
    }

    public void setFechacompra(Date fechacompra) {
        this.fechacompra = fechacompra;
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

    public UnidadOrganicaBean getUnidadOrganicaBean() {
        return unidadOrganicaBean;
    }

    public void setUnidadOrganicaBean(UnidadOrganicaBean unidadOrganicaBean) {
        this.unidadOrganicaBean = unidadOrganicaBean;
    }

    public Integer getIdtipoActivo() {
        return idtipoActivo;
    }

    public void setIdtipoActivo(Integer idtipoActivo) {
        this.idtipoActivo = idtipoActivo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getIdtipounimed() {
        return idtipounimed;
    }

    public void setIdtipounimed(Integer idtipounimed) {
        this.idtipounimed = idtipounimed;
    }

    public Integer getIdtipomoneda() {
        return idtipomoneda;
    }

    public void setIdtipomoneda(Integer idtipomoneda) {
        this.idtipomoneda = idtipomoneda;
    }

    public Double getPreciocompra() {
        return preciocompra;
    }

    public void setPreciocompra(Double preciocompra) {
        this.preciocompra = preciocompra;
    }

    public Integer getIdstatusbien() {
        return idstatusbien;
    }

    public void setIdstatusbien(Integer idstatusbien) {
        this.idstatusbien = idstatusbien;
    }

    public Integer getIdtipostatusopera() {
        return idtipostatusopera;
    }

    public void setIdtipostatusopera(Integer idtipostatusopera) {
        this.idtipostatusopera = idtipostatusopera;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Integer getIdresponsable() {
        return idresponsable;
    }

    public void setIdresponsable(Integer idresponsable) {
        this.idresponsable = idresponsable;
    }

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

    public PlanContableBean getPlanContableBean() {
        return planContableBean;
    }

    public void setPlanContableBean(PlanContableBean planContableBean) {
        this.planContableBean = planContableBean;
    }

    public DetRegistroCompraBean getDetRegistroCompraBean() {
        if (detRegistroCompraBean == null) {
            detRegistroCompraBean = new DetRegistroCompraBean();
        }
        return detRegistroCompraBean;
    }

    public void setDetRegistroCompraBean(DetRegistroCompraBean detRegistroCompraBean) {

        this.detRegistroCompraBean = detRegistroCompraBean;
    }

    public String getUnineg() {
        return unineg;
    }

    public void setUnineg(String unineg) {
        this.unineg = unineg;
    }

    public Date getFechaultimaasignacion() {
        return fechaultimaasignacion;
    }

    public void setFechaultimaasignacion(Date fechaultimaasignacion) {
        this.fechaultimaasignacion = fechaultimaasignacion;
    }

    public boolean isVerPanel() {
        return verPanel;
    }

    public void setVerPanel(boolean verPanel) {
        this.verPanel = verPanel;
    }

    public String getCreaPor() {
        return creaPor;
    }

    public void setCreaPor(String creaPor) {
        this.creaPor = creaPor;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
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

    public CodigoBean getTipoMonedaBean() {
        if (tipoMonedaBean == null) {
            tipoMonedaBean = new CodigoBean();
        }
        return tipoMonedaBean;
    }

    public void setTipoMonedaBean(CodigoBean tipoMonedaBean) {
        this.tipoMonedaBean = tipoMonedaBean;
    }

    public CodigoBean getTipoCategoriaBean() {
        if (tipoCategoriaBean == null) {
            tipoCategoriaBean = new CodigoBean();
        }
        return tipoCategoriaBean;
    }

    public void setTipoCategoriaBean(CodigoBean tipoCategoriaBean) {
        this.tipoCategoriaBean = tipoCategoriaBean;
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

    public RegistroCompraBean getRegistroCompraBean() {
        if (registroCompraBean == null) {
            registroCompraBean = new RegistroCompraBean();
        }
        return registroCompraBean;
    }

    public void setRegistroCompraBean(RegistroCompraBean registroCompraBean) {
        this.registroCompraBean = registroCompraBean;
    }

    public Integer getIdTipoUnidadMedida() {
        return idTipoUnidadMedida;
    }

    public void setIdTipoUnidadMedida(Integer idTipoUnidadMedida) {
        this.idTipoUnidadMedida = idTipoUnidadMedida;
    }

    public String getTipoUnidadMedida() {
        return TipoUnidadMedida;
    }

    public void setTipoUnidadMedida(String TipoUnidadMedida) {
        this.TipoUnidadMedida = TipoUnidadMedida;
    }

    public Integer getIdTipoMoneda() {
        return idTipoMoneda;
    }

    public void setIdTipoMoneda(Integer idTipoMoneda) {
        this.idTipoMoneda = idTipoMoneda;
    }

    public String getTipoMoneda() {
        return TipoMoneda;
    }

    public void setTipoMoneda(String TipoMoneda) {
        this.TipoMoneda = TipoMoneda;
    }

    public Integer getIdTipoCategoria() {
        return idTipoCategoria;
    }

    public void setIdTipoCategoria(Integer idTipoCategoria) {
        this.idTipoCategoria = idTipoCategoria;
    }

    public String getTipoCategoria() {
        return tipoCategoria;
    }

    public void setTipoCategoria(String tipoCategoria) {
        this.tipoCategoria = tipoCategoria;
    }

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public boolean isFlgDonacion() {
        return flgDonacion;
    }

    public void setFlgDonacion(boolean flgDonacion) {
        this.flgDonacion = flgDonacion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getStockActual() {
        return stockActual;
    }

    public void setStockActual(Integer stockActual) {
        this.stockActual = stockActual;
    }

    public String getAlerta() {
        return alerta;
    }

    public void setAlerta(String alerta) {
        this.alerta = alerta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
