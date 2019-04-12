/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.bean.BancoBean;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.ProveedorBean;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author MS001
 */
@ManagedBean
@ViewScoped
public class BancoMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of BancoMB
     */
    public BancoMB() {

        BancoBean a = new BancoBean("10401", "BANCO DE CRÉDITO", "Soles", "193-1428600-0-02", "1011");
        BancoBean b = new BancoBean("10402", "BANCO DE CRÉDITO", "Dólares", "193-1434702-1-48", "1597");
        BancoBean c = new BancoBean("10405", "BANCO DE CRÉDITO", "Soles", "194-1318477-0-48", "8926");
        BancoBean d = new BancoBean("10406", "BANCO DE CRÉDITO", "Soles", "194-1318579-0-78", "683552");

//        BancoBean d1 = new BancoBean("10407", "BANCO CONTINENTAL", "Soles", "193-1428500-0-02", "1597");
//        BancoBean d2 = new BancoBean("10408", "BANCO CONTINENTAL", "Dólares", "193-1434702-1-49", "683552");
//        BancoBean d3 = new BancoBean("10419", "BANCO CONTINENTAL", "Soles", "194-1318580-0-78", "683552");
//
//        BancoBean d5 = new BancoBean("10410", "INTERBANK", "Soles", "194-1318579-0-44", "8946");
//        BancoBean d6 = new BancoBean("10411", "INTERBANK", "Soles", "194-1318579-0-75", "8986");
//        BancoBean d7 = new BancoBean("10412", "INTERBANK", "Dólares", "193-1434702-1-49", "8924");

        listBanco = new ArrayList<>();
        listBanco.add(a);
        listBanco.add(b);
        listBanco.add(c);
        listBanco.add(d);
//        listBanco.add(d1);
//        listBanco.add(d2);
//        listBanco.add(d3);
//        listBanco.add(d5);
//        listBanco.add(d6);
//        listBanco.add(d7);
        
//        BancoBean q = new BancoBean("BANCO DE CREDITO");
//        BancoBean w = new BancoBean("BANCO CONTINENTAL");
//        BancoBean e = new BancoBean("INTERBANK");
        
//        listBancoNom = new ArrayList<>();
//        listBancoNom.add(q);
//        listBancoNom.add(w);
//        listBancoNom.add(e);

    }

    private BancoBean bancoBean;
    private List<BancoBean> listBanco;
    private List<BancoBean> listBancoNom;
    private List<PersonalBean> listaPersonalBean;
    private List<ProveedorBean> listaProveedorBean;
    
    private PersonalBean personalBean;
    private ProveedorBean proveedorBean;

    public void rowSelect2(SelectEvent event) {
        try {
            proveedorBean = (ProveedorBean) event.getObject();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
    
    public void rowSelect3(SelectEvent event) {
        try {
             personalBean =(PersonalBean) event.getObject();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
    
    
    
    
    public BancoBean getBancoBean() {
        if (bancoBean == null) {
            bancoBean = new BancoBean();
        }
        return bancoBean;
    }

    public void setBancoBean(BancoBean bancoBean) {
        this.bancoBean = bancoBean;
    }

    public List<BancoBean> getListBanco() {
        return listBanco;
    }

    public void setListBanco(List<BancoBean> listBanco) {
        this.listBanco = listBanco;
    }

    public void rowSelect(SelectEvent event) {
        try {
            bancoBean = (BancoBean) event.getObject();
           
            
            
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
    
    
    
    

    public List<BancoBean> getListBancoNom() {
        return listBancoNom;
    }

    public void setListBancoNom(List<BancoBean> listBancoNom) {
        this.listBancoNom = listBancoNom;
    }

    public PersonalBean getPersonalBean() {
        if (personalBean == null) {
            personalBean = new PersonalBean();
        }return personalBean;
    }

    public void setPersonalBean(PersonalBean personalBean) {
        this.personalBean = personalBean;
    }

    public ProveedorBean getProveedorBean() {
        if (proveedorBean == null) {
            proveedorBean = new ProveedorBean();
        }return proveedorBean;
    }

    public void setProveedorBean(ProveedorBean proveedorBean) {
        this.proveedorBean = proveedorBean;
    }

    public List<PersonalBean> getListaPersonalBean() {
        return listaPersonalBean;
    }

    public void setListaPersonalBean(List<PersonalBean> listaPersonalBean) {
        this.listaPersonalBean = listaPersonalBean;
    }

    public List<ProveedorBean> getListaProveedorBean() {
        return listaProveedorBean;
    }

    public void setListaProveedorBean(List<ProveedorBean> listaProveedorBean) {
        this.listaProveedorBean = listaProveedorBean;
    }

}
