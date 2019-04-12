/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.Contenedor;
import pe.marista.sigma.bean.ProcesoBancoBean;
import pe.marista.sigma.bean.ProcesoEnvioBean;
import pe.marista.sigma.bean.ProcesoRecuperacionBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.ProcesoBancoService;
import pe.marista.sigma.service.ProcesoEnvioService;
import pe.marista.sigma.service.ProcesoFilesService;
import pe.marista.sigma.service.ProcesoFinalService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author JC
 */
public class BancoTallerMB extends BaseMB implements Serializable {

    @PostConstruct
    public void BancoTallerMB() {
        try {
            usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            GregorianCalendar fechaActual = new GregorianCalendar();
            getProcesoEnvioBean();
            getProcesoEnvioBean().getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getProcesoEnvioBean().setFechaIni(fechaActual.getTime());
            getProcesoEnvioBean().setFechaFin(fechaActual.getTime());
            getProcesoRecuperacionBean();
            getProcesoRecuperacionBean().getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getProcesoBancoBean();
            getProcesoBancoBean().setFecha(fechaActual.getTime());
            nombreFile = obtenerNombreArchivo(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), fechaActual.getTime());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //CLASES
    private UsuarioBean usuarioBean;
    private ProcesoBancoBean procesoBancoBean;
    private ProcesoEnvioBean procesoEnvioBean;
    private ProcesoRecuperacionBean procesoRecuperacionBean;

    //LISTAS    
    private List<ProcesoBancoBean> listaProcesoBancoBean;
    private List<Contenedor> listaProEnvios;

    //VARIABLES
    private Integer filas = 0;
    private Integer filasCab = 0;
    private Integer filasInt = 0;
    private Integer tipoProceso = 0;
    private Boolean flgTipo = false;
    private Integer idProcesoBanco;
    private StreamedContent content;
    private String nombreFile;

    /* METODOS */
    public void cargarDatos() {
        try {
            usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            GregorianCalendar fechaActual = new GregorianCalendar();
            getProcesoBancoBean();
            procesoBancoBean.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            procesoBancoBean.setFechaInicio(fechaActual.getTime());
            procesoBancoBean.setFechaFin(fechaActual.getTime());
//            procesoBancoBean.setFlgProceso(2);
            getProcesoEnvioBean().getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getProcesoEnvioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getProcesoEnvioBean().setCreaPor(usuarioBean.getUsuario());
            if (usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SANJOC)) {
                getProcesoEnvioBean().setRuc(MaristaConstantes.RUC_INTERBANK);
            } else if (usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)) {
                getProcesoEnvioBean().setRuc(MaristaConstantes.RUC_SCOTIABANK);
            } else if (usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SANLUI)) {
                getProcesoEnvioBean().setRuc(MaristaConstantes.RUC_BCP);
            }
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            idProcesoBanco = procesoBancoService.obtenerMaxIdProcesoBanco(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg()) + 1;
            getProcesoEnvioBean().setIdProcesoBanco(idProcesoBanco);
            /* OBTENIENDO TIPO DE PROCESO */
            getTipoProceso();
            setTipoProceso(1);
            obtenerTipoPro();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void filtrarProcesoTaller() {
        try {
            Integer res = 0;
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            if (procesoBancoBean.getFechaInicio() != null) {
                procesoBancoBean.setFechaInicio(procesoBancoBean.getFechaInicio());
                res = 1;
            }
            if (procesoBancoBean.getFechaFin() != null) {
                procesoBancoBean.setFechaFin(procesoBancoBean.getFechaFin());
                res = 1;
            }
            if (res == 1) {
                listaProcesoBancoBean = procesoBancoService.filtrarProceso(procesoBancoBean);
                if (listaProcesoBancoBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    listaProcesoBancoBean = new ArrayList<>();
                }
            } else {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaProcesoBancoBean = new ArrayList<>();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarFiltroBanco() {
        try {
            procesoBancoBean = new ProcesoBancoBean();
            cargarDatos();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
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

    public void obtenerTipoPro() {
        try {
            if (tipoProceso.equals(1)) {
                flgTipo = true;
            } else if (tipoProceso.equals(2)) {
                flgTipo = false;
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void filtrarEnvio() {
        try {
            Integer res = 0;
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            if (procesoEnvioBean.getFechaFin() != null) {
                procesoEnvioBean.setFechaFin(procesoEnvioBean.getFechaFin());
                res = 1;
            }
            if (procesoEnvioBean.getFechaIni() != null) {
                procesoEnvioBean.setFechaIni(procesoEnvioBean.getFechaIni());
                res = 1;
            }
            if (res == 1) {
                listaProEnvios = procesoEnvioService.obtenerEnvioTalleres(procesoEnvioBean);
                if (listaProEnvios.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    listaProEnvios = new ArrayList<>();
                } else if (!listaProEnvios.isEmpty()) {
                    ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
                    filas = procesoFilesService.obtenerNumFiles(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 2, MaristaConstantes.FileDetalle, getProcesoEnvioBean().getRuc());
                    filasCab = procesoFilesService.obtenerNumFiles(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 2, MaristaConstantes.FileCabecera, getProcesoEnvioBean().getRuc());
                    filasInt = procesoFilesService.obtenerNumFiles(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 2, MaristaConstantes.FileIntermedio, getProcesoEnvioBean().getRuc());
                }
            } else if (res == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaProEnvios = new ArrayList<>();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarFiltroTaller() {
        try {
            procesoEnvioBean = new ProcesoEnvioBean();
            cargarDatos();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String obtenerNombreArchivo(String uniNeg, Date fecha) {
        String nombre = "";
        try {
            String fechaProceso = "";
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            ProcesoBancoBean procesoBancoBean = new ProcesoBancoBean();
            procesoBancoBean.getUnidadNegocioBean().setUniNeg(uniNeg);
            procesoBancoBean.setFecha(fecha);
            procesoBancoBean.setFlgProceso(1);
            switch (uniNeg) {
                case MaristaConstantes.UNI_NEG_CHAMPS:
                    fechaProceso = procesoBancoService.obtenerFechaProceso(procesoBancoBean);
                    nombre = "COB".concat(fechaProceso);
                    break;
                case MaristaConstantes.UNI_NEG_SANJOC:
                    fechaProceso = procesoBancoService.obtenerFechaProceso(procesoBancoBean);
                    nombre = fechaProceso;
                    break;
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return nombre;
    }

    //DESCARGAR TXT
    public void mostrarFilesEnvioTalleres() {
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

            listaContenedorDeta = procesoFinalService.obtenerEnvioBancos(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), getProcesoEnvioBean().getRuc(), getProcesoEnvioBean().getIdProcesoBanco(), MaristaConstantes.FileDetalle, 3);
            listaContenedorInte = procesoFinalService.obtenerEnvioBancos(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), getProcesoEnvioBean().getRuc(), getProcesoEnvioBean().getIdProcesoBanco(), MaristaConstantes.FileIntermedio, 2);
            listaContenedorCabe = procesoFinalService.obtenerEnvioBancos(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), getProcesoEnvioBean().getRuc(), getProcesoEnvioBean().getIdProcesoBanco(), MaristaConstantes.FileCabecera, 1);
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
    
     public String eliminarMasivo() {
        String pagina = null;
        try {
            if (pagina == null) {
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                Integer codBanco;
                ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
                procesoBancoService.eliminarProcesoBancoMas(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getIdProcesoBanco());                
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public UsuarioBean getUsuarioBean() {
        if (usuarioBean == null) {
            usuarioBean = new UsuarioBean();
        }
        return usuarioBean;
    }

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
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

    public List<ProcesoBancoBean> getListaProcesoBancoBean() {
        if (listaProcesoBancoBean == null) {
            listaProcesoBancoBean = new ArrayList<>();
        }
        return listaProcesoBancoBean;
    }

    public void setListaProcesoBancoBean(List<ProcesoBancoBean> listaProcesoBancoBean) {
        this.listaProcesoBancoBean = listaProcesoBancoBean;
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

    public Integer getTipoProceso() {
        return tipoProceso;
    }

    public void setTipoProceso(Integer tipoProceso) {
        this.tipoProceso = tipoProceso;
    }

    public ProcesoEnvioBean getProcesoEnvioBean() {
        if (procesoEnvioBean == null) {
            procesoEnvioBean = new ProcesoEnvioBean();
        }
        return procesoEnvioBean;
    }

    public void setProcesoEnvioBean(ProcesoEnvioBean procesoEnvioBean) {
        this.procesoEnvioBean = procesoEnvioBean;
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

    public Boolean getFlgTipo() {
        return flgTipo;
    }

    public void setFlgTipo(Boolean flgTipo) {
        this.flgTipo = flgTipo;
    }

    public Integer getIdProcesoBanco() {
        return idProcesoBanco;
    }

    public void setIdProcesoBanco(Integer idProcesoBanco) {
        this.idProcesoBanco = idProcesoBanco;
    }

    public StreamedContent getContent() {
        return content;
    }

    public void setContent(StreamedContent content) {
        this.content = content;
    }

    public String getNombreFile() {
        return nombreFile;
    }

    public void setNombreFile(String nombreFile) {
        this.nombreFile = nombreFile;
    }

}
