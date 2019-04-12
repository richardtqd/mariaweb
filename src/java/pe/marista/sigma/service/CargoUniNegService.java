package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.CargoUniNegBean;
import pe.marista.sigma.dao.CargoUniNegDAO;

/**
 *
 * @author Administrador
 */
public class CargoUniNegService {
    
    private CargoUniNegDAO cargoUniNegDAO;
    
    @Transactional
    public void insertarCargoUniNeg(CargoUniNegBean cargoUniNegBean) throws Exception {
        cargoUniNegDAO.insertarCargoUniNeg(cargoUniNegBean);
    }
    
    @Transactional
    public void modidicarCargoUniNeg(CargoUniNegBean cargoUniNegBean) throws Exception {
        cargoUniNegDAO.modificarCargoUniNeg(cargoUniNegBean);
    }
    @Transactional
    public void cambiarEstadoCargoUniNeg(CargoUniNegBean cargoUniNegBean) throws Exception {
        cargoUniNegDAO.cambiarEstadoCargoUniNeg(cargoUniNegBean);
    }
    
    @Transactional
    public void eliminarCargoUniNeg(CargoUniNegBean cargoUniNegBean) throws Exception {
        cargoUniNegDAO.eliminarCargoUniNeg(cargoUniNegBean);
    }
    
    
    public CargoUniNegBean obtenerCargoUniNegPorId(CargoUniNegBean cargoUniNegBean) throws Exception {
        return cargoUniNegDAO.obtenerCargoUniNegPorId(cargoUniNegBean);
    }
    
    
    public List<CargoUniNegBean> obtenerCargoUniNegPorUniNeg(String uniNeg) throws Exception {
        return cargoUniNegDAO.obtenerCargoUniNegPorUniNeg(uniNeg);
    }
    public List<CargoUniNegBean> obtenerTodos() throws Exception {
        return cargoUniNegDAO.obtenerTodos();
    }
    
        
    public CargoUniNegDAO getCargoUniNegDAO() {
        return cargoUniNegDAO;
    }
    
    public void setCargoUniNegDAO(CargoUniNegDAO cargoUniNegDAO) {
        this.cargoUniNegDAO = cargoUniNegDAO;
    }
    
}
