package pe.marista.sigma.bean.reporte;

public class ProvicionPensionNivelDetalleRepBean 
{
    private String nombre;
    private Integer idgradoacademico;
    private Integer cantAlumnos;
    private String pension;
    private String cantFacturado;
    private String cantPagantes;
    private String cantDsctoBeca;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    } 

    public Integer getCantAlumnos() {
        return cantAlumnos;
    }

    public void setCantAlumnos(Integer cantAlumnos) {
        this.cantAlumnos = cantAlumnos;
    }

    public String getPension() {
        return pension;
    }

    public void setPension(String pension) {
        this.pension = pension;
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

    public Integer getIdgradoacademico() {
        return idgradoacademico;
    }

    public void setIdgradoacademico(Integer idgradoacademico) {
        this.idgradoacademico = idgradoacademico;
    }
    
    
}
