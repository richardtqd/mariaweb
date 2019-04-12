package pe.marista.sigma.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import pe.marista.sigma.MaristaConstantes;

/**
 *
 * @author Administrador
 */
public class AdmisionBean implements Serializable {

    private Integer idAdmision;
    private EstudianteBean estudianteBean;//idEstudiante
    private UnidadNegocioBean unidadNegocioBean;//uniNeg  
    private Integer anio;
    private GradoAcademicoBean gradoAcademicoBean;//idGradoPostula 
    private ProgramacionBean programacionBean;//idProgramacion 
    private Date fechaInscripcion;
    private Date fecExamen;
    private Date horaExamen;
    private String grupo;
    private CodigoBean codigoBean;//idTipoStatusPostulante
    private String creaPor;
    private String docRefeIngreso;
    private Date fechaIngreso;//se actualiza a la fecha de ingreso del estudiante
    private Date creaFecha;
    //Aprobacion Estudiante
    private Boolean estadoAprobacion;
    private String modiPor;

    //Ayuda
    private String fechaIngresoVista;
    private String docRefeIngresoVista;
    private String pagoCuotaIng;
    private Boolean flgEmail;

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public Integer getIdAdmision() {
        return idAdmision;
    }

    public void setIdAdmision(Integer idAdmision) {
        this.idAdmision = idAdmision;
    }

    public EstudianteBean getEstudianteBean() {
        if (estudianteBean == null) {
            estudianteBean = new EstudianteBean();
        }
        return estudianteBean;
    }

    public void setEstudianteBean(EstudianteBean estudianteBean) {
        this.estudianteBean = estudianteBean;
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

    public GradoAcademicoBean getGradoAcademicoBean() {
        if (gradoAcademicoBean == null) {
            gradoAcademicoBean = new GradoAcademicoBean();
        }
        return gradoAcademicoBean;
    }

    public void setGradoAcademicoBean(GradoAcademicoBean gradoAcademicoBean) {
        this.gradoAcademicoBean = gradoAcademicoBean;
    }

    public Date getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(Date fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public Date getFecExamen() {
        return fecExamen;
    }

    public void setFecExamen(Date fecExamen) {
        this.fecExamen = fecExamen;
    }

    public Date getHoraExamen() {
        return horaExamen;
    }

    public void setHoraExamen(Date horaExamen) {
        this.horaExamen = horaExamen;
    }

    public CodigoBean getCodigoBean() {
        if (codigoBean == null) {
            codigoBean = new CodigoBean();
        }
        return codigoBean;
    }

    public void setCodigoBean(CodigoBean codigoBean) {
        this.codigoBean = codigoBean;
    }

    public ProgramacionBean getProgramacionBean() {
        if (programacionBean == null) {
            programacionBean = new ProgramacionBean();
        }
        return programacionBean;
    }

    public void setProgramacionBean(ProgramacionBean programacionBean) {
        this.programacionBean = programacionBean;
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

    public Boolean getEstadoAprobacion() {
//        estadoAprobacion = codigoBean.getCodigo().equals(MaristaConstantes.COD_ADMITIDO);
        return estadoAprobacion;
    }

    public Boolean getEstadoAprobacionData() {
        return estadoAprobacion;
    }

    public void setEstadoAprobacion(Boolean estadoAprobacion) {
        this.estadoAprobacion = estadoAprobacion;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getDocRefeIngreso() {
        return docRefeIngreso;
    }

    public void setDocRefeIngreso(String docRefeIngreso) {
        this.docRefeIngreso = docRefeIngreso;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getFechaIngresoVista() {
        if (fechaIngreso != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String date = sdf.format(fechaIngreso);
            fechaIngresoVista = date;
        }
        if (fechaIngreso == null) {
            fechaIngresoVista = MaristaConstantes.SIN_FECHA_ING;
        }
        return fechaIngresoVista;
    }

    public void setFechaIngresoVista(String fechaIngresoVista) {
        this.fechaIngresoVista = fechaIngresoVista;
    }

    public String getDocRefeIngresoVista() {
        if (docRefeIngreso == null) {
            docRefeIngresoVista = MaristaConstantes.SIN_DOC_REF;
        } else {
            docRefeIngresoVista = docRefeIngreso;
        }
        return docRefeIngresoVista;
    }

    public void setDocRefeIngresoVista(String docRefeIngresoVista) {
        this.docRefeIngresoVista = docRefeIngresoVista;
    }

    public Boolean getFlgEmail() {
        return flgEmail;
    }

    public void setFlgEmail(Boolean flgEmail) {
        this.flgEmail = flgEmail;
    }

    public String getPagoCuotaIng() { 
        return pagoCuotaIng;
    }

    public void setPagoCuotaIng(String pagoCuotaIng) {
        this.pagoCuotaIng = pagoCuotaIng;
    }

}
