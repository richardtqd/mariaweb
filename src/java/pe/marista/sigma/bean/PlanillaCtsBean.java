package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

public class PlanillaCtsBean implements Serializable{
    private UnidadNegocioBean unidadNegocioBean;
    private Integer idPlanillaCts;
    private Integer anio;
    private Integer parte; //1- mayo 2- noviembre
    private String codigo;
    private String empleado;
    private String fechaIngreso;
    private String fechaInicio;
    private String fechaFin;
    private String cantidadMeses; 
    private String cantidadDias;
    private Double col1;
    private Double col2;
    private Double col3;
    private Double col4;
    private Double col5;
    private Double col6;
    private String col7;
    private Double col8;
    private Double col9;
    private String col10;
    private Double col11;
    private Double col12;
    private String col13;
    private String col14;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private Date fechaSubida;
    private Double totalPersonal; 

    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean==null) {
            unidadNegocioBean= new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public Integer getIdPlanillaCts() {
        return idPlanillaCts;
    }

    public void setIdPlanillaCts(Integer idPlanillaCts) {
        this.idPlanillaCts = idPlanillaCts;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getParte() {
        return parte;
    }

    public void setParte(Integer parte) {
        this.parte = parte;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getCantidadMeses() {
        return cantidadMeses;
    }

    public void setCantidadMeses(String cantidadMeses) {
        this.cantidadMeses = cantidadMeses;
    }

    public String getCantidadDias() {
        return cantidadDias;
    }

    public void setCantidadDias(String cantidadDias) {
        this.cantidadDias = cantidadDias;
    }

    public Double getCol1() {
        return col1;
    }

    public void setCol1(Double col1) {
        this.col1 = col1;
    }

    public Double getCol2() {
        return col2;
    }

    public void setCol2(Double col2) {
        this.col2 = col2;
    }

    public Double getCol3() {
        return col3;
    }

    public void setCol3(Double col3) {
        this.col3 = col3;
    }

    public Double getCol4() {
        return col4;
    }

    public void setCol4(Double col4) {
        this.col4 = col4;
    }

    public Double getCol5() {
        return col5;
    }

    public void setCol5(Double col5) {
        this.col5 = col5;
    }

    public Double getCol6() {
        return col6;
    }

    public void setCol6(Double col6) {
        this.col6 = col6;
    }

    public String getCol7() {
        return col7;
    }

    public void setCol7(String col7) {
        this.col7 = col7;
    }

    public Double getCol8() {
        return col8;
    }

    public void setCol8(Double col8) {
        this.col8 = col8;
    }

    public Double getCol9() {
        return col9;
    }

    public void setCol9(Double col9) {
        this.col9 = col9;
    }

    public String getCol10() {
        return col10;
    }

    public void setCol10(String col10) {
        this.col10 = col10;
    }

    public Double getCol11() {
        return col11;
    }

    public void setCol11(Double col11) {
        this.col11 = col11;
    }

    public Double getCol12() {
        return col12;
    }

    public void setCol12(Double col12) {
        this.col12 = col12;
    }

    public String getCol13() {
        return col13;
    }

    public void setCol13(String col13) {
        this.col13 = col13;
    }

    public String getCol14() {
        return col14;
    }

    public void setCol14(String col14) {
        this.col14 = col14;
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

    public Date getFechaSubida() {
        return fechaSubida;
    }

    public void setFechaSubida(Date fechaSubida) {
        this.fechaSubida = fechaSubida;
    } 

    public Double getTotalPersonal() {
        return totalPersonal;
    }

    public void setTotalPersonal(Double totalPersonal) {
        this.totalPersonal = totalPersonal;
    }
 
}
