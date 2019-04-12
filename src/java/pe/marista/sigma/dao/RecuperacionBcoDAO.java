/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.RecuperacionBcoBean;

/**
 *
 * @author MS-001
 */
public interface RecuperacionBcoDAO {

    public RecuperacionBcoBean obtenerCamposRecuperacion(@Param("uniNeg") String uniNeg, @Param("idProcesoBanco") Integer idProcesoBanco, @Param("txt") String txt) throws Exception;

    public List<RecuperacionBcoBean> obtenerListaRecuperacionPorId(@Param("uniNeg") String uniNeg, @Param("idProcesoBanco") Integer idProcesoBanco) throws Exception;

    public void SP_insertarLogProcesoBanco(@Param("recuperacionBcoBean") RecuperacionBcoBean recuperacionBcoBean,
            @Param("idProcesoBanco") Integer idProcesoBanco, @Param("usuario") String usuario) throws Exception;

    public Integer validarCabeceraRecepcion(@Param("uniNeg") String uniNeg) throws Exception;

    public Integer validarPieRecepcion(@Param("uniNeg") String uniNeg) throws Exception;

    public RecuperacionBcoBean SP_obtenerCamposRecuperacion(@Param("uniNeg") String uniNeg, @Param("txt") String txt) throws Exception;

    public RecuperacionBcoBean SP_ActualizarCtaCte(@Param("recuperacionBcoBean") RecuperacionBcoBean recuperacionBcoBean,
            @Param("idProcesoBanco") Integer idProcesoBanco, @Param("usuario") String usuario) throws Exception;
}
