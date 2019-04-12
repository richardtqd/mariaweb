package pe.marista.sigma.dao;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.CursoTallerBean;
import pe.marista.sigma.bean.reporte.CursoTallerRepBean;
import pe.marista.sigma.bean.reporte.DetCursoTallerRepBean;
import pe.marista.sigma.bean.reporte.DetDetCursoTallerRepBean;

/**
 *
 * @author Administrador
 */
public interface CursoTallerDAO {

    public void insertarCursoTaller(CursoTallerBean cursoTallerBean) throws Exception;

    public void modificarCursoTaller(CursoTallerBean cursoTallerBean) throws Exception;

    public void eliminarCursoTaller(Integer idCursoTaller) throws Exception;

    public List<CursoTallerBean> obtenerCursoTaller() throws Exception;

    public List<CursoTallerBean> obtenerFiltroCursoTaller(CursoTallerBean cursoTallerBean) throws Exception;

    public CursoTallerBean obtenerCursoTallerPorId(@Param("idCursoTaller") Integer idCursoTaller, @Param("uniNeg") String uniNeg) throws Exception;

    public List<CursoTallerRepBean> obtenerTalleresRep(@Param("uniNeg") String uniNeg, @Param("fechaIni") Date fechaIni, @Param("fechaFin") Date fechaFin,@Param("list") List<Integer> idprogramacion,@Param("flg") Integer flg) throws Exception;

    public List<CursoTallerRepBean> obtenerTalleresRepId(@Param("uniNeg") String uniNeg, @Param("fechaIni") Date fechaIni, @Param("fechaFin") Date fechaFin, @Param("idProgramacion") Integer idProgramacion) throws Exception;

    public List<DetCursoTallerRepBean> obtenerDetalleTallerRep(@Param("uniNeg") String uniNeg, @Param("fechaIni") Date fechaIni, @Param("fechaFin") Date fechaFin, @Param("list") List<Integer> idprogramacion,@Param("flg") Integer flg) throws Exception;

    public List<DetCursoTallerRepBean> obtenerDetalleTallerRepId(@Param("uniNeg") String uniNeg, @Param("fechaIni") Date fechaIni, @Param("fechaFin") Date fechaFin, @Param("idProgramacion") Integer idProgramacion) throws Exception;

    public List<DetCursoTallerRepBean> obtenerDetalleTallerRepDesc(@Param("uniNeg") String uniNeg, @Param("fechaIni") Date fechaIni, @Param("fechaFin") Date fechaFin, @Param("list") List<Integer> idprogramacion,@Param("flg") Integer flg,@Param("descripcion") String descripcion) throws Exception;
    
    public List<CursoTallerRepBean> obtenerTalleresRepWeb(@Param("uniNeg") String uniNeg, @Param("fechaIni") String fechaIni, @Param("fechaFin") String fechaFin,@Param("list") List<Integer> idprogramacion,@Param("flg") Integer flg) throws Exception;
    
    public List<DetCursoTallerRepBean> obtenerDetalleTallerWebRepDesc(@Param("uniNeg") String uniNeg, @Param("fechaIni") String fechaIni, @Param("fechaFin") String fechaFin, @Param("list") List<Integer> idprogramacion,@Param("flg") Integer flg,@Param("descripcion") String descripcion) throws Exception;
    
    public List<DetCursoTallerRepBean> obtenerDetalleTallerRepWeb(@Param("uniNeg") String uniNeg, @Param("fechaIni") String fechaIni, @Param("fechaFin") String fechaFin, @Param("list") List<Integer> idprogramacion,@Param("flg") Integer flg) throws Exception;
    
    //public List<DetDetCursoTallerRepBean> obtenerInscritosTalleresRep(@Param("uniNeg") String uniNeg, @Param("idProgramacion") Integer idProgramacion) throws Exception;
    
    public List<DetDetCursoTallerRepBean> obtenerInscritosTalleresRep(@Param("uniNeg") String uniNeg, @Param("idProgramacion") Integer idProgramacion, @Param("fechaIni") Date fechaIni, @Param("fechaFin") Date fechaFin) throws Exception;
    
    public List<DetDetCursoTallerRepBean> obtenerInscritosTalleresRepWeb(@Param("uniNeg") String uniNeg, @Param("idProgramacion") Integer idProgramacion, @Param("fechaIni") String fechaIni, @Param("fechaFin") String fechaFin) throws Exception;

    // BORRAR
    public Integer obtenerNroIns(@Param("uniNeg") String uniNeg,@Param("idPersona") String idPersona,@Param("idProgramacion") Integer idProgramacion) throws Exception;
    
}
