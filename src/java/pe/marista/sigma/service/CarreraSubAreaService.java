package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.CarreraAreaBean;
import pe.marista.sigma.bean.CarreraSubAreaBean;
import pe.marista.sigma.dao.CarreraSubAreaDAO;

public class CarreraSubAreaService{
    
    private CarreraSubAreaDAO carreraSubAreaDAO;
    
    public List<CarreraSubAreaBean> obtenerCarreraSubAreaPorCarreraArea(Integer idCarreraArea) throws Exception {
        return carreraSubAreaDAO.obtenerCarreraSubAreaPorCarreraArea(idCarreraArea);
//        return carreraSubAreaDAO.obtenerCarreraSubAreaPorCarreraArea();
    }
    
    public List<CarreraSubAreaBean> obtenerTodosSA() throws Exception{    
        return carreraSubAreaDAO.obtenerTodosSA();
    }

    public List<CarreraSubAreaBean> obtenerCarreraSAreaPorArea() throws Exception {
        return carreraSubAreaDAO.obtenerCarreraSAreaPorArea();
    }
    
//    public List<CarreraAreaBean> obtenerCarreraArea() throws Exception{
//        
//    }

    @Transactional
    public void insertarSubArea(CarreraSubAreaBean carreraSubAreaBean) throws Exception {
        carreraSubAreaDAO.insertarSubArea(carreraSubAreaBean);
    }

    @Transactional
    public void modificarSubArea(CarreraSubAreaBean carreraSubAreaBean) throws Exception {
        carreraSubAreaDAO.modificarSubArea(carreraSubAreaBean);
    }

    @Transactional
    public void eliminarSubArea(Integer idCarreraSubArea) throws Exception {
        carreraSubAreaDAO.eliminarSubArea(idCarreraSubArea);
    }

    public CarreraSubAreaBean obtenerSubAreaPorId(Integer idCarreraSubArea) throws Exception {
        return carreraSubAreaDAO.obtenerSubAreaPorId(idCarreraSubArea);
    }
    
    
    
    //metodos getter and setter 

    public CarreraSubAreaDAO getCarreraSubAreaDAO() {
        return carreraSubAreaDAO;
    }

    public void setCarreraSubAreaDAO(CarreraSubAreaDAO carreraSubAreaDAO) {
        this.carreraSubAreaDAO = carreraSubAreaDAO;
    }
     
     
     
    
}
