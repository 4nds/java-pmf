/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.databaseconnection;

import java.sql.Timestamp;

/**
 *
 * @author ANDS
 */
public class Obavijest {
    
    private int id;
    private int kolegij_id;
    private String tekst;
    private Timestamp vrijeme;
    
    public Obavijest(int id, int kolegij_id, String tekst, Timestamp vrijeme) {
        this.id = id;
        this.kolegij_id = kolegij_id;
        this.tekst = tekst;
        this.vrijeme = vrijeme;
    }
    
    public int getId() {
            return id;
    }

    public void setId(int id) {
            this.id = id;
    }
    
    public int getKolegijId() {
            return kolegij_id;
    }

    public void setKolegijId(int kolegij_id) {
            this.kolegij_id = kolegij_id;
    }
    
    public String getTekst() {
            return tekst;
    }

    public void setTekst(String tekst) {
            this.tekst = tekst;
    }
    
    public Timestamp getVrijeme() {
            return vrijeme;
    }

    public void setVrijeme(Timestamp vrijeme) {
            this.vrijeme = vrijeme;
    }
    
}
