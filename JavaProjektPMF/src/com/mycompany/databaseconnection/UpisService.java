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
public class UpisService {
    
    private static PreparedStatement pstmt_upis;
    
    public static ArrayList<Upis> findAllByKolegiji(ArrayList<Kolegij> kolegiji, Optional<Connection> con_opt) throws SQLException {
    
        ArrayList<Upis> found_upisi = new ArrayList<>();
        
        Connection con = con_opt.orElse(DriverManager.getConnection(
                ConnectionData.getLink(), ConnectionData.getUsername(), ConnectionData.getPassword()));
               
        String in_sql = String.join(",", Collections.nCopies(kolegiji.size(), "?"));

        String query_find_upisi = String.format(
            "SELECT * FROM slapnicar.java_upisi WHERE java_upisi.kolegij_id IN (%s)", in_sql);

        pstmt_upis = con.prepareStatement(query_find_upisi);

        for (int i = 0; i < kolegiji.size(); i++)
            pstmt_upis.setInt(i + 1, kolegiji.get(i).getId());
        
        ResultSet rs = pstmt_upis.executeQuery();
        
        while (rs.next()) {
            found_upisi.add(new Upis(rs.getInt("kolegij_id"), rs.getInt("jmbag"),
                rs.getBoolean("potvrdjeno")));
        }
        
        return found_upisi;
    }
    
    public static ArrayList<Upis> findAllByKolegiji(ArrayList<Kolegij> kolegiji) throws SQLException {
        return findAllByKolegiji(kolegiji, Optional.empty());
    }
    
    public static ArrayList<Upis> getAll(Optional<Connection> con_opt) throws SQLException {
        
        ArrayList<Upis> found_upisi = new ArrayList<>();
            
        Connection con = con_opt.orElse(DriverManager.getConnection(
                ConnectionData.getLink(), ConnectionData.getUsername(), ConnectionData.getPassword()));

        String query_find_upisi = "SELECT * FROM slapnicar.java_upisi";

        pstmt_upis = con.prepareStatement(query_find_upisi);

        ResultSet rs = pstmt_upis.executeQuery();

        while (rs.next()) {
            found_upisi.add(new Upis(rs.getInt("kolegij_id"), rs.getInt("jmbag"),
                rs.getBoolean("potvrdjeno")));
        }
        
        return found_upisi;
    }
    
    public static ArrayList<Upis> getAll() throws SQLException {
        return getAll(Optional.empty());
    }
    
    public static void insert(Kolegij kolegij, Student student, boolean potvrdjeno, Optional<Connection> con_opt) throws SQLException {

        Connection con = con_opt.orElse(DriverManager.getConnection(
            ConnectionData.getLink(), ConnectionData.getUsername(), ConnectionData.getPassword()));
         
        String query_upis = "INSERT INTO slapnicar.java_upisi VALUES(DEFAULT, ?, ?, ?)";
        
        pstmt_upis = con.prepareStatement(query_upis, new String[]{"java_profesori.id"});

        pstmt_upis.setInt(1, kolegij.getId());
        pstmt_upis.setInt(2, student.getJmbag());
        pstmt_upis.setBoolean(3, potvrdjeno);
        
        pstmt_upis.executeUpdate();
    }
    
    public static void insert(Kolegij kolegij, Student student, boolean potvrdjeno) throws SQLException {
        insert(kolegij, student, potvrdjeno, Optional.empty());
    }
    
    public static void update(Upis upis, Optional<Connection> con_opt) throws SQLException {
        
        Connection con = con_opt.orElse(DriverManager.getConnection(
                ConnectionData.getLink(), ConnectionData.getUsername(), ConnectionData.getPassword()));

        String query_update_kolegij = "UPDATE slapnicar.java_upisi SET java_upisi.kolegij_id=?, java_upisi.jmbag=?, java_upisi.potvrdjeno=? WHERE java_upisi.kolegij_id=? AND java_upisi.jmbag=?";
        
        pstmt_upis = con.prepareStatement(query_update_kolegij);
        
        pstmt_upis.setInt(1, upis.getKolegijId());
        pstmt_upis.setInt(2, upis.getJmbag());
        pstmt_upis.setBoolean(3, upis.getPotvrdjeno());
        
        pstmt_upis.executeUpdate();
    }
    
    public static void update(Upis upis) throws SQLException {
        update(upis, Optional.empty());
    }
    
}
