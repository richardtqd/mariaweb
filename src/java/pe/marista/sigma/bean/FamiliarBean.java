package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;
import org.primefaces.model.UploadedFile;
import pe.marista.sigma.MaristaConstantes;

/**
 *
 * @author Administrador
 */
public class FamiliarBean implements Serializable {

    private UnidadNegocioBean unidadNegocioBean;
    private PersonaBean personaBean;//idFamiliar
    private String profesion;//idTipoProfesion
    private String ocupacion;// 
    private CodigoBean tipoEstCivil;//idTipoEstCivil
    private Boolean flgVive;
    private Boolean flgCatolico;
    private Boolean flgMarista;
    private Integer anioEgresoColegio;
    private String nombreColegio;
    private String foto;
    private String telefonoFijo;
    private String telefonoCelular;
    private String telefonoOficina;
    private String centroLaboral;
    private String cargo;
    private String hobby;
    private Boolean flgGrupoParroquial;
    private String nombreParroquia;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private String direccion;
    private String dniEstudiante;
    private String dniFamiliar;

    //Complementos
    private UploadedFile file;

    public FamiliarBean() {
        this.flgVive = true;
        this.flgCatolico = true;
        this.flgMarista = false;
        this.flgGrupoParroquial = false;
    }

    public PersonaBean getPersonaBean() {
        if (personaBean == null) {
            personaBean = new PersonaBean();
        }
        return personaBean;
    }

    public void setPersonaBean(PersonaBean personaBean) {
        this.personaBean = personaBean;
    }

    public Boolean getFlgVive() {
        return flgVive;
    }

    public String getFlgViveVista() {
        if (flgVive) {
            return MaristaConstantes.SI;
        } else {
            return MaristaConstantes.NO;
        }
    }

    public void setFlgVive(Boolean flgVive) {
        this.flgVive = flgVive;
    }

    public Boolean getFlgCatolico() {
        return flgCatolico;
    }

    public String getFlgCatolicoVista() {
        if (flgCatolico) {
            return MaristaConstantes.SI;
        } else {
            return MaristaConstantes.NO;
        }
    }

    public void setFlgCatolico(Boolean flgCatolico) {
        this.flgCatolico = flgCatolico;
    }

    public Boolean getFlgMarista() {
        return flgMarista;
    }

    public String getFlgMaristaVista() {
        if (flgMarista) {
            return MaristaConstantes.SI;
        } else {
            return MaristaConstantes.NO;
        }
    }

    public void setFlgMarista(Boolean flgMarista) {
        this.flgMarista = flgMarista;
    }

    public String getTelefonoFijo() {
        return telefonoFijo;
    }

    public void setTelefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    public String getTelefonoCelular() {
        return telefonoCelular;
    }

    public void setTelefonoCelular(String telefonoCelular) {
        this.telefonoCelular = telefonoCelular;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String getNoFoto() {
        return MaristaConstantes.RUTA_DOCU_FAM + "noFoto.jpg";
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

    public CodigoBean getTipoEstCivil() {
        if (tipoEstCivil == null) {
            tipoEstCivil = new CodigoBean();
        }
        return tipoEstCivil;
    }

    public void setTipoEstCivil(CodigoBean tipoEstCivil) {
        this.tipoEstCivil = tipoEstCivil;
    }

    public Integer getAnioEgresoColegio() {
        return anioEgresoColegio;
    }

    public void setAnioEgresoColegio(Integer anioEgresoColegio) {
        this.anioEgresoColegio = anioEgresoColegio;
    }

    public String getNombreColegio() {
        return nombreColegio;
    }

    public void setNombreColegio(String nombreColegio) {
        this.nombreColegio = nombreColegio;
    }

    public String getTelefonoOficina() {
        return telefonoOficina;
    }

    public void setTelefonoOficina(String telefonoOficina) {
        this.telefonoOficina = telefonoOficina;
    }

    public String getCentroLaboral() {
        return centroLaboral;
    }

    public void setCentroLaboral(String centroLaboral) {
        this.centroLaboral = centroLaboral;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public Boolean getFlgGrupoParroquial() {
        return flgGrupoParroquial;
    }

    public String getFlgGrupoParroquialVista() {
        if (flgGrupoParroquial == null) {
            return "";
        }
        if (flgGrupoParroquial) {
            return MaristaConstantes.SI;
        } else {
            return MaristaConstantes.NO;
        }

    }

    public void setFlgGrupoParroquial(Boolean flgGrupoParroquial) {
        this.flgGrupoParroquial = flgGrupoParroquial;
    }

    public String getNombreParroquia() {
        return nombreParroquia;
    }

    public void setNombreParroquia(String nombreParroquia) {
        this.nombreParroquia = nombreParroquia;
    }

    public Date getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(Date creaFecha) {
        this.creaFecha = creaFecha;
    }

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public String getCreaPor() {
        return creaPor;
    }

    public void setCreaPor(String creaPor) {
        this.creaPor = creaPor;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDniEstudiante() {
        return dniEstudiante;
    }

    public void setDniEstudiante(String dniEstudiante) {
        this.dniEstudiante = dniEstudiante;
    }

    public String getDniFamiliar() {
        return dniFamiliar;
    }

    public void setDniFamiliar(String dniFamiliar) {
        this.dniFamiliar = dniFamiliar;
    }

}
