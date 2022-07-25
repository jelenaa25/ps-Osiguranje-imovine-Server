/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.so.impl;

import domen.OpstiDomenskiObjekat;
import server.broker.Broker;
import server.so.OpstaSO;

/**
 *
 * @author Korisnik
 */
public class UcitajPolisuSO extends OpstaSO{

    public UcitajPolisuSO(Broker broker, OpstiDomenskiObjekat odo) {
        super(broker, odo);
    }

    @Override
    protected void izvrsiOperaciju() throws Exception {
        odo = broker.pronadjiSlog(odo); //nasla polisu       
        lista = broker.pronadjiSlogove(odo.kreirajInstancuSlabogObjekta());
        odo.postaviSlabeObjekte(lista);
        
    }

    @Override
    protected void proveriPreduslove() throws Exception {
    }
    
}
