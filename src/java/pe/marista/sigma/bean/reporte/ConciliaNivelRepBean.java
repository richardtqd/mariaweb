package pe.marista.sigma.bean.reporte;

import java.io.Serializable;

public class ConciliaNivelRepBean implements Serializable {

    private String ruc;
    private String nombre;
    private String titulo;
    private String contador;
    private String montoPagado;
    private String descripcion;
    private String total;
    private String totalContador;

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContador() {
        return contador;
    }

    public void setContador(String contador) {
        this.contador = contador;
    }

    public String getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(String montoPagado) {
        this.montoPagado = montoPagado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotalContador() {
        return totalContador;
    }

    public void setTotalContador(String totalContador) {
        this.totalContador = totalContador;
    }

}
