package pe.marista.sigma.managedBean;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
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
import org.primefaces.model.DualListModel;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CentroResponsabilidadBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.PlanContableBean;
import pe.marista.sigma.bean.PreguntaBean;
import pe.marista.sigma.bean.PresupuestoNewBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.DetRegistroCompraRepBean;
import pe.marista.sigma.bean.reporte.PrespuestoCabeceraRepBean;
import pe.marista.sigma.bean.reporte.PresupuestoCentroRepBean;
import pe.marista.sigma.bean.reporte.PresupuestoCuentaRepBean;
import pe.marista.sigma.bean.reporte.PresupuestoDetalleRepBean;
import pe.marista.sigma.bean.reporte.PresupuestoNewInicioRepBean;
import pe.marista.sigma.bean.reporte.RegistroCompraRepBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CentroResponsabilidadService;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.DetRegistroCompraService;
import pe.marista.sigma.service.PlanContableService;
import pe.marista.sigma.service.PresupuestoNewService;
import pe.marista.sigma.service.RegistroCompraService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.util.MensajesBackEnd;

public class PresupuestoNewMB extends BaseMB implements Serializable {

    private PresupuestoNewBean presupuestoNewBean;
    private List<CentroResponsabilidadBean> listaCentroResponsabilidadBean;
    private Calendar fecha;
    private List<Integer> listaAnios;
    private Boolean flgTodasCuentas = false;
    private List<PlanContableBean> listaPlanContableBean;
    private PlanContableBean planContableBean;
    private List<CodigoBean> listTipoMoneda;
    private Integer idTipoMoneda = MaristaConstantes.COD_SOLES;
    private UsuarioBean usuarioLogin;
    private List<PresupuestoNewBean> listaPresupuestoNewBean;
    private Boolean flgCrActivo = false;
    //PARA ORDER AL MODO QUE REQUIERAN 
    private Map<String, Integer> listaEgreIng;
    private Map<String, Integer> listaTipoReporte;
    private List<CentroResponsabilidadBean> listaCentroResponsabilidadBeanB;

    //CR Multi
    private DualListModel<CentroResponsabilidadBean> dualCentroResponsabilidadBean;
    //Tipo Filtros
    private Map<String, Integer> listaTipoFiltro;
    private Boolean flgCuentas = false;
    private Boolean flgPorCuenta = false;
    private Boolean flgPorCentro = false;
    private Boolean flgBotonGrabar = false;
    private List<PresupuestoNewBean> listaPresupuestoNewVista;
    private Boolean flgTipoReporte;

    @PostConstruct
    public void PresupuestoNewMB() {
        try {
            CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
            CodigoService codigoService = BeanFactory.getCodigoService();
            PresupuestoNewService presupouesNewService = BeanFactory.getPresupuestoNewService();
            usuarioLogin = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            listaCentroResponsabilidadBean = centroResponsabilidadService.obtenerCentroResponsabilidadVista();
            getListTipoMoneda();
            listTipoMoneda = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_MON));
            for (CodigoBean bean : listTipoMoneda) {
                if (bean.getCodigo().equals("Soles")) {
                    getPresupuestoNewBean().getTipoMonedaBean().setIdCodigo(bean.getIdCodigo());
                    this.idTipoMoneda = bean.getIdCodigo();
                }
            }
            listaIngresosEgresos();
            listaReporte();
            listaTipoFiltro();
            presupuestoNewBean.setFlgTieneCr(false);
//            getListTipoSolicitud();
//            PlanContableService planContableService = BeanFactory.getPlanContableService();
//            getListaPlanContableBean();
//            listaPlanContableBean = planContableService.obtenerPlanContable();

            cargarAnio();
            dualCentroResponsabilidadBean = new DualListModel<>(listaCentroResponsabilidadBean, getListaCentroResponsabilidadBeanB());
            presupuestoNewBean.setAnio(presupuestoNewBean.getAnio());
            presupuestoNewBean.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            String tipoC = "";
            if (presupuestoNewBean.getEgreIng() == 1) {
                tipoC = "E";
            } else if (presupuestoNewBean.getEgreIng() == 2) {
                tipoC = "I";
            }
            presupuestoNewBean.setTipoCuenta(tipoC);
            listaPresupuestoNewVista = presupouesNewService.obtenerListaPresupuestoVista(presupuestoNewBean);
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void cargarAnio() {
        try {
            Calendar miCalendario = Calendar.getInstance();
            int inicio = MaristaConstantes.ANO_INI_DEFAULT_COLE;
            int fin = miCalendario.get(Calendar.YEAR) + 5;
            listaAnios = new ArrayList<>();
            for (int i = inicio; i <= fin; i++) {
                listaAnios.add(i);
            }
            presupuestoNewBean.setTipoCuenta("E");
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarDatos() {
        try {
            PresupuestoNewService presupouesNewService = BeanFactory.getPresupuestoNewService();
            this.fecha = new GregorianCalendar();
            getPresupuestoNewBean().setAnio(fecha.get(Calendar.YEAR));
            getPresupuestoNewBean().setFechaInicio(fecha.getTime());
            getPresupuestoNewBean().setFechaFin(fecha.getTime());
            presupuestoNewBean.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            String tipoC = "";
            if (presupuestoNewBean.getEgreIng() == 1) {
                tipoC = "E";
            } else if (presupuestoNewBean.getEgreIng() == 1) {
                tipoC = "I";
            }
            presupuestoNewBean.setTipoCuenta(tipoC);
            listaPresupuestoNewVista = presupouesNewService.obtenerListaPresupuestoVista(presupuestoNewBean);
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerCentro() {
        try {
            this.flgCuentas = true;
            presupuestoNewBean.setCuentaInicie(null);
            this.flgBotonGrabar = false;
            listaPlanContableBean = new ArrayList<>();
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerReportePorCuenta() {
        try {
            String tipoC = "";
            if (presupuestoNewBean.getEgreIng() == 1) {
                tipoC = "E";
                this.flgTipoReporte = true;
            } else if (presupuestoNewBean.getEgreIng() == 2) {
                tipoC = "I";
                this.flgTipoReporte = false;
            }
            if (presupuestoNewBean.getTipoReporte() == 1 && tipoC != null && !tipoC.equals("")) {
                this.flgPorCuenta = true;
                this.flgPorCentro = false;
            } else if (presupuestoNewBean.getTipoReporte() == 2 && tipoC != null && !tipoC.equals("")) {
                this.flgPorCuenta = false;
                this.flgPorCentro = true;
            } else {
                this.flgPorCuenta = false;
                this.flgPorCentro = false;
                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerCr() {
        try {
            this.flgCrActivo = true;
            presupuestoNewBean.setCuentaInicie(null);
            listaPlanContableBean = new ArrayList<>();
            System.out.println("sdas0 " + presupuestoNewBean.getEgreIng());
            if (presupuestoNewBean.getEgreIng() == 1) {
                presupuestoNewBean.setFlgTieneCr(true);
                this.flgCuentas = false;
            } else {
                presupuestoNewBean.setFlgTieneCr(false);
                this.flgCuentas = true;
            }
            this.flgBotonGrabar = false;

            PresupuestoNewService presupuestoNewService = BeanFactory.getPresupuestoNewService();
            presupuestoNewBean.setAnio(presupuestoNewBean.getAnio());
            presupuestoNewBean.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            String tipoC = "";
            if (presupuestoNewBean.getEgreIng() == 1) {
                tipoC = "E";
            } else if (presupuestoNewBean.getEgreIng() == 2) {
                tipoC = "I";
                presupuestoNewBean.getCentroResponsabilidadBean().setCr(null);
            }
            presupuestoNewBean.setTipoCuenta(tipoC);
            listaPresupuestoNewVista = presupuestoNewService.obtenerListaPresupuestoVista(presupuestoNewBean);
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerPorReporte() {
        try {
            System.out.println("sdas0 " + presupuestoNewBean.getEgreIng());
            if (presupuestoNewBean.getTipoReporte() == 1) {
                this.flgPorCuenta = true;
                this.flgPorCentro = false;
            } else {
                this.flgPorCuenta = false;
                this.flgPorCentro = true;
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    //Reportes
    public void imprimirPresupuestoPorCuentas() {
        ServletOutputStream out = null;
        Integer id = 0;

        try {
            PresupuestoNewService presupuestoNewService = BeanFactory.getPresupuestoNewService();

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reportePresupuestoNew.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);

            String tipoC = "";
            if (presupuestoNewBean.getEgreIng() == 1) {
                tipoC = "E";
            } else if (presupuestoNewBean.getEgreIng() == 2) {
                tipoC = "I";
            }
            List<PrespuestoCabeceraRepBean> listaCabeza = new ArrayList<>();
            listaCabeza = presupuestoNewService.obtenerCabecera(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), presupuestoNewBean.getAnio(), tipoC);
            if (!listaCabeza.isEmpty()) {
//                for (int i = 0; i < listaCabeza.size(); i++) {
                List<PresupuestoNewInicioRepBean> listaInicio = new ArrayList<>();
                listaInicio = presupuestoNewService.obtenerInicioCuentas(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), presupuestoNewBean.getAnio(), tipoC);
                listaCabeza.get(0).setListaInicio(listaInicio);
                if (!listaInicio.isEmpty()) {
                    for (int k = 0; k < listaCabeza.get(0).getListaInicio().getData().size(); k++) {
                        List<PresupuestoCuentaRepBean> listaCuentas = new ArrayList<>();
                        listaCuentas = presupuestoNewService.obtenerCuentas(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), presupuestoNewBean.getAnio(), tipoC, listaInicio.get(k).getInicio());
                        if (!listaCuentas.isEmpty()) {
                            for (int j = 0; j < listaCuentas.size(); j++) {
                                List<PresupuestoCentroRepBean> listaCentroResponsabilidad = new ArrayList<>();
                                listaCentroResponsabilidad = presupuestoNewService.obtenerCentroResp(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), presupuestoNewBean.getAnio(), tipoC, listaCuentas.get(j).getNroCuenta());
                                listaCuentas.get(j).setListaCentroResponsabilidad(listaCentroResponsabilidad);
                                listaInicio.get(k).setListaCuentas(listaCuentas);
                                listaCabeza.get(0).setListaInicio(listaInicio);
                            }
                        }
//                        }
                    }
                }
            }
            System.out.println("imagenRuta:" + absoluteWebPath);
            System.out.println("jasper: " + archivoJasper);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCabeza);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            System.out.println("Ruta:" + ruta);
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
        // Inform JSF that it doesn't need to handle response.
        // This is very important, otherwise you will get the following exception in the logs:
        // java.lang.IllegalStateException: Cannot forward after response has been committed.
        FacesContext.getCurrentInstance().responseComplete();
    }

    //Reportes
    public void imprimirPresupuestoPorCentros() {
        ServletOutputStream out = null;
        Integer id = 0;

        try {
            PresupuestoNewService presupuestoNewService = BeanFactory.getPresupuestoNewService();

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reportePresupuestoPorCentroRes.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);

            String tipoC = "";
            if (presupuestoNewBean.getEgreIng() == 1) {
                tipoC = "E";
            } else if (presupuestoNewBean.getEgreIng() == 2) {
                tipoC = "I";
            }
            List<PrespuestoCabeceraRepBean> listaCabeza = new ArrayList<>();
            listaCabeza = presupuestoNewService.obtenerCabecera(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), presupuestoNewBean.getAnio(), tipoC);

            if (!listaCabeza.isEmpty()) {
//                for (int i = 0; i < listaCabeza.size(); i++) {
                List<PresupuestoNewInicioRepBean> listaInicio = new ArrayList<>();
                listaInicio = presupuestoNewService.obtenerInicioCentroRespResp(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), presupuestoNewBean.getAnio(), tipoC);
                listaCabeza.get(0).setListaInicio(listaInicio);
                if (!listaInicio.isEmpty()) {
                    for (int k = 0; k < listaCabeza.get(0).getListaInicio().getData().size(); k++) {
                        List<PresupuestoCentroRepBean> listaCentroResponsabilidad = new ArrayList<>();
                        listaCentroResponsabilidad = presupuestoNewService.obtenerCentroRespResp(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), presupuestoNewBean.getAnio(), tipoC, listaInicio.get(k).getInicio());
                        if (!listaCentroResponsabilidad.isEmpty()) {
                            for (int j = 0; j < listaCentroResponsabilidad.size(); j++) {
                                List<PresupuestoCuentaRepBean> listaCuentas = new ArrayList<>();
                                listaCuentas = presupuestoNewService.obtenerCuentasResp(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), presupuestoNewBean.getAnio(), tipoC, listaCentroResponsabilidad.get(j).getCr());
                                listaCentroResponsabilidad.get(j).setListaCuentas(listaCuentas);

                                listaInicio.get(k).setListaCentroResponsabilidad(listaCentroResponsabilidad);
                                listaCabeza.get(0).setListaInicio(listaInicio);
                            }
                        }
//                        }
                    }
                }
            }
            System.out.println("imagenRuta:" + absoluteWebPath);
            System.out.println("jasper: " + archivoJasper);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCabeza);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            System.out.println("Ruta:" + ruta);
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
        // Inform JSF that it doesn't need to handle response.
        // This is very important, otherwise you will get the following exception in the logs:
        // java.lang.IllegalStateException: Cannot forward after response has been committed.
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void imprimirPresupuestoPorCuentasIngresos() {
        ServletOutputStream out = null;
        Integer id = 0;

        try {
            PresupuestoNewService presupuestoNewService = BeanFactory.getPresupuestoNewService();

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteIngresosPresupuesto.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);

            String tipoC = "";
            if (presupuestoNewBean.getEgreIng() == 1) {
                tipoC = "E";
            } else if (presupuestoNewBean.getEgreIng() == 2) {
                tipoC = "I";
            }
            List<PrespuestoCabeceraRepBean> listaCabeza = new ArrayList<>();
            listaCabeza = presupuestoNewService.obtenerCabeceraIngresos(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), presupuestoNewBean.getAnio(), tipoC);

            System.out.println("imagenRuta:" + absoluteWebPath);
            System.out.println("jasper: " + archivoJasper);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCabeza);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            System.out.println("Ruta:" + ruta);
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
        // Inform JSF that it doesn't need to handle response.
        // This is very important, otherwise you will get the following exception in the logs:
        // java.lang.IllegalStateException: Cannot forward after response has been committed.
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void obtenerCuentasInicio() {
        try {
            PlanContableService planContableService = BeanFactory.getPlanContableService();
            PresupuestoNewService presupuestoNewService = BeanFactory.getPresupuestoNewService();
            if (flgTodasCuentas == true) {
                listaPlanContableBean = planContableService.obtenerPlanContable();
                presupuestoNewBean.setCuentaInicie(null);
                for (PlanContableBean pl : listaPlanContableBean) {
                    PresupuestoNewBean presupuesto = new PresupuestoNewBean();
                    presupuesto.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    presupuesto.getCentroResponsabilidadBean().setCr(presupuesto.getCentroResponsabilidadBean().getCr());
                    presupuesto.getPlanContableBean().setCuenta(pl.getCuenta());
                    listaPresupuestoNewBean = presupuestoNewService.obtenerListaPresupuesto(presupuesto);
                    for (PresupuestoNewBean pres : listaPresupuestoNewBean) {
                        pl.setValorPresu(pres.getPresupuestoProg());
                    }
                }

            } else {
                System.out.println("No son todas xD");
                listaPlanContableBean = planContableService.obtenerPlanFiltroCuenta(presupuestoNewBean.getCuentaInicie());
                for (PlanContableBean pl : listaPlanContableBean) {
                    PresupuestoNewBean presupuesto = new PresupuestoNewBean();
                    presupuesto.setAnio(presupuestoNewBean.getAnio());
                    presupuesto.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    presupuesto.getCentroResponsabilidadBean().setCr(presupuestoNewBean.getCentroResponsabilidadBean().getCr());
                    presupuesto.getPlanContableBean().setCuenta(pl.getCuenta());
                    listaPresupuestoNewBean = presupuestoNewService.obtenerListaPresupuesto(presupuesto);
                    for (PresupuestoNewBean pres : listaPresupuestoNewBean) {
                        pl.setValorPresu(pres.getPresupuestoProg());
                    }
                }
            }
            this.flgBotonGrabar = true;
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void ValidarBotonGra() {
        try {
            this.flgBotonGrabar = true;
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void grabarPresupuestoCrCuentas() {
        try {
            PresupuestoNewService presupuestoNewService = BeanFactory.getPresupuestoNewService();
            PresupuestoNewBean presupuesto = new PresupuestoNewBean();

            for (PlanContableBean plan : listaPlanContableBean) {
                if (plan.getValorPresu() != null && !plan.getValorPresu().equals("") && !plan.getValorPresu().equals(0.0)) {
                    presupuesto.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    if (presupuestoNewBean.getEgreIng() == 1) {
                        presupuesto.setTipoCuenta("E");
                        presupuesto.setFlgTieneCr(true);
                    } else {
                        presupuesto.setTipoCuenta("I");
                        presupuesto.setFlgTieneCr(false);
                    }
                    presupuesto.getTipoMonedaBean().setIdCodigo(presupuestoNewBean.getTipoMonedaBean().getIdCodigo());
                    presupuesto.getCentroResponsabilidadBean().setCr(presupuestoNewBean.getCentroResponsabilidadBean().getCr());
                    presupuesto.setAnio(presupuestoNewBean.getAnio());
                    presupuesto.getPlanContableBean().setCuenta(plan.getCuenta());
//                    presupuesto.setPresupuestoProg(plan.getValorPresu());
                    listaPresupuestoNewBean = presupuestoNewService.obtenerListaPresupuesto(presupuesto);
                    if (listaPresupuestoNewBean.size() > 0) {
                        for (PresupuestoNewBean pre : listaPresupuestoNewBean) {
                            if (presupuesto.getTipoCuenta().equals("E")
                                    && presupuesto.getAnio().equals(pre.getAnio())
                                    && pre.getUnidadNegocioBean().getUniNeg().equals(presupuesto.getUnidadNegocioBean().getUniNeg())
                                    && pre.getPlanContableBean().getCuenta().equals(presupuesto.getPlanContableBean().getCuenta())
                                    && pre.getCentroResponsabilidadBean().getCr().equals(presupuesto.getCentroResponsabilidadBean().getCr())) {
                                System.out.println("son iguales");
                                presupuesto.setPresupuestoProg(plan.getValorPresu());
                                presupuesto.setModiPor(usuarioLogin.getUsuario());
                                presupuesto.setIdPresupuesto(pre.getIdPresupuesto());
                                presupuestoNewService.modificarPresupuestoCrCuentas(presupuesto);
                            } else if (presupuesto.getTipoCuenta().equals("I")
                                    && presupuesto.getAnio().equals(pre.getAnio())
                                    && pre.getUnidadNegocioBean().getUniNeg().equals(presupuesto.getUnidadNegocioBean().getUniNeg())
                                    && pre.getPlanContableBean().getCuenta().equals(presupuesto.getPlanContableBean().getCuenta())) {
                                presupuesto.setPresupuestoProg(plan.getValorPresu());
                                presupuesto.setModiPor(usuarioLogin.getUsuario());
                                presupuesto.setIdPresupuesto(pre.getIdPresupuesto());
                                presupuestoNewService.modificarPresupuestoCrCuentas(presupuesto);
                            }
                        }
                    } else {
                        System.out.println("no son iguales");
                        presupuesto.setPresupuestoProg(plan.getValorPresu());
                        presupuesto.setCreaPor(usuarioLogin.getUsuario());
                        presupuestoNewService.insertarPresupuestoCrCuentas(presupuesto);
                    }

                } else {
                    presupuesto.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    presupuesto.getCentroResponsabilidadBean().setCr(presupuestoNewBean.getCentroResponsabilidadBean().getCr());
                    presupuesto.setAnio(presupuestoNewBean.getAnio());
                    presupuesto.getPlanContableBean().setCuenta(plan.getCuenta());
                    listaPresupuestoNewBean = presupuestoNewService.obtenerListaPresupuesto(presupuesto);
                    if (listaPresupuestoNewBean.size() > 0) {
                        for (PresupuestoNewBean pre : listaPresupuestoNewBean) {
                            presupuesto.setPresupuestoProg(plan.getValorPresu());
                            presupuesto.setModiPor(usuarioLogin.getUsuario());
                            presupuesto.setIdPresupuesto(pre.getIdPresupuesto());
                            if (presupuesto.getPresupuestoProg() == null || presupuesto.getPresupuestoProg().equals(0.0) || presupuesto.getPresupuestoProg().equals("")) {
                                presupuestoNewService.eliminarPresupuestoCrCuentas(presupuesto);
                                System.out.println("Se va a eliminar: " + presupuesto.getIdPresupuesto());
                            } else {
                                System.out.println("No hay nada pára eliminar");
                            }
                        }
                    }

                }
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
                listaPlanContableBean = new ArrayList<>();
                listaCentroResponsabilidadBean = new ArrayList<>();
                listaCentroResponsabilidadBean = centroResponsabilidadService.obtenerCentroResponsabilidadVista();
                presupuesto.getCentroResponsabilidadBean().setCr(null);
                presupuestoNewBean.setCuentaInicie(null);

            }
            PresupuestoNewService presupouesNewService = BeanFactory.getPresupuestoNewService();
            presupuestoNewBean.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            String tipoC = "";
            if (presupuestoNewBean.getEgreIng() == 1) {
                tipoC = "E";
            } else if (presupuestoNewBean.getEgreIng() == 2) {
                tipoC = "I";
            }
            presupuestoNewBean.setTipoCuenta(tipoC);
            listaPresupuestoNewVista = presupouesNewService.obtenerListaPresupuestoVista(presupuestoNewBean);
            this.flgBotonGrabar = false;
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void generarPorCentroResp() {
        try {
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void generarPorCuenta() {
        try {
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void limpiarPorCentroResp() {
        try {
            listaCentroResponsabilidadBeanB = new ArrayList<>();
            dualCentroResponsabilidadBean = new DualListModel<>(listaCentroResponsabilidadBean, getListaCentroResponsabilidadBeanB());
            cargarDatos();
            presupuestoNewBean.setTipoFiltro(null);
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void limpiarPorCuenta() {
        try {
            cargarDatos();
            presupuestoNewBean.setTipoFiltro(null);
            presupuestoNewBean.setCuentaInicie(null);
            this.flgTodasCuentas = false;
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void listaIngresosEgresos() {
        listaEgreIng = new LinkedHashMap<>();
        listaEgreIng.put(MensajesBackEnd.getValueOfKey("etiquetaEgreso", null), 1);
        listaEgreIng.put(MensajesBackEnd.getValueOfKey("etiquetaIngreso", null), 2);
        listaEgreIng = Collections.unmodifiableMap(listaEgreIng);
    }

    public void listaReporte() {
        listaTipoReporte = new LinkedHashMap<>();
        if (presupuestoNewBean.getEgreIng() == 2 || presupuestoNewBean.getEgreIng() == 1) {
            listaTipoReporte.put(MensajesBackEnd.getValueOfKey("etiquetaReporteCuenta", null), 1);
            if (presupuestoNewBean.getEgreIng() == 1) {
                listaTipoReporte.put(MensajesBackEnd.getValueOfKey("etiquetaReporteCentro", null), 2);
            }
        }
        listaTipoReporte = Collections.unmodifiableMap(listaTipoReporte);
    }

    public void cargarReporte() {
        listaReporte();
        presupuestoNewBean.setTipoReporte(null);
        flgTipoReporte = null;
        flgPorCuenta = null;
    }

    public void listaTipoFiltro() {
        listaTipoFiltro = new LinkedHashMap<>();
        listaTipoFiltro.put(MensajesBackEnd.getValueOfKey("etiquetaConsolidado", null), 3);
        listaTipoFiltro.put(MensajesBackEnd.getValueOfKey("etiquetaDetalleFull", null), 4);
        listaTipoFiltro = Collections.unmodifiableMap(listaTipoFiltro);
    }

    public void ponerMonto() {
        try {

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public PresupuestoNewBean getPresupuestoNewBean() {
        if (presupuestoNewBean == null) {
            presupuestoNewBean = new PresupuestoNewBean();
        }
        return presupuestoNewBean;
    }

    public void setPresupuestoNewBean(PresupuestoNewBean presupuestoNewBean) {
        this.presupuestoNewBean = presupuestoNewBean;
    }

    public List<CentroResponsabilidadBean> getListaCentroResponsabilidadBean() {
        if (listaCentroResponsabilidadBean == null) {
            listaCentroResponsabilidadBean = new ArrayList<>();
        }
        return listaCentroResponsabilidadBean;
    }

    public void setListaCentroResponsabilidadBean(List<CentroResponsabilidadBean> listaCentroResponsabilidadBean) {
        this.listaCentroResponsabilidadBean = listaCentroResponsabilidadBean;
    }

    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }

    public List<Integer> getListaAnios() {
        return listaAnios;
    }

    public void setListaAnios(List<Integer> listaAnios) {
        this.listaAnios = listaAnios;
    }

    public Boolean getFlgTodasCuentas() {
        return flgTodasCuentas;
    }

    public void setFlgTodasCuentas(Boolean flgTodasCuentas) {
        this.flgTodasCuentas = flgTodasCuentas;
    }

    public List<PlanContableBean> getListaPlanContableBean() {
        if (listaPlanContableBean == null) {
            listaPlanContableBean = new ArrayList<>();
        }
        return listaPlanContableBean;
    }

    public void setListaPlanContableBean(List<PlanContableBean> listaPlanContableBean) {
        this.listaPlanContableBean = listaPlanContableBean;
    }

    public PlanContableBean getPlanContableBean() {
        if (planContableBean == null) {
            planContableBean = new PlanContableBean();
        }
        return planContableBean;
    }

    public void setPlanContableBean(PlanContableBean planContableBean) {
        this.planContableBean = planContableBean;
    }

    public List<CodigoBean> getListTipoMoneda() {
        if (listTipoMoneda == null) {
            listTipoMoneda = new ArrayList<>();
        }
        return listTipoMoneda;
    }

    public void setListTipoMoneda(List<CodigoBean> listTipoMoneda) {
        this.listTipoMoneda = listTipoMoneda;
    }

    public Integer getIdTipoMoneda() {
        return idTipoMoneda;
    }

    public void setIdTipoMoneda(Integer idTipoMoneda) {
        this.idTipoMoneda = idTipoMoneda;
    }

    public List<PresupuestoNewBean> getListaPresupuestoNewBean() {
        if (listaPresupuestoNewBean == null) {
            listaPresupuestoNewBean = new ArrayList<>();
        }
        return listaPresupuestoNewBean;
    }

    public void setListaPresupuestoNewBean(List<PresupuestoNewBean> listaPresupuestoNewBean) {
        this.listaPresupuestoNewBean = listaPresupuestoNewBean;
    }

    public Boolean getFlgCrActivo() {
        return flgCrActivo;
    }

    public void setFlgCrActivo(Boolean flgCrActivo) {
        this.flgCrActivo = flgCrActivo;
    }

    public Map<String, Integer> getListaEgreIng() {
        return listaEgreIng;
    }

    public void setListaEgreIng(Map<String, Integer> listaEgreIng) {
        this.listaEgreIng = listaEgreIng;
    }

    public DualListModel<CentroResponsabilidadBean> getDualCentroResponsabilidadBean() {
        if (dualCentroResponsabilidadBean == null) {
            dualCentroResponsabilidadBean = new DualListModel<>();
        }
        return dualCentroResponsabilidadBean;
    }

    public void setDualCentroResponsabilidadBean(DualListModel<CentroResponsabilidadBean> dualCentroResponsabilidadBean) {
        this.dualCentroResponsabilidadBean = dualCentroResponsabilidadBean;
    }

    public List<CentroResponsabilidadBean> getListaCentroResponsabilidadBeanB() {
        if (listaCentroResponsabilidadBeanB == null) {
            listaCentroResponsabilidadBeanB = new ArrayList<>();
        }
        return listaCentroResponsabilidadBeanB;
    }

    public void setListaCentroResponsabilidadBeanB(List<CentroResponsabilidadBean> listaCentroResponsabilidadBeanB) {
        this.listaCentroResponsabilidadBeanB = listaCentroResponsabilidadBeanB;
    }

    public Map<String, Integer> getListaTipoFiltro() {
        return listaTipoFiltro;
    }

    public void setListaTipoFiltro(Map<String, Integer> listaTipoFiltro) {
        this.listaTipoFiltro = listaTipoFiltro;
    }

    public Boolean getFlgCuentas() {
        return flgCuentas;
    }

    public void setFlgCuentas(Boolean flgCuentas) {
        this.flgCuentas = flgCuentas;
    }

    public Boolean getFlgBotonGrabar() {
        return flgBotonGrabar;
    }

    public void setFlgBotonGrabar(Boolean flgBotonGrabar) {
        this.flgBotonGrabar = flgBotonGrabar;
    }

    public List<PresupuestoNewBean> getListaPresupuestoNewVista() {
        if (listaPresupuestoNewVista == null) {
            listaPresupuestoNewVista = new ArrayList<>();
        }
        return listaPresupuestoNewVista;
    }

    public void setListaPresupuestoNewVista(List<PresupuestoNewBean> listaPresupuestoNewVista) {
        this.listaPresupuestoNewVista = listaPresupuestoNewVista;
    }

    public Boolean getFlgPorCuenta() {
        return flgPorCuenta;
    }

    public void setFlgPorCuenta(Boolean flgPorCuenta) {
        this.flgPorCuenta = flgPorCuenta;
    }

    public Boolean getFlgPorCentro() {
        return flgPorCentro;
    }

    public void setFlgPorCentro(Boolean flgPorCentro) {
        this.flgPorCentro = flgPorCentro;
    }

    public Map<String, Integer> getListaTipoReporte() {
        return listaTipoReporte;
    }

    public void setListaTipoReporte(Map<String, Integer> listaTipoReporte) {
        this.listaTipoReporte = listaTipoReporte;
    }

    public Boolean getFlgTipoReporte() {
        return flgTipoReporte;
    }

    public void setFlgTipoReporte(Boolean flgTipoReporte) {
        this.flgTipoReporte = flgTipoReporte;
    }

}
