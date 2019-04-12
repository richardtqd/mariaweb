package pe.marista.sigma.bean;

import java.util.List;
import java.io.Serializable;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class DetPresupuestoNewBean implements Serializable {

    private Integer id;
    private String uniNeg;
    private String crCuenta;
    private Integer cr;
    private String nombreCr;
    private Integer cuenta;
    private String nombreCuenta;
    private Integer anio;
    private String ambito;
    private String fecha;
    private String proveedor;
    private String motivo;
    private String nroDoc;
    private String monedaOrigen;
    private Double valorSoles;
    private String codigo;
    private Double presupuestoProgramado;
    private Double presupuestoEje;
    private Double saldo;
    private Double porcEje;
    private Double porcSaldo;
    //Ayuda
    private String nombreUniNeg;
    private String idTipoMoneda;
    private String rangoFecha;

    //Reporte
    private String subTitulo;
    private String ruc;
    private String titulo;
    private String unineg;
    private JRBeanCollectionDataSource listaDetalle;
    private JRBeanCollectionDataSource listaResumen;
    
    //Reporte Mes a mes
    private Double enero;
    private Double febrero;
    private Double marzo;
    private Double abril;
    private Double mayo;
    private Double junio;
    private Double julio;
    private Double agosto;
    private Double setiembre;
    private Double octubre;
    private Double noviembre;
    private Double diciembre;
    
    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
    }

    public Integer getCr() {
        return cr;
    }

    public void setCr(Integer cr) {
        this.cr = cr;
    }

    public String getNombreCr() {
        return nombreCr;
    }

    public void setNombreCr(String nombreCr) {
        this.nombreCr = nombreCr;
    }

    public Integer getCuenta() {
        return cuenta;
    }

    public void setCuenta(Integer cuenta) {
        this.cuenta = cuenta;
    }

    public String getNombreCuenta() {
        return nombreCuenta;
    }

    public void setNombreCuenta(String nombreCuenta) {
        this.nombreCuenta = nombreCuenta;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getAmbito() {
        return ambito;
    }

    public void setAmbito(String ambito) {
        this.ambito = ambito;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getNroDoc() {
        return nroDoc;
    }

    public void setNroDoc(String nroDoc) {
        this.nroDoc = nroDoc;
    }

    public String getMonedaOrigen() {
        return monedaOrigen;
    }

    public void setMonedaOrigen(String monedaOrigen) {
        this.monedaOrigen = monedaOrigen;
    }

    public Double getValorSoles() {
        return valorSoles;
    }

    public void setValorSoles(Double valorSoles) {
        this.valorSoles = valorSoles;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCrCuenta() {
        return crCuenta;
    }

    public void setCrCuenta(String crCuenta) {
        this.crCuenta = crCuenta;
    }

    public Double getPresupuestoProgramado() {
        return presupuestoProgramado;
    }

    public void setPresupuestoProgramado(Double presupuestoProgramado) {
        this.presupuestoProgramado = presupuestoProgramado;
    }

    public Double getPresupuestoEje() {
        return presupuestoEje;
    }

    public void setPresupuestoEje(Double presupuestoEje) {
        this.presupuestoEje = presupuestoEje;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Double getPorcEje() {
        return porcEje;
    }

    public void setPorcEje(Double porcEje) {
        this.porcEje = porcEje;
    }

    public Double getPorcSaldo() {
        return porcSaldo;
    }

    public void setPorcSaldo(Double porcSaldo) {
        this.porcSaldo = porcSaldo;
    }

    public String getNombreUniNeg() {
        return nombreUniNeg;
    }

    public void setNombreUniNeg(String nombreUniNeg) {
        this.nombreUniNeg = nombreUniNeg;
    }

    public String getIdTipoMoneda() {
        return idTipoMoneda;
    }

    public void setIdTipoMoneda(String idTipoMoneda) {
        this.idTipoMoneda = idTipoMoneda;
    }

    public String getRangoFecha() {
        return rangoFecha;
    }

    public void setRangoFecha(String rangoFecha) {
        this.rangoFecha = rangoFecha;
    }

    public JRBeanCollectionDataSource getListaDetalle() {
        return listaDetalle;
    }

    public void setListaDetalle(List<DetPresupuestoNewBean> listaDetalle) {
        this.listaDetalle = new JRBeanCollectionDataSource(listaDetalle);
    }

    public JRBeanCollectionDataSource getListaResumen() {
        return listaResumen;
    }

    public void setListaResumen(List<DetPresupuestoNewBean> listaResumen) {
        this.listaResumen = new JRBeanCollectionDataSource(listaResumen);
    }

    public String getSubTitulo() {
        return subTitulo;
    }

    public void setSubTitulo(String subTitulo) {
        this.subTitulo = subTitulo;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getUnineg() {
        return unineg;
    }

    public void setUnineg(String unineg) {
        this.unineg = unineg;
    }

    public Double getEnero() {
        return enero;
    }

    public void setEnero(Double enero) {
        this.enero = enero;
    }

    public Double getFebrero() {
        return febrero;
    }

    public void setFebrero(Double febrero) {
        this.febrero = febrero;
    }

    public Double getMarzo() {
        return marzo;
    }

    public void setMarzo(Double marzo) {
        this.marzo = marzo;
    }

    public Double getAbril() {
        return abril;
    }

    public void setAbril(Double abril) {
        this.abril = abril;
    }

    public Double getMayo() {
        return mayo;
    }

    public void setMayo(Double mayo) {
        this.mayo = mayo;
    }

    public Double getJunio() {
        return junio;
    }

    public void setJunio(Double junio) {
        this.junio = junio;
    }

    public Double getJulio() {
        return julio;
    }

    public void setJulio(Double julio) {
        this.julio = julio;
    }

    public Double getAgosto() {
        return agosto;
    }

    public void setAgosto(Double agosto) {
        this.agosto = agosto;
    }

    public Double getSetiembre() {
        return setiembre;
    }

    public void setSetiembre(Double setiembre) {
        this.setiembre = setiembre;
    }

    public Double getOctubre() {
        return octubre;
    }

    public void setOctubre(Double octubre) {
        this.octubre = octubre;
    }

    public Double getNoviembre() {
        return noviembre;
    }

    public void setNoviembre(Double noviembre) {
        this.noviembre = noviembre;
    }

    public Double getDiciembre() {
        return diciembre;
    }

    public void setDiciembre(Double diciembre) {
        this.diciembre = diciembre;
    }
}
