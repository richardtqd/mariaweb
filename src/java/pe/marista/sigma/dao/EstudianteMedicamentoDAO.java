package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.EstudianteMedicamentoBean;
import pe.marista.sigma.bean.reporte.EstudianteMedicamentosRepBean;

public interface EstudianteMedicamentoDAO {

    public void insertarEstMedicamento(EstudianteMedicamentoBean estudianteMedicamentoBean) throws Exception;

    public void modificarEstMedicamento(EstudianteMedicamentoBean estudianteMedicamentoBean) throws Exception;

    public void eliminarEstMedicamento(EstudianteMedicamentoBean estudianteMedicamentoBean) throws Exception;

    public EstudianteMedicamentoBean obtenerEstMedicamentoPorId(EstudianteMedicamentoBean estudianteMedicamentoBean) throws Exception;

    public List<EstudianteMedicamentoBean> obtenerEstMedicamentoPorEst(String idEstudiante) throws Exception;
    
    //FichaClinica
    public List<EstudianteMedicamentosRepBean> obtenerMedicamentos(@Param("idEstudiante") String idEstudiante, @Param("uniNeg") String uniNeg) throws Exception;
}
