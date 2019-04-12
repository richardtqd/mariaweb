package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

public class CarreraSubAreaBean implements Serializable{
    
    private Integer idCarreraSubArea;
    private CarreraAreaBean carreraAreaBean;//id
    private String subArea;
    private String creaPor;
    private Date creaFecha;

    public Integer getIdCarreraSubArea() {
        return idCarreraSubArea;
    }

    public void setIdCarreraSubArea(Integer idCarreraSubArea) {
        this.idCarreraSubArea = idCarreraSubArea;
    }

    public String getSubArea() {
        return subArea;
    }

    public void setSubArea(String subArea) {
        this.subArea = subArea;
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

    public CarreraAreaBean getCarreraAreaBean() {
        if (carreraAreaBean==null){
            carreraAreaBean = new CarreraAreaBean();
        }
        return carreraAreaBean;
    }

    public void setCarreraAreaBean(CarreraAreaBean carreraAreaBean) {
        this.carreraAreaBean = carreraAreaBean;
    }
    
    
    
    
    
}
