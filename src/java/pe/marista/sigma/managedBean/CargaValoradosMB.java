package pe.marista.sigma.managedBean;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
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
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.CuentaBancoBean;
import pe.marista.sigma.bean.EntidadBean;
import pe.marista.sigma.bean.FichaBean;
import pe.marista.sigma.bean.ProcesoBancoBean;
import pe.marista.sigma.bean.ProcesoFilesBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.CobranzaValoradoRepBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.CuentaBancoService;
import pe.marista.sigma.service.EntidadService;
import pe.marista.sigma.service.FichaService;
import pe.marista.sigma.service.ProcesoBancoService;
import pe.marista.sigma.service.ProcesoFilesService;
import pe.marista.sigma.service.ProcesoFinalService;
import pe.marista.sigma.service.ProcesoRecuperacionService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

public class CargaValoradosMB extends BaseMB implements Serializable {

    @PostConstruct
    public void CargaValoradosMB() {
        try {
            usuarioLoginBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            GregorianCalendar fechaActual = new GregorianCalendar();
            getProcesoBancoBean();
            procesoBancoBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            procesoBancoBean.setFechaInicio(fechaActual.getTime());
            procesoBancoBean.setFechaFin(fechaActual.getTime());
            procesoBancoBean.setFecha(fechaActual.getTime());
            EntidadService entidadService = BeanFactory.getEntidadService();
            getListaEntidad();
            listaEntidad = entidadService.obtenerFlgFinanciero(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            getFichaFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getFichaFiltroBean().setFechaIni(fechaActual.getTime());
            getFichaFiltroBean().setFechaFin(fechaActual.getTime());

            CodigoService codigoService = BeanFactory.getCodigoService();
            getListaModoPago();
            listaModoPago = new ArrayList<>();
            listaModoPago = codigoService.obtenerCodigoDocIngreso();

            Integer pro = 0;
            totSoles = new BigDecimal(pro.floatValue());

            //HISTORIAL DE FICHAS
            getFichaHisBean();
            getFichaHisBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            FichaService fichaService = BeanFactory.getFichaService();
            getListaHisFicha();
            listaHisFicha = fichaService.obtenerHistorialCarga(getFichaHisBean());
            setTipFiltro(1);
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    //CLASES
    private ProcesoBancoBean procesoBancoBean;
    private UsuarioBean usuarioLoginBean;
    private FichaBean fichaFiltroBean;
    private FichaBean fichaHisBean;

    //LISTAS
    private List<CuentaBancoBean> listaCuentaBanco;
    private List<EntidadBean> listaEntidad;
    private List<FichaBean> listaFichaFiltroBean;
    private List<CodigoBean> listaModoPago;
    private List<FichaBean> listaHisFicha;
    private List<FichaBean> listaFichaFamilia;

    //VARIABLES DE AYUDA
    private String rucEntidad = "";
    private Integer var = 0;
    private Integer idFile;
    private BigDecimal totSoles;
    private Integer tipFiltro;

    //METODOS DE CLASE
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

            ProcesoBancoService procesoBancoBeanService = BeanFactory.getProcesoBancoService();
            String ruc = procesoBancoBean.getRuc();

            //Obteniendo ubicaciones de los archivos
            CodigoService codigoService = BeanFactory.getCodigoService();
            CodigoBean codigoTipoFileCab = new CodigoBean();
            codigoTipoFileCab = codigoService.obtenerPorCodigo(new CodigoBean(20001, "Cabecera", new TipoCodigoBean(MaristaConstantes.file_Cabecera)));

            CodigoBean codigoTipoFileDet = new CodigoBean();
            codigoTipoFileDet = codigoService.obtenerPorCodigo(new CodigoBean(20002, "Detalle", new TipoCodigoBean(MaristaConstantes.file_Detalle)));

            List<ProcesoFilesBean> listaProcesoFilesBean = new ArrayList<>();
            List<ProcesoFilesBean> listaProcesoFilesBeanDeta = new ArrayList<>();
            ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
            listaProcesoFilesBean = procesoFilesService.obtenerFileProceso(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), ruc, 1, codigoTipoFileCab.getIdCodigo());//Archivo Vuelta
            listaProcesoFilesBeanDeta = procesoFilesService.obtenerFileProceso(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), ruc, 1, codigoTipoFileDet.getIdCodigo());//Archivo Vuelta

            List<String> listaCabeceraBanco = new ArrayList<>(); //Cabecera de Archivo
            List<String> listaDetalleBanco = new ArrayList<>(); //Detalle de Archivo

            List<List<String>> listaDetallesElementos = new ArrayList<>(); //DETALLE DE ELEMENTOS

            Integer cabecera = 0;
            Integer afterListCab = 0;
            Integer beforeListCab = 0;
            afterListCab = listaProcesoFilesBeanDeta.size();

            Integer idProcesoBanco = 0;
            idProcesoBanco = procesoBancoBeanService.obtenerMaxIdProcesoBanco(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg()) + 1;
            ProcesoBancoBean bancoBean = new ProcesoBancoBean();

            String[][] rpta = new String[listaProcesoFilesBean.size()][arreglo.length];
            String[][] rptas = new String[listaProcesoFilesBeanDeta.size()][arreglo.length];

            // Insertando ProcesoBanco ==============================================================================================================================================//              
            if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)
                    || usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SANJOC)) {
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
                                        if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)) {
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
            } else if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SANLUI)) {
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
                                            if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)) {
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
            numFilas = procesoFilesService.obtenerNumFiles(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 1, MaristaConstantes.FileDetalle, ruc);

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

            procesoFinalService.execProcesoRecup(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), listaProcesoFilesBeanDeta.size(), idProcesoBanco);
            System.out.println("banco => " + idProcesoBanco);
            Integer dato = 0;
            String rucBanco = "";
            rucBanco = procesoBancoBean.getRuc();
            for (int i = 0; i < listaDetallesElementos.size(); i++) {
                obtenerFileId();
                for (int j = 0; j < listaDetallesElementos.get(i).size(); j++) {
                    procesoFinalService.execProcesoRecupIns(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), idProcesoBanco, listaDetallesElementos.get(i).get(j), (j + 1), idFile, usuarioLoginBean.getUsuario());
                }
            }

            Integer posicion = 0;
            cabecera = generarRecuperacion(file.getFileName());

            FichaService fichaService = BeanFactory.getFichaService();
            Object obj_update = fichaService.execProCtaCteBingo(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), ruc, idProcesoBanco, usuarioLoginBean.getUsuario());
            System.out.println("objeto >>>>>" + obj_update);
            if (cabecera == 1) {
                getFichaHisBean();
                getFichaHisBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                getListaHisFicha();
                listaHisFicha = fichaService.obtenerHistorialCarga(getFichaHisBean());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            } else {
                RequestContext.getCurrentInstance().addCallbackParam("error", true);
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public static String secciona(String cadena, Integer inicio, Integer fin) {
        String cadenaRpta = null;
        cadenaRpta = cadena.substring((inicio - 1), fin);
        System.out.println(">>>>> " + cadenaRpta);
        return cadenaRpta;
    }

    public void obtenerFileId() {
        try {
            ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
            Integer id = 0;
            id = procesoFinalService.obtenerMaxIdFile(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc());
            idFile = id + 1;
            setIdFile(idFile);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cargarRuc() {
        try {
            Integer moneda = 0;
            UsuarioBean usuarioLoginBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            CuentaBancoService cuentaBancoService = BeanFactory.getCuentaBancoService();
            if (procesoBancoBean.getMoneda() == null) {
                procesoBancoBean.setMoneda(false);
                moneda = MaristaConstantes.COD_SOLES;
            } else {
                if (procesoBancoBean.getMoneda() != null) {
                    if (procesoBancoBean.getMoneda()) {
                        moneda = MaristaConstantes.COD_DOLARES;
                    } else {
                        if (!procesoBancoBean.getMoneda()) {
                            moneda = MaristaConstantes.COD_SOLES;
                        }
                    }
                }
            }
            listaCuentaBanco = cuentaBancoService.obtenerPorRuc(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), moneda);
            if (!listaCuentaBanco.isEmpty()) {
                RequestContext.getCurrentInstance().addCallbackParam("openModalCodUniNeg", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerCuentaBanco(Object object) {
        try {
            CuentaBancoBean cuentaBancoBean = (CuentaBancoBean) object;
            procesoBancoBean.setCodUniNeg(cuentaBancoBean.getCodUniNeg());
            procesoBancoBean.setNumCuenta(cuentaBancoBean.getNumCuenta());
            rucEntidad = cuentaBancoBean.getEntidadBancoBean().getRuc();
            System.out.println("ruc: " + rucEntidad);
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
            montoRecuperado = procesoRecuperacionService.obtenerMontoTotal((idProcesoBanco + 1), fileDetalle, 1, procesoBancoBean.getRuc(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            totalRegistros = procesoRecuperacionService.obtenerTotalRegistros((idProcesoBanco + 1), procesoBancoBean.getRuc(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            //Insertando Registros
            procesoBancoBean.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            procesoBancoBean.setCodUniNeg(procesoBancoBean.getCodUniNeg());
            procesoBancoBean.setNumCuenta(procesoBancoBean.getNumCuenta());
            procesoBancoBean.setMoneda(procesoBancoBean.getMoneda());
            procesoBancoBean.setRuc(procesoBancoBean.getRuc());
            procesoBancoBean.setFecha(procesoBancoBean.getFecha());
            procesoBancoBean.setNombre(nombre);
            procesoBancoBean.setFlgProceso(2);
            procesoBancoBean.setTipoArchivo("C");
            procesoBancoBean.setRegEnv(totalRegistros);
            procesoBancoBean.setMontoRecup(montoRecuperado);
            procesoBancoBean.setCreaPor(beanUsuarioSesion.getUsuario());
            procesoBancoService.insertarProcesoBanco(procesoBancoBean);
            limpiarRecuperacion();
            cabecera = 1;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return cabecera;
    }

    public void limpiarRecuperacion() {
        try {
            procesoBancoBean = new ProcesoBancoBean();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void filtrarFichaPagada() {
        try {
            Integer res = 0;
            Float pro = res.floatValue();
            FichaService fichaService = BeanFactory.getFichaService();
            if (fichaFiltroBean.getFechaIni() != null) {
                fichaFiltroBean.setFechaIni(fichaFiltroBean.getFechaIni());
                getFichaHisBean().setFechaIni(fichaFiltroBean.getFechaIni());
                res = 1;
            }
            if (fichaFiltroBean.getFechaFin() != null) {
                fichaFiltroBean.setFechaFin(fichaFiltroBean.getFechaFin());
                getFichaHisBean().setFechaFin(fichaFiltroBean.getFechaFin());
                res = 1;
            }
            if (fichaFiltroBean.getPaganteBean().getNomPagante() != null
                    && !fichaFiltroBean.getPaganteBean().getNomPagante().equals("")) {
                fichaFiltroBean.getPaganteBean().setNomPagante(fichaFiltroBean.getPaganteBean().getNomPagante());
                res = 1;
            }
            if (fichaFiltroBean.getPaganteBean().getNroDoc() != null
                    && !fichaFiltroBean.getPaganteBean().getNroDoc().equals("")) {
                fichaFiltroBean.getPaganteBean().setNroDoc(fichaFiltroBean.getPaganteBean().getNroDoc());
                res = 1;
            }
            if (fichaFiltroBean.getTipoModoPago().getIdCodigo() != null) {
                fichaFiltroBean.getTipoModoPago().setIdCodigo(fichaFiltroBean.getTipoModoPago().getIdCodigo());
                res = 1;
            }
            if (tipFiltro.equals(1)) {
                if (res != 0) {
                    listaFichaFiltroBean = fichaService.filtrarFichasPagadas(fichaFiltroBean);
                    listaHisFicha = fichaService.obtenerHistorialCarga(getFichaHisBean());
                    if (listaFichaFiltroBean.isEmpty()) {
                        new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                        listaFichaFiltroBean = new ArrayList<>();
                        totSoles = new BigDecimal(pro.floatValue());
                    } else if (!listaFichaFiltroBean.isEmpty()) {
                        for (FichaBean ficha : listaFichaFiltroBean) {
                            pro = ficha.getMontoPagado().floatValue() + pro;
                        }
                        totSoles = new BigDecimal(pro.floatValue());
                    }
                    if (listaHisFicha.isEmpty()) {
                        getFichaHisBean().setFechaIni(null);
                        getFichaHisBean().setFechaFin(null);
                        getFichaHisBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        listaHisFicha = fichaService.obtenerHistorialCarga(getFichaHisBean());
                    }
                } else if (res == 0) {
                    new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                    totSoles = new BigDecimal(pro.floatValue());
                    listaFichaFiltroBean = new ArrayList<>();
                }
            } else if (tipFiltro.equals(2)) {
                if (res == 1) {
                    listaFichaFamilia = fichaService.filtrarFichasPagadasFamilia(fichaFiltroBean);
                    if (listaFichaFamilia.isEmpty()) {
                        new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                        listaFichaFamilia = new ArrayList<>();
                        totSoles = new BigDecimal(pro.floatValue());
                    } else if (!listaFichaFamilia.isEmpty()) {
                        for (FichaBean ficha : listaFichaFamilia) {
                            pro = ficha.getTotalFa().floatValue() + pro;
                        }
                        totSoles = new BigDecimal(pro.floatValue());
                    }
                } else if (res == 0) {
                    new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                    totSoles = new BigDecimal(pro.floatValue());
                    listaFichaFamilia = new ArrayList<>();
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarFicha() {
        try {
            Integer res = 0;
            fichaFiltroBean = new FichaBean();
            totSoles = new BigDecimal(res.floatValue());
            GregorianCalendar fechaActual = new GregorianCalendar();
            getFichaFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getFichaFiltroBean().setFechaIni(fechaActual.getTime());
            getFichaFiltroBean().setFechaFin(fechaActual.getTime());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cambiarTiFiltro() {
        try {
            if (tipFiltro.equals(1)) {
                listaFichaFamilia = new ArrayList<>();
                Integer res = 0;
                totSoles = new BigDecimal(res.floatValue());
            } else if (tipFiltro.equals(2)) {
                listaFichaFiltroBean = new ArrayList<>();
                Integer res = 0;
                totSoles = new BigDecimal(res.floatValue());
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void reimpresionReciboMasivo(Object object, Integer dato) {
        ServletOutputStream out = null;
        try {
            FichaBean fichaBean = (FichaBean) object;
            List<CobranzaValoradoRepBean> listaCobranzaValoradoRepBean = new ArrayList<>();
            FichaService fichaService = BeanFactory.getFichaService();
            List<Integer> listaIds = new ArrayList<>();
            /*
             dato 1 => SE OBTIENE POR UNIDAD
             dato 2 => SE OBTIENE TOTAL
             */
            if (dato.equals(1)) {
                listaIds.add(fichaBean.getIdFicha());
            } else if (dato.equals(2)) {
                List<FichaBean> listaPagoFicha = new ArrayList<>();
                fichaBean.getTipoStatusFicha().setCodigo(MaristaConstantes.COD_FICHA_STATUS_PAGADO);
                fichaBean.getTipoStatusFicha().getTipoCodigoBean().setIdTipoCodigo(MaristaConstantes.TIP_STATUS_FICHA);
                listaPagoFicha = fichaService.obtenerFichaPorPagantePagado(fichaBean);
                for (FichaBean ficha : listaPagoFicha) {
                    listaIds.add(ficha.getIdFicha());
                }
            }
            listaCobranzaValoradoRepBean = fichaService.generarReciboValorado(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaIds);
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/RepCobranzaValorado.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCobranzaValoradoRepBean);
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
        }
        FacesContext.getCurrentInstance().responseComplete();
    }

    //GET Y SET
    public ProcesoBancoBean getProcesoBancoBean() {
        if (procesoBancoBean == null) {
            procesoBancoBean = new ProcesoBancoBean();
        }
        return procesoBancoBean;
    }

    public void setProcesoBancoBean(ProcesoBancoBean procesoBancoBean) {
        this.procesoBancoBean = procesoBancoBean;
    }

    public UsuarioBean getUsuarioLoginBean() {
        if (usuarioLoginBean == null) {
            usuarioLoginBean = new UsuarioBean();
        }
        return usuarioLoginBean;
    }

    public void setUsuarioLoginBean(UsuarioBean usuarioLoginBean) {
        this.usuarioLoginBean = usuarioLoginBean;
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

    public String getRucEntidad() {
        return rucEntidad;
    }

    public void setRucEntidad(String rucEntidad) {
        this.rucEntidad = rucEntidad;
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

    public Integer getVar() {
        return var;
    }

    public void setVar(Integer var) {
        this.var = var;
    }

    public Integer getIdFile() {
        return idFile;
    }

    public void setIdFile(Integer idFile) {
        this.idFile = idFile;
    }

    public FichaBean getFichaFiltroBean() {
        if (fichaFiltroBean == null) {
            fichaFiltroBean = new FichaBean();
        }
        return fichaFiltroBean;
    }

    public void setFichaFiltroBean(FichaBean fichaFiltroBean) {
        this.fichaFiltroBean = fichaFiltroBean;
    }

    public List<FichaBean> getListaFichaFiltroBean() {
        if (listaFichaFiltroBean == null) {
            listaFichaFiltroBean = new ArrayList<>();
        }
        return listaFichaFiltroBean;
    }

    public void setListaFichaFiltroBean(List<FichaBean> listaFichaFiltroBean) {
        this.listaFichaFiltroBean = listaFichaFiltroBean;
    }

    public BigDecimal getTotSoles() {
        return totSoles;
    }

    public void setTotSoles(BigDecimal totSoles) {
        this.totSoles = totSoles;
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

    public List<FichaBean> getListaHisFicha() {
        if (listaHisFicha == null) {
            listaHisFicha = new ArrayList<>();
        }
        return listaHisFicha;
    }

    public void setListaHisFicha(List<FichaBean> listaHisFicha) {
        this.listaHisFicha = listaHisFicha;
    }

    public FichaBean getFichaHisBean() {
        if (fichaHisBean == null) {
            fichaHisBean = new FichaBean();
        }
        return fichaHisBean;
    }

    public void setFichaHisBean(FichaBean fichaHisBean) {
        this.fichaHisBean = fichaHisBean;
    }

    public Integer getTipFiltro() {
        return tipFiltro;
    }

    public void setTipFiltro(Integer tipFiltro) {
        this.tipFiltro = tipFiltro;
    }

    public List<FichaBean> getListaFichaFamilia() {
        if (listaFichaFamilia == null) {
            listaFichaFamilia = new ArrayList<>();
        }
        return listaFichaFamilia;
    }

    public void setListaFichaFamilia(List<FichaBean> listaFichaFamilia) {
        this.listaFichaFamilia = listaFichaFamilia;
    }

}
