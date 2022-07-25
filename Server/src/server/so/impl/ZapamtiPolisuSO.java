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
public class ZapamtiPolisuSO extends OpstaSO{

    public ZapamtiPolisuSO(Broker broker, OpstiDomenskiObjekat odo) {
        super(broker, odo);
    }

    @Override
    protected void izvrsiOperaciju() throws Exception {
        broker.zapamtiSlog(odo);
        ArrayList<OpstiDomenskiObjekat> stavke = (ArrayList<OpstiDomenskiObjekat>) odo.slabiObjekti();
        if(!stavke.isEmpty()){
            for(OpstiDomenskiObjekat oo: stavke){
                OpstiDomenskiObjekat o1 = oo.vratiInstancuNadKlase();
                o1.setId(odo.getID());
                broker.zapamtiSlog(oo);
            }
        }
    }

    @Override
    protected void proveriPreduslove() throws Exception {
        if(!(odo instanceof Polisa)){
            throw new Exception("Invalid param...");
        }
    }
    
}
