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
public class PersonalOtrosEstudiosBean implements Serializable {

    private Integer idPersonalOtrosEstudios;
    private UnidadNegocioBean unidadNegocioBean;
    private PersonalBean personalBean; //idPersonal
//    private EntidadBean entidadBean;//id
    private String centroEstudio;
    private PaisBean paisBean;//id 
    private Integer mesIni;
    private Integer mesFin;
    private Integer anioIni;
    private Integer anioFin;
    private Integer nroHoras;
    private Integer nroCreditos;
    private String obs;
    private String modiPor;
    private String creaPor;
    private Date creaFecha;
    private String anioFinVista;
    private boolean collapsed = true;
    private CodigoBean tipoModalidadBean;
    private CodigoBean tipoOtrosEstudiosBean;
    private Boolean flgCertificado;
    private String nombreCertificado;
    private CodigoBean tipoFinanciamientoBean;
    private Double financiamientoPropio;
    private Double financiamientoInstitucional;
    //Ayuda
    private Integer anoIniCom;//comodin
    private Integer anoFinCom;//comodin
    private String curso;

    public PersonalBean getPersonalBean() {
        if (personalBean == null) {
            personalBean = new PersonalBean();
        }
        return personalBean;
    }

    public void setPersonalBean(PersonalBean personalBean) {
        this.personalBean = personalBean;
    }

//    public EntidadBean getEntidadBean() {
//        if (entidadBean == null) {
//            entidadBean = new EntidadBean();
//        }
//        return entidadBean;
//    }
//
//    public void setEntidadBean(EntidadBean entidadBean) {
//        this.entidadBean = entidadBean;
//    }
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
    
    public Integer getNroHoras() {
        return nroHoras;
    }

    public void setNroHoras(Integer nroHoras) {
        this.nroHoras = nroHoras;
    }

    public Integer getNroCreditos() {
        return nroCreditos;
    }

    public void setNroCreditos(Integer nroCreditos) {
        this.nroCreditos = nroCreditos;
    }

    public Integer getIdPersonalOtrosEstudios() {
        return idPersonalOtrosEstudios;
    }

    public void setIdPersonalOtrosEstudios(Integer idPersonalOtrosEstudios) {
        this.idPersonalOtrosEstudios = idPersonalOtrosEstudios;
    }

    public String getAnioFinVista() {
        if (anioFin == null) {
            anioFinVista = MaristaConstantes.SIN_FECHA_TER_OTROEST;
            return anioFinVista;
        }
        if (anioFin != null) {
            anioFinVista = anioFin.toString();
            return anioFinVista;
        }
        return anioFinVista;
    }

    public boolean isCollapsed() {
        return collapsed;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
    }

    public String getCentroEstudio() {
        return centroEstudio;
    }

    public void setCentroEstudio(String centroEstudio) {
        this.centroEstudio = centroEstudio;
    }

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
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

    public CodigoBean getTipoModalidadBean() {
        if (tipoModalidadBean==null) {
            tipoModalidadBean= new CodigoBean();
        }
        return tipoModalidadBean;
    }

    public void setTipoModalidadBean(CodigoBean tipoModalidadBean) {
        this.tipoModalidadBean = tipoModalidadBean;
    }

    public CodigoBean getTipoOtrosEstudiosBean() {
        if (tipoOtrosEstudiosBean==null) {
            tipoOtrosEstudiosBean= new CodigoBean();
        }
        return tipoOtrosEstudiosBean;
    }

    public void setTipoOtrosEstudiosBean(CodigoBean tipoOtrosEstudiosBean) {
        this.tipoOtrosEstudiosBean = tipoOtrosEstudiosBean;
    }

    public Boolean getFlgCertificado() {
        return flgCertificado;
    }

    public void setFlgCertificado(Boolean flgCertificado) {
        this.flgCertificado = flgCertificado;
    }

    public String getNombreCertificado() {
        return nombreCertificado;
    }

    public void setNombreCertificado(String nombreCertificado) {
        this.nombreCertificado = nombreCertificado;
    }

    public CodigoBean getTipoFinanciamientoBean() {
        if (tipoFinanciamientoBean==null) {
            tipoFinanciamientoBean= new CodigoBean();
        }
        return tipoFinanciamientoBean;
    }

    public void setTipoFinanciamientoBean(CodigoBean tipoFinanciamientoBean) {
        this.tipoFinanciamientoBean = tipoFinanciamientoBean;
    }

    public Double getFinanciamientoPropio() {
        return financiamientoPropio;
    }

    public void setFinanciamientoPropio(Double financiamientoPropio) {
        this.financiamientoPropio = financiamientoPropio;
    }

    public Double getFinanciamientoInstitucional() {
        return financiamientoInstitucional;
    }

    public void setFinanciamientoInstitucional(Double financiamientoInstitucional) {
        this.financiamientoInstitucional = financiamientoInstitucional;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    
}
