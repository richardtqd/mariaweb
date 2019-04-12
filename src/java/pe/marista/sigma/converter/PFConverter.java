///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package pe.marista.sigma.util;
//
//import java.util.List;
//import java.util.Map;
//import javax.faces.component.UIComponent;
//import javax.faces.context.FacesContext;
//import javax.faces.convert.Converter;
//import javax.faces.convert.FacesConverter;
//import org.primefaces.component.orderlist.OrderList;
//import pe.marista.sigma.bean.ProcesoFilesBean;
//import pe.marista.sigma.dao.ProcesoFilesDAO;
//import pe.marista.sigma.factory.BeanFactory;
//
///**
// *
// * @author Administrador
// */
//@FacesConverter("pfConverter")
//public class PFConverter implements Converter {
//
////    private ProcesoFilesDAO procesoFilesDAO;
////    private List<ProcesoFilesBean> listaProcesoFilesBean;
////
////    public PFConverter() {
////        try {
////            procesoFilesDAO = BeanFactory.getProcesoFilesDAO();
////            listaProcesoFilesBean = procesoFilesDAO.
////        } catch (Exception e) {
////            new MensajePrime().addErrorGeneralMessage();
////            GLTLog.writeError(this.getClass(), e);
////        }
////    }
//
//    @Override
//    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
//        Object ret = null;
//        try {
//            if (uic instanceof OrderList) {
//                FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("frmTblProcesoFilesDeta:olProcesoFiles");
////                Object orderList = ((OrderList) uic).getValue();
//                OrderList orderList2 = (OrderList) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("frmTblProcesoFilesDeta:olProcesoFiles");
//                Map<String, Object> map = orderList2.getAttributes();
//                System.out.println("");
////                DualListModel dl = (DualListModel) orderList;
////                for (Object o : orderList2.get) {
////                    String id = "" + ((CentroResponsabilidadBean) o).getCr();
////                    if (string.equals(id)) {
////                        ret = o;
////                        break;
////                    }
////                }
////                if (ret == null) {
////                    for (Object o : dl.getTarget()) {
////                        String id = "" + ((CentroResponsabilidadBean) o).getCr();
////                        if (string.equals(id)) {
////                            ret = o;
////                            break;
////                        }
////                    }
////                }
//            }
//        } catch (Exception e) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), e);
//        }
//        return ret;
//    }
//
//    @Override
//    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
//        try {
//            if (o != null) {
//                return String.valueOf(((ProcesoFilesBean) o).getIdFile());
//            } else {
//                return null;
//            }
//        } catch (Exception e) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), e);
//        }
//        return null;
//    }
//
////    public CentroResponsabilidadBean obtenerCRPorId(Integer cr) {
////        for (CentroResponsabilidadBean centroResponsabilidad : listaCentroResponsabilidadBean) {
////            if (Objects.equals(centroResponsabilidad.getCr(), cr)) {
////                return centroResponsabilidad;
////            }
////        }
////        return null;
////    }
//}
