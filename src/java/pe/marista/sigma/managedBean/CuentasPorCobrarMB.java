/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.awt.Desktop;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CajaGenBean;
import pe.marista.sigma.bean.CentroResponsabilidadBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.ConceptoUniNegBean;
import pe.marista.sigma.bean.CuentasPorCobrarBean;
import pe.marista.sigma.bean.DetDocIngresoBean;
import pe.marista.sigma.bean.DocIngresoBean;
import pe.marista.sigma.bean.EstudianteBean;
import pe.marista.sigma.bean.EstudianteRetiroBean;
import pe.marista.sigma.bean.FamiliarEstudianteBean;
import pe.marista.sigma.bean.GradoAcademicoBean;
import pe.marista.sigma.bean.MatriculaBean;
import pe.marista.sigma.bean.MesBean;
import pe.marista.sigma.bean.ModificacionesBean;
import pe.marista.sigma.bean.NivelAcademicoBean;
import pe.marista.sigma.bean.PerfilBean;
import pe.marista.sigma.bean.PersonaBean;
import pe.marista.sigma.bean.ProcesoBancoBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.TipoFormacionBean;
import pe.marista.sigma.bean.UnidadNegocioBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.CronogramaPagoRepBean;
import pe.marista.sigma.bean.reporte.CuentasPorCobrarRepBean;
import pe.marista.sigma.bean.reporte.DetCobrosPensionesRepBean;
import pe.marista.sigma.bean.reporte.DetDiaResumenIngRepBean;
import pe.marista.sigma.bean.reporte.DetEstadoCtaRepBean;
import pe.marista.sigma.bean.reporte.DetResumenIngRepBean;
import pe.marista.sigma.bean.reporte.EstadoCtaRepBean;
import pe.marista.sigma.bean.reporte.EstadoRecibosRepBean;
import pe.marista.sigma.bean.reporte.EstudianteSaldoPivotRepBean;
import pe.marista.sigma.bean.reporte.EstudianteSaldoRepBean;
import pe.marista.sigma.bean.reporte.NotasOpeFecCobroRepBean;
import pe.marista.sigma.bean.reporte.NotasOpeFecVencRepBean;
import pe.marista.sigma.bean.reporte.NotasOperacionRepBean;
import pe.marista.sigma.bean.reporte.PensionesPorCobrarLPMRepBean;
import pe.marista.sigma.bean.reporte.ProvicionPensionNivelDetalleRepBean;
import pe.marista.sigma.bean.reporte.ProvicionPensionNivelesRepBean;
import pe.marista.sigma.bean.reporte.ProvicionPensionRepBean;
import pe.marista.sigma.bean.reporte.ResumenIngRepBean;
import pe.marista.sigma.bean.reporte.ResumenMatriculaRepBean;
import pe.marista.sigma.bean.reporte.SaldoPensionesRepBean;
import pe.marista.sigma.bean.reporte.VerificacionIngresoPlanillaRepBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CajaGenService;
import pe.marista.sigma.service.CentroResponsabilidadService;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.ConceptoService;
import pe.marista.sigma.service.ConceptoUniNegService;
import pe.marista.sigma.service.CronogramaPagoService;
import pe.marista.sigma.service.CuentasPorCobrarService;
import pe.marista.sigma.service.DocIngresoService;
import pe.marista.sigma.service.EstudianteRetiroService;
import pe.marista.sigma.service.EstudianteService;
import pe.marista.sigma.service.FamiliarService;
import pe.marista.sigma.service.GradoAcademicoService;
import pe.marista.sigma.service.MatriculaService;
import pe.marista.sigma.service.NivelAcademicoService;
import pe.marista.sigma.service.PerfilService;
import pe.marista.sigma.service.PersonaService;
import pe.marista.sigma.service.ProcesoBancoService;
import pe.marista.sigma.service.UnidadNegocioService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.util.MensajesBackEnd;

/**
 *
 * @author MS002
 */
@ManagedBean
@ViewScoped
public class CuentasPorCobrarMB extends BaseMB implements Serializable {

    /*
     *
     * Creates a new instance of CuentasPorCobrarMB
     */
    @PostConstruct
    public void CuentasPorCobrarMB() {
        try {
            usuarioLogin = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            cargarAno();
            getEstudianteBean();
//            SimpleDateFormat formato = new SimpleDateFormat("yyyy");
//            String date = formato.format(new Date());
//            getMatriculaFiltroBean().setAnio(Integer.parseInt(date)); 
            Calendar miCalendario = Calendar.getInstance();
            anio = miCalendario.get(Calendar.YEAR);
            getFechaInicio();
            fechaInicio = miCalendario.getTime();
            SimpleDateFormat formatoDiaCompleto = new SimpleDateFormat("dd-MM-yyyy");
            String dateCompleto = formatoDiaCompleto.format(new Date());
            fechaCorte = formatoDiaCompleto.parse(dateCompleto);
            getListaAnioFiltroMatricula();
            for (int i = miCalendario.get(Calendar.YEAR) - 3; i <= miCalendario.get(Calendar.YEAR) + 1; i++) {
                listaAnioFiltroMatricula.add(i);
            }

            getListaAnioGenerar();
            for (int i = miCalendario.get(Calendar.YEAR) - 2; i <= miCalendario.get(Calendar.YEAR) + 1; i++) {
                listaAnioGenerar.add(i);
            }

            getListaCuentasPorCobrarBean();
            getMatriculaFiltroBean().setAnio(miCalendario.get(Calendar.YEAR));
            getMatriculaFiltroBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());

            getCuentasPorCobrarFiltro().setAnio(miCalendario.get(Calendar.YEAR));
            getCuentasPorCobrarFiltro().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());

            NivelAcademicoService nivelAcademicoService = BeanFactory.getNivelAcademicoService();
            getListaNivelAcademico();
            listaNivelAcademico = nivelAcademicoService.obtenerNivelAcaPorTipoFormacion(new TipoFormacionBean(MaristaConstantes.TIP_FOR_BAS));
            setFlgMatriPend(0);
            setFlgReProceso(0);

            getListaMesAll();
            listaMesAll.add(new MesBean(2, MaristaConstantes.FEBRERO));
            listaMesAll.add(new MesBean(3, MaristaConstantes.MARZO));
            listaMesAll.add(new MesBean(4, MaristaConstantes.ABRIL));
            listaMesAll.add(new MesBean(5, MaristaConstantes.MAYO));
            listaMesAll.add(new MesBean(6, MaristaConstantes.JUNIO));
            listaMesAll.add(new MesBean(7, MaristaConstantes.JULIO));
            listaMesAll.add(new MesBean(8, MaristaConstantes.AGOSTO));
            listaMesAll.add(new MesBean(9, MaristaConstantes.SETIEMBRE));
            listaMesAll.add(new MesBean(10, MaristaConstantes.OCTUBRE));
            listaMesAll.add(new MesBean(11, MaristaConstantes.NOVIEMBRE));
            listaMesAll.add(new MesBean(12, MaristaConstantes.DICIEMBRE));

        } catch (Exception ex) {
            Logger.getLogger(EstudianteBecaMB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private CuentasPorCobrarBean cuentasPorCobrarBean;
    private List<Object> listaAnos;
    private EstudianteBean estudianteBean;
    private PersonaBean personaBean;
    private CuentasPorCobrarBean cuentasPorCobrarFiltro;
    private List<CuentasPorCobrarBean> listaCuentasPorCobrar;
    private List<EstudianteBean> listaEstudianteBean;
    private MatriculaBean matriculaFiltroBean;
    private List<CuentasPorCobrarBean> listaCuentasPorCobrarBean;
    private List<CuentasPorCobrarBean> listaCuentasEstudianteBean;
    private CuentasPorCobrarBean cuentas;
    private List<MatriculaBean> listaMatriculaEstudiantesMasivosBean; //lista de estudiantes matriculados en el historico 

    //AYUDA MATRICULA
    //filtros
    private List<NivelAcademicoBean> listaNivelAcademico;
    private List<Integer> listaAnioFiltroMatricula;
    private List<Integer> listaAnioGenerar;
    private Boolean flgTodos;
    private Boolean flgEstSinPro;
    private Boolean flgPorNivelGrado;
    private Boolean flgEstEsp;
    private Integer flgMatriPend = 0;
    private Integer flgReProceso = 0;
    private Boolean valCtaCteTodos = true;
    private Boolean flgTodosMatriculados;
    private Boolean flgEstEspMatricula;
    private List<GradoAcademicoBean> listaGradoAcademicoFiltroBean;
    private UsuarioBean usuarioLogin;
    private Integer anio;

    private Integer crIni;
    private Integer crPri;
    private Integer crSec;
    private Integer crBac;

    private List<CentroResponsabilidadBean> listaCrInicialBean;
    private List<CentroResponsabilidadBean> listaCrPrimariaBean;
    private List<CentroResponsabilidadBean> listaCrSecundariaBean;
    private List<CentroResponsabilidadBean> listaCrBachillerBean;

    private Integer mesSelect;
    private Integer mesSelectFin;
    private List<MesBean> listaMesAll;

    private Integer idTipoLugarPago = MaristaConstantes.COD_LUGAR_BANCO;
    private Map<String, Integer> listaMeses;
    private Boolean disabled = Boolean.TRUE;
    private Date fechaInicio;
    private List<NotasOpeFecCobroRepBean> listaFechaCobros;

    private List<CuentasPorCobrarRepBean> listaCtaPorCobrarRepBean;
    private String totMora = "0.0";
    private String totDsct = "0.0";
    private String totAdeuda = "0.0";
    private String totCancelado = "0.0";
    //cambio de datos de cta cte
    private DetDocIngresoBean detDocIngresoBean;
    private List<ConceptoUniNegBean> listaConceptoUniNegBean;
    private List<CodigoBean> listaTipoStatusCtaCte;
    private List<CodigoBean> listaTipoDscto;
    private List<CentroResponsabilidadBean> listaCrBean;
    private List<GradoAcademicoBean> listaGradoAcademicoBean;
    private List<CodigoBean> listaTipoLugarPago;
    private List<CodigoBean> listaTipoModoPago;
    private List<CodigoBean> listaTipoStatusDocIng;
    private List<CodigoBean> listaTipoDocumento;
    private List<ProcesoBancoBean> listaProcesoBancoBean;
    private Boolean flgEncargadoCta = false;
    private List<CajaGenBean> listaCajaGenBean;
    private String cajaGen = null;

    private List<MatriculaBean> listaSeccionBean;

    //AYUDA
    private List<DetResumenIngRepBean> listaDetResumenIngRep;
    private List<SaldoPensionesRepBean> listaPensionesRepBean;
    private List<EstudianteSaldoPivotRepBean> listaPensionesRepBeanPivot;
    private List<ResumenIngRepBean> listaResumenIngRepBean;

    //Matriculapara la seccion
    private MatriculaBean matriculaSeccion;
    private List<FamiliarEstudianteBean> listFamiliarEst;
    private List<CodigoBean> listaStatusEst;
    private ModificacionesBean modificacionesBean;
    private Date fechaCorte;
    private Boolean flgModificacionRecibo;
    private Boolean flgStatusActivo = false;

    private EstudianteRetiroBean estudianteRetiroBean;
    private List<EstudianteRetiroBean> listEstudianteRetiroBean;
    private String idEstudianteVista = "";
    private Integer flgCondicion;
    private Integer flgTipoReporte;
    private Boolean flgDesabilitarAnoAnt = false;
    private Boolean flgActivarBuscador = false;

    public void cargarFormulario() {
        String parametro = (String) new MaristaUtils().requestObtenerObjeto("caniari");
        String parametro2 = (String) new MaristaUtils().requestObtenerObjeto("caniari2");
        String parametro3 = (String) new MaristaUtils().requestObtenerObjeto("caniari3");
        try {
            if (parametro != null) {
//          
                EstudianteBean bean = new EstudianteBean();
                bean.getPersonaBean().setIdPersona(parametro);
                bean.getPersonaBean().getUnidadNegocioBean().setUniNeg(parametro2);
                getCuentasPorCobrarFiltro().setAnio(new Integer(parametro3));
                this.anio = new Integer(parametro3);
                obtenerEstudiantePorIdEstadoCuentaRep(bean);
//                obtenerEstudiantePorId(bean);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarFormularioModificaciones() {
        try {
            CodigoService codigoService = BeanFactory.getCodigoService();
            listaStatusEst = codigoService.obtenerPorStatusEstModificaciones(new TipoCodigoBean(MaristaConstantes.TIP_STATUS_EST_BLO));
            Calendar miCalendario = Calendar.getInstance();
            anio = miCalendario.get(Calendar.YEAR);
            GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
            listaGradoAcademicoBean = gradoAcademicoService.obtenerTodosMatri();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void mostarStatusEst() {
        try {
            if (estudianteBean.getTipoStatusEst().getIdCodigo() != null) {
                CodigoService codigoService = BeanFactory.getCodigoService();
                CodigoBean cod = new CodigoBean();
                cod = codigoService.obtenerPorId(estudianteBean.getTipoStatusEst());
                if (estudianteBean.getTipoStatusEst().getIdCodigo() == 18004
                        || estudianteBean.getTipoStatusEst().getIdCodigo() == 18007) {
                    estudianteBean.setStatusEstVista(false);
                    estudianteBean.setStatusEstVista(false);
                    estudianteBean.setTipoStatusEst(cod);
                    this.flgStatusActivo = true;
                } else {
                    estudianteBean.setStatusEstVista(true);
                    estudianteBean.setStatusEstVista(true);
                    estudianteBean.setTipoStatusEst(cod);
                    this.flgStatusActivo = true; //esto estaba false porque antes no se ponia.
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void sacarMeses() {
        try {
            System.out.println("perfecto :" + flgModificacionRecibo);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cargarFormularioCta() {
        String parametro = (String) new MaristaUtils().requestObtenerObjeto("caniari");
        String parametro2 = (String) new MaristaUtils().requestObtenerObjeto("caniari2");
        String parametro3 = (String) new MaristaUtils().requestObtenerObjeto("caniari3");
        String parametro4 = (String) new MaristaUtils().requestObtenerObjeto("caniari4");
        try {
            //validar perfil
            PerfilService perfilService = BeanFactory.getPerfilService();
            List<PerfilBean> listaPerfilBean = new ArrayList<>();
            listaPerfilBean = perfilService.obtenerUsarioPerfil(usuarioLogin);
            for (int i = 0; i < listaPerfilBean.size(); i++) {
                if (listaPerfilBean.get(i).getNombre().equals(MaristaConstantes.ENCARGADO_CTACTE)) {
                    this.flgEncargadoCta = true;
                    break;
                } else {
                    this.flgEncargadoCta = false;
                }
            }
            if (parametro4 != null) {
                if (parametro4.equals("0")) {
                    CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
                    CuentasPorCobrarBean cta = new CuentasPorCobrarBean();
                    Integer id = 0;
                    System.out.println("parametro " + parametro);
                    if (parametro != null) {
                        id = new Integer(parametro);
                    }
                    cta = cuentasPorCobrarService.obtenerCuentaPorId(id, parametro2);
                    if (cta != null) {
                        parametro = cta.getEstudianteBean().getIdEstudiante();
                        parametro3 = cta.getAnio().toString();
                        System.out.println("cta.getEstudianteBean().getIdEstudiante() " + cta.getEstudianteBean().getIdEstudiante());

                        EstudianteBean bean = new EstudianteBean();
                        bean.getPersonaBean().setIdPersona(parametro);
                        bean.getPersonaBean().getUnidadNegocioBean().setUniNeg(parametro2);

                        getCuentasPorCobrarFiltro().setAnio(new Integer(parametro3));
                        this.anio = new Integer(parametro3);
                        setIdEstudianteVista(bean.getPersonaBean().getIdPersona());
                        obtenerEstudiantePorIdEstadoCuentaRep(bean);
                    }
                } else {
                    if (parametro != null) {//          
                        EstudianteBean bean = new EstudianteBean();
                        bean.getPersonaBean().setIdPersona(parametro);
                        bean.getPersonaBean().getUnidadNegocioBean().setUniNeg(parametro2);
                        getCuentasPorCobrarFiltro().setAnio(new Integer(parametro3));
                        this.anio = new Integer(parametro3);
                        obtenerEstudiantePorIdEstadoCuentaRep(bean);
                        setIdEstudianteVista(bean.getPersonaBean().getIdPersona());
//                obtenerEstudiantePorId(bean);
                    }
                }
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarCRs() {
        try {
            CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
            listaCrInicialBean = centroResponsabilidadService.obtenerCrPorNivelAcademico(MaristaConstantes.COD_CR_INI);
            listaCrPrimariaBean = centroResponsabilidadService.obtenerCrPorNivelAcademico(MaristaConstantes.COD_CR_PRI);
            listaCrSecundariaBean = centroResponsabilidadService.obtenerCrPorNivelAcademico(MaristaConstantes.COD_CR_SEC);
            listaCrBachillerBean = centroResponsabilidadService.obtenerCrPorNivelAcademico(MaristaConstantes.COD_CR_BACH);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cargarDatosReporteResumenIng() {
        try {
            CodigoService codigoService = BeanFactory.getCodigoService();
            listaTipoLugarPago = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_LUG_PAGO));
            listaMeses();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cargarDatosReporteNotasOperacion() {
        try {
            listaMeses();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cargarDatosSaldoPensiones() {
        try {
            listaMeses();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void flgDisable() {
        try {
            System.out.println(mesSelect);
            if (mesSelect != null) {
                if (mesSelect > 0) {
                    disabled = Boolean.FALSE;
                } else {
                    disabled = Boolean.TRUE;
                }
            }
            System.out.println(disabled);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void listaMeses() {
        listaMeses = new LinkedHashMap<>();
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaEnero", null), 1);
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaFebrero", null), 2);
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaMarzo", null), 3);
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaAbril", null), 4);
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaMayo", null), 5);
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaJunio", null), 6);
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaJulio", null), 7);
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaAgosto", null), 8);
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaSetiembre", null), 9);
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaOctubre", null), 10);
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaNoviembre", null), 11);
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaDiciembre", null), 12);
        listaMeses = Collections.unmodifiableMap(listaMeses);
    }

    public void anio() {
        try {
//            EstudianteService estudianteService = BeanFactory.getEstudianteService();
//            obtenerEstudiantePorIdEstadoCuenta(estudianteBean);
//            if(estudianteBean!=null){
//            obtenerEstudiantePorIdEstadoCuentaRep(estudianteBean);
//            }
            System.out.println("anio :" + anio);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public MatriculaBean getMatriculaFiltroBean() {
        if (matriculaFiltroBean == null) {
            matriculaFiltroBean = new MatriculaBean();
        }
        return matriculaFiltroBean;
    }

    public void setMatriculaFiltroBean(MatriculaBean matriculaFiltroBean) {
        this.matriculaFiltroBean = matriculaFiltroBean;
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

    public CuentasPorCobrarBean getCuentasPorCobrarBean() {
        if (cuentasPorCobrarBean == null) {
            cuentasPorCobrarBean = new CuentasPorCobrarBean();
        }
        return cuentasPorCobrarBean;
    }

    public void setCuentasPorCobrarBean(CuentasPorCobrarBean cuentasPorCobrarBean) {
        this.cuentasPorCobrarBean = cuentasPorCobrarBean;
    }

    public List<Object> getListaAnos() {
        return listaAnos;
    }

    public void setListaAnos(List<Object> listaAnos) {
        this.listaAnos = listaAnos;
    }

    public CuentasPorCobrarBean getCuentasPorCobrarFiltro() {
        if (cuentasPorCobrarFiltro == null) {
            cuentasPorCobrarFiltro = new CuentasPorCobrarBean();
        }
        return cuentasPorCobrarFiltro;
    }

    public void setCuentasPorCobrarFiltro(CuentasPorCobrarBean cuentasPorCobrarFiltro) {
        this.cuentasPorCobrarFiltro = cuentasPorCobrarFiltro;
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

    public List<CuentasPorCobrarBean> getListaCuentasPorCobrar() {
        return listaCuentasPorCobrar;
    }

    public void setListaCuentasPorCobrar(List<CuentasPorCobrarBean> listaCuentasPorCobrar) {
        this.listaCuentasPorCobrar = listaCuentasPorCobrar;
    }

    public void verificarFiltroTodos() {
        try {
            if (this.flgTodos == true) {
                this.flgEstSinPro = false;
                this.flgPorNivelGrado = false;
                this.flgEstEsp = false;
                matriculaFiltroBean.setEstudianteBean(null);
                matriculaFiltroBean.getEstudianteBean().setGradoHabilitado(null);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void verificarFiltroTodosMatricula() {
        try {
            if (this.flgTodosMatriculados == true) {
                this.flgEstEspMatricula = false;
                matriculaFiltroBean.setEstudianteBean(null);
                matriculaFiltroBean.setGradoAcademicoBean(null);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void verificarFiltroEstEspCta() {
        try {
            if (this.flgEstEspMatricula == true) {
                this.flgTodosMatriculados = false;
                cuentasPorCobrarFiltro.setEstudianteBean(null);
                cuentasPorCobrarFiltro.getMatriculaBean().setGradoAcademicoBean(null);

            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void verificarFiltroTodosCta() {
        try {
            if (this.flgTodosMatriculados == true) {
                this.flgEstEspMatricula = false;
                cuentasPorCobrarFiltro.setEstudianteBean(null);
                cuentasPorCobrarFiltro.getMatriculaBean().setGradoAcademicoBean(null);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void verificarFiltroEstSinPro() {
        try {
            if (this.flgEstSinPro == true) {
                this.flgTodos = false;
                this.flgEstEsp = false;
                matriculaFiltroBean.setEstudianteBean(null);
                matriculaFiltroBean.getEstudianteBean().setGradoHabilitado(null);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void verificarFiltroNivelGrado() {
        try {
            if (this.flgPorNivelGrado == true) {
                this.flgTodos = false;
                this.flgEstEsp = false;
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void verificarFiltroEstEsp() {
        try {
            if (this.flgEstEsp == true) {
                this.flgTodos = false;
                this.flgEstSinPro = false;
                this.flgPorNivelGrado = false;
                matriculaFiltroBean.setEstudianteBean(null);
                matriculaFiltroBean.getEstudianteBean().setGradoHabilitado(null);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerEstudiantePorUniNeg() {
        try {
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            MatriculaBean matriculaBean = new MatriculaBean();
            SimpleDateFormat formato = new SimpleDateFormat("yyyy");
            matriculaBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
            String date = formato.format(new Date());
            matriculaBean.setAnio(Integer.parseInt(date));
            listaEstudianteBean = cuentasPorCobrarService.obtenerMatriculadosPorPeriodo(matriculaBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerFiltroEstudiante() {
        try {
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            if (matriculaFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona() != null && !matriculaFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona().equals("")) {
                matriculaFiltroBean.getEstudianteBean().getPersonaBean().setIdPersona(matriculaFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona());
            }
            if (matriculaFiltroBean.getEstudianteBean().getCodigo() != null && !matriculaFiltroBean.getEstudianteBean().getCodigo().equals("")) {
                matriculaFiltroBean.getEstudianteBean().setCodigo(matriculaFiltroBean.getEstudianteBean().getCodigo());
            }
            if (matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApepat() != null && !matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApepat().equals("")) {
                matriculaFiltroBean.getEstudianteBean().getPersonaBean().setApepat(matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApepat().toUpperCase().trim());
            }
            if (matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApemat() != null && !matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApemat().equals("")) {
                matriculaFiltroBean.getEstudianteBean().getPersonaBean().setApemat(matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApemat().toUpperCase().trim());
            }
            if (matriculaFiltroBean.getEstudianteBean().getPersonaBean().getNombre() != null && !matriculaFiltroBean.getEstudianteBean().getPersonaBean().getNombre().equals("")) {
                matriculaFiltroBean.getEstudianteBean().getPersonaBean().setNombre(matriculaFiltroBean.getEstudianteBean().getPersonaBean().getNombre().toUpperCase().trim());
            }
            if (matriculaFiltroBean.getEstudianteBean().getCodigo() != null && !matriculaFiltroBean.getEstudianteBean().getCodigo().equals("")) {
                matriculaFiltroBean.getEstudianteBean().setCodigo(matriculaFiltroBean.getEstudianteBean().getCodigo().toUpperCase().trim());
            }
            if (anio != null) {
                matriculaFiltroBean.setAnio(anio);
            }
            matriculaFiltroBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
            listaEstudianteBean = cuentasPorCobrarService.obtenerFiltroEstudianteMatriculado(matriculaFiltroBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerFiltroCtaCteEstudiante() {
        try {
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            cuentasPorCobrarFiltro.setAnio(cuentasPorCobrarFiltro.getAnio());
            cuentasPorCobrarFiltro.setFlgDistinct(true);
            listaCuentasEstudianteBean = cuentasPorCobrarService.obtenerCtaCtePorEstudiantePorAnio(cuentasPorCobrarFiltro);
            if (listaCuentasEstudianteBean.isEmpty()) {
                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                listaCuentasEstudianteBean = new ArrayList<>();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerFiltroEstudianteMasivo() {
        try {
            int estado = 0;
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            if (flgTodos == true) {
                matriculaFiltroBean.setAnioFin(null);
                listaMatriculaEstudiantesMasivosBean = matriculaService.obtenerFiltroEstudianteCtasPorCobrarMasivo(matriculaFiltroBean);
            } else {
                Calendar miCalendario = Calendar.getInstance();
                matriculaFiltroBean.setAnioFin(miCalendario.get(Calendar.YEAR));
//                estado = 1;
                if (flgEstSinPro == true) {
                    estado = 1;
                }
                if (matriculaFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona() != null && !matriculaFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona().equals("")) {
                    matriculaFiltroBean.getEstudianteBean().getPersonaBean().setIdPersona(matriculaFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona());
                    estado = 1;
                }
                if (matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApepat() != null && !matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApepat().equals("")) {
                    matriculaFiltroBean.getEstudianteBean().getPersonaBean().setApepat(matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApepat());
                    estado = 1;
                }
                if (matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApemat() != null && !matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApemat().equals("")) {
                    matriculaFiltroBean.getEstudianteBean().getPersonaBean().setApemat(matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApemat());
                    estado = 1;
                }
                if (matriculaFiltroBean.getEstudianteBean().getPersonaBean().getNombre() != null && !matriculaFiltroBean.getEstudianteBean().getPersonaBean().getNombre().equals("")) {
                    matriculaFiltroBean.getEstudianteBean().getPersonaBean().setNombre(matriculaFiltroBean.getEstudianteBean().getPersonaBean().getNombre());
                    estado = 1;
                }
                if (matriculaFiltroBean.getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico() != null && !matriculaFiltroBean.getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico().equals(0)) {
                    matriculaFiltroBean.getGradoAcademicoBean().getNivelAcademicoBean().setIdNivelAcademico(matriculaFiltroBean.getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico());
                    estado = 1;
                }
                if (matriculaFiltroBean.getGradoAcademicoBean().getIdGradoAcademico() != null && !matriculaFiltroBean.getGradoAcademicoBean().getIdGradoAcademico().equals(0)) {
                    matriculaFiltroBean.getGradoAcademicoBean().setIdGradoAcademico(matriculaFiltroBean.getGradoAcademicoBean().getIdGradoAcademico());
                    estado = 1;
                } else if (estado == 0 && flgTodos == false) {
                    new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                    listaMatriculaEstudiantesMasivosBean = new ArrayList<>();
                }
                if (estado == 1) {
                    listaMatriculaEstudiantesMasivosBean = matriculaService.obtenerFiltroEstudianteCtasPorCobrarMasivo(matriculaFiltroBean);
                    if (listaMatriculaEstudiantesMasivosBean.isEmpty()) {
                        new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                        listaMatriculaEstudiantesMasivosBean = new ArrayList<>();
                    }
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerFiltroSaldoPensiones(Date fecha) {
        try {
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();

            Integer idNivel = 0;
            Integer idGrado = 0;
            String secc = "";
            if (getMatriculaFiltroBean().getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico() != null) {
                idNivel = getMatriculaFiltroBean().getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico();
            } else {
                idNivel = null;
            }
            if (getMatriculaFiltroBean().getGradoAcademicoBean().getIdGradoAcademico() != null) {
                idGrado = getMatriculaFiltroBean().getGradoAcademicoBean().getIdGradoAcademico();
            } else {
                idGrado = null;
            }
            if (getMatriculaFiltroBean().getSeccion() != null) {
                secc = getMatriculaFiltroBean().getSeccion();
            } else {
                secc = null;
            }
            System.out.println("idNivel " + idNivel);
            System.out.println("idGrado " + idGrado);
            System.out.println("secc " + secc);
            List<EstudianteSaldoPivotRepBean> listaCabecera = new ArrayList<>();
            listaPensionesRepBeanPivot = new ArrayList<>();
            List<EstudianteSaldoRepBean> listaEstudiantes = new ArrayList<>();
            listaEstudiantes = cuentasPorCobrarService.obtenerEstudianteConSaldoPorFecha(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, 19401, fecha, this.mesSelect, idNivel, idGrado, secc);
            List<String> listaIds = new ArrayList<>();
            for (int k = 0; k < listaEstudiantes.size(); k++) {
                listaIds.add(listaEstudiantes.get(k).getIdestudiante());
            }
            listaCabecera = cuentasPorCobrarService.obtenerSaldoPenionesPorFechaPivot(0, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, 19401, listaIds, fecha, this.mesSelect, idNivel, idGrado, secc);
            if (!listaCabecera.isEmpty()) {
                for (int i = 0; i < listaCabecera.size(); i++) {
                    if (!listaEstudiantes.isEmpty()) {
                        for (int j = 0; j < listaCabecera.size(); j++) {
                            System.out.println("NRO: " + j);
                            listaPensionesRepBeanPivot = cuentasPorCobrarService.obtenerSaldoPenionesPorFechaPivot(1, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, 19401, listaIds, fecha, this.mesSelect, null, null, null);
                            for (EstudianteSaldoPivotRepBean l : listaPensionesRepBeanPivot) {
                                System.out.println("listaaaa " + l.getIdentificador());
                                System.out.println("listaaaa " + l.getNombre());
                            }
                        }
                    }
                }
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerGradoAcaBasica() {
        try {
            GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
            listaGradoAcademicoFiltroBean = gradoAcademicoService.obtenerGradoAcaPorNivelAca(matriculaFiltroBean.getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerIdGradoAca(Integer id) {
        try {
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            getMatriculaFiltroBean().getGradoAcademicoBean().setIdGradoAcademico(id);
            System.out.println("idgrado " + getMatriculaFiltroBean().getGradoAcademicoBean().getIdGradoAcademico());
            listaSeccionBean = matriculaService.obtenerSeccionPorGrado(id, anio, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerSeccion(String sec) {
        try {
            getMatriculaFiltroBean().setSeccion(sec);
            System.out.println("secc " + getMatriculaFiltroBean().getSeccion());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerGradoAcaBasicaFiltro() {
        try {
            GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
            listaGradoAcademicoFiltroBean = gradoAcademicoService.obtenerGradoAcaPorNivelAca(cuentasPorCobrarFiltro.getMatriculaBean().getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarEstudianteMatriculaMasivo() {
        matriculaFiltroBean = new MatriculaBean();
        listaMatriculaEstudiantesMasivosBean = new ArrayList<>();
        flgEstEsp = false;
        flgEstSinPro = false;
        flgPorNivelGrado = false;
        flgTodos = false;
        setFlgMatriPend(0);
        setFlgReProceso(0);

        Calendar miCalendario = Calendar.getInstance();
        getMatriculaFiltroBean().setAnio(miCalendario.get(Calendar.YEAR));
        getMatriculaFiltroBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());

    }

    public void limpiarEstudianteMatricula() {
        matriculaFiltroBean = new MatriculaBean();
        listaMatriculaEstudiantesMasivosBean = new ArrayList<>();
        flgEstEsp = false;
        flgEstSinPro = false;
        flgPorNivelGrado = false;
        flgTodos = false;
        setFlgMatriPend(0);
        setFlgReProceso(0);

        Calendar miCalendario = Calendar.getInstance();
        getMatriculaFiltroBean().setAnio(miCalendario.get(Calendar.YEAR));
        getMatriculaFiltroBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
        listaEstudianteBean = new ArrayList<>();
    }

    public String insertarCtaCtesMasivo() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            System.out.println(">>>> mes " + mesSelect);
            if (pagina == null) {
                if (crIni == null || crPri == null || crSec == null || crBac == null) {
                    new MensajePrime().addInformativeMessagePer("msjDatosRequeridos");
                } else {
                    MatriculaService matriculaService = BeanFactory.getMatriculaService();
                    CuentasPorCobrarBean cuentasPorCobrar = new CuentasPorCobrarBean();
                    matriculaService.insertarCuentasPorCobrarMasivo(cuentasPorCobrar, listaMatriculaEstudiantesMasivosBean, usuarioLogin, this.flgMatriPend, this.flgReProceso, crIni, crPri, crSec, crBac, anio, mesSelect);
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String modificarCuentaFull() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();

            if (pagina == null) {
                CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
                cuentasPorCobrarBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                cuentasPorCobrarBean.setEstudianteBean(estudianteBean);
                cuentasPorCobrarBean.getMatriculaBean().setEstudianteBean(estudianteBean);
                cuentasPorCobrarBean.getMatriculaBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                if (cuentasPorCobrarBean.getCreaDscto().equals("")) {
                    cuentasPorCobrarBean.setCreaDscto(null);
                }
                if (cuentasPorCobrarBean.getObs().equals("")) {
                    cuentasPorCobrarBean.setObs(null);
                }
                if (cuentasPorCobrarBean.getCuentaD().getCuenta().equals("")) {
                    cuentasPorCobrarBean.getCuentaD().setCuenta(null);
                }
                if (cuentasPorCobrarBean.getCuentaH().getCuenta().equals("")) {
                    cuentasPorCobrarBean.getCuentaH().setCuenta(null);
                }
                cuentasPorCobrarService.modificarCuentaFull(cuentasPorCobrarBean, usuarioLogin.getUsuario());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                obtenerEstudiantePorIdEstadoCuentaRep(estudianteBean);

            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String modificarDocIngFull() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();

            if (pagina == null) {
//                CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
                DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
                detDocIngresoBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                detDocIngresoBean.getDocIngresoBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                detDocIngresoBean.setIdDocIngreso(detDocIngresoBean.getDocIngresoBean().getIdDocIngreso());
                if (detDocIngresoBean.getDocIngresoBean().getCodDiscente().equals("")) {
                    detDocIngresoBean.getDocIngresoBean().setCodDiscente(null);
                }
                if (detDocIngresoBean.getDocIngresoBean().getNombreDiscente().equals("")) {
                    detDocIngresoBean.getDocIngresoBean().setNombreDiscente(null);
                }
                if (detDocIngresoBean.getDocIngresoBean().getSeccion().equals("")) {
                    detDocIngresoBean.getDocIngresoBean().setSeccion(null);
                }
                if (detDocIngresoBean.getDocIngresoBean().getDireccion().equals("")) {
                    detDocIngresoBean.getDocIngresoBean().setDireccion(null);
                }
                if (detDocIngresoBean.getDocIngresoBean().getTelefono().equals("")) {
                    detDocIngresoBean.getDocIngresoBean().setTelefono(null);
                }
                if (detDocIngresoBean.getDocIngresoBean().getFlgCajaGenNull().equals(Boolean.TRUE)) {
                    detDocIngresoBean.getDocIngresoBean().setCajaGenBean(null);
                }
                if (detDocIngresoBean.getDocIngresoBean().getImpresoraCajaBean().getImpresora().getImpresora().equals("")) {
                    detDocIngresoBean.getDocIngresoBean().getImpresoraCajaBean().getImpresora().setImpresora(null);
                }
                if (detDocIngresoBean.getDocIngresoBean().getIdRespPago().equals("")) {
                    detDocIngresoBean.getDocIngresoBean().setIdRespPago(null);
                }
                if (detDocIngresoBean.getDocIngresoBean().getNomResPago().equals("")) {
                    detDocIngresoBean.getDocIngresoBean().setNomResPago(null);
                }
                if (detDocIngresoBean.getDocIngresoBean().getCreaStatus().equals("")) {
                    detDocIngresoBean.getDocIngresoBean().setCreaStatus(null);
                }
                if (detDocIngresoBean.getDocIngresoBean().getObs().equals("")) {
                    detDocIngresoBean.getDocIngresoBean().setObs(null);
                }
                if (detDocIngresoBean.getCuentaD().getCuenta().equals("")) {
                    detDocIngresoBean.getCuentaD().setCuenta(null);
                }
                if (detDocIngresoBean.getCuentaH().getCuenta().equals("")) {
                    detDocIngresoBean.getCuentaH().setCuenta(null);
                }
                if (detDocIngresoBean.getReferencia().equals("")) {
                    detDocIngresoBean.setReferencia(null);
                }
                docIngresoService.modificarDocIngyDetalleFull(detDocIngresoBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                obtenerEstudiantePorIdEstadoCuentaRep(estudianteBean);

            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void obtenerFechasCobro() {
        try {
            Integer idTipoLugar = MaristaConstantes.COD_LUGAR_BANCO;
            String uniNeg = usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            listaFechaCobros = cuentasPorCobrarService.obtenerNotaOperacionPorFechaCobros(mesSelect, uniNeg, anio, idTipoLugar, null);
            if (listaFechaCobros.isEmpty()) {
                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarFechasCobros() {
        try {
            mesSelect = null;
            listaFechaCobros = new ArrayList<>();
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public final void cargarAno() {
        try {
            int a = 2030;
            int b = 2010;
            listaAnos = new ArrayList<>();
            for (int i = b; i <= a; i++) {
                listaAnos.add(i);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void consultar() {
        try {
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            CuentasPorCobrarBean cuentasPorCobrar = new CuentasPorCobrarBean();
            cuentasPorCobrar.setAnio(cuentasPorCobrarFiltro.getAnio());
            cuentasPorCobrarBean.setGrado(listaCuentasPorCobrar.get(0).getGrado());
            cuentasPorCobrarBean.setSeccion(listaCuentasPorCobrar.get(0).getSeccion());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }

    }

    public void rowSelect(SelectEvent event) {
        try {
            estudianteBean = (EstudianteBean) event.getObject();
//            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            obtenerEstudiantePorId(estudianteBean);

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void rowSelectEstadoCtaCambioRecibo(SelectEvent event) {
        try {
            estudianteBean = (EstudianteBean) event.getObject();
//            EstudianteService estudianteService = BeanFactory.getEstudianteService();
//            obtenerEstudiantePorIdEstadoCuenta(estudianteBean);
            estudianteBean.setFechaStatusEstAyuda(estudianteBean.getFechaStatusEstAyuda());
            System.out.println("0" + estudianteBean.getFechaStatusEstAyuda());
            obtenerEstudiantePorIdCambioRecibos(estudianteBean);
            estudianteBean.setFechaStatusEstAyuda(estudianteBean.getFechaStatusEstAyuda());
            System.out.println("1" + estudianteBean.getFechaStatusEstAyuda());
            mostarStatusEst();
            Calendar miCalendario = Calendar.getInstance();
            Integer anioActual = miCalendario.get(Calendar.YEAR);
            Integer AnioFiltro = anio;
            if (anioActual > AnioFiltro) {
                flgDesabilitarAnoAnt = true;
            } else {
                flgDesabilitarAnoAnt = false;
            }
            flgActivarBuscador = true;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void rowSelectEstadoCta(SelectEvent event) {
        try {
            estudianteBean = (EstudianteBean) event.getObject();
            idEstudianteVista = estudianteBean.getIdEstudiante();
//            EstudianteService estudianteService = BeanFactory.getEstudianteService();
//            obtenerEstudiantePorIdEstadoCuenta(estudianteBean);  
            estudianteBean.setFechaStatusEstAyuda(estudianteBean.getFechaStatusEstAyuda());
            System.out.println("0" + estudianteBean.getFechaStatusEstAyuda());
            obtenerEstudiantePorIdEstadoCuentaRep(estudianteBean);
            estudianteBean.setFechaStatusEstAyuda(estudianteBean.getFechaStatusEstAyuda());
            System.out.println("1" + estudianteBean.getFechaStatusEstAyuda());
            EstudianteRetiroService estudianteRetiroService = BeanFactory.getEstudianteRetiroService();
            listEstudianteRetiroBean = new ArrayList<>();
            System.out.println("unineg: " + usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            System.out.println("anio: " + anio);
            System.out.println("codigo: " + estudianteBean.getCodigo());
            listEstudianteRetiroBean = estudianteRetiroService.obtenerPorEstudiante(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(),
                    anio, estudianteBean.getCodigo());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerEstudiantePorId(Object estudiante) {
        try {
            estudianteBean = (EstudianteBean) estudiante;
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            estudianteBean.setAnio(anio);
            estudianteBean = estudianteService.obtenerEstPorId(estudianteBean);
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            listaCuentasPorCobrar = cuentasPorCobrarService.obtenerCuentaPorMatAnio(estudianteBean.getPersonaBean().getIdPersona(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), cuentasPorCobrarFiltro.getAnio());
            listaCuentasPorCobrarBean = new ArrayList<>();
            Date fechaHoy = new Date();
            for (CuentasPorCobrarBean cuenta : listaCuentasPorCobrar) {
                if (cuenta.getAnio().equals(cuentasPorCobrarFiltro.getAnio())) {
                    if (cuenta.getFechaPago() != null) {
                        cuenta.setAlerta("/resources/images/verde.png");
                    } else if (cuenta.getFechaVenc().getTime() < fechaHoy.getTime() && cuenta.getFechaPago() == null) {
                        cuenta.setAlerta("/resources/images/rojo.png");
                    } else if (cuenta.getFechaVenc().getTime() > fechaHoy.getTime()) {
                        cuenta.setAlerta("/resources/images/amarillo.png");
                    }
                    listaCuentasPorCobrarBean.add(cuenta);
                }
//                    if (cuenta.getFechaVenc().getTime() < fechaHoy.getTime() && cuenta.getFechaPago() == null) {
//                        cuenta.setAlerta("/resources/images/rojo.png");
//                    } else if (cuenta.getFechaVenc().getTime() < fechaHoy.getTime() && cuenta.getFechaPago() != null) {
//                        cuenta.setAlerta("/resources/images/verde.png");
//                    } else if (cuenta.getFechaVenc().getTime() > fechaHoy.getTime()) {
//                        cuenta.setAlerta("/resources/images/amarillo.png");
//                    }
//                    listaCuentasPorCobrarBean.add(cuenta);
//                }
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerEstudiantePorIdEstadoCuenta(Object estudiante) {
        try {
            estudianteBean = (EstudianteBean) estudiante;
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            estudianteBean.setAnio(anio);
            estudianteBean = estudianteService.obtenerEstPorId(estudianteBean);
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            listaCuentasPorCobrar = cuentasPorCobrarService.obtenerCuentaPorMatAnio(estudianteBean.getPersonaBean().getIdPersona(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), matriculaFiltroBean.getAnio());
            listaCuentasPorCobrarBean = new ArrayList<>();
            Date fechaHoy = new Date();
            for (CuentasPorCobrarBean cuenta : listaCuentasPorCobrar) {
//                if (cuenta.getAnio().equals(matriculaFiltroBean.getAnio())) {
                if (cuenta.getFechaVenc() != null) {
                    if (cuenta.getFechaPago() != null) {
                        cuenta.setAlerta("/resources/images/verde.png");
                    } else if (cuenta.getFechaVenc().getTime() < fechaHoy.getTime() && cuenta.getFechaPago() == null) {
                        cuenta.setAlerta("/resources/images/rojo.png");
                    } else if (cuenta.getFechaVenc().getTime() > fechaHoy.getTime()) {
                        cuenta.setAlerta("/resources/images/amarillo.png");
                    }
                    listaCuentasPorCobrarBean.add(cuenta);
                }
//                } 
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerEstudiantePorIdEstadoCuentaRep(Object estudiante) {
        try {
            totAdeuda = "0.0";
            totMora = "0.0";
            totCancelado = "0.0";
            totDsct = "0.0";

            estudianteBean = (EstudianteBean) estudiante;
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            estudianteBean.setAnio(anio);
            estudianteBean = estudianteService.obtenerEstPorIdMatricula(estudianteBean);
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            listaCtaPorCobrarRepBean = cuentasPorCobrarService.obtenerEstadoCtaPorAnio(estudianteBean.getPersonaBean().getIdPersona(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
            if (!listaCtaPorCobrarRepBean.isEmpty()) {
                for (CuentasPorCobrarRepBean lista : listaCtaPorCobrarRepBean) {
                    totAdeuda = lista.getTotAdeuda();
                    totMora = lista.getTotMora();
                    totCancelado = lista.getTotPag();
                    totDsct = lista.getTotDesc();
                }
            }
            estudianteBean.setFechaStatusEstAyuda(estudianteBean.getFechaStatusEstAyuda());
            System.out.println("2" + estudianteBean.getFechaStatusEstAyuda());

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerEstudiantePorIdCambioRecibos(Object estudiante) {
        try {
            totAdeuda = "0.0";
            totMora = "0.0";
            totCancelado = "0.0";
            totDsct = "0.0";

            estudianteBean = (EstudianteBean) estudiante;
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            estudianteBean.setAnio(anio);
            estudianteBean = estudianteService.obtenerEstPorId(estudianteBean);
            MatriculaService matri = BeanFactory.getMatriculaService();
            MatriculaBean matricula = new MatriculaBean();
            matricula.getEstudianteBean().getPersonaBean().setIdPersona(estudianteBean.getPersonaBean().getIdPersona());
            matricula.setAnio(anio);
            matriculaSeccion = matri.obtenerMatriculaPorIdPeriodo(matricula);
            FamiliarService fam = BeanFactory.getFamiliarService();
            FamiliarEstudianteBean famiEst = new FamiliarEstudianteBean();
            listFamiliarEst = fam.obtenerFamiliarEstPorEst(estudianteBean.getPersonaBean().getIdPersona());
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            listaCtaPorCobrarRepBean = cuentasPorCobrarService.obtenerEstadoCtaPorAnio(estudianteBean.getPersonaBean().getIdPersona(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
            if (!listaCtaPorCobrarRepBean.isEmpty()) {
                for (CuentasPorCobrarRepBean lista : listaCtaPorCobrarRepBean) {
                    totAdeuda = lista.getTotAdeuda();
                    totMora = lista.getTotMora();
                    totCancelado = lista.getTotPag();
                    totDsct = lista.getTotDesc();
                }
            }
            estudianteBean.setFechaStatusEstAyuda(estudianteBean.getFechaStatusEstAyuda());
            System.out.println("2" + estudianteBean.getFechaStatusEstAyuda());

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerEstudiantePorId2(Object ctaCte) {
        try {
            cuentasPorCobrarBean = (CuentasPorCobrarBean) ctaCte;
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            listaCuentasPorCobrar = cuentasPorCobrarService.obtenerCtaCtePorEstudiante(cuentasPorCobrarBean);
            listaCuentasPorCobrarBean = new ArrayList<>();
            Date fechaHoy = new Date();
            for (CuentasPorCobrarBean cuenta : listaCuentasPorCobrar) {
                if (cuenta.getAnio() == matriculaFiltroBean.getAnio()) {
                    if (cuenta.getFechaVenc().getTime() < fechaHoy.getTime() && cuenta.getFechaPago() == null) {
                        cuenta.setAlerta("/resources/images/rojo.png");
                    } else if (cuenta.getFechaVenc().getTime() < fechaHoy.getTime() && cuenta.getFechaPago() != null) {
                        cuenta.setAlerta("/resources/images/verde.png");
                    } else if (cuenta.getFechaVenc().getTime() > fechaHoy.getTime()) {
                        cuenta.setAlerta("/resources/images/verde.png");
                    }
                    listaCuentasPorCobrarBean.add(cuenta);
                }
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cambiarValCtaCteTodos() {
        try {
            if (valCtaCteTodos) {
                for (MatriculaBean matricula : listaMatriculaEstudiantesMasivosBean) {
                    matricula.getEstudianteBean().setEstadoAprobacionCtaPorCobrar(true);
                }
            } else {
                for (MatriculaBean matricula : listaMatriculaEstudiantesMasivosBean) {
                    matricula.getEstudianteBean().setEstadoAprobacionCtaPorCobrar(false);
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarDatosCtaxCobrar() {
        try {
            String uniNeg = usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            //concepto y setteo el idgradoacademico del concepto si tiene
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoUniNegPorIng(uniNeg);
            CodigoService codigoService = BeanFactory.getCodigoService();
            listaTipoStatusCtaCte = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_STATUS_CTA_CTE));
            CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
            listaCrBean = centroResponsabilidadService.obtenerCentroResponsabilidad();
            listaTipoDscto = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_DSCTO));

            GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
            listaGradoAcademicoBean = gradoAcademicoService.obtenerTodosMatri();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void guardarModificaciones() {
        try {
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
//            List<Integer> listaRecibos = cuentasPorCobrarService.obtenerRecibosGenerados(estudianteBean.getPersonaBean().getIdPersona(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
//            if (listaRecibos.size() > 0) {
            if (estudianteBean.getGradoHabilitado().getIdGradoAcademico() != null && !estudianteBean.getGradoHabilitado().getIdGradoAcademico().equals(0)
                    && matriculaSeccion.getSeccion() != null && !matriculaSeccion.getSeccion().trim().equals("")
                    && estudianteBean.getRespPagoBean().getIdPersona() != null && !estudianteBean.getRespPagoBean().getIdPersona().trim().equals("")
                    && estudianteBean.getTipoStatusEst().getIdCodigo() != null && !estudianteBean.getTipoStatusEst().getIdCodigo().equals(0)) {
//                if (modificacionesBean.getMesInicio() != null && !modificacionesBean.getMesInicio().equals(0)
//                        && modificacionesBean.getMesFin() != null && !modificacionesBean.getMesFin().equals(0)) {

                if (estudianteBean.getTipoStatusEst().getIdCodigo().equals(18002)) {
                    if (estudianteBean.getFechaStatusEst() != null && !estudianteBean.getFechaStatusEst().equals("")
                            && estudianteBean.getMotivoStatusEst() != null && !estudianteBean.getMotivoStatusEst().trim().equals("")) {
                        //Cambio de grado
                        MatriculaService matriculaService = BeanFactory.getMatriculaService();
                        EstudianteService estudianteService = BeanFactory.getEstudianteService();
                        PersonaService personaService = BeanFactory.getPersonaService();
                        FamiliarService familiarService = BeanFactory.getFamiliarService();
                        matriculaSeccion.setModiPor(usuarioLogin.getUsuario());
                        matriculaSeccion.getEstudianteBean().setIdEstudiante(estudianteBean.getPersonaBean().getIdPersona());
                        matriculaSeccion.setGradoAcademicoBean(matriculaSeccion.getGradoAcademicoBean());
                        System.out.println("grado: " + matriculaSeccion.getGradoAcademicoBean());
                        matriculaSeccion.setAnio(anio);
//                        matriculaService.modificarMatriculaGradoAcaLista(matriculaSeccion, usuarioLogin, listaMatriculaEstudiantesMasivosBean);
                        matriculaService.modificarMatriculaGradoAca(matriculaSeccion, usuarioLogin, matriculaSeccion.getGradoAcademicoBean());
                        //Modificar la seccion
                        matriculaService.modificarMatriculaSeccion(matriculaSeccion);
                        //ya no se esta cambiando la seccion de estudiante pero si en la matricula
                        estudianteService.modificarEstudianteSeccionyRetiro(estudianteBean.getPersonaBean().getIdPersona(), matriculaSeccion.getSeccion(), estudianteBean.getFechaStatusEst(), estudianteBean.getMotivoStatusEst());
                        //Modificar Responsable Economico
                        FamiliarEstudianteBean fam = new FamiliarEstudianteBean();
                        fam.getFamiliarBean().getPersonaBean().getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        fam.getEstudianteBean().getPersonaBean().setIdPersona(estudianteBean.getPersonaBean().getIdPersona());
                        fam.getFamiliarBean().getPersonaBean().setIdPersona(totMora);
                        familiarService.obtenerFamiliarEstPorId(fam);
                        personaService.modificarResponsable(estudianteBean);
                        //// Docingreso
                        DocIngresoBean docIngresoBean = new DocIngresoBean();
                        PersonaBean perBus = new PersonaBean();
                        perBus.setIdPersona(estudianteBean.getRespPagoBean().getIdPersona());
                        perBus.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        PersonaBean perResul = new PersonaBean();
                        perResul = personaService.obtenerPersPorId(perBus);
                        DocIngresoService doc = BeanFactory.getDocIngresoService();
                        doc.modificarRespPagDocIng(perResul.getIdPersona(), perResul.getNombreCompleto(), estudianteBean.getPersonaBean().getIdPersona(),
                                usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, modificacionesBean.getMesInicio(), modificacionesBean.getMesFin(), matriculaSeccion.getGradoAcademicoBean().getIdGradoAcademico());
                        //Modificar el estado del estudiante  
                        estudianteService.modificarTipoStatusEst(estudianteBean);
                        ////Estado de Ctas 
                        Integer idtipoStatusCtaCte = 0;
                        idtipoStatusCtaCte = 19406;
                        Double monto = 0.0;
                        cuentasPorCobrarService.cambiarEstadoCtaPorCobrarModificaciones(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(),
                                idtipoStatusCtaCte, estudianteBean.getPersonaBean().getIdPersona(), modificacionesBean.getMesInicio(),
                                modificacionesBean.getMesFin(), anio, monto);
                        //Modificando Monto a cero porque seran anulados
                        doc.modificarMontosDocIng(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), estudianteBean.getPersonaBean().getIdPersona(), modificacionesBean.getMesInicio(),
                                modificacionesBean.getMesFin(), anio, monto, matriculaSeccion.getGradoAcademicoBean().getIdGradoAcademico());
                        insertarModificaciones();
//                        RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                        EstudianteRetiroService estudianteRetiroService = BeanFactory.getEstudianteRetiroService();
                        EstudianteRetiroBean estududianteRetiro = new EstudianteRetiroBean();
                        estududianteRetiro.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        estududianteRetiro.setAnio(anio);
                        estududianteRetiro.getEstudianteBean().setCodigo(estudianteBean.getCodigo());
                        SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");
                        String fechaComoCadena = date.format(estudianteBean.getFechaStatusEst());
                        estududianteRetiro.setFechaRetiro(fechaComoCadena);
                        estududianteRetiro.setMotivoRetiro(estudianteBean.getMotivoStatusEst());
                        estududianteRetiro.setNroResolucion(estudianteRetiroBean.getNroResolucion());
                        estududianteRetiro.setCreaPor(usuarioLogin.getUsuario());
                        estudianteRetiroService.insertarEstudianteRetiro(estududianteRetiro);

                    } else {
                        new MensajePrime().addInformativeMessagePer("El alumno esta retirado ingresar la fecha y el motivo del retiro.");
                    }
                } else {
                    if (flgModificacionRecibo != null) {
                        //Cambio de grado
                        MatriculaService matriculaService = BeanFactory.getMatriculaService();
                        EstudianteService estudianteService = BeanFactory.getEstudianteService();
                        PersonaService personaService = BeanFactory.getPersonaService();
                        FamiliarService familiarService = BeanFactory.getFamiliarService();
                        matriculaSeccion.setModiPor(usuarioLogin.getUsuario());
                        matriculaSeccion.getEstudianteBean().setIdEstudiante(estudianteBean.getPersonaBean().getIdPersona());
                        matriculaSeccion.setGradoAcademicoBean(matriculaSeccion.getGradoAcademicoBean());
                        matriculaSeccion.setAnio(anio);
//                    matriculaService.modificarMatriculaGradoAcaLista(matriculaSeccion, usuarioLogin, listaMatriculaEstudiantesMasivosBean);
                        matriculaService.modificarMatriculaGradoAca(matriculaSeccion, usuarioLogin, matriculaSeccion.getGradoAcademicoBean());

                        //Modificar la seccion
                        matriculaService.modificarMatriculaSeccion(matriculaSeccion);
                        estudianteService.modificarEstudianteSeccionyRetiro(estudianteBean.getPersonaBean().getIdPersona(), matriculaSeccion.getSeccion(), estudianteBean.getFechaStatusEst(), estudianteBean.getMotivoStatusEst());
                        //Modificar Responsable Economico
                        FamiliarEstudianteBean fam = new FamiliarEstudianteBean();
                        fam.getFamiliarBean().getPersonaBean().getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        fam.getEstudianteBean().getPersonaBean().setIdPersona(estudianteBean.getPersonaBean().getIdPersona());
                        fam.getFamiliarBean().getPersonaBean().setIdPersona(totMora);
                        familiarService.obtenerFamiliarEstPorId(fam);
                        personaService.modificarResponsable(estudianteBean);
                        //// Docingreso
                        DocIngresoBean docIngresoBean = new DocIngresoBean();
                        PersonaBean perBus = new PersonaBean();
                        perBus.setIdPersona(estudianteBean.getRespPagoBean().getIdPersona());
                        perBus.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        PersonaBean perResul = new PersonaBean();
                        perResul = personaService.obtenerPersPorId(perBus);
                        DocIngresoService doc = BeanFactory.getDocIngresoService();
                        doc.modificarRespPagDocIng(perResul.getIdPersona(), perResul.getNombreCompleto(), estudianteBean.getPersonaBean().getIdPersona(),
                                usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, modificacionesBean.getMesInicio(), modificacionesBean.getMesFin(), matriculaSeccion.getGradoAcademicoBean().getIdGradoAcademico());

                        //Modificar el estado del estudiante  
                        estudianteService.modificarTipoStatusEst(estudianteBean);
                        ////Estado de Ctas 
                        Integer idtipoStatusCtaCte = 0;
                        if (flgModificacionRecibo.equals(true)) {
                            idtipoStatusCtaCte = 19406;
                            Double monto = 0.0;
                            cuentasPorCobrarService.cambiarEstadoCtaPorCobrarModificaciones(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(),
                                    idtipoStatusCtaCte, estudianteBean.getPersonaBean().getIdPersona(),
                                    modificacionesBean.getMesInicio(), modificacionesBean.getMesFin(), anio, monto);
                            //Modificando Monto a cero porque seran anulados
                            doc.modificarMontosDocIng(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), estudianteBean.getPersonaBean().getIdPersona(), modificacionesBean.getMesInicio(),
                                    modificacionesBean.getMesFin(), anio, monto, matriculaSeccion.getGradoAcademicoBean().getIdGradoAcademico());
                        } else {
                            List<Integer> listaIdTipoStatusCtaCte = cuentasPorCobrarService.obtenerCtasXCobrarModificaciones(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, estudianteBean.getPersonaBean().getIdPersona(), modificacionesBean.getMesInicio(), modificacionesBean.getMesFin());
                            for (Integer listaId : listaIdTipoStatusCtaCte) {
                                if (listaId.equals(19406)) {
                                    idtipoStatusCtaCte = 19401;
                                    Double monto = cuentasPorCobrarService.validarMonto(estudianteBean.getPersonaBean().getIdPersona(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
                                    cuentasPorCobrarService.cambiarEstadoCtaPorCobrarModificaciones(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(),
                                            idtipoStatusCtaCte, estudianteBean.getPersonaBean().getIdPersona(),
                                            modificacionesBean.getMesInicio(), modificacionesBean.getMesFin(), anio, monto);
                                    //Modificando Monto a cero porque seran anulados
                                    doc.modificarMontosDocIng(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), estudianteBean.getPersonaBean().getIdPersona(), modificacionesBean.getMesInicio(),
                                            modificacionesBean.getMesFin(), anio, monto, matriculaSeccion.getGradoAcademicoBean().getIdGradoAcademico());
                                }
                            }
                        }
                        insertarModificaciones();
                    } else {
                        new MensajePrime().addInformativeMessagePer("Responder si desea Poner los recibos sin servicio?.");
                    }
                }

//                } else {
//                    new MensajePrime().addInformativeMessagePer("No dejar los rangos de mese sin dato.");
//                }
            } else {
                new MensajePrime().addInformativeMessagePer("No dejar los campos de Grado, sección, responsable "
                        + "econommico y el tipo de status sin llenar.");
            }
//            } else {
//                new MensajePrime().addInformativeMessagePer("No tiene ningun recibo generado, genere los recibos correspondientes");
//            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void insertarModificaciones() {
        try {
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            if (estudianteBean.getGradoHabilitado().getIdGradoAcademico() != null) {
                ModificacionesBean modi = new ModificacionesBean();
                modi.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                modi.setNombreObjeto("GradoAcademico");
                modi.setRspObjeto(estudianteBean.getGradoHabilitado().getNombre());
                modi.setMesInicio(modificacionesBean.getMesInicio());
                modi.setMesFin(modificacionesBean.getMesFin());
                modi.setCreaPor(usuarioLogin.getUsuario());
                modi.getEstudianteBean().getPersonaBean().setIdPersona(estudianteBean.getPersonaBean().getIdPersona());
                cuentasPorCobrarService.insertarModificaciones(modi);
            }
            if (matriculaSeccion.getSeccion() != null) {
                ModificacionesBean modi = new ModificacionesBean();
                modi.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                modi.setNombreObjeto("Sección");
                modi.setRspObjeto(matriculaSeccion.getSeccion());
                modi.setMesInicio(modificacionesBean.getMesInicio());
                modi.setMesFin(modificacionesBean.getMesFin());
                modi.setCreaPor(usuarioLogin.getUsuario());
                modi.getEstudianteBean().getPersonaBean().setIdPersona(estudianteBean.getPersonaBean().getIdPersona());
                cuentasPorCobrarService.insertarModificaciones(modi);
            }
            if (estudianteBean.getTipoRespPago().getIdCodigo() != null) {
                ModificacionesBean modi = new ModificacionesBean();
                modi.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                modi.setNombreObjeto("Responsable Economico");
                modi.setRspObjeto(estudianteBean.getTipoRespPago().getCodigo());
                modi.setMesInicio(modificacionesBean.getMesInicio());
                modi.setMesFin(modificacionesBean.getMesFin());
                modi.setCreaPor(usuarioLogin.getUsuario());
                modi.getEstudianteBean().getPersonaBean().setIdPersona(estudianteBean.getPersonaBean().getIdPersona());
                cuentasPorCobrarService.insertarModificaciones(modi);
            }
            if (estudianteBean.getTipoStatusEst().getIdCodigo() != null) {
                ModificacionesBean modi = new ModificacionesBean();
                modi.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                modi.setNombreObjeto("Estado estudiante");
                modi.setRspObjeto(estudianteBean.getTipoStatusEst().getCodigo());
                modi.setMesInicio(modificacionesBean.getMesInicio());
                modi.setMesFin(modificacionesBean.getMesFin());
                modi.setCreaPor(usuarioLogin.getUsuario());
                modi.getEstudianteBean().getPersonaBean().setIdPersona(estudianteBean.getPersonaBean().getIdPersona());
                cuentasPorCobrarService.insertarModificaciones(modi);
            }
            if (estudianteBean.getFechaStatusEst() != null) {
                ModificacionesBean modi = new ModificacionesBean();
                modi.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                modi.setNombreObjeto("Fecha Estado estudiante");
                modi.setRspObjeto(estudianteBean.getFechaStatusEst().toString());
                modi.setMesInicio(modificacionesBean.getMesInicio());
                modi.setMesFin(modificacionesBean.getMesFin());
                modi.setCreaPor(usuarioLogin.getUsuario());
                modi.getEstudianteBean().getPersonaBean().setIdPersona(estudianteBean.getPersonaBean().getIdPersona());
                cuentasPorCobrarService.insertarModificaciones(modi);
            }
            if (estudianteBean.getMotivoStatusEst() != null) {
                ModificacionesBean modi = new ModificacionesBean();
                modi.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                modi.setNombreObjeto("Motivo Estado estudiante");
                modi.setRspObjeto(estudianteBean.getMotivoStatusEst());
                modi.setMesInicio(modificacionesBean.getMesInicio());
                modi.setMesFin(modificacionesBean.getMesFin());
                modi.setCreaPor(usuarioLogin.getUsuario());
                modi.getEstudianteBean().getPersonaBean().setIdPersona(estudianteBean.getPersonaBean().getIdPersona());
                cuentasPorCobrarService.insertarModificaciones(modi);
            }
            listaCtaPorCobrarRepBean = cuentasPorCobrarService.obtenerEstadoCtaPorAnio(estudianteBean.getPersonaBean().getIdPersona(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarModificaciones() {
        try {
            cargarFormularioModificaciones();
            estudianteBean = new EstudianteBean();
            listaCtaPorCobrarRepBean = new ArrayList<>();
//        modificacionesBean = new ModificacionesBean();
            Calendar miCalendario = Calendar.getInstance();
            anio = miCalendario.get(Calendar.YEAR);
            listFamiliarEst = new ArrayList<>();
            matriculaSeccion = new MatriculaBean();
            flgModificacionRecibo = null;
            flgStatusActivo = false;
            matriculaFiltroBean = new MatriculaBean();
            listaEstudianteBean = new ArrayList<>();
            flgActivarBuscador = false;
        } catch (Exception e) {
        }
    }

    public void cargarDatosDocIng() {
        try {
            listaGradoAcademicoBean = new ArrayList<>();
            String uniNeg = usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            CodigoService codigoService = BeanFactory.getCodigoService();

            listaTipoStatusDocIng = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_STATUS_DOCING));
            listaTipoDocumento = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_DOCUMENTO));
            listaTipoModoPago = codigoService.obtenerCodigoDocIngreso();
            listaTipoLugarPago = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_LUG_PAGO));
            GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
            listaGradoAcademicoBean = gradoAcademicoService.obtenerTodosMatri();

            //detalle
            CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoUniNegPorIng(uniNeg);
            listaCrBean = centroResponsabilidadService.obtenerCentroResponsabilidad();
            listaTipoDscto = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_DSCTO));
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerGradoAcaPorConcepto(Integer idConcepto) {
        try {
            Integer id = null;
            System.out.println("id  con " + idConcepto);
            ConceptoService conceptoService = BeanFactory.getConceptoService();
            id = conceptoService.obtenerGradoAcaPorIdConcepto(idConcepto);
            System.out.println("id  gra " + id);

            if (id != null) {
                GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
                GradoAcademicoBean grado = new GradoAcademicoBean();
                grado.setIdGradoAcademico(id);
                grado = gradoAcademicoService.obtenerPorId(grado);
                if (grado != null) {
                    cuentasPorCobrarBean.getMatriculaBean().setGradoAcademicoBean(grado);
                }
            } else {
                cuentasPorCobrarBean.getConceptoBean().setIdConcepto(idConcepto);
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerCuentaCtePorId(Integer id) {
        try {
            cargarDatosCtaxCobrar();
            System.out.println("id: " + id);
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            cuentasPorCobrarBean = cuentasPorCobrarService.obtenerCuentaPorId(id, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            DocIngresoBean doc = new DocIngresoBean();
            doc = docIngresoService.obtenerDocIngresoPorIdFull(cuentasPorCobrarBean.getDocIngresoBean().getIdDocIngreso(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (doc != null) {
                DetDocIngresoBean det = new DetDocIngresoBean();
                det = docIngresoService.obtenerDetDocIngPorDocIngreso(cuentasPorCobrarBean.getDocIngresoBean().getIdDocIngreso(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                if (det != null) {
                    detDocIngresoBean = det;
                    detDocIngresoBean.setDocIngresoBean(doc);
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void verHistoriaArqueosGeneral(String tipo) {
        try {
            if (tipo == null) {
                System.out.println("caja null->" + this.cajaGen);
            } else {
                this.cajaGen = tipo;
                System.out.println("caja ->" + this.cajaGen);
            }
            limpiarCajaGen();
            if (detDocIngresoBean.getDocIngresoBean().getCajaGenBean().getFechaInicio() != null) {
                Timestamp t = new Timestamp(detDocIngresoBean.getDocIngresoBean().getCajaGenBean().getFechaInicio().getTime());
                t.setHours(0);
                t.setMinutes(0);
                t.setSeconds(0);
                detDocIngresoBean.getDocIngresoBean().getCajaGenBean().setFechaInicio(t);
            }
            if (detDocIngresoBean.getDocIngresoBean().getCajaGenBean().getFechaFin() != null) {
                Timestamp u = new Timestamp(detDocIngresoBean.getDocIngresoBean().getCajaGenBean().getFechaFin().getTime());
                u.setHours(23);
                u.setMinutes(59);
                u.setSeconds(59);
                detDocIngresoBean.getDocIngresoBean().getCajaGenBean().setFechaFin(u);
            }
            CajaGenService cajaGenService = BeanFactory.getCajaGenService();
            listaCajaGenBean = cajaGenService.obtenerCierresCajaPorCajero(null, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), detDocIngresoBean.getDocIngresoBean().getCajaGenBean().getFechaInicio(), detDocIngresoBean.getDocIngresoBean().getCajaGenBean().getFechaFin());
            if (listaCajaGenBean.isEmpty()) {
                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
            }
//            else {
//                for (CajaGenBean lista : listaCajaGenBean) {
//                    lista.setVerArqueo(Boolean.TRUE);
//                }
//            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void verHistoriaArqueosGeneral2(String tipo) {
        try {
            if (tipo == null) {
                System.out.println("caja 22->" + this.cajaGen);
            } else {
                System.out.println("caja 11->" + this.cajaGen);
            }
//            listaCajaGenBean = new ArrayList<>();
//            Calendar fechaActual = new GregorianCalendar();
//            if (detDocIngresoBean.getDocIngresoBean().getCajaGenBean().getFechaInicio() == null) {
//                getDetDocIngresoBean().getDocIngresoBean().getCajaGenBean().setFechaInicio(fechaActual.getTime());
//
//            }
//            if (detDocIngresoBean.getDocIngresoBean().getCajaGenBean().getFechaFin() == null) {
//                getDetDocIngresoBean().getDocIngresoBean().getCajaGenBean().setFechaFin(fechaActual.getTime());
//            }
            if (detDocIngresoBean.getDocIngresoBean().getCajaGenBean().getFechaInicio() != null) {
                Timestamp t = new Timestamp(detDocIngresoBean.getDocIngresoBean().getCajaGenBean().getFechaInicio().getTime());
                t.setHours(0);
                t.setMinutes(0);
                t.setSeconds(0);
                detDocIngresoBean.getDocIngresoBean().getCajaGenBean().setFechaInicio(t);
            }
            if (detDocIngresoBean.getDocIngresoBean().getCajaGenBean().getFechaFin() != null) {
                Timestamp u = new Timestamp(detDocIngresoBean.getDocIngresoBean().getCajaGenBean().getFechaFin().getTime());
                u.setHours(23);
                u.setMinutes(59);
                u.setSeconds(59);
                detDocIngresoBean.getDocIngresoBean().getCajaGenBean().setFechaFin(u);
            }
            CajaGenService cajaGenService = BeanFactory.getCajaGenService();
            listaCajaGenBean = cajaGenService.obtenerCierresCajaPorCajero(null, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), detDocIngresoBean.getDocIngresoBean().getCajaGenBean().getFechaInicio(), detDocIngresoBean.getDocIngresoBean().getCajaGenBean().getFechaFin());
            if (listaCajaGenBean.isEmpty()) {
                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
            }
//            else {
//                for (CajaGenBean lista : listaCajaGenBean) {
//                    lista.setVerArqueo(Boolean.TRUE);
//                }
//            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarCajaGen() {
        try {
            listaCajaGenBean = new ArrayList<>();
            Calendar fechaActual = new GregorianCalendar();
            getDetDocIngresoBean().getDocIngresoBean().getCajaGenBean().setFechaInicio(fechaActual.getTime());
            getDetDocIngresoBean().getDocIngresoBean().getCajaGenBean().setFechaFin(fechaActual.getTime());

        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerDocIngresoPorId(Integer id, Integer idDocIng) {
        try {
            detDocIngresoBean = new DetDocIngresoBean();
            cargarDatosDocIng();
            System.out.println("id cta: " + id);
            System.out.println("id doc: " + idDocIng);
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            cuentasPorCobrarBean = cuentasPorCobrarService.obtenerCuentaPorId(id, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            DocIngresoBean doc = new DocIngresoBean();
            if (idDocIng == null) {
                System.out.println("null idDocIng");
            } else {
                doc = docIngresoService.obtenerDocIngresoPorIdFull(idDocIng, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                if (doc != null) {
                    DetDocIngresoBean det = new DetDocIngresoBean();
                    det = docIngresoService.obtenerDetDocIngPorDocIngreso(idDocIng, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    if (det != null) {
                        detDocIngresoBean = det;
                        detDocIngresoBean.setDocIngresoBean(doc);
                    }
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void nullearFecha(String tipo) {
        try {
            if (tipo.equals("pago")) {
                if (cuentasPorCobrarBean != null) {
                    cuentasPorCobrarBean.setFechaPago(null);
                }
            }
            if (tipo.equals("venc")) {
                if (cuentasPorCobrarBean != null) {
                    cuentasPorCobrarBean.setFechaVenc(null);
                }
            }
            if (tipo.equals("dscto")) {
                if (cuentasPorCobrarBean != null) {
                    cuentasPorCobrarBean.setDsctoFecha(null);
                }
            }
            if (tipo.equals("bco")) {
                if (cuentasPorCobrarBean != null) {
                    cuentasPorCobrarBean.setIdProcesoBanco(null);
                    cuentasPorCobrarBean.setNombreProcesoBanco(null);
                }
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void nullearFechaDocIngreso(String tipo) {
        try {
            if (tipo.equals("pago")) {
                if (detDocIngresoBean != null) {
                    detDocIngresoBean.getDocIngresoBean().setFechaPago(null);
                }
            }
            if (tipo.equals("est")) {
                if (detDocIngresoBean != null) {
                    detDocIngresoBean.getDocIngresoBean().setCreaFechaStatus(null);
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void nullearCajaDocIngreso(String tipo) {
        try {
            if (tipo.equals("cajaGen")) {
                if (detDocIngresoBean != null) {
                    detDocIngresoBean.getDocIngresoBean().getCajaGenBean().setIdCajaGen(null);
                    detDocIngresoBean.getDocIngresoBean().getCajaGenBean().getCajaBean().setIdCaja(null);
                    detDocIngresoBean.getDocIngresoBean().getCajaGenBean().getCajaBean().setNombre(null);
                    detDocIngresoBean.getDocIngresoBean().getCajaGenBean().setFechaAperturaView("  /  /  ");
                }
            }
            if (tipo.equals("cajaGenAnulado")) {
                if (detDocIngresoBean != null) {
                    detDocIngresoBean.getDocIngresoBean().setIdCajaGenAnulado(null);
                    detDocIngresoBean.getDocIngresoBean().setNombreCajaAnulado(null);
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerProcesoBco() {
        try {
            listaProcesoBancoBean = new ArrayList();
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            ProcesoBancoBean procesoBancoFiltroBean = new ProcesoBancoBean();
            procesoBancoFiltroBean.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            procesoBancoFiltroBean.setFlgProceso(0);
            listaProcesoBancoBean = procesoBancoService.filtrarProceso(procesoBancoFiltroBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ponerProcesoBcoEnCta(ProcesoBancoBean proceso) {
        try {
            cuentasPorCobrarBean.setIdProcesoBanco(proceso.getIdProcesoBanco());
            cuentasPorCobrarBean.setNombreProcesoBanco(proceso.getNombre());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ponerCajaGenEnDoc(CajaGenBean cajaGenBean) {
        try {
            if (this.cajaGen != null) {
                System.out.println("caja ->" + this.cajaGen);
                if (this.cajaGen.equals("cajaGen")) {
                    detDocIngresoBean.getDocIngresoBean().setCajaGenBean(cajaGenBean);
                    detDocIngresoBean.getDocIngresoBean().setFlgCajaGenNull(null);
                    System.out.println("idcajagen2 " + detDocIngresoBean.getDocIngresoBean().getCajaGenBean().getIdCajaGen());
                    System.out.println("idcajagen2 " + detDocIngresoBean.getDocIngresoBean().getCajaGenBean().getCajaBean().getIdCaja());
                }
                if (this.cajaGen.equals("cajaGenAnulado")) {
                    detDocIngresoBean.getDocIngresoBean().setIdCajaGenAnulado(cajaGenBean.getIdCajaGen());
                    detDocIngresoBean.getDocIngresoBean().setNombreCajaAnulado(cajaGenBean.getCajaBean().getNombre());
                    detDocIngresoBean.getDocIngresoBean().setFlgCajaGenAnuladoNull(null);
                    System.out.println("idcajagen3 " + detDocIngresoBean.getDocIngresoBean().getIdCajaGenAnulado());
                    System.out.println("idcajagen3 " + detDocIngresoBean.getDocIngresoBean().getNombreCajaAnulado());
                }
            } else {
                System.out.println("null this.cajaGen");
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void imprimirEstadoCtaCtePdf(String idEstudiante) {
        ServletOutputStream out = null;
        try {
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/estadoCuentaEstudiante.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            List<CuentasPorCobrarRepBean> lista = new ArrayList<>();
            lista = cuentasPorCobrarService.obtenerEstadoCtaPorAnio(idEstudiante, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);

            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(lista);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            out = response.getOutputStream();
            out.write(bytes);
            out.flush();

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception ettt) {
                new MensajePrime().addErrorGeneralMessage();
                GLTLog.writeError(this.getClass(), ettt);
            }
        }
        // Inform JSF that it doesn't need to handle response.
        // This is very important, otherwise you will get the following exception in the logs:
        // java.lang.IllegalStateException: Cannot forward after response has been committed.
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void imprimirPensionesPorCobrarLPMPdf() {
        ServletOutputStream out = null;
        try {
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reportePensionesPorCobrar.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            List<PensionesPorCobrarLPMRepBean> lista = new ArrayList<>();
            lista = cuentasPorCobrarService.obtenerPenionesPorCobrarLPM(mesSelect, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, fechaInicio, mesSelectFin);

            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(lista);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=Reporte_Pensiones_Por_Cobrar_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".pdf");
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            out = response.getOutputStream();
            out.write(bytes);
            out.flush();

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception ettt) {
                new MensajePrime().addErrorGeneralMessage();
                GLTLog.writeError(this.getClass(), ettt);
            }
        }
        // Inform JSF that it doesn't need to handle response.
        // This is very important, otherwise you will get the following exception in the logs:
        // java.lang.IllegalStateException: Cannot forward after response has been committed.
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void imprimirPensionesPagadasLPMPdf() {
        ServletOutputStream out = null;
        try {
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reportePensionesPagadasPDF.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            List<PensionesPorCobrarLPMRepBean> lista = new ArrayList<>();
            lista = cuentasPorCobrarService.obtenerPenionesPagadasLPM(mesSelect, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, fechaInicio, mesSelectFin);

            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(lista);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=Reporte_Pensiones_Pagadas_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".pdf");
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            out = response.getOutputStream();
            out.write(bytes);
            out.flush();

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception ettt) {
                new MensajePrime().addErrorGeneralMessage();
                GLTLog.writeError(this.getClass(), ettt);
            }
        }
        // Inform JSF that it doesn't need to handle response.
        // This is very important, otherwise you will get the following exception in the logs:
        // java.lang.IllegalStateException: Cannot forward after response has been committed.
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void imprimirEstadoCtaCteGeneralPdf() {
        ServletOutputStream out = null;
        try {
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            CronogramaPagoService cronogramaPagoService = BeanFactory.getCronogramaPagoService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteInformeEstadoCta.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            List<EstadoCtaRepBean> listaCabecera = new ArrayList<>();
            listaCabecera = cuentasPorCobrarService.ObtenerInformeEstadoCtaGen(null, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);

            if (!listaCabecera.isEmpty()) {
                for (int i = 0; i < listaCabecera.size(); i++) {
//                    System.out.println("saldo: " + listaCabecera.get(i).getSaldo());
                    List<CronogramaPagoRepBean> listaMesesRep = new ArrayList<>();
                    listaMesesRep = cronogramaPagoService.obtenerCronogramaAnioRep(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
                    listaCabecera.get(0).setListaMeses(listaMesesRep);
                    if (!listaMesesRep.isEmpty()) {
                        for (int j = 0; j < listaCabecera.get(0).getListaMeses().getData().size(); j++) {
                            List<DetEstadoCtaRepBean> listaDetEstadoCtaRep = new ArrayList<>();
                            listaDetEstadoCtaRep = cuentasPorCobrarService.ObtenerInformeEstadoCtaGenPorMes((listaMesesRep.get(j).getMes()), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
                            listaMesesRep.get(j).setListaDetalle(listaDetEstadoCtaRep);
                            listaCabecera.get(0).setListaMeses(listaMesesRep);
                        }
                    }
                }
            }

            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCabecera);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            out = response.getOutputStream();
            out.write(bytes);
            out.flush();

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception ettt) {
                new MensajePrime().addErrorGeneralMessage();
                GLTLog.writeError(this.getClass(), ettt);
            }
        }
        // Inform JSF that it doesn't need to handle response.
        // This is very important, otherwise you will get the following exception in the logs:
        // java.lang.IllegalStateException: Cannot forward after response has been committed.
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void imprimirEstadoCtaCteGeneralRangoFechaPdf() {
        ServletOutputStream out = null;
        try {
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            CronogramaPagoService cronogramaPagoService = BeanFactory.getCronogramaPagoService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteInformeEstadoCta.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            List<EstadoCtaRepBean> listaCabecera = new ArrayList<>();
            listaCabecera = cuentasPorCobrarService.ObtenerInformeEstadoCtaGenRangoFecha(null, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, fechaCorte);

            if (!listaCabecera.isEmpty()) {
                for (int i = 0; i < listaCabecera.size(); i++) {
//                    System.out.println("saldo: " + listaCabecera.get(i).getSaldo());
                    List<CronogramaPagoRepBean> listaMesesRep = new ArrayList<>();
                    listaMesesRep = cronogramaPagoService.obtenerCronogramaAnioRep(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
                    listaCabecera.get(0).setListaMeses(listaMesesRep);
                    if (!listaMesesRep.isEmpty()) {
                        for (int j = 0; j < listaCabecera.get(0).getListaMeses().getData().size(); j++) {
                            List<DetEstadoCtaRepBean> listaDetEstadoCtaRep = new ArrayList<>();
                            listaDetEstadoCtaRep = cuentasPorCobrarService.ObtenerInformeEstadoCtaGenPorMesRangoFecha((listaMesesRep.get(j).getMes()), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, fechaCorte);
                            listaMesesRep.get(j).setListaDetalle(listaDetEstadoCtaRep);
                            listaCabecera.get(0).setListaMeses(listaMesesRep);
                        }
                    }
                }
            }

            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCabecera);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            out = response.getOutputStream();
            out.write(bytes);
            out.flush();

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception ettt) {
                new MensajePrime().addErrorGeneralMessage();
                GLTLog.writeError(this.getClass(), ettt);
            }
        }
        // Inform JSF that it doesn't need to handle response.
        // This is very important, otherwise you will get the following exception in the logs:
        // java.lang.IllegalStateException: Cannot forward after response has been committed.
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void obtenerFiltroResumenIngresos() {
        try {
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();

            List<ResumenIngRepBean> listaCabecera = new ArrayList<>();
            listaDetResumenIngRep = new ArrayList<>();
            listaCabecera = cuentasPorCobrarService.obtenerResumenIngPorAnio(mesSelect, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, idTipoLugarPago);
            if (!listaCabecera.isEmpty()) {
                for (int i = 0; i < listaCabecera.size(); i++) {
                    List<DetDiaResumenIngRepBean> listaDias = new ArrayList<>();
                    Integer ultDia = cuentasPorCobrarService.obtenerUltimoDiaMes(mesSelect, anio);
                    for (int d = 1; d <= ultDia; d++) {
                        DetDiaResumenIngRepBean det = new DetDiaResumenIngRepBean();
                        det.setDia(d);
                        det.setAnioAnterior(anio - 1);
                        det.setAnioAnteriorAnt(anio - 2);
                        listaDias.add(det);
                    }
                    listaCabecera.get(0).setListaDias(listaDias);
                    if (!listaDias.isEmpty()) {
                        for (int j = 0; j < listaCabecera.get(0).getListaDias().getData().size(); j++) {
                            List<DetResumenIngRepBean> listaDetResumenIngRepBean = new ArrayList<>();
                            listaDetResumenIngRepBean = cuentasPorCobrarService.obtenerResumenIngPorAnioPorDiaExcel((listaDias.get(j).getDia()), mesSelect, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, idTipoLugarPago);
                            listaDias.get(j).setListaDetalle(listaDetResumenIngRepBean);
                            listaCabecera.get(0).setListaDias(listaDias);
                            listaDetResumenIngRep.add(listaDetResumenIngRepBean.get(0));
                        }
                    }
                }
            }
            for (DetResumenIngRepBean l : listaDetResumenIngRep) {
                System.out.println("listaaaa " + l.getAnioAnterior());
                System.out.println("listaaaa " + l.getAnioAnteriorAnt());
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void imprimirResumenIngresos() {
        ServletOutputStream out = null;
        try {
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteResumenIngPorLugarPago.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            List<ResumenIngRepBean> listaCabecera = new ArrayList<>();
            listaCabecera = cuentasPorCobrarService.obtenerResumenIngPorAnio(mesSelect, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, idTipoLugarPago);
            if (!listaCabecera.isEmpty()) {
                for (int i = 0; i < listaCabecera.size(); i++) {
                    List<DetDiaResumenIngRepBean> listaDias = new ArrayList<>();
                    Integer ultDia = cuentasPorCobrarService.obtenerUltimoDiaMes(mesSelect, anio);
                    for (int d = 1; d <= ultDia; d++) {
                        DetDiaResumenIngRepBean det = new DetDiaResumenIngRepBean();
                        det.setDia(d);
                        det.setAnioAnterior(anio - 1);
                        det.setAnioAnteriorAnt(anio - 2);
                        listaDias.add(det);
                    }
                    listaCabecera.get(0).setListaDias(listaDias);
                    if (!listaDias.isEmpty()) {
                        for (int j = 0; j < listaCabecera.get(0).getListaDias().getData().size(); j++) {
                            List<DetResumenIngRepBean> listaDetResumenIngRepBean = new ArrayList<>();
                            listaDetResumenIngRepBean = cuentasPorCobrarService.obtenerResumenIngPorAnioPorDia((listaDias.get(j).getDia()), mesSelect, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, idTipoLugarPago);
                            listaDias.get(j).setListaDetalle(listaDetResumenIngRepBean);
                            listaCabecera.get(0).setListaDias(listaDias);
                        }
                    }
                }
            }
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCabecera);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            out = response.getOutputStream();
            out.write(bytes);
            out.flush();

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception ettt) {
                new MensajePrime().addErrorGeneralMessage();
                GLTLog.writeError(this.getClass(), ettt);
            }
        }
        // Inform JSF that it doesn't need to handle response.
        // This is very important, otherwise you will get the following exception in the logs:
        // java.lang.IllegalStateException: Cannot forward after response has been committed.
        FacesContext.getCurrentInstance().responseComplete();
    }

//    public void convertirStrFechaSaldoPension() {
//        try {
//            if (fechaInicio != null) {
//                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//                String date = sdf.format(fechaInicio);
//                imprimirSaldoPensiones(date);
//            }
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//    }
    public void imprimirSaldoPensiones(Date fecha) {

        ServletOutputStream out = null;
        try {
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteSaldosPensiones.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            Integer idNivel = 0;
            Integer idGrado = 0;
            String secc = "";
            if (getMatriculaFiltroBean().getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico() != null) {
                idNivel = getMatriculaFiltroBean().getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico();
            } else {
                idNivel = null;
            }
            if (getMatriculaFiltroBean().getGradoAcademicoBean().getIdGradoAcademico() != null) {
                idGrado = getMatriculaFiltroBean().getGradoAcademicoBean().getIdGradoAcademico();
            } else {
                idGrado = null;
            }
            if (getMatriculaFiltroBean().getSeccion() != null) {
                secc = getMatriculaFiltroBean().getSeccion();
            } else {
                secc = null;
            }
            System.out.println("idNivel " + idNivel);
            System.out.println("idGrado " + idGrado);
            System.out.println("secc " + secc);
            List<SaldoPensionesRepBean> listaCabecera = new ArrayList<>();
            listaCabecera = cuentasPorCobrarService.obtenerSaldoPenionesPorFecha(0, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, 19401, null, fecha, this.mesSelect, idNivel, idGrado, "", secc);
            if (!listaCabecera.isEmpty()) {
                for (int i = 0; i < listaCabecera.size(); i++) {
                    System.out.println("cabecera " + listaCabecera.size());
                    List<EstudianteSaldoRepBean> listaEstudiantes = new ArrayList<>();
                    listaEstudiantes = cuentasPorCobrarService.obtenerEstudianteConSaldoPorFecha(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, 19401, fecha, this.mesSelect, idNivel, idGrado, secc);
                    listaCabecera.get(0).setListaEstudiantesSaldo(listaEstudiantes);
                    System.out.println("cantidad de est: " + listaEstudiantes.size());
                    System.out.println("cantidad de est: " + listaCabecera.get(0).getListaEstudiantesSaldo().getData().size());
                    if (!listaEstudiantes.isEmpty()) {

                        for (int j = 0; j < listaCabecera.get(0).getListaEstudiantesSaldo().getData().size(); j++) {
                            System.out.println("NRO: " + j);
                            List<SaldoPensionesRepBean> listaDetSaldoPensionesRepBean = new ArrayList<>();
                            listaDetSaldoPensionesRepBean = cuentasPorCobrarService.obtenerSaldoPenionesPorFecha(1, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, 19401, listaEstudiantes.get(j).getIdestudiante(), fecha, this.mesSelect, null, null, String.valueOf(j + 1), null);
                            listaEstudiantes.get(j).setListaDetalle(listaDetSaldoPensionesRepBean);
                            listaCabecera.get(0).setListaEstudiantesSaldo(listaEstudiantes);
                        }
                    }
                }
            }
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCabecera);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            response.setHeader("Content-Disposition", "inline; filename=SaldoPensiones_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".pdf");
            response.setHeader("Cache-Control", "cache, must-revalidate");
            response.setHeader("Pragma", "public");
            out = response.getOutputStream();
            out.write(bytes);
            out.flush();

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception ettt) {
                new MensajePrime().addErrorGeneralMessage();
                GLTLog.writeError(this.getClass(), ettt);
            }
        }
        // Inform JSF that it doesn't need to handle response.
        // This is very important, otherwise you will get the following exception in the logs:
        // java.lang.IllegalStateException: Cannot forward after response has been committed.
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void imprimirSaldoPensionesPivot(Date fecha) {

        ServletOutputStream out = null;
        try {
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteSaldoPensionesConPivot.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            Integer idNivel = 0;
            Integer idGrado = 0;
            String secc = "";
            if (getMatriculaFiltroBean().getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico() != null) {
                idNivel = getMatriculaFiltroBean().getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico();
            } else {
                idNivel = null;
            }
            if (getMatriculaFiltroBean().getGradoAcademicoBean().getIdGradoAcademico() != null) {
                idGrado = getMatriculaFiltroBean().getGradoAcademicoBean().getIdGradoAcademico();
            } else {
                idGrado = null;
            }
            if (getMatriculaFiltroBean().getSeccion() != null) {
                secc = getMatriculaFiltroBean().getSeccion();
            } else {
                secc = null;
            }
            System.out.println("idNivel " + idNivel);
            System.out.println("idGrado " + idGrado);
            System.out.println("secc " + secc);
            List<EstudianteSaldoPivotRepBean> listaCabecera = new ArrayList<>();
            List<String> listaIds = new ArrayList<>();
            List<EstudianteSaldoRepBean> listaEstudiantes = new ArrayList<>();
            listaEstudiantes = cuentasPorCobrarService.obtenerEstudianteConSaldoPorFecha(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, 19401, fecha, this.mesSelect, idNivel, idGrado, secc);
            for (int k = 0; k < listaEstudiantes.size(); k++) {
                listaIds.add(listaEstudiantes.get(k).getIdestudiante());
            }
            listaCabecera = cuentasPorCobrarService.obtenerSaldoPenionesPorFechaPivot(0, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, 19401, listaIds, fecha, this.mesSelect, idNivel, idGrado, secc);
            System.out.println("ss: " + listaCabecera.size());
            if (!listaEstudiantes.isEmpty()) {
                for (int j = 0; j < listaCabecera.size(); j++) {
                    System.out.println("NRO: " + j);
                    List<EstudianteSaldoPivotRepBean> listaDetSaldoPensionesRepBean = new ArrayList<>();
                    listaDetSaldoPensionesRepBean = cuentasPorCobrarService.obtenerSaldoPenionesPorFechaPivot(1, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, 19401, listaIds, fecha, this.mesSelect, null, null, null);
                    listaCabecera.get(0).setListaEstudiantesSaldo(listaDetSaldoPensionesRepBean);
                }
            }
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCabecera);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            response.setHeader("Content-Disposition", "inline; filename=SaldoPensiones_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".pdf");
            response.setHeader("Cache-Control", "cache, must-revalidate");
            response.setHeader("Pragma", "public");
            out = response.getOutputStream();
            out.write(bytes);
            out.flush();

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception ettt) {
                new MensajePrime().addErrorGeneralMessage();
                GLTLog.writeError(this.getClass(), ettt);
            }
        }
        // Inform JSF that it doesn't need to handle response.
        // This is very important, otherwise you will get the following exception in the logs:
        // java.lang.IllegalStateException: Cannot forward after response has been committed.
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void imprimirSaldoPensionesLetra(Date fecha) {

        ServletOutputStream out = null;
        try {
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteSaldoPensionesConPivotLetra.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            Integer idNivel = 0;
            Integer idGrado = 0;
            String secc = "";
            if (getMatriculaFiltroBean().getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico() != null) {
                idNivel = getMatriculaFiltroBean().getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico();
            } else {
                idNivel = null;
            }
            if (getMatriculaFiltroBean().getGradoAcademicoBean().getIdGradoAcademico() != null) {
                idGrado = getMatriculaFiltroBean().getGradoAcademicoBean().getIdGradoAcademico();
            } else {
                idGrado = null;
            }
            if (getMatriculaFiltroBean().getSeccion() != null) {
                secc = getMatriculaFiltroBean().getSeccion();
            } else {
                secc = null;
            }
            System.out.println("idNivel " + idNivel);
            System.out.println("idGrado " + idGrado);
            System.out.println("secc " + secc);
            List<EstudianteSaldoPivotRepBean> listaCabecera = new ArrayList<>();
            List<String> listaIds = new ArrayList<>();
            List<EstudianteSaldoRepBean> listaEstudiantes = new ArrayList<>();
            listaEstudiantes = cuentasPorCobrarService.obtenerEstudianteConSaldoPorFecha(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, 19401, fecha, this.mesSelect, idNivel, idGrado, secc);
            for (int k = 0; k < listaEstudiantes.size(); k++) {
                listaIds.add(listaEstudiantes.get(k).getIdestudiante());
            }
            listaCabecera = cuentasPorCobrarService.obtenerSaldoPenionesPorFechaPivotLetra(0, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, 19401, listaIds, fecha, this.mesSelect, idNivel, idGrado, secc);
            System.out.println("ss: " + listaCabecera.size());
            if (!listaEstudiantes.isEmpty()) {
                for (int j = 0; j < listaCabecera.size(); j++) {
                    System.out.println("NRO: " + j);
                    List<EstudianteSaldoPivotRepBean> listaDetSaldoPensionesRepBean = new ArrayList<>();
                    listaDetSaldoPensionesRepBean = cuentasPorCobrarService.obtenerSaldoPenionesPorFechaPivotLetra(1, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, 19401, listaIds, fecha, this.mesSelect, null, null, null);
                    listaCabecera.get(0).setListaEstudiantesSaldo(listaDetSaldoPensionesRepBean);
                }
            }

            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCabecera);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            response.setHeader("Content-Disposition", "inline; filename=SaldoPensiones_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".pdf");
            response.setHeader("Cache-Control", "cache, must-revalidate");
            response.setHeader("Pragma", "public");
            out = response.getOutputStream();
            out.write(bytes);
            out.flush();

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception ettt) {
                new MensajePrime().addErrorGeneralMessage();
                GLTLog.writeError(this.getClass(), ettt);
            }
        }
        // Inform JSF that it doesn't need to handle response.
        // This is very important, otherwise you will get the following exception in the logs:
        // java.lang.IllegalStateException: Cannot forward after response has been committed.
        FacesContext.getCurrentInstance().responseComplete();
    }

//    public void imprimirExcel() {
//        ServletOutputStream out = null;
//        Integer id = 0;
//        try {
//
//            UnidadNegocioBean unidadNegocioBean = new UnidadNegocioBean();
//            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
//            OrdenCompraDetalleService ordenCompraDetalleService = BeanFactory.getOrdenCompraDetalleService();
////            id = ordenCompraDetalleService.obtenerUltimo(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            id = ordenCompraDetalleService.obtenerUltimo(ordenCompraBean.getIdOrdenCompra(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            listaOrdenCompraDetalleBean = ordenCompraDetalleService.obtenerPorOrden(ordenCompraBean.getIdOrdenCompra(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
//                    getResponse();
//            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
//                    getRequest()).getServletContext().getRealPath("/reportes/reporteCantratoAdquisicion.jasper");
//            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
//            String absoluteWebPath = externalContext.getRealPath("/");
//            System.out.println("ruta: " + archivoJasper);
//            File file = new File(archivoJasper);
//            List<ContratoAdquisicionRepBean> listaDetContratoBean = new ArrayList<>();
//            listaDetContratoBean = ordenCompraDetalleService.obtenerContratoAdqui(ordenCompraBean.getEntidadBean().getRuc(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), ordenCompraBean.getIdOrdenCompra());
//
//            List<DetContratoAdquisicionRepBean> listaDetDetContratoBean = new ArrayList<>();
//            listaDetDetContratoBean = ordenCompraDetalleService.obtenerDetContratoAdquisicion(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), ordenCompraBean.getIdOrdenCompra());
//            listaDetContratoBean.get(0).setListaContrato(listaDetDetContratoBean);
//            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
//
//            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaDetContratoBean);
//
//            Map<String, Object> parametros = new HashMap<>();
//            String ruta = absoluteWebPath + "reportes\\";
//            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
//            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
//            parametros.put("SUBREPORT_DIR", ruta);
//
//            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
//
//            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, jrbc);
//
//            httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + jasperPrint.getName() + ".docx");
//            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
//
//            JRDocxExporter docxExporter = new JRDocxExporter();
//            docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//            docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
//            docxExporter.setParameter(JRDocxExporterParameter.OUTPUT_STREAM, servletOutputStream);
//            docxExporter.exportReport();
//            FacesContext.getCurrentInstance().responseComplete();
//
////            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
////            response.reset();
////            response.setContentType("application/pdf");
////            response.setContentLength(bytes.length);
////            out = response.getOutputStream();
////            out.write(bytes);
////            out.flush();
////            }
//        } catch (Exception err) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), err);
//        } finally {
//            try {
//                if (out != null) {
//                    out.close();
//                }
//            } catch (Exception ettt) {
//                new MensajePrime().addErrorGeneralMessage();
//                GLTLog.writeError(this.getClass(), ettt);
//            }
//        }
//        // Inform JSF that it doesn't need to handle response.
//        // This is very important, otherwise you will get the following exception in the logs:
//        // java.lang.IllegalStateException: Cannot forward after response has been committed.
//        FacesContext.getCurrentInstance().responseComplete();
//    }
    public void ObtenerReportePorCondicion(Integer tipoRep) {
        try {
            flgTipoReporte = tipoRep;
            if (flgCondicion == 1) {
                if (flgTipoReporte == 1) {
                    imprimirPensionesPorCobrarLPMPdf();
                } else if (flgTipoReporte == 2) {
                    imprimirExcelPensionesPorCobrar();
                }
            } else if (flgCondicion == 2) {
                if (flgTipoReporte == 1) {
                    imprimirPensionesPagadasLPMPdf();
                } else if (flgTipoReporte == 2) {
                    imprimirExcelPensionesPagadas();
                }
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void imrprimirEstadoRecibos() {
        ServletOutputStream out = null;
        Integer id = 0;
        try {
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            CuentasPorCobrarService cuentas = BeanFactory.getCuentasPorCobrarService();
            String uniNeg = usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
//            Date inicio = docEgresoRepFiltroBean.getFechaInicio();
//            Date fin = docEgresoRepFiltroBean.getFechaFin();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteGeneralRecibosPenDeuPa.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            String anioVista = anio.toString();

            List<EstadoRecibosRepBean> listaCabecera = new ArrayList<>();
            listaCabecera = cuentas.obtenerCabeceraEstadoRecibo(anioVista);
            if (!listaCabecera.isEmpty()) {
                List<EstadoRecibosRepBean> listaEstadoRecibos = new ArrayList<>();
                List<EstadoRecibosRepBean> listaEstadoRecibos2 = new ArrayList<>();
//                for (int k = 0; k < listaCabecera.size(); k++) {
                if (!usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SECTOR)) {
                    for (int k = 0; k < listaCabecera.size(); k++) {
                        for (MesBean listaMesess : listaMesAll) {
                            String mes = listaMesess.getValor().toString();
                            listaEstadoRecibos = cuentas.obtenerEstadosRecibos(1, mes, uniNeg, anioVista);
//                        for (EstadoRecibosRepBean estado : listaEstadoRecibos) {
                            if (!listaEstadoRecibos.isEmpty()) {
                                listaEstadoRecibos2.add(listaEstadoRecibos.get(0));
                                listaCabecera.get(k).setListaDetalle(listaEstadoRecibos2);
                            }
//                        }
                        }
                    }
                } else {
                    for (int j = 0; j < listaCabecera.size(); j++) {
                        for (MesBean listaMesess : listaMesAll) {
                            String mes = listaMesess.getValor().toString();
                            listaEstadoRecibos = cuentas.obtenerEstadosRecibos(2, mes, listaCabecera.get(j).getUniNeg(), anioVista);
                            for (EstadoRecibosRepBean estado : listaEstadoRecibos) {
                                listaEstadoRecibos2.add(estado);
                            }
                        }
                        listaCabecera.get(j).setListaDetalle(listaEstadoRecibos2);
                        listaEstadoRecibos2 = new ArrayList<>();
                    }
                }
            }

            for (int g = 0; g < listaCabecera.size(); g++) {
                if (listaCabecera.get(g).getListaDetalle().getData().size() == 0) {
                    listaCabecera.remove(g);
                }
            }
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCabecera);

            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);
            UsuarioBean ub = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            parametros.put("USUARIO", ub.getUsuario());

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            out = response.getOutputStream();
            out.write(bytes);
            out.flush();

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception ettt) {
                new MensajePrime().addErrorGeneralMessage();
                GLTLog.writeError(this.getClass(), ettt);
            }
        }

        FacesContext.getCurrentInstance()
                .responseComplete();
    }

    public void imrprimirResultadoMatricula() {
        ServletOutputStream out = null;
        Integer id = 0;
        try {
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            CuentasPorCobrarService cuentas = BeanFactory.getCuentasPorCobrarService();
            String uniNeg = usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteResumenMatriculaGeneral.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            String anioVista = anio.toString();
            List<ResumenMatriculaRepBean> listaEstadoRecibos = new ArrayList<>();
            if (!usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SECTOR)) {
                listaEstadoRecibos = cuentas.obtenerResumenMatriculaGeneral(1, uniNeg, anioVista, fechaCorte);
                if (!listaEstadoRecibos.isEmpty()) {
                    List<ResumenMatriculaRepBean> listaEstadoRecibosDetalla = new ArrayList<>();
                    for (int k = 0; k < listaEstadoRecibos.size(); k++) {
                        listaEstadoRecibosDetalla = cuentas.obtenerResumenMatricula(1, uniNeg, anioVista, fechaCorte);
                        listaEstadoRecibos.get(0).setListaDetalle(listaEstadoRecibosDetalla);
                    }
                }

            } else {
                listaEstadoRecibos = cuentas.obtenerResumenMatriculaGeneral(2, uniNeg, anioVista, fechaCorte);
                if (!listaEstadoRecibos.isEmpty()) {
                    List<ResumenMatriculaRepBean> listaEstadoRecibosDetalla = new ArrayList<>();
                    List<ResumenMatriculaRepBean> listaEstadoRecibosDetalla2 = new ArrayList<>();
                    for (int k = 0; k < listaEstadoRecibos.size(); k++) {
                        listaEstadoRecibosDetalla2 = cuentas.obtenerResumenMatricula(2, listaEstadoRecibos.get(k).getUniNeg(), anioVista, fechaCorte);
//                        listaEstadoRecibos.get(0).setListaDetalle(listaEstadoRecibosDetalla);
                        for (ResumenMatriculaRepBean estado : listaEstadoRecibosDetalla2) {
                            listaEstadoRecibosDetalla.add(estado);
                        }
                        listaEstadoRecibos.get(k).setListaDetalle(listaEstadoRecibosDetalla);
                        listaEstadoRecibosDetalla = new ArrayList<>();
                    }
                }
            }
//             
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaEstadoRecibos);

            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);
            UsuarioBean ub = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            parametros.put("USUARIO", ub.getUsuario());

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            out = response.getOutputStream();
            out.write(bytes);
            out.flush();

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception ettt) {
                new MensajePrime().addErrorGeneralMessage();
                GLTLog.writeError(this.getClass(), ettt);
            }
        }

        FacesContext.getCurrentInstance()
                .responseComplete();
    }
    
    public void imrprimirResultadoMatriculaExcel() {
        try {
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            CuentasPorCobrarService cuentas = BeanFactory.getCuentasPorCobrarService();
            String uniNeg = usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteResumenMatriculaGeneral.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            String anioVista = anio.toString();
            List<ResumenMatriculaRepBean> listaEstadoRecibos = new ArrayList<>();
            if (!usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SECTOR)) {
                listaEstadoRecibos = cuentas.obtenerResumenMatriculaGeneral(1, uniNeg, anioVista, fechaCorte);
                if (!listaEstadoRecibos.isEmpty()) {
                    List<ResumenMatriculaRepBean> listaEstadoRecibosDetalla = new ArrayList<>();
                    for (int k = 0; k < listaEstadoRecibos.size(); k++) {
                        listaEstadoRecibosDetalla = cuentas.obtenerResumenMatricula(1, uniNeg, anioVista, fechaCorte);
                        listaEstadoRecibos.get(0).setListaDetalle(listaEstadoRecibosDetalla);
                    }
                }

            } else {
                listaEstadoRecibos = cuentas.obtenerResumenMatriculaGeneral(2, uniNeg, anioVista, fechaCorte);
                if (!listaEstadoRecibos.isEmpty()) {
                    List<ResumenMatriculaRepBean> listaEstadoRecibosDetalla = new ArrayList<>();
                    List<ResumenMatriculaRepBean> listaEstadoRecibosDetalla2 = new ArrayList<>();
                    for (int k = 0; k < listaEstadoRecibos.size(); k++) {
                        listaEstadoRecibosDetalla2 = cuentas.obtenerResumenMatricula(2, listaEstadoRecibos.get(k).getUniNeg(), anioVista, fechaCorte);
//                        listaEstadoRecibos.get(0).setListaDetalle(listaEstadoRecibosDetalla);
                        for (ResumenMatriculaRepBean estado : listaEstadoRecibosDetalla2) {
                            listaEstadoRecibosDetalla.add(estado);
                        }
                        listaEstadoRecibos.get(k).setListaDetalle(listaEstadoRecibosDetalla);
                        listaEstadoRecibosDetalla = new ArrayList<>();
                    }
                }
            }
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaEstadoRecibos);

            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);
//            JasperReport report = JasperCompileManager.compileReport(rutaRep + "\\" + narchivo);
            JasperPrint print = JasperFillManager.fillReport(reporte, parametros, jrbc);
            OutputStream out = response.getOutputStream();

            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            JRXlsExporter exporterXLS = new JRXlsExporter();

            exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
            exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, arrayOutputStream);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            
            exporterXLS.exportReport();

            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=Reporte_Pensiones_Por_Cobrar_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".xls");
            response.setContentType("application/vnd.ms-excel");
            response.setContentLength(arrayOutputStream.toByteArray().length);
//            out = response.getOutputStream();
//
            out.write(arrayOutputStream.toByteArray());
            out.flush();
            out.close();
        } catch (Exception ettt) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ettt);
        }
    }

    public void imrprimirEstadoPlanilla() {
        ServletOutputStream out = null;
        Integer id = 0;
        try {
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            CuentasPorCobrarService cuentas = BeanFactory.getCuentasPorCobrarService();
            String uniNeg = usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
//            Date inicio = docEgresoRepFiltroBean.getFechaInicio();
//            Date fin = docEgresoRepFiltroBean.getFechaFin();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteVerificacionIngresoPlanilla.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            String anioVista = anio.toString();

            List<EstadoRecibosRepBean> listaCabecera = new ArrayList<>();
            listaCabecera = cuentas.obtenerCabeceraEstadoRecibo(anioVista);
            if (!listaCabecera.isEmpty()) {
                List<VerificacionIngresoPlanillaRepBean> listaEstadoVerificacion = new ArrayList<>();
                List<VerificacionIngresoPlanillaRepBean> listaEstadoVerificacion2 = new ArrayList<>();

                List<VerificacionIngresoPlanillaRepBean> listaEstadoCTS = new ArrayList<>();
                List<VerificacionIngresoPlanillaRepBean> listaEstadoCTS2 = new ArrayList<>();
                if (!usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SECTOR)) {
                    for (int k = 0; k < listaCabecera.size(); k++) {
                        listaEstadoVerificacion = cuentas.obtenerVerificacionPlanilla(1, listaCabecera.get(k).getUniNeg(), anioVista);
                        if (!listaEstadoVerificacion.isEmpty()) {
                            for (VerificacionIngresoPlanillaRepBean estado : listaEstadoVerificacion) {
                                listaEstadoVerificacion2.add(estado);
                                listaCabecera.get(k).setListaVerificacionPlanilla(listaEstadoVerificacion2);
                            }
                        }

                        listaEstadoCTS = cuentas.obtenerVerificacionPlanillaCTS(1, listaCabecera.get(k).getUniNeg(), anioVista);
                        if (!listaEstadoCTS.isEmpty()) {
                            for (VerificacionIngresoPlanillaRepBean cts : listaEstadoCTS) {
                                listaEstadoCTS2.add(cts);
                                listaCabecera.get(k).setListaCTS(listaEstadoCTS2);
                            }
                        }
                    }
                } else {
                    for (int j = 0; j < listaCabecera.size(); j++) {
                        listaEstadoVerificacion = cuentas.obtenerVerificacionPlanilla(2, listaCabecera.get(j).getUniNeg(), anioVista);
                        for (VerificacionIngresoPlanillaRepBean estado : listaEstadoVerificacion) {
                            listaEstadoVerificacion2.add(estado);
                        }
                        listaEstadoCTS = cuentas.obtenerVerificacionPlanillaCTS(2, listaCabecera.get(j).getUniNeg(), anioVista);
                        if (!listaEstadoCTS.isEmpty()) {
                            for (VerificacionIngresoPlanillaRepBean cts : listaEstadoCTS) {
                                listaEstadoCTS2.add(cts);
                                listaCabecera.get(j).setListaCTS(listaEstadoCTS2);
                            }
                        }

                        listaCabecera.get(j).setListaVerificacionPlanilla(listaEstadoVerificacion2);
                        listaEstadoVerificacion2 = new ArrayList<>();
                        listaCabecera.get(j).setListaCTS(listaEstadoCTS2);
                        listaEstadoCTS2 = new ArrayList<>();
                    }
                }
            }

            for (int g = 0; g < listaCabecera.size(); g++) {
                if (listaCabecera.get(g).getListaVerificacionPlanilla().getData().size() == 0) {
                    listaCabecera.remove(g);
                }
            }
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCabecera);

            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);
            UsuarioBean ub = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            parametros.put("USUARIO", ub.getUsuario());

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            out = response.getOutputStream();
            out.write(bytes);
            out.flush();

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception ettt) {
                new MensajePrime().addErrorGeneralMessage();
                GLTLog.writeError(this.getClass(), ettt);
            }
        }

        FacesContext.getCurrentInstance()
                .responseComplete();
    }

    public void imprimirExcelPensionesPorCobrar() {
        try {

            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reportePensionesPorCobrarEXCEL.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            List<PensionesPorCobrarLPMRepBean> lista = new ArrayList<>();
            lista = cuentasPorCobrarService.obtenerPenionesPorCobrarLPM(mesSelect, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, fechaInicio, mesSelectFin);

            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(lista);

            Map<String, Object> parametros = new HashMap<>();
//            JasperReport report = JasperCompileManager.compileReport(rutaRep + "\\" + narchivo);
            JasperPrint print = JasperFillManager.fillReport(reporte, parametros, jrbc);
            OutputStream out = response.getOutputStream();

            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            JRXlsExporter exporterXLS = new JRXlsExporter();

            exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
            exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, arrayOutputStream);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);
            exporterXLS.exportReport();

            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=Reporte_Pensiones_Por_Cobrar_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".xls");
            response.setContentType("application/vnd.ms-excel");
            response.setContentLength(arrayOutputStream.toByteArray().length);
//            out = response.getOutputStream();
//
            out.write(arrayOutputStream.toByteArray());
            out.flush();
            out.close();
        } catch (Exception ettt) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ettt);
        }
    }

    public void imprimirExcelPensionesPagadas() {
        try {

            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reportePensionesPagadasEXCEL.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            List<PensionesPorCobrarLPMRepBean> lista = new ArrayList<>();
            lista = cuentasPorCobrarService.obtenerPenionesPagadasLPM(mesSelect, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, fechaInicio, mesSelectFin);

            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(lista);

            Map<String, Object> parametros = new HashMap<>();
//            JasperReport report = JasperCompileManager.compileReport(rutaRep + "\\" + narchivo);
            JasperPrint print = JasperFillManager.fillReport(reporte, parametros, jrbc);
            OutputStream out = response.getOutputStream();

            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            JRXlsExporter exporterXLS = new JRXlsExporter();

            exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
            exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, arrayOutputStream);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);
            exporterXLS.exportReport();

            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=Reporte_Pensiones_Pagadas_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".xls");
            response.setContentType("application/vnd.ms-excel");
            response.setContentLength(arrayOutputStream.toByteArray().length);
//            out = response.getOutputStream();
//
            out.write(arrayOutputStream.toByteArray());
            out.flush();
            out.close();
        } catch (Exception ettt) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ettt);
        }
    }

    public void imprimirNotasOperaciones(Date fecha) {

        ServletOutputStream out = null;
        try {
            Integer mes;
            if (mesSelect != null) {
                mes = mesSelect;
            } else {
                mes = 0;
            }

            Integer idTipoLugar = MaristaConstantes.COD_LUGAR_BANCO;
            String uniNeg = usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteNotasOperacion.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            //0.-cab
            List<NotasOperacionRepBean> listaCabecera = new ArrayList<>();
            listaCabecera = cuentasPorCobrarService.obtenerNotaOperacion(uniNeg, mes);
            //1 lista fecha de cobros
            if (!listaCabecera.isEmpty()) {
                for (int g = 0; g < listaCabecera.size(); g++) {
                    List<NotasOpeFecCobroRepBean> listaFechaCobros = new ArrayList<>();
                    if (fecha == null) {
                        listaFechaCobros = cuentasPorCobrarService.obtenerNotaOperacionPorFechaCobros(listaCabecera.get(g).getMes(), uniNeg, anio, idTipoLugar, null);
                    }
                    if (fecha != null) {
                        listaFechaCobros = cuentasPorCobrarService.obtenerNotaOperacionPorFechaCobros(listaCabecera.get(g).getMes(), uniNeg, anio, idTipoLugar, fecha);
                    }
                    listaCabecera.get(0).setListaFechasCobros(listaFechaCobros);
                    //2 obtengo fec venc
                    if (!listaFechaCobros.isEmpty()) {
                        for (int j = 0; j < listaCabecera.get(0).getListaFechasCobros().getData().size(); j++) {
                            System.out.println("Fec. Cobro =========>" + listaFechaCobros.get(j).getFechacobro());
                            List<NotasOpeFecVencRepBean> listaFecVenc = new ArrayList<>();
                            listaFecVenc = cuentasPorCobrarService.obtenerNotaOperacionPorFechaVenc(listaCabecera.get(g).getMes(), uniNeg, anio, idTipoLugar,
                                    listaFechaCobros.get(j).getFechacobro());
                            listaFechaCobros.get(j).setListaFechasVencimiento(listaFecVenc);
                            listaCabecera.get(0).setListaFechasCobros(listaFechaCobros);
                            //3 detalle
                            if (!listaFecVenc.isEmpty()) {
                                System.out.println("Cantidad Fec. Venc " + +listaFecVenc.size());
                                for (int k = 0; k < listaFecVenc.size(); k++) {
                                    System.out.println("Fec. Venc " + k + ": " + listaFecVenc.get(k).getFechavenc());
                                    List<DetCobrosPensionesRepBean> listaDetOpe = new ArrayList<>();
                                    listaDetOpe = cuentasPorCobrarService.obtenerDetNotaOperacionPorFechaVenc(listaCabecera.get(g).getMes(), uniNeg, anio, idTipoLugar,
                                            listaFechaCobros.get(j).getFechacobro(), listaFecVenc.get(k).getFechavenc());

                                    listaFecVenc.get(k).setListaDetCobrosPensiones(listaDetOpe);
                                    listaFechaCobros.get(j).setListaFechasVencimiento(listaFecVenc);
                                    listaCabecera.get(0).setListaFechasCobros(listaFechaCobros);
                                }
                            }
                        }
                    }
                }
            }
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCabecera);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            response.setHeader("Content-Disposition", "inline; filename=NotasOpe_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".pdf");
            response.setHeader("Cache-Control", "cache, must-revalidate");
            response.setHeader("Pragma", "public");
            out = response.getOutputStream();
            out.write(bytes);
            out.flush();

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception ettt) {
                new MensajePrime().addErrorGeneralMessage();
                GLTLog.writeError(this.getClass(), ettt);
            }
        }
        // Inform JSF that it doesn't need to handle response.
        // This is very important, otherwise you will get the following exception in the logs:
        // java.lang.IllegalStateException: Cannot forward after response has been committed.
        FacesContext.getCurrentInstance().responseComplete();
    }

    ///EXCEL
//    public static void main(String[] args) throws Exception{
    public void imprimirExcelSql() throws IOException {
        /*La ruta donde se creará el archivo*/
        String rutaArchivo = System.getProperty("user.home") + "/ejemploExcelJava.xls";
        /*Se crea el objeto de tipo File con la ruta del archivo*/
        File archivoXLS = new File(rutaArchivo);
        /*Si el archivo existe se elimina*/
        if (archivoXLS.exists()) {
            archivoXLS.delete();
        }
        /*Se crea el archivo*/
        archivoXLS.createNewFile();

        /*Se crea el libro de excel usando el objeto de tipo Workbook*/
        Workbook libro = new HSSFWorkbook();
        /*Se inicializa el flujo de datos con el archivo xls*/
        FileOutputStream archivo = new FileOutputStream(archivoXLS);

        /*Utilizamos la clase Sheet para crear una nueva hoja de trabajo dentro del libro que creamos anteriormente*/
        Sheet hoja = libro.createSheet("Mi hoja de trabajo 1");

        /*Hacemos un ciclo para inicializar los valores de 10 filas de celdas*/
        for (int f = 0; f < 10; f++) {
            /*La clase Row nos permitirá crear las filas*/
            Row fila = hoja.createRow(f);

            /*Cada fila tendrá 5 celdas de datos*/
            for (int c = 0; c < 5; c++) {
                /*Creamos la celda a partir de la fila actual*/
                Cell celda = fila.createCell(c);

                /*Si la fila es la número 0, estableceremos los encabezados*/
                if (f == 0) {
                    celda.setCellValue("Encabezado #" + c);
                } else {
                    /*Si no es la primera fila establecemos un valor*/
                    celda.setCellValue("Valor celda " + c + "," + f);
                }
            }
        }

        /*Escribimos en el libro*/
        libro.write(archivo);
        /*Cerramos el flujo de datos*/
        archivo.close();
        /*Y abrimos el archivo con la clase Desktop*/
        Desktop.getDesktop().open(archivoXLS);
    }

    ////////////////////////////////////
    //Provisiones
    public void imprimirProvisionesPensiones() {
        ServletOutputStream out = null;
        try {
            Integer mes;
            if (mesSelect != null) {
                mes = mesSelect;
            } else {
                mes = 0;
            }

            String uniNeg = usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
//            CronogramaPagoService cronogramaPagoService = BeanFactory.getCronogramaPagoService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteProvisionesPensiones.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            List<ProvicionPensionRepBean> listaCabecera = new ArrayList<>();
            listaCabecera = cuentasPorCobrarService.obtenerProvisionCabecera(anio, mes, uniNeg);

            if (!listaCabecera.isEmpty()) {
                for (int i = 0; i < listaCabecera.size(); i++) {
                    List<ProvicionPensionNivelesRepBean> listaNiveles = new ArrayList<>();
                    listaNiveles = cuentasPorCobrarService.obtenerProvisionNiveles(anio, listaCabecera.get(i).getMesAyuda(), uniNeg);
                    listaCabecera.get(0).setListaNiveles(listaNiveles);
                    if (!listaNiveles.isEmpty()) {
                        for (int j = 0; j < listaNiveles.size(); j++) {
                            System.out.println("nivel" + listaNiveles.get(j).getNombre());
                            List<ProvicionPensionNivelDetalleRepBean> listaNivelDetalle = new ArrayList<>();
                            listaNivelDetalle = cuentasPorCobrarService.obtenerProvisionNivelesDetalle(anio, (listaCabecera.get(i).getMesAyuda()), listaNiveles.get(j).getNombre(), uniNeg);
                            listaNiveles.get(j).setListaNivelDetalle(listaNivelDetalle);
                            listaCabecera.get(0).setListaNiveles(listaNiveles);
                        }
                    }
                }
            }

            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCabecera);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            response.setHeader("Content-Disposition", "inline; filename=ProvisionPensiones_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".pdf");
            response.setHeader("Cache-Control", "cache, must-revalidate");
            response.setHeader("Pragma", "public");
            out = response.getOutputStream();
            out.write(bytes);
            out.flush();

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception ettt) {
                new MensajePrime().addErrorGeneralMessage();
                GLTLog.writeError(this.getClass(), ettt);
            }
        }
        // Inform JSF that it doesn't need to handle response.
        // This is very important, otherwise you will get the following exception in the logs:
        // java.lang.IllegalStateException: Cannot forward after response has been committed.
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void obtenerListaNotaAbono() {
        try {
//            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
//            List<SaldoPensionesRepBean> listaCabecera = new ArrayList<>();
//            listaCabecera = cuentasPorCobrarService.obtenerSaldoPenionesPorFecha(0, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, 19401, null, fecha);
//            if (!listaCabecera.isEmpty()) {
//                for (int i = 0; i < listaCabecera.size(); i++) {
//                    List<EstudianteSaldoRepBean> listaEstudiantes = new ArrayList<>();
////                    listaEstudiantes = cuentasPorCobrarService.obtenerEstudianteConSaldoPorFecha(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, 19401, fecha);
//                    listaCabecera.get(0).setListaEstudiantesSaldo(listaEstudiantes);
//                    System.out.println("cantidad de est: " + listaEstudiantes.size());
//                    System.out.println("cantidad de est: " + listaCabecera.get(0).getListaEstudiantesSaldo().getData().size());
//                    if (!listaEstudiantes.isEmpty()) {
//                        for (int j = 0; j < listaCabecera.get(0).getListaEstudiantesSaldo().getData().size(); j++) {                            
//                            listaDetResumenIngRep = cuentasPorCobrarService.obtenerSaldoPenionesPorFecha(1, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, 19401, listaEstudiantes.get(j).getIdestudiante(), fecha);
////                            listaEstudiantes.get(j).setListaDetalle(listaDetResumenIngRep);
//                            listaCabecera.get(0).setListaEstudiantesSaldo(listaEstudiantes);
//                        }
//                    }
//                }
//            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    /////////////////////////////////////////////
    public List<EstudianteBean> getListaEstudianteBean() {
        if (listaEstudianteBean == null) {
            listaEstudianteBean = new ArrayList<>();
        }
        return listaEstudianteBean;
    }

    public void setListaEstudianteBean(List<EstudianteBean> listaEstudianteBean) {
        this.listaEstudianteBean = listaEstudianteBean;
    }

    public List<CuentasPorCobrarBean> getListaCuentasPorCobrarBean() {
        if (listaCuentasPorCobrarBean == null) {
            listaCuentasPorCobrarBean = new ArrayList<>();
        }
        return listaCuentasPorCobrarBean;
    }

    public void setListaCuentasPorCobrarBean(List<CuentasPorCobrarBean> listaCuentasPorCobrarBean) {
        this.listaCuentasPorCobrarBean = listaCuentasPorCobrarBean;
    }

    public CuentasPorCobrarBean getCuentas() {
        if (cuentas == null) {
            cuentas = new CuentasPorCobrarBean();
        }
        return cuentas;
    }

    public void setCuentas(CuentasPorCobrarBean cuentas) {
        this.cuentas = cuentas;
    }

    public Boolean getFlgTodos() {
        return flgTodos;
    }

    public void setFlgTodos(Boolean flgTodos) {
        this.flgTodos = flgTodos;
    }

    public List<MatriculaBean> getListaMatriculaEstudiantesMasivosBean() {
        if (listaMatriculaEstudiantesMasivosBean == null) {
            listaMatriculaEstudiantesMasivosBean = new ArrayList<>();
        }
        return listaMatriculaEstudiantesMasivosBean;
    }

    public void setListaMatriculaEstudiantesMasivosBean(List<MatriculaBean> listaMatriculaEstudiantesMasivosBean) {
        this.listaMatriculaEstudiantesMasivosBean = listaMatriculaEstudiantesMasivosBean;
    }

    public List<NivelAcademicoBean> getListaNivelAcademico() {
        if (listaNivelAcademico == null) {
            listaNivelAcademico = new ArrayList<>();
        }
        return listaNivelAcademico;
    }

    public void setListaNivelAcademico(List<NivelAcademicoBean> listaNivelAcademico) {
        this.listaNivelAcademico = listaNivelAcademico;
    }

    public Boolean getFlgEstSinPro() {
        return flgEstSinPro;
    }

    public void setFlgEstSinPro(Boolean flgEstSinPro) {
        this.flgEstSinPro = flgEstSinPro;
    }

    public Boolean getFlgPorNivelGrado() {
        return flgPorNivelGrado;
    }

    public void setFlgPorNivelGrado(Boolean flgPorNivelGrado) {
        this.flgPorNivelGrado = flgPorNivelGrado;
    }

    public Boolean getFlgEstEsp() {
        return flgEstEsp;
    }

    public void setFlgEstEsp(Boolean flgEstEsp) {
        this.flgEstEsp = flgEstEsp;
    }

    public Boolean getFlgTodosMatriculados() {
        return flgTodosMatriculados;
    }

    public void setFlgTodosMatriculados(Boolean flgTodosMatriculados) {
        this.flgTodosMatriculados = flgTodosMatriculados;
    }

    public Boolean getFlgEstEspMatricula() {
        return flgEstEspMatricula;
    }

    public void setFlgEstEspMatricula(Boolean flgEstEspMatricula) {
        this.flgEstEspMatricula = flgEstEspMatricula;
    }

    public List<Integer> getListaAnioFiltroMatricula() {
        if (listaAnioFiltroMatricula == null) {
            listaAnioFiltroMatricula = new ArrayList<>();
        }
        return listaAnioFiltroMatricula;
    }

    public void setListaAnioFiltroMatricula(List<Integer> listaAnioFiltroMatricula) {
        this.listaAnioFiltroMatricula = listaAnioFiltroMatricula;
    }

    public List<GradoAcademicoBean> getListaGradoAcademicoBean() {
        if (listaGradoAcademicoBean == null) {
            listaGradoAcademicoBean = new ArrayList<>();
        }
        return listaGradoAcademicoBean;
    }

    public void setListaGradoAcademicoBean(List<GradoAcademicoBean> listaGradoAcademicoBean) {
        this.listaGradoAcademicoBean = listaGradoAcademicoBean;
    }

    public List<GradoAcademicoBean> getListaGradoAcademicoFiltroBean() {
        if (listaGradoAcademicoFiltroBean == null) {
            listaGradoAcademicoFiltroBean = new ArrayList<>();
        }
        return listaGradoAcademicoFiltroBean;
    }

    public void setListaGradoAcademicoFiltroBean(List<GradoAcademicoBean> listaGradoAcademicoFiltroBean) {
        this.listaGradoAcademicoFiltroBean = listaGradoAcademicoFiltroBean;
    }

    public Boolean getValCtaCteTodos() {
        return valCtaCteTodos;
    }

    public void setValCtaCteTodos(Boolean valCtaCteTodos) {
        this.valCtaCteTodos = valCtaCteTodos;
    }

    public List<CuentasPorCobrarBean> getListaCuentasEstudianteBean() {
        if (listaCuentasEstudianteBean == null) {
            listaCuentasEstudianteBean = new ArrayList<>();
        }
        return listaCuentasEstudianteBean;
    }

    public void setListaCuentasEstudianteBean(List<CuentasPorCobrarBean> listaCuentasEstudianteBean) {
        this.listaCuentasEstudianteBean = listaCuentasEstudianteBean;
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

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
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

    public List<CentroResponsabilidadBean> getListaCrInicialBean() {
        if (listaCrInicialBean == null) {
            listaCrInicialBean = new ArrayList<>();
        }
        return listaCrInicialBean;
    }

    public void setListaCrInicialBean(List<CentroResponsabilidadBean> listaCrInicialBean) {
        this.listaCrInicialBean = listaCrInicialBean;
    }

    public List<CentroResponsabilidadBean> getListaCrPrimariaBean() {
        if (listaCrPrimariaBean == null) {
            listaCrPrimariaBean = new ArrayList<>();
        }
        return listaCrPrimariaBean;
    }

    public void setListaCrPrimariaBean(List<CentroResponsabilidadBean> listaCrPrimariaBean) {
        this.listaCrPrimariaBean = listaCrPrimariaBean;
    }

    public List<CentroResponsabilidadBean> getListaCrSecundariaBean() {
        if (listaCrSecundariaBean == null) {
            listaCrSecundariaBean = new ArrayList<>();
        }
        return listaCrSecundariaBean;
    }

    public void setListaCrSecundariaBean(List<CentroResponsabilidadBean> listaCrSecundariaBean) {
        this.listaCrSecundariaBean = listaCrSecundariaBean;
    }

    public List<CentroResponsabilidadBean> getListaCrBachillerBean() {
        if (listaCrBachillerBean == null) {
            listaCrBachillerBean = new ArrayList<>();
        }
        return listaCrBachillerBean;
    }

    public void setListaCrBachillerBean(List<CentroResponsabilidadBean> listaCrBachillerBean) {
        this.listaCrBachillerBean = listaCrBachillerBean;
    }

    public List<Integer> getListaAnioGenerar() {
        if (listaAnioGenerar == null) {
            listaAnioGenerar = new ArrayList<>();
        }
        return listaAnioGenerar;
    }

    public void setListaAnioGenerar(List<Integer> listaAnioGenerar) {
        this.listaAnioGenerar = listaAnioGenerar;
    }

    public Integer getMesSelect() {
        return mesSelect;
    }

    public void setMesSelect(Integer mesSelect) {
        this.mesSelect = mesSelect;
    }

    public List<MesBean> getListaMesAll() {
        if (listaMesAll == null) {
            listaMesAll = new ArrayList<>();
        }
        return listaMesAll;
    }

    public void setListaMesAll(List<MesBean> listaMesAll) {
        this.listaMesAll = listaMesAll;
    }

    public List<CodigoBean> getListaTipoLugarPago() {
        return listaTipoLugarPago;
    }

    public void setListaTipoLugarPago(List<CodigoBean> listaTipoLugarPago) {
        this.listaTipoLugarPago = listaTipoLugarPago;
    }

    public Integer getIdTipoLugarPago() {
        return idTipoLugarPago;
    }

    public void setIdTipoLugarPago(Integer idTipoLugarPago) {
        this.idTipoLugarPago = idTipoLugarPago;
    }

    public Map<String, Integer> getListaMeses() {
        return listaMeses;
    }

    public void setListaMeses(Map<String, Integer> listaMeses) {
        this.listaMeses = listaMeses;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public List<NotasOpeFecCobroRepBean> getListaFechaCobros() {
        if (listaFechaCobros == null) {
            listaFechaCobros = new ArrayList<>();
        }
        return listaFechaCobros;
    }

    public void setListaFechaCobros(List<NotasOpeFecCobroRepBean> listaFechaCobros) {
        this.listaFechaCobros = listaFechaCobros;
    }

    public List<CuentasPorCobrarRepBean> getListaCtaPorCobrarRepBean() {
        if (listaCtaPorCobrarRepBean == null) {
            listaCtaPorCobrarRepBean = new ArrayList<>();
        }
        return listaCtaPorCobrarRepBean;
    }

    public void setListaCtaPorCobrarRepBean(List<CuentasPorCobrarRepBean> listaCtaPorCobrarRepBean) {
        this.listaCtaPorCobrarRepBean = listaCtaPorCobrarRepBean;
    }

    public String getTotMora() {
        return totMora;
    }

    public void setTotMora(String totMora) {
        this.totMora = totMora;
    }

    public String getTotDsct() {
        return totDsct;
    }

    public void setTotDsct(String totDsct) {
        this.totDsct = totDsct;
    }

    public String getTotAdeuda() {
        return totAdeuda;
    }

    public void setTotAdeuda(String totAdeuda) {
        this.totAdeuda = totAdeuda;
    }

    public String getTotCancelado() {
        return totCancelado;
    }

    public void setTotCancelado(String totCancelado) {
        this.totCancelado = totCancelado;
    }

    public DetDocIngresoBean getDetDocIngresoBean() {
        if (detDocIngresoBean == null) {
            detDocIngresoBean = new DetDocIngresoBean();
        }
        return detDocIngresoBean;
    }

    public void setDetDocIngresoBean(DetDocIngresoBean detDocIngresoBean) {
        this.detDocIngresoBean = detDocIngresoBean;
    }

    public List<CodigoBean> getListaTipoStatusCtaCte() {
        if (listaTipoStatusCtaCte == null) {
            listaTipoStatusCtaCte = new ArrayList<>();
        }
        return listaTipoStatusCtaCte;
    }

    public void setListaTipoStatusCtaCte(List<CodigoBean> listaTipoStatusCtaCte) {
        this.listaTipoStatusCtaCte = listaTipoStatusCtaCte;
    }

    public List<CodigoBean> getListaTipoDscto() {
        if (listaTipoDscto == null) {
            listaTipoDscto = new ArrayList<>();
        }
        return listaTipoDscto;
    }

    public void setListaTipoDscto(List<CodigoBean> listaTipoDscto) {
        this.listaTipoDscto = listaTipoDscto;
    }

    public List<CentroResponsabilidadBean> getListaCrBean() {
        if (listaCrBean == null) {
            listaCrBean = new ArrayList<>();
        }
        return listaCrBean;
    }

    public void setListaCrBean(List<CentroResponsabilidadBean> listaCrBean) {
        this.listaCrBean = listaCrBean;
    }

    public List<ConceptoUniNegBean> getListaConceptoUniNegBean() {
        if (listaConceptoUniNegBean == null) {
            listaConceptoUniNegBean = new ArrayList();
        }
        return listaConceptoUniNegBean;
    }

    public void setListaConceptoUniNegBean(List<ConceptoUniNegBean> listaConceptoUniNegBean) {
        this.listaConceptoUniNegBean = listaConceptoUniNegBean;
    }

    public List<ProcesoBancoBean> getListaProcesoBancoBean() {
        if (listaProcesoBancoBean == null) {
            listaProcesoBancoBean = new ArrayList<>();
        }
        return listaProcesoBancoBean;
    }

    public void setListaProcesoBancoBean(List<ProcesoBancoBean> listaProcesoBancoBean) {
        this.listaProcesoBancoBean = listaProcesoBancoBean;
    }

    public List<CodigoBean> getListaTipoModoPago() {
        if (listaTipoModoPago == null) {
            listaTipoModoPago = new ArrayList<>();
        }
        return listaTipoModoPago;
    }

    public void setListaTipoModoPago(List<CodigoBean> listaTipoModoPago) {
        this.listaTipoModoPago = listaTipoModoPago;
    }

    public List<CodigoBean> getListaTipoStatusDocIng() {
        if (listaTipoStatusDocIng == null) {
            listaTipoStatusDocIng = new ArrayList<>();
        }
        return listaTipoStatusDocIng;
    }

    public void setListaTipoStatusDocIng(List<CodigoBean> listaTipoStatusDocIng) {
        this.listaTipoStatusDocIng = listaTipoStatusDocIng;
    }

    public List<CodigoBean> getListaTipoDocumento() {
        if (listaTipoDocumento == null) {
            listaTipoDocumento = new ArrayList<>();
        }
        return listaTipoDocumento;
    }

    public void setListaTipoDocumento(List<CodigoBean> listaTipoDocumento) {
        this.listaTipoDocumento = listaTipoDocumento;
    }

    public Boolean getFlgEncargadoCta() {
        return flgEncargadoCta;
    }

    public void setFlgEncargadoCta(Boolean flgEncargadoCta) {
        this.flgEncargadoCta = flgEncargadoCta;
    }

    public List<CajaGenBean> getListaCajaGenBean() {
        if (listaCajaGenBean == null) {
            listaCajaGenBean = new ArrayList<>();
        }
        return listaCajaGenBean;
    }

    public void setListaCajaGenBean(List<CajaGenBean> listaCajaGenBean) {
        this.listaCajaGenBean = listaCajaGenBean;
    }

    public String getCajaGen() {
        return cajaGen;
    }

    public void setCajaGen(String cajaGen) {
        this.cajaGen = cajaGen;
    }

    public List<DetResumenIngRepBean> getListaDetResumenIngRep() {
        return listaDetResumenIngRep;
    }

    public void setListaDetResumenIngRep(List<DetResumenIngRepBean> listaDetResumenIngRep) {
        this.listaDetResumenIngRep = listaDetResumenIngRep;
    }

    public List<MatriculaBean> getListaSeccionBean() {
        return listaSeccionBean;
    }

    public void setListaSeccionBean(List<MatriculaBean> listaSeccionBean) {
        this.listaSeccionBean = listaSeccionBean;
    }

    public List<SaldoPensionesRepBean> getListaPensionesRepBean() {
        if (listaPensionesRepBean == null) {
            listaPensionesRepBean = new ArrayList<>();
        }
        return listaPensionesRepBean;
    }

    public void setListaPensionesRepBean(List<SaldoPensionesRepBean> listaPensionesRepBean) {
        this.listaPensionesRepBean = listaPensionesRepBean;
    }

    public List<ResumenIngRepBean> getListaResumenIngRepBean() {
        if (listaResumenIngRepBean == null) {
            listaResumenIngRepBean = new ArrayList<>();
        }
        return listaResumenIngRepBean;
    }

    public void setListaResumenIngRepBean(List<ResumenIngRepBean> listaResumenIngRepBean) {
        this.listaResumenIngRepBean = listaResumenIngRepBean;
    }

    public MatriculaBean getMatriculaSeccion() {
        if (matriculaSeccion == null) {
            matriculaSeccion = new MatriculaBean();
        }
        return matriculaSeccion;
    }

    public void setMatriculaSeccion(MatriculaBean matriculaSeccion) {
        this.matriculaSeccion = matriculaSeccion;
    }

    public List<FamiliarEstudianteBean> getListFamiliarEst() {
        if (listFamiliarEst == null) {
            listFamiliarEst = new ArrayList<>();
        }
        return listFamiliarEst;
    }

    public void setListFamiliarEst(List<FamiliarEstudianteBean> listFamiliarEst) {
        this.listFamiliarEst = listFamiliarEst;
    }

    public List<CodigoBean> getListaStatusEst() {
        if (listaStatusEst == null) {
            listaStatusEst = new ArrayList<>();
        }
        return listaStatusEst;
    }

    public void setListaStatusEst(List<CodigoBean> listaStatusEst) {
        this.listaStatusEst = listaStatusEst;
    }

    public ModificacionesBean getModificacionesBean() {
        if (modificacionesBean == null) {
            modificacionesBean = new ModificacionesBean();
        }
        return modificacionesBean;
    }

    public void setModificacionesBean(ModificacionesBean modificacionesBean) {
        this.modificacionesBean = modificacionesBean;
    }

    public Date getFechaCorte() {
        return fechaCorte;
    }

    public void setFechaCorte(Date fechaCorte) {
        this.fechaCorte = fechaCorte;
    }

    public Boolean getFlgStatusActivo() {
        return flgStatusActivo;
    }

    public void setFlgStatusActivo(Boolean flgStatusActivo) {
        this.flgStatusActivo = flgStatusActivo;
    }

    public Boolean getFlgModificacionRecibo() {
        return flgModificacionRecibo;
    }

    public void setFlgModificacionRecibo(Boolean flgModificacionRecibo) {
        this.flgModificacionRecibo = flgModificacionRecibo;
    }

    public List<EstudianteSaldoPivotRepBean> getListaPensionesRepBeanPivot() {
        if (listaPensionesRepBeanPivot == null) {
            listaPensionesRepBeanPivot = new ArrayList<>();
        }
        return listaPensionesRepBeanPivot;
    }

    public void setListaPensionesRepBeanPivot(List<EstudianteSaldoPivotRepBean> listaPensionesRepBeanPivot) {
        this.listaPensionesRepBeanPivot = listaPensionesRepBeanPivot;
    }

    public EstudianteRetiroBean getEstudianteRetiroBean() {
        if (estudianteRetiroBean == null) {
            estudianteRetiroBean = new EstudianteRetiroBean();
        }
        return estudianteRetiroBean;
    }

    public void setEstudianteRetiroBean(EstudianteRetiroBean estudianteRetiroBean) {
        this.estudianteRetiroBean = estudianteRetiroBean;
    }

    public List<EstudianteRetiroBean> getListEstudianteRetiroBean() {
        if (listEstudianteRetiroBean == null) {
            listEstudianteRetiroBean = new ArrayList<>();
        }
        return listEstudianteRetiroBean;
    }

    public void setListEstudianteRetiroBean(List<EstudianteRetiroBean> listEstudianteRetiroBean) {
        this.listEstudianteRetiroBean = listEstudianteRetiroBean;
    }

    public String getIdEstudianteVista() {
        return idEstudianteVista;
    }

    public void setIdEstudianteVista(String idEstudianteVista) {
        this.idEstudianteVista = idEstudianteVista;
    }

    public Integer getFlgCondicion() {
        return flgCondicion;
    }

    public void setFlgCondicion(Integer flgCondicion) {
        this.flgCondicion = flgCondicion;
    }

    public Integer getFlgTipoReporte() {
        return flgTipoReporte;
    }

    public void setFlgTipoReporte(Integer flgTipoReporte) {
        this.flgTipoReporte = flgTipoReporte;
    }

    public Boolean getFlgDesabilitarAnoAnt() {
        return flgDesabilitarAnoAnt;
    }

    public void setFlgDesabilitarAnoAnt(Boolean flgDesabilitarAnoAnt) {
        this.flgDesabilitarAnoAnt = flgDesabilitarAnoAnt;
    }

    public Boolean getFlgActivarBuscador() {
        return flgActivarBuscador;
    }

    public void setFlgActivarBuscador(Boolean flgActivarBuscador) {
        this.flgActivarBuscador = flgActivarBuscador;
    }

    public Integer getMesSelectFin() {
        return mesSelectFin;
    }

    public void setMesSelectFin(Integer mesSelectFin) {
        this.mesSelectFin = mesSelectFin;
    }

}
