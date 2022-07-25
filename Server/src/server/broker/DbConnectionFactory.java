/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.broker;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import server.konstante.Konstante;

/**
 *
 * @author Korisnik
 */
public class DbConnectionFactory {
    
    private Connection konekcija;
    private static DbConnectionFactory instanca;

    private DbConnectionFactory() {
    }

    public static DbConnectionFactory getInstanca() {
        if (instanca == null) {
            instanca = new DbConnectionFactory();
        }
        return instanca;
    }

    public Connection getKonekcija() throws SQLException, IOException {

        if (konekcija == null || konekcija.isClosed()) {
            try {
               
                Properties properties = new Properties();
                properties.load(new FileInputStream(Konstante.DB_CONFIG_PATH));
                String url = properties.getProperty(Konstante.DB_CONFIG_URL);
                String user = properties.getProperty(Konstante.DB_CONFIG_USERNAME);
                String password = properties.getProperty(Konstante.DB_CONFIG_PASSWORD);
                
               
                konekcija = DriverManager.getConnection(url, user, password);
                konekcija.setAutoCommit(false);
            } catch (SQLException ex) {
                System.out.println("Neuspesno uspostavljanje konekcije!\n" + ex.getMessage());
                throw ex;
            }
        }
        return konekcija;
    }
    
}
