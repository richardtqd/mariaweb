package pe.marista.sigma.managedBean;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
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
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CajaCuotaIngresoBean;
import pe.marista.sigma.bean.CentroResponsabilidadBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.ConceptoUniNegBean;
import pe.marista.sigma.bean.CuentaBancoBean;
import pe.marista.sigma.bean.CuotaIngresoBean;
import pe.marista.sigma.bean.EstudianteBean;
import pe.marista.sigma.bean.ImpresoraBean;
import pe.marista.sigma.bean.JefeUniOrgBean;
import pe.marista.sigma.bean.PersonaBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.CuotaIngresoRepBean;
import pe.marista.sigma.bean.reporte.DetDocIngresoRepBean;
import pe.marista.sigma.bean.reporte.DocIngresoRepBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CentroResponsabilidadService;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.ConceptoUniNegService;
import pe.marista.sigma.service.CuentaBancoService;
import pe.marista.sigma.service.CuotaIngresoService;
import pe.marista.sigma.service.EstudianteService;
import pe.marista.sigma.service.ImpresoraService;
import pe.marista.sigma.service.JefeUniOrgService;
import pe.marista.sigma.service.PersonaService;
import pe.marista.sigma.service.PersonalService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.util.MensajesBackEnd;

public class CuotaIngresoMB extends BaseMB implements Serializable {

    private CajaCuotaIngresoBean cajaCuotaIngresoBean;
    private UsuarioBean usuario;
    private Calendar fechaActual;
    private List<CajaCuotaIngresoBean> listaCajaCuotaIngreso;
    private List<CuentaBancoBean> listaCuentaBancoFiltroBean;
    private Integer anio;
    private String disabled;
    private Boolean flgAbierto = false;
    private List<PersonaBean> listaPersonaBean;
    private PersonaBean personaBean;
    private Integer tipIndividuo = 1;
    private PersonaBean personaFiltroBean;
//    private Boolean flgSoloEst = true;
    private Boolean flgGenCod = Boolean.TRUE;
    private List<ConceptoUniNegBean> listaConceptoUniNegBean;
    private CuotaIngresoBean cuotaIngresoBean;
    private ImpresoraBean impresoraBean;
    private List<CodigoBean> listaTipoDocumento;
    private List<ImpresoraBean> listaImpresora;
    private String numActual;
    private List<CodigoBean> listaMoneda;
    private List<CodigoBean> listaModoPago;
    private Boolean serGenCollapsed;
    private List<CuotaIngresoBean> listaCuotaIngresoBean;
    private List<CentroResponsabilidadBean> listaCentroResponsabilidadBean;
    private Integer cr;
    private Double montoTotalDouble;
    private String montoTotal;
    private List<CodigoBean> listaTipoEstadpDocIng;

    //Detalle
    private List<CuotaIngresoBean> listaDetalleFiltroCuotaIngreso;
    private CuotaIngresoBean cuotaIngresoFiltroBean;
    private Double totalSol = (0.0);
    private Double totSoles = (0.0);

    @PostConstruct
    public void CuotaIngresoMB() {
        try {
            CuentaBancoService cuentaBancoService = BeanFactory.getCuentaBancoService();
            CuotaIngresoService cuotaIngresoService = BeanFactory.getCuotaIngresoService();
            usuario = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            getCajaCuotaIngresoBean();
            fechaActual = new GregorianCalendar();
            Calendar miCalendario = Calendar.getInstance();
            anio = miCalendario.get(Calendar.YEAR);
            cajaCuotaIngresoBean.setFechaApertura(fechaActual.getTime());
            listaCuentaBancoFiltroBean = cuentaBancoService.obtenerCuentaPorCongregacion(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            cajaCuotaIngresoBean.getUnidadNegocioBean().setUniNeg(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaCajaCuotaIngreso = cuotaIngresoService.obtenerListaCuotaIngreso(cajaCuotaIngresoBean);
            obtenerFlg();
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoUniNegCuotaIng(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            this.serGenCollapsed = false;
            cuotaIngresoFiltroBean = new CuotaIngresoBean();
            cuotaIngresoFiltroBean.setFechaInicio(fechaActual.getTime());
            cuotaIngresoFiltroBean.setFechaFin(fechaActual.getTime());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerFiltroCuotaIngreso() {
        try {
            int estado = 0;
            totSoles = (0.0);
            totalSol = (0.0);
            CuotaIngresoService cuotaIngresoService = BeanFactory.getCuotaIngresoService();
            if (cuotaIngresoFiltroBean.getFechaInicio() != null) {
                Timestamp t = new Timestamp(cuotaIngresoFiltroBean.getFechaInicio().getTime());
                t.setHours(0);
                t.setMinutes(0);
                t.setSeconds(0);
                cuotaIngresoFiltroBean.setFechaInicio(t);
                estado = 1;
            }
            if (cuotaIngresoFiltroBean.getFechaFin() != null) {
                Timestamp u = new Timestamp(cuotaIngresoFiltroBean.getFechaFin().getTime());
                u.setHours(23);
                u.setMinutes(59);
                u.setSeconds(59);
                cuotaIngresoFiltroBean.setFechaFin(u);
                estado = 1;
            }

            if (cuotaIngresoFiltroBean.getSerie() != null && !cuotaIngresoFiltroBean.getSerie().equals("")) {
                cuotaIngresoFiltroBean.setSerie(cuotaIngresoFiltroBean.getSerie());
                estado = 1;
            }
            if (cuotaIngresoFiltroBean.getNroDoc() != null && !cuotaIngresoFiltroBean.getNroDoc().equals("")) {
                cuotaIngresoFiltroBean.setNroDoc(cuotaIngresoFiltroBean.getNroDoc());
                estado = 1;
            }
            if (cuotaIngresoFiltroBean.getDiscente() != null && !cuotaIngresoFiltroBean.getDiscente().equals("")) {
                cuotaIngresoFiltroBean.setDiscente(cuotaIngresoFiltroBean.getDiscente());
                estado = 1;
            } else if (estado == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaDetalleFiltroCuotaIngreso = new ArrayList<>();
            }
            if (estado == 1) {
                listaDetalleFiltroCuotaIngreso = new ArrayList<>();
                cuotaIngresoFiltroBean.getUnidadNegocioBean().setUniNeg(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                listaDetalleFiltroCuotaIngreso = cuotaIngresoService.obtenerFiltroDetalleMovimientosCuoIng(cuotaIngresoFiltroBean);
                System.out.println("size" + ":" + listaDetalleFiltroCuotaIngreso.size());
                if (listaDetalleFiltroCuotaIngreso.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta"); 
                } else {
                    for (CuotaIngresoBean doc : listaDetalleFiltroCuotaIngreso) {
                        totSoles = totSoles + (doc.getMontoEfectivoSol()); 
                    }
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarDocFiltroCuotaIng() {
        try {
            listaDetalleFiltroCuotaIngreso = new ArrayList<>();
            cuotaIngresoFiltroBean = new CuotaIngresoBean();
            fechaActual = new GregorianCalendar();
            getCuotaIngresoFiltroBean().setFechaInicio(fechaActual.getTime());
            getCuotaIngresoFiltroBean().setFechaFin(fechaActual.getTime());
            getCuotaIngresoFiltroBean().setUnidadNegocioBean(usuario.getPersonalBean().getUnidadNegocioBean());
            totSoles = (0.0);
            totalSol = (0.0);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void buscarPersona() {
        try {
            listaPersonaBean = new ArrayList<>();
            PersonaService personaService = BeanFactory.getPersonaService();
            personaFiltroBean.setUnidadNegocioBean(usuario.getPersonalBean().getUnidadNegocioBean());
//            System.out.println("0.-" + personaFiltroBean.getNombreFiltro());
//            System.out.println("0.-" + personaFiltroBean.getIdPersona());
            if (personaFiltroBean.getIdPersona() != null && !personaFiltroBean.getIdPersona().trim().equals("")) {
                personaFiltroBean.setIdPersona(personaFiltroBean.getIdPersona());
            } else {
                personaFiltroBean.setIdPersona(null);
            }
            if (personaFiltroBean.getNombreFiltro() != null && !personaFiltroBean.getNombreFiltro().trim().equals("")) {
                personaFiltroBean.setNombreFiltro(personaFiltroBean.getNombreFiltro());
            } else {
                personaFiltroBean.setNombreFiltro(null);
            }
//            System.out.println("1.-" + personaFiltroBean.getNombreFiltro());
//            System.out.println("1.-" + personaFuiltroBean.getIdPersona());
//            Integer flg = 1;
//            System.out.println(flgSoloEst);
//            if (flgSoloEst.equals(Boolean.TRUE)) {
//                flg = 1;
//            } else {
//                flg = 0;
//            }
//            getPersonaFiltroBean().setFiltro(flg);
            listaPersonaBean = personaService.SP_obtenerPersonaPorFiltro(personaFiltroBean);
//            System.out.println("2.-" + personaFiltroBean.getNombreFiltro());
//            System.out.println("2.-" + personaFiltroBean.getIdPersona());
            if (personaFiltroBean.getIdPersona() == null && personaFiltroBean.getNombreFiltro() == null) {
                listaPersonaBean = personaService.obtenerTop10Persona(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            }

//            listaConceptoUniNegBean = new ArrayList<>();
            personaBean = new PersonaBean();
            personaBean.setColl(false);
            if (listaPersonaBean.isEmpty()) {
                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
//                personaBean.setColl(true);
                listaPersonaBean = new ArrayList<>();
                getPersonaBean().setColl(Boolean.TRUE);
                getPersonaBean();
                setFlgGenCod(Boolean.TRUE);
                generarCodigoPersona();

            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void aaaaa() {
        try {
            System.out.println("aaaaaaaa -->" + this.flgGenCod);
            System.out.println("name -->" + getPersonaBean().getIdPersona() + ", " + getPersonaBean().getApepat() + " " + getPersonaBean().getApemat() + " " + getPersonaBean().getNombre());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void generarCodigoPersona() {
        try {
            System.out.println("flg ini " + this.flgGenCod);
            if (this.flgGenCod.equals(Boolean.TRUE)) {
                this.flgGenCod = Boolean.TRUE;
//                System.out.println("no tiene dni");
                getPersonaBean().setIdPersona(MaristaConstantes.SIN_NRODOC_DOCINGRESO);
                getPersonaBean().setNroDoc(MaristaConstantes.SIN_NRODOC_DOCINGRESO);
                System.out.println("id...1 " + getPersonaBean().getIdPersona());
            } else {
                this.flgGenCod = Boolean.FALSE;
                if (getPersonaBean().getIdPersona().equals(null) || getPersonaBean().getIdPersona().equals(MaristaConstantes.SIN_NRODOC_DOCINGRESO)) {
//                    System.out.println("noooooo tiene dni" + getPersonaBean().getIdPersona());
                    getPersonaBean().setIdPersona("");
                    getPersonaBean().setNroDoc("");
                } else {
                    getPersonaBean().setIdPersona(getPersonaBean().getIdPersona().replace(" ", ""));
//                    System.out.println("siii tiene dni" + getPersonaBean().getIdPersona());
                }
            }
            System.out.println("flg fin " + this.flgGenCod);
            System.out.println("id... " + getPersonaBean().getIdPersona());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String obtenerFlg() {
        String pagina = null;
        pagina = new MaristaUtils().validaUsuarioSesion();
        try {
            CuotaIngresoService cuotaIngresoService = BeanFactory.getCuotaIngresoService();
            Integer idCaja = cuotaIngresoService.obtenerMaxCaja(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            CajaCuotaIngresoBean caja = new CajaCuotaIngresoBean();
            caja.setIdCajaCuotaIngreso(idCaja);
            caja.getUnidadNegocioBean().setUniNeg(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            CajaCuotaIngresoBean caja2 = new CajaCuotaIngresoBean();
            caja2 = cuotaIngresoService.obtenerCajaAbierta(caja);
            if (caja2 != null) {
                if (caja2.getFechaCierre() == null) {
                    this.flgAbierto = true;
                } else {
                    this.flgAbierto = false;
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String obtenerCuotaIngreso() {
        String pagina = null;
        pagina = new MaristaUtils().validaUsuarioSesion();
        try {
            CodigoService codigoService = BeanFactory.getCodigoService();
            ImpresoraService impresoraService = BeanFactory.getImpresoraService();
            buscarPersonal();
            listaMoneda = new ArrayList<>();
            listaMoneda = codigoService.obtenerPorTipoSoles(new TipoCodigoBean(MaristaConstantes.TIP_MON));
            listaModoPago = new ArrayList<>();
            listaModoPago = codigoService.obtenerCodigoDocIngresoBanco();
            listaTipoDocumento = new ArrayList<>();
            listaTipoDocumento = codigoService.obtenerPorTipoRecibo(new TipoCodigoBean(MaristaConstantes.TIP_DOCUMENTO));
            listaImpresora = impresoraService.obtenerCuotaIngre();
            for (ImpresoraBean imp : listaImpresora) {
                impresoraBean = new ImpresoraBean();
                impresoraBean.setActual(imp.getActual());
                impresoraBean.setSerie(imp.getSerie());
                impresoraBean.setImpresora(imp.getImpresora());
                System.out.println("impreso: " + impresoraBean.getSerie());
                System.out.println("impreso2: " + impresoraBean.getActual());
                System.out.println("impreso3: " + impresoraBean.getImpresora());
            }
            cuotaIngresoBean = new CuotaIngresoBean();
            cuotaIngresoBean.getTipoModoPago().setIdCodigo(listaModoPago.get(0).getIdCodigo());
            cuotaIngresoBean.getTipoMonedaBean().setIdCodigo(listaMoneda.get(0).getIdCodigo());
            cuotaIngresoBean.getTipoMonedaBean().setCodigo(listaMoneda.get(0).getCodigo());
            obtenerTipDoc();
            numActual = String.format("%07d", impresoraBean.getActual());
            generarCodigoPersona();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void verSexo() {
        try {
            System.out.println("sex " + getPersonaBean().getSexo());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerTipDoc() {
        try {
            CodigoService codigoService = BeanFactory.getCodigoService();
            listaTipoDocumento = new ArrayList<>();
            listaTipoDocumento = codigoService.obtenerPorTipoRecibo(new TipoCodigoBean(MaristaConstantes.TIP_DOCUMENTO));
            CodigoBean codigoTipoDoc = new CodigoBean();
            for (CodigoBean listaTipo : listaTipoDocumento) {
                System.out.println("entro");
                System.out.println("aaa " + listaTipo.getCodigo());
                if (listaTipo.getCodigo().equals(MaristaConstantes.COD_DOC_RECIBO)) {
                    codigoTipoDoc = codigoService.obtenerPorCodigo(new CodigoBean(0, MaristaConstantes.COD_DOC_RECIBO, new TipoCodigoBean(MaristaConstantes.TIP_DOCUMENTO)));
                    break;
                } else {
                    if (listaTipo.getCodigo().equals(MaristaConstantes.COD_DOC_BOLETA)) {
                        codigoTipoDoc = codigoService.obtenerPorCodigo(new CodigoBean(0, MaristaConstantes.COD_DOC_BOLETA, new TipoCodigoBean(MaristaConstantes.TIP_DOCUMENTO)));
                        break;
                    } else {
                        if (listaTipo.getCodigo().equals(MaristaConstantes.COD_DOC_FACTURA)) {
                            codigoTipoDoc = codigoService.obtenerPorCodigo(new CodigoBean(0, MaristaConstantes.COD_DOC_FACTURA, new TipoCodigoBean(MaristaConstantes.TIP_DOCUMENTO)));
                            break;
                        }
                        if (listaTipo.getCodigo().equals(MaristaConstantes.COD_DOC_COMPROBANTE)) {
                            codigoTipoDoc = codigoService.obtenerPorCodigo(new CodigoBean(0, MaristaConstantes.COD_DOC_COMPROBANTE, new TipoCodigoBean(MaristaConstantes.TIP_DOCUMENTO)));
                            break;
                        }
                    }
                }
            }

            if (codigoTipoDoc.getIdCodigo() != null) {
                impresoraBean.setIdTipoDoc((codigoTipoDoc));
            } else {
                if (listaImpresora.size() >= 1) {
                    if (!listaTipoDocumento.isEmpty()) {
                        impresoraBean.setIdTipoDoc((listaTipoDocumento.get(0)));
                    }
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarCajaApertura() {
        String pagina = null;
        pagina = new MaristaUtils().validaUsuarioSesion();
        try {
            if (pagina == null) {
//                UsuarioBean usuario = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                CuotaIngresoService cuotaIngresoService = BeanFactory.getCuotaIngresoService();
                cajaCuotaIngresoBean.getUnidadNegocioBean().setUniNeg(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                cajaCuotaIngresoBean.setCreaPor(usuario.getUsuario());
                cajaCuotaIngresoBean.setUsuario(usuario.getUsuario());
                cajaCuotaIngresoBean.setAnio(anio);
                cajaCuotaIngresoBean.setIngresoEfectivoSol(0.00);
                System.out.println("1 " + cajaCuotaIngresoBean.getNumeroCuentaBean().getEntidadBancoBean().getRuc());
                System.out.println("1 " + listaCuentaBancoFiltroBean.get(0).getEntidadBancoBean().getRuc());
                cajaCuotaIngresoBean.getRucBancoCongregacionBean().setRuc(listaCuentaBancoFiltroBean.get(0).getEntidadBancoBean().getRuc());
                cuotaIngresoService.insertarCajaCuotaIngreso(cajaCuotaIngresoBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                listaCajaCuotaIngreso = cuotaIngresoService.obtenerListaCuotaIngreso(cajaCuotaIngresoBean);
                obtenerFlg();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String cierreCaja() {
        String pagina = null;
        pagina = new MaristaUtils().validaUsuarioSesion();
        try {
            if (pagina == null) {
                CuotaIngresoService cuotaIngresoService = BeanFactory.getCuotaIngresoService();
                cajaCuotaIngresoBean.setFechaCierre(cajaCuotaIngresoBean.getFechaApertura());
                cajaCuotaIngresoBean.getUnidadNegocioBean().setUniNeg(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                cajaCuotaIngresoBean.setModiPor(usuario.getUsuario());
                Integer idCaja = cuotaIngresoService.obtenerMaxCaja(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                cajaCuotaIngresoBean.setIdCajaCuotaIngreso(idCaja);
                cuotaIngresoService.modificarCierre(cajaCuotaIngresoBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                listaCajaCuotaIngreso = cuotaIngresoService.obtenerListaCuotaIngreso(cajaCuotaIngresoBean);
                obtenerFlg();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String cambioAnulado(Integer idCuota, Integer idCaja, Double monto) {
        String pagina = null;
        pagina = new MaristaUtils().validaUsuarioSesion();
        try {
            CuotaIngresoService cuotaIngresoService = BeanFactory.getCuotaIngresoService();
            CuotaIngresoBean cuo = new CuotaIngresoBean();
            cuo.getTipoStatusDocIngBean().setIdCodigo(MaristaConstantes.COD_ANULADO);
            cuo.getUnidadNegocioBean().setUniNeg(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            cuo.setFlgAnulado(true);
            cuo.setModiPor(usuario.getUsuario());
            cuo.setIdCuotaIngreso(idCuota);
            cuotaIngresoService.cambioAnulado(cuo);
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            //modificar precipo por anulacion 
            CajaCuotaIngresoBean caja = new CajaCuotaIngresoBean();
            caja.getUnidadNegocioBean().setUniNeg(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            caja.setIdCajaCuotaIngreso(idCaja);
            caja = cuotaIngresoService.obtenerCajaAbierta(caja);
            caja.setModiPor(usuario.getUsuario());
            caja.setIngresoEfectivoSol(caja.getIngresoEfectivoSol() - monto);
            cuotaIngresoService.modificarMontoPorCaja(caja);

            listaCajaCuotaIngreso = cuotaIngresoService.obtenerListaCuotaIngreso(cajaCuotaIngresoBean);
            listaCuotaIngresoBean = cuotaIngresoService.obtenerIngresosEnCaja(cuo);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void limpiar() {
        cajaCuotaIngresoBean = new CajaCuotaIngresoBean();
    }

    public void buscarPersonal() {
        try {
            listaPersonaBean = new ArrayList<>();
            PersonaService personaService = BeanFactory.getPersonaService();
            listaPersonaBean = personaService.obtenerTop10Persona(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            personaBean = new PersonaBean();
            personaBean.setColl(false);
            if (listaPersonaBean.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, MensajesBackEnd.getValueOfKey("mensajeAlert", null), MensajesBackEnd.getValueOfKey("mensajeConsPer", null)));
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void rowSelect(SelectEvent event) {
        try {

            SimpleDateFormat formato = new SimpleDateFormat("yyyy");
            String date = formato.format(new Date());
            anio = Integer.parseInt(date);

            String rutaFoto = "";
//            FamiliarService familiarService = BeanFactory.getFamiliarService(); 
            PersonaService personaService = BeanFactory.getPersonaService();
            personaBean = (PersonaBean) event.getObject();
//            System.out.println(">>>>" + personaBean.getEstadoPersona());
//            if (personaBean.getEstado().equals("Bloqueado")) {
//                new MensajePrime().addInformativeMessagePer("msjAlumnoBloq");
//            }

            rutaFoto = personaService.obtenerFoto(getPersonaBean().getIdPersona(), usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            System.out.println("f/" + rutaFoto);
            personaBean.setFoto(rutaFoto);
//            personaBean = personaService.obtenerPersPorId(personaBean);
            personaBean.setColl(true);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerAutorizador() {
        try {
            JefeUniOrgService jefeUniOrgService = BeanFactory.getJefeUniOrgService();
            Integer id = null;

            JefeUniOrgBean jefe = new JefeUniOrgBean();
            jefe = jefeUniOrgService.obtenerIdUniOrgPorNombre(MaristaConstantes.UNI_ORG_ADM, usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (jefe != null) {
                getCajaCuotaIngresoBean().setSupervizaBean(jefe.getPersonalBean());
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void quitarConcepto(CuotaIngresoBean detalle) {
        try {
            listaCuotaIngresoBean.remove(detalle);
            if (listaCuotaIngresoBean != null) {
                if (listaCuotaIngresoBean.isEmpty()) {
                    listaCentroResponsabilidadBean = new ArrayList<>();
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void agregarConcepto(Integer idConcepto) {
        try {
            if (listaImpresora.size() >= 1) {
                getImpresoraBean().setImpresora(listaImpresora.get(0).getImpresora());
                obtenerTipDoc();
            }
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            ConceptoUniNegBean con = new ConceptoUniNegBean();
            con.getConceptoBean().setIdConcepto(idConcepto);
            con.getUnidadNegocioBean().setUniNeg(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            con = conceptoUniNegService.obtenerConceptoPorId(con);
            if (con.getConceptoBean().getNombre().toUpperCase().equals("DEVOLUCION ADELANTO A RENDIR M.N.")
                    || con.getConceptoBean().getNombre().toUpperCase().equals("DEVOLUCION ADELANTO A RENDIR M.E.")) {
                con.getConceptoBean().setNombre(MaristaConstantes.CON_DEV_ARENDIR_MAYUS);
            }
            if (con.getImporte().doubleValue() == 0 && !con.getConceptoBean().getNombre().toUpperCase().equals(MaristaConstantes.CON_DEV_ARENDIR_MAYUS)) {
                new MensajePrime().addInformativeMessagePer("msjSerGenImpCero");
                System.out.println("cero -" + con.getConceptoBean().getNombre());
            } else {
                System.out.println("!cero");
                Integer id = 0;
                id = conceptoUniNegService.obtenerTipoPorProgramacion(idConcepto, usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg());

                SimpleDateFormat fecha = new SimpleDateFormat("dd-MM-yyyy:HH:mm:SS");
                String dia = fecha.format(new Date());
                ConceptoUniNegBean concepto = new ConceptoUniNegBean();
                CuotaIngresoService caja = BeanFactory.getCuotaIngresoService();
                Integer idcaja = caja.obtenerMaxCaja(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                CuotaIngresoBean detalle = new CuotaIngresoBean();
                concepto.getConceptoBean().setIdConcepto(idConcepto);
                concepto.getUnidadNegocioBean().setUniNeg(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                concepto = conceptoUniNegService.obtenerConceptoPorIdConCuotaIng(concepto);
                detalle.setUnidadNegocioBean(usuario.getPersonalBean().getUnidadNegocioBean());
                detalle.getCajaCuotaIngresoBean().setIdCajaCuotaIngreso(idcaja);
                detalle.setConceptoBean(concepto.getConceptoBean());
                System.out.println("importe: " + concepto.getImporte());
                detalle.setMontoEfectivoSol(concepto.getImporte().doubleValue());
                detalle.setCuentaD(concepto.getConceptoBean().getPlanContableCuentaDBean());
                detalle.setCuentaH(concepto.getConceptoBean().getPlanContableCuentaHBean());
                detalle.setCentroResponsabilidadBean(concepto.getConceptoBean().getCr());
                detalle.setCreaPor(usuario.getUsuario());
                detalle.setCreaFecha(fecha.parse(dia));
                detalle.setReferencia(concepto.getConceptoBean().getNombre());

                if (id.equals(0)) {
                    listaCuotaIngresoBean.add(detalle);
                    obtenerCrPorGrado(con.getConceptoBean().getTipoConceptoBean().getIdTipoConcepto());
                } else {
                    listaCuotaIngresoBean.add(detalle);
                    obtenerCrPorGrado(con.getConceptoBean().getTipoConceptoBean().getIdTipoConcepto());
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void imprimirFormatoPdf() {
        ServletOutputStream out = null;
        try {
            CuotaIngresoService cuotaIngresoService = BeanFactory.getCuotaIngresoService();
            System.out.println("llegué");
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteFormatoDocIngresoSANJOC.jasper");
            if (usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)) {
                archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                        getRequest()).getServletContext().getRealPath("/reportes/reporteFormatoDocIngresoLaser2017.jasper");
            }
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            List<DocIngresoRepBean> listaDocIngresoRep = new ArrayList<>();
            CuotaIngresoBean cuota = new CuotaIngresoBean();
            System.out.println("nrodoc: " + cuotaIngresoBean.getNroDoc());
            cuota = cuotaIngresoService.obtenerIdCuotaIngreso(cuotaIngresoBean);
//            cuota.setIdCuotaIngreso(cuota.getIdCuotaIngreso());
//            cuota.setUnidadNegocioBean(usuarioBean.getPersonalBean().getUnidadNegocioBean());
            listaDocIngresoRep = cuotaIngresoService.obtenerCuotaIngreso(cuota);
            if (!listaDocIngresoRep.isEmpty()) {
                List<DetDocIngresoRepBean> listaRepDetDocIngreso = new ArrayList<>();
                listaRepDetDocIngreso = cuotaIngresoService.obtenerFormatoDetalleCuotaIngreso(listaDocIngresoRep.get(0).getIdDocIngreso(), usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                listaDocIngresoRep.get(0).setListaDetalle(listaRepDetDocIngreso);
            }
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaDocIngresoRep);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);
            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            response.setHeader("Content-Disposition", "inline; filename=Recibo.pdf");
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

    public void imprimirFormatoPdfCaja(Integer idCaja) {
        ServletOutputStream out = null;
        try {
            CuotaIngresoService cuotaIngresoService = BeanFactory.getCuotaIngresoService();
            System.out.println("llegué");
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteCuotaIngreso.jasper");

            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");

            List<CuotaIngresoRepBean> listaRepCuota = new ArrayList<>();
            listaRepCuota = cuotaIngresoService.obtenerCuotaReporte(idCaja, usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaRepCuota);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);
            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            response.setHeader("Content-Disposition", "inline; filename=Recibo.pdf");
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

    public String guardarCuotaIngreso() {
        String pagina = null;
        try {
            CuotaIngresoService cuotaIngresoService = BeanFactory.getCuotaIngresoService();
            Integer idCaja = cuotaIngresoService.obtenerMaxCaja(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (idCaja != null) {
                CuotaIngresoBean cuota = new CuotaIngresoBean();
                cuota.getUnidadNegocioBean().setUniNeg(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                cuota.getCajaCuotaIngresoBean().setIdCajaCuotaIngreso(idCaja);
                cuota.setImpresora(impresoraBean.getImpresora());
                cuota.getTipoDocBean().setIdCodigo(impresoraBean.getIdTipoDoc().getIdCodigo());
                cuota.setSerie(impresoraBean.getSerie());
                cuota.setNroDoc(impresoraBean.getActual());
                cuota.getPersonaBean().setIdPersona(personaBean.getIdPersona());
                cuota.setDiscente(personaBean.getNombreCompleto());
                cuota.setAnio(anio);
//            cuota.getTipoLugarPagoBean().setIdCodigo(cuotaIngresoBean.getTipoLugarPagoBean().getIdCodigo());
                cuota.getTipoModoPago().setIdCodigo(cuotaIngresoBean.getTipoModoPago().getIdCodigo());
                cuota.getTipoMonedaBean().setIdCodigo(cuotaIngresoBean.getTipoMonedaBean().getIdCodigo());
                cuota.setCreaPor(usuario.getUsuario());
                cuota.setMontoEfectivoSol(montoTotalDouble);
                cuota.getCentroResponsabilidadBean().setCr(cr);
                cuota.getTipoLugarPagoBean().setIdCodigo(MaristaConstantes.COD_LUGAR_BANCO);
                cuota.getTipoStatusDocIngBean().setIdCodigo(MaristaConstantes.COD_PAGADO);

                for (CuotaIngresoBean lista : listaCuotaIngresoBean) {
                    cuota.getConceptoBean().setIdConcepto(lista.getConceptoBean().getIdConcepto());
                    cuota.getConceptoBean().getPlanContableCuentaDBean().setCuenta(lista.getCuentaD().getCuenta());
                    cuota.getConceptoBean().getPlanContableCuentaHBean().setCuenta(lista.getCuentaH().getCuenta());
                    cuota.getConceptoBean().getPlanContableCuentaHBean().setCuenta(lista.getCuentaH().getCuenta());
                    cuota.setReferencia(lista.getReferencia());
                }
                cuotaIngresoBean = new CuotaIngresoBean();
                setCuotaIngresoBean(cuota);
                cuotaIngresoService.insertarCuotaIngreso(cuota);
                if (impresoraBean.getActual() != null && impresoraBean.getSerie() != null) {
                    ImpresoraBean imp = new ImpresoraBean();
                    imp.setImpresora(impresoraBean.getImpresora());
                    imp.setActual(impresoraBean.getActual() + 1);
                    imp.setModiPor(usuario.getUsuario());
                    imp.getUnidadNegocioBean().setUniNeg(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    imp.getIdTipoDoc().setIdCodigo(cuota.getTipoDocBean().getIdCodigo());
                    ImpresoraService impresoraService = BeanFactory.getImpresoraService();
                    impresoraService.cambiarNro(imp);
                }
                CajaCuotaIngresoBean cu = new CajaCuotaIngresoBean();
                cu.getUnidadNegocioBean().setUniNeg(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                cu.setIdCajaCuotaIngreso(idCaja);
                CajaCuotaIngresoBean cu2 = new CajaCuotaIngresoBean();
                cu2 = cuotaIngresoService.obtenerCajaAbierta(cu);
                cu2.setIngresoEfectivoSol(cu2.getIngresoEfectivoSol() + cuota.getMontoEfectivoSol());
                cuotaIngresoService.modificarMontoPorCaja(cu2);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            } else {
                String mensaje = "Verificar caja: Se encuentra cerrada o hay mas de dos cajas abiertas";
                new MensajePrime().addInformativeMessagePer(mensaje);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void cambiarMonto(RowEditEvent event) {
        try {
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            ConceptoUniNegBean conceptoUNBean = new ConceptoUniNegBean();

            conceptoUNBean.setImporte(((ConceptoUniNegBean) event.getObject()).getImporte());
            conceptoUNBean.setModiPor(usuario.getUsuario());
            conceptoUNBean.setUnidadNegocioBean(usuario.getPersonalBean().getUnidadNegocioBean());
            conceptoUNBean.getConceptoBean().setIdConcepto(((ConceptoUniNegBean) event.getObject()).getConceptoBean().getIdConcepto());
            conceptoUniNegService.modificarMontoConceptoUniNeg(conceptoUNBean);
            conceptoUNBean = new ConceptoUniNegBean();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cambiarMontoAcero(Integer idConcepto) {
        try {
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            ConceptoUniNegBean conceptoUNBean = new ConceptoUniNegBean();
            conceptoUNBean.setImporte(new BigDecimal(0.0));
            conceptoUNBean.setModiPor(usuario.getUsuario());
            conceptoUNBean.setUnidadNegocioBean(usuario.getPersonalBean().getUnidadNegocioBean());
            conceptoUNBean.getConceptoBean().setIdConcepto(idConcepto);
            conceptoUniNegService.modificarMontoConceptoUniNeg(conceptoUNBean);
            conceptoUNBean = new ConceptoUniNegBean();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerListaXCaja(Integer idCajaInteger) {
        try {
            CuotaIngresoService cuotaIngresoService = BeanFactory.getCuotaIngresoService();
            CodigoService codigoService = BeanFactory.getCodigoService();
            CuotaIngresoBean cuo = new CuotaIngresoBean();
            cuo.getUnidadNegocioBean().setUniNeg(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            cuo.getCajaCuotaIngresoBean().setIdCajaCuotaIngreso(idCajaInteger);
            listaCuotaIngresoBean = cuotaIngresoService.obtenerIngresosEnCaja(cuo);
            RequestContext.getCurrentInstance().addCallbackParam("operacionOK", true);
            listaTipoEstadpDocIng = new ArrayList<>();
            listaTipoEstadpDocIng = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_STATUS_DOCING));
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerParaReImprimir(Integer nrodDoc, String serie, Integer idCuotaIngreso) {
        try {
            cuotaIngresoBean = new CuotaIngresoBean();
            cuotaIngresoBean.setNroDoc(nrodDoc);
            cuotaIngresoBean.setSerie(serie);
            cuotaIngresoBean.getUnidadNegocioBean().setUniNeg(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            cuotaIngresoBean.setIdCuotaIngreso(idCuotaIngreso);
            imprimirFormatoPdf();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarCuotaIngreso() {
        try {
            CodigoService codigoService = BeanFactory.getCodigoService();
            ImpresoraService impresoraService = BeanFactory.getImpresoraService();
            buscarPersonal();
            listaMoneda = new ArrayList<>();
            listaMoneda = codigoService.obtenerPorTipoSoles(new TipoCodigoBean(MaristaConstantes.TIP_MON));
            listaModoPago = new ArrayList<>();
            listaModoPago = codigoService.obtenerCodigoDocIngresoBanco();
            listaTipoDocumento = new ArrayList<>();
            listaTipoDocumento = codigoService.obtenerPorTipoRecibo(new TipoCodigoBean(MaristaConstantes.TIP_DOCUMENTO));
            listaImpresora = impresoraService.obtenerCuotaIngre();
            for (ImpresoraBean imp : listaImpresora) {
                impresoraBean = new ImpresoraBean();
                impresoraBean.setActual(imp.getActual());
                impresoraBean.setSerie(imp.getSerie());
                impresoraBean.setImpresora(imp.getImpresora());
                System.out.println("impreso: " + impresoraBean.getSerie());
                System.out.println("impreso2: " + impresoraBean.getActual());
                System.out.println("impreso3: " + impresoraBean.getImpresora());
            }
            cuotaIngresoBean = new CuotaIngresoBean();
            cuotaIngresoBean.getTipoModoPago().setIdCodigo(listaModoPago.get(0).getIdCodigo());
            cuotaIngresoBean.getTipoMonedaBean().setIdCodigo(listaMoneda.get(0).getIdCodigo());
            cuotaIngresoBean.getTipoMonedaBean().setCodigo(listaMoneda.get(0).getCodigo());
            obtenerTipDoc();
            numActual = String.format("%07d", impresoraBean.getActual());
            generarCodigoPersona();
            listaCuotaIngresoBean = new ArrayList<>();
            cr = null;
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }

    }

    public String getMontoTotal() {
        System.out.println("getMontoTotal()");
        Double total = 0.0;
        String tot = "";
        String sol = "S/. ";
        try {
            for (CuotaIngresoBean det : getListaCuotaIngresoBean()) {
//                System.out.println(">>id " + det.getProgramacionBean().getIdProgramacion() + " dscto:" + det.getDscto() + " monto:" + det.getMontoPagado());
            }
            CodigoService codigo = BeanFactory.getCodigoService();
            CodigoBean cod = new CodigoBean();
            cod = codigo.obtenerPorId(cuotaIngresoBean.getTipoMonedaBean());
            for (CuotaIngresoBean detCuentaBean : getListaCuotaIngresoBean()) {
//                obtenerDscto();
                total += detCuentaBean.getMontoEfectivoSol();
            }
            if (cod != null) {
                switch (cod.getCodigo()) {
                    case "Soles":
                        if (getCuotaIngresoBean().getTipoMonedaBean() != null) {
                            switch (getCuotaIngresoBean().getTipoMonedaBean().getCodigo()) {
                                case "Soles":
                                    montoTotalDouble = total;
                                    tot = new DecimalFormat("#,##0.00").format(total);
                                    this.montoTotal = cod.getValor().concat(" ").concat(tot);
                                    break;
                            }
                        }
                }
            } else {
                montoTotalDouble = total;
                tot = new DecimalFormat("#,##0.00").format(total);
                montoTotal = sol.concat(tot);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return montoTotal;
    }

    public void obtenerCrPorGrado(Integer idTipo) {
        try {
            CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            getListaCentroResponsabilidadBean();
            String nivel = null;
            Integer cr2 = 311;
            if (idTipo.equals(103)) {
                nivel = MaristaConstantes.COD_CR_TALLER;
                listaCentroResponsabilidadBean = centroResponsabilidadService.obtenerCrPorNivelAcademico(nivel);
                this.cr = 513;
            } else {

                EstudianteBean estudiante = new EstudianteBean();
                estudiante.getPersonaBean().setIdPersona(personaBean.getIdPersona());
                estudiante.getPersonaBean().getUnidadNegocioBean().setUniNeg(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg());

                estudiante = estudianteService.obtenerEstudianteGradoAca(estudiante);
                if (estudiante != null) {
                    if (estudiante.getNivelAcademico() != null) {
                        switch (estudiante.getNivelAcademico()) {
                            case MaristaConstantes.NIV_ACA_INI:
                                nivel = MaristaConstantes.COD_CR_INI;
                                cr2 = 111;
                                break;
                            case MaristaConstantes.NIV_ACA_PRI:
                                nivel = MaristaConstantes.COD_CR_PRI;
                                cr2 = 211;
                                break;
                            case MaristaConstantes.NIV_ACA_SEC:
                                if (estudiante.getGradoHabilitado().getNombre().equals(MaristaConstantes.Cuarto_Bach_Secundaria)
                                        || estudiante.getGradoHabilitado().getNombre().equals(MaristaConstantes.Quinto_Bach_Secundaria)) {
                                    nivel = MaristaConstantes.COD_CR_BACH;
                                    cr2 = 400;
                                } else {
                                    nivel = MaristaConstantes.COD_CR_SEC;
                                    cr2 = 311;
                                }
                                break;
                        }
                    }
                    listaCentroResponsabilidadBean = centroResponsabilidadService.obtenerCrPorNivelAcademico(nivel);
                    this.cr = cr2;
                } else {
                    listaCentroResponsabilidadBean = centroResponsabilidadService.obtenerCentroResponsabilidad();
                    this.cr = 311;
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public CajaCuotaIngresoBean getCajaCuotaIngresoBean() {
        if (cajaCuotaIngresoBean == null) {
            cajaCuotaIngresoBean = new CajaCuotaIngresoBean();
        }
        return cajaCuotaIngresoBean;
    }

    public void setCajaCuotaIngresoBean(CajaCuotaIngresoBean cajaCuotaIngresoBean) {
        this.cajaCuotaIngresoBean = cajaCuotaIngresoBean;
    }

    public Calendar getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Calendar fechaActual) {
        this.fechaActual = fechaActual;
    }

    public List<CajaCuotaIngresoBean> getListaCajaCuotaIngreso() {
        if (listaCajaCuotaIngreso == null) {
            listaCajaCuotaIngreso = new ArrayList<>();
        }
        return listaCajaCuotaIngreso;
    }

    public void setListaCajaCuotaIngreso(List<CajaCuotaIngresoBean> listaCajaCuotaIngreso) {
        this.listaCajaCuotaIngreso = listaCajaCuotaIngreso;
    }

    public List<CuentaBancoBean> getListaCuentaBancoFiltroBean() {
        if (listaCuentaBancoFiltroBean == null) {
            listaCuentaBancoFiltroBean = new ArrayList();
        }
        return listaCuentaBancoFiltroBean;
    }

    public void setListaCuentaBancoFiltroBean(List<CuentaBancoBean> listaCuentaBancoFiltroBean) {
        this.listaCuentaBancoFiltroBean = listaCuentaBancoFiltroBean;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public Boolean getFlgAbierto() {
        return flgAbierto;
    }

    public void setFlgAbierto(Boolean flgAbierto) {
        this.flgAbierto = flgAbierto;
    }

    public List<PersonaBean> getListaPersonaBean() {
        if (listaPersonaBean == null) {
            listaPersonaBean = new ArrayList<>();
        }
        return listaPersonaBean;
    }

    public void setListaPersonaBean(List<PersonaBean> listaPersonaBean) {
        this.listaPersonaBean = listaPersonaBean;
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

    public Integer getTipIndividuo() {
        return tipIndividuo;
    }

    public void setTipIndividuo(Integer tipIndividuo) {
        this.tipIndividuo = tipIndividuo;
    }

    public PersonaBean getPersonaFiltroBean() {
        if (personaFiltroBean == null) {
            personaFiltroBean = new PersonaBean();
        }
        return personaFiltroBean;
    }

    public void setPersonaFiltroBean(PersonaBean personaFiltroBean) {
        this.personaFiltroBean = personaFiltroBean;
    }

//    public Boolean getFlgSoloEst() {
//        return flgSoloEst;
//    }
//
//    public void setFlgSoloEst(Boolean flgSoloEst) {
//        this.flgSoloEst = flgSoloEst;
//    }
    public Boolean getFlgGenCod() {
        return flgGenCod;
    }

    public void setFlgGenCod(Boolean flgGenCod) {
        this.flgGenCod = flgGenCod;
    }

    public List<ConceptoUniNegBean> getListaConceptoUniNegBean() {
        if (listaConceptoUniNegBean == null) {
            listaConceptoUniNegBean = new ArrayList<>();
        }
        return listaConceptoUniNegBean;
    }

    public void setListaConceptoUniNegBean(List<ConceptoUniNegBean> listaConceptoUniNegBean) {
        this.listaConceptoUniNegBean = listaConceptoUniNegBean;
    }

    public CuotaIngresoBean getCuotaIngresoBean() {
        if (cuotaIngresoBean == null) {
            cuotaIngresoBean = new CuotaIngresoBean();
        }
        return cuotaIngresoBean;
    }

    public void setCuotaIngresoBean(CuotaIngresoBean cuotaIngresoBean) {
        this.cuotaIngresoBean = cuotaIngresoBean;
    }

    public ImpresoraBean getImpresoraBean() {
        if (impresoraBean == null) {
            impresoraBean = new ImpresoraBean();
        }
        return impresoraBean;
    }

    public void setImpresoraBean(ImpresoraBean impresoraBean) {
        this.impresoraBean = impresoraBean;
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

    public List<ImpresoraBean> getListaImpresora() {
        return listaImpresora;
    }

    public void setListaImpresora(List<ImpresoraBean> listaImpresora) {
        this.listaImpresora = listaImpresora;
    }

    public String getNumActual() {
        return numActual;
    }

    public void setNumActual(String numActual) {
        this.numActual = numActual;
    }

    public List<CodigoBean> getListaMoneda() {
        if (listaMoneda == null) {
            listaMoneda = new ArrayList<>();
        }
        return listaMoneda;
    }

    public void setListaMoneda(List<CodigoBean> listaMoneda) {
        this.listaMoneda = listaMoneda;
    }

    public List<CodigoBean> getListaModoPago() {
        if (listaModoPago == null) {
            listaModoPago = new ArrayList<>();
        }
        return listaModoPago;
    }

    public void setListaModoPago(List<CodigoBean> listaModoPago) {
        this.listaModoPago = listaModoPago;
    }

    public Boolean getSerGenCollapsed() {
        return serGenCollapsed;
    }

    public void setSerGenCollapsed(Boolean serGenCollapsed) {
        this.serGenCollapsed = serGenCollapsed;
    }

    public List<CuotaIngresoBean> getListaCuotaIngresoBean() {
        if (listaCuotaIngresoBean == null) {
            listaCuotaIngresoBean = new ArrayList<>();
        }
        return listaCuotaIngresoBean;
    }

    public void setListaCuotaIngresoBean(List<CuotaIngresoBean> listaCuotaIngresoBean) {
        this.listaCuotaIngresoBean = listaCuotaIngresoBean;
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

    public Integer getCr() {
        return cr;
    }

    public void setCr(Integer cr) {
        this.cr = cr;
    }

    public Double getMontoTotalDouble() {
        return montoTotalDouble;
    }

    public void setMontoTotalDouble(Double montoTotalDouble) {
        this.montoTotalDouble = montoTotalDouble;
    }

    public List<CodigoBean> getListaTipoEstadpDocIng() {
        if (listaTipoEstadpDocIng == null) {
            listaTipoEstadpDocIng = new ArrayList<>();
        }
        return listaTipoEstadpDocIng;
    }

    public void setListaTipoEstadpDocIng(List<CodigoBean> listaTipoEstadpDocIng) {
        this.listaTipoEstadpDocIng = listaTipoEstadpDocIng;
    }

    public List<CuotaIngresoBean> getListaDetalleFiltroCuotaIngreso() {
        if (listaDetalleFiltroCuotaIngreso == null) {
            listaDetalleFiltroCuotaIngreso = new ArrayList<>();
        }
        return listaDetalleFiltroCuotaIngreso;
    }

    public void setListaDetalleFiltroCuotaIngreso(List<CuotaIngresoBean> listaDetalleFiltroCuotaIngreso) {
        this.listaDetalleFiltroCuotaIngreso = listaDetalleFiltroCuotaIngreso;
    }

    public CuotaIngresoBean getCuotaIngresoFiltroBean() {
        if (cuotaIngresoFiltroBean == null) {
            cuotaIngresoFiltroBean = new CuotaIngresoBean();
        }
        return cuotaIngresoFiltroBean;
    }

    public void setCuotaIngresoFiltroBean(CuotaIngresoBean cuotaIngresoFiltroBean) {
        this.cuotaIngresoFiltroBean = cuotaIngresoFiltroBean;
    }

    public Double getTotalSol() {
        return totalSol;
    }

    public void setTotalSol(Double totalSol) {
        this.totalSol = totalSol;
    }

    public Double getTotSoles() {
        return totSoles;
    }

    public void setTotSoles(Double totSoles) {
        this.totSoles = totSoles;
    }

}
