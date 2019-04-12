/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Administrador
 */
public class UnidadNegocioBean implements Serializable{

    private String uniNeg;
    private String nombreUniNeg;
    private EntidadBean entidadBean;
    private String ruc;
    private Date fecfundacion;
    private CodigoBean codigoBean;//idTipoUniNeg
    private UnidadNegocioBean uniNegPadre; // idUniNegPadre ??????
    private DistritoBean distritoBean; //idDistrito
    private EntidadSedeBean entidadSedeBean;// idEntSede

    //private String creadoPor;
    //private Date fechaCrea;
    private ProveedorBean proveedorBean;
    
    //Ayuda

    private String creaPor;
    private Date creaFecha;
     private String modiPor;
    
    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }


    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
    }

    public String getNombreUniNeg() {
        return nombreUniNeg;
    }

    public void setNombreUniNeg(String nombreUniNeg) {
        this.nombreUniNeg = nombreUniNeg;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public UnidadNegocioBean getUniNegPadre() {
        if (uniNegPadre == null) {
            uniNegPadre = new UnidadNegocioBean();
        }
        return uniNegPadre; 
    }

    public void setUniNegPadre(UnidadNegocioBean uniNegPadre) {
        this.uniNegPadre = uniNegPadre;
    }

    public DistritoBean getDistritoBean() {
        if (distritoBean == null) {
            distritoBean = new DistritoBean();
        }return distritoBean;
    }

    public void setDistritoBean(DistritoBean distritoBean) {
        this.distritoBean = distritoBean;
    }

    public EntidadSedeBean getEntidadSedeBean() {
        if (entidadSedeBean == null) {
            entidadSedeBean = new EntidadSedeBean();
        }
        return entidadSedeBean;
    }

    public void setEntidadSedeBean(EntidadSedeBean entidadSedeBean) {
        this.entidadSedeBean = entidadSedeBean;
    }

    public CodigoBean getCodigoBean() {
        if(codigoBean==null){
            codigoBean = new CodigoBean();
        }
        return codigoBean;
    }

    public void setCodigoBean(CodigoBean codigoBean) {
        this.codigoBean = codigoBean;
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

    public Date getFecfundacion() {
        return fecfundacion;
    }

    public void setFecfundacion(Date fecfundacion) {
        this.fecfundacion = fecfundacion;
    }

    public ProveedorBean getProveedorBean() {
        if(proveedorBean == null){
            proveedorBean = new ProveedorBean();
        }
        return proveedorBean;
    }

    public void setProveedorBean(ProveedorBean proveedorBean) {
        this.proveedorBean = proveedorBean;
    }

    public EntidadBean getEntidadBean() {
        if(entidadBean==null)
        {
            entidadBean=new EntidadBean();
        }
        return entidadBean;
    }

    public void setEntidadBean(EntidadBean entidadBean) {
        this.entidadBean = entidadBean;
    }
    
    
    
}
