package pe.marista.sigma.bean;

import java.util.Date;

public class EstudianteVacunaBean {
    
    private UnidadNegocioBean unidadNegocioBean;
    private EstudianteBean estudianteBean;
    private Integer idEstudianteVacuna;
    private CodigoBean tipoEdad;
    private CodigoBean tipoVacunas;
//    private Date fechaHemoglobina;
//    private Integer resultado;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;

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

    public Integer getIdEstudianteVacuna() {
        return idEstudianteVacuna;
    }

    public void setIdEstudianteVacuna(Integer idEstudianteVacuna) {
        this.idEstudianteVacuna = idEstudianteVacuna;
    }

    public CodigoBean getTipoEdad() {
        if (tipoEdad== null) {
            tipoEdad= new CodigoBean();
        }
        return tipoEdad;
    }

    public void setTipoEdad(CodigoBean tipoEdad) {
        this.tipoEdad = tipoEdad;
    }

    public CodigoBean getTipoVacunas() {
        if (tipoVacunas== null) {
            tipoVacunas= new CodigoBean();
        }
        return tipoVacunas;
    }

    public void setTipoVacunas(CodigoBean tipoVacunas) {
        this.tipoVacunas = tipoVacunas;
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
 
    
}
