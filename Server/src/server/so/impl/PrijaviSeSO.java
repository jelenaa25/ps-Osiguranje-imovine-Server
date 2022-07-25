/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.so.impl;

import domen.AgentOsiguranja;
import domen.OpstiDomenskiObjekat;
import server.broker.Broker;
import server.so.OpstaSO;

/**
 *
 * @author Korisnik
 */
public class PrijaviSeSO extends OpstaSO{

    public PrijaviSeSO(Broker broker, OpstiDomenskiObjekat odo) {
        super(broker, odo);
    }

    @Override
    protected void izvrsiOperaciju() throws Exception {
        odo = broker.pronadjiSlog(odo);
    }

    @Override
    protected void proveriPreduslove() throws Exception {
        if(!(odo instanceof AgentOsiguranja)){
            throw new Exception("Invalid param...");
        }
    }
    
}
