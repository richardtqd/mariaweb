/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean.reporte;

import java.util.List;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Administrador
 */
public class EstudianteDetalleBecaRepBean {

    private JRBeanCollectionDataSource listaNombreAlumno;
    private String nombreGrado;
    private String nombreAlumno;

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

    public void setListaNombreAlumno(List<EstudianteDetalleBecaRepBean> listaNombreAlumno) {
        this.listaNombreAlumno = new JRBeanCollectionDataSource(listaNombreAlumno);
    }
}
