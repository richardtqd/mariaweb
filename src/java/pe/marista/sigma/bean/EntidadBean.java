/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;
import pe.marista.sigma.MaristaConstantes;

public class EntidadBean implements Serializable {

    private String ruc;
    private String nombre;
    private String nombreComercial;
//    private OrdenCompraBean ordenCompraBean;
    private CodigoBean tipoEntidadBean;//idTipoEntidad
    private CodigoBean tipoRubroBean;//idTipoEntidad
    private PaisBean paisBean;//idPais
    private DistritoBean distritoBean;//idDistrito
    private UnidadNegocioBean unidadNegocioBean;//
//    private ProveedorBean proveedorBean;
    private String rucPadre;
    private String direccion;
    private String telefono;
    private String correo;
    private String contacto;
    private String url;
    private boolean flgProveedor;
    private boolean flgPrevisional;
    private boolean flgSalud;
    private boolean flgEduSup;
    private boolean flgFinanciera;
//    private String entidadVista;
//    private Boolean status;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private String modiVer;
    private EntidadBean entidadPadreBean;  
    private String detraccionBanco;
    private String detraccionCuenta;
    
    //ayuda
    private String flgPrevisionalVista;
    private String flgSaludVista;
    private String flgEduSupVista;
    private String flgFinancieraVista;
    private String flgProveedorVista;
    
    //Nuevos
    private String partidaRegistral;
    private Boolean sexoRepresentante;
    private String dniRepresentante;
    private String representante; 
     
    private String numCtaSol;
    private String cciSol;
    private String rucBancoSol;
    private String nombreBancoSol;
    private String numCtaDol;
    private String cciDol;
    private String rucBancoDol; 
    private String nombreBancoDol;
    private String descripTransfCta;
    
    
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

    public EntidadBean() {
    }

    public EntidadBean(String nombre, String ruc, CodigoBean tipoRubroBean, CodigoBean tipoEntidadBean) {
        this.nombre = nombre;
        this.ruc = ruc;
//        this.tipoRubroBean = tipoRubroBean;
        this.tipoEntidadBean = tipoEntidadBean;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }
  
    public CodigoBean getTipoEntidadBean() {
        if (tipoEntidadBean == null) {
            tipoEntidadBean = new CodigoBean();
        }
        return tipoEntidadBean;
    }

    public void setTipoEntidadBean(CodigoBean tipoEntidadBean) {
        this.tipoEntidadBean = tipoEntidadBean;
    }

    public PaisBean getPaisBean() {
        if (paisBean == null) {
            paisBean = new PaisBean();
        }
        return paisBean;
    }

    public void setPaisBean(PaisBean paisBean) {
        this.paisBean = paisBean;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

//    public String getEntidadVista() {
//        if (nombre == null) {
//            entidadVista = MaristaConstantes.SIN_ENTIDAD;
//            return entidadVista;
//        }
//        if (nombre != null) {
//            entidadVista = nombre;
//            return entidadVista;
//        }
//        return entidadVista;
//    }
//
//    public void setEntidadVista(String entidadVista) {
//        this.entidadVista = entidadVista;
//    }
//
//    public Boolean getStatus() {
//        return status;
//    }
//
//    public void setStatus(Boolean status) {
//        this.status = status;
//    }

    public DistritoBean getDistritoBean() {
        if (distritoBean == null) {
            distritoBean = new DistritoBean();
        }
        return distritoBean;
    }

    public void setDistritoBean(DistritoBean distritoBean) {
        this.distritoBean = distritoBean;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getFlgPrevisional() {
        return flgPrevisional;
    }

    public void setFlgPrevisional(Boolean flgPrevisional) {
        this.flgPrevisional = flgPrevisional;
    }

    public Boolean getFlgSalud() {
        return flgSalud;
    }

    public void setFlgSalud(Boolean flgSalud) {
        this.flgSalud = flgSalud;
    }

    public Boolean getFlgEduSup() {
        return flgEduSup;
    }

    public void setFlgEduSup(Boolean flgEduSup) {
        this.flgEduSup = flgEduSup;
    }

    public Boolean getFlgFinanciera() {
        return flgFinanciera;
    }

    public void setFlgFinanciera(Boolean flgFinanciera) {
        this.flgFinanciera = flgFinanciera;
    }

    public EntidadBean getEntidadPadreBean() {
        if (entidadPadreBean == null) {
            entidadPadreBean = new EntidadBean();
        }
        return entidadPadreBean;
    }

    public void setEntidadPadreBean(EntidadBean entidadPadreBean) {
        this.entidadPadreBean = entidadPadreBean;
    }
//    public OrdenCompraBean getOrdenCompraBean() {
//        if (ordenCompraBean == null) {
//            ordenCompraBean = new OrdenCompraBean();
//        }
//        return ordenCompraBean;
//    }
//
//    public void setOrdenCompraBean(OrdenCompraBean ordenCompraBean) {
//        this.ordenCompraBean = ordenCompraBean;
//    }

    public String getFlgPrevisionalVista() {
        if (flgPrevisional == true) {
            flgPrevisionalVista = MaristaConstantes.SI;
        }
        if (flgPrevisional == false) {
            flgPrevisionalVista = MaristaConstantes.NO;
        }
        return flgPrevisionalVista;

    }

    public void setFlgPrevisionalVista(String flgPrevisionalVista) {
        this.flgPrevisionalVista = flgPrevisionalVista;
    }

    public String getFlgSaludVista() {
        if (flgSalud == true) {
            flgSaludVista = MaristaConstantes.SI;
        }
        if (flgSalud == false) {
            flgSaludVista = MaristaConstantes.NO;
        }
        return flgSaludVista;
    }

    public void setFlgSaludVista(String flgSaludVista) {
        this.flgSaludVista = flgSaludVista;
    }

    public String getFlgEduSupVista() {
        if (flgEduSup == true) {
            flgEduSupVista = MaristaConstantes.SI;
        }
        if (flgEduSup == false) {
            flgEduSupVista = MaristaConstantes.NO;
        }
        return flgEduSupVista;
    }

    public void setFlgEduSupVista(String flgEduSupVista) {
        this.flgEduSupVista = flgEduSupVista;
    }

    public String getFlgFinancieraVista() {
        if (flgFinanciera == true) {
            flgFinancieraVista = MaristaConstantes.SI;
        }
        if (flgFinanciera == false) {
            flgFinancieraVista = MaristaConstantes.NO;
        }
        return flgFinancieraVista;
    }

    public void setFlgFinancieraVista(String flgFinancieraVista) {
        this.flgFinancieraVista = flgFinancieraVista;
    }

    public String getRucPadre() {
        return rucPadre;
    }

    public void setRucPadre(String rucPadre) {
        this.rucPadre = rucPadre;
    }

    public Boolean getFlgProveedor() {
        return flgProveedor;
    }

    public void setFlgProveedor(Boolean flgProveedor) {
        this.flgProveedor = flgProveedor;
    }

    public String getModiVer() {
        return modiVer;
    }

    public void setModiVer(String modiVer) {
        this.modiVer = modiVer;
    }

    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean==null){
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }
 
    public String getFlgProveedorVista() {
        if (flgProveedor == true) {
            flgProveedorVista = MaristaConstantes.SI;
        }
        if (flgProveedor == false) {
            flgProveedorVista = MaristaConstantes.NO;
        }
        return flgProveedorVista;
    }

    public void setFlgProveedorVista(String flgProveedorVista) {
        this.flgProveedorVista = flgProveedorVista;
    }
 
  
    public Date getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(Date creaFecha) {
        this.creaFecha = creaFecha;
    }

//    public ProveedorBean getProveedorBean() {
//        if(proveedorBean==null)
//        {
//            proveedorBean= new ProveedorBean();
//        }
//        return proveedorBean;
//    }
//
//    public void setProveedorBean(ProveedorBean proveedorBean) {
//        this.proveedorBean = proveedorBean;
//    }

    public boolean isFlgProveedor() {
        return flgProveedor;
    }

    public void setFlgProveedor(boolean flgProveedor) {
        this.flgProveedor = flgProveedor;
    }

    public boolean isFlgPrevisional() {
        return flgPrevisional;
    }

    public void setFlgPrevisional(boolean flgPrevisional) {
        this.flgPrevisional = flgPrevisional;
    }

    public boolean isFlgSalud() {
        return flgSalud;
    }

    public void setFlgSalud(boolean flgSalud) {
        this.flgSalud = flgSalud;
    }

    public boolean isFlgEduSup() {
        return flgEduSup;
    }

    public void setFlgEduSup(boolean flgEduSup) {
        this.flgEduSup = flgEduSup;
    }

    public boolean isFlgFinanciera() {
        return flgFinanciera;
    }

    public void setFlgFinanciera(boolean flgFinanciera) {
        this.flgFinanciera = flgFinanciera;
    } 

    public String getDetraccionBanco() {
        return detraccionBanco;
    }

    public void setDetraccionBanco(String detraccionBanco) {
        this.detraccionBanco = detraccionBanco;
    }

    public String getDetraccionCuenta() {
        return detraccionCuenta;
    }

    public void setDetraccionCuenta(String detraccionCuenta) {
        this.detraccionCuenta = detraccionCuenta;
    }

    public CodigoBean getTipoRubroBean() {
        if (tipoRubroBean==null) {
            tipoRubroBean= new CodigoBean();
        }
        return tipoRubroBean;
    }

    public void setTipoRubroBean(CodigoBean tipoRubroBean) {
        this.tipoRubroBean = tipoRubroBean;
    }

    public String getPartidaRegistral() {
        return partidaRegistral;
    }

    public void setPartidaRegistral(String partidaRegistral) {
        this.partidaRegistral = partidaRegistral;
    } 

    public String getDniRepresentante() {
        return dniRepresentante;
    }

    public void setDniRepresentante(String dniRepresentante) {
        this.dniRepresentante = dniRepresentante;
    }

    public String getRepresentante() {
        return representante;
    }

    public void setRepresentante(String representante) {
        this.representante = representante;
    } 

    public Boolean getSexoRepresentante() {
        return sexoRepresentante;
    }

    public void setSexoRepresentante(Boolean sexoRepresentante) {
        this.sexoRepresentante = sexoRepresentante;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getNumCtaSol() {
        return numCtaSol;
    }

    public void setNumCtaSol(String numCtaSol) {
        this.numCtaSol = numCtaSol;
    }

    public String getCciSol() {
        return cciSol;
    }

    public void setCciSol(String cciSol) {
        this.cciSol = cciSol;
    }

    public String getRucBancoSol() {
        return rucBancoSol;
    }

    public void setRucBancoSol(String rucBancoSol) {
        this.rucBancoSol = rucBancoSol;
    }

    public String getNumCtaDol() {
        return numCtaDol;
    }

    public void setNumCtaDol(String numCtaDol) {
        this.numCtaDol = numCtaDol;
    }

    public String getCciDol() {
        return cciDol;
    }

    public void setCciDol(String cciDol) {
        this.cciDol = cciDol;
    }

    public String getRucBancoDol() {
        return rucBancoDol;
    }

    public void setRucBancoDol(String rucBancoDol) {
        this.rucBancoDol = rucBancoDol;
    }

    public String getNombreBancoSol() {
        return nombreBancoSol;
    }

    public void setNombreBancoSol(String nombreBancoSol) {
        this.nombreBancoSol = nombreBancoSol;
    }

    public String getNombreBancoDol() {
        return nombreBancoDol;
    }

    public void setNombreBancoDol(String nombreBancoDol) {
        this.nombreBancoDol = nombreBancoDol;
    }

    public String getDescripTransfCta() {
        return descripTransfCta;
    }

    public void setDescripTransfCta(String descripTransfCta) {
        this.descripTransfCta = descripTransfCta;
    }
    
    
    
}
