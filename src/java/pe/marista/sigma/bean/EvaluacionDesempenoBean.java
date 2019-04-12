package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

public class EvaluacionDesempenoBean implements Serializable{
    private Integer idEvaluadoEvaluador;
    private String nombreEvaluado;
    private PersonalEDBean codigoEvaluado; 
    private String nombreEvaluador;
    private PersonalEDBean codigoEvaluador; 
    private CargoBean cargoEvaluadoBean;
    private CargoBean cargoEvaluadorBean;
    private UnidadNegocioBean unidadNegocioBean;
    private Boolean status;
    private Integer anio;
    private String modiPor;
    private String creaPor;
    private Date creaFecha; 
    private String estadoVista;
    private CodigoBean tipoGrupoOcupacionalBean;
    
    private String foto;

    public Integer getIdEvaluadoEvaluador() {
        return idEvaluadoEvaluador;
    }

    public void setIdEvaluadoEvaluador(Integer idEvaluadoEvaluador) {
        this.idEvaluadoEvaluador = idEvaluadoEvaluador;
    }

    public String getNombreEvaluado() {
        return nombreEvaluado;
    }

    public void setNombreEvaluado(String nombreEvaluado) {
        this.nombreEvaluado = nombreEvaluado;
    }

    public PersonalEDBean getCodigoEvaluado() {
        if (codigoEvaluado==null) {
            codigoEvaluado = new PersonalEDBean();
        }
        return codigoEvaluado;
    }

    public void setCodigoEvaluado(PersonalEDBean codigoEvaluado) {
        this.codigoEvaluado = codigoEvaluado;
    }
 
    public String getNombreEvaluador() {
        return nombreEvaluador;
    }

    public void setNombreEvaluador(String nombreEvaluador) {
        this.nombreEvaluador = nombreEvaluador;
    }

    public PersonalEDBean getCodigoEvaluador() {
        if (codigoEvaluador==null) {
            codigoEvaluador= new PersonalEDBean();
        }
        return codigoEvaluador;
    }

    public void setCodigoEvaluador(PersonalEDBean codigoEvaluador) {
        this.codigoEvaluador = codigoEvaluador;
    }
 
    public CargoBean getCargoEvaluadoBean() {
        if (cargoEvaluadoBean==null) {
            cargoEvaluadoBean= new CargoBean();
        }
        return cargoEvaluadoBean;
    }

    public void setCargoEvaluadoBean(CargoBean cargoEvaluadoBean) {
        this.cargoEvaluadoBean = cargoEvaluadoBean;
    }

    public CargoBean getCargoEvaluadorBean() {
        if (cargoEvaluadorBean== null) {
            cargoEvaluadorBean= new CargoBean();
        }
        return cargoEvaluadorBean;
    }

    public void setCargoEvaluadorBean(CargoBean cargoEvaluadorBean) {
        this.cargoEvaluadorBean = cargoEvaluadorBean;
    }

    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean==null) {
            unidadNegocioBean= new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
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

    public String getEstadoVista() {
        return estadoVista;
    }

    public void setEstadoVista(String estadoVista) {
        this.estadoVista = estadoVista;
    }

    public CodigoBean getTipoGrupoOcupacionalBean() {
        if (tipoGrupoOcupacionalBean==null) {
            tipoGrupoOcupacionalBean=new CodigoBean();
        }
        return tipoGrupoOcupacionalBean;
    }

    public void setTipoGrupoOcupacionalBean(CodigoBean tipoGrupoOcupacionalBean) {
        this.tipoGrupoOcupacionalBean = tipoGrupoOcupacionalBean;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
    
    
}
