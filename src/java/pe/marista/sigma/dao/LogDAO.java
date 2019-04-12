package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.LogBean;


public interface LogDAO {
 
    public void insertarLog(LogBean bean) throws Exception;
    
    public List<LogBean> obtenerTodos() throws Exception;
    
//    public List<LogBean> obtenerPorFiltro(LogFiltroBean logFiltroBean) throws Exception;
    
}
