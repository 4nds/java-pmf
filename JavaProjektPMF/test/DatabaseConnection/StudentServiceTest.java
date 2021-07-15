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
public class StudentServiceTest {
    
    static Connection con;       
    
    public StudentServiceTest() {
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
     * Test of find method, of class StudentService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testFind_String_Optional() throws SQLException {
        System.out.println("find");

        String korisnicko_ime = "__test_jure";
        Optional<Connection> con_opt = Optional.of(con);
        
        String exp_korisnicko_ime = "__test_jure";
        String exp_lozinka = "jurinasifra";
        String exp_ime = "Jurica";
        String exp_prezime = "Perić";
        
        Student result = StudentService.find(korisnicko_ime, con_opt);
        
        assertEquals(exp_korisnicko_ime, result.getKorisnickoIme());
        assertEquals(exp_lozinka, result.getLozinka() );
        assertEquals(exp_ime, result.getIme());
        assertEquals(exp_prezime, result.getPrezime());
    }

    /**
     * Test of find method, of class StudentService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testFind_String() throws SQLException {
        System.out.println("find");
        
        String korisnicko_ime = "__test_jure";
        
        String exp_korisnicko_ime = "__test_jure";
        String exp_lozinka = "jurinasifra";
        String exp_ime = "Jurica";
        String exp_prezime = "Perić";        
        
        Student result = StudentService.find(korisnicko_ime);
        
        assertEquals(exp_korisnicko_ime, result.getKorisnickoIme());
        assertEquals(exp_lozinka, result.getLozinka() );
        assertEquals(exp_ime, result.getIme());
        assertEquals(exp_prezime, result.getPrezime());
    }

    /**
     * Test of findAllByKolegij method, of class StudentService.
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
        
        ArrayList<String> exp_korisnicka_imena = new ArrayList<>(
            Arrays.asList("__test_jure", "__test_ana"));
        ArrayList<String> exp_lozinke = new ArrayList<>(
            Arrays.asList("jurinasifra", "aninasifra"));
        ArrayList<String> exp_imena = new ArrayList<>(
            Arrays.asList("Jurica", "Ana"));
        ArrayList<String> exp_prezimena = new ArrayList<>(
            Arrays.asList("Perić", "Horvat"));
        
        ArrayList<Student> result = StudentService.findAllByKolegij(kolegij, con_opt);
        
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
     * Test of findAllByKolegij method, of class StudentService.
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

        ArrayList<String> exp_korisnicka_imena = new ArrayList<>(
            Arrays.asList("__test_jure", "__test_ana"));
        ArrayList<String> exp_lozinke = new ArrayList<>(
            Arrays.asList("jurinasifra", "aninasifra"));
        ArrayList<String> exp_imena = new ArrayList<>(
            Arrays.asList("Jurica", "Ana"));
        ArrayList<String> exp_prezimena = new ArrayList<>(
            Arrays.asList("Perić", "Horvat"));
        
        ArrayList<Student> result = StudentService.findAllByKolegij(kolegij);
        
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
     * Test of getAll method, of class StudentService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testGetAll_Optional() throws SQLException {
        System.out.println("getAll");
        
        Optional<Connection> con_opt = Optional.of(con);
        
        ArrayList<String> exp_korisnicka_imena = new ArrayList<>(
            Arrays.asList("__test_jure", "__test_ana", "__test_matea"));
        ArrayList<String> exp_lozinke = new ArrayList<>(
            Arrays.asList("jurinasifra", "aninasifra", "mateinasifra"));
        ArrayList<String> exp_imena = new ArrayList<>(
            Arrays.asList("Jurica", "Ana", "Matea"));
        ArrayList<String> exp_prezimena = new ArrayList<>(
            Arrays.asList("Perić", "Horvat", "Kalić"));
        
        ArrayList<Student> result = StudentService.getAll(con_opt);
        
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
     * Test of getAll method, of class StudentService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testGetAll() throws SQLException {
        System.out.println("getAll");
        
        ArrayList<String> exp_korisnicka_imena = new ArrayList<>(
            Arrays.asList("__test_jure", "__test_ana", "__test_matea"));
        ArrayList<String> exp_lozinke = new ArrayList<>(
            Arrays.asList("jurinasifra", "aninasifra", "mateinasifra"));
        ArrayList<String> exp_imena = new ArrayList<>(
            Arrays.asList("Jurica", "Ana", "Matea"));
        ArrayList<String> exp_prezimena = new ArrayList<>(
            Arrays.asList("Perić", "Horvat", "Kalić"));
        
        ArrayList<Student> result = StudentService.getAll();
        
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
     * Test of insert method, of class StudentService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testInsert_5args() throws SQLException {
        System.out.println("insert");
        
        Statement stmt = con.createStatement();
        
        String exp_korisnicko_ime = "__test_marija";
        String exp_lozinka = "marijinasifra";
        String exp_ime = "Marija";
        String exp_prezime = "Marić";
        Optional<Connection> con_opt = Optional.of(con);
        
        int jmbag = StudentService.insert(exp_korisnicko_ime, exp_lozinka, exp_ime, exp_prezime, con_opt);
        
        String sql = "SELECT * FROM slapnicar.java_studenti WHERE java_studenti.jmbag=" + Integer.toString(jmbag) + " LIMIT 1";
        stmt.execute(sql);
        ResultSet rs = stmt.getResultSet();
        assertEquals(true, rs.next());
        assertEquals(jmbag, rs.getInt("jmbag"));
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
     * Test of insert method, of class StudentService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testInsert_4args() throws SQLException {
        System.out.println("insert");
        
        Statement stmt = con.createStatement();
        
        String exp_korisnicko_ime = "__test_marija";
        String exp_lozinka = "marijinasifra";
        String exp_ime = "Marija";
        String exp_prezime = "Marić";
        
        int jmbag = StudentService.insert(exp_korisnicko_ime, exp_lozinka, exp_ime, exp_prezime);
        
        String sql = "SELECT * FROM slapnicar.java_studenti WHERE java_studenti.jmbag=" + Integer.toString(jmbag) + " LIMIT 1";
        stmt.execute(sql);
        ResultSet rs = stmt.getResultSet();
        assertEquals(true, rs.next());
        assertEquals(jmbag, rs.getInt("jmbag"));
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
     * Test of update method, of class StudentService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testUpdate_Student_Optional() throws SQLException {
        System.out.println("update");
        
        String sql;
        ResultSet rs;
        
        Statement stmt = con.createStatement();
        
        sql = "SELECT * FROM slapnicar.java_studenti WHERE java_studenti.korisnicko_ime='__test_jure' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        assertEquals(true, rs.next());
        Student student = new Student(rs.getInt("jmbag"), rs.getString("korisnicko_ime"),
            rs.getString("lozinka"), rs.getString("ime"), rs.getString("prezime"));
        if (! rs.isClosed()) {
            rs.close();
        }
        student.setLozinka("jurinasifra2");
        student.setIme("Jure");
        Optional<Connection> con_opt = Optional.of(con);
        
        String exp_korisnicko_ime = "__test_jure";
        String exp_lozinka = "jurinasifra2";
        String exp_ime = "Jure";
        String exp_prezime = "Perić";
        
        StudentService.update(student, con_opt);
        
        sql = "SELECT * FROM slapnicar.java_studenti WHERE java_studenti.korisnicko_ime='__test_jure' LIMIT 1";
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
        
        student.setLozinka("jurinasifra");
        student.setIme("Jurica");
        exp_lozinka = "jurinasifra";
        exp_ime = "Jurica";
        
        StudentService.update(student, con_opt);
        
        sql = "SELECT * FROM slapnicar.java_studenti WHERE java_studenti.korisnicko_ime='__test_jure' LIMIT 1";
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
     * Test of update method, of class StudentService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testUpdate_Student() throws SQLException {
        System.out.println("update");
        
        String sql;
        ResultSet rs;
        Statement stmt = con.createStatement();
        
        sql = "SELECT * FROM slapnicar.java_studenti WHERE java_studenti.korisnicko_ime='__test_jure' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        assertEquals(true, rs.next());
        Student student = new Student(rs.getInt("jmbag"), rs.getString("korisnicko_ime"),
            rs.getString("lozinka"), rs.getString("ime"), rs.getString("prezime"));
        if (! rs.isClosed()) {
            rs.close();
        }
        student.setLozinka("jurinasifra2");
        student.setIme("Jure");
        
        String exp_korisnicko_ime = "__test_jure";
        String exp_lozinka = "jurinasifra2";
        String exp_ime = "Jure";
        String exp_prezime = "Perić";
        
        StudentService.update(student);
        
        sql = "SELECT * FROM slapnicar.java_studenti WHERE java_studenti.korisnicko_ime='__test_jure' LIMIT 1";
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
        
        student.setLozinka("jurinasifra");
        student.setIme("Jurica");
        
        exp_lozinka = "jurinasifra";
        exp_ime = "Jurica";
        
        StudentService.update(student);
        
        sql = "SELECT * FROM slapnicar.java_studenti WHERE java_studenti.korisnicko_ime='__test_jure' LIMIT 1";
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