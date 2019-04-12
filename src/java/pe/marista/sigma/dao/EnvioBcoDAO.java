/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.EnvioBcoBean;
import pe.marista.sigma.bean.TipoEnvioUniNegBean;

/**
 *
 * @author MS-001
 */
public interface EnvioBcoDAO {

    public List<EnvioBcoBean> obtenerListaEnvioBcoPorId(@Param("uniNeg") String uniNeg, @Param("idProcesoBancoIns") Integer idProcesoBancoIns) throws Exception;
    
    public Integer obtenerCantRegPorId(@Param("uniNeg") String uniNeg, @Param("idProcesoBancoIns") Integer idProcesoBancoIns) throws Exception;
    public String obtenerCabeceraRegPorId(@Param("uniNeg") String uniNeg, @Param("idProcesoBancoIns") Integer idProcesoBancoIns) throws Exception;
    public List<EnvioBcoBean> obtenerListaIntermedioRegPorId(@Param("uniNeg") String uniNeg, @Param("idProcesoBancoIns") Integer idProcesoBancoIns) throws Exception;
    public String obtenerNombreFilePorFormula(@Param("uniNeg") String uniNeg,@Param("fecha") Date fecha) throws Exception;

    public void CURSOR_insertarLogEnvioBco(@Param("uniNeg") String uniNeg,
            //rango de fechas
            @Param("fechaIni") Date fechaIni,
            @Param("fechaFin") Date fechaFin,
            //tipo de registro
            @Param("tipoStatusCtaCte") TipoEnvioUniNegBean tipoStatusCtaCte,
            //Filtros
            @Param("listAnios") List<Integer> listAnios,
            @Param("listMeses") List<Integer> listMeses,
            @Param("usuario") String usuario,
            @Param("idProcesoBancoIns") Integer idProcesoBancoIns
            ) throws Exception;
    public void CURSOR_insertarLogEnvioBcoCabecera(@Param("uniNeg") String uniNeg,
            //rango de fechas
            @Param("fechaIni") Date fechaIni,
            @Param("fechaFin") Date fechaFin,
            //tipo de registro
            @Param("tipoStatusCtaCte") TipoEnvioUniNegBean tipoStatusCtaCte,
            //Filtros
            @Param("listAnios") List<Integer> listAnios,
            @Param("listMeses") List<Integer> listMeses,
            @Param("usuario") String usuario,
            @Param("idProcesoBancoIns") Integer idProcesoBancoIns
            ) throws Exception;
    public void CURSOR_insertarLogEnvioBcoIntermedio(@Param("uniNeg") String uniNeg,
            //rango de fechas
            @Param("fechaIni") Date fechaIni,
            @Param("fechaFin") Date fechaFin,
            //tipo de registro
            @Param("tipoStatusCtaCte") TipoEnvioUniNegBean tipoStatusCtaCte,
            //Filtros
            @Param("listAnios") List<Integer> listAnios,
            @Param("listMeses") List<Integer> listMeses,
            @Param("usuario") String usuario,
            @Param("idProcesoBancoIns") Integer idProcesoBancoIns
            ) throws Exception;
    public Integer countCURSOR_insertarLogEnvioBco(@Param("uniNeg") String uniNeg,
            //rango de fechas
            @Param("fechaIni") Date fechaIni,
            @Param("fechaFin") Date fechaFin,
            //tipo de registro
            @Param("idTipoStatusCtaCte") Integer idTipoStatusCtaCte,
            //Filtros
            @Param("listAnios") List<Integer> listAnios,
            @Param("listMeses") List<Integer> listMeses 
            ) throws Exception;

    
}
