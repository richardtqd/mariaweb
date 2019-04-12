/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
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
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CajaGenBean;
import pe.marista.sigma.bean.CajeroCajaBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.CuentaBancoBean;
import pe.marista.sigma.bean.DetDocIngresoBean;
import pe.marista.sigma.bean.EntidadBean;
import pe.marista.sigma.bean.JefeUniOrgBean;
import pe.marista.sigma.bean.TipoCambioBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.CajaGenCierreRepBean;
import pe.marista.sigma.bean.reporte.CajaGenRepBean;
import pe.marista.sigma.bean.reporte.CajaGeneralCtaRepBean;
import pe.marista.sigma.bean.reporte.CajaGeneralCtasRepBean;
import pe.marista.sigma.bean.reporte.CajaGeneralRepBean;
import pe.marista.sigma.bean.reporte.DetCajaGenCierreRepBean;
import pe.marista.sigma.bean.reporte.DocIngresoRepBeanDesglosado;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CajaGenService;
import pe.marista.sigma.service.CajeroService;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.CuentaBancoService;
import pe.marista.sigma.service.DocIngresoService;
import pe.marista.sigma.service.EntidadService;
import pe.marista.sigma.service.JefeUniOrgService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.util.MensajesBackEnd;

/**
 *
 * @author MS002
 */
public class CajaGenMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of CajaGenMB
     */
    @PostConstruct
    public void CajaGenMB() {
        try {
            usuario = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            getDetDocIngresoBean();
            getCajaGenBean();
            getCajaGenBeanArqueo();
            fechaActual = new GregorianCalendar();
            getDetDocIngresoBean().setFechaInicio(fechaActual.getTime());
            getDetDocIngresoBean().setFechaFin(fechaActual.getTime());

//        fechaActual = new GregorianCalendar();
            getCajaGenBeanArqueo().setFechaInicio(fechaActual.getTime());
            getCajaGenBeanArqueo().setFechaFin(fechaActual.getTime());

            getCajaGenBeanCierre().setFecCierre(fechaActual.getTime());

            getTipoCambioBean().setFechaTc(fechaActual.getTime());

            CodigoService codigoService = BeanFactory.getCodigoService();
            getListaTipoCajaGen();
            listaTipoCajaGen = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_STATUS_CAJA_GEN));

            /* CASO DE BINGOS */
//            Double tcv = 3.388;
//            Double tcc = 3.386;
//            BigDecimal tv = new BigDecimal(tcv);
//            BigDecimal tc = new BigDecimal(tcc);
//            getTipoCambioBean();
//            getTipoCambioBean().setTcVenta(tv);
//            getTipoCambioBean().setTcCompra(tc);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    private UsuarioBean usuario;
    //Apertura
    private CajaGenBean cajaApertura;
    private CajaGenBean cajaGenBean;
    private String disabled;

    //Cierre
    private CajaGenBean cajaCierre;
    private CajaGenBean cajaGenBeanCierre;
    private String disabledCierre;
    private CajaGenBean cajaGenBeanCierreDiaAnte;

    //DepósitoEnCuenta
    private CajaGenBean cajaGenBeanDeposito;
    private List<EntidadBean> listaBancos;
    private List<EntidadBean> listaBancosCongre;
    private String disabledDepSol = "false";
    private String disabledDepDol = "false";
    private String disabledDepCongreSol = "false";
    private String disabledDepCongreDol = "false";
    private List<CuentaBancoBean> listaCuentasBancoSol;
    private List<CuentaBancoBean> listaCuentasBancoDol;

    private List<CuentaBancoBean> listaCuentasBancoCongreSol;
    private List<CuentaBancoBean> listaCuentasBancoCongreDol;
    private String disabledDeposito = "false";

    //Arqueo de Caja
    private CajaGenBean cajaGenBeanArqueo;
    private Double enCajaSol;
    private Double faltanteSol;
    private Double enCajaDol;
    private Double faltanteDol;

    //detalle doc ingreso
    private DetDocIngresoBean detDocIngresoBean;
    private List<DetDocIngresoBean> listaDetDocIngreso;
    private Calendar fechaActual;

    //reportes
    private List<CajaGenBean> listaAperturaCaja;
    private List<CajaGenBean> listaCierreCaja;
    private List<CajaGenBean> listaArqueoCaja;
    private List<Integer> listaIdCajaGen;
    private List<Integer> listaIdCajas;

    private String fechaInicio;
    private String fechaFin;
    private TipoCambioBean tipoCambioBean;

    //CODIGO TIPO CAJA GENERAL
    private List<CodigoBean> listaTipoCajaGen;

    public void verReporte() {
        try {

            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            fechaActual = new GregorianCalendar();

            if (detDocIngresoBean.getFechaInicio() != null) {
                Timestamp t = new Timestamp(detDocIngresoBean.getFechaInicio().getTime());
                t.setHours(0);
                t.setMinutes(0);
                t.setSeconds(0);
                detDocIngresoBean.setFechaInicio(t);

            }
            if (detDocIngresoBean.getFechaFin() != null) {
                Timestamp u = new Timestamp(detDocIngresoBean.getFechaFin().getTime());
                u.setHours(23);
                u.setMinutes(59);
                u.setSeconds(59);
                detDocIngresoBean.setFechaFin(u);

            }
            listaDetDocIngreso = docIngresoService.reporteDelDia(detDocIngresoBean);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public CajaGenBean getCajaGenBean() {
        if (cajaGenBean == null) {
            cajaGenBean = new CajaGenBean();
            cajaGenBean.setAperturaSol(new Double("0.00"));
            cajaGenBean.setAperturaDol(new Double("0.00"));
        }
        return cajaGenBean;
    }

    public void setCajaGenBean(CajaGenBean cajaGenBean) {
        this.cajaGenBean = cajaGenBean;
    }

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public CajaGenBean getCajaApertura() {
        if (cajaApertura == null) {
            cajaApertura = new CajaGenBean();
        }
        return cajaApertura;
    }

    public void setCajaApertura(CajaGenBean cajaApertura) {
        this.cajaApertura = cajaApertura;
    }

    public CajaGenBean getCajaGenBeanCierre() {
        if (cajaGenBeanCierre == null) {
            cajaGenBeanCierre = new CajaGenBean();
        }
        return cajaGenBeanCierre;
    }

    public void setCajaGenBeanCierre(CajaGenBean cajaGenBeanCierre) {
        this.cajaGenBeanCierre = cajaGenBeanCierre;
    }

    public CajaGenBean getCajaCierre() {
        if (cajaCierre == null) {
            cajaCierre = new CajaGenBean();
        }
        return cajaCierre;
    }

    public void setCajaCierre(CajaGenBean cajaCierre) {
        this.cajaCierre = cajaCierre;
    }

    public String getDisabledCierre() {
        return disabledCierre;
    }

    public void setDisabledCierre(String disabledCierre) {
        this.disabledCierre = disabledCierre;
    }

    public CajaGenBean getCajaGenBeanDeposito() {
        if (cajaGenBeanDeposito == null) {
            cajaGenBeanDeposito = new CajaGenBean();
        }
        return cajaGenBeanDeposito;
    }

    public void setCajaGenBeanDeposito(CajaGenBean cajaGenBeanDeposito) {
        this.cajaGenBeanDeposito = cajaGenBeanDeposito;
    }

    public List<EntidadBean> getListaBancos() {
        if (listaBancos == null) {
            listaBancos = new ArrayList<>();
        }
        return listaBancos;
    }

    public void setListaBancos(List<EntidadBean> listaBancos) {
        this.listaBancos = listaBancos;
    }

    public String getDisabledDepSol() {
        return disabledDepSol;
    }

    public void setDisabledDepSol(String disabledDepSol) {
        this.disabledDepSol = disabledDepSol;
    }

    public String getDisabledDepDol() {
        return disabledDepDol;
    }

    public void setDisabledDepDol(String disabledDepDol) {
        this.disabledDepDol = disabledDepDol;
    }

    public Double getEnCajaSol() {
        return enCajaSol;
    }

    public void setEnCajaSol(Double enCajaSol) {
        this.enCajaSol = enCajaSol;
    }

    public Double getFaltanteSol() {
        return faltanteSol;
    }

    public void setFaltanteSol(Double faltanteSol) {
        this.faltanteSol = faltanteSol;
    }

    public Double getEnCajaDol() {
        return enCajaDol;
    }

    public void setEnCajaDol(Double enCajaDol) {
        this.enCajaDol = enCajaDol;
    }

    public Double getFaltanteDol() {
        return faltanteDol;
    }

    public void setFaltanteDol(Double faltanteDol) {
        this.faltanteDol = faltanteDol;
    }

    public CajaGenBean getCajaGenBeanArqueo() {
        if (cajaGenBeanArqueo == null) {
            cajaGenBeanArqueo = new CajaGenBean();
        }
        return cajaGenBeanArqueo;
    }

    public void setCajaGenBeanArqueo(CajaGenBean cajaGenBeanArqueo) {
        this.cajaGenBeanArqueo = cajaGenBeanArqueo;
    }

    public List<CuentaBancoBean> getListaCuentasBancoSol() {
        return listaCuentasBancoSol;
    }

    public void setListaCuentasBancoSol(List<CuentaBancoBean> listaCuentasBancoSol) {
        this.listaCuentasBancoSol = listaCuentasBancoSol;
    }

    public List<CuentaBancoBean> getListaCuentasBancoDol() {
        return listaCuentasBancoDol;
    }

    public void setListaCuentasBancoDol(List<CuentaBancoBean> listaCuentasBancoDol) {
        this.listaCuentasBancoDol = listaCuentasBancoDol;
    }

    public String getDisabledDeposito() {
        return disabledDeposito;
    }

    public void setDisabledDeposito(String disabledDeposito) {
        this.disabledDeposito = disabledDeposito;
    }

    public CajaGenBean getCajaGenBeanCierreDiaAnte() {
        if (cajaGenBeanCierreDiaAnte == null) {
            cajaGenBeanCierreDiaAnte = new CajaGenBean();
        }
        return cajaGenBeanCierreDiaAnte;
    }

    public void setCajaGenBeanCierreDiaAnte(CajaGenBean cajaGenBeanCierreDiaAnte) {
        this.cajaGenBeanCierreDiaAnte = cajaGenBeanCierreDiaAnte;
    }

    public void autenticarCajaCierre() {
        try {
            cajaGenBeanDeposito = new CajaGenBean();
//            SimpleDateFormat formatoDiaCompleto = new SimpleDateFormat("dd-MM-yy");
//            String dateCompleto = formatoDiaCompleto.format(new Date());
//            UsuarioBean usuario = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
//            EntidadService entidadService= BeanFactory.getEntidadService();
            CajeroCajaBean cajeroCajaBean = new CajeroCajaBean();
            CajaGenService cajaGenService = BeanFactory.getCajaGenService();
            CajeroService cajeroService = BeanFactory.getCajeroService();
            cajeroCajaBean.setUnidadNegocioBean(usuario.getPersonalBean().getUnidadNegocioBean());
            cajeroCajaBean.setUsuarioBean(usuario);
///-----------IP CLIENTE
//            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//            String remoteAddress = request.getRemoteAddr();
//            cajeroCajaBean.getCajaBean().setHostIp(remoteAddress);
//----------IP SERVIDOR
            InetAddress localHost = InetAddress.getLocalHost();
            cajeroCajaBean.getCajaBean().setHostIp(localHost.getHostAddress());
            cajeroCajaBean = cajeroService.autenticarUsuarioConCaja(cajeroCajaBean);
            if (cajeroCajaBean != null) {
                getCajaGenBeanCierre().setUniNeg(usuario.getPersonalBean().getUnidadNegocioBean());
                getCajaGenBeanCierre().setCajaBean(cajeroCajaBean.getCajaBean());
                getCajaGenBeanCierre().setFecApertura(new Date());
                Calendar.getInstance().get((Calendar.YEAR));
                SimpleDateFormat formato = new SimpleDateFormat("yyyy");
                String date = formato.format(new Date());
                fechaActual = new GregorianCalendar();

                getCajaGenBeanCierre().setAnio(Integer.parseInt(date));
                getCajaGenBeanCierre().setUsuarioBean(usuario);
//                getCajaGenBeanCierre().setFecApertura(formatoDiaCompleto.parse(dateCompleto));
                getCajaGenBeanCierre().setFecApertura(fechaActual.getTime());
//                getCajaGenBeanCierre().setFecCierre(formatoDiaCompleto.parse(dateCompleto));
                getCajaGenBeanCierre().setFecCierre(fechaActual.getTime());
                this.disabledCierre = "false";
                CajaGenBean cajaGeneral = new CajaGenBean();
                cajaGeneral = cajaGenService.verificarApertura(cajaGenBeanCierre);
                verificarCierreDiaAnterior(cajaGenBeanCierre);
                if (cajaGenBeanCierreDiaAnte.getFecCierre() == null) {
                    this.disabledCierre = "true";
                    this.disabledDeposito = "true";
//                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, MensajesBackEnd.getValueOfKey("mensajeNoCierreDiaAnt", null), MensajesBackEnd.getValueOfKey("msgsNoCaj", null)));
                    new MensajePrime().addInformativeMessagePer("mensajeNoCierreDiaAnt");
                } else {
                    if (cajaGeneral != null) {
                        this.cajaCierre = cajaGeneral;
                        this.cajaGenBeanDeposito = cajaGeneral;
                        CajaGenBean cajaGeneralCierre = new CajaGenBean();
                        cajaGenBeanCierre.setFlgTipoCajaGen(Boolean.TRUE);
                        cajaGeneralCierre = cajaGenService.verificarCierre(cajaGenBeanCierre);
                        if (cajaGeneralCierre != null) {
                            this.cajaCierre = cajaGeneralCierre;
                            this.cajaGenBeanCierre = cajaGeneralCierre;
                            this.cajaGenBeanDeposito = cajaGeneralCierre;
                            this.disabledCierre = "true";
                        } else {
                            getCajaGenBeanCierre().setFecCierre(new Date());
                            this.disabledCierre = "false";
                        }
                    } else {
//                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, MensajesBackEnd.getValueOfKey("mensajeNoApertura", null), MensajesBackEnd.getValueOfKey("msgsNoCaj", null)));
                        new MensajePrime().addInformativeMessagePer("mensajeNoApertura");

                        this.disabledCierre = "true";
                        this.disabledDeposito = "false";
                    }
                }
            } else {
                getCajaGenBeanCierre().setFecCierre(new Date());
//                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, MensajesBackEnd.getValueOfKey("mensajeNoCaja", null), MensajesBackEnd.getValueOfKey("msgsNoCaj", null)));
                new MensajePrime().addInformativeMessagePer("mensajeNoCaja");
                this.disabledCierre = "true";
                this.disabledDeposito = "true";
            }

            verHistorialCierrePorCajero();

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorIdApertura(CajaGenBean cajaGenBean) {
        try {
            CajaGenService cajaGenService = BeanFactory.getCajaGenService();
            cajaGenBean.setUniNeg(usuario.getPersonalBean().getUnidadNegocioBean());
            cajaGenBeanDeposito = cajaGenService.obtenerPorId(cajaGenBean);
            if (!Objects.equals(cajaGenBeanDeposito.getAperturaSol(), cajaGenBeanDeposito.getAperturaSolAnt())
                    || !Objects.equals(cajaGenBeanDeposito.getAperturaDol(), cajaGenBeanDeposito.getAperturaDolAnt())) {
                CajaGenBean cajaApertura = new CajaGenBean();
//                    cajaApertura = cajaGenBeanDeposito;
                cajaGenBeanDeposito.setFlgTipoCajaGen(Boolean.TRUE);
                cajaApertura = cajaGenService.verificarRegistrosCajaDocIng(cajaGenBeanDeposito);
                if (cajaApertura == null) {
                    this.disabled = "true";
                    new MensajePrime().addInformativeMessagePer("msjRegDocIngORegDocEgre");
                } else {
                    if (cajaApertura.getIdCajaGen() != null) {
                        CajaGenBean cajaApertura2 = new CajaGenBean();
//                        cajaApertura2 = cajaGenBeanDeposito;
                        cajaGenBeanDeposito.setFlgTipoCajaGen(Boolean.TRUE);
                        cajaApertura2 = cajaGenService.verificarRegistrosCajaDocEgre(cajaGenBeanDeposito);
                        if (cajaApertura2.getIdCajaGen() != null) {
                            this.disabled = "false";
                        }
                    } else {
                        this.disabled = "true";
                        new MensajePrime().addInformativeMessagePer("msjRegDocIngORegDocEgre");
                    }
                }
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorBcosCtas() {
        try {
            CajaGenService cajaGenService = BeanFactory.getCajaGenService();
            CajaGenBean cajColegio = new CajaGenBean();
            cajColegio.setAyudaBanco(Boolean.TRUE);
            cajColegio.setUniNeg(usuario.getPersonalBean().getUnidadNegocioBean());
            CajaGenBean cajCongre = new CajaGenBean();
            cajCongre.setUniNeg(usuario.getPersonalBean().getUnidadNegocioBean());
            cajCongre.setAyudaBanco(Boolean.FALSE);
            listaBancos = cajaGenService.obtenerBancosDeposito(cajColegio);
            System.out.println("obtenerPorBcosCtas");
            if (listaBancos.size() == 1) {
                getCajaGenBeanDeposito().setRucBanco(listaBancos.get(0).getRuc());
            }
            listaBancosCongre = cajaGenService.obtenerBancosDeposito(cajCongre);
            if (listaBancosCongre.size() == 1) {
                getCajaGenBeanDeposito().setRucBancoCongre(listaBancosCongre.get(0).getRuc());
            }
            obtenerCuentas();

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorBcosCtasPorId() {
        try {
            CajaGenService cajaGenService = BeanFactory.getCajaGenService();
            CajaGenBean cajColegio = new CajaGenBean();
            cajColegio.setAyudaBanco(Boolean.TRUE);
            cajColegio.setUniNeg(usuario.getPersonalBean().getUnidadNegocioBean());
            CajaGenBean cajCongre = new CajaGenBean();
            cajCongre.setUniNeg(usuario.getPersonalBean().getUnidadNegocioBean());
            cajCongre.setAyudaBanco(Boolean.FALSE);
            listaBancos = cajaGenService.obtenerBancosDeposito(cajColegio);
            System.out.println("obtenerPorBcosCtasPorId");
            listaBancosCongre = cajaGenService.obtenerBancosDeposito(cajCongre);
            obtenerCuentasPorId();

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorIdCierre(CajaGenBean cajaGenBean) {
        try {
            EntidadService entidadService = BeanFactory.getEntidadService();
            CajaGenService cajaGenService = BeanFactory.getCajaGenService();
            cajaGenBean.setUniNeg(usuario.getPersonalBean().getUnidadNegocioBean());
            cajaGenBeanDeposito = cajaGenService.obtenerPorId(cajaGenBean);
            System.out.println(cajaGenBeanDeposito.getIngresoSol());
            this.disabled = "true";

            if (cajaGenBeanDeposito.getFecDeposito() == null) {
                cajaGenBeanDeposito.setFecDeposito(new Date());
            }
            if (cajaGenBeanDeposito.getAperturaSol() == null) {
                cajaGenBeanDeposito.setAperturaSol(new Double("0.00"));
            }
            if (cajaGenBeanDeposito.getAperturaDol() == null) {
                cajaGenBeanDeposito.setAperturaDol(new Double("0.00"));
            }
            if (cajaGenBeanDeposito.getIngresoSol() == null) {
                cajaGenBeanDeposito.setIngresoSol(new Double("0.00"));
            }
            if (cajaGenBeanDeposito.getMontoDepositoSol() == null) {
                cajaGenBeanDeposito.setMontoDepositoSol(new Double("0.00"));
            }
            if (cajaGenBeanDeposito.getIngresoDol() == null) {
                cajaGenBeanDeposito.setIngresoDol(new Double("0.00"));
            }
            if (cajaGenBeanDeposito.getMontoDepositoDol() == null) {
                cajaGenBeanDeposito.setMontoDepositoDol(new Double("0.00"));
            }
            if (cajaGenBeanDeposito.getIngresoPos1() == null) {
                cajaGenBeanDeposito.setIngresoPos1(new Double("0.00"));
            }
            if (cajaGenBeanDeposito.getIngresoPos2() == null) {
                cajaGenBeanDeposito.setIngresoPos2(new Double("0.00"));
            }
            if (cajaGenBeanDeposito.getEgresoSol() == null) {
                cajaGenBeanDeposito.setEgresoSol(new Double("0.00"));
            }
            if (cajaGenBeanDeposito.getEgresoDol() == null) {
                cajaGenBeanDeposito.setEgresoDol(new Double("0.00"));
            }

            //congregacion
            if (cajaGenBeanDeposito.getIngresoCongreEfectivoSol() == null) {
                cajaGenBeanDeposito.setIngresoCongreEfectivoSol(new Double("0.00"));
            }
            if (cajaGenBeanDeposito.getMontoDepositoCongreSol() == null) {
                cajaGenBeanDeposito.setMontoDepositoCongreSol(new Double("0.00"));
            }
            if (cajaGenBeanDeposito.getIngresoCongreEfectivoDol() == null) {
                cajaGenBeanDeposito.setIngresoCongreEfectivoDol(new Double("0.00"));
            }
            if (cajaGenBeanDeposito.getMontoDepositoCongreDol() == null) {
                cajaGenBeanDeposito.setMontoDepositoCongreDol(new Double("0.00"));
            }
            if (cajaGenBeanDeposito.getNumOperacionCongreSol() == null) {
                cajaGenBeanDeposito.setNumOperacionCongreSol("0");
            }
            if (cajaGenBeanDeposito.getNumOperacionSol() == null) {
                cajaGenBeanDeposito.setNumOperacionSol("0");
            }
            if (cajaGenBeanDeposito.getNumOperacionCongreDol() == null) {
                cajaGenBeanDeposito.setNumOperacionCongreDol("0");
            }
            if (cajaGenBeanDeposito.getNumOperacionDol() == null) {
                cajaGenBeanDeposito.setNumOperacionDol("0");
            }
            if (cajaGenBeanDeposito != null) {
                CajaGenBean cajColegio = new CajaGenBean();
                cajColegio.setAyudaBanco(Boolean.TRUE);
                cajColegio.setUniNeg(usuario.getPersonalBean().getUnidadNegocioBean());
                listaBancos = cajaGenService.obtenerBancosDeposito(cajColegio);
                System.out.println("obtenerPorIdCierre");
                if (cajaGenBeanDeposito.getRucBanco() != null) {
                    EntidadBean ent = new EntidadBean();
                    ent.setUnidadNegocioBean(cajaGenBeanDeposito.getUniNeg());
                    ent.setRuc(cajaGenBeanDeposito.getRucBanco());
                    ent = entidadService.obtenerEntidadPorId(ent);
                    if (ent != null) {
                        getCajaGenBeanDeposito().setNombreBanco(ent.getNombre());
                        getCajaGenBeanDeposito().setRucBanco(ent.getRuc());
                        System.out.println(getCajaGenBeanDeposito().getRucBanco());
                    }

                } else {
                    if (listaBancos.size() == 1) {
                        getCajaGenBeanDeposito().setRucBanco(listaBancos.get(0).getRuc());
                    }
                }
                obtenerCuentasPorId("colegio");

                CajaGenBean cajCongre = new CajaGenBean();
                cajCongre.setUniNeg(usuario.getPersonalBean().getUnidadNegocioBean());
                cajCongre.setAyudaBanco(Boolean.FALSE);
                listaBancosCongre = cajaGenService.obtenerBancosDeposito(cajCongre);
                if (cajaGenBeanDeposito.getRucBancoCongre() != null) {
                    EntidadBean ent = new EntidadBean();
                    ent.setUnidadNegocioBean(cajaGenBeanDeposito.getUniNeg());
                    ent.setRuc(cajaGenBeanDeposito.getRucBancoCongre());
                    ent = entidadService.obtenerEntidadPorId(ent);
                    if (ent != null) {
                        getCajaGenBeanDeposito().setNombreBancoCongre(ent.getNombre());
                        getCajaGenBeanDeposito().setRucBancoCongre(ent.getRuc());
                        System.out.println(getCajaGenBeanDeposito().getRucBancoCongre());
                    }
                } else {
                    if (listaBancosCongre.size() == 1) {
                        getCajaGenBeanDeposito().setRucBancoCongre(listaBancosCongre.get(0).getRuc());
                    }
                }
                obtenerCuentasPorId("congre");
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorIdArqueo(CajaGenBean cajaGenBean, String tipo) {
        try {
            CajaGenService cajaGenService = BeanFactory.getCajaGenService();
            cajaGenBean.setUniNeg(usuario.getPersonalBean().getUnidadNegocioBean());
            cajaGenBeanArqueo = cajaGenService.obtenerPorId(cajaGenBean);
            this.disabled = "true";
            if (tipo.equals("det")) {
//                imprimirPdf();
                imprimirPdfPorDetalle("usuario");
            } else {
                imprimirPdfPorCta(null, null, "usuario");
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorIdArqueDesglosado(CajaGenBean cajaGenBean, String tipo) {
        try {
            CajaGenService cajaGenService = BeanFactory.getCajaGenService();
            cajaGenBean.setUniNeg(usuario.getPersonalBean().getUnidadNegocioBean());
            cajaGenBeanArqueo = cajaGenService.obtenerPorId(cajaGenBean);
            this.disabled = "true";
            if (tipo.equals("det")) {
                imprimirPdf();
            } else {
                imprimirPdfDesglosado("usuario");
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void autenticarCajaApertura() {
        try {
            SimpleDateFormat formatoDiaCompleto = new SimpleDateFormat("dd-MM-yy");
            String dateCompleto = formatoDiaCompleto.format(new Date());
//            UsuarioBean usuario = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            CajeroCajaBean cajeroCajaBean = new CajeroCajaBean();
            CajaGenService cajaGenService = BeanFactory.getCajaGenService();
            CajeroService cajeroService = BeanFactory.getCajeroService();
            cajeroCajaBean.setUnidadNegocioBean(usuario.getPersonalBean().getUnidadNegocioBean());
            cajeroCajaBean.setUsuarioBean(usuario);
//-----------IP CLIENTE
//            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//            String remoteAddress = request.getRemoteAddr();
//            cajeroCajaBean.getCajaBean().setHostIp(remoteAddress);
//----------IP SERVIDOR
            InetAddress localHost = InetAddress.getLocalHost();
            cajeroCajaBean.getCajaBean().setHostIp(localHost.getHostAddress());
            cajeroCajaBean = cajeroService.autenticarUsuarioConCaja(cajeroCajaBean);
            if (cajeroCajaBean != null) {
                getCajaGenBean().setUniNeg(usuario.getPersonalBean().getUnidadNegocioBean());
                getCajaGenBean().setCajaBean(cajeroCajaBean.getCajaBean());
                Calendar.getInstance().get((Calendar.YEAR));
                SimpleDateFormat formato = new SimpleDateFormat("yyyy");
                String date = formato.format(new Date());
                getCajaGenBean().setAnio(Integer.parseInt(date));
                getCajaGenBean().setUsuarioBean(usuario);
                getCajaGenBean().setFecApertura(formatoDiaCompleto.parse(dateCompleto));
                this.disabled = "false";
                CajaGenBean cajaGeneral = new CajaGenBean();
                cajaGeneral = cajaGenService.verificarApertura(cajaGenBean);
                if (cajaGeneral != null) {
                    this.cajaApertura = cajaGeneral;
                    this.cajaGenBean = cajaGeneral;
                    getCajaGenBean().setFecApertura(new Date());
                    this.disabled = "true";
                } else {
                    CajaGenBean cajaGen = new CajaGenBean();
                    cajaGenBean.setFlgTipoCajaGen(Boolean.TRUE);
                    cajaGen = cajaGenService.obtenerUltimaCajaAbierta(cajaGenBean);
                    if (cajaGen == null) {
                        getCajaGenBean().setFecApertura(new Date());
                        this.disabled = "false";
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, MensajesBackEnd.getValueOfKey("mensajeNoCierreDiaAnt", null), MensajesBackEnd.getValueOfKey("msgsNoCaj", null)));
                        this.disabled = "true";
                    }
                }
            } else {
                getCajaGenBean().setCajaBean(null);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, MensajesBackEnd.getValueOfKey("mensajeNoCaja", null), MensajesBackEnd.getValueOfKey("msgsNoCaj", null)));
                this.disabled = "true";
            }
            //Ayuda para poner predefinido a supervisor a Lam
//            PersonalService personalService = BeanFactory.getPersonalService();
//            PersonalBean personal = new PersonalBean();
//            personal = personalService.buscarPorId(3);
//            cajaGenBean.setIdSupervisa(personal);
            verHistorialAperturaPorCajero();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerAutorizador() {
        try {
            JefeUniOrgService jefeUniOrgService = BeanFactory.getJefeUniOrgService();
            Integer id = null;

            JefeUniOrgBean jefe = new JefeUniOrgBean();
            jefe = jefeUniOrgService.obtenerIdUniOrgPorNombre(MaristaConstantes.UNI_ORG_ADM, usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (jefe != null) {
                getCajaGenBean().setIdSupervisa(jefe.getPersonalBean());
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarDatosApertura() {
        try {
            autenticarCajaApertura();
            obtenerAutorizador();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiar() {
        cajaGenBean = new CajaGenBean();
        cajaGenBean.setAperturaSol(new Double("0.00"));
        cajaGenBean.setAperturaDol(new Double("0.00"));
        cajaGenBean.setFecApertura(new Date());

    }

    public void limpiarCierre() {
        cajaGenBeanCierre = new CajaGenBean();
        cajaGenBeanCierre.setFecCierre(new Date());

    }

    public String insertarCajaApertura() {
        String pagina = null;
        pagina = new MaristaUtils().validaUsuarioSesion();
        try {
            if (pagina == null) {
//                UsuarioBean usuario = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                CajaGenService cajaGenService = BeanFactory.getCajaGenService();
                cajaGenBean.setUniNeg(usuario.getPersonalBean().getUnidadNegocioBean());
                cajaGenBean.setCreaPor(usuario.getUsuario());
                cajaGenBean.setFlgTipoCajaGen(Boolean.TRUE);
                cajaGenService.insertarCajaGen(cajaGenBean, tipoCambioBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                this.cajaApertura = cajaGenBean;
                limpiar();
                this.disabled = "true";
                verHistorialAperturaPorCajero();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String insertarCajaCierre() {
        String pagina = null;
        pagina = new MaristaUtils().validaUsuarioSesion();
        try {
            if (pagina == null) {
//                UsuarioBean usuario = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                CajaGenService cajaGenService = BeanFactory.getCajaGenService();
                cajaCierre.setUniNeg(usuario.getPersonalBean().getUnidadNegocioBean());
                cajaCierre.setFecCierre(new Date());
                cajaGenService.modificarCierre(cajaCierre);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                this.cajaGenBeanCierre = cajaCierre;
                limpiarCierre();
                this.disabledCierre = "true";
                verHistorialCierrePorCajero();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void obtenerDeposito() {
        try {
            CajeroService cajeroService = BeanFactory.getCajeroService();
            CajaGenService cajaGenService = BeanFactory.getCajaGenService();
//            CuentaBancoService cuentaBancoService = BeanFactory.getCuentaBancoService();
//            UsuarioBean usuario = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            CajeroCajaBean cajeroCajaBean = new CajeroCajaBean();
            cajeroCajaBean.setUnidadNegocioBean(usuario.getPersonalBean().getUnidadNegocioBean());
            cajeroCajaBean.setUsuarioBean(usuario);
//-----------IP CLIENTE
//            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//            String remoteAddress = request.getRemoteAddr();
//            cajeroCajaBean.getCajaBean().setHostIp(remoteAddress);
//----------IP SERVIDOR
            InetAddress localHost = InetAddress.getLocalHost();
            cajeroCajaBean.getCajaBean().setHostIp(localHost.getHostAddress());
            cajeroCajaBean = cajeroService.autenticarUsuarioConCaja(cajeroCajaBean);
            if (cajeroCajaBean != null) {
                cajaGenBeanDeposito.setUniNeg(usuario.getPersonalBean().getUnidadNegocioBean());
                cajaGenBeanDeposito.setCajaBean(cajeroCajaBean.getCajaBean());
                SimpleDateFormat formato = new SimpleDateFormat("yyyy");
                String date = formato.format(new Date());
                cajaGenBeanDeposito.setAnio(Integer.parseInt(date));
                cajaGenBeanDeposito.setUsuarioBean(usuario);
                cajaGenBeanDeposito.setFlgTipoCajaGen(Boolean.TRUE);
                cajaGenBeanDeposito = cajaGenService.obtenerCajaDepositoDiaAnterior(cajaGenBeanDeposito);
                if (cajaGenBeanDeposito == null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, MensajesBackEnd.getValueOfKey("mensajeNoDeposito", null), MensajesBackEnd.getValueOfKey("msgsCajDep", null)));
                    this.disabledDepSol = "true";
                    this.disabledDepDol = "true";
                    this.disabledDepCongreSol = "true";
                    this.disabledDepCongreDol = "true";

                    getCajaGenBeanDeposito().setFecDeposito(new Date());
                    getCajaGenBeanDeposito().setIngresoSol(new Double("0.00"));
                    getCajaGenBeanDeposito().setMontoDepositoSol(new Double("0.00"));

                    getCajaGenBeanDeposito().setIngresoDol(new Double("0.00"));
                    getCajaGenBeanDeposito().setMontoDepositoDol(new Double("0.00"));

                    getCajaGenBeanDeposito().setMontoDepositoCongreSol(new Double("0.00"));
                    getCajaGenBeanDeposito().setMontoDepositoCongreDol(new Double("0.00"));

                    getCajaGenBeanDeposito().setNumOperacionSol("0");
                    getCajaGenBeanDeposito().setNumOperacionDol("0");
                    getCajaGenBeanDeposito().setNumOperacionCongreSol("0");
                    getCajaGenBeanDeposito().setNumOperacionCongreDol("0");

                    getCajaGenBeanDeposito().setIngresoPos1(new Double("0.00"));
                    getCajaGenBeanDeposito().setIngresoPos2(new Double("0.00"));
                    getCajaGenBeanDeposito().setEgresoSol(new Double("0.00"));
                    getCajaGenBeanDeposito().setEgresoDol(new Double("0.00"));

                    //congregacion
                    getCajaGenBeanDeposito().setIngresoCongreEfectivoSol(new Double("0.00"));
                    getCajaGenBeanDeposito().setIngresoCongreEfectivoDol(new Double("0.00"));

                    obtenerPorBcosCtas();

                } else {
                    if (cajaGenBeanDeposito.getMontoDepositoSol() != null) {
                        this.disabledDepSol = "true";
                        this.disabledDepDol = "true";
                        this.disabledDepCongreSol = "true";
                        this.disabledDepCongreDol = "true";
                    } else {
                        Double saldoSol = new Double("0.00");
                        Double saldoDol = new Double("0.00");
                        Double ingresoSol = new Double("0.00");
                        Double ingresoDol = new Double("0.00");

                        //congregacion
                        Double ingresoCongreSol = new Double("0.00");
                        Double ingresoCongreDol = new Double("0.00");

                        cajaGenBeanDeposito.setFecDeposito(new Date());
                        if (cajaGenBeanDeposito.getAperturaSol() == null) {
                            cajaGenBeanDeposito.setAperturaSol(new Double("0.00"));
                        }
                        if (cajaGenBeanDeposito.getAperturaDol() == null) {
                            cajaGenBeanDeposito.setAperturaDol(new Double("0.00"));
                        }
                        if (cajaGenBeanDeposito.getIngresoSol() == null) {
                            cajaGenBeanDeposito.setIngresoSol(new Double("0.00"));
                        }
                        if (cajaGenBeanDeposito.getMontoDepositoSol() == null) {
                            cajaGenBeanDeposito.setMontoDepositoSol(new Double("0.00"));
                        }
                        if (cajaGenBeanDeposito.getIngresoDol() == null) {
                            cajaGenBeanDeposito.setIngresoDol(new Double("0.00"));
                        }
                        if (cajaGenBeanDeposito.getMontoDepositoDol() == null) {
                            cajaGenBeanDeposito.setMontoDepositoDol(new Double("0.00"));
                        }
                        if (cajaGenBeanDeposito.getIngresoPos1() == null) {
                            cajaGenBeanDeposito.setIngresoPos1(new Double("0.00"));
                        }
                        if (cajaGenBeanDeposito.getIngresoPos2() == null) {
                            cajaGenBeanDeposito.setIngresoPos2(new Double("0.00"));
                        }
                        if (cajaGenBeanDeposito.getEgresoSol() == null) {
                            cajaGenBeanDeposito.setEgresoSol(new Double("0.00"));
                        }
                        if (cajaGenBeanDeposito.getEgresoDol() == null) {
                            cajaGenBeanDeposito.setEgresoDol(new Double("0.00"));
                        }

                        //congregacion
                        if (cajaGenBeanDeposito.getIngresoCongreEfectivoSol() == null) {
                            cajaGenBeanDeposito.setIngresoCongreEfectivoSol(new Double("0.00"));
                        }
                        if (cajaGenBeanDeposito.getMontoDepositoCongreSol() == null) {
                            cajaGenBeanDeposito.setMontoDepositoCongreSol(new Double("0.00"));
                        }
                        if (cajaGenBeanDeposito.getIngresoCongreEfectivoDol() == null) {
                            cajaGenBeanDeposito.setIngresoCongreEfectivoDol(new Double("0.00"));
                        }
                        if (cajaGenBeanDeposito.getMontoDepositoCongreDol() == null) {
                            cajaGenBeanDeposito.setMontoDepositoCongreDol(new Double("0.00"));
                        }
                        if (cajaGenBeanDeposito.getNumOperacionCongreSol() == null) {
                            cajaGenBeanDeposito.setNumOperacionCongreSol("0");
                        }
                        if (cajaGenBeanDeposito.getNumOperacionSol() == null) {
                            cajaGenBeanDeposito.setNumOperacionSol("0");
                        }
                        if (cajaGenBeanDeposito.getNumOperacionCongreDol() == null) {
                            cajaGenBeanDeposito.setNumOperacionCongreDol("0");
                        }
                        if (cajaGenBeanDeposito.getNumOperacionDol() == null) {
                            cajaGenBeanDeposito.setNumOperacionDol("0");
                        }

//                        ingresoSol = cajaGenBeanDeposito.getIngresoSol() + cajaGenBeanDeposito.getAperturaSol();
//                        ingresoDol = cajaGenBeanDeposito.getIngresoDol() + cajaGenBeanDeposito.getAperturaDol();
                        ingresoSol = cajaGenBeanDeposito.getIngresoSol();
                        ingresoDol = cajaGenBeanDeposito.getIngresoDol();
                        cajaGenBeanDeposito.setIngresoSol(ingresoSol);
                        cajaGenBeanDeposito.setIngresoDol(ingresoDol);
                        saldoSol = cajaGenBeanDeposito.getIngresoSol() - cajaGenBeanDeposito.getEgresoSol();
                        saldoDol = cajaGenBeanDeposito.getIngresoDol() - cajaGenBeanDeposito.getEgresoDol();
                        cajaGenBeanDeposito.setSaldoSol(saldoSol);
                        cajaGenBeanDeposito.setSaldoDol(saldoDol);

//                        ingresoCongreSol=cajaGenBeanDeposito.getIngresoCongreEfectivoSol();
//                        ingresoCongreDol=cajaGenBeanDeposito.getIngresoCongreEfectivoDol();
                        this.disabledDepSol = "false";
                        this.disabledDepDol = "false";
                        this.disabledDepCongreSol = "false";
                        this.disabledDepCongreDol = "false";

//                        listaCuentasBancoSol = new ArrayList<>();
//                        listaCuentasBancoSol = cuentaBancoService.obtenerBancoPorTipMonedaYBanco(MaristaConstantes.TIP_MON, "Soles", usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), cajaGenBeanDeposito.getRucBanco());
//                        if (listaCuentasBancoSol.size() == 1) {
//                            getCajaGenBeanDeposito().setRucBanco(listaCuentasBancoSol.get(0).getEntidadBancoBean().getRuc());
//                            listaCuentasBancoSol = cuentaBancoService.obtenerBancoPorTipMonedaYBanco(MaristaConstantes.TIP_MON, "Soles", usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), cajaGenBeanDeposito.getRucBanco());
//                            if (listaCuentasBancoSol.size() == 1) {
//                                getCajaGenBeanDeposito().setNumCuentaSol(listaCuentasBancoSol.get(0).getNumCuenta());
//                            }
//                        }
                        obtenerPorBcosCtas();
                    }
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerDepositoEvento() {
        try {
            CajeroService cajeroService = BeanFactory.getCajeroService();
            CajaGenService cajaGenService = BeanFactory.getCajaGenService();
            CajeroCajaBean cajeroCajaBean = new CajeroCajaBean();
            cajeroCajaBean.setUnidadNegocioBean(usuario.getPersonalBean().getUnidadNegocioBean());
            cajeroCajaBean.setUsuarioBean(usuario);
//-----------IP CLIENTE
//            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//            String remoteAddress = request.getRemoteAddr();
//            cajeroCajaBean.getCajaBean().setHostIp(remoteAddress);
//----------IP SERVIDOR
            InetAddress localHost = InetAddress.getLocalHost();
            cajeroCajaBean.getCajaBean().setHostIp(localHost.getHostAddress());
            cajeroCajaBean = cajeroService.autenticarUsuarioConCaja(cajeroCajaBean);
            if (cajeroCajaBean != null) {
                cajaGenBeanDeposito.setUniNeg(usuario.getPersonalBean().getUnidadNegocioBean());
                cajaGenBeanDeposito.setCajaBean(cajeroCajaBean.getCajaBean());
                SimpleDateFormat formato = new SimpleDateFormat("yyyy");
                String date = formato.format(new Date());
                cajaGenBeanDeposito.setAnio(Integer.parseInt(date));
                cajaGenBeanDeposito.setUsuarioBean(usuario);
                cajaGenBeanDeposito.setFlgTipoCajaGen(Boolean.FALSE);
                cajaGenBeanDeposito = cajaGenService.obtenerCajaDepositoDiaAnterior(cajaGenBeanDeposito);
                if (cajaGenBeanDeposito == null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, MensajesBackEnd.getValueOfKey("mensajeNoDeposito", null), MensajesBackEnd.getValueOfKey("msgsCajDep", null)));
                    this.disabledDepSol = "true";
                    this.disabledDepDol = "true";
                    this.disabledDepCongreSol = "true";
                    this.disabledDepCongreDol = "true";

                    getCajaGenBeanDeposito().setFecDeposito(new Date());
                    getCajaGenBeanDeposito().setIngresoSol(new Double("0.00"));
                    getCajaGenBeanDeposito().setMontoDepositoSol(new Double("0.00"));

                    getCajaGenBeanDeposito().setIngresoDol(new Double("0.00"));
                    getCajaGenBeanDeposito().setMontoDepositoDol(new Double("0.00"));

                    getCajaGenBeanDeposito().setMontoDepositoCongreSol(new Double("0.00"));
                    getCajaGenBeanDeposito().setMontoDepositoCongreDol(new Double("0.00"));

                    getCajaGenBeanDeposito().setNumOperacionSol("0");
                    getCajaGenBeanDeposito().setNumOperacionDol("0");
                    getCajaGenBeanDeposito().setNumOperacionCongreSol("0");
                    getCajaGenBeanDeposito().setNumOperacionCongreDol("0");

                    getCajaGenBeanDeposito().setIngresoPos1(new Double("0.00"));
                    getCajaGenBeanDeposito().setIngresoPos2(new Double("0.00"));
                    getCajaGenBeanDeposito().setEgresoSol(new Double("0.00"));
                    getCajaGenBeanDeposito().setEgresoDol(new Double("0.00"));

                    //congregacion
                    getCajaGenBeanDeposito().setIngresoCongreEfectivoSol(new Double("0.00"));
                    getCajaGenBeanDeposito().setIngresoCongreEfectivoDol(new Double("0.00"));

                    obtenerPorBcosCtas();

                } else {
                    if (cajaGenBeanDeposito.getMontoDepositoSol() != null) {
                        this.disabledDepSol = "true";
                        this.disabledDepDol = "true";
                        this.disabledDepCongreSol = "true";
                        this.disabledDepCongreDol = "true";
                    } else {
                        Double saldoSol = new Double("0.00");
                        Double saldoDol = new Double("0.00");
                        Double ingresoSol = new Double("0.00");
                        Double ingresoDol = new Double("0.00");

                        //congregacion
                        Double ingresoCongreSol = new Double("0.00");
                        Double ingresoCongreDol = new Double("0.00");

                        cajaGenBeanDeposito.setFecDeposito(new Date());
                        if (cajaGenBeanDeposito.getAperturaSol() == null) {
                            cajaGenBeanDeposito.setAperturaSol(new Double("0.00"));
                        }
                        if (cajaGenBeanDeposito.getAperturaDol() == null) {
                            cajaGenBeanDeposito.setAperturaDol(new Double("0.00"));
                        }
                        if (cajaGenBeanDeposito.getIngresoSol() == null) {
                            cajaGenBeanDeposito.setIngresoSol(new Double("0.00"));
                        }
                        if (cajaGenBeanDeposito.getMontoDepositoSol() == null) {
                            cajaGenBeanDeposito.setMontoDepositoSol(new Double("0.00"));
                        }
                        if (cajaGenBeanDeposito.getIngresoDol() == null) {
                            cajaGenBeanDeposito.setIngresoDol(new Double("0.00"));
                        }
                        if (cajaGenBeanDeposito.getMontoDepositoDol() == null) {
                            cajaGenBeanDeposito.setMontoDepositoDol(new Double("0.00"));
                        }
                        if (cajaGenBeanDeposito.getIngresoPos1() == null) {
                            cajaGenBeanDeposito.setIngresoPos1(new Double("0.00"));
                        }
                        if (cajaGenBeanDeposito.getIngresoPos2() == null) {
                            cajaGenBeanDeposito.setIngresoPos2(new Double("0.00"));
                        }
                        if (cajaGenBeanDeposito.getEgresoSol() == null) {
                            cajaGenBeanDeposito.setEgresoSol(new Double("0.00"));
                        }
                        if (cajaGenBeanDeposito.getEgresoDol() == null) {
                            cajaGenBeanDeposito.setEgresoDol(new Double("0.00"));
                        }

                        //congregacion
                        if (cajaGenBeanDeposito.getIngresoCongreEfectivoSol() == null) {
                            cajaGenBeanDeposito.setIngresoCongreEfectivoSol(new Double("0.00"));
                        }
                        if (cajaGenBeanDeposito.getMontoDepositoCongreSol() == null) {
                            cajaGenBeanDeposito.setMontoDepositoCongreSol(new Double("0.00"));
                        }
                        if (cajaGenBeanDeposito.getIngresoCongreEfectivoDol() == null) {
                            cajaGenBeanDeposito.setIngresoCongreEfectivoDol(new Double("0.00"));
                        }
                        if (cajaGenBeanDeposito.getMontoDepositoCongreDol() == null) {
                            cajaGenBeanDeposito.setMontoDepositoCongreDol(new Double("0.00"));
                        }
                        if (cajaGenBeanDeposito.getNumOperacionCongreSol() == null) {
                            cajaGenBeanDeposito.setNumOperacionCongreSol("0");
                        }
                        if (cajaGenBeanDeposito.getNumOperacionSol() == null) {
                            cajaGenBeanDeposito.setNumOperacionSol("0");
                        }
                        if (cajaGenBeanDeposito.getNumOperacionCongreDol() == null) {
                            cajaGenBeanDeposito.setNumOperacionCongreDol("0");
                        }
                        if (cajaGenBeanDeposito.getNumOperacionDol() == null) {
                            cajaGenBeanDeposito.setNumOperacionDol("0");
                        }
                        ingresoSol = cajaGenBeanDeposito.getIngresoSol();
                        ingresoDol = cajaGenBeanDeposito.getIngresoDol();
                        cajaGenBeanDeposito.setIngresoSol(ingresoSol);
                        cajaGenBeanDeposito.setIngresoDol(ingresoDol);
                        saldoSol = cajaGenBeanDeposito.getIngresoSol() - cajaGenBeanDeposito.getEgresoSol();
                        saldoDol = cajaGenBeanDeposito.getIngresoDol() - cajaGenBeanDeposito.getEgresoDol();
                        cajaGenBeanDeposito.setSaldoSol(saldoSol);
                        cajaGenBeanDeposito.setSaldoDol(saldoDol);
                        this.disabledDepSol = "false";
                        this.disabledDepDol = "false";
                        this.disabledDepCongreSol = "false";
                        this.disabledDepCongreDol = "false";
                        obtenerPorBcosCtas();
                    }
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerDiferenciaSol() {
        Double diferenciaSol = new Double("0.00");
        diferenciaSol = cajaGenBeanDeposito.getMontoDepositoSol() - cajaGenBeanDeposito.getIngresoSol();
        diferenciaSol = (double) Math.round(diferenciaSol * 100) / 100;
        cajaGenBeanDeposito.setDiferenciaSol(diferenciaSol);
    }

    public void obtenerDiferenciaDol() {
        Double diferenciaDol = new Double("0.00");
        diferenciaDol = cajaGenBeanDeposito.getMontoDepositoDol() - cajaGenBeanDeposito.getIngresoDol();
        diferenciaDol = (double) Math.round(diferenciaDol * 100) / 100;
        cajaGenBeanDeposito.setDiferenciaDol(diferenciaDol);
    }

    public String insertarMontoDeposito() {
        String pagina = null;
        pagina = new MaristaUtils().validaUsuarioSesion();
        try {
            if (pagina == null) {
                CajaGenService cajaGenService = BeanFactory.getCajaGenService();
                cajaGenService.modificarDepColegio(cajaGenBeanDeposito);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                this.disabledDepSol = "true";
                verHistorialCierrePorCajero();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String modificarApertura() {
        String pagina = null;
        pagina = new MaristaUtils().validaUsuarioSesion();
        try {
            if (pagina == null) {
                CajaGenService cajaGenService = BeanFactory.getCajaGenService();

                cajaGenService.modificarApertura(cajaGenBeanDeposito);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                this.disabledDepSol = "true";
                verHistorialAperturaPorCajero();

            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String insertarMontoDepSol() {
        String pagina = null;
        pagina = new MaristaUtils().validaUsuarioSesion();
        try {
            if (pagina == null) {
                CajaGenService cajaGenService = BeanFactory.getCajaGenService();
                cajaGenService.modificarDepSoles(cajaGenBeanDeposito);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                this.disabledDepSol = "true";
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String insertarMontoDepDol() {
        String pagina = null;
        pagina = new MaristaUtils().validaUsuarioSesion();
        try {
            if (pagina == null) {
                CajaGenService cajaGenService = BeanFactory.getCajaGenService();
                cajaGenService.modificarDepDolares(cajaGenBeanDeposito);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                this.disabledDepDol = "true";
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    //CONGREGACION
    public String insertarMontoDepCongreSol() {
        String pagina = null;
        pagina = new MaristaUtils().validaUsuarioSesion();
        try {
            if (pagina == null) {
                CajaGenService cajaGenService = BeanFactory.getCajaGenService();
                cajaGenService.modificarDepCongreSoles(cajaGenBeanDeposito);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                this.disabledDepCongreSol = "true";
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String insertarMontoDepCongreDol() {
        String pagina = null;
        pagina = new MaristaUtils().validaUsuarioSesion();
        try {
            if (pagina == null) {
                CajaGenService cajaGenService = BeanFactory.getCajaGenService();
                cajaGenService.modificarDepCongreDolares(cajaGenBeanDeposito);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                this.disabledDepCongreDol = "true";
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void autenticarCajaArqueo() {
        try {
            cajaGenBeanArqueo = new CajaGenBean();
            SimpleDateFormat formatoDiaCompleto = new SimpleDateFormat("dd-MM-yy");
            String dateCompleto = formatoDiaCompleto.format(new Date());
//            UsuarioBean usuario = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            CajeroCajaBean cajeroCajaBean = new CajeroCajaBean();
            CajaGenService cajaGenService = BeanFactory.getCajaGenService();
            CajeroService cajeroService = BeanFactory.getCajeroService();
            cajeroCajaBean.setUnidadNegocioBean(usuario.getPersonalBean().getUnidadNegocioBean());
            cajeroCajaBean.setUsuarioBean(usuario);
//-----------IP CLIENTE
//            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//            String remoteAddress = request.getRemoteAddr();
//            cajeroCajaBean.getCajaBean().setHostIp(remoteAddress);
//----------IP SERVIDOR
            InetAddress localHost = InetAddress.getLocalHost();
            cajeroCajaBean.getCajaBean().setHostIp(localHost.getHostAddress());
            cajeroCajaBean = cajeroService.autenticarUsuarioConCaja(cajeroCajaBean);
            if (cajeroCajaBean != null) {
                getCajaGenBeanArqueo().setUniNeg(usuario.getPersonalBean().getUnidadNegocioBean());
                getCajaGenBeanArqueo().setCajaBean(cajeroCajaBean.getCajaBean());
                getCajaGenBeanArqueo().setFecApertura(new Date());
                Calendar.getInstance().get((Calendar.YEAR));
                SimpleDateFormat formato = new SimpleDateFormat("yyyy");
                String date = formato.format(new Date());
                getCajaGenBeanArqueo().setAnio(Integer.parseInt(date));
                getCajaGenBeanArqueo().setUsuarioBean(usuario);
                getCajaGenBeanArqueo().setFecApertura(formatoDiaCompleto.parse(dateCompleto));
                getCajaGenBeanArqueo().setFecCierre(formatoDiaCompleto.parse(dateCompleto));
                CajaGenBean cajaGeneral = new CajaGenBean();
                cajaGeneral = cajaGenService.verificarApertura(cajaGenBeanArqueo);
                Double saldoSol = new Double("0.00");
                Double saldoDol = new Double("0.00");
                if (cajaGeneral != null) {
                    this.cajaGenBeanArqueo = cajaGeneral;
                    CajaGenBean cajaGeneralCierre = new CajaGenBean();
                    if (cajaGenBeanArqueo.getIngresoSol() == null) {
                        cajaGenBeanArqueo.setIngresoSol(new Double("0.00"));
                    }
                    if (cajaGenBeanArqueo.getIngresoDol() == null) {
                        cajaGenBeanArqueo.setIngresoDol(new Double("0.00"));
                    }
                    if (cajaGenBeanArqueo.getEgresoSol() == null) {
                        cajaGenBeanArqueo.setEgresoSol(new Double("0.00"));
                    }
                    if (cajaGenBeanArqueo.getEgresoDol() == null) {
                        cajaGenBeanArqueo.setEgresoDol(new Double("0.00"));
                    }
                    if (cajaGenBeanArqueo.getIngresoCongreEfectivoSol() == null) {
                        cajaGenBeanArqueo.setIngresoCongreEfectivoSol(new Double("0.00"));
                    }
                    if (cajaGenBeanArqueo.getIngresoCongreEfectivoDol() == null) {
                        cajaGenBeanArqueo.setIngresoCongreEfectivoDol(new Double("0.00"));
                    }
                    saldoSol = cajaGenBeanArqueo.getIngresoSol() - cajaGenBeanArqueo.getEgresoSol();
                    saldoDol = cajaGenBeanArqueo.getIngresoDol() - cajaGenBeanArqueo.getEgresoDol();
                    cajaGenBeanArqueo.setSaldoSol(saldoSol);
                    cajaGenBeanArqueo.setSaldoDol(saldoDol);
                    cajaGenBeanArqueo.setFlgTipoCajaGen(Boolean.TRUE);
                    cajaGeneralCierre = cajaGenService.verificarCierre(cajaGenBeanArqueo);
                    if (cajaGeneralCierre != null) {
                        this.cajaGenBeanArqueo = cajaGeneralCierre;
                        if (cajaGenBeanArqueo.getIngresoSol() == null) {
                            cajaGenBeanArqueo.setIngresoSol(new Double("0.00"));
                        }
                        if (cajaGenBeanArqueo.getIngresoDol() == null) {
                            cajaGenBeanArqueo.setIngresoDol(new Double("0.00"));
                        }
                        if (cajaGenBeanArqueo.getEgresoSol() == null) {
                            cajaGenBeanArqueo.setEgresoSol(new Double("0.00"));
                        }
                        if (cajaGenBeanArqueo.getEgresoDol() == null) {
                            cajaGenBeanArqueo.setEgresoDol(new Double("0.00"));
                        }
                        if (cajaGenBeanArqueo.getIngresoCongreEfectivoSol() == null) {
                            cajaGenBeanArqueo.setIngresoCongreEfectivoSol(new Double("0.00"));
                        }
                        if (cajaGenBeanArqueo.getIngresoCongreEfectivoDol() == null) {
                            cajaGenBeanArqueo.setIngresoCongreEfectivoDol(new Double("0.00"));
                        }
                        saldoSol = cajaGenBeanArqueo.getIngresoSol() - cajaGenBeanArqueo.getEgresoSol();
                        saldoDol = cajaGenBeanArqueo.getIngresoDol() - cajaGenBeanArqueo.getEgresoDol();
                        cajaGenBeanArqueo.setSaldoSol(saldoSol);
                        cajaGenBeanArqueo.setSaldoDol(saldoDol);

                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, MensajesBackEnd.getValueOfKey("mensajeNoApertura", null), MensajesBackEnd.getValueOfKey("msgsNoCaj", null)));
                }

            } else {
                getCajaGenBeanArqueo().setCajaBean(null);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, MensajesBackEnd.getValueOfKey("mensajeNoCaja", null), MensajesBackEnd.getValueOfKey("msgsNoCaj", null)));
            }
            verHistoriaArqueoPorCajero();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void verHistoriaArqueoPorCajero() {
        try {
            CajaGenService cajaGenService = BeanFactory.getCajaGenService();
            listaArqueoCaja = cajaGenService.obtenerCierresCajaPorCajero(usuario.getUsuario(), usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), null, null);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void verHistoriaArqueosGeneral() {
        try {
            if (cajaGenBeanArqueo.getFechaInicio() != null) {
                Timestamp t = new Timestamp(cajaGenBeanArqueo.getFechaInicio().getTime());
                t.setHours(0);
                t.setMinutes(0);
                t.setSeconds(0);
                cajaGenBeanArqueo.setFechaInicio(t);

            }
            if (cajaGenBeanArqueo.getFechaFin() != null) {
                Timestamp u = new Timestamp(cajaGenBeanArqueo.getFechaFin().getTime());
                u.setHours(23);
                u.setMinutes(59);
                u.setSeconds(59);
                cajaGenBeanArqueo.setFechaFin(u);
            }
            CajaGenService cajaGenService = BeanFactory.getCajaGenService();
            listaArqueoCaja = cajaGenService.obtenerCierresCajaPorCajero(null, usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), cajaGenBeanArqueo.getFechaInicio(), cajaGenBeanArqueo.getFechaFin());
            if (listaArqueoCaja.isEmpty()) {
                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
            } else {
                for (CajaGenBean lista : listaArqueoCaja) {
                    lista.setVerArqueo(Boolean.TRUE);
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
//    public void buscarMetodosPDF() {
//        try {
//            
//            convertirStrFechasCierreCaja();
//            //otro metodo
//            verHistoriaArqueosGeneral();
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//    }

    public void convertirStrFechasCierreCaja() {
        try {
            if (cajaGenBeanArqueo.getFechaInicio() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String date = sdf.format(cajaGenBeanArqueo.getFechaInicio());
                fechaInicio = date;
            }
            if (cajaGenBeanArqueo.getFechaFin() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String date = sdf.format(cajaGenBeanArqueo.getFechaFin());
                fechaFin = date;
            }
            imprimirPdfCierresCaja(fechaInicio, fechaFin, usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg());

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarCajaGen() {
        try {
            cajaGenBeanArqueo = new CajaGenBean();
            listaArqueoCaja = new ArrayList<>();
            fechaActual = new GregorianCalendar();
            getCajaGenBean().setFechaInicio(fechaActual.getTime());
            getCajaGenBean().setFechaFin(fechaActual.getTime());

        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void validar() {
        try {
            if (cajaGenBeanArqueo.getFechaInicio() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String date = sdf.format(cajaGenBeanArqueo.getFechaInicio());
                fechaInicio = date;
            }
            if (cajaGenBeanArqueo.getFechaFin() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String date = sdf.format(cajaGenBeanArqueo.getFechaFin());
                fechaFin = date;
            }
            listaIdCajaGen = new ArrayList<>();
            listaIdCajas = new ArrayList<>();
            for (CajaGenBean lista : listaArqueoCaja) {
                if (lista.getVerArqueo() != null) {
                    if (lista.getVerArqueo().equals(Boolean.TRUE)) {
                        listaIdCajaGen.add(lista.getIdCajaGen());
                        listaIdCajas.add(lista.getCajaBean().getIdCaja());
                    }
                }
            }
            imprimirPdfPorCta(fechaInicio, fechaFin, "general");

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void validarDetalle(String flgIncMora) {
        try {
            if (cajaGenBeanArqueo.getFechaInicio() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String date = sdf.format(cajaGenBeanArqueo.getFechaInicio());
                fechaInicio = date;
            }
            if (cajaGenBeanArqueo.getFechaFin() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String date = sdf.format(cajaGenBeanArqueo.getFechaFin());
                fechaFin = date;
            }
            listaIdCajaGen = new ArrayList<>();
            listaIdCajas = new ArrayList<>();
            for (CajaGenBean lista : listaArqueoCaja) {
                if (lista.getVerArqueo() != null) {
                    if (lista.getVerArqueo().equals(Boolean.TRUE)) {
                        listaIdCajaGen.add(lista.getIdCajaGen());
                        listaIdCajas.add(lista.getCajaBean().getIdCaja());
                    }
                }
            }
            imprimirPdfPorDetalleGeneral(fechaInicio, fechaFin, flgIncMora);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void verHistorialAperturaPorCajero() {
        try {
            CajaGenService cajaGenService = BeanFactory.getCajaGenService();
            listaAperturaCaja = cajaGenService.obtenerAperturasCajaPorCajero(usuario.getUsuario(), usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 1);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void verHistorialCierrePorCajero() {
        try {
            CajaGenService cajaGenService = BeanFactory.getCajaGenService();
            listaCierreCaja = cajaGenService.obtenerCierresCajaPorCajero(usuario.getUsuario(), usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), null, null);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerFaltanteSol() {
        faltanteSol = cajaGenBeanArqueo.getSaldoSol() - enCajaSol;
        faltanteSol = (double) Math.round(faltanteSol * 100) / 100;
    }

    public void obtenerFaltanteDol() {
        faltanteDol = cajaGenBeanArqueo.getSaldoDol() - enCajaDol;
        faltanteDol = (double) Math.round(faltanteDol * 100) / 100;
    }

    public void obtenerCuentas(String tipo) {
        try {
            CuentaBancoService cuentaBancoService = BeanFactory.getCuentaBancoService();
            EntidadService entidadService = BeanFactory.getEntidadService();
            if (tipo.equals("colegio")) {
                listaCuentasBancoSol = new ArrayList<>();
                listaCuentasBancoSol = cuentaBancoService.obtenerBancoPorTipMonedaYBancoFlgCobranza(MaristaConstantes.TIP_MON, "Soles", usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), cajaGenBeanDeposito.getRucBanco(), Boolean.FALSE);

                listaCuentasBancoDol = new ArrayList<>();
                listaCuentasBancoDol = cuentaBancoService.obtenerBancoPorTipMonedaYBancoFlgCobranza(MaristaConstantes.TIP_MON, "Dolares", usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), cajaGenBeanDeposito.getRucBanco(), Boolean.FALSE);

                EntidadBean ent = new EntidadBean();
                ent.setUnidadNegocioBean(cajaGenBeanDeposito.getUniNeg());
                ent.setRuc(cajaGenBeanDeposito.getRucBanco());
                ent = entidadService.obtenerEntidadPorId(ent);
                cajaGenBeanDeposito.setNombreBanco(ent.getNombre());
            } else {
                listaCuentasBancoCongreSol = new ArrayList<>();
                listaCuentasBancoCongreSol = cuentaBancoService.obtenerBancoPorTipMonedaYBancoFlgCobranza(MaristaConstantes.TIP_MON, "Soles", usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), cajaGenBeanDeposito.getRucBancoCongre(), Boolean.TRUE);

                listaCuentasBancoCongreDol = new ArrayList<>();
                listaCuentasBancoCongreDol = cuentaBancoService.obtenerBancoPorTipMonedaYBancoFlgCobranza(MaristaConstantes.TIP_MON, "Dolares", usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), cajaGenBeanDeposito.getRucBancoCongre(), Boolean.TRUE);

                EntidadBean ent2 = new EntidadBean();
                ent2.setUnidadNegocioBean(cajaGenBeanDeposito.getUniNeg());
                ent2.setRuc(cajaGenBeanDeposito.getRucBancoCongre());
                ent2 = entidadService.obtenerEntidadPorId(ent2);
                cajaGenBeanDeposito.setNombreBancoCongre(ent2.getNombre());
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerCuentasPorId() {
        try {
            CuentaBancoService cuentaBancoService = BeanFactory.getCuentaBancoService();
            EntidadService entidadService = BeanFactory.getEntidadService();

            listaCuentasBancoSol = new ArrayList<>();
            listaCuentasBancoSol = cuentaBancoService.obtenerBancoPorTipMonedaYBanco(MaristaConstantes.TIP_MON, "Soles", usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), cajaGenBeanDeposito.getRucBanco());

            listaCuentasBancoDol = new ArrayList<>();
            listaCuentasBancoDol = cuentaBancoService.obtenerBancoPorTipMonedaYBanco(MaristaConstantes.TIP_MON, "Dolares", usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), cajaGenBeanDeposito.getRucBanco());

            EntidadBean ent = new EntidadBean();
            ent.setUnidadNegocioBean(cajaGenBeanDeposito.getUniNeg());
            ent.setRuc(cajaGenBeanDeposito.getRucBanco());
            ent = entidadService.obtenerEntidadPorId(ent);
            if (ent != null) {
                cajaGenBeanDeposito.setNombreBanco(ent.getNombre());
            }
            listaCuentasBancoCongreSol = new ArrayList<>();
            listaCuentasBancoCongreSol = cuentaBancoService.obtenerBancoPorTipMonedaYBanco(MaristaConstantes.TIP_MON, "Soles", usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), cajaGenBeanDeposito.getRucBancoCongre());

            listaCuentasBancoCongreDol = new ArrayList<>();
            listaCuentasBancoCongreDol = cuentaBancoService.obtenerBancoPorTipMonedaYBanco(MaristaConstantes.TIP_MON, "Dolares", usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), cajaGenBeanDeposito.getRucBancoCongre());

            EntidadBean ent2 = new EntidadBean();
            ent2.setUnidadNegocioBean(cajaGenBeanDeposito.getUniNeg());
            ent2.setRuc(cajaGenBeanDeposito.getRucBancoCongre());
            ent2 = entidadService.obtenerEntidadPorId(ent2);
            if (ent2 != null) {
                cajaGenBeanDeposito.setNombreBancoCongre(ent2.getNombre());
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerCuentasPorId(String nivel) {
        try {

            CuentaBancoService cuentaBancoService = BeanFactory.getCuentaBancoService();
            EntidadService entidadService = BeanFactory.getEntidadService();
            if (nivel.equals("colegio")) {
                if (cajaGenBeanDeposito.getRucBanco() != null) {
                    listaCuentasBancoSol = new ArrayList<>();
                    listaCuentasBancoSol = cuentaBancoService.obtenerBancoPorTipMonedaYBancoFlgCobranza(MaristaConstantes.TIP_MON, "Soles", usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), cajaGenBeanDeposito.getRucBanco(), Boolean.TRUE);

                    listaCuentasBancoDol = new ArrayList<>();
                    listaCuentasBancoDol = cuentaBancoService.obtenerBancoPorTipMonedaYBancoFlgCobranza(MaristaConstantes.TIP_MON, "Dolares", usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), cajaGenBeanDeposito.getRucBanco(), Boolean.TRUE);

                    if (cajaGenBeanDeposito.getRucBanco() != null && cajaGenBeanDeposito.getNombreBanco() == null) {
                        EntidadBean ent = new EntidadBean();
                        ent.setUnidadNegocioBean(cajaGenBeanDeposito.getUniNeg());
                        ent.setRuc(cajaGenBeanDeposito.getRucBanco());
                        ent = entidadService.obtenerEntidadPorId(ent);
                        if (ent != null) {
                            getCajaGenBeanDeposito().setRucBanco(ent.getRuc());
                            getCajaGenBeanDeposito().setNombreBanco(ent.getNombre());
                        }
                    }
                    if (cajaGenBeanDeposito.getNumCuentaSol() != null) {
                        getCajaGenBeanDeposito().setNumCuentaSol(cajaGenBeanDeposito.getNumCuentaSol());
                    } else {
                        if (listaCuentasBancoSol.size() == 1) {
                            getCajaGenBeanDeposito().setNumCuentaSol(listaCuentasBancoSol.get(0).getNumCuenta());
                        }
                    }
                    if (cajaGenBeanDeposito.getNumCuentaDol() != null) {
                        getCajaGenBeanDeposito().setNumCuentaDol(cajaGenBeanDeposito.getNumCuentaDol());
                    } else {
                        if (listaCuentasBancoDol.size() == 1) {
                            getCajaGenBeanDeposito().setNumCuentaDol(listaCuentasBancoDol.get(0).getNumCuenta());
                        }
                    }
                }
                System.out.println("2:" + getCajaGenBeanDeposito().getRucBanco());
            } else {
                if (cajaGenBeanDeposito.getRucBancoCongre() != null) {
                    listaCuentasBancoCongreSol = new ArrayList<>();
                    listaCuentasBancoCongreSol = cuentaBancoService.obtenerBancoPorTipMonedaYBancoFlgCobranza(MaristaConstantes.TIP_MON, "Soles", usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), cajaGenBeanDeposito.getRucBancoCongre(), Boolean.FALSE);

                    listaCuentasBancoCongreDol = new ArrayList<>();
                    listaCuentasBancoCongreDol = cuentaBancoService.obtenerBancoPorTipMonedaYBancoFlgCobranza(MaristaConstantes.TIP_MON, "Dolares", usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), cajaGenBeanDeposito.getRucBancoCongre(), Boolean.FALSE);
                    if (cajaGenBeanDeposito.getRucBancoCongre() != null && cajaGenBeanDeposito.getNombreBancoCongre() == null) {
                        EntidadBean ent2 = new EntidadBean();
                        ent2.setUnidadNegocioBean(cajaGenBeanDeposito.getUniNeg());
                        ent2.setRuc(cajaGenBeanDeposito.getRucBancoCongre());
                        ent2 = entidadService.obtenerEntidadPorId(ent2);
                        if (ent2 != null) {
                            getCajaGenBeanDeposito().setRucBancoCongre(ent2.getRuc());
                            getCajaGenBeanDeposito().setNombreBancoCongre(ent2.getNombre());
                        }
                    }
                }
                if (cajaGenBeanDeposito.getNumCuentaCongreSol() != null) {
                    getCajaGenBeanDeposito().setNumCuentaCongreSol(cajaGenBeanDeposito.getNumCuentaCongreSol());
                } else {
                    if (listaCuentasBancoCongreSol.size() == 1) {
                        getCajaGenBeanDeposito().setNumCuentaCongreSol(listaCuentasBancoCongreSol.get(0).getNumCuenta());
                    }
                }
                if (cajaGenBeanDeposito.getNumCuentaCongreDol() != null) {
                    getCajaGenBeanDeposito().setNumCuentaCongreDol(cajaGenBeanDeposito.getNumCuentaCongreDol());
                } else {
                    if (listaCuentasBancoCongreDol.size() == 1) {
                        getCajaGenBeanDeposito().setNumCuentaCongreDol(listaCuentasBancoCongreDol.get(0).getNumCuenta());
                    }
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerCuentas() {
        try {
            CuentaBancoService cuentaBancoService = BeanFactory.getCuentaBancoService();
            EntidadService entidadService = BeanFactory.getEntidadService();

            listaCuentasBancoSol = new ArrayList<>();
            listaCuentasBancoSol = cuentaBancoService.obtenerBancoPorTipMonedaYBancoFlgCobranza(MaristaConstantes.TIP_MON, "Soles", usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), cajaGenBeanDeposito.getRucBanco(), Boolean.TRUE);
            if (listaCuentasBancoSol.size() == 1) {
                getCajaGenBeanDeposito().setNumCuentaSol(listaCuentasBancoSol.get(0).getNumCuenta());
            }
            listaCuentasBancoDol = new ArrayList<>();
            listaCuentasBancoDol = cuentaBancoService.obtenerBancoPorTipMonedaYBancoFlgCobranza(MaristaConstantes.TIP_MON, "Dolares", usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), cajaGenBeanDeposito.getRucBanco(), Boolean.TRUE);
            if (listaCuentasBancoDol.size() == 1) {
                getCajaGenBeanDeposito().setNumCuentaDol(listaCuentasBancoDol.get(0).getNumCuenta());
            }
            EntidadBean ent = new EntidadBean();
            ent.setUnidadNegocioBean(cajaGenBeanDeposito.getUniNeg());
            ent.setRuc(cajaGenBeanDeposito.getRucBanco());
            ent = entidadService.obtenerEntidadPorId(ent);
            if (ent != null) {
                getCajaGenBeanDeposito().setNombreBanco(ent.getNombre());
            }
            listaCuentasBancoCongreSol = new ArrayList<>();
            listaCuentasBancoCongreSol = cuentaBancoService.obtenerBancoPorTipMonedaYBancoFlgCobranza(MaristaConstantes.TIP_MON, "Soles", usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), cajaGenBeanDeposito.getRucBancoCongre(), Boolean.FALSE);
            if (listaCuentasBancoCongreSol.size() == 1) {
                getCajaGenBeanDeposito().setNumCuentaCongreSol(listaCuentasBancoCongreSol.get(0).getNumCuenta());
            }
            listaCuentasBancoCongreDol = new ArrayList<>();
            listaCuentasBancoCongreDol = cuentaBancoService.obtenerBancoPorTipMonedaYBancoFlgCobranza(MaristaConstantes.TIP_MON, "Dolares", usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), cajaGenBeanDeposito.getRucBancoCongre(), Boolean.FALSE);
            if (listaCuentasBancoCongreDol.size() == 1) {
                getCajaGenBeanDeposito().setNumCuentaCongreDol(listaCuentasBancoCongreDol.get(0).getNumCuenta());
            }
            EntidadBean ent2 = new EntidadBean();
            ent2.setUnidadNegocioBean(cajaGenBeanDeposito.getUniNeg());
            ent2.setRuc(cajaGenBeanDeposito.getRucBancoCongre());
            ent2 = entidadService.obtenerEntidadPorId(ent2);
            if (ent2 != null) {
                getCajaGenBeanDeposito().setNombreBancoCongre(ent2.getNombre());
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void verificarCierreDiaAnterior(CajaGenBean cajaGenBean) {
        try {
            CajaGenService cajaGenService = BeanFactory.getCajaGenService();
            CajaGenBean cajaGen = new CajaGenBean();
            cajaGenBean.setFlgTipoCajaGen(Boolean.TRUE);
            cajaGen = cajaGenService.obtenerUltimaCajaAbierta(cajaGenBean);
            if (cajaGen != null) {
                cajaGenBeanCierreDiaAnte = cajaGen;
            } else {
                cajaGenBeanCierreDiaAnte = new CajaGenBean();
                cajaGenBeanCierreDiaAnte.setFecCierre(new Date());
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarCierreDiaAnterior() {
        String pagina = null;
        pagina = new MaristaUtils().validaUsuarioSesion();
        try {
            if (pagina == null) {
                CajaGenService cajaGenService = BeanFactory.getCajaGenService();
                cajaGenBeanCierreDiaAnte.setFecCierre(cajaGenBeanCierre.getFecCierre());
                cajaGenService.modificarCierre(cajaGenBeanCierreDiaAnte);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, MensajesBackEnd.getValueOfKey("mensajeNoApertura", null), MensajesBackEnd.getValueOfKey("msgsNoCaj", null)));
                this.disabledCierre = "true";
                this.disabledDeposito = "false";
                verHistorialCierrePorCajero();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

/////////////////////////////////////////////PDF//////////////////////////////////////////////////////////////////////////////////////
    public void imprimirPdf() {
        ServletOutputStream out = null;
        try {
            CajaGenService cajaGenService = BeanFactory.getCajaGenService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/RepDetDocIngreso.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            List<CajaGenRepBean> listaCajaGenRep = new ArrayList<>();
            CajaGenBean cajaGen = new CajaGenBean();
            cajaGen.setIdCajaGen(cajaGenBeanArqueo.getIdCajaGen());
            cajaGen.getCajaBean().setIdCaja(cajaGenBeanArqueo.getCajaBean().getIdCaja());
            cajaGen.setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean());

            listaCajaGenRep = cajaGenService.obtenerCajaGen(cajaGen);

            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCajaGenRep);
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

    public void imprimirPdfPorDetalle(String tipo) {
        ServletOutputStream out = null;
        try {
            CajaGenService cajaGenService = BeanFactory.getCajaGenService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/RepDetDocIngreso.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<CajaGenRepBean> listaCajaGenRep = new ArrayList<>();
            if (tipo.equals("usuario")) {
                listaIdCajaGen = new ArrayList<>();
                listaIdCajaGen.add(cajaGenBeanArqueo.getIdCajaGen());
                listaCajaGenRep = cajaGenService.obtenerCajaGenPorDetalleFor(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaIdCajaGen);
            } else {
                listaCajaGenRep = cajaGenService.obtenerCajaGenPorDetalleFor(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaIdCajaGen);
            }

            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCajaGenRep);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            response.setHeader("Content-Disposition", "inline; filename=Arq_Det_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".pdf");
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

    public void imprimirPdfPorDetalleGeneral(String fechaInicio, String fechaFin, String flgIncMora) {
        ServletOutputStream out = null;
        try {
            CajaGenService cajaGenService = BeanFactory.getCajaGenService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/RepDetDocIngresoGeneral.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<CajaGenRepBean> listaCajaGenRep = new ArrayList<>();
            if ("incMora".equals(flgIncMora)) {
                listaCajaGenRep = cajaGenService.obtenerCajaGeneralPorDetalleForIncMora(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaIdCajaGen);

            } else {
                listaCajaGenRep = cajaGenService.obtenerCajaGeneralPorDetalleFor(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaIdCajaGen);
            }
            String text = "";
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            if (fechaInicio == null) {
                fechaInicio = sdf.format(cajaGenBeanArqueo.getFechaInicio());

            }
            if (fechaFin == null) {
                fechaFin = sdf.format(cajaGenBeanArqueo.getFechaFin());
            }
            text = "DEL " + fechaInicio + "  AL  " + fechaFin;
            if (!listaCajaGenRep.isEmpty()) {
                for (CajaGenRepBean list : listaCajaGenRep) {
                    list.setTxtFiltro(text);
                }
            }

            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCajaGenRep);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            response.setHeader("Content-Disposition", "inline; filename=ArqGen_Det_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".pdf");
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

    public void imprimirPdfPorCta(String fechaInicio, String fechaFin, String tipo) {
        ServletOutputStream out = null;
        try {
            CajaGenService cajaGenService = BeanFactory.getCajaGenService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
//            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/RepDetDocIngresoCuentaGen.jasper");
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/RepDetDocIngresoPorCuentaForGeneral.jasper");
            if (tipo.equals("usuario")) {
                archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                        getRequest()).getServletContext().getRealPath("/reportes/RepDetDocIngresoPorCuentaFor.jasper");
            }
            System.out.println(archivoJasper);
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            List<CajaGeneralRepBean> listaCajaGenRep = new ArrayList<>();
//            List<CajaGenRepBean> listaCajaGenRep = new ArrayList<>();
////            CajaGenBean cajaGen = new CajaGenBean();
//            cajaGen.setIdCajaGen(cajaGenBeanArqueo.getIdCajaGen());
//            cajaGen.getCajaBean().setIdCaja(cajaGenBeanArqueo.getCajaBean().getIdCaja());
//            cajaGen.setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean());
            String text = "";
            text = "DEL " + fechaInicio + "AL " + fechaFin;
            if (tipo.equals("usuario")) {
                listaIdCajaGen = new ArrayList<>();
                listaIdCajas = new ArrayList<>();
                listaIdCajaGen.add(cajaGenBeanArqueo.getIdCajaGen());
                listaIdCajas.add(cajaGenBeanArqueo.getCajaBean().getIdCaja());
//                listaCajaGenRep = cajaGenService.obtenerCajaGenPorCta(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaIdCajaGen, listaIdCajas);
                listaCajaGenRep = cajaGenService.obtenerCajaGeneralPorCtaFor(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaIdCajaGen, 0);
            } else {
                listaCajaGenRep = cajaGenService.obtenerCajaGeneralPorCtaFor(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaIdCajaGen, 1);
            }

            if (!listaCajaGenRep.isEmpty() && fechaInicio != null && fechaFin != null) {
                for (CajaGeneralRepBean list : listaCajaGenRep) {
                    list.setTxtFiltro(text);
                }
            }

            if (!listaCajaGenRep.isEmpty()) {
                for (int i = 0; i < listaCajaGenRep.size(); i++) {
//                    List<CajaGeneralCtaRepBean> listaDetalle = new ArrayList<>();
//                    listaDetalle = cajaGenService.obtenerDetCajaGeneralPorCtaFor(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaIdCajaGen, listaCajaGenRep.get(i).getMora());
//                    listaCajaGenRep.get(i).setListaDetalle(listaDetalle);
                    List<CajaGeneralCtasRepBean> listaCuentas = new ArrayList<>();
                    listaCuentas = cajaGenService.obtenerCuentasCajaGeneralFor(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaIdCajaGen);
                    listaCajaGenRep.get(i).setListaCuentas(listaCuentas);
                    if (!listaCuentas.isEmpty()) {
                        for (int j = 0; j < listaCajaGenRep.get(0).getListaCuentas().getData().size(); j++) {
                            List<CajaGeneralCtaRepBean> listaDetalle = new ArrayList<>();
                            System.out.println("cta-." + listaCuentas.get(j).getCuenta());
                            listaDetalle = cajaGenService.obtenerDetallePorCtaFor(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaIdCajaGen, listaCuentas.get(j).getCuenta());
                            System.out.println("xxx1.- " + listaDetalle.get(0).getMontoPorCtaSoles());
                            System.out.println("xxx2.- " + listaDetalle.get(0).getMontoPorCtaDolares());
                            if (listaDetalle.get(0).getMontoPorCtaSoles() > 0 || listaDetalle.get(0).getMontoPorCtaDolares() > 0) {
                                listaCuentas.get(j).setListaDetalle(listaDetalle);
                                listaCajaGenRep.get(i).setListaCuentas(listaCuentas);
                            }
                        }
                    }
                }
            }

            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCajaGenRep);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            if (tipo.equals("usuario")) {
                response.setHeader("Content-Disposition", "inline; filename=Arq_Cta_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".pdf");
            } else {
                response.setHeader("Content-Disposition", "inline; filename=ArqGen_Cta_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".pdf");
            }
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

    public void imprimirPdfCierresCaja(String fechaInicio, String fechaFin, String uniNeg) {
        ServletOutputStream out = null;
        try {
            CajaGenService cajaGenService = BeanFactory.getCajaGenService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reportCierreCajas.jasper");

            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<CajaGenCierreRepBean> listaCajaGenCierreRep = new ArrayList<>();
            listaCajaGenCierreRep = cajaGenService.obtenerCierresCajaPorFechaTop1(uniNeg, fechaInicio, fechaFin);

            if (!listaCajaGenCierreRep.isEmpty()) {
                for (int i = 0; i < listaCajaGenCierreRep.size(); i++) {
                    listaCajaGenCierreRep.get(0).setFechaInicio(fechaInicio);
                    listaCajaGenCierreRep.get(0).setFechaFin(fechaFin);
                    List<CajaGenCierreRepBean> listaRepDetCajaGenCierre = new ArrayList<>();
                    listaRepDetCajaGenCierre = cajaGenService.obtenerCierresCajaPorFecha(uniNeg, fechaInicio, fechaFin);
                    listaCajaGenCierreRep.get(0).setListaDetalle(listaRepDetCajaGenCierre);
                    if (!listaRepDetCajaGenCierre.isEmpty()) {
                        for (int j = 0; j < listaCajaGenCierreRep.get(0).getListaDetalle().getData().size(); j++) {
                            List<DetCajaGenCierreRepBean> listaRepDetDetCajaGenCierreRep = new ArrayList<>();
                            listaRepDetDetCajaGenCierreRep = cajaGenService.obtenerCierresCajaPorFechaUsu(uniNeg, fechaInicio, fechaFin, listaRepDetCajaGenCierre.get(j).getUsuario(), listaRepDetCajaGenCierre.get(j).getIdCaja());
                            listaRepDetCajaGenCierre.get(j).setListaDetalleCajaGenCierre(listaRepDetDetCajaGenCierreRep);
                            listaCajaGenCierreRep.get(0).setListaDetalle(listaRepDetCajaGenCierre);
                        }
                    }
                }
            }
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCajaGenCierreRep);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            response.setHeader("Content-Disposition", "inline; filename=CierresCaja_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".pdf");
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

    public void imprimirPdfDesglosado(String tipo) {
        ServletOutputStream out = null;
        try {
            CajaGenService cajaGenService = BeanFactory.getCajaGenService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/RepDetDocIngresoCuentaGen.jasper");
            if (tipo.equals("usuario")) {
                archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                        getRequest()).getServletContext().getRealPath("/reportes/RepDetDocIngresoDesglosado.jasper");
            }
            System.out.println(archivoJasper);
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            List<CajaGenRepBean> listaCajaGenRep = new ArrayList<>();
            CajaGenBean cajaGen = new CajaGenBean();
            cajaGen.setIdCajaGen(cajaGenBeanArqueo.getIdCajaGen());
            cajaGen.getCajaBean().setIdCaja(cajaGenBeanArqueo.getCajaBean().getIdCaja());
            cajaGen.setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean());

            if (tipo.equals("usuario")) {
                listaIdCajaGen = new ArrayList<>();
                listaIdCajas = new ArrayList<>();
                listaIdCajaGen.add(cajaGenBeanArqueo.getIdCajaGen());
                listaIdCajas.add(cajaGenBeanArqueo.getCajaBean().getIdCaja());
                listaCajaGenRep = cajaGenService.obtenerCajaGenDesglosadoTop1(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaIdCajaGen, listaIdCajas);
            } else {
                listaCajaGenRep = cajaGenService.obtenerCajaGenDesglosadoTop1(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaIdCajaGen, listaIdCajas);
            }

            for (int i = 0; i < listaCajaGenRep.size(); i++) {
                List<DocIngresoRepBeanDesglosado> listaDetCajaGenRepDesglosado = new ArrayList<>();
                listaDetCajaGenRepDesglosado = cajaGenService.obtenerCajaGenDesglosado(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaIdCajaGen);
                listaCajaGenRep.get(0).setListaDesglosado(listaDetCajaGenRepDesglosado);
                System.out.println(listaDetCajaGenRepDesglosado.size());

                for (int j = 0; j < listaCajaGenRep.get(0).getListaDesglosado().getData().size(); j++) {
                    List<CajaGenRepBean> listaCajaGenRep2 = new ArrayList<>();
                    listaCajaGenRep2 = cajaGenService.obtenerDetalleCajaGenDesglosado(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaIdCajaGen, listaIdCajas, listaDetCajaGenRepDesglosado.get(j).getModo());
                    for (int k = 0; k < listaCajaGenRep2.size(); k++) {
                        listaCajaGenRep2.get(k).setSumTotSoles(listaDetCajaGenRepDesglosado.get(j).getSubTotalSol());
                        listaCajaGenRep2.get(k).setSumTotDolares(listaDetCajaGenRepDesglosado.get(j).getSubTotalDol());

                        listaDetCajaGenRepDesglosado.get(j).setListaDetDetDocIngresoRepBean(listaCajaGenRep2);
                        listaCajaGenRep.get(0).setListaDesglosado(listaDetCajaGenRepDesglosado);
                    }
                }

            }

            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCajaGenRep);
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

    /* CIERRE CAJA EVENTO */
    public void autenticarCajaCierreEvento() {
        try {
            cajaGenBeanDeposito = new CajaGenBean();
            SimpleDateFormat formatoDiaCompleto = new SimpleDateFormat("dd-MM-yy");
            String dateCompleto = formatoDiaCompleto.format(new Date());
//            UsuarioBean usuario = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
//            EntidadService entidadService= BeanFactory.getEntidadService();
            CajeroCajaBean cajeroCajaBean = new CajeroCajaBean();
            CajaGenService cajaGenService = BeanFactory.getCajaGenService();
            CajeroService cajeroService = BeanFactory.getCajeroService();
            cajeroCajaBean.setUnidadNegocioBean(usuario.getPersonalBean().getUnidadNegocioBean());
            cajeroCajaBean.setUsuarioBean(usuario);
///-----------IP CLIENTE
//            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//            String remoteAddress = request.getRemoteAddr();
//            cajeroCajaBean.getCajaBean().setHostIp(remoteAddress);
//----------IP SERVIDOR
            InetAddress localHost = InetAddress.getLocalHost();
            cajeroCajaBean.getCajaBean().setHostIp(localHost.getHostAddress());
            cajeroCajaBean = cajeroService.autenticarUsuarioConCaja(cajeroCajaBean);
            if (cajeroCajaBean != null) {
                getCajaGenBeanCierre().setUniNeg(usuario.getPersonalBean().getUnidadNegocioBean());
                getCajaGenBeanCierre().setCajaBean(cajeroCajaBean.getCajaBean());
                getCajaGenBeanCierre().setFecApertura(new Date());
                Calendar.getInstance().get((Calendar.YEAR));
                SimpleDateFormat formato = new SimpleDateFormat("yyyy");
                String date = formato.format(new Date());
                fechaActual = new GregorianCalendar();

                getCajaGenBeanCierre().setAnio(Integer.parseInt(date));
                getCajaGenBeanCierre().setUsuarioBean(usuario);
//                getCajaGenBeanCierre().setFecApertura(formatoDiaCompleto.parse(dateCompleto));
                getCajaGenBeanCierre().setFecApertura(fechaActual.getTime());
//                getCajaGenBeanCierre().setFecCierre(formatoDiaCompleto.parse(dateCompleto));
                getCajaGenBeanCierre().setFecCierre(fechaActual.getTime());
                getCajaGenBeanCierre().setFlgTipoCajaGen(Boolean.FALSE);
                this.disabledCierre = "false";
                CajaGenBean cajaGeneral = new CajaGenBean();
                cajaGeneral = cajaGenService.verificarAperturaCajaEvento(cajaGenBeanCierre);
                verificarCierreDiaAnteriorEvento(cajaGenBeanCierre);
                if (cajaGenBeanCierreDiaAnte.getFecCierre() == null) {
                    this.disabledCierre = "true";
                    this.disabledDeposito = "true";
//                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, MensajesBackEnd.getValueOfKey("mensajeNoCierreDiaAnt", null), MensajesBackEnd.getValueOfKey("msgsNoCaj", null)));
                    new MensajePrime().addInformativeMessagePer("mensajeNoCierreDiaAnt");
                } else {
                    if (cajaGeneral != null) {
                        this.cajaCierre = cajaGeneral;
                        this.cajaGenBeanDeposito = cajaGeneral;
                        CajaGenBean cajaGeneralCierre = new CajaGenBean();
                        cajaGeneralCierre = cajaGenService.verificarCierreEvento(cajaGenBeanCierre);
                        if (cajaGeneralCierre != null) {
                            this.cajaCierre = cajaGeneralCierre;
                            this.cajaGenBeanCierre = cajaGeneralCierre;
                            this.cajaGenBeanDeposito = cajaGeneralCierre;
                            this.disabledCierre = "true";
                        } else {
                            getCajaGenBeanCierre().setFecCierre(new Date());
                            this.disabledCierre = "false";
                        }
                    } else {
//                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, MensajesBackEnd.getValueOfKey("mensajeNoApertura", null), MensajesBackEnd.getValueOfKey("msgsNoCaj", null)));
                        new MensajePrime().addInformativeMessagePer("mensajeNoApertura");

                        this.disabledCierre = "true";
                        this.disabledDeposito = "false";
                    }
                }
            } else {
                getCajaGenBeanCierre().setFecCierre(new Date());
//                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, MensajesBackEnd.getValueOfKey("mensajeNoCaja", null), MensajesBackEnd.getValueOfKey("msgsNoCaj", null)));
                new MensajePrime().addInformativeMessagePer("mensajeNoCaja");
                this.disabledCierre = "true";
                this.disabledDeposito = "true";
            }

            verHistorialCierrePorCajeroEvento();

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void verHistorialCierrePorCajeroEvento() {
        try {
            CajaGenService cajaGenService = BeanFactory.getCajaGenService();
            listaCierreCaja = cajaGenService.obtenerCierresCajaPorCajeroEvento(usuario.getUsuario(), usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), null, null);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarCierreDiaAnteriorEvento() {
        String pagina = null;
        pagina = new MaristaUtils().validaUsuarioSesion();
        try {
            if (pagina == null) {
                CajaGenService cajaGenService = BeanFactory.getCajaGenService();
                cajaGenBeanCierreDiaAnte.setFecCierre(cajaGenBeanCierre.getFecCierre());
                cajaGenService.modificarCierreEvento(cajaGenBeanCierreDiaAnte);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, MensajesBackEnd.getValueOfKey("mensajeNoApertura", null), MensajesBackEnd.getValueOfKey("msgsNoCaj", null)));
                this.disabledCierre = "true";
                this.disabledDeposito = "false";
                verHistorialCierrePorCajeroEvento();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String insertarMontoDepositoEvento() {
        String pagina = null;
        pagina = new MaristaUtils().validaUsuarioSesion();
        try {
            if (pagina == null) {
                CajaGenService cajaGenService = BeanFactory.getCajaGenService();
                cajaGenService.modificarDepColegio(cajaGenBeanDeposito);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                this.disabledDepSol = "true";
                verHistorialCierrePorCajeroEvento();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    /* CAJA ARQUEO EVENTO */
    public void autenticarCajaArqueoEvento() {
        try {
            cajaGenBeanArqueo = new CajaGenBean();
            SimpleDateFormat formatoDiaCompleto = new SimpleDateFormat("dd-MM-yy");
            String dateCompleto = formatoDiaCompleto.format(new Date());
//            UsuarioBean usuario = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            CajeroCajaBean cajeroCajaBean = new CajeroCajaBean();
            CajaGenService cajaGenService = BeanFactory.getCajaGenService();
            CajeroService cajeroService = BeanFactory.getCajeroService();
            cajeroCajaBean.setUnidadNegocioBean(usuario.getPersonalBean().getUnidadNegocioBean());
            cajeroCajaBean.setUsuarioBean(usuario);
//-----------IP CLIENTE
//            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//            String remoteAddress = request.getRemoteAddr();
//            cajeroCajaBean.getCajaBean().setHostIp(remoteAddress);
//----------IP SERVIDOR
            InetAddress localHost = InetAddress.getLocalHost();
            cajeroCajaBean.getCajaBean().setHostIp(localHost.getHostAddress());
            cajeroCajaBean = cajeroService.autenticarUsuarioConCaja(cajeroCajaBean);
            if (cajeroCajaBean != null) {
                getCajaGenBeanArqueo().setUniNeg(usuario.getPersonalBean().getUnidadNegocioBean());
                getCajaGenBeanArqueo().setCajaBean(cajeroCajaBean.getCajaBean());
                getCajaGenBeanArqueo().setFecApertura(new Date());
                Calendar.getInstance().get((Calendar.YEAR));
                SimpleDateFormat formato = new SimpleDateFormat("yyyy");
                String date = formato.format(new Date());
                getCajaGenBeanArqueo().setAnio(Integer.parseInt(date));
                getCajaGenBeanArqueo().setUsuarioBean(usuario);
                getCajaGenBeanArqueo().setFecApertura(formatoDiaCompleto.parse(dateCompleto));
                getCajaGenBeanArqueo().setFecCierre(formatoDiaCompleto.parse(dateCompleto));
                getCajaGenBeanArqueo().setFlgTipoCajaGen(Boolean.FALSE);
                CajaGenBean cajaGeneral = new CajaGenBean();
                cajaGeneral = cajaGenService.verificarAperturaCajaEvento(cajaGenBeanArqueo);
                Double saldoSol = new Double("0.00");
                Double saldoDol = new Double("0.00");
                if (cajaGeneral != null) {
                    this.cajaGenBeanArqueo = cajaGeneral;
                    CajaGenBean cajaGeneralCierre = new CajaGenBean();
                    if (cajaGenBeanArqueo.getIngresoSol() == null) {
                        cajaGenBeanArqueo.setIngresoSol(new Double("0.00"));
                    }
                    if (cajaGenBeanArqueo.getIngresoDol() == null) {
                        cajaGenBeanArqueo.setIngresoDol(new Double("0.00"));
                    }
                    if (cajaGenBeanArqueo.getEgresoSol() == null) {
                        cajaGenBeanArqueo.setEgresoSol(new Double("0.00"));
                    }
                    if (cajaGenBeanArqueo.getEgresoDol() == null) {
                        cajaGenBeanArqueo.setEgresoDol(new Double("0.00"));
                    }
                    if (cajaGenBeanArqueo.getIngresoCongreEfectivoSol() == null) {
                        cajaGenBeanArqueo.setIngresoCongreEfectivoSol(new Double("0.00"));
                    }
                    if (cajaGenBeanArqueo.getIngresoCongreEfectivoDol() == null) {
                        cajaGenBeanArqueo.setIngresoCongreEfectivoDol(new Double("0.00"));
                    }
                    saldoSol = cajaGenBeanArqueo.getIngresoSol() - cajaGenBeanArqueo.getEgresoSol();
                    saldoDol = cajaGenBeanArqueo.getIngresoDol() - cajaGenBeanArqueo.getEgresoDol();
                    cajaGenBeanArqueo.setSaldoSol(saldoSol);
                    cajaGenBeanArqueo.setSaldoDol(saldoDol);
                    cajaGeneralCierre = cajaGenService.verificarCierreEvento(cajaGenBeanArqueo);
                    if (cajaGeneralCierre != null) {
                        this.cajaGenBeanArqueo = cajaGeneralCierre;
                        if (cajaGenBeanArqueo.getIngresoSol() == null) {
                            cajaGenBeanArqueo.setIngresoSol(new Double("0.00"));
                        }
                        if (cajaGenBeanArqueo.getIngresoDol() == null) {
                            cajaGenBeanArqueo.setIngresoDol(new Double("0.00"));
                        }
                        if (cajaGenBeanArqueo.getEgresoSol() == null) {
                            cajaGenBeanArqueo.setEgresoSol(new Double("0.00"));
                        }
                        if (cajaGenBeanArqueo.getEgresoDol() == null) {
                            cajaGenBeanArqueo.setEgresoDol(new Double("0.00"));
                        }
                        if (cajaGenBeanArqueo.getIngresoCongreEfectivoSol() == null) {
                            cajaGenBeanArqueo.setIngresoCongreEfectivoSol(new Double("0.00"));
                        }
                        if (cajaGenBeanArqueo.getIngresoCongreEfectivoDol() == null) {
                            cajaGenBeanArqueo.setIngresoCongreEfectivoDol(new Double("0.00"));
                        }
                        saldoSol = cajaGenBeanArqueo.getIngresoSol() - cajaGenBeanArqueo.getEgresoSol();
                        saldoDol = cajaGenBeanArqueo.getIngresoDol() - cajaGenBeanArqueo.getEgresoDol();
                        cajaGenBeanArqueo.setSaldoSol(saldoSol);
                        cajaGenBeanArqueo.setSaldoDol(saldoDol);

                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, MensajesBackEnd.getValueOfKey("mensajeNoApertura", null), MensajesBackEnd.getValueOfKey("msgsNoCaj", null)));
                }

            } else {
                getCajaGenBeanArqueo().setCajaBean(null);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, MensajesBackEnd.getValueOfKey("mensajeNoCaja", null), MensajesBackEnd.getValueOfKey("msgsNoCaj", null)));
            }
            verHistoriaArqueoPorCajeroEvento();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void verHistoriaArqueoPorCajeroEvento() {
        try {
            CajaGenService cajaGenService = BeanFactory.getCajaGenService();
            listaArqueoCaja = cajaGenService.obtenerCierresCajaPorCajeroEvento(usuario.getUsuario(), usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), null, null);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorIdArqueoEvento(CajaGenBean cajaGenBean, String tipo) {
        try {
            CajaGenService cajaGenService = BeanFactory.getCajaGenService();
            cajaGenBean.setUniNeg(usuario.getPersonalBean().getUnidadNegocioBean());
            cajaGenBeanArqueo = cajaGenService.obtenerPorId(cajaGenBean);
            this.disabled = "true";
            imprimirPdfPorDetalleEvento("usuario");
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void imprimirPdfPorDetalleEvento(String tipo) {
        ServletOutputStream out = null;
        try {
            CajaGenService cajaGenService = BeanFactory.getCajaGenService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/RepDetDocEvento.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<CajaGenRepBean> listaCajaGenRep = new ArrayList<>();
            if (tipo.equals("usuario")) {
                listaIdCajaGen = new ArrayList<>();
                listaIdCajaGen.add(cajaGenBeanArqueo.getIdCajaGen());
                listaCajaGenRep = cajaGenService.obtenerCajaGenPorDetalleForEvento(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaIdCajaGen);
            } else {
                listaCajaGenRep = cajaGenService.obtenerCajaGenPorDetalleForEvento(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaIdCajaGen);
            }

            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCajaGenRep);
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

    public void cargarDatosAperturaEvento() {
        try {
            autenticarCajaAperturaEvento();
            obtenerAutorizador();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarCajaAperturaEvento() {
        String pagina = null;
        pagina = new MaristaUtils().validaUsuarioSesion();
        try {
            if (pagina == null) {
                CajaGenService cajaGenService = BeanFactory.getCajaGenService();
                cajaGenBean.setUniNeg(usuario.getPersonalBean().getUnidadNegocioBean());
                cajaGenBean.setCreaPor(usuario.getUsuario());
                cajaGenBean.setFlgTipoCajaGen(Boolean.FALSE);
                cajaGenService.insertarCajaGen(cajaGenBean, tipoCambioBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                this.cajaApertura = cajaGenBean;
                limpiar();
                this.disabled = "true";
                verHistorialAperturaPorCajeroEvento();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void verHistorialAperturaPorCajeroEvento() {
        try {
            CajaGenService cajaGenService = BeanFactory.getCajaGenService();
            listaAperturaCaja = cajaGenService.obtenerAperturasCajaPorCajero(usuario.getUsuario(), usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 0);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void autenticarCajaAperturaEvento() {
        try {
            SimpleDateFormat formatoDiaCompleto = new SimpleDateFormat("dd-MM-yy");
            String dateCompleto = formatoDiaCompleto.format(new Date());
            CajeroCajaBean cajeroCajaBean = new CajeroCajaBean();
            CajaGenService cajaGenService = BeanFactory.getCajaGenService();
            CajeroService cajeroService = BeanFactory.getCajeroService();
            cajeroCajaBean.setUnidadNegocioBean(usuario.getPersonalBean().getUnidadNegocioBean());
            cajeroCajaBean.setUsuarioBean(usuario);
//-----------IP CLIENTE
//            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//            String remoteAddress = request.getRemoteAddr();
//            cajeroCajaBean.getCajaBean().setHostIp(remoteAddress);
//----------IP SERVIDOR
            InetAddress localHost = InetAddress.getLocalHost();
            cajeroCajaBean.getCajaBean().setHostIp(localHost.getHostAddress());
            cajeroCajaBean = cajeroService.autenticarUsuarioConCaja(cajeroCajaBean);
            if (cajeroCajaBean != null) {
                getCajaGenBean().setUniNeg(usuario.getPersonalBean().getUnidadNegocioBean());
                getCajaGenBean().setCajaBean(cajeroCajaBean.getCajaBean());
                Calendar.getInstance().get((Calendar.YEAR));
                SimpleDateFormat formato = new SimpleDateFormat("yyyy");
                String date = formato.format(new Date());
                getCajaGenBean().setAnio(Integer.parseInt(date));
                getCajaGenBean().setUsuarioBean(usuario);
                getCajaGenBean().setFecApertura(formatoDiaCompleto.parse(dateCompleto));
                getCajaGenBean().setFlgTipoCajaGen(Boolean.FALSE);
                this.disabled = "false";
                CajaGenBean cajaGeneral = new CajaGenBean();
                cajaGeneral = cajaGenService.verificarAperturaCajaEvento(cajaGenBean);
                if (cajaGeneral != null) {
                    this.cajaApertura = cajaGeneral;
                    this.cajaGenBean = cajaGeneral;
                    getCajaGenBean().setFecApertura(new Date());
                    this.disabled = "true";
                } else {
                    CajaGenBean cajaGen = new CajaGenBean();
                    cajaGenBean.setFlgTipoCajaGen(Boolean.FALSE);
                    cajaGen = cajaGenService.obtenerUltimaCajaAbierta(cajaGenBean);
                    if (cajaGen == null) {
                        getCajaGenBean().setFecApertura(new Date());
                        this.disabled = "false";
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, MensajesBackEnd.getValueOfKey("mensajeNoCierreDiaAnt", null), MensajesBackEnd.getValueOfKey("msgsNoCaj", null)));
                        this.disabled = "true";
                    }
                }
            } else {
                getCajaGenBean().setCajaBean(null);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, MensajesBackEnd.getValueOfKey("mensajeNoCaja", null), MensajesBackEnd.getValueOfKey("msgsNoCaj", null)));
                this.disabled = "true";
            }
            //Ayuda para poner predefinido a supervisor a Lam
//            PersonalService personalService = BeanFactory.getPersonalService();
//            PersonalBean personal = new PersonalBean();
//            personal = personalService.buscarPorId(3);
//            cajaGenBean.setIdSupervisa(personal);
            verHistorialAperturaPorCajeroEvento();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void verificarCierreDiaAnteriorEvento(CajaGenBean cajaGenBean) {
        try {
            CajaGenService cajaGenService = BeanFactory.getCajaGenService();
            CajaGenBean cajaGen = new CajaGenBean();
            cajaGenBean.setFlgTipoCajaGen(Boolean.FALSE);
            cajaGen = cajaGenService.obtenerUltimaCajaAbierta(cajaGenBean);
            if (cajaGen != null) {
                cajaGenBeanCierreDiaAnte = cajaGen;
            } else {
                cajaGenBeanCierreDiaAnte = new CajaGenBean();
                cajaGenBeanCierreDiaAnte.setFecCierre(new Date());
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public DetDocIngresoBean getDetDocIngresoBean() {
        if (detDocIngresoBean == null) {
            detDocIngresoBean = new DetDocIngresoBean();
        }
        return detDocIngresoBean;
    }

    public void setDetDocIngresoBean(DetDocIngresoBean detDocIngresoBean) {
        this.detDocIngresoBean = detDocIngresoBean;
    }

    public List<DetDocIngresoBean> getListaDetDocIngreso() {
        if (listaDetDocIngreso == null) {
            listaDetDocIngreso = new ArrayList<>();
        }
        return listaDetDocIngreso;
    }

    public void setListaDetDocIngreso(List<DetDocIngresoBean> listaDetDocIngreso) {
        this.listaDetDocIngreso = listaDetDocIngreso;
    }

    public String getDisabledDepCongreSol() {
        return disabledDepCongreSol;
    }

    public void setDisabledDepCongreSol(String disabledDepCongreSol) {
        this.disabledDepCongreSol = disabledDepCongreSol;
    }

    public String getDisabledDepCongreDol() {
        return disabledDepCongreDol;
    }

    public void setDisabledDepCongreDol(String disabledDepCongreDol) {
        this.disabledDepCongreDol = disabledDepCongreDol;
    }

    public List<CuentaBancoBean> getListaCuentasBancoCongreSol() {
        if (listaCuentasBancoCongreSol == null) {
            listaCuentasBancoCongreSol = new ArrayList<>();
        }
        return listaCuentasBancoCongreSol;
    }

    public void setListaCuentasBancoCongreSol(List<CuentaBancoBean> listaCuentasBancoCongreSol) {
        this.listaCuentasBancoCongreSol = listaCuentasBancoCongreSol;
    }

    public List<CuentaBancoBean> getListaCuentasBancoCongreDol() {
        if (listaCuentasBancoCongreDol == null) {
            listaCuentasBancoCongreDol = new ArrayList<>();
        }
        return listaCuentasBancoCongreDol;
    }

    public void setListaCuentasBancoCongreDol(List<CuentaBancoBean> listaCuentasBancoCongreDol) {
        this.listaCuentasBancoCongreDol = listaCuentasBancoCongreDol;
    }

    public List<EntidadBean> getListaBancosCongre() {
        if (listaBancosCongre == null) {
            listaBancosCongre = new ArrayList<>();
        }
        return listaBancosCongre;
    }

    public void setListaBancosCongre(List<EntidadBean> listaBancosCongre) {
        this.listaBancosCongre = listaBancosCongre;
    }

    public List<CajaGenBean> getListaAperturaCaja() {
        if (listaAperturaCaja == null) {
            listaAperturaCaja = new ArrayList<>();
        }
        return listaAperturaCaja;
    }

    public void setListaAperturaCaja(List<CajaGenBean> listaAperturaCaja) {
        this.listaAperturaCaja = listaAperturaCaja;
    }

    public List<CajaGenBean> getListaCierreCaja() {
        if (listaCierreCaja == null) {
            listaCierreCaja = new ArrayList<>();
        }
        return listaCierreCaja;
    }

    public void setListaCierreCaja(List<CajaGenBean> listaCierreCaja) {
        this.listaCierreCaja = listaCierreCaja;
    }

    public List<CajaGenBean> getListaArqueoCaja() {
        if (listaArqueoCaja == null) {
            listaArqueoCaja = new ArrayList<>();
        }
        return listaArqueoCaja;
    }

    public void setListaArqueoCaja(List<CajaGenBean> listaArqueoCaja) {
        this.listaArqueoCaja = listaArqueoCaja;
    }

    public List<Integer> getListaIdCajaGen() {
        return listaIdCajaGen;
    }

    public void setListaIdCajaGen(List<Integer> listaIdCajaGen) {
        if (listaIdCajaGen == null) {
            listaIdCajaGen = new ArrayList<>();
        }
        this.listaIdCajaGen = listaIdCajaGen;
    }

    public List<Integer> getListaIdCajas() {
        return listaIdCajas;
    }

    public void setListaIdCajas(List<Integer> listaIdCajas) {
        if (listaIdCajaGen == null) {
            listaIdCajaGen = new ArrayList<>();
        }
        this.listaIdCajas = listaIdCajas;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public TipoCambioBean getTipoCambioBean() {
        if (tipoCambioBean == null) {
            tipoCambioBean = new TipoCambioBean();
        }
        return tipoCambioBean;
    }

    public void setTipoCambioBean(TipoCambioBean tipoCambioBean) {
        this.tipoCambioBean = tipoCambioBean;
    }

    public List<CodigoBean> getListaTipoCajaGen() {
        if (listaTipoCajaGen == null) {
            listaTipoCajaGen = new ArrayList<>();
        }
        return listaTipoCajaGen;
    }

    public void setListaTipoCajaGen(List<CodigoBean> listaTipoCajaGen) {
        this.listaTipoCajaGen = listaTipoCajaGen;
    }

}
