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
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import pe.marista.sigma.bean.FamiliaBean;
import pe.marista.sigma.bean.PersonaBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.FamiliaService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author Administrador
 */
public class FamiliaGrupoMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of FamiliaGrupoMB
     */
    @PostConstruct
    public void init() {
        try {
            getListaFamiliaOrigBean();
//            cargarLista();
            usuarioLogin = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            contTarget = 0;
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }
    private List<FamiliaBean> listaFamiliaOrigBean;
    private List<FamiliaBean> listaFamiliaDestBean;
    private DualListModel<FamiliaBean> dualFamiliaBean;
    private FamiliaBean familiaFiltroBean;
    private FamiliaBean familiaBean;
    private UsuarioBean usuarioLogin;
    private Integer contTarget;
    private List<FamiliaBean> grupoFamiliaBean;
    private FamiliaBean familiaGrupoBean;
    private List<FamiliaBean> listaFamiliaTempBean;
    private List<PersonaBean> listaPersonaBean;

    //Lógica de Negocio
    public void cargarLista() {
        try {
//            FamiliaService familiaService = BeanFactory.getFamiliaService();
//            listaFamiliaOrigBean = familiaService.obtenerFamilia();
            getListaFamiliaOrigBean();
//            if(dualFamiliaBean != null && dualFamiliaBean.getTarget()!=null){
//                setListaFamiliaDestBean(dualFamiliaBean.getTarget());
//            }
            getListaFamiliaDestBean();
            dualFamiliaBean = new DualListModel<>(listaFamiliaOrigBean, listaFamiliaDestBean);

        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerFiltroFamilia() {
        try {
            //Padre
            Boolean valor = Boolean.FALSE;
            if (familiaFiltroBean.getPadreBean().getPersonaBean().getApepat() != null && !familiaFiltroBean.getPadreBean().getPersonaBean().getApepat().equals("")) {
                familiaFiltroBean.getPadreBean().getPersonaBean().setApepat(familiaFiltroBean.getPadreBean().getPersonaBean().getApepat().trim().toUpperCase());
                valor = Boolean.TRUE;
            }
            if (familiaFiltroBean.getPadreBean().getPersonaBean().getApemat() != null && !familiaFiltroBean.getPadreBean().getPersonaBean().getApemat().equals("")) {
                familiaFiltroBean.getPadreBean().getPersonaBean().setApemat(familiaFiltroBean.getPadreBean().getPersonaBean().getApemat().trim().toUpperCase());
                valor = Boolean.TRUE;
            }
            if (familiaFiltroBean.getPadreBean().getPersonaBean().getNombre() != null & !familiaFiltroBean.getPadreBean().getPersonaBean().getNombre().equals("")) {
                familiaFiltroBean.getPadreBean().getPersonaBean().setNombre(familiaFiltroBean.getPadreBean().getPersonaBean().getNombre().trim().toUpperCase());
                valor = Boolean.TRUE;
            }
            //Madre
            if (familiaFiltroBean.getMadreBean().getPersonaBean().getApepat() != null && !familiaFiltroBean.getMadreBean().getPersonaBean().getApepat().equals("")) {
                familiaFiltroBean.getMadreBean().getPersonaBean().setApepat(familiaFiltroBean.getMadreBean().getPersonaBean().getApepat().trim().toUpperCase());
                valor = Boolean.TRUE;
            }
            if (familiaFiltroBean.getMadreBean().getPersonaBean().getApemat() != null && !familiaFiltroBean.getMadreBean().getPersonaBean().getApemat().equals("")) {
                familiaFiltroBean.getMadreBean().getPersonaBean().setApemat(familiaFiltroBean.getMadreBean().getPersonaBean().getApemat().trim().toUpperCase());
                valor = Boolean.TRUE;
            }
            if (familiaFiltroBean.getMadreBean().getPersonaBean().getNombre() != null && !familiaFiltroBean.getMadreBean().getPersonaBean().getNombre().equals("")) {
                familiaFiltroBean.getMadreBean().getPersonaBean().setNombre(familiaFiltroBean.getMadreBean().getPersonaBean().getNombre().trim().toUpperCase());
                valor = Boolean.TRUE;
            }

            if (valor) {
                FamiliaService familiaService = BeanFactory.getFamiliaService();
                familiaFiltroBean.setGpFlg(1);
                listaFamiliaOrigBean = familiaService.obtenerFiltroFamilia(familiaFiltroBean);
                contTarget = dualFamiliaBean.getTarget().size();
                cargarLista();
            } else {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
            }

        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerFiltroGrupoFamilia() {
        try {
            Boolean valor = Boolean.FALSE;
            //Padre
            if (familiaGrupoBean.getPadreBean().getPersonaBean().getApepat() != null && !familiaGrupoBean.getPadreBean().getPersonaBean().getApepat().equals("")) {
                familiaGrupoBean.getPadreBean().getPersonaBean().setApepat(familiaGrupoBean.getPadreBean().getPersonaBean().getApepat().trim().toUpperCase());
                valor = Boolean.TRUE;
            }
            if (familiaGrupoBean.getPadreBean().getPersonaBean().getApemat() != null && !familiaGrupoBean.getPadreBean().getPersonaBean().getApemat().equals("")) {
                familiaGrupoBean.getPadreBean().getPersonaBean().setApemat(familiaGrupoBean.getPadreBean().getPersonaBean().getApemat().trim().toUpperCase());
                valor = Boolean.TRUE;
            }
            if (familiaGrupoBean.getPadreBean().getPersonaBean().getNombre() != null & !familiaGrupoBean.getPadreBean().getPersonaBean().getNombre().equals("")) {
                familiaGrupoBean.getPadreBean().getPersonaBean().setNombre(familiaGrupoBean.getPadreBean().getPersonaBean().getNombre().trim().toUpperCase());
                valor = Boolean.TRUE;
            }
            //Madre
            if (familiaGrupoBean.getMadreBean().getPersonaBean().getApepat() != null && !familiaGrupoBean.getMadreBean().getPersonaBean().getApepat().equals("")) {
                familiaGrupoBean.getMadreBean().getPersonaBean().setApepat(familiaGrupoBean.getMadreBean().getPersonaBean().getApepat().trim().toUpperCase());
                valor = Boolean.TRUE;
            }
            if (familiaGrupoBean.getMadreBean().getPersonaBean().getApemat() != null && !familiaGrupoBean.getMadreBean().getPersonaBean().getApemat().equals("")) {
                familiaGrupoBean.getMadreBean().getPersonaBean().setApemat(familiaGrupoBean.getMadreBean().getPersonaBean().getApemat().trim().toUpperCase());
                valor = Boolean.TRUE;
            }
            if (familiaGrupoBean.getMadreBean().getPersonaBean().getNombre() != null && !familiaGrupoBean.getMadreBean().getPersonaBean().getNombre().equals("")) {
                familiaGrupoBean.getMadreBean().getPersonaBean().setNombre(familiaGrupoBean.getMadreBean().getPersonaBean().getNombre().trim().toUpperCase());
                valor = Boolean.TRUE;
            }
            if (familiaGrupoBean.getNombre() != null && !familiaGrupoBean.getNombre().equals("")) {
                familiaGrupoBean.setNombre(familiaGrupoBean.getNombre().trim().toUpperCase());
                valor = Boolean.TRUE;
            }
            if (familiaGrupoBean.getIdGrupoFamiliar() != null && !familiaFiltroBean.getIdGrupoFamiliar().equals("")) {
                valor = Boolean.TRUE;
            }
            if (valor) {
                FamiliaService familiaService = BeanFactory.getFamiliaService();
                familiaGrupoBean.setGpFlg(3);
                grupoFamiliaBean = familiaService.obtenerFiltroFamiliaPrep(familiaGrupoBean);
//                contTarget = dualFamiliaBean.getTarget().size();
            } else {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
            }

        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerGrupoFamilia() {
        try {
//            //Padre
//            if (familiaFiltroBean.getPadreBean().getPersonaBean().getApepat() != null && !familiaFiltroBean.getPadreBean().getPersonaBean().getApepat().equals("")) {
//                familiaFiltroBean.getPadreBean().getPersonaBean().setApepat(familiaFiltroBean.getPadreBean().getPersonaBean().getApepat().trim().toUpperCase());
//            }
//            if (familiaFiltroBean.getPadreBean().getPersonaBean().getApemat() != null && !familiaFiltroBean.getPadreBean().getPersonaBean().getApemat().equals("")) {
//                familiaFiltroBean.getPadreBean().getPersonaBean().setApemat(familiaFiltroBean.getPadreBean().getPersonaBean().getApemat().trim().toUpperCase());
//            }
//            if (familiaFiltroBean.getPadreBean().getPersonaBean().getNombre() != null & !familiaFiltroBean.getPadreBean().getPersonaBean().getNombre().equals("")) {
//                familiaFiltroBean.getPadreBean().getPersonaBean().setNombre(familiaFiltroBean.getPadreBean().getPersonaBean().getNombre().trim().toUpperCase());
//            }
//            //Madre
//            if (familiaFiltroBean.getMadreBean().getPersonaBean().getApepat() != null && !familiaFiltroBean.getMadreBean().getPersonaBean().getApepat().equals("")) {
//                familiaFiltroBean.getMadreBean().getPersonaBean().setApepat(familiaFiltroBean.getMadreBean().getPersonaBean().getApepat().trim().toUpperCase());
//            }
//            if (familiaFiltroBean.getMadreBean().getPersonaBean().getApemat() != null && !familiaFiltroBean.getMadreBean().getPersonaBean().getApemat().equals("")) {
//                familiaFiltroBean.getMadreBean().getPersonaBean().setApemat(familiaFiltroBean.getMadreBean().getPersonaBean().getApemat().trim().toUpperCase());
//            }
//            if (familiaFiltroBean.getMadreBean().getPersonaBean().getNombre() != null && !familiaFiltroBean.getMadreBean().getPersonaBean().getNombre().equals("")) {
//                familiaFiltroBean.getMadreBean().getPersonaBean().setNombre(familiaFiltroBean.getMadreBean().getPersonaBean().getNombre().trim().toUpperCase());
//            }
            FamiliaService familiaService = BeanFactory.getFamiliaService();
            getFamiliaFiltroBean().setGpFlg(2);
            grupoFamiliaBean = familiaService.obtenerFiltroFamiliaPrep(familiaFiltroBean);
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void prepararOrigen() throws Exception {
        for (int i = 0; i < listaFamiliaDestBean.size(); i++) {
            for (int j = 0; j < listaFamiliaOrigBean.size(); j++) {
                if (listaFamiliaDestBean.get(i).getIdFamilia() != listaFamiliaOrigBean.get(j).getIdFamilia()) {
                    listaFamiliaOrigBean.remove(j);
                    break;
                }
            }
        }
    }

    public void guardarGrupoFamilia() {
        try {
            FamiliaService familiaService = BeanFactory.getFamiliaService();
            familiaBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
            familiaService.guardarGrupoFamilia(familiaBean, dualFamiliaBean.getTarget(), getListaFamiliaTempBean());
            listaFamiliaDestBean = familiaService.obtenerGrupoFamiliirPorId(familiaBean);
            dualFamiliaBean = new DualListModel<>(new ArrayList<FamiliaBean>(), getListaFamiliaDestBean());
            contTarget = listaFamiliaDestBean.size();
            listaFamiliaTempBean = new ArrayList<>();
            listaPersonaBean = familiaService.obtenerEstudiantePorGrupoFam(familiaBean);
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void onTransfer(TransferEvent event) {
        List<FamiliaBean> lista = (List<FamiliaBean>) event.getItems();
        if (contTarget < dualFamiliaBean.getTarget().size()) {
            System.out.println("Ida: " + " Anterior" + contTarget + " Posterior " + dualFamiliaBean.getTarget().size());
        } else {
            for (FamiliaBean bean : lista) {
                if (bean.getIdGrupoFamiliar() != null) {
                    getListaFamiliaTempBean().add(bean);
                }
            }
            System.out.println("Vuelta: " + " Anterior" + contTarget + " Posterior " + dualFamiliaBean.getTarget().size());
        }
        contTarget = dualFamiliaBean.getTarget().size();
//        List<FamiliaBean> listaFamilia = (List<FamiliaBean>) event.getItems();
    }

    public void rowSelect(SelectEvent event) {
        try {
            familiaBean = (FamiliaBean) event.getObject();
            FamiliaService familiaService = BeanFactory.getFamiliaService();
            listaFamiliaDestBean = familiaService.obtenerGrupoFamiliirPorId(familiaBean);
//            listaFamiliaOrigBean = familiaService.obtenerGrupoFamiliirPorIdInverso(familiaBean);
            dualFamiliaBean = new DualListModel<>(new ArrayList<FamiliaBean>(), getListaFamiliaDestBean());
            contTarget = listaFamiliaDestBean.size();
            listaFamiliaTempBean = new ArrayList<>();
            listaPersonaBean = familiaService.obtenerEstudiantePorGrupoFam(familiaBean);
//            prepararOrigen();
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }

    }

    public void limpiarFamiliaBean() {
        try {
            familiaBean = new FamiliaBean();
            familiaFiltroBean = new FamiliaBean();
            listaFamiliaTempBean = new ArrayList<>();
            dualFamiliaBean = new DualListModel<>(new ArrayList<FamiliaBean>(), getListaFamiliaDestBean()); 
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    //Getter y Setter
    public List<FamiliaBean> getListaFamiliaOrigBean() {
        if (listaFamiliaOrigBean == null) {
            listaFamiliaOrigBean = new ArrayList<>();
        }
        return listaFamiliaOrigBean;
    }

    public void setListaFamiliaOrigBean(List<FamiliaBean> listaFamiliaOrigBean) {
        this.listaFamiliaOrigBean = listaFamiliaOrigBean;
    }

    public List<FamiliaBean> getListaFamiliaDestBean() {
        if (listaFamiliaDestBean == null) {
            listaFamiliaDestBean = new ArrayList<>();
        }
        return listaFamiliaDestBean;
    }

    public void setListaFamiliaDestBean(List<FamiliaBean> listaFamiliaDestBean) {
        this.listaFamiliaDestBean = listaFamiliaDestBean;
    }

    public DualListModel<FamiliaBean> getDualFamiliaBean() {
        if (dualFamiliaBean == null) {
            dualFamiliaBean = new DualListModel<>();
        }
        return dualFamiliaBean;
    }

    public void setDualFamiliaBean(DualListModel<FamiliaBean> dualFamiliaBean) {
        this.dualFamiliaBean = dualFamiliaBean;
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

    public FamiliaBean getFamiliaBean() {
        if (familiaBean == null) {
            familiaBean = new FamiliaBean();
        }
        return familiaBean;
    }

    public void setFamiliaBean(FamiliaBean familiaBean) {
        this.familiaBean = familiaBean;
    }

    public List<FamiliaBean> getGrupoFamiliaBean() {
        if (grupoFamiliaBean == null) {
            grupoFamiliaBean = new ArrayList<>();
        }
        return grupoFamiliaBean;
    }

    public void setGrupoFamiliaBean(List<FamiliaBean> grupoFamiliaBean) {
        this.grupoFamiliaBean = grupoFamiliaBean;
    }

    public FamiliaBean getFamiliaGrupoBean() {
        if (familiaGrupoBean == null) {
            familiaGrupoBean = new FamiliaBean();
        }
        return familiaGrupoBean;
    }

    public void setFamiliaGrupoBean(FamiliaBean familiaGrupoBean) {
        this.familiaGrupoBean = familiaGrupoBean;
    }

    public List<FamiliaBean> getListaFamiliaTempBean() {
        if (listaFamiliaTempBean == null) {
            listaFamiliaTempBean = new ArrayList<>();
        }
        return listaFamiliaTempBean;
    }

    public void setListaFamiliaTempBean(List<FamiliaBean> listaFamiliaTempBean) {
        this.listaFamiliaTempBean = listaFamiliaTempBean;
    }

    public List<PersonaBean> getListaPersonaBean() {
        if(listaPersonaBean==null){
            listaPersonaBean = new ArrayList<>();
        }
        return listaPersonaBean;
    }

    public void setListaPersonaBean(List<PersonaBean> listaPersonaBean) {
        this.listaPersonaBean = listaPersonaBean;
    }

}
