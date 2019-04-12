package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.DocumentoBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.CargoBean;
import pe.marista.sigma.bean.CargoUniNegBean;
import pe.marista.sigma.bean.DocumentoCargoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.CargoService;
import pe.marista.sigma.service.CargoUniNegService;
import pe.marista.sigma.service.DocumentoCargoService;
import pe.marista.sigma.service.DocumentoService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author Administrador
 */
public class CargoMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of CargoMB
     */
    @PostConstruct
    public void CargoMB() {
        try {
            beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");

            CodigoService codigoService = BeanFactory.getCodigoService();
            getListaCargoBean();
            listaTipoCategoriaCargoBean = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_CAT_CARGO));

//            CodigoService codigoService = BeanFactory.getCodigoService();
            getListaTipoCopiaBean();
            listaTipoCopiaBean = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_COPIA));

            cargarLista();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    private CargoBean cargoBean;
    private CargoUniNegBean cargoUniNegBean;
    private List<CargoBean> listaCargoBean;

    private List<CargoUniNegBean> listaCargoUniNegBean;
    private List<CodigoBean> listaTipoCategoriaCargoBean;

    //Documento
    private DualListModel<DocumentoBean> dualListaDocumentoBean;
    private List<DocumentoBean> listaDocumentoBean;
    private List<DocumentoBean> listaDocumentoDest;

    //Doc-Car
    private DocumentoCargoBean documentoCargoBean;
    private List<DocumentoCargoBean> listaDocumentoCargoBean;
    private List<CodigoBean> listaTipoCopiaBean;
    private UsuarioBean beanUsuarioSesion;

    //Logica de Negoci
    public void limpiarCargo() {
        cargoBean = new CargoBean();
    }

    public void obtenerTodosCargo() {
        try {
            CargoService cargoService = BeanFactory.getCargoService();
            listaCargoBean = cargoService.obtenerTodos();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerCargoPorUniNeg() {
        try {
            CargoUniNegService cargoUniNegService = BeanFactory.getCargoUniNegService();
            listaCargoUniNegBean = cargoUniNegService.obtenerCargoUniNegPorUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerCargoPorId(Integer idCargo) {
        try {
            CargoService cargoService = BeanFactory.getCargoService();
//            getCargoBean().setIdCargo(idCargo);
            cargoBean = cargoService.obtenerCargoPorId(idCargo);
            cargoUniNegBean.setCargoBean(cargoBean);
            cargoUniNegBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
            DocumentoCargoService documentoCargo = BeanFactory.getDocumentoCargoService();
            listaDocumentoCargoBean = documentoCargo.obtenerPorCargo(cargoBean);
            if (listaDocumentoCargoBean.isEmpty()) {
                listaDocumentoCargoBean = new ArrayList<>();
            }
            cargarLista();
            for (int i = 0; i < listaDocumentoCargoBean.size(); i++) {
                DocumentoBean documentoBean = new DocumentoBean();
                documentoBean.setIdDocumento(listaDocumentoCargoBean.get(i).getDocumentoBean().getIdDocumento());
                documentoBean.setNombre(listaDocumentoCargoBean.get(i).getDocumentoBean().getNombre());

                listaDocumentoDest.add(documentoBean);
                for (int j = 0; j < listaDocumentoBean.size(); j++) {
                    if (listaDocumentoBean.get(j).getIdDocumento().toString().equals(documentoBean.getIdDocumento().toString())) {
                        System.out.println("idDocumento 1:" + listaDocumentoBean.get(j).getIdDocumento() + "idDocumento 2:" + documentoBean.getIdDocumento());
                        listaDocumentoBean.remove(j);
                        break;
                    }
                }
            }
            dualListaDocumentoBean = new DualListModel<>(listaDocumentoBean, listaDocumentoDest);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarCargo() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
//            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            if (pagina == null) {
                CargoService cargoService = BeanFactory.getCargoService();
                CargoUniNegService cargoUniNegService = BeanFactory.getCargoUniNegService();
                DocumentoCargoBean documentoCargo = new DocumentoCargoBean();
                cargoBean.setCreaPor(beanUsuarioSesion.getUsuario());
                documentoCargo.setCreaPor(beanUsuarioSesion.getUsuario());
                cargoService.insertarCargo(cargoBean, dualListaDocumentoBean.getTarget(), documentoCargo,beanUsuarioSesion);
//                listaCargoBean = cargoService.obtenerTodos();

                listaCargoUniNegBean = cargoUniNegService.obtenerCargoUniNegPorUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                DocumentoCargoService documentoCargoService = BeanFactory.getDocumentoCargoService();
                listaDocumentoCargoBean = documentoCargoService.obtenerPorCargo(cargoBean);
                if (listaDocumentoCargoBean.isEmpty()) {
                    listaDocumentoCargoBean = new ArrayList<>();
                }
//                limpiarCargo();
//                cargarLista();
                obtenerCargoPorId(cargoBean.getIdCargo());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String cambiarEstadoCargo() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                CargoService cargoService = BeanFactory.getCargoService();
                if (cargoBean.isStatus()) {
                    cargoBean.setStatus(true);
                } else {
                    cargoBean.setStatus(false);
                }
                cargoService.cambiarEstado(cargoBean);
                listaCargoBean = cargoService.obtenerTodos();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String cambiarEstadoCargouniNeg() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                CargoUniNegService cargoUniNegService = BeanFactory.getCargoUniNegService();
                if (cargoUniNegBean.getCargoBean().isStatus() == true) {
                    cargoUniNegBean.getCargoBean().setStatus(true);
                } else {
                    cargoUniNegBean.getCargoBean().setStatus(false);
                }
//                CargoUniNegBean car = new CargoUniNegBean();
//                car.setCargoBean(cargoBean);
//                car.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                cargoUniNegService.cambiarEstadoCargoUniNeg(cargoUniNegBean);
                listaCargoUniNegBean = cargoUniNegService.obtenerCargoUniNegPorUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarCargo() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                CargoService cargoService = BeanFactory.getCargoService();
                CargoUniNegService cargoUniNegService = BeanFactory.getCargoUniNegService();
                cargoService.modificarCargo(cargoBean, dualListaDocumentoBean.getTarget(), dualListaDocumentoBean.getSource());
                listaCargoBean = cargoService.obtenerTodos();
                listaCargoUniNegBean = cargoUniNegService.obtenerCargoUniNegPorUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                DocumentoCargoService documentoCargo = BeanFactory.getDocumentoCargoService();
                listaDocumentoCargoBean = documentoCargo.obtenerPorCargo(cargoBean);
                if (listaDocumentoCargoBean.isEmpty()) {
                    listaDocumentoCargoBean = new ArrayList<>();
                }
//                limpiarCargo();
//                cargarLista();
                obtenerCargoPorId(cargoBean.getIdCargo());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String eliminarCargo() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                CargoService cargoService = BeanFactory.getCargoService();
                cargoService.eliminarCargo(cargoBean.getIdCargo());
                listaCargoBean = cargoService.obtenerTodos();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void guardarCargo() {
        if (cargoBean.getIdCargo() == null) {
            insertarCargo();
        } else {
            modificarCargo();
        }
    }

    public void rowSelect(SelectEvent event) {
        try {
            cargoBean = (CargoBean) event.getObject();
            CargoService cargoService = BeanFactory.getCargoService();
            cargoBean = cargoService.obtenerCargoPorId(cargoBean.getIdCargo());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ponerCargo(Object cargo) {
//        String pagina = null;
        try {
            cargoBean = (CargoBean) cargo;
        } catch (Exception err) {
//            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ponerCargoUniNeg(Object cargo) {
//        String pagina = null;
        try {
            CargoUniNegService cargoUniNegService = BeanFactory.getCargoUniNegService();
            cargoUniNegBean = (CargoUniNegBean) cargo;
            cargoUniNegBean = cargoUniNegService.obtenerCargoUniNegPorId(cargoUniNegBean);

        } catch (Exception err) {
//            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //solo cargo
    public String insertarSoloCargo() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
//            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            if (pagina == null) {
                CargoService cargoService = BeanFactory.getCargoService();
                cargoBean.setCreaPor(beanUsuarioSesion.getUsuario()); 
                cargoService.insertarSoloCargo(cargoBean);
                listaCargoBean = cargoService.obtenerTodos();
                limpiarCargo();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarSoloCargo() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                CargoService cargoService = BeanFactory.getCargoService();
                cargoBean.setModiPor(beanUsuarioSesion.getUsuario());
                cargoService.modificarSoloCargo(cargoBean);
                listaCargoBean = cargoService.obtenerTodos();
                limpiarCargo();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void guardarSoloCargo() {
        if (cargoBean.getIdCargo() == null) {
            insertarSoloCargo();
        } else {
            modificarSoloCargo();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //lista Documentos
    public final void cargarLista() {
        try {
            listaDocumentoBean = new ArrayList<>();
            listaDocumentoDest = new ArrayList<>();
            DocumentoService documentoService = BeanFactory.getDocumentoService();
            listaDocumentoBean = documentoService.obtenerTodos();
            dualListaDocumentoBean = new DualListModel<>(listaDocumentoBean, listaDocumentoDest);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    //DocumentoCargo

    public void obtenerDocCargoPorId(Object object) {
        try {
            documentoCargoBean = (DocumentoCargoBean) object;
            DocumentoCargoService documentoCargoService = BeanFactory.getDocumentoCargoService();
            documentoCargoBean = documentoCargoService.obtenerDocumentoCargoPorId(documentoCargoBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void onRowEdit(RowEditEvent event) {
        try {

            DocumentoCargoService documentoCargoService = BeanFactory.getDocumentoCargoService();
            documentoCargoBean = new DocumentoCargoBean();
            //Seteando los ID's
            documentoCargoBean.getDocumentoBean().setIdDocumento(((DocumentoCargoBean) event.getObject()).getDocumentoBean().getIdDocumento());
            documentoCargoBean.getCargoBean().setIdCargo(((DocumentoCargoBean) event.getObject()).getCargoBean().getIdCargo());

            documentoCargoBean.setFlgObligatorio(((DocumentoCargoBean) event.getObject()).getFlgObligatorio());
            documentoCargoBean.getTipoCopiaBean().setIdCodigo(((DocumentoCargoBean) event.getObject()).getTipoCopiaBean().getIdCodigo());
            documentoCargoBean.setStatus(((DocumentoCargoBean) event.getObject()).getStatus());

            documentoCargoService.modificarDocumentoCargo(documentoCargoBean);

            listaDocumentoCargoBean = documentoCargoService.obtenerPorCargo(cargoUniNegBean.getCargoBean());
            FacesMessage msg = new FacesMessage("Registro Modificado:", ((DocumentoCargoBean) event.getObject()).getDocumentoBean().getNombre());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            documentoCargoBean = new DocumentoCargoBean();

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edición Cancelada", ((DocumentoCargoBean) event.getObject()).getDocumentoBean().getNombre());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public CargoBean getCargoBean() {
        if (cargoBean == null) {
            cargoBean = new CargoBean();
        }
        return cargoBean;
    }

    public void setCargoBean(CargoBean cargoBean) {
        this.cargoBean = cargoBean;
    }

    public List<CargoBean> getListaCargoBean() {
        return listaCargoBean;
    }

    public void setListaCargoBean(List<CargoBean> listaCargoBean) {
        this.listaCargoBean = listaCargoBean;
    }

    public List<CodigoBean> getListaTipoCategoriaCargoBean() {
        return listaTipoCategoriaCargoBean;
    }

    public void setListaTipoCategoriaCargoBean(List<CodigoBean> listaTipoCategoriaCargoBean) {
        this.listaTipoCategoriaCargoBean = listaTipoCategoriaCargoBean;
    }

    public DualListModel<DocumentoBean> getDualListaDocumentoBean() {
        if (dualListaDocumentoBean == null) {
            dualListaDocumentoBean = new DualListModel<>();
        }
        return dualListaDocumentoBean;
    }

    public void setDualListaDocumentoBean(DualListModel<DocumentoBean> dualListaDocumentoBean) {
        this.dualListaDocumentoBean = dualListaDocumentoBean;
    }

    public List<DocumentoBean> getListaDocumentoBean() {
        if (listaDocumentoBean == null) {
            listaDocumentoBean = new ArrayList<>();
        }
        return listaDocumentoBean;
    }

    public void setListaDocumentoBean(List<DocumentoBean> listaDocumentoBean) {
        this.listaDocumentoBean = listaDocumentoBean;
    }

    public List<DocumentoBean> getListaDocumentoDest() {
        if (listaDocumentoDest == null) {
            listaDocumentoDest = new ArrayList<>();
        }
        return listaDocumentoDest;
    }

    public void setListaDocumentoDest(List<DocumentoBean> listaDocumentoDest) {
        this.listaDocumentoDest = listaDocumentoDest;
    }

    public DocumentoCargoBean getDocumentoCargoBean() {
        if (documentoCargoBean == null) {
            documentoCargoBean = new DocumentoCargoBean();
        }
        return documentoCargoBean;
    }

    public void setDocumentoCargoBean(DocumentoCargoBean documentoCargoBean) {
        this.documentoCargoBean = documentoCargoBean;
    }

    public List<DocumentoCargoBean> getListaDocumentoCargoBean() {
        if (listaDocumentoCargoBean == null) {
            listaDocumentoCargoBean = new ArrayList<>();
        }
        return listaDocumentoCargoBean;
    }

    public void setListaDocumentoCargoBean(List<DocumentoCargoBean> listaDocumentoCargoBean) {
        this.listaDocumentoCargoBean = listaDocumentoCargoBean;
    }

    public List<CodigoBean> getListaTipoCopiaBean() {
        if (listaTipoCopiaBean == null) {
            listaTipoCopiaBean = new ArrayList<>();
        }
        return listaTipoCopiaBean;
    }

    public void setListaTipoCopiaBean(List<CodigoBean> listaTipoCopiaBean) {
        this.listaTipoCopiaBean = listaTipoCopiaBean;
    }

    public List<CargoUniNegBean> getListaCargoUniNegBean() {
        if (listaCargoUniNegBean == null) {
            listaCargoUniNegBean = new ArrayList<>();
        }
        return listaCargoUniNegBean;
    }

    public void setListaCargoUniNegBean(List<CargoUniNegBean> listaCargoUniNegBean) {
        this.listaCargoUniNegBean = listaCargoUniNegBean;
    }

    public CargoUniNegBean getCargoUniNegBean() {
        if (cargoUniNegBean == null) {
            cargoUniNegBean = new CargoUniNegBean();
        }
        return cargoUniNegBean;
    }

    public void setCargoUniNegBean(CargoUniNegBean cargoUniNegBean) {
        this.cargoUniNegBean = cargoUniNegBean;
    }

}
