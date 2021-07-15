/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ANDS
 */
public class ProfesorServiceTest {
    
    static Connection con;
    
    public ProfesorServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws SQLException {
        System.out.println("SET UP CLASS");
        
        con = DriverManager.getConnection(
            ConnectionData.getLink(), ConnectionData.getUsername(), ConnectionData.getPassword());
        
        SetUpAndTearDown.setUpClass(con);
    }
    
    @AfterClass
    public static void tearDownClass() throws SQLException {
        System.out.println("TEAR DOWN CLASS");
        
        SetUpAndTearDown.tearDownClass(con);
        con.close();
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of find method, of class ProfesorService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testFind_String_Optional() throws SQLException {
        System.out.println("find");
        
        String korisnicko_ime = "__test_ivan";
        Optional<Connection> con_opt = Optional.of(con);
        
        String exp_korisnicko_ime = "__test_ivan";
        String exp_lozinka = "ivanovasifra";
        String exp_ime = "Ivan";
        String exp_prezime = "Kovačević";
        
        Profesor result = ProfesorService.find(korisnicko_ime, con_opt);
        
        assertEquals(exp_korisnicko_ime, result.getKorisnickoIme());
        assertEquals(exp_lozinka, result.getLozinka() );
        assertEquals(exp_ime, result.getIme());
        assertEquals(exp_prezime, result.getPrezime());
    }

    /**
     * Test of find method, of class ProfesorService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testFind_String() throws SQLException {
        System.out.println("find");
        
        String korisnicko_ime = "__test_ivan";
        
        String exp_korisnicko_ime = "__test_ivan";
        String exp_lozinka = "ivanovasifra";
        String exp_ime = "Ivan";
        String exp_prezime = "Kovačević";
        
        Profesor result = ProfesorService.find(korisnicko_ime);
        
        assertEquals(exp_korisnicko_ime, result.getKorisnickoIme());
        assertEquals(exp_lozinka, result.getLozinka() );
        assertEquals(exp_ime, result.getIme());
        assertEquals(exp_prezime, result.getPrezime());
    }

    /**
     * Test of findAllByKolegiji method, of class ProfesorService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testFindAllByKolegiji_ArrayList_Optional() throws SQLException {
        System.out.println("findAllByKolegiji");
        
        Statement stmt = con.createStatement();
        String sql;
        ResultSet rs;
        
        ArrayList<Kolegij> kolegiji = new ArrayList<>();
        sql = "SELECT * FROM slapnicar.java_kolegiji WHERE java_kolegiji.ime='__test_Matematika' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        if (rs.next()) {
            kolegiji.add(new Kolegij(rs.getInt("id"), rs.getString("ime"),
                rs.getInt("profesor_id"), rs.getString("opis"), rs.getString("pravila")));
        }
        if (! rs.isClosed()) {
            rs.close();
        }
        sql = "SELECT * FROM slapnicar.java_kolegiji WHERE java_kolegiji.ime='__test_Statistika' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        if (rs.next()) {
            kolegiji.add(new Kolegij(rs.getInt("id"), rs.getString("ime"),
                rs.getInt("profesor_id"), rs.getString("opis"), rs.getString("pravila")));
        }
        if (! rs.isClosed()) {
            rs.close();
        }
        
        Optional<Connection> con_opt = Optional.of(con);
        
        ArrayList<String> exp_korisnicka_imena = new ArrayList<>(
            Arrays.asList("__test_ivan", "__test_marko"));
        ArrayList<String> exp_lozinke = new ArrayList<>(
            Arrays.asList("ivanovasifra", "markovasifra"));
        ArrayList<String> exp_imena = new ArrayList<>(
            Arrays.asList("Ivan", "Marko"));
        ArrayList<String> exp_prezimena = new ArrayList<>(
            Arrays.asList("Kovačević", "Polenko"));
        
        ArrayList<Profesor> result = ProfesorService.findAllByKolegiji(kolegiji, con_opt);
        
        int index;
        int found = 0;
        for (int i = 0; i < result.size(); i++) {
            index = -1;
            for (int j = 0; j < exp_korisnicka_imena.size(); j++) {
                if ( exp_korisnicka_imena.get(j).equals(result.get(i).getKorisnickoIme()) ) {
                    index = j;
                    found++;
                }
            }
            if (index >= 0) {
                assertEquals(exp_korisnicka_imena.get(index), result.get(i).getKorisnickoIme());
                assertEquals(exp_lozinke.get(index), result.get(i).getLozinka() );
                assertEquals(exp_imena.get(index), result.get(i).getIme());
                assertEquals(exp_prezimena.get(index), result.get(i).getPrezime());
            }
        }
        assertEquals(exp_korisnicka_imena.size(), found);
        
        if (! stmt.isClosed()) {
            stmt.close();
        }
    }

    /**
     * Test of findAllByKolegiji method, of class ProfesorService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testFindAllByKolegiji_ArrayList() throws SQLException {
        System.out.println("findAllByKolegiji");
        
        Statement stmt = con.createStatement();
        String sql;
        ResultSet rs;
        
        ArrayList<Kolegij> kolegiji = new ArrayList<>();
        sql = "SELECT * FROM slapnicar.java_kolegiji WHERE java_kolegiji.ime='__test_Matematika' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        if (rs.next()) {
            kolegiji.add(new Kolegij(rs.getInt("id"), rs.getString("ime"),
                rs.getInt("profesor_id"), rs.getString("opis"), rs.getString("pravila")));
        }
        if (! rs.isClosed()) {
            rs.close();
        }
        sql = "SELECT * FROM slapnicar.java_kolegiji WHERE java_kolegiji.ime='__test_Statistika' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        if (rs.next()) {
            kolegiji.add(new Kolegij(rs.getInt("id"), rs.getString("ime"),
                rs.getInt("profesor_id"), rs.getString("opis"), rs.getString("pravila")));
        }
        if (! rs.isClosed()) {
            rs.close();
        }
        
        ArrayList<String> exp_korisnicka_imena = new ArrayList<>(
            Arrays.asList("__test_ivan", "__test_marko"));
        ArrayList<String> exp_lozinke = new ArrayList<>(
            Arrays.asList("ivanovasifra", "markovasifra"));
        ArrayList<String> exp_imena = new ArrayList<>(
            Arrays.asList("Ivan", "Marko"));
        ArrayList<String> exp_prezimena = new ArrayList<>(
            Arrays.asList("Kovačević", "Polenko"));
        
        ArrayList<Profesor> result = ProfesorService.findAllByKolegiji(kolegiji);
        
        int index;
        int found = 0;
        for (int i = 0; i < result.size(); i++) {
            index = -1;
            for (int j = 0; j < exp_korisnicka_imena.size(); j++) {
                if ( exp_korisnicka_imena.get(j).equals(result.get(i).getKorisnickoIme()) ) {
                    index = j;
                    found++;
                }
            }
            if (index >= 0) {
                assertEquals(exp_korisnicka_imena.get(index), result.get(i).getKorisnickoIme());
                assertEquals(exp_lozinke.get(index), result.get(i).getLozinka() );
                assertEquals(exp_imena.get(index), result.get(i).getIme());
                assertEquals(exp_prezimena.get(index), result.get(i).getPrezime());
            }
        }
        assertEquals(exp_korisnicka_imena.size(), found);
        
        if (! stmt.isClosed()) {
            stmt.close();
        }
    }
    
    /**
     * Test of getAll method, of class ProfesorService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testGetAll_Optional() throws SQLException {
        System.out.println("getAll");
        
        Optional<Connection> con_opt = Optional.of(con);
        
        ArrayList<String> exp_korisnicka_imena = new ArrayList<>(
            Arrays.asList("__test_ivan", "__test_marko"));
        ArrayList<String> exp_lozinke = new ArrayList<>(
            Arrays.asList("ivanovasifra", "markovasifra"));
        ArrayList<String> exp_imena = new ArrayList<>(
            Arrays.asList("Ivan", "Marko"));
        ArrayList<String> exp_prezimena = new ArrayList<>(
            Arrays.asList("Kovačević", "Polenko"));
        
        ArrayList<Profesor> result = ProfesorService.getAll(con_opt);
        
        int index;
        int found = 0;
        for (int i = 0; i < result.size(); i++) {
            index = -1;
            for (int j = 0; j < exp_korisnicka_imena.size(); j++) {
                if ( exp_korisnicka_imena.get(j).equals(result.get(i).getKorisnickoIme()) ) {
                    index = j;
                    found++;
                }
            }
            if (index >= 0) {
                assertEquals(exp_korisnicka_imena.get(index), result.get(i).getKorisnickoIme());
                assertEquals(exp_lozinke.get(index), result.get(i).getLozinka() );
                assertEquals(exp_imena.get(index), result.get(i).getIme());
                assertEquals(exp_prezimena.get(index), result.get(i).getPrezime());
            }
        }
        assertEquals(exp_korisnicka_imena.size(), found);
    }

    /**
     * Test of getAll method, of class ProfesorService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testGetAll() throws SQLException {
        System.out.println("getAll");
        
        ArrayList<String> exp_korisnicka_imena = new ArrayList<>(
            Arrays.asList("__test_ivan", "__test_marko"));
        ArrayList<String> exp_lozinke = new ArrayList<>(
            Arrays.asList("ivanovasifra", "markovasifra"));
        ArrayList<String> exp_imena = new ArrayList<>(
            Arrays.asList("Ivan", "Marko"));
        ArrayList<String> exp_prezimena = new ArrayList<>(
            Arrays.asList("Kovačević", "Polenko"));
        
        ArrayList<Profesor> result = ProfesorService.getAll();
        
        int index;
        int found = 0;
        for (int i = 0; i < result.size(); i++) {
            index = -1;
            for (int j = 0; j < exp_korisnicka_imena.size(); j++) {
                if ( exp_korisnicka_imena.get(j).equals(result.get(i).getKorisnickoIme()) ) {
                    index = j;
                    found++;
                }
            }
            if (index >= 0) {
                assertEquals(exp_korisnicka_imena.get(index), result.get(i).getKorisnickoIme());
                assertEquals(exp_lozinke.get(index), result.get(i).getLozinka() );
                assertEquals(exp_imena.get(index), result.get(i).getIme());
                assertEquals(exp_prezimena.get(index), result.get(i).getPrezime());
            }
        }
        assertEquals(exp_korisnicka_imena.size(), found);
    }

    /**
     * Test of insert method, of class ProfesorService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testInsert_5args() throws SQLException {
        System.out.println("insert");
        
        Statement stmt = con.createStatement();
        
        String exp_korisnicko_ime = "__test_Maja";
        String exp_lozinka = "majinasifra";
        String exp_ime = "Maja";
        String exp_prezime = "Marić";
        Optional<Connection> con_opt = Optional.of(con);
        
        int id = ProfesorService.insert(exp_korisnicko_ime, exp_lozinka, exp_ime, exp_prezime, con_opt);
        
        String sql = "SELECT * FROM slapnicar.java_profesori WHERE java_profesori.id=" + Integer.toString(id) + " LIMIT 1";
        stmt.execute(sql);
        ResultSet rs = stmt.getResultSet();
        assertEquals(true, rs.next());
        assertEquals(id, rs.getInt("id"));
        assertEquals(exp_korisnicko_ime, rs.getString("korisnicko_ime"));
        assertEquals(exp_lozinka, rs.getString("lozinka"));
        assertEquals(exp_ime, rs.getString("ime"));
        assertEquals(exp_prezime, rs.getString("prezime"));
        if (! rs.isClosed()) {
            rs.close();
        }
        if (! stmt.isClosed()) {
            stmt.close();
        }
    }

    /**
     * Test of insert method, of class ProfesorService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testInsert_4args() throws SQLException {
        System.out.println("insert");
        
        Statement stmt = con.createStatement();
        
        String exp_korisnicko_ime = "__test_Maja";
        String exp_lozinka = "majinasifra";
        String exp_ime = "Maja";
        String exp_prezime = "Marić";
                
        int id = ProfesorService.insert(exp_korisnicko_ime, exp_lozinka, exp_ime, exp_prezime);
        
        String sql = "SELECT * FROM slapnicar.java_profesori WHERE java_profesori.id=" + Integer.toString(id) + " LIMIT 1";
        stmt.execute(sql);
        ResultSet rs = stmt.getResultSet();
        assertEquals(true, rs.next());
        assertEquals(id, rs.getInt("id"));
        assertEquals(exp_korisnicko_ime, rs.getString("korisnicko_ime"));
        assertEquals(exp_lozinka, rs.getString("lozinka"));
        assertEquals(exp_ime, rs.getString("ime"));
        assertEquals(exp_prezime, rs.getString("prezime"));
        if (! rs.isClosed()) {
            rs.close();
        }
        if (! stmt.isClosed()) {
            stmt.close();
        }
    }

    /**
     * Test of update method, of class ProfesorService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testUpdate_Profesor_Optional() throws SQLException {
        System.out.println("update");
        
        String sql;
        ResultSet rs;
        
        Statement stmt = con.createStatement();
        
        sql = "SELECT * FROM slapnicar.java_profesori WHERE java_profesori.korisnicko_ime='__test_ivan' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        assertEquals(true, rs.next());
        Profesor profesor = new Profesor(rs.getInt("id"), rs.getString("korisnicko_ime"),
            rs.getString("lozinka"), rs.getString("ime"), rs.getString("prezime"));
        if (! rs.isClosed()) {
            rs.close();
        }
        profesor.setLozinka("ivanovasifra2");
        profesor.setIme("Ivica");
        Optional<Connection> con_opt = Optional.of(con);
        
        String exp_korisnicko_ime = "__test_ivan";
        String exp_lozinka = "ivanovasifra2";
        String exp_ime = "Ivica";
        String exp_prezime = "Kovačević";
        
        ProfesorService.update(profesor, con_opt);
        
        sql = "SELECT * FROM slapnicar.java_profesori WHERE java_profesori.korisnicko_ime='__test_ivan' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        assertEquals(true, rs.next());
        assertEquals(exp_korisnicko_ime, rs.getString("korisnicko_ime"));
        assertEquals(exp_lozinka, rs.getString("lozinka"));
        assertEquals(exp_ime, rs.getString("ime"));
        assertEquals(exp_prezime, rs.getString("prezime"));
        if (! rs.isClosed()) {
            rs.close();
        }
        
        profesor.setLozinka("ivanovasifra");
        profesor.setIme("Ivan");
        exp_korisnicko_ime = "__test_ivan";
        exp_lozinka = "ivanovasifra";
        exp_ime = "Ivan";
        exp_prezime = "Kovačević";
        
        ProfesorService.update(profesor, con_opt);
        
        sql = "SELECT * FROM slapnicar.java_profesori WHERE java_profesori.korisnicko_ime='__test_ivan' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        assertEquals(true, rs.next());
        assertEquals(exp_korisnicko_ime, rs.getString("korisnicko_ime"));
        assertEquals(exp_lozinka, rs.getString("lozinka"));
        assertEquals(exp_ime, rs.getString("ime"));
        assertEquals(exp_prezime, rs.getString("prezime"));
        if (! rs.isClosed()) {
            rs.close();
        }
        
        if (! stmt.isClosed()) {
            stmt.close();
        }
    }

    /**
     * Test of update method, of class ProfesorService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testUpdate_Profesor() throws SQLException {
        System.out.println("update");
        
        String sql;
        ResultSet rs;
        
        Statement stmt = con.createStatement();
        
        sql = "SELECT * FROM slapnicar.java_profesori WHERE java_profesori.korisnicko_ime='__test_ivan' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        assertEquals(true, rs.next());
        Profesor profesor = new Profesor(rs.getInt("id"), rs.getString("korisnicko_ime"),
            rs.getString("lozinka"), rs.getString("ime"), rs.getString("prezime"));
        if (! rs.isClosed()) {
            rs.close();
        }
        profesor.setLozinka("ivanovasifra2");
        profesor.setIme("Ivica");
        
        String exp_korisnicko_ime = "__test_ivan";
        String exp_lozinka = "ivanovasifra2";
        String exp_ime = "Ivica";
        String exp_prezime = "Kovačević";
        
        ProfesorService.update(profesor);
        
        sql = "SELECT * FROM slapnicar.java_profesori WHERE java_profesori.korisnicko_ime='__test_ivan' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        assertEquals(true, rs.next());
        assertEquals(exp_korisnicko_ime, rs.getString("korisnicko_ime"));
        assertEquals(exp_lozinka, rs.getString("lozinka"));
        assertEquals(exp_ime, rs.getString("ime"));
        assertEquals(exp_prezime, rs.getString("prezime"));
        if (! rs.isClosed()) {
            rs.close();
        }
        
        profesor.setLozinka("ivanovasifra");
        profesor.setIme("Ivan");
        exp_lozinka = "ivanovasifra";
        exp_ime = "Ivan";
        
        ProfesorService.update(profesor);
        
        sql = "SELECT * FROM slapnicar.java_profesori WHERE java_profesori.korisnicko_ime='__test_ivan' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        assertEquals(true, rs.next());
        assertEquals(exp_korisnicko_ime, rs.getString("korisnicko_ime"));
        assertEquals(exp_lozinka, rs.getString("lozinka"));
        assertEquals(exp_ime, rs.getString("ime"));
        assertEquals(exp_prezime, rs.getString("prezime"));
        if (! rs.isClosed()) {
            rs.close();
        }
        
        if (! stmt.isClosed()) {
            stmt.close();
        }
    }
}
