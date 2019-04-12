package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.bean.ConceptoBean;
import pe.marista.sigma.bean.CtaCteBean;
import pe.marista.sigma.bean.DocEgresoBean2;
import pe.marista.sigma.bean.EstudianteBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.ConceptoService;
import pe.marista.sigma.util.GLTLog;
//import pe.marista.sigma.util.Lorem;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author Administrador
 */
public class CtaCteMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of CtaCteMB
     */
    public CtaCteMB() {
//        listaCtaCteBean = new ArrayList<>();
//        listaCtaCteBean.add(new CtaCteBean(Lorem.getFirstName() + " " + Lorem.getLastName(),
//                "Pension 2014", new BigDecimal(200.50), new BigDecimal(20), new BigDecimal(2), new BigDecimal(2.5), 0, 1, 10001, new Date()));
//        listaCtaCteBean.add(new CtaCteBean(Lorem.getFirstName() + " " + Lorem.getLastName(),
//                "Pension 2014", new BigDecimal(200.50), new BigDecimal(20), new BigDecimal(2), new BigDecimal(2.5), 1, 1, 10002, new Date()));
//        listaCtaCteBean.add(new CtaCteBean(Lorem.getFirstName() + " " + Lorem.getLastName(),
//                "Pension 2014", new BigDecimal(200), new BigDecimal(20), new BigDecimal(2), new BigDecimal(2.5), 0, 1, 10003, new Date()));
//        listaCtaCteBean.add(new CtaCteBean(Lorem.getFirstName() + " " + Lorem.getLastName(),
//                "Pension 2014", new BigDecimal(200), new BigDecimal(20), new BigDecimal(2), new BigDecimal(2.5), 1, 1, 10004, new Date()));
//        listaCtaCteBean.add(new CtaCteBean(Lorem.getFirstName() + " " + Lorem.getLastName(),
//                "Pension 2014", new BigDecimal(200), new BigDecimal(20), new BigDecimal(2), new BigDecimal(2.5), 0, 1, 10005, new Date()));
//        listaCtaCteBean.add(new CtaCteBean(Lorem.getFirstName() + " " + Lorem.getLastName(),
//                "Pension 2014", new BigDecimal(200), new BigDecimal(20), new BigDecimal(2), new BigDecimal(2.5), 1, 1, 10006, new Date()));
//        listaCtaCteBean.add(new CtaCteBean(Lorem.getFirstName() + " " + Lorem.getLastName(),
//                "Pension 2014", new BigDecimal(200), new BigDecimal(20), new BigDecimal(2.1), new BigDecimal(2.5), 0, 1, 10007, new Date()));
//        listaCtaCteBean.add(new CtaCteBean(Lorem.getFirstName() + " " + Lorem.getLastName(),
//                "Pension 2014", new BigDecimal(200), new BigDecimal(20), new BigDecimal(2.1), new BigDecimal(2.5), 1, 1, 10008, new Date()));
//        listaCtaCteBean.add(new CtaCteBean(Lorem.getFirstName() + " " + Lorem.getLastName(),
//                "Pension 2014", new BigDecimal(200), new BigDecimal(20), new BigDecimal(2.1), new BigDecimal(2.5), 0, 1, 10009, new Date()));
//        listaCtaCteBean.add(new CtaCteBean(Lorem.getFirstName() + " " + Lorem.getLastName(),
//                "Pension 2014", new BigDecimal(200), new BigDecimal(20), new BigDecimal(2.1), new BigDecimal(2.5), 1, 1, 10010, new Date()));
    }
    private List<CtaCteBean> listaCtaCteBean;
    private CtaCteBean ctaCteBean;
    private CtaCteBean ctaCteFiltroBean;
    private EstudianteBean estudianteBean;
    private ConceptoBean conceptoBean;
    
    //Metodos de aplicacion
    public List<EstudianteBean> completeTheme(String query) {
        List<EstudianteBean> listaAllEstudianteBean = new ArrayList<>();
        cargarEstudiantes(listaAllEstudianteBean);
        List<EstudianteBean> listaFilterEstudianteBean = new ArrayList<>();
        listaFilterEstudianteBean = new ArrayList<>();

        for (int i = 0; i < listaAllEstudianteBean.size(); i++) {
            EstudianteBean skin = listaAllEstudianteBean.get(i);
//            if (skin.getNomEstudiante().toLowerCase().startsWith(query)) {
//                listaFilterEstudianteBean.add(skin);
//            }
        }
        return listaFilterEstudianteBean;
    }
//    public List<ConceptoBean> completeConcepto(String query) {
//        try {
//            ConceptoService conceptoService = BeanFactory.getConceptoService();
//            List<ConceptoBean> listaAllConceptoBean = conceptoService.obtenerTodos();
//            List<ConceptoBean> listaFilterConceptoBean = new ArrayList<>();
//               
//            for (int i = 0; i < listaAllConceptoBean.size(); i++) {
//                ConceptoBean skin = listaAllConceptoBean.get(i);
//                if (skin.getNombre().toLowerCase().startsWith(query)) {
//                    listaFilterConceptoBean.add(skin);
//                }
//            }
//            return listaFilterConceptoBean;
//        } catch (Exception ex) {
//           new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//        return null;
//    }

    public void cargarEstudiantes(List<EstudianteBean> listaAllEstudianteBean) {

//        listaAllEstudianteBean.add(new EstudianteBean("001", 1, "Juan Carlos Muñoz Ricce"));
//        listaAllEstudianteBean.add(new EstudianteBean("002", 2, "Luis Piere Perez Muñante"));
//        listaAllEstudianteBean.add(new EstudianteBean("003", 3, "Adolfo Arpasi Huanacun"));
//        listaAllEstudianteBean.add(new EstudianteBean("004", 4, "Esteban Ibarcena >apata"));
//        listaAllEstudianteBean.add(new EstudianteBean("006", 5, "Juan Carlos Muñoz Ricce"));
//        listaAllEstudianteBean.add(new EstudianteBean("007", 1, "Andres Mauricio Hidalgo"));
//        listaAllEstudianteBean.add(new EstudianteBean("008", 2, "Anibal John Torres Carpio"));
//        listaAllEstudianteBean.add(new EstudianteBean("009", 3, "Anfel Durand Mendoza"));
    }

    public void obtenerTodos() {
        try {
//            CtaCteService ctaCteService = BeanFactory.getCtaCteService();
//            listaCtaCteBean = ctaCteService.obtenerTodos();
        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarCtaCtebean() {
        ctaCteBean = new CtaCteBean();
    }

    public void obtenerPorFiltro() {
        try {
//            CtaCteService ctaCteService = BeanFactory.getCtaCteService();
//            if(ctaCteFiltroBean.getNombre() != null){
//                ctaCteFiltroBean.setNombre(ctaCteFiltroBean.getNombre().toUpperCase().trim());
//            }
//            
//            listaCtaCteBean = ctaCteService.obtenerFiltro(ctaCteFiltroBean);
        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorId(Integer idCtaCte) {
        try {
//            CtaCteService ctaCteService = BeanFactory.getCtaCteService();
//            getCtaCteBean().setIdCtaCte(idCtaCte);
//            ctaCteBean = ctaCteService.obtenerPorId(ctaCteBean);
//            List<CtaCteModuloBean> listaCtaCteModulo = ctaCteService.obtenerPorCtaCte(ctaCteBean);
//            cargarLista();
//            for (int i = 0; i < listaCtaCteModulo.size(); i++) {
//                ModuloBean moduloBean = new ModuloBean();
//                moduloBean.setIdModulo(listaCtaCteModulo.get(i).getIdModulo());
//                moduloBean.setNodo(listaCtaCteModulo.get(i).getNodo());
//                moduloBean.setUrl(listaCtaCteModulo.get(i).getUrl());
//                moduloBean.setIdTipoNodo(listaCtaCteModulo.get(i).getIdTipoNodo());
//                moduloBean.setIdModuloPadre(listaCtaCteModulo.get(i).getIdModuloPadre());
//                listaModuloDest.add(moduloBean);
//                for (int j = 0; j < listaModuloBean.size(); j++) {          
//                    if (listaModuloBean.get(j).getIdModulo().toString().equals(moduloBean.getIdModulo().toString())) {
//                        System.out.println("idModulo 1:"+listaModuloBean.get(j).getIdModulo()+"idModulo 2:"+moduloBean.getIdModulo());
//                        listaModuloBean.remove(j);
//                        break;
//                    }
//                }
//            }
//            dualListaModuloBean = new DualListModel<>(listaModuloBean, listaModuloDest);

        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarCtaCte() {
        String pagina = null;
        try {
//            pagina = new MaristaUtils().validaUsuarioSesion();
//            if (pagina == null) {
//                CtaCteService ctaCteService = BeanFactory.getCtaCteService();
//                ctaCteService.insertarCtaCte(ctaCteBean, dualListaModuloBean.getTarget());
//                listaCtaCteBean = ctaCteService.obtenerTodos();
//                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
//            }
        } catch (Exception err) {
//            pagina = null;
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarCtaCte() {
        String pagina = null;
        try {
//            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
//                CtaCteService ctaCteService = BeanFactory.getCtaCteService();
//                 ctaCteService.modificarCtaCte(ctaCteBean, dualListaModuloBean.getTarget());
//                listaCtaCteBean = ctaCteService.obtenerTodos();
//                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
//            pagina = null;
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void guardarCtaCte() {
//        if (ctaCteBean.getIdCtaCte() == null) {
//            insertarCtaCte();
//        } else {
//            modificarCtaCte();
//        }
    }

    public String eliminarCtaCte() {
        String pagina = null;
        try {
//            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
//                CtaCteService ctaCteService = BeanFactory.getCtaCteService();
//                ctaCteService.eliminarCtaCte(ctaCteBean);
//                listaCtaCteBean = ctaCteService.obtenerTodos();
//                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
//            pagina = null;
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }
 public void rowSelect(SelectEvent event) {
        try {
            ctaCteBean = (CtaCteBean) event.getObject();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
    //GEtter y Setter
    public List<CtaCteBean> getListaCtaCteBean() {
        if (listaCtaCteBean == null) {
            listaCtaCteBean = new ArrayList<>();
        }
        return listaCtaCteBean;
    }

    public void setListaCtaCteBean(List<CtaCteBean> listaCtaCteBean) {
        this.listaCtaCteBean = listaCtaCteBean;
    }

    public CtaCteBean getCtaCteBean() {
        if (ctaCteBean == null) {
            ctaCteBean = new CtaCteBean();
        }
        return ctaCteBean;
    }

    public void setCtaCteBean(CtaCteBean ctaCteBean) {
        this.ctaCteBean = ctaCteBean;
    }

    public CtaCteBean getCtaCteFiltroBean() {
        return ctaCteFiltroBean;
    }

    public void setCtaCteFiltroBean(CtaCteBean ctaCteFiltroBean) {
        this.ctaCteFiltroBean = ctaCteFiltroBean;
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

    public ConceptoBean getConceptoBean() {
        if(conceptoBean==null){
            conceptoBean = new ConceptoBean();
        }
        return conceptoBean;
    }

    public void setConceptoBean(ConceptoBean conceptoBean) {
        this.conceptoBean = conceptoBean;
    }
    
}
