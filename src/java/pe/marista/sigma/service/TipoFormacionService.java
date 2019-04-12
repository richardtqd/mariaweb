package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.TipoFormacionBean;
import pe.marista.sigma.dao.TipoFormacionDAO;


public class TipoFormacionService {

    private TipoFormacionDAO tipoFormacionDAO;

    //Getter and Setter
    public TipoFormacionDAO getTipoFormacionDAO() {
        return tipoFormacionDAO;
    }

    public void setTipoFormacionDAO(TipoFormacionDAO tipoFormacionDAO) {
        this.tipoFormacionDAO = tipoFormacionDAO;
    }
    
    //Metodos Lógica de Negocio
    @Transactional
    public void insertar(TipoFormacionBean tipoFormacionBean) throws Exception{
        tipoFormacionDAO.insertar(tipoFormacionBean);
    }
    @Transactional
    public void modificar(TipoFormacionBean tipoFormacionBean) throws Exception{
        tipoFormacionDAO.actualizar(tipoFormacionBean);
    }
    @Transactional
    public void eliminar(TipoFormacionBean tipoFormacionBean) throws Exception{
        tipoFormacionDAO.eliminar(tipoFormacionBean);
    }
    public List<TipoFormacionBean> obtenerTodos() throws Exception{    
        return tipoFormacionDAO.obtenerTodos();
    }
    public List<TipoFormacionBean> obtenerPorFiltro(TipoFormacionBean tipoFormacionBean) throws Exception{    
        return tipoFormacionDAO.obtenerPorFiltro(tipoFormacionBean);
    }
    public TipoFormacionBean obtenerPorId(TipoFormacionBean tipoFormacionBean) throws Exception{    
        return tipoFormacionDAO.obtenerPorId(tipoFormacionBean);
    }
    
    public List<TipoFormacionBean> obtenerTipoFormacionSuperior(TipoFormacionBean tipoFormacionBean) throws Exception{    
        return tipoFormacionDAO.obtenerTipoFormacionSuperior(tipoFormacionBean);
    }
}
