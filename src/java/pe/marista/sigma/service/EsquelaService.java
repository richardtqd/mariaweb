/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.EsquelaBean;
import pe.marista.sigma.dao.EsquelaDAO;

/**
 *
 * @author MS002
 */
public class EsquelaService {

    private EsquelaDAO esquelaDAO;

    public List<EsquelaBean> obtenerEsquela(String uniNeg) throws Exception {
        return esquelaDAO.obtenerEsquela(uniNeg);
    }

    public List<EsquelaBean> obtenerEsquelaFiltro(EsquelaBean esquelaBean) throws Exception {
        return esquelaDAO.obtenerEsquelaFiltro(esquelaBean);
    }

    public EsquelaBean obtenerPorId(Integer idEsquela) throws Exception {
        return esquelaDAO.obtenerPorId(idEsquela);
    }

    public EsquelaBean obtenerPorIdEsq(EsquelaBean esquelaBean) throws Exception {
        return esquelaDAO.obtenerPorIdEsq(esquelaBean);
    }

    public void insertarEsquela(EsquelaBean esquelaBean) throws Exception {
        esquelaDAO.insertarEsquela(esquelaBean);
    }

    public void modificarEsquela(EsquelaBean esquelaBean) throws Exception {
        esquelaDAO.modificarEsquela(esquelaBean);
    }

    public void eliminarEsquela(Integer idEsquela) throws Exception {
        esquelaDAO.eliminarEsquela(idEsquela);
    }

    public Integer obtenerMaxEsquela(String uniNeg) throws Exception {
        return esquelaDAO.obtenerMaxEsquela(uniNeg);
    }

    public Integer obtenerMaxAccion(String uniNeg) throws Exception {
        return esquelaDAO.obtenerMaxAccion(uniNeg);
    }

    public List<EsquelaBean> obtenerEsquelaAccion(EsquelaBean esquelaBean) throws Exception {
        return esquelaDAO.obtenerEsquelaAccion(esquelaBean);
    }

    @Transactional
    public void actualizarEsquela(EsquelaBean esquelaBean) throws Exception {
        esquelaDAO.actualizarEsquela(esquelaBean);
    }  

    @Transactional
    public void eliminarEsquelaMasivo(EsquelaBean esquelaBean) throws Exception {
        esquelaDAO.eliminarEsquelaMasivo(esquelaBean);
    }

    public EsquelaDAO getEsquelaDAO() {
        return esquelaDAO;
    }

    public void setEsquelaDAO(EsquelaDAO esquelaDAO) {
        this.esquelaDAO = esquelaDAO;
    }

}
