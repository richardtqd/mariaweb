/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.PaganteBean;

/**
 *
 * @author JC
 */
public interface PaganteDAO {

    public List<PaganteBean> obtener(PaganteBean paganteBean) throws Exception;

    public PaganteBean obtenerPorIdPagEst(PaganteBean paganteBean) throws Exception;

    public PaganteBean obtenerPorIdPagPer(PaganteBean paganteBean) throws Exception;

    public PaganteBean obtenerPorIdPagExt(PaganteBean paganteBean) throws Exception;

    public PaganteBean obtenerPorIdPagEnt(PaganteBean paganteBean) throws Exception;

    public List<PaganteBean> filtrarPagante(PaganteBean paganteBean) throws Exception;

    public List<PaganteBean> filtrarPaganteEst(PaganteBean paganteBean) throws Exception;

    public List<PaganteBean> filtrarPagantePer(PaganteBean paganteBean) throws Exception;

    public List<PaganteBean> filtrarPaganteExt(PaganteBean paganteBean) throws Exception;

    public List<PaganteBean> filtrarPaganteEnt(PaganteBean paganteBean) throws Exception;

    public List<PaganteBean> filtrarPaganteObj(PaganteBean paganteBean) throws Exception;

    public void insertar(PaganteBean paganteBean) throws Exception;

    public void actualizar(PaganteBean paganteBean) throws Exception;

    public PaganteBean obtenerPorId(PaganteBean paganteBean) throws Exception;

    public void eliminar(PaganteBean paganteBean) throws Exception;

    public Object execProPagante(@Param("uniNeg") String uniNeg, @Param("estado") Integer estado, @Param("creaPor") String creaPor) throws Exception;

    //FILTRO DE GRAFICOS DE FICHA POR PAGANTE
    public List<PaganteBean> obtenerGrafoFichaEstudiante(PaganteBean paganteBean) throws Exception;

    //FILTRO GRAFO PAGANTE
    public List<PaganteBean> obtenerGrafoEstudiante(PaganteBean paganteBean) throws Exception;

    public List<PaganteBean> obtenerGrafoPersonal(PaganteBean paganteBean) throws Exception;

    public List<PaganteBean> obtenerGrafoExterno(PaganteBean paganteBean) throws Exception;

    public List<PaganteBean> obtenerGrafoEntidad(PaganteBean paganteBean) throws Exception;

}
