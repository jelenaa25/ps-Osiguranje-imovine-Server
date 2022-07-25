/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.kontroler;

import domen.AgentOsiguranja;
import domen.Klijent;
import domen.Mesto;
import domen.OpstiDomenskiObjekat;
import domen.Pokrice;
import domen.Polisa;
import domen.PredmetOsiguranja;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;
import java.util.Properties;
import komunikacija.Konstanta;
import server.broker.Broker;
import server.forme.KonfiguracijaBaze;
import server.forme.ServerForma;
import server.konstante.Konstante;
import server.model.ModelTabeleAgenata;
import server.niti.ServerNit;
import server.so.OpstaSO;
import server.so.impl.NadjiKlijente;
import server.so.impl.NadjiPokricaSO;
import server.so.impl.ObrisiPolisuSO;
import server.so.impl.PrijaviSeSO;
import server.so.impl.PromeniPokriceSO;
import server.so.impl.UcitajListuKlijenata;
import server.so.impl.UcitajListuMestaSO;
import server.so.impl.UcitajListuPokricaSO;
import server.so.impl.UcitajListuPolisaSO;
import server.so.impl.UcitajListuPredmetaOsiguranjaSO;
import server.so.impl.UcitajPokriceSO;
import server.so.impl.UcitajPolisuSO;
import server.so.impl.ZapamtiKlijentaSO;
import server.so.impl.ZapamtiPokriceSO;
import server.so.impl.ZapamtiPolisuSO;
import server.so.impl.ZapamtiPredmetOsiguranjaSO;

/**
 *
 * @author Korisnik
 */
public class Kontroler {

    private static Kontroler instanca;
    private final Broker broker;
    private ServerNit serverNit;
    private ServerForma serverForm;

    private Kontroler() {
        broker = new Broker();

    }

    public static Kontroler getInstanca() {
        if (instanca == null) {
            instanca = new Kontroler();
        }
        return instanca;
    }

    public void setServerNit(ServerNit serverNit) {
        this.serverNit = serverNit;
    }

    public void setServerForm(ServerForma serverForm) {
        this.serverForm = serverForm;
    }

    public void pokreniServer() throws IOException, Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream(Konstante.SERVER_CONFIG_PATH));
        int port = 0;
        if (!"".equals(properties.getProperty("port"))) {
            port = Integer.parseInt(properties.getProperty("port"));
            //Konstanta.getInstanca().setPORT(port);
            //System.out.println("PORT: "+Konstanta.getInstanca().getPORT());
        } else {
            throw new Exception();
        }
        ServerSocket serverSocket = new ServerSocket(port);
        serverNit = new ServerNit(serverSocket);
        serverNit.start();
        serverForm.getBtnPokreni().setEnabled(false);
        serverForm.getBtnZaustavi().setEnabled(true);
        serverForm.getLblStatus().setText("Server je pokrenut");
        serverForm.getLblStatus().setForeground(Color.GREEN);

    }

    public void zaustaviServer() throws IOException {
        serverNit.zaustavi();
        serverForm.getBtnPokreni().setEnabled(true);
        serverForm.getBtnZaustavi().setEnabled(false);
        serverForm.getLblStatus().setText("Server je zaustavljen");
        serverForm.getLblStatus().setForeground(Color.RED);
    }

    public void obrisiPolisu(Polisa polisa) throws Exception {
        OpstaSO so = new ObrisiPolisuSO(broker, polisa);
        so.opsteIzvrsenjeSo();

    }

    public AgentOsiguranja prijaviSe(AgentOsiguranja ag) throws Exception {
        OpstaSO so = new PrijaviSeSO(broker, ag);
        so.opsteIzvrsenjeSo();
        return (AgentOsiguranja) so.getOdo();

    }

    public List<OpstiDomenskiObjekat> vratiSvaPokrica() throws Exception {
        OpstaSO so = new UcitajListuPokricaSO(broker, new Pokrice());
        so.opsteIzvrsenjeSo();
        return so.getLista();
    }

    public List<OpstiDomenskiObjekat> vratiSvePolise() throws Exception {
        OpstaSO so = new UcitajListuPolisaSO(broker, new Polisa());
        so.opsteIzvrsenjeSo();
        return so.getLista();

    }

    public List<OpstiDomenskiObjekat> vratiSvePredmete() throws Exception {
        OpstaSO so = new UcitajListuPredmetaOsiguranjaSO(broker, new PredmetOsiguranja());
        so.opsteIzvrsenjeSo();
        return so.getLista();
    }

    public void zapamtiPokrice(Pokrice pokrice) throws Exception {
        OpstaSO so = new ZapamtiPokriceSO(broker, pokrice);
        so.opsteIzvrsenjeSo();
    }

    public void zapamtiPolisu(Polisa polisa) throws Exception {
        OpstaSO so = new ZapamtiPolisuSO(broker, polisa);
        so.opsteIzvrsenjeSo();
    }

    public void zapamtiPredmet(PredmetOsiguranja p) throws Exception {
        OpstaSO so = new ZapamtiPredmetOsiguranjaSO(broker, p);
        so.opsteIzvrsenjeSo();
    }

    public Polisa ucitajPolisu(Polisa polisa) throws Exception {
        OpstaSO so = new UcitajPolisuSO(broker, polisa);
        so.opsteIzvrsenjeSo();
        return (Polisa) so.getOdo();
    }

    public List<OpstiDomenskiObjekat> nadjiPokricaPoZadatojVr(String kl) throws Exception {
        Pokrice pokrice = new Pokrice();
        pokrice.setNaziv(kl);
        OpstaSO so = new NadjiPokricaSO(broker, pokrice);
        so.opsteIzvrsenjeSo();
        return (List<OpstiDomenskiObjekat>) so.getLista();
    }

    public Pokrice ucitajPokrice(Pokrice pok) throws Exception {

        OpstaSO so = new UcitajPokriceSO(broker, pok);
        so.opsteIzvrsenjeSo();
        return (Pokrice) so.getOdo();
    }

    public void promeniPokrice(Pokrice pokrice) throws Exception {
        OpstaSO so = new PromeniPokriceSO(broker, pokrice);
        so.opsteIzvrsenjeSo();
    }

    public void izbrisiKorisnikaIzTabele(AgentOsiguranja trenutAgent) {
        ModelTabeleAgenata mm = (ModelTabeleAgenata) serverForm.getjTable1().getModel();
        mm.obrisi(trenutAgent);
    }

    public void dodajUTabelu(AgentOsiguranja trenutniAgent) throws Exception {
        ModelTabeleAgenata mm = (ModelTabeleAgenata) serverForm.getjTable1().getModel();
        if (!mm.postoji(trenutniAgent)) {
            mm.dodaj(trenutniAgent);
        } else {
            throw new Exception("Vec ste ulogovani...");
        }
    }

    public void konfigurisiBazu(String url, String username, String password) throws FileNotFoundException, IOException {
        Properties properties = new Properties();
        properties.setProperty("url", url);
        properties.setProperty("username", username);
        properties.setProperty("password", password);
        properties.store(new FileOutputStream(Konstante.DB_CONFIG_PATH), "");
    }

    public void procitajKonfiguracijuBaze(KonfiguracijaBaze aThis) throws FileNotFoundException, IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(Konstante.DB_CONFIG_PATH));
        
        aThis.getTxtURL().setText(properties.getProperty("url"));
        aThis.getTxtUsername().setText(properties.getProperty("username"));
        aThis.getTxtPassword().setText(properties.getProperty("password"));
    }

    public void konfigurisiServer(String port) throws FileNotFoundException, IOException {
        Properties properties = new Properties();
        properties.setProperty("port", port);
        properties.store(new FileOutputStream(Konstante.SERVER_CONFIG_PATH), port);
        //Konstanta.getInstanca().setPORT(Integer.parseInt(port));
    }

   /* public void procitajKonfiguracijuServera(Konfiguracija aThis) throws FileNotFoundException, IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(Konstante.SERVER_CONFIG_PATH));
        if (properties.getProperty("port") == null) {
            properties.setProperty("port", Konstanta.getInstanca().getDEFAULT_PORT()+"");
            properties.store(new FileOutputStream(Konstante.SERVER_CONFIG_PATH), Konstanta.getInstanca().getDEFAULT_PORT()+"");
        }
        aThis.getTxtPort().setText(properties.getProperty("port"));
        Konstanta.getInstanca().setPORT(Integer.parseInt(properties.getProperty("port")));
    }*/

    public List<OpstiDomenskiObjekat> vratiSveKlijente() throws Exception {
        OpstaSO so = new UcitajListuKlijenata(broker, new Klijent());
        so.opsteIzvrsenjeSo();
        return so.getLista();
    }

    public List<OpstiDomenskiObjekat> vratiKlijentaPoZadatojVr(String kl1) throws Exception {
        Klijent k = new Klijent();
        k.setImePrezime(kl1);
        System.out.println(k.getImePrezime());
        OpstaSO so = new NadjiKlijente(broker, k);
        so.opsteIzvrsenjeSo();
        return so.getLista();
    }

    public List<OpstiDomenskiObjekat> vratiMesta() throws Exception {
        OpstaSO so = new UcitajListuMestaSO(broker, new Mesto());
        so.opsteIzvrsenjeSo();
        return so.getLista();
    }

    public void zapamtiKlijenta(Klijent p) throws Exception {
        OpstaSO so = new ZapamtiKlijentaSO(broker, p);
        so.opsteIzvrsenjeSo();
    }

}
