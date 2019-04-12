package pe.marista.sigma.managedBean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.Contenedor;
import pe.marista.sigma.bean.CuentaBancoBean;
import pe.marista.sigma.bean.CuentasPorCobrarBean;
import pe.marista.sigma.bean.ProcesoBancoBean;
import pe.marista.sigma.bean.ProcesoFilesBean;
import pe.marista.sigma.bean.ProcesoRecuperacionBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.BloqueoService;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.CuentaBancoService;
import pe.marista.sigma.service.CuentasPorCobrarService;
import pe.marista.sigma.service.EstudianteService;
import pe.marista.sigma.service.ProcesoBancoService;
import pe.marista.sigma.service.ProcesoFilesService;
import pe.marista.sigma.service.ProcesoFinalService;
import pe.marista.sigma.service.ProcesoRecuperacionService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

public class RecuperacionMB {

    @PostConstruct
    public void RecuperacionMB() {
        try {
            usuarioSessionBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            getProcesoBancoBean();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //CLASES 
    private ProcesoBancoBean procesoBancoBean;
    private ProcesoRecuperacionBean procesoRecuperacionBean;
    private UsuarioBean usuarioSessionBean;
    private CuentasPorCobrarBean cuentasPorCobrarBean;

    //LISTAS
    private List<ProcesoBancoBean> listaProcesoBancoBean;
    private List<Contenedor> listaProEnviosCab;
    private List<Contenedor> listaProEnviosInt;
    private List<Contenedor> listaProEnvios;
    private List<CuentasPorCobrarBean> listaCuentasPorCobrarBean;

    //VARIABLES DE AYUDA
    private Integer idProcesoBanco;
    private Integer var = 0;
    private Integer idFile;
    private Integer filasCab;
    private Integer filasInt;
    private Integer filas;
    private Integer tipoOperacion;

    private Boolean flgFecha;

    public void cargarDatos() {
        try {
            CuentaBancoService cuentaBancoService = BeanFactory.getCuentaBancoService();
            List<CuentaBancoBean> listaCuentaBancoBean = new ArrayList<>();
            GregorianCalendar fechaActual = new GregorianCalendar();
            getProcesoBancoBean();
            getProcesoBancoBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getProcesoBancoBean().setFecha(fechaActual.getTime());
            getProcesoBancoBean().setFechaInicio(fechaActual.getTime());
            getProcesoBancoBean().setFechaFin(fechaActual.getTime());
            getProcesoBancoBean().setFlgProceso(0);
            if (usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SANJOC)) {
                listaCuentaBancoBean = cuentaBancoService.obtenerPorRucRecauda(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), MaristaConstantes.RUC_INTERBANK, MaristaConstantes.COD_SOLES);
            } else if (usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)) {
                listaCuentaBancoBean = cuentaBancoService.obtenerPorRucRecauda(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), MaristaConstantes.RUC_SCOTIABANK, MaristaConstantes.COD_SOLES);
            } else if (usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SANLUI)) {
                listaCuentaBancoBean = cuentaBancoService.obtenerPorRucRecauda(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), MaristaConstantes.RUC_BCP, MaristaConstantes.COD_SOLES);
            } else if (usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SANJOH)) {
                listaCuentaBancoBean = cuentaBancoService.obtenerPorRucRecauda(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), MaristaConstantes.RUC_BCP, MaristaConstantes.COD_SOLES);
            } else if (usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_BARINA)) {
                listaCuentaBancoBean = cuentaBancoService.obtenerPorRucRecauda(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), MaristaConstantes.RUC_INTERBANK, MaristaConstantes.COD_SOLES);
            }
            getProcesoBancoBean().getEntidadBean().setNombre(listaCuentaBancoBean.get(0).getEntidadBancoBean().getNombre());
            getProcesoBancoBean().getEntidadBean().setRuc(listaCuentaBancoBean.get(0).getEntidadBancoBean().getRuc());
            getProcesoBancoBean().setRuc(listaCuentaBancoBean.get(0).getEntidadBancoBean().getRuc());
            getProcesoBancoBean().setCodUniNeg(listaCuentaBancoBean.get(0).getCodUniNeg());
            getProcesoBancoBean().setNumCuenta(listaCuentaBancoBean.get(0).getNumCuenta());
            getProcesoBancoBean().setTipoMoneda(0);
            getTipoOperacion();
            setTipoOperacion(1);
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            idProcesoBanco = procesoBancoService.obtenerMaxIdProcesoBanco(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg()) + 1;
            if (usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_BARINA)) {
                this.flgFecha = true;
            } else {
                this.flgFecha = false;
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerFiltroProceso() {
        try {
            Integer res = 0;
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            if (procesoBancoBean.getFlgProceso() != null) {
                procesoBancoBean.setFlgProceso(procesoBancoBean.getFlgProceso());
                res = 1;
            }
            if (procesoBancoBean.getFechaInicio() != null) {
                procesoBancoBean.setFechaInicio(procesoBancoBean.getFechaInicio());
                res = 1;
            }
            if (procesoBancoBean.getFechaFin() != null) {
                procesoBancoBean.setFechaFin(procesoBancoBean.getFechaFin());
                res = 1;
            }
            if (res == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaProcesoBancoBean = new ArrayList<>();
            } else if (res == 1) {
                listaProcesoBancoBean = procesoBancoService.filtrarProceso(procesoBancoBean);
                if (listaProcesoBancoBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    listaProcesoBancoBean = new ArrayList<>();
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cambiarEstado() {
        try {
            setTipoOperacion(getTipoOperacion());
            System.out.println(">>>>" + getTipoOperacion());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarBanco(FileUploadEvent event) {
        try {
            if (tipoOperacion.equals(2)) {
                cargarBancoTaller(event);
            } else {
                obtenerFileRecuperacion(event);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarBancoTaller(FileUploadEvent event) {
        try {
            InputStream in = null;
            UploadedFile file = event.getFile();
            in = file.getInputstream();
            String fileObj = file.getFileName();
            String ip = "";
            BloqueoService bloqueoService = BeanFactory.getBloqueoService();
            ip = bloqueoService.obtenerIpServer(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            String destination = "\\\\\\\\" + ip + "\\\\" + usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg() + "\\\\";
            OutputStream out = new FileOutputStream(new File(destination + fileObj));
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            in.close();
            out.flush();
            out.close();
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            System.out.println("destino >>>" + destination);
            System.out.println("El archivo se ha creado con éxito!");

            ProcesoRecuperacionService procesoRecuperacionService = BeanFactory.getProcesoRecuperacionService();
            procesoBancoBean.setIdProcesoBanco(idProcesoBanco);
            procesoBancoBean.setFileObjeto(fileObj);
            procesoBancoBean.setCreaPor(usuarioSessionBean.getUsuario());
            procesoRecuperacionService.execProRecuperacionTaller(procesoBancoBean);
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerFileRecuperacion(FileUploadEvent event) {
        try {
            InputStream miArchivo = null;
            UploadedFile file = event.getFile();
            miArchivo = file.getInputstream();
            byte[] b = new byte[(int) file.getSize()];
            miArchivo.read(b);
            miArchivo.close();
            String resultado = new String(b);
            String[] arreglo = resultado.split("\n");
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();

            String ruc = getProcesoBancoBean().getRuc();

            //Obteniendo ubicaciones de los archivos
            CodigoService codigoService = BeanFactory.getCodigoService();
            CodigoBean codigoTipoFileCab = new CodigoBean();
            codigoTipoFileCab = codigoService.obtenerPorCodigo(new CodigoBean(20001, "Cabecera", new TipoCodigoBean(MaristaConstantes.file_Cabecera)));

            CodigoBean codigoTipoFileDet = new CodigoBean();
            codigoTipoFileDet = codigoService.obtenerPorCodigo(new CodigoBean(20002, "Detalle", new TipoCodigoBean(MaristaConstantes.file_Detalle)));

            List<ProcesoFilesBean> listaProcesoFilesBean = new ArrayList<>();
            List<ProcesoFilesBean> listaProcesoFilesBeanDeta = new ArrayList<>();
            ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
            listaProcesoFilesBean = procesoFilesService.obtenerFileProceso(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), ruc, 1, codigoTipoFileCab.getIdCodigo());//Archivo Vuelta
            listaProcesoFilesBeanDeta = procesoFilesService.obtenerFileProceso(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), ruc, 1, codigoTipoFileDet.getIdCodigo());//Archivo Vuelta

            List<String> listaCabeceraBanco = new ArrayList<>(); //Cabecera de Archivo
            List<String> listaDetalleBanco = new ArrayList<>(); //Detalle de Archivo

            List<List<String>> listaDetallesElementos = new ArrayList<>(); //DETALLE DE ELEMENTOS

            Integer cabecera = 0;
            Integer afterListCab = 0;
            Integer beforeListCab = 0;
            afterListCab = listaProcesoFilesBeanDeta.size();

            ProcesoBancoBean bancoBean = new ProcesoBancoBean();

            String[][] rpta = new String[listaProcesoFilesBean.size()][arreglo.length];
            String[][] rptas = new String[listaProcesoFilesBeanDeta.size()][arreglo.length];

            // Insertando ProcesoBanco ==============================================================================================================================================//              
            if (usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)
                    || usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SANJOC)
                    || usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_BARINA)) {
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
                                        if (usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)) {
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
            } else if (usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SANLUI)
                    || usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SANJOH)) {
                if (!listaProcesoFilesBean.isEmpty() || !listaProcesoFilesBeanDeta.isEmpty()) {
                    for (int i = 0; i < arreglo.length; i++) {
                        String fila = arreglo[i];
                        if (i == 0 && !listaProcesoFilesBean.isEmpty()) {
                            for (int j = 0; j < listaProcesoFilesBean.size(); j++) {
                                rpta[j][0] = secciona(fila, listaProcesoFilesBean.get(j).getPosicionIni(), listaProcesoFilesBean.get(j).getPosicionFin());
                            }
                        } else if (i != 0 && !listaProcesoFilesBeanDeta.isEmpty()) {
                            for (int k = 0; k < listaProcesoFilesBeanDeta.size(); k++) {
                                System.out.println("fila "+fila+" i "+listaProcesoFilesBeanDeta.get(k).getPosicionIni()+ "  / f"+
                                        listaProcesoFilesBeanDeta.get(k).getPosicionFin() );
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
                                            if (usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)) {
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
            numFilas = procesoFilesService.obtenerNumFiles(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 1, MaristaConstantes.FileDetalle, ruc);
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
            System.out.println("Lista de elementos " + listaDetallesElementos.size());
            ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
            for (int i = 0; i < listaDetallesElementos.size(); i++) {
                obtenerFileId();
                for (int j = 0; j < listaDetallesElementos.get(i).size(); j++) {
                    procesoFinalService.execProcesoRecupIns(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), ruc, idProcesoBanco, listaDetallesElementos.get(i).get(j), (j + 1), idFile, usuarioSessionBean.getUsuario());
                }
            }

            Integer posicion = 0;
            cabecera = generarRecuperacion(file.getFileName());

            Object obj_error = "0";
            if (usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SANJOC)) {
               obj_error = procesoFinalService.execProError(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), ruc, idProcesoBanco, usuarioSessionBean.getUsuario());
                System.out.println("objeto >>>> " + obj_error);
                if (Integer.parseInt(obj_error.toString()) > 0) {
                    RequestContext.getCurrentInstance().addCallbackParam("doblePago", true);
                } else {
                    /* RESULTADO DE PROCEDURES */
                    Object obj_genera = procesoFinalService.execProCtaCte1(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), ruc, idProcesoBanco, usuarioSessionBean.getUsuario());
                    Object obj_update = procesoFinalService.execProCtaCte2(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), ruc, idProcesoBanco, usuarioSessionBean.getUsuario());

                    System.out.println("objeto >>>> " + obj_genera);
                    System.out.println("objeto >>>> " + obj_update);

                    /* GRABANDO EN ASIENTO */
                    procesoFinalService.execProAsiento(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), "MT_ProcesoRecup", idProcesoBanco, usuarioSessionBean.getUsuario(), idProcesoBanco);
                    CodigoBean bean = new CodigoBean();
                    bean.setCodigo(MaristaConstantes.TIP_STA_CCH);
                    bean.setTipoCodigoBean(new TipoCodigoBean(null, MaristaConstantes.NOM_PAG));
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                }
            } else {
                Object obj_genera = procesoFinalService.execProCtaCte1(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), ruc, idProcesoBanco, usuarioSessionBean.getUsuario());
                Object obj_update = procesoFinalService.execProCtaCte2(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), ruc, idProcesoBanco, usuarioSessionBean.getUsuario());

                System.out.println("objeto >>>> " + obj_genera);
                System.out.println("objeto >>>> " + obj_update);
                System.out.println("objeto >>>> " + obj_error);

                /* Grabando en el Asiento */
                procesoFinalService.execProAsiento(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), "MT_ProcesoRecup", idProcesoBanco, usuarioSessionBean.getUsuario(), idProcesoBanco);
                CodigoBean bean = new CodigoBean();
                bean.setCodigo(MaristaConstantes.TIP_STA_CCH);
                bean.setTipoCodigoBean(new TipoCodigoBean(null, MaristaConstantes.NOM_PAG));

                if (usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SANJOC)) {
                    if (cabecera == 1) {
                        //OBTENIENDO LISTA ACTUALIZADA
                        listaProcesoBancoBean = procesoBancoService.obtenerPorIdBanco(idProcesoBanco, usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        listaProEnvios = procesoFinalService.execProListaBanco(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), getProcesoBancoBean().getRuc(), idProcesoBanco, MaristaConstantes.FileDetalle, 3, 2);
                        if (!listaProEnvios.isEmpty()) {
                            filas = procesoFilesService.obtenerNumFiles(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 2, MaristaConstantes.FileDetalle, getProcesoBancoBean().getRuc());
                            filasCab = procesoFilesService.obtenerNumFiles(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 2, MaristaConstantes.FileCabecera, getProcesoBancoBean().getRuc());
                            filasInt = procesoFilesService.obtenerNumFiles(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 2, MaristaConstantes.FileIntermedio, getProcesoBancoBean().getRuc());
                        }
                        RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                    } else {
                        RequestContext.getCurrentInstance().addCallbackParam("error", true);
                    }
                    if (Integer.parseInt(obj_update.toString()) == 0) {
                        RequestContext.getCurrentInstance().addCallbackParam("errorDatos", true);
                    }
                } else {
                    if (cabecera == 1) {
                        //OBTENIENDO LISTA ACTUALIZADA
                        listaProcesoBancoBean = procesoBancoService.obtenerPorIdBanco(idProcesoBanco, usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        listaProEnvios = procesoFinalService.execProListaBanco(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), getProcesoBancoBean().getRuc(), idProcesoBanco, MaristaConstantes.FileDetalle, 3, 2);
                        if (!listaProEnvios.isEmpty()) {
                            filas = procesoFilesService.obtenerNumFiles(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 2, MaristaConstantes.FileDetalle, getProcesoBancoBean().getRuc());
                            filasCab = procesoFilesService.obtenerNumFiles(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 2, MaristaConstantes.FileCabecera, getProcesoBancoBean().getRuc());
                            filasInt = procesoFilesService.obtenerNumFiles(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 2, MaristaConstantes.FileIntermedio, getProcesoBancoBean().getRuc());
                        }
                        RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                    } else {
                        RequestContext.getCurrentInstance().addCallbackParam("error", true);
                    }
                }
            }
            cargarDatos();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
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
            idProcesoBanco = procesoBancoService.obtenerMaxIdProcesoBanco(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            montoRecuperado = procesoRecuperacionService.obtenerMontoTotal((idProcesoBanco + 1), fileDetalle, 1, procesoBancoBean.getRuc(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            totalRegistros = procesoRecuperacionService.obtenerTotalRegistros((idProcesoBanco + 1), procesoBancoBean.getRuc(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            //Insertando Registros
            procesoBancoBean.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            procesoBancoBean.setCodUniNeg(procesoBancoBean.getCodUniNeg());
            procesoBancoBean.setNumCuenta(procesoBancoBean.getNumCuenta());
//            procesoBancoBean.setMoneda(procesoBancoBean.getMoneda());
            procesoBancoBean.setTipoMoneda(procesoBancoBean.getTipoMoneda());
            procesoBancoBean.setRuc(procesoBancoBean.getRuc());
            procesoBancoBean.setFecha(procesoBancoBean.getFecha());
            procesoBancoBean.setNombre(nombre);
            procesoBancoBean.setFlgProceso(0);
            procesoBancoBean.setTipoArchivo("C");
            procesoBancoBean.setRegEnv(totalRegistros);
            procesoBancoBean.setMontoRecup(montoRecuperado);
            procesoBancoBean.setCreaPor(usuarioSessionBean.getUsuario());
            procesoBancoService.insertarProcesoBanco(procesoBancoBean);
            cabecera = 1;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return cabecera;
    }

    public void obtenerFileId() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
            Integer id = 0;
            id = procesoFinalService.obtenerMaxIdFile(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc());
            idFile = id + 1;
            setIdFile(idFile);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public static String secciona(String cadena, Integer inicio, Integer fin) {
        String cadenaRpta = null;
        System.out.println("cadena" +cadena+" "+inicio+" "+fin);
        cadenaRpta = cadena.substring((inicio - 1), fin);
        System.out.println(">>>>> " + cadenaRpta);
        return cadenaRpta;
    }

    public void obtenerProcesoRecuperacionPorId(Object object, Integer dato) {
        try {
            procesoBancoBean = (ProcesoBancoBean) object;
//            idProcesoBanco = procesoBancoBean.getIdProcesoBanco();
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            procesoBancoBean = procesoBancoService.obtenerProcesoBancoId(procesoBancoBean.getIdProcesoBanco(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            System.out.println(">>>>" + procesoBancoBean.getIdProcesoBanco());
            if (dato.equals(1)) {
                obtenerFileRecuperacion(procesoBancoBean);
            } else if (dato.equals(2)) {
                obtenerCuentaPorRecuperacion(procesoBancoBean);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerFileRecuperacion(ProcesoBancoBean procesoBancoBean) {
        try {
            ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
            listaProEnvios = procesoFinalService.execProListaBanco(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), getProcesoBancoBean().getRuc(), procesoBancoBean.getIdProcesoBanco(), MaristaConstantes.FileDetalle, 3, 1);
            if (listaProEnvios.isEmpty()) {
                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                listaProEnvios = new ArrayList<>();
            } else if (!listaProEnvios.isEmpty()) {
                ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
                filas = procesoFilesService.obtenerNumFiles(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 1, MaristaConstantes.FileDetalle, getProcesoBancoBean().getRuc());
                filasCab = procesoFilesService.obtenerNumFiles(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 1, MaristaConstantes.FileCabecera, getProcesoBancoBean().getRuc());
                filasInt = procesoFilesService.obtenerNumFiles(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 1, MaristaConstantes.FileIntermedio, getProcesoBancoBean().getRuc());
            }
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
                procesoBancoService.eliminarProcesoBancoMas(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getIdProcesoBanco());
                obtenerFiltroProceso();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void obtenerCuentaPorRecuperacion(ProcesoBancoBean procesoBancoBean) {
        try {
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            CuentasPorCobrarBean cuentasPorCobrarBean = new CuentasPorCobrarBean();
            cuentasPorCobrarBean.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            cuentasPorCobrarBean.getProcesoRecuperacionBean().setIdProcesoRecup(procesoBancoBean.getIdProcesoBanco());
            listaCuentasPorCobrarBean = cuentasPorCobrarService.obtenerCuentaRecuperacion(cuentasPorCobrarBean);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
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

    public List<ProcesoBancoBean> getListaProcesoBancoBean() {
        if (listaProcesoBancoBean == null) {
            listaProcesoBancoBean = new ArrayList<>();
        }
        return listaProcesoBancoBean;
    }

    public void setListaProcesoBancoBean(List<ProcesoBancoBean> listaProcesoBancoBean) {
        this.listaProcesoBancoBean = listaProcesoBancoBean;
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

    public ProcesoRecuperacionBean getProcesoRecuperacionBean() {
        if (procesoRecuperacionBean == null) {
            procesoRecuperacionBean = new ProcesoRecuperacionBean();
        }
        return procesoRecuperacionBean;
    }

    public void setProcesoRecuperacionBean(ProcesoRecuperacionBean procesoRecuperacionBean) {
        this.procesoRecuperacionBean = procesoRecuperacionBean;
    }

    public Integer getIdProcesoBanco() {
        return idProcesoBanco;
    }

    public void setIdProcesoBanco(Integer idProcesoBanco) {
        this.idProcesoBanco = idProcesoBanco;
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

    public List<Contenedor> getListaProEnvios() {
        if (listaProEnvios == null) {
            listaProEnvios = new ArrayList<>();
        }
        return listaProEnvios;
    }

    public void setListaProEnvios(List<Contenedor> listaProEnvios) {
        this.listaProEnvios = listaProEnvios;
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

    public Integer getFilas() {
        return filas;
    }

    public void setFilas(Integer filas) {
        this.filas = filas;
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

    public List<Contenedor> getListaProEnviosCab() {
        if (listaProEnviosCab == null) {
            listaProEnviosCab = new ArrayList<>();
        }
        return listaProEnviosCab;
    }

    public void setListaProEnviosCab(List<Contenedor> listaProEnviosCab) {
        this.listaProEnviosCab = listaProEnviosCab;
    }

    public List<Contenedor> getListaProEnviosInt() {
        if (listaProEnviosInt == null) {
            listaProEnviosInt = new ArrayList<>();
        }
        return listaProEnviosInt;
    }

    public void setListaProEnviosInt(List<Contenedor> listaProEnviosInt) {
        this.listaProEnviosInt = listaProEnviosInt;
    }

    public Integer getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(Integer tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public Boolean getFlgFecha() {
        return flgFecha;
    }

    public void setFlgFecha(Boolean flgFecha) {
        this.flgFecha = flgFecha;
    }

}
