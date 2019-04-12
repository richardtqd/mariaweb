package pe.marista.sigma.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.LogBean;
import pe.marista.sigma.dao.LogDAO;

public class LogService {

    private LogDAO logDAO;

    public LogDAO getLogDAO() {
        return logDAO;
    }

    public void setLogDAO(LogDAO logDAO) {
        this.logDAO = logDAO;
    }

    public List<LogBean> obtenerTodos() throws Exception {
        return logDAO.obtenerTodos();
    }

//    public List<LogBean> obtenerPorFiltro(LogFiltroBean logFiltroBean) throws Exception {
//
//        return logDAO.obtenerPorFiltro(logFiltroBean);
//    }

    @Transactional
    public void insertarLog(LogBean bean) throws Exception {
//        logDAO.insertarLog(bean);
    }
}
