/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.bean.ComprasBean;
import pe.marista.sigma.bean.ConceptoBean;
import pe.marista.sigma.bean.PersonaBean;
import pe.marista.sigma.bean.PostulanteBean;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author MS002
 */
public class PostulanteMB extends BaseMB implements Serializable{

    /**
     * Creates a new instance of PostulanteMB
     */
    public PostulanteMB() {
        PostulanteBean a = new PostulanteBean(1,"Lourdes Noemi" ,"Coronado","Pesantes", "71210097", "3ro","Primaria");
        PostulanteBean b = new PostulanteBean(2,"Gonzalo Hugo" ,"Lovon","Tacora", "45963211", "5to","Secundaria");
        PostulanteBean c = new PostulanteBean(3,"Silvia Elizabeth" ,"Silva","Cortijo", "15146932", "4to","Primaria");
        listPostulante = new ArrayList<>();
        listPostulante.add(a);
        listPostulante.add(b);
        listPostulante.add(c);
        PostulanteBean e = new PostulanteBean(1,"Carlos Noe" ,"Acosta","Pelaez", "12569874", "Padre","Primaria");
        PostulanteBean f = new PostulanteBean(2,"Carla Cecilia" ,"Maradiegue","Tacora", "36489257", "Madre","Secundaria");
        PostulanteBean g = new PostulanteBean(3,"Carlos Noe" ,"Rosales","Horna", "46520765", "Apoderado","Primaria");
        listPadres = new ArrayList<>();
        listPadres.add(e);
        listPadres.add(f);
        listPadres.add(g);
        
        PostulanteBean e1 = new PostulanteBean(10170301,"Carlos Noe" ,"Ibañez","Pelaez", "12569874", "Padre","Primaria");
        PostulanteBean f2 = new PostulanteBean(10170302,"Cecilia" ,"Sanchez","Tacora", "36489257", "Madre","Secundaria");
        PostulanteBean g3 = new PostulanteBean(10170303,"Carlos Javier" ,"Ibañez","Horna", "46520765", "Hijo(a)","Primaria");
         
        listPadres2 = new ArrayList<>();
        listPadres2.add(e1);
        listPadres2.add(f2);
        listPadres2.add(g3);
        
         
        
      
        
    }
    
    private PostulanteBean postulanteBean;
    private List<PostulanteBean> listPostulante;
    private List<PostulanteBean> listPadres;
    private List<PostulanteBean> listPadres2;
    private ComprasBean comprasBean;
    private PersonaBean personaBean;
    

    public PostulanteBean getPostulanteBean() {
        if(postulanteBean == null)
        {
            postulanteBean = new PostulanteBean();
        }
        return postulanteBean;
    }

    public void setPostulanteBean(PostulanteBean postulanteBean) {
        this.postulanteBean = postulanteBean;
    }

    public List<PostulanteBean> getListPostulante() {
        return listPostulante;
    }

    public void setListPostulante(List<PostulanteBean> listPostulante) {
        this.listPostulante = listPostulante;
    }

    public List<PostulanteBean> getListPadres() {
        return listPadres;
    }
    
    public void setListPadres(List<PostulanteBean> listPadres) {
        this.listPadres = listPadres;
    }
      public void rowSelect(SelectEvent event) {
        try {
            personaBean = (PersonaBean) event.getObject();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
      
    public void rowSelectP(SelectEvent event){
        try {
            comprasBean = (ComprasBean) event.getObject();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }  

    public List<PostulanteBean> getListPadres2() {
        return listPadres2;
    }

    public void setListPadres2(List<PostulanteBean> listPadres2) {
        this.listPadres2 = listPadres2;
    }

    public ComprasBean getComprasBean() {
        if(comprasBean == null){
            comprasBean = new ComprasBean();
        }
        return comprasBean;
    }

    public void setComprasBean(ComprasBean comprasBean) {
        this.comprasBean = comprasBean;
    }
    
}
