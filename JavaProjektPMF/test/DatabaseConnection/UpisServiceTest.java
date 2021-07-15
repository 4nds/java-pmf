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
public class UpisServiceTest {
    
    static Connection con;
    
    public UpisServiceTest() {
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
     * Test of findAllByKolegiji method, of class UpisService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testFindAllByKolegiji_ArrayList_Optional() throws SQLException {
        System.out.println("findAllByKolegiji");
        
        Statement stmt = con.createStatement();
        String sql;
        ResultSet rs;
        int kolegij_id, jmbag;
        
        ArrayList<Kolegij> kolegiji = new ArrayList<>();
        Optional<Connection> con_opt = Optional.of(con);
        
        ArrayList<Integer> kolegij_ids = new ArrayList<>();
        ArrayList<Integer> jmbagi = new ArrayList<>();
        ArrayList<Boolean> potvrdjeni = new ArrayList<>();
        
        sql = "SELECT * FROM slapnicar.java_kolegiji WHERE java_kolegiji.ime='__test_Matematika' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        if (rs.next()) {
            kolegiji.add(new Kolegij(rs.getInt("id"), rs.getString("ime"),
                    rs.getInt("profesor_id"), rs.getString("opis"), rs.getString("pravila")));
            kolegij_id = rs.getInt("id");
            if (! rs.isClosed()) {
                rs.close();
            }
            
            sql = "SELECT * FROM slapnicar.java_studenti WHERE java_studenti.korisnicko_ime='__test_jure' LIMIT 1";
            stmt.execute(sql);
            rs = stmt.getResultSet();
            if (rs.next()) {
                
                jmbag = rs.getInt("jmbag");
                if (! rs.isClosed()) {
                    rs.close();
                }
                
                kolegij_ids.add(kolegij_id);
                jmbagi.add(jmbag);
                potvrdjeni.add(true);
            }
            
            sql = "SELECT * FROM slapnicar.java_studenti WHERE java_studenti.korisnicko_ime='__test_ana' LIMIT 1";
            stmt.execute(sql);
            rs = stmt.getResultSet();
            if (rs.next()) {
                jmbag = rs.getInt("jmbag");
                if (! rs.isClosed()) {
                    rs.close();
                }
                
                kolegij_ids.add(kolegij_id);
                jmbagi.add(jmbag);
                potvrdjeni.add(true);
            }
        
        }
        
        sql = "SELECT * FROM slapnicar.java_kolegiji WHERE java_kolegiji.ime='__test_Statistika' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        if (rs.next()) {
            kolegiji.add(new Kolegij(rs.getInt("id"), rs.getString("ime"),
                rs.getInt("profesor_id"), rs.getString("opis"), rs.getString("pravila")));
            kolegij_id = rs.getInt("id");
            if (! rs.isClosed()) {
                rs.close();
            }
            
            sql = "SELECT * FROM slapnicar.java_studenti WHERE java_studenti.korisnicko_ime='__test_matea' LIMIT 1";
            stmt.execute(sql);
            rs = stmt.getResultSet();
            if (rs.next()) {
                jmbag = rs.getInt("jmbag");
                if (! rs.isClosed()) {
                    rs.close();
                }
                
                kolegij_ids.add(kolegij_id);
                jmbagi.add(jmbag);
                potvrdjeni.add(true);
            }
            
            sql = "SELECT * FROM slapnicar.java_studenti WHERE java_studenti.korisnicko_ime='__test_ana' LIMIT 1";
            stmt.execute(sql);
            rs = stmt.getResultSet();
            if (rs.next()) {
                jmbag = rs.getInt("jmbag");
                if (! rs.isClosed()) {
                    rs.close();
                }
                
                kolegij_ids.add(kolegij_id);
                jmbagi.add(jmbag);
                potvrdjeni.add(true);
            }
        
        }

        ArrayList<Upis> result = UpisService.findAllByKolegiji(kolegiji, con_opt);
        
        int index;
        int found = 0;
        for (int i = 0; i < result.size(); i++) {
            index = -1;
            for (int j = 0; j < kolegij_ids.size(); j++) {
                if ( kolegij_ids.get(j) == result.get(i).getKolegijId() && jmbagi.get(j) == result.get(i).getJmbag() ) {
                    index = j;
                    found++;
                }
            }
            if (index >= 0) {
                assertEquals(kolegij_ids.get(index).intValue(), result.get(i).getKolegijId());
                assertEquals(jmbagi.get(index).intValue(), result.get(i).getJmbag() );
                assertEquals(potvrdjeni.get(index), result.get(i).getPotvrdjeno());
            }
        }
        assertEquals(kolegij_ids.size(), found);
        
        if (! stmt.isClosed()) {
            stmt.close();
        }
    }

    /**
     * Test of findAllByKolegiji method, of class UpisService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testFindAllByKolegiji_ArrayList() throws SQLException {
        System.out.println("findAllByKolegiji");
        
        Statement stmt = con.createStatement();
        String sql;
        ResultSet rs;
        int kolegij_id, jmbag;
        
        ArrayList<Kolegij> kolegiji = new ArrayList<>();
        
        ArrayList<Integer> kolegij_ids = new ArrayList<>();
        ArrayList<Integer> jmbagi = new ArrayList<>();
        ArrayList<Boolean> potvrdjeni = new ArrayList<>();
        
        sql = "SELECT * FROM slapnicar.java_kolegiji WHERE java_kolegiji.ime='__test_Matematika' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        if (rs.next()) {
            kolegiji.add(new Kolegij(rs.getInt("id"), rs.getString("ime"),
                    rs.getInt("profesor_id"), rs.getString("opis"), rs.getString("pravila")));
            kolegij_id = rs.getInt("id");
            if (! rs.isClosed()) {
                rs.close();
            }
            
            sql = "SELECT * FROM slapnicar.java_studenti WHERE java_studenti.korisnicko_ime='__test_jure' LIMIT 1";
            stmt.execute(sql);
            rs = stmt.getResultSet();
            if (rs.next()) {
                
                jmbag = rs.getInt("jmbag");
                if (! rs.isClosed()) {
                    rs.close();
                }
                
                kolegij_ids.add(kolegij_id);
                jmbagi.add(jmbag);
                potvrdjeni.add(true);
            }
            
            sql = "SELECT * FROM slapnicar.java_studenti WHERE java_studenti.korisnicko_ime='__test_ana' LIMIT 1";
            stmt.execute(sql);
            rs = stmt.getResultSet();
            if (rs.next()) {
                jmbag = rs.getInt("jmbag");
                if (! rs.isClosed()) {
                    rs.close();
                }
                
                kolegij_ids.add(kolegij_id);
                jmbagi.add(jmbag);
                potvrdjeni.add(true);
            }
        
        }
        
        sql = "SELECT * FROM slapnicar.java_kolegiji WHERE java_kolegiji.ime='__test_Statistika' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        if (rs.next()) {
            kolegiji.add(new Kolegij(rs.getInt("id"), rs.getString("ime"),
                rs.getInt("profesor_id"), rs.getString("opis"), rs.getString("pravila")));
            kolegij_id = rs.getInt("id");
            if (! rs.isClosed()) {
                rs.close();
            }
            
            sql = "SELECT * FROM slapnicar.java_studenti WHERE java_studenti.korisnicko_ime='__test_matea' LIMIT 1";
            stmt.execute(sql);
            rs = stmt.getResultSet();
            if (rs.next()) {
                jmbag = rs.getInt("jmbag");
                if (! rs.isClosed()) {
                    rs.close();
                }
                
                kolegij_ids.add(kolegij_id);
                jmbagi.add(jmbag);
                potvrdjeni.add(true);
            }
            
            sql = "SELECT * FROM slapnicar.java_studenti WHERE java_studenti.korisnicko_ime='__test_ana' LIMIT 1";
            stmt.execute(sql);
            rs = stmt.getResultSet();
            if (rs.next()) {
                jmbag = rs.getInt("jmbag");
                if (! rs.isClosed()) {
                    rs.close();
                }
                
                kolegij_ids.add(kolegij_id);
                jmbagi.add(jmbag);
                potvrdjeni.add(true);
            }
        
        }

        ArrayList<Upis> result = UpisService.findAllByKolegiji(kolegiji);
        
        int index;
        int found = 0;
        for (int i = 0; i < result.size(); i++) {
            index = -1;
            for (int j = 0; j < kolegij_ids.size(); j++) {
                if ( kolegij_ids.get(j) == result.get(i).getKolegijId() && jmbagi.get(j) == result.get(i).getJmbag() ) {
                    index = j;
                    found++;
                }
            }
            if (index >= 0) {
                assertEquals(kolegij_ids.get(index).intValue(), result.get(i).getKolegijId());
                assertEquals(jmbagi.get(index).intValue(), result.get(i).getJmbag() );
                assertEquals(potvrdjeni.get(index), result.get(i).getPotvrdjeno());
            }
        }
        assertEquals(kolegij_ids.size(), found);
        
        if (! stmt.isClosed()) {
            stmt.close();
        }
    }

    /**
     * Test of getAll method, of class UpisService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testGetAll_Optional() throws SQLException {
        System.out.println("getAll");
        
        Statement stmt = con.createStatement();
        String sql;
        ResultSet rs;
        int kolegij_id, jmbag;
        
        Optional<Connection> con_opt = Optional.of(con);
        
        ArrayList<Integer> kolegij_ids = new ArrayList<>();
        ArrayList<Integer> jmbagi = new ArrayList<>();
        ArrayList<Boolean> potvrdjeni = new ArrayList<>();
        
        sql = "SELECT * FROM slapnicar.java_kolegiji WHERE java_kolegiji.ime='__test_Matematika' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        if (rs.next()) {
            kolegij_id = rs.getInt("id");
            if (! rs.isClosed()) {
                rs.close();
            }
            
            sql = "SELECT * FROM slapnicar.java_studenti WHERE java_studenti.korisnicko_ime='__test_jure' LIMIT 1";
            stmt.execute(sql);
            rs = stmt.getResultSet();
            if (rs.next()) {
                
                jmbag = rs.getInt("jmbag");
                if (! rs.isClosed()) {
                    rs.close();
                }
                
                kolegij_ids.add(kolegij_id);
                jmbagi.add(jmbag);
                potvrdjeni.add(true);
            }
            
            sql = "SELECT * FROM slapnicar.java_studenti WHERE java_studenti.korisnicko_ime='__test_ana' LIMIT 1";
            stmt.execute(sql);
            rs = stmt.getResultSet();
            if (rs.next()) {
                jmbag = rs.getInt("jmbag");
                if (! rs.isClosed()) {
                    rs.close();
                }
                
                kolegij_ids.add(kolegij_id);
                jmbagi.add(jmbag);
                potvrdjeni.add(true);
            }
        
        }
        
        sql = "SELECT * FROM slapnicar.java_kolegiji WHERE java_kolegiji.ime='__test_Statistika' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        if (rs.next()) {
            kolegij_id = rs.getInt("id");
            if (! rs.isClosed()) {
                rs.close();
            }
            
            sql = "SELECT * FROM slapnicar.java_studenti WHERE java_studenti.korisnicko_ime='__test_matea' LIMIT 1";
            stmt.execute(sql);
            rs = stmt.getResultSet();
            if (rs.next()) {
                jmbag = rs.getInt("jmbag");
                if (! rs.isClosed()) {
                    rs.close();
                }
                
                kolegij_ids.add(kolegij_id);
                jmbagi.add(jmbag);
                potvrdjeni.add(true);
            }
            
            sql = "SELECT * FROM slapnicar.java_studenti WHERE java_studenti.korisnicko_ime='__test_ana' LIMIT 1";
            stmt.execute(sql);
            rs = stmt.getResultSet();
            if (rs.next()) {
                jmbag = rs.getInt("jmbag");
                if (! rs.isClosed()) {
                    rs.close();
                }
                
                kolegij_ids.add(kolegij_id);
                jmbagi.add(jmbag);
                potvrdjeni.add(true);
            }
        
        }
        
        ArrayList<Upis> result = UpisService.getAll(con_opt);
        
        int index;
        int found = 0;
        for (int i = 0; i < result.size(); i++) {
            index = -1;
            for (int j = 0; j < kolegij_ids.size(); j++) {
                if ( kolegij_ids.get(j) == result.get(i).getKolegijId() && jmbagi.get(j) == result.get(i).getJmbag() ) {
                    index = j;
                    found++;
                }
            }
            if (index >= 0) {
                assertEquals(kolegij_ids.get(index).intValue(), result.get(i).getKolegijId());
                assertEquals(jmbagi.get(index).intValue(), result.get(i).getJmbag() );
                assertEquals(potvrdjeni.get(index), result.get(i).getPotvrdjeno());
            }
        }
        assertEquals(kolegij_ids.size(), found);
        
        if (! stmt.isClosed()) {
            stmt.close();
        }
    }

    /**
     * Test of getAll method, of class UpisService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testGetAll() throws SQLException {
        System.out.println("getAll");
        
        Statement stmt = con.createStatement();
        String sql;
        ResultSet rs;
        int kolegij_id, jmbag;
        
        ArrayList<Integer> kolegij_ids = new ArrayList<>();
        ArrayList<Integer> jmbagi = new ArrayList<>();
        ArrayList<Boolean> potvrdjeni = new ArrayList<>();
        
        sql = "SELECT * FROM slapnicar.java_kolegiji WHERE java_kolegiji.ime='__test_Matematika' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        if (rs.next()) {
            kolegij_id = rs.getInt("id");
            if (! rs.isClosed()) {
                rs.close();
            }
            
            sql = "SELECT * FROM slapnicar.java_studenti WHERE java_studenti.korisnicko_ime='__test_jure' LIMIT 1";
            stmt.execute(sql);
            rs = stmt.getResultSet();
            if (rs.next()) {
                
                jmbag = rs.getInt("jmbag");
                if (! rs.isClosed()) {
                    rs.close();
                }
                
                kolegij_ids.add(kolegij_id);
                jmbagi.add(jmbag);
                potvrdjeni.add(true);
            }
            
            sql = "SELECT * FROM slapnicar.java_studenti WHERE java_studenti.korisnicko_ime='__test_ana' LIMIT 1";
            stmt.execute(sql);
            rs = stmt.getResultSet();
            if (rs.next()) {
                jmbag = rs.getInt("jmbag");
                if (! rs.isClosed()) {
                    rs.close();
                }
                
                kolegij_ids.add(kolegij_id);
                jmbagi.add(jmbag);
                potvrdjeni.add(true);
            }
        
        }
        
        sql = "SELECT * FROM slapnicar.java_kolegiji WHERE java_kolegiji.ime='__test_Statistika' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        if (rs.next()) {
            kolegij_id = rs.getInt("id");
            if (! rs.isClosed()) {
                rs.close();
            }
            
            sql = "SELECT * FROM slapnicar.java_studenti WHERE java_studenti.korisnicko_ime='__test_matea' LIMIT 1";
            stmt.execute(sql);
            rs = stmt.getResultSet();
            if (rs.next()) {
                jmbag = rs.getInt("jmbag");
                if (! rs.isClosed()) {
                    rs.close();
                }
                
                kolegij_ids.add(kolegij_id);
                jmbagi.add(jmbag);
                potvrdjeni.add(true);
            }
            
            sql = "SELECT * FROM slapnicar.java_studenti WHERE java_studenti.korisnicko_ime='__test_ana' LIMIT 1";
            stmt.execute(sql);
            rs = stmt.getResultSet();
            if (rs.next()) {
                jmbag = rs.getInt("jmbag");
                if (! rs.isClosed()) {
                    rs.close();
                }
                
                kolegij_ids.add(kolegij_id);
                jmbagi.add(jmbag);
                potvrdjeni.add(true);
            }
        
        }
        
        ArrayList<Upis> result = UpisService.getAll();
        
        int index;
        int found = 0;
        for (int i = 0; i < result.size(); i++) {
            index = -1;
            for (int j = 0; j < kolegij_ids.size(); j++) {
                if ( kolegij_ids.get(j) == result.get(i).getKolegijId() && jmbagi.get(j) == result.get(i).getJmbag() ) {
                    index = j;
                    found++;
                }
            }
            if (index >= 0) {
                assertEquals(kolegij_ids.get(index).intValue(), result.get(i).getKolegijId());
                assertEquals(jmbagi.get(index).intValue(), result.get(i).getJmbag() );
                assertEquals(potvrdjeni.get(index), result.get(i).getPotvrdjeno());
            }
        }
        assertEquals(kolegij_ids.size(), found);
        
        if (! stmt.isClosed()) {
            stmt.close();
        }
    }

    /**
     * Test of insert method, of class UpisService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testInsert_4args() throws SQLException {
        System.out.println("insert");
        
        Statement stmt = con.createStatement();
        
        Kolegij exp_kolegij = new Kolegij(1231, "", 0, "", "");
        Student exp_student = new Student(1232, "", "", "", "");
        boolean exp_potvrdjeno = false;
        Optional<Connection> con_opt = Optional.of(con);

        UpisService.insert(exp_kolegij, exp_student, exp_potvrdjeno, con_opt);
        
        String sql = "SELECT * FROM slapnicar.java_upisi WHERE java_upisi.jmbag=" 
            + Integer.toString(exp_student.getJmbag()) + " AND java_upisi.kolegij_id=" 
            + Integer.toString(exp_kolegij.getId()) + " LIMIT 1";
        stmt.execute(sql);
        ResultSet rs = stmt.getResultSet();
        assertEquals(true, rs.next());
        assertEquals(exp_kolegij.getId(), rs.getInt("kolegij_id"));
        assertEquals(exp_student.getJmbag(), rs.getInt("jmbag"));
        assertEquals(exp_potvrdjeno, rs.getBoolean("potvrdjeno"));
        if (! rs.isClosed()) {
            rs.close();
        }
        if (! stmt.isClosed()) {
            stmt.close();
        }
    }

    /**
     * Test of insert method, of class UpisService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testInsert_3args() throws SQLException {
        System.out.println("insert");
        
        Statement stmt = con.createStatement();
        
        Kolegij exp_kolegij = new Kolegij(1234, "", 0, "", "");
        Student exp_student = new Student(1235, "", "", "", "");
        boolean exp_potvrdjeno = false;
        Optional<Connection> con_opt = Optional.of(con);

        UpisService.insert(exp_kolegij, exp_student, exp_potvrdjeno);
        
        String sql = "SELECT * FROM slapnicar.java_upisi WHERE java_upisi.jmbag=" 
            + Integer.toString(exp_student.getJmbag()) + " AND java_upisi.kolegij_id=" 
            + Integer.toString(exp_kolegij.getId()) + " LIMIT 1";
        stmt.execute(sql);
        ResultSet rs = stmt.getResultSet();
        assertEquals(true, rs.next());
        assertEquals(exp_kolegij.getId(), rs.getInt("kolegij_id"));
        assertEquals(exp_student.getJmbag(), rs.getInt("jmbag"));
        assertEquals(exp_potvrdjeno, rs.getBoolean("potvrdjeno"));
        if (! rs.isClosed()) {
            rs.close();
        }
        if (! stmt.isClosed()) {
            stmt.close();
        }
    }

    /**
     * Test of update method, of class UpisService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testUpdate_Upis_Optional() throws SQLException {
        System.out.println("update");
        
        String sql;
        ResultSet rs;
        Statement stmt = con.createStatement();
        int kolegij_id = -1;
        int jmbag = -1;
        
        Upis upis = new Upis(-1, -1, false);
        sql = "SELECT * FROM slapnicar.java_kolegiji WHERE java_kolegiji.ime='__test_Matematika' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        if (rs.next()) {
            kolegij_id = rs.getInt("id");
            if (! rs.isClosed()) {
                rs.close();
            }
            
            sql = "SELECT * FROM slapnicar.java_studenti WHERE java_studenti.korisnicko_ime='__test_jure' LIMIT 1";
            stmt.execute(sql);
            rs = stmt.getResultSet();
            if (rs.next()) {
                jmbag = rs.getInt("jmbag");
                if (! rs.isClosed()) {
                    rs.close();
                }
                
                sql = "SELECT * FROM slapnicar.java_upisi WHERE java_upisi.kolegij_id=" + Integer.toString(kolegij_id) + " "
                    + "AND java_upisi.jmbag=" + Integer.toString(jmbag) + " LIMIT 1";
                stmt.execute(sql);
                rs = stmt.getResultSet();
                if (rs.next()) {
                    upis = new Upis(rs.getInt("kolegij_id"), rs.getInt("jmbag"),
                        rs.getBoolean("potvrdjeno"));                    
                }
                if (! rs.isClosed()) {
                    rs.close();
                }
            }
        }
        upis.setPotvrdjeno(false);
        Optional<Connection> con_opt = Optional.of(con);
        
        UpisService.update(upis, con_opt);
        
        sql = "SELECT * FROM slapnicar.java_upisi WHERE java_upisi.kolegij_id=" + Integer.toString(kolegij_id) + " "
            + "AND java_upisi.jmbag=" + Integer.toString(jmbag) + " LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        assertEquals(true, rs.next());
        assertEquals(kolegij_id, rs.getInt("kolegij_id"));
        assertEquals(jmbag, rs.getInt("jmbag"));
        assertEquals(upis.getPotvrdjeno(), rs.getBoolean("potvrdjeno"));
        
        upis.setJmbag(jmbag);
        upis.setPotvrdjeno(true);
        
        UpisService.update(upis, con_opt);
        
        sql = "SELECT * FROM slapnicar.java_upisi WHERE java_upisi.kolegij_id=" + Integer.toString(kolegij_id) + " "
            + "AND java_upisi.jmbag=" + Integer.toString(jmbag) + " LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        assertEquals(true, rs.next());
        assertEquals(kolegij_id, rs.getInt("kolegij_id"));
        assertEquals(jmbag, rs.getInt("jmbag"));
        assertEquals(upis.getPotvrdjeno(), rs.getBoolean("potvrdjeno"));
        
        if (! rs.isClosed()) {
            rs.close();
        }
        if (! stmt.isClosed()) {
            stmt.close();
        }
    }
    
    /**
     * Test of update method, of class UpisService.
     * @throws java.sql.SQLException
     */
    @Test
    public void testUpdate_Upis() throws SQLException {
        System.out.println("update");
        
        String sql;
        ResultSet rs;
        Statement stmt = con.createStatement();
        int kolegij_id = -1;
        int jmbag = -1;
        
        Upis upis = new Upis(-1, -1, false);
        sql = "SELECT * FROM slapnicar.java_kolegiji WHERE java_kolegiji.ime='__test_Matematika' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        if (rs.next()) {
            kolegij_id = rs.getInt("id");
            if (! rs.isClosed()) {
                rs.close();
            }
            
            sql = "SELECT * FROM slapnicar.java_studenti WHERE java_studenti.korisnicko_ime='__test_jure' LIMIT 1";
            stmt.execute(sql);
            rs = stmt.getResultSet();
            if (rs.next()) {
                jmbag = rs.getInt("jmbag");
                if (! rs.isClosed()) {
                    rs.close();
                }
                
                sql = "SELECT * FROM slapnicar.java_upisi WHERE java_upisi.kolegij_id=" + Integer.toString(kolegij_id) + " "
                    + "AND java_upisi.jmbag=" + Integer.toString(jmbag) + " LIMIT 1";
                stmt.execute(sql);
                rs = stmt.getResultSet();
                if (rs.next()) {
                    upis = new Upis(rs.getInt("kolegij_id"), rs.getInt("jmbag"),
                        rs.getBoolean("potvrdjeno"));                    
                }
                if (! rs.isClosed()) {
                    rs.close();
                }
            }
        }
        upis.setPotvrdjeno(false);
        
        UpisService.update(upis);
        
        sql = "SELECT * FROM slapnicar.java_upisi WHERE java_upisi.kolegij_id=" + Integer.toString(kolegij_id) + " "
            + "AND java_upisi.jmbag=" + Integer.toString(jmbag) + " LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        assertEquals(true, rs.next());
        assertEquals(kolegij_id, rs.getInt("kolegij_id"));
        assertEquals(jmbag, rs.getInt("jmbag"));
        assertEquals(upis.getPotvrdjeno(), rs.getBoolean("potvrdjeno"));
        
        upis.setJmbag(jmbag);
        upis.setPotvrdjeno(true);
        
        UpisService.update(upis);
        
        sql = "SELECT * FROM slapnicar.java_upisi WHERE java_upisi.kolegij_id=" + Integer.toString(kolegij_id) + " "
            + "AND java_upisi.jmbag=" + Integer.toString(jmbag) + " LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        assertEquals(true, rs.next());
        assertEquals(kolegij_id, rs.getInt("kolegij_id"));
        assertEquals(jmbag, rs.getInt("jmbag"));
        assertEquals(upis.getPotvrdjeno(), rs.getBoolean("potvrdjeno"));
        
        if (! rs.isClosed()) {
            rs.close();
        }
        if (! stmt.isClosed()) {
            stmt.close();
        }
    }
    
}
