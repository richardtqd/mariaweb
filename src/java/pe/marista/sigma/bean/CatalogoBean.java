package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

public class CatalogoBean implements Serializable {

    private Integer idCatalogo;
    private Integer idCategoria;
    private String item;
    private InventarioAlmacenBean inventarioAlmacenBean;
    private CodigoBean tipoUnidadMedidaBean;
    private Integer idTipoUnidadMedida;
    private String tipoUnidadMedida;
    private CodigoBean tipoMonedaBean;
    private Integer idtipoMoneda;
    private String tipoMoneda;
    private Double precioRef = 0.00;
    private Integer cuenta;
    private CatalogoCategoriaBean catalogoCategoriaBean;
    private String creaPor;
    private Date creaFecha;
    private Integer cantidad = 0;
    private Integer stockActual = 0;
    private String modiPor;
    private CodigoBean tipoCategoriaBean;
    private Integer idTipoCategoria;
    private String tipoCategoria;
    private InventarioActivoBean inventarioActivoBean;
    private EntidadBean entidadBean;
    private String objFile;
    private String uniNeg;

    //variables de ayuda
    private Integer idCatalogoFamilia;
    private String catalogoFamilia;
    private String ruc;

    public Integer getIdCatalogoFamilia() {
        return idCatalogoFamilia;
    }

    public CatalogoBean(Integer idCatalogo) {
        this.idCatalogo = idCatalogo;
    }

    public CatalogoBean() {
    }

    public void setIdCatalogoFamilia(Integer idCatalogoFamilia) {
        this.idCatalogoFamilia = idCatalogoFamilia;
    }

    public String getCatalogoFamilia() {
        return catalogoFamilia;
    }

    public void setCatalogoFamilia(String catalogoFamilia) {
        this.catalogoFamilia = catalogoFamilia;
    }

    public Integer getStockActual() {
        return stockActual;
    }

    public void setStockActual(Integer stockActual) {
        this.stockActual = stockActual;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getIdCatalogo() {
        return idCatalogo;
    }

    public void setIdCatalogo(Integer idCatalogo) {
        this.idCatalogo = idCatalogo;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
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

    public Double getPrecioRef() {
        return precioRef;
    }

    public void setPrecioRef(Double precioRef) {
        this.precioRef = precioRef;
    }

    public Integer getCuenta() {
        return cuenta;
    }

    public void setCuenta(Integer cuenta) {
        this.cuenta = cuenta;
    }

    public CatalogoCategoriaBean getCatalogoCategoriaBean() {
        if (catalogoCategoriaBean == null) {
            catalogoCategoriaBean = new CatalogoCategoriaBean();
        }
        return catalogoCategoriaBean;
    }

    public void setCatalogoCategoriaBean(CatalogoCategoriaBean catalogoCategoriaBean) {
        this.catalogoCategoriaBean = catalogoCategoriaBean;
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

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
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

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
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

    public Integer getIdTipoUnidadMedida() {
        return idTipoUnidadMedida;
    }

    public void setIdTipoUnidadMedida(Integer idTipoUnidadMedida) {
        this.idTipoUnidadMedida = idTipoUnidadMedida;
    }

    public String getTipoUnidadMedida() {
        return tipoUnidadMedida;
    }

    public void setTipoUnidadMedida(String tipoUnidadMedida) {
        this.tipoUnidadMedida = tipoUnidadMedida;
    }

    public Integer getIdtipoMoneda() {
        return idtipoMoneda;
    }

    public void setIdtipoMoneda(Integer idtipoMoneda) {
        this.idtipoMoneda = idtipoMoneda;
    }

    public String getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(String tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
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

    public InventarioActivoBean getInventarioActivoBean() {
        if (inventarioActivoBean == null) {
            inventarioActivoBean = new InventarioActivoBean();
        }
        return inventarioActivoBean;
    }

    public void setInventarioActivoBean(InventarioActivoBean inventarioActivoBean) {
        this.inventarioActivoBean = inventarioActivoBean;
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

    public String getObjFile() {
        return objFile;
    }

    public void setObjFile(String objFile) {
        this.objFile = objFile;
    }

    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

}
