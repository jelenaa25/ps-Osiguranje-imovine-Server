/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.so;

import domen.OpstiDomenskiObjekat;
import java.util.ArrayList;
import java.util.List;
import server.broker.Broker;

/**
 *
 * @author Korisnik
 */
public abstract class OpstaSO {

    protected Broker broker;
    protected OpstiDomenskiObjekat odo;
    protected List<OpstiDomenskiObjekat> lista;

    public OpstaSO(Broker broker, OpstiDomenskiObjekat odo) {
        this.broker = broker;
        this.odo = odo;
        lista = new ArrayList<>();
    }

    public void opsteIzvrsenjeSo() throws Exception {

        broker.uspostaviKonekciju();
        try {
            proveriPreduslove();
            izvrsiOperaciju();
            broker.potvrdiTransakciju();

        } catch (Exception ex) {
            broker.ponistiTransakciju();
            ex.printStackTrace();
            throw ex;
        } finally {
            broker.raskiniKonekciju();
        }

    }

    protected abstract void izvrsiOperaciju() throws Exception;

    protected abstract void proveriPreduslove() throws Exception;

    public List<OpstiDomenskiObjekat> getLista() {
        return lista;
    }

    public OpstiDomenskiObjekat getOdo() {
        return odo;
    }

    
}
