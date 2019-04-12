/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CuentasPorCobrarBean;
import pe.marista.sigma.bean.ProcesoBancoBean;
import pe.marista.sigma.bean.ProcesoEnvioBean;
import pe.marista.sigma.bean.ProcesoRecuperacionBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.ConciliaRepBean;
import pe.marista.sigma.bean.reporte.ProcesosBancosRepBean;
import pe.marista.sigma.bean.reporte.ReporteDeudasRepBean;
import pe.marista.sigma.dao.ProcesoBancoDAO;
import pe.marista.sigma.dao.ProcesoEnvioDAO;
import pe.marista.sigma.dao.ProcesoRecuperacionDAO;
import pe.marista.sigma.util.MaristaUtils;

/**
 *
 * @author MS-005
 */
public class ProcesoBancoService {

    private ProcesoBancoDAO procesoBancoDAO;
    private ProcesoEnvioDAO procesoEnvioDAO;
    private ProcesoRecuperacionDAO procesoRecuperacionDAO;

    public List<ProcesoBancoBean> obtenerProcesoBanco() throws Exception {
        return procesoBancoDAO.obtenerProcesoBanco();
    }
    
    @Transactional
    public void insertarProcesoBanco(ProcesoBancoBean procesoBancoBean) throws Exception {
        procesoBancoDAO.insertarProcesoBanco(procesoBancoBean);
    }
    
    @Transactional
    public void modificarProcesoBanco(ProcesoBancoBean procesoBancoBean) throws Exception {
        procesoBancoDAO.modificarProcesoBanco(procesoBancoBean);
    }
        
    @Transactional
    public void modificarErroresProc(ProcesoBancoBean procesoBancoBean) throws Exception {
        procesoBancoDAO.modificarErroresProc(procesoBancoBean);
    }

//    public ProcesoBancoBean eliminarProcesoBanco(Integer idProcesoBanco) throws Exception {
    public void eliminarProcesoBanco(Integer idProcesoBanco, List<ProcesoRecuperacionBean> listaProRec, List<ProcesoEnvioBean> listaProcesoEnvioFiltroBean, ProcesoBancoBean procesoBancoBean) throws Exception {
        UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        for (ProcesoEnvioBean envio : listaProcesoEnvioFiltroBean) {
            if (envio.getProcesoBancoBean().getIdProcesoBanco().equals(procesoBancoBean.getIdProcesoBanco())) {
                procesoEnvioDAO.eliminarProcesoPorBanco(envio.getProcesoBancoBean().getIdProcesoBanco());
                procesoBancoDAO.eliminarProcesoBanco(procesoBancoBean.getIdProcesoBanco());
            }
            break;
        }
        for (ProcesoRecuperacionBean recup : listaProRec) {
            if (recup.getProcesoBancoBean().getIdProcesoBanco().equals(procesoBancoBean.getIdProcesoBanco())) {
                procesoRecuperacionDAO.elimnarRecuperacionPorBanco(recup.getProcesoBancoBean().getIdProcesoBanco());
                procesoBancoDAO.eliminarProcesoBanco(procesoBancoBean.getIdProcesoBanco());
            }
            break;
        }
    }

    public List<ProcesoBancoBean> obtenerPorId(Integer idProcesoBanco) throws Exception {
        return procesoBancoDAO.obtenerPorId(idProcesoBanco);
    }

    public List<ProcesoBancoBean> obtenerPorIdBanco(Integer idProcesoBanco, String uniNeg) throws Exception {
        return procesoBancoDAO.obtenerPorIdBanco(idProcesoBanco, uniNeg);
    }

    public ProcesoBancoBean obtenerProcesoBancoId(Integer idProcesoBanco, String uniNeg) throws Exception {
        return procesoBancoDAO.obtenerProcesoBancoId(idProcesoBanco, uniNeg);
    }

    public ProcesoBancoBean obtenerProcesoBancoIdVer2(Integer idProcesoBanco, String uniNeg) throws Exception {
        return procesoBancoDAO.obtenerProcesoBancoIdVer2(idProcesoBanco, uniNeg);
    }

    public Integer obtenerNombreDuplicado(String nombre, String uniNeg, Integer anio) throws Exception {
        return procesoBancoDAO.obtenerNombreDuplicado(nombre, uniNeg, anio);
    }

    public Integer verificarEstructuraProcesoBanco(String uniNeg, Integer flgProceso) throws Exception {
        return procesoBancoDAO.verificarEstructuraProcesoBanco(uniNeg, flgProceso);
    }

    public ProcesoBancoBean obterProcBancoPorId(Integer idProcesoBanco) throws Exception {
        return procesoBancoDAO.obterProcBancoPorId(idProcesoBanco);
    }

    public List<ProcesoBancoBean> obtenerPorUniNeg(String uniNeg) throws Exception {
        return procesoBancoDAO.obtenerPorUniNeg(uniNeg);
    }

    public List<ProcesoBancoBean> filtrarProceso(ProcesoBancoBean procesoBancoBean) throws Exception {
        return procesoBancoDAO.filtrarProceso(procesoBancoBean);
    }

    public List<ProcesoBancoBean> filtrarProcesoVer2(ProcesoBancoBean procesoBancoBean) throws Exception {
        return procesoBancoDAO.filtrarProcesoVer2(procesoBancoBean);
    }

    public List<ProcesoBancoBean> obtenerPorTipo(String nombre, String uniNeg) throws Exception {
        return procesoBancoDAO.obtenerPorTipo(nombre, uniNeg);
    }

    public List<ProcesoBancoBean> obtenerUltimaLista(Integer idProcesoBanco) throws Exception {
        return procesoBancoDAO.obtenerUltimaLista(idProcesoBanco);
    }

    public Integer obtenerMaxIdProcesoBanco(String uniNeg) throws Exception {
        return procesoBancoDAO.obtenerMaxIdProcesoBanco(uniNeg);
    }

    public String obtenerFechaActual() throws Exception {
        return procesoBancoDAO.obtenerFechaActual();
    }

    public Integer obtenerFecha(Integer var) throws Exception {
        return procesoBancoDAO.obtenerFecha(var);
    }

    public Integer obtenerIdProcesoBancoMax(String uniNeg, Integer flgProceso) throws Exception {
        return procesoBancoDAO.obtenerIdProcesoBancoMax(uniNeg, flgProceso);
    }

    public List<ProcesoBancoBean> obtenerTipoProceso() throws Exception {
        Map<Integer, Object> parms = new HashMap<>();
        parms.put(1, MaristaConstantes.FLG_PROCENV);
        parms.put(0, MaristaConstantes.FLG_PROCREC);
        return procesoBancoDAO.obtenerTipoProceso(parms);
    }

    public List<ProcesosBancosRepBean> obtenerReporteBanco(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception {
        List<ProcesosBancosRepBean> listaReporte = new ArrayList<>();
        Map<String, Object> objBanco = new HashMap<>();
        SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
        System.out.println("fecha1 " + dt.format(cuentasPorCobrarBean.getFechaInicio()));
        System.out.println("fecha2 " + dt.format(cuentasPorCobrarBean.getFechaFin()));

        objBanco.put("uniNeg", cuentasPorCobrarBean.getUnidadNegocioBean().getUniNeg());
        objBanco.put("idCodigo", cuentasPorCobrarBean.getIdTipoStatusCtaCte().getIdCodigo());
        objBanco.put("fecIni", dt.format(cuentasPorCobrarBean.getFechaInicio()));
        objBanco.put("fecFin", dt.format(cuentasPorCobrarBean.getFechaFin()));
        objBanco.put("idEstudiante", cuentasPorCobrarBean.getEstudianteBean().getIdEstudiante());
        objBanco.put("codigo", cuentasPorCobrarBean.getEstudianteBean().getCodigo());
        objBanco.put("nombres", cuentasPorCobrarBean.getEstudianteBean().getPersonaBean().getNombreCompleto());
        listaReporte = procesoBancoDAO.obtenerReporteProcesosBancos(objBanco);
        return listaReporte;
    }

    public void eliminarProcesoBancoMas(String uniNeg, Integer idProcesoBanco) throws Exception {
        procesoBancoDAO.eliminarProcesoBancoMas(uniNeg, idProcesoBanco);
    }

    public List<ReporteDeudasRepBean> obtenerReporteDeuda(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception {
        return procesoBancoDAO.obtenerReporteDeuda(cuentasPorCobrarBean);
    }

    public List<ConciliaRepBean> obtenerBancoConcilia(ConciliaRepBean conciliaRepBean) throws Exception {
        return procesoBancoDAO.obtenerBancoConcilia(conciliaRepBean);
    }

    public String obtenerFechaProceso(ProcesoBancoBean procesoBancoBean) throws Exception {
        return procesoBancoDAO.obtenerFechaProceso(procesoBancoBean);
    }

    public String obtenerFechaProcesoBcoSigma(ProcesoBancoBean procesoBancoBean) throws Exception {
        return procesoBancoDAO.obtenerFechaProcesoBcoSigma(procesoBancoBean);
    }

    public String obtenerNombreProceso(ProcesoBancoBean procesoBancoBean) throws Exception {
        return procesoBancoDAO.obtenerNombreProceso(procesoBancoBean);
    }

    public Integer obtenerNumeroRegistros(ProcesoBancoBean procesoBancoBean) throws Exception {
        return procesoBancoDAO.obtenerNumeroRegistros(procesoBancoBean);
    }

    @Transactional
    public void modificarBancoCuenta(String uniNeg, Integer idProcesoBanco, Integer flgProceso) throws Exception {
        procesoBancoDAO.modificarBancoCuenta(uniNeg, idProcesoBanco, flgProceso);
    }

    @Transactional
    public void actualizarProcesoBanco(ProcesoBancoBean procesoBancoBean) throws Exception {
        procesoBancoDAO.actualizarProcesoBanco(procesoBancoBean);
    }
    
    @Transactional
    public void modificarProcesoBancoVer2(ProcesoBancoBean procesoBancoBean) throws Exception {
        procesoBancoDAO.modificarProcesoBancoVer2(procesoBancoBean);
    }
    @Transactional
    public void modificarProcesoBancoVer3(ProcesoBancoBean procesoBancoBean) throws Exception {
        procesoBancoDAO.modificarProcesoBancoVer3(procesoBancoBean);
    }

    public List<ReporteDeudasRepBean> obtenerHoraCorte(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception {
        return procesoBancoDAO.obtenerHoraCorte(cuentasPorCobrarBean);
    }

    public List<ReporteDeudasRepBean> obtenerReporteDeudaHor(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception {
        return procesoBancoDAO.obtenerReporteDeudaHor(cuentasPorCobrarBean);
    }

    @Transactional
    public void actualizarFiltroMasivo(ProcesoBancoBean procesoBancoBean) throws Exception {
        procesoBancoDAO.actualizarFiltroMasivo(procesoBancoBean);
    }

    public ProcesoBancoDAO getProcesoBancoDAO() {
        return procesoBancoDAO;
    }

    public void setProcesoBancoDAO(ProcesoBancoDAO procesoBancoDAO) {
        this.procesoBancoDAO = procesoBancoDAO;
    }

    public ProcesoEnvioDAO getProcesoEnvioDAO() {
        return procesoEnvioDAO;
    }

    public void setProcesoEnvioDAO(ProcesoEnvioDAO procesoEnvioDAO) {
        this.procesoEnvioDAO = procesoEnvioDAO;
    }

    public ProcesoRecuperacionDAO getProcesoRecuperacionDAO() {
        return procesoRecuperacionDAO;
    }

    public void setProcesoRecuperacionDAO(ProcesoRecuperacionDAO procesoRecuperacionDAO) {
        this.procesoRecuperacionDAO = procesoRecuperacionDAO;
    }

}
