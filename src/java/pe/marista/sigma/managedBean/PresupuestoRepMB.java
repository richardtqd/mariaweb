/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.metamodel.ListAttribute;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import org.primefaces.model.DualListModel;
import pe.marista.sigma.bean.CentroResponsabilidadBean;
import pe.marista.sigma.bean.DetPresupuestoNewBean;
import pe.marista.sigma.bean.PlanContableBean;
import pe.marista.sigma.bean.PresupuestoNewBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.PresupuestoDetalleRepBean;
import pe.marista.sigma.bean.reporte.PresupuestoExportRepBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CentroResponsabilidadService;
import pe.marista.sigma.service.PlanContableService;
import pe.marista.sigma.service.PresupuestoNewService;
import pe.marista.sigma.service.UsuarioService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author MS-001
 */
public class PresupuestoRepMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of PresupuestoRepMB
     */
    @PostConstruct
    public void PresupuestoRepMB() {
        try {
            usuarioLogin = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    private UsuarioBean usuarioLogin;
    private PresupuestoNewBean presupuestoNewBean;
    private List<PresupuestoNewBean> listaPresupuestoNewBean;
    private List<DetPresupuestoNewBean> listaDetallePresupuestoNewBean;
    private List<DetPresupuestoNewBean> listaDetallePresupuestoNewBeanMes;
    private Double totalPresupuestado = new Double("0.0");
    private Double totalEjecutado = new Double("0.0");
    private Double totalSaldo = new Double("0.0");
    private Double porcEjecutado;
    private Double porcSaldo;

    //rangos de fecha
    private Date fechaInicio;
    private Date fechaFin;

    //acceso
    private Integer periodo;
    private Integer tipoReporte = 1;//1: Resumen,2:Detalle
    private List<Integer> listaTipoAccesoUsu;
    private DualListModel<CentroResponsabilidadBean> dualCentroResponsabilidad;
    private DualListModel<PlanContableBean> dualCuenta;
    private DualListModel<PlanContableBean> dualCuentaIngresos;
    private Integer verPor = 1; //1:CR, 2:CC
    private Boolean pickCR = Boolean.TRUE;
    private Boolean pickCC = Boolean.TRUE;

    //ayuda
    private List<PresupuestoDetalleRepBean> listaDetalle;

    //Ayuda Exclusivo 
    private List<PresupuestoNewBean> listaPresupjuestoMantenimientoExclusivo;
    private Boolean flgExclusivo = false;
    private Boolean flgDisable = false;

    public void calculateTotalPorCr(Integer cr) {
        if (cr != null) {
            this.totalPresupuestado = 0.0d;
            this.totalEjecutado = 0.0d;
            this.totalSaldo = 0.0d;
            Double porceEje = 0.0;
            Double porceSal = 0.0;
            Integer count = 0;
            for (PresupuestoNewBean list : listaPresupuestoNewBean) {
                if (list.getCentroResponsabilidadBean().getCr().equals(cr)) {
                    count = count + 1;
                    if (list.getPresupuestoProg() == null) {
                        list.setPresupuestoProg(0.0);
                    }
                    if (list.getPresupuestoEje() == null) {
                        list.setPresupuestoEje(0.0);
                    }
                    if (list.getSaldo() == null) {
                        list.setSaldo(0.0);
                    }
                    if (list.getPorcEje() == null) {
                        list.setPorcEje(0.0);
                    }
                    if (list.getPorcSaldo() == null) {
                        list.setPorcSaldo(0.0);
                    }
                    this.totalPresupuestado = this.totalPresupuestado + list.getPresupuestoProg();
                    this.totalEjecutado = this.totalEjecutado + list.getPresupuestoEje();
                    this.totalSaldo = this.totalSaldo + list.getSaldo();
                    porceEje = porceEje + list.getPorcEje();
                    porceSal = porceSal + list.getPorcSaldo();
                }
            }
            if (this.totalPresupuestado > 0) {
                this.porcEjecutado = (this.totalEjecutado * 100) / this.totalPresupuestado;
                this.porcEjecutado = (double) Math.round(porcEjecutado * 100) / 100;
                this.porcSaldo = (double) Math.round((100 - porcEjecutado) * 100) / 100;
            } else {
                this.porcEjecutado = new Double("0.0");
                this.porcSaldo = new Double("0.0");
            }

        }
    }

    public void calculateTotalPorCrExclusivo(String cr) {
        this.totalPresupuestado = 0.0d;
        this.totalEjecutado = 0.0d;
        this.totalSaldo = 0.0d;
        Double porceEje = 0.0;
        Double porceSal = 0.0;
        Integer count = 0;
        Integer crAyuda = 0;
        for (PresupuestoNewBean list : listaPresupuestoNewBean) {
            if (list.getCentroResponsabilidadBean().getCr().toString().equals(cr)) {
                count = count + 1;
                if (list.getPresupuestoProg() == null) {
                    list.setPresupuestoProg(0.0);
                }
                if (list.getPresupuestoEje() == null) {
                    list.setPresupuestoEje(0.0);
                }
                if (list.getSaldo() == null) {
                    list.setSaldo(0.0);
                }
                if (list.getPorcEje() == null) {
                    list.setPorcEje(0.0);
                }
                if (list.getPorcSaldo() == null) {
                    list.setPorcSaldo(0.0);
                }
                this.totalPresupuestado = this.totalPresupuestado + list.getPresupuestoProg();
                this.totalEjecutado = this.totalEjecutado + list.getPresupuestoEje();
                this.totalSaldo = this.totalSaldo + list.getSaldo();
                porceEje = porceEje + list.getPorcEje();
                porceSal = porceSal + list.getPorcSaldo();

                if (this.totalPresupuestado > 0) {
                    this.porcEjecutado = (this.totalEjecutado * 100) / this.totalPresupuestado;
                    this.porcEjecutado = (double) Math.round(porcEjecutado * 100) / 100;
                    this.porcSaldo = (double) Math.round((100 - porcEjecutado) * 100) / 100;
                } else {
                    this.porcEjecutado = new Double("0.0");
                    this.porcSaldo = new Double("0.0");
                }

                list.setPresupuestoProgramadoVista(totalPresupuestado);
                list.setPresupuestoEjeVista(totalEjecutado);
                list.setPorcEjeVista(porcEjecutado);
                list.setSaldoVista(totalSaldo);
                list.setPorcSaldoVista(porcSaldo);
                if (!list.getCentroResponsabilidadBean().getCr().equals(crAyuda)) {
                    listaPresupjuestoMantenimientoExclusivo.add(list);
                }

                if (crAyuda != 0) {
                    crAyuda = list.getCentroResponsabilidadBean().getCr();
                }
                for (PresupuestoNewBean pre : listaPresupjuestoMantenimientoExclusivo) {
                    if (pre.getCentroResponsabilidadBean().getCr().equals(crAyuda)) {
                        pre.setPresupuestoProgramadoVista(totalPresupuestado);
                        pre.setPresupuestoEjeVista(totalEjecutado);
                        pre.setPorcEjeVista(porcEjecutado);
                        pre.setSaldoVista(totalSaldo);
                        pre.setPorcSaldoVista(porcSaldo);
                    }
                }
                crAyuda = list.getCentroResponsabilidadBean().getCr();
            }
        }
    }

    public void calculateTotalPorCCExclusivo(String cc) {
        this.totalPresupuestado = 0.0d;
        this.totalEjecutado = 0.0d;
        this.totalSaldo = 0.0d;
        Double porceEje = 0.0;
        Double porceSal = 0.0;
        Integer count = 0;
        Integer ccAyuda = 0;
        for (PresupuestoNewBean list : listaPresupuestoNewBean) {
            if (list.getPlanContableBean().getCuenta().toString().equals(cc)) {
                count = count + 1;
                if (list.getPresupuestoProg() == null) {
                    list.setPresupuestoProg(0.0);
                }
                if (list.getPresupuestoEje() == null) {
                    list.setPresupuestoEje(0.0);
                }
                if (list.getSaldo() == null) {
                    list.setSaldo(0.0);
                }
                if (list.getPorcEje() == null) {
                    list.setPorcEje(0.0);
                }
                if (list.getPorcSaldo() == null) {
                    list.setPorcSaldo(0.0);
                }
                this.totalPresupuestado = this.totalPresupuestado + list.getPresupuestoProg();
                this.totalEjecutado = this.totalEjecutado + list.getPresupuestoEje();
                this.totalSaldo = this.totalSaldo + list.getSaldo();
                porceEje = porceEje + list.getPorcEje();
                porceSal = porceSal + list.getPorcSaldo();

                if (this.totalPresupuestado > 0) {
                    this.porcEjecutado = (this.totalEjecutado * 100) / this.totalPresupuestado;
                    this.porcEjecutado = (double) Math.round(porcEjecutado * 100) / 100;
                    this.porcSaldo = (double) Math.round((100 - porcEjecutado) * 100) / 100;
                } else {
                    this.porcEjecutado = new Double("0.0");
                    this.porcSaldo = new Double("0.0");
                }

                list.setPresupuestoProgramadoVista(totalPresupuestado);
                list.setPresupuestoEjeVista(totalEjecutado);
                list.setPorcEjeVista(porcEjecutado);
                list.setSaldoVista(totalSaldo);
                list.setPorcSaldoVista(porcSaldo);
                if (!list.getPlanContableBean().getCuenta().equals(ccAyuda)) {
                    listaPresupjuestoMantenimientoExclusivo.add(list);
                }

                if (ccAyuda != 0) {
                    ccAyuda = list.getPlanContableBean().getCuenta();
                }
                for (PresupuestoNewBean pre : listaPresupjuestoMantenimientoExclusivo) {
                    if (pre.getPlanContableBean().getCuenta().equals(ccAyuda)) {
                        pre.setPresupuestoProgramadoVista(totalPresupuestado);
                        pre.setPresupuestoEjeVista(totalEjecutado);
                        pre.setPorcEjeVista(porcEjecutado);
                        pre.setSaldoVista(totalSaldo);
                        pre.setPorcSaldoVista(porcSaldo);
                    }
                }
                ccAyuda = list.getPlanContableBean().getCuenta();
            }
        }
    }

    public void cargaFormularioReporte() {
        cargarFiltros();
        obtenerFechaFiltro();
    }

    public void calculateTotalPorCuenta(Integer cc) {
        if (cc != null) {
            this.totalPresupuestado = 0.0d;
            this.totalEjecutado = 0.0d;
            this.totalSaldo = 0.0d;
            Double porceEje = 0.0;
            Double porceSal = 0.0;
            Integer count = 0;
            for (PresupuestoNewBean list : listaPresupuestoNewBean) {
                if (list.getPlanContableBean().getCuenta().equals(cc)) {
                    count = count + 1;
                    if (list.getPresupuestoProg() == null) {
                        list.setPresupuestoProg(0.0);
                    }
                    if (list.getPresupuestoEje() == null) {
                        list.setPresupuestoEje(0.0);
                    }
                    if (list.getSaldo() == null) {
                        list.setSaldo(0.0);
                    }
                    if (list.getPorcEje() == null) {
                        list.setPorcEje(0.0);
                    }
                    if (list.getPorcSaldo() == null) {
                        list.setPorcSaldo(0.0);
                    }
                    this.totalPresupuestado = this.totalPresupuestado + list.getPresupuestoProg();
                    this.totalEjecutado = this.totalEjecutado + list.getPresupuestoEje();
                    this.totalSaldo = this.totalSaldo + list.getSaldo();
                    porceEje = porceEje + list.getPorcEje();
                    porceSal = porceSal + list.getPorcSaldo();
                }
            }
            if (this.totalPresupuestado > 0) {
                this.porcEjecutado = (this.totalEjecutado * 100) / this.totalPresupuestado;
                this.porcEjecutado = (double) Math.round(porcEjecutado * 100) / 100;
                this.porcSaldo = (double) Math.round((100 - porcEjecutado) * 100) / 100;
            } else {
                this.porcEjecutado = new Double("0.0");
                this.porcSaldo = new Double("0.0");
            }

        }
    }

    public void calculateTotalCRyCuenta(DetPresupuestoNewBean detPresu) throws Exception {
        if (detPresu.getCrCuenta() != null) {
            this.totalPresupuestado = 0.0d;
            this.totalEjecutado = 0.0d;
            this.totalSaldo = 0.0d;

            PresupuestoNewService presupuestoNewService = BeanFactory.getPresupuestoNewService();

            PresupuestoNewBean presu = new PresupuestoNewBean();
            presu.getUnidadNegocioBean().setUniNeg(detPresu.getUniNeg());
            presu.getCentroResponsabilidadBean().setCr(detPresu.getCr());
            presu.getPlanContableBean().setCuenta(detPresu.getCuenta());
            presu.setAnio(detPresu.getAnio());
            presu = presupuestoNewService.SP_obtenerPresupuestoPorId(presu, fechaInicio, fechaFin);
            if (presu != null) {
                this.totalPresupuestado = presu.getPresupuestoProg();
                this.totalEjecutado = presu.getPresupuestoEje();
                this.totalSaldo = presu.getSaldo();

                this.porcEjecutado = presu.getPorcEje();
                this.porcSaldo = presu.getPorcSaldo();
            } else {
                for (DetPresupuestoNewBean list : listaDetallePresupuestoNewBean) {
                    if (list.getCrCuenta().equals(detPresu.getCrCuenta())) {
                        if (list.getValorSoles() == null) {
                            list.setValorSoles(0.0);
                        }
                        this.totalEjecutado = this.totalEjecutado + list.getValorSoles();
                    }
                }
                this.totalPresupuestado = new Double("0.0");
                this.totalSaldo = totalEjecutado * -1;
                this.porcEjecutado = new Double("0.0");
                this.porcSaldo = new Double("0.0");
            }
        }
    }

    public void obtenerDetalleGastosCrCuenta(PresupuestoNewBean presupuesto) {
        try {
            PresupuestoNewService presupuestoNewService = BeanFactory.getPresupuestoNewService();
            presupuestoNewBean = presupuestoNewService.SP_obtenerPresupuestoPorId(presupuesto, fechaInicio, fechaFin);
            listaDetallePresupuestoNewBean = presupuestoNewService.SP_obtenerDetalleGastosCrCuenta(presupuesto, fechaInicio, fechaFin);
            Double eje = new Double("0.0");
            for (DetPresupuestoNewBean list : listaDetallePresupuestoNewBean) {
                if (list.getValorSoles() == null) {
                    list.setValorSoles(0.0);
                }
                list.setCuenta(presupuesto.getPlanContableBean().getCuenta());
                list.setNombreCuenta(presupuesto.getPlanContableBean().getNombre());
                list.setCr(presupuesto.getCentroResponsabilidadBean().getCr());
                list.setNombreCr(presupuesto.getCentroResponsabilidadBean().getNombre());

                eje = eje + list.getValorSoles();
            }
            if (presupuestoNewBean != null) {
            } else {
                presupuestoNewBean = new PresupuestoNewBean();
                presupuestoNewBean.setPresupuestoProg(new Double("0.0"));
                presupuestoNewBean.setPresupuestoEje(eje);
                presupuestoNewBean.setSaldo(eje * -1);
                presupuestoNewBean.setPorcEje(new Double("0.0"));
                presupuestoNewBean.setPorcSaldo(new Double("0.0"));
                presupuestoNewBean.setCentroResponsabilidadBean(presupuesto.getCentroResponsabilidadBean());
                presupuestoNewBean.setPlanContableBean(presupuesto.getPlanContableBean());
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarFiltros() {
        try {
            //listas de filtros
            Calendar miCalendario = Calendar.getInstance();
            periodo = miCalendario.get(Calendar.YEAR);
            UsuarioService usuarioService = BeanFactory.getUsuarioService();
            listaTipoAccesoUsu = usuarioService.obtenerTipoNivelAccesoPorUsuario(usuarioLogin.getUsuario(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (listaTipoAccesoUsu.isEmpty()) {
                listaTipoAccesoUsu.add(0);
            }
            List<CentroResponsabilidadBean> listaCROrigen = new ArrayList<>();
            List<CentroResponsabilidadBean> listaCRDestino = new ArrayList<>();

            CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
            listaCROrigen = centroResponsabilidadService.obtenerInCrAccesoPorNivel(listaTipoAccesoUsu, periodo, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            PlanContableService planContableService = BeanFactory.getPlanContableService();
            List<PlanContableBean> listaCuentaOrigen = new ArrayList<>();
            listaCuentaOrigen = planContableService.obtenerInCuentaAccesoPorNivel(listaTipoAccesoUsu, periodo, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            List<PlanContableBean> listaCuentaDestino = new ArrayList<>();

            dualCentroResponsabilidad = new DualListModel<>(listaCROrigen, listaCRDestino);
            dualCuenta = new DualListModel<>(listaCuentaOrigen, listaCuentaDestino);

            List<PlanContableBean> listaCuentaOrigenIngresos = new ArrayList<>();
            listaCuentaOrigenIngresos = planContableService.obtenerPlanContableIngresos();
            List<PlanContableBean> listaCuentaDestinoIngresos = new ArrayList<>();
            dualCuentaIngresos = new DualListModel<>(listaCuentaOrigenIngresos, listaCuentaDestinoIngresos);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarDetalle() {
        try {
            PresupuestoNewService presupuestoNewService = BeanFactory.getPresupuestoNewService();
            List<PresupuestoDetalleRepBean> listaCabeza = new ArrayList<>();
            listaDetalle = new ArrayList<>();
            listaCabeza = presupuestoNewService.obtenerTituloDetalle(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), periodo);
            if (!listaCabeza.isEmpty()) {
                for (int j = 0; j < listaCabeza.size(); j++) {
                    List<PresupuestoDetalleRepBean> listaEjecutado = new ArrayList<>();
                    listaEjecutado = presupuestoNewService.obtenerEjecutado(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), periodo, listaCabeza.get(j).getCuenta());
                    listaCabeza.get(j).setMontoUtilizado(listaEjecutado.get(0).getMontoUtilizado());
                    if (!listaEjecutado.isEmpty()) {
                        List<PresupuestoDetalleRepBean> listaUtilizado = new ArrayList<>();
                        listaUtilizado = presupuestoNewService.obtenerUtilizado(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), periodo, listaCabeza.get(j).getCuenta());
                        listaCabeza.get(j).setPresupuestoUtilizado(listaUtilizado.get(0).getPresupuestoUtilizado());
                        if (!listaUtilizado.isEmpty()) {
                            List<PresupuestoDetalleRepBean> listaDisponible = new ArrayList<>();
                            listaDisponible = presupuestoNewService.obtenerDisponible(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), periodo, listaCabeza.get(j).getCuenta());
                            listaCabeza.get(j).setPresupuestoDisponible(listaDisponible.get(0).getPresupuestoDisponible());
                            listaCabeza.get(j).setListaDisponible(listaDisponible);
                            listaCabeza.get(j).setListaUtilizado(listaUtilizado);
                            listaCabeza.get(j).setListaEjecutado(listaEjecutado);
                            listaDetalle.add(listaCabeza.get(j));
                        }
                    }
                }
            }
            for (PresupuestoDetalleRepBean l : listaDetalle) {

            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarConsolidado() {
        try {
            PresupuestoNewService presupuestoNewService = BeanFactory.getPresupuestoNewService();
            PresupuestoNewBean presupuesto = new PresupuestoNewBean();
            presupuesto.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            presupuesto.setAnio(periodo);
            listaDetallePresupuestoNewBean = presupuestoNewService.SP_obtenerConsolidado(presupuesto, fechaInicio, fechaFin);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void imprimirTodosPdf() {
        ServletOutputStream out = null;
        Integer id = 0;

        try {
            PresupuestoNewService presupuestoNewService = BeanFactory.getPresupuestoNewService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteDetallePresupuesto.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);

            List<PresupuestoDetalleRepBean> listaCabeza = new ArrayList<>();
            listaCabeza = presupuestoNewService.obtenerTituloDetalle(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), periodo);
            Double montoAyuda = 0.00;
            if (!listaCabeza.isEmpty()) {
                for (int j = 0; j < listaCabeza.size(); j++) {
                    List<PresupuestoDetalleRepBean> listaEjecutado = new ArrayList<>();
                    listaEjecutado = presupuestoNewService.obtenerEjecutado(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), periodo, listaCabeza.get(j).getCuenta());
                    if (!listaEjecutado.isEmpty()) {
//                        List<PresupuestoPorcentajeEjecutadoRepBean> listaPorcentajeEjecutado = new ArrayList<>();
//                        listaPorcentajeEjecutado = presupuestoNewService.obtenerPorcentaje(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), periodo, listaCabeza.get(j).getCuenta());
//                        if (!listaPorcentajeEjecutado.isEmpty()) {
                        montoAyuda = montoAyuda + listaEjecutado.get(0).getMontoUtilizadoAyuda();
                        List<PresupuestoDetalleRepBean> listaUtilizado = new ArrayList<>();
                        listaUtilizado = presupuestoNewService.obtenerUtilizado(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), periodo, listaCabeza.get(j).getCuenta());
                        if (!listaUtilizado.isEmpty()) {
                            List<PresupuestoDetalleRepBean> listaDisponible = new ArrayList<>();
                            listaDisponible = presupuestoNewService.obtenerDisponible(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), periodo, listaCabeza.get(j).getCuenta());
                            listaCabeza.get(j).setListaDisponible(listaDisponible);
                            listaCabeza.get(j).setListaUtilizado(listaUtilizado);
                            listaCabeza.get(j).setListaEjecutado(listaEjecutado);
                            DecimalFormat formato = new DecimalFormat("#,###.00");
                            String valorFormateado = formato.format(montoAyuda);
                            listaCabeza.get(j).setTotalEjecutado(valorFormateado);
                        }

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

    public void imprimirTodosConsolidadoPdf() {
        ServletOutputStream out = null;
        Integer id = 0;

        try {
            PresupuestoNewService presupuestoNewService = BeanFactory.getPresupuestoNewService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteConsolidado.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);

            List<DetPresupuestoNewBean> listaConsolidado = new ArrayList<>();
            PresupuestoNewBean presupuesto = new PresupuestoNewBean();
            presupuesto.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            presupuesto.setAnio(periodo);
            listaConsolidado = presupuestoNewService.SP_obtenerConsolidado(presupuesto, fechaInicio, fechaFin);

            System.out.println("imagenRuta:" + absoluteWebPath);
            System.out.println("jasper: " + archivoJasper);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaConsolidado);
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
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void imprimirTodosPdfExport() {
        ServletOutputStream out = null;
        Integer id = 0;

        try {
            PresupuestoNewService presupuestoNewService = BeanFactory.getPresupuestoNewService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reportePresupuestoExport.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);

            // 
            List<Integer> listCR = new ArrayList<>();
            List<Integer> listCC = new ArrayList<>();
            for (Object lista1 : dualCentroResponsabilidad.getTarget()) {
                String cr = "";
                cr = lista1.toString();
                listCR.add(new Integer(cr));
            }
            for (Object list2 : dualCuenta.getTarget()) {
                String cc = "";
                cc = list2.toString();
                listCC.add(new Integer(cc));
            }
            listaDetallePresupuestoNewBean = new ArrayList<>();
            listaPresupuestoNewBean = new ArrayList<>();
            getListaPresupuestoNewBean();
            System.out.println("verPor:" + verPor + "-" + "tipoRep:" + tipoReporte);
            if (verPor.equals(1)) {
                //CR 
                if (tipoReporte.equals(1) || tipoReporte.equals(3) || tipoReporte.equals(4)) {
                    //Resumen 
                    for (Integer list : listCR) {
                        List<PresupuestoNewBean> lista = new ArrayList<>();
                        List<PresupuestoNewBean> listaSinPres = new ArrayList<>();
                        System.out.println("list..." + list);
                        List<Integer> lis = new ArrayList<>();
                        lis.add(list);
                        if (tipoReporte.equals(3) || tipoReporte.equals(4)) {
                            listCC = null;
                        }
                        lista = presupuestoNewService.SP_obtenerPresupuestoPorCRAnio(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), periodo, fechaInicio, fechaFin, lis, listCC, 1, 1, 0, 1);
                        System.out.println("lista size lista" + lis + "-" + lista.size());
                        if (lista != null) {
                            if (!lista.isEmpty()) {
                                for (PresupuestoNewBean ls : lista) {
                                    ls.setCuenta(ls.getPlanContableBean().getCuenta());
                                    ls.setNombreCr(ls.getCentroResponsabilidadBean().getNombre());
                                    ls.setCr(ls.getCentroResponsabilidadBean().getCr());
                                    ls.setNombreCuenta(ls.getPlanContableBean().getNombre());
                                    listaPresupuestoNewBean.add(ls);
                                }
                            }
                        }
                        if (!tipoReporte.equals(3)) {
                            if (!tipoReporte.equals(4)) {
                                listaSinPres = presupuestoNewService.SP_obtenerPresupuestoPorCRAnio(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), periodo, fechaInicio, fechaFin, lis, listCC, 1, 2, 3, 1);
                            }
                        }
                        System.out.println("lista size listaSinPres" + lis + "-" + listaSinPres.size());
                        if (listaSinPres != null) {
                            if (!listaSinPres.isEmpty()) {
                                for (PresupuestoNewBean lsp : listaSinPres) {
                                    lsp.setCuenta(lsp.getPlanContableBean().getCuenta());
                                    lsp.setNombreCr(lsp.getCentroResponsabilidadBean().getNombre());
                                    lsp.setCr(lsp.getCentroResponsabilidadBean().getCr());
                                    lsp.setNombreCuenta(lsp.getPlanContableBean().getNombre());
                                    listaPresupuestoNewBean.add(lsp);
                                }
                            }
                        }
                    }
                } else if (tipoReporte.equals(2)) {
                    //Detallado
                    List<DetPresupuestoNewBean> listaSinPres = new ArrayList<>();

                    PresupuestoNewBean presupuesto = new PresupuestoNewBean();
                    presupuesto.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    presupuesto.setAnio(periodo);
                    listaDetallePresupuestoNewBean = presupuestoNewService.SP_obtenerDetalleGastosCrCuentaList(presupuesto, fechaInicio, fechaFin, listCR, listCC, 1, 1, 0, 1);
                    listaSinPres = presupuestoNewService.SP_obtenerDetalleGastosCrCuentaList(presupuesto, fechaInicio, fechaFin, listCR, listCC, 1, 2, 3, 1);
                    if (listaSinPres != null) {
                        if (listaDetallePresupuestoNewBean != null) {
                            for (DetPresupuestoNewBean lsp : listaSinPres) {
                                listaDetallePresupuestoNewBean.add(lsp);
                            }
                        }
                    }

                    System.out.println("lista size listaDetallePresupuestoNewBean" + listaDetallePresupuestoNewBean.size());
                }
            }

            //
            System.out.println("imagenRuta:" + absoluteWebPath);
            System.out.println("jasper: " + archivoJasper);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaPresupuestoNewBean);
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

    public void obtenerReporteCRIniciando(Integer tipoRep, Integer verPor) {
        try {
            if (tipoRep == 1 || tipoRep == 2) {
                if (dualCuenta.getTarget().size() > 0 && dualCentroResponsabilidad.getTarget().size() > 0) {
                    obtenerReporteCR(tipoRep, verPor);
                } else {
                    new MensajePrime().addInformativeMessagePer("Debe asignar Cuentas y Centro de Responsabilidad");
                }
            } else if (tipoRep == 3 || tipoRep == 4) {
                if (verPor == 1) {
                    if (dualCentroResponsabilidad.getTarget().size() > 0) {
                        obtenerReporteCR(tipoRep, verPor);
                    } else {
                        new MensajePrime().addInformativeMessagePer("Debe asignar Centro de Responsabilidad");
                    }
                } else if (verPor == 2) {
                    if (dualCuenta.getTarget().size() > 0) {
                        obtenerReporteCR(tipoRep, verPor);
                    } else {
                        new MensajePrime().addInformativeMessagePer("Debe asignar Cuentas");
                    }
                }
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerReporteCR(Integer tipoRep, Integer verPor) {
        try {
            listaPresupjuestoMantenimientoExclusivo = new ArrayList<>();
            PresupuestoNewService presupuestoNewService = BeanFactory.getPresupuestoNewService();
            List<Integer> listCR = new ArrayList<>();
            List<Integer> listCC = new ArrayList<>();
            for (Object lista1 : dualCentroResponsabilidad.getTarget()) {
                String cr = "";
                cr = lista1.toString();
                listCR.add(new Integer(cr));
            }
            for (Object list2 : dualCuenta.getTarget()) {
                String cc = "";
                cc = list2.toString();
                listCC.add(new Integer(cc));
            }
            listaDetallePresupuestoNewBean = new ArrayList<>();
            listaPresupjuestoMantenimientoExclusivo = new ArrayList<>();
            listaPresupuestoNewBean = new ArrayList<>();
            getListaPresupuestoNewBean();
            if (verPor.equals(1)) {
                //CR 
                if (tipoRep.equals(1) || tipoRep.equals(3) || tipoRep.equals(4)) {
                    //Resumen 
                    List<PresupuestoNewBean> lista = new ArrayList<>();
                    List<PresupuestoNewBean> listaSinPres = new ArrayList<>();
                    if (tipoRep.equals(3) || tipoRep.equals(4)) {
                        listCC = null;
                    }

                    lista = presupuestoNewService.SP_obtenerPresupuestoPorCRAnio(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), periodo, fechaInicio, fechaFin, listCR, listCC, 1, 1, 0, 1);
//                    System.out.println("lista size lista" + "-" + lista.size());
                    if (lista != null) {
                        if (!lista.isEmpty()) {
                            for (PresupuestoNewBean ls : lista) {
                                listaPresupuestoNewBean.add(ls);
                            }
                        }
                    }
                    if (!tipoRep.equals(3)) {
                        if (!tipoRep.equals(4)) {
                            listaSinPres = presupuestoNewService.SP_obtenerPresupuestoPorCRAnio(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), periodo, fechaInicio, fechaFin, listCR, listCC, 1, 2, 3, 1);
                        }
                    }
                    if (tipoRep.equals(4)) {
                        List<String> listaCR = new ArrayList<>();
                        for (Object obj : dualCentroResponsabilidad.getTarget()) {
                            System.out.println("obj " + obj.toString());
                            listaCR.add(obj.toString());
                        }
                        for (int i = 0; i < listaCR.size(); i++) {
                            calculateTotalPorCrExclusivo(listaCR.get(i));
                        }
                    }
                    if (listaSinPres != null) {
                        if (!listaSinPres.isEmpty()) {
                            for (PresupuestoNewBean lsp : listaSinPres) {
                                listaPresupuestoNewBean.add(lsp);
                            }
                        }
                    }
                    if (tipoReporte.equals(1)) {
                        this.flgExclusivo = false;
                    } else if (tipoReporte.equals(4)) {
                        this.flgExclusivo = true;
                    }
//                    }
                    if (listaPresupuestoNewBean.isEmpty()) {
                        new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    }
                    System.out.println("lista size lista" + "-" + listaPresupuestoNewBean.size());
                } else if (tipoRep.equals(2)) {
                    //Detallado
                    List<DetPresupuestoNewBean> listaSinPres = new ArrayList<>();

                    PresupuestoNewBean presupuesto = new PresupuestoNewBean();
                    presupuesto.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    presupuesto.setAnio(periodo);
                    listaDetallePresupuestoNewBean = presupuestoNewService.SP_obtenerDetalleGastosCrCuentaList(presupuesto, fechaInicio, fechaFin, listCR, listCC, 1, 1, 0, 1);
                    listaSinPres = presupuestoNewService.SP_obtenerDetalleGastosCrCuentaList(presupuesto, fechaInicio, fechaFin, listCR, listCC, 1, 2, 3, 1);
                    if (listaSinPres != null) {
                        if (listaDetallePresupuestoNewBean != null) {
                            for (DetPresupuestoNewBean lsp : listaSinPres) {
                                listaDetallePresupuestoNewBean.add(lsp);
                            }
                        }
                    }
                    if (listaDetallePresupuestoNewBean.isEmpty()) {
                        new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    }
                    System.out.println("lista size listaDetallePresupuestoNewBean" + listaDetallePresupuestoNewBean.size());
                }
                if (listaPresupuestoNewBean.size() > 0 || listaPresupjuestoMantenimientoExclusivo.size() > 0
                        || listaDetallePresupuestoNewBean.size() > 0) {
                    flgDisable = true;
                }
            } else if (verPor.equals(2)) {
                //Cc 
                if (tipoRep.equals(1) || tipoRep.equals(3) || tipoRep.equals(4)) {
                    //Resumen 
                    List<PresupuestoNewBean> lista = new ArrayList<>();
                    List<PresupuestoNewBean> listaSinPres = new ArrayList<>();
                    if (tipoRep.equals(3) || tipoRep.equals(4)) {
                        listCR = null;
                    }

                    lista = presupuestoNewService.SP_obtenerPresupuestoPorCRAnio(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), periodo, fechaInicio, fechaFin, listCR, listCC, 1, 1, 0, 2);
                    if (lista != null) {
                        if (!lista.isEmpty()) {
                            for (PresupuestoNewBean ls : lista) {
                                listaPresupuestoNewBean.add(ls);
                            }
                        }
                    }
                    if (!tipoRep.equals(3)) {
                        if (!tipoRep.equals(4)) {
                            listaSinPres = presupuestoNewService.SP_obtenerPresupuestoPorCRAnio(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), periodo, fechaInicio, fechaFin, listCR, listCC, 2, 1, 3, 2);
                        }
                    }

                    if (tipoRep.equals(4)) {
                        List<String> listaCC = new ArrayList<>();
                        for (Object obj : dualCuenta.getTarget()) {
                            listaCC.add(obj.toString());
                        }
                        for (int i = 0; i < listaCC.size(); i++) {
                            calculateTotalPorCCExclusivo(listaCC.get(i));
                        }
                    }

                    if (listaSinPres != null) {
                        if (!listaSinPres.isEmpty()) {
                            for (PresupuestoNewBean lsp : listaSinPres) {
                                listaPresupuestoNewBean.add(lsp);
                            }
                        }
                    }
//                    }
                    if (tipoReporte.equals(1)) {
                        this.flgExclusivo = false;
                    } else if (tipoReporte.equals(4)) {
                        this.flgExclusivo = true;
                    }
                    if (listaPresupuestoNewBean.isEmpty()) {
                        new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    }
                    System.out.println("lista size lista" + "-" + listaPresupuestoNewBean.size());
                } else if (tipoRep.equals(2)) {
                    //Detallado
                    List<DetPresupuestoNewBean> listaSinPres = new ArrayList<>();

                    PresupuestoNewBean presupuesto = new PresupuestoNewBean();
                    presupuesto.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    presupuesto.setAnio(periodo);
                    listaDetallePresupuestoNewBean = presupuestoNewService.SP_obtenerDetalleGastosCrCuentaList(presupuesto, fechaInicio, fechaFin, listCR, listCC, 1, 1, 0, 2);
                    listaSinPres = presupuestoNewService.SP_obtenerDetalleGastosCrCuentaList(presupuesto, fechaInicio, fechaFin, listCR, listCC, 2, 1, 3, 2);

                    if (listaSinPres != null) {
                        if (listaDetallePresupuestoNewBean != null) {
                            for (DetPresupuestoNewBean lsp : listaSinPres) {
                                listaDetallePresupuestoNewBean.add(lsp);
                            }
                        }
                    }
                    if (listaDetallePresupuestoNewBean.isEmpty()) {
                        new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    }
                    System.out.println("lista size lista" + "-" + listaDetallePresupuestoNewBean.size());
                }
                if (listaPresupuestoNewBean.size() > 0 || listaPresupjuestoMantenimientoExclusivo.size() > 0
                        || listaDetallePresupuestoNewBean.size() > 0) {
                    flgDisable = true;
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerReporteIngresosPresupuesto(Integer tipoRep) {
        try {
            if (dualCuentaIngresos.getTarget().size() > 0) {
                PresupuestoNewService presupuestoNewService = BeanFactory.getPresupuestoNewService();
                List<Integer> listCC = new ArrayList<>();
                for (Object list2 : dualCuentaIngresos.getTarget()) {
                    String cc = "";
                    cc = list2.toString();
                    listCC.add(new Integer(cc));
                }

                PresupuestoNewBean presupuesto = new PresupuestoNewBean();
                presupuesto.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                presupuesto.setAnio(periodo);
                if (tipoRep == 1) {
                    listaDetallePresupuestoNewBean = presupuestoNewService.SP_obtenerIngresosPresupuesto(presupuesto, fechaInicio, fechaFin, listCC);
                } else if (tipoRep == 2) {
                    listaDetallePresupuestoNewBean = presupuestoNewService.SP_obtenerIngresosPresupuestoDetallado(presupuesto, fechaInicio, fechaFin, listCC);
                } else if (tipoRep == 8) {
                    listaDetallePresupuestoNewBeanMes = presupuestoNewService.SP_obtenerIngresosPresupuestoMesaMes(presupuesto, fechaInicio, fechaFin, listCC);
                }
            } else {
                new MensajePrime().addInformativeMessagePer("Debe asignar Cuentas");
            }
            flgDisable = true;
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void imprimirPresupuestoPorCuentasIngresos() {
        ServletOutputStream out = null;
        Integer id = 0;

        try {
            if (dualCuentaIngresos.getTarget().size() > 0) {
                PresupuestoNewService presupuestoNewService = BeanFactory.getPresupuestoNewService();
                List<Integer> listCC = new ArrayList<>();
                for (Object list2 : dualCuentaIngresos.getTarget()) {
                    String cc = "";
                    cc = list2.toString();
                    listCC.add(new Integer(cc));
                }

                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                        getResponse();
                String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                        getRequest()).getServletContext().getRealPath("/reportes/reportePresupuestoIngresos.jasper");
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                String absoluteWebPath = externalContext.getRealPath("/");
                File file = new File(archivoJasper);

                List<DetPresupuestoNewBean> listaCabeza = new ArrayList<>();
                PresupuestoNewBean presupuesto = new PresupuestoNewBean();
                presupuesto.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                presupuesto.setAnio(periodo);
                listaCabeza = presupuestoNewService.SP_obtenerIngresosPresupuestoCabecera(presupuesto, fechaInicio, fechaFin, listCC);
                if (!listaCabeza.isEmpty()) {
                    List<DetPresupuestoNewBean> listaResumen = new ArrayList<>();
                    listaResumen = presupuestoNewService.SP_obtenerIngresosPresupuesto(presupuesto, fechaInicio, fechaFin, listCC);
                    listaCabeza.get(0).setListaResumen(listaResumen);
                    List<DetPresupuestoNewBean> listaDetalle = new ArrayList<>();
                    listaDetalle = presupuestoNewService.SP_obtenerIngresosPresupuestoDetallado(presupuesto, fechaInicio, fechaFin, listCC);
                    listaCabeza.get(0).setListaDetalle(listaDetalle);
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

            } else {
                new MensajePrime().addInformativeMessagePer("Debe asignar Cuentas");
            }
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

    public void imprimirPresupuestoPorCuentasIngresosMesMes() {
        ServletOutputStream out = null;
        Integer id = 0;

        try {
            if (dualCuentaIngresos.getTarget().size() > 0) {
                PresupuestoNewService presupuestoNewService = BeanFactory.getPresupuestoNewService();
                List<Integer> listCC = new ArrayList<>();
                for (Object list2 : dualCuentaIngresos.getTarget()) {
                    String cc = "";
                    cc = list2.toString();
                    listCC.add(new Integer(cc));
                }

                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                        getResponse();
                String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                        getRequest()).getServletContext().getRealPath("/reportes/reportePresupuestoIngresosMes.jasper");
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                String absoluteWebPath = externalContext.getRealPath("/");
                File file = new File(archivoJasper);

                List<DetPresupuestoNewBean> listaCabeza = new ArrayList<>();
                PresupuestoNewBean presupuesto = new PresupuestoNewBean();
                presupuesto.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                presupuesto.setAnio(periodo);
                listaCabeza = presupuestoNewService.SP_obtenerIngresosPresupuestoCabecera(presupuesto, fechaInicio, fechaFin, listCC);
                if (!listaCabeza.isEmpty()) {
                    List<DetPresupuestoNewBean> listaResumen = new ArrayList<>();
                    listaResumen = presupuestoNewService.SP_obtenerIngresosPresupuestoMesaMes(presupuesto, fechaInicio, fechaFin, listCC);
                    listaCabeza.get(0).setListaResumen(listaResumen);
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

            } else {
                new MensajePrime().addInformativeMessagePer("Debe asignar Cuentas");
            }
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

    public void exportXlsAllMesaMes(){
        try {
            if (dualCuentaIngresos.getTarget().size() > 0) {
                PresupuestoNewService presupuestoNewService = BeanFactory.getPresupuestoNewService();
                List<Integer> listCC = new ArrayList<>();
                for (Object list2 : dualCuentaIngresos.getTarget()) {
                    String cc = "";
                    cc = list2.toString();
                    listCC.add(new Integer(cc));
                }

                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                        getResponse();

                String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                        getRequest()).getServletContext().getRealPath("/reportes/reportePresupuestoIngresosMes.jasper");
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                String absoluteWebPath = externalContext.getRealPath("/");
                File file = new File(archivoJasper);
                List<DetPresupuestoNewBean> listaCabeza = new ArrayList<>();
                PresupuestoNewBean presupuesto = new PresupuestoNewBean();
                presupuesto.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                presupuesto.setAnio(periodo);
                listaCabeza = presupuestoNewService.SP_obtenerIngresosPresupuestoCabecera(presupuesto, fechaInicio, fechaFin, listCC);
                if (!listaCabeza.isEmpty()) {
                    List<DetPresupuestoNewBean> listaResumen = new ArrayList<>();
                    listaResumen = presupuestoNewService.SP_obtenerIngresosPresupuestoMesaMes(presupuesto, fechaInicio, fechaFin, listCC);
                    listaCabeza.get(0).setListaResumen(listaResumen);
                }
                
                JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
                JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCabeza);

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
                response.setHeader("Content-Disposition", "attachment; filename=Reporte_Presupuesto_Ingresos_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".xls");
                response.setContentType("application/vnd.ms-excel");
                response.setContentLength(arrayOutputStream.toByteArray().length);
//            out = response.getOutputStream();
//
                out.write(arrayOutputStream.toByteArray());
                out.flush();
                out.close();
            } else {
                new MensajePrime().addInformativeMessagePer("Debe asignar Cuentas");
            }
        } catch (Exception ettt) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ettt);
        }

    }

    public void exportXlsAll(){
        try {
            if (dualCuentaIngresos.getTarget().size() > 0) {
                PresupuestoNewService presupuestoNewService = BeanFactory.getPresupuestoNewService();
                List<Integer> listCC = new ArrayList<>();
                for (Object list2 : dualCuentaIngresos.getTarget()) {
                    String cc = "";
                    cc = list2.toString();
                    listCC.add(new Integer(cc));
                }

                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                        getResponse();

                String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                        getRequest()).getServletContext().getRealPath("/reportes/reportePresupuestoIngresos.jasper");
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                String absoluteWebPath = externalContext.getRealPath("/");
                File file = new File(archivoJasper);
                List<DetPresupuestoNewBean> listaCabeza = new ArrayList<>();
                PresupuestoNewBean presupuesto = new PresupuestoNewBean();
                presupuesto.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                presupuesto.setAnio(periodo);
                listaCabeza = presupuestoNewService.SP_obtenerIngresosPresupuestoCabecera(presupuesto, fechaInicio, fechaFin, listCC);
                if (!listaCabeza.isEmpty()) {
                    List<DetPresupuestoNewBean> listaResumen = new ArrayList<>();
                    listaResumen = presupuestoNewService.SP_obtenerIngresosPresupuesto(presupuesto, fechaInicio, fechaFin, listCC);
                    listaCabeza.get(0).setListaResumen(listaResumen);
                    List<DetPresupuestoNewBean> listaDetalle = new ArrayList<>();
                    listaDetalle = presupuestoNewService.SP_obtenerIngresosPresupuestoDetallado(presupuesto, fechaInicio, fechaFin, listCC);
                    listaCabeza.get(0).setListaDetalle(listaDetalle);
                }

                JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
                JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCabeza);

                Map<String, Object> parametros = new HashMap<>();
                String ruta = absoluteWebPath + "reportes\\";
                System.out.println("ruta:"+ruta);
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
                response.setHeader("Content-Disposition", "attachment; filename=Reporte_Presupuesto_Ingresos_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".xls");
                response.setContentType("application/vnd.ms-excel");
                response.setContentLength(arrayOutputStream.toByteArray().length);
//            out = response.getOutputStream();
//
                out.write(arrayOutputStream.toByteArray());
                out.flush();
                out.close();
            } else {
                new MensajePrime().addInformativeMessagePer("Debe asignar Cuentas");
            }
        } catch (Exception ettt) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ettt);
        }

    }

    public void obtenerTigo(Integer tipo) {
        try {
            this.tipoReporte = tipo;
            listaPresupuestoNewBean = new ArrayList<>();
            listaDetallePresupuestoNewBean = new ArrayList<>();
            listaPresupjuestoMantenimientoExclusivo = new ArrayList<>();
            obtenerPulmones();
            if (tipo == 1 || tipo == 2 || tipo == 3) {
                this.flgExclusivo = false;
            } else {
                this.flgExclusivo = true;
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerFiltro(Integer tipo) {
        try {
            this.verPor = tipo;
            listaPresupuestoNewBean = new ArrayList<>();
            listaDetallePresupuestoNewBean = new ArrayList<>();
            listaPresupjuestoMantenimientoExclusivo = new ArrayList<>();
            obtenerPulmonesPor();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPulmones() {
        try {
            if (this.tipoReporte.equals(3) || this.tipoReporte.equals(4)) {
                if (this.verPor.equals(1)) {
                    this.pickCR = Boolean.TRUE;
                    this.pickCC = Boolean.FALSE;
                } else if (this.verPor.equals(2)) {
                    this.pickCR = Boolean.FALSE;
                    this.pickCC = Boolean.TRUE;
                }
            } else {
                this.pickCR = Boolean.TRUE;
                this.pickCC = Boolean.TRUE;
            }
            listaPresupuestoNewBean = new ArrayList<>();
            listaDetallePresupuestoNewBean = new ArrayList<>();
            listaPresupjuestoMantenimientoExclusivo = new ArrayList<>();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPulmonesPor() {
        try {
            if (this.verPor.equals(1)) {
                if (this.tipoReporte.equals(3) || this.tipoReporte.equals(4)) {
                    this.pickCR = Boolean.TRUE;
                    this.pickCC = Boolean.FALSE;
                } else {
                    this.pickCR = Boolean.TRUE;
                    this.pickCC = Boolean.TRUE;
                }
            } else if (this.verPor.equals(2)) {
                if (this.tipoReporte.equals(3) || this.tipoReporte.equals(4)) {
                    this.pickCR = Boolean.FALSE;
                    this.pickCC = Boolean.TRUE;
                } else {
                    this.pickCR = Boolean.TRUE;
                    this.pickCC = Boolean.TRUE;
                }
            } else {
                this.pickCR = Boolean.TRUE;
                this.pickCC = Boolean.TRUE;
            }

            listaPresupuestoNewBean = new ArrayList<>();
            listaDetallePresupuestoNewBean = new ArrayList<>();
            listaPresupjuestoMantenimientoExclusivo = new ArrayList<>();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerFechaFiltro() {
        try {
            SimpleDateFormat formatoDiaCompleto = new SimpleDateFormat("dd-MM-yyyy");
            Integer anio1 = periodo;
            String fechaI = "01-01-" + anio1.toString();
            fechaInicio = formatoDiaCompleto.parse(fechaI);
            String dateCompleto = formatoDiaCompleto.format(new Date());
            fechaFin = formatoDiaCompleto.parse(dateCompleto);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiar() {
        try {
            cargarFiltros();
            obtenerFechaFiltro();
            listaDetallePresupuestoNewBean = new ArrayList<>();
            listaPresupuestoNewBean = new ArrayList<>();
            listaPresupjuestoMantenimientoExclusivo = new ArrayList<>();
            this.tipoReporte = 1;
            this.verPor = 1;
            this.pickCR = Boolean.TRUE;
            this.pickCC = Boolean.TRUE;
            flgDisable = false;
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarFormularioCentros() {
        try {
            String parametro = (String) new MaristaUtils().requestObtenerObjeto("anio");
            String parametro2 = (String) new MaristaUtils().requestObtenerObjeto("rangoInicio");
            String parametro3 = (String) new MaristaUtils().requestObtenerObjeto("rangoFin");
            String parametro4 = (String) new MaristaUtils().requestObtenerObjeto("tipoReporte");
            String parametro5 = (String) new MaristaUtils().requestObtenerObjeto("verPor");
            String parametro6 = (String) new MaristaUtils().requestObtenerObjeto("dualCentros");
            String parametro7 = (String) new MaristaUtils().requestObtenerObjeto("dualCuentas");
            List<String> aaa = new ArrayList<>();
            aaa.add(parametro6);
            Integer anio = new Integer(parametro);
            List<Integer> listCR = new ArrayList<>();
            List<Integer> listCC = new ArrayList<>();
            for (Object lista1 : aaa) {
                String cr = "";
                cr = lista1.toString();
                listCR.add(new Integer(cr));
            }

            DateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
            Date rangoInicio = (Date) formatter.parse(parametro2.toString());
            Calendar cal = Calendar.getInstance();
            cal.setTime(rangoInicio);
            String formatedDateInicio = cal.get(Calendar.DATE) + "/"
                    + (cal.get(Calendar.MONTH) + 1)
                    + "/" + cal.get(Calendar.YEAR);

            rangoInicio = new Date(formatedDateInicio);

            Date rangoFin = (Date) formatter.parse(parametro3);
            cal.setTime(rangoFin);
            String formatedDateFin = cal.get(Calendar.DATE) + "/"
                    + (cal.get(Calendar.MONTH) + 1)
                    + "/" + cal.get(Calendar.YEAR);

            rangoFin = new Date(formatedDateFin);

            Integer tipoRepo = new Integer(parametro4);
            Integer verPorReporte = new Integer(parametro5);

            setPeriodo(anio);
            setFechaInicio(rangoInicio);
            setFechaFin(rangoFin);
            setTipoReporte(tipoRepo);
            setVerPor(verPorReporte);
//        setDualCentroResponsabilidad(parametro6);
//        setDualCuenta(parametro7);

            obtenerReporteCR(tipoReporte, verPor);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

//////////////////////////////////////////////////////////////
    public List<PresupuestoNewBean> getListaPresupuestoNewBean() {
        if (listaPresupuestoNewBean == null) {
            listaPresupuestoNewBean = new ArrayList<>();
        }
        return listaPresupuestoNewBean;
    }

    public void setListaPresupuestoNewBean(List<PresupuestoNewBean> listaPresupuestoNewBean) {
        this.listaPresupuestoNewBean = listaPresupuestoNewBean;
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

    public Double getTotalPresupuestado() {
        return totalPresupuestado;
    }

    public void setTotalPresupuestado(Double totalPresupuestado) {
        this.totalPresupuestado = totalPresupuestado;
    }

    public Double getTotalEjecutado() {
        return totalEjecutado;
    }

    public void setTotalEjecutado(Double totalEjecutado) {
        this.totalEjecutado = totalEjecutado;
    }

    public Double getPorcEjecutado() {
        return porcEjecutado;
    }

    public void setPorcEjecutado(Double porcEjecutado) {
        this.porcEjecutado = porcEjecutado;
    }

    public Double getPorcSaldo() {
        return porcSaldo;
    }

    public void setPorcSaldo(Double porcSaldo) {
        this.porcSaldo = porcSaldo;
    }

    public Double getTotalSaldo() {
        return totalSaldo;
    }

    public void setTotalSaldo(Double totalSaldo) {
        this.totalSaldo = totalSaldo;
    }

    public List<DetPresupuestoNewBean> getListaDetallePresupuestoNewBean() {
        if (listaDetallePresupuestoNewBean == null) {
            listaDetallePresupuestoNewBean = new ArrayList<>();
        }
        return listaDetallePresupuestoNewBean;
    }

    public void setListaDetallePresupuestoNewBean(List<DetPresupuestoNewBean> listaDetallePresupuestoNewBean) {
        this.listaDetallePresupuestoNewBean = listaDetallePresupuestoNewBean;
    }

    public List<Integer> getListaTipoAccesoUsu() {
        if (listaTipoAccesoUsu == null) {
            listaTipoAccesoUsu = new ArrayList<>();
        }
        return listaTipoAccesoUsu;
    }

    public void setListaTipoAccesoUsu(List<Integer> listaTipoAccesoUsu) {
        this.listaTipoAccesoUsu = listaTipoAccesoUsu;
    }

    public DualListModel<CentroResponsabilidadBean> getDualCentroResponsabilidad() {
        return dualCentroResponsabilidad;
    }

    public void setDualCentroResponsabilidad(DualListModel<CentroResponsabilidadBean> dualCentroResponsabilidad) {
        this.dualCentroResponsabilidad = dualCentroResponsabilidad;
    }

    public DualListModel<PlanContableBean> getDualCuenta() {
        return dualCuenta;
    }

    public void setDualCuenta(DualListModel<PlanContableBean> dualCuenta) {
        this.dualCuenta = dualCuenta;
    }

    public Integer getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }

    public Integer getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(Integer tipoReporte) {
        this.tipoReporte = tipoReporte;
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

    public Integer getVerPor() {
        return verPor;
    }

    public void setVerPor(Integer verPor) {
        this.verPor = verPor;
    }

    public Boolean getPickCR() {
        return pickCR;
    }

    public void setPickCR(Boolean pickCR) {
        this.pickCR = pickCR;
    }

    public Boolean getPickCC() {
        return pickCC;
    }

    public void setPickCC(Boolean pickCC) {
        this.pickCC = pickCC;
    }

    public List<PresupuestoDetalleRepBean> getListaDetalle() {
        if (listaDetalle == null) {
            listaDetalle = new ArrayList<>();
        }
        return listaDetalle;
    }

    public void setListaDetalle(List<PresupuestoDetalleRepBean> listaDetalle) {
        this.listaDetalle = listaDetalle;
    }

    public Boolean getFlgExclusivo() {
        return flgExclusivo;
    }

    public void setFlgExclusivo(Boolean flgExclusivo) {
        this.flgExclusivo = flgExclusivo;
    }

    public List<PresupuestoNewBean> getListaPresupjuestoMantenimientoExclusivo() {
        if (listaPresupjuestoMantenimientoExclusivo == null) {
            listaPresupjuestoMantenimientoExclusivo = new ArrayList<>();
        }
        return listaPresupjuestoMantenimientoExclusivo;
    }

    public void setListaPresupjuestoMantenimientoExclusivo(List<PresupuestoNewBean> listaPresupjuestoMantenimientoExclusivo) {
        this.listaPresupjuestoMantenimientoExclusivo = listaPresupjuestoMantenimientoExclusivo;
    }

    public Boolean getFlgDisable() {
        return flgDisable;
    }

    public void setFlgDisable(Boolean flgDisable) {
        this.flgDisable = flgDisable;
    }

    public DualListModel<PlanContableBean> getDualCuentaIngresos() {
        return dualCuentaIngresos;
    }

    public void setDualCuentaIngresos(DualListModel<PlanContableBean> dualCuentaIngresos) {
        this.dualCuentaIngresos = dualCuentaIngresos;
    }

    public List<DetPresupuestoNewBean> getListaDetallePresupuestoNewBeanMes() {
        if (listaDetallePresupuestoNewBeanMes == null) {
            listaDetallePresupuestoNewBeanMes = new ArrayList<>();
        }
        return listaDetallePresupuestoNewBeanMes;
    }

    public void setListaDetallePresupuestoNewBeanMes(List<DetPresupuestoNewBean> listaDetallePresupuestoNewBeanMes) {
        this.listaDetallePresupuestoNewBeanMes = listaDetallePresupuestoNewBeanMes;
    }

}
