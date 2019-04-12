package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;
import pe.marista.sigma.MaristaConstantes;

public class CarreraBean implements Serializable {

    private Integer idCarrera;
    private CarreraSubAreaBean carreraSubAreaBean;//id
    private String carrera;
    private String profesion;
    private String creaPor;
    private Date creaFecha;
     private  String carreraVista;

    public CarreraSubAreaBean getCarreraSubAreaBean() {
        if (carreraSubAreaBean == null) {
            carreraSubAreaBean = new CarreraSubAreaBean();
        }
        return carreraSubAreaBean;
    }

    public void setCarreraSubAreaBean(CarreraSubAreaBean carreraSubAreaBean) {
        this.carreraSubAreaBean = carreraSubAreaBean;
    }

    public Integer getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(Integer idCarrera) {
        this.idCarrera = idCarrera;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
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
    public String getCarreraVista() {
         if (carrera == null) {
            carreraVista = MaristaConstantes.SIN_CARRERA;
            return carreraVista;
        }
         if (carrera != null) {
            carreraVista = carrera;
            return carreraVista;
        }
        return carreraVista;
    }

    public void setCarreraVista(String carreraVista) {
        this.carreraVista = carreraVista;
    }

}
