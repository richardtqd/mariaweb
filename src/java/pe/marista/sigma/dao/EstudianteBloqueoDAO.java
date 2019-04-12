 package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.EstudianteBloqueoBean;
import pe.marista.sigma.bean.reporte.EstudianteBloqueoRepBean;

public interface EstudianteBloqueoDAO {
    
    public void insertar(EstudianteBloqueoBean estudianteBloqueoBean) throws Exception;
    public void actualizar(EstudianteBloqueoBean estudianteBloqueoBean) throws Exception;
    public List<EstudianteBloqueoBean> obtenerBloqueoPorEstudiantes(EstudianteBloqueoBean estudianteBloqueoBean) throws Exception;
    public List<EstudianteBloqueoBean> obtenerBloqueoFiltro(EstudianteBloqueoBean estudianteBloqueoBean) throws Exception;
    public EstudianteBloqueoBean obtenerEstudianteBloqueo(EstudianteBloqueoBean estudianteBloqueoBean) throws Exception;
     public List<EstudianteBloqueoBean> obtenerFiltroEstudianteMasivo(EstudianteBloqueoBean estudianteBloqueoBean) throws Exception;
     public List<EstudianteBloqueoRepBean> obtenerCabecera(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio) throws Exception;
     public List<EstudianteBloqueoRepBean> obtenerDetalle(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("idEstudiante") String idEstudiante) throws Exception;
}
