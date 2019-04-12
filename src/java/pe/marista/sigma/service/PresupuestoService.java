/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.DetActividadBean;
import pe.marista.sigma.bean.PresupuestoBean;
import pe.marista.sigma.bean.PresupuestoUniOrgBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.CuentaRepBean;
import pe.marista.sigma.dao.PresupuestoDAO;
import pe.marista.sigma.dao.PresupuestoUniOrgDAO;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.util.MaristaUtils;

/**
 *
 * @author MS002
 */
public class PresupuestoService {

    private PresupuestoDAO presupuestoDAO;
    private PresupuestoUniOrgDAO presupuestoUniOrgDAO;
    private Integer anio;

    public List<PresupuestoBean> obtenerPresupesto(String uniNeg) throws Exception {
        return presupuestoDAO.obtenerPresupesto(uniNeg);
    }

    public List<PresupuestoBean> obtenerPresupuestoFiltro(PresupuestoBean presupuestoBean) throws Exception {
        return presupuestoDAO.obtenerPresupuestoFiltro(presupuestoBean);
    }

    public Integer obtenerPresExec(String uniNeg, Integer cuenta) throws Exception {
        return presupuestoDAO.obtenerPresExec(uniNeg, cuenta);
    }

    @Transactional
    public void insertarPresupesto(PresupuestoBean presupuestoBean) throws Exception {
        presupuestoDAO.insertarPresupesto(presupuestoBean);
    }

    @Transactional
    public void modificarPresupesto(PresupuestoBean presupuestoBean) throws Exception {
        presupuestoDAO.modificarPresupesto(presupuestoBean);
    }

    @Transactional
    public void modificarDatosPresupuesto(PresupuestoBean presupuestoBean) throws Exception {
        presupuestoDAO.modificarDatosPresupuesto(presupuestoBean);
    }

    @Transactional
    public void modificarPresupuestoExec(PresupuestoBean presupuestoBean) throws Exception {
        presupuestoDAO.modificarPresupuestoExec(presupuestoBean);
    }

    @Transactional
    public void insertarPresupestoPlan(List<DetActividadBean> listaDetActividadBean, Integer cuentaPres, Integer anio, Integer idUniOrg) throws Exception {
        UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        Integer uniOrg = idUniOrg;
//        Integer a�o = anio;
        setAnio(anio);
        List<PresupuestoBean> listaPresupuesto = new ArrayList<>();
        PresupuestoBean presupuestoBean = new PresupuestoBean();
        PresupuestoBean presupuesto = new PresupuestoBean();

        Integer montoProg = 0;
        Integer cuentaPresUo = cuentaPres;
        //Obteniemdo Presupuesto
        DetActividadService detActividadService = BeanFactory.getDetActividadService();
        listaDetActividadBean = detActividadService.obtenerCuentaPorId(cuentaPres);
        listaPresupuesto = presupuestoDAO.obtenerPresupuestoCuenta(cuentaPres, beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
        presupuesto = presupuestoDAO.obtenerPresPorId(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), cuentaPres, anio);
        for (DetActividadBean de : listaDetActividadBean) {
            montoProg = de.getImporte().intValue() + montoProg;
        }
        if (listaPresupuesto.isEmpty()) {
            BigDecimal monto = new BigDecimal(montoProg);
            for (DetActividadBean deta : listaDetActividadBean) {
                presupuestoBean.setAnio(deta.getAnio());
                presupuestoBean.setCuenta(cuentaPres);
                presupuestoBean.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                presupuestoBean.setCreaPor(beanUsuarioSesion.getUsuario());
                presupuestoBean.setPresupuestoProg(monto);
                presupuestoDAO.insertarPresupestoPlan(presupuestoBean);
                break;
            }
        } else {
            BigDecimal montos = new BigDecimal(montoProg);
            //presupuesto.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            presupuesto.setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            presupuesto.setPresupuestoProg(montos);
            presupuesto.setModiPor(beanUsuarioSesion.getUsuario());
            presupuestoDAO.modificarPresupestoPlan(presupuesto);

        }
        listaPresupuesto = presupuestoDAO.obtenerPresupuestoCuenta(cuentaPres, beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
        insertarPresupuestoUo(presupuesto, listaPresupuesto, cuentaPresUo, uniOrg, anio);
    }

    @Transactional
    public void insertarPresupuestoUo(PresupuestoBean presupuesto, List<PresupuestoBean> listaPresupuesto, Integer cuentaPresUo, Integer uniOrg, Integer anio) throws Exception {
        UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        //Cambios para PResUo
        Integer monto = 0;
        List<PresupuestoUniOrgBean> listaPresupuestoUniOrgBean = new ArrayList<>();
        PresupuestoUniOrgBean presupuestoUniOrgBean = new PresupuestoUniOrgBean();
        PresupuestoUniOrgBean presupuestoUo = new PresupuestoUniOrgBean();

        //Obteniendo Listas
        listaPresupuestoUniOrgBean = presupuestoUniOrgDAO.obtenerListaCuentaUo(cuentaPresUo, beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), uniOrg, anio);
        presupuestoUo = presupuestoUniOrgDAO.obtenerListaCuentaUorg(cuentaPresUo, beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), uniOrg, anio);

        //Ejecutando Metodos
        for (PresupuestoBean presu : listaPresupuesto) {
            monto = presu.getPresupuestoProg().intValue() + monto;
        }
        if (listaPresupuestoUniOrgBean.isEmpty()) {
            for (PresupuestoBean pres : listaPresupuesto) {
                presupuestoUniOrgBean.setPresupuestoProg(pres.getPresupuestoProg());
                presupuestoUniOrgBean.setCuenta(pres.getPlanContableBean().getCuenta());
                presupuestoUniOrgBean.getUnidadOrganicaBean().setIdUniOrg(uniOrg);
                presupuestoUniOrgBean.setIdPresupuesto(pres.getIdPresupuesto());
                presupuestoUniOrgBean.setAnio(pres.getAnio());
                presupuestoUniOrgBean.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                presupuestoUniOrgBean.setCreaPor(beanUsuarioSesion.getUsuario());
                presupuestoUniOrgDAO.insertarPresupuestoProg(presupuestoUniOrgBean);
                break;
            }
        } else {
            if (presupuestoUo != null) {
                BigDecimal count = new BigDecimal(monto);
                presupuestoUo.setPresupuestoProg(count);
                presupuestoUo.setModiPor(beanUsuarioSesion.getUsuario());
                presupuestoUniOrgDAO.modificarPresupuestoProg(presupuestoUo);
            }
        }
    }

    @Transactional
    public void modificarPresupuestoProg(List<DetActividadBean> listaDeta) throws Exception {
        UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        //Obteniendo DetActividad
        Integer cuenta = 0;
        Integer monto = 0;
        Integer montoPres = 0;
        for (int i = 0; i < listaDeta.size(); i++) {
            cuenta = listaDeta.get(i).getPlanContableBean().getCuenta();
            monto = listaDeta.get(i).getImporte().intValue();
        }

        //Cargando Lista Presupuesto
        List<PresupuestoBean> listaPresupuesto = new ArrayList<>();
        listaPresupuesto = presupuestoDAO.obtenerPresupuestoCuenta(cuenta, beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
        for (int j = 0; j < listaPresupuesto.size(); j++) {
            montoPres = listaPresupuesto.get(j).getPresupuestoProg().intValue();
        }

        Integer m = montoPres - monto;
        if (listaPresupuesto.size() > 0) {
            BigDecimal programado = new BigDecimal(m);
            for (PresupuestoBean presupuesto : listaPresupuesto) {
                if (m != 0) {
                    presupuesto.setPresupuestoProg(programado);
                    presupuesto.setModiPor(beanUsuarioSesion.getUsuario());
                    presupuestoDAO.modificarPresupestoPlan(presupuesto);
                    break;
                } else {
                    if (m.equals(0)) {
                        presupuestoDAO.eliminarPresupestoProg(presupuesto);
                        break;
                    }
                }
            }
            modificarPresUniOrg(cuenta, m);
        }
    }

    @Transactional
    public void modificarPresUniOrg(Integer cuenta, Integer m) throws Exception {
        UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        List<PresupuestoUniOrgBean> listaPresupuestoUniOrgBean = new ArrayList<>();
        listaPresupuestoUniOrgBean = presupuestoUniOrgDAO.obtenerPorCuenta(cuenta, beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        if (listaPresupuestoUniOrgBean.size() > 0) {
            BigDecimal programado = new BigDecimal(m);
            for (PresupuestoUniOrgBean presupuestoUniOrg : listaPresupuestoUniOrgBean) {
                if (m != 0) {
                    presupuestoUniOrg.setPresupuestoProg(programado);
                    presupuestoUniOrg.setModiPor(beanUsuarioSesion.getUsuario());
                    presupuestoUniOrgDAO.modificarPresupuestoProg(presupuestoUniOrg);
                    break;
                } else {
                    if (m.equals(0)) {
                        presupuestoUniOrgDAO.eliminarPresupuestoUniOrg(presupuestoUniOrg);
                        break;
                    }
                }
            }
        }
    }

    public PresupuestoBean obtenerPorId(Integer idPresupuesto) throws Exception {
        return presupuestoDAO.obtenerPorId(idPresupuesto);
    }

    public List<PresupuestoBean> obtenerListaPresID(Integer idPresupuesto) throws Exception {
        return presupuestoDAO.obtenerListaPresID(idPresupuesto);
    }

    public List<PresupuestoBean> obtenerPresupuestoCuenta(Integer cuenta, String uniNeg, Integer anio) throws Exception {
        return presupuestoDAO.obtenerPresupuestoCuenta(cuenta, uniNeg, anio);
    }

    @Transactional
    public void eliminarPresupesto(Integer idPresupuesto) throws Exception {
        presupuestoDAO.eliminarPresupesto(idPresupuesto);
    }

    @Transactional
    public void eliminarPresupestoProg(PresupuestoBean presupuestoBean) throws Exception {
        presupuestoDAO.eliminarPresupestoProg(presupuestoBean);
    }

    @Transactional
    public Object execProPres(String uniNeg) throws Exception {
        return presupuestoDAO.execProPres(uniNeg);
    }

    //REPORTE ESTRATEGICO
    public List<CuentaRepBean> obtenerListaCuentaFiltro(CuentaRepBean cuentaRepBean) throws Exception {
        return presupuestoDAO.obtenerListaCuentaFiltro(cuentaRepBean);
    }

    public List<CuentaRepBean> obtenerListaCuentaFiltroRep(CuentaRepBean cuentaRepBean) throws Exception {
        return presupuestoDAO.obtenerListaCuentaFiltroRep(cuentaRepBean);
    }

    @Transactional
    public Object execPresupuesto(PresupuestoBean presupuestoBean) throws Exception {
        return presupuestoDAO.execPresupuesto(presupuestoBean);
    }

    public List<PresupuestoBean> filtrarPresupuesto(PresupuestoBean presupuestoBean) throws Exception {
        return presupuestoDAO.filtrarPresupuesto(presupuestoBean);
    }

    public void eliminarPresupestoNuevo(PresupuestoBean presupuestoBean) throws Exception {
        presupuestoDAO.eliminarPresupestoNuevo(presupuestoBean);
    }

    public PresupuestoBean obtenerPorCuenta(PresupuestoBean presupuestoBean) throws Exception {
        return presupuestoDAO.obtenerPorCuenta(presupuestoBean);
    }

    public PresupuestoDAO getPresupuestoDAO() {
        return presupuestoDAO;
    }

    public void setPresupuestoDAO(PresupuestoDAO presupuestoDAO) {
        this.presupuestoDAO = presupuestoDAO;
    }

    public PresupuestoUniOrgDAO getPresupuestoUniOrgDAO() {
        return presupuestoUniOrgDAO;
    }

    public void setPresupuestoUniOrgDAO(PresupuestoUniOrgDAO presupuestoUniOrgDAO) {
        this.presupuestoUniOrgDAO = presupuestoUniOrgDAO;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

}
