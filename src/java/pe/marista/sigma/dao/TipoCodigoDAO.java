
package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.TipoCodigoBean;

/**
 *
 * @author Administrador
 */
public interface TipoCodigoDAO {

    public void insertar(TipoCodigoBean tipoCodigoBean) throws Exception;

    public void actualizar(TipoCodigoBean tipoCodigoBean) throws Exception;

    public void eliminar(TipoCodigoBean tipoCodigoBean) throws Exception;

    public List<TipoCodigoBean> obtenerTodos() throws Exception;

    public List<TipoCodigoBean> obtenerPorTipo(@Param("idTipoCodigo")String idTipoCodigo) throws Exception;

    public List<TipoCodigoBean> obtenerFiltro(TipoCodigoBean tipoCodigoBean) throws Exception;

    public TipoCodigoBean obtenerPorId(TipoCodigoBean tipoCodigoBean) throws Exception;

}
