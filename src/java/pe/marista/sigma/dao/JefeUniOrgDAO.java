/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.JefeUniOrgBean;

/**
 *
 * @author MS001
 */
public interface JefeUniOrgDAO {

    public List<JefeUniOrgBean> obtenerJefeUniOrgPorUniNegPorEstado(@Param("status") Boolean status, @Param("uniNeg") String uniNeg) throws Exception;

    public List<JefeUniOrgBean> obtenerJefeUniOrgPorUniOrg(@Param("idUniOrg") Integer idUniOrg, @Param("uniNeg") String uniNeg) throws Exception;

    public JefeUniOrgBean obtenerJefeUniOrgPorId(JefeUniOrgBean jefeUniOrgBean) throws Exception;

    public JefeUniOrgBean obtenerIdUniOrgPorNombre(@Param("uniOrg") String uniOrg, @Param("uniNeg") String uniNeg) throws Exception;

    public void insertarJefeUniOrg(JefeUniOrgBean jefeUniOrgBean) throws Exception;

    public void modificarJefeUniOrg(JefeUniOrgBean jefeUniOrgBean) throws Exception;

    public void eliminarJefeUniOrg(JefeUniOrgBean jefeUniOrgBean) throws Exception;

    public void cambiarEstado(JefeUniOrgBean jefeUniOrgBean) throws Exception;

    public void cambiarEstadoAllInactivo(@Param("idJeFeUniOrg") Integer idJeFeUniOrg, @Param("uniNeg") String uniNeg, @Param("modiPor") String modiPor) throws Exception;

}
