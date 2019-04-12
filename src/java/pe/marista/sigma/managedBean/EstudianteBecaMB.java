/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
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
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.BecaBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.CronogramaPagoBean;
import pe.marista.sigma.bean.CuentasPorCobrarBean;
import pe.marista.sigma.bean.DetDocIngresoBean;
import pe.marista.sigma.bean.EstudianteBean;
import pe.marista.sigma.bean.EstudianteBecaBean;
import pe.marista.sigma.bean.EstudianteSeguroBean;
import pe.marista.sigma.bean.MatriculaBean;
import pe.marista.sigma.bean.PersonaBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.CuentasPorCobrarRepBean;
import pe.marista.sigma.bean.reporte.EstudianteBecaRepBean;
import pe.marista.sigma.bean.reporte.EstudianteDetalleBecaRepBean;
import pe.marista.sigma.bean.reporte.EstudianteSaldoRepBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.BecaService;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.CuentasPorCobrarService;
import pe.marista.sigma.service.DocIngresoService;
import pe.marista.sigma.service.EstudianteAlergiaService;
import pe.marista.sigma.service.EstudianteBloqueoService;
import pe.marista.sigma.service.EstudianteEnfermedadService;
import pe.marista.sigma.service.EstudianteMedicamentoService;
import pe.marista.sigma.service.EstudianteNacimientoService;
import pe.marista.sigma.service.EstudianteSeguroService;
import pe.marista.sigma.service.EstudianteService;
import pe.marista.sigma.service.EstudianteTraumaService;
import pe.marista.sigma.service.EstudianteVacunaService;
import pe.marista.sigma.service.FamiliarService;
import pe.marista.sigma.service.PersonaService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author MS002
 */
@ManagedBean
@ViewScoped
public class EstudianteBecaMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of EstudianteBecaMB
     */
    @PostConstruct
    public void EstudianteBecaMB() {
        try {
            usuarioLogin = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            CodigoService codigoService = BeanFactory.getCodigoService();
            getListaDocPer();
            listaDocPer = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_DOC_PER));
            getListaMotivoBeca();
            listaMotivoBeca = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_MOTIVO_BECA));
            BecaService becaService = BeanFactory.getBecaService();
            getListaBeca();
            listaBeca = becaService.obtenerTodosActivos();
            getListaCronograma();
            listaCronograma = becaService.obtenerCronograma();
            cargarAno();
            SimpleDateFormat formato = new SimpleDateFormat("yyyy");
            String date = formato.format(new Date());
            getMatriculaFiltroBean();
            getMatriculaFiltroBean().setAnio(Integer.parseInt(date));

            Calendar miCalendario = Calendar.getInstance();
            anio = miCalendario.get(Calendar.YEAR);

            fechaActual = new GregorianCalendar();
            getEstudianteBecaBean().getIdTipoMotivoBeca().setIdCodigo(MaristaConstantes.TIPO_MOT_BECA_EST);
            getEstudianteBecaBean().getCronogramaPagoBean().setMes(MaristaConstantes.NUM_ABRIL);
            getEstudianteBecaBean().setFechaBeca(fechaActual.getTime());
            getEstudianteBecaBean().setMesFin(MaristaConstantes.NUM_DICIEMBRE);
        } catch (Exception ex) {
            Logger.getLogger(EstudianteBecaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private List<EstudianteBean> listaEstudianteBean;
    private MatriculaBean matriculaFiltroBean;
    private EstudianteBean estudianteFiltroBean;
    private EstudianteBean estudianteBean;
    private PersonaBean personaBean;
    private List<CodigoBean> listaDocPer;
    private List<BecaBean> listaBeca;
    private EstudianteBecaBean estudianteBecaBean;
    private List<Object> listaAnos;
    private List<EstudianteBecaBean> listaEstudianteBeca;
    private List<EstudianteBecaBean> listaEstudiantesBecados;
    private List<CodigoBean> listaMotivoBeca;
    private List<CronogramaPagoBean> listaCronograma;
    private List<CuentasPorCobrarBean> listaCuentasPorCobrar;
    private List<CuentasPorCobrarBean> listaCuentasPorCobrarBean;
    private List<CuentasPorCobrarRepBean> listaCtaPorCobrarRepBean;
    private CuentasPorCobrarBean cuentasPorCobrarBean;

    private UsuarioBean usuarioLogin;
    private Integer anio;
    private Integer mes;

    private String totMora = "0.0";
    private String totDsct = "0.0";
    private String totAdeuda = "0.0";
    private String totCancelado = "0.0";

    //cambio
    private Calendar fechaActual;

    public void obtenerEstudiantePorUniNeg() {
        try {
//            estudianteBecaBean = new EstudianteBecaBean();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            BecaService becaService = BeanFactory.getBecaService();
            MatriculaBean matriculaBean = new MatriculaBean();
            SimpleDateFormat formato = new SimpleDateFormat("yyyy");
            matriculaBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
            String date = formato.format(new Date());
            matriculaBean.setAnio(Integer.parseInt(date));
            listaEstudianteBean = becaService.obtenerMatriculadosPorPeriodo(matriculaBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerFiltroEstudiante() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            BecaService becaService = BeanFactory.getBecaService();
            if (matriculaFiltroBean.getEstudianteBean().getCodigo() != null && !matriculaFiltroBean.getEstudianteBean().getCodigo().equals("")) {
                matriculaFiltroBean.getEstudianteBean().setCodigo(matriculaFiltroBean.getEstudianteBean().getCodigo().toUpperCase().trim());
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
            if (matriculaFiltroBean.getEstudianteBean().getIdEstudiante() != null && !matriculaFiltroBean.getEstudianteBean().getIdEstudiante().equals("")) {
                matriculaFiltroBean.getEstudianteBean().setIdEstudiante(matriculaFiltroBean.getEstudianteBean().getIdEstudiante().toUpperCase().trim());
            }
            if (anio != null) {
                matriculaFiltroBean.setAnio(anio);
            }
            matriculaFiltroBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
            listaEstudianteBean = becaService.obtenerFiltroEstudianteMatriculado(matriculaFiltroBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerEstudiantePorId(Object estudiante) {
        try {
            estudianteBean = (EstudianteBean) estudiante;
            estudianteBean.getPersonaBean().setUnidadNegocioBean(estudianteBean.getUnidadNegocioBean());
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            estudianteBean.setAnio(anio);
            estudianteBean = estudianteService.obtenerEstPorId(estudianteBean);
            obtenerTodosBecaPorEstudiante();
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            listaCuentasPorCobrar = cuentasPorCobrarService.obtenerCuentaPorMatAnio(estudianteBean.getPersonaBean().getIdPersona(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
            listaCuentasPorCobrarBean = new ArrayList<>();
            for (CuentasPorCobrarBean cuentas : listaCuentasPorCobrar) {
//                System.out.println("cta1 " + cuentas.getMeses());
                if (cuentas.getAnio().equals(anio)) {
//                    System.out.println("cta2 " + cuentas.getMeses());
                    listaCuentasPorCobrarBean.add(cuentas);
                }
            }
            for (CuentasPorCobrarBean cuentas : listaCuentasPorCobrarBean) {
                System.out.println("cta " + cuentas.getMeses());
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void imprimirBecados() {

        ServletOutputStream out = null;
        try {
            BecaService becaService = BeanFactory.getBecaService();

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteBeca2.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<EstudianteBecaRepBean> listaTitutlo = new ArrayList<>();
            listaTitutlo = becaService.obetenerTitulo(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), mes, anio);
            if (!listaTitutlo.isEmpty()) {
                for (int j = 0; j < listaTitutlo.size(); j++) {
                    List<EstudianteBecaRepBean> listaNombreAlumno = new ArrayList<>();
                    listaNombreAlumno = becaService.obetenerNombreAlumno(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), mes, anio, listaTitutlo.get(j).getNombreBeca());
                    listaTitutlo.get(j).setListaNombreAlumno(listaNombreAlumno);
                }
            }
            System.out.println("imagenRuta:" + absoluteWebPath);
            System.out.println("jasper: " + archivoJasper);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaTitutlo);
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
        FacesContext.getCurrentInstance().responseComplete();
    }

    public List<PersonaBean> completePersona(String query) {
        try {
            PersonaService personaService = BeanFactory.getPersonaService();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            List<PersonaBean> listaPersonaTodosBean = personaService.obtenerPersonaAdmPorUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            List<PersonaBean> listaPersonaFiltroBean = new ArrayList<>();
            for (int i = 0; i < listaPersonaTodosBean.size(); i++) {
                PersonaBean bean = listaPersonaTodosBean.get(i);
                if (bean.getNombreCompleto().toLowerCase().contains(query)) {
                    listaPersonaFiltroBean.add(bean);
                }
            }
            return listaPersonaFiltroBean;

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return null;
    }

    public void onItemSelect(SelectEvent event) {
        try {
            PersonaBean persona = (PersonaBean) event.getObject();
            estudianteBean.setPersonaBean(persona);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public final void cargarAno() {
        try {
            int a = 2040;
            int b = 2014;
            listaAnos = new ArrayList<>();
            for (int i = b; i <= a; i++) {
                listaAnos.add(i);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerTodosBecaPorEstudiante() {
        try {
            BecaService becaService = BeanFactory.getBecaService();
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            estudianteBecaBean.setUniNegUsu(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            estudianteBecaBean.getEstudianteBean().setIdEstudiante(estudianteBean.getPersonaBean().getIdPersona());
            listaEstudianteBeca = becaService.obtenerTodosBecaPorEstudiante(estudianteBecaBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerTodosEstudianteBeca() {
        try {
            BecaService becaService = BeanFactory.getBecaService();
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            estudianteBecaBean.setUniNegUsu(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaEstudianteBeca = becaService.obtenerTodosEstudianteBeca(estudianteBecaBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarBecaEstudiante() {
        estudianteBecaBean = new EstudianteBecaBean();
        estudianteBean = new EstudianteBean();
        listaCtaPorCobrarRepBean = new ArrayList<>();
        fechaActual = new GregorianCalendar();
        getEstudianteBecaBean().getIdTipoMotivoBeca().setIdCodigo(MaristaConstantes.TIPO_MOT_BECA_EST);
        getEstudianteBecaBean().getCronogramaPagoBean().setMes(MaristaConstantes.NUM_ABRIL);
        getEstudianteBecaBean().setFechaBeca(fechaActual.getTime());
        getEstudianteBecaBean().setMesFin(MaristaConstantes.NUM_DICIEMBRE);
    }

    public void obternerEstudiantesBecados() {
        try {
            BecaService becaService = BeanFactory.getBecaService();
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            estudianteBecaBean.setUniNegUsu(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            estudianteBecaBean.setAnio(anio);
            System.out.println("estado1: " + estudianteBecaBean.getStatus());
            System.out.println(anio);
            listaEstudiantesBecados = becaService.buscarBecadosAnio(estudianteBecaBean);
            System.out.println("estado2: " + estudianteBecaBean.getStatus());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
//    public void obternerEstudiantesPdfBecados() {
//        try {
//            BecaService becaService = BeanFactory.getBecaService();
//            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
//            estudianteBecaBean.setUniNegUsu(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            setAnio(anio);
//            setMes(mes);
//            System.out.println(anio); 
//            listaEstudiantesBecados = becaService.buscarBecadosAnio(estudianteBecaBean);
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//    }

    public void obtenerPorIdEstudianteBeca(EstudianteBecaBean estudianteBecaBean) {
        try {
            BecaService becaService = BeanFactory.getBecaService();
            estudianteBecaBean = becaService.buscarPorIdEstudianteBeca(estudianteBecaBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarEstudianteBeca() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
                BecaService becaService = BeanFactory.getBecaService();
                if (estudianteBecaBean.getBecaBean().getIdBeca() != null) {
                    UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                    estudianteBecaBean.setCreaPor(usuarioBean.getUsuario());
                    SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yy:HH:mm:SS");
                    String date = formato.format(new Date());
                    estudianteBecaBean.setUniNegUsu(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    estudianteBecaBean.setCreaFecha(formato.parse(date));
                    estudianteBecaBean.setAnio(anio);
                    becaService.insertarEstudianteBeca(estudianteBecaBean);
                    BecaBean beca = new BecaBean();
                    beca = becaService.buscarPorId(estudianteBecaBean.getBecaBean());
                    for (CuentasPorCobrarBean cuenta : listaCuentasPorCobrarBean) {
                        if (cuenta.getMes() >= estudianteBecaBean.getCronogramaPagoBean().getMes()) {
                            System.out.println("cuenta.getMes() " + cuenta.getMes());
                            Double dsctoBeca = cuenta.getMonto().doubleValue() * beca.getPorcentaje().doubleValue() / 100;
                            cuenta.setEstudianteBecaBean(estudianteBecaBean);
                            cuenta.setDsctoBeca(BigDecimal.valueOf(dsctoBeca));
                            becaService.actualizarCtaCte(cuenta);
                        }
                    }
                    listaCuentasPorCobrar = cuentasPorCobrarService.obtenerCuentaPorMatAnio(estudianteBean.getPersonaBean().getIdPersona(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
                    listaCuentasPorCobrarBean = new ArrayList<>();
                    for (CuentasPorCobrarBean cuentas : listaCuentasPorCobrar) {
                        if (cuentas.getAnio() == anio) {
                            listaCuentasPorCobrarBean.add(cuentas);
                        }
                    }
                } else {

                }
                listaEstudianteBeca = becaService.buscarPorIdEstudianteBecaAnio(estudianteBecaBean);
                limpiarBecaEstudiante();
                getEstudianteBecaBean().getIdTipoMotivoBeca().setIdCodigo(MaristaConstantes.TIPO_MOT_BECA_EST);
                getEstudianteBecaBean().getCronogramaPagoBean().setMes(MaristaConstantes.NUM_ABRIL);
                getEstudianteBecaBean().setFechaBeca(fechaActual.getTime());
                getEstudianteBecaBean().setMesFin(MaristaConstantes.NUM_DICIEMBRE);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String insertarEstudianteBecaVer2() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                System.out.println("insertarEstudianteBecaVer2");
                CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
                BecaService becaService = BeanFactory.getBecaService();
                if (estudianteBecaBean.getBecaBean().getIdBeca() != null) {
                    UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                    estudianteBecaBean.setCreaPor(usuarioBean.getUsuario());
                    SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yy:HH:mm:SS");
                    String date = formato.format(new Date());
                    estudianteBecaBean.setUniNegUsu(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    estudianteBecaBean.setCreaFecha(formato.parse(date));
                    estudianteBecaBean.setAnio(anio);
                    becaService.insertarEstudianteBecaVer2(estudianteBecaBean, listaCuentasPorCobrarBean);
//                    BecaBean beca = new BecaBean();
//                    beca = becaService.buscarPorId(estudianteBecaBean.getBecaBean());
//                    for (CuentasPorCobrarBean cuenta : listaCuentasPorCobrarBean) {
//                        if (cuenta.getMes() >= estudianteBecaBean.getCronogramaPagoBean().getMes()) {
//                            Double dsctoBeca = cuenta.getMonto().doubleValue() * beca.getPorcentaje().doubleValue() / 100;
//                            cuenta.setEstudianteBecaBean(estudianteBecaBean);
//                            cuenta.setDsctoBeca(BigDecimal.valueOf(dsctoBeca));
//                            becaService.actualizarCtaCte(cuenta);
//                        }
//                    }
                    Integer inicio = estudianteBecaBean.getCronogramaPagoBean().getMes();
                    Integer fin = estudianteBecaBean.getMesFin();
                    listaCuentasPorCobrar = cuentasPorCobrarService.obtenerCuentaPorMatAnioDscBeca(estudianteBean.getPersonaBean().getIdPersona(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, inicio, fin);
                    listaCuentasPorCobrarBean = new ArrayList<>();
                    for (CuentasPorCobrarBean cuentas : listaCuentasPorCobrar) {
                        if (cuentas.getAnio() == anio) {
                            listaCuentasPorCobrarBean.add(cuentas);
                        }
                        List<DetDocIngresoBean> det = new ArrayList<>();
                        DocIngresoService docIngreso = BeanFactory.getDocIngresoService();
                        det = docIngreso.obtenerDetoDocIdNew(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), cuentas.getIdCtasXCobrar());
                        System.out.println("nombre: " + cuentas.getEstudianteBecaBean().getBecaBean().getNombre());
                        if (cuentas.getEstudianteBecaBean().getBecaBean().getNombre().equals(MaristaConstantes.Beca6)) {
                            docIngreso.modificarDetalleDocIngDesBecaTotal(cuentas.getIdCtasXCobrar(), cuentas.getUnidadNegocioBean().getUniNeg(), cuentas.getDsctoBeca());
                        } else if (cuentas.getFechaPago() == null) {
                            docIngreso.cambiarDsctoBeca(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), cuentas.getDsctoBeca(), cuentas.getDocIngresoBean().getIdDocIngreso());
                        } else {
                            docIngreso.modificarDetalleDocIngDesCambioBeca(cuentas.getIdCtasXCobrar(), cuentas.getUnidadNegocioBean().getUniNeg(), cuentas.getDsctoBeca(),
                                    cuentas.getDocIngresoBean().getIdDocIngreso());
                        }
                    }
                    obtenerEstudiantePorIdEstadoCuentaRep(estudianteBean);
                } else {

                }
                listaEstudianteBeca = becaService.buscarPorIdEstudianteBecaAnio(estudianteBecaBean);
                limpiarBecaEstudiante();
                getEstudianteBecaBean().getIdTipoMotivoBeca().setIdCodigo(MaristaConstantes.TIPO_MOT_BECA_EST);
                getEstudianteBecaBean().getCronogramaPagoBean().setMes(MaristaConstantes.NUM_ABRIL);
                getEstudianteBecaBean().setFechaBeca(fechaActual.getTime());
                getEstudianteBecaBean().setMesFin(MaristaConstantes.NUM_DICIEMBRE);

                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarEstudianteBeca() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                BecaService becaService = BeanFactory.getBecaService();
                CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
                becaService.actualizarEstudianteBeca(estudianteBecaBean, listaCuentasPorCobrarBean);
                Integer inicio = estudianteBecaBean.getCronogramaPagoBean().getMes();
                Integer fin = estudianteBecaBean.getMesFin();
                listaCuentasPorCobrar = cuentasPorCobrarService.obtenerCuentaPorMatAnioDscBeca(estudianteBean.getPersonaBean().getIdPersona(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, inicio, fin);
                listaCuentasPorCobrarBean = new ArrayList<>();
                for (CuentasPorCobrarBean cuentas : listaCuentasPorCobrar) {
                    if (cuentas.getAnio() == anio) {
                        listaCuentasPorCobrarBean.add(cuentas);
                    }
                    List<DetDocIngresoBean> det = new ArrayList<>();
                    DocIngresoService docIngreso = BeanFactory.getDocIngresoService();
                    det = docIngreso.obtenerDetoDocIdNew(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), cuentas.getIdCtasXCobrar());
                    if (cuentas.getEstudianteBecaBean().getBecaBean().getNombre().equals(MaristaConstantes.Beca6)) {
                        docIngreso.modificarDetalleDocIngDesBecaTotal(cuentas.getIdCtasXCobrar(), cuentas.getUnidadNegocioBean().getUniNeg(), cuentas.getDsctoBeca());
                    } else if (cuentas.getFechaPago() == null) {
                        docIngreso.cambiarDsctoBeca(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), cuentas.getDsctoBeca(), cuentas.getDocIngresoBean().getIdDocIngreso());
                    } else {
                        docIngreso.modificarDetalleDocIngDesCambioBeca(cuentas.getIdCtasXCobrar(), cuentas.getUnidadNegocioBean().getUniNeg(), cuentas.getDsctoBeca(), cuentas.getDocIngresoBean().getIdDocIngreso());
                    }
                }
                obtenerEstudiantePorIdEstadoCuentaRep(estudianteBean);

                listaEstudianteBeca = becaService.obtenerTodosBecaPorEstudiante(estudianteBecaBean);
                limpiarBecaEstudiante();
//                getEstudianteBecaBean().getIdTipoMotivoBeca().setIdCodigo(MaristaConstantes.TIPO_MOT_BECA_EST);
//                getEstudianteBecaBean().getCronogramaPagoBean().setMes(MaristaConstantes.NUM_ABRIL);
//                getEstudianteBecaBean().setFechaBeca(fechaActual.getTime());
//                getEstudianteBecaBean().setMesFin(MaristaConstantes.NUM_DICIEMBRE);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarEstudianteBecaStado(Object estudianteBeca) {
        String pagina = null;
        try {
            estudianteBecaBean = (EstudianteBecaBean) estudianteBeca;
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                BecaService becaService = BeanFactory.getBecaService();
//                estudianteBecaBean.getEstudianteBean().getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//                estudianteBecaBean.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                becaService.actualizarEstudianteBecaEstado(estudianteBecaBean);
                listaEstudianteBeca = becaService.obtenerTodosBecaPorEstudiante(estudianteBecaBean);
                limpiarBecaEstudiante();
                getEstudianteBecaBean().getIdTipoMotivoBeca().setIdCodigo(MaristaConstantes.TIPO_MOT_BECA_EST);
                getEstudianteBecaBean().getCronogramaPagoBean().setMes(MaristaConstantes.NUM_ABRIL);
                getEstudianteBecaBean().setFechaBeca(fechaActual.getTime());
                getEstudianteBecaBean().setMesFin(MaristaConstantes.NUM_DICIEMBRE);
//                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void guardarEstudianteBeca() {
        try {
            if (estudianteBecaBean.getIdEstudianteBeca() == null) {
                BecaService becaService = BeanFactory.getBecaService();
                List<EstudianteBecaBean> listaBecadosActivos = new ArrayList<>();
                estudianteBecaBean.getEstudianteBean().getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                listaBecadosActivos = becaService.obtenerTodosBecaPorEstudianteActivo(estudianteBecaBean);
                for (EstudianteBecaBean estuBeca : listaBecadosActivos) {
                    becaService.actualizarEstudianteBecaEstadoOff(estuBeca);
                }
                insertarEstudianteBecaVer2();
            } else {
                modificarEstudianteBeca();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }

    }

    public String eliminarEstudianteBeca() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                BecaService becaService = BeanFactory.getBecaService();
                becaService.eliminarEstudianteBeca(estudianteBecaBean);
                listaBeca = becaService.obtenerTodos();
                limpiarBecaEstudiante();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void ponerEstadoEstBeca(Object estudianteBeca) {
        try {
            estudianteBecaBean = (EstudianteBecaBean) estudianteBeca;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String cambiarEstadoCaja() {
        String pagina = null;
        try {
            if (pagina == null) {
                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                modificarEstudianteBeca();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void rowSelect(SelectEvent event) {
        try {
            estudianteBecaBean = (EstudianteBecaBean) event.getObject();
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            EstudianteBean estudiante = new EstudianteBean();
            estudiante = estudianteBecaBean.getEstudianteBean();
            estudiante.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            estudiante.getPersonaBean().getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            estudiante.setAnio(anio);
            estudianteBean = estudianteService.obtenerEstPorId(estudiante);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void rowSelectNew(EstudianteBecaBean estudianteBeca) {
        try {
            estudianteBecaBean = (EstudianteBecaBean) estudianteBeca;
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            EstudianteBean estudiante = new EstudianteBean();
            estudiante = estudianteBecaBean.getEstudianteBean();
            estudiante.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            estudiante.getPersonaBean().getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            estudiante.setAnio(anio);
            estudianteBean = estudianteService.obtenerEstPorId(estudiante);
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            listaCuentasPorCobrar = cuentasPorCobrarService.obtenerCuentaPorMatAnio(estudianteBean.getPersonaBean().getIdPersona(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
            listaCuentasPorCobrarBean = new ArrayList<>();
            for (CuentasPorCobrarBean cuentas : listaCuentasPorCobrar) {
//                System.out.println("cta1 " + cuentas.getMeses());
                if (cuentas.getAnio().equals(anio)) {
//                    System.out.println("cta2 " + cuentas.getMeses());
                    listaCuentasPorCobrarBean.add(cuentas);
                }
            }
            for (CuentasPorCobrarBean cuentas : listaCuentasPorCobrarBean) {
                System.out.println("cta " + cuentas.getMeses());
            }
            obtenerEstudiantePorIdEstadoCuentaRep(estudianteBean);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void rowSelect2(SelectEvent event) {
        try {
            estudianteBean = (EstudianteBean) event.getObject();
            estudianteBean.getPersonaBean().setUnidadNegocioBean(estudianteBean.getUnidadNegocioBean());
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            estudianteBean.setAnio(anio);
            estudianteBean = estudianteService.obtenerEstPorId(estudianteBean);
            obtenerTodosBecaPorEstudiante();
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            listaCuentasPorCobrar = cuentasPorCobrarService.obtenerCuentaPorMatAnio(estudianteBean.getPersonaBean().getIdPersona(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
            listaCuentasPorCobrarBean = new ArrayList<>();
            for (CuentasPorCobrarBean cuentas : listaCuentasPorCobrar) {
//                System.out.println("cta1 " + cuentas.getMeses());
                if (cuentas.getAnio().equals(anio)) {
//                    System.out.println("cta2 " + cuentas.getMeses());
                    listaCuentasPorCobrarBean.add(cuentas);
                }
            }
            for (CuentasPorCobrarBean cuentas : listaCuentasPorCobrarBean) {
                System.out.println("cta " + cuentas.getMeses());
            }
            obtenerEstudiantePorIdEstadoCuentaRep(estudianteBean);
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
            estudianteBean = estudianteService.obtenerEstPorId(estudianteBean);
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

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public List<EstudianteBean> getListaEstudianteBean() {
        if (listaEstudianteBean == null) {
            listaEstudianteBean = new ArrayList<>();
        }
        return listaEstudianteBean;
    }

    public void setListaEstudianteBean(List<EstudianteBean> listaEstudianteBean) {
        this.listaEstudianteBean = listaEstudianteBean;
    }

    public EstudianteBean getEstudianteFiltroBean() {
        if (estudianteFiltroBean == null) {
            estudianteFiltroBean = new EstudianteBean();
        }
        return estudianteFiltroBean;
    }

    public void setEstudianteFiltroBean(EstudianteBean estudianteFiltroBean) {
        this.estudianteFiltroBean = estudianteFiltroBean;
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

    public PersonaBean getPersonaBean() {
        if (personaBean == null) {
            personaBean = new PersonaBean();
        }
        return personaBean;
    }

    public void setPersonaBean(PersonaBean personaBean) {
        this.personaBean = personaBean;
    }

    public List<CodigoBean> getListaDocPer() {
        if (listaDocPer == null) {
            listaDocPer = new ArrayList<>();
        }
        return listaDocPer;
    }

    public void setListaDocPer(List<CodigoBean> listaDocPer) {
        this.listaDocPer = listaDocPer;
    }

    public List<BecaBean> getListaBeca() {
        if (listaBeca == null) {
            listaBeca = new ArrayList<>();
        }
        return listaBeca;
    }

    public void setListaBeca(List<BecaBean> listaBeca) {
        this.listaBeca = listaBeca;
    }

    public EstudianteBecaBean getEstudianteBecaBean() {
        if (estudianteBecaBean == null) {
            estudianteBecaBean = new EstudianteBecaBean();
        }
        return estudianteBecaBean;
    }

    public void setEstudianteBecaBean(EstudianteBecaBean estudianteBecaBean) {
        this.estudianteBecaBean = estudianteBecaBean;
    }

    public List<Object> getListaAnos() {
        return listaAnos;
    }

    public void setListaAnos(List<Object> listaAnos) {
        this.listaAnos = listaAnos;
    }

    public List<EstudianteBecaBean> getListaEstudianteBeca() {
        if (listaEstudianteBeca == null) {
            listaEstudianteBeca = new ArrayList<>();
        }
        return listaEstudianteBeca;
    }

    public void setListaEstudianteBeca(List<EstudianteBecaBean> listaEstudianteBeca) {
        this.listaEstudianteBeca = listaEstudianteBeca;
    }

    public List<EstudianteBecaBean> getListaEstudiantesBecados() {
        return listaEstudiantesBecados;
    }

    public void setListaEstudiantesBecados(List<EstudianteBecaBean> listaEstudiantesBecados) {
        this.listaEstudiantesBecados = listaEstudiantesBecados;
    }

    public List<CodigoBean> getListaMotivoBeca() {
        if (listaMotivoBeca == null) {
            listaMotivoBeca = new ArrayList<>();
        }
        return listaMotivoBeca;
    }

    public void setListaMotivoBeca(List<CodigoBean> listaMotivoBeca) {
        this.listaMotivoBeca = listaMotivoBeca;
    }

    public List<CronogramaPagoBean> getListaCronograma() {
        if (listaCronograma == null) {
            listaCronograma = new ArrayList<>();
        }
        return listaCronograma;
    }

    public void setListaCronograma(List<CronogramaPagoBean> listaCronograma) {
        this.listaCronograma = listaCronograma;
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

    public List<CuentasPorCobrarBean> getListaCuentasPorCobrar() {
        if (listaCuentasPorCobrar == null) {
            listaCuentasPorCobrar = new ArrayList<>();
        }
        return listaCuentasPorCobrar;
    }

    public void setListaCuentasPorCobrar(List<CuentasPorCobrarBean> listaCuentasPorCobrar) {
        this.listaCuentasPorCobrar = listaCuentasPorCobrar;
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

    public CuentasPorCobrarBean getCuentasPorCobrarBean() {
        if (cuentasPorCobrarBean == null) {
            cuentasPorCobrarBean = new CuentasPorCobrarBean();
        }
        return cuentasPorCobrarBean;
    }

    public void setCuentasPorCobrarBean(CuentasPorCobrarBean cuentasPorCobrarBean) {
        this.cuentasPorCobrarBean = cuentasPorCobrarBean;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Calendar getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Calendar fechaActual) {
        this.fechaActual = fechaActual;
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

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

}
