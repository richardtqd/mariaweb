package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.CargoUniNegBean;
import pe.marista.sigma.bean.CodigoBean;

public interface CargoUniNegDAO {
 
    public void insertarCargoUniNeg(CargoUniNegBean cargoUniNegBean) throws Exception;

    public void modificarCargoUniNeg(CargoUniNegBean cargoUniNegBean) throws Exception;

    public void eliminarCargoUniNeg(CargoUniNegBean cargoUniNegBean) throws Exception;

    public CargoUniNegBean  obtenerCargoUniNegPorId(CargoUniNegBean cargoUniNegBean) throws Exception;
    
    public void cambiarEstadoCargoUniNeg(CargoUniNegBean cargoUniNegBean) throws Exception;

    public List<CargoUniNegBean> obtenerCargoUniNegPorUniNeg(String uniNeg) throws Exception;
    
    public List<CargoUniNegBean> obtenerCargoPorCategoria(CodigoBean codigoBean) throws Exception;
        
    public List<CargoUniNegBean> obtenerTodos() throws Exception;
    
    
    
}
