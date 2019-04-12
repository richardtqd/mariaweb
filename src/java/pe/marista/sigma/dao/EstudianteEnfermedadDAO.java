package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.EstudianteEnfermedadBean;
import pe.marista.sigma.bean.reporte.EstudianteEnfermedadRepBean;

public interface EstudianteEnfermedadDAO {

    public void insertarEstEnfermedad(EstudianteEnfermedadBean estudianteEnfermedadBean) throws Exception;

    public void modificarEstEnfermedad(EstudianteEnfermedadBean estudianteEnfermedadBean) throws Exception;

    public void eliminarEstEnfermedad(EstudianteEnfermedadBean estudianteEnfermedadBean) throws Exception;

    public EstudianteEnfermedadBean obtenerEstEnfermedadPorId(EstudianteEnfermedadBean estudianteEnfermedadBean) throws Exception;

    public List<EstudianteEnfermedadBean> obtenerEstEnfermedadPorEst(String idEstudiante) throws Exception;
   
     //FichaClinica
    public List<EstudianteEnfermedadRepBean> obtenerEstudianteEnfermedad(@Param("idEstudiante") String idEstudiante, @Param("uniNeg") String uniNeg) throws Exception;
}
