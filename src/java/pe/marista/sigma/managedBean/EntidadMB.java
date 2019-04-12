package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.DepartamentoBean;
import pe.marista.sigma.bean.DistritoBean;
import pe.marista.sigma.bean.EntidadBean;
import pe.marista.sigma.bean.ViewEntidadBean;
import pe.marista.sigma.bean.EntidadSedeBean;
import pe.marista.sigma.bean.PaisBean;
import pe.marista.sigma.bean.PersonaBean;
import pe.marista.sigma.bean.ProvinciaBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.DistritoService;
import pe.marista.sigma.service.EntidadService;
import pe.marista.sigma.service.PaisService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author Administrador
 */
public class EntidadMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of EntidadMB
     */
    @PostConstruct
    public void EntidadMB() {
        try {
            usuarioLoginBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");

            CodigoService codigoService = BeanFactory.getCodigoService();
            getListaRubroBean();
            listaRubroBean = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_RUBRO));

            getListaTipoEntidadBean();
            listaTipoEntidadBean = codigoService.funcionObtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_ENTIDAD));
            getListaPaisBean();
            PaisService paisService = BeanFactory.getPaisService();
            listaPaisBean = paisService.obtenerPais();
            getListaEntidadPadreBean();
            getListaEntidadBean();
//            EntidadService entidadService = BeanFactory.getEntidadService();
//            getListaEntidadPadreBean();
//            listaEntidadPadreBean = entidadService.obtenerEntidadPorUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getListaDepartamentoBean();
            DistritoService distritoService = BeanFactory.getDistritoService();
            listaDepartamentoBean = distritoService.obtenerDepartamentos();
//            obtenerTodos();
            //Lima por defecto
//            getEntidadBean().getDistritoBean().getProvinciaBean().getDepartamentoBean().setIdDepartamento(15);
//            obtenerProvincia();
//            getEntidadBean().getDistritoBean().getProvinciaBean().setIdProvincia(127);
//            obtenerDistrito();  
            getEntidadBean();
            obtenerProvincia();
            obtenerDistrito();
            getEntidadFiltroBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    private EntidadBean entidadBean;
    private EntidadBean entidadFiltroBean;
    private EntidadBean entidadPadreBean;
    private EntidadBean entidadVista;
    private List<EntidadBean> listaEntidadBean;
    private List<CodigoBean> listaRubroBean;
    private List<CodigoBean> listaTipoEntidadBean;
    private List<PaisBean> listaPaisBean;
    private List<EntidadBean> listaEntidadPadreBean;
//    private List<EntidadSedeBean> listaEntidadSedeBean;
//    private EntidadSedeBean entidadSedeBean;
    private List<DepartamentoBean> listaDepartamentoBean;
    private List<ProvinciaBean> listaProvinciaBean;
    private List<DistritoBean> listaDistritoBean;
    private List<ViewEntidadBean> listaEntidadProvisionalBean;

    private UsuarioBean usuarioLoginBean;
    private List<EntidadBean> listaEntidadCuentaSolBean;
    private List<EntidadBean> listaEntidadCuentaDolBean;

    //Logica de Negocio
    public void ponerEntidadEnSolicitudCCH(Object entidad, Boolean flgSoli, String idTipoSol, String idTipoResp) {
        try {
            entidadBean = (EntidadBean) entidad;
            SolicitudCajaCHMB solicitudCajaCHMB = (SolicitudCajaCHMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("solicitudCajaCHMB");
            if (flgSoli.equals(true)) {
                solicitudCajaCHMB.getSolicitudCajaCHBean().setEntidadBean(entidadBean);
                solicitudCajaCHMB.getSolicitudCajaCHBean().getPersonalBean().getUnidadOrganicaBean().setNombreUniOrg("-");
                solicitudCajaCHMB.getSolicitudCajaCHBean().setNombreSolicitante(entidadBean.getNombre());
                if (idTipoSol != null) {
                    solicitudCajaCHMB.getSolicitudCajaCHBean().setIdTipoSolicitante(idTipoSol);
                }
            } else {
                solicitudCajaCHMB.getSolicitudCajaCHBean().setIdRespCheque(entidadBean.getRuc());
                solicitudCajaCHMB.getSolicitudCajaCHBean().setNomRespCheque(entidadBean.getNombre());
                if (idTipoResp != null) {
                    solicitudCajaCHMB.getSolicitudCajaCHBean().setIdTipoRespCheque(idTipoResp);
                }
            }

            FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("solicitudCajaCHMB", solicitudCajaCHMB);
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ponerPersonaEnMovimientoAlmacen(Object entidad, Boolean flgSoli, String idTipoSol, String idTipoResp) {
        try {
            entidadBean = (EntidadBean) entidad;
            InventarioAlmacenMB inventarioAlmacenMB = (InventarioAlmacenMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("inventarioAlmacenMB");
            if (flgSoli.equals(true)) {
                inventarioAlmacenMB.getMovimientoAlmacenBean().getCatalogoBean().setEntidadBean(entidadBean);
                inventarioAlmacenMB.getMovimientoAlmacenBean().getPersonalBean().getUnidadOrganicaBean().setNombreUniOrg("-");
                inventarioAlmacenMB.getMovimientoAlmacenBean().setEntregadoPor(entidadBean.getNombre());
                if (idTipoSol != null) {
                    inventarioAlmacenMB.getMovimientoAlmacenBean().setIdTipoSolicitante(idTipoSol);
                }
            } else {
                inventarioAlmacenMB.getMovimientoAlmacenBean().setIdRecibido(entidadBean.getRuc());
                inventarioAlmacenMB.getMovimientoAlmacenBean().setRecibidopor(entidadBean.getNombre());

                if (idTipoResp != null) {
                    inventarioAlmacenMB.getMovimientoAlmacenBean().setIdTipoRecibido(idTipoResp);
                }
            }
            FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("inventarioAlmacenMB", inventarioAlmacenMB);
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ponerEntidad(Object entidad) {
        try {
            entidadBean = (EntidadBean) entidad;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarEntidad() {
        try {
            entidadBean = new EntidadBean();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarFiltroEntidad() {
        try {
            entidadFiltroBean = new EntidadBean();
            entidadFiltroBean.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
            listaEntidadBean = new ArrayList<>();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerTodos() {
        try {
//            EntidadService entidadService = BeanFactory.getEntidadService();
//            listaEntidadBean = entidadService.obtenerEntidadPorUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            EntidadService entidadService = BeanFactory.getEntidadService();
            getListaEntidadPadreBean();
            listaEntidadPadreBean = entidadService.obtenerEntidadPorUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaEntidadCuentaSolBean = entidadService.obtenerFlgFinanciero(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaEntidadCuentaDolBean = listaEntidadCuentaSolBean;

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorId(Object object) {
        try {
            entidadBean = (EntidadBean) object;
            EntidadService entidadService = BeanFactory.getEntidadService();
            entidadBean = entidadService.obtenerEntidadPorId(entidadBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void comprobarEntidad() {
        try {
            System.out.println("aaaa");
            EntidadService entidadService = BeanFactory.getEntidadService();
            entidadVista = entidadService.obtenerEntidadPorId(entidadBean);
            if (entidadVista != null) {
                RequestContext.getCurrentInstance().addCallbackParam("error", true);
            } else {
                insertarEntidad();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarEntidad() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EntidadService entidadService = BeanFactory.getEntidadService();
                entidadBean.setCreaPor(usuarioLoginBean.getUsuario());
                entidadBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//                entidadBean.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                if (entidadBean.getEntidadPadreBean().getRuc() != null) {
                    entidadBean.getEntidadPadreBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                } else {
                    entidadBean.getEntidadPadreBean().setUnidadNegocioBean(null);
                }
                entidadService.insertarEntidad(entidadBean);
//                ingresoNEntidad();
                listaEntidadBean = entidadService.obtenerEntidadPorFiltro(entidadFiltroBean);
                limpiarEntidad();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }
//    
//   ModuloBean moduloSeleccionado = (ModuloBean) selectedNode.getData();
//                moduloBean.setIdModuloPadre(moduloSeleccionado.getIdModulo());
//                

//    public void ingresoNEntidad(){
//        try {
//            if(entidadBean.getRuc() != null){
//                entidadBean.setRucPadre(entidadBean.getEntidadPadreBean().getRuc());
//            }
//        } catch (Exception err) {
//           new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), err);
//        }
//    }
    public String modificarEntidad() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EntidadService entidadService = BeanFactory.getEntidadService();
                entidadBean.setModiPor(usuarioLoginBean.getUsuario());
                if (entidadBean.getEntidadPadreBean().getRuc() != null) {
                    entidadBean.getEntidadPadreBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                } else {
                    entidadBean.getEntidadPadreBean().setUnidadNegocioBean(null);
                }
                entidadService.modificarEntidad(entidadBean);
                limpiarEntidad();
//                listaEntidadBean = entidadService.obtenerEntidadPorUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                listaEntidadBean = entidadService.obtenerEntidadPorFiltro(entidadFiltroBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String eliminarEntidad() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EntidadService entidadService = BeanFactory.getEntidadService();
                entidadService.eliminarEntidad(entidadBean.getRuc());
                limpiarEntidad();
//                listaEntidadBean = entidadService.obtenerEntidadPorUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                listaEntidadBean = entidadService.obtenerEntidadPorFiltro(entidadFiltroBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void guardarEntidad() {
        try {
            EntidadService entidadService = BeanFactory.getEntidadService();
            EntidadBean entidad = new EntidadBean();
            entidad = entidadService.obtenerEntidadPorId(entidadBean);
            if (entidad.getRuc() != null) {
                modificarEntidad();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerEntidadPorFiltro() {
        try {
            int estado = 0;
            EntidadService entidadService = BeanFactory.getEntidadService();
            if (entidadFiltroBean.getRuc() != null && !entidadFiltroBean.getRuc().equals("")) {
                entidadFiltroBean.setRuc(entidadFiltroBean.getRuc());
                estado = 1;
            }
            if (entidadFiltroBean.getNombre() != null && !entidadFiltroBean.getNombre().equals("")) {
                entidadFiltroBean.setNombre(entidadFiltroBean.getNombre());
                estado = 1;
            }
            if (entidadFiltroBean.getTipoEntidadBean().getIdCodigo() != null && !entidadFiltroBean.getTipoEntidadBean().getIdCodigo().equals(0)) {
                entidadFiltroBean.getTipoEntidadBean().setIdCodigo(entidadFiltroBean.getTipoEntidadBean().getIdCodigo());
                estado = 1;
            }
            if (entidadFiltroBean.getTipoRubroBean().getIdCodigo() != null && !entidadFiltroBean.getTipoRubroBean().getIdCodigo().equals(0)) {
                entidadFiltroBean.getTipoRubroBean().setIdCodigo(entidadFiltroBean.getTipoRubroBean().getIdCodigo());
                estado = 1;
            }
            if (entidadFiltroBean.getDistritoBean().getIdDistrito() != null && !entidadFiltroBean.getDistritoBean().getIdDistrito().equals(0)) {
                entidadFiltroBean.getDistritoBean().setIdDistrito(entidadFiltroBean.getDistritoBean().getIdDistrito());
                estado = 1;
            }
            if (entidadFiltroBean.getFlgEduSup() == true) {
                entidadFiltroBean.setFlgEduSup(true);
                estado = 1;
            }
            if (entidadFiltroBean.getFlgFinanciera() == true) {
                entidadFiltroBean.setFlgFinanciera(true);
                estado = 1;
            }
            if (entidadFiltroBean.getFlgPrevisional() == true) {
                entidadFiltroBean.setFlgPrevisional(true);
                estado = 1;
            }
            if (entidadFiltroBean.getFlgProveedor() == true) {
                entidadFiltroBean.setFlgProveedor(true);
                estado = 1;
            }
            if (entidadFiltroBean.getFlgSalud() == true) {
                entidadFiltroBean.setFlgSalud(true);
                estado = 1;
            } else if (estado == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaEntidadBean = new ArrayList<>();
            }
            if (estado == 1) {
                entidadFiltroBean.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                listaEntidadBean = entidadService.obtenerEntidadPorFiltro(entidadFiltroBean);
                if (listaEntidadBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

//    CodigoService codigoService = BeanFactory.getCodigoService();
//    CodigoBean bean = codigoService.obtenerPorId(personaBean.getIdTipoDocPer());
//    PersonaService personaService = BeanFactory.getPersonaService();
//    PersonaBean persona = new PersonaBean();
//    persona  = personaService.obtenerPersPorId(personaBean.getIdPersona());
//    if (persona
//
//    
//        != null) {
//                modificarPersona();
//    }
    public void rowSelect(SelectEvent event) {
        try {
            entidadBean = (EntidadBean) event.getObject();
            EntidadService entidadService = BeanFactory.getEntidadService();
            entidadBean = entidadService.obtenerEntidadPorId(entidadBean);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerProvincia() {
        try {
            DistritoService distritoService = BeanFactory.getDistritoService();
            listaProvinciaBean = distritoService.obtenerProvinciaPorDep(entidadBean.getDistritoBean().getProvinciaBean().getDepartamentoBean());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerDistrito() {
        try {
            DistritoService distritoService = BeanFactory.getDistritoService();
            listaDistritoBean = distritoService.obtenerDistritoPorProv(entidadBean.getDistritoBean().getProvinciaBean());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //EntidadSede
//
//    public void limpiarEntidadSede() {
//        entidadSedeBean = new EntidadSedeBean();
//    }
//    public void obtenerSedePorId(Object object) {
//        try {
//            entidadSedeBean = (EntidadSedeBean) object;
//            EntidadService entidadService = BeanFactory.getEntidadService();
//            entidadSedeBean = entidadService.obtenerEntidadSedePorId(entidadSedeBean);
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//    } 
//    public void obtenerSedePorEntidad(Object object) {
//        try {
//            entidadBean = (EntidadBean) object;
//            EntidadService entidadService = BeanFactory.getEntidadService();
////            listaEntidadSedeBean = entidadService.obtenerEntidadSedePorEntidad(entidadBean.getIdEntidad());
//            limpiarEntidadSede();
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//    } 
//    public String insertarEntidadSede() {
//        String pagina = null;
//        try {
//            pagina = new MaristaUtils().validaUsuarioSesion();
//            if (pagina == null) {
//                EntidadService entidadService = BeanFactory.getEntidadService();
//                entidadSedeBean.setEntidadBean(entidadBean);
//                entidadService.insertarEntidadSede(entidadSedeBean);
////                listaEntidadSedeBean = entidadService.obtenerEntidadSedePorEntidad(entidadBean.getIdEntidad());
//                limpiarEntidadSede();
//                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
//            }
//        } catch (Exception err) {
//            pagina = null;
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), err);
//        }
//        return pagina;
//    } 
//    public String modificarEntidadSede() {
//        String pagina = null;
//        try {
//            pagina = new MaristaUtils().validaUsuarioSesion();
//            if (pagina == null) {
//                EntidadService entidadService = BeanFactory.getEntidadService();
//                entidadService.modificarEntidadSede(entidadSedeBean);
////                listaEntidadSedeBean = entidadService.obtenerEntidadSedePorEntidad(entidadBean.getIdEntidad());
//                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
//            }
//        } catch (Exception err) {
//            pagina = null;
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), err);
//        }
//        return pagina;
//    } 
//    public String eliminarEntidadSede() {
//        String pagina = null;
//        try {
//            pagina = new MaristaUtils().validaUsuarioSesion();
//            if (pagina == null) {
//                EntidadService entidadService = BeanFactory.getEntidadService();
//                entidadService.eliminarEntidadSede(entidadSedeBean.getIdEntidadSede());
////                listaEntidadSedeBean = entidadService.obtenerEntidadSedePorEntidad(entidadBean.getIdEntidad());
//                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
//            }
//        } catch (Exception err) {
//            pagina = null;
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), err);
//        }
//        return pagina;
//    } 
//    public void guardarEntidadSede() {
//        if (entidadSedeBean.getIdEntidadSede() != null) {
//            modificarEntidadSede();
//        } else {
//            insertarEntidadSede();
//        }
//    } 
//    public void rowSelectSede(SelectEvent event) {
//        try {
//            entidadSedeBean = (EntidadSedeBean) event.getObject();
//            EntidadService entidadService = BeanFactory.getEntidadService();
//            entidadSedeBean = entidadService.obtenerEntidadSedePorId(entidadSedeBean);
//            obtenerProvincia();
//            obtenerDistrito();
//        } catch (Exception err) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), err);
//        }
//    }
//    public void obtenerProvincia() {
//        try {
//            DistritoService distritoService = BeanFactory.getDistritoService();
//            listaProvinciaBean = distritoService.obtenerProvinciaPorDep(entidadSedeBean.getDistritoBean().getProvinciaBean().getDepartamentoBean());
//          
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//    }
//
//    public void obtenerDistrito() {
//        try {
//            DistritoService distritoService = BeanFactory.getDistritoService();
//            listaDistritoBean = distritoService.obtenerDistritoPorProv(entidadSedeBean.getDistritoBean().getProvinciaBean());
//            
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//    }
    //Getter y Setter
    public EntidadBean getEntidadBean() {
        if (entidadBean == null) {
            entidadBean = new EntidadBean();
        }
        return entidadBean;
    }

    public void setEntidadBean(EntidadBean entidadBean) {
        this.entidadBean = entidadBean;
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

    public List<CodigoBean> getListaRubroBean() {
        if (listaRubroBean == null) {
            listaRubroBean = new ArrayList<>();
        }
        return listaRubroBean;
    }

    public void setListaRubroBean(List<CodigoBean> listaRubroBean) {
        this.listaRubroBean = listaRubroBean;
    }

    public List<CodigoBean> getListaTipoEntidadBean() {
        if (listaTipoEntidadBean == null) {
            listaTipoEntidadBean = new ArrayList<>();
        }
        return listaTipoEntidadBean;
    }

    public void setListaTipoEntidadBean(List<CodigoBean> listaTipoEntidadBean) {
        this.listaTipoEntidadBean = listaTipoEntidadBean;
    }

    public List<PaisBean> getListaPaisBean() {
        if (listaPaisBean == null) {
            listaPaisBean = new ArrayList<>();
        }
        return listaPaisBean;
    }

    public void setListaPaisBean(List<PaisBean> listaPaisBean) {
        this.listaPaisBean = listaPaisBean;
    }

    public List<EntidadBean> getListaEntidadPadreBean() {
        if (listaEntidadPadreBean == null) {
            listaEntidadPadreBean = new ArrayList<>();
        }
        return listaEntidadPadreBean;
    }

    public void setListaEntidadPadreBean(List<EntidadBean> listaEntidadPadreBean) {
        this.listaEntidadPadreBean = listaEntidadPadreBean;
    }
//
//    public List<EntidadSedeBean> getListaEntidadSedeBean() {
//        if (listaEntidadSedeBean == null) {
//            listaEntidadSedeBean = new ArrayList<>();
//        }
//        return listaEntidadSedeBean;
//    }
//
//    public void setListaEntidadSedeBean(List<EntidadSedeBean> listaEntidadSedeBean) {
//        this.listaEntidadSedeBean = listaEntidadSedeBean;
//    }
//
//    public EntidadSedeBean getEntidadSedeBean() {
//        if (entidadSedeBean == null) {
//            entidadSedeBean = new EntidadSedeBean();
//        }
//        return entidadSedeBean;
//    }
//
//    public void setEntidadSedeBean(EntidadSedeBean entidadSedeBean) {
//        this.entidadSedeBean = entidadSedeBean;
//    }

    public List<DepartamentoBean> getListaDepartamentoBean() {
        if (listaDepartamentoBean == null) {
            listaDepartamentoBean = new ArrayList<>();
        }
        return listaDepartamentoBean;
    }

    public void setListaDepartamentoBean(List<DepartamentoBean> listaDepartamentoBean) {
        this.listaDepartamentoBean = listaDepartamentoBean;
    }

    public List<ProvinciaBean> getListaProvinciaBean() {
        if (listaProvinciaBean == null) {
            listaProvinciaBean = new ArrayList<>();
        }
        return listaProvinciaBean;
    }

    public void setListaProvinciaBean(List<ProvinciaBean> listaProvinciaBean) {
        this.listaProvinciaBean = listaProvinciaBean;
    }

    public List<DistritoBean> getListaDistritoBean() {
        if (listaDistritoBean == null) {
            listaDistritoBean = new ArrayList<>();
        }
        return listaDistritoBean;
    }

    public void setListaDistritoBean(List<DistritoBean> listaDistritoBean) {
        this.listaDistritoBean = listaDistritoBean;
    }

    public EntidadBean getEntidadVista() {
        if (entidadVista == null) {
            entidadVista = new EntidadBean();
        }
        return entidadVista;
    }

    public void setEntidadVista(EntidadBean entidadVista) {
        this.entidadVista = entidadVista;
    }

    public EntidadBean getEntidadPadreBean() {
        if (entidadPadreBean == null) {
            entidadPadreBean = new EntidadBean();
        }
        return entidadPadreBean;
    }

    public void setEntidadPadreBean(EntidadBean entidadPadreBean) {
        this.entidadPadreBean = entidadPadreBean;
    }

    public List<ViewEntidadBean> getListaEntidadProvisionalBean() {
        if (listaEntidadProvisionalBean == null) {
            listaEntidadProvisionalBean = new ArrayList<>();
        }
        return listaEntidadProvisionalBean;
    }

    public void setListaEntidadProvisionalBean(List<ViewEntidadBean> listaEntidadProvisionalBean) {
        this.listaEntidadProvisionalBean = listaEntidadProvisionalBean;
    }

    public EntidadBean getEntidadFiltroBean() {
        if (entidadFiltroBean == null) {
            entidadFiltroBean = new EntidadBean();
        }
        return entidadFiltroBean;
    }

    public void setEntidadFiltroBean(EntidadBean entidadFiltroBean) {
        this.entidadFiltroBean = entidadFiltroBean;
    }

    public List<EntidadBean> getListaEntidadCuentaSolBean() {
        if (listaEntidadCuentaSolBean == null) {
            listaEntidadCuentaSolBean = new ArrayList<>();
        }
        return listaEntidadCuentaSolBean;
    }

    public void setListaEntidadCuentaSolBean(List<EntidadBean> listaEntidadCuentaSolBean) {
        this.listaEntidadCuentaSolBean = listaEntidadCuentaSolBean;
    }

    public List<EntidadBean> getListaEntidadCuentaDolBean() {
        if (listaEntidadCuentaDolBean == null) {
            listaEntidadCuentaDolBean = new ArrayList<>();
        }
        return listaEntidadCuentaDolBean;
    }

    public void setListaEntidadCuentaDolBean(List<EntidadBean> listaEntidadCuentaDolBean) {
        this.listaEntidadCuentaDolBean = listaEntidadCuentaDolBean;
    }

}
