/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.AmbienteBean;

/**
 *
 * @author MS002
 */
public interface AmbienteDAO {
    
    public List<AmbienteBean> obtenerAmbiente() throws Exception;
    public List<AmbienteBean> obtenerAmbientePorUnidadNeg(String uniNeg) throws Exception;
    public AmbienteBean obtenerAmbientePorId(Integer idAmbiente) throws Exception;
    
    public void insertarAmbiente(AmbienteBean ambienteBean) throws Exception;
    public void modificarAmbiente(AmbienteBean ambienteBean) throws Exception;
    public void eliminarAmbiente(Integer idAmbiente) throws Exception;
}
