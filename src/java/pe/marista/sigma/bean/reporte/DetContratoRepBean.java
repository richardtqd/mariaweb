package pe.marista.sigma.bean.reporte;

import java.io.Serializable;

public class DetContratoRepBean implements Serializable{
    private String horaRegreso;
    private String horaSalida;
    private String fechaSalida;
    private String destinoServicio;
    private Integer cantidad;
    private String item;
    private String sumaImporte;
    private String montoRef;

    public String getHoraRegreso() {
        return horaRegreso;
    }

    public void setHoraRegreso(String horaRegreso) {
        this.horaRegreso = horaRegreso;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getDestinoServicio() {
        return destinoServicio;
    }

    public void setDestinoServicio(String destinoServicio) {
        this.destinoServicio = destinoServicio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getSumaImporte() {
        return sumaImporte;
    }

    public void setSumaImporte(String sumaImporte) {
        this.sumaImporte = sumaImporte;
    }

    public String getMontoRef() {
        return montoRef;
    }

    public void setMontoRef(String montoRef) {
        this.montoRef = montoRef;
    }
    
    
}
