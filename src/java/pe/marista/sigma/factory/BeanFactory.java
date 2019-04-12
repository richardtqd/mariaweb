/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

 */
package pe.marista.sigma.factory;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.dao.EstudianteBloqueoDAO;
import pe.marista.sigma.dao.ActividadCrDAO;
import pe.marista.sigma.dao.ActividadDAO;
import pe.marista.sigma.dao.AsientoDAO;
import pe.marista.sigma.dao.BecaDAO;
import pe.marista.sigma.dao.BloqueoDAO;
import pe.marista.sigma.dao.CajaChicaDAO;
import pe.marista.sigma.dao.CajaChicaLiquidacionDAO;
import pe.marista.sigma.dao.CajaGenDAO;
import pe.marista.sigma.dao.CargoDAO;
import pe.marista.sigma.dao.CargoUniNegDAO;
import pe.marista.sigma.dao.CarreraAreaDAO;
import pe.marista.sigma.dao.CarreraDAO;
import pe.marista.sigma.dao.CarreraSubAreaDAO;
import pe.marista.sigma.dao.CatalogoCategoriaDAO;
import pe.marista.sigma.dao.CatalogoDAO;
import pe.marista.sigma.dao.CatalogoFamiliaDAO;
import pe.marista.sigma.dao.CentroResponsabilidadDAO;
import pe.marista.sigma.dao.ChequeAnuladoDAO;
import pe.marista.sigma.dao.ChequeDAO;
import pe.marista.sigma.dao.ConceptoDAO;
import pe.marista.sigma.dao.CronogramaPagoDAO;
import pe.marista.sigma.dao.CuentaBancoDAO;
import pe.marista.sigma.dao.CuentasPorCobrarDAO;
import pe.marista.sigma.dao.CuotaIngresoDAO;
import pe.marista.sigma.dao.DetActividadDAO;
import pe.marista.sigma.dao.DetEsquelaDAO;
import pe.marista.sigma.dao.DetraccionDAO;
import pe.marista.sigma.dao.DiccionarioDAO;
import pe.marista.sigma.dao.DocEgresoDAO;
import pe.marista.sigma.dao.DocIngresoDAO;
import pe.marista.sigma.dao.DocIngresoSerieDAO;
import pe.marista.sigma.dao.DocumentoCargoDAO;
import pe.marista.sigma.dao.DocumentoDAO;
import pe.marista.sigma.dao.EnfermedadDAO;
import pe.marista.sigma.dao.EnvioBcoDAO;
import pe.marista.sigma.dao.EsquelaDAO;
import pe.marista.sigma.dao.EstudianteDAO;
import pe.marista.sigma.dao.EstudianteDocumentoDAO;
import pe.marista.sigma.dao.EstudianteMovilidadDAO;
import pe.marista.sigma.dao.EstudianteNacimientoDAO;
import pe.marista.sigma.dao.EstudianteRetiroDAO;
import pe.marista.sigma.dao.EstudianteVacunaDAO;
import pe.marista.sigma.dao.EventoDAO;
import pe.marista.sigma.dao.EventoTipoPaganteDAO;
import pe.marista.sigma.dao.FalloDAO;
import pe.marista.sigma.dao.FichaDAO;
import pe.marista.sigma.dao.GradoAcademicoDAO;
//import pe.marista.sigma.dao.ImpresoraDAO;
import pe.marista.sigma.dao.ImpresoraDAO;
import pe.marista.sigma.dao.IndicadorDAO;
import pe.marista.sigma.dao.InventarioAlmacenDAO;
import pe.marista.sigma.dao.JefeUniOrgDAO;
import pe.marista.sigma.dao.LineaEstrategicaDAO;
import pe.marista.sigma.dao.MovimientoActivoDAO;
import pe.marista.sigma.dao.MovilidadDAO;
import pe.marista.sigma.dao.NivelAcademicoDAO;
import pe.marista.sigma.dao.NivelTipoAccesoDAO;
import pe.marista.sigma.dao.ObjetivoEstrategicoDAO;
import pe.marista.sigma.dao.ObjetivoEstrategicoDetDAO;
import pe.marista.sigma.dao.ObjetivoOperativoDAO;
import pe.marista.sigma.dao.OrdenCompraDAO;
import pe.marista.sigma.dao.OrdenCompraDetalleDAO;
import pe.marista.sigma.dao.PaganteDAO;
import pe.marista.sigma.dao.PagoBancoDAO;
import pe.marista.sigma.dao.PaisDAO;
import pe.marista.sigma.dao.PerfilModuloDAO;
import pe.marista.sigma.dao.PeriodoDAO;
import pe.marista.sigma.dao.PersonaDAO;
import pe.marista.sigma.dao.PersonalAlergiaDAO;
import pe.marista.sigma.dao.PersonalCargoDAO;
import pe.marista.sigma.dao.PersonalContratoDAO;
import pe.marista.sigma.dao.PersonalDAO;
import pe.marista.sigma.dao.PersonalDependienteDAO;
import pe.marista.sigma.dao.PersonalDocumentoDAO;
import pe.marista.sigma.dao.PersonalEnfermedadDAO;
import pe.marista.sigma.dao.PersonalExperienciaDAO;
import pe.marista.sigma.dao.PersonalFormacionDAO;
import pe.marista.sigma.dao.PersonalIdiomaDAO;
import pe.marista.sigma.dao.PersonalOtrosEstudiosDAO;
import pe.marista.sigma.dao.PersonalProcesoJudicialDAO;
import pe.marista.sigma.dao.PlanEstrategicoDAO;
import pe.marista.sigma.dao.PlanOperativoDAO;
import pe.marista.sigma.dao.PlanillaDAO;
import pe.marista.sigma.dao.PresupuestoDAO;
import pe.marista.sigma.dao.PresupuestoNewDAO;
import pe.marista.sigma.dao.PresupuestoUniOrgDAO;
import pe.marista.sigma.dao.ProcesoBancoDAO;
import pe.marista.sigma.dao.ProcesoEnvioDAO;
import pe.marista.sigma.dao.ProcesoErrorDAO;
import pe.marista.sigma.dao.ProcesoFalloDAO;
import pe.marista.sigma.dao.ProcesoFileDAO;
import pe.marista.sigma.dao.ProcesoFilesDAO;
import pe.marista.sigma.dao.ProcesoFinalDAO;
import pe.marista.sigma.dao.ProcesoRecuperacionDAO;
import pe.marista.sigma.dao.ProgramacionDsctoDAO;
import pe.marista.sigma.dao.RecEnvDAO;
import pe.marista.sigma.dao.RecibosMoraDAO;
import pe.marista.sigma.dao.RecuperacionBcoDAO;
import pe.marista.sigma.dao.ReporteRechazoDAO;
import pe.marista.sigma.dao.RespuestasDAO;
import pe.marista.sigma.dao.SolicitudCajaCHDAO;
import pe.marista.sigma.dao.SolicitudLogisticoDAO;
import pe.marista.sigma.dao.SolicitudLogisticoDetalleDAO;
import pe.marista.sigma.dao.TipoCambioDAO;
import pe.marista.sigma.dao.TipoEnvioUniNegDAO;
import pe.marista.sigma.dao.TipoFormacionDAO;
import pe.marista.sigma.dao.TipoPaganteDAO;
import pe.marista.sigma.dao.TipoSolicitudDAO;
import pe.marista.sigma.dao.UnidadNegocioDAO;
import pe.marista.sigma.dao.UnidadOrganicaDAO;
import pe.marista.sigma.dao.UsuarioDAO;
import pe.marista.sigma.dao.ResDimensionDAO;
import pe.marista.sigma.service.EstudianteBloqueoService;
import pe.marista.sigma.service.ActividadCrService;
import pe.marista.sigma.service.ActividadService;
import pe.marista.sigma.service.AdmisionService;
import pe.marista.sigma.service.AmbienteService;
import pe.marista.sigma.service.AsientoService;
import pe.marista.sigma.service.BecaService;
import pe.marista.sigma.service.BloqueoService;
import pe.marista.sigma.service.CajaChicaLiquidacionService;
import pe.marista.sigma.service.CajaChicaMovService;
import pe.marista.sigma.service.CajaChicaService;
import pe.marista.sigma.service.CajaGenService;
import pe.marista.sigma.service.CajaService;
import pe.marista.sigma.service.CajeroService;
import pe.marista.sigma.service.CargoService;
import pe.marista.sigma.service.CargoUniNegService;
import pe.marista.sigma.service.CarreraAreaService;
import pe.marista.sigma.service.CarreraService;
import pe.marista.sigma.service.CarreraSubAreaService;
import pe.marista.sigma.service.CatalogoCategoriaService;
import pe.marista.sigma.service.CatalogoFamiliaService;
import pe.marista.sigma.service.CatalogoService;
import pe.marista.sigma.service.TipoConceptoService;
import pe.marista.sigma.service.CentroCostoService;
import pe.marista.sigma.service.CentroResponsabilidadService;
import pe.marista.sigma.service.ChequeAnuladoService;
import pe.marista.sigma.service.ChequeService;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.ConceptoService;
import pe.marista.sigma.service.ConceptoUniNegService;
import pe.marista.sigma.service.CotizacionService;
import pe.marista.sigma.service.CronogramaPagoService;
import pe.marista.sigma.service.CuentaBancoService;
import pe.marista.sigma.service.CuentasPorCobrarService;
import pe.marista.sigma.service.CuotaIngresoService;
import pe.marista.sigma.service.CursoTallerService;
import pe.marista.sigma.service.DetActividadService;
import pe.marista.sigma.service.DetCotizacionService;
import pe.marista.sigma.service.DetEsquelaService;
import pe.marista.sigma.service.DiccionarioService;
import pe.marista.sigma.service.DistritoService;
import pe.marista.sigma.service.DocIngresoSerieService;
import pe.marista.sigma.service.DocIngresoService;
import pe.marista.sigma.service.DocumentoCargoService;
import pe.marista.sigma.service.DocumentoService;
import pe.marista.sigma.service.EnfermedadService;
import pe.marista.sigma.service.EntidadService;
import pe.marista.sigma.service.EstudianteAlergiaService;
import pe.marista.sigma.service.EstudianteDocumentoService;
import pe.marista.sigma.service.EstudianteEnfermedadService;
import pe.marista.sigma.service.EstudianteMedicamentoService;
import pe.marista.sigma.service.EstudianteMovilidadService;
import pe.marista.sigma.service.EstudianteSeguroService;
import pe.marista.sigma.service.EstudianteService;
import pe.marista.sigma.service.EstudianteTraumaService;
import pe.marista.sigma.service.FamiliaService;
import pe.marista.sigma.service.FamiliarService;
import pe.marista.sigma.service.GradoAcademicoService;
import pe.marista.sigma.service.ImpresoraService;
//import pe.marista.sigma.service.ImpresoraService;
//import pe.marista.sigma.service.ImpresoraService;
import pe.marista.sigma.service.IndicadorService;
import pe.marista.sigma.service.InventarioActivoService;
import pe.marista.sigma.service.InventarioAlmacenService;
import pe.marista.sigma.service.LegajoService;
import pe.marista.sigma.service.LineaEstrategicaService;
import pe.marista.sigma.service.LogService;
import pe.marista.sigma.service.LoginService;
import pe.marista.sigma.service.MatriculaService;
import pe.marista.sigma.service.MensajeService;
import pe.marista.sigma.service.UsuarioService;
import pe.marista.sigma.service.ModuloService;
import pe.marista.sigma.service.MovimientoActivoService;
import pe.marista.sigma.service.MovilidadService;
import pe.marista.sigma.service.NivelAcademicoService;
import pe.marista.sigma.service.ObjetivoEstrategicoDetService;
import pe.marista.sigma.service.ObjetivoEstrategicoService;
import pe.marista.sigma.service.ObjetivoOperativoService;
import pe.marista.sigma.service.OrdenCompraDetalleService;
import pe.marista.sigma.service.OrdenCompraService;
import pe.marista.sigma.service.PerfilService;
import pe.marista.sigma.service.PerfilUnidadNegocioService;
import pe.marista.sigma.service.PlanEstrategicoService;
import pe.marista.sigma.service.PlanOperativoService;
import pe.marista.sigma.service.PresupuestoService;
import pe.marista.sigma.service.PaisService;
import pe.marista.sigma.service.PeriodoService;
import pe.marista.sigma.service.PersonaService;
import pe.marista.sigma.service.PersonalAlergiaService;
import pe.marista.sigma.service.PersonalCargoService;
import pe.marista.sigma.service.PersonalContratoService;
import pe.marista.sigma.service.PersonalDependienteService;
import pe.marista.sigma.service.PersonalDocumentoService;
import pe.marista.sigma.service.PersonalService;
import pe.marista.sigma.service.PersonalEnfermedadService;
import pe.marista.sigma.service.PersonalExperienciaService;
import pe.marista.sigma.service.PersonalFormacionService;
import pe.marista.sigma.service.PersonalIdiomaService;
import pe.marista.sigma.service.PersonalOtrosEstudiosService;
import pe.marista.sigma.service.PersonalProcesoJudicialService;
import pe.marista.sigma.service.PlanContableService;
import pe.marista.sigma.service.ProcesoBancoService;
import pe.marista.sigma.service.ProcesoEnvioService;
import pe.marista.sigma.service.ProcesoFileService;
import pe.marista.sigma.service.ProcesoRecuperacionService;
import pe.marista.sigma.service.ProcesoService;
import pe.marista.sigma.service.SolicitudLogisticoService;
import pe.marista.sigma.service.ProgramacionService;
import pe.marista.sigma.service.SolicitudCajaCHService;
import pe.marista.sigma.service.SolicitudLogisticoDetalleService;
import pe.marista.sigma.service.TipoCambioService;
import pe.marista.sigma.service.TipoCodigoService;
import pe.marista.sigma.service.TipoFormacionService;
import pe.marista.sigma.service.TipoSolicitudService;
import pe.marista.sigma.service.UniNegUniOrgService;
import pe.marista.sigma.service.UnidadNegocioService;
import pe.marista.sigma.service.UnidadOrganicaService;
import pe.marista.sigma.service.RegistroCompraService;
import pe.marista.sigma.service.DetRegistroCompraService;
import pe.marista.sigma.service.DetraccionService;
import pe.marista.sigma.service.DocEgresoService;
import pe.marista.sigma.service.EnvioBcoService;
import pe.marista.sigma.service.EsquelaService;
import pe.marista.sigma.service.EstudianteNacimientoService;
import pe.marista.sigma.service.EstudianteVacunaService;
import pe.marista.sigma.service.EvaluacionDesempenoService;
import pe.marista.sigma.service.EventoService;
import pe.marista.sigma.service.EventoTipoPaganteService;
import pe.marista.sigma.service.FalloService;
import pe.marista.sigma.service.FichaService;
import pe.marista.sigma.service.JefeUniOrgService;
import pe.marista.sigma.service.MotivoMovimientoService;
import pe.marista.sigma.service.NivelTipoAccesoService;
import pe.marista.sigma.service.PaganteService;
import pe.marista.sigma.service.PagoBancoService;
import pe.marista.sigma.service.PlanillaService;
import pe.marista.sigma.service.PresupuestoNewService;
import pe.marista.sigma.service.PresupuestoUniOrgService;
import pe.marista.sigma.service.ProcesoErrorService;
import pe.marista.sigma.service.ProcesoFalloService;
import pe.marista.sigma.service.ProcesoFilesService;
import pe.marista.sigma.service.ProcesoFinalService;
import pe.marista.sigma.service.ProgramacionDsctoService;
import pe.marista.sigma.service.RecEnvService;
import pe.marista.sigma.service.RecibosMoraService;
import pe.marista.sigma.service.RecuperacionBcoService;
import pe.marista.sigma.service.ReporteRechazoService;
import pe.marista.sigma.service.RespuestasService;
import pe.marista.sigma.service.TipoEnvioUniNegService;
import pe.marista.sigma.service.TipoPaganteService;
import pe.marista.sigma.util.SpringWebApplicationContext;
import pe.marista.sigma.service.ResDimensionService;
import pe.marista.sigma.service.EvaluacionDesempenoService;
import pe.marista.sigma.dao.EvaluacionDesempenoDAO;
import pe.marista.sigma.service.EstudianteRetiroService;

/**
 *
 * @author Administrador
 */
public class BeanFactory {

    public static DriverManagerDataSource getDriverManagerDataSource() {
        return (DriverManagerDataSource) SpringWebApplicationContext.
                getInstance().getBean("dataSourceMySql");
    }

    public static UsuarioDAO getUsuarioDAO() {
        return (UsuarioDAO) SpringWebApplicationContext.
                getInstance().getBean("usuarioDAO");
    }

    public static PerfilModuloDAO getPerfilUsuarioDAO() {
        return (PerfilModuloDAO) SpringWebApplicationContext.
                getInstance().getBean("perfilUsuarioDAO");
    }

    public static IndicadorDAO getIndicadorDAO() {
        return (IndicadorDAO) SpringWebApplicationContext.
                getInstance().getBean("indicadorDAO");
    }

    public static PlanEstrategicoDAO getPlanEstrategicoDAO() {
        return (PlanEstrategicoDAO) SpringWebApplicationContext.
                getInstance().getBean("planEstrategicoDAO");
    }

    public static PlanOperativoDAO getPlanOperativoDAO() {
        return (PlanOperativoDAO) SpringWebApplicationContext.
                getInstance().getBean("planOperativoDAO");
    }

    public static PresupuestoDAO getPresupuestoDAO() {
        return (PresupuestoDAO) SpringWebApplicationContext.
                getInstance().getBean("presupuestoDAO");
    }

    public static TipoFormacionDAO getTipoFormacionDAO() {
        return (TipoFormacionDAO) SpringWebApplicationContext.
                getInstance().getBean("tipoFormacionDAO");
    }

    public static NivelAcademicoDAO getNivelAcademicoDAO() {
        return (NivelAcademicoDAO) SpringWebApplicationContext.
                getInstance().getBean("nivelAcademicoDAO");
    }

    public static GradoAcademicoDAO getGradoAcademicoDAO() {
        return (GradoAcademicoDAO) SpringWebApplicationContext.
                getInstance().getBean("gradoAcademicoDAO");
    }

    public static CatalogoDAO getCatalogoDAO() {
        return (CatalogoDAO) SpringWebApplicationContext.
                getInstance().getBean("catalogoDAO");
    }

    public static UnidadOrganicaDAO getUnidadOrganicaDAO() {
        return (UnidadOrganicaDAO) SpringWebApplicationContext.
                getInstance().getBean("unidadOrganicaDAO");
    }

    public static DocIngresoDAO getDocIngresoDAO() {
        return (DocIngresoDAO) SpringWebApplicationContext.
                getInstance().getBean("docIngresoDAO");
    }

    public static PersonalDAO getPersonalDAO() {
        return (PersonalDAO) SpringWebApplicationContext.
                getInstance().getBean("personalDAO");
    }

    public static ActividadDAO getActividadDAO() {
        return (ActividadDAO) SpringWebApplicationContext.
                getInstance().getBean("actividadDAO");
    }

    public static ObjetivoOperativoDAO getObjetivoOperativoDAO() {
        return (ObjetivoOperativoDAO) SpringWebApplicationContext.
                getInstance().getBean("objetivoOperativoDAO");
    }

    public static PersonalDependienteDAO getPersonalDependienteDAO() {
        return (PersonalDependienteDAO) SpringWebApplicationContext.
                getInstance().getBean("personalDependiente");
    }

    public static SolicitudLogisticoDAO getSolicitudLogisticoDAO() {
        return (SolicitudLogisticoDAO) SpringWebApplicationContext.
                getInstance().getBean("solicitudLogisticoDAO");
    }

    public static CarreraDAO getCarreraDAO() {
        return (CarreraDAO) SpringWebApplicationContext.
                getInstance().getBean("carreraDAO");
    }

    public static CarreraAreaDAO getCarreraAreaDAO() {
        return (CarreraAreaDAO) SpringWebApplicationContext.
                getInstance().getBean("carreraAreaDAO");
    }

    public static CarreraSubAreaDAO getCarreraSubAreaDAO() {
        return (CarreraSubAreaDAO) SpringWebApplicationContext.
                getInstance().getBean("carreraSubAreaDAO");
    }

    public static SolicitudLogisticoDetalleDAO getSolicitudLogisticoDetalleDAO() {
        return (SolicitudLogisticoDetalleDAO) SpringWebApplicationContext.
                getInstance().getBean("solicitudLogisticoDetalleDAO");
    }

    public static EstudianteDAO getEstudianteDAO() {
        return (EstudianteDAO) SpringWebApplicationContext.
                getInstance().getBean("estudianteDAO");
    }

    public static EstudianteDocumentoDAO getEstudianteDocumentoDAO() {
        return (EstudianteDocumentoDAO) SpringWebApplicationContext.
                getInstance().getBean("estudianteDocumentoDAO");
    }

    public static PersonalCargoDAO getPersonalCargoDAO() {
        return (PersonalCargoDAO) SpringWebApplicationContext.
                getInstance().getBean("personalCargoDAO");
    }

    public static CargoUniNegDAO getCargoUniNegDAO() {
        return (CargoUniNegDAO) SpringWebApplicationContext.
                getInstance().getBean("cargoUniNegDAO");
    }

    public static DocIngresoSerieDAO getDocIngresoSerieDAO() {
        return (DocIngresoSerieDAO) SpringWebApplicationContext.
                getInstance().getBean("docIngresoSerieDAO");
    }

    public static PersonalEnfermedadDAO getPersonalEnfermedadDAO() {
        return (PersonalEnfermedadDAO) SpringWebApplicationContext.
                getInstance().getBean("personalEnfermedadDAO");
    }

    public static EnfermedadDAO getEnfermedadDAO() {
        return (EnfermedadDAO) SpringWebApplicationContext.
                getInstance().getBean("enfermedadDAO");
    }

    public static PersonalExperienciaDAO getPersonalExperienciaDAO() {
        return (PersonalExperienciaDAO) SpringWebApplicationContext.
                getInstance().getBean("personalExperienciaDAO");
    }

    public static PersonalContratoDAO getPersonalContratoDAO() {
        return (PersonalContratoDAO) SpringWebApplicationContext.
                getInstance().getBean("personalContratoDAO");
    }

    public static OrdenCompraDAO getOrdenCompraDAO() {
        return (OrdenCompraDAO) SpringWebApplicationContext.
                getInstance().getBean("ordenCompraDAO");
    }

    public static PersonalProcesoJudicialDAO getPersonalProcesoJudicialDAO() {
        return (PersonalProcesoJudicialDAO) SpringWebApplicationContext.
                getInstance().getBean("personalProcesoJudicialDAO");
    }

    public static TipoCambioDAO getTipoCambioDAO() {
        return (TipoCambioDAO) SpringWebApplicationContext.
                getInstance().getBean("tipoCambioDAO");
    }

    public static PersonalAlergiaDAO getPersonalAlergiaDAO() {
        return (PersonalAlergiaDAO) SpringWebApplicationContext.
                getInstance().getBean("personalAlergiaDAO");
    }

    public static CuentasPorCobrarDAO getCuentasPorCobrarDAO() {
        return (CuentasPorCobrarDAO) SpringWebApplicationContext.
                getInstance().getBean("cuentasPorCobrarDAO");
    }

    public static PersonaDAO getPersonaDAO() {
        return (PersonaDAO) SpringWebApplicationContext.
                getInstance().getBean("personaDAO");
    }

    public static PersonalFormacionDAO getPersonalFormacionDAO() {
        return (PersonalFormacionDAO) SpringWebApplicationContext.
                getInstance().getBean("personalFormacionDAO");
    }

    public static PersonalIdiomaDAO getPersonalIdiomaDAO() {
        return (PersonalIdiomaDAO) SpringWebApplicationContext.
                getInstance().getBean("personalIdiomaDAO");
    }

    public static OrdenCompraDetalleDAO getOrdenCompraDetalleDAO() {
        return (OrdenCompraDetalleDAO) SpringWebApplicationContext.
                getInstance().getBean("ordenCompraDetalleDAO");
    }

    public static BecaDAO getBecaDAO() {
        return (BecaDAO) SpringWebApplicationContext.
                getInstance().getBean("becaDAO");
    }

    public static DiccionarioDAO getDiccionarioDAO() {
        return (DiccionarioDAO) SpringWebApplicationContext.
                getInstance().getBean("diccionarioDAO");
    }

    public static PersonalOtrosEstudiosDAO getPersonalOtrosEstudiosDAO() {
        return (PersonalOtrosEstudiosDAO) SpringWebApplicationContext.
                getInstance().getBean("personalOtrosEstudiosDAO");
    }

    public static CatalogoFamiliaDAO getCatalogoFamiliaDAO() {
        return (CatalogoFamiliaDAO) SpringWebApplicationContext.
                getInstance().getBean("catalogoFamiliaDAO");
    }

    public static CatalogoCategoriaDAO getCatalogoCategoriaDAO() {
        return (CatalogoCategoriaDAO) SpringWebApplicationContext.
                getInstance().getBean("catalogoCategoriaDAO");
    }

    public static DocumentoDAO getDocumentoDAO() {
        return (DocumentoDAO) SpringWebApplicationContext.
                getInstance().getBean("documentoDAO");
    }

    public static DocumentoCargoDAO getDocumentoCargoDAO() {
        return (DocumentoCargoDAO) SpringWebApplicationContext.
                getInstance().getBean("documentoCargoDAO");
    }

    public static CargoDAO getCargoDAO() {
        return (CargoDAO) SpringWebApplicationContext.
                getInstance().getBean("cargoDAO");
    }

    public static CentroResponsabilidadDAO getCentroResponsabilidadDAO() {
        return (CentroResponsabilidadDAO) SpringWebApplicationContext.
                getInstance().getBean("centroResponsabilidadDAO");
    }

    //PaisDAO
    public static PaisDAO getPaisDAO() {
        return (PaisDAO) SpringWebApplicationContext.
                getInstance().getBean("PaisDAO");
    }

    public static PersonalDocumentoDAO getPersonalDocumentoDAO() {
        return (PersonalDocumentoDAO) SpringWebApplicationContext.
                getInstance().getBean("personalDocumentoDAO");
    }

    public static MovimientoActivoDAO getMovActivoDAO() {
        return (MovimientoActivoDAO) SpringWebApplicationContext.
                getInstance().getBean("MovActivoDAO");
    }

    public static InventarioAlmacenDAO getInventarioAlmacenDAO() {
        return (InventarioAlmacenDAO) SpringWebApplicationContext.
                getInstance().getBean("inventarioAlmacenDAO");
    }

    //Linea Estrategica DAO 
    public static LineaEstrategicaDAO getLineaEstrategicaDAO() {
        return (LineaEstrategicaDAO) SpringWebApplicationContext.
                getInstance().getBean("lineaEstrategicaDAO");
    }

    //Objetivo Estrategico
    public static ObjetivoEstrategicoDAO getObjetivoEstrategicoDAO() {
        return (ObjetivoEstrategicoDAO) SpringWebApplicationContext.
                getInstance().getBean("objetivoEstrategicoDAO");
    }

    //Objetivo Estrategico Det
    public static ObjetivoEstrategicoDetDAO getObjetivoEstrategicoDetDAO() {
        return (ObjetivoEstrategicoDetDAO) SpringWebApplicationContext.
                getInstance().getBean("objetivoEstrategicoDetDAO");
    }

    //Peirodo 
    public static PeriodoDAO getPeriodoDAO() {
        return (PeriodoDAO) SpringWebApplicationContext.
                getInstance().getBean("periodoDAO");
    }

    //solicitud caja chica 
    public static SolicitudCajaCHDAO getSolicitudCajaCHDAO() {
        return (SolicitudCajaCHDAO) SpringWebApplicationContext.
                getInstance().getBean("solicitudCajaCHDAO");
    }

    //Proceso BancoDAO
    public static ProcesoBancoDAO getProcesoBancoDAO() {
        return (ProcesoBancoDAO) SpringWebApplicationContext.
                getInstance().getBean("procesoBancoDAO");
    }

    //Proceso Envio DAO
    public static ProcesoEnvioDAO getProcesoEnvioDAO() {
        return (ProcesoEnvioDAO) SpringWebApplicationContext.
                getInstance().getBean("procesoEnvioDAO");
    }

    //TipoSolicitud DAO
    public static TipoSolicitudDAO getTipoSolicitudDAO() {
        return (TipoSolicitudDAO) SpringWebApplicationContext.
                getInstance().getBean("tipoSolicitudDAO");
    }

    public static ImpresoraDAO getImpresoraDAO() {
        return (ImpresoraDAO) SpringWebApplicationContext.
                getInstance().getBean("impresoraDAO");
    }

    //Proceso File DAO
    public static ProcesoFileDAO getProcesoFileDAO() {
        return (ProcesoFileDAO) SpringWebApplicationContext.
                getInstance().getBean("procesoFileDAO");
    }

    //Proceso Recuperacion
    public static ProcesoRecuperacionDAO getProcesoRecuperacionDAO() {
        return (ProcesoRecuperacionDAO) SpringWebApplicationContext.
                getInstance().getBean("procesoRecuperacionDAO");
    }

    //MovilidadDAO  
    public static MovilidadDAO getMovilidadDAO() {
        return (MovilidadDAO) SpringWebApplicationContext.
                getInstance().getBean("movilidadDAO");
    }

    //EstudianteMovilidadDAO
    public static EstudianteMovilidadDAO getEstudianteMovilidadDAO() {
        return (EstudianteMovilidadDAO) SpringWebApplicationContext.
                getInstance().getBean("estudianteMovilidadDAO");
    }

    //CuentaBancoDAO
    public static CuentaBancoDAO getCuentaBancoDAO() {
        return (CuentaBancoDAO) SpringWebApplicationContext.
                getInstance().getBean("cuentaBancoDAO");
    }

    //DocEgresoDAO
    public static DocEgresoDAO getDocEgresoDAO() {
        return (DocEgresoDAO) SpringWebApplicationContext.
                getInstance().getBean("docEgresoDAO");
    }

    //ChequeAnuladoDAO
    public static ChequeAnuladoDAO getChequeAnuladoDAO() {
        return (ChequeAnuladoDAO) SpringWebApplicationContext.
                getInstance().getBean("chequeAnuladoDAO");
    }

    //DetraccionDAO
    public static DetraccionDAO getDetraccionDAO() {
        return (DetraccionDAO) SpringWebApplicationContext.
                getInstance().getBean("detraccionDAO");
    }

    //CajaGeneralDAO
    public static CajaGenDAO getCajaGenDAO() {
        return (CajaGenDAO) SpringWebApplicationContext.
                getInstance().getBean("cajaGenDAO");
    }

    //ReporteRechazoDAO
    public static ReporteRechazoDAO getReporteRechazoDAO() {
        return (ReporteRechazoDAO) SpringWebApplicationContext.
                getInstance().getBean("reporteRechazoDAO");
    }

    //EsquelaDAO
    public static EsquelaDAO getEsquelaDAO() {
        return (EsquelaDAO) SpringWebApplicationContext.
                getInstance().getBean("esquelaDAO");
    }

    //DetEsquelaDAO
    public static DetEsquelaDAO getDetEsquelaDAO() {
        return (DetEsquelaDAO) SpringWebApplicationContext.
                getInstance().getBean("detEsquelaDAO");
    }

    //DetActividadDAO
    public static DetActividadDAO getDetActividadDAO() {
        return (DetActividadDAO) SpringWebApplicationContext.
                getInstance().getBean("detActividadDAO");
    }

    //Presupuesto Por UniOrg
    public static PresupuestoUniOrgDAO getPresupuestoUniOrgDAO() {
        return (PresupuestoUniOrgDAO) SpringWebApplicationContext.
                getInstance().getBean("presupuestoUniOrgDAO");
    }

    //DetActividadCR 
    public static ActividadCrDAO getActividadCrDAO() {
        return (ActividadCrDAO) SpringWebApplicationContext.
                getInstance().getBean("actividadCrDAO");
    }

    //Respuestas 
    public static RespuestasDAO getRespuestasDAO() {
        return (RespuestasDAO) SpringWebApplicationContext.
                getInstance().getBean("respuestasDAO");
    }

    //ProcesoFilesService
    public static ProcesoFilesDAO getProcesoFilesDAO() {
        return (ProcesoFilesDAO) SpringWebApplicationContext.
                getInstance().getBean("procesoFilesDAO");
    }

    //ProcesoFinal_DAO
    public static ProcesoFinalDAO getProcesoFinalDAO() {
        return (ProcesoFinalDAO) SpringWebApplicationContext.
                getInstance().getBean("procesoFinalDAO");
    }

    //Proceso_Erro_DAO
    public static ProcesoErrorDAO getProcesoErrorDAO() {
        return (ProcesoErrorDAO) SpringWebApplicationContext.
                getInstance().getBean("procesoErrorDAO");
    }

    //Cheque
    public static ChequeDAO getChequeDAO() {
        return (ChequeDAO) SpringWebApplicationContext.
                getInstance().getBean("chequeDAO");
    }

    //jefe uni org
    public static JefeUniOrgDAO getJefeUniOrgDAO() {
        return (JefeUniOrgDAO) SpringWebApplicationContext.
                getInstance().getBean("JefeUniOrgDAO");
    }

    //Cronograma de Pago
    public static CronogramaPagoDAO getCronogramaPagoDAO() {
        return (CronogramaPagoDAO) SpringWebApplicationContext.
                getInstance().getBean("cronogramaPagoDAO");
    }

    //Caja ChicaDAO
    public static CajaChicaDAO getCajaChicaDAO() {
        return (CajaChicaDAO) SpringWebApplicationContext.
                getInstance().getBean("cajaChicaDAO");
    }

    //Caja ChicaDAO
    public static AsientoDAO getAsientoDAO() {
        return (AsientoDAO) SpringWebApplicationContext.
                getInstance().getBean("asientoDAO");
    }

    //Caja ConceptoDAO
    public static ConceptoDAO getConceptoDAO() {
        return (ConceptoDAO) SpringWebApplicationContext.
                getInstance().getBean("conceptoDAO");
    }

    public static RecEnvDAO getRecEnvDAO() {
        return (RecEnvDAO) SpringWebApplicationContext.
                getInstance().getBean("recEnvDAO");
    }

    public static CajaChicaLiquidacionDAO getCajaChicaLiquidacionDAO() {
        return (CajaChicaLiquidacionDAO) SpringWebApplicationContext.
                getInstance().getBean("cajaChicaLiquidacionDAO");
    }

    public static EstudianteBloqueoDAO getEstudianteBloqueoDAO() {
        return (EstudianteBloqueoDAO) SpringWebApplicationContext.
                getInstance().getBean("estudianteBloqueoDAO");
    }

    //EstudianteNacimientoDAO
    public static EstudianteNacimientoDAO getEstudianteNacimientoDAO() {
        return (EstudianteNacimientoDAO) SpringWebApplicationContext.
                getInstance().getBean("estudianteNacimientoDAO");
    }

    //EstudianteVacunaDAO
    public static EstudianteVacunaDAO getEstudianteVacunaDAO() {
        return (EstudianteVacunaDAO) SpringWebApplicationContext.
                getInstance().getBean("estudianteVacunaDAO");
    }

    //Fallos de Proceso Banco
    public static ProcesoFalloDAO getProcesoFalloDAO() {
        return (ProcesoFalloDAO) SpringWebApplicationContext.
                getInstance().getBean("procesoFalloDAO");
    }

    //EVENTO
    public static EventoDAO getEventoDAO() {
        return (EventoDAO) SpringWebApplicationContext.
                getInstance().getBean("eventoDAO");
    }

    //PAGANTE
    public static PaganteDAO getPaganteDAO() {
        return (PaganteDAO) SpringWebApplicationContext.
                getInstance().getBean("paganteDAO");
    }

    //FICHA
    public static FichaDAO getFichaDAODAO() {
        return (FichaDAO) SpringWebApplicationContext.
                getInstance().getBean("fichaDAO");
    }

    //EVENTO PAGANTE
    public static EventoTipoPaganteDAO getEventoTipoPaganteDAO() {
        return (EventoTipoPaganteDAO) SpringWebApplicationContext.
                getInstance().getBean("eventoTipoPaganteDAO");
    }

    //TIPO PAGANTE
    public static TipoPaganteDAO getTipoPaganteDAO() {
        return (TipoPaganteDAO) SpringWebApplicationContext.
                getInstance().getBean("tipoPaganteDAO");
    }

    //PLANILLA
    public static PlanillaDAO getPlanillaDAO() {
        return (PlanillaDAO) SpringWebApplicationContext.
                getInstance().getBean("planillaDAO");
    }

    //BLOQUEO
    public static BloqueoDAO getBloqueoDAO() {
        return (BloqueoDAO) SpringWebApplicationContext.
                getInstance().getBean("bloqueoDAO");
    }

    //ProgramacionDsctoDAO 
    public static ProgramacionDsctoDAO getProgramacionDsctoDAO() {
        return (ProgramacionDsctoDAO) SpringWebApplicationContext.
                getInstance().getBean("programacionDsctoDAO");
    }

    //ProgramacionDsctoDAO  
    public static PagoBancoDAO getPagoBancoDAO() {
        return (PagoBancoDAO) SpringWebApplicationContext.
                getInstance().getBean("pagoBancoDAO");
    }

    //PROCESO FALLO
    public static FalloDAO getFalloDAO() {
        return (FalloDAO) SpringWebApplicationContext.
                getInstance().getBean("falloDAO");
    }

    //RecuperacionBco   
    public static RecuperacionBcoDAO getRecuperacionBcoDAO() {
        return (RecuperacionBcoDAO) SpringWebApplicationContext.
                getInstance().getBean("recuperacionBcoDAO");
    }

    //Unidad Negocio 
    public static UnidadNegocioDAO getUnidadNegocioDAO() {
        return (UnidadNegocioDAO) SpringWebApplicationContext.
                getInstance().getBean("unidadNegocioDAO");
    }

    //Presupuesto New

    public static PresupuestoNewDAO getPresupuestoNewDAO() {
        return (PresupuestoNewDAO) SpringWebApplicationContext.
                getInstance().getBean("presupuestoNewDAO");
    }

    //NivelTipoAcceso
    public static NivelTipoAccesoDAO getNivelTipoAccesoDAO() {
        return (NivelTipoAccesoDAO) SpringWebApplicationContext.
                getInstance().getBean("nivelTipoAccesoDAO");
    }

    //RecibosMora
    public static RecibosMoraDAO getRecibosMoraDAO() {
        return (RecibosMoraDAO) SpringWebApplicationContext.
                getInstance().getBean("recibosMoraDAO");
    }

    //EnvioBco 
    public static EnvioBcoDAO getEnvioBcoDAO() {
        return (EnvioBcoDAO) SpringWebApplicationContext.
                getInstance().getBean("envioBcoDAO");
    }

    //TipoEnvioUniNeg

    public static TipoEnvioUniNegDAO getTipoEnvioUniNegDAO() {
        return (TipoEnvioUniNegDAO) SpringWebApplicationContext.
                getInstance().getBean("tipoEnvioUniNegDAO");
    }
    //CajaCuotaIngreso

    public static CuotaIngresoDAO getCuotaIngresoDAO() {
        return (CuotaIngresoDAO) SpringWebApplicationContext.
                getInstance().getBean("cuotaIngresoDAO");
    }
    
    //Resultado x Dimension 
    public static ResDimensionDAO getResDimensionDAO() {
        return (ResDimensionDAO) SpringWebApplicationContext.
                getInstance().getBean("resDimensionDAO");
    }
    
    //Encuesta Evaluacion Desempeno
    public static EvaluacionDesempenoDAO getEvaluacionDesempenoDAO() {
        return (EvaluacionDesempenoDAO) SpringWebApplicationContext.
                getInstance().getBean("evaluacionDesempenoDAO");
    }    
    //Estudiante Retiro
    public static EstudianteRetiroDAO getEstudianteRetiroDAO() {
        return (EstudianteRetiroDAO) SpringWebApplicationContext.
                getInstance().getBean("estudianteRetiroDAO");
    }    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Services
    public static UsuarioService getUsuarioService() {
        return (UsuarioService) SpringWebApplicationContext.
                getInstance().getBean("usuarioService");
    }

    public static LoginService getLoginService() {
        return (LoginService) SpringWebApplicationContext.
                getInstance().getBean("loginService");
    }

    public static UsuarioBean getBeanLoginSession() {
        return (UsuarioBean) SpringWebApplicationContext.
                getInstance().getBean("loginBean");
    }

//     public static PerfilUnidadNegocioService getPerfilUsuarioService() {
//        return (PerfilUnidadNegocioService) SpringWebApplicationContext.
//                getInstance().getBean("perfilUsuarioService");
//    }
    public static LogService getLogService() {
        return (LogService) SpringWebApplicationContext.
                getInstance().getBean("logService");
    }

    //Inicio
    public static PerfilUnidadNegocioService getPerfilUnidadNegocioService() {
        return (PerfilUnidadNegocioService) SpringWebApplicationContext.
                getInstance().getBean("perfilUnidadNegocioService");
    }

    public static ModuloService getModuloService() {
        return (ModuloService) SpringWebApplicationContext.
                getInstance().getBean("moduloService");
    }

    public static PerfilService getPerfilService() {
        return (PerfilService) SpringWebApplicationContext.
                getInstance().getBean("perfilService");
    }

    public static TipoCodigoService getTipoCodigoService() {
        return (TipoCodigoService) SpringWebApplicationContext.
                getInstance().getBean("tipoCodigoService");
    }

    public static CodigoService getCodigoService() {
        return (CodigoService) SpringWebApplicationContext.
                getInstance().getBean("codigoService");
    }

    public static MotivoMovimientoService getMotivoMovimientoService() {
        return (MotivoMovimientoService) SpringWebApplicationContext.
                getInstance().getBean("motivoMovimientoService");
    }

    public static TipoConceptoService getTipoConceptoService() {
        return (TipoConceptoService) SpringWebApplicationContext.
                getInstance().getBean("tipoConceptoService");
    }

    public static ConceptoService getConceptoService() {
        return (ConceptoService) SpringWebApplicationContext.
                getInstance().getBean("conceptoService");
    }

    public static IndicadorService getIndicadorService() {
        return (IndicadorService) SpringWebApplicationContext.
                getInstance().getBean("indicadorService");
    }

    public static CajaService getCajaService() {
        return (CajaService) SpringWebApplicationContext.
                getInstance().getBean("cajaService");
    }

    public static UnidadNegocioService getUnidadNegocioService() {
        return (UnidadNegocioService) SpringWebApplicationContext.
                getInstance().getBean("unidadNegocioService");
    }

    public static PlanEstrategicoService getPlanEstrategicoService() {
        return (PlanEstrategicoService) SpringWebApplicationContext.
                getInstance().getBean("planEstrategicoService");
    }

    public static PlanOperativoService getPlanOperativoService() {
        return (PlanOperativoService) SpringWebApplicationContext.
                getInstance().getBean("planOperativoService");
    }

    public static PresupuestoService getPresupuestoService() {
        return (PresupuestoService) SpringWebApplicationContext.
                getInstance().getBean("presupuestoService");
    }

    public static CajeroService getCajeroService() {
        return (CajeroService) SpringWebApplicationContext.
                getInstance().getBean("cajeroService");
    }

    public static DistritoService getDistritoService() {
        return (DistritoService) SpringWebApplicationContext.
                getInstance().getBean("distritoService");
    }

    public static LegajoService getLegajoService() {
        return (LegajoService) SpringWebApplicationContext.
                getInstance().getBean("legajoService");
    }

    public static ProcesoService getProcesoService() {
        return (ProcesoService) SpringWebApplicationContext.
                getInstance().getBean("procesoService");
    }

    public static PersonaService getPersonaService() {
        return (PersonaService) SpringWebApplicationContext.
                getInstance().getBean("personaService");
    }

    public static PaisService getPaisService() {
        return (PaisService) SpringWebApplicationContext.
                getInstance().getBean("paisService");
    }

    public static EstudianteService getEstudianteService() {
        return (EstudianteService) SpringWebApplicationContext.
                getInstance().getBean("estudianteService");
    }

    public static CentroCostoService getCentroCostoService() {
        return (CentroCostoService) SpringWebApplicationContext.
                getInstance().getBean("centroCostoService");
    }

    public static TipoFormacionService getTipoFormacionService() {
        return (TipoFormacionService) SpringWebApplicationContext.
                getInstance().getBean("tipoFormacionService");
    }

    public static CentroResponsabilidadService getCentroResponsabilidadService() {
        return (CentroResponsabilidadService) SpringWebApplicationContext.
                getInstance().getBean("centroResponsabilidadService");
    }

    public static NivelAcademicoService getNivelAcademicoService() {
        return (NivelAcademicoService) SpringWebApplicationContext.
                getInstance().getBean("nivelAcademicoService");
    }

    public static GradoAcademicoService getGradoAcademicoService() {
        return (GradoAcademicoService) SpringWebApplicationContext.
                getInstance().getBean("gradoAcademicoService");
    }

    public static CatalogoService getCatalogoService() {
        return (CatalogoService) SpringWebApplicationContext.
                getInstance().getBean("catalogoService");
    }

    public static UnidadOrganicaService getUnidadOrganicaService() {
        return (UnidadOrganicaService) SpringWebApplicationContext.
                getInstance().getBean("unidadOrganicaService");
    }

    public static EntidadService getEntidadService() {
        return (EntidadService) SpringWebApplicationContext.
                getInstance().getBean("entidadService");
    }

    public static DocIngresoService getDocIngresoService() {
        return (DocIngresoService) SpringWebApplicationContext.
                getInstance().getBean("docIngresoService");
    }

    public static PersonalService getPersonalService() {
        return (PersonalService) SpringWebApplicationContext.
                getInstance().getBean("personalService");
    }

    public static CarreraService getCarreraService() {
        return (CarreraService) SpringWebApplicationContext.
                getInstance().getBean("carreraService");
    }

    public static ActividadService getActividadService() {
        return (ActividadService) SpringWebApplicationContext.
                getInstance().getBean("actividadService");
    }

    public static ObjetivoOperativoService getObjetivoOperativoService() {
        return (ObjetivoOperativoService) SpringWebApplicationContext.
                getInstance().getBean("objetivoOperativoService");
    }

    public static PersonalDependienteService getPersonalDependienteService() {
        return (PersonalDependienteService) SpringWebApplicationContext.
                getInstance().getBean("personalDependienteService");
    }

    public static SolicitudLogisticoService getSolicitudLogisticoService() {
        return (SolicitudLogisticoService) SpringWebApplicationContext.
                getInstance().getBean("solicitudLogisticoService");
    }

    public static ProgramacionService getProgramacionService() {
        return (ProgramacionService) SpringWebApplicationContext.
                getInstance().getBean("programacionService");
    }

    public static AmbienteService getAmbienteService() {
        return (AmbienteService) SpringWebApplicationContext.
                getInstance().getBean("ambienteService");
    }

    public static PlanContableService getPlanContableService() {
        return (PlanContableService) SpringWebApplicationContext.
                getInstance().getBean("planContableService");
    }

    public static CarreraAreaService getCarreraAreaService() {
        return (CarreraAreaService) SpringWebApplicationContext.
                getInstance().getBean("carreraAreaService");
    }

    public static CarreraSubAreaService getCarreraSubAreaService() {
        return (CarreraSubAreaService) SpringWebApplicationContext.
                getInstance().getBean("carreraSubAreaService");
    }

    public static SolicitudLogisticoDetalleService getSolicitudLogisticoDetalleService() {
        return (SolicitudLogisticoDetalleService) SpringWebApplicationContext.
                getInstance().getBean("solicitudLogisticoDetalleService");
    }

    public static AdmisionService getAdmisionService() {
        return (AdmisionService) SpringWebApplicationContext.
                getInstance().getBean("admisionService");
    }

    public static MatriculaService getMatriculaService() {
        return (MatriculaService) SpringWebApplicationContext.
                getInstance().getBean("matriculaService");
    }

    public static PersonalCargoService getPersonalCargoService() {
        return (PersonalCargoService) SpringWebApplicationContext.
                getInstance().getBean("personalCargoService");
    }

    public static DocIngresoSerieService getDocIngresoSerieService() {
        return (DocIngresoSerieService) SpringWebApplicationContext.
                getInstance().getBean("docIngresoSerieService");
    }

    public static FamiliarService getFamiliarService() {
        return (FamiliarService) SpringWebApplicationContext.
                getInstance().getBean("familiarService");
    }

    public static UniNegUniOrgService getUniNegUniOrgService() {
        return (UniNegUniOrgService) SpringWebApplicationContext.
                getInstance().getBean("uniNegUniOrgService");
    }

    public static CargoUniNegService getCargoUniNegService() {
        return (CargoUniNegService) SpringWebApplicationContext.
                getInstance().getBean("cargoUniNegService");
    }

    public static EnfermedadService getEnfermedadService() {
        return (EnfermedadService) SpringWebApplicationContext.
                getInstance().getBean("enfermedadService");
    }

    public static PersonalEnfermedadService getPersonalEnfermedadService() {
        return (PersonalEnfermedadService) SpringWebApplicationContext.
                getInstance().getBean("personalEnfermedadService");
    }

    public static PersonalExperienciaService getPersonalExperienciaService() {
        return (PersonalExperienciaService) SpringWebApplicationContext.
                getInstance().getBean("personalExperienciaService");
    }

    public static PersonalContratoService getPersonalContratoService() {
        return (PersonalContratoService) SpringWebApplicationContext.
                getInstance().getBean("personalContratoService");
    }

    public static EstudianteEnfermedadService getEstudianteEnfermedadService() {
        return (EstudianteEnfermedadService) SpringWebApplicationContext.
                getInstance().getBean("estudianteEnfermedadService");
    }

    public static EstudianteDocumentoService getEstudianteDocumentoService() {
        return (EstudianteDocumentoService) SpringWebApplicationContext.
                getInstance().getBean("estudianteDocumentoService");
    }

    public static EstudianteMedicamentoService getEstudianteMedicamentoService() {
        return (EstudianteMedicamentoService) SpringWebApplicationContext.
                getInstance().getBean("estudianteMedicamentoService");
    }

    public static EstudianteAlergiaService getEstudianteAlergiaService() {
        return (EstudianteAlergiaService) SpringWebApplicationContext.
                getInstance().getBean("estudianteAlergiaService");
    }

    public static EstudianteTraumaService getEstudianteTraumaService() {
        return (EstudianteTraumaService) SpringWebApplicationContext.
                getInstance().getBean("estudianteTraumaService");
    }

    public static OrdenCompraService getOrdenCompraService() {
        return (OrdenCompraService) SpringWebApplicationContext.
                getInstance().getBean("ordenCompraService");
    }

    public static PersonalProcesoJudicialService getPersonalProcesoJudicialService() {
        return (PersonalProcesoJudicialService) SpringWebApplicationContext.
                getInstance().getBean("personalProcesoJudicialService");
    }

    public static TipoCambioService getTipoCambioService() {
        return (TipoCambioService) SpringWebApplicationContext.
                getInstance().getBean("tipoCambioService");
    }

    public static PersonalAlergiaService getPersonalAlergiaService() {
        return (PersonalAlergiaService) SpringWebApplicationContext.
                getInstance().getBean("personalAlergiaService");
    }

    public static CuentasPorCobrarService getCuentasPorCobrarService() {
        return (CuentasPorCobrarService) SpringWebApplicationContext.
                getInstance().getBean("cuentasPorCobrarService");
    }

    public static PersonalFormacionService getPersonalFormacionService() {
        return (PersonalFormacionService) SpringWebApplicationContext.
                getInstance().getBean("personalFormacionService");
    }

    public static PersonalIdiomaService getPersonalIdiomaService() {
        return (PersonalIdiomaService) SpringWebApplicationContext.
                getInstance().getBean("personalIdiomaService");
    }

    public static ConceptoUniNegService getConceptoUniNegService() {
        return (ConceptoUniNegService) SpringWebApplicationContext.
                getInstance().getBean("conceptoUniNegService");
    }

    public static OrdenCompraDetalleService getOrdenCompraDetalleService() {
        return (OrdenCompraDetalleService) SpringWebApplicationContext.
                getInstance().getBean("ordenCompraDetalleService");
    }

    public static CotizacionService getCotizacionService() {
        return (CotizacionService) SpringWebApplicationContext.
                getInstance().getBean("cotizacionService");
    }

    public static DetCotizacionService getDetCotizacionService() {
        return (DetCotizacionService) SpringWebApplicationContext.
                getInstance().getBean("detCotizacionService");
    }

    public static BecaService getBecaService() {
        return (BecaService) SpringWebApplicationContext.
                getInstance().getBean("becaService");
    }

    public static CursoTallerService getCursoTallerService() {
        return (CursoTallerService) SpringWebApplicationContext.
                getInstance().getBean("cursoTallerService");
    }

    public static DiccionarioService getDiccionarioService() {
        return (DiccionarioService) SpringWebApplicationContext.
                getInstance().getBean("diccionarioService");
    }

    public static PersonalOtrosEstudiosService getPersonalOtrosEstudiosService() {
        return (PersonalOtrosEstudiosService) SpringWebApplicationContext.
                getInstance().getBean("personalOtrosEstudiosService");
    }

    public static CatalogoFamiliaService getCatalogoFamiliaService() {
        return (CatalogoFamiliaService) SpringWebApplicationContext.
                getInstance().getBean("catalogoFamiliaService");
    }

    public static CatalogoCategoriaService getCatalogoCategoriaService() {
        return (CatalogoCategoriaService) SpringWebApplicationContext.
                getInstance().getBean("catalogoCategoriaService");
    }

    public static EstudianteSeguroService getEstudianteSeguroService() {
        return (EstudianteSeguroService) SpringWebApplicationContext.
                getInstance().getBean("estudianteSeguroService");
    }

    public static DocumentoService getDocumentoService() {
        return (DocumentoService) SpringWebApplicationContext.
                getInstance().getBean("documentoService");
    }

    public static CargoService getCargoService() {
        return (CargoService) SpringWebApplicationContext.
                getInstance().getBean("cargoService");
    }

    public static DocumentoCargoService getDocumentoCargoService() {
        return (DocumentoCargoService) SpringWebApplicationContext.
                getInstance().getBean("documentoCargoService");
    }

    public static PersonalDocumentoService getPersonalDocumentoService() {
        return (PersonalDocumentoService) SpringWebApplicationContext.
                getInstance().getBean("personalDocumentoService");
    }

    public static InventarioActivoService getInventarioActivoService() {
        return (InventarioActivoService) SpringWebApplicationContext.
                getInstance().getBean("inventarioActivoService");
    }

    public static MovimientoActivoService getMovimientoActivoService() {
        return (MovimientoActivoService) SpringWebApplicationContext.
                getInstance().getBean("movimientoActivoService");
    }

    //MovilidadService
    public static MovilidadService getMovilidadService() {
        return (MovilidadService) SpringWebApplicationContext.
                getInstance().getBean("movilidadService");
    }

    //EstudianteMovilidadService
    public static EstudianteMovilidadService getEstudianteMovilidadService() {
        return (EstudianteMovilidadService) SpringWebApplicationContext.
                getInstance().getBean("estudianteMovilidadService");
    }

    public static FamiliaService getFamiliaService() {
        return (FamiliaService) SpringWebApplicationContext.
                getInstance().getBean("familiaService");
    }

    //InventarioAlmacen
    public static InventarioAlmacenService getInventarioAlmacenService() {
        return (InventarioAlmacenService) SpringWebApplicationContext.
                getInstance().getBean("inventarioAlmacenService");
    }

    //Linea Estrategica
    public static LineaEstrategicaService getLineaEstrategicaService() {
        return (LineaEstrategicaService) SpringWebApplicationContext.
                getInstance().getBean("lineaEstrategicaService");
    }

    //Objetivo Estrategico
    public static ObjetivoEstrategicoService getObjetivoEstrategicoService() {
        return (ObjetivoEstrategicoService) SpringWebApplicationContext.
                getInstance().getBean("objetivoEstrategicoService");
    }

    //Proceso Banco
    public static ProcesoBancoService getProcesoBancoService() {
        return (ProcesoBancoService) SpringWebApplicationContext.
                getInstance().getBean("procesoBancoService");
    }

    //Objetivo Estrategico DEt
    public static ObjetivoEstrategicoDetService getObjetivoEstrategicoDetService() {
        return (ObjetivoEstrategicoDetService) SpringWebApplicationContext.
                getInstance().getBean("objetivoEstrategicoDetService");
    }

    //Periodo 
    public static PeriodoService getPeriodoService() {
        return (PeriodoService) SpringWebApplicationContext.
                getInstance().getBean("periodoService");
    }

    //solicitud caja chica 
    public static SolicitudCajaCHService getSolicitudCajaCHService() {
        return (SolicitudCajaCHService) SpringWebApplicationContext.
                getInstance().getBean("solicitudCajaCHService");
    }

    //Proceso Envio
    public static ProcesoEnvioService getProcesoEnvioService() {
        return (ProcesoEnvioService) SpringWebApplicationContext.
                getInstance().getBean("procesoEnvioService");
    }

    public static ImpresoraService getImpresoraService() {
        return (ImpresoraService) SpringWebApplicationContext.
                getInstance().getBean("impresoraService");
    }

    //TipoSolicitud
    public static TipoSolicitudService getTipoSolicitudService() {
        return (TipoSolicitudService) SpringWebApplicationContext.
                getInstance().getBean("tipoSolicitudService");
    }

    //Proceso File
    public static ProcesoFileService getProcesoFileService() {
        return (ProcesoFileService) SpringWebApplicationContext.
                getInstance().getBean("procesoFileService");
    }

    public static MensajeService getMensajeService() {
        return (MensajeService) SpringWebApplicationContext.
                getInstance().getBean("mensajeService");
    }

    public static CajaChicaService getCajaChicaService() {
        return (CajaChicaService) SpringWebApplicationContext.
                getInstance().getBean("cajaChicaService");
    }

    //Proceso Recuperacion
    public static ProcesoRecuperacionService getProcesoRecuperacionService() {
        return (ProcesoRecuperacionService) SpringWebApplicationContext.
                getInstance().getBean("procesoRecuperacionService");
    }

    public static CajaChicaMovService getCajaChicaMovService() {
        return (CajaChicaMovService) SpringWebApplicationContext.
                getInstance().getBean("cajaChicaMovService");
    }

    //RegistroCompra
    public static RegistroCompraService getRegistroCompraService() {
        return (RegistroCompraService) SpringWebApplicationContext.
                getInstance().getBean("registroCompraService");
    }

    //DetRegistroCompra
    public static DetRegistroCompraService getDetRegistroCompraService() {
        return (DetRegistroCompraService) SpringWebApplicationContext.
                getInstance().getBean("detRegistroCompraService");
    }

    //CuentaBanco
    public static CuentaBancoService getCuentaBancoService() {
        return (CuentaBancoService) SpringWebApplicationContext.
                getInstance().getBean("cuentaBancoService");
    }

    //docEgreso
    public static DocEgresoService getDocEgresoService() {
        return (DocEgresoService) SpringWebApplicationContext.
                getInstance().getBean("docEgresoService");
    }

    //chequeAnulado
    public static ChequeAnuladoService getChequeAnuladoService() {
        return (ChequeAnuladoService) SpringWebApplicationContext.
                getInstance().getBean("chequeAnuladoService");
    }

    //detraccion
    public static DetraccionService getDetraccionService() {
        return (DetraccionService) SpringWebApplicationContext.
                getInstance().getBean("detraccionService");
    }

    //cajaGeneral
    public static CajaGenService getCajaGenService() {
        return (CajaGenService) SpringWebApplicationContext.
                getInstance().getBean("cajaGenService");
    }

    //ReporteRechazo
    public static ReporteRechazoService getReporteRechazoService() {
        return (ReporteRechazoService) SpringWebApplicationContext.
                getInstance().getBean("reporteRechazoService");
    }

    //Esquela
    public static EsquelaService getEsquelaService() {
        return (EsquelaService) SpringWebApplicationContext.
                getInstance().getBean("esquelaService");
    }

    //DetEsquela
    public static DetEsquelaService getDetEsquelaService() {
        return (DetEsquelaService) SpringWebApplicationContext.
                getInstance().getBean("detEsquelaService");
    }

    //DetActividad
    public static DetActividadService getDetActividadService() {
        return (DetActividadService) SpringWebApplicationContext.
                getInstance().getBean("detActividadService");
    }

    //Presupuesto Por UniOrg
    public static PresupuestoUniOrgService getPresupuestoUniOrgService() {
        return (PresupuestoUniOrgService) SpringWebApplicationContext.
                getInstance().getBean("presupuestoUniOrgService");
    }

    //ActividadCR 
    public static ActividadCrService getActividadCrService() {
        return (ActividadCrService) SpringWebApplicationContext.
                getInstance().getBean("actividadCrService");
    }

    //Respuestas
    public static RespuestasService getRespuestasService() {
        return (RespuestasService) SpringWebApplicationContext.
                getInstance().getBean("respuestasService");
    }

    //CajaChicaLiquidacion
    public static CajaChicaLiquidacionService getCajaChicaLiquidacionService() {
        return (CajaChicaLiquidacionService) SpringWebApplicationContext.
                getInstance().getBean("cajaChicaLiquidacionService");
    }

    //Proceso_Files_Services
    public static ProcesoFilesService getProcesoFilesService() {
        return (ProcesoFilesService) SpringWebApplicationContext.
                getInstance().getBean("procesoFilesService");
    }

    //Proceso_Final_Service
    public static ProcesoFinalService getProcesoFinalService() {
        return (ProcesoFinalService) SpringWebApplicationContext.
                getInstance().getBean("procesoFinalService");
    }

    //Proceso_Error
    public static ProcesoErrorService getProcesoErrorService() {
        return (ProcesoErrorService) SpringWebApplicationContext.
                getInstance().getBean("procesoErrorService");
    }

    //cheque
    public static ChequeService getChequeService() {
        return (ChequeService) SpringWebApplicationContext.
                getInstance().getBean("chequeService");
    }

    //Jefe Uni Org
    public static JefeUniOrgService getJefeUniOrgService() {
        return (JefeUniOrgService) SpringWebApplicationContext.
                getInstance().getBean("jefeUniOrgService");
    }

    //Cronograma de Pago
    public static CronogramaPagoService getCronogramaPagoService() {
        return (CronogramaPagoService) SpringWebApplicationContext.
                getInstance().getBean("cronogramaPagoService");
    }

    //Asiento Service
    public static AsientoService getAsientoService() {
        return (AsientoService) SpringWebApplicationContext.
                getInstance().getBean("asientoService");
    }

    //Recepcion Envio ExtraSistema
    public static RecEnvService getRecEnvService() {
        return (RecEnvService) SpringWebApplicationContext.
                getInstance().getBean("recEnvService");
    }

    //Estudiante Bloqueo
    public static EstudianteBloqueoService getEstudianteBloqueoService() {
        return (EstudianteBloqueoService) SpringWebApplicationContext.
                getInstance().getBean("estudianteBloqueoService");
    }

    //EstudianteNacimientoService
    public static EstudianteNacimientoService getEstudianteNacimientoService() {
        return (EstudianteNacimientoService) SpringWebApplicationContext.
                getInstance().getBean("estudianteNacimientoService");
    }

    //EstudianteVacunaService
    public static EstudianteVacunaService getEstudianteVacunaService() {
        return (EstudianteVacunaService) SpringWebApplicationContext.
                getInstance().getBean("estudianteVacunaService");
    }

    //Fallos de Proceso Banco
    public static ProcesoFalloService getProcesoFalloService() {
        return (ProcesoFalloService) SpringWebApplicationContext.
                getInstance().getBean("procesoFalloService");
    }

    //EVENTO
    public static EventoService getEventoService() {
        return (EventoService) SpringWebApplicationContext.
                getInstance().getBean("eventoService");
    }

    //PAGANTE
    public static PaganteService getPaganteService() {
        return (PaganteService) SpringWebApplicationContext.
                getInstance().getBean("paganteService");
    }

    //FICHA
    public static FichaService getFichaService() {
        return (FichaService) SpringWebApplicationContext.
                getInstance().getBean("fichaService");
    }

    //EVENTO PAGANTE
    public static EventoTipoPaganteService getEventoTipoPaganteService() {
        return (EventoTipoPaganteService) SpringWebApplicationContext.
                getInstance().getBean("eventoTipoPaganteService");
    }

    //TIPO PAGANTE
    public static TipoPaganteService getTipoPaganteService() {
        return (TipoPaganteService) SpringWebApplicationContext.
                getInstance().getBean("tipoPaganteService");
    }

    //PLANILLA
    public static PlanillaService getPlanillaService() {
        return (PlanillaService) SpringWebApplicationContext.
                getInstance().getBean("planillaService");
    }

    //BLOQUEO
    public static BloqueoService getBloqueoService() {
        return (BloqueoService) SpringWebApplicationContext.
                getInstance().getBean("bloqueoService");
    }

    //ProgramacionDscto
    public static ProgramacionDsctoService getProgramacionDsctoService() {
        return (ProgramacionDsctoService) SpringWebApplicationContext.
                getInstance().getBean("programacionDsctoService");
    }

    //ProgramacionDscto 
    public static PagoBancoService getPagoBancoService() {
        return (PagoBancoService) SpringWebApplicationContext.
                getInstance().getBean("pagoBancoService");
    }

    //PROCESO FALLO
    public static FalloService getFalloService() {
        return (FalloService) SpringWebApplicationContext.
                getInstance().getBean("falloService");
    }

    //Recuperacion BcoService

    public static RecuperacionBcoService getRecuperacionBcoService() {
        return (RecuperacionBcoService) SpringWebApplicationContext.
                getInstance().getBean("recuperacionBcoService");
    }

    //Presupuesto New

    public static PresupuestoNewService getPresupuestoNewService() {
        return (PresupuestoNewService) SpringWebApplicationContext.
                getInstance().getBean("presupuestoNewService");
    }

    //NivelTipoAcceso 

    public static NivelTipoAccesoService getNivelTipoAccesoService() {
        return (NivelTipoAccesoService) SpringWebApplicationContext.
                getInstance().getBean("nivelTipoAccesoService");
    }

    //RecibosMora 
    public static RecibosMoraService getRecibosMoraService() {
        return (RecibosMoraService) SpringWebApplicationContext.
                getInstance().getBean("recibosMoraService");
    }

    //Envio Banco 

    public static EnvioBcoService getEnvioBcoService() {
        return (EnvioBcoService) SpringWebApplicationContext.
                getInstance().getBean("envioBcoService");
    }

    //TipoEnvioUniNeg

    public static TipoEnvioUniNegService getTipoEnvioUniNegService() {
        return (TipoEnvioUniNegService) SpringWebApplicationContext.
                getInstance().getBean("tipoEnvioUniNegService");
    }
    //CuotaIngresoDAO

    public static CuotaIngresoService getCuotaIngresoService() {
        return (CuotaIngresoService) SpringWebApplicationContext.
                getInstance().getBean("cuotaIngresoService");
    }

    
    //Resultado x Dimension 
    public static ResDimensionService getResDimensionService() {
        return (ResDimensionService) SpringWebApplicationContext.
                getInstance().getBean("resDimensionService");
    }
    //EvaluacionDesempeno
    public static EvaluacionDesempenoService getEvaluacionDesempenoService() {
        return (EvaluacionDesempenoService) SpringWebApplicationContext.
                getInstance().getBean("evaluacionDesempenoService");
    }
    //Estudiante Retiro
    public static EstudianteRetiroService getEstudianteRetiroService() {
        return (EstudianteRetiroService) SpringWebApplicationContext.
                getInstance().getBean("estudianteRetiroService");
    }
     
    
}
