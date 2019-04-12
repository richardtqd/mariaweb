/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.ProcesoFileBean;

/**
 *
 * @author MS002
 */
public interface ProcesoFileDAO {

    public List<ProcesoFileBean> obtenerProcesoFile() throws Exception;

    public List<ProcesoFileBean> obtenerPorFiltro(ProcesoFileBean procesoFileBean) throws Exception;

    public List<ProcesoFileBean> obtenerPorBanco(String ruc) throws Exception;
    

    public void insertarProcesoFile(ProcesoFileBean procesoFileBean) throws Exception;

    public void modificarProcesoFile(ProcesoFileBean procesoFileBean) throws Exception;

}
