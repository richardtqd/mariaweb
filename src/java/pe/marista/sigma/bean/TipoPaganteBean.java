package pe.marista.sigma.bean;

import java.io.Serializable;

public class TipoPaganteBean implements Serializable {

    private Integer idtipoPagante;
    private Integer nroAsignaciones;
    private String nomPagante;
    private UnidadNegocioBean unidadNegocioBean;

    public Integer getIdtipoPagante() {
        return idtipoPagante;
    }

    public void setIdtipoPagante(Integer idtipoPagante) {
        this.idtipoPagante = idtipoPagante;
    }

    public Integer getNroAsignaciones() {
        return nroAsignaciones;
    }

    public void setNroAsignaciones(Integer nroAsignaciones) {
        this.nroAsignaciones = nroAsignaciones;
    }

    public String getNomPagante() {
        return nomPagante;
    }

    public void setNomPagante(String nomPagante) {
        this.nomPagante = nomPagante;
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

}
