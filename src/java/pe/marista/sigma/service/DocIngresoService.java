/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CajaBean;
import pe.marista.sigma.bean.CajaGenBean;
import pe.marista.sigma.bean.CajeroCajaBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.ConceptoUniNegBean;
import pe.marista.sigma.bean.CuentasPorCobrarBean;
import pe.marista.sigma.bean.CursoTallerBean;
import pe.marista.sigma.bean.DescuentoTallerBean;
import pe.marista.sigma.bean.DetDocIngresoBean;
import pe.marista.sigma.bean.DocEgresoBean;
import pe.marista.sigma.bean.DocIngresoBean;
import pe.marista.sigma.bean.EstudianteBean;
import pe.marista.sigma.bean.FamiliarBean;
import pe.marista.sigma.bean.ImpresoraBean;
import pe.marista.sigma.bean.ImpresoraCajaBean;
import pe.marista.sigma.bean.MatriculaBean;
import pe.marista.sigma.bean.PersonaBean;
import pe.marista.sigma.bean.ProgramacionBean;
import pe.marista.sigma.bean.SolicitudCajaCHBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.TipoDiscenteBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.DetDocIngresoRepBean;
import pe.marista.sigma.bean.reporte.DocIngresoABRepBean;
import pe.marista.sigma.bean.reporte.DocIngresoRepBean;
import pe.marista.sigma.bean.reporte.ReciboPensionRepBean;
import pe.marista.sigma.dao.CajaChicaLiquidacionDAO;
import pe.marista.sigma.dao.CuentasPorCobrarDAO;
import pe.marista.sigma.dao.DocEgresoDAO;
import pe.marista.sigma.dao.DocIngresoDAO;
import pe.marista.sigma.dao.SolicitudCajaCHDAO;
import pe.marista.sigma.factory.BeanFactory;

/**
 *
 * @author MS002
 */
public class DocIngresoService {

    private DocIngresoDAO docIngresoDAO;

    public DocIngresoDAO getDocIngresoDAO() {
        return docIngresoDAO;
    }

    public DocIngresoBean obtenerDocIngresoPorIdFull(Integer idDocIngreso, String uniNeg) throws Exception {
        return docIngresoDAO.obtenerDocIngresoPorIdFull(idDocIngreso, uniNeg);
    }

    public void setDocIngresoDAO(DocIngresoDAO docIngresoDAO) {
        this.docIngresoDAO = docIngresoDAO;
    }

    public List<DetDocIngresoBean> reporteDelDia(DetDocIngresoBean detDocIngresoBean) throws Exception {
        return docIngresoDAO.reporteDelDia(detDocIngresoBean);
    }

    public List<CuentasPorCobrarBean> obtenerCuentaPorMatricula(String idPersona) throws Exception {
        return docIngresoDAO.obtenerCuentaPorMatricula(idPersona);
    }

    public List<PersonaBean> buscarPersona(PersonaBean personaBean) throws Exception {
        return docIngresoDAO.buscarPersona(personaBean);
    }

    public List<ConceptoUniNegBean> obtenerConcepto(PersonaBean personaBean) throws Exception {
        return docIngresoDAO.obtenerConcepto(personaBean);
    }

    public TipoDiscenteBean obtenerTipoDiscente(String idPersona, Integer anio, String uniNeg) throws Exception {
        return docIngresoDAO.obtenerTipoDiscente(idPersona, anio, uniNeg);
    }

    public DetDocIngresoBean obtenerDetDocIngresoBeanPorId(DetDocIngresoBean detDocIngresoBean) throws Exception {
        return docIngresoDAO.obtenerDetDocIngresoBeanPorId(detDocIngresoBean);
    }

    public PersonaBean buscarPersonaPostulante(String uniNeg, String idPersona) throws Exception {
        Map<String, Object> parms = new HashMap<>();
        parms.put("codigo", MaristaConstantes.TIP_POST);
        parms.put("uniNeg", uniNeg);
        parms.put("idPersona", idPersona);
        return docIngresoDAO.buscarPersonaPostulante(parms);
    }

    public PersonaBean buscarPersonaInscPostulante(Integer anio, String uniNeg, String idPersona) throws Exception {
        Map<String, Object> parms = new HashMap<>();
        parms.put("codigo", MaristaConstantes.TIP_ADM);
        parms.put("anio", anio);
        parms.put("uniNeg", uniNeg);
        parms.put("idPersona", idPersona);
        return docIngresoDAO.buscarPersonaInscPostulante(parms);
    }

    public PersonaBean buscarPersonaMatriculado(Integer anio, String uniNeg, String idPersona) throws Exception {
        Map<String, Object> parms = new HashMap<>();
        parms.put("anio", anio);
        parms.put("uniNeg", uniNeg);
        parms.put("idPersona", idPersona);
        return docIngresoDAO.buscarPersonaMatriculado(parms);
    }

    public List<ImpresoraBean> obtenerImpresoraCajero(CajeroCajaBean cajeroCajaBean) throws Exception {
        return docIngresoDAO.obtenerImpresoraCajero(cajeroCajaBean);
    }

    public List<ImpresoraBean> obtenerImpresoraCajeroPensiones(CajeroCajaBean cajeroCajaBean) throws Exception {
        return docIngresoDAO.obtenerImpresoraCajeroPensiones(cajeroCajaBean);
    }

    public List<ImpresoraBean> obtenerImpresoraCajeroDocEgreso(CajeroCajaBean cajeroCajaBean) throws Exception {
        return docIngresoDAO.obtenerImpresoraCajeroDocEgreso(cajeroCajaBean);
    }

    public List<CodigoBean> obtenerTipDocumentoPorImpresora(String impresora, String usuario, String uniNeg, Integer idCaja) throws Exception {
        return docIngresoDAO.obtenerTipDocumentoPorImpresora(impresora, usuario, uniNeg, idCaja);
    }

    public List<CodigoBean> obtenerTipDocumentoPorImpresoraPensiones(String impresora, String usuario, String uniNeg, Integer idCaja, String tipoDoc) throws Exception {
        return docIngresoDAO.obtenerTipDocumentoPorImpresoraPensiones(impresora, usuario, uniNeg, idCaja, tipoDoc);
    }

    public List<CodigoBean> obtenerTipDocumentoPorImpresoraComprobante(String impresora, String usuario, String uniNeg, Integer idCaja, String tipoDoc) throws Exception {
        return docIngresoDAO.obtenerTipDocumentoPorImpresoraComprobante(impresora, usuario, uniNeg, idCaja, tipoDoc);
    }

    public List<CodigoBean> obtenerImpresoraParaRecibos(String impresora, String usuario, String uniNeg, Integer idCaja, String tipoDoc) throws Exception {
        return docIngresoDAO.obtenerTipDocumentoPorImpresoraComprobante(impresora, usuario, uniNeg, idCaja, tipoDoc);
    }

    public ImpresoraCajaBean obtenerDetalleTipoDoc(@Param("impresora") String impresora, @Param("usuario") String usuario, @Param("uniNeg") String uniNeg, @Param("idTipoDoc") Integer idTipoDoc, @Param("idCaja") Integer idCaja) throws Exception {
        return docIngresoDAO.obtenerDetalleTipoDoc(impresora, usuario, uniNeg, idTipoDoc, idCaja);
    }

    @Transactional
    public void insertarDocIngreso(DocIngresoBean docIngresoBean, List<DetDocIngresoBean> listaDetDocIngreso, CajaGenBean cajaGenBean,
            Integer cr, List<ProgramacionBean> listaProgramacionSessionBean, SolicitudCajaCHBean solicitudCajaCHBean,
            BigDecimal tc, Boolean flgPension, Boolean flgGenCod, PersonaBean persona) throws Exception {
        ProgramacionService programacionService = BeanFactory.getProgramacionService();
        DocIngresoBean doc = new DocIngresoBean();
        ImpresoraService impresoraService = BeanFactory.getImpresoraService();
        ImpresoraBean impre = new ImpresoraBean();
        impre = impresoraService.buscarPorId(docIngresoBean.getImpresoraCajaBean().getImpresora());
        Integer estadoCupos = 1;
        Integer nroDocInt = 0;
        if (flgPension.equals(true)) {
            doc = null;
            estadoCupos = 1;
            System.out.println("true nrodoc1..." + impre.getActual());
            nroDocInt = impre.getActual() + 1;
            System.out.println("true nrodoc2..." + nroDocInt);
            String nroDoc = nroDocInt.toString();
            doc = docIngresoDAO.validarSerieNroDoc(docIngresoBean.getImpresoraCajaBean().getImpresora().getSerie(), nroDoc, docIngresoBean.getImpresoraCajaBean().getImpresora().getImpresora(), docIngresoBean.getUnidadNegocioBean().getUniNeg());
        } else {
            //rec generalconsta talleres certi
            System.out.println("nrodoc1..." + impre.getActual());
            nroDocInt = impre.getActual() + 1;
            System.out.println("nrodoc2..." + nroDocInt);
            String nroDoc = nroDocInt.toString();
            doc = docIngresoDAO.validarSerieNroDoc(docIngresoBean.getImpresoraCajaBean().getImpresora().getSerie(), nroDoc, docIngresoBean.getImpresoraCajaBean().getImpresora().getImpresora(), docIngresoBean.getUnidadNegocioBean().getUniNeg());
            List<Integer> lista = new ArrayList<>();
            if (listaProgramacionSessionBean != null) {
                if (!listaProgramacionSessionBean.isEmpty()) {
                    for (ProgramacionBean prog : listaProgramacionSessionBean) {
                        lista.add(prog.getIdProgramacion());
                    }
                } else {
                    lista.add(0);
                }
            }
            if (lista.size() > 0) {
                System.out.println(">0");
                estadoCupos = programacionService.obtenerCuposPrograPorIdFor(docIngresoBean.getUnidadNegocioBean().getUniNeg(), lista);
            }
        }
        //si hay cupos
        if (estadoCupos.equals(1)) {
            if (doc == null) {// si doc es null quiere decir que el nro no existe
                CodigoService codigoService = BeanFactory.getCodigoService();
                CajaGenService cajaGenService = BeanFactory.getCajaGenService();
                CodigoBean codigo = new CodigoBean();
                codigo = codigoService.obtenerPorCodigo(new CodigoBean(0, "Pagado", new TipoCodigoBean(MaristaConstantes.TIP_STATUS_CTA_CTE)));
                CodigoBean codStatusDocIng = new CodigoBean();
                codStatusDocIng = codigoService.obtenerPorCodigo(new CodigoBean(0, "Pagado", new TipoCodigoBean(MaristaConstantes.TIP_STATUS_DOCING)));
                docIngresoBean.setTipoStatusDocIng(codStatusDocIng);
                Double montoEfectSoles = (double) Math.round(docIngresoBean.getMontoEfectivoSol() * 100) / 100;
                Double montoEfectDolares = (double) Math.round(docIngresoBean.getMontoEfectivoDol() * 100) / 100;
                Double montoSolesTemp = 0.0;
                Double montoDolaresTemp = 0.0;
                Double montoPos1 = (double) Math.round(docIngresoBean.getMontoPos1() * 100) / 100;//100 
                Double montoPos2 = (double) Math.round(docIngresoBean.getMontoPos2() * 100) / 100;//1000
                Double montoEfe = (double) Math.round(docIngresoBean.getMontoEfectivoSol() * 100) / 100;//0
//
                Integer estado = 0;
                Integer estadoCab = 0;
                Integer estadoCab2 = 0;
                Integer actual = null;

                for (DetDocIngresoBean detalle : listaDetDocIngreso) {
//                    detalle.setMontoSoles(BigDecimal.valueOf((double) Math.round(detalle.getMontoSoles().doubleValue() * 100) / 100));
//                    detalle.setMontoPagado(BigDecimal.valueOf((double) Math.round(detalle.getMontoPagado().doubleValue() * 100) / 100));
//                    System.out.println("getMontoPagado---> "+detalle.getMontoPagado());
//                    System.out.println("getMontoSoles----> "+detalle.getMontoSoles());
                    Integer id = null;
                    id = docIngresoDAO.obtenerDocIngresoPorCtaCobrar(detalle.getCuentasPorCobrarBean().getIdCtasXCobrar(),
                            detalle.getUnidadNegocioBean().getUniNeg());
                    Double montoPagado = 0.0;
                    System.out.println("-------------INICIO--------------------");
                    if (flgPension.equals(true)) {
                        montoPagado = (double) Math.round(detalle.getMontoPagado().doubleValue() * 100) / 100;
                        if (montoPagado <= montoPos1) {
                            docIngresoBean.setMontoPos1(montoPagado);
                            docIngresoBean.setMontoPos2(0.0);
                            docIngresoBean.setMontoEfectivoSol(0.0);
                            montoPos1 = (double) Math.round((montoPos1 - montoPagado) * 100) / 100;
                        } else {
                            montoPagado = (double) Math.round((montoPagado - montoPos1) * 100) / 100;
                            docIngresoBean.setMontoPos1(montoPos1);
                            montoPos1 = 0.0;
                            if (montoPos2 >= montoPagado) {
                                docIngresoBean.setMontoPos2(montoPagado);
                                docIngresoBean.setMontoEfectivoSol(0.0);
                                montoPos2 = (double) Math.round((montoPos2 - montoPagado) * 100) / 100;
                            } else {
                                //pago con efectivo
                                if (montoPagado <= montoEfe) {
                                    docIngresoBean.setMontoEfectivoSol(montoPagado);
                                    montoEfe = (double) Math.round((montoEfe - montoPagado) * 100) / 100;
                                } else {
                                    montoPagado = (double) Math.round((montoPagado - montoEfe) * 100) / 100;
                                    docIngresoBean.setMontoEfectivoSol(montoEfe);
                                    if (montoPagado <= montoPos1) {
                                        docIngresoBean.setMontoPos1(montoPagado);
                                        docIngresoBean.setMontoPos2(0.0);
                                        montoPos1 = (double) Math.round((montoPos1 - montoPagado) * 100) / 100;
                                    } else {
                                        montoPagado = (double) Math.round((montoPagado - montoPos1) * 100) / 100;
                                        docIngresoBean.setMontoPos1(montoPos1);
                                        if (montoPos2 >= montoPagado) {
                                            docIngresoBean.setMontoPos2(montoPagado);
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        if (estadoCab.equals(0)) {
                            for (DetDocIngresoBean deta : listaDetDocIngreso) {
                                montoPagado = montoPagado + (double) Math.round(deta.getMontoPagado().doubleValue() * 100) / 100;
                            }
                            if (docIngresoBean.getIdTipoMoneda().getCodigo().equals(MaristaConstantes.PAGO_MON_SOL)) {
                                if (montoPagado <= montoPos1) {
                                    docIngresoBean.setMontoPos1(montoPagado);
                                    docIngresoBean.setMontoPos2(0.0);
                                    docIngresoBean.setMontoEfectivoSol(0.0);
                                    montoPos1 = (double) Math.round((montoPos1 - montoPagado) * 100) / 100;
                                } else {
                                    montoPagado = (double) Math.round((montoPagado - montoPos1) * 100) / 100;
                                    docIngresoBean.setMontoPos1(montoPos1);
                                    montoPos1 = 0.0;
                                    if (montoPos2 >= montoPagado) {
                                        docIngresoBean.setMontoPos2(montoPagado);
                                        docIngresoBean.setMontoEfectivoSol(0.0);
                                        montoPos2 = (double) Math.round((montoPos2 - montoPagado) * 100) / 100;
                                    } else {
                                        //pago con efectivo
                                        if (montoPagado <= montoEfe) {
                                            docIngresoBean.setMontoEfectivoSol(montoPagado);
                                            montoEfe = (double) Math.round((montoEfe - montoPagado) * 100) / 100;
                                        } else {
                                            montoPagado = (double) Math.round((montoPagado - montoEfe) * 100) / 100;
                                            docIngresoBean.setMontoEfectivoSol(montoEfe);
                                            if (montoPagado <= montoPos1) {
                                                docIngresoBean.setMontoPos1(montoPagado);
                                                docIngresoBean.setMontoPos2(0.0);
                                                montoPos1 = (double) Math.round((montoPos1 - montoPagado) * 100) / 100;
                                            } else {
                                                montoPagado = (double) Math.round((montoPagado - montoPos1) * 100) / 100;
                                                docIngresoBean.setMontoPos1(montoPos1);
                                                if (montoPos2 >= montoPagado) {
                                                    docIngresoBean.setMontoPos2(montoPagado);
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                if (listaDetDocIngreso.size() == 1) {
                                    System.out.println("ooc ing listaDetDocIngreso");
                                }
                            }
                            estadoCab = 1;
                        }
                    }
                    System.out.println("monto: " + montoPagado);

                    docIngresoBean.setAnio(detalle.getCuentasPorCobrarBean().getAnio());
                    if (id == null) {//obtenerDocIngresoPorCtaCobrar
                        if (estado == 0) {
                            System.out.println("nroDocInt: " + nroDocInt);
                            actual = nroDocInt;
//                            actual = docIngresoBean.getImpresoraCajaBean().getImpresora().getActual();
                            estado = 1;
                        } else {
                            if (estadoCab2.equals(0) && flgPension.equals(true)) {
                                actual = actual + 1;
                                docIngresoBean.getImpresoraCajaBean().getImpresora().setActual(actual);
                            }
                        }
                    }
                    if (flgPension.equals(true)) {
                        if (id != null) {
                            docIngresoBean.setIdDocIngreso(id);
                            docIngresoBean.setIdCtaxCobrar(detalle.getCuentasPorCobrarBean().getIdCtasXCobrar());
                            docIngresoBean.setModiPor(docIngresoBean.getCreaPor());
                            if (docIngresoBean.getFechaPago().before(detalle.getCuentasPorCobrarBean().getFechaVenc())) {
                                System.out.println("fecha pago es menor");
                                docIngresoBean.setFlgMasivo("C");
                            } else {
                                System.out.println("fecha pago es mayor?");
                                SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yy");
                                String fechaPago = fecha.format(new Date());
                                String fechaVenc = fecha.format(detalle.getCuentasPorCobrarBean().getFechaVenc());
                                if (fechaPago.equals(fechaVenc)) {
                                    docIngresoBean.setFlgMasivo("C");
                                } else {
                                    docIngresoBean.setFlgMasivo("G");
                                }
                            }
                            docIngresoDAO.modificarDocIngresoPension(docIngresoBean);
                        } else {
                            //RES PAGO
                            FamiliarService familiarService = BeanFactory.getFamiliarService();
                            FamiliarBean familiarResPago = new FamiliarBean();
                            PersonaBean personaBean = new PersonaBean();
                            personaBean.setIdPersona(docIngresoBean.getIdDiscente().getIdPersona());
                            personaBean.setUnidadNegocioBean(docIngresoBean.getUnidadNegocioBean());
                            familiarResPago = familiarService.obtenerResPagoPorId(personaBean);
                            if (familiarResPago != null) {
                                docIngresoBean.setFamiliarBean(familiarResPago.getPersonaBean());
                            }
//                            System.out.println("fecha p " + docIngresoBean.getFechaPago());
//                            System.out.println("fecha ven " + detalle.getCuentasPorCobrarBean().getFechaVenc());
                            if (docIngresoBean.getFechaPago().before(detalle.getCuentasPorCobrarBean().getFechaVenc())) {
                                System.out.println("fecha pago es menor");
                                docIngresoBean.setFlgMasivo("C");
                            } else {
                                System.out.println("fecha pago es mayor?");
                                SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yy");
                                String fechaPago = fecha.format(new Date());
                                String fechaVenc = fecha.format(detalle.getCuentasPorCobrarBean().getFechaVenc());
                                if (fechaPago.equals(fechaVenc)) {
//                                    System.out.println(fechaVenc);
//                                    System.out.println(fechaVenc);
                                    docIngresoBean.setFlgMasivo("C");
                                } else {
                                    docIngresoBean.setFlgMasivo("G");
                                }
                            }

                            docIngresoBean.getImpresoraCajaBean().getImpresora().setActual(actual);
                            docIngresoDAO.insertarDocIngreso(docIngresoBean);
                            ImpresoraBean impresora = new ImpresoraBean();
                            impresora.setActual(actual);
                            System.out.println("impresora.setActual(nroDocInt);" + actual);
//                            impresora.setActual(actual + 1);
                            impresora.setImpresora(docIngresoBean.getImpresoraCajaBean().getImpresora().getImpresora());
                            impresora.setIdTipoDoc(docIngresoBean.getImpresoraCajaBean().getImpresora().getIdTipoDoc());
                            impresora.setUnidadNegocioBean(docIngresoBean.getUnidadNegocioBean());
                            docIngresoDAO.modificarImpresoraActual(impresora);
                        }
                    } else {
                        if (estadoCab2.equals(0)) {
                            System.out.println("--------------+");
                            persona.setUnidadNegocioBean(docIngresoBean.getUnidadNegocioBean());
                            //INSERTANDO PERSONA
                            System.out.println("flag " + flgGenCod);
                            if (flgGenCod.equals(Boolean.FALSE)) {
                                PersonaService personaService = BeanFactory.getPersonaService();
                                if (persona.getIdPersona().equals(MaristaConstantes.SIN_NRODOC_DOCINGRESO)) {
                                    persona.setIdPersona(null);
                                }
                                PersonaBean listaPersonaBean = new PersonaBean();
                                listaPersonaBean = personaService.obtenerPersonaPorNombre(persona);
                                if (listaPersonaBean != null) {
                                    System.out.println("ya existe");
                                    persona.setIdPersona(listaPersonaBean.getIdPersona());
                                    System.out.println("persona->" + persona.getIdPersona());
                                    System.out.println("persona nombre->" + persona.getNombreCompletoDesdeApellidos());
                                    System.out.println("persona " + persona.getIdPersona());
                                    System.out.println("persona nombre" + persona.getIdPersona());
                                    docIngresoBean.setIdDiscente(persona);
                                } else {
                                    System.out.println("no existe");
                                    String cod = null;
                                    if (persona.getIdPersona() == null) {
                                        persona.setIdPersona(cod);
                                    } else {
//                                        System.out.println("aaaaaaaaaaa)");
                                        persona.setIdPersona(persona.getIdPersona());
                                    }

                                    SimpleDateFormat formato = new SimpleDateFormat("yyyy");
                                    String date = formato.format(new Date());
                                    Integer anio = new Integer(date);

                                    persona.setSexo(persona.getSexo());
                                    persona.setCreaPor(docIngresoBean.getCreaPor());
                                    persona.setCreaFecha(new Date());
                                    persona.setUnidadNegocioBean(docIngresoBean.getUnidadNegocioBean());
                                    System.out.println("persona.getIdPersona() " + persona.getIdPersona());
                                    if (persona.getIdPersona() != null) {
                                        if (persona.getIdPersona().equals(MaristaConstantes.SIN_NRODOC_DOCINGRESO)) {
                                            cod = personaService.generarCodigoPersona(anio, docIngresoBean.getUnidadNegocioBean().getUniNeg());
                                        } else {
                                            cod = persona.getIdPersona();
                                        }
                                        persona.setIdPersona(cod);
                                    } else {
                                        cod = personaService.generarCodigoPersona(anio, docIngresoBean.getUnidadNegocioBean().getUniNeg());
                                        System.out.println("codigo codigo codigo: 2" + cod);
                                        persona.setIdPersona(cod);
                                    }
                                    personaService.insertarPersona(persona);
                                    docIngresoBean.getIdDiscente().setIdPersona(cod.toString());

                                    docIngresoBean.getIdDiscente().setApepat(persona.getApepat());
                                    docIngresoBean.getIdDiscente().setApemat(persona.getApemat() + ",");
                                    docIngresoBean.getIdDiscente().setNombre(persona.getNombre());

                                    System.out.println(" getNombreCompleto() " + docIngresoBean.getIdDiscente().getNombreCompleto());
                                }
                            }
                            docIngresoBean.getImpresoraCajaBean().getImpresora().setActual(nroDocInt);
                            docIngresoDAO.insertarDocIngreso(docIngresoBean);
                            estadoCab2 = 1;
                        }
                    }
                    Double montoPag = (double) Math.round(detalle.getMontoPagado().doubleValue() * 100) / 100;
                    if (detalle.getCentroResponsabilidadBean().getCr() != null) {
                        detalle.getCentroResponsabilidadBean().setCr(detalle.getCentroResponsabilidadBean().getCr());
                    } else {
                        detalle.getCentroResponsabilidadBean().setCr(cr);
                    }
                    detalle.setCreaFecha(new Date());
                    if (montoEfectSoles.equals(0.0) && docIngresoBean.getIdTipoMoneda().getCodigo().equals(MaristaConstantes.PAGO_MON_DOL)) {
                        detalle.setMontoDolares(BigDecimal.valueOf(montoPag));
                        detalle.setMontoSoles(new BigDecimal(0.0));
                    } else {
                        if (montoEfectDolares.equals(0.0)) {
                            detalle.setMontoSoles(BigDecimal.valueOf(montoPag));
                            detalle.setMontoDolares(new BigDecimal(0.0));
                        } else {
                            if (docIngresoBean.getIdTipoMoneda().getCodigo().equals(MaristaConstantes.PAGO_MON_SOL)) {
                                if (montoPag <= montoEfectSoles) {
                                    montoEfectSoles = (double) Math.round((montoEfectSoles - montoPag) * 100) / 100;
                                    detalle.setMontoSoles(BigDecimal.valueOf(montoPag));
                                    detalle.setMontoDolares(new BigDecimal(0.0));
                                } else {
                                    montoSolesTemp = montoEfectSoles;
                                    detalle.setMontoSoles(BigDecimal.valueOf(montoSolesTemp));
                                    montoEfectSoles = 0.0;
                                    if (!docIngresoBean.getIdTipoMoneda().getCodigo().equals(MaristaConstantes.PAGO_MON_DOL)) {
                                        montoPag = (double) Math.round((montoPag - montoSolesTemp) * 100) / 100;
                                        montoDolaresTemp = (double) Math.round((montoPag / tc.doubleValue()) * 100) / 100;
                                        detalle.setMontoDolares(BigDecimal.valueOf(montoDolaresTemp));
                                        montoEfectDolares = (double) Math.round((montoEfectDolares - (montoDolaresTemp)) * 100) / 100;
                                    } else {
                                        montoPag = (double) Math.round((montoPag - (montoSolesTemp / tc.doubleValue())) * 100) / 100;
                                        montoDolaresTemp = (double) Math.round((montoPag) * 100) / 100;
                                        detalle.setMontoDolares(BigDecimal.valueOf(montoDolaresTemp));
                                        montoEfectDolares = (double) Math.round((montoEfectDolares - (montoDolaresTemp)) * 100) / 100;
                                    }
                                }
                            } else {
                                if (listaDetDocIngreso.size() == 1) {
                                    Double montoS = (double) Math.round((montoEfectSoles + montoPos1 + montoPos2) * 100) / 100;
                                    detalle.setMontoSoles((BigDecimal.valueOf(montoS)));
                                    detalle.setMontoDolares(new BigDecimal(montoEfectDolares));
                                }
                            }
                        }
                    }
                    if (flgPension.equals(true)) {
                        if (id != null) {
                            docIngresoBean.setIdDocIngreso(id);
                            docIngresoBean.setIdCtaxCobrar(detalle.getCuentasPorCobrarBean().getIdCtasXCobrar());
                            detalle.setModiPor(docIngresoBean.getCreaPor());
                            docIngresoDAO.modificarDetDocIngresoPension(detalle);
                            detalle.setIdDocIngreso(detalle.getDocIngresoBean().getIdDocIngreso());
                        } else {
                            detalle.setDocIngresoBean(docIngresoBean);
                            docIngresoDAO.insertarDocIngresoDetalle(detalle);
                            detalle.setIdDocIngreso(detalle.getDocIngresoBean().getIdDocIngreso());
                        }
                    } else {
                        detalle.setDocIngresoBean(docIngresoBean);
                        docIngresoDAO.insertarDocIngresoDetalle(detalle);
                        detalle.setIdDocIngreso(detalle.getDocIngresoBean().getIdDocIngreso());
                    }
                    if (detalle.getCuentasPorCobrarBean().getIdCtasXCobrar() != null) {
                        CuentasPorCobrarBean cuentas = new CuentasPorCobrarBean();
                        cuentas.setIdCtasXCobrar(detalle.getCuentasPorCobrarBean().getIdCtasXCobrar());
                        cuentas.setUnidadNegocioBean(detalle.getUnidadNegocioBean());
                        cuentas.setDocIngresoBean(docIngresoBean);
                        cuentas.setMontoPagado(detalle.getMontoPagado());
                        cuentas.setFechaPago(docIngresoBean.getFechaPago());
                        cuentas.setIdTipoStatusCtaCte(codigo);
                        docIngresoDAO.modificarPagoCtasXCobrar(cuentas);
                    }
                    System.out.println("3: " + detalle.getDocIngresoBean().getIdDocIngreso());
                    System.out.println("-------------FIN--------------------");
//                    List<ProgramacionBean> listaPro = new ArrayList<>(); 
//                    listaPro.add(detalle.)
                    System.out.println("programacion: "+detalle.getProgramacionBean());
                    System.out.println("programacion2: "+detalle.getProgramacionBean().getIdProgramacion());
                    if (detalle.getProgramacionBean().getIdProgramacion() != null) {
                        CursoTallerService cursoTallerService = BeanFactory.getCursoTallerService();
                        CursoTallerBean cursoTallerBean = new CursoTallerBean();
                        cursoTallerBean.setProgramacionBean(detalle.getProgramacionBean());
                        cursoTallerBean.getPersonaBean().setIdPersona(docIngresoBean.getIdDiscente().getIdPersona());
                        cursoTallerBean.setFechaInscripcion(new Date());
                        cursoTallerBean.setUnidadNegocioBean(docIngresoBean.getUnidadNegocioBean());
                        cursoTallerBean.setCreaPor(docIngresoBean.getCreaPor());
                        cursoTallerBean.setIdConcepto(detalle.getProgramacionBean().getConceptoUniNegBean().getConceptoBean().getIdConcepto());
                        cursoTallerService.insertarCursoTaller(cursoTallerBean);
                        ProgramacionBean curso = new ProgramacionBean();
                        curso.setIdProgramacion(detalle.getProgramacionBean().getIdProgramacion());
                        curso.setUnidadNegocioBean(detalle.getUnidadNegocioBean());
                        programacionService.modificarProgramacionCupos(curso);
                    }
                }

                if (!flgPension.equals(true)) {
                    ImpresoraBean impresora = new ImpresoraBean();
//                    impresora.setActual(docIngresoBean.getImpresoraCajaBean().getImpresora().getActual() + 1);
                    impresora.setActual(nroDocInt);
                    impresora.setImpresora(docIngresoBean.getImpresoraCajaBean().getImpresora().getImpresora());
                    impresora.setIdTipoDoc(docIngresoBean.getImpresoraCajaBean().getImpresora().getIdTipoDoc());
                    impresora.setUnidadNegocioBean(docIngresoBean.getUnidadNegocioBean());
                    docIngresoDAO.modificarImpresoraActual(impresora);
                    cajaGenService.modificarIngresoSolYDol(cajaGenBean);

                    if (listaProgramacionSessionBean != null) {
                        if (!listaProgramacionSessionBean.isEmpty()) {
//                            CursoTallerService cursoTallerService = BeanFactory.getCursoTallerService();
                            for (ProgramacionBean prog : listaProgramacionSessionBean) {
//                                CursoTallerBean cursoTallerBean = new CursoTallerBean();
//                                cursoTallerBean.setProgramacionBean(prog);
//                                cursoTallerBean.getPersonaBean().setIdPersona(docIngresoBean.getIdDiscente().getIdPersona());
//                                cursoTallerBean.setFechaInscripcion(new Date());
//                                cursoTallerBean.setUnidadNegocioBean(docIngresoBean.getUnidadNegocioBean());
//                                cursoTallerBean.setCreaPor(docIngresoBean.getCreaPor());
//                                cursoTallerBean.setIdConcepto(prog.getConceptoUniNegBean().getConceptoBean().getIdConcepto());
//                                cursoTallerService.insertarCursoTaller(cursoTallerBean);
//                                programacionService.modificarProgramacionCupos(prog);
                            }
                        }
                    }
                    if (solicitudCajaCHBean != null && solicitudCajaCHBean.getIdSolicitudCajaCh() != null) {
                        SolicitudCajaCHDAO solicitudCajaCHDAO = BeanFactory.getSolicitudCajaCHDAO();
                        solicitudCajaCHBean.setTipoEstRend(new CodigoBean(MaristaConstantes.COD_REND_FINALIZADO));
                        solicitudCajaCHDAO.modificarTipoEstRend(solicitudCajaCHBean);
                        DocEgresoBean docEgresoBean = new DocEgresoBean();
                        DocEgresoDAO docEgresoDAO = BeanFactory.getDocEgresoDAO();
                        docEgresoBean = docEgresoDAO.obtenerDocEgresoPorSol(solicitudCajaCHBean);
                        docEgresoBean.setFlgRendicion(Boolean.TRUE);
                        docEgresoDAO.cambiarEstadoRendicionDoc(docEgresoBean);
                        CajaChicaLiquidacionDAO cajaChicaLiquidacionDAO = BeanFactory.getCajaChicaLiquidacionDAO();
//                    CajaChicaLiquidacionBean cajaChicaLiquidacionBean = new CajaChicaLiquidacionBean();
                        Double monto = 0.0;
                        for (DetDocIngresoBean detalle : listaDetDocIngreso) {
                            monto = monto + detalle.getMontoPagado().doubleValue();
                            monto = (double) Math.round((monto) * 100) / 100;
                        }
                        cajaChicaLiquidacionDAO.modificarDevCajaChicaLiquidacion(docIngresoBean.getUnidadNegocioBean().getUniNeg(), solicitudCajaCHBean.getIdSolicitudCajaCh(), monto);
                    }
                } else {
                    cajaGenService.modificarIngresoSolYDol(cajaGenBean);
                }
                docIngresoBean.setEstadoRegIng(1);
                if (flgPension.equals(true)) {
                    System.out.println("sp estado");
                    EstudianteService estudianteService = BeanFactory.getEstudianteService();
                    EstudianteBean est = new EstudianteBean();
                    est.getPersonaBean().setIdPersona(docIngresoBean.getIdDiscente().getIdPersona());
                    est.getPersonaBean().setUnidadNegocioBean(docIngresoBean.getUnidadNegocioBean());
                    estudianteService.SP_actualizarEstadoEst(est);
                } else {
                    System.out.println("sp xxxxxxxxx");
                }
            } else {
                docIngresoBean.setEstadoRegIng(0);
            }
            //end
        } else {
            //#3
            docIngresoBean.setEstadoRegIng(3);
        }
    }

    public CajaGenBean obtenerCajaGeneral(CajaBean cajaBean) throws Exception {
        return docIngresoDAO.obtenerCajaGeneral(cajaBean);
    }

    public List<DocIngresoRepBean> obtenerDocIngreso(DocIngresoBean docIngresoBean) throws Exception {
        return docIngresoDAO.obtenerDocIngreso(docIngresoBean);
    }

    public List<DocIngresoRepBean> obtenerFormatoDocIngreso(DocIngresoBean docIngresoBean) throws Exception {
        return docIngresoDAO.obtenerFormatoDocIngreso(docIngresoBean);
    }

    public List<DocIngresoRepBean> obtenerFormatoDocIngresoFor(String uniNeg, List<Integer> ids) throws Exception {
        return docIngresoDAO.obtenerFormatoDocIngresoFor(uniNeg, ids);
    }

    public Integer obtenerFormatoDocIngresoPension(String uniNeg, List<Integer> ids) throws Exception {
        return docIngresoDAO.obtenerFormatoDocIngresoPension(uniNeg, ids);
    }

    public Integer obtenerDocIngresoPorCtaCobrar(Integer idDocIngreso, String uniNeg) throws Exception {
        return docIngresoDAO.obtenerDocIngresoPorCtaCobrar(idDocIngreso, uniNeg);
    }

    public List<DetDocIngresoRepBean> obtenerDetalleDocIngreso(Integer idDocIngreso, String uniNeg) throws Exception {
        return docIngresoDAO.obtenerDetalleDocIngreso(idDocIngreso, uniNeg);
    }

    public List<DetDocIngresoRepBean> obtenerFiltroDetDocIngreso(DetDocIngresoRepBean detDocIngresoRepBean) throws Exception {
        return docIngresoDAO.obtenerFiltroDetDocIngreso(detDocIngresoRepBean);
    }

    public List<DetDocIngresoRepBean> obtenerFiltroDetPagoMatricula(DetDocIngresoRepBean detDocIngresoRepBean) throws Exception {
        return docIngresoDAO.obtenerFiltroDetPagoMatricula(detDocIngresoRepBean);
    }

    public List<DetDocIngresoRepBean> obtenerFormatoDetalleDocIngreso(Integer idDocIngreso, String uniNeg) throws Exception {
        return docIngresoDAO.obtenerFormatoDetalleDocIngreso(idDocIngreso, uniNeg);
    }

    public List<DetDocIngresoRepBean> obtenerFormatoDetalleDocIngresoConDscto(Integer idDocIngreso, String uniNeg, Integer flgDscto) throws Exception {
        return docIngresoDAO.obtenerFormatoDetalleDocIngresoConDscto(idDocIngreso, uniNeg, flgDscto);
    }

    public List<DocIngresoBean> obtenerFiltroDocIngreso(DocIngresoBean docIngresoBean) throws Exception {
        return docIngresoDAO.obtenerFiltroDocIngreso(docIngresoBean);
    }

    public DocIngresoBean obtenerDocIngresoPorId(Integer idDocIngreso, String uniNeg) throws Exception {
        return docIngresoDAO.obtenerDocIngresoPorId(idDocIngreso, uniNeg);
    }

    public DocIngresoBean obtenerDocIngresoCuotaPorDiscente(String idDiscente, String uniNeg, String concepto) throws Exception {
        return docIngresoDAO.obtenerDocIngresoCuotaPorDiscente(idDiscente, uniNeg, concepto);
    }

    @Transactional
    public void cambiarDatBasicosDocIngreso(DocIngresoBean docIngresoBean) throws Exception {

        PersonaService personaService = BeanFactory.getPersonaService();
        personaService.modificarPersona(docIngresoBean.getIdDiscente());

        String nomDiscente = "";
        StringBuilder sb = new StringBuilder();
        if (docIngresoBean.getIdDiscente().getApepat() != null) {
            sb.append(docIngresoBean.getIdDiscente().getApepat()).append(" ");
        }
        if (docIngresoBean.getIdDiscente().getApemat() != null) {
            sb.append(docIngresoBean.getIdDiscente().getApemat()).append(" ");
        }
        if (docIngresoBean.getIdDiscente().getNombre() != null) {
            sb.append(docIngresoBean.getIdDiscente().getNombre());
        }
        nomDiscente = sb.toString();
        docIngresoBean.setNombreDiscente(nomDiscente);
        docIngresoDAO.cambiarDatBasicosDocIngreso(docIngresoBean);
    }

    @Transactional
    public void cambiarEstadoDocIngreso(DocIngresoBean docIngresoBean, String estado, UsuarioBean usu) throws Exception {
        CajaGenService cajaGenService = BeanFactory.getCajaGenService();
        CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();

        CuentasPorCobrarBean cta = new CuentasPorCobrarBean();
        cta = cuentasPorCobrarService.validarDocIngresoEnCtaCte(docIngresoBean.getIdDocIngreso(), usu.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        switch (estado) {
            case MaristaConstantes.COD_STA_DOC_ANULADO:
                //Datos del doc cajagen
                BigDecimal efecCajaGenPOS1 = new BigDecimal(0.0);
                BigDecimal efecCajaGenPOS2 = new BigDecimal(0.0);
                BigDecimal efecCajaGenSoles = new BigDecimal(0.0);
                BigDecimal efecCajaGenDolares = new BigDecimal(0.0);
                //Datos del doc ingreso
                BigDecimal efectivoPOS1 = new BigDecimal(0.0);
                BigDecimal efectivoPOS2 = new BigDecimal(0.0);
                BigDecimal efectivoSoles = new BigDecimal(0.0);
                BigDecimal efectivoDolares = new BigDecimal(0.0);
                //Nuevos montos
                BigDecimal actPOS1 = new BigDecimal(0.0);
                BigDecimal actPOS2 = new BigDecimal(0.0);
                BigDecimal actEfecSoles = new BigDecimal(0.0);
                BigDecimal actEfecDolares = new BigDecimal(0.0);
                CajaGenBean cjaGen = new CajaGenBean();
                cjaGen.setIdCajaGen(docIngresoBean.getCajaGenBean().getIdCajaGen());
                cjaGen.setUniNeg(usu.getPersonalBean().getUnidadNegocioBean());
                cjaGen = cajaGenService.obtenerPorId(cjaGen);
                //setteando montos  de doc ingreso
                efectivoSoles = new BigDecimal(docIngresoBean.getMontoEfectivoSol());
                efectivoDolares = new BigDecimal(docIngresoBean.getMontoEfectivoDol());
                efectivoPOS1 = new BigDecimal(docIngresoBean.getMontoPos1());
                efectivoPOS2 = new BigDecimal(docIngresoBean.getMontoPos2());
                efecCajaGenPOS1 = new BigDecimal(cjaGen.getIngresoPos1());
                efecCajaGenPOS2 = new BigDecimal(cjaGen.getIngresoPos2());
                efecCajaGenSoles = new BigDecimal(cjaGen.getIngresoSol());
                efecCajaGenDolares = new BigDecimal(cjaGen.getIngresoDol());
                //actualizando montos
                actPOS1 = efecCajaGenPOS1.subtract(efectivoPOS1);
                actPOS2 = efecCajaGenPOS2.subtract(efectivoPOS2);
                actEfecSoles = efecCajaGenSoles.subtract(efectivoSoles);
                actEfecDolares = efecCajaGenDolares.subtract(efectivoDolares);
                cjaGen.setIngresoSol(actEfecSoles.doubleValue());
                cjaGen.setIngresoDol(actEfecDolares.doubleValue());
                cjaGen.setIngresoPos1(actPOS1.doubleValue());
                cjaGen.setIngresoPos2(actPOS2.doubleValue());
                cajaGenService.modificarIngresoSolYDol(cjaGen);
                docIngresoBean.setUnidadNegocioBean(usu.getPersonalBean().getUnidadNegocioBean());
                docIngresoBean.setCreaStatus(usu.getUsuario());

                docIngresoBean.setMontoEfectivoSol(0.0);
                docIngresoBean.setMontoEfectivoDol(0.0);
                docIngresoBean.setMontoPos1(0.0);
                docIngresoBean.setMontoPos2(0.0);
//                if (cta != null) {
//                    if (cta.getIdCtasXCobrar() != null) {
//                        docIngresoBean.setIdCtaxCobrar(cta.getIdCtasXCobrar());
//                    }
//                } else {
//                    docIngresoBean.setIdCtaxCobrar(0);
//                }
                docIngresoDAO.cambiarEstadoDocIngreso(docIngresoBean);
                docIngresoDAO.modificarDetDocIngresoPensionAnulacion(docIngresoBean);
//                docIngresoDAO.anularDetDocIngresoPorIdDocIng(docIngresoBean);
                break;
            case MaristaConstantes.COD_STA_DOC_DEVUELTO:
                docIngresoBean.setCreaStatus(usu.getUsuario());
                docIngresoBean.setUnidadNegocioBean(usu.getPersonalBean().getUnidadNegocioBean());
                docIngresoDAO.cambiarEstadoDocIngreso(docIngresoBean);
                break;
            case MaristaConstantes.COD_STA_DOC_PAGADO:
                docIngresoBean.setCreaStatus(usu.getUsuario());
                docIngresoBean.setUnidadNegocioBean(usu.getPersonalBean().getUnidadNegocioBean());
                docIngresoDAO.cambiarEstadoDocIngreso(docIngresoBean);
                break;
        }

        List<CuentasPorCobrarBean> listaBean = new ArrayList<>();
        listaBean = cuentasPorCobrarService.obtenerCuentaPorIdDocIngreso(docIngresoBean.getIdDocIngreso(), usu.getPersonalBean().getUnidadNegocioBean().getUniNeg());

        if (!listaBean.isEmpty()) {
            for (CuentasPorCobrarBean ctasPorCobrar : listaBean) {
                ctasPorCobrar.setUnidadNegocioBean(usu.getPersonalBean().getUnidadNegocioBean());
                ctasPorCobrar.getIdTipoStatusCtaCte().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STATUS_CTA_CTE);
                ctasPorCobrar.getIdTipoStatusCtaCte().setCodigo(MaristaConstantes.ESTADO_PENDIENTE);
                ctasPorCobrar.setMontoPagado(BigDecimal.ZERO);
                ctasPorCobrar.setFechaPago(null);
                ctasPorCobrar.setModiPor(usu.getUsuario());
                StringBuilder sb = new StringBuilder();
                if (docIngresoBean.getTipoStatusDocIng().getCodigo() != null) {
                    sb.append(docIngresoBean.getTipoStatusDocIng().getCodigo()).append(" - ");
                }
                if (docIngresoBean.getFechaPagoVista() != null) {
                    sb.append(docIngresoBean.getFechaPagoVista()).append(" - ");
                }
                if (docIngresoBean.getSerieNroDoc() != null) {
                    sb.append("Rec. Nro.:").append(docIngresoBean.getSerieNroDoc());
                }
                ctasPorCobrar.setObs(sb.toString());
                cuentasPorCobrarService.cambiarEstadoCtaPorCobrar(ctasPorCobrar);
            }
        }
    }

    @Transactional
    public void generaComprobanteMasivo(List<MatriculaBean> listaMatriculaEstudianteMasivoBean, List<Integer> listaMesSel, String usuario, ImpresoraBean impresoraBean, Integer anio) throws Exception {
        System.out.println("recibo2: " + impresoraBean.getIdTipoDoc().getIdCodigo());
        long a = System.currentTimeMillis();
        CuentasPorCobrarDAO cuentasPorCobrarDAO = BeanFactory.getCuentasPorCobrarDAO();
        String uniNeg = listaMatriculaEstudianteMasivoBean.get(0).getEstudianteBean().getPersonaBean().getUnidadNegocioBean().getUniNeg();
        List<List<CuentasPorCobrarBean>> listaCuenta = new ArrayList<>();
        DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
        DocIngresoBean doc = new DocIngresoBean();
        doc.getUnidadNegocioBean().setUniNeg(uniNeg);
        doc.setSerie(impresoraBean.getSerie());
        Integer nroDocMax = docIngresoService.obtenerMaxNroDocSerie(doc);
        for (int i = 0; i < listaMatriculaEstudianteMasivoBean.size(); i++) {
            List<CuentasPorCobrarBean> listaCtasxCobrar = cuentasPorCobrarDAO.
                    obtenerCtasXCobrarXMesesNoGenerado(listaMesSel, listaMatriculaEstudianteMasivoBean.get(i).getEstudianteBean().getPersonaBean().
                            getUnidadNegocioBean().getUniNeg(), listaMatriculaEstudianteMasivoBean.get(i).getAnio(),
                            listaMatriculaEstudianteMasivoBean.get(i).
                            getEstudianteBean().getPersonaBean().getIdPersona());
            System.out.println(i + " size " + listaCtasxCobrar.size());
            listaCuenta.add(listaCtasxCobrar);
            for (int j = 0; j < listaCtasxCobrar.size(); j++) {
                System.out.println(j + " de " + listaCtasxCobrar.size());
                DocIngresoBean docIngresoBean = new DocIngresoBean();
//                docIngresoBean.setSerie(impresoraBean.getSerie());
                System.out.println("recibo3: " + impresoraBean.getIdTipoDoc().getIdCodigo());
                docIngresoBean.getImpresoraCajaBean().getIdTipoDoc().getIdTipoDoc().setIdCodigo(impresoraBean.getIdTipoDoc().getIdCodigo());
//                docIngresoBean.setNroDoc(impresoraBean.getActual().toString());
                docIngresoBean.setDireccion(listaMatriculaEstudianteMasivoBean.get(i).getEstudianteBean().getViaDomi());
                docIngresoBean.setTelefono(listaMatriculaEstudianteMasivoBean.get(i).getEstudianteBean().getTelefono1Domi());
                docIngresoBean.setUnidadNegocioBean(listaMatriculaEstudianteMasivoBean.get(i).getEstudianteBean().getPersonaBean().getUnidadNegocioBean());
                docIngresoBean.setIdDiscente(listaMatriculaEstudianteMasivoBean.get(i).getEstudianteBean().getPersonaBean());
                docIngresoBean.setCuentasPorCobrarBean(listaCtasxCobrar.get(j));
                docIngresoBean.setFamiliarBean(listaMatriculaEstudianteMasivoBean.get(i).getEstudianteBean().getRespPagoBean());
                docIngresoBean.setAnio(listaMatriculaEstudianteMasivoBean.get(i).getAnio());
                docIngresoBean.setCreaPor(usuario);
                docIngresoBean.setCreaFecha(new Date());
                docIngresoBean.setSerie(impresoraBean.getSerie());
                docIngresoBean.setNroDoc(impresoraBean.getActual().toString());
                docIngresoBean.getUnidadNegocioBean().setUniNeg(uniNeg);

//                ImpresoraCajaBean impresoraCajaBean = new ImpresoraCajaBean();
//                if (impresoraBean.getActual() != null && impresoraBean.getSerie() != null) {
//                    impresoraCajaBean.setImpresora(impresoraBean);
//                    impresoraCajaBean.getImpresora().setActual(impresoraBean.getActual() + 1);
//                    ImpresoraService impresoraService = BeanFactory.getImpresoraService();
//                    impresoraService.cambiarNro(impresoraBean);
//                }
//                docIngresoBean.setImpresoraCajaBean(impresoraCajaBean);
                docIngresoBean.setFlgMasivo("G");
                if (listaCtasxCobrar.get(j).getIdTipoMoneda().getIdCodigo() == null) {
                    listaCtasxCobrar.get(j).getIdTipoMoneda().setIdCodigo(MaristaConstantes.COD_SOLES);
                }
                docIngresoBean.getIdTipoMoneda().setIdCodigo(listaCtasxCobrar.get(j).getIdTipoMoneda().getIdCodigo());
                docIngresoDAO.insertarDocIngreso(docIngresoBean);

                DetDocIngresoBean detDocIngresoBean = new DetDocIngresoBean();
                detDocIngresoBean.setDocIngresoBean(docIngresoBean);
                detDocIngresoBean.setConceptoBean(listaCtasxCobrar.get(j).getConceptoBean());
                detDocIngresoBean.setMonto(listaCtasxCobrar.get(j).getMonto());
                detDocIngresoBean.setDscto(listaCtasxCobrar.get(j).getDscto());
                detDocIngresoBean.setDsctoBeca(listaCtasxCobrar.get(j).getDsctoBeca());
                detDocIngresoBean.setIdTipoDscto(listaCtasxCobrar.get(j).getIdTipoDscto());
                detDocIngresoBean.setIdTipoMotivoDscto(listaCtasxCobrar.get(j).getIdTipoMotivoDscto());
                detDocIngresoBean.setCuentaD(listaCtasxCobrar.get(j).getCuentaD());
                detDocIngresoBean.setCuentaH(listaCtasxCobrar.get(j).getCuentaH());
                detDocIngresoBean.setCentroResponsabilidadBean(listaCtasxCobrar.get(j).getCentroResponsabilidadBean());
                detDocIngresoBean.setCreaPor(usuario);
                detDocIngresoBean.setCreaFecha(new Date());
                detDocIngresoBean.setMontoPagado(BigDecimal.ZERO);
                detDocIngresoBean.setUnidadNegocioBean(docIngresoBean.getUnidadNegocioBean());
                detDocIngresoBean.setCuentasPorCobrarBean(listaCtasxCobrar.get(j));
                System.out.println(">>>>>" + listaCtasxCobrar.get(j).getAnio());
                System.out.println(">>>>>" + listaCtasxCobrar.get(j).getConceptoBean().getNombre());
                detDocIngresoBean.setReferencia(listaCtasxCobrar.get(j).getConceptoBean().getNombre().concat(" - ").concat(listaCtasxCobrar.get(j).getAnio().toString()));
                System.out.println(">>>>>" + detDocIngresoBean.getReferencia());
                docIngresoDAO.insertarDocIngresoDetalle(detDocIngresoBean);
                ImpresoraCajaBean impresoraCajaBean = new ImpresoraCajaBean();
                if (impresoraBean.getActual() != null && impresoraBean.getSerie() != null) {
                    impresoraCajaBean.setImpresora(impresoraBean);
                    impresoraCajaBean.getImpresora().setActual(impresoraBean.getActual() + 1);
                    ImpresoraService impresoraService = BeanFactory.getImpresoraService();
                    impresoraService.cambiarNro(impresoraBean);
                }
                docIngresoBean.setImpresoraCajaBean(impresoraCajaBean);
                docIngresoDAO.ponerNroDoc(docIngresoBean);

            }

//            List<DocIngresoBean> listDocIngreso = new ArrayList<>();
//            listDocIngreso = docIngresoDAO.obtenerDocIngresosSinNroDoc(uniNeg, anio);
//            for (DocIngresoBean docIngreso : listDocIngreso) {
//                 docIngreso.setSerie(impresoraBean.getSerie());
//                docIngreso.setNroDoc(impresoraBean.getActual().toString());
//                docIngreso.getUnidadNegocioBean().setUniNeg(uniNeg);
//                ImpresoraCajaBean impresoraCajaBean = new ImpresoraCajaBean();
//                if (impresoraBean.getActual() != null && impresoraBean.getSerie() != null) {
//                    impresoraCajaBean.setImpresora(impresoraBean);
//                    impresoraCajaBean.getImpresora().setActual(impresoraBean.getActual() + 1);
//                    ImpresoraService impresoraService = BeanFactory.getImpresoraService();
//                    impresoraService.cambiarNro(impresoraBean);
//                }
//                docIngreso.setImpresoraCajaBean(impresoraCajaBean);
//                docIngresoDAO.ponerNroDoc(docIngreso);
//            }
        }

        modificarCuentaRecibo(listaCuenta, uniNeg);
        long b = System.currentTimeMillis();
        System.out.println("tiempo: " + (b - a));
    }

    public Integer obtenerMaxNroDocSerie(DocIngresoBean docIngresoBean) throws Exception {
        return docIngresoDAO.obtenerMaxNroDocSerie(docIngresoBean);
    }

    @Transactional
    public void modificarCuentaRecibo(List<List<CuentasPorCobrarBean>> listaCuentasPorCobrarBean, String uniNeg) throws Exception {
        for (int i = 0; i < listaCuentasPorCobrarBean.size(); i++) {
            for (int j = 0; j < listaCuentasPorCobrarBean.get(i).size(); j++) {
                List<DetDocIngresoBean> listaDetDocIngresoBean = new ArrayList<>();
                listaDetDocIngresoBean = docIngresoDAO.obtenerDetoDocId(uniNeg, listaCuentasPorCobrarBean.get(i).get(j).getIdCtasXCobrar());
                CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
                for (DetDocIngresoBean det : listaDetDocIngresoBean) {
                    CuentasPorCobrarBean cuentasPorCobrarBean = new CuentasPorCobrarBean();
                    cuentasPorCobrarBean.getDocIngresoBean().setIdDocIngreso(det.getIdDocIngreso());
                    cuentasPorCobrarBean.setIdCtasXCobrar(det.getIdCtasXCobrar());
                    cuentasPorCobrarBean.getUnidadNegocioBean().setUniNeg(uniNeg);
                    cuentasPorCobrarService.modificarCuentaDocIngreso(cuentasPorCobrarBean);
                }
            }
        }
    }

    public List<ReciboPensionRepBean> obtenerReporteMasivo(List<MatriculaBean> listaMatricula, Integer mes, String flgMasivo, Integer flgMas) throws Exception {
        List<String> listaEstudiantes = new ArrayList<>();
        for (MatriculaBean matriculaBean : listaMatricula) {
            listaEstudiantes.add(matriculaBean.getEstudianteBean().getPersonaBean().getIdPersona());
        }
        return docIngresoDAO.obtenerReporteMasivo(listaEstudiantes, listaMatricula.get(0).getUnidadNegocioBean().getUniNeg(), listaMatricula.get(0).getAnio(), mes, flgMasivo, flgMas);
    }

    public List<ReciboPensionRepBean> obtenerReporteMasivoIngreso(List<MatriculaBean> listaMatricula, Integer mes, String flgMasivo, Integer flgMas) throws Exception {
        List<String> listaEstudiantes = new ArrayList<>();
        for (MatriculaBean matriculaBean : listaMatricula) {
            listaEstudiantes.add(matriculaBean.getEstudianteBean().getPersonaBean().getIdPersona());
        }
        return docIngresoDAO.obtenerReporteMasivo(listaEstudiantes, listaMatricula.get(0).getUnidadNegocioBean().getUniNeg(), listaMatricula.get(0).getAnio(), mes, flgMasivo, flgMas);
    }

    public List<DocIngresoRepBean> obtenerReporteMasivoIng(String uniNeg, Integer anio, Integer mes, String flgMasivo, Integer flgMas, MatriculaBean matriculaBean) throws Exception {
        return docIngresoDAO.obtenerReporteMasivoIng(uniNeg, anio, mes, flgMasivo, flgMas, matriculaBean);
    }

    public List<DocIngresoRepBean> obtenerRecDocIngresoForSinOrderBy(String uniNeg, List<Integer> ids, Integer orden) throws Exception {
        return docIngresoDAO.obtenerRecDocIngresoForSinOrderBy(uniNeg, ids, orden);
    }

    public List<DocIngresoRepBean> obtenerRecDocIngresoFor(String uniNeg, List<Integer> ids) throws Exception {
        return docIngresoDAO.obtenerRecDocIngresoFor(uniNeg, ids);
    }

    public List<DocIngresoABRepBean> obtenerRecDocIngresoABFor(String uniNeg, List<Integer> ids) throws Exception {
        return docIngresoDAO.obtenerRecDocIngresoABFor(uniNeg, ids);
    }

    public List<DocIngresoRepBean> obtenerRecDocIngresoForForSimple(String uniNeg, List<Integer> ids) throws Exception {
        return docIngresoDAO.obtenerRecDocIngresoForForSimple(uniNeg, ids);
    }

    public List<DetDocIngresoRepBean> obtenerRecDetDocIngreso(String uniNeg, Integer idDocIngreso, Integer mora, Integer dscto, Integer beca) throws Exception {
        return docIngresoDAO.obtenerRecDetDocIngreso(uniNeg, idDocIngreso, mora, dscto, beca);
    }

    public List<DetDocIngresoRepBean> obtenerRecDetDocIngresoMas(String uniNeg, Integer idDocIngreso, Integer mora, Integer dscto, Integer beca, Integer infoMonto) throws Exception {
        return docIngresoDAO.obtenerRecDetDocIngresoMas(uniNeg, idDocIngreso, mora, dscto, beca, infoMonto);
    }

    public List<DocIngresoRepBean> obtenerRecDocIngresoForSinOrderByUnico(String uniNeg, Integer ids, Integer orden) throws Exception {
        return docIngresoDAO.obtenerRecDocIngresoForSinOrderByUnico(uniNeg, ids, orden);
    }

    //Reporte Banco 
    public List<DocIngresoBean> obtenerFiltroDocIngresoBanco(DocIngresoBean docIngresoBean) throws Exception {
        return docIngresoDAO.obtenerFiltroDocIngresoBanco(docIngresoBean);
    }

    public void modificarEstadoImpreso(DocIngresoBean docIngresoBean) throws Exception {
        docIngresoDAO.modificarEstadoImpreso(docIngresoBean);
    }

    public Integer obtenerNumAnulados(DocIngresoBean docIngresoBean) throws Exception {
        return docIngresoDAO.obtenerNumAnulados(docIngresoBean);
    }

    @Transactional
    public Object eliminarRecibo(String uniNeg, Integer nroDocIni, Integer nroDocFin, Integer nroRecIni, Integer anio, Integer mes, String creaPor) throws Exception {
        return docIngresoDAO.eliminarRecibo(uniNeg, nroDocIni, nroDocFin, nroRecIni, anio, mes, creaPor);
    }

    public List<DocIngresoBean> obtenerRecibos(MatriculaBean matriculaBean) throws Exception {
        return docIngresoDAO.obtenerRecibos(matriculaBean);
    }

    @Transactional
    public void actualizarRecibosMasivos(String uniNeg, Integer anio, Integer mes, String flgMasivo, Integer flgMas, MatriculaBean matriculaBean) throws Exception {
        docIngresoDAO.actualizarRecibosMasivos(uniNeg, anio, mes, flgMasivo, flgMas, matriculaBean);
    }

    public Integer obtenerMaxNroDoc(DocIngresoBean docIngresoBean) throws Exception {
        return docIngresoDAO.obtenerMaxNroDoc(docIngresoBean);
    }

    @Transactional
    public void modificarNroDocSerie(DocIngresoBean docIngresoBean) throws Exception {
        docIngresoDAO.modificarNroDocSerie(docIngresoBean);
    }

    // REIMPRESION MASIVO
    public List<DocIngresoBean> obtenerFiltroDocIngresoMasivo(DocIngresoBean docIngresoBean) throws Exception {
        return docIngresoDAO.obtenerFiltroDocIngresoMasivo(docIngresoBean);
    }

    public List<DocIngresoRepBean> obtenerRecDocIngresoForMasivo(String uniNeg, List<Integer> ids) throws Exception {
        return docIngresoDAO.obtenerRecDocIngresoForMasivo(uniNeg, ids);
    }

    public List<DetDocIngresoRepBean> obtenerRecDocIngresoForMasivo(String uniNeg, Integer idDocIngreso, Integer mora, Integer dscto, Integer beca, Integer infoMonto) throws Exception {
        return docIngresoDAO.obtenerRecDocIngresoForMasivo(uniNeg, idDocIngreso, mora, dscto, beca, infoMonto);
    }

    public List<DetDocIngresoRepBean> obtenerRecDetDocIngresoMasivo(String uniNeg, Integer idDocIngreso, Integer mora, Integer dscto, Integer beca, Integer infoMonto) throws Exception {
        return docIngresoDAO.obtenerRecDetDocIngresoMasivo(uniNeg, idDocIngreso, mora, dscto, beca, infoMonto);
    }

    public DetDocIngresoBean obtenerDetDocIngPorDocIngreso(Integer idDocIngreso, String uniNeg) throws Exception {
        return docIngresoDAO.obtenerDetDocIngPorDocIngreso(idDocIngreso, uniNeg);
    }

    public void cambiarGradoAcademicoEnDocIng(DocIngresoBean docIngresoBean) throws Exception {
        docIngresoDAO.cambiarGradoAcademicoEnDocIng(docIngresoBean);
    }

    public void modificarDocIngFull(DocIngresoBean docIngresoBean) throws Exception {
        docIngresoDAO.modificarDocIngFull(docIngresoBean);
    }

    public void modificarDocIngyDetalleFull(DetDocIngresoBean detDocIngresoBean) throws Exception {
        docIngresoDAO.modificarDocIngFull(detDocIngresoBean.getDocIngresoBean());
        modificarDetalleDocIngFull(detDocIngresoBean);
    }

    public void modificarDocIngyDetalleFullCajGen(DetDocIngresoBean detDocIngresoBean) throws Exception {
        docIngresoDAO.modificarDocIngFull(detDocIngresoBean.getDocIngresoBean());
        modificarDetalleDocIngFull(detDocIngresoBean);
        CajaGenService cajaGenService = BeanFactory.getCajaGenService();
        if (detDocIngresoBean.getDocIngresoBean().getCajaGenBean().getIdCajaGen() != null) {
            System.out.println("id caja gen..." + detDocIngresoBean.getDocIngresoBean().getCajaGenBean().getIdCajaGen());
            cajaGenService.actualizarCajaGenAuto(detDocIngresoBean.getDocIngresoBean().getCajaGenBean().getIdCajaGen(),
                    detDocIngresoBean.getDocIngresoBean().getUnidadNegocioBean().getUniNeg());
        }
    }

    public void modificarDetalleDocIngFull(DetDocIngresoBean detDocIngresoBean) throws Exception {
        docIngresoDAO.modificarDetalleDocIngFull(detDocIngresoBean);
    }

    public Integer cantDetPorIdDocIngreso(Integer idDocIngreso, String uniNeg) throws Exception {
        return docIngresoDAO.cantDetPorIdDocIngreso(idDocIngreso, uniNeg);
    }

    @Transactional
    public void cambiarFechaImpresion(String uniNeg, List<Integer> lista) throws Exception {
        docIngresoDAO.cambiarFechaImpresion(uniNeg, lista);
    }

    public List<DetDocIngresoBean> obtenerDetoDocIdNew(String uniNeg, Integer idCtasXCobrar) throws Exception {
        return docIngresoDAO.obtenerDetoDocIdNew(uniNeg, idCtasXCobrar);
    }

    public void cambiarDsctoBeca(String uniNeg, BigDecimal dsctoBeca, Integer idDocIngreso) throws Exception {
        docIngresoDAO.cambiarDsctoBeca(uniNeg, dsctoBeca, idDocIngreso);
    }

    public void modificarDetalleDocIngDes(Integer idCtasxCobrar, String uniNeg, BigDecimal dsctoBeca) throws Exception {
        docIngresoDAO.modificarDetalleDocIngDes(idCtasxCobrar, uniNeg, dsctoBeca);
    }

    @Transactional
    public void modificarDocIngresoRetificacion(DocIngresoBean docIngresoBean) throws Exception {
        docIngresoDAO.modificarDocIngresoRetificacion(docIngresoBean);
    }

    @Transactional
    public void modificarDetDocIngresoRetificacion(DetDocIngresoBean detDocIngresoBean) throws Exception {
        docIngresoDAO.modificarDetDocIngresoRetificacion(detDocIngresoBean);
    }

    @Transactional
    public void modificarDetalleDocIngDesBecaTotal(Integer idCtasxCobrar, String uniNeg, BigDecimal dsctoBeca) throws Exception {
        docIngresoDAO.modificarDetalleDocIngDesBecaTotal(idCtasxCobrar, uniNeg, dsctoBeca);
    }

    @Transactional
    public void modificarDetalleDocIngDesCambioBeca(Integer idCtasxCobrar, String uniNeg, BigDecimal dsctoBeca, Integer idDocIngreso) throws Exception {
        docIngresoDAO.modificarDetalleDocIngDesCambioBeca(idCtasxCobrar, uniNeg, dsctoBeca, idDocIngreso);
    }

    public void cambiarDatBasicosDocIngresoBecaTotal(DocIngresoBean docIngresoBean) throws Exception {
        docIngresoDAO.cambiarDatBasicosDocIngresoBecaTotal(docIngresoBean);
    }

    public void cambiarDatBasicosDocIngresoBecaToCambio(DocIngresoBean docIngresoBean) throws Exception {
        docIngresoDAO.cambiarDatBasicosDocIngresoBecaToCambio(docIngresoBean);
    }

    public void cambiarDatBasicosDocIngresoBeca(DocIngresoBean docIngresoBean) throws Exception {
        docIngresoDAO.cambiarDatBasicosDocIngresoBeca(docIngresoBean);
    }

    public List<DetDocIngresoRepBean> obtenerRecDetDocIngresoMora(String uniNeg, Integer idDocIngreso, Integer mora, Integer dscto, Integer beca) throws Exception {
        return docIngresoDAO.obtenerRecDetDocIngresoMora(uniNeg, idDocIngreso, mora, dscto, beca);
    }

    public void ponerNroReciboMora(String uniNeg, Integer idRecibosMora, Integer idDocIngreso) throws Exception {
        docIngresoDAO.ponerNroReciboMora(uniNeg, idRecibosMora, idDocIngreso);
    }

    public List<DocIngresoBean> obtenerDocIngresosSinNroDoc(String uniNeg, Integer anio) throws Exception {
        return docIngresoDAO.obtenerDocIngresosSinNroDoc(uniNeg, anio);
    }

    public void ponerNroDoc(DocIngresoBean docIngresoBean) throws Exception {
        docIngresoDAO.ponerNroDoc(docIngresoBean);
    }

    public List<DescuentoTallerBean> obtenerDescuentosHabilitados(DescuentoTallerBean descuentoTallerBean) throws Exception {
        return docIngresoDAO.obtenerDescuentosHabilitados(descuentoTallerBean);
    }

    public void modificarConceptoCambioGrado(Integer idDocIngreso, String uniNeg, Integer idGradoAcademico) throws Exception {
        docIngresoDAO.modificarConceptoCambioGrado(idDocIngreso, uniNeg, idGradoAcademico);
    }

    public void modificarRespPagDocIng(String idRespPago, String nomResPago, String idDiscente, String uniNeg, Integer anio, Integer mesInicio, Integer mesFin,Integer idGradoAcademico) throws Exception {
        docIngresoDAO.modificarRespPagDocIng(idRespPago, nomResPago, idDiscente, uniNeg, anio, mesInicio, mesFin,idGradoAcademico);
    }

    public void modificarMontosDocIng(String uniNeg, String idEstudiante, Integer mesInicio, Integer mesFin, Integer anio, Double monto,Integer idGradoAcademico) throws Exception {
        docIngresoDAO.modificarMontosDocIng(uniNeg, idEstudiante, mesInicio, mesFin, anio, monto, idGradoAcademico);
    }
    
    //Modificar una cobranza de una caja a otra  
    public DocIngresoBean obtenerMontosAntiguaCajaGen(Integer idCajaGen) throws Exception {
        return docIngresoDAO.obtenerMontosAntiguaCajaGen(idCajaGen);
    }

    public void modificarMontosDocIngActivosCambioGrado(String uniNeg, String idEstudiante, Integer mesInicio, Integer mesFin, Integer anio, Double monto,Integer idGradoAcademico) throws Exception {
        docIngresoDAO.modificarMontosDocIngActivosCambioGrado(uniNeg, idEstudiante, mesInicio, mesFin, anio, monto,idGradoAcademico);
    }

    //Para talleres web Listado de alumnos 
    public List<DocIngresoBean> obtenerTalleresWeb(String uniNeg, Integer anio, Integer orden, String serie, String nroDoc, String nombre, Integer flgRecImp) throws Exception {
        return docIngresoDAO.obtenerTalleresWeb(uniNeg, anio, orden, serie, nroDoc, nombre, flgRecImp);
    }
    
}
