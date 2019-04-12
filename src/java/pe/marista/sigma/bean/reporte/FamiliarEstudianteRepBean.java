/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean.reporte;

import java.io.Serializable;
import java.util.List;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Administrador
 */
public class FamiliarEstudianteRepBean implements Serializable {

    private String idFamiliar;
    private String tipoParentesco;
    private JRBeanCollectionDataSource listaFamiliares;
    private JRBeanCollectionDataSource listaResEco;

    public String getIdFamiliar() {
        return idFamiliar;
    }

    public void setIdFamiliar(String idFamiliar) {
        this.idFamiliar = idFamiliar;
    }

    public String getTipoParentesco() {
        return tipoParentesco;
    }

    public void setTipoParentesco(String tipoParentesco) {
        this.tipoParentesco = tipoParentesco;
    }

    public JRBeanCollectionDataSource getListaFamiliares() {
        return listaFamiliares;
    }

    public void setListaFamiliares(List<FamiliarRepBean> listaFamiliares) {
        this.listaFamiliares = new JRBeanCollectionDataSource(listaFamiliares);
    }

    public JRBeanCollectionDataSource getListaResEco() {
        return listaResEco;
    }

    public void setListaResEco(List<ResponsableEconomicoRepBean> listaResEco) {
        this.listaResEco = new JRBeanCollectionDataSource(listaResEco);
    }

}
