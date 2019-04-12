/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.CheckListBean;
import pe.marista.sigma.bean.ProcesoBean;

/**
 *
 * @author MS001
 */
public interface CheckListDAO {
    
    public void insertarCheckList(CheckListBean checkListBean) throws Exception;
    public void modificarCheckList(CheckListBean checkListBean) throws Exception;
    public void eliminarCheckList(CheckListBean checkListBean) throws Exception; 
    public void cambiarFlags(CheckListBean checkListBean) throws Exception;
    public List<CheckListBean> obtenerPorUniNeg(String uniNeg) throws Exception; 
    public List<CheckListBean> obtenerPorProceso(ProcesoBean procesoBean) throws Exception;
    public CheckListBean obtenerCheckListPorId(CheckListBean checkListBean) throws Exception;
   
}
