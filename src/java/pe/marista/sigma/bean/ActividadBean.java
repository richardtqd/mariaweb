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
public class ActividadBean implements Serializable {

    private Integer idActividad;
    private ObjOperativoBean objOperativoBean;
    private AnioBean anioBean;
    private UnidadNegocioBean unidadNegocioBean;
    private UnidadOrganicaBean unidadOrganicaBean;
    private IndicadorBean indicadorBean;
    private CodigoBean codigoBean1; //idTipoUniMed
    private PresupuestoUniOrgBean presupuestoUniOrgBean;
    private String codigo;
    private String nombre;
    private CodigoBean tipoTareaBean; // idTipoTarea
    private CodigoBean tipoUniMedBean;
    private Integer idTipoUniMed;
    private String tipoUniMed;
    private String responsable;
    private BigDecimal egreso;
    private BigDecimal ingreso;
    private Integer metaProgramada;
    private Integer metaEjecutada;
    private Integer meta;
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
    private String creaPor;
    private String creaFecha;
    private String modiPor;
    private String modiVer;
    private Integer tipoImporte;
    private String labelTipoImporte;

    //Ayuda
    private Integer idUniOrg;
    private Integer anio;
    private String creaFechaAc;
    private String creaHoraAc;

    public ActividadBean() {
    }

    public Integer getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Integer idActividad) {
        this.idActividad = idActividad;
    }

    public ObjOperativoBean getObjOperativoBean() {
        if (objOperativoBean == null) {
            objOperativoBean = new ObjOperativoBean();
        }
        return objOperativoBean;
    }

    public void setObjOperativoBean(ObjOperativoBean objOperativoBean) {
        this.objOperativoBean = objOperativoBean;
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

    public CodigoBean getCodigoBean1() {
        if (codigoBean1 == null) {
            codigoBean1 = new CodigoBean();
        }
        return codigoBean1;
    }

    public void setCodigoBean1(CodigoBean codigoBean1) {
        this.codigoBean1 = codigoBean1;
    }

    public PresupuestoUniOrgBean getPresupuestoUniOrgBean() {
        if (presupuestoUniOrgBean == null) {
            presupuestoUniOrgBean = new PresupuestoUniOrgBean();
        }
        return presupuestoUniOrgBean;
    }

    public void setPresupuestoUniOrgBean(PresupuestoUniOrgBean presupuestoUniOrgBean) {
        this.presupuestoUniOrgBean = presupuestoUniOrgBean;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public CodigoBean getTipoTareaBean() {
        if (tipoTareaBean == null) {
            tipoTareaBean = new CodigoBean();
        }
        return tipoTareaBean;
    }

    public void setTipoTareaBean(CodigoBean tipoTareaBean) {
        this.tipoTareaBean = tipoTareaBean;
    }

    public CodigoBean getTipoUniMedBean() {
        return tipoUniMedBean;
    }

    public void setTipoUniMedBean(CodigoBean tipoUniMedBean) {
        this.tipoUniMedBean = tipoUniMedBean;
    }

    public Integer getIdTipoUniMed() {
        return idTipoUniMed;
    }

    public void setIdTipoUniMed(Integer idTipoUniMed) {
        this.idTipoUniMed = idTipoUniMed;
    }

    public String getTipoUniMed() {
        return tipoUniMed;
    }

    public void setTipoUniMed(String tipoUniMed) {
        this.tipoUniMed = tipoUniMed;
    }

    public String getCreaPor() {
        return creaPor;
    }

    public void setCreaPor(String creaPor) {
        this.creaPor = creaPor;
    }

    public String getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(String creaFecha) {
        this.creaFecha = creaFecha;
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

    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean == null) {
            unidadNegocioBean = new UnidadNegocioBean();
        }

        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public UnidadOrganicaBean getUnidadOrganicaBean() {
        if (unidadOrganicaBean == null) {
            unidadOrganicaBean = new UnidadOrganicaBean();
        }
        return unidadOrganicaBean;
    }

    public void setUnidadOrganicaBean(UnidadOrganicaBean unidadOrganicaBean) {
        this.unidadOrganicaBean = unidadOrganicaBean;
    }

    public Integer getMeta() {
        return meta;
    }

    public void setMeta(Integer meta) {
        this.meta = meta;
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

    public Integer getIdUniOrg() {
        return idUniOrg;
    }

    public void setIdUniOrg(Integer idUniOrg) {
        this.idUniOrg = idUniOrg;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getCreaFechaAc() {
        return creaFechaAc;
    }

    public void setCreaFechaAc(String creaFechaAc) {
        this.creaFechaAc = creaFechaAc;
    }

    public String getCreaHoraAc() {
        return creaHoraAc;
    }

    public void setCreaHoraAc(String creaHoraAc) {
        this.creaHoraAc = creaHoraAc;
    }

    public Integer getTipoImporte() {
        return tipoImporte;
    }

    public void setTipoImporte(Integer tipoImporte) {
        this.tipoImporte = tipoImporte;
    }

    public String getLabelTipoImporte() {
        return labelTipoImporte;
    }

    public void setLabelTipoImporte(String labelTipoImporte) {
        this.labelTipoImporte = labelTipoImporte;
    }

}
