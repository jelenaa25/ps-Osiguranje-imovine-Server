/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

import domen.AgentOsiguranja;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Korisnik
 */
public class ModelTabeleAgenata extends AbstractTableModel{
    private List<AgentOsiguranja> korisnici;
    private String[] kolone= {"Ime", "Prezime", "Username", "Strucna sprema"};
    public ModelTabeleAgenata() {
        korisnici = new ArrayList<>();
    }

    public ModelTabeleAgenata(List<AgentOsiguranja> korisnici) {
        this.korisnici = korisnici;
    }
    
    
    @Override
    public int getRowCount() {
        return korisnici.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        AgentOsiguranja agentOsiguranja = korisnici.get(i);
        switch(i1){
            case 0: return agentOsiguranja.getIme();
            case 1: return agentOsiguranja.getPrezime();
            case 2: return agentOsiguranja.getUsername();
            case 3: return agentOsiguranja.getStrucnaSprema();
            default: return "";
        }
    }

    @Override
    public String getColumnName(int i) {
        return kolone[i];
    }

    public void setKorisnici(List<AgentOsiguranja> korisnici) {
        this.korisnici = korisnici;
        fireTableDataChanged();
    }

    public List<AgentOsiguranja> getKorisnici() {
        return korisnici;
    }
    
    public void dodaj(AgentOsiguranja a){
        korisnici.add(a); fireTableDataChanged();
    }
    public void obrisi(AgentOsiguranja a){
        korisnici.remove(a); fireTableDataChanged();
    }

    public boolean postoji(AgentOsiguranja agentOsiguranja) {
        for (AgentOsiguranja agentOsiguranja1 : korisnici) {
            if (agentOsiguranja1.getAgentID()==agentOsiguranja.getAgentID()) {
                return true;
            }
        } return false;
    }
    
}
