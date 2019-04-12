package pe.marista.sigma.managedBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.ConceptoBean;
import pe.marista.sigma.bean.Contenedor;
import pe.marista.sigma.bean.CronogramaPagoBean;
import pe.marista.sigma.bean.CuentaBancoBean;
import pe.marista.sigma.bean.CuentasPorCobrarBean;
import pe.marista.sigma.bean.EntidadBean;
import pe.marista.sigma.bean.MesBean;
import pe.marista.sigma.bean.ProcesoBancoBean;
import pe.marista.sigma.bean.ProcesoEnvioBean;
import pe.marista.sigma.bean.ProcesoFilesBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.TipoConceptoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.ConceptoService;
import pe.marista.sigma.service.CuentaBancoService;
import pe.marista.sigma.service.EntidadService;
import pe.marista.sigma.service.ProcesoBancoService;
import pe.marista.sigma.service.ProcesoEnvioService;
import pe.marista.sigma.service.ProcesoFilesService;
import pe.marista.sigma.service.ProcesoFinalService;
import pe.marista.sigma.service.ProcesoRecuperacionService;
import pe.marista.sigma.service.ReporteRechazoService;
import pe.marista.sigma.service.TipoConceptoService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

public class ProcesoBancoNewMB extends BaseMB implements Serializable {

    @PostConstruct
    public void ProcesoBancoNewMB() {
        try {
            usuarioSessionBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ponerProceso();
            GregorianCalendar fechaActual = new GregorianCalendar();
            getProcesoBancoFiltroBean().setFechaInicio(fechaActual.getTime());
            getProcesoBancoFiltroBean().setFechaFin(fechaActual.getTime());

            getCuentaFiltroBean();
            getCuentaFiltroBean().setFechaInicio(fechaActual.getTime());
            getCuentaFiltroBean().setFechaFin(fechaActual.getTime());

            getProcesoBanco();
            getProcesoBanco().setFecha(fechaActual.getTime());
            getProcesoBanco().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            EntidadService entidadService = BeanFactory.getEntidadService();
            getListaEntidad();
            listaEntidad = entidadService.obtenerFlgFinanciero(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getCronogramaPagoBean();
            getCronogramaPagoBean().setAnio(Integer.parseInt(MaristaConstantes.Anios_Banco));
            getListaMesAll();
            obtenerListaMeses();
            TipoConceptoService tipoConceptoService = BeanFactory.getTipoConceptoService();
            getListaTipoConceptoBean();
            listaTipoConceptoBean = tipoConceptoService.obtenerTipoConceptoCurso();
            if (usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SANJOC)) {
                setFlgUniNeg(true);
            } else {
                setFlgUniNeg(false);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    /* ====================================================================== */
    /* CLASES MODELO */
    private ProcesoBancoBean procesoBancoFiltroBean;
    private ProcesoBancoBean procesoBancoBean;
    private UsuarioBean usuarioSessionBean;
    private ProcesoBancoBean procesoBanco;
    private TipoConceptoBean tipoConceptoBean;
    private CuentasPorCobrarBean cuentaFiltroBean;
    private CronogramaPagoBean cronogramaPagoBean;

    /* LISTAS */
    private Map<String, Integer> listaProcesos;
    private List<ProcesoBancoBean> listaProcesoBancoFiltroBean;
    private List<Contenedor> listaProEnvios;
    private List<TipoConceptoBean> listaTipoConceptoBean;
    private List<CuentaBancoBean> listaCuentaBanco;
    private List<EntidadBean> listaEntidadBean;
    private List<EntidadBean> listaEntidad;
    private List<ConceptoBean> listaconceptoBean;
    private List<Contenedor> listaContenedorEnvio;
    private List<Contenedor> listaContenedorEnvioCabecera;
    private List<Contenedor> listaContenedorEnvioIntermedio;
    private List<MesBean> listaMesAll;

    /* VARIABLES DE AYUDA */
    private Integer filas = 0;
    private Integer filasCab = 0;
    private Integer filasInt = 0;

    private Integer idProceso = 0;
    private String rucEntidad = "";
    private StreamedContent content;
    private Boolean proView;
    private Boolean proView2;
    private Integer selEnvio;
    private Date fechaRecup;
    private boolean mostrarBotonS = true;
    private Boolean disableBotonGuardar = false;
    private Boolean disableBotonDescarga = true;
    private boolean flgUniNeg;
    private String tipoRegistro;
    private Integer tipoEnvio;
    private Integer var = 0;
    private Integer fail = 0;
    private Integer idFile;
    private Integer flgEstado;
    private Boolean disabledExporter = false;
    private String nombreFile;

    /* ====================================================================== */
    /* METODOS GET Y SET */
    public ProcesoBancoBean getProcesoBancoFiltroBean() {
        if (procesoBancoFiltroBean == null) {
            procesoBancoFiltroBean = new ProcesoBancoBean();
        }
        return procesoBancoFiltroBean;
    }

    public void setProcesoBancoFiltroBean(ProcesoBancoBean procesoBancoFiltroBean) {
        this.procesoBancoFiltroBean = procesoBancoFiltroBean;
    }

    public Map<String, Integer> getListaProcesos() {
        if (listaProcesos == null) {
            listaProcesos = new HashMap<>();
        }
        return listaProcesos;
    }

    public void setListaProcesos(Map<String, Integer> listaProcesos) {
        this.listaProcesos = listaProcesos;
    }

    public List<ProcesoBancoBean> getListaProcesoBancoFiltroBean() {
        if (listaProcesoBancoFiltroBean == null) {
            listaProcesoBancoFiltroBean = new ArrayList<>();
        }
        return listaProcesoBancoFiltroBean;
    }

    public void setListaProcesoBancoFiltroBean(List<ProcesoBancoBean> listaProcesoBancoFiltroBean) {
        this.listaProcesoBancoFiltroBean = listaProcesoBancoFiltroBean;
    }

    public ProcesoBancoBean getProcesoBancoBean() {
        if (procesoBancoBean == null) {
            procesoBancoBean = new ProcesoBancoBean();
        }
        return procesoBancoBean;
    }

    public void setProcesoBancoBean(ProcesoBancoBean procesoBancoBean) {
        this.procesoBancoBean = procesoBancoBean;
    }

    public List<Contenedor> getListaProEnvios() {
        if (listaProEnvios == null) {
            listaProEnvios = new ArrayList<>();
        }
        return listaProEnvios;
    }

    public void setListaProEnvios(List<Contenedor> listaProEnvios) {
        this.listaProEnvios = listaProEnvios;
    }

    public Integer getFilas() {
        return filas;
    }

    public void setFilas(Integer filas) {
        this.filas = filas;
    }

    public Integer getFilasCab() {
        return filasCab;
    }

    public void setFilasCab(Integer filasCab) {
        this.filasCab = filasCab;
    }

    public Integer getFilasInt() {
        return filasInt;
    }

    public void setFilasInt(Integer filasInt) {
        this.filasInt = filasInt;
    }

    public Integer getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(Integer idProceso) {
        this.idProceso = idProceso;
    }

    public String getRucEntidad() {
        return rucEntidad;
    }

    public void setRucEntidad(String rucEntidad) {
        this.rucEntidad = rucEntidad;
    }

    public StreamedContent getContent() {
        return content;
    }

    public void setContent(StreamedContent content) {
        this.content = content;
    }

    public UsuarioBean getUsuarioSessionBean() {
        if (usuarioSessionBean == null) {
            usuarioSessionBean = new UsuarioBean();
        }
        return usuarioSessionBean;
    }

    public void setUsuarioSessionBean(UsuarioBean usuarioSessionBean) {
        this.usuarioSessionBean = usuarioSessionBean;
    }

    public Boolean getProView() {
        return proView;
    }

    public void setProView(Boolean proView) {
        this.proView = proView;
    }

    public Boolean getProView2() {
        return proView2;
    }

    public void setProView2(Boolean proView2) {
        this.proView2 = proView2;
    }

    public ProcesoBancoBean getProcesoBanco() {
        if (procesoBanco == null) {
            procesoBanco = new ProcesoBancoBean();
        }
        return procesoBanco;
    }

    public void setProcesoBanco(ProcesoBancoBean procesoBanco) {
        this.procesoBanco = procesoBanco;
    }

    public Integer getSelEnvio() {
        return selEnvio;
    }

    public void setSelEnvio(Integer selEnvio) {
        this.selEnvio = selEnvio;
    }

    public Date getFechaRecup() {
        return fechaRecup;
    }

    public void setFechaRecup(Date fechaRecup) {
        this.fechaRecup = fechaRecup;
    }

    public boolean isMostrarBotonS() {
        return mostrarBotonS;
    }

    public void setMostrarBotonS(boolean mostrarBotonS) {
        this.mostrarBotonS = mostrarBotonS;
    }

    public Boolean getDisableBotonGuardar() {
        return disableBotonGuardar;
    }

    public void setDisableBotonGuardar(Boolean disableBotonGuardar) {
        this.disableBotonGuardar = disableBotonGuardar;
    }

    public boolean isFlgUniNeg() {
        return flgUniNeg;
    }

    public void setFlgUniNeg(boolean flgUniNeg) {
        this.flgUniNeg = flgUniNeg;
    }

    public String getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(String tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public Integer getTipoEnvio() {
        return tipoEnvio;
    }

    public void setTipoEnvio(Integer tipoEnvio) {
        this.tipoEnvio = tipoEnvio;
    }

    public Boolean getDisableBotonDescarga() {
        return disableBotonDescarga;
    }

    public void setDisableBotonDescarga(Boolean disableBotonDescarga) {
        this.disableBotonDescarga = disableBotonDescarga;
    }

    public TipoConceptoBean getTipoConceptoBean() {
        if (tipoConceptoBean == null) {
            tipoConceptoBean = new TipoConceptoBean();
        }
        return tipoConceptoBean;
    }

    public void setTipoConceptoBean(TipoConceptoBean tipoConceptoBean) {
        this.tipoConceptoBean = tipoConceptoBean;
    }

    public List<TipoConceptoBean> getListaTipoConceptoBean() {
        if (listaTipoConceptoBean == null) {
            listaTipoConceptoBean = new ArrayList<>();
        }
        return listaTipoConceptoBean;
    }

    public void setListaTipoConceptoBean(List<TipoConceptoBean> listaTipoConceptoBean) {
        this.listaTipoConceptoBean = listaTipoConceptoBean;
    }

    public List<CuentaBancoBean> getListaCuentaBanco() {
        if (listaCuentaBanco == null) {
            listaCuentaBanco = new ArrayList<>();
        }
        return listaCuentaBanco;
    }

    public void setListaCuentaBanco(List<CuentaBancoBean> listaCuentaBanco) {
        this.listaCuentaBanco = listaCuentaBanco;
    }

    public Integer getVar() {
        return var;
    }

    public void setVar(Integer var) {
        this.var = var;
    }

    public Integer getFail() {
        return fail;
    }

    public void setFail(Integer fail) {
        this.fail = fail;
    }

    public Integer getIdFile() {
        return idFile;
    }

    public void setIdFile(Integer idFile) {
        this.idFile = idFile;
    }

    public List<EntidadBean> getListaEntidadBean() {
        if (listaEntidadBean == null) {
            listaEntidadBean = new ArrayList<>();
        }
        return listaEntidadBean;
    }

    public void setListaEntidadBean(List<EntidadBean> listaEntidadBean) {
        this.listaEntidadBean = listaEntidadBean;
    }

    public List<EntidadBean> getListaEntidad() {
        if (listaEntidad == null) {
            listaEntidad = new ArrayList<>();
        }
        return listaEntidad;
    }

    public void setListaEntidad(List<EntidadBean> listaEntidad) {
        this.listaEntidad = listaEntidad;
    }

    public CuentasPorCobrarBean getCuentaFiltroBean() {
        if (cuentaFiltroBean == null) {
            cuentaFiltroBean = new CuentasPorCobrarBean();
        }
        return cuentaFiltroBean;
    }

    public void setCuentaFiltroBean(CuentasPorCobrarBean cuentaFiltroBean) {
        this.cuentaFiltroBean = cuentaFiltroBean;
    }

    public List<ConceptoBean> getListaconceptoBean() {
        if (listaconceptoBean == null) {
            listaconceptoBean = new ArrayList<>();
        }
        return listaconceptoBean;
    }

    public void setListaconceptoBean(List<ConceptoBean> listaconceptoBean) {
        this.listaconceptoBean = listaconceptoBean;
    }

    public List<Contenedor> getListaContenedorEnvio() {
        if (listaContenedorEnvio == null) {
            listaContenedorEnvio = new ArrayList<>();
        }
        return listaContenedorEnvio;
    }

    public void setListaContenedorEnvio(List<Contenedor> listaContenedorEnvio) {
        this.listaContenedorEnvio = listaContenedorEnvio;
    }

    public List<Contenedor> getListaContenedorEnvioCabecera() {
        if (listaContenedorEnvioCabecera == null) {
            listaContenedorEnvioCabecera = new ArrayList<>();
        }
        return listaContenedorEnvioCabecera;
    }

    public void setListaContenedorEnvioCabecera(List<Contenedor> listaContenedorEnvioCabecera) {
        this.listaContenedorEnvioCabecera = listaContenedorEnvioCabecera;
    }

    public List<Contenedor> getListaContenedorEnvioIntermedio() {
        if (listaContenedorEnvioIntermedio == null) {
            listaContenedorEnvioIntermedio = new ArrayList<>();
        }
        return listaContenedorEnvioIntermedio;
    }

    public void setListaContenedorEnvioIntermedio(List<Contenedor> listaContenedorEnvioIntermedio) {
        this.listaContenedorEnvioIntermedio = listaContenedorEnvioIntermedio;
    }

    public Integer getFlgEstado() {
        return flgEstado;
    }

    public void setFlgEstado(Integer flgEstado) {
        this.flgEstado = flgEstado;
    }

    public Boolean getDisabledExporter() {
        return disabledExporter;
    }

    public void setDisabledExporter(Boolean disabledExporter) {
        this.disabledExporter = disabledExporter;
    }

    public CronogramaPagoBean getCronogramaPagoBean() {
        if (cronogramaPagoBean == null) {
            cronogramaPagoBean = new CronogramaPagoBean();
        }
        return cronogramaPagoBean;
    }

    public void setCronogramaPagoBean(CronogramaPagoBean cronogramaPagoBean) {
        this.cronogramaPagoBean = cronogramaPagoBean;
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

    public String getNombreFile() {
        return nombreFile;
    }

    public void setNombreFile(String nombreFile) {
        this.nombreFile = nombreFile;
    }

    /* METODOS DE CLASE */
    public void ponerProceso() {
        try {
            listaProcesos = new LinkedHashMap<>();
            listaProcesos.put("Envio", 1);
            listaProcesos.put("Recepcion", 0);
            listaProcesos = Collections.unmodifiableMap(listaProcesos);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerFiltroProceso() {
        try {
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            procesoBancoFiltroBean.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (procesoBancoFiltroBean.getFlgProceso() != null) {
                procesoBancoFiltroBean.setFlgProceso(procesoBancoFiltroBean.getFlgProceso());
            }
            if (procesoBancoFiltroBean.getNombre() != null) {
                procesoBancoFiltroBean.setNombre(procesoBancoFiltroBean.getNombre());
            }
            if (procesoBancoFiltroBean.getEntidadBean().getRuc() != null) {
                procesoBancoFiltroBean.getEntidadBean().setRuc(procesoBancoFiltroBean.getEntidadBean().getRuc());
            }
            if (procesoBancoFiltroBean.getAnio() != null) {
                procesoBancoFiltroBean.setAnio(procesoBancoFiltroBean.getAnio());
            }
            if (procesoBancoFiltroBean.getMes() != null) {
                procesoBancoFiltroBean.setMes(procesoBancoFiltroBean.getMes());
            }
            if (procesoBancoFiltroBean.getDia() != null) {
                procesoBancoFiltroBean.setDia(procesoBancoFiltroBean.getDia());
            }
            if (procesoBancoFiltroBean.getFechaInicio() != null) {
                procesoBancoFiltroBean.setFechaInicio(procesoBancoFiltroBean.getFechaInicio());
            }
            if (procesoBancoFiltroBean.getFechaFin() != null) {
                procesoBancoFiltroBean.setFechaFin(procesoBancoFiltroBean.getFechaFin());
            }
            listaProcesoBancoFiltroBean = procesoBancoService.filtrarProceso(procesoBancoFiltroBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarFiltroBanco() {
        try {
            procesoBancoFiltroBean = new ProcesoBancoBean();
            listaProcesoBancoFiltroBean = new ArrayList<>();
            procesoBancoBean = new ProcesoBancoBean();
            listaProEnvios = new ArrayList<>();
            GregorianCalendar fechaActual = new GregorianCalendar();
            getProcesoBancoFiltroBean().setFechaInicio(fechaActual.getTime());
            getProcesoBancoFiltroBean().setFechaFin(fechaActual.getTime());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerProcesoEnvioPorId2(Object object) {
        try {
            procesoBancoBean = (ProcesoBancoBean) object;
            Integer flgProceso = 0;
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            procesoBancoBean.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            procesoBancoService.obtenerPorId(procesoBancoBean.getIdProcesoBanco());

            ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
            ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
            if (procesoBancoBean.getFlgProceso().equals(1)) {
                listaProEnvios = procesoFinalService.execProListaBanco(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), MaristaConstantes.FileDetalle, 3, 2);
                filas = procesoFilesService.obtenerNumFiles(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 2, MaristaConstantes.FileDetalle, procesoBancoBean.getRuc());
                filasCab = procesoFilesService.obtenerNumFiles(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 2, MaristaConstantes.FileCabecera, procesoBancoBean.getRuc());
                filasInt = procesoFilesService.obtenerNumFiles(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 2, MaristaConstantes.FileIntermedio, procesoBancoBean.getRuc());
            } else {
                if (procesoBancoBean.getFlgProceso().equals(0)) {
                    listaProEnvios = procesoFinalService.execProListaBanco(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), MaristaConstantes.FileDetalle, 3, 1);
                    filas = procesoFilesService.obtenerNumFiles(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 1, MaristaConstantes.FileDetalle, procesoBancoBean.getRuc());
                    filasCab = procesoFilesService.obtenerNumFiles(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 1, MaristaConstantes.FileCabecera, procesoBancoBean.getRuc());
                    filasInt = procesoFilesService.obtenerNumFiles(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 1, MaristaConstantes.FileIntermedio, procesoBancoBean.getRuc());
                }
            }
            System.out.println("elementos: " + filas + " / " + filasCab + " / " + filasInt);
            idProceso = procesoBancoBean.getIdProcesoBanco();
            rucEntidad = procesoBancoBean.getRuc();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerProcesoEnvioPorId(Object object) {
        try {
            procesoBancoBean = (ProcesoBancoBean) object;
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            procesoBancoService.obtenerPorId(procesoBancoBean.getIdProcesoBanco());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarProcesoBancoReg() {
        try {
            procesoBanco = new ProcesoBancoBean();
//            setTipoRegistro(null);
            setSelEnvio(null);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void mostraBoton2() {
        try {
            mostrarBotonS = true;
//            procesoBanco = new ProcesoBancoBean();
            GregorianCalendar fechaActual = new GregorianCalendar();
            getProcesoBanco().setFecha(fechaActual.getTime());
            setFechaRecup(fechaActual.getTime());
            String nombre = obtenerNombreArchivo(procesoBanco.getUnidadNegocioBean().getUniNeg(), procesoBanco.getFecha(), 1);
            getProcesoBanco().setNombre(nombre);
            System.out.println(">>>" + procesoBanco.getFecha());
            System.out.println(">>>" + procesoBanco.getNombre());
            setDisableBotonGuardar(false);
            setProView(true);
            setProView2(false);
            limpiarProcesoBancoReg();
            System.out.println(">>>1" + proView);
            System.out.println(">>>2" + proView2);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void mostrarBoton3() {
        try {
//            procesoBanco = new ProcesoBancoBean();
            GregorianCalendar fechaActual = new GregorianCalendar();
            getProcesoBanco().setFecha(fechaActual.getTime());
            setFechaRecup(fechaActual.getTime());
            String nombre = obtenerNombreArchivo(procesoBanco.getUnidadNegocioBean().getUniNeg(), procesoBanco.getFecha(), 0);
            getProcesoBanco().setNombre(nombre);
            System.out.println(">>>" + procesoBanco.getFecha());
            System.out.println(">>>" + procesoBanco.getNombre());
            setDisableBotonGuardar(false);
            setProView2(true);
            setProView(false);
            limpiarProcesoBancoReg();
            System.out.println(">>>1" + proView);
            System.out.println(">>>2" + proView2);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cargarDatosProceso(Integer proceso) {
        try {
            String nombre = "";
            if (proceso.equals(1)) {
                nombre = obtenerNombreArchivo(procesoBanco.getUnidadNegocioBean().getUniNeg(), procesoBanco.getFecha(), 1);
            } else if (proceso.equals(0)) {
                nombre = obtenerNombreArchivo(procesoBanco.getUnidadNegocioBean().getUniNeg(), procesoBanco.getFecha(), 0);
            }
            procesoBanco.setNombre(nombre);
            System.out.println(">>>>" + nombre);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String eliminarMasivo() {
        String pagina = null;
        try {
            if (pagina == null) {
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                Integer codBanco;
                ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
                listaProcesoBancoFiltroBean = procesoBancoService.obtenerPorId(procesoBancoBean.getIdProcesoBanco());
                procesoBancoService.eliminarProcesoBancoMas(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getIdProcesoBanco());
                if (procesoBancoBean.getFlgProceso().equals(0)) {
                    procesoBancoService.modificarBancoCuenta(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getIdProcesoBanco(), 0);
                } else if (procesoBancoBean.getFlgProceso().equals(1)) {
                    procesoBancoService.modificarBancoCuenta(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getIdProcesoBanco(), 1);
                }
                listaProEnvios = new ArrayList<>();
//                procesoBancoFiltroBean = new ProcesoBancoBean();
                procesoBancoBean = new ProcesoBancoBean();
                listaProEnvios = new ArrayList<>();
                obtenerFiltroProceso();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void modificarCuota() {
        try {
            ProcesoEnvioBean procesoEnvioBean = new ProcesoEnvioBean();
            procesoEnvioBean.setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            procesoEnvioBean.setCuota(getProcesoBanco().getCuota());
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            procesoEnvioService.modificarCuota(procesoEnvioBean);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void insertarProcesoBancoEnvio() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            setTipoEnvio(procesoBanco.getTipoEnvio());
            nombreFile = procesoBanco.getNombre();
            System.out.println(">>>" + tipoEnvio);
            System.out.println(">>>" + nombreFile);

            procesoBanco.setFlgProceso(1);
            procesoBanco.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            procesoBanco.setCreaPor(beanUsuarioSesion.getUsuario());
            procesoBanco.setTipoArchivo("C");
            procesoBancoService.insertarProcesoBanco(procesoBanco);
            RequestContext.getCurrentInstance().addCallbackParam("operacioncorrecta", true);
            limpiarProcesoBancoReg();
            idProceso = procesoBancoService.obtenerIdProcesoBancoMax(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 1);
            disableBotonDescarga = false;
            disableBotonGuardar = true;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerVistaPrevia() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            System.out.println(">>>>" + tipoEnvio);
            if (tipoEnvio.equals(1)) {
                TipoConceptoService tipoConceptoService = BeanFactory.getTipoConceptoService();
                listaTipoConceptoBean = tipoConceptoService.obtenerPorTipoProcesoBanco();
                RequestContext.getCurrentInstance().addCallbackParam("cuenta", true);
            } else {
                if (tipoEnvio.equals(0)) {
                    TipoConceptoService tipoConceptoService = BeanFactory.getTipoConceptoService();
                    listaTipoConceptoBean = tipoConceptoService.obtenerTipoConceptoCurso();
                    RequestContext.getCurrentInstance().addCallbackParam("cuentaCur", true);
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerCuentaBanco(Object object) {
        try {
            CuentaBancoBean cuentaBancoBean = (CuentaBancoBean) object;
            procesoBanco.setCodUniNeg(cuentaBancoBean.getCodUniNeg());
            procesoBanco.setNumCuenta(cuentaBancoBean.getNumCuenta());
            rucEntidad = cuentaBancoBean.getEntidadBancoBean().getRuc();
            System.out.println("ruc: " + rucEntidad);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cargarRuc() {
        try {
            Integer moneda = 0;
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            CuentaBancoService cuentaBancoService = BeanFactory.getCuentaBancoService();
            if (procesoBanco.getMoneda() == null) {
                procesoBanco.setMoneda(false);
                moneda = MaristaConstantes.COD_SOLES;
            } else {
                if (procesoBanco.getMoneda() != null) {
                    if (procesoBanco.getMoneda()) {
                        moneda = MaristaConstantes.COD_DOLARES;
                    } else {
                        if (!procesoBanco.getMoneda()) {
                            moneda = MaristaConstantes.COD_SOLES;
                        }
                    }
                }
            }
            listaCuentaBanco = cuentaBancoService.obtenerPorRuc(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBanco.getRuc(), moneda);
            if (!listaCuentaBanco.isEmpty()) {
                RequestContext.getCurrentInstance().addCallbackParam("openModalCodUniNeg", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerFileId() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
            Integer id = 0;
            id = procesoFinalService.obtenerMaxIdFile(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBanco.getRuc());
            idFile = id + 1;
            setIdFile(idFile);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public static String secciona(String cadena, Integer inicio, Integer fin) {
        String cadenaRpta = null;
        cadenaRpta = cadena.substring((inicio - 1), fin);
        System.out.println(">>>>> " + cadenaRpta);
        return cadenaRpta;
    }

    public void limpiarRecuperacion() {
        try {
            procesoBanco = new ProcesoBancoBean();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public Integer generarRecuperacion(String nombre) {
        Integer cabecera = 0;
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            ProcesoRecuperacionService procesoRecuperacionService = BeanFactory.getProcesoRecuperacionService();
            Integer idProcesoBanco = 0, totalRegistros = 0, fileDetalle = 0;
            Float montoRecuperado;
            fileDetalle = MaristaConstantes.FileDetalle;
            idProcesoBanco = procesoBancoService.obtenerMaxIdProcesoBanco(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            montoRecuperado = procesoRecuperacionService.obtenerMontoTotal((idProcesoBanco + 1), fileDetalle, 1, procesoBanco.getRuc(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            totalRegistros = procesoRecuperacionService.obtenerTotalRegistros((idProcesoBanco + 1), procesoBanco.getRuc(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            //Insertando Registros
            procesoBanco.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            procesoBanco.setCodUniNeg(procesoBanco.getCodUniNeg());
            procesoBanco.setNumCuenta(procesoBanco.getNumCuenta());
            procesoBanco.setMoneda(procesoBanco.getMoneda());
            procesoBanco.setRuc(procesoBanco.getRuc());
            procesoBanco.setFecha(procesoBanco.getFecha());
            procesoBanco.setNombre(nombre);
            procesoBanco.setFlgProceso(0);
            procesoBanco.setTipoArchivo("C");
            procesoBanco.setRegEnv(totalRegistros);
            procesoBanco.setMontoRecup(montoRecuperado);
            procesoBanco.setCreaPor(beanUsuarioSesion.getUsuario());
            procesoBancoService.insertarProcesoBanco(procesoBanco);
            limpiarRecuperacion();
            cabecera = 1;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return cabecera;
    }

    public String obtenerNombreArchivo(String uniNeg, Date fecha, Integer tipoProceso) {
        String nombre = "";
        try {
            String fechaProceso = "";
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            ProcesoBancoBean procesoBancoBean = new ProcesoBancoBean();
            procesoBancoBean.getUnidadNegocioBean().setUniNeg(uniNeg);
            procesoBancoBean.setFecha(fecha);
            procesoBancoBean.setFlgProceso(tipoProceso);
            switch (uniNeg) {
                case MaristaConstantes.UNI_NEG_CHAMPS:
                    if (tipoProceso.equals(0)) {
                        fechaProceso = procesoBancoService.obtenerFechaProceso(procesoBancoBean);
                        nombre = "MP".concat(fechaProceso);
                    } else if (tipoProceso.equals(1)) {
                        fechaProceso = procesoBancoService.obtenerFechaProceso(procesoBancoBean);
                        nombre = "COB".concat(fechaProceso);
                    }
                    break;
                case MaristaConstantes.UNI_NEG_SANJOC:
                    if (tipoProceso.equals(0)) {
                        fechaProceso = procesoBancoService.obtenerFechaProceso(procesoBancoBean);
                        nombre = "P".concat(fechaProceso);
                    } else if (tipoProceso.equals(1)) {
                        fechaProceso = procesoBancoService.obtenerFechaProceso(procesoBancoBean);
                        nombre = fechaProceso;
                    }
                    break;
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return nombre;
    }

    public void obtenerFechaRecup() {
        try {
            setFechaRecup(procesoBanco.getFecha());
            String nombre = obtenerNombreArchivo(procesoBanco.getUnidadNegocioBean().getUniNeg(), procesoBanco.getFecha(), 0);
            getProcesoBanco().setNombre(nombre);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerPorTipo() {
        try {
            ConceptoService conceptoService = BeanFactory.getConceptoService();
            getListaconceptoBean();
            listaconceptoBean = conceptoService.obtenerPorTipo(getCuentaFiltroBean().getConceptoBean().getTipoConceptoBean());
            getCuentaFiltroBean().getConceptoBean().getTipoConceptoBean().setIdTipoConcepto(getCuentaFiltroBean().getConceptoBean().getTipoConceptoBean().getIdTipoConcepto());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarBusquedaEnvio() {
        try {
            cuentaFiltroBean = new CuentasPorCobrarBean();
            GregorianCalendar fechaActual = new GregorianCalendar();
            getCuentaFiltroBean().setFechaInicio(fechaActual.getTime());
            getCuentaFiltroBean().setFechaFin(fechaActual.getTime());
            listaContenedorEnvio = new ArrayList<>();
            listaContenedorEnvioCabecera = new ArrayList<>();
            listaContenedorEnvioIntermedio = new ArrayList<>();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void buscarEstudianteCuentas(Integer dato) {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            Boolean res = validarCampos(dato);
            ProcesoEnvioBean proEnvio = new ProcesoEnvioBean();
            proEnvio.setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            proEnvio.setIdProcesoBanco(idProceso);
            proEnvio.setRuc(rucEntidad);
            proEnvio.setCreaPor(beanUsuarioSesion.getUsuario());
            String fechaIni = "", fechaFin = "";
            if (res) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
            } else {
                if (dato.equals(1)) {
                    SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
                    if (!getCuentaFiltroBean().getEstudianteBean().getCodigo().equals("")
                            && getCuentaFiltroBean().getEstudianteBean().getCodigo() != null) {
                        getCuentaFiltroBean().getEstudianteBean().setCodigo(getCuentaFiltroBean().getEstudianteBean().getCodigo());
                        proEnvio.setCodigo(getCuentaFiltroBean().getEstudianteBean().getCodigo());
                    }
                    if (!getCuentaFiltroBean().getEstudianteBean().getIdEstudiante().equals("")
                            && getCuentaFiltroBean().getEstudianteBean().getIdEstudiante() != null) {
                        getCuentaFiltroBean().getEstudianteBean().setIdEstudiante(getCuentaFiltroBean().getEstudianteBean().getIdEstudiante());
                        proEnvio.setIdEstudiante(getCuentaFiltroBean().getEstudianteBean().getIdEstudiante());
                    }
                    if (!getCuentaFiltroBean().getEstudianteBean().getPersonaBean().getNombreCompleto().equals("")
                            && getCuentaFiltroBean().getEstudianteBean().getPersonaBean().getNombreCompleto() != null) {
                        getCuentaFiltroBean().getEstudianteBean().getPersonaBean().setNombreCompleto(getCuentaFiltroBean().getEstudianteBean().getPersonaBean().getNombreCompleto());
                        proEnvio.setNombres(getCuentaFiltroBean().getEstudianteBean().getPersonaBean().getNombreCompleto());
                    }
                    if (getCuentaFiltroBean().getConceptoBean().getTipoConceptoBean().getIdTipoConcepto() != null) {
                        getCuentaFiltroBean().getConceptoBean().getTipoConceptoBean().setIdTipoConcepto(getCuentaFiltroBean().getConceptoBean().getTipoConceptoBean().getIdTipoConcepto());
                        proEnvio.setIdTipoConcepto(getCuentaFiltroBean().getConceptoBean().getTipoConceptoBean().getIdTipoConcepto());
                    }
                    if (getCuentaFiltroBean().getConceptoBean().getIdConcepto() != null) {
                        getCuentaFiltroBean().getConceptoBean().setIdConcepto(getCuentaFiltroBean().getConceptoBean().getIdConcepto());
                        proEnvio.setIdConcepto(getCuentaFiltroBean().getConceptoBean().getIdConcepto());
                    } else {
                        proEnvio.setIdConcepto(0);
                    }
                    if (getCuentaFiltroBean().getFechaInicio() != null) {
                        getCuentaFiltroBean().setFechaInicio(getCuentaFiltroBean().getFechaInicio());
                        proEnvio.setFechaIni(getCuentaFiltroBean().getFechaInicio());
                    }
                    if (getCuentaFiltroBean().getFechaFin() != null) {
                        getCuentaFiltroBean().setFechaFin(getCuentaFiltroBean().getFechaFin());
                        proEnvio.setFechaFin(getCuentaFiltroBean().getFechaFin());
                    }
                    ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
                    ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();

                    listaContenedorEnvio = procesoEnvioService.execProEnvioOperacion(proEnvio);
                    listaContenedorEnvioCabecera = procesoFinalService.obtenerEnvioBancos(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), rucEntidad, idProceso, MaristaConstantes.FileCabecera, 1);
                    listaContenedorEnvioIntermedio = procesoFinalService.obtenerEnvioBancos(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), rucEntidad, idProceso, MaristaConstantes.FileIntermedio, 2);
                    setDisabledExporter(true);
                }
                if (dato.equals(2)) {
                    System.out.println(">>>> " + 2);
                    SimpleDateFormat dt1 = new SimpleDateFormat("yyyyy-dd-mm");
                    if (!getCuentaFiltroBean().getEstudianteBean().getCodigo().equals("")
                            && getCuentaFiltroBean().getEstudianteBean().getCodigo() != null) {
                        getCuentaFiltroBean().getEstudianteBean().setCodigo(getCuentaFiltroBean().getEstudianteBean().getCodigo());
                    }
                    if (!getCuentaFiltroBean().getEstudianteBean().getIdEstudiante().equals("")
                            && getCuentaFiltroBean().getEstudianteBean().getIdEstudiante() != null) {
                        getCuentaFiltroBean().getEstudianteBean().setIdEstudiante(getCuentaFiltroBean().getEstudianteBean().getIdEstudiante());
                    }
                    if (!getCuentaFiltroBean().getEstudianteBean().getPersonaBean().getNombreCompleto().equals("")
                            && getCuentaFiltroBean().getEstudianteBean().getPersonaBean().getNombreCompleto() != null) {
                        getCuentaFiltroBean().getEstudianteBean().getPersonaBean().setNombreCompleto(getCuentaFiltroBean().getEstudianteBean().getPersonaBean().getNombreCompleto());
                    }
                    if (getCuentaFiltroBean().getConceptoBean().getTipoConceptoBean().getIdTipoConcepto() != null) {
                        getCuentaFiltroBean().getConceptoBean().getTipoConceptoBean().setIdTipoConcepto(getCuentaFiltroBean().getConceptoBean().getTipoConceptoBean().getIdTipoConcepto());
                    }
                    if (getCuentaFiltroBean().getConceptoBean().getIdConcepto() != null) {
                        getCuentaFiltroBean().getConceptoBean().setIdConcepto(getCuentaFiltroBean().getConceptoBean().getIdConcepto());
                    }
                    if (getCuentaFiltroBean().getFechaInicio() != null) {
                        getCuentaFiltroBean().setFechaInicio(getCuentaFiltroBean().getFechaInicio());
                    }
                    if (getCuentaFiltroBean().getFechaFin() != null) {
                        getCuentaFiltroBean().setFechaFin(getCuentaFiltroBean().getFechaFin());
                    }
                    if (tipoRegistro != null) {
                        setTipoRegistro(getTipoRegistro());
                    }
                    if (flgEstado != null) {
                        setFlgEstado(getFlgEstado());
                    }
                    if (getCuentaFiltroBean().getMes() != null) {
                        getCuentaFiltroBean().setMes(getCuentaFiltroBean().getMes());
                    }

                    ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
                    ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
                    procesoEnvioService.execProEnvioPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), rucEntidad, idProceso, getCronogramaPagoBean().getAnio(), getCuentaFiltroBean().getEstudianteBean().getIdEstudiante(), getCuentaFiltroBean().getEstudianteBean().getCodigo(), getCuentaFiltroBean().getEstudianteBean().getPersonaBean().getNombreCompleto(), getCuentaFiltroBean().getConceptoBean().getTipoConceptoBean().getIdTipoConcepto(), getCuentaFiltroBean().getConceptoBean().getIdConcepto(), beanUsuarioSesion.getUsuario(), getTipoRegistro(), getFlgEstado(), getCuentaFiltroBean().getMes());
//                    procesoFinalService.execProEnvioProCab(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), rucEntidad, idProceso, beanUsuarioSesion.getUsuario(), fechaRecu, getTipoRegistro());
                    listaContenedorEnvioCabecera = procesoFinalService.obtenerEnvioBancos(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), rucEntidad, idProceso, MaristaConstantes.FileCabecera, 1);
                    listaContenedorEnvioIntermedio = procesoFinalService.obtenerEnvioBancos(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), rucEntidad, idProceso, MaristaConstantes.FileIntermedio, 2);
                    listaContenedorEnvio = procesoFinalService.obtenerEnvioBancos(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), rucEntidad, idProceso, MaristaConstantes.FileDetalle, 3);
                    setDisabledExporter(true);
                }
                ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
                filas = procesoFilesService.obtenerNumFiles(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 2, MaristaConstantes.FileDetalle, rucEntidad);
                filasCab = procesoFilesService.obtenerNumFiles(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 2, MaristaConstantes.FileCabecera, rucEntidad);
                filasInt = procesoFilesService.obtenerNumFiles(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 2, MaristaConstantes.FileIntermedio, rucEntidad);
                System.out.println(">>>" + listaContenedorEnvioCabecera.size());
                System.out.println(">>>" + listaContenedorEnvioIntermedio.size());
                System.out.println(">>>" + listaContenedorEnvio.size());
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public Boolean validarCampos(Integer dato) {
        Boolean res = false;
        try {
            if (dato.equals(2)) {
//                if (getCuentaFiltroBean().getFechaInicio() == null) {
//                    res = true;
//                }
//                if (getCuentaFiltroBean().getFechaVenc() == null) {
//                    res = true;
//                }
            } else {
                if (dato.equals(1)) {
                    if (getCuentaFiltroBean().getFechaInicio() == null) {
                        res = true;
                    }
                }
            }

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return res;
    }

    public void obtenerListaMeses() {
        try {
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
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cerrarFiltroEnvio() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
            procesoFinalService.modificarEnvioFiltro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), rucEntidad, idProceso, MaristaConstantes.Defecto_Importe);
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            listaProcesoBancoFiltroBean = procesoBancoService.obtenerPorIdBanco(idProceso, beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaContenedorEnvio = new ArrayList<>();
            listaContenedorEnvioCabecera = new ArrayList<>();
            listaContenedorEnvioIntermedio = new ArrayList<>();
            if (tipoEnvio.equals(1)) {
                RequestContext.getCurrentInstance().addCallbackParam("operacioncorrecta", true);
            } else if (tipoEnvio.equals(0)) {
                RequestContext.getCurrentInstance().addCallbackParam("operacioncorrectaOp", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cargarDatosProcesos() {
        try {
            GregorianCalendar fechaActual = new GregorianCalendar();
            getProcesoBanco();
            getProcesoBanco().setFecha(fechaActual.getTime());
            getProcesoBanco().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            disableBotonGuardar = false;
            disableBotonDescarga = true;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    /* METODOS DE ARCHIVO */
    public void obtenerFileRecuperacion(FileUploadEvent event) {
        try {
            InputStream miArchivo = null;
            UploadedFile file = event.getFile();
            miArchivo = file.getInputstream();
            System.out.println("nombre txt:");
            byte[] b = new byte[(int) file.getSize()];
            miArchivo.read(b);
            miArchivo.close();
            String resultado = new String(b);
            String[] arreglo = resultado.split("\n");
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();

            //Declarando Variables
            String ruc = procesoBanco.getRuc();

            //Obteniendo ubicaciones de los archivos
            CodigoService codigoService = BeanFactory.getCodigoService();
            CodigoBean codigoTipoFileCab = new CodigoBean();
            codigoTipoFileCab = codigoService.obtenerPorCodigo(new CodigoBean(20001, "Cabecera", new TipoCodigoBean(MaristaConstantes.file_Cabecera)));

            CodigoBean codigoTipoFileDet = new CodigoBean();
            codigoTipoFileDet = codigoService.obtenerPorCodigo(new CodigoBean(20002, "Detalle", new TipoCodigoBean(MaristaConstantes.file_Detalle)));

            List<ProcesoFilesBean> listaProcesoFilesBean = new ArrayList<>();
            List<ProcesoFilesBean> listaProcesoFilesBeanDeta = new ArrayList<>();
            ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
            listaProcesoFilesBean = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), ruc, 1, codigoTipoFileCab.getIdCodigo());//Archivo Vuelta
            listaProcesoFilesBeanDeta = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), ruc, 1, codigoTipoFileDet.getIdCodigo());//Archivo Vuelta

            List<String> listaCabeceraBanco = new ArrayList<>(); //Cabecera de Archivo
            List<String> listaDetalleBanco = new ArrayList<>(); //Detalle de Archivo

            List<List<String>> listaDetallesElementos = new ArrayList<>(); //DETALLE DE ELEMENTOS

            Integer cabecera = 0;
            Integer afterListCab = 0;
            Integer beforeListCab = 0;
            afterListCab = listaProcesoFilesBeanDeta.size();

            Integer idProcesoBanco = 0;
            idProcesoBanco = procesoBancoService.obtenerMaxIdProcesoBanco(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg()) + 1;
//            idProcesoBanco = procesoBancoService.obtenerIdProcesoBancoMax(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 0) + 1;
            ProcesoBancoBean bancoBean = new ProcesoBancoBean();

            String[][] rpta = new String[listaProcesoFilesBean.size()][arreglo.length];
            String[][] rptas = new String[listaProcesoFilesBeanDeta.size()][arreglo.length];

            // Insertando ProcesoBanco ==============================================================================================================================================//              
            if (beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)
                    || beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SANJOC)) {
                if (!listaProcesoFilesBean.isEmpty() || !listaProcesoFilesBeanDeta.isEmpty()) {
                    for (int i = 0; i < arreglo.length; i++) {
                        String fila = arreglo[i];
                        if (!listaProcesoFilesBeanDeta.isEmpty()) {
                            for (int k = 0; k < listaProcesoFilesBeanDeta.size(); k++) {
                                rptas[k][i] = secciona(fila, listaProcesoFilesBeanDeta.get(k).getPosicionIni(), listaProcesoFilesBeanDeta.get(k).getPosicionFin());
                            }
                        }
                    }
                    for (int i = 0; i < arreglo.length; i++) {
                        var++;
                        if (!listaProcesoFilesBeanDeta.isEmpty()) {
                            for (int k = 0; k < listaProcesoFilesBeanDeta.size(); k++) {
                                String filaD = rptas[k][i];
                                if (listaProcesoFilesBeanDeta.get(k).getTipoDato().getIdCodigo().equals(20103)) {
                                    String fec1 = filaD.substring(0, 4);
                                    String fec2 = filaD.substring(4, 6);
                                    String fec3 = filaD.substring(6, 8);
                                    String fecha = fec1 + "-" + fec2 + "-" + fec3;
                                    listaDetalleBanco.add(fecha);
                                } else {
                                    if (listaProcesoFilesBeanDeta.get(k).getTipoDato().getIdCodigo().equals(20105)) {
                                        if (beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)) {
                                            String money = filaD.substring(0, filaD.length() - 2);
                                            System.out.println(">>>>>" + money);
                                            listaDetalleBanco.add(money);
                                        } else {
                                            String dinero = filaD.substring(0, filaD.length() - 2);
                                            String dineroAfter = filaD.substring(filaD.length() - 2, filaD.length());
                                            System.out.println(dinero);
                                            System.out.println(">>>>>");
                                            System.out.println(dineroAfter);
                                            listaDetalleBanco.add(dinero.concat(".").concat(dineroAfter));
                                        }
                                    } else {
                                        listaDetalleBanco.add(filaD);
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SANLUI)) {
                if (!listaProcesoFilesBean.isEmpty() || !listaProcesoFilesBeanDeta.isEmpty()) {
                    for (int i = 0; i < arreglo.length; i++) {
                        String fila = arreglo[i];
                        if (i == 0 && !listaProcesoFilesBean.isEmpty()) {
                            for (int j = 0; j < listaProcesoFilesBean.size(); j++) {
                                rpta[j][0] = secciona(fila, listaProcesoFilesBean.get(j).getPosicionIni(), listaProcesoFilesBean.get(j).getPosicionFin());
                            }
                        } else if (i != 0 && !listaProcesoFilesBeanDeta.isEmpty()) {
                            for (int k = 0; k < listaProcesoFilesBeanDeta.size(); k++) {
                                rptas[k][i] = secciona(fila, listaProcesoFilesBeanDeta.get(k).getPosicionIni(), listaProcesoFilesBeanDeta.get(k).getPosicionFin());
                            }
                        }
                    }
                    for (int i = 0; i < arreglo.length; i++) {
                        var++;
                        if (i == 0 && !listaProcesoFilesBean.isEmpty()) {
                            for (int j = 0; j < listaProcesoFilesBean.size(); j++) {
                                String filaC = rpta[j][0];
                                listaCabeceraBanco.add(filaC);
                            }
                        } else {
                            if (i != 0 && !listaProcesoFilesBeanDeta.isEmpty()) {
                                for (int k = 0; k < listaProcesoFilesBeanDeta.size(); k++) {
                                    String filaD = rptas[k][i];
                                    if (listaProcesoFilesBeanDeta.get(k).getTipoDato().getIdCodigo().equals(20103)) {
                                        String fec1 = filaD.substring(0, 4);
                                        String fec2 = filaD.substring(4, 6);
                                        String fec3 = filaD.substring(6, 8);
                                        String fecha = fec1 + "-" + fec2 + "-" + fec3;
                                        listaDetalleBanco.add(fecha);
                                    } else {
                                        if (listaProcesoFilesBeanDeta.get(k).getTipoDato().getIdCodigo().equals(20105)) {
                                            if (beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)) {
                                                String money = filaD.substring(0, filaD.length() - 2);
                                                System.out.println(">>>>>" + money);
                                                listaDetalleBanco.add(money);
                                            } else {
                                                String dinero = filaD.substring(0, filaD.length() - 2);
                                                String dineroAfter = filaD.substring(filaD.length() - 2, filaD.length());
                                                System.out.println(dinero);
                                                System.out.println(">>>>>");
                                                System.out.println(dineroAfter);
                                                listaDetalleBanco.add(dinero.concat(".").concat(dineroAfter));
                                            }
                                        } else {
                                            listaDetalleBanco.add(filaD);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            //Numero de Columnas del Archivo
            Integer numFilas = 0;
            numFilas = procesoFilesService.obtenerNumFiles(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 1, MaristaConstantes.FileDetalle, ruc);

            List<String> listaDeta = new ArrayList<>();
            Integer a = 0;
            Integer c = 0;
            Integer d = 0;
            d = listaProcesoFilesBeanDeta.size();
            c = listaDetalleBanco.size() / listaProcesoFilesBeanDeta.size();
            for (int i = 0; i < c; i++) {
                listaDeta = listaDetalleBanco.subList(a, d);
                if (!listaDeta.isEmpty()) {
                    listaDetallesElementos.add(listaDeta);
                }
                a = a + numFilas;
                d = d + numFilas;
            }

            /* Ejecutando SP_Recuperacion */
            ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();

            procesoFinalService.execProcesoRecup(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBanco.getRuc(), listaProcesoFilesBeanDeta.size(), idProcesoBanco);
            System.out.println("banco => " + idProcesoBanco);
            Integer dato = 0;
            String rucBanco = "";
            rucBanco = procesoBanco.getRuc();
            for (int i = 0; i < listaDetallesElementos.size(); i++) {
                obtenerFileId();
                for (int j = 0; j < listaDetallesElementos.get(i).size(); j++) {
                    procesoFinalService.execProcesoRecupIns(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBanco.getRuc(), idProcesoBanco, listaDetallesElementos.get(i).get(j), (j + 1), idFile, beanUsuarioSesion.getUsuario());
                }
            }

            Integer posicion = 0;
            cabecera = generarRecuperacion(file.getFileName());

            /* Obteniendo Listas Actualizadas */
            listaProcesoBancoFiltroBean = procesoBancoService.obtenerPorId(idProcesoBanco);
            Object obj_error = "0";
            if (usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SANJOC)) {
//                obj_error = procesoFinalService.execProError(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), ruc, idProcesoBanco, beanUsuarioSesion.getUsuario());
                //            Object object = procesoFinalService.execProCtaCte(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), ruc, idProcesoBanco, beanUsuarioSesion.getUsuario());
                Object obj_genera = procesoFinalService.execProCtaCte1(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), ruc, idProcesoBanco, beanUsuarioSesion.getUsuario());
                Object obj_update = procesoFinalService.execProCtaCte2(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), ruc, idProcesoBanco, beanUsuarioSesion.getUsuario());

                System.out.println("objeto >>>> " + obj_genera);
                System.out.println("objeto >>>> " + obj_update);
                System.out.println("objeto >>>> " + obj_error);

                Integer proceso = 0;
                proceso = procesoBancoService.obtenerMaxIdProcesoBanco(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            procesoFinalService.execProErrores(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), proceso, beanUsuarioSesion.getUsuario(), 2);

                /* Grabando en el Asiento */
                procesoFinalService.execProAsiento(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), "MT_ProcesoRecup", idProcesoBanco, beanUsuarioSesion.getUsuario(), idProcesoBanco);
                CodigoBean bean = new CodigoBean();
                bean.setCodigo(MaristaConstantes.TIP_STA_CCH);
                bean.setTipoCodigoBean(new TipoCodigoBean(null, MaristaConstantes.NOM_PAG));

                //Enviando Log de Errores
                ReporteRechazoService reporteRechazoService = BeanFactory.getReporteRechazoService();
                fail = reporteRechazoService.obtenerCantidadRechazos(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), idProcesoBanco);

                idProcesoBanco = procesoBancoService.obtenerMaxIdProcesoBanco(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
//                if (usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SANJOC)) {
//                    if (Integer.parseInt(obj_update.toString()) != 0) {
//                        if (cabecera == 1) {
//                            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
//                        } else {
//                            RequestContext.getCurrentInstance().addCallbackParam("error", true);
//                        }
//                    } else if (Integer.parseInt(obj_update.toString()) == 0) {
//                        RequestContext.getCurrentInstance().addCallbackParam("errorDatos", true);
//                    }
//                } else {
//                    if (cabecera == 1) {
//                        RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
//                    } else {
//                        RequestContext.getCurrentInstance().addCallbackParam("error", true);
//                    }
//                }
                /* INICIALIZANDO PROCESO_BANCO */
                procesoBanco = new ProcesoBancoBean();
                GregorianCalendar fechaActual = new GregorianCalendar();
                getProcesoBanco();
                getProcesoBanco().setFecha(fechaActual.getTime());
                getProcesoBanco().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            } else {
                //            Object object = procesoFinalService.execProCtaCte(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), ruc, idProcesoBanco, beanUsuarioSesion.getUsuario());
                Object obj_genera = procesoFinalService.execProCtaCte1(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), ruc, idProcesoBanco, beanUsuarioSesion.getUsuario());
                Object obj_update = procesoFinalService.execProCtaCte2(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), ruc, idProcesoBanco, beanUsuarioSesion.getUsuario());

                System.out.println("objeto >>>> " + obj_genera);
                System.out.println("objeto >>>> " + obj_update);
                System.out.println("objeto >>>> " + obj_error);

                Integer proceso = 0;
                proceso = procesoBancoService.obtenerMaxIdProcesoBanco(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            procesoFinalService.execProErrores(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), proceso, beanUsuarioSesion.getUsuario(), 2);

                /* Grabando en el Asiento */
                procesoFinalService.execProAsiento(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), "MT_ProcesoRecup", idProcesoBanco, beanUsuarioSesion.getUsuario(), idProcesoBanco);
                CodigoBean bean = new CodigoBean();
                bean.setCodigo(MaristaConstantes.TIP_STA_CCH);
                bean.setTipoCodigoBean(new TipoCodigoBean(null, MaristaConstantes.NOM_PAG));

                //Enviando Log de Errores
                ReporteRechazoService reporteRechazoService = BeanFactory.getReporteRechazoService();
                fail = reporteRechazoService.obtenerCantidadRechazos(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), idProcesoBanco);

                idProcesoBanco = procesoBancoService.obtenerMaxIdProcesoBanco(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
//                if (usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SANJOC)) {
//                    if (Integer.parseInt(obj_update.toString()) != 0) {
//                        if (cabecera == 1) {
//                            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
//                        } else {
//                            RequestContext.getCurrentInstance().addCallbackParam("error", true);
//                        }
//                    } else if (Integer.parseInt(obj_update.toString()) == 0) {
//                        RequestContext.getCurrentInstance().addCallbackParam("errorDatos", true);
//                    }
//                } else {
//                    if (cabecera == 1) {
//                        RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
//                    } else {
//                        RequestContext.getCurrentInstance().addCallbackParam("error", true);
//                    }
//                }
                /* INICIALIZANDO PROCESO_BANCO */
                procesoBanco = new ProcesoBancoBean();
                GregorianCalendar fechaActual = new GregorianCalendar();
                getProcesoBanco();
                getProcesoBanco().setFecha(fechaActual.getTime());
                getProcesoBanco().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            }
            // End ==============================================================================================================================================//
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void mostrarFilesEnvio() {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            File archivo = new File("Envio.txt");
            fichero = new FileWriter(archivo);
            pw = new PrintWriter(archivo);
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
            List<Contenedor> listaContenedorDeta = new ArrayList<>();
            List<Contenedor> listaContenedorCabe = new ArrayList<>();
            List<Contenedor> listaContenedorInte = new ArrayList<>();

            listaContenedorDeta = procesoFinalService.obtenerEnvioBancos(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), rucEntidad, idProceso, MaristaConstantes.FileDetalle, 3);
            listaContenedorInte = procesoFinalService.obtenerEnvioBancos(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), rucEntidad, idProceso, MaristaConstantes.FileIntermedio, 2);
            listaContenedorCabe = procesoFinalService.obtenerEnvioBancos(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), rucEntidad, idProceso, MaristaConstantes.FileCabecera, 1);
            if (listaContenedorDeta.isEmpty() || listaContenedorInte.isEmpty() || listaContenedorCabe.isEmpty()) {
                if (!listaContenedorCabe.isEmpty()) {
                    for (int i = 0; i < listaContenedorCabe.size(); i++) {
                        for (int j = 0; j < listaContenedorCabe.get(i).getListaContenedor().size(); j++) {
                            if (j < (listaContenedorCabe.get(i).getListaContenedor().size() - 1)) {
                                pw.print(listaContenedorCabe.get(i).getListaContenedor().get(j).getValor());
                            } else {
                                if (j == (listaContenedorCabe.get(i).getListaContenedor().size() - 1)) {
                                    pw.print(listaContenedorCabe.get(i).getListaContenedor().get(j).getValor());
                                    pw.println();
                                }
                            }
                        }
                    }
                    pw.println();
                }
                if (!listaContenedorInte.isEmpty()) {
                    for (int i = 0; i < listaContenedorInte.size(); i++) {
                        for (int j = 0; j < listaContenedorInte.get(i).getListaContenedor().size(); j++) {
                            if (j < (listaContenedorInte.get(i).getListaContenedor().size() - 1)) {
                                pw.print(listaContenedorInte.get(i).getListaContenedor().get(j).getValor());
                            } else {
                                if (j == (listaContenedorInte.get(i).getListaContenedor().size() - 1)) {
                                    pw.print(listaContenedorInte.get(i).getListaContenedor().get(j).getValor());
                                    pw.println();
                                }
                            }
                        }
                    }
                    pw.println();
                }
                if (!listaContenedorDeta.isEmpty()) {
                    for (int i = 0; i < listaContenedorDeta.size(); i++) {
                        for (int j = 0; j < listaContenedorDeta.get(i).getListaContenedor().size(); j++) {
                            if (j < (listaContenedorDeta.get(i).getListaContenedor().size() - 1)) {
                                pw.print(listaContenedorDeta.get(i).getListaContenedor().get(j).getValor());
                            } else {
                                if (j == (listaContenedorDeta.get(i).getListaContenedor().size() - 1)) {
                                    pw.print(listaContenedorDeta.get(i).getListaContenedor().get(j).getValor());
                                    pw.println();
                                }
                            }
                        }
                    }
                }
                pw.close();
            }
            content = new DefaultStreamedContent(new FileInputStream(archivo), "txt", nombreFile);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception err) {
                new MensajePrime().addErrorGeneralMessage();
                GLTLog.writeError(this.getClass(), err);
            }
        }
    }

    public void crearXls(Object document) {
        try {
            ProcesoBancoBean procesoBancoBean = new ProcesoBancoBean();
            procesoBancoBean.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            procesoBancoBean.setFlgProceso(1);
            procesoBancoBean.setIdProcesoBanco(idProceso);
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            Integer reg = procesoBancoService.obtenerNumeroRegistros(procesoBancoBean);
            System.out.println(">>>>" + reg);
            ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
            ProcesoFilesBean procesoFilesBean = new ProcesoFilesBean();
            procesoFilesBean.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            procesoFilesBean.setFlgProceso(2);
            HSSFWorkbook wb = (HSSFWorkbook) document;
            HSSFSheet sheet = wb.getSheetAt(0);
            CellStyle cellStyle = wb.createCellStyle();
            CreationHelper createHelper = wb.getCreationHelper();
            if (reg != null && !reg.equals(0)) {
                for (int j = 1; j <= reg; j++) {
                    HSSFRow header = sheet.getRow(j);
                    for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
                        procesoFilesBean.setPosicionItem(i + 1);
                        Integer tipoDato = procesoFilesService.obtenerPosTipoFile(procesoFilesBean);
                        System.out.println(">>>>" + tipoDato);
                        if (tipoDato.equals(MaristaConstantes.TIPO_FILE_FECHA)) {
                            HSSFCell cell = header.getCell(i);
                            cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("MM/dd/yyyy"));
                            cell.setCellStyle(cellStyle);
                            System.out.println(">>>>>>" + header.getCell(i));
                        } else if (tipoDato.equals(MaristaConstantes.TIPO_FILE_HORA)) {
                            HSSFCell cell = header.getCell(i);
                            cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("HH:mm:ss"));
                            cell.setCellStyle(cellStyle);
                            System.out.println(">>>>>>" + header.getCell(i));
                        } else if (tipoDato.equals(MaristaConstantes.TIPO_FILE_MONEDA)) {
                            HSSFCell cell = header.getCell(i);
                            if (cell.toString() != null && !cell.toString().equals("")) {
                                cell.setCellValue(Double.parseDouble(header.getCell(i).toString()));
                                cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#.#"));
                                cell.setCellStyle(cellStyle);
                            } else {
                                cell.setCellValue(Double.parseDouble("0.0"));
                                cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("0.0"));
                                cell.setCellStyle(cellStyle);
                            }
                            System.out.println(">>>>>>" + header.getCell(i));
                        }
                    }
                }
            } else {
                System.out.println(">>>> nada");
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void postXls(Object document) {
        try {
            HSSFWorkbook wb = (HSSFWorkbook) document;
            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow header = sheet.getRow(0);
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            CellStyle cellStyle = wb.createCellStyle();
            CreationHelper createHelper = wb.getCreationHelper();
            cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("MM/dd/yyyy"));

            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat tiempo = new SimpleDateFormat("HH:mm:ss");

            List<ProcesoFilesBean> listaProcesoFilesBean = new ArrayList<>();
            ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
            listaProcesoFilesBean = procesoFilesService.obtenerFileProceso(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), rucEntidad, 2, MaristaConstantes.FileDetalle);
            List<Contenedor> listaContenedorDeta = new ArrayList<>();
            ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
            listaContenedorDeta = procesoFinalService.obtenerEnvioBancos(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), rucEntidad, idProceso, MaristaConstantes.FileDetalle, 3);
            ProcesoEnvioBean procesoEnvioBean = new ProcesoEnvioBean();
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            Integer num = 0;
            procesoEnvioBean.setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            procesoEnvioBean.setIdProcesoBanco(idProceso);
            num = procesoEnvioService.obtenerNumFilas(procesoEnvioBean);

            if (!listaProcesoFilesBean.isEmpty() && !listaContenedorDeta.isEmpty()) {
                /* CREANDO DETALLE */
                for (int i = 0; i < num; i++) {
                    HSSFRow fila = sheet.createRow(i);
                    for (int j = 0; j < listaContenedorDeta.get(i).getListaContenedor().size(); j++) {
                        HSSFCell celda = fila.createCell(j);
                        System.out.println(">>>" + listaContenedorDeta.get(i).getListaContenedor().get(j).getValor());
                        for (int k = 0; k < listaProcesoFilesBean.size(); k++) {
                            if (j != 0) {
                                if (j == k) {
                                    if (listaProcesoFilesBean.get(k).getTipoDato().getIdCodigo().equals(20101)) {
                                        if (listaContenedorDeta.get(i).getListaContenedor().get(j).getValor() != null
                                                && !listaContenedorDeta.get(i).getListaContenedor().get(j).getValor().equals("")) {
                                            celda.setCellValue(listaContenedorDeta.get(i).getListaContenedor().get(j).getValor().toString());
                                        } else {
                                            celda.setCellValue("");
                                        }
                                    } else if (listaProcesoFilesBean.get(k).getTipoDato().getIdCodigo().equals(20102)) {
                                        if (listaContenedorDeta.get(i).getListaContenedor().get(j).getValor() != null
                                                && !listaContenedorDeta.get(i).getListaContenedor().get(j).getValor().equals("")) {
                                            celda.setCellValue(Integer.parseInt(listaContenedorDeta.get(i).getListaContenedor().get(j).getValor().toString()));
                                        } else {
                                            celda.setCellValue(Integer.parseInt("0"));
                                        }
                                    } else if (listaProcesoFilesBean.get(k).getTipoDato().getIdCodigo().equals(20103)) {
                                        if (listaContenedorDeta.get(i).getListaContenedor().get(j).getValor() != null
                                                && !listaContenedorDeta.get(i).getListaContenedor().get(j).getValor().equals("")) {
                                            System.out.println(">>>>" + listaContenedorDeta.get(i).getListaContenedor().get(j).getValor().toString());
                                            celda.setCellValue(formato.parse(listaContenedorDeta.get(i).getListaContenedor().get(j).getValor().toString()));
                                            celda.setCellValue(new Date());
                                            celda.setCellStyle(cellStyle);
                                        } else {
                                            celda.setCellValue("");
                                        }
                                    } else if (listaProcesoFilesBean.get(k).getTipoDato().getIdCodigo().equals(20104)) {
                                        if (listaContenedorDeta.get(i).getListaContenedor().get(j).getValor() != null
                                                && !listaContenedorDeta.get(i).getListaContenedor().get(j).getValor().equals("")) {
                                            celda.setCellValue(tiempo.parse(listaContenedorDeta.get(i).getListaContenedor().get(j).getValor().toString()));
                                        } else {
                                            celda.setCellValue("");
                                        }
                                    } else if (listaProcesoFilesBean.get(k).getTipoDato().getIdCodigo().equals(20105)) {
                                        if (listaContenedorDeta.get(i).getListaContenedor().get(j).getValor() != null
                                                && !listaContenedorDeta.get(i).getListaContenedor().get(j).getValor().equals("")) {
                                            celda.setCellValue(Double.parseDouble(listaContenedorDeta.get(i).getListaContenedor().get(j).getValor().toString()));
                                        } else {
                                            celda.setCellValue(Double.parseDouble("0"));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                Integer hoja = sheet.getLastRowNum();
                System.out.println(">>>>" + hoja);
                HSSFRow ultimo = sheet.getRow(num + 1);
                sheet.removeRow(ultimo);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

}
