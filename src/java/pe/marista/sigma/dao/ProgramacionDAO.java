package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.ProgramacionBean;

/**
 *
 * @author Administrador
 */
public interface ProgramacionDAO {

    public void insertarProgramacion(ProgramacionBean programacionBean) throws Exception;

    public void modificarProgramacion(ProgramacionBean programacionBean) throws Exception;

    public void modificarProgramacionCupos(ProgramacionBean programacionBean) throws Exception;

    public void eliminarProgramacion(ProgramacionBean programacionBean) throws Exception;

    public void cambiarEstado(ProgramacionBean programacionBean) throws Exception;

    public List<ProgramacionBean> obtenerProgramacion(ProgramacionBean programacionBean) throws Exception;

    public List<ProgramacionBean> obtenerProgramacionActivos(ProgramacionBean programacionBean) throws Exception;

    public List<ProgramacionBean> obtenerProgramacionImput( @Param("uniNeg") String uniNeg, @Param("idProgramacionDscto") Integer idProgramacionDscto) throws Exception;

    public List<ProgramacionBean> obtenerProgramacionOutput( @Param("uniNeg") String uniNeg, @Param("idProgramacionDscto") Integer idProgramacionDscto) throws Exception;

    public ProgramacionBean obtenerPrograPorId(ProgramacionBean programacionBean) throws Exception;

    public Integer obtenerCupoPrograPorId(ProgramacionBean programacionBean) throws Exception;

    //1 si hay cupos - 0 no hay cupos
    public Integer obtenerCuposPrograPorIdFor(@Param("uniNeg") String uniNeg, @Param("list") List<Integer> idProgramacion) throws Exception;

    public List<ProgramacionBean> obtenerPrograPorTipo(@Param("codigo") String codigo, @Param("uniNeg") String uniNeg) throws Exception;

    public List<ProgramacionBean> obtenerPrograAdmisionUniNeg(ProgramacionBean programacionBean) throws Exception;

    public List<ProgramacionBean> obtenerProgPorTipoPorAnioPorUniNeg(@Param("tipoCodigo") String tipoCodigo, @Param("codigo") String codigo, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio) throws Exception;

    public List<ProgramacionBean> obtenerProgPorTipoActivos(String uniNeg) throws Exception;

    public List<ProgramacionBean> obtenerProgPorTipoActivosPorConcepto(@Param("uniNeg") String uniNeg, @Param("idConcepto") Integer idConcepto) throws Exception;

    public List<ProgramacionBean> obtenerProgPorTipoActivosDocIngFor(@Param("uniNeg") String uniNeg, @Param("list") List<Integer> idProgramacion, @Param("idConcepto") Integer idConcepto) throws Exception;

    public List<ProgramacionBean> obtenerProgPorTipoActivosRef(ProgramacionBean programacionBean) throws Exception;

    public List<Integer> obtenerProgRegPorDiscente(@Param("uniNeg") String uniNeg, @Param("idDiscente") String idDiscente) throws Exception;

    public List<ProgramacionBean> obtenerTodos() throws Exception;

}
