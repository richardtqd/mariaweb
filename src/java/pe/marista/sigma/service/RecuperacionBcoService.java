/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.sql.SQLException;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.EstudianteBean;
import pe.marista.sigma.bean.ProcesoBancoBean;
import pe.marista.sigma.bean.RecuperacionBcoBean;
import pe.marista.sigma.dao.EstudianteDAO;
import pe.marista.sigma.dao.RecuperacionBcoDAO;
import pe.marista.sigma.factory.BeanFactory;

/**
 *
 * @author MS-001
 */
public class RecuperacionBcoService {

    private RecuperacionBcoDAO recuperacionBcoDAO;

    public RecuperacionBcoBean obtenerCamposRecuperacion(String uniNeg, Integer idProcesoBanco, String txt) throws Exception {
        return recuperacionBcoDAO.obtenerCamposRecuperacion(uniNeg, idProcesoBanco, txt);
    }

    public Integer validarCabeceraRecepcion(String uniNeg) throws Exception {
        return recuperacionBcoDAO.validarCabeceraRecepcion(uniNeg);
    }

    public Integer validarPieRecepcion(String uniNeg) throws Exception {
        return recuperacionBcoDAO.validarPieRecepcion(uniNeg);
    }
    
    
    
    @Transactional
    public void modificarCuentasPorCobrar(String uniNeg, List<RecuperacionBcoBean> listaRecuperacionBcoBean, String usuario, ProcesoBancoBean procesoBancoBean) throws Exception {
        ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
        CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
        Integer registros = 0;
//        Integer registrosOK = 0;
        Integer registrosError = 0;
        Integer idProcesoBco = 0;
        Double montoRecuperado = new Double("0.00");
        if (!listaRecuperacionBcoBean.isEmpty()) {
            ProcesoBancoBean proceso = new ProcesoBancoBean();
            //B. INSERTANDO PROCESO BANCO
            proceso = procesoBancoBean;
            proceso.setIdProcesoBanco(idProcesoBco);
            proceso.getUnidadNegocioBean().setUniNeg(uniNeg);
            proceso.setFlgProceso(0);//0:recuperacion , 1:envio
            proceso.setTipoArchivo("C");
            proceso.setRegEnv(registros);
            proceso.setRegError(registrosError);
            proceso.setMontoRecup(montoRecuperado.floatValue());
            proceso.setCreaPor(usuario);
            procesoBancoService.insertarProcesoBanco(proceso);
            idProcesoBco = (proceso.getIdProcesoBanco());
            System.out.println("idProceso " + idProcesoBco);
            //A. INSERTANDO DETALLE //Insertando Registros
            for (RecuperacionBcoBean lista : listaRecuperacionBcoBean) {
                Integer codigoError = 0;
                registros = registros + 1;
                montoRecuperado = montoRecuperado + lista.getMontoPension();
//                System.out.println("============INICIO MODIFICACION REGISTRO N:" + registros);
                //1.- Si el getIdCtasXcobrar llega null: 
                //opc1: codigo del estudiante no existe, :fecha de venc no es correcta
                if (lista.getIdCtasXcobrar() == null) {
                    EstudianteDAO estudianteDAO = BeanFactory.getEstudianteDAO();
                    EstudianteBean estudiante = new EstudianteBean();
                    estudiante = estudianteDAO.obtenerEstudiantePorCodigo(lista.getCodigo(), uniNeg);
                    if (estudiante == null) {
                        //opc1 
                        codigoError = MaristaConstantes.COD_ErrorBco_CodigoErrorEst;
//                        System.out.println("Codigo estudiante no existe");
                        registrosError = registrosError + 1;
                    } else {
                        //opc2  
                        codigoError = MaristaConstantes.COD_ErrorBco_FechaVencIncorrecta;
//                        System.out.println("Fecha de venc. incorrecta");
                        registrosError = registrosError + 1;
                    }
                } else {
                    if (lista.getIdDocIngreso() != null) {
                        //getIdCtasXcobrar OK
                        if (lista.getMontoPensionCta().equals(lista.getMontoPension())) {
                            //monto OK
                            System.out.println("MontopensionCTA"+lista.getMontoPensionCta()+" MontoPension"+lista.getMontoPension());
                            //--1:ya pagó, 0:no ha pagado
                            if (lista.getPagoMesAnt() == null ) {
                                System.out.println("xxxxxxxxxxxxxxxxxxxxxxxx getPagoMesAnt");
                            } 
                            if (lista.getPagoMesAnt().equals(0)) {
                                Integer pago = 0;
                                pago = cuentasPorCobrarService.pagoMesAnterior(uniNeg, lista.getIdCtaPensAnt());
                                lista.setPagoMesAnt(pago);
                                System.out.println("pago_" + pago);
                            }

                            if (lista.getPagoMesAnt().equals(1)) {
//                                System.out.println("Sí pagó el mes anterior");
                                if (lista.getFechaPagoCta() == null) {
//                                    System.out.println("PENSION A MODIFICAR OK");
                                    codigoError = MaristaConstantes.COD_ErrorBco_OK;
                                    SP_ActualizarCtaCte(lista, idProcesoBco, usuario);
                                } else {
//                                    System.out.println("Pago duplicado");
//                                    System.out.println("PENSION ERROR");
                                    codigoError = MaristaConstantes.COD_ErrorBco_ErrorEstado_PagoDup;
                                    registrosError = registrosError + 1;
                                }
                            } else if (lista.getPagoMesAnt().equals(0)) {
                                codigoError = MaristaConstantes.COD_ErrorBco_NoPagoPensAnt;
//                                System.out.println("No pagó el mes anterior");
                                registrosError = registrosError + 1;
                            } else {
                                codigoError = MaristaConstantes.COD_ErrorBco_DeudaNoExiste;
//                                System.out.println("no tiene cta x cobrar");
                                registrosError = registrosError + 1;
                            }
                        } else {
                            codigoError = MaristaConstantes.COD_ErrorBco_MontoNoIgual;
//                            System.out.println("monto diferente");
                            registrosError = registrosError + 1;
                        }
                    } else {
                        codigoError = MaristaConstantes.COD_ErrorBco_RecNoGen;
//                        System.out.println("pension sin documento de ingreso");
                        registrosError = registrosError + 1;
                    }
                }
                lista.setIdTipoErrorBanco(codigoError);
                SP_insertarLogProcesoBanco(lista, idProcesoBco, usuario);
//                System.out.println("============FIN MODIFICACION REGISTRO N:" + registros);
            }
            //B. modificando PROCESO BANCO
            ProcesoBancoBean procesoBcoModi = new ProcesoBancoBean();
            //B. INSERTANDO PROCESO BANCO 
            procesoBcoModi.setIdProcesoBanco(idProcesoBco);
            procesoBcoModi.getUnidadNegocioBean().setUniNeg(uniNeg);
            procesoBcoModi.setRegEnv(registros);
            procesoBcoModi.setRegError(registrosError);
            procesoBcoModi.setMontoRecup(montoRecuperado.floatValue());
            procesoBancoService.modificarProcesoBancoVer2(procesoBcoModi);
        } else {
            //mensja lista vacia
            System.out.println("lista vacia");
        }
    }

    @Transactional
    public RecuperacionBcoBean SP_obtenerCamposRecuperacion(String uniNeg, String txt) throws Exception {
        return recuperacionBcoDAO.SP_obtenerCamposRecuperacion(uniNeg, txt);
    }

    @Transactional
    public RecuperacionBcoBean SP_ActualizarCtaCte(RecuperacionBcoBean recuperacionBcoBean, Integer idProcesoBanco, String usuario) throws Exception {
        return recuperacionBcoDAO.SP_ActualizarCtaCte(recuperacionBcoBean, idProcesoBanco, usuario);
    }

    @Transactional
    public void SP_insertarLogProcesoBanco(RecuperacionBcoBean recuperacionBcoBean, Integer idProcesoBanco, String usuario) throws Exception {
        recuperacionBcoDAO.SP_insertarLogProcesoBanco(recuperacionBcoBean, idProcesoBanco, usuario);
    }

    public List<RecuperacionBcoBean> obtenerListaRecuperacionPorId(String uniNeg, Integer idProcesoBanco) throws Exception {
        return recuperacionBcoDAO.obtenerListaRecuperacionPorId(uniNeg, idProcesoBanco);
    }

    //metodos getter and setter
    public RecuperacionBcoDAO getRecuperacionBcoDAO() {
        return recuperacionBcoDAO;
    }

    public void setRecuperacionBcoDAO(RecuperacionBcoDAO recuperacionBcoDAO) {
        this.recuperacionBcoDAO = recuperacionBcoDAO;
    }

}
