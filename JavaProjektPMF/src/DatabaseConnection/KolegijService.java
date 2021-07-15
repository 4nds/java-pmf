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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

/**
 *
 * @author ANDS
 */
public class KolegijService {
    
    private static PreparedStatement pstmt_kolegij;
    
    public static Kolegij find(String ime, Optional<Connection> con_opt) throws SQLException {
        
        Kolegij found_kolegij = null;
            
        Connection con = con_opt.orElse(DriverManager.getConnection(
                ConnectionData.getLink(), ConnectionData.getUsername(), ConnectionData.getPassword()));

        String query_find_kolegij = "SELECT * FROM slapnicar.java_kolegiji WHERE java_kolegiji.ime=? LIMIT 1";

        pstmt_kolegij = con.prepareStatement(query_find_kolegij);

        pstmt_kolegij.setString(1, ime);

        ResultSet rs = pstmt_kolegij.executeQuery();

        while (rs.next()) {
            found_kolegij = new Kolegij(rs.getInt("id"), rs.getString("ime"),
                rs.getInt("profesor_id"), rs.getString("opis"), rs.getString("pravila"));
        }
        
        if (! rs.isClosed()) {
            rs.close();
        }
        pstmt_kolegij.close();
        if (! con_opt.isPresent()) {
            con.close();
        }
        
        return found_kolegij;
    }
    
    public static Kolegij find(String ime) throws SQLException {
        return find(ime, Optional.empty());
    }
    
    public static Kolegij findById(int id, Optional<Connection> con_opt) throws SQLException {
        
        Kolegij found_kolegij = null;
            
        Connection con = con_opt.orElse(DriverManager.getConnection(
                ConnectionData.getLink(), ConnectionData.getUsername(), ConnectionData.getPassword()));

        String query_find_kolegij = "SELECT * FROM slapnicar.java_kolegiji WHERE java_kolegiji.id=? LIMIT 1";

        pstmt_kolegij = con.prepareStatement(query_find_kolegij);

        pstmt_kolegij.setInt(1, id);

        ResultSet rs = pstmt_kolegij.executeQuery();

        while (rs.next()) {
            found_kolegij = new Kolegij(rs.getInt("id"), rs.getString("ime"),
                rs.getInt("profesor_id"), rs.getString("opis"), rs.getString("pravila"));
        }
        
        if (! rs.isClosed()) {
            rs.close();
        }
        pstmt_kolegij.close();
        if (! con_opt.isPresent()) {
            con.close();
        }
        
        return found_kolegij;
    }
    
    public static Kolegij findById(int id) throws SQLException {
        return findById(id, Optional.empty());
    }
    
    public static ArrayList<Kolegij> findAllByStudent(Student student, Optional<Connection> con_opt) throws SQLException {
    
        ArrayList<Kolegij> found_kolegiji = new ArrayList<>();
        
        Connection con = con_opt.orElse(DriverManager.getConnection(
                ConnectionData.getLink(), ConnectionData.getUsername(), ConnectionData.getPassword()));

        String query_find_kolegiji = "SELECT * FROM slapnicar.java_upisi WHERE java_upisi.jmbag=? AND java_upisi.potvrdjeno=true";

        pstmt_kolegij = con.prepareStatement(query_find_kolegiji);
        
        pstmt_kolegij.setInt(1, student.getJmbag());
        
        ResultSet rs = pstmt_kolegij.executeQuery();
        
        while (rs.next()) {
            found_kolegiji.add(findById(rs.getInt("kolegij_id"), Optional.of(con)));
        }
        
        if (! rs.isClosed()) {
            rs.close();
        }
        pstmt_kolegij.close();
        if (! con_opt.isPresent()) {
            con.close();
        }
        
        return found_kolegiji;
    }
    
    public static ArrayList<Kolegij> findAllByStudent(Student student) throws SQLException {
        return findAllByStudent(student, Optional.empty());
    }
    
    public static ArrayList<Kolegij> findAllByProfesor(Profesor profesor, Optional<Connection> con_opt) throws SQLException {
    
        ArrayList<Kolegij> found_kolegiji = new ArrayList<>();
        
        Connection con = con_opt.orElse(DriverManager.getConnection(
                ConnectionData.getLink(), ConnectionData.getUsername(), ConnectionData.getPassword()));

        String query_find_kolegiji = "SELECT * FROM slapnicar.java_kolegiji WHERE java_kolegiji.profesor_id=?";

        pstmt_kolegij = con.prepareStatement(query_find_kolegiji);
        
        pstmt_kolegij.setInt(1, profesor.getId());
        
        ResultSet rs = pstmt_kolegij.executeQuery();
        
        while (rs.next()) {
            found_kolegiji.add(new Kolegij(rs.getInt("id"), rs.getString("ime"),
                rs.getInt("profesor_id"), rs.getString("opis"), rs.getString("pravila")));
        }
        
        if (! rs.isClosed()) {
            rs.close();
        }
        pstmt_kolegij.close();
        if (! con_opt.isPresent()) {
            con.close();
        }
        
        return found_kolegiji;
    }
    
    public static ArrayList<Kolegij> findAllByProfesor(Profesor profesor) throws SQLException {
        return findAllByProfesor(profesor, Optional.empty());
    }
    
    public static ArrayList<Kolegij> findAllByObavijesti(ArrayList<Obavijest> obavijesti, Optional<Connection> con_opt) throws SQLException {
    
        ArrayList<Kolegij> found_kolegiji = new ArrayList<>();
        
        Connection con = con_opt.orElse(DriverManager.getConnection(
                ConnectionData.getLink(), ConnectionData.getUsername(), ConnectionData.getPassword()));
        
        String in_sql = String.join(",", Collections.nCopies(obavijesti.size(), "?"));

        String query_find_kolegiji = String.format(
            "SELECT * FROM slapnicar.java_obavijesti WHERE java_obavijesti.kolegij_id IN (%s)", in_sql);

        pstmt_kolegij = con.prepareStatement(query_find_kolegiji);

        for (int i = 0; i < obavijesti.size(); i++)
            pstmt_kolegij.setInt(i + 1, obavijesti.get(i).getKolegijId());
        
        ResultSet rs = pstmt_kolegij.executeQuery();
        
        while (rs.next()) {
            found_kolegiji.add(findById(rs.getInt("kolegij_id"), Optional.of(con)));
        }
        
        if (! rs.isClosed()) {
            rs.close();
        }
        pstmt_kolegij.close();
        if (! con_opt.isPresent()) {
            con.close();
        }
        
        return found_kolegiji;
    }
    
    public static ArrayList<Kolegij> findAllByObavijesti(ArrayList<Obavijest> obavijesti) throws SQLException {
        return findAllByObavijesti(obavijesti, Optional.empty());
    }
    
    public static ArrayList<Kolegij> getAll(Optional<Connection> con_opt) throws SQLException {
        
        ArrayList<Kolegij> found_kolegiji = new ArrayList<>();
            
        Connection con = con_opt.orElse(DriverManager.getConnection(
                ConnectionData.getLink(), ConnectionData.getUsername(), ConnectionData.getPassword()));

        String query_find_kolegiji = "SELECT * FROM slapnicar.java_kolegiji";

        pstmt_kolegij = con.prepareStatement(query_find_kolegiji);

        ResultSet rs = pstmt_kolegij.executeQuery();

        while (rs.next()) {
            found_kolegiji.add(new Kolegij(rs.getInt("id"), rs.getString("ime"),
                rs.getInt("profesor_id"), rs.getString("opis"), rs.getString("pravila")));
        }
        
        if (! rs.isClosed()) {
            rs.close();
        }
        pstmt_kolegij.close();
        if (! con_opt.isPresent()) {
            con.close();
        }
        
        return found_kolegiji;
    }
    
    public static ArrayList<Kolegij> getAll() throws SQLException {
        return getAll(Optional.empty());
    }
    
    public static int insert(String ime, Profesor profesor, String opis, String pravila, Optional<Connection> con_opt) throws SQLException {

        int id = -1;
        
        Connection con = con_opt.orElse(DriverManager.getConnection(
            ConnectionData.getLink(), ConnectionData.getUsername(), ConnectionData.getPassword()));
        
        String query_kolegij = "INSERT INTO slapnicar.java_kolegiji VALUES(DEFAULT, ?, ?, ?, ?)";

        pstmt_kolegij = con.prepareStatement(query_kolegij, new String[]{"java_profesori.id"});

        pstmt_kolegij.setString(1, ime);
        pstmt_kolegij.setInt(2, profesor.getId());
        pstmt_kolegij.setString(3, opis);
        pstmt_kolegij.setString(4, pravila);

        int j = pstmt_kolegij.executeUpdate();
        if (j > 0) {
            ResultSet rs = pstmt_kolegij.getGeneratedKeys();
            while (rs.next()) {
                id = Integer.parseInt(rs.getString(1));
            }
            
            if (! rs.isClosed()) {
                rs.close();
            }
        }
        
        pstmt_kolegij.close();
        if (! con_opt.isPresent()) {
            con.close();
        }
        
        return id;
    }
    
    public static int insert(String ime, Profesor profesor, String opis, String pravila) throws SQLException {
        return insert(ime, profesor, opis, pravila, Optional.empty());
    }
    
    public static void update(Kolegij kolegij, Optional<Connection> con_opt) throws SQLException {
        
        Connection con = con_opt.orElse(DriverManager.getConnection(
                ConnectionData.getLink(), ConnectionData.getUsername(), ConnectionData.getPassword()));

        String query_update_kolegij = "UPDATE slapnicar.java_kolegiji SET java_kolegiji.ime=?, java_kolegiji.profesor_id=?, java_kolegiji.opis=?, java_kolegiji.pravila=? WHERE java_kolegiji.id=?";
        
        pstmt_kolegij = con.prepareStatement(query_update_kolegij);
        
        pstmt_kolegij.setString(1, kolegij.getIme());
        pstmt_kolegij.setInt(2, kolegij.getProfesorId());
        pstmt_kolegij.setString(3, kolegij.getOpis());
        pstmt_kolegij.setString(4, kolegij.getPravila());
        pstmt_kolegij.setInt(5, kolegij.getId());
        
        pstmt_kolegij.executeUpdate();
        
        pstmt_kolegij.close();
        if (! con_opt.isPresent()) {
            con.close();
        }
    }
    
    public static void update(Kolegij kolegij) throws SQLException {
        update(kolegij, Optional.empty());
    }
    
}
