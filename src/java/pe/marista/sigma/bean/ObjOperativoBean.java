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
public class ObjOperativoBean implements Serializable{
    
    private UnidadNegocioBean unidadNegocioBean;
    private UnidadOrganicaBean unidadOrganicaBean;
//    private AnioBean anioBean;
    private Integer anio;
    private PlanOperativoBean planOperativoBean;
    private Integer idObjOperativo; 
    private String codigo;
    private String nombre;
    private PlanEstrategicoBean planEstrategicoBean;
    private CodigoBean codigoBean;//idTipoActividad
    private CodigoBean codigoBean1;//idTipoUniMed
    private ObjetivoEstrategicaBean objetivoEstrategicaBean;
    private ObjetivoEstrategicoDetBean objetivoEstrategicoDetBean;
    private LineaEstrategicaBean lineaEstrategicaBean;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private String modiVer;
    
    public ObjOperativoBean(Integer idObjOperativo, PlanOperativoBean planOperativoBean, String codigo, String nombre, ObjetivoEstrategicaBean objetivoEstrategicaBean) {
        this.idObjOperativo = idObjOperativo;
        this.planOperativoBean = planOperativoBean;
        this.codigo = codigo;
        this.nombre = nombre;
        this.objetivoEstrategicaBean = objetivoEstrategicaBean;
    }

    public ObjOperativoBean() {
    }

    public Integer getIdObjOperativo() {
        return idObjOperativo;
    }

    public void setIdObjOperativo(Integer idObjOperativo) {
        this.idObjOperativo = idObjOperativo;
    }

    public ObjetivoEstrategicaBean getObjetivoEstrategicaBean() {
        if(objetivoEstrategicaBean == null){
            objetivoEstrategicaBean = new ObjetivoEstrategicaBean();
        }
        return objetivoEstrategicaBean;
    }

    public void setObjetivoEstrategicaBean(ObjetivoEstrategicaBean objetivoEstrategicaBean) {
        this.objetivoEstrategicaBean = objetivoEstrategicaBean;
    }

    public ObjetivoEstrategicoDetBean getObjetivoEstrategicoDetBean() {
        if(objetivoEstrategicoDetBean == null){
            objetivoEstrategicoDetBean = new ObjetivoEstrategicoDetBean();
        }
        return objetivoEstrategicoDetBean;
    }

    public void setObjetivoEstrategicoDetBean(ObjetivoEstrategicoDetBean objetivoEstrategicoDetBean) {
        this.objetivoEstrategicoDetBean = objetivoEstrategicoDetBean;
    }    

    public PlanOperativoBean getPlanOperativoBean() {
        if(planOperativoBean == null)
        {
            planOperativoBean = new PlanOperativoBean();
        }
        return planOperativoBean;
    }

    public void setPlanOperativoBean(PlanOperativoBean planOperativoBean) {
        this.planOperativoBean = planOperativoBean;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public CodigoBean getCodigoBean() {
        if(codigoBean == null)
        {
            codigoBean = new CodigoBean();
        }
        return codigoBean;
    }

    public void setCodigoBean(CodigoBean codigoBean) {
        this.codigoBean = codigoBean;
    }

    public CodigoBean getCodigoBean1() {
        if(codigoBean1 == null)
        {
            codigoBean1 = new CodigoBean();
        }
        return codigoBean1;
    }

    public void setCodigoBean1(CodigoBean codigoBean1) {
        this.codigoBean1 = codigoBean1;
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

    public PlanEstrategicoBean getPlanEstrategicoBean() {
        if(planEstrategicoBean == null){
            planEstrategicoBean = new PlanEstrategicoBean();
        }
        return planEstrategicoBean;
    }

    public void setPlanEstrategicoBean(PlanEstrategicoBean planEstrategicoBean) {
        this.planEstrategicoBean = planEstrategicoBean;
    }
 
     
    public LineaEstrategicaBean getLineaEstrategicaBean() {
        if(lineaEstrategicaBean == null){
            lineaEstrategicaBean = new LineaEstrategicaBean();
        }
        return lineaEstrategicaBean;
    }

    public void setLineaEstrategicaBean(LineaEstrategicaBean lineaEstrategicaBean) {
        this.lineaEstrategicaBean = lineaEstrategicaBean;
    }

    public UnidadNegocioBean getUnidadNegocioBean() {
        if(unidadNegocioBean == null){
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public UnidadOrganicaBean getUnidadOrganicaBean() {
        if(unidadOrganicaBean == null){
            unidadOrganicaBean = new UnidadOrganicaBean();
        }
        return unidadOrganicaBean;
    }

    public void setUnidadOrganicaBean(UnidadOrganicaBean unidadOrganicaBean) {
        this.unidadOrganicaBean = unidadOrganicaBean;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }    
        
}
