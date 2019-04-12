/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author MS002
 */
public class PeriodoBean implements Serializable {

    private UnidadNegocioBean uniNeg;
    private PlanEstrategicoBean planEstrategicoBean;
    private LineaEstrategicaBean lineaEstrategicaBean;
    private ObjetivoEstrategicaBean objetivoEstrategicaBean;
    private ObjetivoEstrategicoDetBean objetivoEstrategicoDetBean;
    private AnioBean anioBean;
    private Integer idPeriodo;
    private Integer anio;
    private IndicadorBean indicadorBean;
    private CodigoBean codigoValor;
    private Integer meta;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private String modiVer;
    private String idComodin;

    //Ayuda 
    private Integer idIndicador;
    private Integer an;
    
    public UnidadNegocioBean getUniNeg() {
        if(uniNeg == null){
            uniNeg = new UnidadNegocioBean();
        }
        return uniNeg;
    }

    public void setUniNeg(UnidadNegocioBean uniNeg) {
        this.uniNeg = uniNeg;
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

    public AnioBean getAnioBean() {
        if (anioBean == null) {
            anioBean = new AnioBean();
        }
        return anioBean;
    }

    public void setAnioBean(AnioBean anioBean) {
        this.anioBean = anioBean;
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

    public CodigoBean getCodigoValor() {
        if (codigoValor == null) {
            codigoValor = new CodigoBean();
        }
        return codigoValor;
    }

    public void setCodigoValor(CodigoBean codigoValor) {
        this.codigoValor = codigoValor;
    }

    public Integer getMeta() {
        return meta;
    }

    public void setMeta(Integer meta) {
        this.meta = meta;
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

    public ObjetivoEstrategicoDetBean getObjetivoEstrategicoDetBean() {
        if (objetivoEstrategicoDetBean == null) {
            objetivoEstrategicoDetBean = new ObjetivoEstrategicoDetBean();
        }
        return objetivoEstrategicoDetBean;
    }

    public void setObjetivoEstrategicoDetBean(ObjetivoEstrategicoDetBean objetivoEstrategicoDetBean) {
        this.objetivoEstrategicoDetBean = objetivoEstrategicoDetBean;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getIdComodin() {
        return idComodin;
    }

    public void setIdComodin(String idComodin) {
        this.idComodin = idComodin;
    }

    public Integer getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(Integer idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public Integer getIdIndicador() {
        return idIndicador;
    }

    public void setIdIndicador(Integer idIndicador) {
        this.idIndicador = idIndicador;
    }

    public Integer getAn() {
        return an;
    }

    public void setAn(Integer an) {
        this.an = an;
    }
    
}
