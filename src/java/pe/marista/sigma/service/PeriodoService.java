/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.PeriodoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.dao.PeriodoDAO;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.util.MaristaUtils;

/**
 *
 * @author MS002
 */
public class PeriodoService {

    private PeriodoDAO periodoDAO;

    public List<PeriodoBean> obtenerPeriodo() throws Exception {
        return periodoDAO.obtenerPeriodo();
    }

    public void insertarPeriodo(PeriodoBean periodoBean) throws Exception {
        periodoDAO.insertarPeriodo(periodoBean);
    }

    @Transactional
    public void insertarPeriodoAnio(PeriodoBean periodoBean, List<Object> listaAniosPeriodo) throws Exception {
        PeriodoBean periodo = new PeriodoBean();
        UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        for (Object anio : listaAniosPeriodo) {
            periodo.getUniNeg().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            periodo.getPlanEstrategicoBean().setIdPlanEstrategico(periodoBean.getPlanEstrategicoBean().getIdPlanEstrategico());
            periodo.getLineaEstrategicaBean().setIdLinea(periodoBean.getLineaEstrategicaBean().getIdLinea());
            periodo.getObjetivoEstrategicaBean().setIdObjEstrategico(periodoBean.getObjetivoEstrategicaBean().getIdObjEstrategico());
            periodo.getObjetivoEstrategicoDetBean().setIdObjEstrategicoDet(periodoBean.getObjetivoEstrategicoDetBean().getIdObjEstrategicoDet());
            periodo.setAnio(Integer.parseInt(anio.toString()));
            periodo.getIndicadorBean().setIdIndicador(periodoBean.getIndicadorBean().getIdIndicador());
            periodo.getCodigoValor().setIdCodigo(periodo.getCodigoValor().getIdCodigo());
            periodo.setMeta(periodoBean.getMeta());
            periodo.setCreaPor(beanUsuarioSesion.getUsuario());
            periodoDAO.insertarPeriodo(periodo);
        }
    }

    public void modificarPeriodo(PeriodoBean periodoBean) throws Exception {
        periodoDAO.modificarPeriodo(periodoBean);
    }

    public void modificarPer(PeriodoBean periodoBean) throws Exception {
        periodoDAO.modificarPer(periodoBean);
    }

    @Transactional
    public void modidificarPeriodoDeta(PeriodoBean periodoBean, List<Object> listaAniosPeriodo, Integer flg) throws Exception {
        UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        List<PeriodoBean> listaPeriodo = new ArrayList<>();
        PeriodoService periodoService = BeanFactory.getPeriodoService();
        listaPeriodo = periodoService.obtenerPorDetalles(periodoBean.getObjetivoEstrategicoDetBean().getIdObjEstrategicoDet(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        System.out.println(listaPeriodo.size());
        for (PeriodoBean periodo : listaPeriodo) {
            for (Object anio : listaAniosPeriodo) {
                if (periodo.getAnio().equals(Integer.parseInt(anio.toString()))) {
//                    periodo.setAn(Integer.parseInt(anio.toString()));
////                    periodoDAO.modificarPer(periodo);
                } else {
                    if (!periodo.getAnio().equals(Integer.parseInt(anio.toString()))) {
                        if (flg != null) {
                            if (flg.equals(1)) {
                                periodo.setAnio(Integer.parseInt(anio.toString()));
                                periodo.setCreaPor(beanUsuarioSesion.getUsuario());
                                periodo.getUniNeg().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                periodoDAO.insertarPeriodo(periodo);
                            } else {
                                if (flg.equals(0)) {
                                    periodoDAO.eliminarPeriodo(periodo);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void eliminarPeriodo(PeriodoBean periodoBean) throws Exception {
        periodoDAO.eliminarPeriodo(periodoBean);
    }

    public PeriodoBean obtenerPorId(PeriodoBean periodoBean) throws Exception {
        return periodoDAO.obtenerPorId(periodoBean);
    }

    public PeriodoBean obtenerPeriodoPorId(Integer idPeriodo) throws Exception {
        return periodoDAO.obtenerPeriodoPorId(idPeriodo);
    }

    public PeriodoBean obtenerPerPorId(String uniNeg, Integer idPlanEstrategico, Integer idLinea, Integer idObjEstrategico, Integer idObjEstrategicoDet, Integer anio) throws Exception {
        return periodoDAO.obtenerPerPorId(uniNeg, idPlanEstrategico, idLinea, idObjEstrategico, idObjEstrategicoDet, anio);
    }

    public PeriodoBean obtenerPorDet(Integer idObjEstrategicoDet, String uniNeg) throws Exception {
        return periodoDAO.obtenerPorDet(idObjEstrategicoDet, uniNeg);
    }

    public List<PeriodoBean> obtenerPorDetalles(Integer idObjEstrategicoDet, String uniNeg) throws Exception {
        return periodoDAO.obtenerPorDetalles(idObjEstrategicoDet, uniNeg);
    }

    public List<PeriodoBean> obtenerPorPlan(Integer idPlanEstrategico) throws Exception {
        return periodoDAO.obtenerPorPlan(idPlanEstrategico);
    }

    public PeriodoDAO getPeriodoDAO() {
        return periodoDAO;
    }

    public void setPeriodoDAO(PeriodoDAO periodoDAO) {
        this.periodoDAO = periodoDAO;
    }

}
