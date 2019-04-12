package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.primefaces.context.RequestContext;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.TipoCodigoService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author Administrador
 */
public class TipoCodigoMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of TipoCodigoMB
     */
    public TipoCodigoMB() {
    }
    private List<TipoCodigoBean> listaTipoCodigoBean;
    private TipoCodigoBean tipoCodigoBean;
    private TipoCodigoBean tipoCodigoFiltroBean;
    private CodigoBean codigoBean;

    //Metodos Logica
    public void limpiarTipoCodigoBean() {
        tipoCodigoBean = new TipoCodigoBean();
        tipoCodigoBean.setListaCodigoBean(new ArrayList<CodigoBean>());
    }

    public String insertarTipo() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                TipoCodigoService tipoCodigoService = BeanFactory.getTipoCodigoService();
                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                tipoCodigoBean.setCreaPor(usuarioBean.getUsuario());
                tipoCodigoService.insertar(tipoCodigoBean);
                listaTipoCodigoBean = tipoCodigoService.obtenerTodos();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarTipo() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                TipoCodigoService tipoCodigoService = BeanFactory.getTipoCodigoService();
                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                tipoCodigoBean.setModiPor(usuarioBean.getUsuario());
                tipoCodigoService.modificar(tipoCodigoBean);
                listaTipoCodigoBean = tipoCodigoService.obtenerTodos();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String eliminarTipo() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                TipoCodigoService tipoCodigoService = BeanFactory.getTipoCodigoService();
                tipoCodigoService.eliminar(tipoCodigoBean);
                listaTipoCodigoBean = tipoCodigoService.obtenerTodos();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String guardarTipo() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                if (tipoCodigoBean.getEdita() == null) {
                    insertarTipo();
                } else {
                    modificarTipo();
                }
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void obtenerTodos() {
        String parametro = (String) new MaristaUtils().requestObtenerObjeto("TipoUniMed");
        try {
            System.out.println(">>>>>>" + parametro);
            TipoCodigoService tipoCodigoService = BeanFactory.getTipoCodigoService();
            if (parametro == null) {
                listaTipoCodigoBean = tipoCodigoService.obtenerTodos();
            } else {
                if (parametro != null) {
                    listaTipoCodigoBean = tipoCodigoService.obtenerPorTipo(parametro);
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

//    public void obtenerFiltro() {
//        try {
//            TipoCodigoService tipoCodigoService = BeanFactory.getTipoCodigoService();
//            if (tipoCodigoFiltroBean.getTipoCodigo() != null) {
//                tipoCodigoFiltroBean.setTipoCodigo(tipoCodigoFiltroBean.getTipoCodigo().toUpperCase().trim());
//            }
//            if (tipoCodigoFiltroBean.getDescripTipoCodigo() != null) {
//                tipoCodigoFiltroBean.setDescripTipoCodigo(tipoCodigoFiltroBean.getDescripTipoCodigo().toUpperCase().trim());
//            }
//            listaTipoCodigoBean = tipoCodigoService.obtenerFiltro(tipoCodigoFiltroBean);
//
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//    }
    public void obtenerPorId(Object object) {
        try {
            tipoCodigoBean = (TipoCodigoBean) object;
            TipoCodigoService tipoCodigoService = BeanFactory.getTipoCodigoService();
            CodigoService codigoService = BeanFactory.getCodigoService();
            tipoCodigoBean = tipoCodigoService.obtenerPorId(tipoCodigoBean);
            tipoCodigoBean.setEdita(1);
            List<CodigoBean> listaCodigo = codigoService.obtenerPorTipo(tipoCodigoBean);
            tipoCodigoBean.setListaCodigoBean(listaCodigo);
            System.out.println("");
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //Codigos
    public void limpiarCodigoBean() {
        if (tipoCodigoBean.getIdTipoCodigo() != null) {
            codigoBean = new CodigoBean();
        } else {
            guardarTipo();
        }
    }

    public String insertarCodigo() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                CodigoService codigoService = BeanFactory.getCodigoService();
                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                codigoBean.setCreaPor(usuarioBean.getUsuario());
                codigoBean.setTipoCodigoBean(tipoCodigoBean);
                codigoBean.setIdTipo(tipoCodigoBean.getIdTipo());
                codigoService.insertarCodigo(codigoBean);
                tipoCodigoBean.setListaCodigoBean(codigoService.obtenerPorTipo(tipoCodigoBean));
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarCodigo() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                CodigoService codigoService = BeanFactory.getCodigoService();
                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                codigoBean.setModiPor(usuarioBean.getUsuario());
                codigoService.modificarCodigo(codigoBean);
                tipoCodigoBean.setListaCodigoBean(codigoService.obtenerPorTipo(tipoCodigoBean));
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String eliminarCodigo() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                CodigoService codigoService = BeanFactory.getCodigoService();
                codigoService.eliminarCodigo(codigoBean);
                tipoCodigoBean.setListaCodigoBean(codigoService.obtenerPorTipo(tipoCodigoBean));
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void obtenerCodigoPorId(Object object) {
        try {
            codigoBean = (CodigoBean) object;
            CodigoService codigoService = BeanFactory.getCodigoService();
            codigoBean = codigoService.obtenerPorId(codigoBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String guardarCodigo() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                if (codigoBean.getIdCodigo() != null) {
                    modificarCodigo();
                } else {
                    insertarCodigo();
                }
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void obtenerPorTipo() {
        try {
            CodigoService codigoService = BeanFactory.getCodigoService();
            tipoCodigoBean.setListaCodigoBean(codigoService.obtenerPorTipo(tipoCodigoBean));
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    //Metodos Getter y Setter

    public List<TipoCodigoBean> getListaTipoCodigoBean() {
        if (listaTipoCodigoBean == null) {
            listaTipoCodigoBean = new ArrayList<>();
        }
        return listaTipoCodigoBean;
    }

    public void setListaTipoCodigoBean(List<TipoCodigoBean> listaTipoCodigoBean) {
        this.listaTipoCodigoBean = listaTipoCodigoBean;
    }

    public TipoCodigoBean getTipoCodigoBean() {
        if (tipoCodigoBean == null) {
            tipoCodigoBean = new TipoCodigoBean();
        }
        return tipoCodigoBean;
    }

    public void setTipoCodigoBean(TipoCodigoBean tipoCodigoBean) {
        this.tipoCodigoBean = tipoCodigoBean;
    }

    public TipoCodigoBean getTipoCodigoFiltroBean() {
        if (tipoCodigoFiltroBean == null) {
            tipoCodigoFiltroBean = new TipoCodigoBean();
        }
        return tipoCodigoFiltroBean;
    }

    public void setTipoCodigoFiltroBean(TipoCodigoBean tipoCodigoFiltroBean) {
        this.tipoCodigoFiltroBean = tipoCodigoFiltroBean;
    }

    public CodigoBean getCodigoBean() {
        if (codigoBean == null) {
            codigoBean = new CodigoBean();
        }
        return codigoBean;
    }

    public void setCodigoBean(CodigoBean codigoBean) {
        this.codigoBean = codigoBean;
    }

}
