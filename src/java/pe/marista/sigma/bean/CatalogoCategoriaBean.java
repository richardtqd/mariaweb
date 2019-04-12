

package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;


public class CatalogoCategoriaBean implements Serializable
{
    private Integer idCatalogoCategoria;
    private String nombre;
    private CatalogoFamiliaBean catalogoFamiliaBean;
    private String creaPor;
    private Date creaFecha;

    public Integer getIdCatalogoCategoria() {
        return idCatalogoCategoria;
    }

    public void setIdCatalogoCategoria(Integer idCatalogoCategoria) {
        this.idCatalogoCategoria = idCatalogoCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public CatalogoFamiliaBean getCatalogoFamiliaBean() {
        if(catalogoFamiliaBean == null)
        { catalogoFamiliaBean = new CatalogoFamiliaBean(); }
        return catalogoFamiliaBean;
    }

    public void setCatalogoFamiliaBean(CatalogoFamiliaBean catalogoFamiliaBean) {
        this.catalogoFamiliaBean = catalogoFamiliaBean;
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
    
    
}
