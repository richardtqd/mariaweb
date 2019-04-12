package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.TipoSolicitudBean;
import pe.marista.sigma.bean.UniNegUniOrgBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.LegajoService;
import pe.marista.sigma.service.TipoSolicitudService;
import pe.marista.sigma.service.UniNegUniOrgService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.util.MensajesBackEnd;

/**
 *
 * @author Administrador
 */
public class TipoSolicitudMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of TipoSolicitudMB
     */
    @PostConstruct
    public void TipoSolicitudMB() {
        try {
            usuarioLoginBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            getTipoSolicitudFiltroBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
            TipoSolicitudService tipoSolicitudService = BeanFactory.getTipoSolicitudService();
            listaTipoSolicitudBean = tipoSolicitudService.obtenerTodosTipoSolicitudPorUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            CodigoService codigoService = BeanFactory.getCodigoService();
            listaTipoAmbitoSolBean = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_AMB_SOL));
//            getListaTipoAtoriza1Bean();
//            listaTipoAtoriza1Bean = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_AUTORRIZA));
//            getListaTipoAtoriza2Bean();
//            listaTipoAtoriza2Bean = listaTipoAtoriza1Bean;
//            getListaTipoAtoriza3Bean();
//            listaTipoAtoriza3Bean = listaTipoAtoriza2Bean;
            listaTipoAutoriza();
            //unidades org. por uni. de negocio
            UniNegUniOrgService uniNegUniOrgService = BeanFactory.getUniNegUniOrgService();
            getListaUniNegUniOrg1Bean();
            listaUniNegUniOrg1Bean = uniNegUniOrgService.obtenerUniOrgPorUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getListaUniNegUniOrg2Bean();
            listaUniNegUniOrg2Bean = listaUniNegUniOrg1Bean;
            getListaUniNegUniOrg3Bean();
            listaUniNegUniOrg3Bean = listaUniNegUniOrg2Bean;

            LegajoService legajoService = BeanFactory.getLegajoService();
            getListaPersonal1Bean();
            listaPersonal1Bean = legajoService.obtenerPersonalPorUnidadNegocio(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getListaPersonal2Bean();
            listaPersonal2Bean = listaPersonal1Bean;
            getListaPersonal3Bean();
            listaPersonal3Bean = listaPersonal2Bean;

            this.autoPer1 = false;

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    private TipoSolicitudBean tipoSolicitudBean;
    private TipoSolicitudBean tipoSolicitudFiltroBean;
    private List<TipoSolicitudBean> listaTipoSolicitudBean;

    private List<CodigoBean> listaTipoAmbitoSolBean;

    //autoriza1
    private List<CodigoBean> listaTipoAtoriza1Bean;
    private List<UniNegUniOrgBean> listaUniNegUniOrg1Bean;
    private List<PersonalBean> listaPersonal1Bean;
    //autoriza2
    private List<CodigoBean> listaTipoAtoriza2Bean;
    private List<UniNegUniOrgBean> listaUniNegUniOrg2Bean;
    private List<PersonalBean> listaPersonal2Bean;
    //autoriza3
    private List<CodigoBean> listaTipoAtoriza3Bean;
    private List<UniNegUniOrgBean> listaUniNegUniOrg3Bean;
    private List<PersonalBean> listaPersonal3Bean;

    private Map<String, String> listaTipoAutoriza1;
    private Map<String, String> listaTipoAutoriza2;
    private Map<String, String> listaTipoAutoriza3;

    public void listaTipoAutoriza() {
        listaTipoAutoriza1 = new LinkedHashMap<>();
        listaTipoAutoriza1.put(MensajesBackEnd.getValueOfKey("etiquetaListaPersonal", null), "P");
        listaTipoAutoriza1.put(MensajesBackEnd.getValueOfKey("etiquetaListaUniOrg", null), "U");
        listaTipoAutoriza1 = Collections.unmodifiableMap(listaTipoAutoriza1);
    }

    //MOSTRAR AUTORIZADORES
    private Boolean autoPer1 = false;
    private Boolean autoPer2 = false;
    private Boolean autoPer3 = false;
    private Boolean autoUO1 = false;
    private Boolean autoUO2 = false;
    private Boolean autoUO3 = false;
    private Boolean ans1 = false;
    private Boolean ans2 = false;
    //flg check
    private Boolean mostrarAutoriza1 = false;
    private Boolean mostrarAutoriza2 = false;
    private Boolean mostrarAutoriza3 = false;

    private Boolean flgAutoriza1 = false;
    private Boolean flgAutoriza2 = false;
    private Boolean flgAutoriza3 = false;

    //usuario
    private UsuarioBean usuarioLoginBean;

    public void obtenerTipoSolicitudPorFiltro() {
        try {
            int estado = 0;
            TipoSolicitudService tipoSolicitudService = BeanFactory.getTipoSolicitudService();
            if (tipoSolicitudFiltroBean.getNombre() != null && !tipoSolicitudFiltroBean.getNombre().equals("")) {
                tipoSolicitudFiltroBean.setNombre(tipoSolicitudFiltroBean.getNombre().trim());
                estado = 1;
            }
            if (tipoSolicitudFiltroBean.getTipoAmbitoSolBean().getIdCodigo() != null && !tipoSolicitudFiltroBean.getTipoAmbitoSolBean().getIdCodigo().equals(0)) {
                tipoSolicitudFiltroBean.getTipoAmbitoSolBean().setIdCodigo(tipoSolicitudFiltroBean.getTipoAmbitoSolBean().getIdCodigo());
                estado = 1;
            }
            if (tipoSolicitudFiltroBean.getTipoAmbitoSolBean().getIdCodigo() != null && !tipoSolicitudFiltroBean.getTipoAmbitoSolBean().getIdCodigo().equals(0)) {
                tipoSolicitudFiltroBean.getTipoAmbitoSolBean().setIdCodigo(tipoSolicitudFiltroBean.getTipoAmbitoSolBean().getIdCodigo());
                estado = 1;
            }
            if (tipoSolicitudFiltroBean.getStatus() != null) {
                tipoSolicitudFiltroBean.setStatus(tipoSolicitudFiltroBean.getStatus());
                estado = 1;
            }
            if (tipoSolicitudFiltroBean.getFlgAutoEscala() != null) {
                tipoSolicitudFiltroBean.setFlgAutoEscala(tipoSolicitudFiltroBean.getFlgAutoEscala());
                estado = 1;
            } else if (estado == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaTipoSolicitudBean = new ArrayList<>();
            }
            if (estado == 1) {
                listaTipoSolicitudBean = tipoSolicitudService.obtenerTipoSolicitudPorFiltro(tipoSolicitudFiltroBean);
                if (listaTipoSolicitudBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //Metodos Logica
    public void limpiarTipoSolicitudBean() {
        tipoSolicitudBean = new TipoSolicitudBean();
        this.tipoSolicitudBean.setFlgAutoEscala(false);
        this.tipoSolicitudBean.setStatus(true);
        mostrarAutoriza1 = false;
        mostrarAutoriza2 = false;
        mostrarAutoriza3 = false;
        flgAutoriza1 = false;
        flgAutoriza2 = false;
        flgAutoriza3 = false;
        autoPer1 = false;
        autoPer2 = false;
        autoPer3 = false;
        autoUO1 = false;
        autoUO2 = false;
        autoUO3 = false;
        ans1 = false;
        ans2 = false;
    }

    public void limpiarTipoSolicitudFiltroBean() {
        tipoSolicitudFiltroBean = new TipoSolicitudBean();
        listaTipoSolicitudBean = new ArrayList<>();
        getTipoSolicitudFiltroBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());

    }

    public void mostar1() {
        try {
            if (mostrarAutoriza1 == false) {
                RequestContext.getCurrentInstance().addCallbackParam("operacion", true);
            } else {
                flgAutoriza1 = true;
            }

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void mostar2() {
        try {
            if (mostrarAutoriza2 == false) {
                RequestContext.getCurrentInstance().addCallbackParam("operacion", true);
            } else {
                flgAutoriza2 = true;
            }

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void mostar3() {
        try {
            if (mostrarAutoriza3 == false) {
                RequestContext.getCurrentInstance().addCallbackParam("operacion", true);
            } else {
                flgAutoriza3 = true;
            }

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void verificarPaneles() {
        try {
            mostrarAutoriza1 = false;
            mostrarAutoriza2 = false;
            mostrarAutoriza3 = false;
            flgAutoriza1 = false;
            flgAutoriza2 = false;
            flgAutoriza3 = false;
            if (tipoSolicitudBean.getIdTipoAutoriza1() != null) {
                mostrarAutoriza1 = true;
                flgAutoriza1 = true;
                mostarAutorizador1();
            }
            if (tipoSolicitudBean.getIdTipoAutoriza2() != null) {
                mostrarAutoriza2 = true;
                flgAutoriza2 = true;
                mostarAutorizador2();
            }
            if (tipoSolicitudBean.getIdTipoAutoriza3() != null) {
                mostrarAutoriza3 = true;
                flgAutoriza3 = true;
                mostarAutorizador3();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarAutoGeneral1() {
        limpiarAuto1();
        limpiarAuto2();
        limpiarAuto3();
    }

    public void limpiarAutoGeneral2() {
        limpiarAuto2();
        limpiarAuto3();
    }

    public void limpiarAutoGeneral3() {
        limpiarAuto3();
    }

    public void limpiarAuto1() {
        mostrarAutoriza1 = false;
        flgAutoriza1 = false;
        this.autoPer1 = false;
        this.ans1 = false;
        this.autoUO1 = false;
        this.tipoSolicitudBean.setAns1(null);
        this.tipoSolicitudBean.setIdAutorizaUO1Bean(null);
        this.tipoSolicitudBean.setIdAutorizaPer1Bean(null);
        this.tipoSolicitudBean.setIdTipoAutoriza1(null);
    }

    public void limpiarAuto2() {
        mostrarAutoriza2 = false;
        flgAutoriza2 = false;
        this.autoPer2 = false;
        this.ans2 = false;
        this.autoUO2 = false;
        this.tipoSolicitudBean.setAns2(null);
        this.tipoSolicitudBean.setIdAutorizaUO2Bean(null);
        this.tipoSolicitudBean.setIdAutorizaPer2Bean(null);
        this.tipoSolicitudBean.setIdTipoAutoriza2(null);
    }

    public void limpiarAuto3() {
        mostrarAutoriza3 = false;
        flgAutoriza3 = false;
        this.autoPer3 = false;
        this.autoUO3 = false;
        this.tipoSolicitudBean.setIdAutorizaUO3Bean(null);
        this.tipoSolicitudBean.setIdAutorizaPer3Bean(null);
        this.tipoSolicitudBean.setIdTipoAutoriza3(null);
    }

    public void panelIgual() {
        mostrarAutoriza1 = true;
    }

    public void panelIgual2() {
        mostrarAutoriza1 = true;
        mostrarAutoriza2 = true;
    }

    public void panelIgual3() {
        mostrarAutoriza1 = true;
        mostrarAutoriza2 = true;
        mostrarAutoriza3 = true;
    }

    public String insertarTipoSolicitud() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                if (tipoSolicitudBean.getFlgAutoEscala() == true && tipoSolicitudBean.getIdTipoAutoriza1() == null && !tipoSolicitudBean.getTipoAmbitoSolBean().getIdCodigo().equals(0)) {
                    RequestContext.getCurrentInstance().addCallbackParam("noEscala", true);
                } else {
                    TipoSolicitudService tipoSolicitudService = BeanFactory.getTipoSolicitudService();
                    tipoSolicitudBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    tipoSolicitudBean.setCreaPor(usuarioLoginBean.getUsuario());
                    tipoSolicitudService.insertarTipoSolicitud(tipoSolicitudBean);
                    listaTipoSolicitudBean = tipoSolicitudService.obtenerTipoSolicitudPorFiltro(tipoSolicitudFiltroBean);
                    limpiarTipoSolicitudBean();
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                }
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarTipoSolicitud() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                if (tipoSolicitudBean.getFlgAutoEscala() == true && tipoSolicitudBean.getIdTipoAutoriza1() == null && !tipoSolicitudBean.getTipoAmbitoSolBean().getIdCodigo().equals(0)) {
                    RequestContext.getCurrentInstance().addCallbackParam("noEscala", true);
                } else {
                    TipoSolicitudService tipoSolicitudService = BeanFactory.getTipoSolicitudService();
                    tipoSolicitudBean.setModiPor(usuarioLoginBean.getUsuario());
                    tipoSolicitudService.modificarTipoSolicitud(tipoSolicitudBean);
                    listaTipoSolicitudBean = tipoSolicitudService.obtenerTipoSolicitudPorFiltro(tipoSolicitudFiltroBean);
                    limpiarTipoSolicitudBean();
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                }
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String eliminarTipoSolicitud() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                TipoSolicitudService tipoSolicitudService = BeanFactory.getTipoSolicitudService();
                tipoSolicitudService.eliminarTipoSolicitud(tipoSolicitudBean);
//                listaTipoSolicitudBean = tipoSolicitudService.obtenerTodosTipoSolicitudPorUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                listaTipoSolicitudBean = tipoSolicitudService.obtenerTipoSolicitudPorFiltro(tipoSolicitudFiltroBean);
                limpiarTipoSolicitudBean();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String guardarTipoSolicitud() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                if (tipoSolicitudBean.getIdTipoSolicitud() != null) {
                    modificarTipoSolicitud();
                } else {
                    insertarTipoSolicitud();
                }
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void obtenerPorId(Object object) {
        try {
            tipoSolicitudBean = (TipoSolicitudBean) object;
            TipoSolicitudService tipoSolicitudService = BeanFactory.getTipoSolicitudService();
            tipoSolicitudBean = tipoSolicitudService.obtenerTipoSolicitudPorId(tipoSolicitudBean);
            verificarPaneles();

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void rowSelect(SelectEvent event) {
        try {
            tipoSolicitudBean = (TipoSolicitudBean) event.getObject();
            verificarPaneles();

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void mostarAutorizador1() {
        try {
            if (tipoSolicitudBean.getIdTipoAutoriza1() != null) {
                if (tipoSolicitudBean.getIdTipoAutoriza1().equals("P")) {
                    this.autoPer1 = true;
                    this.ans1 = true;
                    this.autoUO1 = false;
                    this.tipoSolicitudBean.setIdAutorizaUO1Bean(null);
                }
                if (tipoSolicitudBean.getIdTipoAutoriza1().equals("U")) {
                    this.autoUO1 = true;
                    this.autoPer1 = false;
                    this.ans1 = true;
                    this.tipoSolicitudBean.setIdAutorizaPer1Bean(null);
                }
                if (tipoSolicitudBean.getIdTipoAutoriza1() == null) {
                    this.autoPer1 = false;
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void mostarAutorizador2() {
        try {
            if (tipoSolicitudBean.getIdTipoAutoriza2() != null) {
                if (tipoSolicitudBean.getIdTipoAutoriza2().equals("P")) {
                    this.autoPer2 = true;
                    this.ans2 = true;
                    this.autoUO2 = false;
                    this.tipoSolicitudBean.setIdAutorizaUO2Bean(null);
                }
                if (tipoSolicitudBean.getIdTipoAutoriza2().equals("U")) {
                    this.autoUO2 = true;
                    this.autoPer2 = false;
                    this.ans2 = true;
                    this.tipoSolicitudBean.setIdAutorizaPer2Bean(null);
                }
                if (tipoSolicitudBean.getIdTipoAutoriza2() == null) {
                    this.autoPer2 = false;
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void mostarAutorizador3() {
        try {
            if (tipoSolicitudBean.getIdTipoAutoriza3() != null) {
                if (tipoSolicitudBean.getIdTipoAutoriza3().equals("P")) {
                    this.autoPer3 = true;
                    this.autoUO3 = false;
                    this.tipoSolicitudBean.setIdAutorizaUO3Bean(null);
                }
                if (tipoSolicitudBean.getIdTipoAutoriza3().equals("U")) {
                    this.autoUO3 = true;
                    this.autoPer3 = false;
                    this.tipoSolicitudBean.setIdAutorizaPer3Bean(null);
                }
                if (tipoSolicitudBean.getIdTipoAutoriza3() == null) {
                    this.autoPer3 = false;
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //Metodos Getter y Setter
    public TipoSolicitudBean getTipoSolicitudBean() {
        if (tipoSolicitudBean == null) {
            tipoSolicitudBean = new TipoSolicitudBean();
        }
        return tipoSolicitudBean;
    }

    public void setTipoSolicitudBean(TipoSolicitudBean tipoSolicitudBean) {
        this.tipoSolicitudBean = tipoSolicitudBean;
    }

    public List<TipoSolicitudBean> getListaTipoSolicitudBean() {
        if (listaTipoSolicitudBean == null) {
            listaTipoSolicitudBean = new ArrayList<>();
        }
        return listaTipoSolicitudBean;
    }

    public void setListaTipoSolicitudBean(List<TipoSolicitudBean> listaTipoSolicitudBean) {
        this.listaTipoSolicitudBean = listaTipoSolicitudBean;
    }

    public List<CodigoBean> getListaTipoAmbitoSolBean() {
        if (listaTipoAmbitoSolBean == null) {
            listaTipoAmbitoSolBean = new ArrayList<>();
        }
        return listaTipoAmbitoSolBean;
    }

    public void setListaTipoAmbitoSolBean(List<CodigoBean> listaTipoAmbitoSolBean) {
        this.listaTipoAmbitoSolBean = listaTipoAmbitoSolBean;
    }

    public List<CodigoBean> getListaTipoAtoriza1Bean() {
        if (listaTipoAtoriza1Bean == null) {
            listaTipoAtoriza1Bean = new ArrayList<>();
        }
        return listaTipoAtoriza1Bean;
    }

    public void setListaTipoAtoriza1Bean(List<CodigoBean> listaTipoAtoriza1Bean) {
        this.listaTipoAtoriza1Bean = listaTipoAtoriza1Bean;
    }

    public List<UniNegUniOrgBean> getListaUniNegUniOrg1Bean() {
        if (listaUniNegUniOrg1Bean == null) {
            listaUniNegUniOrg1Bean = new ArrayList<>();
        }
        return listaUniNegUniOrg1Bean;
    }

    public void setListaUniNegUniOrg1Bean(List<UniNegUniOrgBean> listaUniNegUniOrg1Bean) {
        this.listaUniNegUniOrg1Bean = listaUniNegUniOrg1Bean;
    }

    public List<PersonalBean> getListaPersonal1Bean() {
        if (listaPersonal1Bean == null) {
            listaPersonal1Bean = new ArrayList<>();
        }
        return listaPersonal1Bean;
    }

    public void setListaPersonal1Bean(List<PersonalBean> listaPersonal1Bean) {
        this.listaPersonal1Bean = listaPersonal1Bean;
    }

    public List<CodigoBean> getListaTipoAtoriza2Bean() {
        if (listaTipoAtoriza2Bean == null) {
            listaTipoAtoriza2Bean = new ArrayList<>();
        }
        return listaTipoAtoriza2Bean;
    }

    public void setListaTipoAtoriza2Bean(List<CodigoBean> listaTipoAtoriza2Bean) {
        this.listaTipoAtoriza2Bean = listaTipoAtoriza2Bean;
    }

    public List<UniNegUniOrgBean> getListaUniNegUniOrg2Bean() {
        if (listaUniNegUniOrg2Bean == null) {
            listaUniNegUniOrg2Bean = new ArrayList<>();
        }
        return listaUniNegUniOrg2Bean;
    }

    public void setListaUniNegUniOrg2Bean(List<UniNegUniOrgBean> listaUniNegUniOrg2Bean) {
        this.listaUniNegUniOrg2Bean = listaUniNegUniOrg2Bean;
    }

    public List<PersonalBean> getListaPersonal2Bean() {
        if (listaPersonal2Bean == null) {
            listaPersonal2Bean = new ArrayList<>();
        }
        return listaPersonal2Bean;
    }

    public void setListaPersonal2Bean(List<PersonalBean> listaPersonal2Bean) {
        this.listaPersonal2Bean = listaPersonal2Bean;
    }

    public List<CodigoBean> getListaTipoAtoriza3Bean() {
        if (listaTipoAtoriza3Bean == null) {
            listaTipoAtoriza3Bean = new ArrayList();
        }
        return listaTipoAtoriza3Bean;
    }

    public void setListaTipoAtoriza3Bean(List<CodigoBean> listaTipoAtoriza3Bean) {
        this.listaTipoAtoriza3Bean = listaTipoAtoriza3Bean;
    }

    public List<UniNegUniOrgBean> getListaUniNegUniOrg3Bean() {
        if (listaUniNegUniOrg3Bean == null) {
            listaUniNegUniOrg3Bean = new ArrayList();
        }
        return listaUniNegUniOrg3Bean;
    }

    public void setListaUniNegUniOrg3Bean(List<UniNegUniOrgBean> listaUniNegUniOrg3Bean) {
        this.listaUniNegUniOrg3Bean = listaUniNegUniOrg3Bean;
    }

    public List<PersonalBean> getListaPersonal3Bean() {
        if (listaPersonal3Bean == null) {
            listaPersonal3Bean = new ArrayList();
        }
        return listaPersonal3Bean;
    }

    public void setListaPersonal3Bean(List<PersonalBean> listaPersonal3Bean) {
        this.listaPersonal3Bean = listaPersonal3Bean;
    }

    public Boolean getAutoPer1() {
        return autoPer1;
    }

    public void setAutoPer1(Boolean autoPer1) {
        this.autoPer1 = autoPer1;
    }

    public Boolean getAutoPer2() {
        return autoPer2;
    }

    public void setAutoPer2(Boolean autoPer2) {
        this.autoPer2 = autoPer2;
    }

    public Boolean getAutoPer3() {
        return autoPer3;
    }

    public void setAutoPer3(Boolean autoPer3) {
        this.autoPer3 = autoPer3;
    }

    public Boolean getAutoUO1() {
        return autoUO1;
    }

    public void setAutoUO1(Boolean autoUO1) {
        this.autoUO1 = autoUO1;
    }

    public Boolean getAutoUO2() {
        return autoUO2;
    }

    public void setAutoUO2(Boolean autoUO2) {
        this.autoUO2 = autoUO2;
    }

    public Boolean getAutoUO3() {
        return autoUO3;
    }

    public void setAutoUO3(Boolean autoUO3) {
        this.autoUO3 = autoUO3;
    }

    public Boolean getAns1() {
        return ans1;
    }

    public void setAns1(Boolean ans1) {
        this.ans1 = ans1;
    }

    public Boolean getAns2() {
        return ans2;
    }

    public void setAns2(Boolean ans2) {
        this.ans2 = ans2;
    }

    public Boolean getMostrarAutoriza2() {
        return mostrarAutoriza2;
    }

    public void setMostrarAutoriza2(Boolean mostrarAutoriza2) {
        this.mostrarAutoriza2 = mostrarAutoriza2;
    }

    public Boolean getMostrarAutoriza3() {
        return mostrarAutoriza3;
    }

    public void setMostrarAutoriza3(Boolean mostrarAutoriza3) {
        this.mostrarAutoriza3 = mostrarAutoriza3;
    }

    public Boolean getMostrarAutoriza1() {
        return mostrarAutoriza1;
    }

    public void setMostrarAutoriza1(Boolean mostrarAutoriza1) {
        this.mostrarAutoriza1 = mostrarAutoriza1;
    }

    public Map<String, String> getListaTipoAutoriza1() {
        return listaTipoAutoriza1;
    }

    public void setListaTipoAutoriza1(Map<String, String> listaTipoAutoriza1) {
        this.listaTipoAutoriza1 = listaTipoAutoriza1;
    }

    public Map<String, String> getListaTipoAutoriza2() {
        return listaTipoAutoriza2;
    }

    public void setListaTipoAutoriza2(Map<String, String> listaTipoAutoriza2) {
        this.listaTipoAutoriza2 = listaTipoAutoriza2;
    }

    public Map<String, String> getListaTipoAutoriza3() {
        return listaTipoAutoriza3;
    }

    public void setListaTipoAutoriza3(Map<String, String> listaTipoAutoriza3) {
        this.listaTipoAutoriza3 = listaTipoAutoriza3;
    }

    public UsuarioBean getUsuarioLoginBean() {
        return usuarioLoginBean;
    }

    public void setUsuarioLoginBean(UsuarioBean usuarioLoginBean) {
        this.usuarioLoginBean = usuarioLoginBean;
    }

    public Boolean getFlgAutoriza1() {
        return flgAutoriza1;
    }

    public void setFlgAutoriza1(Boolean flgAutoriza1) {
        this.flgAutoriza1 = flgAutoriza1;
    }

    public Boolean getFlgAutoriza2() {
        return flgAutoriza2;
    }

    public void setFlgAutoriza2(Boolean flgAutoriza2) {
        this.flgAutoriza2 = flgAutoriza2;
    }

    public Boolean getFlgAutoriza3() {
        return flgAutoriza3;
    }

    public void setFlgAutoriza3(Boolean flgAutoriza3) {
        this.flgAutoriza3 = flgAutoriza3;
    }

    public TipoSolicitudBean getTipoSolicitudFiltroBean() {
        if (tipoSolicitudFiltroBean == null) {
            tipoSolicitudFiltroBean = new TipoSolicitudBean();
        }
        return tipoSolicitudFiltroBean;
    }

    public void setTipoSolicitudFiltroBean(TipoSolicitudBean tipoSolicitudFiltroBean) {
        this.tipoSolicitudFiltroBean = tipoSolicitudFiltroBean;
    }

}
