/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.broker;

import domen.OpstiDomenskiObjekat;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Korisnik
 */
public class Broker {

    private Connection konekcija;

    public void uspostaviKonekciju() throws Exception {
        DbConnectionFactory.getInstanca().getKonekcija();
    }

    public void potvrdiTransakciju() throws Exception {
        DbConnectionFactory.getInstanca().getKonekcija().commit();
    }

    public void ponistiTransakciju() throws Exception {
        DbConnectionFactory.getInstanca().getKonekcija().rollback();
    }

    public void raskiniKonekciju() throws Exception {
        DbConnectionFactory.getInstanca().getKonekcija().close();
    }

    public List<OpstiDomenskiObjekat> pronadjiSlogove(OpstiDomenskiObjekat odo) throws IOException, SQLException {
        List<OpstiDomenskiObjekat> lista = new ArrayList<>();
        try {
            String upit = "SELECT * FROM " + odo.vratiImeKlase() + " WHERE " + odo.vratiUslovZaNadjiSlogove();
            System.out.println(upit);
            konekcija = DbConnectionFactory.getInstanca().getKonekcija();
            Statement statement = konekcija.createStatement();
            ResultSet rs = statement.executeQuery(upit);
            while (rs.next()) {
                OpstiDomenskiObjekat odo1 = odo.kreirajInstancu();
                odo1.napuni(rs);
                lista.add(odo1);
            }
            

        } catch (SQLException ex) {
            throw ex;
        }
        return lista;
    }

    public void obrisiSlog(OpstiDomenskiObjekat odo) throws IOException, SQLException{
        try {
            String upit = "DELETE FROM " + odo.vratiImeKlase() + " WHERE " + odo.vratiUslovZaNadjiSlog();
            //System.out.println(upit);
            konekcija = DbConnectionFactory.getInstanca().getKonekcija();
            Statement statement = konekcija.createStatement();
            statement.executeUpdate(upit);
        } catch (SQLException ex) {
            throw ex;
        }
    }
    /*public void obrisiSlogSlozeni(OpstiDomenskiObjekat odo) throws IOException, SQLException {
        OpstiDomenskiObjekat slab = odo.vratiSlabObjekat();
        //slab.postaviSlabomObjIDNadklase(odo.getID());
        System.out.println(slab.getID());
        System.out.println(odo.getID());
        ArrayList<OpstiDomenskiObjekat> slabiObj = (ArrayList<OpstiDomenskiObjekat>) pronadjiSlogove(slab);
        for (OpstiDomenskiObjekat o : slabiObj) {
            System.out.println(o);
            obrisiSlog(o);
        }
        obrisiSlog(odo);
       
    }*/

    public List<OpstiDomenskiObjekat> vratiSve(OpstiDomenskiObjekat odo) throws IOException, SQLException {
        List<OpstiDomenskiObjekat> lista = new ArrayList<>();
        try {
            String upit = "SELECT * FROM " + odo.vratiImeKlase();
            konekcija = DbConnectionFactory.getInstanca().getKonekcija();
            Statement statement = konekcija.createStatement();
            ResultSet rs = statement.executeQuery(upit);
            while (rs.next()) {
                OpstiDomenskiObjekat odoo = odo.kreirajInstancu();
                odoo.napuni(rs);
                lista.add(odoo);
            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            throw ex;
        }
        return lista;
    }

    public OpstiDomenskiObjekat pronadjiSlog(OpstiDomenskiObjekat odo) throws IOException, Exception {
                try {
            String upit = "SELECT * FROM " + odo.vratiImeKlase() + " WHERE " + odo.vratiUslovZaNadjiSlog();
            konekcija = DbConnectionFactory.getInstanca().getKonekcija();
            Statement statement = konekcija.createStatement();
            ResultSet rs = statement.executeQuery(upit);
            if (rs.next()) {
                odo.napuni(rs);
                return odo;
            } else {
               // System.out.println(upit);
                throw new Exception("Slog nije pronadjen\n");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    

    public void zapamtiSlog(OpstiDomenskiObjekat odo) throws SQLException, IOException {
        String upit;
        try {
            upit = "INSERT INTO " + odo.vratiImeKlase()
                    + " VALUES (" + odo.vratiVrAtributa() + ")";
            
            konekcija = DbConnectionFactory.getInstanca().getKonekcija();
            Statement statement = konekcija.createStatement();
            statement.executeUpdate(upit, Statement.RETURN_GENERATED_KEYS);
            ResultSet rsKey = statement.getGeneratedKeys();
            if (rsKey.next()) {
                int id = rsKey.getInt(1);
                odo.setId(id);
            }
            statement.close(); 
        } catch (SQLException ex) {
            throw ex;
        }
    }
  /*  public void zapamtiSlogSlozeni(OpstiDomenskiObjekat odo) throws SQLException, IOException{
        zapamtiSlog(odo);
        ArrayList<OpstiDomenskiObjekat> slabi = (ArrayList<OpstiDomenskiObjekat>) odo.slabiObjekti();
        for (OpstiDomenskiObjekat opstiDomenskiObjekat : slabi) {
            zapamtiSlog(opstiDomenskiObjekat);
        }
        
    }*/
    
    public void promeniSlog(OpstiDomenskiObjekat odo) throws IOException, Exception{
         try {
            String upit = "UPDATE " + odo.vratiImeKlase() + " SET " + odo.postaviVrAtributa()+ " WHERE " + odo.vratiUslovZaNadjiSlog();
             //System.out.println(upit);
            konekcija = DbConnectionFactory.getInstanca().getKonekcija();
            Statement statement = konekcija.createStatement();
            if (statement.executeUpdate(upit) == 0) {
                throw new Exception();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

}
