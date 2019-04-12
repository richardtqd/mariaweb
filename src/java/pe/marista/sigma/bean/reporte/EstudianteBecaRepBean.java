package pe.marista.sigma.bean.reporte;

import java.util.List;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class EstudianteBecaRepBean {
    
    private String anio;
    private String nombreUniNeg;
    private String ruc;
    private String nombreBeca;
    private JRBeanCollectionDataSource listaNombreBeca; 
    private JRBeanCollectionDataSource listaNombreAlumno;
    private String nombreGrado;
    private String nombreAlumno;
    private Integer cantidad;

    public String getNombreGrado() {
        return nombreGrado;
    }

    public void setNombreGrado(String nombreGrado) {
        this.nombreGrado = nombreGrado;
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }

    public JRBeanCollectionDataSource getListaNombreAlumno() {
        return listaNombreAlumno;
    }

    public void setListaNombreAlumno(List<EstudianteBecaRepBean> listaNombreAlumno) {
        this.listaNombreAlumno = new JRBeanCollectionDataSource(listaNombreAlumno);
    }
   
    

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getNombreUniNeg() {
        return nombreUniNeg;
    }

    public void setNombreUniNeg(String nombreUniNeg) {
        this.nombreUniNeg = nombreUniNeg;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getNombreBeca() {
        return nombreBeca;
    }

    public void setNombreBeca(String nombreBeca) {
        this.nombreBeca = nombreBeca;
    } 

//    public JRBeanCollectionDataSource getListaNombreBeca() {
//        return listaNombreBeca;
//    }
//
//    public void setListaNombreBeca(JRBeanCollectionDataSource listaNombreBeca) {
//        this.listaNombreBeca = listaNombreBeca;
//    }

//    public JRBeanCollectionDataSource getListaNombreAlumno() {
//        return listaNombreAlumno;
//    }
//
//    public void setListaNombreAlumno(JRBeanCollectionDataSource listaNombreAlumno) {
//        this.listaNombreAlumno = listaNombreAlumno;
//    }
    
    public JRBeanCollectionDataSource getListaNombreBeca() {
        return listaNombreBeca;
    }

    public void setListaNombreBeca(List<EstudianteBecaRepBean> listaNombreBeca) {
        this.listaNombreBeca = new JRBeanCollectionDataSource(listaNombreBeca);
    } 

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
  
}
