package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import pe.marista.sigma.bean.FamiliarBean;
import pe.marista.sigma.bean.FamiliarEstudianteBean;
import pe.marista.sigma.bean.PersonaBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.FamiliarService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author Administrador
 */
public class FamiliarMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of CajaMB
     */
    @PostConstruct
    public void FamiliarMB() {
        usuario = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        getFamiliarFiltroBean().getPersonaBean().setUnidadNegocioBean(usuario.getPersonalBean().getUnidadNegocioBean());
    }
    private FamiliarBean familiarBean;
    private PersonaBean personaBean;
    private List<FamiliarBean> listaFamiliarBean;
    private List<PersonaBean> listaPersonaFamiliarBean;
    private List<FamiliarEstudianteBean> listaFamiliarEstudianteBean;
    private FamiliarEstudianteBean familiarEstudianteBean;
    private FamiliarBean familiarFiltroBean;
    private String tipoFam;
    private UsuarioBean usuario;

    //Metodos de aplicacion
    public void prepararEstudianteBean() {
        familiarBean = new FamiliarBean();
    }

    public String insertarFamiliar() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                FamiliarService familiarService = BeanFactory.getFamiliarService();
                familiarService.insertarFamiliar(familiarBean);
                listaFamiliarBean = familiarService.obtenerFamiliar();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                prepararEstudianteBean();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void obtenerPorFiltro() {
        try {
            int estado = 0;
            FamiliarService familiarService = BeanFactory.getFamiliarService();
            if (familiarFiltroBean.getPersonaBean().getIdPersona() != null && !familiarFiltroBean.getPersonaBean().getIdPersona().equals("")) {
                familiarFiltroBean.getPersonaBean().setIdPersona(familiarFiltroBean.getPersonaBean().getIdPersona().trim());
                estado = 1;
            }
            if (familiarFiltroBean.getPersonaBean().getApepat() != null && !familiarFiltroBean.getPersonaBean().getApepat().equals("")) {
                familiarFiltroBean.getPersonaBean().setApepat(familiarFiltroBean.getPersonaBean().getApepat().trim());
                estado = 1;
            }
            if (familiarFiltroBean.getPersonaBean().getApemat() != null && !familiarFiltroBean.getPersonaBean().getApemat().equals("")) {
                familiarFiltroBean.getPersonaBean().setApemat(familiarFiltroBean.getPersonaBean().getApemat().trim());
                estado = 1;
            }
            if (familiarFiltroBean.getPersonaBean().getNombre() != null && !familiarFiltroBean.getPersonaBean().getNombre().equals("")) {
                familiarFiltroBean.getPersonaBean().setNombre(familiarFiltroBean.getPersonaBean().getNombre().trim());
                estado = 1;
            }
            if (familiarFiltroBean.getPersonaBean().getGradoAcademicoBean().getIdGradoAcademico() != null && !familiarFiltroBean.getPersonaBean().getGradoAcademicoBean().getIdGradoAcademico().equals(0)) {
                familiarFiltroBean.getPersonaBean().getGradoAcademicoBean().setIdGradoAcademico(familiarFiltroBean.getPersonaBean().getGradoAcademicoBean().getIdGradoAcademico());
                estado = 1;
            } else if (estado == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaFamiliarBean = new ArrayList<>();
                listaPersonaFamiliarBean = new ArrayList<>();
            }
            if (estado == 1) {
                listaPersonaFamiliarBean = new ArrayList<>();
                listaFamiliarBean = new ArrayList<>();
                listaFamiliarBean = familiarService.obtenerFamiliarPorFiltro(familiarFiltroBean);
                if (listaFamiliarBean.isEmpty()) {
                    listaFamiliarBean = new ArrayList<>();
                    listaPersonaFamiliarBean = new ArrayList<>();
                    listaPersonaFamiliarBean = familiarService.obtenerFamiliarPersonaPorFiltro(familiarFiltroBean);
                    if (listaPersonaFamiliarBean.isEmpty()) {
                        listaPersonaFamiliarBean = new ArrayList<>();
                        listaFamiliarBean = new ArrayList<>();
                        new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    }
                }

            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarFamiliarFiltro(String tip) {
        try {
            tipoFam = tip;
            listaFamiliarBean = new ArrayList<>();
            listaPersonaFamiliarBean = new ArrayList<>();
            familiarFiltroBean = new FamiliarBean();
            familiarFiltroBean.getPersonaBean().setUnidadNegocioBean(usuario.getPersonalBean().getUnidadNegocioBean());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ponerFamiliar(Object familiar) {
        try {
            familiarBean = (FamiliarBean) familiar;
            ProspectoMB prospectoMB = (ProspectoMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("prospectoMB");
            switch (tipoFam) {
                case "padre":
                    prospectoMB.getPadreEstudianteBean().setFamiliarBean(familiarBean);
                    break;
                case "madre":
                    prospectoMB.getMadreEstudianteBean().setFamiliarBean(familiarBean);
                    break;
            }
            FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("prospectoMB", prospectoMB);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ponerPersona(Object persona) {
        try {
            personaBean = (PersonaBean) persona;
            getFamiliarBean().setPersonaBean(personaBean);
            ProspectoMB prospectoMB = (ProspectoMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("prospectoMB");
            switch (tipoFam) {
                case "padre":
                    prospectoMB.getPadreEstudianteBean().setFamiliarBean(familiarBean);
                    break;
                case "madre":
                    prospectoMB.getMadreEstudianteBean().setFamiliarBean(familiarBean);
                    break;
            }
            FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("prospectoMB", prospectoMB);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ponerFamiliarEst(Object familiar) {
        try {
            familiarBean = (FamiliarBean) familiar;
            EstudianteMB estudianteMB = (EstudianteMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("estudianteMB");
            switch (tipoFam) {
                case "padre":
                    estudianteMB.getPadreEstudianteBean().setFamiliarBean(familiarBean);
                    break;
                case "madre":
                    estudianteMB.getMadreEstudianteBean().setFamiliarBean(familiarBean);
                    break;
                case "otro":
                    estudianteMB.getFamiliarEstudianteBean().setFamiliarBean(familiarBean);
                    break;
            }
            FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("estudianteMB", estudianteMB);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    public void ponerFamiliarPerEst(Object persona) {
        try {
            personaBean = (PersonaBean) persona;
            getFamiliarBean().setPersonaBean(personaBean);
            EstudianteMB estudianteMB = (EstudianteMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("estudianteMB");
            switch (tipoFam) {
                case "padre":
                    estudianteMB.getPadreEstudianteBean().setFamiliarBean(familiarBean);
                    break;
                case "madre":
                    estudianteMB.getMadreEstudianteBean().setFamiliarBean(familiarBean);
                    break;
                case "otro":
                    estudianteMB.getFamiliarEstudianteBean().setFamiliarBean(familiarBean);
                    break;
            }
            FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("estudianteMB", estudianteMB);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ponerFamiliarEstFam(Object familiar) {
        try {
            familiarBean = (FamiliarBean) familiar;
            FamiliaMB familiaMB = (FamiliaMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("familiaMB");
            switch (tipoFam) {
                case "padre":
                    familiaMB.getFamiliaBean().setPadreBean(familiarBean);
                    break;
                case "madre":
                    familiaMB.getFamiliaBean().setMadreBean(familiarBean);
                    break;
//                case "otro":
//                    familiaMB.getFamiliaBean().setFamiliarBean(familiarBean);
//                    break;
            }
            FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("familiaMB", familiaMB);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //Getter y Setters
    public FamiliarBean getFamiliarBean() {
        if (familiarBean == null) {
            familiarBean = new FamiliarBean();
        }
        return familiarBean;
    }

    public void setFamiliarBean(FamiliarBean familiarBean) {
        this.familiarBean = familiarBean;
    }

    public List<FamiliarBean> getListaFamiliarBean() {
        if (listaFamiliarBean == null) {
            listaFamiliarBean = new ArrayList<>();
        }
        return listaFamiliarBean;
    }

    public void setListaFamiliarBean(List<FamiliarBean> listaFamiliarBean) {
        this.listaFamiliarBean = listaFamiliarBean;
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

    public FamiliarBean getFamiliarFiltroBean() {
        if (familiarFiltroBean == null) {
            familiarFiltroBean = new FamiliarBean();
        }
        return familiarFiltroBean;
    }

    public void setFamiliarFiltroBean(FamiliarBean familiarFiltroBean) {
        this.familiarFiltroBean = familiarFiltroBean;
    }

    public String getTipoFam() {
        return tipoFam;
    }

    public void setTipoFam(String tipoFam) {
        this.tipoFam = tipoFam;
    }

    public List<PersonaBean> getListaPersonaFamiliarBean() {
        if (listaPersonaFamiliarBean == null) {
            listaPersonaFamiliarBean = new ArrayList<>();
        }
        return listaPersonaFamiliarBean;
    }

    public void setListaPersonaFamiliarBean(List<PersonaBean> listaPersonaFamiliarBean) {
        this.listaPersonaFamiliarBean = listaPersonaFamiliarBean;
    }

    public PersonaBean getPersonaBean() {
        if (personaBean == null) {
            personaBean = new PersonaBean();
        }
        return personaBean;
    }

    public void setPersonaBean(PersonaBean personaBean) {
        this.personaBean = personaBean;
    }

}
