/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.BecaBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.CronogramaPagoBean;
import pe.marista.sigma.bean.CuentasPorCobrarBean;
import pe.marista.sigma.bean.DocIngresoBean;
import pe.marista.sigma.bean.EstudianteBean;
import pe.marista.sigma.bean.EstudianteBecaBean;
import pe.marista.sigma.bean.MatriculaBean;
import pe.marista.sigma.bean.reporte.EstudianteBecaRepBean;
import pe.marista.sigma.bean.reporte.EstudianteDetalleBecaRepBean;
import pe.marista.sigma.dao.BecaDAO;
import pe.marista.sigma.dao.EstudianteBecaDAO;
import pe.marista.sigma.factory.BeanFactory;

/**
 *
 * @author MS002
 */
public class BecaService {

    private BecaDAO becaDAO;
    private EstudianteBecaDAO estudianteBecaDAO;

    public List<BecaBean> obtenerTodos() throws Exception {
        return becaDAO.obtenerTodos();
    }

    public List<BecaBean> obtenerTodosActivos() throws Exception {
        return becaDAO.obtenerTodosActivos();
    }

    public BecaBean buscarPorId(BecaBean becaBean) throws Exception {
        return becaDAO.buscarPorId(becaBean);
    }

    @Transactional
    public void insertar(BecaBean becaBean) throws Exception {
        becaDAO.insertar(becaBean);
    }

    @Transactional
    public void actualizar(BecaBean becaBean) throws Exception {
        becaDAO.actualizar(becaBean);
    }

    @Transactional
    public void eliminar(BecaBean becaBean) throws Exception {
        becaDAO.eliminar(becaBean);
    }

    @Transactional
    public void cambiarEstado(BecaBean becaBean) throws Exception {
        becaDAO.cambiarEstado(becaBean);
    }

    public List<EstudianteBecaBean> obtenerTodosEstudianteBeca(EstudianteBecaBean estudianteBecaBean) throws Exception {
        return estudianteBecaDAO.obtenerTodosEstudianteBeca(estudianteBecaBean);
    }

    public List<EstudianteBecaBean> obtenerTodosBecaPorEstudiante(EstudianteBecaBean estudianteBecaBean) throws Exception {
        return estudianteBecaDAO.obtenerTodosBecaPorEstudiante(estudianteBecaBean);
    }

    public List<EstudianteBecaBean> obtenerTodosBecaPorEstudianteActivo(EstudianteBecaBean estudianteBecaBean) throws Exception {
        return estudianteBecaDAO.obtenerTodosBecaPorEstudianteActivo(estudianteBecaBean);
    }

    public EstudianteBecaBean buscarPorIdEstudianteBeca(EstudianteBecaBean estudianteBecaBean) throws Exception {
        return estudianteBecaDAO.buscarPorIdEstudianteBeca(estudianteBecaBean);
    }

    public List<EstudianteBecaBean> buscarPorIdEstudianteBecaAnio(EstudianteBecaBean estudianteBecaBean) throws Exception {
        return estudianteBecaDAO.buscarPorIdEstudianteBecaAnio(estudianteBecaBean);
    }

    public List<EstudianteBecaBean> buscarBecadosAnio(EstudianteBecaBean estudianteBecaBean) throws Exception {
        return estudianteBecaDAO.buscarBecadosAnio(estudianteBecaBean);
    }

    @Transactional
    public void insertarEstudianteBeca(EstudianteBecaBean estudianteBecaBean) throws Exception {
        estudianteBecaDAO.insertarEstudianteBeca(estudianteBecaBean);
    }

    @Transactional
    public void insertarEstudianteBecaVer2(EstudianteBecaBean estudianteBecaBean, List<CuentasPorCobrarBean> lista) throws Exception {
        estudianteBecaDAO.insertarEstudianteBeca(estudianteBecaBean);
        DocIngresoService docin = BeanFactory.getDocIngresoService();
        BecaBean beca = new BecaBean();
        beca = becaDAO.buscarPorId(estudianteBecaBean.getBecaBean());
        System.out.println("i " + estudianteBecaBean.getCronogramaPagoBean().getMes());
        System.out.println("f " + estudianteBecaBean.getMesFin());
        Integer inicio = estudianteBecaBean.getCronogramaPagoBean().getMes();
        Integer fin = estudianteBecaBean.getMesFin();
        for (CuentasPorCobrarBean cuenta : lista) {
            if (inicio <= cuenta.getMes()) {
                if (cuenta.getMes() <= fin) {
                    Double dsctoBeca = becaDAO.obtenerDsctoPorId(beca.getIdBeca(), cuenta.getMonto().doubleValue());
                    System.out.println("dscto..." + dsctoBeca);
                    cuenta.setEstudianteBecaBean(estudianteBecaBean);
                    cuenta.setDsctoBeca(BigDecimal.valueOf(dsctoBeca));
                    System.out.println("beca: " + estudianteBecaBean.getBecaBean().getNombre());
                    System.out.println("beca: " + estudianteBecaBean.getBecaBean().getIdBeca());
                    System.out.println("nombre: " + cuenta.getEstudianteBecaBean().getBecaBean().getNombre());
                    System.out.println("nombre2: " + beca.getNombre());
                    DocIngresoBean doc = new DocIngresoBean();
                    if (beca.getNombre().equals(MaristaConstantes.Beca6)) { 
                        if (cuenta.getMes()==inicio) {
                            cuenta.setFechaPago(estudianteBecaBean.getCreaFecha());
                        } else {
                            cuenta.setFechaPago(cuenta.getFechaVenc());
                        } 
//                        cuenta.setMontoPagado(cuenta.getMonto());
                        actualizarCtaCteBecaTotal(cuenta);
                        docin.modificarDetalleDocIngDesBecaTotal(cuenta.getIdCtasXCobrar(), cuenta.getUnidadNegocioBean().getUniNeg(), cuenta.getDsctoBeca());
                        doc.getCuentasPorCobrarBean().getEstudianteBecaBean().setIdEstudianteBeca(estudianteBecaBean.getIdEstudianteBeca());
                        System.out.println("numero = "+cuenta.getMes());
                        System.out.println("numero = "+inicio);
                        if (cuenta.getMes()==inicio) {
                            doc.setFechaPago(estudianteBecaBean.getCreaFecha());
                        } else {
                            doc.setFechaPago(cuenta.getFechaVenc());
                        }
//                        doc.setMontoEfectivoSol(cuenta.getMonto().doubleValue());
                        doc.setIdDocIngreso(cuenta.getDocIngresoBean().getIdDocIngreso());
                        doc.getUnidadNegocioBean().setUniNeg(cuenta.getUnidadNegocioBean().getUniNeg());
                        docin.cambiarDatBasicosDocIngresoBecaTotal(doc);
                    } else if (cuenta.getFechaPago() == null) {
                        actualizarCtaCte(cuenta);
                        docin.modificarDetalleDocIngDes(cuenta.getIdCtasXCobrar(), cuenta.getUnidadNegocioBean().getUniNeg(), cuenta.getDsctoBeca());
                        doc.setIdDocIngreso(cuenta.getDocIngresoBean().getIdDocIngreso());
                        doc.getCuentasPorCobrarBean().getEstudianteBecaBean().setIdEstudianteBeca(estudianteBecaBean.getIdEstudianteBeca());
                        doc.getUnidadNegocioBean().setUniNeg(cuenta.getUnidadNegocioBean().getUniNeg());
                        docin.cambiarDatBasicosDocIngresoBeca(doc);
                    } else {
                        actualizarCtaCtecambioBecaTo(cuenta);
                        docin.modificarDetalleDocIngDesCambioBeca(cuenta.getIdCtasXCobrar(), cuenta.getUnidadNegocioBean().getUniNeg(), cuenta.getDsctoBeca(),cuenta.getDocIngresoBean().getIdDocIngreso());
                        doc.setIdDocIngreso(cuenta.getDocIngresoBean().getIdDocIngreso());
                        doc.getCuentasPorCobrarBean().getEstudianteBecaBean().setIdEstudianteBeca(estudianteBecaBean.getIdEstudianteBeca());
                        doc.getUnidadNegocioBean().setUniNeg(cuenta.getUnidadNegocioBean().getUniNeg());
                        docin.cambiarDatBasicosDocIngresoBecaToCambio(doc);
                    }

                } else {
                    System.out.println("cuenta.getMes() " + cuenta.getMes());
                }
            }
        }
        List<CuentasPorCobrarBean> mesesDespues = new ArrayList<>();
        CuentasPorCobrarService cuentas = BeanFactory.getCuentasPorCobrarService();
        mesesDespues = cuentas.obtenerCuentaPorIdBecaDespues(estudianteBecaBean.getEstudianteBean().getIdEstudiante(), lista.get(0).getUnidadNegocioBean().getUniNeg(), estudianteBecaBean.getAnio(), fin);
        for (CuentasPorCobrarBean cuentasPorCobrarBean : mesesDespues) {
            CuentasPorCobrarBean estCu = new CuentasPorCobrarBean();
            estCu.getUnidadNegocioBean().setUniNeg(cuentasPorCobrarBean.getUnidadNegocioBean().getUniNeg());
            estCu.getEstudianteBean().setIdEstudiante(cuentasPorCobrarBean.getEstudianteBean().getIdEstudiante());
            estCu.setAnio(cuentasPorCobrarBean.getAnio());
            estCu.setMes(fin);
            estudianteBecaDAO.actualizarCtaCteMesDespues(estCu);
            docin.modificarDetalleDocIngDes(cuentasPorCobrarBean.getIdCtasXCobrar(), cuentasPorCobrarBean.getUnidadNegocioBean().getUniNeg(), cuentasPorCobrarBean.getDsctoBeca());
        }
    }

    @Transactional
    public void actualizarEstudianteBeca(EstudianteBecaBean estudianteBecaBean, List<CuentasPorCobrarBean> lista) throws Exception {
        estudianteBecaDAO.actualizarEstudianteBeca(estudianteBecaBean);
        DocIngresoService docin = BeanFactory.getDocIngresoService();
        BecaBean beca = new BecaBean();
        beca = becaDAO.buscarPorId(estudianteBecaBean.getBecaBean());
        System.out.println("i " + estudianteBecaBean.getCronogramaPagoBean().getMes());
        System.out.println("f " + estudianteBecaBean.getMesFin());
        Integer inicio = estudianteBecaBean.getCronogramaPagoBean().getMes();
        Integer fin = estudianteBecaBean.getMesFin();
        for (CuentasPorCobrarBean cuenta : lista) {
            if (inicio <= cuenta.getMes()) {
                if (cuenta.getMes() <= fin) {
                    Double dsctoBeca = becaDAO.obtenerDsctoPorId(beca.getIdBeca(), cuenta.getMonto().doubleValue());
                    System.out.println("dscto..." + dsctoBeca);
                    cuenta.setEstudianteBecaBean(estudianteBecaBean);
                    cuenta.setDsctoBeca(BigDecimal.valueOf(dsctoBeca));
                    System.out.println("beca: " + estudianteBecaBean.getBecaBean().getNombre());
                    System.out.println("beca: " + estudianteBecaBean.getBecaBean().getIdBeca());
                    DocIngresoBean doc = new DocIngresoBean();
                    if (cuenta.getEstudianteBecaBean().getBecaBean().getNombre().equals(MaristaConstantes.Beca6)) {
                        if (cuenta.getMes()==inicio) {
                            cuenta.setFechaPago(estudianteBecaBean.getCreaFecha());
                        } else {
                            cuenta.setFechaPago(cuenta.getFechaVenc());
                        } 
//                        cuenta.setMontoPagado(cuenta.getMonto());
                        actualizarCtaCteBecaTotal(cuenta);
                        docin.modificarDetalleDocIngDesBecaTotal(cuenta.getIdCtasXCobrar(), cuenta.getUnidadNegocioBean().getUniNeg(), cuenta.getDsctoBeca());
                        doc.getCuentasPorCobrarBean().getEstudianteBecaBean().setIdEstudianteBeca(estudianteBecaBean.getIdEstudianteBeca());
                        System.out.println("numero = "+cuenta.getMes());
                        System.out.println("numero = "+inicio);
                        if (cuenta.getMes()==inicio) {
                            doc.setFechaPago(estudianteBecaBean.getCreaFecha());
                        } else {
                            doc.setFechaPago(cuenta.getFechaVenc());
                        }
//                        doc.setMontoEfectivoSol(cuenta.getMonto().doubleValue());
                        doc.setIdDocIngreso(cuenta.getDocIngresoBean().getIdDocIngreso());
                        doc.getUnidadNegocioBean().setUniNeg(cuenta.getUnidadNegocioBean().getUniNeg());
                        docin.cambiarDatBasicosDocIngresoBecaTotal(doc);
                    } else if (cuenta.getFechaPago() == null) {
                        actualizarCtaCte(cuenta);
                        docin.modificarDetalleDocIngDes(cuenta.getIdCtasXCobrar(), cuenta.getUnidadNegocioBean().getUniNeg(), cuenta.getDsctoBeca());
                        doc.setIdDocIngreso(cuenta.getDocIngresoBean().getIdDocIngreso());
                        doc.getCuentasPorCobrarBean().getEstudianteBecaBean().setIdEstudianteBeca(estudianteBecaBean.getIdEstudianteBeca());
                        doc.getUnidadNegocioBean().setUniNeg(cuenta.getUnidadNegocioBean().getUniNeg());
                        docin.cambiarDatBasicosDocIngresoBeca(doc);
                    } else {
                        actualizarCtaCtecambioBecaTo(cuenta);
                        docin.modificarDetalleDocIngDesCambioBeca(cuenta.getIdCtasXCobrar(), cuenta.getUnidadNegocioBean().getUniNeg(), cuenta.getDsctoBeca(),cuenta.getDocIngresoBean().getIdDocIngreso());
                        doc.setIdDocIngreso(cuenta.getDocIngresoBean().getIdDocIngreso());
                        doc.getCuentasPorCobrarBean().getEstudianteBecaBean().setIdEstudianteBeca(estudianteBecaBean.getIdEstudianteBeca());
                        doc.getUnidadNegocioBean().setUniNeg(cuenta.getUnidadNegocioBean().getUniNeg());
                        docin.cambiarDatBasicosDocIngresoBecaToCambio(doc);
                    }

                } else {
                    System.out.println("cuenta.getMes() " + cuenta.getMes());
                }
            }
        }
        List<CuentasPorCobrarBean> mesesDespues = new ArrayList<>();
        CuentasPorCobrarService cuentas = BeanFactory.getCuentasPorCobrarService();
        mesesDespues = cuentas.obtenerCuentaPorIdBecaDespues(estudianteBecaBean.getEstudianteBean().getIdEstudiante(), estudianteBecaBean.getEstudianteBean().getUnidadNegocioBean().getUniNeg(), estudianteBecaBean.getAnio(), fin);
        for (CuentasPorCobrarBean cuentasPorCobrarBean : mesesDespues) {
            CuentasPorCobrarBean estCu = new CuentasPorCobrarBean();
            estCu.getUnidadNegocioBean().setUniNeg(cuentasPorCobrarBean.getUnidadNegocioBean().getUniNeg());
            estCu.getEstudianteBean().setIdEstudiante(cuentasPorCobrarBean.getEstudianteBean().getIdEstudiante());
            estCu.setAnio(cuentasPorCobrarBean.getAnio());
            estCu.setMes(fin);
            estudianteBecaDAO.actualizarCtaCteMesDespues(estCu);
            docin.modificarDetalleDocIngDes(cuentasPorCobrarBean.getIdCtasXCobrar(), cuentasPorCobrarBean.getUnidadNegocioBean().getUniNeg(), cuentasPorCobrarBean.getDsctoBeca());
        }
    }

    @Transactional
    public void actualizarEstudianteBecaEstado(EstudianteBecaBean EstudianteBecaBean) throws Exception {
        estudianteBecaDAO.actualizarEstudianteBecaEstado(EstudianteBecaBean);
    }

    @Transactional
    public void actualizarEstudianteBecaEstadoOff(EstudianteBecaBean EstudianteBecaBean) throws Exception {
        estudianteBecaDAO.actualizarEstudianteBecaEstadoOff(EstudianteBecaBean);
    }

    @Transactional
    public void eliminarEstudianteBeca(EstudianteBecaBean estudianteBecaBean) throws Exception {
        estudianteBecaDAO.eliminarEstudianteBeca(estudianteBecaBean);
    }

    public List<CronogramaPagoBean> obtenerCronograma() throws Exception {
        return estudianteBecaDAO.obtenerCronograma();
    }

    public List<EstudianteBean> obtenerMatriculadosPorPeriodo(MatriculaBean matriculaBean) throws Exception {
        return estudianteBecaDAO.obtenerMatriculadosPorPeriodo(matriculaBean);
    }

    public List<EstudianteBean> obtenerFiltroEstudianteMatriculado(MatriculaBean matriculaBean) throws Exception {
        return estudianteBecaDAO.obtenerFiltroEstudianteMatriculado(matriculaBean);
    }

    @Transactional
    public void actualizarCtaCte(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception {
        estudianteBecaDAO.actualizarCtaCte(cuentasPorCobrarBean);
    }

    public Double obtenerDsctoPorId(Integer id, Double monto) throws Exception {
        return becaDAO.obtenerDsctoPorId(id, monto);
    }

    public BecaDAO getBecaDAO() {
        return becaDAO;
    }

    public void setBecaDAO(BecaDAO becaDAO) {
        this.becaDAO = becaDAO;
    }

    public EstudianteBecaDAO getEstudianteBecaDAO() {
        return estudianteBecaDAO;
    }

    public void setEstudianteBecaDAO(EstudianteBecaDAO estudianteBecaDAO) {
        this.estudianteBecaDAO = estudianteBecaDAO;
    }

    public List<EstudianteBecaRepBean> obetenerTitulo(String uniNeg, Integer mes, Integer anio) throws Exception {
        return estudianteBecaDAO.obetenerTitulo(uniNeg, mes, anio);
    }

    public List<EstudianteBecaRepBean> obetenerNombreBeca(String uniNeg, Integer mes, Integer anio) throws Exception {
        return estudianteBecaDAO.obetenerNombreBeca(uniNeg, mes, anio);
    }

    public List<EstudianteBecaRepBean> obetenerNombreAlumno(String uniNeg, Integer mes, Integer anio, String nombreBeca) throws Exception {
        return estudianteBecaDAO.obetenerNombreAlumno(uniNeg, mes, anio, nombreBeca);
    }

    @Transactional
    public void actualizarCtaCtecambioBecaTo(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception {
        estudianteBecaDAO.actualizarCtaCtecambioBecaTo(cuentasPorCobrarBean);
    }

    @Transactional
    public void actualizarCtaCteBecaTotal(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception {
        estudianteBecaDAO.actualizarCtaCteBecaTotal(cuentasPorCobrarBean);
    }

}
