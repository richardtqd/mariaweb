/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author MS002
 */
public class ProveedorBean implements Serializable{
    

     private UnidadNegocioBean unidadNegocioBean;
     private String uniNeg;
     private EntidadBean entidadBean;
     private Boolean statud;
//     private String correoProv;
//    
//     private boolean estado;
//     
//     private String representante;
//     private String cargo;
//     private Integer celularRepre;
//       
//     private String creadoPor;
//     private Date fechaCrea;

     private Integer idProveedor;
     private String codProveedor;
     private String razonSocial;
     private String ruc;
     private String domicilio;
     private DistritoBean IdDistritoDomBean;
     private String sitioWeB;          
     private Integer idTelefono1;
     private Integer idTelefono2;
     private Integer fax;
     
     private String correoProv;
    
     private boolean estado;
     
     private String representante;
     private String cargo;
     private Integer celularRepre;
       
     private String creaPor;
     private Date creaFecha;

    public ProveedorBean(String codProveedor, String razonSocial, String ruc, String domicilio, Integer idTelefono1) {
        this.codProveedor = codProveedor;
        this.razonSocial = razonSocial;
        this.ruc = ruc;
        this.domicilio = domicilio;
        this.idTelefono1 = idTelefono1;
    }
 
     
     
     
    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getCodProveedor() {
        return codProveedor;
    }

    public void setCodProveedor(String codProveedor) {
        this.codProveedor = codProveedor;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public DistritoBean getIdDistritoDomBean() {
        return IdDistritoDomBean;
    }

    public void setIdDistritoDomBean(DistritoBean IdDistritoDomBean) {
        this.IdDistritoDomBean = IdDistritoDomBean;
    }

    public String getSitioWeB() {
        return sitioWeB;
    }


     //     private Integer idProveedor;
//     private String codProveedor;
//     private String razonSocial;
//     private String ruc;
//     private String domicilio;
//     private DistritoBean IdDistritoDomBean;
//     private String sitioWeB;          
//     private Integer idTelefono1;
//     private Integer idTelefono2;
//     private Integer fax;

    public UnidadNegocioBean getUnidadNegocioBean() 
    {   if(unidadNegocioBean == null){
        unidadNegocioBean = new UnidadNegocioBean();
    }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public EntidadBean getEntidadBean() {
        if(entidadBean == null){
        entidadBean = new EntidadBean();
        }
        return entidadBean;
    }

    public void setEntidadBean(EntidadBean entidadBean) {
        this.entidadBean = entidadBean;
    }

    public Boolean getStatud() {
        return statud;
    }

    public void setStatud(Boolean statud) {
        this.statud = statud;
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

    public Integer getIdTelefono1() {
        return idTelefono1;
    }

    public void setIdTelefono1(Integer idTelefono1) {
        this.idTelefono1 = idTelefono1;
    }

    public Integer getIdTelefono2() {
        return idTelefono2;
    }

    public void setIdTelefono2(Integer idTelefono2) {
        this.idTelefono2 = idTelefono2;
    }

   public boolean isEstado() {    
        return estado;
    }
    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public ProveedorBean() {
    }

    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
    }


    
    
    
}
