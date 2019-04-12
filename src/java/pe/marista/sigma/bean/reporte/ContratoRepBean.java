package pe.marista.sigma.bean.reporte;

import java.io.Serializable;
import java.util.List;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ContratoRepBean implements Serializable {
    
    private String contrato;
    private String primeraParte;
    private String segundaParte;
    private String terceraParte;
    private String cuartaParte;
    private String quintaParte;
    private String sextaParte;
    private String setimaParte;
    private String octavaParte;
    private String novenaParte;
    private String decimaParte;
    private String representanteCole;
    private String repreProveedor;
    private String nobreProveedor;
    private String nombreCole;
    private String idOrdenCompra;
    private JRBeanCollectionDataSource listaContrato;

    public String getContrato() {
        return contrato;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

    public String getPrimeraParte() {
        return primeraParte;
    }

    public void setPrimeraParte(String primeraParte) {
        this.primeraParte = primeraParte;
    }

    public String getSegundaParte() {
        return segundaParte;
    }

    public void setSegundaParte(String segundaParte) {
        this.segundaParte = segundaParte;
    }

    public String getTerceraParte() {
        return terceraParte;
    }

    public void setTerceraParte(String terceraParte) {
        this.terceraParte = terceraParte;
    }

    public String getCuartaParte() {
        return cuartaParte;
    }

    public void setCuartaParte(String cuartaParte) {
        this.cuartaParte = cuartaParte;
    }

    public String getQuintaParte() {
        return quintaParte;
    }

    public void setQuintaParte(String quintaParte) {
        this.quintaParte = quintaParte;
    }

    public String getSextaParte() {
        return sextaParte;
    }

    public void setSextaParte(String sextaParte) {
        this.sextaParte = sextaParte;
    }

    public String getSetimaParte() {
        return setimaParte;
    }

    public void setSetimaParte(String setimaParte) {
        this.setimaParte = setimaParte;
    }

    public String getOctavaParte() {
        return octavaParte;
    }

    public void setOctavaParte(String octavaParte) {
        this.octavaParte = octavaParte;
    }

    public String getNovenaParte() {
        return novenaParte;
    }

    public void setNovenaParte(String novenaParte) {
        this.novenaParte = novenaParte;
    }

    public String getDecimaParte() {
        return decimaParte;
    }

    public void setDecimaParte(String decimaParte) {
        this.decimaParte = decimaParte;
    }

    public String getRepresentanteCole() {
        return representanteCole;
    }

    public void setRepresentanteCole(String representanteCole) {
        this.representanteCole = representanteCole;
    }

    public String getRepreProveedor() {
        return repreProveedor;
    }

    public void setRepreProveedor(String repreProveedor) {
        this.repreProveedor = repreProveedor;
    }

    public JRBeanCollectionDataSource getListaContrato() {
        return listaContrato;
    }

    public void setListaContrato(List<DetContratoRepBean> listaContrato) {
        this.listaContrato = new JRBeanCollectionDataSource(listaContrato);
    }
 

    public String getNombreCole() {
        return nombreCole;
    }

    public void setNombreCole(String nombreCole) {
        this.nombreCole = nombreCole;
    }

    public String getNobreProveedor() {
        return nobreProveedor;
    }

    public void setNobreProveedor(String nobreProveedor) {
        this.nobreProveedor = nobreProveedor;
    }

    public String getIdOrdenCompra() {
        return idOrdenCompra;
    }

    public void setIdOrdenCompra(String idOrdenCompra) {
        this.idOrdenCompra = idOrdenCompra;
    }
    
    
}
