/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.databaseconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author ANDS
 */
public class StudentService {
    
    private static PreparedStatement pstmt_student;
    
    public static Student find(String korisnicko_ime, Optional<Connection> con_opt) throws SQLException {
        
        Student found_student = null;
            
        Connection con = con_opt.orElse(DriverManager.getConnection(
                ConnectionData.getLink(), ConnectionData.getUsername(), ConnectionData.getPassword()));

        String query_find_student = "SELECT * FROM slapnicar.java_studenti WHERE java_studenti.korisnicko_ime=? LIMIT 1";

        pstmt_student = con.prepareStatement(query_find_student);

        pstmt_student.setString(1, korisnicko_ime);

        ResultSet rs = pstmt_student.executeQuery();

        while (rs.next()) {
            found_student = new Student(rs.getInt("jmbag"), rs.getString("korisnicko_ime"),
                rs.getString("lozinka"), rs.getString("ime"), rs.getString("prezime"));
        }
        
        return found_student;
    }
    
    public static Student find(String korisnicko_ime) throws SQLException {
        return find(korisnicko_ime, Optional.empty());
    }
    
    public static ArrayList<Student> findAllByKolegij(Kolegij kolegij, Optional<Connection> con_opt) throws SQLException {
    
        ArrayList<Student> found_studenti = new ArrayList<>();
        
        Connection con = con_opt.orElse(DriverManager.getConnection(
                ConnectionData.getLink(), ConnectionData.getUsername(), ConnectionData.getPassword()));

        String query_find_studenti = "SELECT * FROM slapnicar.java_upisi WHERE java_upisi.kolegij_id=? AND java_upisi.potvrdjeno=true";

        pstmt_student = con.prepareStatement(query_find_studenti);
        
        pstmt_student.setInt(1, kolegij.getId());
        
        ResultSet rs = pstmt_student.executeQuery();
        
        while (rs.next()) {
            found_studenti.add(new Student(rs.getInt("jmbag"), rs.getString("korisnicko_ime"),
                rs.getString("lozinka"), rs.getString("ime"), rs.getString("prezime")));
        }
        
        return found_studenti;
    }
    
    public static ArrayList<Student> findAllByKolegij(Kolegij kolegij) throws SQLException {
        return findAllByKolegij(kolegij, Optional.empty());
    }
    
    public static ArrayList<Student> getAll(Optional<Connection> con_opt) throws SQLException {
        
        ArrayList<Student> found_studenti = new ArrayList<>();
            
        Connection con = con_opt.orElse(DriverManager.getConnection(
                ConnectionData.getLink(), ConnectionData.getUsername(), ConnectionData.getPassword()));

        String query_find_studenti = "SELECT * FROM slapnicar.java_studenti";

        pstmt_student = con.prepareStatement(query_find_studenti);

        ResultSet rs = pstmt_student.executeQuery();

        while (rs.next()) {
            found_studenti.add(new Student(rs.getInt("jmbag"), rs.getString("korisnicko_ime"),
                rs.getString("lozinka"), rs.getString("ime"), rs.getString("prezime")));
        }
        
        return found_studenti;
    }
    
    public static ArrayList<Student> getAll() throws SQLException {
        return getAll(Optional.empty());
    }
    
    public static int insert(String korisnicko_ime, String lozinka, String ime, String prezime, Optional<Connection> con_opt) throws SQLException {

        int jmbag = -1;
        
        Connection con = con_opt.orElse(DriverManager.getConnection(
            ConnectionData.getLink(), ConnectionData.getUsername(), ConnectionData.getPassword()));
        
        String query_student = "INSERT INTO slapnicar.java_studenti VALUES(DEFAULT, ?, ?, ?, ?) RETURNING java_studenti.id";

        pstmt_student = con.prepareStatement(query_student, new String[]{"java_studenti.id"});

        pstmt_student.setString(1, korisnicko_ime);
        pstmt_student.setString(2, lozinka);
        pstmt_student.setString(3, ime);
        pstmt_student.setString(4, prezime);

        int j = pstmt_student.executeUpdate();
        if (j > 0) {
            ResultSet rs = pstmt_student.getGeneratedKeys();
            while (rs.next()) {
                jmbag = Integer.parseInt(rs.getString(1));
            }
        }
        return jmbag;
    }
    
    public static int insert(String korisnicko_ime, String lozinka, String ime, String prezime) throws SQLException {
        return insert(korisnicko_ime, lozinka, ime, prezime, Optional.empty());
    }
    
    public static void update(Student student, Optional<Connection> con_opt) throws SQLException {
        
        Connection con = con_opt.orElse(DriverManager.getConnection(
                ConnectionData.getLink(), ConnectionData.getUsername(), ConnectionData.getPassword()));

        String query_update_student = "UPDATE slapnicar.java_studenti SET java_studenti.korisnicko_ime=?, java_studenti.lozinka=?, java_studenti.ime=?, java_studenti.prezime=? WHERE java_studenti.jmbag=?";
        
        pstmt_student = con.prepareStatement(query_update_student);
        
        pstmt_student.setString(1, student.getKorisnickoIme());
        pstmt_student.setString(2, student.getLozinka());
        pstmt_student.setString(3, student.getIme());
        pstmt_student.setString(4, student.getprezime());
        pstmt_student.setInt(5, student.getJmbag());
        
        pstmt_student.executeUpdate();
    }
    
    public static void update(Student student) throws SQLException {
        update(student, Optional.empty());
    }
    
}
