package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;
import org.jfree.data.time.Hour;

/**
 *
 * @author Administrador
 */
public class PersonalAlergiaBean implements Serializable {

    private Integer idPersonalAlergia;
    private UnidadNegocioBean unidadNegocioBean;
    private PersonalBean personalBean;
    private CodigoBean tipoAlergiaBean;
    private String alergia;
    private String obs;
    private Date creaFecha;
    private String creaPor;
    private String modiPor;
    private boolean collapsed = true;
    
    private Boolean flgMedicamentos;
    private String medicamento1;
    private String medicamento2;
    private String medicamento3; 

    public Integer getIdPersonalAlergia() {
        return idPersonalAlergia;
    }

    public void setIdPersonalAlergia(Integer idPersonalAlergia) {
        this.idPersonalAlergia = idPersonalAlergia;
    }

    public CodigoBean getTipoAlergiaBean() {
        if (tipoAlergiaBean == null) {
            tipoAlergiaBean = new CodigoBean();
        }
        return tipoAlergiaBean;
    }

    public void setTipoAlergiaBean(CodigoBean tipoAlergiaBean) {
        this.tipoAlergiaBean = tipoAlergiaBean;
    }

    public String getAlergia() {
        return alergia;
    }

    public void setAlergia(String alergia) {
        this.alergia = alergia;
    }

    public PersonalBean getPersonalBean() {
        if (personalBean == null) {
            personalBean = new PersonalBean();
        }
        return personalBean;
    }

    public void setPersonalBean(PersonalBean personalBean) {
        this.personalBean = personalBean;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public Date getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(Date creaFecha) {
        this.creaFecha = creaFecha;
    }

    public String getCreaPor() {
        return creaPor;
    }

    public void setCreaPor(String creaPor) {
        this.creaPor = creaPor;
    }

    public boolean isCollapsed() {
        return collapsed;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
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

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    } 

    public String getMedicamento1() {
        return medicamento1;
    }

    public void setMedicamento1(String medicamento1) {
        this.medicamento1 = medicamento1;
    }

    public String getMedicamento2() {
        return medicamento2;
    }

    public void setMedicamento2(String medicamento2) {
        this.medicamento2 = medicamento2;
    }

    public String getMedicamento3() {
        return medicamento3;
    }

    public void setMedicamento3(String medicamento3) {
        this.medicamento3 = medicamento3;
    }

    public Boolean getFlgMedicamentos() {
        return flgMedicamentos;
    }

    public void setFlgMedicamentos(Boolean flgMedicamentos) {
        this.flgMedicamentos = flgMedicamentos;
    } 

}
