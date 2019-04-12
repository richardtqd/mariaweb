/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
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
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CajaGenBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.ConceptoBean;
import pe.marista.sigma.bean.ConceptoUniNegBean;
import pe.marista.sigma.bean.DetProgramacionDsctoBean;
import pe.marista.sigma.bean.ImpresoraBean;
import pe.marista.sigma.bean.PagoBancoBean;
import pe.marista.sigma.bean.PerfilBean;
import pe.marista.sigma.bean.PersonaBean;
import pe.marista.sigma.bean.ProgramacionBean;
import pe.marista.sigma.bean.ProgramacionDsctoBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.TipoDiscenteBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.ArqueoPagoBcoRepBean;
import pe.marista.sigma.bean.reporte.DetArqueoPagoBcoRepBean;
import pe.marista.sigma.bean.reporte.PagoBancoRepBean;
import pe.marista.sigma.bean.reporte.TalleresWebRepBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.ConceptoUniNegService;
import pe.marista.sigma.service.DocIngresoService;
import pe.marista.sigma.service.ImpresoraService;
import pe.marista.sigma.service.PagoBancoService;
import pe.marista.sigma.service.PerfilService;
import pe.marista.sigma.service.PersonaService;
import pe.marista.sigma.service.ProgramacionDsctoService;
import pe.marista.sigma.service.ProgramacionService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.util.MensajesBackEnd;

/**
 *
 * @author MS001
 */
public class PagoBancoMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of PagoBancoMB
     */
    @PostConstruct
    public void init() {
        try {
            usuarioLoginBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            SimpleDateFormat formato = new SimpleDateFormat("yyyy");
            String date = formato.format(new Date());
            anio = new Integer(date);
            getPagoBancoBean();
            CodigoService codigoService = BeanFactory.getCodigoService();
            listaTipoLugarPago = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_LUG_PAGO));

//            listaTipoDoc = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_DOCUMENTO));
            listaModoPago = codigoService.obtenerCodigoDocIngresoBanco();
            listaMoneda = new ArrayList<>();
            listaMoneda = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_MON));

            //validar perfil
            if (usuarioLoginBean != null) {
                //validar perfil
                PerfilService perfilService = BeanFactory.getPerfilService();
                List<PerfilBean> listaPerfilBean = perfilService.obtenerUsarioPerfil(usuarioLoginBean);
                for (PerfilBean listaPerfilBean1 : listaPerfilBean) {
                    if (listaPerfilBean1.getNombre().equals(MaristaConstantes.PER_CAJERO)) {
                        System.out.println("perfil..." + MaristaConstantes.PER_CAJERO);
                        this.flgPerfilCajero = Boolean.TRUE;
                        break;
                    } else {
                        System.out.println("padre...");
                        this.flgPerfilCajero = Boolean.FALSE;
                    }
                }
            }
            getFlgGenCod();
            setFlgGenCod(Boolean.TRUE);
            getPersonaFiltroBean();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    private PagoBancoBean pagoBancoBean;
    private List<PagoBancoBean> listaDetPagoBancoBean;
    private ImpresoraBean impresoraBean;
    private PersonaBean personaBean;
    private PersonaBean personaFiltroBean;
    private List<PersonaBean> listaPersonaBean;
    private TipoDiscenteBean tipoDiscenteBean;

//    private String numActual;
    private List<ConceptoUniNegBean> listaConceptoUniNegBean;
    private List<CodigoBean> listaTipoDoc;
    private List<CodigoBean> listaModoPago;
    private List<CodigoBean> listaMoneda;
    private List<CodigoBean> listaTipoEstadoPagoBco;
    private List<CodigoBean> listaTipoLugarPago;

    private List<ProgramacionBean> listaProgramacionBean;
    private List<ProgramacionBean> listaProgramacionSessionBean;
    private ProgramacionBean programacionBean;

    private List<ProgramacionDsctoBean> listaProgramacionDsctoBean;

    //ayuda
    private Boolean valAdmTodos;
    private Boolean flgGenCod = Boolean.FALSE;

    private UsuarioBean usuarioLoginBean;
    private Integer anio;
    private Object strDscto;
    private BigDecimal dscto = new BigDecimal(0);
    private BigDecimal total = new BigDecimal(0);
    private Boolean flgPerfilCajero;
    private Boolean flgDisableGenCod = Boolean.FALSE;
    private Date fechaInicio;
    private Date fechaFin;
    private List<ArqueoPagoBcoRepBean> listaArqueoPagoBcoRepBean;
    private List<DetArqueoPagoBcoRepBean> listaDetArqueoPagoBcoRepBean; 
    private String nombreConcepto;
    private BigDecimal dsctoAyuda = new BigDecimal(0);
    private Double totalDouble=0.00;

    public void obtenerDsctoDet(Integer n) {
        try {
            listaDetPagoBancoBean.get(n).setMontoPagado(listaDetPagoBancoBean.get(n).getMonto().subtract(listaDetPagoBancoBean.get(n).getDscto()));
            listaDetPagoBancoBean.get(n).setDsctoTipoDicente((listaDetPagoBancoBean.get(n).getDscto()));
            dsctoAyuda= listaDetPagoBancoBean.get(n).getDscto();
//            for (PagoBancoBean det : getListaDetPagoBancoBean()) {
//                det.setMontoPagado(det.getMontoPagado().subtract(det.getDscto()));
//            }
            obtenerDscto();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void verHistoriaArqueosTalleresBco() {
        try {
            if (getFechaInicio() != null) {
                Timestamp t = new Timestamp(getFechaInicio().getTime());
                t.setHours(0);
                t.setMinutes(0);
                t.setSeconds(0);
                setFechaInicio(t);

            } else {
                setFechaInicio(null);
            }
            if (getFechaFin() != null) {
                Timestamp u = new Timestamp(getFechaFin().getTime());
                u.setHours(23);
                u.setMinutes(59);
                u.setSeconds(59);
                setFechaFin(u);
            } else {
                setFechaInicio(null);
            }
            PagoBancoService pagoBancoService = BeanFactory.getPagoBancoService();
            listaArqueoPagoBcoRepBean = pagoBancoService.obtenerArqueoPagoBancoPorUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), usuarioLoginBean.getUsuario(), getFechaInicio(), getFechaFin());
            if (listaArqueoPagoBcoRepBean.isEmpty()) {
                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void verSexo() {
        try {
            System.out.println("sex " + getPersonaBean().getSexo());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void generarCodigoPersona() {
        try {
            if (flgGenCod.equals(Boolean.TRUE)) {
//                PersonaService personaService = BeanFactory.getPersonaService();
//                String cod = null;
//                cod = personaService.generarCodigoPersona(anio, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//                getPersonaBean().setIdPersona(cod.toString());
//                getPersonaBean().setNroDoc(null);
                System.out.println("no tiene dni");
            } else {
                getPersonaBean().setIdPersona(null);
                getPersonaBean().setNroDoc(null);
            }
            aaa();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void aaa() {
        try {
            System.out.println("name aaa-->" + getPersonaBean().getApepat() + " " + getPersonaBean().getApemat() + " " + getPersonaBean().getNombre());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPersonaPorUsu() {
        try {
            PersonaService personaService = BeanFactory.getPersonaService();
            if (this.flgPerfilCajero.equals(Boolean.TRUE)) {
                listaPersonaBean = personaService.obtenerTop10Persona(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            } else {
                //            listaPersonaBean = personaService.obtenerPersonaPorCorreo(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), usuarioLoginBean.getUsuario());
                listaPersonaBean = personaService.obtenerTop10Persona(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            }
            cargarDatosSinDiscente();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void setterarCodigos() {
        try {
            System.out.println("tipo doc " + impresoraBean.getIdTipoDoc().getCodigo());
            getPagoBancoBean().setIdTipoDoc(getImpresoraBean().getIdTipoDoc());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void rowSelect(SelectEvent event) {
        try {
            CodigoService codigoService = BeanFactory.getCodigoService();
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            personaBean = (PersonaBean) event.getObject();
            this.flgDisableGenCod = Boolean.TRUE;
            tipoDiscenteBean = docIngresoService.obtenerTipoDiscente(personaBean.getIdPersona(), anio, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (getTipoDiscenteBean().getFlgAlu()) {
                strDscto = MaristaConstantes.DSCTO_ALU;
            } else if (getTipoDiscenteBean().getFlgExa()) {
                strDscto = MaristaConstantes.DSCTO_EX_ALU;
            } else if (getTipoDiscenteBean().getFlgExt()) {
                strDscto = MaristaConstantes.DSCTO_EXT;
            } else if (getTipoDiscenteBean().getFlgHem()) {
                strDscto = MaristaConstantes.DSCTO_EMP;
            } else if (getTipoDiscenteBean().getFlgHex()) {
                strDscto = MaristaConstantes.DSCTO_EX_ALU;
            } else {
                strDscto = MaristaConstantes.DSCTO_EXT;
            }
            if (listaConceptoUniNegBean == null) {
                listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoConProgramacion(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), strDscto);
            }
            CodigoBean codigoTipoLugarPago = new CodigoBean();
            CodigoBean codigoTipoModoPago = new CodigoBean();
            CodigoBean codigoTipoMoneda = new CodigoBean();
            CodigoBean codigoStatusPagoBanco = new CodigoBean();

            codigoTipoLugarPago = codigoService.obtenerPorCodigo(new CodigoBean(0, "Banco", new TipoCodigoBean(MaristaConstantes.TIP_LUG_PAGO)));
            codigoTipoModoPago = codigoService.obtenerPorCodigo(new CodigoBean(0, "Banco", new TipoCodigoBean(MaristaConstantes.TIP_MOD_PAGO)));
            codigoTipoMoneda = codigoService.obtenerPorCodigo(new CodigoBean(0, "Soles", new TipoCodigoBean(MaristaConstantes.TIP_MON)));
            codigoStatusPagoBanco = codigoService.obtenerPorCodigo(new CodigoBean(0, "Emitido", new TipoCodigoBean(MaristaConstantes.TIP_STATUS_DOCING)));

            pagoBancoBean.setIdTipoLugarPago(codigoTipoLugarPago);
            pagoBancoBean.setIdTipoModoPago(codigoTipoModoPago);
            pagoBancoBean.setIdTipoMoneda(codigoTipoMoneda);
            pagoBancoBean.setTipoStatusPagoBanco(codigoStatusPagoBanco);

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }

    }

    public void cargarDatosSinDiscente() {
        try {
            pagoBancoBean = new PagoBancoBean();

            listaDetPagoBancoBean = new ArrayList<>();
            CodigoService codigoService = BeanFactory.getCodigoService();
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
//            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
//            tipoDiscenteBean = docIngresoService.obtenerTipoDiscente(personaBean.getIdPersona(), anio, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            strDscto = MaristaConstantes.DSCTO_EXT;
            listaConceptoUniNegBean = new ArrayList<>();
            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoConProgramacion(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), strDscto);
//            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoExterno(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), strDscto);

            CodigoBean codigoTipoLugarPago = new CodigoBean();
            CodigoBean codigoTipoModoPago = new CodigoBean();
            CodigoBean codigoTipoMoneda = new CodigoBean();
//            CodigoBean codigoStatusPagoBanco = new CodigoBean();

            codigoTipoLugarPago = codigoService.obtenerPorCodigo(new CodigoBean(0, "Banco", new TipoCodigoBean(MaristaConstantes.TIP_LUG_PAGO)));
            codigoTipoModoPago = codigoService.obtenerPorCodigo(new CodigoBean(0, "Banco", new TipoCodigoBean(MaristaConstantes.TIP_MOD_PAGO)));
            codigoTipoMoneda = codigoService.obtenerPorCodigo(new CodigoBean(0, "Soles", new TipoCodigoBean(MaristaConstantes.TIP_MON)));
//            codigoStatusPagoBanco = codigoService.obtenerPorCodigo(new CodigoBean(0, "Emitido", new TipoCodigoBean(MaristaConstantes.TIP_STATUS_PAGO_BCO)));

            pagoBancoBean.setIdTipoDoc(getImpresoraBean().getIdTipoDoc());
            pagoBancoBean.setIdTipoLugarPago(codigoTipoLugarPago);
            pagoBancoBean.setIdTipoModoPago(codigoTipoModoPago);
            pagoBancoBean.setIdTipoMoneda(codigoTipoMoneda);
//            pagoBancoBean.setTipoStatusPagoBanco(codigoStatusPagoBanco);

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }

    }

    public void agregarConcepto(Integer idConcepto) {
        try {
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            ConceptoUniNegBean con = new ConceptoUniNegBean();
            con.getConceptoBean().setIdConcepto(idConcepto);
            con.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            con = conceptoUniNegService.obtenerConceptoPorId(con);
            if (con.getImporte().doubleValue() == 0 && !con.getConceptoBean().getNombre().equals(MaristaConstantes.CON_DEV_ARENDIR)) {
                new MensajePrime().addInformativeMessagePer("msjSerGenImpCero");
            } else {
                Integer id = 0;
//                id = conceptoUniNegService.obtenerTipoPorProgramacion(idConcepto, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//                if (id.equals(1)) {
//                    RequestContext.getCurrentInstance().addCallbackParam("abrirProgramcion", true);
//                    obtenerProgramacionActivos(con.getConceptoBean().getIdConcepto());
//                }

                SimpleDateFormat fecha = new SimpleDateFormat("dd-MM-yyyy:HH:mm:SS");
                String dia = fecha.format(new Date());
                ConceptoUniNegBean concepto = new ConceptoUniNegBean();
                PagoBancoBean detalle = new PagoBancoBean();
                concepto.getConceptoBean().setIdConcepto(idConcepto);
                concepto.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

                if (strDscto == null) {
                    strDscto = MaristaConstantes.DSCTO_EXT;
                }
                concepto = conceptoUniNegService.obtenerConceptoPorIdConDscto(concepto, strDscto);

                detalle.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                detalle.setConceptoBean(concepto.getConceptoBean());
                detalle.setMonto(concepto.getImporte());
                Double dsctoIm = concepto.getImporte().doubleValue() - concepto.getImporteConDscto().doubleValue();
                detalle.setDsctoTipoDicente(BigDecimal.valueOf(dsctoIm));//concepto.getDsctoTipoPersona
                detalle.setDscto(BigDecimal.valueOf(dsctoIm));//concepto.getDsctoTipoPersona

                dsctoAyuda=detalle.getDscto();
                detalle.setMontoConDscto(concepto.getImporteConDscto());//  
                detalle.setMontoPagado(concepto.getImporteConDscto());//concepto.getDsctoTipoPersona   RESTA DE MONTO CON DSCTO  
                detalle.setIdTipoDscto(null);

                detalle.setCuentaD(concepto.getConceptoBean().getPlanContableCuentaDBean().getCuenta());
                detalle.setCuentaH(concepto.getConceptoBean().getPlanContableCuentaHBean().getCuenta());
//                    detalle.setCentroResponsabilidadBean(concepto.getConceptoBean().getCr());
                detalle.setCreaPor(usuarioLoginBean.getUsuario());
                detalle.setCreaFecha(fecha.parse(dia));
                detalle.setReferencia(concepto.getConceptoBean().getNombre());

                id = conceptoUniNegService.obtenerTipoPorProgramacion(idConcepto, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                if (id.equals(1)) {
                    RequestContext.getCurrentInstance().addCallbackParam("abrirProgramcion", true);
                    obtenerProgramacionActivos(con.getConceptoBean().getIdConcepto());
                    listaDetPagoBancoBean.add(detalle);
                } else {
                    listaDetPagoBancoBean.add(detalle);
                }

            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerProgramacionActivos(Integer idConcepto) {
        try {
            if (idConcepto != null) {
//                for (ProgramacionBean listaProgramacion : listaProgramacionBean) {
//                    System.out.println("lista prog  v2: " + listaProgramacion.getFlgInscrito());
//                }
                List<Integer> lista = new ArrayList<>();
                if (listaDetPagoBancoBean.isEmpty()) {
                    lista.add(0);
                } else {
                    for (PagoBancoBean det : listaDetPagoBancoBean) {
//                        System.out.println("listaxxxxxxxxxxxxxxxxxx" + det.getProgramacionBean().getIdProgramacion());
                        lista.add(det.getProgramacionBean().getIdProgramacion());
                    }
                }
                ProgramacionService programacionService = BeanFactory.getProgramacionService();
                if (personaBean.getIdPersona() != null) {
                    List<Integer> lista2 = new ArrayList<>();
                    lista2 = programacionService.obtenerProgRegPorDiscente(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), personaBean.getIdPersona());
                    if (!lista2.isEmpty()) {
                        for (Integer p : lista2) {
                            System.out.println("listyy" + p);
                            lista.add(p);
                        }
                    }
                }
                listaProgramacionBean = new ArrayList<>();
                if (!idConcepto.equals(null)) {
                    listaProgramacionBean = programacionService.obtenerProgPorTipoActivosDocIngFor(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), lista, idConcepto);
                    for (ProgramacionBean listaProgramacion : listaProgramacionBean) {
//                        if (listaProgramacion.getDisponibles().equals(0)) {
                        System.out.println("cupos " + listaProgramacion.getCupos());
                        if (listaProgramacion.getCupos().equals(0)) {
                            System.out.println("true");
//                    listaProgramacionBean.remove(listaProgramacion);
                            listaProgramacion.setFlgBloqueado(Boolean.TRUE);
//                            break;
                        } else {
                            System.out.println("false");
                            listaProgramacion.setFlgBloqueado(Boolean.FALSE);
                        }

                    }
                } else {
                    listaProgramacionBean = programacionService.obtenerProgPorTipoActivos(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    for (ProgramacionBean listaProgramacion : listaProgramacionBean) {
                        if (listaProgramacion.getCupos().equals(0)) {
//                    listaProgramacionBean.remove(listaProgramacion);
                            listaProgramacion.setFlgBloqueado(Boolean.TRUE);
//                            break;
                        } else {
                            listaProgramacion.setFlgBloqueado(Boolean.FALSE);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerProgramacionRegistro() {
        try {
            ProgramacionService programacionService = BeanFactory.getProgramacionService();
            if (listaProgramacionSessionBean == null) {
                listaProgramacionSessionBean = new ArrayList<>();
            }
            int est = 0;
            for (ProgramacionBean lista : listaProgramacionBean) {
                if (lista.getFlgInscrito() == null) {
                    lista.setFlgInscrito(Boolean.FALSE);
                }
                System.out.println("est " + lista.getFlgInscrito());
                if (!lista.getFlgInscrito().equals(null)) {
                    if (lista.getFlgInscrito().equals(Boolean.TRUE)) {
                        est = 1;
                        break;
                    }
                }
            }
            System.out.println("-------------------estado prog break------------" + est);
            //ingresa
            if (est == 1) {
                ProgramacionBean progra = new ProgramacionBean();
                for (ProgramacionBean listaProgramacionBean1 : listaProgramacionBean) {
                    if (listaProgramacionBean1.getFlgInscrito() != null) {
                        if (!listaProgramacionBean1.getFlgInscrito().equals(null)) {
                            if (listaProgramacionBean1.getFlgInscrito().equals(true)) {
                                progra.setIdProgramacion(listaProgramacionBean1.getIdProgramacion());
                                progra.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                                progra = programacionService.obtenerPrograPorId(progra);
                                listaProgramacionSessionBean.add(progra);
                                System.out.println("descrip:" + progra.getDescripProgramacion());

                                for (PagoBancoBean listDetDocIngreso2 : listaDetPagoBancoBean) {
                                    if (progra.getConceptoUniNegBean().getConceptoBean().getIdConcepto() != null
                                            && listDetDocIngreso2.getConceptoBean().getIdConcepto().equals(progra.getConceptoUniNegBean().getConceptoBean().getIdConcepto())) {
                                        if (listDetDocIngreso2.getProgramacionBean().getIdProgramacion() == null) {
                                            listDetDocIngreso2.setReferencia(progra.getDescripProgramacion());
                                            listDetDocIngreso2.setProgramacionBean(progra);
                                        } else {
                                            if (progra.getIdProgramacion().equals(listDetDocIngreso2.getProgramacionBean().getIdProgramacion())) {
                                                listDetDocIngreso2.setReferencia(progra.getDescripProgramacion());
                                                listDetDocIngreso2.setProgramacionBean(progra);
                                            }
                                        }
                                    }
                                    System.out.println("list prog->" + listDetDocIngreso2.getProgramacionBean().getIdProgramacion());
                                }
                                System.out.println("list->" + listaDetPagoBancoBean.size());
                            }
                        }
                    }
                }
                //borrar
                for (PagoBancoBean det : listaDetPagoBancoBean) {
                    System.out.println("listayy" + det.getProgramacionBean().getIdProgramacion());
                }
                validarDescuentosTalleresFase1(null);
                for (PagoBancoBean det : listaDetPagoBancoBean) {
                    if (det.getDscto().equals(null)) {
                        det.setDscto(new BigDecimal(0));
                    } else {
                        det.setDscto(det.getDsctoTipoDicente());
                    }
                }
                //borrar
                for (PagoBancoBean det : listaDetPagoBancoBean) {
                    System.out.println("listaff" + det.getProgramacionBean().getIdProgramacion());
                }
                if (listaProgramacionDsctoBean != null) {
                    if (listaProgramacionDsctoBean.size() > 0) {
                        for (ProgramacionDsctoBean li : listaProgramacionDsctoBean) {
                            ProgramacionDsctoService programacionDsctoService = BeanFactory.getProgramacionDsctoService();
                            System.out.println("id progra despues validarDescuentosTalleres " + li.getIdProgramacionDscto());

                            List<DetProgramacionDsctoBean> listaDetProgDscto = new ArrayList<>();
                            List<Integer> idProgDscto = new ArrayList<>();//ids de las programaciones del detalle
                            idProgDscto.add(li.getIdProgramacionDscto());

                            listaDetProgDscto = programacionDsctoService.obtenerDetallePorProgramacionDscto(idProgDscto, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

                            for (DetProgramacionDsctoBean lis : listaDetProgDscto) {
                                System.out.println("-----------inicio:" + lis.getProgramacionBean().getIdProgramacion());
                                for (PagoBancoBean det : listaDetPagoBancoBean) {
                                    System.out.println("id 1:" + lis.getProgramacionBean().getIdProgramacion() + "--" + det.getProgramacionBean().getIdProgramacion());
                                    if (!det.getProgramacionBean().getIdProgramacion().equals(null)) {
                                        if (lis.getProgramacionBean().getIdProgramacion().equals(det.getProgramacionBean().getIdProgramacion())) {
                                            System.out.println("id det->>>>>>>>>>>>>> :" + det.getProgramacionBean().getIdProgramacion() + "-" + lis.getValor().doubleValue());
                                            BigDecimal dscto = new BigDecimal(lis.getValor().doubleValue());
                                            det.setMontoPagado(det.getMontoConDscto().subtract(dscto));
                                            det.setDscto(dscto.add(det.getDsctoTipoDicente()));
                                            det.setProgramacionBeanDscto(lis.getProgramacionDsctoBean());
                                            System.out.println("id>>>> " + det.getProgramacionBean().getIdProgramacion() + " dscto:" + det.getDscto() + " monto:" + det.getMontoPagado());
                                            break;
                                        }
                                    }
                                }
                                System.out.println("-----------fiin:" + lis.getProgramacionBean().getIdProgramacion());
                            }
                        }
                    }
                }
                //borrar
                for (PagoBancoBean det : listaDetPagoBancoBean) {
                    System.out.println("listamm" + det.getProgramacionBean().getIdProgramacion());
                }
                obtenerDscto();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            } else {
                RequestContext.getCurrentInstance().addCallbackParam("operacionError", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerDscto() {
        try {  
            this.dscto = new BigDecimal(0);
            for (PagoBancoBean detDocIngresoBean : listaDetPagoBancoBean) {
                detDocIngresoBean.setDscto(dsctoAyuda);
                System.out.println("dscto " + detDocIngresoBean.getDscto());
                if (detDocIngresoBean.getDscto() == null) {
                    detDocIngresoBean.setDscto(new BigDecimal(0));
                }
                    this.dscto = this.dscto.add(detDocIngresoBean.getDscto());
            }
            obtenerMontoTotal();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }

    }

    public List<ProgramacionDsctoBean> validarDescuentosTalleresFase1(List<Integer> listaNewIds) {
        try {

            System.out.println("validarDescuentosTalleresFase1");
            ProgramacionDsctoService programacionDsctoService = BeanFactory.getProgramacionDsctoService();
            ProgramacionDsctoBean programacionDsctoBean = new ProgramacionDsctoBean();
            List<Integer> lista = new ArrayList<>();//ids de las programaciones del detalle 
            List<ProgramacionDsctoBean> listaProg = new ArrayList<>();//ids de las programaciones del detalle
            if (listaNewIds != null) {
                for (Integer l : lista) {
                    System.out.println("  lista..." + l);
                }
            } else {
                System.out.println("  lista null...");

            }
            if (listaNewIds == null) {
                System.out.println("llenando lista...");
                //OBTENGO LOS IDS DE LAS PROGRAMACIONES DEL DETALLE DEL DOC ING 
                if (listaDetPagoBancoBean.isEmpty()) {
                    lista.add(0);
                } else {
                    for (PagoBancoBean det : listaDetPagoBancoBean) {
                        if (!det.getProgramacionBean().getIdProgramacion().equals(null)) {
                            lista.add(det.getProgramacionBean().getIdProgramacion());
                            System.out.println("id det :" + det.getProgramacionBean().getIdProgramacion());
                        }
                    }
                }
            } else {
                System.out.println("llenando listaNewIds ...");
                lista = listaNewIds;
            }

            Integer size = lista.size();
            Integer sizeDetLista = listaDetPagoBancoBean.size();
            System.out.println("//OBTENGO LA MEJOR OPCION DE LA PROGRAMACIONDSCTO");
            //OBTENGO LA MEJOR OPCION DE LA PROGRAMACIONDSCTO
            listaProg = programacionDsctoService.obtenerProgDsctoPorProgramacionesCantidadFor(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), lista, size);
            if (listaProg != null) {
                if (!listaProg.isEmpty()) {
                    ProgramacionDsctoBean progDsctoAux = new ProgramacionDsctoBean();
                    Double aux = 0.0;
                    for (ProgramacionDsctoBean siz : listaProg) {
                        programacionDsctoBean = programacionDsctoService.obtenerProgDsctoPorProgramacionesFor(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), lista, siz.getCantProgramaciones());
//                    System.out.println("id========>" + programacionDsctoBean.getIdProgramacionDscto());
                        if (programacionDsctoBean != null) {
                            if (programacionDsctoBean.getIdProgramacionDscto() != null) {
                                System.out.println("id--------------------------------->" + programacionDsctoBean.getIdProgramacionDscto());
                                if (progDsctoAux.getValor() == null) {
                                    progDsctoAux.setValor(aux);
                                }
                                System.out.println("aux->" + aux);
                                System.out.println("bean->" + programacionDsctoBean.getValor());
                                if (programacionDsctoBean.getValor() > progDsctoAux.getValor()) {
                                    progDsctoAux = programacionDsctoBean;
                                }
                            } else {
                                System.out.println("nada");
                            }
                        }
                    }
                    if (progDsctoAux.getIdProgramacionDscto() != null) {
//                        System.out.println("win:" + programacion,,,,..DsctoBean.getIdProgramacionDscto());
                        programacionDsctoBean = progDsctoAux;
                    } else {
                        programacionDsctoBean = null;

                    }
//                    System.out.println("winnnn----" + programacionDsctoBean.getIdProgramacionDscto());
                    if (programacionDsctoBean != null) {
                        if (programacionDsctoBean.getIdProgramacionDscto() != null) {
                            System.out.println("prog dscto" + programacionDsctoBean.getIdProgramacionDscto() + " - " + programacionDsctoBean.getDescripcion());
                            List<DetProgramacionDsctoBean> listaDetProgDscto = new ArrayList<>();
                            List<Integer> idProgDscto = new ArrayList<>();//ids de las programaciones del detalle
                            idProgDscto.add(programacionDsctoBean.getIdProgramacionDscto());

                            listaDetProgDscto = programacionDsctoService.obtenerDetallePorProgramacionDscto(idProgDscto, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

                            //SI EL TAMAÑO DE PROGRAMACIONES DEL DETALLE ES IGUAL AL DEL DETALLE DE PROGRAMCIONES DEL DSCTO 
                            if (sizeDetLista.equals(listaDetProgDscto.size())) {
                                listaProgramacionDsctoBean = new ArrayList<>();//= SOLO HAY UN DSCTO -->NEW ARRAY LIST  
                                listaProgramacionDsctoBean.add(programacionDsctoBean);
                                System.out.println("fin!!!!!!!!!!!");
                            } else {
                                List<Integer> listaNew = new ArrayList<>();
                                for (DetProgramacionDsctoBean A : listaDetProgDscto) {
                                    listaNew.add(A.getProgramacionBean().getIdProgramacion());
                                }

                                for (ProgramacionDsctoBean list : listaProgramacionDsctoBean) {
                                    System.out.println("id1:" + programacionDsctoBean.getIdProgramacionDscto() + "id2:" + list.getIdProgramacionDscto());
                                    Integer estado = 0;
                                    if (!programacionDsctoBean.getIdProgramacionDscto().equals(list.getIdProgramacionDscto())) {
                                        System.out.println("! equal id");
                                        estado = programacionDsctoService.validarProg1En2(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaNew, list.getIdProgramacionDscto(), listaNew.size());
                                        if (estado == null) {
                                            estado = 0;
                                        }
                                        list.setFlgDelete(estado);
                                    } else {
                                        System.out.println("equal id");
                                        list.setFlgDelete(estado);
                                    }
                                }
                                removeProgramacionDscto();
                                List<Integer> listaIds = new ArrayList<>();//ids de las programaciones  
                                for (ProgramacionDsctoBean list : listaProgramacionDsctoBean) {
                                    List<Integer> listaIdProgDscto = new ArrayList<>();
                                    listaIdProgDscto.add(list.getIdProgramacionDscto());
                                    List<DetProgramacionDsctoBean> listaDetProgDscto2 = new ArrayList<>();
                                    listaDetProgDscto2 = programacionDsctoService.obtenerDetallePorProgramacionDscto(listaIdProgDscto, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                    for (DetProgramacionDsctoBean l : listaDetProgDscto2) {
                                        listaIds.add(l.getProgramacionBean().getIdProgramacion());
                                    }
                                }
//                                for (Integer l : listaIds) {
//                                    System.out.println("listaIds :" + l);
//                                }
//                                for (Integer l : lista) {
//                                    System.out.println("lista  :" + l);
//                                }
                                Integer sumSize = 0;
                                if (listaIds != null && lista != null) {
                                    sumSize = lista.size() + listaIds.size();
                                }
                                if (!sumSize.equals(sizeDetLista)) {
                                    System.out.println("sigue-----------------!!");
                                    validarIdProgramacion(listaIds, lista);
                                } else {
                                    listaProgramacionDsctoBean.add(programacionDsctoBean);
                                    System.out.println("fin");
                                }
                            }
                        }
                    } else {
                        System.out.println("prog dscto null");
                    }
                } else {
                    System.out.println(" if (!listaProg.isEmpty())  null");
                    //añadir doc ing
                    listaProgramacionDsctoBean = new ArrayList<>();
                    for (PagoBancoBean det : listaDetPagoBancoBean) {
                        System.out.println("xxxx");
                        det.setProgramacionBeanDscto(null);
//                        det.setProgramacionBean(null);
                        if (det.getDscto().equals(null)) {
                            det.setDscto(new BigDecimal(0));
                        } else {
                            det.setDscto(det.getDsctoTipoDicente());
                        }
                    }
                    obtenerDscto();
                }
            } else {
                System.out.println("prog dscto null<<<<<<<");
            }
            System.out.println("-----------fin //OBTENGO LA MEJOR OPCION DE LA PROGRAMACIONDSCTO-------");
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return listaProgramacionDsctoBean;
    }

    public void quitarUltimoConceptoProgramacion() {
        try {
            Integer size = listaDetPagoBancoBean.size() - 1;
            System.out.println("size " + size);
            PagoBancoBean detalle = new PagoBancoBean();
            detalle = listaDetPagoBancoBean.get(size);
            listaDetPagoBancoBean.remove(detalle);

            if (listaProgramacionSessionBean != null) {
                if (!listaProgramacionSessionBean.isEmpty()) {
                    for (ProgramacionBean lista : listaProgramacionSessionBean) {
                        if (detalle.getConceptoBean().getIdConcepto() != null
                                && lista.getConceptoUniNegBean().getConceptoBean().getIdConcepto().equals(detalle.getConceptoBean().getIdConcepto())
                                && lista.getIdProgramacion().equals(detalle.getProgramacionBean().getIdProgramacion())) {
                            listaProgramacionSessionBean.remove(lista);
                            break;
                        }
                    }
                }
            }
            validarDescuentosTalleresFase1(null);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void validarIdProgramacion(List<Integer> listIds, List<Integer> listDetalleDoc) {

        try {
            System.out.println("validarIdProgramacion");
            List<Integer> newList = new ArrayList<>();
//            newList = listDetalleDoc;
            for (Integer X : listDetalleDoc) {
                Integer estad = 1;
                System.out.println("X:" + X);
                for (Integer Y : listIds) {
                    System.out.println("X:" + X + " = " + "Y:" + Y);
                    if (X.equals(Y)) {
                        estad = 0;
                        break;
                    }
                }
                if (estad.equals(1)) {
                    System.out.println("add:" + X);
                    newList.add(X);
                }
            }

            if (newList.size() > 0) {
                System.out.println("newList.size()>0");
                validarDescuentosTalleresFase1(newList);
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public ProgramacionDsctoBean validarIdProgramacionDscto() {
        ProgramacionDsctoBean programacionDscto = new ProgramacionDsctoBean();
        try {

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return programacionDscto;
    }

    public void removeProgramacionDscto() {
        try {
            System.out.println("removeProgramacionDscto");
            if (listaProgramacionDsctoBean != null) {
                List<ProgramacionDsctoBean> lista = new ArrayList<>();
                lista = listaProgramacionDsctoBean;
                for (ProgramacionDsctoBean list : lista) {
                    if (list.getFlgDelete() != null) {
                        if (list.getFlgDelete().equals(1)) {
                            System.out.println("eliminando ... " + list.getIdProgramacionDscto());
                            listaProgramacionDsctoBean.remove(list);
                        } else {
                            System.out.println("se queda ... " + list.getIdProgramacionDscto());
                        }
                    }
                }
//                for (ProgramacionDsctoBean o : listaProgramacionDsctoBean) {
//                    System.out.println("lista final " + o.getIdProgramacionDscto());
//                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void validarProgramacion(Integer idProgramacion) {
        try {
            for (ProgramacionBean lista : listaProgramacionBean) {
                if (lista.getStatus() != null) {
                    if (lista.getStatus().equals(Boolean.TRUE) && lista.getIdProgramacion().equals(idProgramacion)) {
                        lista.setFlgInscrito(Boolean.TRUE);
                    } else {
                        lista.setFlgInscrito(Boolean.FALSE);
                    }
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void noSeleccionarProgra() {
        try {
            for (ProgramacionBean lista : listaProgramacionBean) {
                lista.setFlgInscrito(Boolean.FALSE);
            }
            this.setValAdmTodos(Boolean.FALSE);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String guardarPagoBanco() {
        String pagina = null;
        try {

            PagoBancoService pagoBancoService = BeanFactory.getPagoBancoService();
            pagina = new MaristaUtils().validaUsuarioSesion();

            if (pagina == null) {
                System.out.println("nombre1 " + getPersonaBean().getApemat() + " " + getPersonaBean().getApemat() + " " + getPersonaBean().getNombre());
                SimpleDateFormat fecha = new SimpleDateFormat("dd-MM-yyyy:HH:mm:SS");
                String dia = fecha.format(new Date());

                if (getPersonaBean().getIdPersona() != null || this.flgGenCod.equals(Boolean.TRUE)) {
                    System.out.println("if (getPersonaBean().getIdPersona() != null || this.flgGenCod.equals(Boolean.TRUE))");
                    aaa();
                    System.out.println("nombre " + getPersonaBean().getApemat() + " " + getPersonaBean().getApemat() + " " + getPersonaBean().getNombre());
                    pagoBancoBean.setIdDiscente(getPersonaBean().getIdPersona());
                    pagoBancoBean.setDiscente(getPersonaBean().getNombreCompletoDesdeApellidos());

                    pagoBancoBean.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                    pagoBancoBean.setAnio(anio);
                    pagoBancoBean.setFechaPago(fecha.parse(dia));
                    pagoBancoBean.setCreaPor(usuarioLoginBean.getUsuario());
                    pagoBancoBean.setCreaFecha(fecha.parse(dia));

                    // getEstadoRegIng:
                    //1.- se registro correctamente
                    //2.-error
                    //3.- prog sin cupo
                    pagoBancoService.insertarDocIngresoPagoBanco(pagoBancoBean, listaDetPagoBancoBean, listaProgramacionSessionBean, this.flgGenCod, getPersonaBean());

                    System.out.println("pagoBancoBean.getEstadoRegIng()" + pagoBancoBean.getEstadoRegIng());

                    if (pagoBancoBean.getEstadoRegIng().equals(1)) {
                        RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                    } else if (pagoBancoBean.getEstadoRegIng().equals(0)) {
                        RequestContext.getCurrentInstance().addCallbackParam("operacionErronea", true);
                    } else if (pagoBancoBean.getEstadoRegIng().equals(3)) {
                        RequestContext.getCurrentInstance().addCallbackParam("operacionSinCupos", true);
                    }
                } else {
                    new MensajePrime().addInformativeMessagePer("msjEtiquetaPersReq");
                    System.out.println("id " + personaBean.getIdPersona());
                }
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void obtenerMontoTotal() {
        System.out.println("getMontoTotal()");
        try {
            this.total = new BigDecimal(0);
            for (PagoBancoBean det : listaDetPagoBancoBean) {
                total = total.add(det.getMontoPagado());
                totalDouble= total.doubleValue();
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

    public void quitarConcepto(PagoBancoBean detalle) {
        try {

            listaDetPagoBancoBean.remove(detalle);

            if (listaProgramacionSessionBean != null) {
                if (!listaProgramacionSessionBean.isEmpty()) {
                    for (ProgramacionBean lista : listaProgramacionSessionBean) {
                        if (detalle.getConceptoBean().getIdConcepto() != null
                                && lista.getConceptoUniNegBean().getConceptoBean().getIdConcepto().equals(detalle.getConceptoBean().getIdConcepto())
                                && lista.getIdProgramacion().equals(detalle.getProgramacionBean().getIdProgramacion())) {
                            listaProgramacionSessionBean.remove(lista);
                            break;
                        }
                    }
                    validarDescuentosTalleresFase1(null);
                }
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarPagoBanco() {
        try {
            this.flgDisableGenCod = Boolean.FALSE;
            this.flgGenCod = Boolean.FALSE;
            personaBean = new PersonaBean();
            listaProgramacionSessionBean = new ArrayList<>();
            this.dscto = new BigDecimal(0.0);
            this.total = new BigDecimal(0);
//            cargarDatosSinDiscente();
            obtenerPersonaPorUsu();
//            DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("frmSerGen:tblSerGen");
//            if (!dataTable.getFilters().isEmpty()) {
//                dataTable.reset();
//            }
            setFlgGenCod(Boolean.TRUE);
            generarCodigoPersona();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarArqueoPagoBanco() {
        try {
            listaArqueoPagoBcoRepBean = new ArrayList<>();
            listaDetArqueoPagoBcoRepBean = new ArrayList<>();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void imprimirPdfPorDetalle(ArqueoPagoBcoRepBean arqueo) {
        ServletOutputStream out = null;
        try {
            String usuario = arqueo.getCreaPor();
            String cant = arqueo.getCant();
            Date fecha = arqueo.getFecha();
            String monto = arqueo.getTotalRecaudado();
            Integer flgMonto = 1;

            PagoBancoService pagoBancoService = BeanFactory.getPagoBancoService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/repDetPagoTalleresBanco.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            listaDetArqueoPagoBcoRepBean = new ArrayList<>();

            listaDetArqueoPagoBcoRepBean = pagoBancoService.obtenerDetArqPagoBanco(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(),
                    usuario, cant, monto, fecha, flgMonto);

            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaDetArqueoPagoBcoRepBean);
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

    public void buscarPersona() {
        try {
            System.out.println("buscarPersona()");
            listaPersonaBean = new ArrayList<>();
            PersonaService personaService = BeanFactory.getPersonaService();
            personaFiltroBean.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());

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
            Integer flg = 0;
//            System.out.println(flgSoloEst);
//            if (flgSoloEst.equals(Boolean.TRUE)) {
//                flg = 1;
//            } else {
//                flg = 0;
//            }
            personaFiltroBean.setFiltro(flg);
            listaPersonaBean = personaService.SP_obtenerPersonaPorFiltro(personaFiltroBean);

            if (personaFiltroBean.getIdPersona() == null && personaFiltroBean.getNombreFiltro() == null) {
                listaPersonaBean = personaService.obtenerTop10Persona(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            }

            personaBean = new PersonaBean();
            personaBean.setColl(false);
            if (listaPersonaBean.isEmpty()) {
                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                personaBean.setColl(true);
                listaPersonaBean = new ArrayList<>();
            } else {
                System.out.println("size " + listaPersonaBean.size());
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    public void buscarTalleres() {
        try {  
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            listaConceptoUniNegBean = new ArrayList<>();
            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoConProgramacionPorFiltro(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), strDscto,
                    nombreConcepto);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String autenticar() {
        String pagina = null;
        try {
            ImpresoraService impresoraService = BeanFactory.getImpresoraService();
            impresoraBean = impresoraService.obtenerPorNombre(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), MaristaConstantes.IMPRESORA_WEB);
            if (impresoraBean != null) {
                setterarCodigos();
                obtenerPersonaPorUsu();
                System.out.println("IMPRESORA WEB");
                String rutaCajero = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                        getRequest()).getServletContext().getRealPath("/mantenimientos/mantCobranzaTalleres.xhtml");
                new MaristaUtils().sesionColocarObjeto("ruta_cajero", rutaCajero);
                pagina = "toRoot";

            } else {
                FacesContext fc = FacesContext.getCurrentInstance();
                ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
                nav.performNavigation("logOut");
                new MensajePrime().addErrorMessage(MensajesBackEnd.getValueOfKey("errorCajero", null));
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
            pagina = null;
        }
        return pagina;
    }

    public void imprimirFormatoPdf() {
        ServletOutputStream out = null;
        try {
            System.out.println("llegué");

            //foramto=1 -> formato de caja
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
//                    getRequest()).getServletContext().getRealPath("/reportes/reportTalleresBco.jasper");
                    getRequest()).getServletContext().getRealPath("/reportes/reporteTalleresWeb.jasper");

            System.out.println(archivoJasper);
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<Integer> listaIds = new ArrayList<>();
            if (!listaDetPagoBancoBean.isEmpty()) {
                for (PagoBancoBean list : listaDetPagoBancoBean) {
                    listaIds.add(list.getIdPagoBanco());
                }
            } else {
                System.out.println("lista vacia");
            }
            for (Integer i : listaIds) {
                System.out.println("id :..." + i);
            }
            List<TalleresWebRepBean> listaPagoTalleresWeb = new ArrayList<>();
            PagoBancoService pagoBancoService = BeanFactory.getPagoBancoService();
//            listaPagoBanco = pagoBancoService.obtenerPagoBancoFor(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaIds);
            listaPagoTalleresWeb =pagoBancoService.obtenerPagoTalleresWeb(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaIds);
            if(!listaPagoTalleresWeb.isEmpty()){
                List<TalleresWebRepBean> listaDetalle = new ArrayList<>();
                listaDetalle = pagoBancoService.obtenerPagoTalleresWebDetalle(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaIds);
                listaPagoTalleresWeb.get(0).setListaDetalle(listaDetalle);
            }
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaPagoTalleresWeb);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
//                response.setContentType("application/pdf");
//                response.setContentLength(bytes.length);
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
    //metodos getter and setter

    public TipoDiscenteBean getTipoDiscenteBean() {
        if (tipoDiscenteBean == null) {
            tipoDiscenteBean = new TipoDiscenteBean();
        }
        return tipoDiscenteBean;
    }

    public void setTipoDiscenteBean(TipoDiscenteBean tipoDiscenteBean) {
        this.tipoDiscenteBean = tipoDiscenteBean;
    }

    public Boolean getValAdmTodos() {
        return valAdmTodos;
    }

    public void setValAdmTodos(Boolean valAdmTodos) {
        this.valAdmTodos = valAdmTodos;
    }

    public Object getStrDscto() {
        return strDscto;
    }

    public void setStrDscto(Object strDscto) {
        this.strDscto = strDscto;
    }

    public PagoBancoBean getPagoBancoBean() {
        if (pagoBancoBean == null) {
            pagoBancoBean = new PagoBancoBean();
        }
        return pagoBancoBean;
    }

    public void setPagoBancoBean(PagoBancoBean pagoBancoBean) {
        this.pagoBancoBean = pagoBancoBean;
    }

    public List<PagoBancoBean> getListaDetPagoBancoBean() {
        if (listaDetPagoBancoBean == null) {
            listaDetPagoBancoBean = new ArrayList<>();
        }
        return listaDetPagoBancoBean;
    }

    public void setListaDetPagoBancoBean(List<PagoBancoBean> listaDetPagoBancoBean) {
        this.listaDetPagoBancoBean = listaDetPagoBancoBean;
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

    public List<PersonaBean> getListaPersonaBean() {
        if (listaPersonaBean == null) {
            listaPersonaBean = new ArrayList<>();
        }
        return listaPersonaBean;
    }

    public void setListaPersonaBean(List<PersonaBean> listaPersonaBean) {
        this.listaPersonaBean = listaPersonaBean;
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

    public List<CodigoBean> getListaTipoDoc() {
        if (listaTipoDoc == null) {
            listaTipoDoc = new ArrayList<>();
        }
        return listaTipoDoc;
    }

    public void setListaTipoDoc(List<CodigoBean> listaTipoDoc) {
        this.listaTipoDoc = listaTipoDoc;
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

    public List<CodigoBean> getListaMoneda() {
        if (listaMoneda == null) {
            listaMoneda = new ArrayList<>();;
        }
        return listaMoneda;
    }

    public void setListaMoneda(List<CodigoBean> listaMoneda) {
        this.listaMoneda = listaMoneda;
    }

    public List<CodigoBean> getListaTipoEstadoPagoBco() {
        if (listaTipoEstadoPagoBco == null) {
            listaTipoEstadoPagoBco = new ArrayList<>();
        }
        return listaTipoEstadoPagoBco;
    }

    public void setListaTipoEstadoPagoBco(List<CodigoBean> listaTipoEstadoPagoBco) {
        this.listaTipoEstadoPagoBco = listaTipoEstadoPagoBco;
    }

    public List<CodigoBean> getListaTipoLugarPago() {
        if (listaTipoLugarPago == null) {
            listaTipoLugarPago = new ArrayList<>();
        }
        return listaTipoLugarPago;
    }

    public void setListaTipoLugarPago(List<CodigoBean> listaTipoLugarPago) {
        this.listaTipoLugarPago = listaTipoLugarPago;
    }

    public List<ProgramacionBean> getListaProgramacionBean() {
        if (listaProgramacionBean == null) {
            listaProgramacionBean = new ArrayList<>();
        }
        return listaProgramacionBean;
    }

    public void setListaProgramacionBean(List<ProgramacionBean> listaProgramacionBean) {
        this.listaProgramacionBean = listaProgramacionBean;
    }

    public List<ProgramacionBean> getListaProgramacionSessionBean() {
        if (listaProgramacionSessionBean == null) {
            listaProgramacionSessionBean = new ArrayList<>();
        }
        return listaProgramacionSessionBean;
    }

    public void setListaProgramacionSessionBean(List<ProgramacionBean> listaProgramacionSessionBean) {
        this.listaProgramacionSessionBean = listaProgramacionSessionBean;
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

    public List<ProgramacionDsctoBean> getListaProgramacionDsctoBean() {
        if (listaProgramacionDsctoBean == null) {
            listaProgramacionDsctoBean = new ArrayList<>();
        }
        return listaProgramacionDsctoBean;
    }

    public void setListaProgramacionDsctoBean(List<ProgramacionDsctoBean> listaProgramacionDsctoBean) {
        this.listaProgramacionDsctoBean = listaProgramacionDsctoBean;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public BigDecimal getDscto() {
        return dscto;
    }

    public void setDscto(BigDecimal dscto) {
        this.dscto = dscto;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
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

    public Boolean getFlgPerfilCajero() {
        return flgPerfilCajero;
    }

    public void setFlgPerfilCajero(Boolean flgPerfilCajero) {
        this.flgPerfilCajero = flgPerfilCajero;
    }

    public Boolean getFlgGenCod() {
        return flgGenCod;
    }

    public void setFlgGenCod(Boolean flgGenCod) {
        this.flgGenCod = flgGenCod;
    }

    public Boolean getFlgDisableGenCod() {
        return flgDisableGenCod;
    }

    public void setFlgDisableGenCod(Boolean flgDisableGenCod) {
        this.flgDisableGenCod = flgDisableGenCod;
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

    public List<ArqueoPagoBcoRepBean> getListaArqueoPagoBcoRepBean() {
        if (listaArqueoPagoBcoRepBean == null) {
            listaArqueoPagoBcoRepBean = new ArrayList<>();
        }
        return listaArqueoPagoBcoRepBean;
    }

    public void setListaArqueoPagoBcoRepBean(List<ArqueoPagoBcoRepBean> listaArqueoPagoBcoRepBean) {
        this.listaArqueoPagoBcoRepBean = listaArqueoPagoBcoRepBean;
    }

    public List<DetArqueoPagoBcoRepBean> getListaDetArqueoPagoBcoRepBean() {
        if (listaDetArqueoPagoBcoRepBean == null) {
            listaDetArqueoPagoBcoRepBean = new ArrayList<>();
        }
        return listaDetArqueoPagoBcoRepBean;
    }

    public void setListaDetArqueoPagoBcoRepBean(List<DetArqueoPagoBcoRepBean> listaDetArqueoPagoBcoRepBean) {
        this.listaDetArqueoPagoBcoRepBean = listaDetArqueoPagoBcoRepBean;
    }
 
    public String getNombreConcepto() {
        return nombreConcepto;
    }

    public void setNombreConcepto(String nombreConcepto) {
        this.nombreConcepto = nombreConcepto;
    }

    public BigDecimal getDsctoAyuda() {
        return dsctoAyuda;
    }

    public void setDsctoAyuda(BigDecimal dsctoAyuda) {
        this.dsctoAyuda = dsctoAyuda;
    }

    public Double getTotalDouble() {
        return totalDouble;
    }

    public void setTotalDouble(Double totalDouble) {
        this.totalDouble = totalDouble;
    }

}
