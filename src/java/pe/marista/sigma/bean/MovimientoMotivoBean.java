
package pe.marista.sigma.bean;

import java.io.Serializable;

public class MovimientoMotivoBean implements Serializable{
    
    private Integer idmovimientomotivo;
    private String motivo;
    private Integer idtipomovactivo;

    public Integer getIdmovimientomotivo() {
        return idmovimientomotivo;
    }

    public void setIdmovimientomotivo(Integer idmovimientomotivo) {
        this.idmovimientomotivo = idmovimientomotivo;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Integer getIdtipomovactivo() {
        return idtipomovactivo;
    }

    public void setIdtipomovactivo(Integer idtipomovactivo) {
        this.idtipomovactivo = idtipomovactivo;
    }
    
}
