/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseConnection;

/**
 *
 * @author ANDS
 */
public class Upis {
    
    private int kolegij_id;
    private int jmbag;
    private boolean potvrdjeno;
    
    public Upis(int kolegij_id, int jmbag, boolean potvrdjeno) {
        this.kolegij_id = kolegij_id;
        this.jmbag = jmbag;
        this.potvrdjeno = potvrdjeno;
    }
    
    public int getKolegijId() {
            return kolegij_id;
    }

    public void setKolegijId(int kolegij_id) {
            this.kolegij_id = kolegij_id;
    }
     
    public int getJmbag() {
            return jmbag;
    }

    public void setJmbag(int jmbag) {
            this.jmbag = jmbag;
    }
    
    public boolean getPotvrdjeno() {
            return potvrdjeno;
    }

    public void setPotvrdjeno(boolean potvrdjeno) {
            this.potvrdjeno = potvrdjeno;
    }
    
}
