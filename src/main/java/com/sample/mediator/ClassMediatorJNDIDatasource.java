package com.sample.mediator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.synapse.MessageContext;
import org.apache.synapse.mediators.AbstractMediator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ClassMediatorJNDIDatasource extends AbstractMediator {

    private Object jndiObj;
    private static final Log log = LogFactory.getLog(ClassMediatorJNDIDatasource.class);

    public ClassMediatorJNDIDatasource() {
        try {
            // initialize datasource context with JNDI lookup
            // FIXME: change the JNDI Datasource name accordingly
            jndiObj = new InitialContext().lookup("jdbc/WSO2ExternalDB");

        } catch (NamingException e) {
            log.error("Error while getting datasource configurations", e);
        }
    }

    @Override
    public boolean mediate(MessageContext synCtx) {
        // sql connection variables
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            DataSource externalDatasource = (DataSource) jndiObj;
            conn = externalDatasource.getConnection();
            stmt = conn.createStatement();

            // given below is a sample block on using the datasource connection to connect
            // with the external datasource
            // rs = stmt.executeQuery("SELECT version()");
            // if (rs.next()) {
            // String version = rs.getString(1);
            // synCtx.setProperty("DatabaseResult", version);
            // log.info("Database Version : " + version);
            // }

            // TODO: implement mediation logic

        } catch (SQLException e) {
            log.error("Error while accessing DB connection", e);
        } finally {
            // close all the datasource connections
            closeConnection(conn);
            closeStatement(stmt);
            closeResultSet(rs);
        }

        return true;
    }

    // #region misc datasource implementations

    private void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                log.error("Error while closing the connection", e);
            }
        }

    }

    private void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                log.error("Error while closing the statement", e);
            }
        }

    }

    private void closeResultSet(ResultSet reset) {
        if (reset != null) {
            try {
                reset.close();
            } catch (SQLException e) {
                log.error("Error while closing the result set", e);
            }
        }
    }

    // #endregion
}
