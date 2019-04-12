package pe.marista.sigma.bean.reporte;

import java.util.List;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class PresupuestoDetalleRepBean {

    private String nombreUniNeg;
    private Integer cuenta;
    private String presupuestoProg;
    private JRBeanCollectionDataSource listaEjecutado;
//    private JRBeanCollectionDataSource listaPorcentajeEje;
    private JRBeanCollectionDataSource listaDisponible;
    private JRBeanCollectionDataSource listaUtilizado;

    private String presupuestoDisponible;
    private String montoUtilizado;
    private String porcentajeEjecutado;
    private String presupuestoUtilizado;
    
    private String totalPresupuestado; 
    private String totalEjecutado;
    private Double montoUtilizadoAyuda;
            

    public String getNombreUniNeg() {
        return nombreUniNeg;
    }

    public void setNombreUniNeg(String nombreUniNeg) {
        this.nombreUniNeg = nombreUniNeg;
    }

    public Integer getCuenta() {
        return cuenta;
    }

    public void setCuenta(Integer cuenta) {
        this.cuenta = cuenta;
    }

    public String getPresupuestoProg() {
        return presupuestoProg;
    }

    public void setPresupuestoProg(String presupuestoProg) {
        this.presupuestoProg = presupuestoProg;
    }

    public JRBeanCollectionDataSource getListaEjecutado() {
        return listaEjecutado;
    }

    public void setListaEjecutado(List<PresupuestoDetalleRepBean> listaEjecutado) {
        this.listaEjecutado = new JRBeanCollectionDataSource(listaEjecutado);
    }

//    public JRBeanCollectionDataSource getListaPorcentajeEje() {
//        return listaPorcentajeEje;
//    }
//
//    public void setListaPorcentajeEje(List<PresupuestoPorcentajeEjecutadoRepBean> listaPorcentajeEje) {
//        this.listaPorcentajeEje = new JRBeanCollectionDataSource(listaPorcentajeEje);
//    }
    public JRBeanCollectionDataSource getListaDisponible() {
        return listaDisponible;
    }

    public void setListaDisponible(List<PresupuestoDetalleRepBean> listaDisponible) {
        this.listaDisponible = new JRBeanCollectionDataSource(listaDisponible);
    }

    public JRBeanCollectionDataSource getListaUtilizado() {
        return listaUtilizado;
    }

    public void setListaUtilizado(List<PresupuestoDetalleRepBean> listaUtilizado) {
        this.listaUtilizado = new JRBeanCollectionDataSource(listaUtilizado);
    }

    public String getPresupuestoDisponible() {
        return presupuestoDisponible;
    }

    public void setPresupuestoDisponible(String presupuestoDisponible) {
        this.presupuestoDisponible = presupuestoDisponible;
    }

    public String getMontoUtilizado() {
        return montoUtilizado;
    }

    public void setMontoUtilizado(String montoUtilizado) {
        this.montoUtilizado = montoUtilizado;
    }

    public String getPorcentajeEjecutado() {
        return porcentajeEjecutado;
    }

    public void setPorcentajeEjecutado(String porcentajeEjecutado) {
        this.porcentajeEjecutado = porcentajeEjecutado;
    }

    public String getPresupuestoUtilizado() {
        return presupuestoUtilizado;
    }

    public void setPresupuestoUtilizado(String presupuestoUtilizado) {
        this.presupuestoUtilizado = presupuestoUtilizado;
    }
  
    public String getTotalPresupuestado() {
        return totalPresupuestado;
    }

    public void setTotalPresupuestado(String totalPresupuestado) {
        this.totalPresupuestado = totalPresupuestado;
    }

    public String getTotalEjecutado() {
        return totalEjecutado;
    }

    public void setTotalEjecutado(String totalEjecutado) {
        this.totalEjecutado = totalEjecutado;
    }

    public Double getMontoUtilizadoAyuda() {
        return montoUtilizadoAyuda;
    }

    public void setMontoUtilizadoAyuda(Double montoUtilizadoAyuda) {
        this.montoUtilizadoAyuda = montoUtilizadoAyuda;
    }

    
}
