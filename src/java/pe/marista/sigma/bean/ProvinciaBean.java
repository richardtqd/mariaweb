/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.marista.sigma.bean;

import java.io.Serializable;
import pe.marista.sigma.MaristaConstantes;

/**
 *
 * @author Administrador
 */
public class ProvinciaBean implements Serializable{
    private Integer idProvincia;
    private String nombre;
    private DepartamentoBean departamentoBean;//idDepartamento

    public ProvinciaBean() {
        this.idProvincia = MaristaConstantes.PROV_LIMA;
    }
    
    public Integer getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(Integer idProvincia) {
        this.idProvincia = idProvincia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public DepartamentoBean getDepartamentoBean() {
        if(departamentoBean==null){
            departamentoBean=new DepartamentoBean();
        }
        return departamentoBean;
    }

    public void setDepartamentoBean(DepartamentoBean departamentoBean) {
        this.departamentoBean = departamentoBean;
    }
    
    
}
