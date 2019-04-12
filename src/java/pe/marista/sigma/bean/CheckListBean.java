/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;
import pe.marista.sigma.MaristaConstantes;

/**
 *
 * @author MS001
 */
public class CheckListBean implements Serializable {

    private ProcesoBean procesoBean;
    private Integer idCheckList;
    private String documento;
    private CodigoBean tipoCopiaBean;
    private boolean flg01;
    private boolean flg02;
    private boolean flg03;
    private boolean flg04;
    private boolean flg05;
    private boolean flg06;
    private boolean flg07;
    private boolean flg08;
    private boolean flg09;
    private boolean flg10;
    private boolean flg11;
    private boolean flg23;
    private boolean flg24;
    private boolean flg25;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private Boolean flgObligatorio;
    
     private boolean collapsed=true;
    //ayuda vista
    private String flg01Vista;
    private String flg02Vista;
    private String flg03Vista;
    private String flg04Vista;
    private String flg05Vista;
    private String flg06Vista;
    private String flg07Vista;
    private String flg08Vista;
    private String flg09Vista;
    private String flg10Vista;
    private String flg11Vista;
    private String flg23Vista;
    private String flg24Vista;
    private String flg25Vista;
    private String flgObligatorioVista;

    public ProcesoBean getProcesoBean() {
        if (procesoBean == null) {
            procesoBean = new ProcesoBean();
        }
        return procesoBean;
    }

    public void setProcesoBean(ProcesoBean procesoBean) {
        this.procesoBean = procesoBean;
    }

    public Integer getIdCheckList() {
        return idCheckList;
    }

    public void setIdCheckList(Integer idCheckList) {
        this.idCheckList = idCheckList;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public CodigoBean getTipoCopiaBean() {
        if (tipoCopiaBean == null) {
            tipoCopiaBean = new CodigoBean();
        }
        return tipoCopiaBean;
    }

    public void setTipoCopiaBean(CodigoBean tipoCopiaBean) {
        this.tipoCopiaBean = tipoCopiaBean;
    }

    public boolean isFlg01() {
        return flg01;
    }

    public void setFlg01(boolean flg01) {
        this.flg01 = flg01;
    }

    public boolean isFlg02() {
        return flg02;
    }

    public void setFlg02(boolean flg02) {
        this.flg02 = flg02;
    }

    public boolean isFlg03() {
        return flg03;
    }

    public void setFlg03(boolean flg03) {
        this.flg03 = flg03;
    }

    public boolean isFlg04() {
        return flg04;
    }

    public void setFlg04(boolean flg04) {
        this.flg04 = flg04;
    }

    public boolean isFlg05() {
        return flg05;
    }

    public void setFlg05(boolean flg05) {
        this.flg05 = flg05;
    }

    public boolean isFlg06() {
        return flg06;
    }

    public void setFlg06(boolean flg06) {
        this.flg06 = flg06;
    }

    public boolean isFlg07() {
        return flg07;
    }

    public void setFlg07(boolean flg07) {
        this.flg07 = flg07;
    }

    public boolean isFlg08() {
        return flg08;
    }

    public void setFlg08(boolean flg08) {
        this.flg08 = flg08;
    }

    public boolean isFlg09() {
        return flg09;
    }

    public void setFlg09(boolean flg09) {
        this.flg09 = flg09;
    }

    public boolean isFlg10() {
        return flg10;
    }

    public void setFlg10(boolean flg10) {
        this.flg10 = flg10;
    }

    public boolean isFlg11() {
        return flg11;
    }

    public void setFlg11(boolean flg11) {
        this.flg11 = flg11;
    }

    public boolean isFlg23() {
        return flg23;
    }

    public void setFlg23(boolean flg23) {
        this.flg23 = flg23;
    }

    public boolean isFlg24() {
        return flg24;
    }

    public void setFlg24(boolean flg24) {
        this.flg24 = flg24;
    }

    public boolean isFlg25() {
        return flg25;
    }

    public void setFlg25(boolean flg25) {
        this.flg25 = flg25;
    }

    public String getCreaPor() {
        return creaPor;
    }

    public void setCreaPor(String creaPor) {
        this.creaPor = creaPor;
    }

    public Date getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(Date creaFecha) {
        this.creaFecha = creaFecha;
    }

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public String getFlg01Vista() {
        if (flg01 == true) {
            flg01Vista = MaristaConstantes.SI;
        }
        if (flg01 == false) {
            flg01Vista = MaristaConstantes.NO;
        }
        return flg01Vista;
    }

    public void setFlg01Vista(String flg01Vista) {
        this.flg01Vista = flg01Vista;
    }

    public String getFlg02Vista() {
        if (flg02 == true) {
            flg02Vista = MaristaConstantes.SI;
        }
        if (flg02 == false) {
            flg02Vista = MaristaConstantes.NO;
        }
        return flg02Vista;
    }

    public void setFlg02Vista(String flg02Vista) {
        this.flg02Vista = flg02Vista;
    }

    public String getFlg03Vista() {
        if (flg03 == true) {
            flg03Vista = MaristaConstantes.SI;
        }
        if (flg03 == false) {
            flg03Vista = MaristaConstantes.NO;
        }
        return flg03Vista;
    }

    public void setFlg03Vista(String flg03Vista) {
        this.flg03Vista = flg03Vista;
    }

    public String getFlg04Vista() {
        if (flg04 == true) {
            flg04Vista = MaristaConstantes.SI;
        }
        if (flg04 == false) {
            flg04Vista = MaristaConstantes.NO;
        }
        return flg04Vista;
    }

    public void setFlg04Vista(String flg04Vista) {
        this.flg04Vista = flg04Vista;
    }

    public String getFlg05Vista() {
        if (flg05 == true) {
            flg05Vista = MaristaConstantes.SI;
        }
        if (flg05 == false) {
            flg05Vista = MaristaConstantes.NO;
        }
        return flg05Vista;
    }

    public void setFlg05Vista(String flg05Vista) {
        this.flg05Vista = flg05Vista;
    }

    public String getFlg06Vista() {
        if (flg06 == true) {
            flg06Vista = MaristaConstantes.SI;
        }
        if (flg06 == false) {
            flg06Vista = MaristaConstantes.NO;
        }
        return flg06Vista;
    }

    public void setFlg06Vista(String flg06Vista) {
        this.flg06Vista = flg06Vista;
    }

    public String getFlg07Vista() {
        if (flg07 == true) {
            flg07Vista = MaristaConstantes.SI;
        }
        if (flg07 == false) {
            flg07Vista = MaristaConstantes.NO;
        }
        return flg07Vista;
    }

    public void setFlg07Vista(String flg07Vista) {
        this.flg07Vista = flg07Vista;
    }

    public String getFlg08Vista() {
        if (flg08 == true) {
            flg08Vista = MaristaConstantes.SI;
        }
        if (flg08 == false) {
            flg08Vista = MaristaConstantes.NO;
        }
        return flg08Vista;
    }

    public void setFlg08Vista(String flg08Vista) {
        this.flg08Vista = flg08Vista;
    }

    public String getFlg09Vista() {
        if (flg09 == true) {
            flg09Vista = MaristaConstantes.SI;
        }
        if (flg09 == false) {
            flg09Vista = MaristaConstantes.NO;
        }
        return flg09Vista;
    }

    public void setFlg09Vista(String flg09Vista) {
        this.flg09Vista = flg09Vista;
    }

    public String getFlg10Vista() {
        if (flg10 == true) {
            flg10Vista = MaristaConstantes.SI;
        }
        if (flg10 == false) {
            flg10Vista = MaristaConstantes.NO;
        }
        return flg10Vista;
    }

    public void setFlg10Vista(String flg10Vista) {
        this.flg10Vista = flg10Vista;
    }

    public String getFlg11Vista() {
        if (flg11 == true) {
            flg11Vista = MaristaConstantes.SI;
        }
        if (flg11 == false) {
            flg11Vista = MaristaConstantes.NO;
        }
        return flg11Vista;
    }

    public void setFlg11Vista(String flg11Vista) {
        this.flg11Vista = flg11Vista;
    }

    public String getFlg23Vista() {
        if (flg23 == true) {
            flg23Vista = MaristaConstantes.SI;
        }
        if (flg23 == false) {
            flg23Vista = MaristaConstantes.NO;
        }
        return flg23Vista;
    }

    public void setFlg23Vista(String flg23Vista) {
        this.flg23Vista = flg23Vista;
    }

    public String getFlg24Vista() {
        if (flg24 == true) {
            flg24Vista = MaristaConstantes.SI;
        }
        if (flg24 == false) {
            flg24Vista = MaristaConstantes.NO;
        }
        return flg24Vista;
    }

    public void setFlg24Vista(String flg24Vista) {
        this.flg24Vista = flg24Vista;
    }

    public String getFlg25Vista() {
        if (flg25 == true) {
            flg25Vista = MaristaConstantes.SI;
        }
        if (flg25 == false) {
            flg25Vista = MaristaConstantes.NO;
        }
        return flg25Vista;
    }

    public void setFlg25Vista(String flg25Vista) {
        this.flg25Vista = flg25Vista;
    }

    public boolean isCollapsed() {
        return collapsed;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
    }

    public Boolean getFlgObligatorio() {
        return flgObligatorio;
    }

    public void setFlgObligatorio(Boolean flgObligatorio) {
        this.flgObligatorio = flgObligatorio;
    }

    public String getFlgObligatorioVista() {
        if (flgObligatorio==true){
            flgObligatorioVista=MaristaConstantes.SI;
        }
        if (flgObligatorio==false){
            flgObligatorioVista=MaristaConstantes.NO;
        }
        return flgObligatorioVista;
    }

    public void setFlgObligatorioVista(String flgObligatorioVista) {
        this.flgObligatorioVista = flgObligatorioVista;
    }
    

}
