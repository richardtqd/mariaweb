package pe.marista.sigma.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CargoUniNegBean implements Serializable{
    private UnidadNegocioBean unidadNegocioBean;
    private CargoBean cargoBean;
    private BigDecimal asigCargo;
    private Boolean status;
    private String creaPor;
    private String modiPor;
    private Date creaFecha;

    public UnidadNegocioBean getUnidadNegocioBean() {
         if(unidadNegocioBean==null){
            unidadNegocioBean= new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public CargoBean getCargoBean() {
        if(cargoBean==null){
            cargoBean= new CargoBean();
        }
        return cargoBean;
    }

    public void setCargoBean(CargoBean cargoBean) {
        this.cargoBean = cargoBean;
    }

    public BigDecimal getAsigCargo() {
        return asigCargo;
    }

    public void setAsigCargo(BigDecimal asigCargo) {
        this.asigCargo = asigCargo;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    
}
