package pe.marista.sigma.bean;

import java.math.BigDecimal;
import java.util.Date;

public class PlanillaNoProcesadoBean {
     private UnidadNegocioBean unidadNegocioBean;
    private Integer idPlanilla;
    private Integer anio;
    private Integer mes;
    private String empleado;
    private String cargo;
    private String codigo;
    private Date fecPlanilla;
    private PersonalBean personalBean;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private Date modiFecha;
    private String modiVer;
    private Integer horas;
    private Integer laboracion;

    //VARIABLES DE AYUDA
    //PRIMARA PARTE 
    private BigDecimal remuneracion; //1
    private BigDecimal bonificacion; //2
    private BigDecimal aisgFam; //3
    private BigDecimal boniCargo; //4
    private BigDecimal boniReg; //7
    private BigDecimal adl1; //8
    private BigDecimal adl2; //9
    private BigDecimal evaluacion; //10
    private BigDecimal nivelacion; //11
    private BigDecimal heFijas; //12
    private BigDecimal heVar; //13
    private BigDecimal compVac; //17
    private BigDecimal remVac; //18
    private BigDecimal asigOtros; //19
    private BigDecimal aum1; //20
    private BigDecimal aum2; //21
    private BigDecimal gratOrd; //22
    private BigDecimal totRem; //23
    private BigDecimal noRemu; //24

    //SEGUNDA PARTE
    private BigDecimal snp; //1
    private BigDecimal vidaSa; //2
    private BigDecimal quintaCat; //3
    private BigDecimal apOblAfp; //4
    private BigDecimal apVolAfp; //5
    private BigDecimal primaAfp; //6
    private BigDecimal cVarAfp; //8
    private BigDecimal faltas; //9
    private BigDecimal tardanzas; //10
    private BigDecimal adelantos; //11
    private BigDecimal prestamos; //12
    private BigDecimal dsctoJudic; //14
    private BigDecimal otros; //16
    private BigDecimal asigEduc; //17
    private BigDecimal eps; //21
    private BigDecimal totDesc; //23
    private BigDecimal liquido; //24

    //TERCERA PARTE
    private BigDecimal salud; //1 
    private BigDecimal aportEps; //2
    private BigDecimal totAport; //12
    
    private String estado;

    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean == null) {
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public Integer getIdPlanilla() {
        return idPlanilla;
    }

    public void setIdPlanilla(Integer idPlanilla) {
        this.idPlanilla = idPlanilla;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Date getFecPlanilla() {
        return fecPlanilla;
    }

    public void setFecPlanilla(Date fecPlanilla) {
        this.fecPlanilla = fecPlanilla;
    }

    public PersonalBean getPersonalBean() {
        if (personalBean == null) {
            personalBean = new PersonalBean();
        }
        return personalBean;
    }

    public void setPersonalBean(PersonalBean personalBean) {
        this.personalBean = personalBean;
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

    public Date getModiFecha() {
        return modiFecha;
    }

    public void setModiFecha(Date modiFecha) {
        this.modiFecha = modiFecha;
    }

    public String getModiVer() {
        return modiVer;
    }

    public void setModiVer(String modiVer) {
        this.modiVer = modiVer;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public BigDecimal getRemuneracion() {
        return remuneracion;
    }

    public void setRemuneracion(BigDecimal remuneracion) {
        this.remuneracion = remuneracion;
    }

    public BigDecimal getBonificacion() {
        return bonificacion;
    }

    public void setBonificacion(BigDecimal bonificacion) {
        this.bonificacion = bonificacion;
    }

    public BigDecimal getAisgFam() {
        return aisgFam;
    }

    public void setAisgFam(BigDecimal aisgFam) {
        this.aisgFam = aisgFam;
    }

    public BigDecimal getBoniCargo() {
        return boniCargo;
    }

    public void setBoniCargo(BigDecimal boniCargo) {
        this.boniCargo = boniCargo;
    }

    public BigDecimal getBoniReg() {
        return boniReg;
    }

    public void setBoniReg(BigDecimal boniReg) {
        this.boniReg = boniReg;
    }

    public BigDecimal getAdl1() {
        return adl1;
    }

    public void setAdl1(BigDecimal adl1) {
        this.adl1 = adl1;
    }

    public BigDecimal getAdl2() {
        return adl2;
    }

    public void setAdl2(BigDecimal adl2) {
        this.adl2 = adl2;
    }

    public BigDecimal getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(BigDecimal evaluacion) {
        this.evaluacion = evaluacion;
    }

    public BigDecimal getNivelacion() {
        return nivelacion;
    }

    public void setNivelacion(BigDecimal nivelacion) {
        this.nivelacion = nivelacion;
    }

    public BigDecimal getHeFijas() {
        return heFijas;
    }

    public void setHeFijas(BigDecimal heFijas) {
        this.heFijas = heFijas;
    }

    public BigDecimal getHeVar() {
        return heVar;
    }

    public void setHeVar(BigDecimal heVar) {
        this.heVar = heVar;
    }

    public BigDecimal getCompVac() {
        return compVac;
    }

    public void setCompVac(BigDecimal compVac) {
        this.compVac = compVac;
    }

    public BigDecimal getRemVac() {
        return remVac;
    }

    public void setRemVac(BigDecimal remVac) {
        this.remVac = remVac;
    }

    public BigDecimal getAsigOtros() {
        return asigOtros;
    }

    public void setAsigOtros(BigDecimal asigOtros) {
        this.asigOtros = asigOtros;
    }

    public BigDecimal getAum1() {
        return aum1;
    }

    public void setAum1(BigDecimal aum1) {
        this.aum1 = aum1;
    }

    public BigDecimal getAum2() {
        return aum2;
    }

    public void setAum2(BigDecimal aum2) {
        this.aum2 = aum2;
    }

    public BigDecimal getGratOrd() {
        return gratOrd;
    }

    public void setGratOrd(BigDecimal gratOrd) {
        this.gratOrd = gratOrd;
    }

    public BigDecimal getTotRem() {
        return totRem;
    }

    public void setTotRem(BigDecimal totRem) {
        this.totRem = totRem;
    }

    public BigDecimal getNoRemu() {
        return noRemu;
    }

    public void setNoRemu(BigDecimal noRemu) {
        this.noRemu = noRemu;
    }

    public BigDecimal getSnp() {
        return snp;
    }

    public void setSnp(BigDecimal snp) {
        this.snp = snp;
    }

    public BigDecimal getVidaSa() {
        return vidaSa;
    }

    public void setVidaSa(BigDecimal vidaSa) {
        this.vidaSa = vidaSa;
    }

    public BigDecimal getQuintaCat() {
        return quintaCat;
    }

    public void setQuintaCat(BigDecimal quintaCat) {
        this.quintaCat = quintaCat;
    }

    public BigDecimal getApOblAfp() {
        return apOblAfp;
    }

    public void setApOblAfp(BigDecimal apOblAfp) {
        this.apOblAfp = apOblAfp;
    }

    public BigDecimal getApVolAfp() {
        return apVolAfp;
    }

    public void setApVolAfp(BigDecimal apVolAfp) {
        this.apVolAfp = apVolAfp;
    }

    public BigDecimal getPrimaAfp() {
        return primaAfp;
    }

    public void setPrimaAfp(BigDecimal primaAfp) {
        this.primaAfp = primaAfp;
    }

    public BigDecimal getcVarAfp() {
        return cVarAfp;
    }

    public void setcVarAfp(BigDecimal cVarAfp) {
        this.cVarAfp = cVarAfp;
    }

    public BigDecimal getFaltas() {
        return faltas;
    }

    public void setFaltas(BigDecimal faltas) {
        this.faltas = faltas;
    }

    public BigDecimal getTardanzas() {
        return tardanzas;
    }

    public void setTardanzas(BigDecimal tardanzas) {
        this.tardanzas = tardanzas;
    }

    public BigDecimal getAdelantos() {
        return adelantos;
    }

    public void setAdelantos(BigDecimal adelantos) {
        this.adelantos = adelantos;
    }

    public BigDecimal getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(BigDecimal prestamos) {
        this.prestamos = prestamos;
    }

    public BigDecimal getDsctoJudic() {
        return dsctoJudic;
    }

    public void setDsctoJudic(BigDecimal dsctoJudic) {
        this.dsctoJudic = dsctoJudic;
    }

    public BigDecimal getOtros() {
        return otros;
    }

    public void setOtros(BigDecimal otros) {
        this.otros = otros;
    }

    public BigDecimal getAsigEduc() {
        return asigEduc;
    }

    public void setAsigEduc(BigDecimal asigEduc) {
        this.asigEduc = asigEduc;
    }

    public BigDecimal getEps() {
        return eps;
    }

    public void setEps(BigDecimal eps) {
        this.eps = eps;
    }

    public BigDecimal getTotDesc() {
        return totDesc;
    }

    public void setTotDesc(BigDecimal totDesc) {
        this.totDesc = totDesc;
    }

    public BigDecimal getLiquido() {
        return liquido;
    }

    public void setLiquido(BigDecimal liquido) {
        this.liquido = liquido;
    }

    public BigDecimal getSalud() {
        return salud;
    }

    public void setSalud(BigDecimal salud) {
        this.salud = salud;
    }

    public BigDecimal getAportEps() {
        return aportEps;
    }

    public void setAportEps(BigDecimal aportEps) {
        this.aportEps = aportEps;
    }

    public BigDecimal getTotAport() {
        return totAport;
    }

    public void setTotAport(BigDecimal totAport) {
        this.totAport = totAport;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Integer getHoras() {
        return horas;
    }

    public void setHoras(Integer horas) {
        this.horas = horas;
    }

    public Integer getLaboracion() {
        return laboracion;
    }

    public void setLaboracion(Integer laboracion) {
        this.laboracion = laboracion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
