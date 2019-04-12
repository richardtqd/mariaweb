package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.EstudianteRetiroBean;

public interface EstudianteRetiroDAO {

    public List<EstudianteRetiroBean> obtenerPorEstudiante(@Param("uniNeg") String uniNeg,@Param("anio") Integer anio, @Param("codigo") String codigo) throws Exception;
    
    public void insertarEstudianteRetiro(EstudianteRetiroBean estudianteRetiroBean) throws Exception;
}

