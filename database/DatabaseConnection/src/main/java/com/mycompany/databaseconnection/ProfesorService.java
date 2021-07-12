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
import java.util.Collections;
import java.util.Optional;

/**
 *
 * @author ANDS
 */
public class ProfesorService {
    
    private static PreparedStatement pstmt_profesor;
    
    public static Profesor find(String korisnicko_ime, Optional<Connection> con_opt) throws SQLException {
        
        Profesor found_profesor = null;
            
        Connection con = con_opt.orElse(DriverManager.getConnection(
                ConnectionData.getLink(), ConnectionData.getUsername(), ConnectionData.getPassword()));

        String query_find_profesor = "SELECT * FROM slapnicar.java_profesori WHERE java_profesori.korisnicko_ime=? LIMIT 1";

        pstmt_profesor = con.prepareStatement(query_find_profesor);

        pstmt_profesor.setString(1, korisnicko_ime);

        ResultSet rs = pstmt_profesor.executeQuery();

        while (rs.next()) {
            found_profesor = new Profesor(rs.getInt("id"), rs.getString("korisnicko_ime"),
                rs.getString("lozinka"), rs.getString("ime"), rs.getString("prezime"));
        }
        
        return found_profesor;
    }
    
    public static Profesor find(String korisnicko_ime) throws SQLException {
        return find(korisnicko_ime, Optional.empty());
    }
    
    public static ArrayList<Profesor> findAllByKolegiji(ArrayList<Kolegij> kolegiji, Optional<Connection> con_opt) throws SQLException {
    
        ArrayList<Profesor> found_profesori = new ArrayList<>();
        
        Connection con = con_opt.orElse(DriverManager.getConnection(
                ConnectionData.getLink(), ConnectionData.getUsername(), ConnectionData.getPassword()));

        String in_sql = String.join(",", Collections.nCopies(kolegiji.size(), "?"));

        String query_find_profesori = String.format(
            "SELECT * FROM slapnicar.java_kolegiji WHERE java_kolegiji.kolegij_id IN (%s)", in_sql);

        pstmt_profesor = con.prepareStatement(query_find_profesori);

        for (int i = 0; i < kolegiji.size(); i++)
            pstmt_profesor.setInt(i + 1, kolegiji.get(i).getId());
        
        ResultSet rs = pstmt_profesor.executeQuery();
        
        while (rs.next()) {
            found_profesori.add(new Profesor(rs.getInt("id"), rs.getString("korisnicko_ime"),
                rs.getString("lozinka"), rs.getString("ime"), rs.getString("prezime")));
        }
        
        return found_profesori;
    }
    
    public static ArrayList<Profesor> findAllByKolegiji(ArrayList<Kolegij> kolegiji) throws SQLException {
        return findAllByKolegiji(kolegiji, Optional.empty());
    }
    
    public static ArrayList<Profesor> getAll(Optional<Connection> con_opt) throws SQLException {
        
        ArrayList<Profesor> found_profesori = new ArrayList<>();
            
        Connection con = con_opt.orElse(DriverManager.getConnection(
                ConnectionData.getLink(), ConnectionData.getUsername(), ConnectionData.getPassword()));

        String query_find_profesori = "SELECT * FROM slapnicar.java_profesori";

        pstmt_profesor = con.prepareStatement(query_find_profesori);

        ResultSet rs = pstmt_profesor.executeQuery();

        while (rs.next()) {
            found_profesori.add(new Profesor(rs.getInt("id"), rs.getString("korisnicko_ime"),
                rs.getString("lozinka"), rs.getString("ime"), rs.getString("prezime")));
        }
        
        return found_profesori;
    }
    
    public static ArrayList<Profesor> getAll() throws SQLException {
        return getAll(Optional.empty());
    }
    
    public static int insert(String korisnicko_ime, String lozinka, String ime, String prezime, Optional<Connection> con_opt) throws SQLException {

        int id = -1;
        
        Connection con = con_opt.orElse(DriverManager.getConnection(
            ConnectionData.getLink(), ConnectionData.getUsername(), ConnectionData.getPassword()));
        
        
        String query_profesor = "INSERT INTO slapnicar.java_profesori VALUES(DEFAULT, ?, ?, ?, ?) RETURNING java_profesori.id";

        pstmt_profesor = con.prepareStatement(query_profesor, new String[]{"java_profesori.id"});

        pstmt_profesor.setString(1, korisnicko_ime);
        pstmt_profesor.setString(2, lozinka);
        pstmt_profesor.setString(3, ime);
        pstmt_profesor.setString(4, prezime);

        int j = pstmt_profesor.executeUpdate();
        if (j > 0) {
            ResultSet rs = pstmt_profesor.getGeneratedKeys();
            while (rs.next()) {
                id = Integer.parseInt(rs.getString(1));
            }
        }
        return id;
    }
    
    public static int insert(String korisnicko_ime, String lozinka, String ime, String prezime) throws SQLException {
        return insert(korisnicko_ime, lozinka, ime, prezime, Optional.empty());
    }
    
    public static void update(Profesor profesor, Optional<Connection> con_opt) throws SQLException {
        
        Connection con = con_opt.orElse(DriverManager.getConnection(
                ConnectionData.getLink(), ConnectionData.getUsername(), ConnectionData.getPassword()));

        String query_update_profesor = "UPDATE slapnicar.java_profesori SET java_profesori.korisnicko_ime=?, java_profesori.lozinka=?, java_profesori.ime=?, java_profesori.prezime=? WHERE java_profesori.id=?";
        
        pstmt_profesor = con.prepareStatement(query_update_profesor);
        
        pstmt_profesor.setString(1, profesor.getKorisnickoIme());
        pstmt_profesor.setString(2, profesor.getLozinka());
        pstmt_profesor.setString(3, profesor.getIme());
        pstmt_profesor.setString(4, profesor.getprezime());
        pstmt_profesor.setInt(5, profesor.getId());
        
        pstmt_profesor.executeUpdate();
    }
    
    public static void update(Profesor profesor) throws SQLException {
        update(profesor, Optional.empty());
    }
    
}
