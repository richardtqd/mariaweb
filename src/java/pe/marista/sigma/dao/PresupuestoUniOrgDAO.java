/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.PresupuestoBean;
import pe.marista.sigma.bean.PresupuestoUniOrgBean;
import pe.marista.sigma.bean.reporte.DetPresUniOrgRepBean;
import pe.marista.sigma.bean.reporte.PresUniOrgRepBean;

/**
 *
 * @author MS002
 */
public interface PresupuestoUniOrgDAO {

    public List<PresupuestoUniOrgBean> obtenerPresupuestoUniOrg(String uniNeg) throws Exception;

    public List<PresupuestoUniOrgBean> obtenerPorPlanOperativo(@Param("idUniOrg") Integer idUniOrg, @Param("uniNeg") String uniNeg) throws Exception;

    public void insertarPresupuestoUniOrg(PresupuestoUniOrgBean presupuestoUniOrgBean) throws Exception;

    public void insertarPresupuestoProg(PresupuestoUniOrgBean presupuestoUniOrgBean) throws Exception;

    public void modificarPresupuestoUniOrg(PresupuestoUniOrgBean presupuestoUniOrgBean) throws Exception;

    public void modificarPresupuestoProg(PresupuestoUniOrgBean presupuestoUniOrgBean) throws Exception;

    public void eliminarPresupuestoUniOrg(PresupuestoUniOrgBean presupuestoUniOrgBean) throws Exception;

    public PresupuestoUniOrgBean obtenerPorId(PresupuestoUniOrgBean presupuestoUniOrgBean) throws Exception;

    public List<PresupuestoUniOrgBean> obtenerListaPorId(PresupuestoUniOrgBean presupuestoUniOrgBean) throws Exception;

    public List<PresupuestoUniOrgBean> obtenerListaCuentaUo(@Param("cuenta") Integer cuenta, @Param("uniNeg") String uniNeg, @Param("idUniOrg") Integer idUniOrg, @Param("anio") Integer anio) throws Exception;

    public List<PresupuestoUniOrgBean> obtenerPorCuenta(@Param("cuenta") Integer cuenta, @Param("uniNeg") String uniNeg) throws Exception;

    public PresupuestoUniOrgBean obtenerListaCuentaUorg(@Param("cuenta") Integer cuenta, @Param("uniNeg") String uniNeg, @Param("idUniOrg") Integer idUniOrg, @Param("anio") Integer anio) throws Exception;

    public List<PresupuestoUniOrgBean> obtenerPresupuestoOrg(@Param("uniNeg") String uniNeg) throws Exception;

    public List<PresupuestoUniOrgBean> obtenerPresupuestoOrgId(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio) throws Exception;

    public Object execProPresUniOrg(@Param("uniNeg") String uniNeg) throws Exception;

    public List<PresUniOrgRepBean> obtenerPresupuestoPorUniOrgForTop1(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("list") List<Integer> ids) throws Exception;

    public List<PresUniOrgRepBean> obtenerPresupuestoPorUniOrgFor(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("list") List<Integer> ids) throws Exception;

    public List<DetPresUniOrgRepBean> obtenerDetPresupuestoPorUniOrg(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("idUniOrg") Integer idUniOrg) throws Exception;

    //FILTRO DE PRESUPUESTO
    public List<PresupuestoUniOrgBean> filtrarPresupuesto(PresupuestoBean presupuestoBean) throws Exception;
}
