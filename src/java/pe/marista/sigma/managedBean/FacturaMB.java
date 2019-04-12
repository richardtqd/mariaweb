/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author Ernesto
 */
public class FacturaMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of FacturaMB
     */
    public FacturaMB() {
//        obtenerPersonas();
    }
//    List<FacturaBean> listaFactura;
//    List<String> listaApoderado;
//    List<String> listaEstudiante;
//    Map<String, Integer> mapApoderado;
//    Map<String, Integer> mapEstudiante;
//    Double total = new Double("0.00");
//    String texto = "";
//
//    public List<FacturaBean> getListaFactura() {
//        return listaFactura;
//    }
//
//    public void setListaFactura(List<FacturaBean> listaFactura) {
//        this.listaFactura = listaFactura;
//    }
//
//    public Map<String, Integer> getMapApoderado() {
//        return mapApoderado;
//    }
//
//    public void setMapApoderado(Map<String, Integer> mapApoderado) {
//        this.mapApoderado = mapApoderado;
//    }
//
//    public Map<String, Integer> getMapEstudiante() {
//        return mapEstudiante;
//    }
//
//    public void setMapEstudiante(Map<String, Integer> mapEstudiante) {
//        this.mapEstudiante = mapEstudiante;
//    }
//
//    public List<String> getListaApoderado() {
//        return listaApoderado;
//    }
//
//    public void setListaApoderado(List<String> listaApoderado) {
//        this.listaApoderado = listaApoderado;
//    }
//
//    public List<String> getListaEstudiante() {
//        return listaEstudiante;
//    }
//
//    public void setListaEstudiante(List<String> listaEstudiante) {
//        this.listaEstudiante = listaEstudiante;
//    }
//
//    public Double getTotal() {
//        return total;
//    }
//
//    public void setTotal(Double total) {
//        this.total = total;
//    }
//
//    public String getTexto() {
//        return texto;
//    }
//
//    public void setTexto(String texto) {
//        this.texto = texto;
//    }
//
//    public final void obtenerPersonas() {
//        try {
//            listaApoderado = new ArrayList<>();
//            listaEstudiante = new ArrayList<>();
//            listaFactura = new ArrayList<>();
//            FacturaBean a = new FacturaBean();
//            a.setApoderado("Apodera A");
//            a.setId(1);
//            FacturaBean b = new FacturaBean();
//            b.setApoderado("Apodera B");
//            b.setId(2);
//            FacturaBean c = new FacturaBean();
//            c.setApoderado("Apodera C");
//            c.setId(3);
//            FacturaBean d = new FacturaBean();
//            d.setApoderado("Apodera D");
//            d.setId(4);
//            listaApoderado.add("Apodera A");
//            listaApoderado.add("Apodera B");
//            listaApoderado.add("Apodera C");
//            listaApoderado.add("Apodera D");
//
//            FacturaBean e = new FacturaBean();
//            e.setApoderado("Estudiante A");
//            e.setId(5);
//            FacturaBean f = new FacturaBean();
//            f.setApoderado("Estudiante B");
//            f.setId(6);
//            FacturaBean g = new FacturaBean();
//            g.setApoderado("Estudiante C");
//            g.setId(7);
//            FacturaBean h = new FacturaBean();
//            h.setApoderado("Estudiante D");
//            d.setId(8);
//
//            listaEstudiante.add("Estudiante A");
//            listaEstudiante.add("Estudiante B");
//            listaEstudiante.add("Estudiante C");
//            listaEstudiante.add("Estudiante D");
////            
////            mapApoderado = new LinkedHashMap<String, Integer>();
////            for (FacturaBean factura : listaApoderado) {
////                mapApoderado.put(factura.getApoderado(), new Integer(factura.getId()));
////            }
////            
////            mapEstudiante = new LinkedHashMap<String, Integer>();
////            for (FacturaBean factura2 : listaEstudiante) {
////                mapEstudiante.put(factura2.getEstudiante(), new Integer(factura2.getId()));
////            }
////            
//            FacturaBean fac1 = new FacturaBean("Pago Matrícula", 1, Double.parseDouble("200.00"));
//            FacturaBean fac2 = new FacturaBean("Mora Pago Matrícula", 2, Double.parseDouble("2.00"));
//            FacturaBean fac3 = new FacturaBean("Subvención de Pensión", 3, Double.parseDouble("5.00"));
//            FacturaBean fac4 = new FacturaBean("Pago Pensión <<Enero>>", 4, Double.parseDouble("500.00"));
//            FacturaBean fac5 = new FacturaBean("Pago Pensión <<Febrero>>", 5, Double.parseDouble("500.00"));
//            texto = "Mil ciento noventa y siete y/00 Nuevos Soles";
//            listaFactura.add(fac1);
//            listaFactura.add(fac2);
//            listaFactura.add(fac3);
//            listaFactura.add(fac4);
//            listaFactura.add(fac5);
//            total = fac1.getImporte() + fac2.getImporte() + fac3.getImporte() + fac4.getImporte() + fac5.getImporte();
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
////        }
//    }
}
