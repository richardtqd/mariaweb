package pe.marista.sigma.dao;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.ProcesoEnvioBean;
import pe.marista.sigma.bean.RecEnvBean;
import pe.marista.sigma.bean.reporte.IngresoCajaPensionesRepBean;

public interface RecEnvDAO {

    public List<RecEnvBean> buscarProcesoRecEnv(@Param("uniNeg") String uniNeg, @Param("fecha") Date fecha) throws Exception;

    public List<IngresoCajaPensionesRepBean> buscarPensionesEnCajaPorFecha(@Param("uniNeg") String uniNeg, @Param("fechaIni") Date fechaIni, @Param("fechaFin") Date fechaFin) throws Exception;

    public Object execProEnvioCol(ProcesoEnvioBean procesoEnvioBean) throws Exception;

}
