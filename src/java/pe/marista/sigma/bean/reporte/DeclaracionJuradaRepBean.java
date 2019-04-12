package pe.marista.sigma.bean.reporte;

import java.io.Serializable;
import java.util.List;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class DeclaracionJuradaRepBean implements Serializable{
    private String nombreUniNeg;
    private String primeraCabezal;
    private String segundaCabezal;
    private String titulo;
    private String inicio;
    private String nombreAlumno;
    private String codigoAlumno;
    private JRBeanCollectionDataSource listaPrimera;
    private JRBeanCollectionDataSource listaSegunda;
    private JRBeanCollectionDataSource listaTercero;
    private String nombreRespPago;
    private String dni;
    private String direccion;
    private Integer dia;
    private String mes;
    private JRBeanCollectionDataSource listaCuarto;
    private String dniDirector;

    public String getNombreUniNeg() {
        return nombreUniNeg;
    }

    public void setNombreUniNeg(String nombreUniNeg) {
        this.nombreUniNeg = nombreUniNeg;
    }

    public String getPrimeraCabezal() {
        return primeraCabezal;
    }

    public void setPrimeraCabezal(String primeraCabezal) {
        this.primeraCabezal = primeraCabezal;
    }

    public String getSegundaCabezal() {
        return segundaCabezal;
    }

    public void setSegundaCabezal(String segundaCabezal) {
        this.segundaCabezal = segundaCabezal;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }

    public String getCodigoAlumno() {
        return codigoAlumno;
    }

    public void setCodigoAlumno(String codigoAlumno) {
        this.codigoAlumno = codigoAlumno;
    }

    public JRBeanCollectionDataSource getListaPrimera() {
        return listaPrimera;
    }

    public void setListaPrimera(List<DeclaracionJuradaRepBean> listaPrimera) {
        this.listaPrimera =  new JRBeanCollectionDataSource(listaPrimera);
    } 

    public JRBeanCollectionDataSource getListaSegunda() {
        return listaSegunda;
    }

    public void setListaSegunda(List<DeclaracionJuradaRepBean> listaSegunda) {
        this.listaSegunda = new JRBeanCollectionDataSource(listaSegunda);
    }

    public JRBeanCollectionDataSource getListaTercero() {
        return listaTercero;
    }

    public void setListaTercero(List<DeclaracionJuradaRepBean> listaTercero) {
        this.listaTercero = new JRBeanCollectionDataSource(listaTercero);
    }

    public String getNombreRespPago() {
        return nombreRespPago;
    }

    public void setNombreRespPago(String nombreRespPago) {
        this.nombreRespPago = nombreRespPago;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getDia() {
        return dia;
    }

    public void setDia(Integer dia) {
        this.dia = dia;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public JRBeanCollectionDataSource getListaCuarto() {
        return listaCuarto;
    }

    public void setListaCuarto(List<DeclaracionJuradaRepBean> listaCuarto) {
        this.listaCuarto = new JRBeanCollectionDataSource(listaCuarto);
    }

    public String getDniDirector() {
        return dniDirector;
    }

    public void setDniDirector(String dniDirector) {
        this.dniDirector = dniDirector;
    }
}
