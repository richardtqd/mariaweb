/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean.reporte;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author MS001
 */
public class DetCursoTallerRepBean implements Serializable {

    private Integer idProgramacion;
    private String taller;
    private String montoPagadoPorTaller;
    private String total;
    private JRBeanCollectionDataSource listaDetalleInscritosPorCursoTaller;
    private List<DetDetCursoTallerRepBean> listaDetalleInscritos;

    private String nrodoc;

    public DetCursoTallerRepBean() {
        listaDetalleInscritos = new ArrayList<DetDetCursoTallerRepBean>();
    }

    public DetCursoTallerRepBean(Integer idProgramacion, String taller, String montoPagadoPorTaller) {
        this.idProgramacion = idProgramacion;
        this.taller = taller;
        this.montoPagadoPorTaller = montoPagadoPorTaller;
        listaDetalleInscritos = new ArrayList<DetDetCursoTallerRepBean>();
    }

    public Integer getIdProgramacion() {
        return idProgramacion;
    }

    public void setIdProgramacion(Integer idProgramacion) {
        this.idProgramacion = idProgramacion;
    }

    public String getTaller() {
        return taller;
    }

    public void setTaller(String taller) {
        this.taller = taller;
    }

    public JRBeanCollectionDataSource getListaDetalleInscritosPorCursoTaller() {
        return listaDetalleInscritosPorCursoTaller;
    }

    public void setListaDetalleInscritosPorCursoTaller(List<DetDetCursoTallerRepBean> listaDetalleInscritosPorCursoTaller) {
        this.listaDetalleInscritosPorCursoTaller = new JRBeanCollectionDataSource(listaDetalleInscritosPorCursoTaller);
    }

    public String getMontoPagadoPorTaller() {
        return montoPagadoPorTaller;
    }

    public void setMontoPagadoPorTaller(String montoPagadoPorTaller) {
        this.montoPagadoPorTaller = montoPagadoPorTaller;
    }

    public List<DetDetCursoTallerRepBean> getListaDetalleInscritos() {
        if (listaDetalleInscritos == null) {
            listaDetalleInscritos = new ArrayList<>();
        }
        return listaDetalleInscritos;
    }

    public void setListaDetalleInscritos(List<DetDetCursoTallerRepBean> listaDetalleInscritos) {
        this.listaDetalleInscritos = listaDetalleInscritos;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getNrodoc() {
        return nrodoc;
    }

    public void setNrodoc(String nrodoc) {
        this.nrodoc = nrodoc;
    }

}
