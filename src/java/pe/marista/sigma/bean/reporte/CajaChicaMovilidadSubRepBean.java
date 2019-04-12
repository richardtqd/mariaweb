package pe.marista.sigma.bean.reporte;

public class CajaChicaMovilidadSubRepBean {
    private String solicitante;
    private String dni;
    private Integer idpersonal;
    private Integer idsolicitudcajach;
    private String fecha;
    private String motivo;
    private String obs;
    private Double montoaprobado;

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Integer getIdpersonal() {
        return idpersonal;
    }

    public void setIdpersonal(Integer idpersonal) {
        this.idpersonal = idpersonal;
    }
 
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public Double getMontoaprobado() {
        return montoaprobado;
    }

    public void setMontoaprobado(Double montoaprobado) {
        this.montoaprobado = montoaprobado;
    }

    public Integer getIdsolicitudcajach() {
        return idsolicitudcajach;
    }

    public void setIdsolicitudcajach(Integer idsolicitudcajach) {
        this.idsolicitudcajach = idsolicitudcajach;
    }
    
    
    
}
