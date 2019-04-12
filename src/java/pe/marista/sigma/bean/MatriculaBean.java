package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import pe.marista.sigma.MaristaConstantes;

/**
 *
 * @author Administrador
 */
public class MatriculaBean implements Serializable {

    private Integer idMatricula;
    private EstudianteBean estudianteBean;//idEstudiante
    private UnidadNegocioBean unidadNegocioBean;//uniNeg
    private Integer anio;
    private Integer grado;
    private String seccion;
    private Date fechaMatricula;
    private CodigoBean tipoMatriculaBean;//idTipoMatricula
    private GradoAcademicoBean gradoAcademicoBean;//idGradoAcademico
    private ProgramacionBean programacionBean;//idProgramacion
    private Boolean flgBachillerato;
    private String creaPor;
    private Date creaFecha;
    private Date fecha;
    private boolean flgMatricula;
    private String modiPor;

    //ayuda
    private Integer anioIni = 2015;
    private Integer anioFin;
    private Integer flgMatriPend;
    private Integer flgReProceso;
    private String flgMatriVista;
    private String gradoAcademicoVista;
    private String gradoAcademicoFiltro;

    //ayuda cr
    private Integer crIni;
    private Integer crPri;
    private Integer crSec;
    private Integer crBac;
    private Integer dato;
    private Date fechaPago;
    private Integer cont;
    private Date fechaInicio;
    private Date fechaFin;
    //Ayuda meses
    private List<Integer> listaCuentas;
    private String ctaMeses;

    //Ayuda
    private String nomRes;
    private String nomApo;
    private String nomPap;
    private String nomMam;

    //AYUDA MASIVO
    private String flgMasivo;
    private Integer mes;
    private Integer flgMas;
    private Integer nroIni;
    private Integer nroFin;

    private Integer tipoImpresion;
    private Integer idTipoEstado;
    private Integer flgMatriculaVista;
    
    private Date fechaInicioClases;
    private Date fechaFinFiltro;
    private Integer matricula;

    //AYUDA MENSAJE
    private String mensaje;

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    //StoredProcedure
    private Integer resultado;

    public Integer getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(Integer idMatricula) {
        this.idMatricula = idMatricula;
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

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getGrado() {
        return grado;
    }

    public void setGrado(Integer grado) {
        this.grado = grado;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
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

    public ProgramacionBean getProgramacionBean() {
        if (programacionBean == null) {
            programacionBean = new ProgramacionBean();
        }
        return programacionBean;
    }

    public void setProgramacionBean(ProgramacionBean programacionBean) {
        this.programacionBean = programacionBean;
    }

    public Date getFechaMatricula() {
        return fechaMatricula;
    }

    public void setFechaMatricula(Date fechaMatricula) {
        this.fechaMatricula = fechaMatricula;
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

    public CodigoBean getTipoMatriculaBean() {
        if (tipoMatriculaBean == null) {
            tipoMatriculaBean = new CodigoBean();
        }
        return tipoMatriculaBean;
    }

    public void setTipoMatriculaBean(CodigoBean tipoMatriculaBean) {
        this.tipoMatriculaBean = tipoMatriculaBean;
    }

    public Integer getResultado() {
        return resultado;
    }

    public void setResultado(Integer resultado) {
        this.resultado = resultado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean isFlgMatricula() {
        return flgMatricula;
    }

    public void setFlgMatricula(boolean flgMatricula) {
        this.flgMatricula = flgMatricula;
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

    public String getFlgMatriVista() {
        if (flgMatricula == true) {
            flgMatriVista = MaristaConstantes.FLG_MATRICULA_TRUE;
        }
        if (flgMatricula == false) {
            flgMatriVista = MaristaConstantes.FLG_MATRICULA_FALSE;
        }
        return flgMatriVista;
    }

    public void setFlgMatriVista(String flgMatriVista) {
        this.flgMatriVista = flgMatriVista;
    }

    public String getGradoAcademicoVista() {
        if (flgBachillerato != null) {
            if (flgBachillerato == true) {
                gradoAcademicoVista=gradoAcademicoBean.getNombre()+""+MaristaConstantes.FLG_BACHILLER;
//                gradoAcademicoVista = gradoAcademicoBean.getNombre();
            } else {
                gradoAcademicoVista = gradoAcademicoBean.getNombre();
//                gradoAcademicoVista = gradoAcademicoBean.getNombre() + "" + MaristaConstantes.FLG_BACHILLER;
            }
        } else {
            gradoAcademicoVista = gradoAcademicoBean.getNombre();
        }
        return gradoAcademicoVista;
    }

    public void setGradoAcademicoVista(String gradoAcademicoVista) {
        this.gradoAcademicoVista = gradoAcademicoVista;
    }

    public Boolean getFlgBachillerato() {
        return flgBachillerato;
    }

    public void setFlgBachillerato(Boolean flgBachillerato) {
        this.flgBachillerato = flgBachillerato;
    }

    public Integer getFlgMatriPend() {
        return flgMatriPend;
    }

    public void setFlgMatriPend(Integer flgMatriPend) {
        this.flgMatriPend = flgMatriPend;
    }

    public Integer getFlgReProceso() {
        return flgReProceso;
    }

    public void setFlgReProceso(Integer flgReProceso) {
        this.flgReProceso = flgReProceso;
    }

    public String getGradoAcademicoFiltro() {
        return gradoAcademicoFiltro;
    }

    public void setGradoAcademicoFiltro(String gradoAcademicoFiltro) {
        this.gradoAcademicoFiltro = gradoAcademicoFiltro;
    }

    public Integer getCrIni() {
        return crIni;
    }

    public void setCrIni(Integer crIni) {
        this.crIni = crIni;
    }

    public Integer getCrPri() {
        return crPri;
    }

    public void setCrPri(Integer crPri) {
        this.crPri = crPri;
    }

    public Integer getCrSec() {
        return crSec;
    }

    public void setCrSec(Integer crSec) {
        this.crSec = crSec;
    }

    public Integer getCrBac() {
        return crBac;
    }

    public void setCrBac(Integer crBac) {
        this.crBac = crBac;
    }

    public Integer getDato() {
        return dato;
    }

    public void setDato(Integer dato) {
        this.dato = dato;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Integer getCont() {
        return cont;
    }

    public void setCont(Integer cont) {
        this.cont = cont;
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

    public List<Integer> getListaCuentas() {
        listaCuentas = new ArrayList<>(12);
        String[] array = new String[0];
        if (ctaMeses != null) {
            array = ctaMeses.split(":");
            for (int i = 0; i < 12; i++) {
                if (i < array.length && !Objects.equals("", array[i])) {
                    listaCuentas.add(new Integer(array[i].trim()));
                } else {
                    listaCuentas.add(0);
                }
            }
        } else {
            for (int i = 0; i < 12; i++) {
                listaCuentas.add(0);
            }
        }
        return listaCuentas;
    }

    public void setListaCuentas(List<Integer> listaCuentas) {
        this.listaCuentas = listaCuentas;
    }

    public String getCtaMeses() {
        return ctaMeses;
    }

    public void setCtaMeses(String ctaMeses) {
        this.ctaMeses = ctaMeses;
    }

    public String getNomRes() {
        return nomRes;
    }

    public void setNomRes(String nomRes) {
        this.nomRes = nomRes;
    }

    public String getNomApo() {
        return nomApo;
    }

    public void setNomApo(String nomApo) {
        this.nomApo = nomApo;
    }

    public String getNomPap() {
        return nomPap;
    }

    public void setNomPap(String nomPap) {
        this.nomPap = nomPap;
    }

    public String getNomMam() {
        return nomMam;
    }

    public void setNomMam(String nomMam) {
        this.nomMam = nomMam;
    }

    public String getFlgMasivo() {
        return flgMasivo;
    }

    public void setFlgMasivo(String flgMasivo) {
        this.flgMasivo = flgMasivo;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getFlgMas() {
        return flgMas;
    }

    public void setFlgMas(Integer flgMas) {
        this.flgMas = flgMas;
    }

    public Integer getNroIni() {
        return nroIni;
    }

    public void setNroIni(Integer nroIni) {
        this.nroIni = nroIni;
    }

    public Integer getNroFin() {
        return nroFin;
    }

    public void setNroFin(Integer nroFin) {
        this.nroFin = nroFin;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Integer getTipoImpresion() {
        return tipoImpresion;
    }

    public void setTipoImpresion(Integer tipoImpresion) {
        this.tipoImpresion = tipoImpresion;
    }

    public Integer getIdTipoEstado() {
        return idTipoEstado;
    }

    public void setIdTipoEstado(Integer idTipoEstado) {
        this.idTipoEstado = idTipoEstado;
    }

    public Integer getFlgMatriculaVista() {
        return flgMatriculaVista;
    }

    public void setFlgMatriculaVista(Integer flgMatriculaVista) {
        this.flgMatriculaVista = flgMatriculaVista;
    }

    public Date getFechaInicioClases() {
        return fechaInicioClases;
    }

    public void setFechaInicioClases(Date fechaInicioClases) {
        this.fechaInicioClases = fechaInicioClases;
    }

    public Date getFechaFinFiltro() {
        return fechaFinFiltro;
    }

    public void setFechaFinFiltro(Date fechaFinFiltro) {
        this.fechaFinFiltro = fechaFinFiltro;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

}
