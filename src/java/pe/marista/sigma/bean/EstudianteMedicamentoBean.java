package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;
import pe.marista.sigma.MaristaConstantes;

public class EstudianteMedicamentoBean implements Serializable {

    private UnidadNegocioBean unidadNegocioBean;
    private Integer idEstudianteMedicamento;
    private EstudianteBean estudianteBean;
    private String medicamento;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private String modiVer;
    private Boolean flgAutorizado;
    private String vistaFlgAutorizado;

    public String getCreaPor() {
        return creaPor;
    }

    public void setCreaPor(String creaPor) {
        this.creaPor = creaPor;
    }

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public Integer getIdEstudianteMedicamento() {
        return idEstudianteMedicamento;
    }

    public void setIdEstudianteMedicamento(Integer idEstudianteMedicamento) {
        this.idEstudianteMedicamento = idEstudianteMedicamento;
    }

    public EstudianteBean getEstudianteBean() {
        if (estudianteBean == null) {
            estudianteBean = new EstudianteBean();
        }
        return estudianteBean;
    }

    public void setEstudianteBean(EstudianteBean estudianteBean) {
        this.estudianteBean = estudianteBean;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean == null) {
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public String getModiVer() {
        return modiVer;
    }

    public void setModiVer(String modiVer) {
        this.modiVer = modiVer;
    }

    public Date getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(Date creaFecha) {
        this.creaFecha = creaFecha;
    }

    public Boolean getFlgAutorizado() {
        return flgAutorizado;
    }

    public void setFlgAutorizado(Boolean flgAutorizado) {
        this.flgAutorizado = flgAutorizado;
    }

    public String getVistaFlgAutorizado() {
        if (flgAutorizado != null) {
            if (flgAutorizado.equals(true)) {
                vistaFlgAutorizado = MaristaConstantes.SI;
            } else {
                vistaFlgAutorizado = MaristaConstantes.NO;
            }

        } else {
            vistaFlgAutorizado = MaristaConstantes.GUION;
        }
        return vistaFlgAutorizado;
    }

    public void setVistaFlgAutorizado(String vistaFlgAutorizado) {
        this.vistaFlgAutorizado = vistaFlgAutorizado;
    }

}
