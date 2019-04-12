/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.util;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.log4j.jdbc.JDBCAppender;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.factory.BeanFactory;

/**
 *
 * @author Administrador
 */
public class GLTLogBD extends JDBCAppender{
     @Override
    protected Connection getConnection() throws SQLException {
        DriverManagerDataSource conexion = BeanFactory.getDriverManagerDataSource();
        return conexion.getConnection();
    }

    @Override
    @Transactional
    protected void execute(String sql) throws SQLException {
        super.execute(sql);
    }
}
