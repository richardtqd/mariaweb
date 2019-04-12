/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author MS002
 */
public class ObjetivoEstrategicaBean implements Serializable {

    private UnidadNegocioBean unidadNegocioBean;
    private PlanEstrategicoBean planEstrategicoBean;
    private LineaEstrategicaBean lineaEstrategicaBean;
    private Integer idObjEstrategico;
    private String nombre;
    private String descripcion;
    private String status;
//    private List<ObjetivoEspecificoBean> listaObjetivoEspecifico;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private String modiVer;

    //Ayuda
    private String nombreObjetivoEsSub;
    private String DescObjetivoEsSub;

//    public ObjetivoEstrategicaBean(Integer idObjEstrategico, LineaEstrategicaBean lineaEstrategicaBean, String codigoObjEstrategico, String nombreObjEstrategico) {
//        this.idObjEstrategico = idObjEstrategico;
//        this.lineaEstrategicaBean = lineaEstrategicaBean;
//        
//    }
//
//    public ObjetivoEstrategicaBean(Integer idObjEstrategico, LineaEstrategicaBean lineaEstrategicaBean, String nombre, String descripcion, String status) {
//        this.idObjEstrategico = idObjEstrategico;
//        this.lineaEstrategicaBean = lineaEstrategicaBean;
//        this.nombre = nombre;
//        this.descripcion = descripcion;
//        this.status = status;
//    }
    public ObjetivoEstrategicaBean() {
    }

    public Integer getIdObjEstrategico() {
        return idObjEstrategico;
    }

    public void setIdObjEstrategico(Integer idObjEstrategico) {
        this.idObjEstrategico = idObjEstrategico;
    }

    public LineaEstrategicaBean getLineaEstrategicaBean() {
        if (lineaEstrategicaBean == null) {
            lineaEstrategicaBean = new LineaEstrategicaBean();
        }
        return lineaEstrategicaBean;
    }

    public void setLineaEstrategicaBean(LineaEstrategicaBean lineaEstrategicaBean) {
        this.lineaEstrategicaBean = lineaEstrategicaBean;
    }

//    public List<ObjetivoEspecificoBean> getListaObjetivoEspecifico() {
//        if(listaObjetivoEspecifico == null)
//        {
//            listaObjetivoEspecifico = new ArrayList<>();
//        }
//        return listaObjetivoEspecifico;
//    }
//
//    public void setListaObjetivoEspecifico(List<ObjetivoEspecificoBean> listaObjetivoEspecifico) {
//        this.listaObjetivoEspecifico = listaObjetivoEspecifico;
//    }
    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean == null) {
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public PlanEstrategicoBean getPlanEstrategicoBean() {
        if (planEstrategicoBean == null) {
            planEstrategicoBean = new PlanEstrategicoBean();
        }
        return planEstrategicoBean;
    }

    public void setPlanEstrategicoBean(PlanEstrategicoBean planEstrategicoBean) {
        this.planEstrategicoBean = planEstrategicoBean;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public String getModiVer() {
        return modiVer;
    }

    public void setModiVer(String modiVer) {
        this.modiVer = modiVer;
    }

    public String getNombreObjetivoEsSub() {
        return nombreObjetivoEsSub;
    }

    public void setNombreObjetivoEsSub(String nombreObjetivoEsSub) {
        this.nombreObjetivoEsSub = nombreObjetivoEsSub;
    }

    public String getDescObjetivoEsSub() {
        return DescObjetivoEsSub;
    }

    public void setDescObjetivoEsSub(String DescObjetivoEsSub) {
        this.DescObjetivoEsSub = DescObjetivoEsSub;
    }

}
