
package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.DiccionarioBean;
 

/**
 *
 * @author Administrador
 */
public interface DiccionarioDAO {

    
    public void modificarDiccionarioDescripcion(DiccionarioBean diccionarioBean) throws Exception;

    public DiccionarioBean obtenerDiccionarioPorId(DiccionarioBean diccionarioBean) throws Exception;

    public List<DiccionarioBean> obtenerDiccionario() throws Exception;
    public List<DiccionarioBean> obtenerTablaDiccionario() throws Exception;
    public List<DiccionarioBean> obtenerColumnaDiccionario() throws Exception;
    public List<DiccionarioBean> obtenerTipoDiccionario() throws Exception;
    
    public List<DiccionarioBean> obtenerTodosActivos() throws Exception;
    public DiccionarioBean ejecutarStoredProcedure(DiccionarioBean diccionarioBean) throws Exception;
    
    
     
     
}
