/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.AmbienteBean;
import pe.marista.sigma.dao.AmbienteDAO;

/**
 *
 * @author MS002
 */
public class AmbienteService {
    
    private AmbienteDAO ambienteDAO;
    
    public List<AmbienteBean> obtenerAmbiente() throws Exception{
        return ambienteDAO.obtenerAmbiente();
    }
    public List<AmbienteBean> obtenerAmbientePorUnidadNeg(String uniNeg) throws Exception{
        return ambienteDAO.obtenerAmbientePorUnidadNeg(uniNeg);
    }
    public AmbienteBean obtenerAmbientePorId(Integer idAmbiente) throws Exception{
        return ambienteDAO.obtenerAmbientePorId(idAmbiente);
    }
  
    @Transactional
    public void insertarAmbiente(AmbienteBean ambienteBean) throws Exception{
        ambienteDAO.insertarAmbiente(ambienteBean);
    }
    @Transactional
    public void modificarAmbiente(AmbienteBean ambienteBean) throws Exception{
        ambienteDAO.modificarAmbiente(ambienteBean);
    }
    @Transactional
    public void eliminarAmbiente(Integer idAmbiente) throws Exception{
        ambienteDAO.eliminarAmbiente(idAmbiente);
    }
    public AmbienteDAO getAmbienteDAO() {
        return ambienteDAO;
    }
    public void setAmbienteDAO(AmbienteDAO ambienteDAO) {
        this.ambienteDAO = ambienteDAO;
    }
    
}
