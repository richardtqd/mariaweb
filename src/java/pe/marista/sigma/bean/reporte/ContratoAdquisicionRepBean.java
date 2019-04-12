package pe.marista.sigma.bean.reporte;

import java.io.Serializable;
import java.util.List;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ContratoAdquisicionRepBean implements Serializable {

    private String contrato;
    private String primero;
    private String segundo;
    private String tercero;
    private String cuarto;
    private String quinto;
    private String sexto;
    private String repreProveedor;
    private String representanteCole;
    private String cole;
    private String proveedor;
    private JRBeanCollectionDataSource listaContrato;
    private String descripcionn;
    private String adelanto;

    public String getContrato() {
        return contrato;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

    public String getPrimero() {
        return primero;
    }

    public void setPrimero(String primero) {
        this.primero = primero;
    }

    public String getSegundo() {
        return segundo;
    }

    public void setSegundo(String segundo) {
        this.segundo = segundo;
    }

    public String getTercero() {
        return tercero;
    }

    public void setTercero(String tercero) {
        this.tercero = tercero;
    }

    public String getCuarto() {
        return cuarto;
    }

    public void setCuarto(String cuarto) {
        this.cuarto = cuarto;
    }

    public String getQuinto() {
        return quinto;
    }

    public void setQuinto(String quinto) {
        this.quinto = quinto;
    }

    public String getSexto() {
        return sexto;
    }

    public void setSexto(String sexto) {
        this.sexto = sexto;
    }

    public String getRepreProveedor() {
        return repreProveedor;
    }

    public void setRepreProveedor(String repreProveedor) {
        this.repreProveedor = repreProveedor;
    }

    public String getRepresentanteCole() {
        return representanteCole;
    }

    public void setRepresentanteCole(String representanteCole) {
        this.representanteCole = representanteCole;
    }

    public String getCole() {
        return cole;
    }

    public void setCole(String cole) {
        this.cole = cole;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public JRBeanCollectionDataSource getListaContrato() {
        return listaContrato;
    }

    public void setListaContrato(List<DetContratoAdquisicionRepBean> listaContrato) {
        this.listaContrato = new JRBeanCollectionDataSource(listaContrato);
    }
    
    public String getAdelanto() {
        return adelanto;
    }

    public void setAdelanto(String adelanto) {
        this.adelanto = adelanto;
    }

    public String getDescripcionn() {
        return descripcionn;
    }

    public void setDescripcionn(String descripcionn) {
        this.descripcionn = descripcionn;
    }

}
