/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.type.JdbcType;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.ProcesoFinalService;
import pe.marista.sigma.util.MaristaUtils;

/**
 *
 * @author MS002
 */
public class ProcesoFinalBean implements Serializable {

    private Integer idProcesoFinal;
    private Integer posicion;
    private String valor;
    private Integer idReferencia;

    //Propiedades de la Clase
    private Integer id;
    private String uniNeg;
    private Map<Integer, String> elementos;
    private String creaPor;
    private String modiPor;
    private String creaFecha;

    //Ayuda
    private String ruc;
    private String constante;
    private Integer valorConstante;

    public Integer getIdProcesoFinal() {
        return idProcesoFinal;
    }

    public void setIdProcesoFinal(Integer idProcesoFinal) {
        this.idProcesoFinal = idProcesoFinal;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Integer getIdReferencia() {
        return idReferencia;
    }

    public void setIdReferencia(Integer idReferencia) {
        this.idReferencia = idReferencia;
    }

    public Integer getPosicion() {
        return posicion;
    }

    public void setPosicion(Integer posicion) {
        this.posicion = posicion;
    }

    //Propiedades de la Clase
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
    }

    public Map<Integer, String> getElementos() throws Exception {
        if (elementos == null) {
            elementos = new HashMap<>();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");

            ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
            Integer var_ini = 0;/* Posicion Inicial */ 
            var_ini = procesoFinalService.obtenerPosItem(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), getRuc(), 0, 1);

            Integer var_fin = 0;/* Posicion Final */ 
            var_fin = procesoFinalService.obtenerPosItem(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), getRuc(), 1, 1);

            for (int i = 0; i <= var_fin; i++) {
                var_ini++;
                elementos.put(var_ini, ("elemento_" + var_ini.toString()));
            }
        }
        return elementos;
    }

    public void setElementos(Map<Integer, String> elementos) {
        this.elementos = elementos;
    }

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

    public String getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(String creaFecha) {
        this.creaFecha = creaFecha;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String obtenerRuc(String ruc) {
        String valor = "";
        valor = ruc;
        return valor;
    }

    public String getConstante() {
        return constante;
    }

    public void setConstante(String constante) {
        this.constante = constante;
    }

    public Integer getValorConstante() {
        return valorConstante;
    }

    public void setValorConstante(Integer valorConstante) {
        this.valorConstante = valorConstante;
    }

}
