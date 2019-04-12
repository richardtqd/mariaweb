/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.util.Date;
import java.util.List;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.DetEsquelaBean;
import pe.marista.sigma.bean.EntidadBean;
import pe.marista.sigma.bean.EsquelaBean;
import pe.marista.sigma.bean.MatriculaBean;
import pe.marista.sigma.bean.PersonaBean;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.reporte.MasivoCartaUnoBean;
import pe.marista.sigma.bean.reporte.SubReporteMasivoCartaUno;
import pe.marista.sigma.dao.DetEsquelaDAO;
import pe.marista.sigma.util.Mailing;

/**
 *
 * @author MS002
 */
public class DetEsquelaService {

    private DetEsquelaDAO detEsquelaDAO;
    private Boolean result;

    @Transactional
    public void insertarDetEsquela(DetEsquelaBean detEsquelaBean, List<MatriculaBean> listaMatriculaEstudiantesMasivosBean, Boolean FlgPa, Boolean FlgMa, Boolean FlgAp, Boolean flgResPa, EsquelaBean esquelaBean, Integer dato) throws Exception {
        Integer i = 0;
        for (MatriculaBean matricula : listaMatriculaEstudiantesMasivosBean) {
            i++;
            if (matricula.getEstudianteBean().getPersonaBean().getCorreo() != null && matricula.getEstudianteBean().getPersonaBean().getCorreo().trim() != null
                    && !matricula.getEstudianteBean().getPersonaBean().getCorreo().equals("") && !matricula.getEstudianteBean().getPersonaBean().getCorreo().trim().equals("")) {
                if (FlgPa == true) {
                    System.out.println("flg FlgPa true");
                    if (detEsquelaBean.getEstudianteBean().getFamiliaBean().getPadreBean().getPersonaBean().getCorreo() != null
                            && !detEsquelaBean.getEstudianteBean().getFamiliaBean().getPadreBean().getPersonaBean().getCorreo().equals("")
                            && detEsquelaBean.getEstudianteBean().getFamiliaBean().getPadreBean().getPersonaBean().getCorreo().trim() != null
                            && !detEsquelaBean.getEstudianteBean().getFamiliaBean().getPadreBean().getPersonaBean().getCorreo().trim().equals("")) {
                        if (isCorreoValido(matricula.getEstudianteBean().getRespPagoBean().getCorreo()) == true) {
                            System.out.println("Entrada OK, introdujo el correo " + matricula.getEstudianteBean().getFamiliaBean().getPadreBean().getPersonaBean().getCorreo());
                            if (dato.equals(1)) {
                                new Mailing().enviarCorreoMasivo(matricula.getEstudianteBean().getFamiliaBean().getPadreBean().getPersonaBean().getCorreo(), detEsquelaBean, esquelaBean, listaMatriculaEstudiantesMasivosBean);
                            }
                            detEsquelaBean.getEstudianteBean().setIdEstudiante(matricula.getEstudianteBean().getIdEstudiante());
                            detEsquelaBean.setStatus(1);
                            detEsquelaBean.getEstudianteBean().getPersonaBean().setCorreo(matricula.getEstudianteBean().getFamiliaBean().getPadreBean().getPersonaBean().getCorreo());
                            detEsquelaDAO.insertarDetEsquela(detEsquelaBean);

                        } else {
                            System.out.println("Entrada no válida, Formato de E-mail del Padre no válido " + matricula.getEstudianteBean().getFamiliaBean().getPadreBean().getPersonaBean().getCorreo());
                            detEsquelaBean.getEstudianteBean().setIdEstudiante(matricula.getEstudianteBean().getIdEstudiante());
                            detEsquelaBean.setStatus(0);
                            detEsquelaBean.getEstudianteBean().getPersonaBean().setCorreo(matricula.getEstudianteBean().getFamiliaBean().getPadreBean().getPersonaBean().getCorreo());
                            detEsquelaDAO.insertarDetEsquela(detEsquelaBean);
                        }
                    } else {
                        detEsquelaBean.getEstudianteBean().setIdEstudiante(matricula.getEstudianteBean().getIdEstudiante());
                        detEsquelaBean.setStatus(0);
                        detEsquelaBean.getEstudianteBean().getPersonaBean().setCorreo(matricula.getEstudianteBean().getFamiliaBean().getPadreBean().getPersonaBean().getCorreo());
                        detEsquelaDAO.insertarDetEsquela(detEsquelaBean);
                    }
                } else {
                    System.out.println("flg FlgPa false");
                }
                if (FlgMa == true) {
                    System.out.println("flg FlgMa");
                    if (matricula.getEstudianteBean().getFamiliaBean().getMadreBean().getPersonaBean().getCorreo() != null
                            && !matricula.getEstudianteBean().getFamiliaBean().getMadreBean().getPersonaBean().getCorreo().equals("")
                            && matricula.getEstudianteBean().getFamiliaBean().getMadreBean().getPersonaBean().getCorreo().trim() != null
                            && !matricula.getEstudianteBean().getFamiliaBean().getMadreBean().getPersonaBean().getCorreo().trim().equals("")) {
//                        isValidEmailAddress(matricula.getEstudianteBean().getApoderadoBean().getPersonaBean().getCorreo());
//                        if (result == true) {
//                            if (dato.equals(1)) {
//                                new Mailing().enviarCorreoMasivo(detEsquelaBean.getEstudianteBean().getFamiliaBean().getMadreBean().getPersonaBean().getCorreo(), detEsquelaBean, esquelaBean, listaMatriculaEstudiantesMasivosBean);
//                            }
//                            detEsquelaBean.getEstudianteBean().setIdEstudiante(matricula.getEstudianteBean().getIdEstudiante());
//                            detEsquelaBean.setStatus(1);
//                            detEsquelaBean.getEstudianteBean().getPersonaBean().setCorreo(matricula.getEstudianteBean().getPersonaBean().getCorreo());
//                            detEsquelaDAO.insertarDetEsquela(detEsquelaBean);
//                        }
                        if (isCorreoValido(matricula.getEstudianteBean().getRespPagoBean().getCorreo()) == true) {
                            System.out.println("Entrada OK, introdujo el correo " + matricula.getEstudianteBean().getFamiliaBean().getPadreBean().getPersonaBean().getCorreo());
                            if (dato.equals(1)) {
                                new Mailing().enviarCorreoMasivo(matricula.getEstudianteBean().getFamiliaBean().getMadreBean().getPersonaBean().getCorreo(), detEsquelaBean, esquelaBean, listaMatriculaEstudiantesMasivosBean);
                            }
                            detEsquelaBean.getEstudianteBean().setIdEstudiante(matricula.getEstudianteBean().getIdEstudiante());
                            detEsquelaBean.setStatus(1);
                            detEsquelaBean.getEstudianteBean().getPersonaBean().setCorreo(matricula.getEstudianteBean().getFamiliaBean().getMadreBean().getPersonaBean().getCorreo());
                            detEsquelaDAO.insertarDetEsquela(detEsquelaBean);

                        } else {
                            System.out.println("Entrada no válida, Formato de E-mail del Padre no válido " + matricula.getEstudianteBean().getFamiliaBean().getMadreBean().getPersonaBean().getCorreo());
                            detEsquelaBean.getEstudianteBean().setIdEstudiante(matricula.getEstudianteBean().getIdEstudiante());
                            detEsquelaBean.setStatus(0);
                            detEsquelaBean.getEstudianteBean().getPersonaBean().setCorreo(matricula.getEstudianteBean().getFamiliaBean().getMadreBean().getPersonaBean().getCorreo());
                            detEsquelaDAO.insertarDetEsquela(detEsquelaBean);
                        }
                    } else {
                        detEsquelaBean.getEstudianteBean().setIdEstudiante(matricula.getEstudianteBean().getIdEstudiante());
                        detEsquelaBean.setStatus(0);
                        detEsquelaBean.getEstudianteBean().getPersonaBean().setCorreo(matricula.getEstudianteBean().getFamiliaBean().getMadreBean().getPersonaBean().getCorreo());
                        detEsquelaDAO.insertarDetEsquela(detEsquelaBean);
                    }
                } else {
                    System.out.println("flg FlgMa false");
                }
                if (FlgAp == true) {
                    System.out.println("flg FlgAp");
                    if (matricula.getEstudianteBean().getApoderadoBean().getPersonaBean().getCorreo() != null
                            && matricula.getEstudianteBean().getApoderadoBean().getPersonaBean().getCorreo().trim() != null
                            && !matricula.getEstudianteBean().getApoderadoBean().getPersonaBean().getCorreo().equals("")
                            && !matricula.getEstudianteBean().getApoderadoBean().getPersonaBean().getCorreo().trim().equals("")) {
                        if (isCorreoValido(matricula.getEstudianteBean().getApoderadoBean().getPersonaBean().getCorreo()) == true) {
                            System.out.println("Entrada OK, introdujo el correo " + matricula.getEstudianteBean().getApoderadoBean().getPersonaBean().getCorreo());
                            if (dato.equals(1)) {
                                new Mailing().enviarCorreoMasivo(matricula.getEstudianteBean().getApoderadoBean().getPersonaBean().getCorreo(), detEsquelaBean, esquelaBean, listaMatriculaEstudiantesMasivosBean);
                            }
                            detEsquelaBean.getEstudianteBean().setIdEstudiante(matricula.getEstudianteBean().getIdEstudiante());
                            detEsquelaBean.setStatus(1);
                            detEsquelaBean.getEstudianteBean().getPersonaBean().setCorreo(matricula.getEstudianteBean().getApoderadoBean().getPersonaBean().getCorreo());
                            detEsquelaDAO.insertarDetEsquela(detEsquelaBean);

                        } else {
                            System.out.println("Entrada no válida, Formato de E-mail del Padre no válido " + matricula.getEstudianteBean().getApoderadoBean().getPersonaBean().getCorreo());
                            detEsquelaBean.getEstudianteBean().setIdEstudiante(matricula.getEstudianteBean().getIdEstudiante());
                            detEsquelaBean.setStatus(0);
                            detEsquelaBean.getEstudianteBean().getPersonaBean().setCorreo(matricula.getEstudianteBean().getApoderadoBean().getPersonaBean().getCorreo());
                            detEsquelaDAO.insertarDetEsquela(detEsquelaBean);
                        }
                    } else {
                        detEsquelaBean.getEstudianteBean().setIdEstudiante(matricula.getEstudianteBean().getIdEstudiante());
                        detEsquelaBean.setStatus(0);
                        detEsquelaBean.getEstudianteBean().getPersonaBean().setCorreo(matricula.getEstudianteBean().getApoderadoBean().getPersonaBean().getCorreo());
                        detEsquelaDAO.insertarDetEsquela(detEsquelaBean);
                    }
                } else {
                    System.out.println("flg Apo false");
                }
                if (flgResPa == true) {
                    System.out.println("flg flgResPa");
                    if (matricula.getEstudianteBean().getRespPagoBean().getCorreo() != null
                            && matricula.getEstudianteBean().getRespPagoBean().getCorreo().trim() != null
                            && !matricula.getEstudianteBean().getRespPagoBean().getCorreo().equals("")
                            && !matricula.getEstudianteBean().getRespPagoBean().getCorreo().trim().equals("")) {
                        if (isCorreoValido(matricula.getEstudianteBean().getRespPagoBean().getCorreo()) == true) {
                            System.out.println("Entrada OK, introdujo el correo " + matricula.getEstudianteBean().getRespPagoBean().getCorreo());
                            if (dato.equals(1)) {
                                new Mailing().enviarCorreoMasivo(matricula.getEstudianteBean().getRespPagoBean().getCorreo(), detEsquelaBean, esquelaBean, listaMatriculaEstudiantesMasivosBean);
                            }
                            detEsquelaBean.getEstudianteBean().setIdEstudiante(matricula.getEstudianteBean().getIdEstudiante());
                            detEsquelaBean.setStatus(1);
                            detEsquelaBean.getEstudianteBean().getPersonaBean().setCorreo(matricula.getEstudianteBean().getRespPagoBean().getCorreo());
                            detEsquelaDAO.insertarDetEsquela(detEsquelaBean);

                        } else {
                            System.out.println("Entrada no válida, Formato de E-mail del Padre no válido " + matricula.getEstudianteBean().getRespPagoBean().getCorreo());
                            detEsquelaBean.getEstudianteBean().setIdEstudiante(matricula.getEstudianteBean().getIdEstudiante());
                            detEsquelaBean.setStatus(0);
                            detEsquelaBean.getEstudianteBean().getPersonaBean().setCorreo(matricula.getEstudianteBean().getRespPagoBean().getCorreo());
                            detEsquelaDAO.insertarDetEsquela(detEsquelaBean);
                        }
                    } else {
                        detEsquelaBean.getEstudianteBean().setIdEstudiante(matricula.getEstudianteBean().getIdEstudiante());
                        detEsquelaBean.setStatus(0);
                        detEsquelaBean.getEstudianteBean().getPersonaBean().setCorreo(matricula.getEstudianteBean().getRespPagoBean().getCorreo());
                        detEsquelaDAO.insertarDetEsquela(detEsquelaBean);
                    }
                }
                else {
                System.out.println("flg flgResPa false");
                }
            }
        }

    }

    public boolean isValidEmailAddress(String email) {
        result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();

        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    public static Boolean isCorreoValido(String str) {
        return (str.matches("^[_A-Za-z0-9-]+(\\." + "[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*" + "(\\.[A-Za-z]{2,})$") && str.equals("") == false);
    }

    @Transactional
    public void modificarDetEsquela(DetEsquelaBean detEsquelaBean) throws Exception {
        detEsquelaDAO.modificarDetEsquela(detEsquelaBean);
    }

    @Transactional
    public void modificarDetMensaje(DetEsquelaBean detEsquelaBean) throws Exception {
        detEsquelaDAO.modificarDetMensaje(detEsquelaBean);
    }

    @Transactional
    public void eliminarDetEsquela(DetEsquelaBean detEsquelaBean) throws Exception {
        detEsquelaDAO.eliminarDetEsquela(detEsquelaBean);
    }

    public DetEsquelaBean obtenerPorId(Integer idDetEsquela) throws Exception {
        return detEsquelaDAO.obtenerPorId(idDetEsquela);
    }

    public DetEsquelaBean obtenerPorTitulo(String titulo) throws Exception {
        return detEsquelaDAO.obtenerPorTitulo(titulo);
    }

    public Integer obtenerTotalPorDia(String uniNeg) throws Exception {
        return detEsquelaDAO.obtenerTotalPorDia(uniNeg);
    }

    public Integer obtenerMaxId(String uniNeg) throws Exception {
        return detEsquelaDAO.obtenerMaxId(uniNeg);
    }

    public List<DetEsquelaBean> obtenerTodos(String uniNeg) throws Exception {
        return detEsquelaDAO.obtenerTodos(uniNeg);
    }

    public List<DetEsquelaBean> obtenerDetalles(String uniNeg) throws Exception {
        return detEsquelaDAO.obtenerDetalles(uniNeg);
    }

    public List<DetEsquelaBean> obtenerListaOk(String fecha, String uniNeg, Integer idEsquela) throws Exception {
        return detEsquelaDAO.obtenerListaOk(fecha, uniNeg, idEsquela);
    }

    public List<DetEsquelaBean> obtenerListaFail(Date creaFecha, String uniNeg, Integer idEsquela) throws Exception {
        return detEsquelaDAO.obtenerListaFail(creaFecha, uniNeg, idEsquela);
    }

    public List<MasivoCartaUnoBean> obtenerListaEsquelaRep(DetEsquelaBean detEsquelaBean) throws Exception {
        return detEsquelaDAO.obtenerListaEsquelaRep(detEsquelaBean);
    }

    public List<SubReporteMasivoCartaUno> obtenerListaDeuda(String uniNeg, String idEstudiante, Integer anio, Integer[] listaMeses) throws Exception {
        return detEsquelaDAO.obtenerListaDeuda(uniNeg, idEstudiante, anio, listaMeses);
    }

    public List<DetEsquelaBean> obtenerPorFecha(String fecha, String uniNeg, Integer status) throws Exception {
        return detEsquelaDAO.obtenerPorFecha(fecha, uniNeg, status);
    }

    public List<DetEsquelaBean> obtenerPorFechaMen(String fecha, String uniNeg) throws Exception {
        return detEsquelaDAO.obtenerPorFechaMen(fecha, uniNeg);
    }

    //METODOS MASIVOS DE ENVIO
    //ENVIO DE ESTUDIANTE
    @Transactional
    public void enviarMailEstudiante(DetEsquelaBean detEsquelaBean) throws Exception {
        detEsquelaDAO.insertarEnvioMasivo(detEsquelaBean);
        new Mailing().envioMasivoObjeto(detEsquelaBean);
    }

    @Transactional
    public void enviarMailEstudianteConf(DetEsquelaBean detEsquelaBean) throws Exception {
        detEsquelaDAO.insertarEnvioMasivo(detEsquelaBean);
        new Mailing().envioMasivoObjetoConf(detEsquelaBean);
    }

    @Transactional
    public void enviarMailError(DetEsquelaBean detEsquelaBean) throws Exception {
        detEsquelaDAO.insertarEnvioMasivo(detEsquelaBean);
    }

    @Transactional
    public void insertarEnvioMasivo(DetEsquelaBean detEsquelaBean) throws Exception {
        detEsquelaDAO.insertarEnvioMasivo(detEsquelaBean);
        new Mailing().envioMasivoObjeto(detEsquelaBean);
    }

    public List<DetEsquelaBean> obtenerMensajesPorTipo(DetEsquelaBean detEsquelaBean) throws Exception {
        return detEsquelaDAO.obtenerMensajesPorTipo(detEsquelaBean);
    }

    public Integer obtenerMaxEsquela(String uniNeg) throws Exception {
        return detEsquelaDAO.obtenerMaxEsquela(uniNeg);
    }

    public Object execProEsquelaMasivo(String unineg, String idestudiante, Integer anio, Integer mes, String mensaje) throws Exception {
        return detEsquelaDAO.execProEsquelaMasivo(unineg, idestudiante, anio, mes, mensaje);
    }

    public void modificarEstadoBorrador(DetEsquelaBean detEsquelaBean) throws Exception {
        detEsquelaDAO.modificarEstadoBorrador(detEsquelaBean);
    }

    public DetEsquelaDAO getDetEsquelaDAO() {
        return detEsquelaDAO;
    }

    public void setDetEsquelaDAO(DetEsquelaDAO detEsquelaDAO) {
        this.detEsquelaDAO = detEsquelaDAO;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

}
