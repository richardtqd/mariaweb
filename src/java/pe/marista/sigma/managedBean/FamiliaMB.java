package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.EstudianteBean;
import pe.marista.sigma.bean.FamiliaBean;
import pe.marista.sigma.bean.FamiliarBean;
import pe.marista.sigma.bean.FamiliarEstudianteBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.FamiliaService;
import pe.marista.sigma.service.FamiliarService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author Administrador
 */
public class FamiliaMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of FamiliaMB
     */
    @PostConstruct
    public void init() {
        try {
//            getListaTipoResPagoBean();
//            CodigoService codigoService = BeanFactory.getCodigoService();
//            listaTipoResPagoBean = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_RES_PAGO));
            usuarioLoginBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            getFamiliaBean().setStatus(true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    private FamiliaBean familiaBean;
    private FamiliaBean familiaFiltroBean;
    private List<FamiliaBean> listaFamiliaBean;
    private FamiliarEstudianteBean padreEstudianteBean;
    private FamiliarEstudianteBean madreEstudianteBean;
    private List<FamiliarEstudianteBean> listaFamiliarEstudianteBean;
    private FamiliarEstudianteBean familiarEstudianteBean;
    private UsuarioBean usuarioLoginBean;
    private EstudianteBean estudianteBean;
    private FamiliarBean familiarBean;
//    private List<CodigoBean> listaTipoResPagoBean;
    private boolean skip;
    private String tipoFam;

    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }

    public void obtenerFamilia() {
        try {
            FamiliaService familiaService = BeanFactory.getFamiliaService();
            listaFamiliaBean = familiaService.obtenerFamilia();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerFiltroFamilia() {
        try {
            int estado = 0;
            //Padre
            if (familiaFiltroBean.getPadreBean().getPersonaBean().getIdPersona() != null && !familiaFiltroBean.getPadreBean().getPersonaBean().getIdPersona().equals("")) {
                familiaFiltroBean.getPadreBean().getPersonaBean().setIdPersona(familiaFiltroBean.getPadreBean().getPersonaBean().getIdPersona().trim().toUpperCase());
                estado = 1;
            }
            if (familiaFiltroBean.getPadreBean().getPersonaBean().getApepat() != null && !familiaFiltroBean.getPadreBean().getPersonaBean().getApepat().equals("")) {
                familiaFiltroBean.getPadreBean().getPersonaBean().setApepat(familiaFiltroBean.getPadreBean().getPersonaBean().getApepat().trim().toUpperCase());
                estado = 1;
            }
            if (familiaFiltroBean.getPadreBean().getPersonaBean().getApemat() != null && !familiaFiltroBean.getPadreBean().getPersonaBean().getApemat().equals("")) {
                familiaFiltroBean.getPadreBean().getPersonaBean().setApemat(familiaFiltroBean.getPadreBean().getPersonaBean().getApemat().trim().toUpperCase());
                estado = 1;
            }
            if (familiaFiltroBean.getPadreBean().getPersonaBean().getNombre() != null & !familiaFiltroBean.getPadreBean().getPersonaBean().getNombre().equals("")) {
                familiaFiltroBean.getPadreBean().getPersonaBean().setNombre(familiaFiltroBean.getPadreBean().getPersonaBean().getNombre().trim().toUpperCase());
                estado = 1;
            }
            //Madre
            if (familiaFiltroBean.getMadreBean().getPersonaBean().getIdPersona() != null && !familiaFiltroBean.getMadreBean().getPersonaBean().getIdPersona().equals("")) {
                familiaFiltroBean.getMadreBean().getPersonaBean().setIdPersona(familiaFiltroBean.getMadreBean().getPersonaBean().getIdPersona().trim().toUpperCase());
                estado = 1;
            }
            if (familiaFiltroBean.getMadreBean().getPersonaBean().getApepat() != null && !familiaFiltroBean.getMadreBean().getPersonaBean().getApepat().equals("")) {
                familiaFiltroBean.getMadreBean().getPersonaBean().setApepat(familiaFiltroBean.getMadreBean().getPersonaBean().getApepat().trim().toUpperCase());
                estado = 1;
            }
            if (familiaFiltroBean.getMadreBean().getPersonaBean().getApemat() != null && !familiaFiltroBean.getMadreBean().getPersonaBean().getApemat().equals("")) {
                familiaFiltroBean.getMadreBean().getPersonaBean().setApemat(familiaFiltroBean.getMadreBean().getPersonaBean().getApemat().trim().toUpperCase());
                estado = 1;
            }
            if (familiaFiltroBean.getMadreBean().getPersonaBean().getNombre() != null && !familiaFiltroBean.getMadreBean().getPersonaBean().getNombre().equals("")) {
                familiaFiltroBean.getMadreBean().getPersonaBean().setNombre(familiaFiltroBean.getMadreBean().getPersonaBean().getNombre().trim().toUpperCase());
                estado = 1;
            } else if (estado == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaFamiliaBean = new ArrayList<>();
            }
            if (estado == 1) {
                FamiliaService familiaService = BeanFactory.getFamiliaService();
                familiaFiltroBean.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                listaFamiliaBean = familiaService.obtenerFiltroFamilia(familiaFiltroBean);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
//    public void obtenerFamiliaPorUniNeg() {
//        try {
//            FamiliaService familiaService = BeanFactory.getFamiliaService();
//            listaFamiliaBean = familiaService.obtenerFamilia();
//        } catch (Exception err) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), err);
//        }
//    }

    public void obtenerFamiliaPorId(Object object) {
        try {
            familiaBean = (FamiliaBean) object;
            FamiliaService familiaService = BeanFactory.getFamiliaService();
            familiaBean = familiaService.obtenerFamiliaPorId(getFamiliaBean());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarFamilia() {
        familiaBean = new FamiliaBean();
    }

    public String insertarFamilia(Object idEstudiante) {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                String objeto = (String) idEstudiante;
                FamiliaService familiaService = BeanFactory.getFamiliaService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
//                familiaBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
//                familiaBean.setCreaPor(beanUsuarioSesion.getUsuario());
                EstudianteMB estudianteMB = (EstudianteMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("estudianteMB");
                if (estudianteMB != null) {
                    familiaService.insertarFamilia(estudianteMB.getFamiliaBean(), objeto);
                    limpiarFamilia();
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                }
//                listaFamiliaBean = familiaService.obtenerFiltroFamilia(familiaFiltroBean);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarFamilia(Object idEstudiante) {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                String objeto = (String) idEstudiante;
                FamiliaService familiaService = BeanFactory.getFamiliaService();
                familiaService.modificarFamilia(familiaBean, objeto);
//                listaFamiliaBean = familiaService.obtenerFiltroFamilia(familiaFiltroBean);
                limpiarFamilia();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String eliminarFamilia() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                FamiliaService familiaService = BeanFactory.getFamiliaService();
                familiaService.eliminarFamilia(familiaBean.getIdFamilia());
//                listaFamiliaBean = familiaService.obtenerFiltroFamilia(familiaFiltroBean);
                limpiarFamilia();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void rowSelect(SelectEvent event) {
        try {
            familiaBean = (FamiliaBean) event.getObject();
            FamiliaService familiaService = BeanFactory.getFamiliaService();
            familiaBean = familiaService.obtenerFamiliaPorId(familiaBean);
            System.out.println("");
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void guardarFamilia(Object idEstudiante) {
        try {
            EstudianteMB estudianteMB = (EstudianteMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("estudianteMB");
            if (estudianteMB.getFamiliaBean().getIdFamilia() == null) {
                insertarFamilia(idEstudiante);
            } else {
                modificarFamilia(idEstudiante);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
//    public void ponerFamiliarEst(Object familiar) {
//        try {
//            familiarBean = (FamiliarBean) familiar;
//            FamiliaMB familiaMB = (FamiliaMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("familiaMB");
//            switch (tipoFam) {
//                case "padre":
//                    familiaMB.getPadreEstudianteBean().setFamiliarBean(familiarBean);
//                    break;
//                case "madre":
//                    familiaMB.getMadreEstudianteBean().setFamiliarBean(familiarBean);
//                    break;
//                case "otro":
//                    familiaMB.getFamiliarEstudianteBean().setFamiliarBean(familiarBean);
//                    break;
//            }
//            FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("familiaMB", familiaMB);
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//    }
//    public void limpiarFamiliarFiltro(String tip) {
//        try {
//            tipoFam = tip;
//            familiarFiltroBean = new FamiliarBean();
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//    }
    //Familiares

    public void limpiarFamiliarEstudiante() {
        familiarEstudianteBean = new FamiliarEstudianteBean();
    }

    public void limpiarPadreEstudianteBean() {
        padreEstudianteBean = new FamiliarEstudianteBean();
    }

    public void limpiarMadreEstudianteBean() {
        madreEstudianteBean = new FamiliarEstudianteBean();
    }

    public void guardarFamiliar(String tipo) {
        if (tipo.equals(MaristaConstantes.COD_PAPA)) {
//            padreEstudianteBean 
            padreEstudianteBean = new FamiliarEstudianteBean();
            padreEstudianteBean.getFamiliarBean().setPersonaBean(familiaBean.getPadreBean().getPersonaBean());
            padreEstudianteBean.getFamiliarBean().getPersonaBean().setIdPersonaOld(familiaBean.getPadreBean().getPersonaBean().getIdPersona());
            padreEstudianteBean.setFamiliarBean(familiaBean.getPadreBean());
            guardarFamiliarEstudiante(padreEstudianteBean);
        }
        if (tipo.equals(MaristaConstantes.COD_MAMA)) { 
            madreEstudianteBean = new FamiliarEstudianteBean();
            madreEstudianteBean.getFamiliarBean().setPersonaBean(familiaBean.getMadreBean().getPersonaBean());
            madreEstudianteBean.getFamiliarBean().getPersonaBean().setIdPersonaOld(familiaBean.getMadreBean().getPersonaBean().getIdPersona());
            madreEstudianteBean.setFamiliarBean(familiaBean.getMadreBean());
            guardarFamiliarEstudiante(madreEstudianteBean);
        }
        if (tipo.equals(MaristaConstantes.COD_OTRO)) { 
            guardarFamiliarEstudiante(familiarEstudianteBean);
        }
    }

    public String guardarFamiliarEstudiante(FamiliarEstudianteBean familiarEstudiante) {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                FamiliarService familiarService = BeanFactory.getFamiliarService();
                familiarEstudiante.setCreaPor(usuarioLoginBean.getUsuario());
                familiarEstudiante.getFamiliarBean().setCreaPor(usuarioLoginBean.getUsuario());
                familiarEstudiante.setEstudianteBean(estudianteBean);
//                familiarEstudiante.getFamiliarBean().getPersonaBean().setIdPersona(familiarEstudiante.getFamiliarBean().getPersonaBean().getNroDoc());
                familiarEstudiante.getEstudianteBean().getPersonaBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                familiarService.guardarFamiliarEstudiante(familiarEstudiante);
                listaFamiliarEstudianteBean = familiarService.obtenerFamiliarEstPorEstSinPadres(familiarEstudiante.getEstudianteBean().getPersonaBean().getIdPersona());
                limpiarFamiliarEstudiante();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    //Getter y Setter
    public FamiliaBean getFamiliaBean() {
        if (familiaBean == null) {
            familiaBean = new FamiliaBean();
        }
        return familiaBean;
    }

    public void setFamiliaBean(FamiliaBean familiaBean) {
        this.familiaBean = familiaBean;
    }

    public List<FamiliaBean> getListaFamiliaBean() {
        if (listaFamiliaBean == null) {
            listaFamiliaBean = new ArrayList<>();
        }
        return listaFamiliaBean;
    }

    public void setListaFamiliaBean(List<FamiliaBean> listaFamiliaBean) {
        this.listaFamiliaBean = listaFamiliaBean;
    }

//    public List<CodigoBean> getListaTipoResPagoBean() {
//        if (listaTipoResPagoBean == null) {
//            listaTipoResPagoBean = new ArrayList<>();
//        }
//        return listaTipoResPagoBean;
//    }
//
//    public void setListaTipoResPagoBean(List<CodigoBean> listaTipoResPagoBean) {
//        this.listaTipoResPagoBean = listaTipoResPagoBean;
//    }
    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public FamiliaBean getFamiliaFiltroBean() {
        if (familiaFiltroBean == null) {
            familiaFiltroBean = new FamiliaBean();
        }
        return familiaFiltroBean;
    }

    public void setFamiliaFiltroBean(FamiliaBean familiaFiltroBean) {
        this.familiaFiltroBean = familiaFiltroBean;
    }

    public FamiliarEstudianteBean getPadreEstudianteBean() {
        if (padreEstudianteBean == null) {
            padreEstudianteBean = new FamiliarEstudianteBean();
        }
        return padreEstudianteBean;
    }

    public void setPadreEstudianteBean(FamiliarEstudianteBean padreEstudianteBean) {
        this.padreEstudianteBean = padreEstudianteBean;
    }

    public FamiliarEstudianteBean getMadreEstudianteBean() {
        if (madreEstudianteBean == null) {
            madreEstudianteBean = new FamiliarEstudianteBean();
        }
        return madreEstudianteBean;
    }

    public void setMadreEstudianteBean(FamiliarEstudianteBean madreEstudianteBean) {
        this.madreEstudianteBean = madreEstudianteBean;
    }

    public List<FamiliarEstudianteBean> getListaFamiliarEstudianteBean() {
        if (listaFamiliarEstudianteBean == null) {
            listaFamiliarEstudianteBean = new ArrayList<>();
        }
        return listaFamiliarEstudianteBean;
    }

    public void setListaFamiliarEstudianteBean(List<FamiliarEstudianteBean> listaFamiliarEstudianteBean) {
        this.listaFamiliarEstudianteBean = listaFamiliarEstudianteBean;
    }

    public FamiliarEstudianteBean getFamiliarEstudianteBean() {
        if (familiarEstudianteBean == null) {
            familiarEstudianteBean = new FamiliarEstudianteBean();
        }
        return familiarEstudianteBean;
    }

    public void setFamiliarEstudianteBean(FamiliarEstudianteBean familiarEstudianteBean) {
        this.familiarEstudianteBean = familiarEstudianteBean;
    }

    public UsuarioBean getUsuarioLoginBean() {
        if (usuarioLoginBean == null) {
            usuarioLoginBean = new UsuarioBean();
        }
        return usuarioLoginBean;
    }

    public void setUsuarioLoginBean(UsuarioBean usuarioLoginBean) {
        this.usuarioLoginBean = usuarioLoginBean;
    }

    public EstudianteBean getEstudianteBean() {
        if (estudianteBean == null) {
            estudianteBean = new EstudianteBean();
        }
        return estudianteBean;
    }

    public void setEstudianteBean(EstudianteBean estudianteBean) {
        this.estudianteBean = estudianteBean;
    }

    public String getTipoFam() {
        return tipoFam;
    }

    public void setTipoFam(String tipoFam) {
        this.tipoFam = tipoFam;
    }

    public FamiliarBean getFamiliarBean() {
        if (familiarBean == null) {
            familiarBean = new FamiliarBean();
        }
        return familiarBean;
    }

    public void setFamiliarBean(FamiliarBean familiarBean) {
        this.familiarBean = familiarBean;
    }

}
