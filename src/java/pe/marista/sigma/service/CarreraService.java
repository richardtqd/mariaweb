package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.CarreraBean;
import pe.marista.sigma.dao.CarreraDAO;

public class CarreraService{
    private CarreraDAO carreraDAO;
 
    public List<CarreraBean> obtenerCarreraPorNivelAcademico(Integer idNivelAcademico) throws Exception {
        return carreraDAO.obtenerCarreraPorNivelAcademico(idNivelAcademico);
    }
    public List<CarreraBean> obtenerTodosCAR() throws Exception {
        return carreraDAO.obtenerTodosCAR();
    }
    public List<CarreraBean> obtenerCarreraPorCarreraArea(Integer idCarreraAreaBean) throws Exception {
        return carreraDAO.obtenerCarreraPorCarreraArea(idCarreraAreaBean);
    }
    
    public List<CarreraBean> obtenerCarreraPorCarreraSubArea(Integer idCarreraSubArea) throws Exception {
        return carreraDAO.obtenerCarreraPorCarreraSubArea(idCarreraSubArea);
    }
    public List<CarreraBean> obtenerCarreraPorGradoAca(Integer idGradoAcademico) throws Exception {
        return carreraDAO.obtenerCarreraPorGradoAca(idGradoAcademico);
    }
    
    public CarreraBean obtenerCarreraPorId(Integer idCarrera) throws Exception{
        return carreraDAO.obtenerCarreraPorId(idCarrera);
    }

    @Transactional
    public void insertarCarrera(CarreraBean carreraBean) throws Exception {
        carreraDAO.insertarCarrera(carreraBean);
    }

    @Transactional
    public void modificarCarrera(CarreraBean carreraBean) throws Exception {
        carreraDAO.modificarCarrera(carreraBean);
    }

    @Transactional
    public void eliminarCarrera(Integer idCarrera) throws Exception {
        carreraDAO.eliminarCarrera(idCarrera);
    }
       
     
    //metodos getter y setter
    public CarreraDAO getCarreraDAO() {
        return carreraDAO;
    }

    public void setCarreraDAO(CarreraDAO carreraDAO) {
        this.carreraDAO = carreraDAO;
    }
    
}
