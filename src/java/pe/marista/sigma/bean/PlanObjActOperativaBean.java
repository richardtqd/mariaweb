

package pe.marista.sigma.bean;

import java.io.Serializable;


public class PlanObjActOperativaBean implements Serializable
{
    
    private String codigoPlan;
    private String plan;
    private String codigoObj;
    private String objetivo;
    private String codigoAct;
    private String actividad;
    private Double presupuesto;

    public PlanObjActOperativaBean(String codigoPlan, String plan, String codigoObj, String objetivo, String codigoAct, String actividad, Double presupuesto) {
        this.codigoPlan = codigoPlan;
        this.plan = plan;
        this.codigoObj = codigoObj;
        this.objetivo = objetivo;
        this.codigoAct = codigoAct;
        this.actividad = actividad;
        this.presupuesto = presupuesto;
    }
 

    public String getCodigoPlan() {
        return codigoPlan;
    }

    public void setCodigoPlan(String codigoPlan) {
        this.codigoPlan = codigoPlan;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getCodigoObj() {
        return codigoObj;
    }

    public void setCodigoObj(String codigoObj) {
        this.codigoObj = codigoObj;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getCodigoAct() {
        return codigoAct;
    }

    public void setCodigoAct(String codigoAct) {
        this.codigoAct = codigoAct;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public Double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Double presupuesto) {
        this.presupuesto = presupuesto;
    }
    
    
}
