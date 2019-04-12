package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.bean.GradoAcademicoBean;
import pe.marista.sigma.bean.NivelAcademicoBean;
import pe.marista.sigma.bean.TipoFormacionBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.GradoAcademicoService;
import pe.marista.sigma.service.NivelAcademicoService;
import pe.marista.sigma.service.TipoFormacionService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

public class TipoFormacionMB extends BaseMB implements Serializable {

    //variables
    private List<TipoFormacionBean> listaTipoFormacionBean;
    private TipoFormacionBean tipoFormacionBean;
    private List<NivelAcademicoBean> listaNivelAcademicoBean;
    private List<NivelAcademicoBean> listaNivelAcademicoFiltroBean;
    private NivelAcademicoBean nivelAcademicoBean;
    private GradoAcademicoBean gradoAcademicoBean;
    private List<GradoAcademicoBean> listaGradoAcademicoBean;
    private List<GradoAcademicoBean> listaGradoAcademicoFiltroBean;
    private Integer idTipoForm;
    private Integer idNivelAca;
    private UsuarioBean usuarioLoginBean;

    //getter and setter
    public List<NivelAcademicoBean> getListaNivelAcademicoFiltroBean() {
        return listaNivelAcademicoFiltroBean;
    }

    public void setListaNivelAcademicoFiltroBean(List<NivelAcademicoBean> listaNivelAcademicoFiltroBean) {
        this.listaNivelAcademicoFiltroBean = listaNivelAcademicoFiltroBean;
    }

    public List<GradoAcademicoBean> getListaGradoAcademicoFiltroBean() {
        return listaGradoAcademicoFiltroBean;
    }

    public void setListaGradoAcademicoFiltroBean(List<GradoAcademicoBean> listaGradoAcademicoFiltroBean) {
        this.listaGradoAcademicoFiltroBean = listaGradoAcademicoFiltroBean;
    }

    public Integer getIdTipoForm() {
        return idTipoForm;
    }

    public void setIdTipoForm(Integer idTipoForm) {
        this.idTipoForm = idTipoForm;
    }

    public Integer getIdNivelAca() {
        return idNivelAca;
    }

    public void setIdNivelAca(Integer idNivelAca) {
        this.idNivelAca = idNivelAca;
    }

    public List<GradoAcademicoBean> getListaGradoAcademicoBean() {
        return listaGradoAcademicoBean;
    }

    public void setListaGradoAcademicoBean(List<GradoAcademicoBean> listaGradoAcademicoBean) {
        this.listaGradoAcademicoBean = listaGradoAcademicoBean;
    }

    public List<TipoFormacionBean> getListaTipoFormacionBean() {
        return listaTipoFormacionBean;
    }

    public void setListaTipoFormacionBean(List<TipoFormacionBean> listaTipoFormacionBean) {
        this.listaTipoFormacionBean = listaTipoFormacionBean;
    }

    public TipoFormacionBean getTipoFormacionBean() {
        if (tipoFormacionBean == null) {
            tipoFormacionBean = new TipoFormacionBean();
        }
        return tipoFormacionBean;
    }

    public void setTipoFormacionBean(TipoFormacionBean tipoFormacionBean) {
        this.tipoFormacionBean = tipoFormacionBean;
    }

    public List<NivelAcademicoBean> getListaNivelAcademicoBean() {
        return listaNivelAcademicoBean;
    }

    public void setListaNivelAcademicoBean(List<NivelAcademicoBean> listaNivelAcademicoBean) {
        this.listaNivelAcademicoBean = listaNivelAcademicoBean;
    }

    public NivelAcademicoBean getNivelAcademicoBean() {
        if (nivelAcademicoBean == null) {
            nivelAcademicoBean = new NivelAcademicoBean();
        }
        return nivelAcademicoBean;
    }

    public void setNivelAcademicoBean(NivelAcademicoBean nivelAcademicoBean) {
        this.nivelAcademicoBean = nivelAcademicoBean;
    }

    public GradoAcademicoBean getGradoAcademicoBean() {
        if (gradoAcademicoBean == null) {
            gradoAcademicoBean = new GradoAcademicoBean();
        }
        return gradoAcademicoBean;
    }

    public void setGradoAcademicoBean(GradoAcademicoBean gradoAcademicoBean) {
        this.gradoAcademicoBean = gradoAcademicoBean;
    }

    //constructor
    @PostConstruct
    public void cargaDatos() {
        usuarioLoginBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        this.obtenerTodosTF();
        this.obtenerTodosNA();
        this.obtenerTodosGA();
    }

    //--------------------------------------- Metodos de Tipo de Formacion ---------------------------
    public void limpiarTF() {
        tipoFormacionBean = new TipoFormacionBean();
    }

    public void obtenerTodosTF() {
        try {
            TipoFormacionService tipoFormacionService = BeanFactory.getTipoFormacionService();
            listaTipoFormacionBean = tipoFormacionService.obtenerTodos();

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorIdTF(TipoFormacionBean tipoForm) {
        try {
            TipoFormacionService tipoFormacionService = BeanFactory.getTipoFormacionService();
            tipoFormacionBean = tipoFormacionService.obtenerPorId(tipoForm);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void rowSelectTF(SelectEvent event) {
        try {
            tipoFormacionBean = (TipoFormacionBean) event.getObject();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String insertarTF() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {

                TipoFormacionService tipoFormacionService = BeanFactory.getTipoFormacionService();
                tipoFormacionBean.setCreaPor(usuarioLoginBean.getUsuario());
                tipoFormacionService.insertar(tipoFormacionBean);
                listaTipoFormacionBean = tipoFormacionService.obtenerTodos();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                this.limpiarTF();
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarTF() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                TipoFormacionService tipoFormacionService = BeanFactory.getTipoFormacionService();
                tipoFormacionBean.setModiPor(usuarioLoginBean.getUsuario());
                tipoFormacionService.modificar(tipoFormacionBean);
                listaTipoFormacionBean = tipoFormacionService.obtenerTodos();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                this.limpiarTF();
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String eliminarTF() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                TipoFormacionService tipoFormacionService = BeanFactory.getTipoFormacionService();
                tipoFormacionService.eliminar(tipoFormacionBean);
                listaTipoFormacionBean = tipoFormacionService.obtenerTodos();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                this.limpiarTF();
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String guardarTF() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                if (tipoFormacionBean.getIdTipoFormacion() == null) {
                    insertarTF();
                } else {
                    modificarTF();
                }
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    //--------------------------------------- Metodos de Nivel Académico ---------------------------
    public void limpiarNA() {
        nivelAcademicoBean = new NivelAcademicoBean();
    }

    public void obtenerTodosNA() {
        try {
            NivelAcademicoService nivelAcademicoService = BeanFactory.getNivelAcademicoService();
            listaNivelAcademicoBean = nivelAcademicoService.obtenerTodos();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorIdNA(NivelAcademicoBean nivelAcad) {
        try {
            NivelAcademicoService nivelAcademicoService = BeanFactory.getNivelAcademicoService();
            nivelAcademicoBean = nivelAcademicoService.obtenerPorId(nivelAcad);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void rowSelectNA(SelectEvent event) {
        try {
            nivelAcademicoBean = (NivelAcademicoBean) event.getObject();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String insertarNA() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                NivelAcademicoService nivelAcademicoService = BeanFactory.getNivelAcademicoService();
                nivelAcademicoService.insertar(nivelAcademicoBean);
                listaNivelAcademicoBean = nivelAcademicoService.obtenerTodos();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                this.limpiarNA();
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarNA() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                NivelAcademicoService nivelAcademicoService = BeanFactory.getNivelAcademicoService();
                nivelAcademicoService.modificar(nivelAcademicoBean);
                listaNivelAcademicoBean = nivelAcademicoService.obtenerTodos();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                this.limpiarNA();
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String eliminarNA() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                NivelAcademicoService nivelAcademicoService = BeanFactory.getNivelAcademicoService();
                nivelAcademicoService.eliminar(nivelAcademicoBean);
                listaNivelAcademicoBean = nivelAcademicoService.obtenerTodos();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                this.limpiarNA();
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String guardarNA() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                if (nivelAcademicoBean.getIdNivelAcademico() == null) {
                    insertarNA();
                } else {
                    modificarNA();
                }
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    //--------------------------------------- Metodos de Grado Académico ---------------------------
    public void limpiarGA() {
        gradoAcademicoBean = new GradoAcademicoBean();
    }

    public void obtenerTodosGA() {
        try {
            GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
            listaGradoAcademicoBean = gradoAcademicoService.obtenerTodos();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorIdGA(GradoAcademicoBean gradoAcad) {
        try {
            GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
            gradoAcademicoBean = gradoAcademicoService.obtenerPorId(gradoAcad);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void rowSelectGA(SelectEvent event) {
        try {
            gradoAcademicoBean = (GradoAcademicoBean) event.getObject();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String insertarGA() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
                gradoAcademicoService.insertar(gradoAcademicoBean);
                listaGradoAcademicoBean = gradoAcademicoService.obtenerTodos();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                this.limpiarGA();
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarGA() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
                gradoAcademicoService.modificar(gradoAcademicoBean);
                listaGradoAcademicoBean = gradoAcademicoService.obtenerTodos();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                this.limpiarGA();
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String eliminarGA() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
                gradoAcademicoService.eliminar(gradoAcademicoBean);
                listaGradoAcademicoBean = gradoAcademicoService.obtenerTodos();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                this.limpiarGA();
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String guardarGA() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                if (gradoAcademicoBean.getIdGradoAcademico() == null) {
                    insertarGA();
                } else {
                    modificarGA();
                }
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void obtenerNivelPorTipoForm() {
        try {
            NivelAcademicoService nivelAcademicoService = BeanFactory.getNivelAcademicoService();
            listaNivelAcademicoFiltroBean = nivelAcademicoService.obtenerPorTipoFormacion(this.idTipoForm);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
