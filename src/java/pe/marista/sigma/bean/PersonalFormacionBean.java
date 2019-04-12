/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;
import pe.marista.sigma.MaristaConstantes;

/**
 *
 * @author Administrador
 */
public class PersonalFormacionBean implements Serializable{

    private Integer idPersonalFormacion;
    private UnidadNegocioBean  unidadNegocioBean;
    private PersonalBean personalBean; //idPersonal
    private GradoAcademicoBean gradoAcademicoBean; //id
    private CarreraBean carreraBean; //id
    private EntidadBean entidadBean;//ruc
    private String otraCarrera;
    private Integer mesIni;
    private Integer mesFin;
    private String descripcionGrado;
    private Integer anioIni;
    private Integer anioFin;
    private PaisBean paisBean;//id
    private boolean flgGrado;
    private boolean flgTitulo;
    private String obs;
    private String nombreTitulo;
    private String creaPor;
    private String modiPor;
    private Date creaFecha;
    
    //Ayuda
    private Integer anoIniCom;//comodin
    private Integer anoFinCom;//comodin
    private String anioFinVista;
    private boolean collapsed=true;
    
    private String nombreInstitucion;
    private NivelAcademicoBean nivelAcademicoBean;
    private String nombreCarrera;
    private Boolean flgSoloConcluido;
    private Boolean flgProceso;
    private Boolean flgMarista;
    private CodigoBean tipoEstudioBean;
    private CodigoBean tipoModalidadBean;

    public Integer getIdPersonalFormacion() {
        return idPersonalFormacion;
    }

    public void setIdPersonalFormacion(Integer idPersonalFormacion) {
        this.idPersonalFormacion = idPersonalFormacion;
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

    public GradoAcademicoBean getGradoAcademicoBean() {
        if (gradoAcademicoBean == null) {
            gradoAcademicoBean = new GradoAcademicoBean();
        }
        return gradoAcademicoBean;
    }

    public void setGradoAcademicoBean(GradoAcademicoBean gradoAcademicoBean) {
        this.gradoAcademicoBean = gradoAcademicoBean;
    }

    public CarreraBean getCarreraBean() {
        if (carreraBean == null) {
            carreraBean = new CarreraBean();
        }
        return carreraBean;
    }

    public void setCarreraBean(CarreraBean carreraBean) {
        this.carreraBean = carreraBean;
    }

    public EntidadBean getEntidadBean() {
        if (entidadBean == null) {
            entidadBean = new EntidadBean();
        }
        return entidadBean;
    }

    public void setEntidadBean(EntidadBean entidadBean) {
        this.entidadBean = entidadBean;
    }

    public String getOtraCarrera() {
        return otraCarrera;
    }

    public void setOtraCarrera(String otraCarrera) {
        this.otraCarrera = otraCarrera;
    }

    public Integer getMesIni() {
        return mesIni;
    }

    public void setMesIni(Integer mesIni) {
        this.mesIni = mesIni;
    }

    public Integer getMesFin() {
        return mesFin;
    }

    public void setMesFin(Integer mesFin) {
        this.mesFin = mesFin;
    }

    public Integer getAnioIni() {
        return anioIni;
    }

    public void setAnioIni(Integer anioIni) {
        this.anioIni = anioIni;
    }

    public Integer getAnioFin() {
        return anioFin;
    }

    public void setAnioFin(Integer anioFin) {
        this.anioFin = anioFin;
    }

    public PaisBean getPaisBean() {
        if (paisBean == null) {
            paisBean = new PaisBean();
        }
        return paisBean;
    }

    public void setPaisBean(PaisBean paisBean) {
        this.paisBean = paisBean;
    }

    public String getNombreTitulo() {
        return nombreTitulo;
    }

    public void setNombreTitulo(String nombreTitulo) {
        this.nombreTitulo = nombreTitulo;
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

    public boolean isFlgGrado() {
        return flgGrado;
    }

    public void setFlgGrado(boolean flgGrado) {
        this.flgGrado = flgGrado;
    }

    public boolean isFlgTitulo() {
        return flgTitulo;
    }

    public void setFlgTitulo(boolean flgTitulo) {
        this.flgTitulo = flgTitulo;
    }

    public Integer getAnoIniCom() {
        return anoIniCom;
    }

    public void setAnoIniCom(Integer anoIniCom) {
        this.anoIniCom = anoIniCom;
    }

    public Integer getAnoFinCom() {
        return anoFinCom;
    }

    public void setAnoFinCom(Integer anoFinCom) {
        this.anoFinCom = anoFinCom;
    }

    public String getDescripcionGrado() {
        return descripcionGrado;
    }

    public void setDescripcionGrado(String descripcionGrado) {
        this.descripcionGrado = descripcionGrado;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    } 

    public String getAnioFinVista() {
        if (anioFin == null) {
            anioFinVista = MaristaConstantes.SIN_FECHA_TERMINO;
            return anioFinVista;
        }
        if (anioFin != null) {
            anioFinVista = anioFin.toString();
            return anioFinVista;
        }
        return anioFinVista;
    }

    public void setAnioFinVista(String anioFinVista) {
        this.anioFinVista = anioFinVista;
    }

    public boolean isCollapsed() {
        return collapsed;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
    }

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
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

    public String getNombreInstitucion() {
        return nombreInstitucion;
    }

    public void setNombreInstitucion(String nombreInstitucion) {
        this.nombreInstitucion = nombreInstitucion;
    }

    public NivelAcademicoBean getNivelAcademicoBean() {
        if (nivelAcademicoBean==null) {
            nivelAcademicoBean= new NivelAcademicoBean();
        }
        return nivelAcademicoBean;
    }

    public void setNivelAcademicoBean(NivelAcademicoBean nivelAcademicoBean) {
        this.nivelAcademicoBean = nivelAcademicoBean;
    }

    public String getNombreCarrera() {
        return nombreCarrera;
    }

    public void setNombreCarrera(String nombreCarrera) {
        this.nombreCarrera = nombreCarrera;
    }

    public Boolean getFlgSoloConcluido() {
        return flgSoloConcluido;
    }

    public void setFlgSoloConcluido(Boolean flgSoloConcluido) {
        this.flgSoloConcluido = flgSoloConcluido;
    }

    public Boolean getFlgProceso() {
        return flgProceso;
    }

    public void setFlgProceso(Boolean flgProceso) {
        this.flgProceso = flgProceso;
    }

    public Boolean getFlgMarista() {
        return flgMarista;
    }

    public void setFlgMarista(Boolean flgMarista) {
        this.flgMarista = flgMarista;
    }

    public CodigoBean getTipoEstudioBean() {
        if (tipoEstudioBean==null) {
            tipoEstudioBean= new CodigoBean();
        }
        return tipoEstudioBean;
    }

    public void setTipoEstudioBean(CodigoBean tipoEstudioBean) {
        this.tipoEstudioBean = tipoEstudioBean;
    } 

    public CodigoBean getTipoModalidadBean() {
        if (tipoModalidadBean==null) {
            tipoModalidadBean= new CodigoBean();
        }
        return tipoModalidadBean;
    }

    public void setTipoModalidadBean(CodigoBean tipoModalidadBean) {
        this.tipoModalidadBean = tipoModalidadBean;
    }

}
