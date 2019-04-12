/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;

/**
 *
 * @author MS001
 */
public class EvaluacionBean  implements Serializable  {
    
    private Integer idevaluadoevaluador;
    private Integer idcodigo;
    private Integer codigoevaluado;
    private String nombre;
    private String apepat;
    private String apemat;
    private Integer idcargoevaluado;
    private String nomcargo;
    private Integer flag;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getIdevaluadoevaluador() {
        return idevaluadoevaluador;
    }

    public void setIdevaluadoevaluador(Integer idevaluadoevaluador) {
        this.idevaluadoevaluador = idevaluadoevaluador;
    }



    public Integer getCodigoevaluado() {
        return codigoevaluado;
    }

    public void setCodigoevaluado(Integer codigoevaluado) {
        this.codigoevaluado = codigoevaluado;
    }

    public String getApepat() {
        return apepat;
    }

    public void setApepat(String apepat) {
        this.apepat = apepat;
    }

    public String getApemat() {
        return apemat;
    }

    public void setApemat(String apemat) {
        this.apemat = apemat;
    }

    public Integer getIdcodigo() {
        return idcodigo;
    }

    public void setIdcodigo(Integer idcodigo) {
        this.idcodigo = idcodigo;
    }

    public Integer getIdcargoevaluado() {
        return idcargoevaluado;
    }

    public void setIdcargoevaluado(Integer idcargoevaluado) {
        this.idcargoevaluado = idcargoevaluado;
    }

    public String getNomcargo() {
        return nomcargo;
    }

    public void setNomcargo(String nomcargo) {
        this.nomcargo = nomcargo;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
    
    
}
