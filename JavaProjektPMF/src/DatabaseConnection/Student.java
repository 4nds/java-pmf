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
public class Student {
    
    private int jmbag;
    private String korisnicko_ime;
    private String lozinka;
    private String ime;
    private String prezime;
    
    public Student(int jmbag, String korisnicko_ime, String lozinka, String ime, String prezime) {
        this.jmbag = jmbag;
        this.korisnicko_ime = korisnicko_ime;
        this.lozinka = lozinka;
        this.ime = ime;
        this.prezime = prezime;
    }
    
    public int getJmbag() {
            return jmbag;
    }

    public void setJmbag(int jmbag) {
            this.jmbag = jmbag;
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
    
    @Override
    public String toString() { 
        return "Student('" + korisnicko_ime + "', '" + lozinka + "', '" + ime +"', '" + prezime + "')";  
    }
    
}
