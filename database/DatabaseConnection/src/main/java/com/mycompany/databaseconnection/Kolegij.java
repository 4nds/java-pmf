/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.databaseconnection;

/**
 *
 * @author ANDS
 */
public class Kolegij {
    
    private int id;
    private String ime;
    private int profesor_id;
    private String opis;
    private String pravila;
    
    public Kolegij(int id, String ime, int profesor_id, String opis, String pravila) {
        this.id = id;
        this.ime = ime;
        this.profesor_id = profesor_id;
        this.opis = opis;
        this.pravila = pravila;
    }
    
    public int getId() {
            return id;
    }

    public void setId(int id) {
            this.id = id;
    }
    
    public String getIme() {
            return ime;
    }

    public void setIme(String ime) {
            this.ime = ime;
    }
    
    public int getProfesorId() {
            return profesor_id;
    }

    public void setProfesorId(int profesor_id) {
            this.profesor_id = profesor_id;
    }
    
    public String getOpis() {
            return opis;
    }

    public void setOpis(String korisnicko_ime) {
            this.opis = opis;
    }
    
    public String getPravila() {
            return pravila;
    }

    public void setPravila(String pravila) {
            this.pravila = pravila;
    }
    
}
