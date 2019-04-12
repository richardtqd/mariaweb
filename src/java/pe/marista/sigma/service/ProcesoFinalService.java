/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.Contenedor;
import pe.marista.sigma.bean.CuentasPorCobrarBean;
import pe.marista.sigma.bean.ProcesoBancoBean;
import pe.marista.sigma.bean.ProcesoFinalBean;
import pe.marista.sigma.bean.reporte.ConciliaBancoRepBean;
import pe.marista.sigma.bean.reporte.NotaAbonoRepBean;
import pe.marista.sigma.bean.reporte.PagosUniNegRepBean;
import pe.marista.sigma.bean.reporte.ProcesoFalloRepBean;
import pe.marista.sigma.bean.reporte.ReportePagoRepBean;
import pe.marista.sigma.dao.ProcesoFinalDAO;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.util.SpringWebApplicationContext;

/**
 *
 * @author MS002
 */
public class ProcesoFinalService {

    private ProcesoFinalDAO procesoFinalDAO;

    @Transactional
    public void insertarProcesoFinal(ProcesoFinalBean procesoFinalBean) throws Exception {
        procesoFinalDAO.insertarProcesoFinal(procesoFinalBean);
    }

    @Transactional
    public Object execProRecup(String uniNeg, Integer idProcesoBanco, String valor, Integer posicion) throws Exception {
        return procesoFinalDAO.execProRecup(uniNeg, idProcesoBanco, valor, posicion);
    }

    @Transactional
    public Object execProcesoRecup(String uniNeg, String ruc, Integer sizeFile, Integer idprocesobanco) throws Exception {
        return procesoFinalDAO.execProcesoRecup(uniNeg, ruc, sizeFile, idprocesobanco);
    }

    @Transactional
    public Object execProcesoRecupIns(String uniNeg, String ruc, Integer idprocesobanco, String elemento, Integer posicion, Integer id, String creaPor) throws Exception {
        return procesoFinalDAO.execProcesoRecupIns(uniNeg, ruc, idprocesobanco, elemento, posicion, id, creaPor);
    }

    @Transactional
    public Object execProConcilia(String uniNeg, String ruc, Integer idProcesoBanco, Integer id, String modiPor) throws Exception {
        return procesoFinalDAO.execProConcilia(uniNeg, ruc, idProcesoBanco, id, modiPor);
    }

    @Transactional
    public Object execProAsiento(String uniNeg, String objeto, Integer idObjeto, String creaPor, Integer idProcesoBanco) throws Exception {
        return procesoFinalDAO.execProAsiento(uniNeg, objeto, idObjeto, creaPor, idProcesoBanco);
    }

    public List<Contenedor> obtenerListaBancos(String uniNeg, String ruc, Integer idProcesoBanco) throws Exception {
        List<Contenedor> listaProBanco = new ArrayList<>();
//        List<List<Contenedor>> listaProceso = new ArrayList<>();
        List<Contenedor> listaProceso = new ArrayList<>();
        SqlSessionFactory ssf = (SqlSessionFactory) SpringWebApplicationContext.getInstance().getBean("sqlSessionFactory");
        SqlSession sesion = ssf.openSession();
        Connection cn = sesion.getConnection();
        PreparedStatement pt = cn.prepareCall("declare @uniNeg char(6), "
                + "@ruc varchar(11),@idprocesobanco Integer  "
                + "declare @nomBanco varchar(1200) = '', @sql varchar(5000) = '' set @uniNeg = ? set @ruc = ? "
                + "declare @nomTable varchar(1200) = '', @proceso Integer = 0 "
                + "set @idprocesobanco = ? set @nomBanco = (select case when (select ruc from MO_Entidad "
                + "where flgfinanciera = 1 and unineg = @uniNeg and ruc = @ruc) = @ruc then (select nombre from MO_Entidad where "
                + "flgfinanciera = 1 and unineg = @unineg and ruc = @ruc) end as nomEn) "
                //                + "set @sql = "
                //                + "'select * from RECUPERACION_'+@uniNeg+'_'+@nomBanco+' where idprocesobanco='+convert(varchar,@idprocesobanco)"
                + "set @proceso = (select flgproceso from MT_ProcesoBanco where unineg = @unineg and idprocesobanco = @idprocesobanco)"
                + "set @nomTable = (select case \n"
                + "					when '20100053455' = @ruc then (case \n"
                + "                                                                           when @proceso = 1 then 'ENVIO_' +@uniNeg+'_InterBank'  \n"
                + "                                                                           when @proceso = 0 then 'RECUPERACION_' +@uniNeg+'_InterBank' \n"
                + "									end)\n"
                + "					when '20100047218' = @ruc then (case \n"
                + "                                                                           when @proceso = 1 then 'ENVIO_' +@uniNeg+'_BCP'  \n"
                + "                                                                           when @proceso = 0 then 'RECUPERACION_' +@uniNeg+'_BCP' \n"
                + "									end)   \n"
                + "					when '20100043140' = @ruc then (case \n"
                + "                                                                           when @proceso = 1 then 'ENVIO_' +@uniNeg+'_SCOTIA'  \n"
                + "                                                                           when @proceso = 0 then 'RECUPERACION_' +@uniNeg+'_SCOTIA' \n"
                + "                                                                       end) \n"
                + "                                   end) "
                + "if OBJECT_ID(@nomTable,'U') is not null \n"
                + "begin "
                + "set @sql = 'select * from '+@nomTable+' where idprocesobanco = '+convert(varchar,@idprocesobanco) "
                + "exec(@sql) "
                + "end");
        pt.setString(1, uniNeg);
        pt.setString(2, ruc);
        pt.setInt(3, idProcesoBanco);
        ResultSet rs = pt.executeQuery();
        Integer ini = procesoFinalDAO.obtenerPosItem(uniNeg, ruc, 0, 1);
        Integer fin = procesoFinalDAO.obtenerPosItem(uniNeg, ruc, 1, 1);
        ini = ini + 3;
        Integer row = 0;
        Contenedor cont = new Contenedor();

        while (rs.next()) {
            row++;
            for (int i = (ini); i <= (fin + 4); i++) {
                Contenedor contenedor = new Contenedor();
                contenedor.setValor(rs.getObject(i));
                listaProBanco.add(contenedor);
            }

//            listaProceso.add(listaProBanco);
//            mapaProceso.add(row, listaProBanco);
            cont.setListaContenedor(listaProBanco);
            listaProceso.add(cont);
            listaProBanco = new ArrayList<>();
            cont = new Contenedor();
        }
//        pt.close();
//        rs.close();
//        cn.close();
        return listaProceso;
    }

    public List<Contenedor> obtenerIdList(String uniNeg, String ruc, Integer idProcesoBanco) throws Exception {
        List<Contenedor> listaProBanco = new ArrayList<>();
//        List<List<Object>> listaProceso = new ArrayList<>();
//        HashMap<Integer, List<Object>> mapaProceso = new HashMap();
        List<Contenedor> listaProceso = new ArrayList<>();
        SqlSessionFactory ssf = (SqlSessionFactory) SpringWebApplicationContext.getInstance().getBean("sqlSessionFactory");
        SqlSession sesion = ssf.openSession();
        Connection cn = sesion.getConnection();
        PreparedStatement pt = cn.prepareCall("declare @uniNeg char(6),"
                + "@ruc varchar(11),@idprocesobanco Integer  "
                + "declare @nomBanco varchar(1200) = '', @sql varchar(5000) = ''set @uniNeg = ? set @ruc = ? "
                + "set @idprocesobanco = ? set @nomBanco = (select case when (select ruc from MO_Entidad "
                + "where flgfinanciera = 1 and unineg = @uniNeg and ruc = @ruc) = @ruc then (select nombre from MO_Entidad where "
                + "flgfinanciera = 1 and unineg = @unineg and ruc = @ruc) end as nomEn) set @sql = "
                + "'select * from RECUPERACION_'+@uniNeg+'_'+@nomBanco+' where idprocesobanco=' + convert(varchar,@idprocesobanco) + ' and unineg = ' + '''' + @uniNeg + '''' "
                + "exec(@sql)");
        pt.setString(1, uniNeg);
        pt.setString(2, ruc);
        pt.setInt(3, idProcesoBanco);
        ResultSet rs = pt.executeQuery();
        Integer row = 0;
        while (rs.next()) {
            row++;
            Contenedor contenedor1 = new Contenedor(rs.getObject(row));
            listaProBanco.add(contenedor1);
//            mapaProceso.put(row, listaProBanco);
            listaProceso.get(row - 1).setListaContenedor(listaProBanco);
            listaProBanco = new ArrayList<>();
        }
        pt.close();
        rs.close();
        cn.close();
        return listaProceso;
    }

    public List obtenerColumnas() throws Exception {
        List<Object> listaColumnas = new ArrayList<>();
        SqlSessionFactory ssf = (SqlSessionFactory) SpringWebApplicationContext.getInstance().getBean("sqlSessionFactory");
        SqlSession sesion = ssf.openSession();
        Connection cn = sesion.getConnection();
        PreparedStatement pt = cn.prepareStatement("select column_name,\n"
                + "                 (\n"
                + "                 	case \n"
                + "                 		when column_name = 'unineg'		then 'unidad de Negocio'\n"
                + "                 		when column_name = 'iddiscente'	then 'Codigo de Estudiante'\n"
                + "                 		when column_name = 'nomdiscente'	then 'Nombres de Estudiante'\n"
                + "                 		when column_name = 'inforetorno'	then 'Inf. de Retorno'\n"
                + "                 		when column_name = 'fechaemision'	then 'Fecha de Emisión'\n"
                + "                 		when column_name = 'fechavenc'	then 'Fecha de Vencimiento'\n"
                + "                 		when column_name = 'moneda'		then 'Tipo de Moneda'\n"
                + "                 		when column_name = 'monto'		then 'Importe'\n"
                + "                 		when column_name = 'mora'			then 'Mora'\n"
                + "                 		when column_name = 'tiporegistro'	then 'Tipo de Registro'\n"
                + "                 		when column_name = 'idconcepto'	then 'Concepto'\n"
                + "				when column_name = 'creafecha'  then 'Seleccionar'\n"
                + "                 		when column_name = 'idctasxcobrar'	then 'Codigo Antiguo'\n"
                + "				when column_name = 'creapor'  then 'otros'\n"
                + "							\n"
                + "                 	end\n"
                + "                 ) as Columna\n"
                + "                 from INFORMATION_SCHEMA.columns \n"
                + "                 where table_name = 'MT_ProcesoEnvio' \n"
                + "                 and column_name <> 'idprocesoenvio'\n"
                + "                 and column_name <> 'idprocesobanco' \n"
                + "                 and column_name <> 'modipor' \n"
                + "                 and column_name <> 'modiver'\n"
                + "                 and column_name <> 'flgEnvio' \n"
                + "                 and column_name <> 'flgGrabar'\n"
                + "                 and column_name <> 'flgGrabar' \n"
                + "                 and column_name <> 'unineg'\n"
                + "                 and column_name <> 'codigodiscente'\n"
                + "				 order by column_name");
        ResultSet rs = pt.executeQuery();
        while (rs.next()) {
            listaColumnas.add(rs.getObject(2));
        }
        pt.close();
        rs.close();
        cn.close();
        System.out.println(listaColumnas.size());
        return listaColumnas;
    }

    public List<Object> obtenerColumnasCab() throws Exception {
        List<Object> listaCabecera = new ArrayList<>();
        SqlSessionFactory ssf = (SqlSessionFactory) SpringWebApplicationContext.getInstance().getBean("sqlSessionFactory");
        SqlSession sesion = ssf.openSession();
        Connection cn = sesion.getConnection();
        PreparedStatement pt = cn.prepareStatement("select column_name,\n"
                + "(\n"
                + "     	case \n"
                + "            when column_name = 'unineg'		then 'unidad de Negocio'\n"
                + "            when column_name = 'anio'	then 'Año'\n"
                + "            when column_name = 'ruc'	then 'Ruc'\n"
                + "			when COLUMN_NAME = 'fecha' then 'Fecha'\n"
                + "            when column_name = 'nombre'	then 'Nombre de Proceso'\n"
                + "            when column_name = 'codunineg'	then 'Codigo de UniNeg'\n"
                + "            when column_name = 'numcuenta'	then 'Numero de Cuenta'\n"
                + "            when column_name = 'moneda'		then 'Tipo de Moneda'\n"
                + "            when column_name = 'regenv'		then 'Registros enviados'\n"
                + "            when column_name = 'montoenv'			then 'Monto Enviado'\n"
                + "			when column_name = 'horacorte'	then 'Hora'\n"
                + "            when column_name = 'tipoarchivo'	then 'Tipo de Archivo'\n"
                + "			when column_name = 'creafecha'  then 'Seleccionar'\n"
                + "			when column_name = 'creapor'  then 'otros' \n"
                + "         end\n"
                + ") as Columna\n"
                + "from INFORMATION_SCHEMA.columns  \n"
                + "where table_name = 'MT_ProcesoBanco'\n"
                + "and column_name <> 'unineg'\n"
                + "and column_name <> 'flgproceso'   \n"
                + "and column_name <> 'idprocesobanco' \n"
                + "and column_name <> 'modipor' \n"
                + "and column_name <> 'modiver' \n"
                + "and column_name <> 'montorecup' \n"
                + "and column_name <> 'regerror' \n"
                + "and column_name <> 'creafecha' \n"
                + "and column_name <> 'creapor' \n"
                + "order by column_name");
        ResultSet rs = pt.executeQuery();
        while (rs.next()) {
            listaCabecera.add(rs.getObject(2));
        }
        pt.close();
        rs.close();
        cn.close();
        return listaCabecera;
    }

    public void execProEnvio(String uniNeg, String ruc, Integer sizeFile, Integer idProcesoBanco) throws Exception {
        procesoFinalDAO.execProEnvio(uniNeg, ruc, sizeFile, idProcesoBanco);
    }

    public List<Contenedor> execProListaBanco(String uniNeg, String ruc, Integer idprocesobanco, Integer procesoFile, Integer dato, Integer proceso) throws Exception {
        List<Contenedor> listaProBanco = new ArrayList<>();
        List<Contenedor> listaProceso = new ArrayList<>();
        SqlSessionFactory ssf = (SqlSessionFactory) SpringWebApplicationContext.getInstance().getBean("sqlSessionFactory");
        SqlSession sesion = ssf.openSession();
        Connection cn = sesion.getConnection();
        PreparedStatement pt = cn.prepareStatement("exec PRO_LISTAR_PRO_BANCO ? , ? , ? , ? , ?");
        pt.setString(1, uniNeg);
        pt.setString(2, ruc);
        pt.setInt(3, idprocesobanco);
        pt.setInt(4, procesoFile);
        pt.setInt(5, proceso);
        if (pt.execute()) {
            ResultSet rs = pt.executeQuery();
            Integer ini = 0;
            Integer fin = 0;
            if (dato.equals(1)) {
                ini = procesoFinalDAO.obtenerPosItemCab(uniNeg, ruc, 0, 2);
                fin = procesoFinalDAO.obtenerPosItemCab(uniNeg, ruc, 1, 2);
            }
            if (dato.equals(2)) {
                ini = procesoFinalDAO.obtenerPosItemInt(uniNeg, ruc, 0, 2);
                fin = procesoFinalDAO.obtenerPosItemInt(uniNeg, ruc, 1, 2);
            }
            if (dato.equals(3)) {
                ini = procesoFinalDAO.obtenerPosItem(uniNeg, ruc, 0, proceso);
                fin = procesoFinalDAO.obtenerPosItem(uniNeg, ruc, 1, proceso);
            }
//        ini = ini + 3;
            Integer row = 0;
            Contenedor cont = new Contenedor();
            while (rs.next()) {
                row++;
                for (int i = (ini); i <= fin; i++) {
                    Contenedor contenedor = new Contenedor();
                    contenedor.setValor(rs.getObject(i));
                    listaProBanco.add(contenedor);
                }
                cont.setListaContenedor(listaProBanco);
                listaProceso.add(cont);
                listaProBanco = new ArrayList<>();
                cont = new Contenedor();
            }
//            pt.close();
//            rs.close();
//            cn.close();
        }
        return listaProceso;
    }

    public List<Contenedor> execProListaBancoCol(String uniNeg, String ruc, Integer idprocesobanco, Integer procesoFile) throws Exception {
        List<Contenedor> listaProBanco = new ArrayList<>();
        List<Contenedor> listaProceso = new ArrayList<>();
        SqlSessionFactory ssf = (SqlSessionFactory) SpringWebApplicationContext.getInstance().getBean("sqlSessionFactory");
        SqlSession sesion = ssf.openSession();
        Connection cn = sesion.getConnection();
        PreparedStatement pt = cn.prepareStatement("exec PRO_LISTAR_PRO_BANCO_SIGMA ? , ? , ? , ?");
        pt.setString(1, uniNeg);
        pt.setString(2, ruc);
        pt.setInt(3, idprocesobanco);
        pt.setInt(4, procesoFile);
        if (pt.execute()) {
            ResultSet rs = pt.executeQuery();
            Integer ini = 0;
            Integer fin = 0;
            switch (uniNeg) {
                case MaristaConstantes.UNI_NEG_CHAMPS:
                    ini = 1;
                    fin = 23;
                    break;
                case MaristaConstantes.UNI_NEG_SANJOC:
                    ini = 1; //A CAMBIAR
                    fin = 23; //A CAMBIAR
                    break;
                case MaristaConstantes.UNI_NEG_SANLUI:
                    ini = 1; //A CAMBIAR
                    fin = 23; //A CAMBIAR
                    break;
            }
            System.out.println(">>>" + ini + " // " + fin);
//        ini = ini + 3;
            Integer row = 0;
            Contenedor cont = new Contenedor();
            while (rs.next()) {
                row++;
                for (int i = (ini); i <= fin; i++) {
                    Contenedor contenedor = new Contenedor();
                    contenedor.setValor(rs.getObject(i));
                    listaProBanco.add(contenedor);
                }
                cont.setListaContenedor(listaProBanco);
                listaProceso.add(cont);
                listaProBanco = new ArrayList<>();
                cont = new Contenedor();
            }
//            pt.close();
//            rs.close();
//            cn.close();
        }
        return listaProceso;
    }

    public List<Contenedor> obtenerEnvioBancos(String uniNeg, String ruc, Integer idprocesobanco, Integer procesoFile, Integer dato) throws Exception {
        System.out.println(">>>" + uniNeg + ruc + idprocesobanco.toString() + procesoFile + dato.toString());
        List<Contenedor> listaProBanco = new ArrayList<>();
        List<Contenedor> listaProceso = new ArrayList<>();
        SqlSessionFactory ssf = (SqlSessionFactory) SpringWebApplicationContext.getInstance().getBean("sqlSessionFactory");
        SqlSession sesion = ssf.openSession();
        Connection cn = sesion.getConnection();
        PreparedStatement pt = cn.prepareStatement("declare @uniNeg char(6) = '',@ruc varchar(11),@ini Integer = 0,@fin Integer = 0,\n"
                + "@idprocesoBanco Integer = 0,@nomTable varchar(1000) = '',@nomBanco varchar(1000),\n"
                + "@tipoFile Integer = 0,@proceso Integer = 0 \n"
                + "declare @tableEnvio4 varchar(max) = '' \n"
                + "set @uniNeg = ? \n"
                + "set @ruc = ? \n"
                + "set @idprocesoBanco = ? \n"
                + "set @tipoFile = ? \n"
                + "set @proceso = (select flgproceso from MT_ProcesoBanco where unineg = @unineg and idprocesobanco = @idprocesobanco)"
                + "set @nomBanco = (select case \n"
                + "when (select ruc from MO_Entidad where flgfinanciera = 1 and unineg = @uniNeg and ruc = @ruc) = @ruc then (select nombre from MO_Entidad where flgfinanciera = 1 and unineg = @uniNeg and ruc = @ruc) \n"
                + "end as nomEn)\n"
                + "set @nomBanco = (select case\n"
                + "					when '20100053455' = @ruc then 'InterBank' \n"
                + "					when '20100047218' = @ruc then 'BCP' \n"
                + "					when '20100043140' = @ruc then 'SCOTIABANK' \n"
                + "				   end)"
                + "set @nomTable = (select case \n"
                + "when @tipoFile = 20001 then (case \n"
                + "when @proceso = 1 then 'ENVIO_Cabecera_' +@uniNeg+'_'+@nomBanco \n"
                + "when @proceso = 0 then 'RECUPERACION_Cabecera_' +@uniNeg+'_'+@nomBanco \n"
                + "end)"
                + "when @tipoFile = 20002 then (case \n"
                + "when @proceso = 1 then 'ENVIO_' +@uniNeg+'_'+@nomBanco \n"
                + "when @proceso = 0 then 'RECUPERACION_' +@uniNeg+'_'+@nomBanco \n"
                + "end) \n"
                + "when @tipoFile = 20003 then (case \n"
                + "when @proceso = 1 then 'ENVIO_Int_' +@uniNeg+'_'+@nomBanco \n"
                + "when @proceso = 0 then 'RECUPERACION_Int_' +@uniNeg+'_'+@nomBanco \n"
                + "end) \n"
                + "end)\n"
                + "if OBJECT_ID(@nomTable,'U') is not null\n"
                + "	begin  \n"
                + "		set @ini = (select ISNULL(min(posicionitem),0) from MT_ProcesoFile where unineg = @uniNeg and flgproceso = 2 and idtipofile = @tipoFile and ruc = @ruc) \n"
                + "		set @fin = (select ISNULL(max(posicionitem),0) from MT_ProcesoFile where unineg = @uniNeg and flgproceso = 2 and idtipofile = @tipoFile and ruc = @ruc) \n"
                + "if(@ini <> 0 and @fin <> 0)\n"
                + "       begin "
                + "		set @tableEnvio4 += 'select '\n"
                + "		while @ini <= @fin\n"
                + "		begin\n"
                + "		if(@ini < @fin)\n"
                + "			begin\n"
                + "				set @tableEnvio4 += ' elemento_'+CONVERT(varchar,@ini)+', '\n"
                + "			end\n"
                + "		else\n"
                + "			begin\n"
                + "				if(@ini = @fin)\n"
                + "					begin\n"
                + "						set @tableEnvio4 += ' elemento_'+CONVERT(varchar,@ini)+' from '+@nomTable+' where unineg = '+''''+@unineg+''''+' and idprocesobanco = '+CONVERT(varchar,@idprocesoBanco) \n"
                + "					end\n"
                + "				end\n"
                + "				set @ini = @ini + 1\n"
                + "			end \n"
                + "        end "
                + "		print @tableEnvio4 \n"
                + "		exec(@tableEnvio4) \n"
                + "	end");
        pt.setString(1, uniNeg);
        pt.setString(2, ruc);
        pt.setInt(3, idprocesobanco);
        pt.setInt(4, procesoFile);
        if (pt.execute()) {
            ResultSet rs = pt.executeQuery();
            Integer ini = 0;
            Integer fin = 0;
            if (dato.equals(1)) {
                ini = procesoFinalDAO.obtenerPosItemCab(uniNeg, ruc, 0, 2);
                fin = procesoFinalDAO.obtenerPosItemCab(uniNeg, ruc, 1, 2);
            }
            if (dato.equals(2)) {
                ini = procesoFinalDAO.obtenerPosItemInt(uniNeg, ruc, 0, 2);
                fin = procesoFinalDAO.obtenerPosItemInt(uniNeg, ruc, 1, 2);
            }
            if (dato.equals(3)) {
                ini = procesoFinalDAO.obtenerPosItem(uniNeg, ruc, 0, 2);
                fin = procesoFinalDAO.obtenerPosItem(uniNeg, ruc, 1, 2);
            }
            System.out.println(">>>" + procesoFile);
            System.out.println(">>>" + dato);
            System.out.println(">>>" + ini + "-" + fin);
//        ini = ini + 3;
            Integer row = 0;
            Contenedor cont = new Contenedor();
            while (rs.next()) {
                row++;
                for (int i = (ini); i <= fin; i++) {
                    Contenedor contenedor = new Contenedor();
                    contenedor.setValor(rs.getObject(i));
                    listaProBanco.add(contenedor);
                }
                cont.setListaContenedor(listaProBanco);
                listaProceso.add(cont);
                listaProBanco = new ArrayList<>();
                cont = new Contenedor();
            }
            pt.close();
            rs.close();
            cn.close();
        }
        return listaProceso;
    }

    public List<Contenedor> obtenerEnvioBancosCurCod(String uniNeg, String ruc, Integer idprocesobanco, Integer procesoFile, Integer dato) throws Exception {
        System.out.println(">>>" + uniNeg + ruc + idprocesobanco.toString() + procesoFile + dato.toString());
        List<Contenedor> listaProBanco = new ArrayList<>();
        List<Contenedor> listaProceso = new ArrayList<>();
        SqlSessionFactory ssf = (SqlSessionFactory) SpringWebApplicationContext.getInstance().getBean("sqlSessionFactory");
        SqlSession sesion = ssf.openSession();
        Connection cn = sesion.getConnection();
        PreparedStatement pt = cn.prepareStatement("declare @uniNeg char(6) = '',@ruc varchar(11),@ini Integer = 0,@fin Integer = 0,\n"
                + "@idprocesoBanco Integer = 0,@nomTable varchar(1000) = '',@nomBanco varchar(1000),\n"
                + "@tipoFile Integer = 0 \n"
                + "declare @tableEnvio4 varchar(max) = ''\n"
                + "set @uniNeg = ? \n"
                + "set @ruc = ?\n"
                + "set @idprocesoBanco = ? \n"
                + "set @tipoFile = ? \n"
                + "set @nomBanco = (select case \n"
                + "when (select ruc from MO_Entidad where flgfinanciera = 1 and unineg = @uniNeg and ruc = @ruc) = @ruc then (select nombre from MO_Entidad where flgfinanciera = 1 and unineg = @uniNeg and ruc = @ruc) \n"
                + "end as nomEn)\n"
                + "set @nomBanco = (select case\n"
                + "					when '20100130204' = @ruc then 'BBVA' \n"
                + "					when '20100047218' = @ruc then 'BCP' \n"
                + "					when '20100043140' = @ruc then 'SCOTIA' \n"
                + "				   end)"
                + "set @nomTable = (select case \n"
                + "					when @tipoFile = 20001 then 'Envio_Cabecera_'+@uniNeg+'_'+@nomBanco\n"
                + "					when @tipoFile = 20002 then 'Envio_'+@uniNeg+'_'+@nomBanco\n"
                + "					when @tipoFile = 20003 then 'Envio_Int_'+@uniNeg+'_'+@nomBanco\n"
                + "				end)\n"
                + "if OBJECT_ID(@nomTable,'U') is not null\n"
                + "	begin  \n"
                + "		set @ini = (select ISNULL(min(posicionitem),0) from MT_ProcesoFile where unineg = @uniNeg and flgproceso = 2 and idtipofile = @tipoFile and ruc = @ruc) \n"
                + "		set @fin = (select ISNULL(max(posicionitem),0) from MT_ProcesoFile where unineg = @uniNeg and flgproceso = 2 and idtipofile = @tipoFile and ruc = @ruc) \n"
                + "if(@ini <> 0 and @fin <> 0)\n"
                + "       begin "
                + "		set @tableEnvio4 += 'select '\n"
                + "		while @ini <= @fin\n"
                + "		begin\n"
                + "		if(@ini < @fin)\n"
                + "			begin\n"
                + "				set @tableEnvio4 += ' elemento_'+CONVERT(varchar,@ini)+', '\n"
                + "			end\n"
                + "		else\n"
                + "			begin\n"
                + "				if(@ini = @fin)\n"
                + "					begin\n"
                + "						set @tableEnvio4 += ' elemento_'+CONVERT(varchar,@ini)+' from '+@nomTable+' where unineg = '+''''+@unineg+''''+' and idprocesobanco = '+CONVERT(varchar,@idprocesoBanco) \n"
                + "					end\n"
                + "				end\n"
                + "				set @ini = @ini + 1\n"
                + "			end \n"
                + "        end "
                + "		print @tableEnvio4 \n"
                + "		exec(@tableEnvio4) \n"
                + "	end");
        pt.setString(1, uniNeg);
        pt.setString(2, ruc);
        pt.setInt(3, idprocesobanco);
        pt.setInt(4, procesoFile);
        if (pt.execute()) {
            ResultSet rs = pt.executeQuery();
            Integer ini = 0;
            Integer fin = 0;
            if (dato.equals(1)) {
                ini = procesoFinalDAO.obtenerPosItemCab(uniNeg, ruc, 0, 2);
                fin = procesoFinalDAO.obtenerPosItemCab(uniNeg, ruc, 1, 2);
            }
            if (dato.equals(2)) {
                ini = procesoFinalDAO.obtenerPosItemInt(uniNeg, ruc, 0, 2);
                fin = procesoFinalDAO.obtenerPosItemInt(uniNeg, ruc, 1, 2);
            }
            if (dato.equals(3)) {
                ini = procesoFinalDAO.obtenerPosItem(uniNeg, ruc, 0, 2);
                fin = procesoFinalDAO.obtenerPosItem(uniNeg, ruc, 1, 2);
            }
//        ini = ini + 3;
            Integer row = 0;
            Contenedor cont = new Contenedor();
            while (rs.next()) {
                row++;
                for (int i = (ini); i <= fin; i++) {
                    Contenedor contenedor = new Contenedor();
                    contenedor.setValor(rs.getObject(i));
                    listaProBanco.add(contenedor);
                }
                cont.setListaContenedor(listaProBanco);
                listaProceso.add(cont);
                listaProBanco = new ArrayList<>();
                cont = new Contenedor();
            }
            pt.close();
            rs.close();
            cn.close();
        }
        return listaProceso;
    }

    @Transactional
    public void execProEnvioIns(String uniNeg, String ruc, Integer idProcesoBanco, String creaPor, Integer posicion, String nomColumna, Integer constante, String valorCons, Integer noConstante) throws Exception {
        procesoFinalDAO.execProEnvioIns(uniNeg, ruc, idProcesoBanco, creaPor, posicion, nomColumna, constante, valorCons, noConstante);
    }

    @Transactional
    public Object execProEnvioPro(String uniNeg, String ruc, Integer idProcesoBanco, Date fechaVenc, Date fechaInic, Integer anio, String idEstudiante, String codigo, String nombreCompleto, Integer idTipoConcepto, Integer idConcepto, String creaPor, String idTipoRegistro, Integer estado) throws Exception {
        return procesoFinalDAO.execProEnvioPro(uniNeg, ruc, idProcesoBanco, fechaVenc, fechaInic, anio, idEstudiante, codigo, nombreCompleto, idTipoConcepto, idConcepto, creaPor, idTipoRegistro, estado);
    }

    @Transactional
    public Object execProEnvioProCab(String uniNeg, String ruc, Integer idProcesoBanco, String creaPor, String fecha, String registroDefecto) throws Exception {
        return procesoFinalDAO.execProEnvioProCab(uniNeg, ruc, idProcesoBanco, creaPor, fecha, registroDefecto);
    }

    @Transactional
    public Object execProEnvioProCur(String uniNeg, String ruc, Integer idProcesoBanco, String idEstudiante, String codigo, String nombres, String creaPor, String fechaInic, String fecha, Integer idTipoConcepto, Integer idConcepto) throws Exception {
        return procesoFinalDAO.execProEnvioProCur(uniNeg, ruc, idProcesoBanco, idEstudiante, codigo, nombres, creaPor, fechaInic, fecha, idTipoConcepto, idConcepto);
    }

    public void execProEnvioInsCons(String uniNeg, String ruc, Integer idProcesoBanco, String creaPor, Integer posicion, String nomColumna, Integer constante, String valorCons) throws Exception {
        procesoFinalDAO.execProEnvioInsCons(uniNeg, ruc, idProcesoBanco, creaPor, posicion, nomColumna, constante, valorCons);
    }

    public List<Object> execProEnvioFile(String uniNeg, String ruc, Integer idProcesoBanco) throws Exception {
        return procesoFinalDAO.execProEnvioFile(uniNeg, ruc, idProcesoBanco);
    }

    public HashMap obtenerFile(String uniNeg, String ruc, Integer idprocesobanco) throws Exception {
        List<Object> listaFile = new ArrayList<>();
        List<List<Object>> listaProcesoEnvio = new ArrayList<>();
        HashMap<Integer, List<Object>> mapaEnvio = new HashMap();
        SqlSessionFactory ssf = (SqlSessionFactory) SpringWebApplicationContext.getInstance().getBean("sqlSessionFactory");
        SqlSession sesion = ssf.openSession();
        Connection cn = sesion.getConnection();
        PreparedStatement pt = cn.prepareStatement("exec PRO_ENVIO_FILE ?,?,?");
        pt.setString(1, uniNeg);
        pt.setString(2, ruc);
        pt.setInt(3, idprocesobanco);
        ResultSet rs = pt.executeQuery();
        Integer row = 0;
        Integer ini = procesoFinalDAO.obtenerPosItem(uniNeg, ruc, 0, 2);
        Integer fin = procesoFinalDAO.obtenerPosItem(uniNeg, ruc, 1, 2);
        while (rs.next()) {
            row++;
            for (int i = ini; i <= fin; i++) {
                listaFile.add(rs.getObject(i));
            }
            listaProcesoEnvio.add(listaFile);
            mapaEnvio.put(row, listaFile);
            listaFile = new ArrayList<>();
        }
        pt.close();
        rs.close();
        cn.close();
        return mapaEnvio;
    }

    public HashMap obtenerCabFile(String uniNeg, String ruc, Integer idprocesobanco, Integer sizeFile) throws Exception {
        List<Object> listaFile = new ArrayList<>();
        List<List<Object>> listaProcesoEnvio = new ArrayList<>();
        HashMap<Integer, List<Object>> mapaEnvio = new HashMap();
        SqlSessionFactory ssf = (SqlSessionFactory) SpringWebApplicationContext.getInstance().getBean("sqlSessionFactory");
        SqlSession sesion = ssf.openSession();
        Connection cn = sesion.getConnection();
        System.out.println(uniNeg + ruc + idprocesobanco + sizeFile);
        PreparedStatement pt = cn.prepareCall("exec PRO_ENVIO_CAB_FILE ?,?,?,?");
        pt.setString(1, uniNeg);
        pt.setString(2, ruc);
        pt.setInt(3, idprocesobanco);
        pt.setInt(4, sizeFile);
        System.out.println("numfilas + " + pt.executeUpdate());
        if (pt.executeUpdate() != 0 && pt.executeUpdate() > 0) {
            ResultSet rs = pt.executeQuery();
            Integer row = 0;
            Integer ini = procesoFinalDAO.obtenerPosItemCab(uniNeg, ruc, 0, 2);
            Integer fin = procesoFinalDAO.obtenerPosItemCab(uniNeg, ruc, 1, 2);
            if (rs.getRow() != 0) {
                while (rs.next()) {
                    row++;
                    for (int i = ini; i <= fin; i++) {
                        listaFile.add(rs.getObject(i));
                    }
                    listaProcesoEnvio.add(listaFile);
                    mapaEnvio.put(row, listaFile);
                    listaFile = new ArrayList<>();
                }
            }
            pt.close();
            rs.close();
            cn.close();
        }
        return mapaEnvio;
    }

    public HashMap obtenerIntFile(String uniNeg, String ruc, Integer idprocesobanco, Integer sizeFile) throws Exception {
        List<Object> listaFile = new ArrayList<>();
        List<List<Object>> listaProcesoEnvio = new ArrayList<>();
        HashMap<Integer, List<Object>> mapaEnvio = new HashMap();
        SqlSessionFactory ssf = (SqlSessionFactory) SpringWebApplicationContext.getInstance().getBean("sqlSessionFactory");
        SqlSession sesion = ssf.openSession();
        Connection cn = sesion.getConnection();
        PreparedStatement pt = cn.prepareStatement("exec PRO_ENVIO_INT_FILE ?,?,?,?");
        pt.setString(1, uniNeg);
        pt.setString(2, ruc);
        pt.setInt(3, idprocesobanco);
        pt.setInt(4, sizeFile);
        if (pt.executeUpdate() != 0 && pt.executeUpdate() > 0) {
            ResultSet rs = pt.executeQuery();
            Integer row = 0;
            Integer ini = procesoFinalDAO.obtenerPosItemInt(uniNeg, ruc, 0, 2);
            Integer fin = procesoFinalDAO.obtenerPosItemInt(uniNeg, ruc, 1, 2);
            while (rs.next()) {
                row++;
                for (int i = ini; i <= fin; i++) {
                    listaFile.add(rs.getObject(i));
                }
                listaProcesoEnvio.add(listaFile);
                mapaEnvio.put(row, listaFile);
                listaFile = new ArrayList<>();
            }
            pt.close();
            rs.close();
            cn.close();
        }
        return mapaEnvio;
    }

    public Integer obtenerPosItem(String uniNeg, String ruc, Integer post, Integer flgProceso) throws Exception {
        return procesoFinalDAO.obtenerPosItem(uniNeg, ruc, post, flgProceso);
    }

    public Integer obtenerPosItemCab(String uniNeg, String ruc, Integer post, Integer flgProceso) throws Exception {
        return procesoFinalDAO.obtenerPosItemCab(uniNeg, ruc, post, flgProceso);
    }

    public Integer obtenerPosItemInt(String uniNeg, String ruc, Integer post, Integer flgProceso) throws Exception {
        return procesoFinalDAO.obtenerPosItemInt(uniNeg, ruc, post, flgProceso);
    }

    public Integer obtenerMaxIdFile(String uniNeg, String ruc) throws Exception {
        return procesoFinalDAO.obtenerMaxIdFile(uniNeg, ruc);
    }

    public Integer obtenerDisabledItem(String uniNeg, String ruc, Integer idProcesoBanco) throws Exception {
        return procesoFinalDAO.obtenerDisabledItem(uniNeg, ruc, idProcesoBanco);
    }

    @Transactional
    public void execProEnvioCab(String uniNeg, String ruc, Integer idProcesoBanco, Integer sizeFile) throws Exception {
        procesoFinalDAO.execProEnvioCab(uniNeg, ruc, idProcesoBanco, sizeFile);
    }

    @Transactional
    public void execEnvioCab(String uniNeg, String ruc, Integer idProcesoBanco, Integer sizeFile) throws Exception {
        SqlSessionFactory ssf = (SqlSessionFactory) SpringWebApplicationContext.getInstance().getBean("sqlSessionFactory");
        SqlSession sesion = ssf.openSession();
        Connection cn = sesion.getConnection();
        PreparedStatement pt = cn.prepareStatement(" exec PRO_ENVIO_CAB ? , ? , ? , ? ");
        pt.setString(1, uniNeg);
        pt.setString(2, ruc);
        pt.setInt(3, idProcesoBanco);
        pt.setInt(4, sizeFile);
        ResultSet rs = pt.executeQuery();
        pt.close();
        rs.close();
        cn.close();
    }

    public List<ProcesoFalloRepBean> obtenerListaFallo(ProcesoFalloRepBean procesoFalloRepBean) {
        return procesoFinalDAO.obtenerListaFallo(procesoFalloRepBean);
    }

    @Transactional
    public void execProErrores(String uniNeg, Integer idprocesobanco, String creaPor, Integer proceso) throws Exception {
        procesoFinalDAO.execProErrores(uniNeg, idprocesobanco, creaPor, proceso);
    }

    @Transactional
    public void execProEnvioCabIns(String uniNeg, String ruc, Integer idProcesoBanco, Integer posicion, String nomColumna, Integer constante, String valorCons, String creaPor) throws Exception {
        procesoFinalDAO.execProEnvioCabIns(uniNeg, ruc, idProcesoBanco, posicion, nomColumna, constante, valorCons, creaPor);
    }

    public void execProEnvioCabInsFile(String uniNeg, String ruc, Integer idProcesoBanco, Integer sizeFile) throws Exception {
        procesoFinalDAO.execProEnvioCabInsFile(uniNeg, ruc, idProcesoBanco, sizeFile);
    }

    public void execProEnvioInt(String uniNeg, String ruc, Integer sizeFile, Integer idProcesoBanco) throws Exception {
        procesoFinalDAO.execProEnvioInt(uniNeg, ruc, sizeFile, idProcesoBanco);
    }

    public void execProEnvioCabIntFile(String uniNeg, String ruc, Integer idProcesoBanco, Integer sizeFile) throws Exception {
        procesoFinalDAO.execProEnvioCabIntFile(uniNeg, ruc, idProcesoBanco, sizeFile);
    }

    @Transactional
    public void execProEnvioIntIns(String uniNeg, String ruc, Integer idProcesoBanco, Integer posicion, String nomColumna, Integer constante, String valorCons, String creaPor) throws Exception {
        procesoFinalDAO.execProEnvioIntIns(uniNeg, ruc, idProcesoBanco, posicion, nomColumna, constante, valorCons, creaPor);
    }

    @Transactional
    public void eliminarArchivoFile(String uniNeg, String ruc, Integer idProcesoBanco) throws Exception {
        procesoFinalDAO.eliminarArchivoFile(uniNeg, ruc, idProcesoBanco);
    }

    @Transactional
    public Object execProCtaCte(String uniNeg, String ruc, Integer idProcesoBanco, String modiPor) throws Exception {
        return procesoFinalDAO.execProCtaCte(uniNeg, ruc, idProcesoBanco, modiPor);
    }

    @Transactional
    public void eliminarFile(String uniNeg, String ruc, Integer res) throws Exception {
        procesoFinalDAO.eliminarFile(uniNeg, ruc, res);
    }

    @Transactional
    public void modificarEnvioFiltro(String uniNeg, String ruc, Integer idProcesoBanco, Integer defectoImporte) throws Exception {
        procesoFinalDAO.modificarEnvioFiltro(uniNeg, ruc, idProcesoBanco, defectoImporte);
    }

    public List<ReportePagoRepBean> obtenerTotalRepBanco(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception {
        return procesoFinalDAO.obtenerTotalRepBanco(cuentasPorCobrarBean);
    }

    public List<ReportePagoRepBean> obtenerTotalRepBancoDeuda(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception {
        return procesoFinalDAO.obtenerTotalRepBancoDeuda(cuentasPorCobrarBean);
    }

    @Transactional
    public Object execProGenRepPago(String uniNeg, Date fecIni, Date fecFin, Integer idTipoRepPago) throws Exception {
        return procesoFinalDAO.execProGenRepPago(uniNeg, fecIni, fecFin, idTipoRepPago);
    }

    public List<PagosUniNegRepBean> obtenerRepPago(PagosUniNegRepBean pagosUniNegRepBean) throws Exception {
        return procesoFinalDAO.obtenerRepPago(pagosUniNegRepBean);
    }

    public List<NotaAbonoRepBean> obtenerRepNotaAbono(ProcesoBancoBean procesoBancoBean) throws Exception {
        return procesoFinalDAO.obtenerRepNotaAbono(procesoBancoBean);
    }

    /* CONCILIA BANCO */
    public List<ConciliaBancoRepBean> obtenerListaDetConcilia(ConciliaBancoRepBean conciliaBancoRepBean) throws Exception {
        return procesoFinalDAO.obtenerListaDetConcilia(conciliaBancoRepBean);
    }

    /* PROCEDURE PRO_CTA_CTE */
    @Transactional
    public Object execProCtaCte1(String uniNeg, String ruc, Integer idProcesoBanco, String modiPor) throws Exception {
        return procesoFinalDAO.execProCtaCte1(uniNeg, ruc, idProcesoBanco, modiPor);
    }

    @Transactional
    public Object execProCtaCte2(String uniNeg, String ruc, Integer idProcesoBanco, String modiPor) throws Exception {
        return procesoFinalDAO.execProCtaCte2(uniNeg, ruc, idProcesoBanco, modiPor);
    }

    public List<ConciliaBancoRepBean> obtenerListaDetConciliaFallo(ConciliaBancoRepBean conciliaBancoRepBean) throws Exception {
        return procesoFinalDAO.obtenerListaDetConciliaFallo(conciliaBancoRepBean);
    }

    @Transactional
    public Object execProError(String uniNeg, String ruc, Integer idProcesoBanco, String modiPor) throws Exception {
        return procesoFinalDAO.execProError(uniNeg, ruc, idProcesoBanco, modiPor);
    }

    public ProcesoFinalDAO getProcesoFinalDAO() {
        return procesoFinalDAO;
    }

    public void setProcesoFinalDAO(ProcesoFinalDAO procesoFinalDAO) {
        this.procesoFinalDAO = procesoFinalDAO;
    }

}
