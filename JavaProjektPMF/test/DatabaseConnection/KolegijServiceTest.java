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
public class KolegijServiceTest {
    
    static Connection con;
    
    public KolegijServiceTest() {
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
     * Test of find method, of class KolegijService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testFind_String_Optional() throws SQLException {
        System.out.println("find");
        
        Statement stmt = con.createStatement();
        
        String ime = "__test_Matematika";
        Optional<Connection> con_opt = Optional.of(con);
        
        String exp_ime = "__test_Matematika";
        String sql = "SELECT * FROM slapnicar.java_profesori WHERE java_profesori.korisnicko_ime='__test_ivan' LIMIT 1";
        stmt.execute(sql);
        ResultSet rs = stmt.getResultSet();
        int exp_profesor_id = rs.next() ? rs.getInt("id") : -1;
        if (! rs.isClosed()) {
            rs.close();
        }
        String exp_opis = "Realni brojevi i funkcije realne varijable. Limes niza. Limes realne funkcije realne varijable. Derivacija funkcije i primjene. Integralni račun i primjene. Matrični račun, determinante i rješavanje sustava linearnih jednadžbi.";
        String exp_pravila = "Elementi ocjenjivanja su dva kolokvija po 30 bodova, kratke provjere znanja po 10 bodova i završni ispit koji nosi 20 bodova.";
        
        Kolegij result = KolegijService.find(ime, con_opt);
        
        assertEquals(exp_ime, result.getIme());
        assertEquals(exp_profesor_id, result.getProfesorId());
        assertEquals(exp_opis, result.getOpis());
        assertEquals(exp_pravila, result.getPravila());
        
        if (! stmt.isClosed()) {
            stmt.close();
        }
    }

    /**
     * Test of find method, of class KolegijService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testFind_String() throws SQLException {
        Statement stmt = con.createStatement();
        
        String ime = "__test_Matematika";
        
        String exp_ime = "__test_Matematika";
        String sql = "SELECT * FROM slapnicar.java_profesori WHERE java_profesori.korisnicko_ime='__test_ivan' LIMIT 1";
        stmt.execute(sql);
        ResultSet rs = stmt.getResultSet();
        int exp_profesor_id = rs.next() ? rs.getInt("id") : -1;
        if (! rs.isClosed()) {
            rs.close();
        }
        String exp_opis = "Realni brojevi i funkcije realne varijable. Limes niza. Limes realne funkcije realne varijable. Derivacija funkcije i primjene. Integralni račun i primjene. Matrični račun, determinante i rješavanje sustava linearnih jednadžbi.";
        String exp_pravila = "Elementi ocjenjivanja su dva kolokvija po 30 bodova, kratke provjere znanja po 10 bodova i završni ispit koji nosi 20 bodova.";
        
        Kolegij result = KolegijService.find(ime);
        
        assertEquals(exp_ime, result.getIme());
        assertEquals(exp_profesor_id, result.getProfesorId());
        assertEquals(exp_opis, result.getOpis());
        assertEquals(exp_pravila, result.getPravila());
        
        if (! stmt.isClosed()) {
            stmt.close();
        }
    }

    /**
     * Test of findAllByStudent method, of class KolegijService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testFindAllByStudent_Student_Optional() throws SQLException {
        System.out.println("findAllByStudent");
        
        Statement stmt = con.createStatement();
        
        String sql = "SELECT * FROM slapnicar.java_studenti WHERE java_studenti.korisnicko_ime='__test_ana' LIMIT 1";
        stmt.execute(sql);
        ResultSet rs = stmt.getResultSet();
        Student student = new Student(-1, "", "", "", "");
        if (rs.next()) {
            student = new Student(rs.getInt("jmbag"), rs.getString("korisnicko_ime"),
                rs.getString("lozinka"), rs.getString("ime"), rs.getString("prezime"));
        }
        if (! rs.isClosed()) {
            rs.close();
        }
        
        Optional<Connection> con_opt = Optional.of(con);
        
        ArrayList<String> exp_imena = new ArrayList<>(
            Arrays.asList("__test_Matematika", "__test_Statistika"));
        ArrayList<String> exp_opisi = new ArrayList<>(Arrays.asList(
            "Realni brojevi i funkcije realne varijable. Limes niza. Limes realne funkcije realne varijable. Derivacija funkcije i primjene. Integralni račun i primjene. Matrični račun, determinante i rješavanje sustava linearnih jednadžbi.",
            "Statistika je matematička disciplina koja proučava načine sakupljanja, sažimanja i prikazivanja zaključaka iz nekih podataka. Primjenjuje se u mnogim strukama, kao i u svakodnevnom životu."
        ));
        ArrayList<String> exp_pravila = new ArrayList<>(Arrays.asList(
            "Elementi ocjenjivanja su dva kolokvija po 30 bodova, kratke provjere znanja po 10 bodova i završni ispit koji nosi 20 bodova.",
            "Elementi ocjenjivanja su dva kolokvija po 40 bodova te usmeni ispit koji nosi 20 bodova."
        ));
        
        ArrayList<Kolegij> result = KolegijService.findAllByStudent(student, con_opt);
        
        int index;
        int found = 0;
        for (int i = 0; i < result.size(); i++) {
            index = -1;
            for (int j = 0; j < exp_imena.size(); j++) {
                if ( exp_imena.get(j).equals(result.get(i).getIme()) ) {
                    index = j;
                    found++;
                }
            }
            if (index >= 0) {
                assertEquals(exp_imena.get(index), result.get(i).getIme());
                assertEquals(exp_opisi.get(index), result.get(i).getOpis());
                assertEquals(exp_pravila.get(index), result.get(i).getPravila());
            }
        }
        assertEquals(exp_imena.size(), found);
        
        if (! stmt.isClosed()) {
            stmt.close();
        }
    }

    /**
     * Test of findAllByStudent method, of class KolegijService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testFindAllByStudent_Student() throws SQLException {
        System.out.println("findAllByStudent");
        
        Statement stmt = con.createStatement();
        
        String sql = "SELECT * FROM slapnicar.java_studenti WHERE java_studenti.korisnicko_ime='__test_ana' LIMIT 1";
        stmt.execute(sql);
        ResultSet rs = stmt.getResultSet();
        Student student = new Student(-1, "", "", "", "");
        if (rs.next()) {
            student = new Student(rs.getInt("jmbag"), rs.getString("korisnicko_ime"),
                rs.getString("lozinka"), rs.getString("ime"), rs.getString("prezime"));
        }
        if (! rs.isClosed()) {
            rs.close();
        }
        
        ArrayList<String> exp_imena = new ArrayList<>(
            Arrays.asList("__test_Matematika", "__test_Statistika"));
        ArrayList<String> exp_opisi = new ArrayList<>(Arrays.asList(
            "Realni brojevi i funkcije realne varijable. Limes niza. Limes realne funkcije realne varijable. Derivacija funkcije i primjene. Integralni račun i primjene. Matrični račun, determinante i rješavanje sustava linearnih jednadžbi.",
            "Statistika je matematička disciplina koja proučava načine sakupljanja, sažimanja i prikazivanja zaključaka iz nekih podataka. Primjenjuje se u mnogim strukama, kao i u svakodnevnom životu."
        ));
        ArrayList<String> exp_pravila = new ArrayList<>(Arrays.asList(
            "Elementi ocjenjivanja su dva kolokvija po 30 bodova, kratke provjere znanja po 10 bodova i završni ispit koji nosi 20 bodova.",
            "Elementi ocjenjivanja su dva kolokvija po 40 bodova te usmeni ispit koji nosi 20 bodova."
        ));
        
        ArrayList<Kolegij> result = KolegijService.findAllByStudent(student);
        
        int index;
        int found = 0;
        for (int i = 0; i < result.size(); i++) {
            index = -1;
            for (int j = 0; j < exp_imena.size(); j++) {
                if ( exp_imena.get(j).equals(result.get(i).getIme()) ) {
                    index = j;
                    found++;
                }
            }
            if (index >= 0) {
                assertEquals(exp_imena.get(index), result.get(i).getIme());
                assertEquals(exp_opisi.get(index), result.get(i).getOpis());
                assertEquals(exp_pravila.get(index), result.get(i).getPravila());
            }
        }
        assertEquals(exp_imena.size(), found);
        
        if (! stmt.isClosed()) {
            stmt.close();
        }
    }

    /**
     * Test of findAllByProfesor method, of class KolegijService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testFindAllByProfesor_Profesor_Optional() throws SQLException {
        System.out.println("findAllByProfesor");
        
        Statement stmt = con.createStatement();
        
        String sql = "SELECT * FROM slapnicar.java_profesori WHERE java_profesori.korisnicko_ime='__test_ivan' LIMIT 1";
        stmt.execute(sql);
        ResultSet rs = stmt.getResultSet();
        Profesor profesor = new Profesor(-1, "", "", "", "");
        if (rs.next()) {
            profesor = new Profesor(rs.getInt("id"), rs.getString("korisnicko_ime"),
                rs.getString("lozinka"), rs.getString("ime"), rs.getString("prezime"));
        }
        if (! rs.isClosed()) {
            rs.close();
        }
        
        Optional<Connection> con_opt = Optional.of(con);
        
        ArrayList<String> exp_imena = new ArrayList<>(
            Arrays.asList("__test_Matematika"));
        ArrayList<String> exp_opisi = new ArrayList<>(Arrays.asList(
            "Realni brojevi i funkcije realne varijable. Limes niza. Limes realne funkcije realne varijable. Derivacija funkcije i primjene. Integralni račun i primjene. Matrični račun, determinante i rješavanje sustava linearnih jednadžbi."
        ));
        ArrayList<String> exp_pravila = new ArrayList<>(Arrays.asList(
            "Elementi ocjenjivanja su dva kolokvija po 30 bodova, kratke provjere znanja po 10 bodova i završni ispit koji nosi 20 bodova."
        ));
        
        ArrayList<Kolegij> result = KolegijService.findAllByProfesor(profesor, con_opt);
        
        int index;
        int found = 0;
        for (int i = 0; i < result.size(); i++) {
            index = -1;
            for (int j = 0; j < exp_imena.size(); j++) {
                if ( exp_imena.get(j).equals(result.get(i).getIme()) ) {
                    index = j;
                    found++;
                }
            }
            if (index >= 0) {
                assertEquals(exp_imena.get(index), result.get(i).getIme());
                assertEquals(exp_opisi.get(index), result.get(i).getOpis());
                assertEquals(exp_pravila.get(index), result.get(i).getPravila());
            }
        }
        assertEquals(exp_imena.size(), found);
        
        if (! stmt.isClosed()) {
            stmt.close();
        }
    }

    /**
     * Test of findAllByProfesor method, of class KolegijService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testFindAllByProfesor_Profesor() throws SQLException {
        System.out.println("findAllByProfesor");
        
        Statement stmt = con.createStatement();
        
        String sql = "SELECT * FROM slapnicar.java_profesori WHERE java_profesori.korisnicko_ime='__test_ivan' LIMIT 1";
        stmt.execute(sql);
        ResultSet rs = stmt.getResultSet();
        Profesor profesor = new Profesor(-1, "", "", "", "");
        if (rs.next()) {
            profesor = new Profesor(rs.getInt("id"), rs.getString("korisnicko_ime"),
                rs.getString("lozinka"), rs.getString("ime"), rs.getString("prezime"));
        }
        if (! rs.isClosed()) {
            rs.close();
        }
        
        ArrayList<String> exp_imena = new ArrayList<>(
            Arrays.asList("__test_Matematika"));
        ArrayList<String> exp_opisi = new ArrayList<>(Arrays.asList(
            "Realni brojevi i funkcije realne varijable. Limes niza. Limes realne funkcije realne varijable. Derivacija funkcije i primjene. Integralni račun i primjene. Matrični račun, determinante i rješavanje sustava linearnih jednadžbi."
        ));
        ArrayList<String> exp_pravila = new ArrayList<>(Arrays.asList(
            "Elementi ocjenjivanja su dva kolokvija po 30 bodova, kratke provjere znanja po 10 bodova i završni ispit koji nosi 20 bodova."
        ));
        
        ArrayList<Kolegij> result = KolegijService.findAllByProfesor(profesor);
        
        int index;
        int found = 0;
        for (int i = 0; i < result.size(); i++) {
            index = -1;
            for (int j = 0; j < exp_imena.size(); j++) {
                if ( exp_imena.get(j).equals(result.get(i).getIme()) ) {
                    index = j;
                    found++;
                }
            }
            if (index >= 0) {
                assertEquals(exp_imena.get(index), result.get(i).getIme());
                assertEquals(exp_opisi.get(index), result.get(i).getOpis());
                assertEquals(exp_pravila.get(index), result.get(i).getPravila());
            }
        }
        assertEquals(exp_imena.size(), found);
        
        if (! stmt.isClosed()) {
            stmt.close();
        }
    }

    /**
     * Test of findAllByObavijesti method, of class KolegijService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testFindAllByObavijesti_ArrayList_Optional() throws SQLException {
        System.out.println("findAllByObavijesti");
        
        Statement stmt = con.createStatement();
        String sql;
        ResultSet rs;
        
        ArrayList<Obavijest> obavijesti = new ArrayList<>();
        sql = "SELECT * FROM slapnicar.java_obavijesti WHERE java_obavijesti.tekst='Na stranicama kolegija je objavljena zadaća.' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        if (rs.next()) {
            obavijesti.add(new Obavijest(rs.getInt("id"), rs.getInt("kolegij_id"),
                rs.getString("tekst"), rs.getTimestamp("vrijeme")));
        }
        if (! rs.isClosed()) {
            rs.close();
        }
        sql = "SELECT * FROM slapnicar.java_obavijesti WHERE java_obavijesti.tekst='Na stranicama kolegija su objevljeni rezultati prvog kolokvija.' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        if (rs.next()) {
            obavijesti.add(new Obavijest(rs.getInt("id"), rs.getInt("kolegij_id"),
                rs.getString("tekst"), rs.getTimestamp("vrijeme")));
        }
        if (! rs.isClosed()) {
            rs.close();
        }
        sql = "SELECT * FROM slapnicar.java_obavijesti WHERE java_obavijesti.tekst='18.6.2021. će se održati zanimljiva prezentacija o Markovljevim lancima.' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        if (rs.next()) {
            obavijesti.add(new Obavijest(rs.getInt("id"), rs.getInt("kolegij_id"),
                rs.getString("tekst"), rs.getTimestamp("vrijeme")));
        }
        if (! rs.isClosed()) {
            rs.close();
        }
        
        Optional<Connection> con_opt = Optional.of(con);
        
        ArrayList<String> exp_imena = new ArrayList<>(
            Arrays.asList("__test_Matematika", "__test_Statistika"));
        ArrayList<String> exp_opisi = new ArrayList<>(Arrays.asList(
            "Realni brojevi i funkcije realne varijable. Limes niza. Limes realne funkcije realne varijable. Derivacija funkcije i primjene. Integralni račun i primjene. Matrični račun, determinante i rješavanje sustava linearnih jednadžbi.",
            "Statistika je matematička disciplina koja proučava načine sakupljanja, sažimanja i prikazivanja zaključaka iz nekih podataka. Primjenjuje se u mnogim strukama, kao i u svakodnevnom životu."
        ));
        ArrayList<String> exp_pravila = new ArrayList<>(Arrays.asList(
            "Elementi ocjenjivanja su dva kolokvija po 30 bodova, kratke provjere znanja po 10 bodova i završni ispit koji nosi 20 bodova.",
            "Elementi ocjenjivanja su dva kolokvija po 40 bodova te usmeni ispit koji nosi 20 bodova."
        ));
        
        ArrayList<Kolegij> result = KolegijService.findAllByObavijesti(obavijesti, con_opt);
        
        int index;
        ArrayList<String> found = new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            index = -1;
            for (int j = 0; j < exp_imena.size(); j++) {
                if ( exp_imena.get(j).equals(result.get(i).getIme()) ) {
                    index = j;
                    if (! found.contains(exp_imena.get(j)) ) {
                        found.add(exp_imena.get(j));
                    }
                }
            }
            if (index >= 0) {
                assertEquals(exp_imena.get(index), result.get(i).getIme());
                assertEquals(exp_opisi.get(index), result.get(i).getOpis());
                assertEquals(exp_pravila.get(index), result.get(i).getPravila());
            }
        }
        assertEquals(exp_imena.size(), found.size());
        
        if (! stmt.isClosed()) {
            stmt.close();
        }
    }

    /**
     * Test of findAllByObavijesti method, of class KolegijService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testFindAllByObavijesti_ArrayList() throws SQLException {
        System.out.println("findAllByObavijesti");
        
        Statement stmt = con.createStatement();
        String sql;
        ResultSet rs;
        
        ArrayList<Obavijest> obavijesti = new ArrayList<>();
        sql = "SELECT * FROM slapnicar.java_obavijesti WHERE java_obavijesti.tekst='Na stranicama kolegija je objavljena zadaća.' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        if (rs.next()) {
            obavijesti.add(new Obavijest(rs.getInt("id"), rs.getInt("kolegij_id"),
                rs.getString("tekst"), rs.getTimestamp("vrijeme")));
        }
        if (! rs.isClosed()) {
            rs.close();
        }
        sql = "SELECT * FROM slapnicar.java_obavijesti WHERE java_obavijesti.tekst='Na stranicama kolegija su objevljeni rezultati prvog kolokvija.' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        if (rs.next()) {
            obavijesti.add(new Obavijest(rs.getInt("id"), rs.getInt("kolegij_id"),
                rs.getString("tekst"), rs.getTimestamp("vrijeme")));
        }
        if (! rs.isClosed()) {
            rs.close();
        }
        sql = "SELECT * FROM slapnicar.java_obavijesti WHERE java_obavijesti.tekst='18.6.2021. će se održati zanimljiva prezentacija o Markovljevim lancima.' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        if (rs.next()) {
            obavijesti.add(new Obavijest(rs.getInt("id"), rs.getInt("kolegij_id"),
                rs.getString("tekst"), rs.getTimestamp("vrijeme")));
        }
        if (! rs.isClosed()) {
            rs.close();
        }
        
        ArrayList<String> exp_imena = new ArrayList<>(
            Arrays.asList("__test_Matematika", "__test_Statistika"));
        ArrayList<String> exp_opisi = new ArrayList<>(Arrays.asList(
            "Realni brojevi i funkcije realne varijable. Limes niza. Limes realne funkcije realne varijable. Derivacija funkcije i primjene. Integralni račun i primjene. Matrični račun, determinante i rješavanje sustava linearnih jednadžbi.",
            "Statistika je matematička disciplina koja proučava načine sakupljanja, sažimanja i prikazivanja zaključaka iz nekih podataka. Primjenjuje se u mnogim strukama, kao i u svakodnevnom životu."
        ));
        ArrayList<String> exp_pravila = new ArrayList<>(Arrays.asList(
            "Elementi ocjenjivanja su dva kolokvija po 30 bodova, kratke provjere znanja po 10 bodova i završni ispit koji nosi 20 bodova.",
            "Elementi ocjenjivanja su dva kolokvija po 40 bodova te usmeni ispit koji nosi 20 bodova."
        ));
        
        ArrayList<Kolegij> result = KolegijService.findAllByObavijesti(obavijesti);
        
        int index;
        ArrayList<String> found = new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            index = -1;
            for (int j = 0; j < exp_imena.size(); j++) {
                if ( exp_imena.get(j).equals(result.get(i).getIme()) ) {
                    index = j;
                    if (! found.contains(exp_imena.get(j)) ) {
                        found.add(exp_imena.get(j));
                    }
                }
            }
            if (index >= 0) {
                assertEquals(exp_imena.get(index), result.get(i).getIme());
                assertEquals(exp_opisi.get(index), result.get(i).getOpis());
                assertEquals(exp_pravila.get(index), result.get(i).getPravila());
            }
        }
        assertEquals(exp_imena.size(), found.size());
        
        if (! stmt.isClosed()) {
            stmt.close();
        }
    }

    /**
     * Test of getAll method, of class KolegijService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testGetAll_Optional() throws SQLException {
        System.out.println("getAll");
        
        Optional<Connection> con_opt = Optional.of(con);
        
        ArrayList<String> exp_imena = new ArrayList<>(
            Arrays.asList("__test_Matematika", "__test_Statistika"));
        ArrayList<String> exp_opisi = new ArrayList<>(Arrays.asList(
            "Realni brojevi i funkcije realne varijable. Limes niza. Limes realne funkcije realne varijable. Derivacija funkcije i primjene. Integralni račun i primjene. Matrični račun, determinante i rješavanje sustava linearnih jednadžbi.",
            "Statistika je matematička disciplina koja proučava načine sakupljanja, sažimanja i prikazivanja zaključaka iz nekih podataka. Primjenjuje se u mnogim strukama, kao i u svakodnevnom životu."
        ));
        ArrayList<String> exp_pravila = new ArrayList<>(Arrays.asList(
            "Elementi ocjenjivanja su dva kolokvija po 30 bodova, kratke provjere znanja po 10 bodova i završni ispit koji nosi 20 bodova.",
            "Elementi ocjenjivanja su dva kolokvija po 40 bodova te usmeni ispit koji nosi 20 bodova."
        ));
        
        ArrayList<Kolegij> result = KolegijService.getAll(con_opt);
        
        int index;
        int found = 0;
        for (int i = 0; i < result.size(); i++) {
            index = -1;
            for (int j = 0; j < exp_imena.size(); j++) {
                if ( exp_imena.get(j).equals(result.get(i).getIme()) ) {
                    index = j;
                    found++;
                }
            }
            if (index >= 0) {
                assertEquals(exp_imena.get(index), result.get(i).getIme());
                assertEquals(exp_opisi.get(index), result.get(i).getOpis());
                assertEquals(exp_pravila.get(index), result.get(i).getPravila());
            }
        }
        assertEquals(exp_imena.size(), found);
    }

    /**
     * Test of getAll method, of class KolegijService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testGetAll() throws SQLException {
        System.out.println("getAll");
        
        ArrayList<String> exp_imena = new ArrayList<>(
            Arrays.asList("__test_Matematika", "__test_Statistika"));
        ArrayList<String> exp_opisi = new ArrayList<>(Arrays.asList(
            "Realni brojevi i funkcije realne varijable. Limes niza. Limes realne funkcije realne varijable. Derivacija funkcije i primjene. Integralni račun i primjene. Matrični račun, determinante i rješavanje sustava linearnih jednadžbi.",
            "Statistika je matematička disciplina koja proučava načine sakupljanja, sažimanja i prikazivanja zaključaka iz nekih podataka. Primjenjuje se u mnogim strukama, kao i u svakodnevnom životu."
        ));
        ArrayList<String> exp_pravila = new ArrayList<>(Arrays.asList(
            "Elementi ocjenjivanja su dva kolokvija po 30 bodova, kratke provjere znanja po 10 bodova i završni ispit koji nosi 20 bodova.",
            "Elementi ocjenjivanja su dva kolokvija po 40 bodova te usmeni ispit koji nosi 20 bodova."
        ));
        
        ArrayList<Kolegij> result = KolegijService.getAll();
        
        int index;
        int found = 0;
        for (int i = 0; i < result.size(); i++) {
            index = -1;
            for (int j = 0; j < exp_imena.size(); j++) {
                if ( exp_imena.get(j).equals(result.get(i).getIme()) ) {
                    index = j;
                    found++;
                }
            }
            if (index >= 0) {
                assertEquals(exp_imena.get(index), result.get(i).getIme());
                assertEquals(exp_opisi.get(index), result.get(i).getOpis());
                assertEquals(exp_pravila.get(index), result.get(i).getPravila());
            }
        }
        assertEquals(exp_imena.size(), found);
    }

    /**
     * Test of insert method, of class KolegijService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testInsert_5args() throws SQLException {
        System.out.println("insert");
        
        Statement stmt = con.createStatement();
        
        String exp_ime = "__test_Programiranje";
        Profesor exp_profesor = new Profesor(123, "", "", "", "");
        String exp_opis = "Programiranje je pisanje uputa računalu što i kako učiniti, a izvodi se u nekom od programskih jezika. Stvaranje programa sadrži u sebi pojedine elemente dizajna, umjetnosti, znanosti, matematike kao i inžinjeringa.";
        String exp_pravila = "Elementi ocjenjivanja su dva kolokvija po 40 bodova te 4 kratka testa po 5 bodova.";
        Optional<Connection> con_opt = Optional.of(con);
        
        int id = KolegijService.insert(exp_ime, exp_profesor, exp_opis, exp_pravila, con_opt);
        
        String sql = "SELECT * FROM slapnicar.java_kolegiji WHERE java_kolegiji.id=" + Integer.toString(id) + " LIMIT 1";
        stmt.execute(sql);
        ResultSet rs = stmt.getResultSet();
        assertEquals(true, rs.next());
        assertEquals(id, rs.getInt("id"));
        assertEquals(exp_ime, rs.getString("ime"));
        assertEquals(exp_profesor.getId(), rs.getInt("profesor_id"));
        assertEquals(exp_opis, rs.getString("opis"));
        assertEquals(exp_pravila, rs.getString("pravila"));
        if (! rs.isClosed()) {
            rs.close();
        }
        if (! stmt.isClosed()) {
            stmt.close();
        }
    }

    /**
     * Test of insert method, of class KolegijService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testInsert_4args() throws SQLException {
        System.out.println("insert");
        
        Statement stmt = con.createStatement();
        
        String exp_ime = "__test_Programiranje";
        Profesor exp_profesor = new Profesor(123, "", "", "", "");
        String exp_opis = "Programiranje je pisanje uputa računalu što i kako učiniti, a izvodi se u nekom od programskih jezika. Stvaranje programa sadrži u sebi pojedine elemente dizajna, umjetnosti, znanosti, matematike kao i inžinjeringa.";
        String exp_pravila = "Elementi ocjenjivanja su dva kolokvija po 40 bodova te 4 kratka testa po 5 bodova.";
        
        int id = KolegijService.insert(exp_ime, exp_profesor, exp_opis, exp_pravila);
        
        String sql = "SELECT * FROM slapnicar.java_kolegiji WHERE java_kolegiji.id=" + Integer.toString(id) + " LIMIT 1";
        stmt.execute(sql);
        ResultSet rs = stmt.getResultSet();
        assertEquals(true, rs.next());
        assertEquals(id, rs.getInt("id"));
        assertEquals(exp_ime, rs.getString("ime"));
        assertEquals(exp_profesor.getId(), rs.getInt("profesor_id"));
        assertEquals(exp_opis, rs.getString("opis"));
        assertEquals(exp_pravila, rs.getString("pravila"));
        if (! rs.isClosed()) {
            rs.close();
        }
        if (! stmt.isClosed()) {
            stmt.close();
        }
    }

    /**
     * Test of update method, of class KolegijService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testUpdate_Kolegij_Optional() throws SQLException {
        System.out.println("update");
        
        String sql;
        ResultSet rs;
        
        Statement stmt = con.createStatement();
        
        sql = "SELECT * FROM slapnicar.java_kolegiji WHERE java_kolegiji.ime='__test_Matematika' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        assertEquals(true, rs.next());
        Kolegij kolegij = new Kolegij(rs.getInt("id"), rs.getString("ime"),
                rs.getInt("profesor_id"), rs.getString("opis"), rs.getString("pravila"));
        if (! rs.isClosed()) {
            rs.close();
        }
        kolegij.setOpis("Realni brojevi i funkcije realne varijable. Limes niza.");
        kolegij.setPravila("Elementi ocjenjivanja su dva kolokvija po 30 bodova.");
        Optional<Connection> con_opt = Optional.of(con);
        
        String exp_ime = "__test_Matematika";
        sql = "SELECT * FROM slapnicar.java_profesori WHERE java_profesori.korisnicko_ime='__test_ivan' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        int exp_profesor_id = rs.next() ? rs.getInt("id") : -1;
        if (! rs.isClosed()) {
            rs.close();
        }
        String exp_opis = "Realni brojevi i funkcije realne varijable. Limes niza.";
        String exp_pravila = "Elementi ocjenjivanja su dva kolokvija po 30 bodova.";
        
        KolegijService.update(kolegij, con_opt);
        
        sql = "SELECT * FROM slapnicar.java_kolegiji WHERE java_kolegiji.ime='__test_Matematika' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        assertEquals(true, rs.next());
        assertEquals(exp_ime, rs.getString("ime"));
        assertEquals(exp_profesor_id, rs.getInt("profesor_id"));
        assertEquals(exp_opis, rs.getString("opis"));
        assertEquals(exp_pravila, rs.getString("pravila"));
        if (! rs.isClosed()) {
            rs.close();
        }
        
        kolegij.setOpis("Realni brojevi i funkcije realne varijable. Limes niza. Limes realne funkcije realne varijable. Derivacija funkcije i primjene. Integralni račun i primjene. Matrični račun, determinante i rješavanje sustava linearnih jednadžbi.");
        kolegij.setPravila("Elementi ocjenjivanja su dva kolokvija po 30 bodova, kratke provjere znanja po 10 bodova i završni ispit koji nosi 20 bodova.");
        
        exp_opis = "Realni brojevi i funkcije realne varijable. Limes niza. Limes realne funkcije realne varijable. Derivacija funkcije i primjene. Integralni račun i primjene. Matrični račun, determinante i rješavanje sustava linearnih jednadžbi.";
        exp_pravila = "Elementi ocjenjivanja su dva kolokvija po 30 bodova, kratke provjere znanja po 10 bodova i završni ispit koji nosi 20 bodova.";
        
        KolegijService.update(kolegij, con_opt);
        
        sql = "SELECT * FROM slapnicar.java_kolegiji WHERE java_kolegiji.ime='__test_Matematika' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        assertEquals(true, rs.next());
        assertEquals(exp_ime, rs.getString("ime"));
        assertEquals(exp_profesor_id, rs.getInt("profesor_id"));
        assertEquals(exp_opis, rs.getString("opis"));
        assertEquals(exp_pravila, rs.getString("pravila"));
        if (! rs.isClosed()) {
            rs.close();
        }
        
        if (! stmt.isClosed()) {
            stmt.close();
        }
    }

    /**
     * Test of update method, of class KolegijService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testUpdate_Kolegij() throws SQLException {
        System.out.println("update");
        
        String sql;
        ResultSet rs;
        
        Statement stmt = con.createStatement();
        
        sql = "SELECT * FROM slapnicar.java_kolegiji WHERE java_kolegiji.ime='__test_Matematika' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        assertEquals(true, rs.next());
        Kolegij kolegij = new Kolegij(rs.getInt("id"), rs.getString("ime"),
                rs.getInt("profesor_id"), rs.getString("opis"), rs.getString("pravila"));
        if (! rs.isClosed()) {
            rs.close();
        }
        kolegij.setOpis("Realni brojevi i funkcije realne varijable. Limes niza.");
        kolegij.setPravila("Elementi ocjenjivanja su dva kolokvija po 30 bodova.");
        
        String exp_ime = "__test_Matematika";
        sql = "SELECT * FROM slapnicar.java_profesori WHERE java_profesori.korisnicko_ime='__test_ivan' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        int exp_profesor_id = rs.next() ? rs.getInt("id") : -1;
        if (! rs.isClosed()) {
            rs.close();
        }
        String exp_opis = "Realni brojevi i funkcije realne varijable. Limes niza.";
        String exp_pravila = "Elementi ocjenjivanja su dva kolokvija po 30 bodova.";
        
        KolegijService.update(kolegij);
        
        sql = "SELECT * FROM slapnicar.java_kolegiji WHERE java_kolegiji.ime='__test_Matematika' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        assertEquals(true, rs.next());
        assertEquals(exp_ime, rs.getString("ime"));
        assertEquals(exp_profesor_id, rs.getInt("profesor_id"));
        assertEquals(exp_opis, rs.getString("opis"));
        assertEquals(exp_pravila, rs.getString("pravila"));
        if (! rs.isClosed()) {
            rs.close();
        }
        
        kolegij.setOpis("Realni brojevi i funkcije realne varijable. Limes niza. Limes realne funkcije realne varijable. Derivacija funkcije i primjene. Integralni račun i primjene. Matrični račun, determinante i rješavanje sustava linearnih jednadžbi.");
        kolegij.setPravila("Elementi ocjenjivanja su dva kolokvija po 30 bodova, kratke provjere znanja po 10 bodova i završni ispit koji nosi 20 bodova.");
        
        exp_opis = "Realni brojevi i funkcije realne varijable. Limes niza. Limes realne funkcije realne varijable. Derivacija funkcije i primjene. Integralni račun i primjene. Matrični račun, determinante i rješavanje sustava linearnih jednadžbi.";
        exp_pravila = "Elementi ocjenjivanja su dva kolokvija po 30 bodova, kratke provjere znanja po 10 bodova i završni ispit koji nosi 20 bodova.";
        
        KolegijService.update(kolegij);
        
        sql = "SELECT * FROM slapnicar.java_kolegiji WHERE java_kolegiji.ime='__test_Matematika' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        assertEquals(true, rs.next());
        assertEquals(exp_ime, rs.getString("ime"));
        assertEquals(exp_profesor_id, rs.getInt("profesor_id"));
        assertEquals(exp_opis, rs.getString("opis"));
        assertEquals(exp_pravila, rs.getString("pravila"));
        if (! rs.isClosed()) {
            rs.close();
        }
        
        if (! stmt.isClosed()) {
            stmt.close();
        }
    }
    
}
