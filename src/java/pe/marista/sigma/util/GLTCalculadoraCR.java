/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.util;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CentroResponsabilidadBean;
import pe.marista.sigma.bean.DetSolicitudCajaChCRBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.ViewMatriculaBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CentroResponsabilidadService;

/**
 *
 * @author Administrador
 */
public class GLTCalculadoraCR {

    private ViewMatriculaBean viewMatriculaBean;

    public GLTCalculadoraCR() {
        try {
             
            UsuarioBean usuarioLogin = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            Calendar cal = Calendar.getInstance();
            
            int mes = 0;
            mes= cal.get(Calendar.MONTH)+1;
            System.out.println("mes: "+mes);
            int anio;
            if(mes==1 || mes==2){
                anio=cal.get(Calendar.YEAR)-1;
            }else{
                anio=cal.get(Calendar.YEAR);
            }
            viewMatriculaBean = new ViewMatriculaBean(
                    usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(),
                    anio,
                    MaristaConstantes.NIV_ACA_INI,
                    MaristaConstantes.NIV_ACA_PRI,
                    MaristaConstantes.NIV_ACA_SEC,
                    MaristaConstantes.NIV_ACA_SEC_BAC_4,
                    MaristaConstantes.NIV_ACA_SEC_BAC_5
            );
            CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
            viewMatriculaBean = centroResponsabilidadService.obtenerTotales(viewMatriculaBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

//    public Double calcularPorDivision(Map<CentroResponsabilidadBean> listaElementos) {
//        Double sum = 0d;
//        Double rpta = 0d;
//        try {
//            if (listaElementos != null && !listaElementos.isEmpty()) {
//                for (Double elemento : listaElementos) {
//                    sum += elemento;
//                }
//                rpta = sum / listaElementos.size();
//            }
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//        return rpta;
//    }
    public void calcularPorPonderacion(List<CentroResponsabilidadBean> listaCentroResponsabilidadBean, Double monto) {
        try {
            Double universo = 0d;
            Double sumaCen = 0.0;
            for (CentroResponsabilidadBean centroResponsabilidadBean : listaCentroResponsabilidadBean) {
                switch (centroResponsabilidadBean.getTipoNivelCR()) {
                    case MaristaConstantes.COD_GRUP_CR_INI:
                        universo = universo + viewMatriculaBean.getTotalIni();
                        break;
                    case MaristaConstantes.COD_GRUP_CR_PRI:
                        universo = universo + viewMatriculaBean.getTotalPri();
                        break;
                    case MaristaConstantes.COD_GRUP_CR_SEC:
                        universo = universo + viewMatriculaBean.getTotalSec();
                        break;
                    case MaristaConstantes.COD_GRUP_CR_SECB:
                        universo = universo + viewMatriculaBean.getTotalSecB();
                        break;
                    case MaristaConstantes.COD_GRUP_CR_TALL:
                        universo = universo + viewMatriculaBean.getTotalTaller();
                        break;
                    case MaristaConstantes.COD_GRUP_CR_APA:
                        universo = universo + viewMatriculaBean.getSumMatriTot();
                        break;
                }
            }
            System.out.println("entró");
            for (CentroResponsabilidadBean centroResponsabilidadBean : listaCentroResponsabilidadBean) {
                Double a = 0d;
                Double b = 0d;
                Double c = 0d;

                switch (centroResponsabilidadBean.getTipoNivelCR()) {
                    case MaristaConstantes.COD_GRUP_CR_INI:
                        a = (viewMatriculaBean.getTotalIni()) / universo;
                        b = monto * a;
                        c = (double) Math.round(b * 100) / 100;
                        centroResponsabilidadBean.setMontoDistribucion(c);
                        break;
                    case MaristaConstantes.COD_GRUP_CR_PRI:
                        a = (viewMatriculaBean.getTotalPri()) / universo;
                        b = monto * a;
                        c = (double) Math.round(b * 100) / 100;
                        centroResponsabilidadBean.setMontoDistribucion(c);
                        break;
                    case MaristaConstantes.COD_GRUP_CR_SEC:
                        a = (viewMatriculaBean.getTotalSec()) / universo;
                        b = monto * a;
                        c = (double) Math.round(b * 100) / 100;
                        centroResponsabilidadBean.setMontoDistribucion(c);
                        break;
                    case MaristaConstantes.COD_GRUP_CR_SECB:
                        a = (viewMatriculaBean.getTotalSecB()) / universo;
                        b = monto * a;
                        c = (double) Math.round(b * 100) / 100;
                        centroResponsabilidadBean.setMontoDistribucion(c);
                        break;
                    case MaristaConstantes.COD_GRUP_CR_TALL:
                        a = (viewMatriculaBean.getTotalTaller()) / universo;
                        b = monto * a;
                        c = (double) Math.round(b * 100) / 100;
                        centroResponsabilidadBean.setMontoDistribucion(c);
                        break;
                    case MaristaConstantes.COD_GRUP_CR_APA:
                        a = (viewMatriculaBean.getSumMatriTot()) / universo;
                        b = monto * a;
                        c = (double) Math.round(b * 100) / 100;
                        centroResponsabilidadBean.setMontoDistribucion(c);
                        break;
                }
                System.out.println("monto: " + c);
                sumaCen = (double) Math.round((sumaCen + c) * 100) / 100;
                System.out.println("monto: " + sumaCen);
            }

            for (CentroResponsabilidadBean centroResponsabilidadBean : listaCentroResponsabilidadBean) {
                Double a = 0d;
                Double b = 0d;
                Double c = 0d;
                if (sumaCen > monto) {
                    switch (centroResponsabilidadBean.getTipoNivelCR()) {
                        case MaristaConstantes.COD_GRUP_CR_INI:
                            a = (viewMatriculaBean.getTotalIni()) / universo;
                            b = monto * a;
                            c = (double) Math.round((b - 0.01) * 100) / 100;
                            centroResponsabilidadBean.setMontoDistribucion(c);
                            break;
                    }
                    System.out.println("entro1");
                } else if (sumaCen < monto) {
                    switch (centroResponsabilidadBean.getTipoNivelCR()) {
                        case MaristaConstantes.COD_GRUP_CR_INI:
                            a = (viewMatriculaBean.getTotalIni()) / universo;
                            b = monto * a;
                            c = (double) Math.round((b + 0.01) * 100) / 100;
                            centroResponsabilidadBean.setMontoDistribucion(c);
                            break;
                    }
                    System.out.println("entro2");
                }
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void calcularPorDivision(List<CentroResponsabilidadBean> listaCentroResponsabilidadBean, Double monto) {
        try {
            Double mDivision = 0d;
            Double newTot = 0d;
            Double newDif = 0d;
//            Double newAdd = 0d;
            if (monto != 0d && listaCentroResponsabilidadBean != null && !listaCentroResponsabilidadBean.isEmpty()) {
                mDivision = monto / listaCentroResponsabilidadBean.size();
                System.out.println("mDivision 1: " + mDivision);
                mDivision = (double) Math.round(mDivision * 100) / 100;
                System.out.println("mDivision 2: " + mDivision);
                newTot = (double) Math.round(mDivision * listaCentroResponsabilidadBean.size() * 100) / 100;
                System.out.println("newTot 1: " + newTot);
                System.out.println("monto 1: " + monto);
                newDif = (double) Math.round((monto - newTot) * 100) / 100;
                System.out.println("new: " + newDif);
//                for (CentroResponsabilidadBean centroResponsabilidadBean : listaCentroResponsabilidadBean) {
//                }
                for (int i = 0; i < listaCentroResponsabilidadBean.size(); i++) {
                    if (i == (listaCentroResponsabilidadBean.size() - 1) && newDif != 0d) {
                        mDivision = (double) Math.round((mDivision + newDif) * 100) / 100;
                        System.out.println("newDif: " + newDif);
                        System.out.println("i: " + i);
                        System.out.println("mDivision: " + mDivision);
                    }
                    listaCentroResponsabilidadBean.get(i).setMontoDistribucion(mDivision);
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void calcularPorDivisionDet(List<DetSolicitudCajaChCRBean> listaCentroResponsabilidadBean, Double monto) {
        try {
            Double mDivision = 0d;
            Double newTot = 0d;
            Double newDif = 0d;
//            Double newAdd = 0d;
            if (monto != 0d && listaCentroResponsabilidadBean != null && !listaCentroResponsabilidadBean.isEmpty()) {
                mDivision = monto / listaCentroResponsabilidadBean.size();
//                System.out.println("mDivision 1: "+mDivision);
                mDivision = (double) Math.round(mDivision * 100) / 100;
//                System.out.println("mDivision 2: "+mDivision);
                newTot = (double) Math.round(mDivision * listaCentroResponsabilidadBean.size() * 100) / 100;
//                System.out.println("newTot 1: "+newTot);
//                System.out.println("monto 1: "+monto);
                newDif = (double) Math.round((monto - newTot) * 100) / 100;
//                System.out.println("new: " + newDif);
//                for (CentroResponsabilidadBean centroResponsabilidadBean : listaCentroResponsabilidadBean) {
//                }
                for (int i = 0; i < listaCentroResponsabilidadBean.size(); i++) {
                    if (i == (listaCentroResponsabilidadBean.size() - 1) && newDif != 0d) {
                        mDivision = (double) Math.round((mDivision + newDif) * 100) / 100;
//                        System.out.println("newDif: "+newDif);
//                        System.out.println("i: " + i);
//                        System.out.println("mDivision: " + mDivision);
                    }
//                    listaCentroResponsabilidadBean.get(i).setValor(mDivision);
                    listaCentroResponsabilidadBean.get(i).setValorD(mDivision);
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void calcularPorPersonalizacion(List<CentroResponsabilidadBean> listaCentroResponsabilidadBean) {
        try {
            if (listaCentroResponsabilidadBean != null && !listaCentroResponsabilidadBean.isEmpty()) {
                for (CentroResponsabilidadBean listaCentroResponsabilidadBean1 : listaCentroResponsabilidadBean) {
                    if (listaCentroResponsabilidadBean1.getMontoDistribucion() == null) {
                        listaCentroResponsabilidadBean1.setMontoDistribucion(0.0);
                    }
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
}
