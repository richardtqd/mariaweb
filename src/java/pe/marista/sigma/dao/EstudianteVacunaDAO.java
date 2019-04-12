package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.EstudianteVacunaBean;
import pe.marista.sigma.bean.reporte.EstudianteVacunasRepBean;

public interface EstudianteVacunaDAO {
    public void insertarEstudianteVacuna(EstudianteVacunaBean estudianteNacimientoBean) throws Exception;
    
    public void modificarEstudianteVacuna(EstudianteVacunaBean estudianteNacimientoBean) throws Exception;
    
    public List<EstudianteVacunaBean> obtenerEstVacunaPorEst(String idEstudiante) throws Exception;
    
    public EstudianteVacunaBean obtenerEstEnfermedadPorId(EstudianteVacunaBean estudianteNacimientoBean) throws Exception;
    
    public void eliminarEstMedicamento(EstudianteVacunaBean estudianteVacunaBean) throws Exception;
    
    //FichaClinica
    public List<EstudianteVacunasRepBean> obtenerVacunas(@Param("idEstudiante") String idEstudiante, @Param("uniNeg") String uniNeg) throws Exception;
    
}

