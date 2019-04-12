package pe.marista.sigma.bean;

import java.util.Date;

public class EstudianteNacimientoBean {
    
    private UnidadNegocioBean unidadNegocioBean;
    private EstudianteBean estudianteBean;
    private Integer idEstudianteNacimiento;
    private Boolean controlPrenatal;
    private Integer cantidadControles;
    private Boolean enfEmbarazo;
    private Boolean partoNormal;
    private Boolean apliAnestesia;
    private Boolean partoSesarea;
    private String causaSesarea;
    private Double pesoBebe;
    private Double talla;
    private Integer puntajeApgarMinuto;
    private Integer puntajeApgarCincoMinutos;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private String modiVer;

    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean==null) {
            unidadNegocioBean= new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public EstudianteBean getEstudianteBean() {
        if (estudianteBean==null) {
            estudianteBean= new EstudianteBean();
        }
        return estudianteBean;
    }

    public void setEstudianteBean(EstudianteBean estudianteBean) {
        this.estudianteBean = estudianteBean;
    }

    public Integer getIdEstudianteNacimiento() {
        return idEstudianteNacimiento;
    }

    public void setIdEstudianteNacimiento(Integer idEstudianteNacimiento) {
        this.idEstudianteNacimiento = idEstudianteNacimiento;
    }

    public Boolean getControlPrenatal() {
        return controlPrenatal;
    }

    public void setControlPrenatal(Boolean controlPrenatal) {
        this.controlPrenatal = controlPrenatal;
    }

    public Integer getCantidadControles() {
        return cantidadControles;
    }

    public void setCantidadControles(Integer cantidadControles) {
        this.cantidadControles = cantidadControles;
    }

    public Boolean getEnfEmbarazo() {
        return enfEmbarazo;
    }

    public void setEnfEmbarazo(Boolean enfEmbarazo) {
        this.enfEmbarazo = enfEmbarazo;
    }

    public Boolean getPartoNormal() {
        return partoNormal;
    }

    public void setPartoNormal(Boolean partoNormal) {
        this.partoNormal = partoNormal;
    }

    public Boolean getApliAnestesia() {
        return apliAnestesia;
    }

    public void setApliAnestesia(Boolean apliAnestesia) {
        this.apliAnestesia = apliAnestesia;
    } 

    public Double getPesoBebe() {
        return pesoBebe;
    }

    public void setPesoBebe(Double pesoBebe) {
        this.pesoBebe = pesoBebe;
    }

    public Double getTalla() {
        return talla;
    }

    public void setTalla(Double talla) {
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

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public String getModiVer() {
        return modiVer;
    }

    public void setModiVer(String modiVer) {
        this.modiVer = modiVer;
    }

    public Boolean getPartoSesarea() {
        return partoSesarea;
    }

    public void setPartoSesarea(Boolean partoSesarea) {
        this.partoSesarea = partoSesarea;
    }

    public String getCausaSesarea() {
        return causaSesarea;
    }

    public void setCausaSesarea(String causaSesarea) {
        this.causaSesarea = causaSesarea;
    }
        
    
}
