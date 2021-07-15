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
import java.sql.Timestamp;
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
public class ObavijestServiceTest {
    
    static Connection con;  
    
    public ObavijestServiceTest() {
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
     * Test of findAllByKolegij method, of class ObavijestService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testFindAllByKolegij_Kolegij_Optional() throws SQLException {
        System.out.println("findAllByKolegij");
        
        Statement stmt = con.createStatement();
        
        String sql = "SELECT * FROM slapnicar.java_kolegiji WHERE java_kolegiji.ime='__test_Matematika' LIMIT 1";
        stmt.execute(sql);
        ResultSet rs = stmt.getResultSet();
        Kolegij kolegij = new Kolegij(-1, "", -1, "", "");
        if (rs.next()) {
            kolegij = new Kolegij(rs.getInt("id"), rs.getString("ime"),
                rs.getInt("profesor_id"), rs.getString("opis"), rs.getString("pravila"));
        }
        if (! rs.isClosed()) {
            rs.close();
        }
        Optional<Connection> con_opt = Optional.of(con);
        
        ArrayList<Integer> exp_kolegij_ids = new ArrayList<>(
            Arrays.asList(kolegij.getId(), kolegij.getId()));
        ArrayList<String> exp_tekstovi = new ArrayList<>(Arrays.asList(
            "Na stranicama kolegija je objavljena zadaća.",
            "Na stranicama kolegija su objavljeni rezultati prvog kolokvija."
        ));
        ArrayList<Timestamp> exp_vremena = new ArrayList<>(Arrays.asList(
            Timestamp.valueOf("2021-06-15 13:38:26"),
            Timestamp.valueOf("2021-04-21 16:43:19")
        ));

        ArrayList<Obavijest> result = ObavijestService.findAllByKolegij(kolegij, con_opt);
        
        int index;
        int found = 0;
        for (int i = 0; i < result.size(); i++) {
            index = -1;
            for (int j = 0; j < exp_tekstovi.size(); j++) {
                if ( exp_tekstovi.get(j).equals(result.get(i).getTekst()) ) {
                    index = j;
                    found++;
                }
            }
            if (index >= 0) {
                assertEquals(exp_kolegij_ids.get(index).intValue(), result.get(i).getKolegijId());
                assertEquals(exp_tekstovi.get(index), result.get(i).getTekst());
                assertEquals(exp_vremena.get(index), result.get(i).getVrijeme());
            }
        }
        assertEquals(exp_tekstovi.size(), found);
        
        if (! stmt.isClosed()) {
            stmt.close();
        }
    }

    /**
     * Test of findAllByKolegij method, of class ObavijestService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testFindAllByKolegij_Kolegij() throws SQLException {
        System.out.println("findAllByKolegij");
        
        Statement stmt = con.createStatement();
        
        String sql = "SELECT * FROM slapnicar.java_kolegiji WHERE java_kolegiji.ime='__test_Matematika' LIMIT 1";
        stmt.execute(sql);
        ResultSet rs = stmt.getResultSet();
        Kolegij kolegij = new Kolegij(-1, "", -1, "", "");
        if (rs.next()) {
            kolegij = new Kolegij(rs.getInt("id"), rs.getString("ime"),
                rs.getInt("profesor_id"), rs.getString("opis"), rs.getString("pravila"));
        }
        if (! rs.isClosed()) {
            rs.close();
        }
        
        ArrayList<Integer> exp_kolegij_ids = new ArrayList<>(
            Arrays.asList(kolegij.getId(), kolegij.getId()));
        ArrayList<String> exp_tekstovi = new ArrayList<>(Arrays.asList(
            "Na stranicama kolegija je objavljena zadaća.",
            "Na stranicama kolegija su objavljeni rezultati prvog kolokvija."
        ));
        ArrayList<Timestamp> exp_vremena = new ArrayList<>(Arrays.asList(
            Timestamp.valueOf("2021-06-15 13:38:26"),
            Timestamp.valueOf("2021-04-21 16:43:19")
        ));

        ArrayList<Obavijest> result = ObavijestService.findAllByKolegij(kolegij);
        
        int index;
        int found = 0;
        for (int i = 0; i < result.size(); i++) {
            index = -1;
            for (int j = 0; j < exp_tekstovi.size(); j++) {
                if ( exp_tekstovi.get(j).equals(result.get(i).getTekst()) ) {
                    index = j;
                    found++;
                }
            }
            if (index >= 0) {
                assertEquals(exp_kolegij_ids.get(index).intValue(), result.get(i).getKolegijId());
                assertEquals(exp_tekstovi.get(index), result.get(i).getTekst());
                assertEquals(exp_vremena.get(index), result.get(i).getVrijeme());
            }
        }
        assertEquals(exp_tekstovi.size(), found);
        
        if (! stmt.isClosed()) {
            stmt.close();
        }
    }

    /**
     * Test of getAll method, of class ObavijestService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testGetAll_Optional() throws SQLException {
        System.out.println("getAll");
        
        Optional<Connection> con_opt = Optional.of(con);
        
        ArrayList<String> exp_tekstovi = new ArrayList<>(Arrays.asList(
            "Na stranicama kolegija je objavljena zadaća.",
            "Na stranicama kolegija su objavljeni rezultati prvog kolokvija.",
            "18.6.2021. će se održati zanimljiva prezentacija o Markovljevim lancima."
        ));
        ArrayList<Timestamp> exp_vremena = new ArrayList<>(Arrays.asList(
            Timestamp.valueOf("2021-06-15 13:38:26"),
            Timestamp.valueOf("2021-04-21 16:43:19"),
            Timestamp.valueOf("2021-05-06 10:43:07")
        ));
        
        ArrayList<Obavijest> result = ObavijestService.getAll(con_opt);
        
        int index;
        int found = 0;
        for (int i = 0; i < result.size(); i++) {
            index = -1;
            for (int j = 0; j < exp_tekstovi.size(); j++) {
                if ( exp_tekstovi.get(j).equals(result.get(i).getTekst()) ) {
                    index = j;
                    found++;
                }
            }
            if (index >= 0) {
                assertEquals(exp_tekstovi.get(index), result.get(i).getTekst());
                assertEquals(exp_vremena.get(index), result.get(i).getVrijeme());
            }
        }
        assertEquals(exp_tekstovi.size(), found);
    }

    /**
     * Test of getAll method, of class ObavijestService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testGetAll() throws SQLException {
        System.out.println("getAll");
        
        ArrayList<String> exp_tekstovi = new ArrayList<>(Arrays.asList(
            "Na stranicama kolegija je objavljena zadaća.",
            "Na stranicama kolegija su objavljeni rezultati prvog kolokvija.",
            "18.6.2021. će se održati zanimljiva prezentacija o Markovljevim lancima."
        ));
        ArrayList<Timestamp> exp_vremena = new ArrayList<>(Arrays.asList(
            Timestamp.valueOf("2021-06-15 13:38:26"),
            Timestamp.valueOf("2021-04-21 16:43:19"),
            Timestamp.valueOf("2021-05-06 10:43:07")
        ));
        
        ArrayList<Obavijest> result = ObavijestService.getAll();
        
        int index;
        int found = 0;
        for (int i = 0; i < result.size(); i++) {
            index = -1;
            for (int j = 0; j < exp_tekstovi.size(); j++) {
                if ( exp_tekstovi.get(j).equals(result.get(i).getTekst()) ) {
                    index = j;
                    found++;
                }
            }
            if (index >= 0) {
                assertEquals(exp_tekstovi.get(index), result.get(i).getTekst());
                assertEquals(exp_vremena.get(index), result.get(i).getVrijeme());
            }
        }
        assertEquals(exp_tekstovi.size(), found);
    }

    /**
     * Test of insert method, of class ObavijestService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testInsert_4args() throws SQLException {
        System.out.println("insert");
        
        Statement stmt = con.createStatement();
        
        Kolegij exp_kolegij = new Kolegij(123, "", 0, "", "");
        String exp_tekst = "25.05.2021. će se pisati kratki test u predavaonici A101.";
        Timestamp exp_vrijeme = Timestamp.valueOf("2021-05-18 18:22:41");
        Optional<Connection> con_opt = Optional.of(con);
        
        int id = ObavijestService.insert(exp_kolegij, exp_tekst, exp_vrijeme, con_opt);
        
        String sql = "SELECT * FROM slapnicar.java_obavijesti WHERE java_obavijesti.id=" + Integer.toString(id) + " LIMIT 1";
        stmt.execute(sql);
        ResultSet rs = stmt.getResultSet();
        assertEquals(true, rs.next());
        assertEquals(id, rs.getInt("id"));
        assertEquals(exp_kolegij.getId(), rs.getInt("kolegij_id"));
        assertEquals(exp_tekst, rs.getString("tekst"));
        assertEquals(exp_vrijeme, rs.getTimestamp("vrijeme"));
        if (! rs.isClosed()) {
            rs.close();
        }
        if (! stmt.isClosed()) {
            stmt.close();
        }
    }

    /**
     * Test of insert method, of class ObavijestService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testInsert_3args() throws SQLException {
        System.out.println("insert");
        
        Statement stmt = con.createStatement();
        
        Kolegij exp_kolegij = new Kolegij(123, "", 0, "", "");
        String exp_tekst = "25.05.2021. će se pisati kratki test u predavaonici A101.";
        Timestamp exp_vrijeme = Timestamp.valueOf("2021-05-18 18:22:41");
        
        int id = ObavijestService.insert(exp_kolegij, exp_tekst, exp_vrijeme);
        
        String sql = "SELECT * FROM slapnicar.java_obavijesti WHERE java_obavijesti.id=" + Integer.toString(id) + " LIMIT 1";
        stmt.execute(sql);
        ResultSet rs = stmt.getResultSet();
        assertEquals(true, rs.next());
        assertEquals(id, rs.getInt("id"));
        assertEquals(exp_kolegij.getId(), rs.getInt("kolegij_id"));
        assertEquals(exp_tekst, rs.getString("tekst"));
        assertEquals(exp_vrijeme, rs.getTimestamp("vrijeme"));
        if (! rs.isClosed()) {
            rs.close();
        }
        if (! stmt.isClosed()) {
            stmt.close();
        }
    }

    /**
     * Test of update method, of class ObavijestService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testUpdate_Obavijest_Optional() throws SQLException {
        System.out.println("update");

        String sql;
        ResultSet rs;
        
        Statement stmt = con.createStatement();
        
        sql = "SELECT * FROM slapnicar.java_obavijesti WHERE java_obavijesti.tekst='Na stranicama kolegija je objavljena zadaća.' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        assertEquals(true, rs.next());
        Obavijest obavijest = new Obavijest(rs.getInt("id"), rs.getInt("kolegij_id"),
            rs.getString("tekst"), rs.getTimestamp("vrijeme"));
        if (! rs.isClosed()) {
            rs.close();
        }
        obavijest.setTekst("Na stranicama kolegija je objavljena druga zadaća.");
        obavijest.setVrijeme(Timestamp.valueOf("2021-07-05 14:18:33"));
        Optional<Connection> con_opt = Optional.of(con);
        
        int exp_kolegij_id = obavijest.getKolegijId();
        String exp_tekst = "Na stranicama kolegija je objavljena druga zadaća.";
        Timestamp exp_vrijeme = Timestamp.valueOf("2021-07-05 14:18:33");
        
        ObavijestService.update(obavijest, con_opt);
        
        sql = "SELECT * FROM slapnicar.java_obavijesti WHERE java_obavijesti.tekst='Na stranicama kolegija je objavljena druga zadaća.' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        assertEquals(true, rs.next());
        assertEquals(exp_kolegij_id, rs.getInt("kolegij_id"));
        assertEquals(exp_tekst, rs.getString("tekst"));
        assertEquals(exp_vrijeme, rs.getTimestamp("vrijeme"));
        if (! rs.isClosed()) {
            rs.close();
        }
        
        obavijest.setTekst("Na stranicama kolegija je objavljena zadaća.");
        obavijest.setVrijeme(Timestamp.valueOf("2021-06-15 13:38:26"));
        exp_tekst = "Na stranicama kolegija je objavljena zadaća.";
        exp_vrijeme = Timestamp.valueOf("2021-06-15 13:38:26");
        
        ObavijestService.update(obavijest, con_opt);
        
        sql = "SELECT * FROM slapnicar.java_obavijesti WHERE java_obavijesti.tekst='Na stranicama kolegija je objavljena zadaća.' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        assertEquals(true, rs.next());
        assertEquals(exp_kolegij_id, rs.getInt("kolegij_id"));
        assertEquals(exp_tekst, rs.getString("tekst"));
        assertEquals(exp_vrijeme, rs.getTimestamp("vrijeme"));
        if (! rs.isClosed()) {
            rs.close();
        }
        
        if (! stmt.isClosed()) {
            stmt.close();
        }
    }

    /**
     * Test of update method, of class ObavijestService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testUpdate_Obavijest() throws SQLException {
        System.out.println("update");
        
        String sql;
        ResultSet rs;
        
        Statement stmt = con.createStatement();
        
        sql = "SELECT * FROM slapnicar.java_obavijesti WHERE java_obavijesti.tekst='Na stranicama kolegija je objavljena zadaća.' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        assertEquals(true, rs.next());
        Obavijest obavijest = new Obavijest(rs.getInt("id"), rs.getInt("kolegij_id"),
            rs.getString("tekst"), rs.getTimestamp("vrijeme"));
        if (! rs.isClosed()) {
            rs.close();
        }
        obavijest.setTekst("Na stranicama kolegija je objavljena druga zadaća.");
        obavijest.setVrijeme(Timestamp.valueOf("2021-07-05 14:18:33"));
        
        int exp_kolegij_id = obavijest.getKolegijId();
        String exp_tekst = "Na stranicama kolegija je objavljena druga zadaća.";
        Timestamp exp_vrijeme = Timestamp.valueOf("2021-07-05 14:18:33");
        
        ObavijestService.update(obavijest);
        
        sql = "SELECT * FROM slapnicar.java_obavijesti WHERE java_obavijesti.tekst='Na stranicama kolegija je objavljena druga zadaća.' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        assertEquals(true, rs.next());
        assertEquals(exp_kolegij_id, rs.getInt("kolegij_id"));
        assertEquals(exp_tekst, rs.getString("tekst"));
        assertEquals(exp_vrijeme, rs.getTimestamp("vrijeme"));
        if (! rs.isClosed()) {
            rs.close();
        }
        
        obavijest.setTekst("Na stranicama kolegija je objavljena zadaća.");
        obavijest.setVrijeme(Timestamp.valueOf("2021-06-15 13:38:26"));
        exp_tekst = "Na stranicama kolegija je objavljena zadaća.";
        exp_vrijeme = Timestamp.valueOf("2021-06-15 13:38:26");
        
        ObavijestService.update(obavijest);
        
        sql = "SELECT * FROM slapnicar.java_obavijesti WHERE java_obavijesti.tekst='Na stranicama kolegija je objavljena zadaća.' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        assertEquals(true, rs.next());
        assertEquals(exp_kolegij_id, rs.getInt("kolegij_id"));
        assertEquals(exp_tekst, rs.getString("tekst"));
        assertEquals(exp_vrijeme, rs.getTimestamp("vrijeme"));
        if (! rs.isClosed()) {
            rs.close();
        }
        
        if (! stmt.isClosed()) {
            stmt.close();
        }
    }
    
}
