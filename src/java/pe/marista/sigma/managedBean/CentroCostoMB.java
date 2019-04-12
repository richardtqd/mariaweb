/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pe.marista.sigma.bean.CentroCostoBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CentroCostoService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author MS002
 */
public class CentroCostoMB extends BaseMB implements Serializable{

 
    public CentroCostoMB() {
         
          
    }
     
    private CentroCostoBean centroCostoBean;
    private List<CentroCostoBean> listaCentroCosto;
    private List<CentroCostoBean> listaCentroCostoPadre;
    private Map<String , String> listaCentroCostoPadreMap;
    
    public CentroCostoBean getCentroCostoBean() {
        return centroCostoBean;
    }

    public void setCentroCostoBean(CentroCostoBean centroCostoBean) {
        this.centroCostoBean = centroCostoBean;
    }

    public List<CentroCostoBean> getListaCentroCosto() {
         if (listaCentroCosto == null) {
            listaCentroCosto = new ArrayList<>();
        }
         return listaCentroCosto;
    }

    public void setListaCentroCosto(List<CentroCostoBean> listaCentroCosto) {
        this.listaCentroCosto = listaCentroCosto;
    }

    public List<CentroCostoBean> getListaCentroCostoPadre() {
        return listaCentroCostoPadre;
    }

    public void setListaCentroCostoPadre(List<CentroCostoBean> listaCentroCostoPadre) {
        this.listaCentroCostoPadre = listaCentroCostoPadre;
    }

    public Map<String, String> getListaCentroCostoPadreMap() {
        return listaCentroCostoPadreMap;
    }

    public void setListaCentroCostoPadreMap(Map<String, String> listaCentroCostoPadreMap) {
        this.listaCentroCostoPadreMap = listaCentroCostoPadreMap;
    }
    
//    
//      public final void cargarLista() 
//      {
//      
//      CentroCostoBean a = new CentroCostoBean();
//      a.setIdCentroCosto(1);
//      a.setCodigo("1");
//      a.setNombre("Administración");
//      a.setNivel(1);
//      
//      CentroCostoBean b = new CentroCostoBean();
//      b.setIdCentroCosto(2);
//      b.setCodigo("C001-A");
//      b.setNombre("Inicial");
//      b.setNivel(2);
//      b.setCentroCostoBean(a);
//      
//      CentroCostoBean c = new CentroCostoBean();
//      c.setIdCentroCosto(3);
//      c.setCodigo("C002-A");
//      c.setNombre("Primaria");
//      c.setNivel(2);
//      c.setCentroCostoBean(a);
//      
//      CentroCostoBean d = new CentroCostoBean();
//      d.setIdCentroCosto(4);
//      d.setCodigo("C003-A");
//      d.setNombre("Secundaria");
//      d.setNivel(2);
//      d.setCentroCostoBean(a);
//      
//      CentroCostoBean e = new CentroCostoBean();
//      e.setIdCentroCosto(5);
//      e.setCodigo("004");
//      e.setNombre("Inicial");
//      e.setNivel(2);
//      e.setCentroCostoBean(a);
//      
//      CentroCostoBean f = new CentroCostoBean();
//      f.setIdCentroCosto(6);
//      f.setCodigo("C001-I");
//      f.setNombre("Laboratorio Cómputo");
//      f.setNivel(2);
//      f.setCentroCostoBean(e);
//      
//      CentroCostoBean g = new CentroCostoBean();
//      g.setIdCentroCosto(7);
//      g.setCodigo("C002-I");
//      g.setNombre("Mantenimiento");
//      g.setNivel(2);
//      g.setCentroCostoBean(e);
//      
//      CentroCostoBean ff = new CentroCostoBean();
//      ff.setIdCentroCosto(6);
//      ff.setCodigo("C001-S");
//      ff.setNombre("Laboratorio Cómputo");
//      ff.setNivel(2);
//      ff.setCentroCostoBean(d);
//      
//      CentroCostoBean gg = new CentroCostoBean();
//      gg.setIdCentroCosto(7);
//      gg.setCodigo("C002-S");
//      gg.setNombre("Mantenimiento");
//      gg.setNivel(2);
//      gg.setCentroCostoBean(d);
//      
////      CentroCostoBean H = new CentroCostoBean();
////      H.setIdCentroCosto(5);
////      H.setCodigo("004");
////      H.setNombre("Primaria");
////      H.setNivel(1);
//      
//      CentroCostoBean I = new CentroCostoBean();
//      I.setIdCentroCosto(6);
//      I.setCodigo("C001-P");
//      I.setNombre("Laboratorio Cómputo");
//      I.setNivel(2);
//      I.setCentroCostoBean(c);
//      
//      CentroCostoBean J = new CentroCostoBean();
//      J.setIdCentroCosto(7);
//      J.setCodigo("C002-P");
//      J.setNombre("Mantenimiento");
//      J.setNivel(2);
//      J.setCentroCostoBean(c);
//      
//      listaCentroCosto = new ArrayList<>();
//      listaCentroCosto.add(a);
//      listaCentroCosto.add(b);
//      listaCentroCosto.add(c);
//      listaCentroCosto.add(d);
//     // listaCentroCosto.add(e);
//      listaCentroCosto.add(f);
//      listaCentroCosto.add(g);
//      listaCentroCosto.add(I);
//      listaCentroCosto.add(J);
//      listaCentroCosto.add(ff);
//      listaCentroCosto.add(gg);
//      //listaCentroCosto.add(H);
//      
//      
//      listaCentroCostoPadreMap = new HashMap<>();
//      listaCentroCostoPadreMap.put(a.getNombre(),a.getCodigo());
//    //  listaCentroCostoPadreMap.put(e.getNombre(),e.getCodigo());
//      //listaCentroCostoPadreMap.put(H.getNombre(),H.getCodigo());
//      
//      
//      }
      
      
      public void obtenerTodos() {
        try {
            CentroCostoService centroCostoService = BeanFactory.getCentroCostoService();
            listaCentroCosto = centroCostoService.obtenerCentroCosto();

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

}
