/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.DetProgramacionDsctoBean;
import pe.marista.sigma.bean.ProgramacionDsctoBean;

/**
 *
 * @author MS001
 */
public interface ProgramacionDsctoDAO {

    public List<ProgramacionDsctoBean> obtenerTodosPorUniNeg(String uniNeg) throws Exception;

    public ProgramacionDsctoBean obtenerProgramacionDsctoPorId(@Param("idProgramacionDscto") Integer idProgramacionDscto, @Param("uniNeg") String uniNeg) throws Exception;

    public ProgramacionDsctoBean obtenerProgDsctoPorProgramacionesFor(@Param("uniNeg") String uniNeg, @Param("list") List<Integer> idProgramacion, @Param("cant") Integer cant) throws Exception;
    public ProgramacionDsctoBean obtenerProgDsctoPorProgramacionesForSan(@Param("uniNeg") String uniNeg, @Param("list") List<Integer> idProgramacion, @Param("cant") Integer cant,@Param("flgEst") Boolean flgEst,@Param("mes") Integer mes) throws Exception;

    public ProgramacionDsctoBean obtenerProgDsctoPorProgramacionesForVer2(@Param("uniNeg") String uniNeg, @Param("list") List<Integer> idProgramacion, @Param("cant") Integer cant, @Param("montoTotal") Double montoTotal,@Param("flgEst") Integer flgEst) throws Exception;

    public List<ProgramacionDsctoBean> obtenerProgDsctoPorProgramacionesCantidadFor(@Param("uniNeg") String uniNeg, @Param("list") List<Integer> idProgramacion, @Param("cant") Integer cant) throws Exception;

    public Integer validarProg1En2(@Param("uniNeg") String uniNeg, @Param("list") List<Integer> idProgramacion, @Param("idProgramacionDscto") Integer idProgramacionDscto, @Param("cant") Integer cant) throws Exception;

    public Integer validarProgEnDetDscto(@Param("uniNeg") String uniNeg, @Param("idProgramacion") Integer idProgramacion, @Param("idProgramacionDscto") Integer idProgramacionDscto) throws Exception;

    public void insertarProgramacionDscto(ProgramacionDsctoBean ProgramacionDsctoBean) throws Exception;

    public void modificarProgramacionDscto(ProgramacionDsctoBean ProgramacionDsctoBean) throws Exception;

    public void eliminarProgramacionDscto(@Param("idProgramacionDscto") Integer idProgramacionDscto, @Param("uniNeg") String uniNeg) throws Exception;

    //DETALLE
    public List<DetProgramacionDsctoBean> obtenerDetallePorProgramacionDscto(@Param("list") List<Integer> idProgramacionDscto, @Param("uniNeg") String uniNeg) throws Exception;

    public void insertarDetProgramacionDscto(DetProgramacionDsctoBean detProgramacionDsctoBean) throws Exception;

    public void eliminarDetallePorProgramacionDscto(@Param("idProgramacionDscto") Integer idProgramacionDscto, @Param("uniNeg") String uniNeg) throws Exception;

    public void modificarDetProgramacionDscto(DetProgramacionDsctoBean detProgramacionDsctoBean) throws Exception;
    
    public List<DetProgramacionDsctoBean> obtenerTodosPorUniNegDetalle(String uniNeg) throws Exception;
      
}
