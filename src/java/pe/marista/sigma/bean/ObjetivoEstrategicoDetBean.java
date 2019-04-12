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
public class ObjetivoEstrategicoDetBean implements Serializable {

    private UnidadNegocioBean unidadNegocioBean;
    private PlanEstrategicoBean planEstrategicoBean;
    private LineaEstrategicaBean lineaEstrategicaBean;
    private ObjetivoEstrategicaBean objetivoEstrategicaBean;
    private Integer idObjEstrategicoDet;
    private IndicadorBean indicadorBean;
    private CodigoBean codigoBean;//Tipo_Valor
    private Integer meta;
    private String responsable;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private String modiVer;

    //TreeNode
    private String nombre;
    private String desc;
    
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

    public LineaEstrategicaBean getLineaEstrategicaBean() {
        if (lineaEstrategicaBean == null) {
            lineaEstrategicaBean = new LineaEstrategicaBean();
        }
        return lineaEstrategicaBean;
    }

    public void setLineaEstrategicaBean(LineaEstrategicaBean lineaEstrategicaBean) {
        this.lineaEstrategicaBean = lineaEstrategicaBean;
    }

    public ObjetivoEstrategicaBean getObjetivoEstrategicaBean() {
        if (objetivoEstrategicaBean == null) {
            objetivoEstrategicaBean = new ObjetivoEstrategicaBean();
        }
        return objetivoEstrategicaBean;
    }

    public void setObjetivoEstrategicaBean(ObjetivoEstrategicaBean objetivoEstrategicaBean) {
        this.objetivoEstrategicaBean = objetivoEstrategicaBean;
    }

    public IndicadorBean getIndicadorBean() {
        if (indicadorBean == null) {
            indicadorBean = new IndicadorBean();
        }
        return indicadorBean;
    }

    public void setIndicadorBean(IndicadorBean indicadorBean) {
        this.indicadorBean = indicadorBean;
    }

    public CodigoBean getCodigoBean() {
        if (codigoBean == null) {
            codigoBean = new CodigoBean();
        }
        return codigoBean;
    }

    public void setCodigoBean(CodigoBean codigoBean) {
        this.codigoBean = codigoBean;
    }

    public Integer getMeta() {
        return meta;
    }

    public void setMeta(Integer meta) {
        this.meta = meta;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getCreaPor() {
        return creaPor;
    }

    public void setCreaPor(String creaPor) {
        this.creaPor = creaPor;
    }

//    public Date getFechaCrea() {
//        return fechaCrea;
//    }
//
//    public void setFechaCrea(Date fechaCrea) {
//        this.fechaCrea = fechaCrea;
//    }
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

    public Date getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(Date creaFecha) {
        this.creaFecha = creaFecha;
    }

    public Integer getIdObjEstrategicoDet() {
        return idObjEstrategicoDet;
    }

    public void setIdObjEstrategicoDet(Integer idObjEstrategicoDet) {
        this.idObjEstrategicoDet = idObjEstrategicoDet;
    }

    //TreeNode 
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    
//    public ObjetivoEstrategicoDetBean(String nombre, String desc) {
//        this.nombre = nombre;
//        this.desc = desc; 
//    }

}
