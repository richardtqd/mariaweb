/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Administrador
 */
public class PersonalDependienteBean implements Serializable {

    private Integer idPersonalDependiente;
    private UnidadNegocioBean unidadNegocioBean;
    private PersonalBean personalBean;//idPersonal
    private String nombre;
    private String apepat;
    private String apemat;
    private Date fecNac;
    private int sexo = 1;
    private CodigoBean tipoEstadoCivilBean;//idTipoEstadoCivil
    private CodigoBean tipoDocPerBean;//idTipoDocPersonal
    private String nroDoc;
    private CodigoBean tipoParentescoBean;//idTipoParentesco
    private PaisBean paisNacionalidadBean; //idNacionalidad
    private CarreraBean carreraBean;
    private GradoAcademicoBean gradoAcademicoBean;
    private String centroLaboral;
    private String cargo;
    private String area;
    private String especialidad;
    private String telefono;

    private String creaPor;
    private String modiPor;
    private Date creaFecha;

    private boolean flgVive = true;

    //ayuda
    private String nombreCompleto;
    private int flgViveInt;
    private boolean collapsed = true;
    
    private String nombreCarrera;
    private Boolean flgAsignacionFamiliar;
    private Boolean flgBecas;

    public PersonalDependienteBean() {
    }

    public Integer getIdPersonalDependiente() {
        return idPersonalDependiente;
    }

    public void setIdPersonalDependiente(Integer idPersonalDependiente) {
        this.idPersonalDependiente = idPersonalDependiente;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApepat() {
        return apepat;
    }

    public void setApepat(String apepat) {
        this.apepat = apepat;
    }

    public String getApemat() {
        return apemat;
    }

    public void setApemat(String apemat) {
        this.apemat = apemat;
    }

    public Date getFecNac() {
        return fecNac;
    }

    public void setFecNac(Date fecNac) {
        this.fecNac = fecNac;
    }

    public int getSexo() {
        return sexo;
    }

    public void setSexo(int sexo) {
        this.sexo = sexo;
    }

    public CodigoBean getTipoEstadoCivilBean() {
        if (tipoEstadoCivilBean == null) {
            tipoEstadoCivilBean = new CodigoBean();
        }
        return tipoEstadoCivilBean;
    }

    public void setTipoEstadoCivilBean(CodigoBean tipoEstadoCivilBean) {
        this.tipoEstadoCivilBean = tipoEstadoCivilBean;
    }

    public CodigoBean getTipoDocPerBean() {
        if (tipoDocPerBean == null) {
            tipoDocPerBean = new CodigoBean();
        }
        return tipoDocPerBean;
    }

    public void setTipoDocPerBean(CodigoBean tipoDocPerBean) {
        this.tipoDocPerBean = tipoDocPerBean;
    }

    public String getNroDoc() {
        return nroDoc;
    }

    public void setNroDoc(String nroDoc) {
        this.nroDoc = nroDoc;
    }

    public CodigoBean getTipoParentescoBean() {
        if (tipoParentescoBean == null) {
            tipoParentescoBean = new CodigoBean();
        }
        return tipoParentescoBean;
    }

    public void setTipoParentescoBean(CodigoBean tipoParentescoBean) {
        this.tipoParentescoBean = tipoParentescoBean;
    }

    public PaisBean getPaisNacionalidadBean() {
        if (paisNacionalidadBean == null) {
            paisNacionalidadBean = new PaisBean();
        }
        return paisNacionalidadBean;
    }

    public void setPaisNacionalidadBean(PaisBean paisNacionalidadBean) {
        this.paisNacionalidadBean = paisNacionalidadBean;
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

    public GradoAcademicoBean getGradoAcademicoBean() {
        if (gradoAcademicoBean == null) {
            gradoAcademicoBean = new GradoAcademicoBean();
        }
        return gradoAcademicoBean;
    }

    public void setGradoAcademicoBean(GradoAcademicoBean gradoAcademicoBean) {
        this.gradoAcademicoBean = gradoAcademicoBean;
    }

    public String getCentroLaboral() {
        return centroLaboral;
    }

    public void setCentroLaboral(String centroLaboral) {
        this.centroLaboral = centroLaboral;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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

    public String getNombreCompleto() {
        StringBuilder sb = new StringBuilder();
        if (apepat != null) {
            sb.append(apepat).append(" ");
        }
        if (apemat != null) {
            sb.append(apemat).append(" ");
        }
        if (nombre != null) {
            sb.append(nombre);
        }
        nombreCompleto = sb.toString();
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public boolean isFlgVive() {
        return flgVive;
    }

    public int getFlgViveInt() {
        if (flgVive) {
            return 1;
        } else {
            return 0;
        }
    }

    public void setFlgVive(boolean flgVive) {
        this.flgVive = flgVive;
    }

    public void setFlgViveInt(int flgViveInt) {
        this.flgViveInt = flgViveInt;
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

    public String getNombreCarrera() {
        return nombreCarrera;
    }

    public void setNombreCarrera(String nombreCarrera) {
        this.nombreCarrera = nombreCarrera;
    }

    public Boolean getFlgAsignacionFamiliar() {
        return flgAsignacionFamiliar;
    }

    public void setFlgAsignacionFamiliar(Boolean flgAsignacionFamiliar) {
        this.flgAsignacionFamiliar = flgAsignacionFamiliar;
    }

    public Boolean getFlgBecas() {
        return flgBecas;
    }

    public void setFlgBecas(Boolean flgBecas) {
        this.flgBecas = flgBecas;
    }

}
