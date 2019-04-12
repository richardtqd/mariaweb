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
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TreeDragDropEvent;
import org.primefaces.model.TreeNode;
import pe.marista.sigma.bean.ModuloBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.ModuloService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author Administrador
 */
public class ModuloMB implements Serializable {

    /**
     * Creates a new instance of ModuloMB
     */
    @PostConstruct
    public void ModuloMB() {
        try {
            usuarioLogin = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
        listaModuloBean = new ArrayList<>();

    }
    //VAriables
    private TreeNode root;
    private TreeNode selectedNode;
    private ModuloBean moduloBean;
    private List<ModuloBean> listaModuloBean;
    private UsuarioBean usuarioLogin;

    //Metodos
//    public void onDragDrop(TreeDragDropEvent event) {
//        TreeNode dragNode = event.getDragNode();
//        TreeNode dropNode = event.getDropNode();
//
//        int dropIndex = event.getDropIndex();
//
//    }
    public String moverModulo(TreeDragDropEvent event) {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                ModuloBean moduloOrigen = (ModuloBean) event.getDragNode().getData();
                ModuloBean moduloDestino = (ModuloBean) event.getDropNode().getData();
                ModuloService moduloService = BeanFactory.getModuloService();
                moduloOrigen.setIdModuloPadre(moduloDestino.getIdModulo());
                moduloService.actualizarPadre(moduloOrigen);
                ordenamiento(moduloOrigen, moduloDestino, event.getDropIndex());
                //ordenarModulo(moduloOrigen.getIdModulo(), moduloDestino.getIdModulo(), moduloOrigen, event.getDropIndex());
                obtenerTodos();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;

    }

    public void ordenamiento(ModuloBean origen, ModuloBean destino, Integer posicionDestino) {
        try {
            //DEFINIENDO LISTA Y CLASE DE MODULOS
            List<ModuloBean> listaModulo = new ArrayList<>();
            ModuloBean modulo = new ModuloBean();
            ModuloBean mod = new ModuloBean();

            //OBTENIENDO LISTA Y OBJ
            ModuloService moduloService = BeanFactory.getModuloService();
            listaModulo = moduloService.obtenerModuloPadrePos(destino.getIdModulo());
            Integer cero = moduloService.obtenerPosicionCero(destino.getIdModulo());

            //LIMPIANDO POSICIONES  
            if (!cero.equals(0)) {
                if (!listaModulo.isEmpty()) {
                    moduloService.modificarOrdenamiento(destino);
                }
            }

            //MODULOS DE ORIGEN Y DESTINO
            System.out.println("ORIGEN >>>" + origen.getIdModulo() + " POS: " + origen.getPosicion());
            System.out.println("DESTINO >>>" + destino.getIdModulo() + " POS: " + destino.getPosicion());

            //GENERANDO POSICIONAMIENTO
            mod.setPosicion(posicionDestino);
            mod.setIdModulo(origen.getIdModulo());
            moduloService.modificarPosicionPro(mod);
            modulo = moduloService.obtenerPorId(origen);
            moduloService.modificarOrdenamientoPro(destino);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ordenarModulo(Integer idModuloOrigen, Integer idModuloDestino, ModuloBean moduloOrigen, Integer posicionDestino) {
        try {
            ModuloService moduloService = BeanFactory.getModuloService();
            List<ModuloBean> listaModuloBean = new ArrayList<>();
            ModuloBean moduloBean = new ModuloBean();
            listaModuloBean = moduloService.obtenerModuloPadre(idModuloDestino);
            moduloBean = moduloService.obtenerPorId(moduloOrigen);
            Integer posicion = moduloBean.getPosicion();
            Integer k = 0;
            /* Modificar posicion */
            System.out.println(">>>> " + posicion + " >>>> " + posicionDestino);
            if (posicion > posicionDestino) {
                System.out.println("cambio" + 1);
                moduloService.modificarPosicion(posicionDestino + 1, idModuloOrigen, idModuloDestino);
            } else {
                if (posicion < posicionDestino) {
                    System.out.println("cambio" + 2);
                    moduloService.modificarPosicion(posicionDestino, idModuloOrigen, idModuloDestino);
                } else {
                    if (posicion.equals(posicionDestino)) {
                        System.out.println("cambio" + 3);
                        moduloService.modificarPosicion(posicionDestino, idModuloOrigen, idModuloDestino);
                    }
                }
            }
            /* Cambio de Posiciones */
            if (posicion > posicionDestino) {
                listaModuloBean = moduloService.obtenerModuloPadrePosicion(idModuloDestino, posicionDestino, posicion, idModuloOrigen);
                for (int i = 1; i < listaModuloBean.size(); i++) {
                    Integer pos = posicionDestino + i;
                    moduloService.modificarPosicion(pos + 1, listaModuloBean.get(i).getIdModulo(), idModuloDestino);
                }
            } else {
                if (posicion < posicionDestino) {
                    listaModuloBean = moduloService.obtenerModuloPadrePosicion(idModuloDestino, posicion, posicionDestino, idModuloOrigen);
                    for (int i = 0; i < listaModuloBean.size(); i++) {
                        Integer pos = posicionDestino - i;
                        moduloService.modificarPosicion(pos - 1, listaModuloBean.get(i).getIdModulo(), idModuloDestino);
                    }
                } else {
                    if (posicion.equals(posicionDestino)) {
                        listaModuloBean = moduloService.obtenerModuloPadrePosicion(idModuloDestino, posicion, posicionDestino, idModuloOrigen);
                        for (int i = 0; i < listaModuloBean.size(); i++) {
                            Integer pos = posicionDestino - i;
                            moduloService.modificarPosicion(pos - 1, listaModuloBean.get(i).getIdModulo(), idModuloDestino);
                        }
                    }
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void displaySelectedSingle() {
        if (selectedNode != null) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", selectedNode.getData().toString());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public String eliminarModulo() {
        String pagina = null;
        try {
//            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                ModuloService moduloService = BeanFactory.getModuloService();
//                ModuloBean moduloSeleccionado = (ModuloBean) selectedNode.getData();
//                moduloBean.setIdModuloPadre(moduloSeleccionado.getIdModulo());
                ModuloBean moduloSeleccionado = (ModuloBean) selectedNode.getData();
                moduloService.eliminar(moduloSeleccionado);
                limpiarModuloBean();
                obtenerTodos();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }
//    public void deleteNode() {
//        selectedNode.getChildren().clear();
//        selectedNode.getParent().getChildren().remove(selectedNode);
//        selectedNode.setParent(null);
//
//        selectedNode = null;
//    }

//    public void addNode() {
//
//        TreeNode node0 = new DefaultTreeNode(moduloBean.getNodo(), selectedNode1);
//        selectedNode1.getChildren().add(node0);
////        selectedNode1.getParent().getChildren().remove(selectedNode1);
//        selectedNode1.setParent(null);
//        selectedNode1 = null;
//    }
    public String guardarModulo() {
        String pagina = null;
        try {
//            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                if (moduloBean.getIdModulo() != null) {
                    modificarModulo();
                } else {
                    insertarModulo();
                }
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String insertarModulo() {
        String pagina = null;
        try {
//            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
//                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                ModuloService moduloService = BeanFactory.getModuloService();
                ModuloBean moduloSeleccionado = (ModuloBean) selectedNode.getData();
                moduloBean.setIdModuloPadre(moduloSeleccionado.getIdModulo());

                moduloBean.setCreaPor(usuarioLogin.getUsuario());

//                moduloBean.setCreaPor(pagina);
//                moduloBean.setCreaPor(new );
                moduloService.insertar(moduloBean);
                limpiarModuloBean();
                obtenerTodos();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarModulo() {
        String pagina = null;
        try {
//            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                ModuloService moduloService = BeanFactory.getModuloService();
//                ModuloBean moduloSeleccionado = (ModuloBean) selectedNode.getData();
//                moduloBean.setIdModuloPadre(moduloSeleccionado.getIdModulo());
                moduloService.actualizar(moduloBean);
                limpiarModuloBean();
                obtenerTodos();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;

    }

    public void limpiarModuloBean() {
        moduloBean = new ModuloBean();
    }

    public void obtenerTodos() {
        try {
            ModuloService moduloService = BeanFactory.getModuloService();
            listaModuloBean = moduloService.obtenerTodos();
            root = moduloService.mappearModulos(listaModuloBean);
//            root.setExpanded(true);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorId() {
        try {
            ModuloService moduloService = BeanFactory.getModuloService();
            ModuloBean moduloSeleccionado = (ModuloBean) selectedNode.getData();
            moduloBean = moduloSeleccionado;
            moduloBean.setIdModuloPadre(moduloSeleccionado.getIdModulo());
            moduloBean = moduloService.obtenerPorId(moduloBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //Metodos Getter y Setters
    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public ModuloBean getModuloBean() {
        if (moduloBean == null) {
            moduloBean = new ModuloBean();
        }
        return moduloBean;
    }

    public void setModuloBean(ModuloBean moduloBean) {
        this.moduloBean = moduloBean;
    }

    public List<ModuloBean> getListaModuloBean() {
        if (listaModuloBean == null) {
            listaModuloBean = new ArrayList<>();
        }
        return listaModuloBean;
    }

    public void setListaModuloBean(List<ModuloBean> listaModuloBean) {
        this.listaModuloBean = listaModuloBean;
    }

}
