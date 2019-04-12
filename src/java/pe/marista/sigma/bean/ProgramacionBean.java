package pe.marista.sigma.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Administrador
 */
public class ProgramacionBean implements Serializable {

    private Integer idProgramacion;
    private ConceptoUniNegBean conceptoUniNegBean;
    private ConceptoBean conceptoBean;//idConcepto
    private UnidadNegocioBean unidadNegocioBean;//uniNeg
    private ProcesoBean procesoBean;//idProceso
    private AmbienteBean ambienteBean;//idAmbiente
    private Integer anio;
    private Double precio;
    private Date fecIni;
    private Date fecFin;
    private Integer max;
    private Integer min;
    private CentroResponsabilidadBean centroResponsabilidadBean;//cr
    private Integer cuenta;
    private String horario;
    private Boolean status;
    private String seccion;
    private String grupo;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private String descripProgramacion;
    private CodigoBean idTipoMoneda;
    private GradoAcademicoBean gradoAcademicoBean; //id

    //Valdia MaxMin
    private Integer ocupados;
    private Integer disponibles;
    //Valdia MaxMin
    private Integer ocupadosCT;
    private Integer disponiblesCT;
    //Nombre
    private String nombreProgramacion;

    //Valdia MaxMin
    private Integer ocupadosAdm;
    private Integer disponiblesAdm;

    //cupos
    private Integer cupos;

    private Boolean flgInscrito;
    private Boolean flgBloqueado;

    private String fechaIniFinVista;
    private String glosa;
    private String seccVista;
    private Integer mes;
    private String mesProg;

    private Boolean flgSelect;
    private Integer statusProgramacion;

    public ProgramacionBean() {
    }

    public ProgramacionBean(UnidadNegocioBean unidadNegocioBean, String codigo) {
        this.unidadNegocioBean = unidadNegocioBean;
        CodigoBean cod = new CodigoBean();
        cod.setCodigo(codigo);
        ProcesoBean pb = new ProcesoBean();
        pb.setCodigoBean(cod);
        this.procesoBean = pb;
    }

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public Integer getIdProgramacion() {
        return idProgramacion;
    }

    public void setIdProgramacion(Integer idProgramacion) {
        this.idProgramacion = idProgramacion;
    }

//    public ConceptoBean getConceptoBean() {
//        if (conceptoBean == null) {
//            conceptoBean = new ConceptoBean();
//        }
//        return conceptoBean;
//    }
//
//    public void setConceptoBean(ConceptoBean conceptoBean) {
//        this.conceptoBean = conceptoBean;
//    }
    public ProcesoBean getProcesoBean() {
        if (procesoBean == null) {
            procesoBean = new ProcesoBean();
        }
        return procesoBean;
    }

    public void setProcesoBean(ProcesoBean procesoBean) {
        this.procesoBean = procesoBean;
    }

    public AmbienteBean getAmbienteBean() {
        if (ambienteBean == null) {
            ambienteBean = new AmbienteBean();
        }
        return ambienteBean;
    }

    public void setAmbienteBean(AmbienteBean ambienteBean) {
        this.ambienteBean = ambienteBean;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Date getFecIni() {
        return fecIni;
    }

    public void setFecIni(Date fecIni) {
        this.fecIni = fecIni;
    }

    public Date getFecFin() {
        return fecFin;
    }

    public void setFecFin(Date fecFin) {
        this.fecFin = fecFin;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

//    public CentroResponsabilidadBean getCentroResponsabilidadBean() {
//        if (centroResponsabilidadBean == null) {
//            centroResponsabilidadBean = new CentroResponsabilidadBean();
//        }
//        return centroResponsabilidadBean;
//    }
//
//    public void setCentroResponsabilidadBean(CentroResponsabilidadBean centroResponsabilidadBean) {
//        this.centroResponsabilidadBean = centroResponsabilidadBean;
//    }
//    public UnidadNegocioBean getUnidadNegocioBean() {
//        if (unidadNegocioBean == null) {
//            unidadNegocioBean = new UnidadNegocioBean();
//        }
//        return unidadNegocioBean;
//    }
//
//    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
//        this.unidadNegocioBean = unidadNegocioBean;
//    }
    public Integer getCuenta() {
        return cuenta;
    }

    public void setCuenta(Integer cuenta) {
        this.cuenta = cuenta;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
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

    public Integer getOcupados() {
        return ocupados;
    }

    public void setOcupados(Integer ocupados) {
        this.ocupados = ocupados;
    }

    public Integer getDisponibles() {
        return disponibles;
    }

    public void setDisponibles(Integer disponibles) {
        this.disponibles = disponibles;
    }

    public String getNombreProgramacion() {
        StringBuilder sb = new StringBuilder();
        if (procesoBean != null && procesoBean.getDescripcion() != null) {
            sb.append(procesoBean.getDescripcion()).append(" - ");
        }
        if (ambienteBean != null && ambienteBean.getNombre() != null) {
            sb.append(ambienteBean.getNombre()).append(" - ");
        }
        if (seccion != null) {
            sb.append(seccion).append(" - ");
        }
        if (grupo != null) {
            sb.append(grupo);
        }
        nombreProgramacion = sb.toString();
        return nombreProgramacion;
    }

    public void setNombreProgramacion(String nombreProgramacion) {
        this.nombreProgramacion = nombreProgramacion;
    }

    public ConceptoUniNegBean getConceptoUniNegBean() {
        if (conceptoUniNegBean == null) {
            conceptoUniNegBean = new ConceptoUniNegBean();
        }
        return conceptoUniNegBean;
    }

    public void setConceptoUniNegBean(ConceptoUniNegBean conceptoUniNegBean) {
        this.conceptoUniNegBean = conceptoUniNegBean;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public ConceptoBean getConceptoBean() {
        if (conceptoBean == null) {
            conceptoBean = new ConceptoBean();
        }
        return conceptoBean;
    }

    public void setConceptoBean(ConceptoBean conceptoBean) {
        this.conceptoBean = conceptoBean;
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

    public CodigoBean getIdTipoMoneda() {
        if (idTipoMoneda == null) {
            idTipoMoneda = new CodigoBean();
        }
        return idTipoMoneda;
    }

    public void setIdTipoMoneda(CodigoBean idTipoMoneda) {
        this.idTipoMoneda = idTipoMoneda;
    }

    public CentroResponsabilidadBean getCentroResponsabilidadBean() {
        if (centroResponsabilidadBean == null) {
            centroResponsabilidadBean = new CentroResponsabilidadBean();
        }
        return centroResponsabilidadBean;
    }

    public void setCentroResponsabilidadBean(CentroResponsabilidadBean centroResponsabilidadBean) {
        this.centroResponsabilidadBean = centroResponsabilidadBean;
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

    public Integer getOcupadosCT() {
        return ocupadosCT;
    }

    public void setOcupadosCT(Integer ocupadosCT) {
        this.ocupadosCT = ocupadosCT;
    }

    public Integer getDisponiblesCT() {
        return disponiblesCT;
    }

    public void setDisponiblesCT(Integer disponiblesCT) {
        this.disponiblesCT = disponiblesCT;
    }

    public Integer getOcupadosAdm() {
        return ocupadosAdm;
    }

    public void setOcupadosAdm(Integer ocupadosAdm) {
        this.ocupadosAdm = ocupadosAdm;
    }

    public Integer getDisponiblesAdm() {
        return disponiblesAdm;
    }

    public void setDisponiblesAdm(Integer disponiblesAdm) {
        this.disponiblesAdm = disponiblesAdm;
    }

    public Boolean getFlgInscrito() {
        return flgInscrito;
    }

    public void setFlgInscrito(Boolean flgInscrito) {
        this.flgInscrito = flgInscrito;
    }

    public String getFechaIniFinVista() {
        String i = "-";
        String f = "-";
        if (fecIni != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String date = sdf.format(fecIni);
            i = date;
        }
        if (fecFin != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String date = sdf.format(fecFin);
            f = date;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(i).append(" - ").append(f);
        fechaIniFinVista = sb.toString();
        return fechaIniFinVista;
    }

    public void setFechaIniFinVista(String fechaIniFinVista) {
        this.fechaIniFinVista = fechaIniFinVista;
    }

    public String getSeccVista() {
        if (seccion == null || seccion.equals("")) {
            seccVista = "-";
        } else {
            seccVista = seccion;
        }
        return seccVista;
    }

    public void setSeccVista(String seccVista) {
        this.seccVista = seccVista;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public String getMesProg() {
        return mesProg;
    }

    public void setMesProg(String mesProg) {
        this.mesProg = mesProg;
    }

    public Boolean getFlgBloqueado() {
        return flgBloqueado;
    }

    public void setFlgBloqueado(Boolean flgBloqueado) {
        this.flgBloqueado = flgBloqueado;
    }

    public String getDescripProgramacion() {
        return descripProgramacion;
    }

    public void setDescripProgramacion(String descripProgramacion) {
        this.descripProgramacion = descripProgramacion;
    }

    public Boolean getFlgSelect() {
        return flgSelect;
    }

    public void setFlgSelect(Boolean flgSelect) {
        this.flgSelect = flgSelect;
    }

    public Integer getCupos() {
        return cupos;
    }

    public void setCupos(Integer cupos) {
        this.cupos = cupos;
    }

    public String getGlosa() {
        return glosa;
    }

    public void setGlosa(String glosa) {
        this.glosa = glosa;
    }

    public Integer getStatusProgramacion() {
        return statusProgramacion;
    }

    public void setStatusProgramacion(Integer statusProgramacion) {
        this.statusProgramacion = statusProgramacion;
    }

}
