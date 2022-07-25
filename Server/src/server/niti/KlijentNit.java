/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.niti;

import domen.AgentOsiguranja;
import domen.Klijent;
import domen.OpstiDomenskiObjekat;
import domen.Pokrice;
import domen.Polisa;
import domen.PredmetOsiguranja;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import komunikacija.Odgovor;
import komunikacija.Primalac;
import komunikacija.Zahtev;
import komunikacija.Operacije;
import komunikacija.Posiljalac;
import komunikacija.TipOdgovora;
import server.kontroler.Kontroler;

/**
 *
 * @author Korisnik
 */
public class KlijentNit extends Thread {

    Socket socket;
    ServerNit serverskaNit;
    AgentOsiguranja trenutniAgent;

    public KlijentNit(Socket socket, ServerNit serverskaNit) {
        this.socket = socket;
        this.serverskaNit = serverskaNit;
    }

    public AgentOsiguranja getTrenutAgent() {
        return trenutniAgent;
    }

    @Override
    public void run() {
        while (!socket.isClosed()) {
            try {
                Zahtev zahtev = (Zahtev) new Primalac(socket).primi();
                obradiZahtev(zahtev);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        serverskaNit.odjavi(this);

    }

    void zaustavi() {
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(KlijentNit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void obradiZahtev(Zahtev zahtev) throws Exception {
        Odgovor odgovor = new Odgovor();
        switch (zahtev.getOperacija()) {
            case Operacije.NADJI_POLISE:
                odgovor = nadjiPolise(zahtev);
                break;
            case Operacije.OBRISI_POLISU:
                odgovor = obrisiPolisu(zahtev);
                break;
            case Operacije.PRIJAVA:
                odgovor = prijaviSe(zahtev);
                break;
            case Operacije.UCITAJ_POKRICA:
                odgovor = ucitajPokrica(zahtev);
                break;
            case Operacije.VRATI_POLISE:
                odgovor = vratiSvePolise(zahtev);
                break;
            case Operacije.UCITAJ_PREDMETE:
                odgovor = ucitajPredmete(zahtev);
                break;
            case Operacije.ZAPAMTI_POKRICE:
                odgovor = zapamtiPokrice(zahtev);
                break;
            case Operacije.ZAPAMTI_POLISU:
                odgovor = zapamtiPolisu(zahtev);
                break;
            case Operacije.ZAPAMTI_PREDMET:
                odgovor = zapamtiPredmet(zahtev);
                break;
            case Operacije.UCITAJ_POLISU:
                odgovor = ucitajPolisu(zahtev);
                break;
            case Operacije.NADJI_POKRICA:
                odgovor = nadjiPokrica(zahtev);
                break;
            case Operacije.UCITAJ_POKRICE:
                odgovor = ucitajPokrice(zahtev);
                break;
            case Operacije.PROMENI_POKRICE:
                odgovor = promeniPokrice(zahtev);
                break;
            case Operacije.UCITAJ_KLIJENTE:
                odgovor = vratiKlijente(zahtev);
                break;
            case Operacije.UCITAJ_MESTA:
                odgovor = vratiMesta(zahtev);
                break;
            case Operacije.ZAPAMTI_KLIJENTA:
                odgovor = zapamtiKlijenta(zahtev);
                break;
            default:
                break;

        }
        new Posiljalac(socket).posalji(odgovor);
    }

    private Odgovor obrisiPolisu(Zahtev zahtev) {

        Polisa polisa = (Polisa) zahtev.getArgument();
        Odgovor odgovor = new Odgovor();

        try {
            Kontroler.getInstanca().obrisiPolisu(polisa);
            odgovor.setTipOdgovora(TipOdgovora.USPEH);
        } catch (Exception ex) {

            odgovor.setTipOdgovora(TipOdgovora.GREŠKA);
            odgovor.setException(ex);
        }
        return odgovor;
    }

    private Odgovor prijaviSe(Zahtev zahtev) {
        AgentOsiguranja ag = (AgentOsiguranja) zahtev.getArgument();

        Odgovor odgovor = new Odgovor();

        try {
            AgentOsiguranja agentOsiguranja = Kontroler.getInstanca().prijaviSe(ag);
            trenutniAgent = agentOsiguranja;
            Kontroler.getInstanca().dodajUTabelu(trenutniAgent);
            odgovor.setTipOdgovora(TipOdgovora.USPEH);
            odgovor.setRezultat(agentOsiguranja);
        } catch (Exception ex) {

            odgovor.setTipOdgovora(TipOdgovora.GREŠKA);
            odgovor.setException(ex);
        }
        return odgovor;
    }

    private Odgovor ucitajPokrica(Zahtev zahtev) {
        Odgovor odgovor = new Odgovor();

        try {
            List<OpstiDomenskiObjekat> pokrica = Kontroler.getInstanca().vratiSvaPokrica();
            odgovor.setTipOdgovora(TipOdgovora.USPEH);
            odgovor.setRezultat(pokrica);
        } catch (Exception ex) {

            odgovor.setTipOdgovora(TipOdgovora.GREŠKA);
            odgovor.setException(ex);
        }
        return odgovor;
    }

    private Odgovor vratiSvePolise(Zahtev zahtev) {
        Odgovor odgovor = new Odgovor();
        try {
            List<OpstiDomenskiObjekat> polise = Kontroler.getInstanca().vratiSvePolise();
            odgovor.setTipOdgovora(TipOdgovora.USPEH);
            odgovor.setRezultat(polise);
        } catch (Exception ex) {

            odgovor.setTipOdgovora(TipOdgovora.GREŠKA);
            odgovor.setException(ex);
        }
        return odgovor;
    }

    private Odgovor ucitajPredmete(Zahtev zahtev) {
        Odgovor odgovor = new Odgovor();

        try {
            List<OpstiDomenskiObjekat> pr = Kontroler.getInstanca().vratiSvePredmete();
            odgovor.setTipOdgovora(TipOdgovora.USPEH);
            odgovor.setRezultat(pr);
        } catch (Exception ex) {

            odgovor.setTipOdgovora(TipOdgovora.GREŠKA);
            odgovor.setException(ex);
        }
        return odgovor;
    }

    private Odgovor zapamtiPokrice(Zahtev zahtev) {
        Odgovor odgovor = new Odgovor();
        Pokrice pokrice = (Pokrice) zahtev.getArgument();
        try {
            Kontroler.getInstanca().zapamtiPokrice(pokrice);
            odgovor.setTipOdgovora(TipOdgovora.USPEH);

        } catch (Exception ex) {

            odgovor.setTipOdgovora(TipOdgovora.GREŠKA);
            odgovor.setException(ex);
        }
        return odgovor;

    }

    private Odgovor zapamtiPolisu(Zahtev zahtev) {
        Odgovor odgovor = new Odgovor();
        Polisa polisa = (Polisa) zahtev.getArgument();
        try {
            Kontroler.getInstanca().zapamtiPolisu(polisa);
            odgovor.setTipOdgovora(TipOdgovora.USPEH);

        } catch (Exception ex) {

            odgovor.setTipOdgovora(TipOdgovora.GREŠKA);
            odgovor.setException(ex);
        }
        return odgovor;
    }

    private Odgovor zapamtiPredmet(Zahtev zahtev) {
        Odgovor odgovor = new Odgovor();
        PredmetOsiguranja p = (PredmetOsiguranja) zahtev.getArgument();
        try {
            Kontroler.getInstanca().zapamtiPredmet(p);
            odgovor.setTipOdgovora(TipOdgovora.USPEH);

        } catch (Exception ex) {

            odgovor.setTipOdgovora(TipOdgovora.GREŠKA);
            odgovor.setException(ex);
        }
        return odgovor;
    }

    private Odgovor nadjiPolise(Zahtev zahtev) {
        
        Odgovor odgovor = new Odgovor();
        Klijent kl = (Klijent) zahtev.getArgument();
        String kl1 = kl.getImePrezime();
        System.out.println("kl1: "+kl1);
        List<OpstiDomenskiObjekat> vrati = new ArrayList<>();
        try {
            List<OpstiDomenskiObjekat> klijenti = Kontroler.getInstanca().vratiKlijentaPoZadatojVr(kl1); //kl sa uslovom
            System.out.println("SIZE = "+klijenti.size());
            List<OpstiDomenskiObjekat> svePolise = Kontroler.getInstanca().vratiSvePolise();
            for(OpstiDomenskiObjekat o: svePolise){ //kroz sve polise
                for(OpstiDomenskiObjekat o1: klijenti){ //kroz svakog kl 
                    Polisa p = (Polisa) o;
                    Klijent klij = (Klijent) o1;
                    if(p.getKlijent().getId() == klij.getId()){
                        //odgovarajuca polisa
                        vrati.add((Polisa) o);
                    }
                }
            }
            //List<OpstiDomenskiObjekat> polise = Kontroler.getInstanca().nadjiPolisePoZadatojVr(kl);
            
            if (!vrati.isEmpty()) {
                System.out.println("SVE LISTE: "+svePolise.size());
                odgovor.setTipOdgovora(TipOdgovora.USPEH);
                odgovor.setRezultat(vrati);
            } else {
                throw new Exception();
            }
        } catch (Exception ex) {

            odgovor.setTipOdgovora(TipOdgovora.GREŠKA);
            odgovor.setException(ex);
        }
        return odgovor;
    }

    private Odgovor ucitajPolisu(Zahtev zahtev) {
        Odgovor odgovor = new Odgovor();
        Polisa polisa = (Polisa) zahtev.getArgument();
        try {
            Polisa polisa1 = Kontroler.getInstanca().ucitajPolisu(polisa);
            odgovor.setTipOdgovora(TipOdgovora.USPEH);
            odgovor.setRezultat(polisa1);
        } catch (Exception ex) {

            odgovor.setTipOdgovora(TipOdgovora.GREŠKA);
            odgovor.setException(ex);
        }
        return odgovor;
    }

    private Odgovor nadjiPokrica(Zahtev zahtev) {
        Odgovor odgovor = new Odgovor();
        String kl = (String) zahtev.getArgument();
        try {
            List<OpstiDomenskiObjekat> pokrica = Kontroler.getInstanca().nadjiPokricaPoZadatojVr(kl);
            if (pokrica.isEmpty()) {
                throw new Exception();
            }
            odgovor.setTipOdgovora(TipOdgovora.USPEH);
            odgovor.setRezultat(pokrica);
        } catch (Exception ex) {

            odgovor.setTipOdgovora(TipOdgovora.GREŠKA);
            odgovor.setException(ex);
            
        }
        return odgovor;
    }

    private Odgovor ucitajPokrice(Zahtev zahtev) {
        Odgovor odgovor = new Odgovor();
        Pokrice pokrice = (Pokrice) zahtev.getArgument();
        try {
            Pokrice pokrice1 = Kontroler.getInstanca().ucitajPokrice(pokrice);
            odgovor.setTipOdgovora(TipOdgovora.USPEH);
            odgovor.setRezultat(pokrice1);
        } catch (Exception ex) {

            odgovor.setTipOdgovora(TipOdgovora.GREŠKA);
            odgovor.setException(ex);
        }
        return odgovor;

    }

    private Odgovor promeniPokrice(Zahtev zahtev) {
        Odgovor odgovor = new Odgovor();
        Pokrice pokrice = (Pokrice) zahtev.getArgument();
        try {
            Kontroler.getInstanca().promeniPokrice(pokrice);
            odgovor.setTipOdgovora(TipOdgovora.USPEH);
            odgovor.setRezultat(pokrice);
        } catch (Exception ex) {

            odgovor.setTipOdgovora(TipOdgovora.GREŠKA);
            odgovor.setException(ex);
        }
        return odgovor;
    }

    private Odgovor vratiKlijente(Zahtev zahtev) {
        Odgovor odgovor = new Odgovor();

        try {
            List<OpstiDomenskiObjekat> pr = Kontroler.getInstanca().vratiSveKlijente();
            odgovor.setTipOdgovora(TipOdgovora.USPEH);
            odgovor.setRezultat(pr);
        } catch (Exception ex) {

            odgovor.setTipOdgovora(TipOdgovora.GREŠKA);
            odgovor.setException(ex);
        }
        return odgovor;
    }

    private Odgovor vratiMesta(Zahtev zahtev) {
        Odgovor odgovor = new Odgovor();

        try {
            List<OpstiDomenskiObjekat> pr = Kontroler.getInstanca().vratiMesta();
            if(!pr.isEmpty()){
            odgovor.setTipOdgovora(TipOdgovora.USPEH);
            odgovor.setRezultat(pr);
            }else throw new Exception();
        } catch (Exception ex) {

            odgovor.setTipOdgovora(TipOdgovora.GREŠKA);
            odgovor.setException(ex);
        }
        return odgovor;
    }

    private Odgovor zapamtiKlijenta(Zahtev zahtev) {
        Odgovor odgovor = new Odgovor();
        Klijent p = (Klijent) zahtev.getArgument();
        try {
            Kontroler.getInstanca().zapamtiKlijenta(p);
            odgovor.setTipOdgovora(TipOdgovora.USPEH);

        } catch (Exception ex) {

            odgovor.setTipOdgovora(TipOdgovora.GREŠKA);
            odgovor.setException(ex);
        }
        return odgovor;
    }
}
