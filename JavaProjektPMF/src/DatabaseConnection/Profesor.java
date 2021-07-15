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
public class Profesor {
    
    private int id;
    private String korisnicko_ime;
    private String lozinka;
    private String ime;
    private String prezime;
    
    public Profesor(int id, String korisnicko_ime, String lozinka, String ime, String prezime) {
        this.id = id;
        this.korisnicko_ime = korisnicko_ime;
        this.lozinka = lozinka;
        this.ime = ime;
        this.prezime = prezime;
    }
    
    public int getId() {
            return id;
    }

    public void setId(int id) {
            this.id = id;
    }
    
    public String getKorisnickoIme() {
            return korisnicko_ime;
    }

    public void setKorisnickoIme(String korisnicko_ime) {
            this.korisnicko_ime = korisnicko_ime;
    }
    
    public String getLozinka() {
            return lozinka;
    }

    public void setLozinka(String lozinka) {
            this.lozinka = lozinka;
    }
    
    public String getIme() {
            return ime;
    }

    public void setIme(String ime) {
            this.ime = ime;
    }
    
    public String getPrezime() {
            return prezime;
    }

    public void setPrezime(String prezime) {
            this.prezime = prezime;
    }
    
}
