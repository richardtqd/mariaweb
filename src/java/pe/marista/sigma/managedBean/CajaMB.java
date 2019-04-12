package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CajaBean;
import pe.marista.sigma.bean.ChequeBean;
import pe.marista.sigma.bean.DocIngresoSerieCajaBean;
import pe.marista.sigma.bean.ImpresoraBean;
import pe.marista.sigma.bean.ImpresoraCajaBean;
import pe.marista.sigma.bean.UnidadNegocioBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CajaService;
import pe.marista.sigma.service.ImpresoraService;
import pe.marista.sigma.service.UnidadNegocioService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author Administrador
 */
public class CajaMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of CajaMB
     */
    @PostConstruct
    public void CajaMB() {
        try {
            cargarLista();
            UnidadNegocioService unidadNegocioService = BeanFactory.getUnidadNegocioService();
            listaUnidadNegocioBean = unidadNegocioService.obtenerTodos();
            listaEstado = new LinkedHashMap<>();
            listaEstado.put(MaristaConstantes.ESTADO_ACTIVO_DES, MaristaConstantes.ESTADO_ACTIVO);
            listaEstado.put(MaristaConstantes.ESTADO_INACTIVO_DES, MaristaConstantes.ESTADO_INACTIVO);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }

    }
    private List<CajaBean> listaCajaBean;
    private List<UnidadNegocioBean> listaUnidadNegocioBean;
    private Map<String, Integer> listaEstado;
    private CajaBean cajaBean;
//    private DualListModel<DocIngresoSerieBean> dualListaDocIngresoSerie;
//    private List<DocIngresoSerieBean> listaDocIngresoSerieBean;
//    private List<DocIngresoSerieBean> listDocIngresoSerieDest;
//    private DocIngresoSerieCajaBean docIngresoSerieCajaBean;

    private DualListModel<ImpresoraBean> dualImpresora;
    private List<ImpresoraBean> listaImpresoraBean;
    private List<ImpresoraBean> listImpresoraDest;
    private ImpresoraCajaBean impresoraCajaBean;

    private ChequeBean chequeBean;

    //Metodos de aplicacion
    public final void cargarLista() {
        try {
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            listaImpresoraBean = new ArrayList<>();
            listImpresoraDest = new ArrayList<>();
            ImpresoraService impresoraService = BeanFactory.getImpresoraService();
            listaImpresoraBean = impresoraService.obtenerGrupoImpresoraActivos(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            dualImpresora = new DualListModel<>(listaImpresoraBean, listImpresoraDest);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerTodos() {
        try {
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            CajaService cajaService = BeanFactory.getCajaService();
            listaCajaBean = cajaService.obtenerTodos(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            cajaBean = new CajaBean();
            impresoraCajaBean = new ImpresoraCajaBean();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarCajabean() {
        cajaBean = new CajaBean();
        listaImpresoraBean = new ArrayList<>();
        listImpresoraDest = new ArrayList<>();
        cargarLista();
    }

    public void obtenerPorId(Integer idCaja) {
        try {
            CajaService cajaService = BeanFactory.getCajaService();
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            cajaBean.setIdCaja(idCaja);
            cajaBean.setUnidadNegocioBean(usuarioBean.getPersonalBean().getUnidadNegocioBean());
            cajaBean = cajaService.buscarPorId(cajaBean);
            if (cajaBean.getHostIp() != null) {
                String ip = cajaBean.getHostIp().replace(".", ":");
                String cadena[] = ip.split(":");
                cajaBean.setIp1(cadena[0]);
                cajaBean.setIp2(cadena[1]);
                cajaBean.setIp3(cadena[2]);
                cajaBean.setIp4(cadena[3]);
            }
            List<ImpresoraBean> listaImpresora = cajaService.obtenerImpresoraPorCaja(cajaBean);
//            List<DocIngresoSerieBean> listaDocIngresoSerie = cajaService.obtenerPorCaja(cajaBean);
            cargarLista();
            for (int i = 0; i < listaImpresora.size(); i++) {
                ImpresoraBean impresoraBean = new ImpresoraBean();
                impresoraBean.setUnidadNegocioBean(listaImpresora.get(i).getUnidadNegocioBean());
                impresoraBean.setIdTipoDoc(listaImpresora.get(i).getIdTipoDoc());
                impresoraBean.setImpresora(listaImpresora.get(i).getImpresora());
                listImpresoraDest.add(impresoraBean);
                for (int j = 0; j < listaImpresoraBean.size(); j++) {
                    if (listaImpresoraBean.get(j).getImpresora().equals(impresoraBean.getImpresora())
                            && listaImpresoraBean.get(j).getIdTipoDoc().getIdCodigo().equals(impresoraBean.getIdTipoDoc().getIdCodigo())
                            && listaImpresoraBean.get(j).getUnidadNegocioBean().getUniNeg().equals(impresoraBean.getUnidadNegocioBean().getUniNeg())) {
                        listaImpresoraBean.remove(j);
                    }
                    break;

                }
                dualImpresora = new DualListModel<>(listaImpresoraBean, listImpresoraDest);
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarCaja() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                cajaBean.setCreaPor(usuarioBean.getUsuario());
                cajaBean.setUnidadNegocioBean(usuarioBean.getPersonalBean().getUnidadNegocioBean());
                SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yy:HH:mm:SS");
                String date = formato.format(new Date());
                cajaBean.setCreaFecha(formato.parse(date));
                cajaBean.setHostIp(cajaBean.getIpCompleto());
                DocIngresoSerieCajaBean docIngresoSerieCaja = new DocIngresoSerieCajaBean();
                impresoraCajaBean.setStatus(cajaBean.getStatus());
                CajaService cajaService = BeanFactory.getCajaService();
                cajaService.insertarCaja(cajaBean, dualImpresora.getTarget(), impresoraCajaBean);
                listaCajaBean = cajaService.obtenerTodos(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                limpiarCajabean();
                cargarLista();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarCaja() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                CajaService cajaService = BeanFactory.getCajaService();
                cajaBean.setHostIp(cajaBean.getIpCompleto());
                cajaBean.setUnidadNegocioBean(usuarioBean.getPersonalBean().getUnidadNegocioBean());
                cajaService.modificarCaja(cajaBean, dualImpresora.getTarget());
                listaCajaBean = cajaService.obtenerTodos(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                limpiarCajabean();
                cargarLista();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void guardarCaja() {
        try {
            if (cajaBean.getIdCaja() == null) {
                insertarCaja();
            } else {
                modificarCaja();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }

    }

    public String eliminarCaja() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                CajaService cajaService = BeanFactory.getCajaService();
                cajaService.eliminarCaja(cajaBean);
                listaCajaBean = cajaService.obtenerTodos(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                limpiarCajabean();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String cambiarEstadoCaja() {
        String pagina = null;
        try {
            if (pagina == null) {
                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                CajaService cajaService = BeanFactory.getCajaService();
                cajaService.cambiarEstadoCaja(cajaBean);
                listaCajaBean = cajaService.obtenerTodos(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void ponerCaja(Object caja) {
        try {
            cajaBean = (CajaBean) caja;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void rowSelect(SelectEvent event) {
        try {
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            cajaBean = (CajaBean) event.getObject();
            cajaBean.setUnidadNegocioBean(usuarioBean.getPersonalBean().getUnidadNegocioBean());
            String ips = cajaBean.getHostIp();
            String ipPto[] = ips.split("\\.");
            cajaBean.setIp1(ipPto[0]);
            cajaBean.setIp2(ipPto[1]);
            cajaBean.setIp3(ipPto[2]);
            cajaBean.setIp4(ipPto[3]);
            CajaService cajaService = BeanFactory.getCajaService();
            ImpresoraService impresoraService = BeanFactory.getImpresoraService();
            List<ImpresoraBean> listaImpresora = cajaService.obtenerImpresoraPorCaja(cajaBean);
            cargarLista();
            for (int i = 0; i < listaImpresora.size(); i++) {
                ImpresoraBean impresoraBean = new ImpresoraBean();
                impresoraBean.setUnidadNegocioBean(listaImpresora.get(i).getUnidadNegocioBean());
                impresoraBean.setImpresora(listaImpresora.get(i).getImpresora());
//                impresoraBean = impresoraService.buscarPorId(impresoraBean);
                listImpresoraDest.add(impresoraBean);
                for (int j = 0; j < listaImpresoraBean.size(); j++) {
                    if (listaImpresoraBean.get(j).getImpresora().equals(impresoraBean.getImpresora())) {
                        listaImpresoraBean.remove(j);
                    }
                }
            }

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //GEtter y Setter
    public List<CajaBean> getListaCajaBean() {
        if (listaCajaBean == null) {
            listaCajaBean = new ArrayList<>();
        }
        return listaCajaBean;
    }

    public void setListaCajaBean(List<CajaBean> listaCajaBean) {
        this.listaCajaBean = listaCajaBean;
    }

    public List<UnidadNegocioBean> getListaUnidadNegocioBean() {
        if (listaUnidadNegocioBean == null) {
            listaUnidadNegocioBean = new ArrayList<>();
        }
        return listaUnidadNegocioBean;
    }

    public void setListaUnidadNegocioBean(List<UnidadNegocioBean> listaUnidadNegocioBean) {
        this.listaUnidadNegocioBean = listaUnidadNegocioBean;
    }

    public Map<String, Integer> getListaEstado() {
        return listaEstado;
    }

    public void setListaEstado(Map<String, Integer> listaEstado) {
        this.listaEstado = listaEstado;
    }

    public CajaBean getCajaBean() {
        if (cajaBean == null) {
            cajaBean = new CajaBean();
        }
        return cajaBean;
    }

    public void setCajaBean(CajaBean cajaBean) {
        this.cajaBean = cajaBean;
    }

//    public DualListModel<DocIngresoSerieBean> getDualListaDocIngresoSerie() {
//        return dualListaDocIngresoSerie;
//    }
//
//    public void setDualListaDocIngresoSerie(DualListModel<DocIngresoSerieBean> dualListaDocIngresoSerie) {
//        this.dualListaDocIngresoSerie = dualListaDocIngresoSerie;
//    }
//
//    public List<DocIngresoSerieBean> getListaDocIngresoSerieBean() {
//        return listaDocIngresoSerieBean;
//    }
//
//    public void setListaDocIngresoSerieBean(List<DocIngresoSerieBean> listaDocIngresoSerieBean) {
//        this.listaDocIngresoSerieBean = listaDocIngresoSerieBean;
//    }
//
//    public List<DocIngresoSerieBean> getListDocIngresoSerieDest() {
//        return listDocIngresoSerieDest;
//    }
//
//    public void setListDocIngresoSerieDest(List<DocIngresoSerieBean> listDocIngresoSerieDest) {
//        this.listDocIngresoSerieDest = listDocIngresoSerieDest;
//    }
//
//    public DocIngresoSerieCajaBean getDocIngresoSerieCajaBean() {
//        if(docIngresoSerieCajaBean == null)
//        {
//            docIngresoSerieCajaBean = new DocIngresoSerieCajaBean();
//        }
//        return docIngresoSerieCajaBean;
//    }
//
//    public void setDocIngresoSerieCajaBean(DocIngresoSerieCajaBean docIngresoSerieCajaBean) {
//        this.docIngresoSerieCajaBean = docIngresoSerieCajaBean;
//    }
    public DualListModel<ImpresoraBean> getDualImpresora() {
        return dualImpresora;
    }

    public void setDualImpresora(DualListModel<ImpresoraBean> dualImpresora) {
        this.dualImpresora = dualImpresora;
    }

    public List<ImpresoraBean> getListaImpresoraBean() {
        return listaImpresoraBean;
    }

    public void setListaImpresoraBean(List<ImpresoraBean> listaImpresoraBean) {
        this.listaImpresoraBean = listaImpresoraBean;
    }

    public List<ImpresoraBean> getListImpresoraDest() {
        return listImpresoraDest;
    }

    public void setListImpresoraDest(List<ImpresoraBean> listImpresoraDest) {
        this.listImpresoraDest = listImpresoraDest;
    }

    public ImpresoraCajaBean getImpresoraCajaBean() {
        if (impresoraCajaBean == null) {
            impresoraCajaBean = new ImpresoraCajaBean();
        }
        return impresoraCajaBean;
    }

    public void setImpresoraCajaBean(ImpresoraCajaBean impresoraCajaBean) {
        this.impresoraCajaBean = impresoraCajaBean;
    }

    public ChequeBean getChequeBean() {
        if (chequeBean == null) {
            chequeBean = new ChequeBean();
        }
        return chequeBean;
    }

    public void setChequeBean(ChequeBean chequeBean) {
        this.chequeBean = chequeBean;
    }

}
