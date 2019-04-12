/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.TipoEnvioUniNegBean;

/**
 *
 * @author MS-001
 */
public interface TipoEnvioUniNegDAO {
    
    public List<TipoEnvioUniNegBean> obtenerTipoEnvioUniNeg(String uniNeg) throws Exception;
    public TipoEnvioUniNegBean obtenerTipoEnvioPorId(@Param("idTipoEnvioUniNeg") Integer idTipoEnvioUniNeg, @Param("uniNeg") String uniNeg) throws Exception;
}
