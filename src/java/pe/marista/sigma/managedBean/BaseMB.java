package pe.marista.sigma.managedBean;

import pe.marista.sigma.MaristaConstantes;

public class BaseMB{

    private int numRegistrosTablas = MaristaConstantes.TABLA_NUMERO_REGISTROS_MOSTRAR;
    private int numRegistrosTablasMin = MaristaConstantes.TABLA_NUMERO_REGISTROS_MOSTRAR_MIN;
    private int numRegistrosTablasPorFiltroMin = MaristaConstantes.TABLA_NUMERO_REGISTROS_MOSTRAR_POR_FILTRO_MIN;
    private int numRegistrosTablasPorFiltro = MaristaConstantes.TABLA_NUMERO_REGISTROS_MOSTRAR_POR_FILTRO;
    private int numRegistrosTablasLista = MaristaConstantes.TABLA_NUMERO_REGISTROS_MOSTRAR_LISTA;
    private String anchoTablas = MaristaConstantes.TABLA_ANCHO;
    private String anchoTablasBig = MaristaConstantes.TABLA_ANCHO_BIG;
    private String anchoTablasGrande = MaristaConstantes.TABLA_ANCHO_GRANDE;
    private String anchoTablasExtraGrande = MaristaConstantes.TABLA_ANCHO_EXTRA_GRANDE;
    private String anchoTablasSuperGrande = MaristaConstantes.TABLA_ANCHO_SUPER_GRANDE;
    private String anchoTablasShort = MaristaConstantes.TABLA_ANCHO_SHORT;
    private String anchoTablasPopup = MaristaConstantes.TABLA_ANCHO_POPUP;
    private int numRegistrosTablasReportes = MaristaConstantes.TABLA_NUMERO_REGISTROS_REPORTES;

    public int getNumRegistrosTablasReportes() {
        return numRegistrosTablasReportes;
    }

    public void setNumRegistrosTablasReportes(int numRegistrosTablasReportes) {
        this.numRegistrosTablasReportes = numRegistrosTablasReportes;
    }

    public String getAnchoTablasPopup() {
        return anchoTablasPopup;
    }

    public void setAnchoTablasPopup(String anchoTablasPopup) {
        this.anchoTablasPopup = anchoTablasPopup;
    }

    public int getNumRegistrosTablas() {
        return numRegistrosTablas;
    }

    public void setNumRegistrosTablas(int numRegistrosTablas) {
        this.numRegistrosTablas = numRegistrosTablas;
    }

    public String getAnchoTablas() {
        return anchoTablas;
    }

    public void setAnchoTablas(String anchoTablas) {
        this.anchoTablas = anchoTablas;
    }

    public String getAnchoTablasBig() {
        return anchoTablasBig;
    }

    public void setAnchoTablasBig(String anchoTablasBig) {
        this.anchoTablasBig = anchoTablasBig;
    }

    public String getAnchoTablasShort() {
        return anchoTablasShort;
    }

    public void setAnchoTablasShort(String anchoTablasShort) {
        this.anchoTablasShort = anchoTablasShort;
    }

    public String getAnchoTablasGrande() {
        return anchoTablasGrande;
    }

    public void setAnchoTablasGrande(String anchoTablasGrande) {
        this.anchoTablasGrande = anchoTablasGrande;
    }

    public String getAnchoTablasExtraGrande() {
        return anchoTablasExtraGrande;
    }

    public void setAnchoTablasExtraGrande(String anchoTablasExtraGrande) {
        this.anchoTablasExtraGrande = anchoTablasExtraGrande;
    }

    public String getAnchoTablasSuperGrande() {
        return anchoTablasSuperGrande;
    }

    public void setAnchoTablasSuperGrande(String anchoTablasSuperGrande) {
        this.anchoTablasSuperGrande = anchoTablasSuperGrande;
    } 

    public int getNumRegistrosTablasMin() {
        return numRegistrosTablasMin;
    }

    public void setNumRegistrosTablasMin(int numRegistrosTablasMin) {
        this.numRegistrosTablasMin = numRegistrosTablasMin;
    }

    public int getNumRegistrosTablasPorFiltro() {
        return numRegistrosTablasPorFiltro;
    }

    public void setNumRegistrosTablasPorFiltro(int numRegistrosTablasPorFiltro) {
        this.numRegistrosTablasPorFiltro = numRegistrosTablasPorFiltro;
    }

    public int getNumRegistrosTablasLista() {
        return numRegistrosTablasLista;
    }

    public void setNumRegistrosTablasLista(int numRegistrosTablasLista) {
        this.numRegistrosTablasLista = numRegistrosTablasLista;
    }

    public int getNumRegistrosTablasPorFiltroMin() {
        return numRegistrosTablasPorFiltroMin;
    }

    public void setNumRegistrosTablasPorFiltroMin(int numRegistrosTablasPorFiltroMin) {
        this.numRegistrosTablasPorFiltroMin = numRegistrosTablasPorFiltroMin;
    }
    
    
    
    
}
