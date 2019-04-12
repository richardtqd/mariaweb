/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;
import pe.marista.sigma.MaristaConstantes;

/**
 *
 * @author MS001
 */
public class CargoBean implements Serializable{
    private Integer idCargo;
    private String nombre;
    private String codigo;
    private CodigoBean tipoCategoriaCargoBean;//idTipoCategoriaCargo
    private boolean status = true;
    private String creaPor;
    private String modiPor;
    private Date creaFecha;
    private Boolean flgAsignacion;
    
    private String statusVista;
    
    private CodigoBean tipoGrupoOcupacionalBean;
     

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
 
    

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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

    public CodigoBean getTipoCategoriaCargoBean() {
        if(tipoCategoriaCargoBean==null){
            tipoCategoriaCargoBean= new CodigoBean();
        }
        return tipoCategoriaCargoBean;
    }

    public void setTipoCategoriaCargoBean(CodigoBean tipoCategoriaCargoBean) {
        this.tipoCategoriaCargoBean = tipoCategoriaCargoBean;
    }
 
    public String getStatusVista() {
         if( status == true){
        statusVista = MaristaConstantes.ESTADO_ACTIVO_DES;
        }
        if( status == false){
        statusVista = MaristaConstantes.ESTADO_INACTIVO_DES;
        }
        return statusVista;
    }

    public void setStatusVista(String statusVista) {
        this.statusVista = statusVista;
    }

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public Boolean getFlgAsignacion() {
        return flgAsignacion;
    }

    public void setFlgAsignacion(Boolean flgAsignacion) {
        this.flgAsignacion = flgAsignacion;
    }

    public CodigoBean getTipoGrupoOcupacionalBean() {
        if (tipoGrupoOcupacionalBean==null){
            tipoGrupoOcupacionalBean= new CodigoBean();
        }
        return tipoGrupoOcupacionalBean;
    }

    public void setTipoGrupoOcupacionalBean(CodigoBean tipoGrupoOcupacionalBean) {
        this.tipoGrupoOcupacionalBean = tipoGrupoOcupacionalBean;
    }

    
    
    
}
