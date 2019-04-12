package pe.marista.sigma.bean.reporte;

public class SeguimientoEDRepBean {
    private Integer pendientes;
    private String porcPendientes;
    private Integer completas;
    private String porcCompletas;
    private Integer totalEncuestas;
    private String totalPorcEncuesta;

    public Integer getPendientes() {
        return pendientes;
    }

    public void setPendientes(Integer pendientes) {
        this.pendientes = pendientes;
    }

    public String getPorcPendientes() {
        return porcPendientes;
    }

    public void setPorcPendientes(String porcPendientes) {
        this.porcPendientes = porcPendientes;
    }

    public Integer getCompletas() {
        return completas;
    }

    public void setCompletas(Integer completas) {
        this.completas = completas;
    }

    public String getPorcCompletas() {
        return porcCompletas;
    }

    public void setPorcCompletas(String porcCompletas) {
        this.porcCompletas = porcCompletas;
    }

    public String getTotalPorcEncuesta() {
        return totalPorcEncuesta;
    }

    public void setTotalPorcEncuesta(String totalPorcEncuesta) {
        this.totalPorcEncuesta = totalPorcEncuesta;
    }

    public Integer getTotalEncuestas() {
        return totalEncuestas;
    }

    public void setTotalEncuestas(Integer totalEncuestas) {
        this.totalEncuestas = totalEncuestas;
    }
    
}
