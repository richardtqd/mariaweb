/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.util.List;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.DetProgramacionDsctoBean;
import pe.marista.sigma.bean.ProgramacionBean;
import pe.marista.sigma.bean.ProgramacionDsctoBean;
import pe.marista.sigma.dao.ProgramacionDsctoDAO;
import pe.marista.sigma.factory.BeanFactory;

/**
 *
 * @author MS001
 */
public class ProgramacionDsctoService {

    private ProgramacionDsctoDAO programacionDsctoDAO;

    public List<ProgramacionDsctoBean> obtenerTodosPorUniNeg(String uniNeg) throws Exception {
        return programacionDsctoDAO.obtenerTodosPorUniNeg(uniNeg);
    }

    public ProgramacionDsctoBean obtenerProgramacionDsctoPorId(Integer idProgramacionDscto, String uniNeg) throws Exception {
        return programacionDsctoDAO.obtenerProgramacionDsctoPorId(idProgramacionDscto, uniNeg);
    }

    public void insertarProgramacionDscto(ProgramacionDsctoBean ProgramacionDsctoBean) throws Exception {
        programacionDsctoDAO.insertarProgramacionDscto(ProgramacionDsctoBean);
    }

    public void insertarProgramacionDsctoVer2(ProgramacionDsctoBean programacionDsctoBean, List<ProgramacionBean> listaProgramacion) throws Exception {
        programacionDsctoDAO.insertarProgramacionDscto(programacionDsctoBean);
        System.out.println("pro:" + programacionDsctoBean.getIdProgramacionDscto());
        DetProgramacionDsctoBean detpro = new DetProgramacionDsctoBean();
        detpro.getProgramacionDsctoBean().setIdProgramacionDscto(programacionDsctoBean.getIdProgramacionDscto());
        detpro.getUnidadNegocioBean().setUniNeg(programacionDsctoBean.getUnidadNegocioBean().getUniNeg());
        detpro.getTipoValorBean().setIdCodigo(programacionDsctoBean.getTipoValorBean().getIdCodigo());
        detpro.setValor(programacionDsctoBean.getValorUnitario());
        detpro.setStatus(programacionDsctoBean.getStatus());
        detpro.setCreaPor(programacionDsctoBean.getCreaPor());
        insertarProDsctoVer2(detpro, listaProgramacion);

    }

    public void modificarProgramacionDscto(ProgramacionDsctoBean ProgramacionDsctoBean) throws Exception {
        programacionDsctoDAO.modificarProgramacionDscto(ProgramacionDsctoBean);
    }

    public void eliminarProgramacionDscto(Integer idProgramacionDscto, String uniNeg) throws Exception {
        programacionDsctoDAO.eliminarProgramacionDscto(idProgramacionDscto, uniNeg);
    }

    public ProgramacionDsctoBean obtenerProgDsctoPorProgramacionesFor(String uniNeg, List<Integer> idProgramacion, Integer cant) throws Exception {
        return programacionDsctoDAO.obtenerProgDsctoPorProgramacionesFor(uniNeg, idProgramacion, cant);
    }

    public ProgramacionDsctoBean obtenerProgDsctoPorProgramacionesForVer2(String uniNeg, List<Integer> idProgramacion, Integer cant, Double montoTotal, Boolean flgEst) throws Exception {
        Integer flg = 0;
        if (flgEst.equals(Boolean.TRUE)) {
            flg = 1;
        }
        return programacionDsctoDAO.obtenerProgDsctoPorProgramacionesForVer2(uniNeg, idProgramacion, cant, montoTotal, flg);
    }

    public Integer validarProg1En2(String uniNeg, List<Integer> idProgramacion, Integer idProgramacionDscto, Integer cant) throws Exception {
        return programacionDsctoDAO.validarProg1En2(uniNeg, idProgramacion, idProgramacionDscto, cant);
    }

    public List<ProgramacionDsctoBean> obtenerProgDsctoPorProgramacionesCantidadFor(String uniNeg, List<Integer> idProgramacion, Integer cant) throws Exception {
        return programacionDsctoDAO.obtenerProgDsctoPorProgramacionesCantidadFor(uniNeg, idProgramacion, cant);
    }

    //DETALLE 
    public List<DetProgramacionDsctoBean> obtenerDetallePorProgramacionDscto(List<Integer> idProgramacionDscto, String uniNeg) throws Exception {
        return programacionDsctoDAO.obtenerDetallePorProgramacionDscto(idProgramacionDscto, uniNeg);
    }

    public void insertarDetProgramacionDscto(DetProgramacionDsctoBean detProgramacionDsctoBean) throws Exception {
        programacionDsctoDAO.insertarDetProgramacionDscto(detProgramacionDsctoBean);
    }

    public void eliminarDetallePorProgramacionDscto(Integer idProgramacionDscto, String uniNeg) throws Exception {
        programacionDsctoDAO.eliminarDetallePorProgramacionDscto(idProgramacionDscto, uniNeg);
    }

    public void modificarDetProgramacionDscto(DetProgramacionDsctoBean detProgramacionDsctoBean) throws Exception {
        programacionDsctoDAO.modificarDetProgramacionDscto(detProgramacionDsctoBean);
    }

    public Integer validarProgEnDetDscto(String uniNeg, Integer idProgramacion, Integer idProgramacionDscto) throws Exception {
        return programacionDsctoDAO.validarProgEnDetDscto(uniNeg, idProgramacion, idProgramacionDscto);
    }

    //metodos set and get
    public ProgramacionDsctoDAO getProgramacionDsctoDAO() {
        return programacionDsctoDAO;
    }

    public void setProgramacionDsctoDAO(ProgramacionDsctoDAO programacionDsctoDAO) {
        this.programacionDsctoDAO = programacionDsctoDAO;
    }

    public ProgramacionDsctoBean obtenerProgDsctoPorProgramacionesForSan(String uniNeg, List<Integer> idProgramacion, Integer cant, Boolean flgEst, Integer mes) throws Exception {
        return programacionDsctoDAO.obtenerProgDsctoPorProgramacionesForSan(uniNeg, idProgramacion, cant, flgEst, mes);
    }

    @Transactional
    public void insertarProDsctoVer2(DetProgramacionDsctoBean detProgramacionDsctoBean, List<ProgramacionBean> listaProgramacion) throws Exception {
        ProgramacionService programacionService = BeanFactory.getProgramacionService();
        List<ProgramacionBean> lista = programacionService.obtenerTodos();
        for (ProgramacionBean objecto : listaProgramacion) {
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getIdProgramacion().equals(objecto.getIdProgramacion())) {
                    detProgramacionDsctoBean.setProgramacionBean(lista.get(i));
                    programacionDsctoDAO.insertarDetProgramacionDscto(detProgramacionDsctoBean);
                }
            }
        }

    }

    @Transactional
    public void modificarProDsctoVer2(DetProgramacionDsctoBean detProgramacionDsctoBean, List<ProgramacionBean> listaProgramacion) throws Exception {
        ProgramacionService programacionService = BeanFactory.getProgramacionService();
        String uniNeg = detProgramacionDsctoBean.getUnidadNegocioBean().getUniNeg();
        Integer idProgramacionDscto = detProgramacionDsctoBean.getProgramacionDsctoBean().getIdProgramacionDscto();
        eliminarDetallePorProgramacionDscto(idProgramacionDscto, uniNeg);
        List<ProgramacionBean> lista = programacionService.obtenerTodos();
        for (ProgramacionBean objecto : listaProgramacion) {
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getIdProgramacion().equals(objecto.getIdProgramacion())) {
                    detProgramacionDsctoBean.setProgramacionBean(lista.get(i));
                    programacionDsctoDAO.insertarDetProgramacionDscto(detProgramacionDsctoBean);
                }
            }
        }

    }

    public List<DetProgramacionDsctoBean> obtenerTodosPorUniNegDetalle(String uniNeg) throws Exception {
        return programacionDsctoDAO.obtenerTodosPorUniNegDetalle(uniNeg);
    }

}
