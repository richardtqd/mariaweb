package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Administrador
 */
public class EstudianteInfoBean implements Serializable {

    private EstudianteBean estudianteBean; //idEstudiante
    private UnidadNegocioBean unidadNegocioBean;//uniNeg ?????????????????????
    private Boolean flgBautizo;
    private Boolean flgPrimeraComunion;
    private Boolean flgVidaReligiosa;
    private Boolean flgConfirmacion;
    private Integer aniosColegioProcedencia;
    private CodigoBean tipoCambioColegio;//idTipoCambioColegio
    private CodigoBean tipoMotivoCambio;//idTipoMotivoCambio
    private Integer anioRetiro;
    private GradoAcademicoBean gradoAcademicoCambioBean;//idGradoAcademicoCambio
    private Integer nroHermanos;
    private Integer nroHermanas;
    private Integer nroHermano;//orden de hermano
    private String refeFamiliar;
    private CodigoBean estadoCivilPadres;//idTipoEstadoCivilPadres
    private CodigoBean viveCon;//idTipoViveCon
    private String nombreViveCon;
    private Date fecIniViveCon;
    private Double ingPadBruto;
    private Double ingPadNeto;
    private Double ingMadBruto;
    private Double ingMadNeto;
    private Double ingFam1Bruto;
    private Double ingFam1Neto;
    private Double ingFam2Bruto;
    private Double ingFam2Neto;
    private CodigoBean tipoParentescoFam1;//idTipoParentescoFam1
    private CodigoBean tipoParentescoFam2;//idTipoParentescoFam2
    private Double pensionColegioProce;
    private String obsFamiliar;
    private String parroquiaAsiste;
    private String parroquiaBautizo;
    private String colegioProcedencia;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;

    //ayuda
    private Boolean viveConVista;
    private Boolean fecViveConVista;

    private Date fechaHemoglobina;
    private Integer resultado;
    private Boolean diabetesPadres;
    private Boolean hipertensionPadres;
    private Boolean tbcPadres;
    private Boolean flgSeguroMadre;
    private Boolean flgSeguroPadre;
    private Boolean flgSeguroAccidente;
    private CodigoBean tipoSeguroBean;

    private String sexoEstudiante;
    
    private String codigoModularAl;
    private String codigoModularCol;

    private String dniEstudiante;
    
    public EstudianteInfoBean() {
//        this.flgBautizo = true;
//        this.flgPrimeraComunion = true;
//        this.flgVidaReligiosa = true;
//        this.flgConfirmacion = false;
        this.aniosColegioProcedencia = 0;
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

    public Integer getAnosColegioProcedencia() {
        return aniosColegioProcedencia;
    }

    public Integer getAnioRetiro() {
        return anioRetiro;
    }

    public void setAnioRetiro(Integer anioRetiro) {
        this.anioRetiro = anioRetiro;
    }

    public Integer getNroHermanos() {
        return nroHermanos;
    }

    public void setNroHermanos(Integer nroHermanos) {
        this.nroHermanos = nroHermanos;
    }

    public Integer getNroHermanas() {
        return nroHermanas;
    }

    public void setNroHermanas(Integer nroHermanas) {
        this.nroHermanas = nroHermanas;
    }

    public Integer getNroHermano() {
        return nroHermano;
    }

    public void setNroHermano(Integer nroHermano) {
        this.nroHermano = nroHermano;
    }

    public String getRefeFamiliar() {
        return refeFamiliar;
    }

    public void setRefeFamiliar(String refeFamiliar) {
        this.refeFamiliar = refeFamiliar;
    }

    public CodigoBean getEstadoCivilPadres() {
        if (estadoCivilPadres == null) {
            estadoCivilPadres = new CodigoBean();
        }
        return estadoCivilPadres;
    }

    public void setEstadoCivilPadres(CodigoBean estadoCivilPadres) {
        this.estadoCivilPadres = estadoCivilPadres;
    }

    public CodigoBean getViveCon() {
        if (viveCon == null) {
            viveCon = new CodigoBean();
        }
        return viveCon;
    }

    public void setViveCon(CodigoBean viveCon) {
        this.viveCon = viveCon;
    }

    public String getNombreViveCon() {
        return nombreViveCon;
    }

    public void setNombreViveCon(String nombreViveCon) {
        this.nombreViveCon = nombreViveCon;
    }

    public Date getFecIniViveCon() {
        return fecIniViveCon;
    }

    public void setFecIniViveCon(Date fecIniViveCon) {
        this.fecIniViveCon = fecIniViveCon;
    }

    public Double getPensionColegioProce() {
        return pensionColegioProce;
    }

    public void setPensionColegioProce(Double pensionColegioProce) {
        this.pensionColegioProce = pensionColegioProce;
    }

    public String getObsFamiliar() {
        return obsFamiliar;
    }

    public void setObsFamiliar(String obsFamiliar) {
        this.obsFamiliar = obsFamiliar;
    }

    public String getParroquiaAsiste() {
        return parroquiaAsiste;
    }

    public void setParroquiaAsiste(String parroquiaAsiste) {
        this.parroquiaAsiste = parroquiaAsiste;
    }

    public String getParroquiaBautizo() {
        return parroquiaBautizo;
    }

    public void setParroquiaBautizo(String parroquiaBautizo) {
        this.parroquiaBautizo = parroquiaBautizo;
    }

    public String getColegioProcedencia() {
        return colegioProcedencia;
    }

    public void setColegioProcedencia(String colegioProcedencia) {
        this.colegioProcedencia = colegioProcedencia;
    }

    public Integer getAniosColegioProcedencia() {
        return aniosColegioProcedencia;
    }

    public void setAniosColegioProcedencia(Integer aniosColegioProcedencia) {
        this.aniosColegioProcedencia = aniosColegioProcedencia;
    }

    public CodigoBean getTipoCambioColegio() {
        if (tipoCambioColegio == null) {
            tipoCambioColegio = new CodigoBean();
        }
        return tipoCambioColegio;
    }

    public void setTipoCambioColegio(CodigoBean tipoCambioColegio) {
        this.tipoCambioColegio = tipoCambioColegio;
    }

    public CodigoBean getTipoMotivoCambio() {
        if (tipoMotivoCambio == null) {
            tipoMotivoCambio = new CodigoBean();
        }
        return tipoMotivoCambio;
    }

    public void setTipoMotivoCambio(CodigoBean tipoMotivoCambio) {
        this.tipoMotivoCambio = tipoMotivoCambio;
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

    public GradoAcademicoBean getGradoAcademicoCambioBean() {
        if (gradoAcademicoCambioBean == null) {
            gradoAcademicoCambioBean = new GradoAcademicoBean();
        }
        return gradoAcademicoCambioBean;
    }

    public void setGradoAcademicoCambioBean(GradoAcademicoBean gradoAcademicoCambioBean) {
        this.gradoAcademicoCambioBean = gradoAcademicoCambioBean;
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

    public Double getIngPadBruto() {
        return ingPadBruto;
    }

    public void setIngPadBruto(Double ingPadBruto) {
        this.ingPadBruto = ingPadBruto;
    }

    public Double getIngPadNeto() {
        return ingPadNeto;
    }

    public void setIngPadNeto(Double ingPadNeto) {
        this.ingPadNeto = ingPadNeto;
    }

    public Double getIngMadBruto() {
        return ingMadBruto;
    }

    public void setIngMadBruto(Double ingMadBruto) {
        this.ingMadBruto = ingMadBruto;
    }

    public Double getIngMadNeto() {
        return ingMadNeto;
    }

    public void setIngMadNeto(Double ingMadNeto) {
        this.ingMadNeto = ingMadNeto;
    }

    public Double getIngFam1Bruto() {
        return ingFam1Bruto;
    }

    public void setIngFam1Bruto(Double ingFam1Bruto) {
        this.ingFam1Bruto = ingFam1Bruto;
    }

    public Double getIngFam1Neto() {
        return ingFam1Neto;
    }

    public void setIngFam1Neto(Double ingFam1Neto) {
        this.ingFam1Neto = ingFam1Neto;
    }

    public Double getIngFam2Bruto() {
        return ingFam2Bruto;
    }

    public void setIngFam2Bruto(Double ingFam2Bruto) {
        this.ingFam2Bruto = ingFam2Bruto;
    }

    public Double getIngFam2Neto() {
        return ingFam2Neto;
    }

    public void setIngFam2Neto(Double ingFam2Neto) {
        this.ingFam2Neto = ingFam2Neto;
    }

    public CodigoBean getTipoParentescoFam1() {
        if (tipoParentescoFam1 == null) {
            tipoParentescoFam1 = new CodigoBean();
        }
        return tipoParentescoFam1;
    }

    public void setTipoParentescoFam1(CodigoBean tipoParentescoFam1) {
        this.tipoParentescoFam1 = tipoParentescoFam1;
    }

    public CodigoBean getTipoParentescoFam2() {
        if (tipoParentescoFam2 == null) {
            tipoParentescoFam2 = new CodigoBean();
        }
        return tipoParentescoFam2;
    }

    public void setTipoParentescoFam2(CodigoBean tipoParentescoFam2) {
        this.tipoParentescoFam2 = tipoParentescoFam2;
    }

    public Boolean getFlgBautizo() {
        return flgBautizo;
    }

    public void setFlgBautizo(Boolean flgBautizo) {
        this.flgBautizo = flgBautizo;
    }

    public Boolean getFlgPrimeraComunion() {
        return flgPrimeraComunion;
    }

    public void setFlgPrimeraComunion(Boolean flgPrimeraComunion) {
        this.flgPrimeraComunion = flgPrimeraComunion;
    }

    public Boolean getFlgVidaReligiosa() {
        return flgVidaReligiosa;
    }

    public void setFlgVidaReligiosa(Boolean flgVidaReligiosa) {
        this.flgVidaReligiosa = flgVidaReligiosa;
    }

    public Boolean getFlgConfirmacion() {
        return flgConfirmacion;
    }

    public void setFlgConfirmacion(Boolean flgConfirmacion) {
        this.flgConfirmacion = flgConfirmacion;
    }

    public Boolean getViveConVista() {
        return viveConVista;
    }

    public void setViveConVista(Boolean viveConVista) {
        this.viveConVista = viveConVista;
    }

    public Boolean getFecViveConVista() {
        return fecViveConVista;
    }

    public void setFecViveConVista(Boolean fecViveConVista) {
        this.fecViveConVista = fecViveConVista;
    }

    public Date getFechaHemoglobina() {
        return fechaHemoglobina;
    }

    public void setFechaHemoglobina(Date fechaHemoglobina) {
        this.fechaHemoglobina = fechaHemoglobina;
    }

    public Integer getResultado() {
        return resultado;
    }

    public void setResultado(Integer resultado) {
        this.resultado = resultado;
    }

    public Boolean getDiabetesPadres() {
        return diabetesPadres;
    }

    public void setDiabetesPadres(Boolean diabetesPadres) {
        this.diabetesPadres = diabetesPadres;
    }

    public Boolean getHipertensionPadres() {
        return hipertensionPadres;
    }

    public void setHipertensionPadres(Boolean hipertensionPadres) {
        this.hipertensionPadres = hipertensionPadres;
    }

    public Boolean getTbcPadres() {
        return tbcPadres;
    }

    public void setTbcPadres(Boolean tbcPadres) {
        this.tbcPadres = tbcPadres;
    }

    public String getSexoEstudiante() {
        return sexoEstudiante;
    }

    public void setSexoEstudiante(String sexoEstudiante) {
        this.sexoEstudiante = sexoEstudiante;
    }

    public Boolean getFlgSeguroMadre() {
        return flgSeguroMadre;
    }

    public void setFlgSeguroMadre(Boolean flgSeguroMadre) {
        this.flgSeguroMadre = flgSeguroMadre;
    }

    public Boolean getFlgSeguroPadre() {
        return flgSeguroPadre;
    }

    public void setFlgSeguroPadre(Boolean flgSeguroPadre) {
        this.flgSeguroPadre = flgSeguroPadre;
    }

    public Boolean getFlgSeguroAccidente() {
        return flgSeguroAccidente;
    }

    public void setFlgSeguroAccidente(Boolean flgSeguroAccidente) {
        this.flgSeguroAccidente = flgSeguroAccidente;
    }

    public CodigoBean getTipoSeguroBean() {
        if (tipoSeguroBean == null) {
            tipoSeguroBean = new CodigoBean();
        }
        return tipoSeguroBean;
    }

    public void setTipoSeguroBean(CodigoBean tipoSeguroBean) {
        this.tipoSeguroBean = tipoSeguroBean;
    }

    public String getCodigoModularAl() {
        return codigoModularAl;
    }

    public void setCodigoModularAl(String codigoModularAl) {
        this.codigoModularAl = codigoModularAl;
    }

    public String getCodigoModularCol() {
        return codigoModularCol;
    }

    public void setCodigoModularCol(String codigoModularCol) {
        this.codigoModularCol = codigoModularCol;
    }

    public String getDniEstudiante() {
        return dniEstudiante;
    }

    public void setDniEstudiante(String dniEstudiante) {
        this.dniEstudiante = dniEstudiante;
    }
}
