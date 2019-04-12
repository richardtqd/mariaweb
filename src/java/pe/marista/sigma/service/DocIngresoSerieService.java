/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.DocIngresoSerieBean;
import pe.marista.sigma.dao.DocIngresoSerieDAO;

/**
 *
 * @author MS002
 */
public class DocIngresoSerieService {
    
    private DocIngresoSerieDAO docIngresoSerieDAO;

    public DocIngresoSerieDAO getDocIngresoSerieDAO() {
        return docIngresoSerieDAO;
    }

    public void setDocIngresoSerieDAO(DocIngresoSerieDAO docIngresoSerieDAO) {
        this.docIngresoSerieDAO = docIngresoSerieDAO;
    }
    
    public List<DocIngresoSerieBean> obtenerTodos(String uniNeg) throws Exception{
        return docIngresoSerieDAO.obtenerTodos(uniNeg);
    }

    public List<DocIngresoSerieBean> obtenerTodosActivos(String uniNeg) throws Exception{
        return docIngresoSerieDAO.obtenerTodosActivos(uniNeg);
    }
    
    public DocIngresoSerieBean buscarPorId(DocIngresoSerieBean docIngresoSerieBean) throws Exception{
        return docIngresoSerieDAO.buscarPorId(docIngresoSerieBean);
    }

    @Transactional
    public void insertar(DocIngresoSerieBean docIngresoSerieBean) throws Exception{
        docIngresoSerieDAO.insertar(docIngresoSerieBean);
    }
            
    @Transactional
    public void actualizar(DocIngresoSerieBean docIngresoSerieBean) throws Exception{
        docIngresoSerieDAO.actualizar(docIngresoSerieBean);
    }

    @Transactional
    public void eliminar(DocIngresoSerieBean docIngresoSerieBean) throws Exception{
        docIngresoSerieDAO.eliminar(docIngresoSerieBean);
    }

    @Transactional
    public void cambiarEstado(DocIngresoSerieBean docIngresoSerieBean) throws Exception{
        docIngresoSerieDAO.cambiarEstado(docIngresoSerieBean);
    }
    
    public DocIngresoSerieBean obtenerActual() throws Exception{
       return docIngresoSerieDAO.obtenerActual();
    }
}
