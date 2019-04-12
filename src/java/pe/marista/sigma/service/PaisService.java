/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.PaisBean;
import pe.marista.sigma.dao.PaisDAO;

/**
 *
 * @author Administrador
 */
public class PaisService {

    private PaisDAO paisDAO;

    public List<PaisBean> obtenerPais() throws Exception {
        return paisDAO.obtenerPais();
    }

    @Transactional
    public void insertarPais(PaisBean paisBean) throws Exception {
        paisDAO.insertarPais(paisBean);
    }

    @Transactional
    public void modificarPais(PaisBean paisBean) throws Exception {
        paisDAO.modificarPais(paisBean);
    }

    @Transactional
    public void eliminarPais(Integer idPais) throws Exception {
        paisDAO.eliminarPais(idPais);
    }

    public PaisBean obtenerPaisPorId(Integer idPais) throws Exception {
        return paisDAO.obtenerPaisPorId(idPais);
    }
    public PaisBean obtenerPaisPorDefectoPeru(String nombre) throws Exception { 
        return paisDAO.obtenerPaisPorDefectoPeru(nombre);
    }

    public PaisDAO getPaisDAO() {
        return paisDAO;
    }

    public void setPaisDAO(PaisDAO paisDAO) {
        this.paisDAO = paisDAO;
    }

    

}
