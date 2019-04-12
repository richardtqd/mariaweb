/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.JefeUniOrgBean;
import pe.marista.sigma.dao.JefeUniOrgDAO;

/**
 *
 * @author MS001
 */
public class JefeUniOrgService {

    private JefeUniOrgDAO jefeUniOrgDAO;

    public List<JefeUniOrgBean> obtenerJefeUniOrgPorUniNegPorEstado(Boolean status, String uniNeg) throws Exception {
        return jefeUniOrgDAO.obtenerJefeUniOrgPorUniNegPorEstado(status, uniNeg);
    }

    public List<JefeUniOrgBean> obtenerJefeUniOrgPorUniOrg(Integer idUniOrg, String uniNeg) throws Exception {
        return jefeUniOrgDAO.obtenerJefeUniOrgPorUniOrg(idUniOrg, uniNeg);
    }

    public JefeUniOrgBean obtenerJefeUniOrgPorId(JefeUniOrgBean jefeUniOrgBean) throws Exception {
        return jefeUniOrgDAO.obtenerJefeUniOrgPorId(jefeUniOrgBean);
    }

    public JefeUniOrgBean obtenerIdUniOrgPorNombre(String uniOrg, String uniNeg) throws Exception {
        return jefeUniOrgDAO.obtenerIdUniOrgPorNombre(uniOrg, uniNeg);
    }

    //Logica de Negocio
    @Transactional
    public void insertarJefeUniOrg(JefeUniOrgBean jefeUniOrgBean, List<JefeUniOrgBean> listaJefeUniOrgPorUniOrgBean) throws Exception {
        jefeUniOrgDAO.insertarJefeUniOrg(jefeUniOrgBean);
        if (jefeUniOrgBean.getStatus().equals(true)) {
            for (JefeUniOrgBean lista : listaJefeUniOrgPorUniOrgBean) {
                jefeUniOrgDAO.cambiarEstadoAllInactivo(lista.getIdJefeUniOrg(), jefeUniOrgBean.getUnidadNegocioBean().getUniNeg(), jefeUniOrgBean.getCreaPor());
            }
        }
    }

    @Transactional
    public void modificarJefeUniOrg(JefeUniOrgBean jefeUniOrgBean) throws Exception {
        jefeUniOrgDAO.modificarJefeUniOrg(jefeUniOrgBean);
    }

    @Transactional
    public void eliminarJefeUniOrg(JefeUniOrgBean jefeUniOrgBean) throws Exception {
        jefeUniOrgDAO.eliminarJefeUniOrg(jefeUniOrgBean);
    }

    @Transactional
    public void cambiarEstado(JefeUniOrgBean jefeUniOrgBean) throws Exception {
        jefeUniOrgDAO.cambiarEstado(jefeUniOrgBean);
    }

    @Transactional
    public void cambiarEstadoAllInactivo(Integer idJeFeUniOrg, String uniNeg, String modiPor) throws Exception {
        jefeUniOrgDAO.cambiarEstadoAllInactivo(idJeFeUniOrg, uniNeg, modiPor);
    }

    //metodos getter and setter
    public JefeUniOrgDAO getJefeUniOrgDAO() {
        return jefeUniOrgDAO;
    }

    public void setJefeUniOrgDAO(JefeUniOrgDAO jefeUniOrgDAO) {
        this.jefeUniOrgDAO = jefeUniOrgDAO;
    }

}
