/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.so.impl;

import domen.Klijent;
import domen.OpstiDomenskiObjekat;
import server.broker.Broker;
import server.so.OpstaSO;

/**
 *
 * @author Korisnik
 */
public class ZapamtiKlijentaSO extends OpstaSO{

    public ZapamtiKlijentaSO(Broker broker, OpstiDomenskiObjekat odo) {
        super(broker, odo);
    }

    @Override
    protected void izvrsiOperaciju() throws Exception {
         broker.zapamtiSlog(odo);
    }

    @Override
    protected void proveriPreduslove() throws Exception {
        if(!(odo instanceof Klijent)){
            throw new Exception("Invalid param...");
        }
    }
    
}
