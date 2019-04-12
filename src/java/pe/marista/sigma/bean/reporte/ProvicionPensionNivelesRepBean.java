package pe.marista.sigma.bean.reporte;

import java.util.List;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ProvicionPensionNivelesRepBean 
{
    private String nombre;
    private Integer cantAlumnos;
    private String cantFacturado;
    private String cantPagantes;
    private String cantDsctoBeca;
    private JRBeanCollectionDataSource listaNivelDetalle;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public JRBeanCollectionDataSource getListaNivelDetalle() {
        return listaNivelDetalle;
    }

    public void setListaNivelDetalle(List<ProvicionPensionNivelDetalleRepBean> listaNivelDetalle) {
        this.listaNivelDetalle = new JRBeanCollectionDataSource(listaNivelDetalle);
    } 

    public Integer getCantAlumnos() {
        return cantAlumnos;
    }

    public void setCantAlumnos(Integer cantAlumnos) {
        this.cantAlumnos = cantAlumnos;
    }

    public String getCantFacturado() {
        return cantFacturado;
    }

    public void setCantFacturado(String cantFacturado) {
        this.cantFacturado = cantFacturado;
    }

    public String getCantPagantes() {
        return cantPagantes;
    }

    public void setCantPagantes(String cantPagantes) {
        this.cantPagantes = cantPagantes;
    }

    public String getCantDsctoBeca() {
        return cantDsctoBeca;
    }

    public void setCantDsctoBeca(String cantDsctoBeca) {
        this.cantDsctoBeca = cantDsctoBeca;
    }
}
