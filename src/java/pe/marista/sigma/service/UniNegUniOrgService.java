/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.marista.sigma.service;

import java.util.List;
import pe.marista.sigma.bean.UniNegUniOrgBean;
import pe.marista.sigma.bean.UnidadOrganicaBean;
import pe.marista.sigma.dao.UniNegUniOrgDAO;


/**
 *
 * @author Administrador
 */
public class UniNegUniOrgService {
    //Variable
    private UniNegUniOrgDAO UniNegUniOrgDAO;
    
   //Metodos Logica dew Negocio
    public List<UniNegUniOrgBean> obtenerTodos() throws Exception{
        return UniNegUniOrgDAO.obtenerTodos();
    }
    public List<UniNegUniOrgBean> obtenerUniOrgPorUniNeg(String uniNeg) throws Exception{
        return UniNegUniOrgDAO.obtenerUniOrgPorUniNeg(uniNeg);
    }
    //Getter and Setter

    public UniNegUniOrgDAO getUniNegUniOrgDAO() {
        return UniNegUniOrgDAO;
    }

    public void setUniNegUniOrgDAO(UniNegUniOrgDAO UniNegUniOrgDAO) {
        this.UniNegUniOrgDAO = UniNegUniOrgDAO;
    }
    
    
    
}
