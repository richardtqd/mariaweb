package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.CatalogoCategoriaBean;
import pe.marista.sigma.dao.CatalogoCategoriaDAO;


public class CatalogoCategoriaService 
{
    
    private CatalogoCategoriaDAO catalogoCategoriaDAO;

    //Getter and Setter
    public CatalogoCategoriaDAO getCatalogoCategoriaDAO() {
        return catalogoCategoriaDAO;
    }

    public void setCatalogoCategoriaDAO(CatalogoCategoriaDAO catalogoCategoriaDAO) {
        this.catalogoCategoriaDAO = catalogoCategoriaDAO;
    }
    

    //Metodos Lógica de Negocio
    @Transactional
    public void insertar(CatalogoCategoriaBean catalogoCategoriaBean) throws Exception {
        catalogoCategoriaDAO.insertar(catalogoCategoriaBean);
    }
    @Transactional
    public void modificar(CatalogoCategoriaBean catalogoCategoriaBean) throws Exception {
        catalogoCategoriaDAO.actualizar(catalogoCategoriaBean);
    }
    @Transactional
    public void eliminar(CatalogoCategoriaBean catalogoCategoriaBean) throws Exception {
        catalogoCategoriaDAO.eliminar(catalogoCategoriaBean);
    }
    public List<CatalogoCategoriaBean> obtenerTodos() throws Exception {
        return catalogoCategoriaDAO.obtenerTodos();
    }
    
    public List<CatalogoCategoriaBean> obtenerPorFamilia(Integer idCatalogoFamilia) throws Exception {
        return catalogoCategoriaDAO.obtenerPorFamilia(idCatalogoFamilia);
    }

    public CatalogoCategoriaBean obtenerPorId(Integer id) throws Exception {
        return catalogoCategoriaDAO.obtenerPorId(id);
    }

}
