package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.CatalogoFamiliaBean;
import pe.marista.sigma.dao.CatalogoFamiliaDAO;


public class CatalogoFamiliaService {

    private CatalogoFamiliaDAO catalogoFamiliaDAO;

    //Getter and Setter
    public CatalogoFamiliaDAO getCatalogoFamiliaDAO() {
        return catalogoFamiliaDAO;
    }

    public void setCatalogoFamiliaDAO(CatalogoFamiliaDAO catalogoFamiliaDAO) {
        this.catalogoFamiliaDAO = catalogoFamiliaDAO;
    }
   
    
    //Metodos Lógica de Negocio
    
    @Transactional
    public void insertar(CatalogoFamiliaBean catalogoFamiliaBean) throws Exception{
        catalogoFamiliaDAO.insertar(catalogoFamiliaBean);
    }
    @Transactional
    public void modificar(CatalogoFamiliaBean catalogoFamiliaBean) throws Exception{
        catalogoFamiliaDAO.actualizar(catalogoFamiliaBean);
    }
    @Transactional
    public void eliminar(CatalogoFamiliaBean catalogoFamiliaBean) throws Exception{
        catalogoFamiliaDAO.eliminar(catalogoFamiliaBean);
    }
    
    public List<CatalogoFamiliaBean> obtenerTodos() throws Exception{    
        return catalogoFamiliaDAO.obtenerTodos();
    }
    public CatalogoFamiliaBean obtenerPorId(Integer id) throws Exception{    
        return catalogoFamiliaDAO.obtenerPorId(id);
    }

}
