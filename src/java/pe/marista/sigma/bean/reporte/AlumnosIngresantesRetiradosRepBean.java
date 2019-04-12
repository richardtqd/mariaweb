package pe.marista.sigma.bean.reporte;

import java.util.List;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class AlumnosIngresantesRetiradosRepBean {

    private Integer cantidadEstudianteInicio;
    private Integer cantidadIngresantes;
    private Integer cantidadRetirados;
    private Integer cantidadTotal;
    private String nombreUniNeg;
    private String titulo;
    private String sub1;
    private String sub2;
    private String nombreNivel;
    private Integer idNivelAcademico;
    private JRBeanCollectionDataSource listaDetalle;
    private JRBeanCollectionDataSource listaSubDetalle;
    private String nombreGrado;
    private Integer idGrado;
    private JRBeanCollectionDataSource listaNiveles;
    private String usuario;
    private String sub3;

    public Integer getCantidadEstudianteInicio() {
        return cantidadEstudianteInicio;
    }

    public void setCantidadEstudianteInicio(Integer cantidadEstudianteInicio) {
        this.cantidadEstudianteInicio = cantidadEstudianteInicio;
    }

    public Integer getCantidadIngresantes() {
        return cantidadIngresantes;
    }

    public void setCantidadIngresantes(Integer cantidadIngresantes) {
        this.cantidadIngresantes = cantidadIngresantes;
    }

    public Integer getCantidadRetirados() {
        return cantidadRetirados;
    }

    public void setCantidadRetirados(Integer cantidadRetirados) {
        this.cantidadRetirados = cantidadRetirados;
    }

    public Integer getCantidadTotal() {
        return cantidadTotal;
    }

    public void setCantidadTotal(Integer cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
    }

    public String getNombreUniNeg() {
        return nombreUniNeg;
    }

    public void setNombreUniNeg(String nombreUniNeg) {
        this.nombreUniNeg = nombreUniNeg;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSub1() {
        return sub1;
    }

    public void setSub1(String sub1) {
        this.sub1 = sub1;
    }

    public String getSub2() {
        return sub2;
    }

    public void setSub2(String sub2) {
        this.sub2 = sub2;
    }

    public String getNombreNivel() {
        return nombreNivel;
    }

    public void setNombreNivel(String nombreNivel) {
        this.nombreNivel = nombreNivel;
    }

    public Integer getIdNivelAcademico() {
        return idNivelAcademico;
    }

    public void setIdNivelAcademico(Integer idNivelAcademico) {
        this.idNivelAcademico = idNivelAcademico;
    }

    public JRBeanCollectionDataSource getListaDetalle() {
        return listaDetalle;
    }

    public void setListaDetalle(List<AlumnosIngresantesRetiradosRepBean> listaDetalle) {
        this.listaDetalle = new JRBeanCollectionDataSource(listaDetalle);
    }

    public JRBeanCollectionDataSource getListaSubDetalle() {
        return listaSubDetalle;
    }

    public void setListaSubDetalle(List<AlumnosIngresantesRetiradosRepBean> listaSubDetalle) {
        this.listaSubDetalle = new JRBeanCollectionDataSource(listaSubDetalle);
    }

    public String getNombreGrado() {
        return nombreGrado;
    }

    public void setNombreGrado(String nombreGrado) {
        this.nombreGrado = nombreGrado;
    }

    public Integer getIdGrado() {
        return idGrado;
    }

    public void setIdGrado(Integer idGrado) {
        this.idGrado = idGrado;
    }

    public JRBeanCollectionDataSource getListaNiveles() {
        return listaNiveles;
    }

    public void setListaNiveles(List<AlumnosIngresantesRetiradosRepBean> listaNiveles) {
        this.listaNiveles = new JRBeanCollectionDataSource(listaNiveles);
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSub3() {
        return sub3;
    }

    public void setSub3(String sub3) {
        this.sub3 = sub3;
    }

}
