package pe.marista.sigma.bean;

import java.sql.Timestamp;
import java.util.Date;

public class EstudianteBloqueoBean 
{
    private Integer idEstudianteBloqueo;
    private UnidadNegocioBean unidadNegocioBean;
    private EstudianteBean estudianteBean;
    private CodigoBean tipoStatusEstBean;
    private CodigoBean tipoStatusBloqueoBean;
    private String motivo;
    private String responsable;
    private Date fechaBloqueo;
    private Date fechaSolucion;
    private Integer anio;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private String modiVer;
    //Ayuda
    private PersonalBean personalBean;
    private String idTipoSolicitante;//PRO:PROVEEDOR,PER:PERSONA,COL:COLABORADOR-TRABAJADOR-PERSONAL
    private Integer idTipoSol;//PRO:PROVEEDOR,PER:PERSONA,COL:COLABORADOR-TRABAJADOR-PERSONAL
    private String idPersonalSol;//PRO:PROVEEDOR,PER:PERSONA,COL:COLABORADOR-TRABAJADOR-PERSONAL
     private String idTipoRecibido;//PRO:PROVEEDOR,PER:PERSONA,COL:COLABORADOR-TRABAJADOR-PERSONAL
     private Integer numero;

    public Integer getIdEstudianteBloqueo() {
        return idEstudianteBloqueo;
    }

    public void setIdEstudianteBloqueo(Integer idEstudianteBloqueo) {
        this.idEstudianteBloqueo = idEstudianteBloqueo;
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

    public EstudianteBean getEstudianteBean() {
        if (estudianteBean== null) {
            estudianteBean= new EstudianteBean();
        }
        return estudianteBean;
    }

    public void setEstudianteBean(EstudianteBean estudianteBean) {
        this.estudianteBean = estudianteBean;
    }

    public CodigoBean getTipoStatusEstBean() {
        if (tipoStatusEstBean ==  null) {
            tipoStatusEstBean= new CodigoBean();
        }
        return tipoStatusEstBean;
    }

    public void setTipoStatusEstBean(CodigoBean tipoStatusEstBean) {
        this.tipoStatusEstBean = tipoStatusEstBean;
    }

    public CodigoBean getTipoStatusBloqueoBean() {
        if (tipoStatusBloqueoBean== null) {
            tipoStatusBloqueoBean= new CodigoBean();
        }
        return tipoStatusBloqueoBean;
    }

    public void setTipoStatusBloqueoBean(CodigoBean tipoStatusBloqueoBean) {
        this.tipoStatusBloqueoBean = tipoStatusBloqueoBean;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public Date getFechaBloqueo() {
        return fechaBloqueo;
    }

    public void setFechaBloqueo(Date fechaBloqueo) {
        this.fechaBloqueo = fechaBloqueo;
    }

    public Date getFechaSolucion() {
        return fechaSolucion;
    }

    public void setFechaSolucion(Date fechaSolucion) {
        this.fechaSolucion = fechaSolucion;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
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

    public PersonalBean getPersonalBean() {
        if (personalBean==null) {
            personalBean= new PersonalBean();
        }
        return personalBean;
    }

    public void setPersonalBean(PersonalBean personalBean) {
        this.personalBean = personalBean;
    }

    public String getIdTipoSolicitante() {
        return idTipoSolicitante;
    }

    public void setIdTipoSolicitante(String idTipoSolicitante) {
        this.idTipoSolicitante = idTipoSolicitante;
    }

    public Integer getIdTipoSol() {
        return idTipoSol;
    }

    public void setIdTipoSol(Integer idTipoSol) {
        this.idTipoSol = idTipoSol;
    }

    public String getIdPersonalSol() {
        return idPersonalSol;
    }

    public void setIdPersonalSol(String idPersonalSol) {
        this.idPersonalSol = idPersonalSol;
    }

    public String getIdTipoRecibido() {
        return idTipoRecibido;
    }

    public void setIdTipoRecibido(String idTipoRecibido) {
        this.idTipoRecibido = idTipoRecibido;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }
    
    
}
