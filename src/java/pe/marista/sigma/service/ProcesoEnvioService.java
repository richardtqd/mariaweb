/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.Contenedor;
import pe.marista.sigma.bean.CuentasPorCobrarBean;
import pe.marista.sigma.bean.PersonaBean;
import pe.marista.sigma.bean.ProcesoBancoBean;
import pe.marista.sigma.bean.ProcesoEnvioBean;
import pe.marista.sigma.bean.TipoConceptoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.dao.CuentasPorCobrarDAO;
import pe.marista.sigma.dao.ProcesoBancoDAO;
import pe.marista.sigma.dao.ProcesoEnvioDAO;
import pe.marista.sigma.dao.ProcesoFinalDAO;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.SpringWebApplicationContext;

/**
 *
 * @author MS002
 */
public class ProcesoEnvioService {

    private ProcesoEnvioDAO procesoEnvioDAO;
    private ProcesoBancoDAO procesoBancoDAO;
    private CuentasPorCobrarDAO cuentasPorCobrarDAO;

    public List<ProcesoEnvioBean> obtenerProcesoEnvio() throws Exception {
        return procesoEnvioDAO.obtenerProcesoEnvio();
    }

    public void insertarProcesoEnvio(ProcesoEnvioBean procesoEnvioBean) throws Exception {
        procesoEnvioDAO.insertarProcesoEnvio(procesoEnvioBean);
    }

    public List<ProcesoEnvioBean> obtenerEnvio() throws Exception {
        return procesoEnvioDAO.obtenerEnvio();
    }

    public void insertarProcesoEnvio(ProcesoEnvioBean procesoEnvioBean, CuentasPorCobrarBean cuentasPorCobrarBean, List<CuentasPorCobrarBean> listaCtaFiltro, List<ProcesoEnvioBean> listaProcesoEnvioBean) throws Exception {
        ProcesoEnvioBean procesoEnvio = new ProcesoEnvioBean();
        for (CuentasPorCobrarBean cta : listaCtaFiltro) {
            for (ProcesoEnvioBean envio : listaProcesoEnvioBean) {
                if (listaCtaFiltro != null && listaProcesoEnvioBean != null) {
                    procesoEnvio.getCuentasPorCobrarBean().setIdCtasXCobrar(cta.getIdCtasXCobrar());
                    procesoEnvio.setMoneda(true);
                    procesoEnvio.getCuentasPorCobrarBean().setMonto(cta.getMonto());
                    procesoEnvio.getCuentasPorCobrarBean().setMora(cta.getMora());
                    procesoEnvio.getCuentasPorCobrarBean().setFechaVenc(cta.getFechaVenc());
                    procesoEnvio.getCuentasPorCobrarBean().setFechaPago(cta.getFechaPago());
                    procesoEnvio.getCuentasPorCobrarBean().getIdTipoMoneda().setIdCodigo(cta.getIdTipoMoneda().getIdCodigo());
                    procesoEnvio.getCuentasPorCobrarBean().getConceptoBean().setIdConcepto(cta.getConceptoBean().getIdConcepto()); // Agregar Datos en el service
                    procesoEnvio.getCuentasPorCobrarBean().getEstudianteBean().getPersonaBean().setIdPersona(cta.getEstudianteBean().getPersonaBean().getIdPersona());
                    procesoEnvio.getCuentasPorCobrarBean().getEstudianteBean().getPersonaBean().setNombre(cta.getEstudianteBean().getPersonaBean().getNombre());

                    UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                    procesoEnvio.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                    procesoEnvio.setCreaPor(beanUsuarioSesion.getUsuario());
                    procesoEnvio.getProcesoBancoBean().setIdProcesoBanco(envio.getProcesoBancoBean().getIdProcesoBanco());
                    procesoEnvio.setTipoRegistro(envio.getTipoRegistro());
                    procesoEnvioDAO.insertarProcesoEnvio(procesoEnvio);
                }
                break;
            }
//            for (ProcesoEnvioBean envio : listaProcesoEnvioBean) {
//                ProcesoEnvioBean procesoEnvio = new ProcesoEnvioBean();
//                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
//                procesoEnvio.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
//                procesoEnvio.setCreaPor(beanUsuarioSesion.getUsuario());
//                procesoEnvio.getCuentasPorCobrarBean().setIdCtasXCobrar(envio.getCuentasPorCobrarBean().getIdCtasXCobrar());
//                procesoEnvio.setMonto(envio.getCuentasPorCobrarBean().getMonto());
//                procesoEnvio.setCodigoMoneda(envio.getCuentasPorCobrarBean().getIdTipoMoneda());
//                procesoEnvio.setConceptoBean(envio.getCuentasPorCobrarBean().getConceptoBean());
//                procesoEnvio.getIdDiscente().setIdEstudiante(envio.getCuentasPorCobrarBean().getEstudianteBean().getPersonaBean().getIdPersona());
//                procesoEnvio.getProcesoBancoBean().setIdProcesoBanco(envio.getProcesoBancoBean().getIdProcesoBanco());
//                procesoEnvio.setTipoRegistro(envio.getTipoRegistro());
//                procesoEnvioDAO.insertarProcesoEnvio(procesoEnvio);  List<ProcesoEnvioBean> listaProcesoEnvioBean
//            }
        }
    }

    public void insertarEnvioMasivo(ProcesoEnvioBean procesoEnvio, CuentasPorCobrarBean cuentasPorCobrarBean, List<CuentasPorCobrarBean> listaCtaFiltro, List<ProcesoEnvioBean> listaProcesoEnvioBean) throws Exception {
//        ProcesoEnvioBean procesoEnvio = new ProcesoEnvioBean();
        Integer sum = 0, n = 0, error = 0, m = 0;
        UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        ProcesoBancoBean procesoBancoBean = new ProcesoBancoBean();
        ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
        sum = procesoBancoService.obtenerMaxIdProcesoBanco(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoHora = new SimpleDateFormat("hh:mm:ss");

        Date horaActualEnvio = new Date(System.currentTimeMillis());
        String horaCorteProceso = (horaActualEnvio.getHours() + ":" + horaActualEnvio.getMinutes() + ":" + horaActualEnvio.getSeconds());
        System.out.println("Corte====>   " + horaCorteProceso);
        DateFormat formatter = new SimpleDateFormat("hh:mm:ss");
        Date horaCorteEnv = formatter.parse(horaCorteProceso);

//        Calendar fecha = new GregorianCalendar();
//        Integer datoAnio = fecha.get(Calendar.YEAR);
//        Integer datoMes = fecha.get(Calendar.MONTH);
//        Integer datoDia = fecha.get(Calendar.DAY_OF_MONTH);
//        String FechaEnvio = datoAnio.toString() + 0 + datoMes.toString() + datoDia.toString();
//        Date fechaActualEnvio = formato.parse(FechaEnvio);
//        System.out.println("=======>>>>>" + fechaActualEnvio);
        Integer suma = 0;
        Integer totalEnvios = 0;
        Integer envios = 0;
        String hora = procesoEnvio.getHora();
        String minuto = procesoEnvio.getMinuto();
        String segundo = procesoEnvio.getSegundo();
        String horaActual = hora + ":" + minuto + ":" + segundo;
//        Date hour = formatoHora.parse(horaActual);
        Integer res = 0;
        for (CuentasPorCobrarBean cuenta : listaCtaFiltro) {
            if (cuenta.getIdCtasXCobrar() != null && cuenta.getFlgEnvio().equals(true)) {
                envios = envios + 1;
            }
        }

        System.out.println("========>>>>" + envios);
        for (CuentasPorCobrarBean ctas : listaCtaFiltro) {
            Integer totales = 0;
            if (ctas.getIdCtasXCobrar() != null && ctas.getFlgEnvio().equals(true)) {
                Integer sumatoria = 0;
                res = ctas.getMonto().intValue() + res;
//                System.out.println(totales);
//                System.out.println(res);
            }
        }

        //Si el Envio esta vacio
        if (listaProcesoEnvioBean.isEmpty()) {
            for (CuentasPorCobrarBean cta : listaCtaFiltro) {//listaCtasXCobrarBean
                for (ProcesoEnvioBean envio : listaProcesoEnvioBean) {
                    n++;
                    totalEnvios = cta.getMonto().intValue();
                    if (n == 1) {
                        sum = sum + 1;
                        procesoEnvio.getProcesoBancoBean().setIdProcesoBanco(sum);
                        procesoBancoBean.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        procesoBancoBean.setIdProcesoBanco(sum);
                        procesoBancoBean.setAnio(Integer.parseInt(envio.getAnio()));
                        procesoBancoBean.setRuc(procesoEnvio.getProcesoBancoBean().getCuentaBancoBean().getEntidadBancoBean().getRuc());
                        procesoBancoBean.setNombre(procesoEnvio.getProcesoBancoBean().getNombre());
                        procesoBancoBean.setFlgProceso(1);
                        procesoBancoBean.setRegProc(envios);
                        procesoBancoBean.setRegError(0);
                        procesoBancoBean.setCodUniNeg(procesoEnvio.getProcesoBancoBean().getCuentaBancoBean().getCodUniNeg());
                        procesoBancoBean.setMoneda(procesoEnvio.getMoneda());
                        procesoBancoBean.setNumCuenta(procesoEnvio.getProcesoBancoBean().getCuentaBancoBean().getNumCuenta());
                        procesoBancoBean.setFecha(procesoEnvio.getCreaFecha());
                        procesoBancoBean.setHoraCorte(horaCorteEnv);//cambio
                        procesoBancoBean.setRegEnv(envios);
                        procesoBancoBean.setMontoEnv(res.floatValue());//.floatValue() m.floatValue()
                        procesoBancoBean.setMontoRecup(null);
                        procesoBancoBean.setTipoArchivo("C");
                        procesoBancoBean.setCreaPor(beanUsuarioSesion.getUsuario());
                        System.out.println(sum);
                        procesoBancoDAO.insertarProcesoBanco(procesoBancoBean);
                    }
                    if (listaCtaFiltro != null && listaProcesoEnvioBean != null) {
                        if (cta.getIdCtasXCobrar() != null && cta.getFlgEnvio().equals(true)) {
                            n++;
//                        procesoEnvio.getProcesoBancoBean().setIdProcesoBanco(sum);
                            procesoEnvio.getCuentasPorCobrarBean().setIdCtasXCobrar(cta.getIdCtasXCobrar());
                            procesoEnvio.setMoneda(false);
                            procesoEnvio.getCuentasPorCobrarBean().setMonto(cta.getMonto());
                            procesoEnvio.getCuentasPorCobrarBean().setMora(cta.getMora());
                            procesoEnvio.getCuentasPorCobrarBean().setFechaVenc(cta.getFechaVenc());
                            procesoEnvio.getCuentasPorCobrarBean().setFechaPago(cta.getFechaPago());
                            procesoEnvio.setTipoRegistro(procesoEnvio.getTipoRegistro());
                            procesoEnvio.setMoneda(procesoEnvio.getMoneda());
                            procesoEnvio.setTipoRegistro(procesoEnvio.getTipoRegistro());
                            procesoEnvio.getCuentasPorCobrarBean().getConceptoBean().setIdConcepto(cta.getConceptoBean().getIdConcepto()); // Agregar Datos en el service
                            procesoEnvio.getCuentasPorCobrarBean().getEstudianteBean().getPersonaBean().setIdPersona(cta.getEstudianteBean().getPersonaBean().getIdPersona());
                            procesoEnvio.getCuentasPorCobrarBean().getEstudianteBean().getPersonaBean().setNombre(cta.getEstudianteBean().getPersonaBean().getNombre());
                            procesoEnvio.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                            procesoEnvio.setCreaPor(beanUsuarioSesion.getUsuario());
                            System.out.println(sum);
                            procesoEnvioDAO.insertarEnvioMasivo(procesoEnvio);
                        }
                    }
                    if (cta.getEstudianteBean().getPersonaBean().getIdPersona().equals(envio.getIdDiscente().getPersonaBean().getIdPersona())) {
                        sum = sum + 1;
                        cta.getProcesoEnvioBean().setIdProcesoEnvio(sum);
                        cta.setModiPor(beanUsuarioSesion.getUsuario());
                        System.out.println(sum);
                        cuentasPorCobrarDAO.modificarCuentaPorEnvio(cta);
                    }
                    break;
                }
            }
        }

        //Si no esta vacio
        for (CuentasPorCobrarBean cta : listaCtaFiltro) {//listaCtasXCobrarBean
            for (ProcesoEnvioBean envio : listaProcesoEnvioBean) {
                n++;
//                Integer m = 0;
                totalEnvios = cta.getMonto().intValue();
                if (n == 1) {
                    sum = sum + 1;
                    procesoEnvio.getProcesoBancoBean().setIdProcesoBanco(sum);
                    procesoBancoBean.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    procesoBancoBean.setIdProcesoBanco(sum);
                    procesoBancoBean.setAnio(Integer.parseInt(envio.getAnio()));
                    procesoBancoBean.setRuc(procesoEnvio.getProcesoBancoBean().getCuentaBancoBean().getEntidadBancoBean().getRuc());
                    procesoBancoBean.setNombre(procesoEnvio.getProcesoBancoBean().getNombre());
                    procesoBancoBean.setFlgProceso(1);
                    procesoBancoBean.setRegProc(envios);
                    procesoBancoBean.setRegError(0);
                    procesoBancoBean.setCodUniNeg(procesoEnvio.getProcesoBancoBean().getCuentaBancoBean().getCodUniNeg());
                    procesoBancoBean.setMoneda(procesoEnvio.getMoneda());
                    procesoBancoBean.setNumCuenta(procesoEnvio.getProcesoBancoBean().getCuentaBancoBean().getNumCuenta());
                    procesoBancoBean.setFecha(procesoEnvio.getCreaFecha());
                    procesoBancoBean.setHoraCorte(horaCorteEnv);//cambio
                    procesoBancoBean.setRegEnv(envios);
                    procesoBancoBean.setMontoEnv(res.floatValue());//.floatValue() m.floatValue()
                    procesoBancoBean.setMontoRecup(null);
                    procesoBancoBean.setTipoArchivo("C");
                    procesoBancoBean.setCreaPor(beanUsuarioSesion.getUsuario());
                    System.out.println(sum);
                    procesoBancoDAO.insertarProcesoBanco(procesoBancoBean);
                }
                if (listaCtaFiltro != null && listaProcesoEnvioBean != null) {
                    if (cta.getIdCtasXCobrar() != null && cta.getFlgEnvio().equals(true)) {
                        n++;
//                        procesoEnvio.getProcesoBancoBean().setIdProcesoBanco(sum);
                        procesoEnvio.getCuentasPorCobrarBean().setIdCtasXCobrar(cta.getIdCtasXCobrar());
                        procesoEnvio.setMoneda(false);
                        procesoEnvio.getCuentasPorCobrarBean().setMonto(cta.getMonto());
                        procesoEnvio.getCuentasPorCobrarBean().setMora(cta.getMora());
                        procesoEnvio.getCuentasPorCobrarBean().setFechaVenc(cta.getFechaVenc());
                        procesoEnvio.getCuentasPorCobrarBean().setFechaPago(cta.getFechaPago());
                        procesoEnvio.setTipoRegistro(procesoEnvio.getTipoRegistro());
                        procesoEnvio.setMoneda(procesoEnvio.getMoneda());
                        procesoEnvio.setTipoRegistro(procesoEnvio.getTipoRegistro());
                        procesoEnvio.getCuentasPorCobrarBean().getConceptoBean().setIdConcepto(cta.getConceptoBean().getIdConcepto()); // Agregar Datos en el service
                        procesoEnvio.getCuentasPorCobrarBean().getEstudianteBean().getPersonaBean().setIdPersona(cta.getEstudianteBean().getPersonaBean().getIdPersona());
                        procesoEnvio.getCuentasPorCobrarBean().getEstudianteBean().getPersonaBean().setNombre(cta.getEstudianteBean().getPersonaBean().getNombre());
                        procesoEnvio.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                        procesoEnvio.setCreaPor(beanUsuarioSesion.getUsuario());
                        System.out.println(sum);
                        procesoEnvioDAO.insertarEnvioMasivo(procesoEnvio);
                    }
                }

                if (cta.getEstudianteBean().getPersonaBean().getIdPersona().equals(envio.getIdDiscente().getPersonaBean().getIdPersona())) {
                    sum = sum + 1;
                    cta.getProcesoEnvioBean().setIdProcesoEnvio(sum);
                    cta.setModiPor(beanUsuarioSesion.getUsuario());
                    System.out.println(sum);
                    cuentasPorCobrarDAO.modificarCuentaPorEnvio(cta);
                }
                break;
            }
        }
    }

    @Transactional
    public void modificarEstadoEnvio(List<CuentasPorCobrarBean> listaCtaFiltro, Boolean flgStatus) throws Exception {
        UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        for (CuentasPorCobrarBean cta : listaCtaFiltro) {
            if (cta.getFlgEnvio().equals(true)) {
                cta.setIdCtasXCobrar(cta.getIdCtasXCobrar());
                cta.setFlgEnvio(false);
                cta.setModiPor(beanUsuarioSesion.getUsuario());
                procesoEnvioDAO.llamarProChangeEnvioCta(cta);
            } else {
                if (cta.getFlgEnvio().equals(false)) {
                    cta.setIdCtasXCobrar(cta.getIdCtasXCobrar());
                    cta.setFlgEnvio(true);
                    cta.setModiPor(beanUsuarioSesion.getUsuario());
                    procesoEnvioDAO.llamarProChangeEnvioCta(cta);
                }
            }
        }
    }

    public List<ProcesoEnvioBean> obtenerEnvioPorFiltro(ProcesoEnvioBean procesoEnvioBean) throws Exception {
        return procesoEnvioDAO.obtenerEnvioPorFiltro(procesoEnvioBean);
    }

    public void modificarProcesoEnvio(ProcesoEnvioBean procesoEnvioBean) throws Exception {
        procesoEnvioDAO.modificarProcesoEnvio(procesoEnvioBean);
    }

    public void modificarStatusCuenta(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception {
        procesoEnvioDAO.modificarStatusCuenta(cuentasPorCobrarBean);
    }

    public void modificarStatusEnvio(ProcesoEnvioBean procesoEnvioBean) throws Exception {
        procesoEnvioDAO.modificarStatusEnvio(procesoEnvioBean);
    }

    public void modificarStatus(ProcesoEnvioBean procesoEnvioBean) throws Exception {
        procesoEnvioDAO.modificarStatus(procesoEnvioBean);
    }

    public void modificarStatusGraba(ProcesoEnvioBean procesoEnvioBean) throws Exception {
        procesoEnvioDAO.modificarStatusGraba(procesoEnvioBean);
    }

    public void eliminarProcesoEnvio(Integer idProcesoEnvio) throws Exception {
        procesoEnvioDAO.eliminarProcesoEnvio(idProcesoEnvio);
    }

    public void eliminarProcesoPorBanco(Integer idProcesoBanco) throws Exception {
        procesoEnvioDAO.eliminarProcesoPorBanco(idProcesoBanco);
    }

    public List<ProcesoEnvioBean> obtenerPorId(Integer idProcesoEnvio) throws Exception {
        return procesoEnvioDAO.obtenerPorId(idProcesoEnvio);
    }

    public ProcesoEnvioBean obtenerPorBanco(Integer idProcesoEnvio) throws Exception {
        return procesoEnvioDAO.obtenerPorBanco(idProcesoEnvio);
    }

    public List<ProcesoEnvioBean> obtenerPorUnineg(String uniNeg) throws Exception {
        return procesoEnvioDAO.obtenerPorUnineg(uniNeg);
    }

    public List<PersonaBean> buscarPersona(PersonaBean personaBean) throws Exception {
        return procesoEnvioDAO.buscarPersona(personaBean);
    }

    public List<CuentasPorCobrarBean> buscarCuentas(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception {
        return procesoEnvioDAO.buscarCuentas(cuentasPorCobrarBean);
    }

    public void modificarCuentaCambio(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception {
        procesoEnvioDAO.modificarCuentaCambio(cuentasPorCobrarBean);
    }

    public List<CuentasPorCobrarBean> obtenerCuentas(String uniNeg) throws Exception {
        return procesoEnvioDAO.obtenerCuentas(uniNeg);
    }

    public List<ProcesoEnvioBean> obtenerPorProcesoBanco(Integer idProcesoBanco, String uniNeg) throws Exception {
        return procesoEnvioDAO.obtenerPorProcesoBanco(idProcesoBanco, uniNeg);
    }

    public List<CuentasPorCobrarBean> obtenerCuentaEstudiante(String uniNeg, String idEstudiante) throws Exception {
        return procesoEnvioDAO.obtenerCuentaEstudiante(uniNeg, idEstudiante);
    }

    public Integer obtenerDeudaEstudiante(String idEstudiante, String uniNeg, Integer idProcesoBanco, String idDiscente) throws Exception {
        return procesoEnvioDAO.obtenerDeudaEstudiante(idEstudiante, uniNeg, idProcesoBanco, idDiscente);
    }

    public Integer obtenerSumaImporte(String uniNeg, Integer idProcesoBanco) throws Exception {
        return procesoEnvioDAO.obtenerSumaImporte(uniNeg, idProcesoBanco);
    }

    public void modificarStatusEnvioCuenta(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception {
        procesoEnvioDAO.modificarStatusEnvioCuenta(cuentasPorCobrarBean);
    }

    public Integer obtenerMaxIdProcesoBanco(String uniNeg) throws Exception {
        return procesoEnvioDAO.obtenerMaxIdProcesoBanco(uniNeg);
    }

    public Integer obtenerDeudaConciliaCta(Integer idProcesoRecup, String idEstudiante) throws Exception {
        return procesoEnvioDAO.obtenerDeudaConciliaCta(idProcesoRecup, idEstudiante);
    }

    public void insertarProcesoBanco(ProcesoBancoBean procesoBancoBean) throws Exception {
        procesoBancoDAO.insertarProcesoBanco(procesoBancoBean);
    }

    public List<TipoConceptoBean> obtenerTipoConcepto(Integer idTipoConcepto) throws Exception {
        return procesoEnvioDAO.obtenerTipoConcepto(idTipoConcepto);
    }

    public List<CuentasPorCobrarBean> obtenerEnvioCuenta(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception {
        return procesoEnvioDAO.obtenerEnvioCuenta(cuentasPorCobrarBean);
    }

    public List<CuentasPorCobrarBean> obtenerCuentaId(Integer idCtasXCobrar) throws Exception {
        return procesoEnvioDAO.obtenerCuentaId(idCtasXCobrar);
    }

    public void modificarStatusEnvioCuentaTrueFalse(String uniNeg, Boolean flgEnvio, List<CuentasPorCobrarBean> listaCtaFiltro) throws Exception {
        procesoEnvioDAO.modificarStatusEnvioCuentaTrueFalse(uniNeg, flgEnvio, listaCtaFiltro);
    }

    public Object llamarProChangeEnvioCta(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception {
        return procesoEnvioDAO.llamarProChangeEnvioCta(cuentasPorCobrarBean);
    }

    @Transactional
    public Object execProEnvioOp(ProcesoEnvioBean procesoEnvioBean) throws Exception {
        return procesoEnvioDAO.execProEnvioOp(procesoEnvioBean);
    }

    /* ENVIO BANCO OPERACIONES */
    @Transactional
    public List<Contenedor> execProEnvioOperacion(ProcesoEnvioBean procesoEnvioBean) throws Exception {
        ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
        List<Contenedor> listaEnvioCurso = new ArrayList<>();
        Object valor = execProEnvioOp(procesoEnvioBean);
        if (valor != null || valor != "") {
            listaEnvioCurso = procesoFinalService.execProListaBanco(procesoEnvioBean.getUniNeg(), procesoEnvioBean.getRuc(), procesoEnvioBean.getIdProcesoBanco(), MaristaConstantes.FileDetalle, 3, 2);
        }
        return listaEnvioCurso;
    }

    /* ENVIO BANCO CUENTAS */
    @Transactional
    public Object execProEnvioPro(String uniNeg, String ruc, Integer idProcesoBanco, Integer anio, String idEstudiante, String codigo, String nombreCompleto, Integer idTipoConcepto, Integer idConcepto, String creaPor, String idTipoRegistro, Integer estado, Integer mes) throws Exception {
        return procesoEnvioDAO.execProEnvioPro(uniNeg, ruc, idProcesoBanco, anio, idEstudiante, codigo, nombreCompleto, idTipoConcepto, idConcepto, creaPor, idTipoRegistro, estado, mes);
    }

    @Transactional
    public void modificarCuota(ProcesoEnvioBean procesoEnvioBean) throws Exception {
        procesoEnvioDAO.modificarCuota(procesoEnvioBean);
    }

    public ProcesoEnvioDAO getProcesoEnvioDAO() {
        return procesoEnvioDAO;
    }

    public void setProcesoEnvioDAO(ProcesoEnvioDAO procesoEnvioDAO) {
        this.procesoEnvioDAO = procesoEnvioDAO;
    }

    public ProcesoBancoDAO getProcesoBancoDAO() {
        return procesoBancoDAO;
    }

    public void setProcesoBancoDAO(ProcesoBancoDAO procesoBancoDAO) {
        this.procesoBancoDAO = procesoBancoDAO;
    }

    public CuentasPorCobrarDAO getCuentasPorCobrarDAO() {
        return cuentasPorCobrarDAO;
    }

    public void setCuentasPorCobrarDAO(CuentasPorCobrarDAO cuentasPorCobrarDAO) {
        this.cuentasPorCobrarDAO = cuentasPorCobrarDAO;
    }

    public Float obtenerSumaEnvio(String uniNeg, String anio, Integer idProcesoBanco) throws Exception {
        return procesoEnvioDAO.obtenerSumaEnvio(uniNeg, anio, idProcesoBanco);
    }

    @Transactional
    public void insertarMasivoBanco(ProcesoEnvioBean procesoEnvioBean, Integer numRegistros, Float monto, Integer anio, String fechaEnvio) throws Exception {
        UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");

        Date horaActualEnvio = new Date(System.currentTimeMillis());
        String horaCorteProceso = (horaActualEnvio.getHours() + ":" + horaActualEnvio.getMinutes() + ":" + horaActualEnvio.getSeconds());
        DateFormat formatter = new SimpleDateFormat("hh:mm:ss");
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        Date horaCorteEnv = formatter.parse(horaCorteProceso);

        ProcesoBancoBean procesoBancoBean = new ProcesoBancoBean();
        procesoBancoBean.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        procesoBancoBean.setAnio(anio);
        procesoBancoBean.setRuc(procesoEnvioBean.getProcesoBancoBean().getCuentaBancoBean().getEntidadBancoBean().getRuc());
        procesoBancoBean.setNombre(procesoEnvioBean.getProcesoBancoBean().getNombre());
        procesoBancoBean.setFlgProceso(1);
        procesoBancoBean.setRegProc(numRegistros);
        procesoBancoBean.setRegError(0);
        procesoBancoBean.setCodUniNeg(procesoEnvioBean.getProcesoBancoBean().getCuentaBancoBean().getCodUniNeg());
        procesoBancoBean.setMoneda(procesoEnvioBean.getMoneda());
        procesoBancoBean.setNumCuenta(procesoEnvioBean.getProcesoBancoBean().getCuentaBancoBean().getNumCuenta());
        procesoBancoBean.setFecha(formato.parse(fechaEnvio));
        procesoBancoBean.setHoraCorte(horaCorteEnv);//cambio
        procesoBancoBean.setRegEnv(numRegistros);
        procesoBancoBean.setMontoEnv(monto);
        procesoBancoBean.setMontoRecup(null);
        procesoBancoBean.setTipoArchivo("C");
        procesoBancoBean.setCreaPor(beanUsuarioSesion.getUsuario());
        procesoBancoDAO.insertarProcesoBanco(procesoBancoBean);
    }

    @Transactional
    public Object insertarMasivoEnvio(String uniNeg, String idDiscente, String creaPor, String registro, Integer moneda, Integer idProcesoBanco, Integer mes) throws Exception {
        return procesoEnvioDAO.insertarMasivoEnvio(uniNeg, idDiscente, creaPor, registro, moneda, idProcesoBanco, mes);
    }

    public Integer obtenerNumFilas(ProcesoEnvioBean procesoEnvioBean) throws Exception {
        return procesoEnvioDAO.obtenerNumFilas(procesoEnvioBean);
    }

    public CuentasPorCobrarBean obtenerCtaEstudiante(String uniNeg, String idEstudiante, Integer anio, Integer mes) throws Exception {
        return procesoEnvioDAO.obtenerCtaEstudiante(uniNeg, idEstudiante, anio, mes);
    }

    public List<CuentasPorCobrarBean> obtenerEnvioCuentaResDes(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception {
        return procesoEnvioDAO.obtenerEnvioCuentaResDes(cuentasPorCobrarBean);
    }

    public List<CuentasPorCobrarBean> obtenerResTrans(String uniNeg, Integer idCtasXCobrar, Integer idCtasXCobrarDes) throws Exception {
        return procesoEnvioDAO.obtenerResTrans(uniNeg, idCtasXCobrar, idCtasXCobrarDes);
    }

    public List<CuentasPorCobrarBean> obtenerEnvioCuentaRes(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception {
        return procesoEnvioDAO.obtenerEnvioCuentaRes(cuentasPorCobrarBean);
    }

    /* Nuevo Procesoe de Envio */
    @Transactional
    public List<Contenedor> obtenerEnvioUniNeg(ProcesoEnvioBean procesoEnvioBean) throws Exception {
        List<Contenedor> listaContenedor = new ArrayList<>();
        ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
        Object object = new Object();
        object = procesoEnvioDAO.execProUniNeg(procesoEnvioBean);
        System.out.println(">>>>>" + object);
        listaContenedor = procesoFinalService.execProListaBanco(procesoEnvioBean.getUniNeg(), procesoEnvioBean.getRuc(), procesoEnvioBean.getIdProcesoBanco(), MaristaConstantes.FileDetalle, 3, 2);
        return listaContenedor;
    }

    @Transactional
    public List<Contenedor> obtenerEnvioUniNegCab(ProcesoEnvioBean procesoEnvioBean) throws Exception {
        List<Contenedor> listaContenedor = new ArrayList<>();
        ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
        Object object = new Object();
        object = procesoEnvioDAO.execProUniNegCab(procesoEnvioBean);
        System.out.println(">>>>>" + object);
        listaContenedor = procesoFinalService.execProListaBanco(procesoEnvioBean.getUniNeg(), procesoEnvioBean.getRuc(), procesoEnvioBean.getIdProcesoBanco(), MaristaConstantes.FileCabecera, 3, 2);
        return listaContenedor;
    }

    @Transactional
    public List<Contenedor> obtenerEnvioTalleres(ProcesoEnvioBean procesoEnvioBean) throws Exception {
        List<Contenedor> listaContenedor = new ArrayList<>();
        ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
        Object object = new Object();
        object = procesoEnvioDAO.execProTalleres(procesoEnvioBean);
        listaContenedor = procesoFinalService.execProListaBanco(procesoEnvioBean.getUniNeg(), procesoEnvioBean.getRuc(), procesoEnvioBean.getIdProcesoBanco(), MaristaConstantes.FileDetalle, 3, 2);
        return listaContenedor;
    }

    /* PROCESO DE ENVIO POR TALLERES */
    @Transactional
    public List<Contenedor> obtenerEnvioUniNegBancoTaller(ProcesoEnvioBean procesoEnvioBean) throws Exception {
        List<Contenedor> listaContenedor = new ArrayList<>();
        ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
        Object object = new Object();
        object = procesoEnvioDAO.execProUniNegTaller(procesoEnvioBean);
        System.out.println(">>>>>" + object);
        listaContenedor = procesoFinalService.execProListaBanco(procesoEnvioBean.getUniNeg(), procesoEnvioBean.getRuc(), procesoEnvioBean.getIdProcesoBanco(), MaristaConstantes.FileDetalle, 3, 2);
        return listaContenedor;
    }

    public Float obtenerMontoTotal(Integer idProcesoBanco, Integer idTipoFile, Integer flgProceso, String ruc, String uniNeg) throws Exception {
        return procesoEnvioDAO.obtenerMontoTotal(idProcesoBanco, idTipoFile, flgProceso, ruc, uniNeg);
    }

    public Integer obtenerTotalRegistros(Integer idProcesoBanco, String ruc, String uniNeg) throws Exception {
        return procesoEnvioDAO.obtenerTotalRegistros(idProcesoBanco, ruc, uniNeg);
    }

    public List<CuentasPorCobrarBean> buscarCuentaCorriente(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception {
        return procesoEnvioDAO.buscarCuentaCorriente(cuentasPorCobrarBean);
    }

}
