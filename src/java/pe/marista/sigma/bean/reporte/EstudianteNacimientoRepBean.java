package pe.marista.sigma.bean.reporte;

import java.io.Serializable;
import java.math.BigDecimal;

public class EstudianteNacimientoRepBean implements Serializable{
  
    private String controlPrenatal; 
    private Integer cantidadControles;
    private String enfEmbarazo;
    private String partoNormal;
    private String apliAnestesia;
    private String partoSesarea;
    private String causaSesarea;
    private String pesoBebe;
    private String talla;
    private Integer puntajeApgarMinuto;
    private Integer puntajeApgarCincoMinutos;

    public String getControlPrenatal() {
        return controlPrenatal;
    }

    public void setControlPrenatal(String controlPrenatal) {
        this.controlPrenatal = controlPrenatal;
    }

    public Integer getCantidadControles() {
        return cantidadControles;
    }

    public void setCantidadControles(Integer cantidadControles) {
        this.cantidadControles = cantidadControles;
    }

    public String getEnfEmbarazo() {
        return enfEmbarazo;
    }

    public void setEnfEmbarazo(String enfEmbarazo) {
        this.enfEmbarazo = enfEmbarazo;
    }

    public String getPartoNormal() {
        return partoNormal;
    }

    public void setPartoNormal(String partoNormal) {
        this.partoNormal = partoNormal;
    }

    public String getApliAnestesia() {
        return apliAnestesia;
    }

    public void setApliAnestesia(String apliAnestesia) {
        this.apliAnestesia = apliAnestesia;
    }

    public String getPartoSesarea() {
        return partoSesarea;
    }

    public void setPartoSesarea(String partoSesarea) {
        this.partoSesarea = partoSesarea;
    }

    public String getCausaSesarea() {
        return causaSesarea;
    }

    public void setCausaSesarea(String causaSesarea) {
        this.causaSesarea = causaSesarea;
    }

    public String getPesoBebe() {
        return pesoBebe;
    }

    public void setPesoBebe(String pesoBebe) {
        this.pesoBebe = pesoBebe;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public Integer getPuntajeApgarMinuto() {
        return puntajeApgarMinuto;
    }

    public void setPuntajeApgarMinuto(Integer puntajeApgarMinuto) {
        this.puntajeApgarMinuto = puntajeApgarMinuto;
    }

    public Integer getPuntajeApgarCincoMinutos() {
        return puntajeApgarCincoMinutos;
    }

    public void setPuntajeApgarCincoMinutos(Integer puntajeApgarCincoMinutos) {
        this.puntajeApgarCincoMinutos = puntajeApgarCincoMinutos;
    }

    
   
    
}
