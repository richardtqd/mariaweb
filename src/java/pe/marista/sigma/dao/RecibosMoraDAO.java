package pe.marista.sigma.dao;

import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.RecibosMoraBean;

public interface RecibosMoraDAO {

    public Integer obtenerUltimo(String uniNeg) throws Exception;

    public RecibosMoraBean obtenerId(@Param("nroDocMora") Integer nroDocMora, @Param("uniNeg") String uniNeg, @Param("serieMora") String serieMora, @Param("idDocIngreso") Integer idDocIngreso) throws Exception;
    
    public String obtenerIdDocIngreso(@Param("idDocIngreso") Integer idDocIngreso) throws Exception;

    public void insertarRecibosMora(RecibosMoraBean recibosMoraBean) throws Exception;
}
