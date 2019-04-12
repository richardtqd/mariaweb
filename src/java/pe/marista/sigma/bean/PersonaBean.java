/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import pe.marista.sigma.MaristaConstantes;

/**
 *
 * @author GLOVON
 */
public class PersonaBean implements Serializable {

    private UnidadNegocioBean unidadNegocioBean;//idUniNeg
    private String idPersona;
    private String nombre;
    private String apepat;
    private String apemat;
    private int sexo;
    private CodigoBean idTipoDocPer;//idTipoDocPer=idCodigocodigoean
    private String nroDoc;
    private Date fecNac;
    private String correo;
    private PaisBean paisBean;//idNacionalidad
    private GradoAcademicoBean gradoAcademicoBean;//idGradoAcademico
    private boolean flgExAlumno;
    private CodigoBean tipoPersona;//idTipoPer
    private String contacto;

    //Propiedades Añadidas
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private String modiVer;

    //Ayuda
    private String nombreCompleto;
    private String nombreCompletoDesdeApellidos;
    private String nombreFiltro;
    private Integer filtro;//comodin
    private boolean coll = false;//comodin collapsable
    private String uniNegUsuario;
    private String idPersonaOld;
    private String estado;

    //AYUDA
    private Integer idCodigo;//cod postulante para el filtro
    private Integer count;// 

    private String foto;

    //AYUDA
    private String estadoPersona;

    //Ayuda
    private String sexoAyuda;
    private String fecNacAyuda;
    private String direccion;
    private String telefonoCelular;
    private Boolean flgVive;
    private String parentesco;

    //Ayuda
    private String profesion;

    public PersonaBean() {
        this.sexo = 1;
        this.flgExAlumno = false;
//        this.foto= "/noFoto.bmp";
    }

    public String getNombre() {
        return nombre;
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

    public String getNombreCompletoDesdeApellidos() {
        StringBuilder sb = new StringBuilder();
        if (apepat != null) {
            sb.append(apepat).append(" ");
        }
        if (apemat != null) {
            sb.append(apemat).append(", ");
        }
        if (nombre != null) {
            sb.append(nombre);
        }
        nombreCompletoDesdeApellidos = sb.toString();
        return nombreCompletoDesdeApellidos;
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

    public String getNroDoc() {
        return nroDoc;
    }

    public void setNroDoc(String nroDoc) {
        this.nroDoc = nroDoc;
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

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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

    public int getSexo() {
        return sexo;
    }

    public String getSexoVista() {
        if (sexo == 1) {
            return MaristaConstantes.MASCULINO;
        }
        if (sexo == 0) {
            return MaristaConstantes.FEMENINO;
        }
        return null;
    }

    public void setSexo(int sexo) {
        this.sexo = sexo;
    }

    public CodigoBean getIdTipoDocPer() {
        if (idTipoDocPer == null) {
            idTipoDocPer = new CodigoBean();
        }
        return idTipoDocPer;
    }

    public void setIdTipoDocPer(CodigoBean idTipoDocPer) {
        this.idTipoDocPer = idTipoDocPer;
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

    public Integer getFiltro() {
        return filtro;
    }

    public void setFiltro(Integer filtro) {
        this.filtro = filtro;
    }

    public CodigoBean getTipoPersona() {
        if (tipoPersona == null) {
            tipoPersona = new CodigoBean();
        }
        return tipoPersona;
    }

    public void setTipoPersona(CodigoBean tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public String getNombreFiltro() {
        return nombreFiltro;
    }

    public void setNombreFiltro(String nombreFiltro) {
        this.nombreFiltro = nombreFiltro;
    }

    public String getUniNegUsuario() {
        return uniNegUsuario;
    }

    public void setUniNegUsuario(String uniNegUsuario) {
        this.uniNegUsuario = uniNegUsuario;
    }

    public String getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    public boolean isColl() {
        return coll;
    }

    public void setColl(boolean coll) {
        this.coll = coll;
    }

    public boolean isFlgExAlumno() {
        return flgExAlumno;
    }

    public void setFlgExAlumno(boolean flgExAlumno) {
        this.flgExAlumno = flgExAlumno;
    }

    public String getCreaPor() {
        return creaPor;
    }

    public void setCreaPor(String creaPor) {
        this.creaPor = creaPor;
    }

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public String getModiVer() {
        return modiVer;
    }

    public void setModiVer(String modiVer) {
        this.modiVer = modiVer;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public Date getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(Date creaFecha) {
        this.creaFecha = creaFecha;
    }

    public void setNombreCompletoDesdeApellidos(String nombreCompletoDesdeApellidos) {
        this.nombreCompletoDesdeApellidos = nombreCompletoDesdeApellidos;
    }

    public String getIdPersonaOld() {
        return idPersonaOld;
    }

    public void setIdPersonaOld(String idPersonaOld) {
        this.idPersonaOld = idPersonaOld;
    }

    public Integer getIdCodigo() {
        return idCodigo;
    }

    public void setIdCodigo(Integer idCodigo) {
        this.idCodigo = idCodigo;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getEstadoPersona() {
        return estadoPersona;
    }

    public void setEstadoPersona(String estadoPersona) {
        this.estadoPersona = estadoPersona;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getSexoAyuda() {
        return sexoAyuda;
    }

    public void setSexoAyuda(String sexoAyuda) {
        this.sexoAyuda = sexoAyuda;
    }

    public String getFecNacAyuda() {
        return fecNacAyuda;
    }

    public void setFecNacAyuda(String fecNacAyuda) {
        this.fecNacAyuda = fecNacAyuda;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefonoCelular() {
        return telefonoCelular;
    }

    public void setTelefonoCelular(String telefonoCelular) {
        this.telefonoCelular = telefonoCelular;
    }

    public Boolean getFlgVive() {
        return flgVive;
    }

    public void setFlgVive(Boolean flgVive) {
        this.flgVive = flgVive;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

}
