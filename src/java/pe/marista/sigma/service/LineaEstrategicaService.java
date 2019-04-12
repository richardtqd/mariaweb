/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.util.List;
import pe.marista.sigma.bean.LineaEstrategicaBean;
import pe.marista.sigma.dao.LineaEstrategicaDAO;

/**
 *
 * @author MS002
 */
public class LineaEstrategicaService {
    
    private LineaEstrategicaDAO lineaEstrategicaDAO;

    public List<LineaEstrategicaBean> obtenerLineaEstrategica() throws Exception {
        return lineaEstrategicaDAO.obtenerLineaEstrategica();
    }

    public LineaEstrategicaBean obtenerPorId(Integer idLinea) throws Exception {
        return lineaEstrategicaDAO.obtenerPorId(idLinea);
    }

    public List<LineaEstrategicaBean> obtenerListaPorId(Integer idLinea, String uniNeg) throws Exception {
        return lineaEstrategicaDAO.obtenerListaPorId(idLinea, uniNeg);
    }

    public void insertarLineaEstrategica(LineaEstrategicaBean lineaEstrategicaBean) throws Exception {
        lineaEstrategicaDAO.insertarLineaEstrategica(lineaEstrategicaBean);
    }

    public void actualizarLineaEstrategica(LineaEstrategicaBean lineaEstrategicaBean) throws Exception {
        lineaEstrategicaDAO.actualizarLineaEstrategica(lineaEstrategicaBean);
    }

    public void eliminarLineaEstrategica(Integer idLinea) throws Exception {
        lineaEstrategicaDAO.eliminarLineaEstrategica(idLinea);
    }

    public String obtenerUltimoCodigo(String uniNeg) throws Exception {
        return lineaEstrategicaDAO.obtenerUltimoCodigo(uniNeg);
    }      

    public void cambiarEstado(LineaEstrategicaBean lineaEstrategicaBean) throws Exception {
        lineaEstrategicaDAO.cambiarEstado(lineaEstrategicaBean);
    }

    public List<LineaEstrategicaBean> obtenerLineaPorPlanEstrategico(Integer idPlanEstrategico, String uniNeg) throws Exception {
        return lineaEstrategicaDAO.obtenerLineaPorPlanEstrategico(idPlanEstrategico, uniNeg);
    }
 
    public LineaEstrategicaDAO getLineaEstrategicaDAO() {
        return lineaEstrategicaDAO;
    }

    public void setLineaEstrategicaDAO(LineaEstrategicaDAO lineaEstrategicaDAO) {
        this.lineaEstrategicaDAO = lineaEstrategicaDAO;
    }

}
