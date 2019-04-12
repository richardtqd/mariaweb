package pe.marista.sigma.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import pe.marista.sigma.MaristaConstantes;

public class PersonalCargoBean implements Serializable {

    private Integer idPersonalCargo;
    private PersonalBean personalBean; //idPersonaBean
    private UnidadOrganicaBean unidadOrganicaBean; //idUnidadOrg
    private UnidadNegocioBean unidadNegocioBean;//uniNeg
    private CargoBean cargoBean; //idCargoBean
    private Boolean flgCargoConfianza;
    private BigDecimal asigCargo;
    private Date fecIni;
    private Date fecTer;
    private String documento;
    private String obs;
    private Boolean status;
    private String creaPor;
    private String modiPor;
    private Date creaFecha;
    private String flgCargoConfianzaVista;
    private Boolean collapsed = true;
    private String statusVista;

    private GradoAcademicoBean gradoAcademicoBean;
    private String nombreCurso;
    private Boolean flgCargoPrincipal;
    private BigDecimal asigCargoEstatal;
    
    public PersonalCargoBean() {
        this.status=true;
    }

    public Integer getIdPersonalCargo() {
        return idPersonalCargo;
    }

    public void setIdPersonalCargo(Integer idPersonalCargo) {
        this.idPersonalCargo = idPersonalCargo;
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

    public UnidadOrganicaBean getUnidadOrganicaBean() {
        if (unidadOrganicaBean == null) {
            unidadOrganicaBean = new UnidadOrganicaBean();
        }
        return unidadOrganicaBean;
    }

    public void setUnidadOrganicaBean(UnidadOrganicaBean unidadOrganicaBean) {
        this.unidadOrganicaBean = unidadOrganicaBean;
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

    public CargoBean getCargoBean() {
        if (cargoBean == null) {
            cargoBean = new CargoBean();
        }
        return cargoBean;
    }

    public void setCargoBean(CargoBean cargoBean) {
        this.cargoBean = cargoBean;
    }

    public BigDecimal getAsigCargo() {
        return asigCargo;
    }

    public void setAsigCargo(BigDecimal asigCargo) {
        this.asigCargo = asigCargo;
    }

    public Date getFecIni() {
        return fecIni;
    }

    public void setFecIni(Date fecIni) {
        this.fecIni = fecIni;
    }

    public Date getFecTer() {
        return fecTer;
    }

    public void setFecTer(Date fecTer) {
        this.fecTer = fecTer;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
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

    public String getFlgCargoConfianzaVista() {
        if (flgCargoConfianza != null) {
            if (flgCargoConfianza == true) {
                flgCargoConfianzaVista = "Sí";
                return flgCargoConfianzaVista;
            }
            if (flgCargoConfianza == false) {
                flgCargoConfianzaVista = "No";
                return flgCargoConfianzaVista;
            }
        } else {
            flgCargoConfianzaVista = "No";
        }
        return flgCargoConfianzaVista;
    }

    public void setFlgCargoConfianzaVista(String flgCargoConfianzaVista) {
        this.flgCargoConfianzaVista = flgCargoConfianzaVista;
    }

    public String getStatusVista() {
        if (status != null) {
            if (status == true) {
                statusVista = MaristaConstantes.ESTADO_ACTIVO_DES;
            }
            if (status == false) {
                statusVista = MaristaConstantes.ESTADO_INACTIVO_DES;
            }
        } else {
            statusVista = MaristaConstantes.ESTADO_INACTIVO_DES;
        }
        return statusVista;
    }

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public Boolean getFlgCargoConfianza() {
        return flgCargoConfianza;
    }

    public void setFlgCargoConfianza(Boolean flgCargoConfianza) {
        this.flgCargoConfianza = flgCargoConfianza;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getCollapsed() {
        return collapsed;
    }

    public void setCollapsed(Boolean collapsed) {
        this.collapsed = collapsed;
    }

    public GradoAcademicoBean getGradoAcademicoBean() {
        if (gradoAcademicoBean==null) {
         gradoAcademicoBean= new GradoAcademicoBean();
        }
        return gradoAcademicoBean;
    }

    public void setGradoAcademicoBean(GradoAcademicoBean gradoAcademicoBean) {
        this.gradoAcademicoBean = gradoAcademicoBean;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public Boolean getFlgCargoPrincipal() {
        return flgCargoPrincipal;
    }

    public void setFlgCargoPrincipal(Boolean flgCargoPrincipal) {
        this.flgCargoPrincipal = flgCargoPrincipal;
    }

    public BigDecimal getAsigCargoEstatal() {
        return asigCargoEstatal;
    }

    public void setAsigCargoEstatal(BigDecimal asigCargoEstatal) {
        this.asigCargoEstatal = asigCargoEstatal;
    }

}
