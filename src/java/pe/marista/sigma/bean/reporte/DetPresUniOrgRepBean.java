/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean.reporte;

import java.io.Serializable;

/**
 *
 * @author MS001
 */
public class DetPresUniOrgRepBean implements Serializable {

    private String cuenta;
    private String nombreCta;
    private String presupuestoProg;
    private String presupuestoEjec;
    private String nombreUniOrg;
    private String presupuestoProgUniOrg;
    private String presupuestoEjecUniOrg;
    private String presupuestoTotalProgUniOrg;
    private String presupuestototalEjecUniOrg;
    

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getNombreCta() {
        return nombreCta;
    }

    public void setNombreCta(String nombreCta) {
        this.nombreCta = nombreCta;
    }

    public String getPresupuestoProg() {
        return presupuestoProg;
    }

    public void setPresupuestoProg(String presupuestoProg) {
        this.presupuestoProg = presupuestoProg;
    }

    public String getPresupuestoEjec() {
        return presupuestoEjec;
    }

    public void setPresupuestoEjec(String presupuestoEjec) {
        this.presupuestoEjec = presupuestoEjec;
    }

    public String getNombreUniOrg() {
        return nombreUniOrg;
    }

    public void setNombreUniOrg(String nombreUniOrg) {
        this.nombreUniOrg = nombreUniOrg;
    }

    public String getPresupuestoProgUniOrg() {
        return presupuestoProgUniOrg;
    }

    public void setPresupuestoProgUniOrg(String presupuestoProgUniOrg) {
        this.presupuestoProgUniOrg = presupuestoProgUniOrg;
    }

    public String getPresupuestoEjecUniOrg() {
        return presupuestoEjecUniOrg;
    }

    public void setPresupuestoEjecUniOrg(String presupuestoEjecUniOrg) {
        this.presupuestoEjecUniOrg = presupuestoEjecUniOrg;
    }

    public String getPresupuestoTotalProgUniOrg() {
        return presupuestoTotalProgUniOrg;
    }

    public void setPresupuestoTotalProgUniOrg(String presupuestoTotalProgUniOrg) {
        this.presupuestoTotalProgUniOrg = presupuestoTotalProgUniOrg;
    }

    public String getPresupuestototalEjecUniOrg() {
        return presupuestototalEjecUniOrg;
    }

    public void setPresupuestototalEjecUniOrg(String presupuestototalEjecUniOrg) {
        this.presupuestototalEjecUniOrg = presupuestototalEjecUniOrg;
    }
    
    

}
