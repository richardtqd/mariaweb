package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;
import org.primefaces.model.UploadedFile;
import pe.marista.sigma.MaristaConstantes;

/**
 *
 * @author Administrador
 */
public class EstudianteBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String idEstudiante;
    private UnidadNegocioBean unidadNegocioBean;//uniNeg ??
    private PersonaBean personaBean;//idEstudiante;
    private String codigo;
    private String codigoColegio;
    private FamiliaBean familiaBean;//idFamilia
    private CodigoBean tipoStatusEst;//idTipoStatusEst
    private CodigoBean tipoIngresoEst;//idTipoIngresoEst
    private GradoAcademicoBean gradoAcademicoBean;//idGradoIngreso;
    private Integer anioIngreso;
    private Date fechaIngreso;
    private GradoAcademicoBean gradoHabilitado;//idGradoHabilitado
    private String foto;
    private DistritoBean idDistritoNaci;//idDistritoNaci
    private PaisBean paisNaciBean;
    private String refeLugarNaci;
    private CodigoBean tipoIdioma;//idTipoIdioma
    private DistritoBean idDistritoDomi;//idDistritoDomi
    private CodigoBean idTipoViaDomi;//idTipoViaDomi
    private String viaDomi;
    private String nroDomi;
    private String urbDomi;
    private String refeDomi;
    private String telefono1Domi;
    private String telefono2Domi;
    private String factorSanguineo;
    private String grupoSanguineo;
    private FamiliarBean familiarBean;//idFamiliarEmergencia=idFamiliar
    private FamiliarBean apoderadoBean;//idApoderado
    private PersonaBean respPagoBean;//idRespPago
    private CodigoBean tipoRespPago;//idTipoRespPago 
    private CodigoBean tipoMovilidad;//idTipoMovilidad    
    private Date fechaStatusEst;
    private String motivoStatusEst;
    private boolean statusEstVista;

    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private String modiVer;
    private String nivelAcademico;
    private String statusEst;
    private String statusBtnMatricula;
    private String sexoEstudiante;
    private String usuarioMatricula;
    private Date fechaMatricula;

    //ayuda 
    private boolean collapsed;
    private UploadedFile file;
    private String seccion; //seccion de matricula --> seccion verdadera
    private Boolean estadoAprobacion;//para matricularlo
    private Boolean estadoAprobacionCtaPorCobrar;//para generar ctas ctes
    private String periodo;
   
    private Integer anio;   
    private Integer idTipoStatusEst1;
    private Integer idTipoStatusEst2;

    //ayudaExcel
    private String creaPorAyuda;
    private String tipoSeguro;
    private String tipoSeguroAyuda;
    private String NOMB_PAD;
    private String TDOC_PAD;
    private String LIBE_PAD;
    private String FENA_PAD;
    private String EMAIL_PAD;
    private String DIRTR_PAD;
    private String CEL_PAD;
    private String VIVE_PAD;
    private String NOMB_MAD;
    private String TDOC_MAD;
    private String LIBE_MAD;
    private String FENA_MAD;
    private String EMAIL_MAD;
    private String DIRTR_MAD;
    private String CEL_MAD;
    private String VIVE_MAD;
    private String NOMB_APO;
    private String TDOC_APO;
    private String LIBE_APO;
    private String FENA_APO;
    private String EMAIL_APO;
    private String DIRTR_APO;
    private String CEL_APO;
    private String VIVE_APO;
    private String PAREN_APO;
    private String nombreCompleto;
    private String nombreGradoAcaMat;
    private String APAT_PAD;
    private String AMAT_PAD; 
    private String APAT_MAD;
    private String AMAT_MAD; 
    private String APAT_APO;
    private String AMAT_APO; 
    private String AMAT_ALU; 
    private String APAT_ALU; 
    private String NOMB_ALU; 
 
    private String viveCon;
    private String USUARIO;
    private String FECMATRICULA;
    private String HORA;
    private String obs_ViveCon;
    
    //Ayuda
    private String fechaStatusEstAyuda;
    private String dniEstudiante;
 
    public EstudianteBean() {
        this.estadoAprobacion = true;
        this.estadoAprobacionCtaPorCobrar = true;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getFoto() {
        if (foto != null) {
            String formato = ".bmp";
            if (getUnidadNegocioBean().getUniNeg() != null) {
                if (getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)) {
                    formato = ".bmp";
                }
            }
            return MaristaConstantes.RUTA_DOCUMENTOS + MaristaConstantes.RUTA_DOCU_EST + foto + formato;
        }
        return foto;
    }

    public String getNoFoto() {
        return MaristaConstantes.RUTA_DOCU_EST + "noFoto.jpg";
    }

    public String getFotoVista() {
        if (foto == null) {
            return MaristaConstantes.RUTA_DOCUMENTOS + foto;
        }
        return null;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public DistritoBean getIdDistritoNaci() {
        if (idDistritoNaci == null) {
            idDistritoNaci = new DistritoBean();
        }
        return idDistritoNaci;
    }

    public void setIdDistritoNaci(DistritoBean idDistritoNaci) {
        this.idDistritoNaci = idDistritoNaci;
    }

    public String getRefeLugarNaci() {
        return refeLugarNaci;
    }

    public void setRefeLugarNaci(String refeLugarNaci) {
        this.refeLugarNaci = refeLugarNaci;
    }

    public DistritoBean getIdDistritoDomi() {
        if (idDistritoDomi == null) {
            idDistritoDomi = new DistritoBean();
        }
        return idDistritoDomi;
    }

    public void setIdDistritoDomi(DistritoBean idDistritoDomi) {
        this.idDistritoDomi = idDistritoDomi;
    }

    public CodigoBean getIdTipoViaDomi() {
        if (idTipoViaDomi == null) {
            idTipoViaDomi = new CodigoBean();
        }
        return idTipoViaDomi;
    }

    public void setIdTipoViaDomi(CodigoBean idTipoViaDomi) {
        this.idTipoViaDomi = idTipoViaDomi;
    }

    public String getViaDomi() {
        return viaDomi;
    }

    public void setViaDomi(String viaDomi) {
        this.viaDomi = viaDomi;
    }

    public String getNroDomi() {
        return nroDomi;
    }

    public void setNroDomi(String nroDomi) {
        this.nroDomi = nroDomi;
    }

    public String getUrbDomi() {
        return urbDomi;
    }

    public void setUrbDomi(String urbDomi) {
        this.urbDomi = urbDomi;
    }

    public String getRefeDomi() {
        return refeDomi;
    }

    public void setRefeDomi(String refeDomi) {
        this.refeDomi = refeDomi;
    }

    public String getFactorSanguineo() {
        return factorSanguineo;
    }

    public void setFactorSanguineo(String factorSanguineo) {
        this.factorSanguineo = factorSanguineo;
    }

    public String getGrupoSanguineo() {
        return grupoSanguineo;
    }

    public void setGrupoSanguineo(String grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
    }

    public FamiliarBean getFamiliarBean() {
        if (familiarBean == null) {
            familiarBean = new FamiliarBean();
        }
        return familiarBean;
    }

    public void setFamiliarBean(FamiliarBean familiarBean) {
        this.familiarBean = familiarBean;
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

    public GradoAcademicoBean getGradoAcademicoBean() {
        if (gradoAcademicoBean == null) {
            gradoAcademicoBean = new GradoAcademicoBean();
        }
        return gradoAcademicoBean;
    }

    public void setGradoAcademicoBean(GradoAcademicoBean gradoAcademicoBean) {
        this.gradoAcademicoBean = gradoAcademicoBean;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public GradoAcademicoBean getGradoHabilitado() {
        if (gradoHabilitado == null) {
            gradoHabilitado = new GradoAcademicoBean();
        }
        return gradoHabilitado;
    }

    public void setGradoHabilitado(GradoAcademicoBean gradoHabilitado) {
        this.gradoHabilitado = gradoHabilitado;
    }

    public Integer getAnioIngreso() {
        return anioIngreso;
    }

    public void setAnioIngreso(Integer anioIngreso) {
        this.anioIngreso = anioIngreso;
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

    public boolean isCollapsed() {
        return collapsed;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
    }

    public FamiliaBean getFamiliaBean() {
        if (familiaBean == null) {
            familiaBean = new FamiliaBean();
        }
        return familiaBean;
    }

    public void setFamiliaBean(FamiliaBean familiaBean) {
        this.familiaBean = familiaBean;
    }

    public CodigoBean getTipoStatusEst() {
        if (tipoStatusEst == null) {
            tipoStatusEst = new CodigoBean();
        }
        return tipoStatusEst;
    }

    public void setTipoStatusEst(CodigoBean tipoStatusEst) {
        this.tipoStatusEst = tipoStatusEst;
    }

    public CodigoBean getTipoIngresoEst() {
        if (tipoIngresoEst == null) {
            tipoIngresoEst = new CodigoBean();
        }
        return tipoIngresoEst;
    }

    public void setTipoIngresoEst(CodigoBean tipoIngresoEst) {
        this.tipoIngresoEst = tipoIngresoEst;
    }

    public CodigoBean getTipoIdioma() {
        if (tipoIdioma == null) {
            tipoIdioma = new CodigoBean();
        }
        return tipoIdioma;
    }

    public void setTipoIdioma(CodigoBean tipoIdioma) {
        this.tipoIdioma = tipoIdioma;
    }

    public FamiliarBean getApoderadoBean() {
        if (apoderadoBean == null) {
            apoderadoBean = new FamiliarBean();
        }
        return apoderadoBean;
    }

    public void setApoderadoBean(FamiliarBean apoderadoBean) {
        this.apoderadoBean = apoderadoBean;
    }

    public CodigoBean getTipoRespPago() {
        if (tipoRespPago == null) {
            tipoRespPago = new CodigoBean();
        }
        return tipoRespPago;
    }

    public void setTipoRespPago(CodigoBean tipoRespPago) {
        this.tipoRespPago = tipoRespPago;
    }

    public String getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(String idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public PersonaBean getRespPagoBean() {
        if (respPagoBean == null) {
            respPagoBean = new PersonaBean();
        }
        return respPagoBean;
    }

    public void setRespPagoBean(PersonaBean respPagoBean) {
        this.respPagoBean = respPagoBean;
    }

    public Date getFechaStatusEst() {
        return fechaStatusEst;
    }

    public void setFechaStatusEst(Date fechaStatusEst) {
        this.fechaStatusEst = fechaStatusEst;
    }

    public String getMotivoStatusEst() {
        return motivoStatusEst;
    }

    public void setMotivoStatusEst(String motivoStatusEst) {
        this.motivoStatusEst = motivoStatusEst;
    }

    public boolean isStatusEstVista() {
        return statusEstVista;
    }

    public void setStatusEstVista(boolean statusEstVista) {
        this.statusEstVista = statusEstVista;
    }

    public PaisBean getPaisNaciBean() {
        if (paisNaciBean == null) {
            paisNaciBean = new PaisBean();
        }
        return paisNaciBean;
    }

    public void setPaisNaciBean(PaisBean paisNaciBean) {
        this.paisNaciBean = paisNaciBean;
    }

    public CodigoBean getTipoMovilidad() {
        if (tipoMovilidad == null) {
            tipoMovilidad = new CodigoBean();
        }
        return tipoMovilidad;
    }

    public void setTipoMovilidad(CodigoBean tipoMovilidad) {
        this.tipoMovilidad = tipoMovilidad;
    }

    public String getTelefono1Domi() {
        return telefono1Domi;
    }

    public void setTelefono1Domi(String telefono1Domi) {
        this.telefono1Domi = telefono1Domi;
    }

    public String getTelefono2Domi() {
        return telefono2Domi;
    }

    public void setTelefono2Domi(String telefono2Domi) {
        this.telefono2Domi = telefono2Domi;
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

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public Boolean getEstadoAprobacion() {
        return estadoAprobacion;
    }

    public void setEstadoAprobacion(Boolean estadoAprobacion) {
        this.estadoAprobacion = estadoAprobacion;
    }

    public Boolean getEstadoAprobacionCtaPorCobrar() {
        return estadoAprobacionCtaPorCobrar;
    }

    public void setEstadoAprobacionCtaPorCobrar(Boolean estadoAprobacionCtaPorCobrar) {
        this.estadoAprobacionCtaPorCobrar = estadoAprobacionCtaPorCobrar;
    }

    public String getNivelAcademico() {
        return nivelAcademico;
    }

    public void setNivelAcademico(String nivelAcademico) {
        this.nivelAcademico = nivelAcademico;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getCodigoColegio() {
        return codigoColegio;
    }

    public void setCodigoColegio(String codigoColegio) {
        this.codigoColegio = codigoColegio;
    }

    public Integer getIdTipoStatusEst2() {
        return idTipoStatusEst2;
    }

    public void setIdTipoStatusEst2(Integer idTipoStatusEst2) {
        this.idTipoStatusEst2 = idTipoStatusEst2;
    }

    public Integer getIdTipoStatusEst1() {
        return idTipoStatusEst1;
    }

    public void setIdTipoStatusEst1(Integer idTipoStatusEst1) {
        this.idTipoStatusEst1 = idTipoStatusEst1;
    }

    public String getStatusEst() {
        return statusEst;
    }

    public void setStatusEst(String statusEst) {
        this.statusEst = statusEst;
    }

    public String getStatusBtnMatricula() {
        return statusBtnMatricula;
    }

    public void setStatusBtnMatricula(String statusBtnMatricula) {
        this.statusBtnMatricula = statusBtnMatricula;
    }

    public String getSexoEstudiante() {
        return sexoEstudiante;
    }

    public void setSexoEstudiante(String sexoEstudiante) {
        this.sexoEstudiante = sexoEstudiante;
    }

    public String getUsuarioMatricula() {
        return usuarioMatricula;
    }

    public void setUsuarioMatricula(String usuarioMatricula) {
        this.usuarioMatricula = usuarioMatricula;
    }

    public Date getFechaMatricula() {
        return fechaMatricula;
    }

    public void setFechaMatricula(Date fechaMatricula) {
        this.fechaMatricula = fechaMatricula;
    }    

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getCreaPorAyuda() {
        return creaPorAyuda;
    }

    public void setCreaPorAyuda(String creaPorAyuda) {
        this.creaPorAyuda = creaPorAyuda;
    }

    public String getTipoSeguro() {
        return tipoSeguro;
    }

    public void setTipoSeguro(String tipoSeguro) {
        this.tipoSeguro = tipoSeguro;
    }

    public String getTipoSeguroAyuda() {
        return tipoSeguroAyuda;
    }

    public void setTipoSeguroAyuda(String tipoSeguroAyuda) {
        this.tipoSeguroAyuda = tipoSeguroAyuda;
    }

    public String getNOMB_PAD() {
        return NOMB_PAD;
    }

    public void setNOMB_PAD(String NOMB_PAD) {
        this.NOMB_PAD = NOMB_PAD;
    }

    public String getTDOC_PAD() {
        return TDOC_PAD;
    }

    public void setTDOC_PAD(String TDOC_PAD) {
        this.TDOC_PAD = TDOC_PAD;
    }

    public String getLIBE_PAD() {
        return LIBE_PAD;
    }

    public void setLIBE_PAD(String LIBE_PAD) {
        this.LIBE_PAD = LIBE_PAD;
    }

    public String getFENA_PAD() {
        return FENA_PAD;
    }

    public void setFENA_PAD(String FENA_PAD) {
        this.FENA_PAD = FENA_PAD;
    }

    public String getEMAIL_PAD() {
        return EMAIL_PAD;
    }

    public void setEMAIL_PAD(String EMAIL_PAD) {
        this.EMAIL_PAD = EMAIL_PAD;
    }

    public String getDIRTR_PAD() {
        return DIRTR_PAD;
    }

    public void setDIRTR_PAD(String DIRTR_PAD) {
        this.DIRTR_PAD = DIRTR_PAD;
    }

    public String getCEL_PAD() {
        return CEL_PAD;
    }

    public void setCEL_PAD(String CEL_PAD) {
        this.CEL_PAD = CEL_PAD;
    }

    public String getVIVE_PAD() {
        return VIVE_PAD;
    }

    public void setVIVE_PAD(String VIVE_PAD) {
        this.VIVE_PAD = VIVE_PAD;
    }

    public String getNOMB_MAD() {
        return NOMB_MAD;
    }

    public void setNOMB_MAD(String NOMB_MAD) {
        this.NOMB_MAD = NOMB_MAD;
    }

    public String getTDOC_MAD() {
        return TDOC_MAD;
    }

    public void setTDOC_MAD(String TDOC_MAD) {
        this.TDOC_MAD = TDOC_MAD;
    }

    public String getLIBE_MAD() {
        return LIBE_MAD;
    }

    public void setLIBE_MAD(String LIBE_MAD) {
        this.LIBE_MAD = LIBE_MAD;
    }

    public String getFENA_MAD() {
        return FENA_MAD;
    }

    public void setFENA_MAD(String FENA_MAD) {
        this.FENA_MAD = FENA_MAD;
    }

    public String getEMAIL_MAD() {
        return EMAIL_MAD;
    }

    public void setEMAIL_MAD(String EMAIL_MAD) {
        this.EMAIL_MAD = EMAIL_MAD;
    }

    public String getDIRTR_MAD() {
        return DIRTR_MAD;
    }

    public void setDIRTR_MAD(String DIRTR_MAD) {
        this.DIRTR_MAD = DIRTR_MAD;
    }

    public String getCEL_MAD() {
        return CEL_MAD;
    }

    public void setCEL_MAD(String CEL_MAD) {
        this.CEL_MAD = CEL_MAD;
    }

    public String getVIVE_MAD() {
        return VIVE_MAD;
    }

    public void setVIVE_MAD(String VIVE_MAD) {
        this.VIVE_MAD = VIVE_MAD;
    }

    public String getNOMB_APO() {
        return NOMB_APO;
    }

    public void setNOMB_APO(String NOMB_APO) {
        this.NOMB_APO = NOMB_APO;
    }

    public String getTDOC_APO() {
        return TDOC_APO;
    }

    public void setTDOC_APO(String TDOC_APO) {
        this.TDOC_APO = TDOC_APO;
    }

    public String getLIBE_APO() {
        return LIBE_APO;
    }

    public void setLIBE_APO(String LIBE_APO) {
        this.LIBE_APO = LIBE_APO;
    }

    public String getFENA_APO() {
        return FENA_APO;
    }

    public void setFENA_APO(String FENA_APO) {
        this.FENA_APO = FENA_APO;
    }

    public String getEMAIL_APO() {
        return EMAIL_APO;
    }

    public void setEMAIL_APO(String EMAIL_APO) {
        this.EMAIL_APO = EMAIL_APO;
    }

    public String getDIRTR_APO() {
        return DIRTR_APO;
    }

    public void setDIRTR_APO(String DIRTR_APO) {
        this.DIRTR_APO = DIRTR_APO;
    }

    public String getCEL_APO() {
        return CEL_APO;
    }

    public void setCEL_APO(String CEL_APO) {
        this.CEL_APO = CEL_APO;
    }

    public String getVIVE_APO() {
        return VIVE_APO;
    }

    public void setVIVE_APO(String VIVE_APO) {
        this.VIVE_APO = VIVE_APO;
    }

    public String getPAREN_APO() {
        return PAREN_APO;
    }

    public void setPAREN_APO(String PAREN_APO) {
        this.PAREN_APO = PAREN_APO;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
 
    public String getNombreGradoAcaMat() {
        return nombreGradoAcaMat;
    }

    public void setNombreGradoAcaMat(String nombreGradoAcaMat) {
        this.nombreGradoAcaMat = nombreGradoAcaMat;
    }
 
    public String getViveCon() {
        return viveCon;
    }

    public void setViveCon(String viveCon) {
        this.viveCon = viveCon;
    }

    public String getObs_ViveCon() {
        return obs_ViveCon;
    }

    public void setObs_ViveCon(String obs_ViveCon) {
        this.obs_ViveCon = obs_ViveCon;
    }

    public String getUSUARIO() {
        return USUARIO;
    }

    public void setUSUARIO(String USUARIO) {
        this.USUARIO = USUARIO;
    }

    public String getFECMATRICULA() {
        return FECMATRICULA;
    }

    public void setFECMATRICULA(String FECMATRICULA) {
        this.FECMATRICULA = FECMATRICULA;
    }

    public String getHORA() {
        return HORA;
    }

    public void setHORA(String HORA) {
        this.HORA = HORA;
    }

    public String getAPAT_PAD() {
        return APAT_PAD;
    }

    public void setAPAT_PAD(String APAT_PAD) {
        this.APAT_PAD = APAT_PAD;
    }

    public String getAMAT_PAD() {
        return AMAT_PAD;
    }

    public void setAMAT_PAD(String AMAT_PAD) {
        this.AMAT_PAD = AMAT_PAD;
    }

    public String getAPAT_MAD() {
        return APAT_MAD;
    }

    public void setAPAT_MAD(String APAT_MAD) {
        this.APAT_MAD = APAT_MAD;
    }

    public String getAMAT_MAD() {
        return AMAT_MAD;
    }

    public void setAMAT_MAD(String AMAT_MAD) {
        this.AMAT_MAD = AMAT_MAD;
    }

    public String getAPAT_APO() {
        return APAT_APO;
    }

    public void setAPAT_APO(String APAT_APO) {
        this.APAT_APO = APAT_APO;
    }

    public String getAMAT_APO() {
        return AMAT_APO;
    }

    public void setAMAT_APO(String AMAT_APO) {
        this.AMAT_APO = AMAT_APO;
    }

    public String getAMAT_ALU() {
        return AMAT_ALU;
    }

    public void setAMAT_ALU(String AMAT_ALU) {
        this.AMAT_ALU = AMAT_ALU;
    }

    public String getAPAT_ALU() {
        return APAT_ALU;
    }

    public void setAPAT_ALU(String APAT_ALU) {
        this.APAT_ALU = APAT_ALU;
    }

    public String getNOMB_ALU() {
        return NOMB_ALU;
    }

    public void setNOMB_ALU(String NOMB_ALU) {
        this.NOMB_ALU = NOMB_ALU;
    }

    public String getFechaStatusEstAyuda() {
        return fechaStatusEstAyuda;
    }

    public void setFechaStatusEstAyuda(String fechaStatusEstAyuda) {
        this.fechaStatusEstAyuda = fechaStatusEstAyuda;
    }

    public String getDniEstudiante() {
        return dniEstudiante;
    }

    public void setDniEstudiante(String dniEstudiante) {
        this.dniEstudiante = dniEstudiante;
    }
 
}
