package pe.marista.sigma.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.GradoAcademicoBean;

public interface GradoAcademicoDAO {

    public void insertar(GradoAcademicoBean gradoAcademicoBean) throws Exception;

    public void actualizar(GradoAcademicoBean gradoAcademicoBean) throws Exception;

    public void eliminar(GradoAcademicoBean gradoAcademicoBean) throws Exception;

    public List<GradoAcademicoBean> obtenerTodos() throws Exception;

    public List<GradoAcademicoBean> obtenerPorFiltro(GradoAcademicoBean gradoAcademicoBean) throws Exception;

    public GradoAcademicoBean obtenerPorId(GradoAcademicoBean gradoAcademicoBean) throws Exception;

    public GradoAcademicoBean obtenerPorIdNombre(String nombre) throws Exception;

    //Filtros Matricula
    public List<GradoAcademicoBean> obtenerTodosMatri(@Param("parms") Map<String, Object> parms) throws Exception;

    public List<GradoAcademicoBean> obtenerTodosMatriSinBachiller(@Param("parms") Map<String, Object> parms) throws Exception;

    public List<GradoAcademicoBean> obtenerCuartoBachillerTercero(@Param("parms") Map<String, Object> parms) throws Exception;

    public List<GradoAcademicoBean> obtenerQuintoCuartoBachiller(@Param("parms") Map<String, Object> parms) throws Exception;

    public List<GradoAcademicoBean> obtenerTodosIniPriSec(@Param("parms") Map<String, Object> parms) throws Exception;

    //Carrera
    public List<GradoAcademicoBean> obtenerGradoAcaPorNivelAca(Integer idNivelAcademico) throws Exception;

    public List<GradoAcademicoBean> obtenerPorIdNombreLista(String nombre) throws Exception;
}
