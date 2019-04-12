/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.marista.sigma.bean;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 *
 * @author MS002
 */
public class TareaBean implements Serializable{
    
    private Integer idTarea;
    private ActividadBean actividadBean;//idActividad
    private IndicadorBean indicadorBean;//idIndicador
    private CodigoBean codigoBean;//idTipoUniMed
    private PresupuestoUniOrgBean presupuestoUniOrgBean;//idPresupuestoUniOrg
    private String codigoTarea;
    private String nombreTarea;
    private CodigoBean codigoBean2; // idTipoTarea
    private String responsable;
    private BigDecimal egreso;
    private BigDecimal ingreso;
    private Integer metaProgramada;
    private Integer metaEjecutada;
    private Integer pro_01;
    private Integer pro_02;
    private Integer pro_03;
    private Integer pro_04;
    private Integer pro_05;
    private Integer pro_06;
    private Integer pro_07;
    private Integer pro_08;
    private Integer pro_09;
    private Integer pro_10;
    private Integer pro_11;
    private Integer pro_12;
    private Integer eje_01;
    private Integer eje_02;
    private Integer eje_03;
    private Integer eje_04;
    private Integer eje_05;
    private Integer eje_06;
    private Integer eje_07;
    private Integer eje_08;
    private Integer eje_09;
    private Integer eje_10;
    private Integer eje_11;
    private Integer eje_12;
    
    public Integer getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(Integer idTarea) {
        this.idTarea = idTarea;
    }

    public ActividadBean getActividadBean() {
        if(actividadBean == null)
        {
            actividadBean = new ActividadBean();
        }
        return actividadBean;
    }

    public void setActividadBean(ActividadBean actividadBean) {
        this.actividadBean = actividadBean;
    }

    public IndicadorBean getIndicadorBean() {
        if(indicadorBean == null)
        {
            indicadorBean = new IndicadorBean();
        }
        return indicadorBean;
    }

    public void setIndicadorBean(IndicadorBean indicadorBean) {
        this.indicadorBean = indicadorBean;
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

    public PresupuestoUniOrgBean getPresupuestoUniOrgBean() {
        if(presupuestoUniOrgBean == null)
        {
            presupuestoUniOrgBean= new PresupuestoUniOrgBean();
        }
        return presupuestoUniOrgBean;
    }

    public void setPresupuestoUniOrgBean(PresupuestoUniOrgBean presupuestoUniOrgBean) {
        this.presupuestoUniOrgBean = presupuestoUniOrgBean;
    }

    public String getCodigoTarea() {
        return codigoTarea;
    }

    public void setCodigoTarea(String codigoTarea) {
        this.codigoTarea = codigoTarea;
    }

    public String getNombreTarea() {
        return nombreTarea;
    }

    public void setNombreTarea(String nombreTarea) {
        this.nombreTarea = nombreTarea;
    }

    public CodigoBean getCodigoBean2() {
        if(codigoBean2 == null)
        {
            codigoBean2= new CodigoBean();
        }
        return codigoBean2;
    }

    public void setCodigoBean2(CodigoBean codigoBean2) {
        this.codigoBean2 = codigoBean2;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public BigDecimal getEgreso() {
        return egreso;
    }

    public void setEgreso(BigDecimal egreso) {
        this.egreso = egreso;
    }

    public BigDecimal getIngreso() {
        return ingreso;
    }

    public void setIngreso(BigDecimal ingreso) {
        this.ingreso = ingreso;
    }

    public Integer getMetaProgramada() {
        return metaProgramada;
    }

    public void setMetaProgramada(Integer metaProgramada) {
        this.metaProgramada = metaProgramada;
    }

    public Integer getMetaEjecutada() {
        return metaEjecutada;
    }

    public void setMetaEjecutada(Integer metaEjecutada) {
        this.metaEjecutada = metaEjecutada;
    }

    public Integer getPro_01() {
        return pro_01;
    }

    public void setPro_01(Integer pro_01) {
        this.pro_01 = pro_01;
    }

    public Integer getPro_02() {
        return pro_02;
    }

    public void setPro_02(Integer pro_02) {
        this.pro_02 = pro_02;
    }

    public Integer getPro_03() {
        return pro_03;
    }

    public void setPro_03(Integer pro_03) {
        this.pro_03 = pro_03;
    }

    public Integer getPro_04() {
        return pro_04;
    }

    public void setPro_04(Integer pro_04) {
        this.pro_04 = pro_04;
    }

    public Integer getPro_05() {
        return pro_05;
    }

    public void setPro_05(Integer pro_05) {
        this.pro_05 = pro_05;
    }

    public Integer getPro_06() {
        return pro_06;
    }

    public void setPro_06(Integer pro_06) {
        this.pro_06 = pro_06;
    }

    public Integer getPro_07() {
        return pro_07;
    }

    public void setPro_07(Integer pro_07) {
        this.pro_07 = pro_07;
    }

    public Integer getPro_08() {
        return pro_08;
    }

    public void setPro_08(Integer pro_08) {
        this.pro_08 = pro_08;
    }

    public Integer getPro_09() {
        return pro_09;
    }

    public void setPro_09(Integer pro_09) {
        this.pro_09 = pro_09;
    }

    public Integer getPro_10() {
        return pro_10;
    }

    public void setPro_10(Integer pro_10) {
        this.pro_10 = pro_10;
    }

    public Integer getPro_11() {
        return pro_11;
    }

    public void setPro_11(Integer pro_11) {
        this.pro_11 = pro_11;
    }

    public Integer getPro_12() {
        return pro_12;
    }

    public void setPro_12(Integer pro_12) {
        this.pro_12 = pro_12;
    }

    public Integer getEje_01() {
        return eje_01;
    }

    public void setEje_01(Integer eje_01) {
        this.eje_01 = eje_01;
    }

    public Integer getEje_02() {
        return eje_02;
    }

    public void setEje_02(Integer eje_02) {
        this.eje_02 = eje_02;
    }

    public Integer getEje_03() {
        return eje_03;
    }

    public void setEje_03(Integer eje_03) {
        this.eje_03 = eje_03;
    }

    public Integer getEje_04() {
        return eje_04;
    }

    public void setEje_04(Integer eje_04) {
        this.eje_04 = eje_04;
    }

    public Integer getEje_05() {
        return eje_05;
    }

    public void setEje_05(Integer eje_05) {
        this.eje_05 = eje_05;
    }

    public Integer getEje_06() {
        return eje_06;
    }

    public void setEje_06(Integer eje_06) {
        this.eje_06 = eje_06;
    }

    public Integer getEje_07() {
        return eje_07;
    }

    public void setEje_07(Integer eje_07) {
        this.eje_07 = eje_07;
    }

    public Integer getEje_08() {
        return eje_08;
    }

    public void setEje_08(Integer eje_08) {
        this.eje_08 = eje_08;
    }

    public Integer getEje_09() {
        return eje_09;
    }

    public void setEje_09(Integer eje_09) {
        this.eje_09 = eje_09;
    }

    public Integer getEje_10() {
        return eje_10;
    }

    public void setEje_10(Integer eje_10) {
        this.eje_10 = eje_10;
    }

    public Integer getEje_11() {
        return eje_11;
    }

    public void setEje_11(Integer eje_11) {
        this.eje_11 = eje_11;
    }

    public Integer getEje_12() {
        return eje_12;
    }

    public void setEje_12(Integer eje_12) {
        this.eje_12 = eje_12;
    }
    
    
    
    
}
