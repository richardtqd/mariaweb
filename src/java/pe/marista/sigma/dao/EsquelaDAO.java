/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.EsquelaBean;

/**
 *
 * @author MS002
 */
public interface EsquelaDAO {

    public List<EsquelaBean> obtenerEsquela(String uniNeg) throws Exception;

    public List<EsquelaBean> obtenerEsquelaFiltro(EsquelaBean esquelaBean) throws Exception;

    public EsquelaBean obtenerPorId(Integer idEsquela) throws Exception;

    public EsquelaBean obtenerPorIdEsq(EsquelaBean esquelaBean) throws Exception;

    public void eliminarEsquela(Integer idEsquela) throws Exception;

    public void insertarEsquela(EsquelaBean esquelaBean) throws Exception;

    public void modificarEsquela(EsquelaBean esquelaBean) throws Exception;

    public Integer obtenerMaxEsquela(String uniNeg) throws Exception;

    public Integer obtenerMaxAccion(String uniNeg) throws Exception;

    public List<EsquelaBean> obtenerEsquelaAccion(EsquelaBean esquelaBean) throws Exception;

    public void actualizarEsquela(EsquelaBean esquelaBean) throws Exception;

    public void eliminarEsquelaMasivo(EsquelaBean esquelaBean) throws Exception;

}
