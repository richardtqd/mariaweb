/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.EntidadBean;
import pe.marista.sigma.bean.ProcesoFilesBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.EntidadService;
import pe.marista.sigma.service.ProcesoFilesService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import javax.faces.context.FacesContext;
import pe.marista.sigma.bean.CajaGenBean;
import pe.marista.sigma.bean.CuentasPorCobrarBean;
import pe.marista.sigma.service.CajaGenService;
import pe.marista.sigma.service.ProcesoFinalService;

/**
 *
 * @author MS002
 */
public class ProcesoFilesMB implements Serializable {

    /**
     * Creates a new instance of ProcesoFilesMB
     */
    @PostConstruct
    public void ProcesoFilesMB() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            obtenerProcesoFiles();

            //Obteniendo Lista de Entidades
            EntidadService entidadService = BeanFactory.getEntidadService();
            CajaGenBean cajaGenBean = new CajaGenBean();
            getListaEntidadBean();
            cajaGenBean.setAyudaBanco(true);
            cajaGenBean.getUniNeg().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            listaEntidadBean = entidadService.obtenerFlgFinanciero(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            CajaGenService cajaGenService = BeanFactory.getCajaGenService();
            listaEntidadBean = cajaGenService.obtenerBancosDeposito(cajaGenBean);

            //Obteniendo Tipo de Archivos
            CodigoService codigoService = BeanFactory.getCodigoService();
            getListaTipoArchivo();
            listaTipoArchivo = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.Tip_Archivo));

            getListaTipoDefecto();
            listaTipoDefecto = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_Default));

            //Obteniendo Tipos de Files
            obtenerCodigoTipoFile();

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
    //ProcesosFiles
    private List<ProcesoFilesBean> listaProcesoFilesBean;
    private List<ProcesoFilesBean> listaProcesoFilesDetBean;
    private List<ProcesoFilesBean> listaProcesoFilesIntDetBean;
    private List<ProcesoFilesBean> listaProcesoFilesDetDetBean;
    private ProcesoFilesBean procesoFilesBean;
    private ProcesoFilesBean procesoFilesDetaBean;
    private List<EntidadBean> listaEntidadBean;
    private List<CodigoBean> listaTipoFile;
    private List<CodigoBean> listaTipoDefecto;
    private String nombreFilePadre;
    private List<CodigoBean> listaTipoArchivo;

    //Listas de Edicion
    private List<ProcesoFilesBean> listaCabecera;
    private List<ProcesoFilesBean> listaDetalle;
    private List<ProcesoFilesBean> listaIntermedio;
    private ProcesoFilesBean fileDetaBean;

    //Ayudas
    private Integer valDel;
    private Integer flgPro;
    private Integer idpadre;
    private String ruc;
    private String nombre;

    public List<ProcesoFilesBean> getListaProcesoFilesBean() {
        if (listaProcesoFilesBean == null) {
            listaProcesoFilesBean = new ArrayList<>();
        }
        return listaProcesoFilesBean;
    }

    public void setListaProcesoFilesBean(List<ProcesoFilesBean> listaProcesoFilesBean) {
        this.listaProcesoFilesBean = listaProcesoFilesBean;
    }

    public ProcesoFilesBean getProcesoFilesBean() {
        if (procesoFilesBean == null) {
            procesoFilesBean = new ProcesoFilesBean();
        }
        return procesoFilesBean;
    }

    public void setProcesoFilesBean(ProcesoFilesBean procesoFilesBean) {
        this.procesoFilesBean = procesoFilesBean;
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

    public ProcesoFilesBean getProcesoFilesDetaBean() {
        if (procesoFilesDetaBean == null) {
            procesoFilesDetaBean = new ProcesoFilesBean();
        }
        return procesoFilesDetaBean;
    }

    public void setProcesoFilesDetaBean(ProcesoFilesBean procesoFilesDetaBean) {
        this.procesoFilesDetaBean = procesoFilesDetaBean;
    }

    public List<ProcesoFilesBean> getListaProcesoFilesDetBean() {
        if (listaProcesoFilesDetBean == null) {
            listaProcesoFilesDetBean = new ArrayList<>();
        }
        return listaProcesoFilesDetBean;
    }

    public void setListaProcesoFilesDetBean(List<ProcesoFilesBean> listaProcesoFilesDetBean) {
        this.listaProcesoFilesDetBean = listaProcesoFilesDetBean;
    }

    public String getNombreFilePadre() {
        return nombreFilePadre;
    }

    public void setNombreFilePadre(String nombreFilePadre) {
        this.nombreFilePadre = nombreFilePadre;
    }

    public List<CodigoBean> getListaTipoFile() {
        if (listaTipoFile == null) {
            listaTipoFile = new ArrayList<>();
        }
        return listaTipoFile;
    }

    public void setListaTipoFile(List<CodigoBean> listaTipoFile) {
        this.listaTipoFile = listaTipoFile;
    }

    public List<ProcesoFilesBean> getListaProcesoFilesDetDetBean() {
        if (listaProcesoFilesDetDetBean == null) {
            listaProcesoFilesDetDetBean = new ArrayList<>();
        }
        return listaProcesoFilesDetDetBean;
    }

    public void setListaProcesoFilesDetDetBean(List<ProcesoFilesBean> listaProcesoFilesDetDetBean) {
        this.listaProcesoFilesDetDetBean = listaProcesoFilesDetDetBean;
    }

    public List<ProcesoFilesBean> getListaCabecera() {
        if (listaCabecera == null) {
            listaCabecera = new ArrayList<>();
        }
        return listaCabecera;
    }

    public void setListaCabecera(List<ProcesoFilesBean> listaCabecera) {
        this.listaCabecera = listaCabecera;
    }

    public List<ProcesoFilesBean> getListaDetalle() {
        if (listaDetalle == null) {
            listaDetalle = new ArrayList<>();
        }
        return listaDetalle;
    }

    public void setListaDetalle(List<ProcesoFilesBean> listaDetalle) {
        this.listaDetalle = listaDetalle;
    }

    public Integer getValDel() {
        return valDel;
    }

    public void setValDel(Integer valDel) {
        this.valDel = valDel;
    }

    public ProcesoFilesBean getFileDetaBean() {
        if (fileDetaBean == null) {
            fileDetaBean = new ProcesoFilesBean();
        }
        return fileDetaBean;
    }

    public void setFileDetaBean(ProcesoFilesBean fileDetaBean) {
        this.fileDetaBean = fileDetaBean;
    }

    public Integer getFlgPro() {
        return flgPro;
    }

    public void setFlgPro(Integer flgPro) {
        this.flgPro = flgPro;
    }

    public List<CodigoBean> getListaTipoArchivo() {
        if (listaTipoArchivo == null) {
            listaTipoArchivo = new ArrayList<>();
        }
        return listaTipoArchivo;
    }

    public void setListaTipoArchivo(List<CodigoBean> listaTipoArchivo) {
        this.listaTipoArchivo = listaTipoArchivo;
    }

    public Integer getIdpadre() {
        return idpadre;
    }

    public void setIdpadre(Integer idpadre) {
        this.idpadre = idpadre;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<ProcesoFilesBean> getListaProcesoFilesIntDetBean() {
        if (listaProcesoFilesIntDetBean == null) {
            listaProcesoFilesIntDetBean = new ArrayList<>();
        }
        return listaProcesoFilesIntDetBean;
    }

    public void setListaProcesoFilesIntDetBean(List<ProcesoFilesBean> listaProcesoFilesIntDetBean) {
        this.listaProcesoFilesIntDetBean = listaProcesoFilesIntDetBean;
    }

    public List<ProcesoFilesBean> getListaIntermedio() {
        if (listaIntermedio == null) {
            listaIntermedio = new ArrayList<>();
        }
        return listaIntermedio;
    }

    public void setListaIntermedio(List<ProcesoFilesBean> listaIntermedio) {
        this.listaIntermedio = listaIntermedio;
    }

    public List<CodigoBean> getListaTipoDefecto() {
        if (listaTipoDefecto == null) {
            listaTipoDefecto = new ArrayList<>();
        }
        return listaTipoDefecto;
    }

    public void setListaTipoDefecto(List<CodigoBean> listaTipoDefecto) {
        this.listaTipoDefecto = listaTipoDefecto;
    }

//Proceso File do
    public void obtenerProcesoFiles() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
            listaProcesoFilesBean = procesoFilesService.obtenerProcesosFiles(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerCodigoTipoFile() {
        try {
            CodigoService codigoService = BeanFactory.getCodigoService();
            listaTipoFile = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.Tip_File));
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarProcesoFiles() {
        try {
            procesoFilesBean = new ProcesoFilesBean();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarFilesDeta() {
        try {
            procesoFilesDetaBean = new ProcesoFilesBean();
            procesoFilesDetaBean.setIdFilePadre(procesoFilesBean.getIdFile());
            procesoFilesDetaBean.getEntidadBean().setRuc(procesoFilesBean.getEntidadBean().getRuc());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String insertarProcesosFiles() {
        String pagina = null;
        try {
            if (pagina == null) {
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
                procesoFilesService.insertarProcesosFilesBanco(procesoFilesBean);
                listaProcesoFilesBean = procesoFilesService.obtenerProcesosFiles(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                limpiarFilesDeta();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarProcesosFiles() {
        String pagina = null;
        try {
            if (pagina == null) {
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
                procesoFilesBean.setModiPor(beanUsuarioSesion.getUsuario());
                procesoFilesService.modificarSuperFile(procesoFilesBean);
                listaProcesoFilesBean = procesoFilesService.obtenerProcesosFiles(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                limpiarFilesDeta();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void guardarFiles() {
        try {
            if (procesoFilesBean.getIdFile() != null) {
                modificarProcesosFiles();
            } else {
                insertarProcesosFiles();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerSuperFile(SelectEvent event) {
        try {
            procesoFilesBean = (ProcesoFilesBean) event.getObject();
            ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            procesoFilesBean = procesoFilesService.obtenerDetaFilesId(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerResource(Integer resouce) {
        try {
            procesoFilesDetaBean = new ProcesoFilesBean();
            flgPro = resouce;
            obtenerDetallesFile(procesoFilesBean, resouce);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerDetallesFile(Object object, Integer flgPro) {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
            if (flgPro.equals(0)) {
                procesoFilesBean = (ProcesoFilesBean) object;
                idpadre = procesoFilesBean.getIdFile();
                ruc = procesoFilesBean.getEntidadBean().getRuc();
                nombre = procesoFilesBean.getNombre();
                listaProcesoFilesDetBean = procesoFilesService.obtenerProcesosFilesDetaCabPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), idpadre, flgPro);
            }
            if (flgPro.equals(1)) {
                procesoFilesDetaBean = new ProcesoFilesBean();
                procesoFilesDetaBean.setIdFilePadre(idpadre);
                procesoFilesDetaBean.getEntidadBean().setRuc(ruc);
                listaProcesoFilesDetBean = procesoFilesService.obtenerProcesosFilesDetaCabPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), idpadre, flgPro);
                listaProcesoFilesIntDetBean = procesoFilesService.obtenerProcesosFilesIntDetPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), idpadre, flgPro);
                listaProcesoFilesDetDetBean = procesoFilesService.obtenerProcesosFilesDetaDetPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), idpadre, flgPro);
            }
            if (flgPro.equals(2)) {
                procesoFilesDetaBean = new ProcesoFilesBean();
                procesoFilesDetaBean.setIdFilePadre(idpadre);
                procesoFilesDetaBean.getEntidadBean().setRuc(ruc);
                listaProcesoFilesDetBean = procesoFilesService.obtenerProcesosFilesDetaCabPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), idpadre, flgPro);
                listaProcesoFilesIntDetBean = procesoFilesService.obtenerProcesosFilesIntDetPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), idpadre, flgPro);
                listaProcesoFilesDetDetBean = procesoFilesService.obtenerProcesosFilesDetaDetPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), idpadre, flgPro);
            }
            CodigoService codigoService = BeanFactory.getCodigoService();
            CodigoBean codigoTipoFileCab = new CodigoBean();
            codigoTipoFileCab = codigoService.obtenerPorCodigo(new CodigoBean(20001, "Cabecera", new TipoCodigoBean(MaristaConstantes.file_Cabecera)));
            CodigoBean codigoTipoFileDet = new CodigoBean();
            codigoTipoFileDet = codigoService.obtenerPorCodigo(new CodigoBean(20002, "Detalle", new TipoCodigoBean(MaristaConstantes.file_Detalle)));
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerSuper(Object object) {
        try {
            procesoFilesBean = (ProcesoFilesBean) object;
            ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            procesoFilesBean = procesoFilesService.obtenerDetaFilesId(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String eliminarFile() {
        String pagina = null;
        try {
            if (pagina == null) {
                ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                procesoFilesService.eliminarProcesosFile(procesoFilesBean);
                listaProcesoFilesBean = procesoFilesService.obtenerProcesosFiles(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    //Detalles
    public void insertarProcesosFilesDeta() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
            Integer posFin = 0, posicion = 0, posini = 0, posfin = 0;
            posFin = procesoFilesService.obtenerMaxPosFin(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getEntidadBean().getRuc());
//            if (posFin > procesoFilesDetaBean.getPosicionIni()) {
//                FacesMessage msg = new FacesMessage("Ingresar numero mayor al último registro ", null);
//                FacesContext.getCurrentInstance().addMessage(null, msg);
//            } else {
            posFin = procesoFilesService.obtenerMaxPosFin(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getEntidadBean().getRuc());
            posicion = procesoFilesService.obtenerUltimaPosicion(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesDetaBean.getTipoFile().getIdCodigo(), procesoFilesBean.getEntidadBean().getRuc(), flgPro);
            posini = posicion + 1;
            posfin = (procesoFilesDetaBean.getLongitud() - 1) + posini;
            if (procesoFilesDetaBean.getLongitud() != 0 && procesoFilesBean.getLongitud() != null) {
                procesoFilesDetaBean.setPosicionIni(posini);
                procesoFilesDetaBean.setPosicionFin(posfin);
                procesoFilesDetaBean.setFlgProceso(flgPro);
                procesoFilesDetaBean.getTipoDato().setIdCodigo(procesoFilesDetaBean.getDato());
                procesoFilesDetaBean.setPosicionValor(getProcesoFilesDetaBean().getPosicionValor());
                procesoFilesDetaBean.setComplemento(getProcesoFilesDetaBean().getComplemento());
                procesoFilesDetaBean.setTipoValorEstado(getProcesoFilesDetaBean().getTipoValorEstado());
                procesoFilesDetaBean.setConstante(getProcesoFilesDetaBean().getConstante());
                procesoFilesService.insertarProcesosFilesDetaBanco(procesoFilesDetaBean, procesoFilesBean, flgPro);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                listaProcesoFilesDetBean = procesoFilesService.obtenerProcesosFilesDetaCabPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);
                listaProcesoFilesIntDetBean = procesoFilesService.obtenerProcesosFilesIntDetPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);
                listaProcesoFilesDetDetBean = procesoFilesService.obtenerProcesosFilesDetaDetPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);
                //Verificando Codigos
                CodigoService codigoService = BeanFactory.getCodigoService();
                CodigoBean codigoTipoFileCab = new CodigoBean();
                codigoTipoFileCab = codigoService.obtenerPorCodigo(new CodigoBean(20001, "Cabecera", new TipoCodigoBean(MaristaConstantes.file_Cabecera)));
            } else {
                listaProcesoFilesDetBean = procesoFilesService.obtenerProcesosFilesDetaCabPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);
                listaProcesoFilesDetDetBean = procesoFilesService.obtenerProcesosFilesDetaDetPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);
                RequestContext.getCurrentInstance().addCallbackParam("error", true);
            }

//            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cambiarFile() {
        try {
            System.out.println("");
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            procesoFilesDetaBean.getTipoDato().setIdCodigo(procesoFilesDetaBean.getDato());
            if (procesoFilesDetaBean.getTipoDato().getIdCodigo() != null) {
                guardarDetaFiles();
            }
            Integer var = 0;
            if (procesoFilesDetaBean.getTipoFile().getIdCodigo().equals(20001)) {
                var = 1;
            } else {
                if (procesoFilesDetaBean.getTipoFile().getIdCodigo().equals(20002)) {
                    var = 2;
                } else {
                    if (procesoFilesDetaBean.getTipoFile().getIdCodigo().equals(20003)) {
                        var = 3;
                    }
                }
            }
            ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
//            procesoFinalService.eliminarFile(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getEntidadBean().getRuc(), var);
            limpiarFilesDeta();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String actualizarProcesosFilesDeta() {
        String pagina = null;
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
            if (procesoFilesDetaBean.getPosicionFin() > procesoFilesDetaBean.getPosicionIni() || procesoFilesDetaBean.getPosicionFin().equals(procesoFilesDetaBean.getPosicionIni())) {
                procesoFilesDetaBean.setModiPor(beanUsuarioSesion.getUsuario());
                Integer longitud = 0;
                longitud = procesoFilesDetaBean.getPosicionFin() - procesoFilesDetaBean.getPosicionIni();
                procesoFilesDetaBean.setLongitud(longitud);
                procesoFilesService.modificarProcesosFiles(procesoFilesDetaBean);
                listaProcesoFilesDetBean = procesoFilesService.obtenerProcesosFilesDetaCabPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);
                listaProcesoFilesDetDetBean = procesoFilesService.obtenerProcesosFilesDetaDetPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);
                limpiarFilesDeta();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            } else {
                listaProcesoFilesDetBean = procesoFilesService.obtenerProcesosFilesDetaCabPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);
                listaProcesoFilesDetDetBean = procesoFilesService.obtenerProcesosFilesDetaDetPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);
                RequestContext.getCurrentInstance().addCallbackParam("error", true);
                limpiarFilesDeta();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void guardarDetaFiles() {
        try {
            if (procesoFilesDetaBean.getIdFile() == null) {
                insertarProcesosFilesDeta();
            } else {
                actualizarProcesosFilesDeta();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ponerDato() {
        try {
            System.out.println(">>>" + procesoFilesDetaBean.getDato());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ponerFile() {
        try {
            procesoFilesDetaBean.getTipoFile().setIdCodigo(procesoFilesDetaBean.getFile());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ponerDefecto() {
        try {
            procesoFilesDetaBean.setIdDefecto(procesoFilesDetaBean.getIdDefecto());
            System.out.println(procesoFilesDetaBean.getIdDefecto());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ponerLong() {
        try {
            System.out.println(">>>" + procesoFilesDetaBean.getLongitud());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ponerNombreDes() {
        try {
            System.out.println(">>>" + procesoFilesDetaBean.getNombre());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ponerDescrip() {
        try {
            System.out.println(">>>" + procesoFilesDetaBean.getDescripcion());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String eliminarFileDeta() {
        String pagina = null;
        try {
            if (pagina == null) {
                CodigoService codigoService = BeanFactory.getCodigoService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                CodigoBean codigoTipoFileCab = new CodigoBean();
                CodigoBean codigoTipoFileInt = new CodigoBean();
                codigoTipoFileCab = codigoService.obtenerPorCodigo(new CodigoBean(20001, "Cabecera", new TipoCodigoBean(MaristaConstantes.file_Cabecera)));
                codigoTipoFileInt = codigoService.obtenerPorCodigo(new CodigoBean(20003, "Intermedio", new TipoCodigoBean(MaristaConstantes.file_Intermedio)));
                ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
                procesoFilesService.eliminarProcesosFile(fileDetaBean);
                Integer var = 0;
                if (fileDetaBean.getTipoFile().getIdCodigo().equals(20001)) {
                    var = 1;
                } else {
                    if (fileDetaBean.getTipoFile().getIdCodigo().equals(20002)) {
                        var = 2;
                    } else {
                        if (fileDetaBean.getTipoFile().getIdCodigo().equals(20003)) {
                            var = 3;
                        }
                    }
                }
                ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
                procesoFinalService.eliminarFile(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getEntidadBean().getRuc(), var);
                if (fileDetaBean.getTipoFile().getIdCodigo().equals(codigoTipoFileCab.getIdCodigo())) {
                    listaCabecera = procesoFilesService.obtenerProcesosFilesDetaCabPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);
                    listaProcesoFilesDetBean = procesoFilesService.obtenerProcesosFilesDetaCabPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);
                } else {
                    if (fileDetaBean.getTipoFile().getIdCodigo().equals(codigoTipoFileInt.getIdCodigo())) {
                        listaIntermedio = procesoFilesService.obtenerProcesosFilesIntDetPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);
                        listaProcesoFilesIntDetBean = procesoFilesService.obtenerProcesosFilesIntDetPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);
                    } else {
                        listaDetalle = procesoFilesService.obtenerProcesosFilesDetaDetPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);
                        listaProcesoFilesDetDetBean = procesoFilesService.obtenerProcesosFilesDetaDetPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);
                    }
                }
                listaProcesoFilesDetBean = new ArrayList<>();
                listaProcesoFilesDetDetBean = new ArrayList<>();
                listaProcesoFilesIntDetBean = new ArrayList<>();
                listaProcesoFilesDetBean = procesoFilesService.obtenerProcesosFilesDetaCabPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);
                listaProcesoFilesIntDetBean = procesoFilesService.obtenerProcesosFilesIntDetPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);
                listaProcesoFilesDetDetBean = procesoFilesService.obtenerProcesosFilesDetaDetPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    //Eventos Select
    public void onSelectFileCab(SelectEvent event) {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
            procesoFilesDetaBean = procesoFilesService.obtenerDetaFilesId(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), Integer.parseInt(event.getObject().toString()));
            System.out.println(event.getObject().toString());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void onSelectFileDet(SelectEvent event) {
        try {
            System.out.println(event.getObject().toString());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerProcesoFile(Integer resource) {
        try {
            System.out.println(">>>" + resource);
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
            if (resource.equals(1)) {
                listaCabecera = procesoFilesService.obtenerProcesosFilesDetaCabPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);
            } else {
                if (resource.equals(0)) {
                    listaDetalle = procesoFilesService.obtenerProcesosFilesDetaDetPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);
                } else {
                    if (resource.equals(2)) {
                        listaIntermedio = procesoFilesService.obtenerProcesosFilesIntDetPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);
                    }
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerFileId(Object object) {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
            fileDetaBean = (ProcesoFilesBean) object;
            fileDetaBean = procesoFilesService.obtenerDetaFilesId(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), fileDetaBean.getIdFile());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------------
    //Posicionamiento Cabecera DEF
    public void posicionarFileCab() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
            Integer posicion = 0;
            Integer file = 0;
            Integer tipoFile = 0;
            Integer k = 0;
            for (int i = 0; i < listaProcesoFilesDetBean.size(); i++) {
                Object objeto = listaProcesoFilesDetBean.get(i);
                Integer idFile = new Integer(objeto.toString());
                procesoFilesDetaBean = procesoFilesService.obtenerDetaFilesId(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), idFile);
                file = procesoFilesDetaBean.getIdFile();
                posicion = procesoFilesDetaBean.getPosicionItem();
                tipoFile = procesoFilesDetaBean.getTipoFile().getIdCodigo();
                k = i + 1;
                actualizarPosiciones(procesoFilesDetaBean.getPosicionItem(), procesoFilesDetaBean.getIdFile(), procesoFilesDetaBean.getTipoFile().getIdCodigo(), k);
            }
            valDel = 1;//File Cabecera
            moverPosiciones(valDel);
            listaProcesoFilesDetBean = procesoFilesService.obtenerProcesosFilesDetaCabPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);
            listaProcesoFilesIntDetBean = procesoFilesService.obtenerProcesosFilesIntDetPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);
            listaProcesoFilesDetDetBean = procesoFilesService.obtenerProcesosFilesDetaDetPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
    //END

    //Posicionamiento Detalle DEF
    public void posicionarFileInt() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
            Integer posicion = 0;
            Integer file = 0;
            Integer tipoFile = 0;
            Integer k = 0;
            System.out.println(">>>" + listaProcesoFilesIntDetBean.size());
            for (int i = 0; i < listaProcesoFilesIntDetBean.size(); i++) {
                Object objeto = listaProcesoFilesIntDetBean.get(i);
                Integer idFile = new Integer(objeto.toString());
                procesoFilesDetaBean = procesoFilesService.obtenerDetaFilesId(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), idFile);
                file = procesoFilesDetaBean.getIdFile();
                posicion = procesoFilesDetaBean.getPosicionItem();
                tipoFile = procesoFilesDetaBean.getTipoFile().getIdCodigo();
                k = i + 1;
                actualizarPosiciones(procesoFilesDetaBean.getPosicionItem(), procesoFilesDetaBean.getIdFile(), procesoFilesDetaBean.getTipoFile().getIdCodigo(), k);
            }
            valDel = 2;//File Detalle
            moverPosiciones(valDel);
            listaProcesoFilesIntDetBean = procesoFilesService.obtenerProcesosFilesDetaDetPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            listaProcesoFilesDetBean = procesoFilesService.obtenerProcesosFilesDetaCabPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);
            listaProcesoFilesIntDetBean = procesoFilesService.obtenerProcesosFilesIntDetPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);
            listaProcesoFilesDetDetBean = procesoFilesService.obtenerProcesosFilesDetaDetPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //Posicionamiento Detalle DEF
    public void posicionarFileDet() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
            Integer posicion = 0;
            Integer file = 0;
            Integer tipoFile = 0;
            Integer k = 0;
            for (int i = 0; i < listaProcesoFilesDetDetBean.size(); i++) {
                Object objeto = listaProcesoFilesDetDetBean.get(i);
                Integer idFile = new Integer(objeto.toString());
                procesoFilesDetaBean = procesoFilesService.obtenerDetaFilesId(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), idFile);
                file = procesoFilesDetaBean.getIdFile();
                posicion = procesoFilesDetaBean.getPosicionItem();
                tipoFile = procesoFilesDetaBean.getTipoFile().getIdCodigo();
                k = i + 1;
                actualizarPosiciones(procesoFilesDetaBean.getPosicionItem(), procesoFilesDetaBean.getIdFile(), procesoFilesDetaBean.getTipoFile().getIdCodigo(), k);
            }
            valDel = 0;//File Detalle
            moverPosiciones(valDel);
            listaProcesoFilesDetBean = procesoFilesService.obtenerProcesosFilesDetaCabPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);
            listaProcesoFilesIntDetBean = procesoFilesService.obtenerProcesosFilesIntDetPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);
            listaProcesoFilesDetDetBean = procesoFilesService.obtenerProcesosFilesDetaDetPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
    //END

    //Actualizando Posiciones DEF
    public void actualizarPosiciones(Integer posicion, Integer file, Integer tipoFile, Integer k) {
        try {
            List<ProcesoFilesBean> listaCabecera = new ArrayList<>();
            List<ProcesoFilesBean> listaDetalle = new ArrayList<>();
            List<ProcesoFilesBean> listaIntermedio = new ArrayList<>();
            Integer ini = 0;
            Integer fin = 0;
            //Verificando Codigos
            CodigoService codigoService = BeanFactory.getCodigoService();
            CodigoBean codigoTipoFileCab = new CodigoBean();
            CodigoBean codigoTipoFiltInt = new CodigoBean();
            codigoTipoFileCab = codigoService.obtenerPorCodigo(new CodigoBean(20001, "Cabecera", new TipoCodigoBean(MaristaConstantes.file_Cabecera)));
            codigoTipoFiltInt = codigoService.obtenerPorCodigo(new CodigoBean(20003, "Intermedio", new TipoCodigoBean(MaristaConstantes.file_Intermedio)));
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
            listaCabecera = procesoFilesService.obtenerProcesosFilesDetaCabPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);
            listaDetalle = procesoFilesService.obtenerProcesosFilesDetaDetPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);
            listaIntermedio = procesoFilesService.obtenerProcesosFilesIntDetPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);
            if (tipoFile.equals(codigoTipoFileCab.getIdCodigo())) {
                for (int j = 0; j < listaCabecera.size(); j++) {
                    if (listaCabecera.get(j).getIdFile().equals(file)) {
                        listaCabecera.get(j).setPosicionItem(k);
                        listaCabecera.get(j).setModiPor(beanUsuarioSesion.getUsuario());
                        procesoFilesService.modificarPosicionFiles(listaCabecera.get(j));
                        break;
                    }
                }
            } else {
                if (tipoFile.equals(codigoTipoFiltInt.getIdCodigo())) {
                    for (int i = 0; i < listaIntermedio.size(); i++) {
                        if (listaIntermedio.get(i).getIdFile().equals(file)) {
                            listaIntermedio.get(i).setPosicionItem(k);
                            listaIntermedio.get(i).setModiPor(beanUsuarioSesion.getUsuario());
                            procesoFilesService.modificarPosicionFiles(listaIntermedio.get(i));
                            break;
                        }
                    }
                } else {
                    for (int i = 0; i < listaDetalle.size(); i++) {
                        if (listaDetalle.get(i).getIdFile().equals(file)) {
                            listaDetalle.get(i).setPosicionItem(k);
                            listaDetalle.get(i).setModiPor(beanUsuarioSesion.getUsuario());
                            procesoFilesService.modificarPosicionFiles(listaDetalle.get(i));
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
    //END

    public void moverPosiciones(Integer valDel) {
        try {
            Integer ini = 1;
            Integer fin = 1;
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
            List<ProcesoFilesBean> listaCabeceraFiles = new ArrayList<>();
            List<ProcesoFilesBean> listaDetallesFiles = new ArrayList<>();
            List<ProcesoFilesBean> listaIntFiles = new ArrayList<>();

            listaCabeceraFiles = procesoFilesService.obtenerProcesosFilesDetaCabPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);
            listaDetallesFiles = procesoFilesService.obtenerProcesosFilesDetaDetPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);
            listaIntFiles = procesoFilesService.obtenerProcesosFilesIntDetPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);;

            if (valDel.equals(1)) {
                System.out.println("Cabecera");
                for (int m = 0; m < listaCabeceraFiles.size(); m++) {
                    ini = fin;
                    fin = fin + listaCabeceraFiles.get(m).getLongitud() - 1;
                    listaCabeceraFiles.get(m).setPosicionIni(ini);
                    listaCabeceraFiles.get(m).setPosicionFin(fin);
                    listaCabeceraFiles.get(m).setModiPor(beanUsuarioSesion.getUsuario());
                    procesoFilesService.modificarPosicionesFiles(listaCabeceraFiles.get(m));
                    fin++;
                }
            } else {
                if (valDel.equals(0)) {
                    System.out.println("Detalle");
                    for (int n = 0; n < listaDetallesFiles.size(); n++) {
                        ini = fin;
                        fin = fin + listaDetallesFiles.get(n).getLongitud() - 1;
                        listaDetallesFiles.get(n).setPosicionIni(ini);
                        listaDetallesFiles.get(n).setPosicionFin(fin);
                        listaDetallesFiles.get(n).setModiPor(beanUsuarioSesion.getUsuario());
                        procesoFilesService.modificarPosicionesFiles(listaDetallesFiles.get(n));
                        fin++;
                    }
                } else {
                    if (valDel.equals(2)) {
                        System.out.println("Intermedio");
                        for (int n = 0; n < listaIntFiles.size(); n++) {
                            ini = fin;
                            fin = fin + listaIntFiles.get(n).getLongitud() - 1;
                            listaIntFiles.get(n).setPosicionIni(ini);
                            listaIntFiles.get(n).setPosicionFin(fin);
                            listaIntFiles.get(n).setModiPor(beanUsuarioSesion.getUsuario());
                            procesoFilesService.modificarPosicionesFiles(listaIntFiles.get(n));
                            fin++;
                        }
                    }
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------------
    public void editarFile() {

    }

    public void cancelEditFile() {

    }

    public void onRowEditCab(RowEditEvent event) {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
            Integer ini = 0;
            Integer fin = 0;
            Integer res = 0;
            Integer response = 0;
            Integer compare = 0;
            ini = ((ProcesoFilesBean) event.getObject()).getPosicionIni();
            fin = ((ProcesoFilesBean) event.getObject()).getPosicionFin();
            res = fin - ini;
            if (fin > ini || fin.equals(ini)) {
                procesoFilesDetaBean = new ProcesoFilesBean();
                procesoFilesDetaBean.setNombre(((ProcesoFilesBean) event.getObject()).getNombre());
                procesoFilesDetaBean.setPosicionIni(((ProcesoFilesBean) event.getObject()).getPosicionIni());
                procesoFilesDetaBean.setPosicionFin(((ProcesoFilesBean) event.getObject()).getPosicionFin());
                procesoFilesDetaBean.setDescripcion(((ProcesoFilesBean) event.getObject()).getDescripcion());
                procesoFilesDetaBean.setLongitud(res + 1);
                procesoFilesDetaBean.setIdDefecto(((ProcesoFilesBean) event.getObject()).getIdDefecto());
                procesoFilesDetaBean.getTipoDato().setIdCodigo(((ProcesoFilesBean) event.getObject()).getTipoDato().getIdCodigo());
                procesoFilesDetaBean.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                procesoFilesDetaBean.getEntidadBean().setRuc(((ProcesoFilesBean) event.getObject()).getEntidadBean().getRuc());
                procesoFilesDetaBean.setIdDefecto(((ProcesoFilesBean) event.getObject()).getIdDefecto());
                procesoFilesDetaBean.setIdFile(((ProcesoFilesBean) event.getObject()).getIdFile());
                procesoFilesDetaBean.setPosicionValor(((ProcesoFilesBean) event.getObject()).getPosicionValor());
                procesoFilesDetaBean.setTipoValorEstado(((ProcesoFilesBean) event.getObject()).getTipoValorEstado());
                procesoFilesDetaBean.setConstante(((ProcesoFilesBean) event.getObject()).getConstante());
                procesoFilesDetaBean.setComplemento(((ProcesoFilesBean) event.getObject()).getComplemento());
                procesoFilesService.modificarCabeceraFiles(procesoFilesDetaBean);
                listaCabecera = procesoFilesService.obtenerProcesosFilesDetaCabPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);
                listaDetalle = procesoFilesService.obtenerProcesosFilesDetaDetPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);
                listaIntermedio = procesoFilesService.obtenerProcesosFilesIntDetPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);
                if (((ProcesoFilesBean) event.getObject()).getTipoFile().getIdCodigo().equals(20001)) {
                    response = 1;
                } else {
                    if (((ProcesoFilesBean) event.getObject()).getTipoFile().getIdCodigo().equals(20002)) {
                        response = 2;
                    } else {
                        if (((ProcesoFilesBean) event.getObject()).getTipoFile().getIdCodigo().equals(20003)) {
                            response = 3;
                        } else {
                            response = 0;
                        }
                    }
                }
                System.out.println("res: " + response);
                ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
//                procesoFinalService.eliminarFile(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getEntidadBean().getRuc(), response);
                limpiarFilesDeta();
                FacesMessage msg = new FacesMessage("Cambio exitoso: ", ((ProcesoFilesBean) event.getObject()).getNombre());
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                listaCabecera = procesoFilesService.obtenerProcesosFilesDetaCabPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);
                listaDetalle = procesoFilesService.obtenerProcesosFilesDetaDetPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoFilesBean.getIdFile(), flgPro);
                FacesMessage msg = new FacesMessage("Error de validación: Posicion Final debe ser menor a la inicial", null);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void onRowCancelCab(RowEditEvent event) {
        try {
            FacesMessage msg = new FacesMessage("Operacion Cancelada: ", ((ProcesoFilesBean) event.getObject()).getNombre());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ponerCons() {
        try {
            getProcesoFilesDetaBean().setTipoValorEstado(getProcesoFilesDetaBean().getTipoValorEstado());
            if (getProcesoFilesDetaBean().getTipoValorEstado().equals(1)) {
                getProcesoFilesDetaBean().setPosicionValorItem(false);
            } else {
                getProcesoFilesDetaBean().setPosicionValorItem(true);
            }
            System.out.println(">>>" + getProcesoFilesDetaBean().getTipoValorEstado());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

//end
}
