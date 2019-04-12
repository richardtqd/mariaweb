/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.bean.CtaCteBean;
import pe.marista.sigma.bean.EstudianteBean;

/**
 *
 * @author GLOVON
 */
public class CobranzaMB implements Serializable{

    /**
     * Creates a new instance of CobranzaMB
     */
    public CobranzaMB() {
        listaEstudiantesBean = new ArrayList<>();
//        listaEstudiantesBean.add(new EstudianteBean("001", 1, "Juan Carlos Muñoz Ricce"));
//        listaEstudiantesBean.add(new EstudianteBean("002", 2, "Luis Piere Perez Muñante"));
//        listaEstudiantesBean.add(new EstudianteBean("003", 3, "Adolfo Arpasi Huanacun"));
//        listaEstudiantesBean.add(new EstudianteBean("004", 4, "Esteban Ibarcena >apata"));
//        listaEstudiantesBean.add(new EstudianteBean("006", 5, "Juan Carlos Muñoz Ricce"));
//        listaEstudiantesBean.add(new EstudianteBean("007", 1, "Andres Mauricio Hidalgo"));
//        listaEstudiantesBean.add(new EstudianteBean("008", 2, "Anibal John Torres Carpio"));
//        listaEstudiantesBean.add(new EstudianteBean("009", 3, "Anfel Durand Mendoza"));
        listaCtaCteBean = new ArrayList<>();
        listaCtaCteBean.add(new CtaCteBean(100, new BigDecimal("300.00"), "Pago Derecho Estudio"));
        listaCtaCteBean.add(new CtaCteBean(101, new BigDecimal("150.00"), "APAFA"));
        listaCtaCteBean.add(new CtaCteBean(102, new BigDecimal("40.00"), "Rifa "));
         
         
    }
    public void rowSelect(SelectEvent event){
        estudianteBean = new EstudianteBean();
        estudianteBean = (EstudianteBean) event.getObject();
  
    }
    private List<EstudianteBean> listaEstudiantesBean;
    private List<CtaCteBean> listaCtaCteBean;
    private CtaCteBean ctaCteBean;
    private CtaCteBean ctaCteBean2;
    private EstudianteBean estudianteBean;

    public List<EstudianteBean> getListaEstudiantesBean() {
        return listaEstudiantesBean;
    }

    public void setListaEstudiantesBean(List<EstudianteBean> listaEstudiantesBean) {
        this.listaEstudiantesBean = listaEstudiantesBean;
    }

    public List<CtaCteBean> getListaCtaCteBean() {
        return listaCtaCteBean;
    }

    public void setListaCtaCteBean(List<CtaCteBean> listaCtaCteBean) {
        this.listaCtaCteBean = listaCtaCteBean;
    }

    public CtaCteBean getCtaCteBean() {
        if(ctaCteBean==null){
            ctaCteBean = new CtaCteBean();
        }
        return ctaCteBean;
    }

    public void setCtaCteBean(CtaCteBean ctaCteBean) {
        this.ctaCteBean = ctaCteBean;
    }

    public EstudianteBean getEstudianteBean() {
        if(estudianteBean==null){
            estudianteBean = new EstudianteBean();
        }
        return estudianteBean;
    }

    public void setEstudianteBean(EstudianteBean estudianteBean) {
        this.estudianteBean = estudianteBean;
    }

    public CtaCteBean getCtaCteBean2() {
        if(ctaCteBean2==null){
            ctaCteBean2 = new CtaCteBean();
        }
        return ctaCteBean2;
    }

    public void setCtaCteBean2(CtaCteBean ctaCteBean2) {
        this.ctaCteBean2 = ctaCteBean2;
    }
    
}
