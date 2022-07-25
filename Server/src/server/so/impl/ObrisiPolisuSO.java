/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.so.impl;

import domen.OpstiDomenskiObjekat;
import domen.Polisa;
import java.util.ArrayList;
import server.broker.Broker;
import server.so.OpstaSO;

/**
 *
 * @author Korisnik
 */
public class ObrisiPolisuSO extends OpstaSO{

    public ObrisiPolisuSO(Broker broker, OpstiDomenskiObjekat odo) {
        super(broker, odo);
    }

    @Override
    protected void izvrsiOperaciju() throws Exception {
        
        OpstiDomenskiObjekat slab = odo.vratiSlabObjekat();

        ArrayList<OpstiDomenskiObjekat> slabiObj = (ArrayList<OpstiDomenskiObjekat>) broker.pronadjiSlogove(slab);
        for (OpstiDomenskiObjekat o : slabiObj) {
            System.out.println(o);
            broker.obrisiSlog(o);
        }
        broker.obrisiSlog(odo);
    }

    @Override
    protected void proveriPreduslove() throws Exception {
        if(!(odo instanceof Polisa)){
            throw new Exception("Invalid param...");
        }
    }
    
}
