package pe.marista.sigma.bean;

import java.io.Serializable;

public class PersonalInformacionBancariaBean implements Serializable {

    private UnidadNegocioBean unidadNegocioBean;
    private PersonalBean personalBean;
    private Integer idPersonalInformacionBancaria;
    private String nombreEntidadHaberes;
    private String cuentaBancariaHaberes;
    private String cuentaInterbancariaHaberes;
    private String nombreEntidadCts;
    private String cuentaSolesCts;
    private String cuentaDolaresCts;
    private String creaPor;
    private String creaFecha;
    private String modiPor;

    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean == null) {
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
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

    public Integer getIdPersonalInformacionBancaria() {
        return idPersonalInformacionBancaria;
    }

    public void setIdPersonalInformacionBancaria(Integer idPersonalInformacionBancaria) {
        this.idPersonalInformacionBancaria = idPersonalInformacionBancaria;
    }

    public String getNombreEntidadHaberes() {
        return nombreEntidadHaberes;
    }

    public void setNombreEntidadHaberes(String nombreEntidadHaberes) {
        this.nombreEntidadHaberes = nombreEntidadHaberes;
    }

    public String getCuentaBancariaHaberes() {
        return cuentaBancariaHaberes;
    }

    public void setCuentaBancariaHaberes(String cuentaBancariaHaberes) {
        this.cuentaBancariaHaberes = cuentaBancariaHaberes;
    }

    public String getCuentaInterbancariaHaberes() {
        return cuentaInterbancariaHaberes;
    }

    public void setCuentaInterbancariaHaberes(String cuentaInterbancariaHaberes) {
        this.cuentaInterbancariaHaberes = cuentaInterbancariaHaberes;
    }

    public String getNombreEntidadCts() {
        return nombreEntidadCts;
    }

    public void setNombreEntidadCts(String nombreEntidadCts) {
        this.nombreEntidadCts = nombreEntidadCts;
    }

    public String getCuentaSolesCts() {
        return cuentaSolesCts;
    }

    public void setCuentaSolesCts(String cuentaSolesCts) {
        this.cuentaSolesCts = cuentaSolesCts;
    }

    public String getCuentaDolaresCts() {
        return cuentaDolaresCts;
    }

    public void setCuentaDolaresCts(String cuentaDolaresCts) {
        this.cuentaDolaresCts = cuentaDolaresCts;
    }

    public String getCreaPor() {
        return creaPor;
    }

    public void setCreaPor(String creaPor) {
        this.creaPor = creaPor;
    }

    public String getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(String creaFecha) {
        this.creaFecha = creaFecha;
    }

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

}
