/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author ANDS
 */
public class ObavijestService {
    
    private static PreparedStatement pstmt_obavijest;
    
    public static ArrayList<Obavijest> findAllByKolegij(Kolegij kolegij, Optional<Connection> con_opt) throws SQLException {
    
        ArrayList<Obavijest> found_obavijesti = new ArrayList<>();
        
        Connection con = con_opt.orElse(DriverManager.getConnection(
                ConnectionData.getLink(), ConnectionData.getUsername(), ConnectionData.getPassword()));

        String query_find_obavijesti = "SELECT * FROM slapnicar.java_obavijesti WHERE java_obavijesti.kolegij_id=? ORDER BY java_obavijesti.vrijeme";

        pstmt_obavijest = con.prepareStatement(query_find_obavijesti);
        
        pstmt_obavijest.setInt(1, kolegij.getId());
        
        ResultSet rs = pstmt_obavijest.executeQuery();
        
        while (rs.next()) {
            found_obavijesti.add(new Obavijest(rs.getInt("id"), rs.getInt("kolegij_id"),
                rs.getString("tekst"), rs.getTimestamp("vrijeme")));
        }
        
        if (! rs.isClosed()) {
            rs.close();
        }
        pstmt_obavijest.close();
        if (! con_opt.isPresent()) {
            con.close();
        }
        
        return found_obavijesti;
    }
    
    public static ArrayList<Obavijest> findAllByKolegij(Kolegij kolegij) throws SQLException {
        return findAllByKolegij(kolegij, Optional.empty());
    }
    
    public static ArrayList<Obavijest> getAll(Optional<Connection> con_opt) throws SQLException {
        
        ArrayList<Obavijest> found_obavijesti = new ArrayList<>();
            
        Connection con = con_opt.orElse(DriverManager.getConnection(
                ConnectionData.getLink(), ConnectionData.getUsername(), ConnectionData.getPassword()));

        String query_find_obavijesti = "SELECT * FROM slapnicar.java_obavijesti ORDER BY java_obavijesti.vrijeme";

        pstmt_obavijest = con.prepareStatement(query_find_obavijesti);

        ResultSet rs = pstmt_obavijest.executeQuery();

        while (rs.next()) {
            found_obavijesti.add(new Obavijest(rs.getInt("id"), rs.getInt("kolegij_id"),
                rs.getString("tekst"), rs.getTimestamp("vrijeme")));
        }
        
        if (! rs.isClosed()) {
            rs.close();
        }
        pstmt_obavijest.close();
        if (! con_opt.isPresent()) {
            con.close();
        }
        
        return found_obavijesti;
    }
    
    public static ArrayList<Obavijest> getAll() throws SQLException {
        return getAll(Optional.empty());
    }
    
    public static int insert(Kolegij kolegij, String tekst, Timestamp vrijeme, Optional<Connection> con_opt) throws SQLException {

        int id = -1;
        
        Connection con = con_opt.orElse(DriverManager.getConnection(
            ConnectionData.getLink(), ConnectionData.getUsername(), ConnectionData.getPassword()));
        
        String query_obavijest = "INSERT INTO slapnicar.java_obavijesti VALUES(DEFAULT, ?, ?, ?)";

        pstmt_obavijest = con.prepareStatement(query_obavijest, new String[]{"java_profesori.id"});

        pstmt_obavijest.setInt(1, kolegij.getId());
        pstmt_obavijest.setString(2, tekst);
        pstmt_obavijest.setTimestamp(3, vrijeme);

        int j = pstmt_obavijest.executeUpdate();
        if (j > 0) {
            ResultSet rs = pstmt_obavijest.getGeneratedKeys();
            while (rs.next()) {
                id = Integer.parseInt(rs.getString(1));
            }
            
                if (! rs.isClosed()) {
                rs.close();
            }
        }
        
        pstmt_obavijest.close();
        if (! con_opt.isPresent()) {
            con.close();
        }
        
        return id;
    }
    
    public static int insert(Kolegij kolegij, String tekst, Timestamp vrijeme) throws SQLException {
        return insert(kolegij, tekst, vrijeme, Optional.empty());
    }
    
    public static void update(Obavijest obavijest, Optional<Connection> con_opt) throws SQLException {
        
        Connection con = con_opt.orElse(DriverManager.getConnection(
                ConnectionData.getLink(), ConnectionData.getUsername(), ConnectionData.getPassword()));

        String query_update_obavijest = "UPDATE slapnicar.java_obavijesti SET java_obavijesti.kolegij_id=?, java_obavijesti.tekst=?, java_obavijesti.vrijeme=? WHERE java_obavijesti.id=?";
        
        pstmt_obavijest = con.prepareStatement(query_update_obavijest);
        pstmt_obavijest.setInt(1, obavijest.getKolegijId());
        pstmt_obavijest.setString(2, obavijest.getTekst());
        pstmt_obavijest.setTimestamp(3, obavijest.getVrijeme());
        pstmt_obavijest.setInt(4, obavijest.getId());
        
        pstmt_obavijest.executeUpdate();
        
        pstmt_obavijest.close();
        if (! con_opt.isPresent()) {
            con.close();
        }
    }
    
    public static void update(Obavijest obavijest) throws SQLException {
        update(obavijest, Optional.empty());
    }
    
}
