/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma;

import java.util.ArrayList;
import java.util.List;
import pe.marista.sigma.bean.CentroResponsabilidadBean;

/**
 *
 * @author Administrador
 */
public class MaristaConstantes {

    public MaristaConstantes() {
    }
    public static final int TABLA_NUMERO_REGISTROS_MOSTRAR = 5;
    public static final int TABLA_NUMERO_REGISTROS_MOSTRAR_MIN = 2;
    public static final int TABLA_NUMERO_REGISTROS_MOSTRAR_POR_FILTRO_MIN = 10;
    public static final int TABLA_NUMERO_REGISTROS_MOSTRAR_POR_FILTRO = 20;
    public static final int TABLA_NUMERO_REGISTROS_MOSTRAR_LISTA = 30;
    public static final int TABLA_NUMERO_REGISTROS_REPORTES = 100;
    public static final String TABLA_ANCHO = "800px";
    public static final String TABLA_ANCHO_BIG = "1000px";
    public static final String TABLA_ANCHO_GRANDE = "1200px";
    public static final String TABLA_ANCHO_EXTRA_GRANDE = "1300px";
    public static final String TABLA_ANCHO_SUPER_GRANDE = "1400px";
    public static final String TABLA_ANCHO_SHORT = "500px";
    public static final String TABLA_ANCHO_POPUP = "700px";

    public static final String ARCHIVO_MENSAJES_NOMBRE = "mensajes";
//    public static Map<String, String> sexosMapFiltro;

//    static {
//        Map<String, String> aMap = new LinkedHashMap();
//        aMap.put(MensajesBackEnd.getValueOfKey("estadoTodos", null), MensajesBackEnd.getValueOfKey("comboSeleccionarTodosValue", null));
//        aMap.put(MensajesBackEnd.getValueOfKey("estadoMasculino", null), "M");
//        aMap.put(MensajesBackEnd.getValueOfKey("estadoFemenino", null), "F");
//        sexosMapFiltro = Collections.unmodifiableMap(aMap);
//    }
//    public static Map<String, String> sexosMap;
//    static {
//        Map<String, String> aMap = new LinkedHashMap();
//        aMap.put(MensajesBackEnd.getValueOfKey("estadoMasculino", null), "M");
//        aMap.put(MensajesBackEnd.getValueOfKey("estadoFemenino", null), "F");
//        sexosMap = Collections.unmodifiableMap(aMap);
//    }
    public static final int ESTADO_ACTIVO = new Integer(1);
    public static final int ESTADO_INACTIVO = new Integer(0);

    public static final String ESTADO_ENTREGADO = "Entregado";
    public static final String ESTADO_PENDIENTE = "Pendiente";
    public static final String ESTADO_ACTIVO_DES = "Activo";
    public static final String ESTADO_INACTIVO_DES = "Inactivo";
    public static final String SIN_ESTADO = "Sin Estado";
    public static final String ESTADO_VIGENTE_DES = "Vigente";
    public static final String ESTADO_PASS_DES = "Pass";

    public static final String CANAL_PAGO_BAN = "Banco";
    public static final String CANAL_PAGO_CA = "Caja";
    public static final String PAGO_MON_SOL = "Soles";
    public static final String PAGO_MON_DOL = "Dolares";

    public static final String SI = "Sí";
    public static final String NO = "No";
    public static final String GUION = "---";

    //MESES
    public static final String ENERO = "ENERO";
    public static final String FEBRERO = "MATRÍCULA";
    public static final String MES_FEBRERO = "FEBRERO";
    public static final String MARZO = "MARZO";
    public static final String ABRIL = "ABRIL";
    public static final String MAYO = "MAYO";
    public static final String JUNIO = "JUNIO";
    public static final String JULIO = "JULIO";
    public static final String AGOSTO = "AGOSTO";
    public static final String SETIEMBRE = "SEPTIEMBRE";
    public static final String OCTUBRE = "OCTUBRE";
    public static final String NOVIEMBRE = "NOVIEMBRE";
    public static final String DICIEMBRE = "DICIEMBRE";
    public static final String DICIEMBRE1 = "DICIEMBRE 1";
    public static final String DICIEMBRE2 = "DICIEMBRE 2";
    public static final String JULIO_GRATI = "JULIO - GRATI";
    public static final String DICIEMBRE_GRATI= "DICIEMBRE - GRATI";
    public static final String CTS_MAYO= "CTS MAYO";
    public static final String CTS_NOVIEMBRE= "CTS NOVIEMBRE";

    //pais
    public static final Integer COD_PERU = 1;
    public static final Integer DEP_LIMA = 16;
    public static final Integer PROV_LIMA = 135;

    //ITEMS DEL MENU DEL SISTEMA
    //SEGURIDAD
    public static final String SEG_MANT_PERFILES = "Mantenimiento de Perfiles";
    public static final String SEG_MANT_USUARIOS = "Mantenimiento de Usuarios";
    public static final String SEG_CAMBIO_CLAVE = "Cambio Clave de Usuario";
    public static final String SEG_ACTUALIZA_USUARIO = "Actualizar Datos de Usuario";
    public static final String SEG_LOG = "Log de cambios";

    //Tipos de Codigo
    public static final String TIP_COD_PROC = "TipoProceso";
    public static final String TIP_DOC_PER = "TipoDocPer";
    public static final String TIP_objeto_PDB = "TipoObjetoSubidaPDF";
    public static final String TIP_GRA_ACA = "TipoGradoAcademico";
    public static final String MASCULINO = "Masculino";
    public static final String FEMENINO = "Femenino";
    public static final String TIP_STATUS_EST = "TipoStatusEst";
    public static final String TIP_INGRESO_ESTUDIANTE = "TipoIngresoEst";
    public static final String TIP_IDIOMA = "TipoIdioma";
    public static final String TIP_NIVEL_IDIOMA = "TipoNivelIdioma";
    public static final String TIP_VIA_DOMI = "TipoVia";
    public static final String TIP_Seguro = "TipoSeguro";
    public static final String TIP_RUBRO = "TipoRubro";
    public static final String TIP_ENTIDAD = "TipoEntidad";
    public static final String TIP_MOTIVO_CAMB = "TipoMotivoCambio";
    public static final String TIP_CAMB_COLE = "TipoCambioColegio";
    public static final String TIP_EST_CIV = "TipoEstadoCivil";
    public static final String TIP_VIVE_CON = "TipoViveCon";
    public static final String TIP_VIVIENDA = "TipoVivienda";
    public static final String TIP_VIA = "TipoVia";
    public static final String TIP_FORMACIONCARISMA = "TipoFormacion";
    public static final String TIP_ZONA = "TipoZona";
    public static final String TIP_CASA = "TipoCasa";
    public static final String TIP_STATUS_CASA = "TipoStatusCasa";
    public static final String TIP_ASEGURADO = "TipoAsegurado";
    public static final String TIP_SEGURO = "TipoSeguro";
    public static final String TIP_ResPago = "TipoResPago";
    public static final String TIP_SEGURO_PER = "TipoSeguroPersonal";
    public static final String TIP_OCUPACION = "TipoOcupacion";
    public static final String TIP_NivelColegio = "TipoNivelesColegio";
    public static final String TIP_NivelCong= "TipoNivelesCong";
    public static final String TIP_PARENTESCO = "TipoParentesco";
    public static final String TIP_RUBRO_EPS = "Salud y Asist.Social";
    public static final String TIP_RUBRO_AFP = "Finanzas y Seguros";
    public static final String TIP_RUBRO_ENSENANZA = "Enseñanza";
    public static final String TIP_RUBRO_RELIGIOSA = "Religiosa";
    public static final String TIP_STATUS_POST = "TipoStatusPostulante";
    public static final String TIP_STATUS_ENF = "TipoStatusEnfermedad";
    public static final String TIP_PER = "TipoPersona";
    public static final String TIP_MATRICULA = "TipoMatricula";
    public static final String TIP_PROFESION = "TipoProfesion";
    public static final String TIP_CARGO = "TipoCargo";
    public static final String TIP_CONTRATO = "TipoContrato";
    public static final String TIP_PLANILLA_COLEGIO = "TipoPlanillaColegio";
    public static final String TIP_Grupo_Ocupacional = "TipoGrupoOcupacional";
    public static final String TIP_ALERGIA = "TipoAlergia";
    public static final String TIP_EDAD = "TipoEdad";
    public static final String TIP_VACUNA = "TipoVacunas";
    public static final String TIP_PROJUD = "TipoProcesoJudicial";
    public static final String TIP_RETENCION = "TipoRetencion";
    public static final String TIP_VALOR = "TipoValor";
    public static final String TIP_MODOPAGO = "TipoModoPago";
    public static final String TIP_ENFERMEDAD = "TipoEnfermedad";
    public static final String TIP_AMBIENTE = "TipoAmbiente";
    public static final String TIP_MOTIVO_BECA = "TipoMotivoBeca";
    public static final String TIP_BECA = "TipoBeca";
    public static final String TIP_DOCUMENTO = "TipoDoc";
    public static final String TIP_CAT_DOC = "TipoCatDoc";
    public static final String TIP_COPIA = "TipoCopia";
    public static final String TIP_CAT_CARGO = "TipoCategoriaCargo";
    public static final String TIP_INDICADOR = "TipoIndicador";
    public static final String TIP_GRUPOCR = "TipoGrupoCR";
    public static final String TIP_USO_INDICADOR = "TipoUsoIndicador";
    public static final String RUTA_DOCUMENTOS = "C:\\SigmaDocumentos\\";
    public static final String RUTA_DOCU_EST = "archivoEstudiante\\fotosEstudiante\\";
    public static final String RUTA_DOCU_FAM = "archivoEstudiante\\fotosFamiliar\\";
    public static final String TIP_RES_PAGO = "TipoResPago";
    public static final String TIP_MON = "TipoMoneda";
    public static final String TIP_PRIORIDAD = "TipoPrioridad";
    public static final String TIP_STA_CCH = "TipoStatusSolCajaCh";
    public static final String TIP_STA_REGISTRO = "TipoStatusRegC";
    public static final String TIP_STA_REQ = "TipoStatusReq";
    public static final String TIP_STA_MSJE = "TipoStatusMsje";
    public static final String TIP_MOVI = "TipoMovilidad";
    public static final String TIP_STATUS_EST_BLO = "TipoStatusEst";
    public static final String TIP_AMB_SOL = "TipoAmbitoSol";
    public static final String TIP_AUTORRIZA = "TipoAutoriza";
    public static final String TIP_CUENTA = "TipoCuenta";
    public static final String TIP_CUENTA_BCO = "TipoCuentaBanco";
    public static final String TIP_MOD_PAGO = "TipoModoPago";
    public static final String TIP_LUG_PAGO = "TipoLugarPago";
    public static final String TIP_ACCESO = "TipoAcceso";
    public static final String TIP_STATUS_CTA_CTE = "TipoStatusCtaCte";
    public static final String TIP_TAREA = "TipoTarea";
    public static final String TIP_ESQUELA = "TipoEsquela";
    public static final String TIP_GRUPO_CTA = "TipoGrupoCta";
    public static final String TIP_CTA = "TipoCuenta";
    public static final String TIP_STA_FACT_COM = "TipoStatusFactura";
    public static final String Tip_File = "TipoFile";
    public static final String Tip_Archivo = "TipoArchivo";
    public static final String Tip_File_Com = "TipoFile";
    public static final String TIP_DSCTO = "TipoDscto";
    public static final String TIP_Default = "TipoDefecto";
    public static final String TIP_STATUS_DOCING = "TipoStatusDocIng";
    public static final String TIP_STATUS_CUENTA = "TipoStatusCtaCte";
    public static final String TIP_DISTRIB = "TipoDistribucionCR";
    public static final String TIP_SITUACION_PER = "TipoSituacionPer"; // TIPOSITUACION DEL PERSONAL
    public static final String TIP_STATUS_FICHA = "TipoStatusFicha";
    public static final String TIP_STATUS_CAJA_GEN = "tipoCajaGen";// TIPO DE CAJA GENERAL
    public static final String TIP_STATUS_BLOQUEO_EST = "TipoBloqueoEst";// TIPO DE BLOQUEO ESTUDIANTE
    public static final String TIP_STATUS_PAGO_BCO = "TipoStatusPagoBanco";
    public static final String TIP_PERIODO = "TipoPeriodo";
    public static final String TIP_PARENTESCO_HAB = "tipoParentestoHab";

    //Codigos
    public static final String COD_ADMITIDO = "Admitido";//Representacion del Codigo de Admitido
    public static final String COD_RECIBIDO = "Recibido";
    public static final String COD_NO_ADMITIDO = "No Admitido";//Representacion del Codigo de No Admitido
    public static final String COD_EVALUACION = "Evaluación";//Representacion del Codigo de Evaluaciï¿½n 
    public static final String COD_DNI = "DNI";
    public static final String COD_EST_ACTIVO = "Activo";
    public static final String COD_EST_NUEVO = "Nuevo";
    public static final String COD_INSCRITO = "Inscrito";
    public static final String COD_Soles_Cod = "Soles";
    public static final String COD_Dolares = "Dolares";
    public static final String COD_ADMISION = "Admisión";
    public static final String COD_MATRICULA = "Matrícula";
    public static final String COD_CURSO = "Inscripción Cursos Verano";
    public static final String INGRESO = "Ingreso";
    public static final String EGRESO = "Egreso";
    public static final String EGRESADO = "Egresado";
    public static final String ACTIVO = "Activo";
    public static final String COD_POSTULANTE = "Postulante";
    public static final String COD_TESO_ING = "Caja Chica";
    public static final String COD_AUTORI = "Autorizado";
    public static final String COD_VACACIONES = "Vacaciones";

    //Filtros GradoAcademico para Matricula
    public static final String SIN_CARRERA = "-----------------";
    public static final String SIN_ENTIDAD = "-----------------";
    public static final String SIN_FECHA_TERMINO = "En Curso";
    public static final String SIN_FECHA_TER_OTROEST = "-----";
    public static final String NIV_ACA_INI = "Inicial";
    public static final String NIV_ACA_PRI = "Primaria";
    public static final String NIV_ACA_SEC = "Secundaria";
    public static final String NIV_INI_PRI_SEC = "Ini-Pri-Sec";
    public static final String NIV_ACA_SEC_BAC_5 = "5to Secundaria - Bach";
    public static final String NIV_ACA_SEC_BAC_4 = "4to Secundaria - Bach";
    //Centro de responsabilidad para matricula academico
    public static final String CR_ACADEMICO = "SEC-Académico";
    public static final Integer CODIGO_PADRE = 12402;
    public static final Integer CODIGO_MADRE = 12403;

    //Personal Formaciï¿½n
    public static final String TIP_FOR_BAS = "Básica";

    //aï¿½os defecto
    public static final int ANO_INI_DEFAULT = 1950; //rrhh
    public static final int ANO_INI_DEFAULT_COLE = 2005;

    // Sollicitud pagada
    public static final Integer solicitud_Pagada = 18705; 
    public static final Integer solicitud_Pagada_Log = 19905; 
    
    //Personal Documento
    public static final String SIN_FEC_CADUCA = "-----";
    public static final String CON_FEC_CADUCA = "Poner Fecha";
    public static final String SIN_FEC_PRESENTACION = "-----";
    public static final String CON_FEC_PRESENTACION = "Poner Fecha";

    //super admin
    public static final String SUPER_ADMIN = "Jefe de Informática";
    public static final String SISTEMAS = "Sistemas";
    public static final String ANULAR_SOLI = "Anulador de Recibos";
    public static final String ENCARGADO_CTACTE = "Encargado de Cta. Cte.";
    public static final String GESTOR_SOLI = "Gestor Solicitudes";
    public static final String ENCARGADO_RRHH = "Encargado de RRHH";
    public static final String ENCARGADO_LOG = "Encargado de Logistica";

    //ESTADOS
    public static final String Activo = "Activo";
    public static final String Inactivo = "Inactivo";
    public static final String SinEstado = "-";
    //ESTADO CIVIL
    public static final String COD_DIVORCIADO = "Divorciado(a)";
    public static final String COD_SEPARADO = "Separado(a)";
    public static final String COD_SOLTERO = "Soltero(a)";
    public static final String COD_VIUDO = "Viudo(a)";
    //Parentesco
    public static final Integer COD_PAPA_id = 17401;
    public static final Integer COD_MAMA_id = 17402;
    public static final Integer COD_Apoderado_id = 17403;
    public static final String COD_PAPA = "Padre";
    public static final String COD_MAMA = "Madre";
    public static final String COD_OTRO = "Otro";
    public static final String COD_Apoderado = "Apoderado";
    public static final String COD_hrn = "Hermano(a)";
    public static final String COD_tia = "Tío(a)";
    public static final String COD_abue = "Abuelo(a)";
    public static final String COD_PRIMO = "Primo(a)";
    public static final String COD_SOBRINO = "Sobrino(a)";
    public static final String COD_REPRESENTANTE = "Representante Legal";
    public static final String COD_HIJA = "Hijo(a)";

    public static final String CAT_CATEGORIA = "CatalogoCategoria";

    public static final String VIEW_ENT_SUP = "VW_EntidadEduSup";
    public static final String VIEW_ENT_FIN = "VW_EntidadFinanciera";
    public static final String VIEW_ENT_PROVEEDOR = "VW_EntidadProveedor";
    public static final String VIEW_ENT_PROV = "VW_EntidadPrevisional";//AFP , ONP
    public static final String VIEW_ENT_SAL = "VW_EntidadSalud";//hospitales, clinicas,EPS,ESSALUD

    //TipoActividad
    public static final String TIP_ACTIVIDAD = "TipoActividad";
    public static final String TIP_UNIMED = "TipoUnidadMedida";

    //TipoIndicador
    public static final String TIP_Indicador = "TipoIndicador";
    public static final String TIP_UsoIndicador = "TipoUsoIndicador";

    //Concepto $$$
    public static final String SIN_CONCEPTO = "General";

    //Lista Admison distintas
    public static final String ADM_GRUPO = "Grupo";

    //
    public static final String SIN_DOC_REF = "Sin Doc. de Referencia";
    public static final String SIN_FECHA_ING = "Sin Fecha de Ingreso";

    //TipoConcepto
    public static final String TIP_CON_MATRICULA = "Matricula";
    public static final String TIP_CON_PENSION = "Pension de Enseñanza";

    //NombrePerfil
    public static final String PER_CAJERO = "Cajero";
    public static final String PER_CAJERO_CCH = "Cajero Caja Chica";

    //tipo solicitud 
    public static final String SIN_AUTORIZA = "--------";
    public static final String SIN_AUTORIZADOR = "Sin Autorizador";
    public static final String COD_PERSONAL = "Personal";
    public static final String COD_UNIORG = "Unidad Orgánica";
    public static final String SIN_ANS = "---";
    public static final Integer TIP_SOL_PERSONAL = 18501;
    public static final Integer TIP_SOL_UNIORG = 18502;
    public static final Integer COD_PENDIENTE_CCH = 18701;
    public static final Integer COD_PENDIENTE_RCL = 19004;
    public static final Integer COD_AUTORIZADO_SOL = 18703;

    //Tipo de Registro de Compra
    public static final String COD_REGISTRADO = "Registrado";
    public static final String COD_Comprado_Parcial = "Parcial";
    public static final Integer COD_REGISTRADO_REGISTRO_COMPRA = 19006;
    public static final Integer parcial = 11205;
    public static final Integer COD_COMPRADO_REGISTRO_COMPRA = 19007;
    public static final Integer COD_COMPRADO_PARCIAL_REGISTRO_COMPRA = 19008;

    //Tipo de Requerimiento
    public static final Integer COD_COMPRADO_REQUERIMIENTO = 11209;
    public static final String COD_COMPRAREQUERIMIENTO = "Comprado";

    //solicitud caja chica
    public static final String COD_SOL_ANULADO = "Anulado";
    public static final String COD_SOL_AUTORIZADO = "Autorizado";
    public static final String COD_SOL_PAGADO = "Pagado";
    public static final String COD_SOL_NO_AUTORIZADO = "No autorizado";
    public static final String COD_SOL_PENDIENTE = "Pendiente";
    public static final String COD_SOL_SERVICIO = "Servicio";

    //Solicitud Registro Compra
    public static final String COD_SOL_REG_ANULADO = "Anulado";
    public static final String COD_SOL_REG_AUTORIZADO = "Autorizado";
    public static final String COD_SOL_REG_AUTORIZADA = "Autorizada";
    public static final String COD_SOL_REG_NO_AUTORIZADO = "No autorizado";
    public static final String COD_SOL_REG_NO_AUTORIZADA = "No autorizada";
    public static final String COD_SOL_REG_PENDIENTE = "Pendiente";

    //Objetos de Mensaje
    public static final String OBJ_SOL_CAJACH = "MT_SolicitudCajaCh";
    public static final String OBJ_SOL_REG_COMPRA = "ML_RegistroCompra";
    public static final String OBJ_SOL_ORD_COMPRA = "ML_OrdenCompra";
    public static final String OBJ_SOL_FACT_COMPRA = "ML_FacturaCompra";
    public static final String OBJ_SOL_LOG = "ML_Requerimiento";
    public static final String OBJ_LIQUIDACION = "MT_CajaChicaLiquidacion";
    public static final String COD_ELIMINADO = "Eliminado";
    public static final String COD_ATENTIDO = "Atendido";
    public static final String COD_SOL_PENDIENTE_MSJE = "Pendiente";
    public static final String NIVEL_AUTO_1 = "Autorizador 1";
    public static final String NIVEL_AUTO_2 = "Autorizador 2";
    public static final String NIVEL_AUTO_3 = "Autorizador 3";
    public static final String OBJ_SOL_CAJACH_VISTA = "General";
    public static final String OBJ_SOL_REG_COMPRA_VISTA = "Pagos";
    public static final String OBJ_FACT_COMPRA_VISTA = "Factura";
    public static final String OBJ_SOL_LOG_VISTA = "Requerimiento";

    //Tipo Status Estudiante
    public static final String TIP_POST = "Postulante";

    //Tipo Status Postulante    
    public static final String TIP_ADM = "Admitido";

    //Categoria Nombre   
    public static final String NOM_CAT_MAT = "Matricula";
    public static final String NOM_CAT_PEN = "Pension de Enseñanza";
    //
    public static final String NOM_CAT_EXA = "Exámenes";
    public static final String NOM_CAT_CUR_VAC = "Cursos Vacacionales";
    public static final String NOM_CAT_ACT_COM = "Actividades complementarias";
    public static final String NOM_CAT_DES_REB_BON = "Descuentos, Rebajas y Bonificaciones";
    public static final String NOM_CAT_DER_INS = "Derecho de Inscripción";
    public static final String NOM_CAT_OTR_ING = "Otros Ingresos";
    public static final String NOM_CAT_OTR_ING_GEST = "Otros Ingresos de Gestión";
    public static final String NOM_CAT_EXPED = "-";
    public static final String NOM_CAT_TALLERES = "Talleres";
    public static final String NOM_CAT_ALQUILERES = "Alquileres";

    public static final Integer COD_EST_MAT = 11701;

//    public static final String NOM_CAT_EXPED = "EXPEDIC. CERTIFIC. CONST. DIPLOM. Y SIMILARES";
    public static final String NOM_CAT_RECLA_MN = "RECLAMACIONES DE TERCEROS - M.N.";
    public static final String NOM_CAT_RECLA_ME = "RECLAMACIONES DE TERCEROS - M.E.";
    //concepto
    public static final String NOM_CON_CUOTA_ING = "Cuota de ingreso";

    public static final String COD_REQ_LOG = "Requerimiento";

    //Descuentos Conceptos Tipo Discente
    public static final Object DSCTO_ALU = "dsctoAlumno";
    public static final Object DSCTO_EMP = "dsctoEmpleado";
    public static final Object DSCTO_EX_ALU = "dsctoExAlumno";
    public static final Object DSCTO_EXT = "dsctoExterno";
    public static final Double VALOR_IGV = 18.00d;
    public static final Double VALOR_IGV_0 = 0.00d;
    public static final Double VALOR_IGV_RECIBO_POSITIVO_0 = 0.00d;
    public static final Double VALOR_IGV_RECIBO_NEGATIVO_8 = 8.00d;
    //doc Egreso
    public static final Integer CODIGO_CHEQUE = 15801;
    public static final Integer CODIGO_TRANSFERENCIA = 15802;
    public static final Integer CODIGO_CARTAORDEN = 15804;
    public static final String COD_CHEQUE = "Cheque";
    public static final String COD_CHEQUE_No_Anulado = "ChequeNoAnulado";
    public static final String COD_CARTA_ORD = "Carta orden";
    public static final String COD_TRANSFERENCIA = "Transferencia";
    public static final String COD_CARTA_ORDEN = "Carta orden";
    public static final String SIN_NUM_CHEQUE = "------";
    public static final String COD_SERVICIO = "Servicio";
    public static final String COD_TIPO_CTA_CTE = "Corriente";
    public static final String COD_TIPO_AHORROS = "Ahorro";

    //Elegir Documento para Registro de Compra 
    public static final Integer COD_DOC_BOL = 15202;
    public static final Integer COD_DOC_FACT = 15201;
    public static final Integer COD_DOC_REC = 15203;
    public static final Integer COD_DOC_COMP = 15204;
    public static final Integer COD_DOC_RECCAJA = 15205;

    public static final String COD_DOC_BOLETA = "Boleta";
    public static final String COD_DOC_FACTURA = "Factura";
    public static final String COD_DOC_COMPROBANTE = "Comprobante";
    public static final String COD_DOC_RECIBO = "Recibo";
    //CajaChica
    public static final String NOM_ENT = "Entrada";
    public static final String NOM_SAL = "Salida";
    public static final String NOM_PAG = "Pagado";

    //proceso banco
    public static final String TIP_CON_MAT = "Matricula";
    public static final String TIP_CON_PEN = "Pension de Enseñanza";
    public static final String FLG_PROCREC = "Recuperación";
    public static final String FLG_PROCENV = "Envio";
    public static final String TIP_CON_CUR = "Cursos Vacacionales";

    //detraccion
    //estado matricula
    public static final String FLG_MATRICULA_TRUE = "Matriculado";
    public static final String FLG_MATRICULA_FALSE = "No Matrículado";
    public static final String FLG_BACHILLER = "- Bach";

    //codigo doc ingreso
    public static final String COD_EFECTIVO = "Efectivo";
    public static final String COD_Carta_Orden = "Carta orden";
    public static final String COD_POS = "POS";
    public static final String COD_EFE_POS = "Ambos";
    public static final String COD_BANCO = "Banco";
    public static final String COD_SOL_DOL = "Ambos";
    public static final String COD_AMBOS = "Ambos";
    public static final Integer CODIGO_EFE_POS = 15806;
    public static final Integer CODIGO_POS = 15805;

    //cuenta bco soles y dolares 
    public static final Integer CTA_BCO_SOLES = 10411;
    public static final Integer CTA_BCO_SOLES_DOL = 104;
    public static final Integer CTA_BCO_DOL = 10413;
    public static final Integer COD_SOLES = 14901;
    public static final Integer COD_DOLARES = 14902;

    //CAja
    public static final String CONTRA_PAGO = "Contra Pago";
    public static final String A_RENDIR = "A rendir";
    public static final String Entregas_A_RENDIR_2 = "E - Entregas a rendir";
    public static final String Entregas_A_RENDIR = "Entregas a rendir";
    public static final String TIP_STATUS_SOL = "TipoStatusSolCajaCh";
    public static final String TIP_SOL_GEN = "General";

    //Tipo UniMEd Plan Operativo
    public static final String TIP_UNIMED_OPER = "tipoUniMed";
    //
    public static final String COD_GRUP_CR_INI = "11401";
    public static final String COD_GRUP_CR_PRI = "11402";
    public static final String COD_GRUP_CR_SEC = "11403";
    public static final String COD_GRUP_CR_SECB = "11404";
    public static final String COD_GRUP_CR_TALL = "11405";
    public static final String COD_GRUP_CR_APA = "11406";
    public static final String TIP_DIST_PON = "Ponderación";
    public static final String TIP_DIST_DIV = "División";
    public static final String TIP_DIST_PER = "Personalizada";

    //cajaGEn
    public static final String TIP_CON_ALQUILERES = "Alquileres";

    //Factura Compra 
    public static final String TIP_SOL_FACT_COMPRA = "Factura Compra";
    public static final String TIP_SOL_ARENDIR = "A rendir";
    public static final String TIP_SOL_CONTRA_PAG = "Contra Pago";
    public static final String ESTADO_PENDIENTE_FAC_COM = "Pendiente";
    public static final String ESTADO_AUTORIZADA_FAC_COM = "Autorizada";
    public static final Integer Id_ESTADO_AUTORIZADA_FAC_COM = 19901;

    //Tipo File
    public static final String file_Cabecera = "TipoFile";
    public static final String file_Detalle = "TipoFile";
    public static final String file_Intermedio = "TipoFile";

    //grados academicos
    public static final String PreInicial_3_anios = "Pre-Inicial 3 años";
    public static final String PreInicial_4_anios = "Pre-Inicial 4 años";
    public static final String Inicial_5_anios = "Inicial 5 años";
    public static final String Primero_Primaria = "1ro Primaria";
    public static final String Segundo_Primaria = "2do Primaria";
    public static final String Tercero_Primaria = "3ro Primaria";
    public static final String Cuarto_Primaria = "4to Primaria";
    public static final String Quinto_Primaria = "5to Primaria";
    public static final String Sexto_Primaria = "6to Primaria";
    public static final String Primero_Secundaria = "1ro Secundaria";
    public static final String Segundo_Secundaria = "2do Secundaria";
    public static final String Tercero_Secundaria = "3ro Secundaria";
    public static final String Cuarto_Bach_Secundaria = "4to Secundaria - Bach";
    public static final String Cuarto_Secundaria = "4to Secundaria";
    public static final String Quinto_Secundaria = "5to Secundaria";
    public static final String Quinto_Bach_Secundaria = "5to Secundaria - Bach";

    //centro de responsabilidad
    public static final String COD_CR_INI = "INI";
    public static final String COD_CR_PRI = "PRI";
    public static final String COD_CR_SEC = "SEC";
    public static final String COD_CR_BACH = "BACH";
    public static final String COD_CR_TALLER = "TALLER";

    //Tipo Lugar de Pago
    public static final Integer COD_COLEGIO = 19301;
    public static final Integer COD_LUGAR_BANCO = 19302;
    public static final Integer COD_LUGAR_AMBOS = 19303;
    public static final Integer COD_EFEC = 15803;

    //Cambiar y pone rel correcto, este solo es para pruebas
    public static final Integer COD_DEVOLUCION_CAJA = 10708;
    public static final Integer TIP_DEVOLUCION_CAJA = 107;
    public static final Integer TIP_OTROS_GAS_FI = 135;
    public static final Integer CON_REP_CAJA_CH = 13502;
    //Unidad de Negocio20604
    public static final String UNI_NEG_SANLUI = "SANLUI";
    public static final String UNI_NEG_CHAMPS = "CHAMPS";
    public static final String UNI_NEG_SANJOC = "SANJOC";
    public static final String UNI_NEG_SANJOH = "SANJOH";
    public static final String UNI_NEG_BARINA = "BARINA";
    public static final String UNI_NEG_SECTOR = "SECTOR";
    public static final String SIN_FEC_CIERRE = "-----";

    //Tipo Modo Pago
    public static final Integer COD_MODO_PAGO_BANCO=15807;
    public static final Integer COD_MODO_PAGO_EFECTIVO=15803;
    
    //Anios
    public static final Integer INICIO = 2014;
    public static final Integer FIN = 2020;

    //Tipo Files
    public static final Integer FileCabecera = 20001;
    public static final Integer FileDetalle = 20002;
    public static final Integer FileIntermedio = 20003;

    //factura
    public static final String COD_SOL_PAGADA = "Pagada";
    //Tipo Status ESt
    public static final Integer COD_EST_BLO = 18003;
    public static final Integer COD_EST_CONDI = 18008;
    public static final Integer COD_EST_FOR = 18009;
    public static final Integer COD_ESTUDIANTE_ACTIVO = 18004;

    //estado cta cte
    public static final String COD_CTACTE_PAGADO = "Pagado";

    //condonacion
    public static final Integer COD_MORA = 20501;
    public static final Integer COD_PARCIAL = 20502;

    //cod postualnte
    public static final Integer COD_INT_POSTULANTE = 18007;

    public static final String SIN_FECHA = "Sin fecha";

    //Tipos Concepto Cronograma
    public static final Integer ID_TIPO_CONCEPTOUNO = 100;
    public static final Integer ID_TIPO_CONCEPTODOS = 101;

    public static final String Envio = "Envio";
    public static final String Recuperacion = "Recepción";

    //codigoCR Adminsitracion
    public static final Integer CR_INI_ADM = 141;
    public static final Integer CR_PRI_ADM = 241;
    public static final List<CentroResponsabilidadBean> LISTA_CR_ADM;
    public static final List<CentroResponsabilidadBean> LISTA_CR_ADM_SECTOR;

    static {
        LISTA_CR_ADM = new ArrayList<>();
        LISTA_CR_ADM.add(new CentroResponsabilidadBean(141));
        LISTA_CR_ADM.add(new CentroResponsabilidadBean(241));
        LISTA_CR_ADM.add(new CentroResponsabilidadBean(341));
    }
    static {
        LISTA_CR_ADM_SECTOR = new ArrayList<>();
        LISTA_CR_ADM_SECTOR.add(new CentroResponsabilidadBean(140)); 
    }
    //codigo anulado y devuelto

    public static final Integer COD_PAGADO = 20601;
    public static final Integer COD_ANULADO = 20602;
    public static final Integer COD_DEVUELTO = 20603;
    public static final Integer COD_PENDIENTE = 20604;

    public static final String COD_STA_DOC_ANULADO = "Anulado";
    public static final String COD_STA_DOC_DEVUELTO = "Devuelto";
    public static final String COD_STA_DOC_PAGADO = "Pagado";
    //Devolucion a rednir
    public static final Integer COD_CONCEPTO_DEV_AREN = 10708;
    public static final Integer COD_STA_CTA_EST_PAG = 19404;
    public static final Integer COD_STA_CTA_EST_PEN = 19401;

    //Rendicion docegreso
    public static final Integer COD_REND_INICIADO = 20701;
    public static final Integer COD_REND_FINALIZADO = 20702;
    //TipoCajaChica
    public static final String TIPO_CAJACH = "TipoCajaChica";
    public static final String TIPO_CAJACH_STAN = "Estandar";
    public static final String TIPO_CAJACH_APA = "APAFA";
    //Formato
    public static final String FORMATO_REP_DECIMAL1 = "###,##0.00";
    public static final String FORMATO_REP_DECIMAL2 = "#0.00";

    //Proceso Banco
    public static final String Anios_Banco = "2016";
    public static final Integer Dias_Banco = 1;

    //InventarioAlmacenCategoria
    public static final String Categoria_Almacen = "Material";
    public static final String Categoria_Actvo = "Activo Fijo";
    public static final Integer Id_Categoria_Almacen = 18201;
    public static final Integer Id_Categoria_Activo = 18202;
    public static final Integer Id_Categoria_Servicio = 18203;

    //Tipo Defecto
    public static final Integer Defecto_Importe = 20402;
    //Mes
    public static final Integer NUM_ABRIL = 4;
    public static final Integer NUM_DICIEMBRE = 12;

    //Tipo Motivo Beca 
    public static final Integer TIPO_MOT_BECA_EST = 16601;
    public static final Integer TIPO_MOT_BECA_EXT = 16602;
    public static final Integer TIPO_MOT_BECA_HEM = 16603;
    //uniorg Adm
    public static final String UNI_ORG_ADM = "Administración";

    //distribucion
    public static final Integer Id_Division = 19501;

    //mensaje solicitud automatica
    public static final String MSJ_DIF_SOL = "(Diferencia A rendir)";

    //EstudianteBloqueo
    public static final Integer Id_Bloqueo_Activo = 20901;
    public static final Integer Id_Bloqueo_Resuelto = 20902;
    public static final String bloqueo_Activo = "Pendiente";
    public static final String bloqueo_Resuelto = "Solucionado";
    public static final String TIP_Status_Bloqueo = "TipoStatusBloqueo";

    public static final Integer Id_Bloqueo_Activo_SANJOC = 26001;
    public static final Integer Id_Bloqueo_Resuelto_SANJOC = 26002;

    public static final String Grupo_Especial = "especial";
    //vacunas
    public static final String vacuna_Recien_Nacido = "Bcg HepatitisB";
    public static final String Otro = "Otro";
    public static final String vacuna_2Meses = "HepatitisB Hib Polio Triple";
    public static final String vacuna_6Meses = "Hib Polio Triple";
    public static final String vacuna_1Año = "Gripe HepatitisA Rubeola Sara";
    public static final String vacuna_1Año_6Meses = "HepatitisA Meningococo Polio";
    public static final String vacuna_2Años = "Gripe Pneumococo";
    public static final String vacuna_3Años_6Meses = "Polio Triple";

    //SERIE DOC
    public static final String serie_numdoc = "002";
    public static final String serie_numdoc_1 = "001";
    public static final String serie_numdoc_3 = "003";

    //TIPO DE FILE
    public static Integer TIPO_FILE_CARACTER = 20101;
    public static Integer TIPO_FILE_NUMERO = 20102;
    public static Integer TIPO_FILE_FECHA = 20103;
    public static Integer TIPO_FILE_HORA = 20104;
    public static Integer TIPO_FILE_MONEDA = 20105;

    public static final Integer id_Transporte = 116;
//    public static final String transporte = "Transporte correos y gastos de viaje";
    //unidad
    public static final String UNI_ORG_LOGISTICA = "Logística";
    public static final String TIPO_SOL_REQ_AUTO = "Requerimiento con Autorizacion";
    public static final Integer CON_VALORADOS = 10742;

    //TIPO STATUS FICHA
    public static final Integer STATUS_FICHA_PEN = 26101;
    public static final Integer STATUS_FICHA_ANU = 26103;
    public static final Integer STATUS_FICHA_PAG = 26102;
    public static final Integer STATUS_FICHA_DON = 26104;

    //TIPO DE CAJA GEN
    public static final Integer COD_CAJA_COL = 20903;
    public static final Integer COD_CAJA_APAFA = 20903;

    //
    public static final String CON_DEV_ARENDIR = "Devolución a Rendir";
    public static final String CON_DEV_ARENDIR_MAYUS = "DEVOLUCIÓN A RENDIR";
    public static final String COD_CAJA_GEN_COL = "Colegio";
    public static final String COD_CAJA_GEN_APAFA = "Apafa";

    //CODIGO ESTADO FICHA
    public static final String COD_FICHA_STATUS_PENDIENTE = "Pendiente";
    public static final String COD_FICHA_STATUS_PAGADO = "Pagada";
    public static final String COD_FICHA_STATUS_ANULADO = "Anulado";
    public static final String COD_FICHA_STATUS_DONADO = "Donado";

    //RUC BANCOS
    public static final String RUC_INTERBANK = "20100053455";
    public static final String RUC_BCP = "20100047218";
    public static final String RUC_SCOTIABANK = "20100043140";

    public static final String servicioTransporte = "Servicio Transporte";

    //CODIGOS DE ESQUELA
    public static final Integer COD_TIP_ESQUELA_AVISO = 19703;
    public static final Integer COD_TIP_ESQUELA_DEUDA = 19702;
    public static final Integer COD_TIP_ESQUELA_PENSI = 19701;

    public static final String transporte = "Transporte";

    //impresora web
    public static final String IMPRESORA_WEB = "IMPRESORA WEB";

    public static final String TIP_PAGOSEGURO = "TipoPagoSeguro";

    //CONSTANTE TIPO CONCEPTO
    public static final Integer TIP_CONCEPTO_TALLER = 103;

    //FOTOS
    public static final String NO_FOTO_CHAMPS = "/noFoto.jpg";
    public static final String NO_FOTO_BARINA = "/noFoto.jpg";

    //
    public static final String SIN_NRODOC_DOCINGRESO = "--------";

    public static final String EST_BLOQUEO = "Bloqueado";
    public static final String IMPRESORA_PARA_PENS = "IMPR-PENSIONES";

    //TIPO DE USO INDICADOR
    public static final Integer COD_TIP_USO_IND_PE = 14401;
    public static final Integer COD_TIP_USO_IND_PO = 14402;
    public static final Integer COD_TIP_USO_IND_AM = 14403;

    //TIPO DE PERIODO
    public static final Integer COD_PERIODO_ANUAL = 26401;
    public static final Integer COD_PERIODO_SEMESTRAL = 26402;
    public static final Integer COD_PERIODO_BIMESTRAL = 26403;
    public static final Integer COD_PERIODO_MENSUAL = 26404;

    public static final String COD_LAB_PERIODO_ANUAL = "Anual";
    public static final String COD_LAB_PERIODO_SEMESTRAL = "Semestral";
    public static final String COD_LAB_PERIODO_BIMESTRAL = "Bimestral";
    public static final String COD_LAB_PERIODO_MENSUAL = "Mensual";

    //ESTADO PAGO BANCO
    public static final Integer COD_PAG_BANCO_EMI = 21201;
    public static final Integer COD_PAG_BANCO_PAG = 21202;
    public static final Integer COD_PAG_BANCO_VEN = 21203;
    public static final Integer COD_PAG_BANCO_ANU = 21202;

    //Solicitud Autorizada
    public static final Integer COD_Soli_Autorizada = 11204;

    //Codigo error banco 
    public static final Integer COD_ErrorBco_OK = 30001;
    public static final Integer COD_ErrorBco_DeudaNoExiste = 30002;
    public static final Integer COD_ErrorBco_MontoNoIgual = 30003;
    public static final Integer COD_ErrorBco_ErrorEstado_PagoDup = 30004;
    public static final Integer COD_ErrorBco_NoPagoPensAnt = 30005;
    public static final Integer COD_ErrorBco_CodigoErrorEst = 30006;
    public static final Integer COD_ErrorBco_FechaVencIncorrecta = 30007;
    public static final Integer COD_ErrorBco_RecNoGen = 30008;
    
    //Beca
    public static final String Beca6="6/6";
    
       //Reingreso
    public static final Integer COD_REINGRESO = 17603; 

    //Tipo Valor descto
    public static final String Porcentual="Porcentual";
    public static final String Ordinal="Ordinal";
    public static final Integer idPorcentual=15701;
    public static final Integer idOrdinal=15702;
    
    //Tipo Descanso Medico
    public static final Integer COD_Descanso_Medico = 31301;
    public static final Integer COD_Inasistencia_Medico = 31302;
    public static final Integer COD_Accidente_Laboral = 31303;
    
    public static final String TIP_Estudios = "TipoEstudiosMaristas";
    public static final String TIP_OtrosEstudios = "TipoOtrosEstudios";
    public static final String TIP_Financiamiento = "TipoFinanciamiento";
    public static final String TIP_Modalidad_Estudios = "TipoModalidad";
}
