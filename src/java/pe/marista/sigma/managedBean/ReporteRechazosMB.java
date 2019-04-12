/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.CuentasPorCobrarBean;
import pe.marista.sigma.bean.EstudianteBean;
import pe.marista.sigma.bean.MatriculaBean;
import pe.marista.sigma.bean.ProcesoRecuperacionBean;
import pe.marista.sigma.bean.ReporteRechazoBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.CuentasPorCobrarService;
import pe.marista.sigma.service.MatriculaService;
import pe.marista.sigma.service.ProcesoEnvioService;
import pe.marista.sigma.service.ProcesoRecuperacionService;
import pe.marista.sigma.service.ReporteRechazoService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author MS002
 */
public class ReporteRechazosMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of ReporteRechazosMB
     */
    @PostConstruct
    public void ReporteRechazosMB() {
        try {
//            validarErrores();
//            obtenerErrorGeneral();
            obtenerReporteRechazo();
            obtenerErrores();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
    private ReporteRechazoBean reporteRechazoBean;
    private ReporteRechazoBean reporteFiltroBean;
    private List<ReporteRechazoBean> listaReporteRechazoBean;
    private List<ReporteRechazoBean> listaReporteRechazoFiltroBean;

    private ProcesoRecuperacionBean procesoRecuperacionBean;
    private List<ProcesoRecuperacionBean> listaProcesoRecuperacionBean;
    private List<ProcesoRecuperacionBean> listaFiltroRecuperacionBean;
    private CuentasPorCobrarBean cuentasPorCobrarBean;
    private List<CuentasPorCobrarBean> listaCuentasPorCobrarBean;
    private List<CuentasPorCobrarBean> listaCuentaFiltroBean;
    private EstudianteBean estudianteBean;
    private List<EstudianteBean> listaEstudianteBean;
    private MatriculaBean matriculaBean;
    private List<MatriculaBean> listaMatriculaBean;

    //Ayuda
    private Boolean renderGen;
    private Boolean render1;
    private Boolean render2;
    private Boolean render3;
    private String mesPago;
    private String mesPago2;
    private Boolean renderLink = true;
    //Render Tab
    private Boolean renderTab1;
    private Boolean renderTab2;
    private Boolean renderTab3;

    public void obtenerReporteRechazo() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ReporteRechazoService reporteRechazoService = BeanFactory.getReporteRechazoService();
            listaReporteRechazoBean = reporteRechazoService.obtenerReporteRechazo(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerErrores() {
        try {
            reporteRechazoBean = new ReporteRechazoBean();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            /*Lista Cuentas*/
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            listaCuentasPorCobrarBean = procesoEnvioService.obtenerCuentas(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            /*Lista Recuperados*/
            ProcesoRecuperacionService procesoRecuperacionService = BeanFactory.getProcesoRecuperacionService();
            listaProcesoRecuperacionBean = procesoRecuperacionService.obtenerProcesoRecPorUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            /*Lista Matriculados*/
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            listaMatriculaBean = matriculaService.obtenerMatricula();
            /*Services Reporte Rechazos*/
            ReporteRechazoService reporteRechazoService = BeanFactory.getReporteRechazoService();
            ReporteRechazoBean reporteRechazo = new ReporteRechazoBean();
            CodigoService codigoService = BeanFactory.getCodigoService();
            CodigoBean codigoTipoStatusCtaCte = new CodigoBean();
            codigoTipoStatusCtaCte = codigoService.obtenerPorCodigo(new CodigoBean(0, "Pagado", new TipoCodigoBean(MaristaConstantes.TIP_STATUS_CTA_CTE)));
            String notificacion = "";
            System.out.println("=================================================");
            for (ProcesoRecuperacionBean recup : listaProcesoRecuperacionBean) {
                /*Primer Error*/
                if (recup.getEstudianteBean().getIdEstudiante() == null
                        || recup.getEstudianteBean().getIdEstudiante() == null
                        || recup.getCuentasPorCobrarBean().getIdTipoStatusCtaCte().getIdCodigo() == null
                        || recup.getFlgConcilia() == null
                        || recup.getCuentasPorCobrarBean().getFechaVenc() == null || recup.getFechaVen() == null) {
                    System.out.println("Error");
                } else {
                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                    String fecha1 = formato.format(recup.getCuentasPorCobrarBean().getFechaVenc());
                    String fecha11[] = fecha1.split("-");
                    String fecha12 = fecha11[0];
                    String fecha13 = fecha11[1];
                    String fecha14 = fecha12 + fecha13;

                    String fecha2 = formato.format(recup.getFechaVen());
                    String fecha21[] = fecha2.split("-");
                    String fecha22 = fecha21[0];
                    String fecha23 = fecha21[1];
                    String fecha24 = fecha22 + fecha23;
                    System.out.println(fecha14);
                    System.out.println(fecha24);
                    if (recup.getEstudianteBean().getIdEstudiante().equals(recup.getCuentasPorCobrarBean().getEstudianteBean().getIdEstudiante())) {
                        if (recup.getEstudianteBean().getIdEstudiante() == null
                                || recup.getCuentasPorCobrarBean().getEstudianteBean().getIdEstudiante() == null
                                || recup.getCuentasPorCobrarBean().getIdTipoStatusCtaCte().getIdCodigo() == null
                                || recup.getFlgConcilia() == null
                                || recup.getCuentasPorCobrarBean().getProcesoRecuperacionBean().getIdProcesoRecup() == null) {
                            System.out.println("No encontro");
                        } else {

                            if (recup.getCuentasPorCobrarBean().getIdTipoStatusCtaCte().getIdCodigo().equals(codigoTipoStatusCtaCte.getIdCodigo())) {
                                recup.getCuentasPorCobrarBean().getIdTipoStatusCtaCte().setIdCodigo(1);
                            }
//                                for (ReporteRechazoBean rechazo : listaReporteRechazoBean) {
                            if (recup.getFlgConcilia().equals(recup.getCuentasPorCobrarBean().getIdTipoStatusCtaCte().getIdCodigo())) {
                                System.out.println("========================================================================Encontro en 1 ================================================================" + recup.getCuentasPorCobrarBean().getEstudianteBean().getIdEstudiante());
//                                reporteRechazoBean.getProcesoRecuperacionBean().getProcesoBancoBean().setIdProcesoBanco(recup.getProcesoBancoBean().getIdProcesoBanco());
//                                reporteRechazoBean.getProcesoRecuperacionBean().setIdProcesoRecup(recup.getIdProcesoRecup());
//                                reporteRechazoBean.getCuentasPorCobrarBean().setIdCtasXCobrar(cuenta.getIdCtasXCobrar());
//                                reporteRechazoBean.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//                                reporteRechazoBean.getEstudianteBean().setIdEstudiante(cuenta.getEstudianteBean().getIdEstudiante());
//                                reporteRechazoBean.setCreaPor(beanUsuarioSesion.getUsuario());
//                                reporteRechazoBean.setFlgError(1);
//                                reporteRechazoBean.setDescripcion("Error de Conciliacion");
//                                reporteRechazoService.insertarReporteRechazo(reporteRechazoBean);
                                break;
                            }
//                                }
                        }
                    } else {
                        System.out.println("Error 2");
                    }
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerError() {
        try {
            reporteRechazoBean = new ReporteRechazoBean();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            /*Lista Cuentas*/
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            listaCuentasPorCobrarBean = procesoEnvioService.obtenerCuentas(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            /*Lista Recuperados*/
            ProcesoRecuperacionService procesoRecuperacionService = BeanFactory.getProcesoRecuperacionService();
            listaProcesoRecuperacionBean = procesoRecuperacionService.obtenerProcesoRecPorUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            /*Lista Matriculados*/
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            listaMatriculaBean = matriculaService.obtenerMatricula();
            /*Services Reporte Rechazos*/
            ReporteRechazoService reporteRechazoService = BeanFactory.getReporteRechazoService();
            ReporteRechazoBean reporteRechazo = new ReporteRechazoBean();
            CodigoService codigoService = BeanFactory.getCodigoService();
            CodigoBean codigoTipoStatusCtaCte = new CodigoBean();
            codigoTipoStatusCtaCte = codigoService.obtenerPorCodigo(new CodigoBean(0, "Pagado", new TipoCodigoBean(MaristaConstantes.TIP_STATUS_CTA_CTE)));
            String notificacion = "";
            System.out.println("=================================================");
            for (ProcesoRecuperacionBean recup : listaProcesoRecuperacionBean) {
                for (CuentasPorCobrarBean cuenta : listaCuentasPorCobrarBean) {
                    System.out.println("------------");

                    /*Primer Error*/
                    if (recup.getEstudianteBean().getIdEstudiante() == null
                            || cuenta.getEstudianteBean().getIdEstudiante() == null
                            || cuenta.getIdTipoStatusCtaCte().getIdCodigo() == null
                            || recup.getFlgConcilia() == null) {
                        System.out.println("No encontro");
                    } else {
                        if (cuenta.getIdTipoStatusCtaCte().getIdCodigo().equals(codigoTipoStatusCtaCte.getIdCodigo())) {
                            cuenta.getIdTipoStatusCtaCte().setIdCodigo(1);
                        }
                        if (recup.getFlgConcilia().equals(cuenta.getIdTipoStatusCtaCte().getIdCodigo())
                                && recup.getProcesoBancoBean().getIdProcesoBanco() != null
                                && cuenta.getIdCtasXCobrar() != null) {
                            System.out.println("========================================================================Encontro en 1 ================================================================" + cuenta.getEstudianteBean().getIdEstudiante());
                            reporteRechazoBean.getProcesoBancoBean().setIdProcesoBanco(cuenta.getProcesoEnvioBean().getProcesoBancoBean().getIdProcesoBanco());
                            reporteRechazoBean.getCuentasPorCobrarBean().setIdCtasXCobrar(cuenta.getIdCtasXCobrar());
                            reporteRechazoBean.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                            reporteRechazoBean.getEstudianteBean().setIdEstudiante(cuenta.getEstudianteBean().getIdEstudiante());
                            reporteRechazoBean.setCreaPor(beanUsuarioSesion.getUsuario());
                            reporteRechazoBean.setFlgError(1);
                            reporteRechazoService.insertarReporteRechazo(reporteRechazoBean);
                            break;
                        }
                    }

                    /*Segundo Error*/
                    if (recup.getEstudianteBean().getIdEstudiante() == null
                            || cuenta.getEstudianteBean().getIdEstudiante() == null
                            || recup.getProcesoBancoBean().getIdProcesoBanco() == null
                            || cuenta.getIdTipoStatusCtaCte().getIdCodigo() == null
                            || recup.getFlgConcilia() == null) {
                        System.out.println("No encontro");
                    } else {
                        if (cuenta.getIdTipoStatusCtaCte().getIdCodigo().equals(codigoTipoStatusCtaCte.getIdCodigo())) {
                            cuenta.getIdTipoStatusCtaCte().setIdCodigo(1);
                        }
                        if (!recup.getEstudianteBean().getPersonaBean().getNombre().equals(cuenta.getEstudianteBean().getPersonaBean().getNombre())
                                && !recup.getEstudianteBean().getPersonaBean().getApepat().equals(cuenta.getEstudianteBean().getPersonaBean().getApepat())
                                && !recup.getEstudianteBean().getPersonaBean().getApemat().equals(cuenta.getEstudianteBean().getPersonaBean().getApemat())
                                && recup.getFlgConcilia().equals(cuenta.getIdTipoStatusCtaCte().getIdCodigo())
                                && recup.getProcesoBancoBean().getIdProcesoBanco() != null
                                && cuenta.getIdCtasXCobrar() != null) {
                            System.out.println("========================================================================Encontro  en 2================================================================" + cuenta.getEstudianteBean().getIdEstudiante());
                            reporteRechazoBean.getProcesoBancoBean().setIdProcesoBanco(cuenta.getProcesoEnvioBean().getProcesoBancoBean().getIdProcesoBanco());
                            reporteRechazoBean.getCuentasPorCobrarBean().setIdCtasXCobrar(cuenta.getIdCtasXCobrar());
                            reporteRechazoBean.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                            reporteRechazoBean.getEstudianteBean().setIdEstudiante(cuenta.getEstudianteBean().getIdEstudiante());
                            reporteRechazoBean.setCreaPor(beanUsuarioSesion.getUsuario());
                            reporteRechazoBean.setFlgError(1);
                            reporteRechazoService.insertarReporteRechazo(reporteRechazoBean);
                            break;
                        }
                    }

                    /*Tercer Error*/
                    if (recup.getEstudianteBean().getIdEstudiante() == null
                            || cuenta.getEstudianteBean().getIdEstudiante() == null
                            || recup.getProcesoBancoBean().getIdProcesoBanco() == null
                            || cuenta.getIdTipoStatusCtaCte().getIdCodigo() == null
                            || recup.getFlgConcilia() == null) {
                        System.out.println("No encontro");
                    } else {
                        if (cuenta.getIdTipoStatusCtaCte().getIdCodigo().equals(codigoTipoStatusCtaCte.getIdCodigo())) {
                            cuenta.getIdTipoStatusCtaCte().setIdCodigo(1);
                        }
                        if (!recup.getEstudianteBean().getPersonaBean().getNombre().equals(cuenta.getEstudianteBean().getPersonaBean().getNombre())
                                && recup.getFlgConcilia().equals(cuenta.getIdTipoStatusCtaCte().getIdCodigo())
                                && recup.getProcesoBancoBean().getIdProcesoBanco() != null
                                && cuenta.getIdCtasXCobrar() != null) {
                            System.out.println("========================================================================Encontro  en 3================================================================" + cuenta.getEstudianteBean().getIdEstudiante());
                            reporteRechazoBean.getProcesoBancoBean().setIdProcesoBanco(cuenta.getProcesoEnvioBean().getProcesoBancoBean().getIdProcesoBanco());
                            reporteRechazoBean.getCuentasPorCobrarBean().setIdCtasXCobrar(cuenta.getIdCtasXCobrar());
                            reporteRechazoBean.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                            reporteRechazoBean.getEstudianteBean().setIdEstudiante(cuenta.getEstudianteBean().getIdEstudiante());
                            reporteRechazoBean.setCreaPor(beanUsuarioSesion.getUsuario());
                            reporteRechazoBean.setFlgError(1);
                            reporteRechazoService.insertarReporteRechazo(reporteRechazoBean);
                            break;
                        }
                    }

                    /*Cuarto Error*/
                    if (recup.getEstudianteBean().getIdEstudiante() == null
                            || cuenta.getEstudianteBean().getIdEstudiante() == null
                            || recup.getProcesoBancoBean().getIdProcesoBanco() == null
                            || cuenta.getIdTipoStatusCtaCte().getIdCodigo() == null
                            || recup.getFlgConcilia() == null) {
                        System.out.println("No encontro");
                    } else {
                        if (cuenta.getIdTipoStatusCtaCte().getIdCodigo().equals(codigoTipoStatusCtaCte.getIdCodigo())) {
                            cuenta.getIdTipoStatusCtaCte().setIdCodigo(1);
                        }
                        if (!recup.getEstudianteBean().getPersonaBean().getApepat().equals(cuenta.getEstudianteBean().getPersonaBean().getApepat())
                                && recup.getFlgConcilia().equals(cuenta.getIdTipoStatusCtaCte().getIdCodigo())
                                && recup.getProcesoBancoBean().getIdProcesoBanco() != null
                                && cuenta.getIdCtasXCobrar() != null) {
                            System.out.println("========================================================================Encontro  en 4================================================================" + cuenta.getEstudianteBean().getIdEstudiante());
                            reporteRechazoBean.getProcesoBancoBean().setIdProcesoBanco(cuenta.getProcesoEnvioBean().getProcesoBancoBean().getIdProcesoBanco());
                            reporteRechazoBean.getCuentasPorCobrarBean().setIdCtasXCobrar(cuenta.getIdCtasXCobrar());
                            reporteRechazoBean.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                            reporteRechazoBean.getEstudianteBean().setIdEstudiante(cuenta.getEstudianteBean().getIdEstudiante());
                            reporteRechazoBean.setCreaPor(beanUsuarioSesion.getUsuario());
                            reporteRechazoBean.setFlgError(1);
                            reporteRechazoService.insertarReporteRechazo(reporteRechazoBean);
                            break;
                        }
                    }

                    /*Quinto Error*/
                    if (recup.getEstudianteBean().getIdEstudiante() == null
                            || cuenta.getEstudianteBean().getIdEstudiante() == null
                            || recup.getProcesoBancoBean().getIdProcesoBanco() == null
                            || cuenta.getIdTipoStatusCtaCte().getIdCodigo() == null
                            || recup.getFlgConcilia() == null) {
                        System.out.println("No encontro");
                    } else {
                        if (cuenta.getIdTipoStatusCtaCte().getIdCodigo().equals(codigoTipoStatusCtaCte.getIdCodigo())) {
                            cuenta.getIdTipoStatusCtaCte().setIdCodigo(1);
                        }
                        if (!recup.getEstudianteBean().getPersonaBean().getApemat().equals(cuenta.getEstudianteBean().getPersonaBean().getApemat())
                                && recup.getFlgConcilia().equals(cuenta.getIdTipoStatusCtaCte().getIdCodigo())
                                && recup.getProcesoBancoBean().getIdProcesoBanco() != null
                                && cuenta.getIdCtasXCobrar() != null) {
                            System.out.println("========================================================================Encontro  en 5================================================================" + cuenta.getEstudianteBean().getIdEstudiante());
                            reporteRechazoBean.getProcesoBancoBean().setIdProcesoBanco(cuenta.getProcesoEnvioBean().getProcesoBancoBean().getIdProcesoBanco());
                            reporteRechazoBean.getCuentasPorCobrarBean().setIdCtasXCobrar(cuenta.getIdCtasXCobrar());
                            reporteRechazoBean.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                            reporteRechazoBean.getEstudianteBean().setIdEstudiante(cuenta.getEstudianteBean().getIdEstudiante());
                            reporteRechazoBean.setCreaPor(beanUsuarioSesion.getUsuario());
                            reporteRechazoBean.setFlgError(1);
                            reporteRechazoService.insertarReporteRechazo(reporteRechazoBean);
                            break;
                        }
                    }

                    /*Sexto Error*/
                    if (recup.getFlgConcilia() == null
                            || recup.getProcesoBancoBean().getIdProcesoBanco() == null
                            || cuenta.getIdCtasXCobrar() == null) {
                        System.out.println("No encontro");
                    } else {
                        if (recup.getFlgConcilia() == null) {
                            recup.setFlgConcilia(0);
                        }
                        if (recup.getFlgConcilia().equals(0)
                                && recup.getProcesoBancoBean().getIdProcesoBanco() != null
                                && recup.getProcesoBancoBean().getIdProcesoBanco() != null
                                && cuenta.getIdCtasXCobrar() != null) {
                            System.out.println("========================================================================Encontro  en 6================================================================" + cuenta.getEstudianteBean().getIdEstudiante());
                            reporteRechazoBean.getProcesoBancoBean().setIdProcesoBanco(cuenta.getProcesoEnvioBean().getProcesoBancoBean().getIdProcesoBanco());
                            reporteRechazoBean.getCuentasPorCobrarBean().setIdCtasXCobrar(cuenta.getIdCtasXCobrar());
                            reporteRechazoBean.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                            reporteRechazoBean.getEstudianteBean().setIdEstudiante(cuenta.getEstudianteBean().getIdEstudiante());
                            reporteRechazoBean.setCreaPor(beanUsuarioSesion.getUsuario());
                            reporteRechazoBean.setFlgError(1);
                            reporteRechazoService.insertarReporteRechazo(reporteRechazoBean);
                            break;
                        }
                    }
                }
            }
            listaReporteRechazoBean = reporteRechazoService.obtenerReporteRechazo(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerErrorGeneral() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ReporteRechazoService reporteRechazoService = BeanFactory.getReporteRechazoService();
            obtenerError1();
            obtenerError2();
//            obtenerError3();
//            obtenerError4();
//            obtenerError5();
//            obtenerError6();
            listaReporteRechazoBean = reporteRechazoService.obtenerReporteRechazo(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerError1() {
        try {
            Integer codBanco;
            reporteRechazoBean = new ReporteRechazoBean();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            /*Lista Cuentas*/
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            listaCuentasPorCobrarBean = procesoEnvioService.obtenerCuentas(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            /*Lista Recuperados*/
            ProcesoRecuperacionService procesoRecuperacionService = BeanFactory.getProcesoRecuperacionService();
//            listaProcesoRecuperacionBean = procesoRecuperacionService.obtenerProcesoRecPorUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            codBanco = procesoRecuperacionService.obtenerMaxIdProcesoBanco(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaProcesoRecuperacionBean = procesoRecuperacionService.obtenerPorProcesoBanco(1444, beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            /*Lista Matriculados*/
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            listaMatriculaBean = matriculaService.obtenerMatricula();

            /*Services Reporte Rechazos*/
            ReporteRechazoService reporteRechazoService = BeanFactory.getReporteRechazoService();
            listaReporteRechazoBean = reporteRechazoService.obtenerReporteRechazo(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            ReporteRechazoBean reporteRechazo = new ReporteRechazoBean();
            CodigoService codigoService = BeanFactory.getCodigoService();
            CodigoBean codigoTipoStatusCtaCte = new CodigoBean();
            codigoTipoStatusCtaCte = codigoService.obtenerPorCodigo(new CodigoBean(0, "Pagado", new TipoCodigoBean(MaristaConstantes.TIP_STATUS_CTA_CTE)));
            String notificacion = "";
            System.out.println("=================================================");

            for (CuentasPorCobrarBean cuenta : listaCuentasPorCobrarBean) {
                for (ProcesoRecuperacionBean recup : listaProcesoRecuperacionBean) {
                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                    String fecha1 = formato.format(cuenta.getFechaVenc());
                    String fecha11[] = fecha1.split("-");
                    String fecha12 = fecha11[0];
                    String fecha13 = fecha11[1];
                    String fecha14 = fecha12 + fecha13;

                    String fecha2 = formato.format(recup.getFechaVen());
                    String fecha21[] = fecha2.split("-");
                    String fecha22 = fecha21[0];
                    String fecha23 = fecha21[1];
                    String fecha24 = fecha22 + fecha23;

                    /*Primer Error*/
                    if (recup.getEstudianteBean().getIdEstudiante() == null
                            || cuenta.getEstudianteBean().getIdEstudiante() == null
                            || cuenta.getIdTipoStatusCtaCte().getIdCodigo() == null
                            || recup.getFlgConcilia() == null
                            || fecha14 == null || fecha24 == null) {
                        System.out.println("Error");
                    } else {
                        if (recup.getEstudianteBean().getIdEstudiante().equals(cuenta.getEstudianteBean().getIdEstudiante())) {
                            if (recup.getEstudianteBean().getIdEstudiante() == null
                                    || cuenta.getEstudianteBean().getIdEstudiante() == null
                                    || cuenta.getIdTipoStatusCtaCte().getIdCodigo() == null
                                    || recup.getFlgConcilia() == null
                                    || cuenta.getProcesoRecuperacionBean().getIdProcesoRecup() == null) {
                                System.out.println("No encontro");
                            } else {
                                if (cuenta.getIdTipoStatusCtaCte().getIdCodigo().equals(codigoTipoStatusCtaCte.getIdCodigo())) {
                                    cuenta.getIdTipoStatusCtaCte().setIdCodigo(1);
                                }
//                                for (ReporteRechazoBean rechazo : listaReporteRechazoBean) {
                                if (recup.getFlgConcilia().equals(cuenta.getIdTipoStatusCtaCte().getIdCodigo())
                                        && recup.getProcesoBancoBean().getIdProcesoBanco() != null
                                        && recup.getIdProcesoRecup().equals(cuenta.getProcesoRecuperacionBean().getIdProcesoRecup())
                                        && cuenta.getIdCtasXCobrar() != null
                                        && fecha24.equals(fecha14)) {
                                    System.out.println("========================================================================Encontro en 1 ================================================================" + cuenta.getEstudianteBean().getIdEstudiante());
                                    reporteRechazoBean.getProcesoRecuperacionBean().getProcesoBancoBean().setIdProcesoBanco(recup.getProcesoBancoBean().getIdProcesoBanco());
                                    reporteRechazoBean.getProcesoRecuperacionBean().setIdProcesoRecup(recup.getIdProcesoRecup());
                                    reporteRechazoBean.getCuentasPorCobrarBean().setIdCtasXCobrar(cuenta.getIdCtasXCobrar());
                                    reporteRechazoBean.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                    reporteRechazoBean.getEstudianteBean().setIdEstudiante(cuenta.getEstudianteBean().getIdEstudiante());
                                    reporteRechazoBean.setCreaPor(beanUsuarioSesion.getUsuario());
                                    reporteRechazoBean.setFlgError(1);
                                    reporteRechazoBean.setDescripcion("Error de Conciliacion");
                                    reporteRechazoService.insertarReporteRechazo(reporteRechazoBean);
                                    break;
                                }
//                                }
                            }
                        } else {
                            System.out.println("Error 2");
                        }
                    }
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerError2() {
        try {
            reporteRechazoBean = new ReporteRechazoBean();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            /*Lista Cuentas*/
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            listaCuentasPorCobrarBean = procesoEnvioService.obtenerCuentas(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            /*Lista Recuperados*/
            ProcesoRecuperacionService procesoRecuperacionService = BeanFactory.getProcesoRecuperacionService();
            listaProcesoRecuperacionBean = procesoRecuperacionService.obtenerProcesoRecPorUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            /*Lista Matriculados*/
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            listaMatriculaBean = matriculaService.obtenerMatricula();
            /*Services Reporte Rechazos*/
            ReporteRechazoService reporteRechazoService = BeanFactory.getReporteRechazoService();
            ReporteRechazoBean reporteRechazo = new ReporteRechazoBean();
            CodigoService codigoService = BeanFactory.getCodigoService();
            CodigoBean codigoTipoStatusCtaCte = new CodigoBean();
            codigoTipoStatusCtaCte = codigoService.obtenerPorCodigo(new CodigoBean(0, "Pagado", new TipoCodigoBean(MaristaConstantes.TIP_STATUS_CTA_CTE)));
            String notificacion = "";
            System.out.println("=================================================");
            for (ProcesoRecuperacionBean recup : listaProcesoRecuperacionBean) {
                for (CuentasPorCobrarBean cuenta : listaCuentasPorCobrarBean) {
                    /*Segundo Error*/
                    if (recup.getEstudianteBean().getIdEstudiante() == null
                            || cuenta.getEstudianteBean().getIdEstudiante() == null
                            || recup.getProcesoBancoBean().getIdProcesoBanco() == null
                            || cuenta.getIdTipoStatusCtaCte().getIdCodigo() == null
                            || recup.getFlgConcilia() == null) {
                        System.out.println("No encontro");
                    } else {
                        if (cuenta.getIdTipoStatusCtaCte().getIdCodigo().equals(codigoTipoStatusCtaCte.getIdCodigo())) {
                            cuenta.getIdTipoStatusCtaCte().setIdCodigo(1);
                        }
                        if (!recup.getEstudianteBean().getPersonaBean().getNombre().equals(cuenta.getEstudianteBean().getPersonaBean().getNombre())
                                && !recup.getEstudianteBean().getPersonaBean().getApepat().equals(cuenta.getEstudianteBean().getPersonaBean().getApepat())
                                && !recup.getEstudianteBean().getPersonaBean().getApemat().equals(cuenta.getEstudianteBean().getPersonaBean().getApemat())
                                && recup.getFlgConcilia().equals(cuenta.getIdTipoStatusCtaCte().getIdCodigo())
                                && recup.getProcesoBancoBean().getIdProcesoBanco() != null
                                && cuenta.getIdCtasXCobrar() != null) {
                            System.out.println("========================================================================Encontro  en 2================================================================" + cuenta.getEstudianteBean().getIdEstudiante());
                            reporteRechazoBean.getProcesoBancoBean().setIdProcesoBanco(recup.getProcesoBancoBean().getIdProcesoBanco());
                            reporteRechazoBean.getProcesoRecuperacionBean().setIdProcesoRecup(recup.getIdProcesoRecup());
                            reporteRechazoBean.getCuentasPorCobrarBean().setIdCtasXCobrar(cuenta.getIdCtasXCobrar());
                            reporteRechazoBean.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                            reporteRechazoBean.getEstudianteBean().setIdEstudiante(cuenta.getEstudianteBean().getIdEstudiante());
                            reporteRechazoBean.setCreaPor(beanUsuarioSesion.getUsuario());
                            reporteRechazoBean.setFlgError(1);
                            reporteRechazoBean.setDescripcion("Nombres y Apellidos no coinciden");
                            reporteRechazoService.insertarReporteRechazo(reporteRechazoBean);
                            break;
                        }
                    }
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerError3() {
        try {
            reporteRechazoBean = new ReporteRechazoBean();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            /*Lista Cuentas*/
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            listaCuentasPorCobrarBean = procesoEnvioService.obtenerCuentas(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            /*Lista Recuperados*/
            ProcesoRecuperacionService procesoRecuperacionService = BeanFactory.getProcesoRecuperacionService();
            listaProcesoRecuperacionBean = procesoRecuperacionService.obtenerProcesoRecPorUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            /*Lista Matriculados*/
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            listaMatriculaBean = matriculaService.obtenerMatricula();
            /*Services Reporte Rechazos*/
            ReporteRechazoService reporteRechazoService = BeanFactory.getReporteRechazoService();
            ReporteRechazoBean reporteRechazo = new ReporteRechazoBean();
            CodigoService codigoService = BeanFactory.getCodigoService();
            CodigoBean codigoTipoStatusCtaCte = new CodigoBean();
            codigoTipoStatusCtaCte = codigoService.obtenerPorCodigo(new CodigoBean(0, "Pagado", new TipoCodigoBean(MaristaConstantes.TIP_STATUS_CTA_CTE)));
            String notificacion = "";
            System.out.println("=================================================");
            for (ProcesoRecuperacionBean recup : listaProcesoRecuperacionBean) {
                for (CuentasPorCobrarBean cuenta : listaCuentasPorCobrarBean) {
                    /*Tercer Error*/
                    if (recup.getEstudianteBean().getIdEstudiante() == null
                            || cuenta.getEstudianteBean().getIdEstudiante() == null
                            || recup.getProcesoBancoBean().getIdProcesoBanco() == null
                            || cuenta.getIdTipoStatusCtaCte().getIdCodigo() == null
                            || recup.getFlgConcilia() == null) {
                        System.out.println("No encontro");
                    } else {
                        if (cuenta.getIdTipoStatusCtaCte().getIdCodigo().equals(codigoTipoStatusCtaCte.getIdCodigo())) {
                            cuenta.getIdTipoStatusCtaCte().setIdCodigo(1);
                        }
                        if (!recup.getEstudianteBean().getPersonaBean().getNombre().equals(cuenta.getEstudianteBean().getPersonaBean().getNombre())
                                && recup.getFlgConcilia().equals(cuenta.getIdTipoStatusCtaCte().getIdCodigo())
                                && recup.getProcesoBancoBean().getIdProcesoBanco() != null
                                && cuenta.getIdCtasXCobrar() != null) {
                            System.out.println("========================================================================Encontro  en 3================================================================" + cuenta.getEstudianteBean().getIdEstudiante());
                            reporteRechazoBean.getProcesoBancoBean().setIdProcesoBanco(recup.getProcesoBancoBean().getIdProcesoBanco());
                            reporteRechazoBean.getProcesoRecuperacionBean().setIdProcesoRecup(recup.getIdProcesoRecup());
                            reporteRechazoBean.getCuentasPorCobrarBean().setIdCtasXCobrar(cuenta.getIdCtasXCobrar());
                            reporteRechazoBean.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                            reporteRechazoBean.getEstudianteBean().setIdEstudiante(cuenta.getEstudianteBean().getIdEstudiante());
                            reporteRechazoBean.setCreaPor(beanUsuarioSesion.getUsuario());
                            reporteRechazoBean.setFlgError(1);
                            reporteRechazoBean.setDescripcion("Nombres no coinciden");
                            reporteRechazoService.insertarReporteRechazo(reporteRechazoBean);
                            break;
                        }
                    }
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerError4() {
        try {
            reporteRechazoBean = new ReporteRechazoBean();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            /*Lista Cuentas*/
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            listaCuentasPorCobrarBean = procesoEnvioService.obtenerCuentas(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            /*Lista Recuperados*/
            ProcesoRecuperacionService procesoRecuperacionService = BeanFactory.getProcesoRecuperacionService();
            listaProcesoRecuperacionBean = procesoRecuperacionService.obtenerProcesoRecPorUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            /*Lista Matriculados*/
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            listaMatriculaBean = matriculaService.obtenerMatricula();
            /*Services Reporte Rechazos*/
            ReporteRechazoService reporteRechazoService = BeanFactory.getReporteRechazoService();
            ReporteRechazoBean reporteRechazo = new ReporteRechazoBean();
            CodigoService codigoService = BeanFactory.getCodigoService();
            CodigoBean codigoTipoStatusCtaCte = new CodigoBean();
            codigoTipoStatusCtaCte = codigoService.obtenerPorCodigo(new CodigoBean(0, "Pagado", new TipoCodigoBean(MaristaConstantes.TIP_STATUS_CTA_CTE)));
            String notificacion = "";
            System.out.println("=================================================");
            for (ProcesoRecuperacionBean recup : listaProcesoRecuperacionBean) {
                for (CuentasPorCobrarBean cuenta : listaCuentasPorCobrarBean) {
                    /*Cuarto Error*/
                    if (recup.getEstudianteBean().getIdEstudiante() == null
                            || cuenta.getEstudianteBean().getIdEstudiante() == null
                            || recup.getProcesoBancoBean().getIdProcesoBanco() == null
                            || cuenta.getIdTipoStatusCtaCte().getIdCodigo() == null
                            || recup.getFlgConcilia() == null) {
                        System.out.println("No encontro");
                    } else {
                        if (cuenta.getIdTipoStatusCtaCte().getIdCodigo().equals(codigoTipoStatusCtaCte.getIdCodigo())) {
                            cuenta.getIdTipoStatusCtaCte().setIdCodigo(1);
                        }
                        if (!recup.getEstudianteBean().getPersonaBean().getApepat().equals(cuenta.getEstudianteBean().getPersonaBean().getApepat())
                                && recup.getFlgConcilia().equals(cuenta.getIdTipoStatusCtaCte().getIdCodigo())
                                && recup.getProcesoBancoBean().getIdProcesoBanco() != null
                                && cuenta.getIdCtasXCobrar() != null) {
                            System.out.println("========================================================================Encontro  en 4================================================================" + cuenta.getEstudianteBean().getIdEstudiante());
                            reporteRechazoBean.getProcesoBancoBean().setIdProcesoBanco(recup.getProcesoBancoBean().getIdProcesoBanco());
                            reporteRechazoBean.getProcesoRecuperacionBean().setIdProcesoRecup(recup.getIdProcesoRecup());
                            reporteRechazoBean.getCuentasPorCobrarBean().setIdCtasXCobrar(cuenta.getIdCtasXCobrar());
                            reporteRechazoBean.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                            reporteRechazoBean.getEstudianteBean().setIdEstudiante(cuenta.getEstudianteBean().getIdEstudiante());
                            reporteRechazoBean.setCreaPor(beanUsuarioSesion.getUsuario());
                            reporteRechazoBean.setFlgError(1);
                            reporteRechazoBean.setDescripcion("Apellido Paterno no coinciden");
                            reporteRechazoService.insertarReporteRechazo(reporteRechazoBean);
                            break;
                        }
                    }
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerError5() {
        try {
            reporteRechazoBean = new ReporteRechazoBean();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            /*Lista Cuentas*/
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            listaCuentasPorCobrarBean = procesoEnvioService.obtenerCuentas(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            /*Lista Recuperados*/
            ProcesoRecuperacionService procesoRecuperacionService = BeanFactory.getProcesoRecuperacionService();
            listaProcesoRecuperacionBean = procesoRecuperacionService.obtenerProcesoRecPorUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            /*Lista Matriculados*/
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            listaMatriculaBean = matriculaService.obtenerMatricula();
            /*Services Reporte Rechazos*/
            ReporteRechazoService reporteRechazoService = BeanFactory.getReporteRechazoService();
            ReporteRechazoBean reporteRechazo = new ReporteRechazoBean();
            CodigoService codigoService = BeanFactory.getCodigoService();
            CodigoBean codigoTipoStatusCtaCte = new CodigoBean();
            codigoTipoStatusCtaCte = codigoService.obtenerPorCodigo(new CodigoBean(0, "Pagado", new TipoCodigoBean(MaristaConstantes.TIP_STATUS_CTA_CTE)));
            String notificacion = "";
            System.out.println("=================================================");
            for (ProcesoRecuperacionBean recup : listaProcesoRecuperacionBean) {
                for (CuentasPorCobrarBean cuenta : listaCuentasPorCobrarBean) {
                    /*Quinto Error*/
                    if (recup.getEstudianteBean().getIdEstudiante() == null
                            || cuenta.getEstudianteBean().getIdEstudiante() == null
                            || recup.getProcesoBancoBean().getIdProcesoBanco() == null
                            || cuenta.getIdTipoStatusCtaCte().getIdCodigo() == null
                            || recup.getFlgConcilia() == null) {
                        System.out.println("No encontro");
                    } else {
                        if (cuenta.getIdTipoStatusCtaCte().getIdCodigo().equals(codigoTipoStatusCtaCte.getIdCodigo())) {
                            cuenta.getIdTipoStatusCtaCte().setIdCodigo(1);
                        }
                        if (!recup.getEstudianteBean().getPersonaBean().getApemat().equals(cuenta.getEstudianteBean().getPersonaBean().getApemat())
                                && recup.getFlgConcilia().equals(cuenta.getIdTipoStatusCtaCte().getIdCodigo())
                                && recup.getProcesoBancoBean().getIdProcesoBanco() != null
                                && cuenta.getIdCtasXCobrar() != null) {
                            System.out.println("========================================================================Encontro  en 5================================================================" + cuenta.getEstudianteBean().getIdEstudiante());
                            reporteRechazoBean.getProcesoBancoBean().setIdProcesoBanco(recup.getProcesoBancoBean().getIdProcesoBanco());
                            reporteRechazoBean.getProcesoRecuperacionBean().setIdProcesoRecup(recup.getIdProcesoRecup());
                            reporteRechazoBean.getCuentasPorCobrarBean().setIdCtasXCobrar(cuenta.getIdCtasXCobrar());
                            reporteRechazoBean.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                            reporteRechazoBean.getEstudianteBean().setIdEstudiante(cuenta.getEstudianteBean().getIdEstudiante());
                            reporteRechazoBean.setCreaPor(beanUsuarioSesion.getUsuario());
                            reporteRechazoBean.setFlgError(1);
                            reporteRechazoBean.setDescripcion("Apellido Materno no coinciden");
                            reporteRechazoService.insertarReporteRechazo(reporteRechazoBean);
                            break;
                        }
                    }
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerError6() {
        try {
            reporteRechazoBean = new ReporteRechazoBean();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            /*Lista Cuentas*/
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            listaCuentasPorCobrarBean = procesoEnvioService.obtenerCuentas(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            /*Lista Recuperados*/
            ProcesoRecuperacionService procesoRecuperacionService = BeanFactory.getProcesoRecuperacionService();
            listaProcesoRecuperacionBean = procesoRecuperacionService.obtenerProcesoRecPorUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            /*Lista Matriculados*/
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            listaMatriculaBean = matriculaService.obtenerMatricula();
            /*Services Reporte Rechazos*/
            ReporteRechazoService reporteRechazoService = BeanFactory.getReporteRechazoService();
            ReporteRechazoBean reporteRechazo = new ReporteRechazoBean();
            CodigoService codigoService = BeanFactory.getCodigoService();
            CodigoBean codigoTipoStatusCtaCte = new CodigoBean();
            codigoTipoStatusCtaCte = codigoService.obtenerPorCodigo(new CodigoBean(0, "Pagado", new TipoCodigoBean(MaristaConstantes.TIP_STATUS_CTA_CTE)));
            String notificacion = "";
            System.out.println("=================================================");
            for (ProcesoRecuperacionBean recup : listaProcesoRecuperacionBean) {
                for (CuentasPorCobrarBean cuenta : listaCuentasPorCobrarBean) {
                    /*Sexto Error*/
                    if (recup.getFlgConcilia() == null
                            || recup.getProcesoBancoBean().getIdProcesoBanco() == null
                            || cuenta.getIdCtasXCobrar() == null) {
                        System.out.println("No encontro");
                    } else {
                        if (recup.getFlgConcilia() == null) {
                            recup.setFlgConcilia(0);
                        }
                        if (recup.getFlgConcilia().equals(0)
                                && recup.getProcesoBancoBean().getIdProcesoBanco() != null
                                && recup.getProcesoBancoBean().getIdProcesoBanco() != null
                                && cuenta.getIdCtasXCobrar() != null) {
                            System.out.println("========================================================================Encontro  en 6================================================================" + cuenta.getEstudianteBean().getIdEstudiante());
                            reporteRechazoBean.getProcesoRecuperacionBean().getProcesoBancoBean().setIdProcesoBanco(recup.getProcesoBancoBean().getIdProcesoBanco());
                            reporteRechazoBean.getProcesoRecuperacionBean().setIdProcesoRecup(recup.getIdProcesoRecup());
                            reporteRechazoBean.getCuentasPorCobrarBean().setIdCtasXCobrar(cuenta.getIdCtasXCobrar());
                            reporteRechazoBean.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                            reporteRechazoBean.getEstudianteBean().setIdEstudiante(cuenta.getEstudianteBean().getIdEstudiante());
                            reporteRechazoBean.setCreaPor(beanUsuarioSesion.getUsuario());
                            reporteRechazoBean.setFlgError(1);
                            reporteRechazoBean.setDescripcion("No conciliado");
                            reporteRechazoService.insertarReporteRechazo(reporteRechazoBean);
                        }
                        break;
                    }
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void corregirErrores(Object object) {
        try {
            reporteFiltroBean = (ReporteRechazoBean) object;
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ReporteRechazoService reporteRechazoService = BeanFactory.getReporteRechazoService();
            reporteRechazoService.obtenerReporteRechazoPorId(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), reporteFiltroBean.getIdReporteRechazo());
            listaReporteRechazoFiltroBean = reporteRechazoService.obtenerReporteRechazoPorId(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), reporteFiltroBean.getIdReporteRechazo());
            String fecha = "";
            for (int i = 0; i < listaReporteRechazoFiltroBean.size(); i++) {
                if (listaReporteRechazoFiltroBean.get(i).getDescripcion().equals("Error de Conciliacion")) {
                    renderTab1 = true;
                    renderTab3 = true;
                    renderGen = true;
                    render1 = true;
                    render2 = false;
                    render3 = false;
                    System.out.println("Error 1 ");
                    break;
                }
                if (listaReporteRechazoFiltroBean.get(i).getDescripcion().equals("Nombres y Apellidos no coinciden") || listaReporteRechazoFiltroBean.get(i).equals("Nombres no coinciden")
                        || listaReporteRechazoFiltroBean.get(i).getDescripcion().equals("Apellido Paterno no coinciden") || listaReporteRechazoFiltroBean.get(i).equals("Apellido Materno no coinciden")) {
                    renderTab2 = true;
                    renderTab3 = true;
                    renderGen = true;
                    render1 = false;
                    render2 = true;
                    render3 = false;
                    System.out.println("Error 2 ");
                    break;
                }
                if (listaReporteRechazoFiltroBean.get(i).getDescripcion().equals("No conciliado")) {
                    renderTab2 = true;
                    renderTab3 = true;
                    renderGen = true;
                    render1 = false;
                    render2 = false;
                    render3 = true;
                    System.out.println("Error 3 ");
                    break;
                }
            }
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            for (int i = 0; i < listaReporteRechazoFiltroBean.size(); i++) {
                String meses = formato.format(listaReporteRechazoFiltroBean.get(i).getCuentasPorCobrarBean().getFechaVenc());
                System.out.println(meses);
                String meses2[] = meses.split("-");
                String meses3 = meses2[0];
                String meses4 = meses2[1];
                System.out.println(meses3);

                System.out.println(meses4);

                if (meses4.equals("01")) {
                    mesPago = "Enero";
                    System.out.println("aqui 1");
                }
                if (meses4.equals("02")) {
                    mesPago = "Febrero";
                    System.out.println("aqui 2");
                }
                if (meses4.equals("03")) {
                    mesPago = "Marzo";
                    System.out.println("aqui 3");
                }
                if (meses4.equals("04")) {
                    mesPago = "Abril";
                    System.out.println("aqui 4");
                }
                if (meses4.equals("05")) {
                    mesPago = "Mayo";
                    System.out.println("aqui 5");
                }
                if (meses4.equals("06")) {
                    mesPago = "Junio";
                    System.out.println("aqui 6");
                }
                if (meses4.equals("07")) {
                    mesPago = "Julio";
                    System.out.println("aqui 7");
                }
                if (meses4.equals("08")) {
                    mesPago = "Agosto";
                    System.out.println("aqui 8");
                }
                if (meses4.equals("09")) {
                    mesPago = "Setiembre";
                    System.out.println("aqui 9");
                }
                if (meses4.equals("10")) {
                    mesPago = "Octubre";
                    System.out.println("aqui 10");
                }
                if (meses4.equals("11")) {
                    mesPago = "Noviembre";
                    System.out.println("aqui 11");
                }
                if (meses4.equals("12")) {
                    mesPago = "Diciembre";
                    System.out.println("aqui 12");
                }
            } 
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            listaCuentasPorCobrarBean = cuentasPorCobrarService.obtenerCuentaPorMat(reporteFiltroBean.getEstudianteBean().getIdEstudiante(),beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            for (int i = 0; i < listaCuentasPorCobrarBean.size(); i++) {
                String mesesCuenta = formato.format(listaCuentasPorCobrarBean.get(i).getFechaVenc());
                System.out.println(mesesCuenta);
                String mesCuenta[] = mesesCuenta.split("-");
                String mesCuenta1 = mesCuenta[0];
                String mesCuenta2 = mesCuenta[1];
                if (mesCuenta2.equals("01")) {
                    mesPago2 = "Enero";
                    System.out.println("aqui 1");
                }
                if (mesCuenta2.equals("02")) {
                    mesPago2 = "Febrero";
                    System.out.println("aqui 2");
                }
                if (mesCuenta2.equals("03")) {
                    mesPago2 = "Marzo";
                    System.out.println("aqui 3");
                }
                if (mesCuenta2.equals("04")) {
                    mesPago2 = "Abril";
                    System.out.println("aqui 4");
                }
                if (mesCuenta2.equals("05")) {
                    mesPago2 = "Mayo";
                    System.out.println("aqui 5");
                }
                if (mesCuenta2.equals("06")) {
                    mesPago2 = "Junio";
                    System.out.println("aqui 6");
                }
                if (mesCuenta2.equals("07")) {
                    mesPago2 = "Julio";
                    System.out.println("aqui 7");
                }
                if (mesCuenta2.equals("08")) {
                    mesPago2 = "Agosto";
                    System.out.println("aqui 8");
                }
                if (mesCuenta2.equals("09")) {
                    mesPago2 = "Setiembre";
                    System.out.println("aqui 9");
                }
                if (mesCuenta2.equals("10")) {
                    mesPago2 = "Octubre";
                    System.out.println("aqui 10");
                }
                if (mesCuenta2.equals("11")) {
                    mesPago2 = "Noviembre";
                    System.out.println("aqui 11");
                }
                if (mesCuenta2.equals("12")) {
                    mesPago2 = "Diciembre";
                    System.out.println("aqui 12");
                }
            }
//            reporteRechazoBean.setIdReporteRechazo(reporteFiltroBean.getIdReporteRechazo());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void validarErrores() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ReporteRechazoService reporteRechazoService = BeanFactory.getReporteRechazoService();
            listaReporteRechazoBean = reporteRechazoService.obtenerReporteRechazo(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            listaCuentasPorCobrarBean = procesoEnvioService.obtenerCuentas(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            Integer idRechazo = 0;
            Integer datoRechazo = 0;
            Integer m = 0;
            idRechazo = reporteRechazoService.obtenerMaxIdRechazo(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            datoRechazo = reporteRechazoService.obtenerMinIdRechazo(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

//            if (getReporteRechazoBean().getIdReporteRechazo() == null) {
//                obtenerErrorGeneral();
//            }
//            if (getReporteRechazoBean().getCuentasPorCobrarBean().getIdCtasXCobrar().equals(getCuentasPorCobrarBean().getIdCtasXCobrar())) {
//                System.out.println("iguales " + m);
//            }
            obtenerErrorGeneral();
            for (int i = 0; i < listaReporteRechazoBean.size(); i++) {
                for (int j = 0; j < listaCuentasPorCobrarBean.size(); j++) {
                    idRechazo++;
                    if (listaReporteRechazoBean.get(i).getCuentasPorCobrarBean().getIdCtasXCobrar().equals(listaCuentasPorCobrarBean.get(j).getIdCtasXCobrar())) {
                        obtenerReporteRechazo();
                        break;
                    } else {
                        if (listaReporteRechazoBean.get(i).getIdReporteRechazo() == null) {
                            obtenerErrorGeneral();
                            break;
                        }
                    }
                    break;
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void modificarCuenta(Object object) {
        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            CuentasPorCobrarBean cuentas = (CuentasPorCobrarBean) object;
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            listaCuentasPorCobrarBean = procesoEnvioService.obtenerCuentas(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaCuentaFiltroBean = procesoEnvioService.obtenerCuentaId(cuentas.getIdCtasXCobrar());
            ReporteRechazoService reporteRechazoService = BeanFactory.getReporteRechazoService();
            listaReporteRechazoBean = reporteRechazoService.obtenerReporteRechazo(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            for (int i = 0; i < listaCuentasPorCobrarBean.size(); i++) {
                for (int j = 0; j < listaCuentaFiltroBean.size(); j++) {
                    for (int k = 0; k < listaReporteRechazoBean.size(); k++) {
                        String fecha = formato.format(listaCuentasPorCobrarBean.get(i).getFechaVenc());
                        String fecha1[] = fecha.split("-");
                        String fecha2 = fecha1[0];
                        String fecha3 = fecha1[1];
                        String fecha4 = fecha1[2];
                        String fecha5 = fecha2 + fecha3 + fecha4;
                        String fechas = formato.format(listaCuentaFiltroBean.get(j).getFechaVenc());
                        String fechas1[] = fechas.split("-");
                        String fechas11 = fechas1[0];
                        String fechas12 = fechas1[1];
                        String fechas13 = fechas1[2];
                        String fechas14 = fechas11 + fechas12 + fechas13;
                        if (fechas14.equals(fecha5) && listaCuentaFiltroBean.get(j).getEstudianteBean().getIdEstudiante().equals(listaCuentasPorCobrarBean.get(i).getEstudianteBean().getIdEstudiante())
                                && listaReporteRechazoBean.get(k).getEstudianteBean().getIdEstudiante().equals(listaCuentaFiltroBean.get(j).getEstudianteBean().getIdEstudiante())) {
                            listaCuentaFiltroBean.get(j).getIdTipoStatusCtaCte().setIdCodigo(19404);
                            listaCuentaFiltroBean.get(j).setMontoPagado(listaReporteRechazoBean.get(k).getCuentasPorCobrarBean().getMontoPagado());
                            listaCuentaFiltroBean.get(j).setFechaPago(listaCuentaFiltroBean.get(j).getFechaPago());
                            procesoEnvioService.modificarStatusCuenta(listaCuentaFiltroBean.get(j));
                        }
                        if (fechas14.equals(fecha5) && listaCuentaFiltroBean.get(j).getEstudianteBean().getIdEstudiante().equals(listaCuentasPorCobrarBean.get(i).getEstudianteBean().getIdEstudiante())
                                && listaReporteRechazoBean.get(k).getEstudianteBean().getIdEstudiante().equals(listaCuentaFiltroBean.get(j).getEstudianteBean().getIdEstudiante())) {
                            listaReporteRechazoBean.get(k).setFlgError(0);
                            reporteRechazoService.modificarStatusError(listaReporteRechazoBean.get(k));
                        }
                    }
                }
            }
            listaCuentaFiltroBean = procesoEnvioService.obtenerCuentaId(cuentas.getIdCtasXCobrar());
            renderGen = false;
            listaReporteRechazoBean = reporteRechazoService.obtenerReporteRechazo(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void activarOpcion() {
        try {
            renderLink = false;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public ProcesoRecuperacionBean getProcesoRecuperacionBean() {
        if (procesoRecuperacionBean == null) {
            procesoRecuperacionBean = new ProcesoRecuperacionBean();
        }
        return procesoRecuperacionBean;
    }

    public void setProcesoRecuperacionBean(ProcesoRecuperacionBean procesoRecuperacionBean) {
        this.procesoRecuperacionBean = procesoRecuperacionBean;
    }

    public List<ProcesoRecuperacionBean> getListaProcesoRecuperacionBean() {
        if (listaProcesoRecuperacionBean == null) {
            listaProcesoRecuperacionBean = new ArrayList<>();
        }
        return listaProcesoRecuperacionBean;
    }

    public void setListaProcesoRecuperacionBean(List<ProcesoRecuperacionBean> listaProcesoRecuperacionBean) {
        this.listaProcesoRecuperacionBean = listaProcesoRecuperacionBean;
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

    public List<CuentasPorCobrarBean> getListaCuentasPorCobrarBean() {
        if (listaCuentasPorCobrarBean == null) {
            listaCuentasPorCobrarBean = new ArrayList<>();
        }
        return listaCuentasPorCobrarBean;
    }

    public void setListaCuentasPorCobrarBean(List<CuentasPorCobrarBean> listaCuentasPorCobrarBean) {
        this.listaCuentasPorCobrarBean = listaCuentasPorCobrarBean;
    }

    public List<ProcesoRecuperacionBean> getListaFiltroRecuperacionBean() {
        if (listaFiltroRecuperacionBean == null) {
            listaFiltroRecuperacionBean = new ArrayList<>();
        }
        return listaFiltroRecuperacionBean;
    }

    public void setListaFiltroRecuperacionBean(List<ProcesoRecuperacionBean> listaFiltroRecuperacionBean) {
        this.listaFiltroRecuperacionBean = listaFiltroRecuperacionBean;
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

    public List<EstudianteBean> getListaEstudianteBean() {
        if (listaEstudianteBean == null) {
            listaEstudianteBean = new ArrayList<>();
        }
        return listaEstudianteBean;
    }

    public void setListaEstudianteBean(List<EstudianteBean> listaEstudianteBean) {
        this.listaEstudianteBean = listaEstudianteBean;
    }

    public MatriculaBean getMatriculaBean() {
        if (matriculaBean == null) {
            matriculaBean = new MatriculaBean();
        }
        return matriculaBean;
    }

    public void setMatriculaBean(MatriculaBean matriculaBean) {
        this.matriculaBean = matriculaBean;
    }

    public List<MatriculaBean> getListaMatriculaBean() {
        if (listaMatriculaBean == null) {
            listaMatriculaBean = new ArrayList<>();
        }
        return listaMatriculaBean;
    }

    public void setListaMatriculaBean(List<MatriculaBean> listaMatriculaBean) {
        this.listaMatriculaBean = listaMatriculaBean;
    }

    public ReporteRechazoBean getReporteRechazoBean() {
        if (reporteRechazoBean == null) {
            reporteRechazoBean = new ReporteRechazoBean();
        }
        return reporteRechazoBean;
    }

    public void setReporteRechazoBean(ReporteRechazoBean reporteRechazoBean) {
        this.reporteRechazoBean = reporteRechazoBean;
    }

    public List<ReporteRechazoBean> getListaReporteRechazoBean() {
        if (listaReporteRechazoBean == null) {
            listaReporteRechazoBean = new ArrayList<>();
        }
        return listaReporteRechazoBean;
    }

    public void setListaReporteRechazoBean(List<ReporteRechazoBean> listaReporteRechazoBean) {
        this.listaReporteRechazoBean = listaReporteRechazoBean;
    }

    public Boolean getRender1() {
        return render1;
    }

    public void setRender1(Boolean render1) {
        this.render1 = render1;
    }

    public Boolean getRender2() {
        return render2;
    }

    public void setRender2(Boolean render2) {
        this.render2 = render2;
    }

    public Boolean getRender3() {
        return render3;
    }

    public void setRender3(Boolean render3) {
        this.render3 = render3;
    }

    public ReporteRechazoBean getReporteFiltroBean() {
        if (reporteFiltroBean == null) {
            reporteFiltroBean = new ReporteRechazoBean();
        }
        return reporteFiltroBean;
    }

    public void setReporteFiltroBean(ReporteRechazoBean reporteFiltroBean) {
        this.reporteFiltroBean = reporteFiltroBean;
    }

    public List<ReporteRechazoBean> getListaReporteRechazoFiltroBean() {
        if (listaReporteRechazoFiltroBean == null) {
            listaReporteRechazoFiltroBean = new ArrayList<>();
        }
        return listaReporteRechazoFiltroBean;
    }

    public void setListaReporteRechazoFiltroBean(List<ReporteRechazoBean> listaReporteRechazoFiltroBean) {
        this.listaReporteRechazoFiltroBean = listaReporteRechazoFiltroBean;
    }

    public Boolean getRenderGen() {
        return renderGen;
    }

    public void setRenderGen(Boolean renderGen) {
        this.renderGen = renderGen;
    }

    public String getMesPago() {
        return mesPago;
    }

    public void setMesPago(String mesPago) {
        this.mesPago = mesPago;
    }

    public String getMesPago2() {
        return mesPago2;
    }

    public void setMesPago2(String mesPago2) {
        this.mesPago2 = mesPago2;
    }

    public List<CuentasPorCobrarBean> getListaCuentaFiltroBean() {
        if (listaCuentaFiltroBean == null) {
            listaCuentaFiltroBean = new ArrayList<>();
        }
        return listaCuentaFiltroBean;
    }

    public void setListaCuentaFiltroBean(List<CuentasPorCobrarBean> listaCuentaFiltroBean) {
        this.listaCuentaFiltroBean = listaCuentaFiltroBean;
    }

    public Boolean getRenderLink() {
        return renderLink;
    }

    public void setRenderLink(Boolean renderLink) {
        this.renderLink = renderLink;
    }

    public Boolean getRenderTab1() {
        return renderTab1;
    }

    public void setRenderTab1(Boolean renderTab1) {
        this.renderTab1 = renderTab1;
    }

    public Boolean getRenderTab2() {
        return renderTab2;
    }

    public void setRenderTab2(Boolean renderTab2) {
        this.renderTab2 = renderTab2;
    }

    public Boolean getRenderTab3() {
        return renderTab3;
    }

    public void setRenderTab3(Boolean renderTab3) {
        this.renderTab3 = renderTab3;
    }

}
