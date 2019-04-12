package pe.marista.sigma.bean;
import java.io.Serializable;
import org.joda.time.DateTime;

public class ModificacionesBean implements Serializable{
    private UnidadNegocioBean unidadNegocioBean;
    private Integer idModificaciones;
    private String nombreObjeto;
    private String rspObjeto;
    private Integer mesInicio;
    private Integer mesFin;
    private DateTime creafecha;
    private String creaPor;
    private EstudianteBean estudianteBean;
//    private DateTime fecIni;
//    private DateTime fecFin;

    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean==null) {
            unidadNegocioBean= new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public Integer getIdModificaciones() {
        return idModificaciones;
    }

    public void setIdModificaciones(Integer idModificaciones) {
        this.idModificaciones = idModificaciones;
    }

    public String getNombreObjeto() {
        return nombreObjeto;
    }

    public void setNombreObjeto(String nombreObjeto) {
        this.nombreObjeto = nombreObjeto;
    }

    public String getRspObjeto() {
        return rspObjeto;
    }

    public void setRspObjeto(String rspObjeto) {
        this.rspObjeto = rspObjeto;
    }

    public Integer getMesInicio() {
        return mesInicio;
    }

    public void setMesInicio(Integer mesInicio) {
        this.mesInicio = mesInicio;
    }

    public Integer getMesFin() {
        return mesFin;
    }

    public void setMesFin(Integer mesFin) {
        this.mesFin = mesFin;
    }

    public DateTime getCreafecha() {
        return creafecha;
    }

    public void setCreafecha(DateTime creafecha) {
        this.creafecha = creafecha;
    }

    public String getCreaPor() {
        return creaPor;
    }

    public void setCreaPor(String creaPor) {
        this.creaPor = creaPor;
    }

//    public DateTime getFecIni() {
//        return fecIni;
//    }
//
//    public void setFecIni(DateTime fecIni) {
//        this.fecIni = fecIni;
//    }
//
//    public DateTime getFecFin() {
//        return fecFin;
//    }
//
//    public void setFecFin(DateTime fecFin) {
//        this.fecFin = fecFin;
//    }

    public EstudianteBean getEstudianteBean() {
        if (estudianteBean==null) {
            estudianteBean= new EstudianteBean();
        }
        return estudianteBean;
    }

    public void setEstudianteBean(EstudianteBean estudianteBean) {
        this.estudianteBean = estudianteBean;
    }
}
