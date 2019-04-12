/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.NivelTipoAccesoBean;

/**
 *
 * @author MS-001
 */
public interface NivelTipoAccesoDAO {

    public void insertarNivelTipoAcceso(NivelTipoAccesoBean niveTipoAccesoBean) throws Exception;

    public void actualizarNivelTipoAcceso(NivelTipoAccesoBean niveTipoAccesoBean) throws Exception;

    public void eliminarNivelTipoAcceso(NivelTipoAccesoBean niveTipoAccesoBean) throws Exception;
 
    public List<NivelTipoAccesoBean> obtenerNivelTipoAccesoPorAnio(NivelTipoAccesoBean niveTipoAccesoBean) throws Exception;

    public NivelTipoAccesoBean obtenerNivelTipoAcceso(NivelTipoAccesoBean niveTipoAccesoBean) throws Exception;

}
