package pe.marista.sigma.bean;

//import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SolicitudLogisticoBean implements Serializable {

    private Integer idRequerimiento;
    private UnidadNegocioBean unidadNegocioBean;
    private InventarioAlmacenBean inventarioAlmacenBean;
    private String unidadNegocio;
    private UnidadOrganicaBean unidadOrganicaBean;
    private String unidadOrganica;
    private UsuarioBean usuarioBean;
    private PersonalBean personalBean;
    private String solicitante;

    private ActividadBean actividadBean;
    private ObjOperativoBean objOperativoBean;
    private String actividad;
    private String titulo;
    private CodigoBean tipoCategoriaBean;
    private Integer idTipoCategoria;
    private String tipoCategoria;
    private CodigoBean tipoEstadoBean;
    private Integer idTipoEstado;
    private String tipoEstado;
    private Date fechaSolicitud;
    private Date fechaAprobacion;
    private Date fechaListo;
    private Date fechaEntrega;
    private CodigoBean tipoPrioridadBean;
    private Integer idTipoPrioridad;
    private String tipoPrioridad;
    private String creaPor;
    private Date creaFecha;
    private Double importe;
    private Integer idTipoMoneda;
    private String TipoMoneda;
    private Integer anio;
    private Date fechaInicio;
    private Date fechaFin;
    private String idPaso;
    private TipoSolicitudBean tipoSolicitudBean;
    private Integer idTipoSolicitud;
    private String objeto;
    private Integer idAutoriza1;
    private Integer idAutoriza2;
    private Integer idAutoriza3;
    private Integer nivelAutoriza;
    private Date fecAutoriza1;
    private Date fecAutoriza2;
    private Date fecAutoriza3;
    private Boolean flgAutoriza1;
    private Boolean flgAutoriza2;
    private Boolean flgAutoriza3;
    private ConceptoBean conceptoBean;
    private ConceptoUniNegBean conceptoUniNegBean;
    private CentroResponsabilidadBean centroResponsabilidadBean;
    private CentroResponsabilidadBean centroRespInicialBean;
    private CentroResponsabilidadBean centroRespPrimariaBean;
    private CentroResponsabilidadBean centroRespSecundariaBean;
    private CentroResponsabilidadBean centroRespBachillerBean;
    private CodigoBean tipoDistribucionBean;
    private Double montoI;
    private Double montoP;
    private Double montoS;
    private Double montoB;
    private Double ImportePropuesto = 0.00;
    private List<DetRequerimientoCRBean> listaDetRequerimientoCRBean;
    private Integer codDistribucion;
    private DetRequerimientoCRBean detRequerimientoCRBean;
    private Boolean flgTransporte;

    //Ayuda-Extra
    private Double montoReq;
    private String idTipoSolicitante;
    private String idRecibido;
    private String idSol;
    private String nroSolicitud;
    private Boolean flgAutorizar;

    public SolicitudLogisticoBean() {
    }

    public String getIdPaso() {
        return idPaso;
    }

    public void setIdPaso(String idPaso) {
        this.idPaso = idPaso;
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

    public UsuarioBean getUsuarioBean() {
        if (usuarioBean == null) {
            usuarioBean = new UsuarioBean();
        }
        return usuarioBean;
    }

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public CodigoBean getTipoCategoriaBean() {
        if (tipoCategoriaBean == null) {
            tipoCategoriaBean = new CodigoBean();
        }
        return tipoCategoriaBean;
    }

    public void setTipoCategoriaBean(CodigoBean tipoCategoriaBean) {
        this.tipoCategoriaBean = tipoCategoriaBean;
    }

    public CodigoBean getTipoEstadoBean() {
        if (tipoEstadoBean == null) {
            tipoEstadoBean = new CodigoBean();
        }
        return tipoEstadoBean;
    }

    public void setTipoEstadoBean(CodigoBean tipoEstadoBean) {
        this.tipoEstadoBean = tipoEstadoBean;
    }

    public CodigoBean getTipoPrioridadBean() {
        if (tipoPrioridadBean == null) {
            tipoPrioridadBean = new CodigoBean();
        }
        return tipoPrioridadBean;
    }

    public void setTipoPrioridadBean(CodigoBean tipoPrioridadBean) {
        this.tipoPrioridadBean = tipoPrioridadBean;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Integer getIdTipoMoneda() {
        return idTipoMoneda;
    }

    public void setIdTipoMoneda(Integer idTipoMoneda) {
        this.idTipoMoneda = idTipoMoneda;
    }

    public String getTipoMoneda() {
        return TipoMoneda;
    }

    public void setTipoMoneda(String TipoMoneda) {
        this.TipoMoneda = TipoMoneda;
    }

    public String getUnidadNegocio() {
        return unidadNegocio;
    }

    public void setUnidadNegocio(String unidadNegocio) {
        this.unidadNegocio = unidadNegocio;
    }

    public String getUnidadOrganica() {
        return unidadOrganica;
    }

    public void setUnidadOrganica(String unidadOrganica) {
        this.unidadOrganica = unidadOrganica;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getTipoCategoria() {
        return tipoCategoria;
    }

    public void setTipoCategoria(String tipoCategoria) {

        this.tipoCategoria = tipoCategoria;
    }

    public String getTipoEstado() {
        return tipoEstado;
    }

    public void setTipoEstado(String tipoEstado) {
        this.tipoEstado = tipoEstado;
    }

    public String getTipoPrioridad() {
        return tipoPrioridad;
    }

    public void setTipoPrioridad(String tipoPrioridad) {
        this.tipoPrioridad = tipoPrioridad;
    }

    public Integer getIdRequerimiento() {
        return idRequerimiento;
    }

    public void setIdRequerimiento(Integer idRequerimiento) {
        this.idRequerimiento = idRequerimiento;
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

    public UnidadOrganicaBean getUnidadOrganicaBean() {
        if (unidadOrganicaBean == null) {
            unidadOrganicaBean = new UnidadOrganicaBean();
        }
        return unidadOrganicaBean;
    }

    public void setUnidadOrganicaBean(UnidadOrganicaBean unidadOrganicaBean) {
        this.unidadOrganicaBean = unidadOrganicaBean;
    }

    public ActividadBean getActividadBean() {
        if (actividadBean == null) {
            actividadBean = new ActividadBean();
        }
        return actividadBean;
    }

    public void setActividadBean(ActividadBean actividadBean) {
        this.actividadBean = actividadBean;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getIdTipoCategoria() {
        return idTipoCategoria;
    }

    public void setIdTipoCategoria(Integer idTipoCategoria) {
        this.idTipoCategoria = idTipoCategoria;
    }

    public Integer getIdTipoEstado() {
        return idTipoEstado;
    }

    public void setIdTipoEstado(Integer idTipoEstado) {
        this.idTipoEstado = idTipoEstado;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public Date getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(Date fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    public Date getFechaListo() {
        return fechaListo;
    }

    public void setFechaListo(Date fechaListo) {
        this.fechaListo = fechaListo;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Integer getIdTipoPrioridad() {
        return idTipoPrioridad;
    }

    public void setIdTipoPrioridad(Integer idTipoPrioridad) {
        this.idTipoPrioridad = idTipoPrioridad;
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

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public ObjOperativoBean getObjOperativoBean() {
        if (objOperativoBean == null) {
            objOperativoBean = new ObjOperativoBean();
        }
        return objOperativoBean;
    }

    public void setObjOperativoBean(ObjOperativoBean objOperativoBean) {
        this.objOperativoBean = objOperativoBean;
    }

    public InventarioAlmacenBean getInventarioAlmacenBean() {
        if (inventarioAlmacenBean == null) {
            inventarioAlmacenBean = new InventarioAlmacenBean();
        }
        return inventarioAlmacenBean;
    }

    public void setInventarioAlmacenBean(InventarioAlmacenBean inventarioAlmacenBean) {
        this.inventarioAlmacenBean = inventarioAlmacenBean;
    }

    public TipoSolicitudBean getTipoSolicitudBean() {
        if (tipoSolicitudBean == null) {
            tipoSolicitudBean = new TipoSolicitudBean();
        }
        return tipoSolicitudBean;
    }

    public void setTipoSolicitudBean(TipoSolicitudBean tipoSolicitudBean) {
        this.tipoSolicitudBean = tipoSolicitudBean;
    }

    public Integer getIdTipoSolicitud() {
        return idTipoSolicitud;
    }

    public void setIdTipoSolicitud(Integer idTipoSolicitud) {
        this.idTipoSolicitud = idTipoSolicitud;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public Integer getIdAutoriza1() {
        return idAutoriza1;
    }

    public void setIdAutoriza1(Integer idAutoriza1) {
        this.idAutoriza1 = idAutoriza1;
    }

    public Integer getIdAutoriza2() {
        return idAutoriza2;
    }

    public void setIdAutoriza2(Integer idAutoriza2) {
        this.idAutoriza2 = idAutoriza2;
    }

    public Integer getIdAutoriza3() {
        return idAutoriza3;
    }

    public void setIdAutoriza3(Integer idAutoriza3) {
        this.idAutoriza3 = idAutoriza3;
    }

    public Date getFecAutoriza1() {
        return fecAutoriza1;
    }

    public void setFecAutoriza1(Date fecAutoriza1) {
        this.fecAutoriza1 = fecAutoriza1;
    }

    public Date getFecAutoriza2() {
        return fecAutoriza2;
    }

    public void setFecAutoriza2(Date fecAutoriza2) {
        this.fecAutoriza2 = fecAutoriza2;
    }

    public Date getFecAutoriza3() {
        return fecAutoriza3;
    }

    public void setFecAutoriza3(Date fecAutoriza3) {
        this.fecAutoriza3 = fecAutoriza3;
    }

    public Boolean getFlgAutoriza1() {
        return flgAutoriza1;
    }

    public void setFlgAutoriza1(Boolean flgAutoriza1) {
        this.flgAutoriza1 = flgAutoriza1;
    }

    public Boolean getFlgAutoriza2() {
        return flgAutoriza2;
    }

    public void setFlgAutoriza2(Boolean flgAutoriza2) {
        this.flgAutoriza2 = flgAutoriza2;
    }

    public Boolean getFlgAutoriza3() {
        return flgAutoriza3;
    }

    public void setFlgAutoriza3(Boolean flgAutoriza3) {
        this.flgAutoriza3 = flgAutoriza3;
    }

    public Integer getNivelAutoriza() {
        return nivelAutoriza;
    }

    public void setNivelAutoriza(Integer nivelAutoriza) {
        this.nivelAutoriza = nivelAutoriza;
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

    public ConceptoUniNegBean getConceptoUniNegBean() {
        if (conceptoUniNegBean == null) {
            conceptoUniNegBean = new ConceptoUniNegBean();
        }
        return conceptoUniNegBean;
    }

    public void setConceptoUniNegBean(ConceptoUniNegBean conceptoUniNegBean) {
        this.conceptoUniNegBean = conceptoUniNegBean;
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

    public CentroResponsabilidadBean getCentroRespInicialBean() {
        if (centroRespInicialBean == null) {
            centroRespInicialBean = new CentroResponsabilidadBean();
        }
        return centroRespInicialBean;
    }

    public void setCentroRespInicialBean(CentroResponsabilidadBean centroRespInicialBean) {
        this.centroRespInicialBean = centroRespInicialBean;
    }

    public CentroResponsabilidadBean getCentroRespPrimariaBean() {
        if (centroRespPrimariaBean == null) {
            centroRespPrimariaBean = new CentroResponsabilidadBean();
        }
        return centroRespPrimariaBean;
    }

    public void setCentroRespPrimariaBean(CentroResponsabilidadBean centroRespPrimariaBean) {
        this.centroRespPrimariaBean = centroRespPrimariaBean;
    }

    public CentroResponsabilidadBean getCentroRespSecundariaBean() {
        if (centroRespSecundariaBean == null) {
            centroRespSecundariaBean = new CentroResponsabilidadBean();
        }
        return centroRespSecundariaBean;
    }

    public void setCentroRespSecundariaBean(CentroResponsabilidadBean centroRespSecundariaBean) {
        this.centroRespSecundariaBean = centroRespSecundariaBean;
    }

    public CentroResponsabilidadBean getCentroRespBachillerBean() {
        if (centroRespBachillerBean == null) {
            centroRespBachillerBean = new CentroResponsabilidadBean();
        }
        return centroRespBachillerBean;
    }

    public void setCentroRespBachillerBean(CentroResponsabilidadBean centroRespBachillerBean) {
        this.centroRespBachillerBean = centroRespBachillerBean;
    }

    public CodigoBean getTipoDistribucionBean() {
        if (tipoDistribucionBean == null) {
            tipoDistribucionBean = new CodigoBean();
        }
        return tipoDistribucionBean;
    }

    public void setTipoDistribucionBean(CodigoBean tipoDistribucionBean) {
        this.tipoDistribucionBean = tipoDistribucionBean;
    }

    public Double getMontoI() {
        return montoI;
    }

    public void setMontoI(Double montoI) {
        this.montoI = montoI;
    }

    public Double getMontoP() {
        return montoP;
    }

    public void setMontoP(Double montoP) {
        this.montoP = montoP;
    }

    public Double getMontoS() {
        return montoS;
    }

    public void setMontoS(Double montoS) {
        this.montoS = montoS;
    }

    public Double getMontoB() {
        return montoB;
    }

    public void setMontoB(Double montoB) {
        this.montoB = montoB;
    }

    public List<DetRequerimientoCRBean> getListaDetRequerimientoCRBean() {
        if (listaDetRequerimientoCRBean == null) {
            listaDetRequerimientoCRBean = new ArrayList<>();
        }
        return listaDetRequerimientoCRBean;
    }

    public void setListaDetRequerimientoCRBean(List<DetRequerimientoCRBean> listaDetRequerimientoCRBean) {
        this.listaDetRequerimientoCRBean = listaDetRequerimientoCRBean;
    }

    public Integer getCodDistribucion() {
        return codDistribucion;
    }

    public void setCodDistribucion(Integer codDistribucion) {
        this.codDistribucion = codDistribucion;
    }

    public DetRequerimientoCRBean getDetRequerimientoCRBean() {
        if (detRequerimientoCRBean == null) {
            detRequerimientoCRBean = new DetRequerimientoCRBean();
        }
        return detRequerimientoCRBean;
    }

    public void setDetRequerimientoCRBean(DetRequerimientoCRBean detRequerimientoCRBean) {
        this.detRequerimientoCRBean = detRequerimientoCRBean;
    }

    public Double getMontoReq() {
        return montoReq;
    }

    public Double getImportePropuesto() {
        return ImportePropuesto;
    }

    public void setImportePropuesto(Double ImportePropuesto) {
        this.ImportePropuesto = ImportePropuesto;
    }

    public void setMontoReq(Double montoReq) {
        this.montoReq = montoReq;
    }

    public String getIdTipoSolicitante() {
        return idTipoSolicitante;
    }

    public void setIdTipoSolicitante(String idTipoSolicitante) {
        this.idTipoSolicitante = idTipoSolicitante;
    }

    public String getIdRecibido() {
        return idRecibido;
    }

    public void setIdRecibido(String idRecibido) {
        this.idRecibido = idRecibido;
    }

    public String getIdSol() {
        return idSol;
    }

    public void setIdSol(String idSol) {
        this.idSol = idSol;
    }

    public String getNroSolicitud() {
        return nroSolicitud;
    }

    public void setNroSolicitud(String nroSolicitud) {
        this.nroSolicitud = nroSolicitud;
    }

    public Boolean getFlgTransporte() {
        return flgTransporte;
    }

    public void setFlgTransporte(Boolean flgTransporte) {
        this.flgTransporte = flgTransporte;
    }

    public Boolean getFlgAutorizar() {
        return flgAutorizar;
    }

    public void setFlgAutorizar(Boolean flgAutorizar) {
        this.flgAutorizar = flgAutorizar;
    }

}
