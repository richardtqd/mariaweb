/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.ProcesoFileBean;
import pe.marista.sigma.dao.ProcesoFileDAO;

/**
 *
 * @author MS002
 */
public class ProcesoFileService {
    
    private ProcesoFileDAO procesoFileDAO;

    public List<ProcesoFileBean> obtenerProcesoFile() throws Exception {
        return procesoFileDAO.obtenerProcesoFile();
    }

    public List<ProcesoFileBean> obtenerPorFiltro(ProcesoFileBean procesoFileBean) throws Exception {
        return procesoFileDAO.obtenerPorFiltro(procesoFileBean);
    }

    public List<ProcesoFileBean> obtenerPorBanco(String ruc) throws Exception {
        return procesoFileDAO.obtenerPorBanco(ruc);
    }

    @Transactional
    public void insertarProcesoFile(ProcesoFileBean procesoFileBean) throws Exception {
        procesoFileDAO.insertarProcesoFile(procesoFileBean);
    }

    @Transactional
    public void modificarProcesoFile(ProcesoFileBean procesoFileBean) throws Exception {
        procesoFileDAO.modificarProcesoFile(procesoFileBean);
    }    

    public ProcesoFileDAO getProcesoFileDAO() {
        return procesoFileDAO;
    }

    public void setProcesoFileDAO(ProcesoFileDAO procesoFileDAO) {
        this.procesoFileDAO = procesoFileDAO;
    }
        
}
