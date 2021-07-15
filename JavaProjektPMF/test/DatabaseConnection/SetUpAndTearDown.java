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

/**
 *
 * @author ANDS
 */
public class SetUpAndTearDown {
    
     public static void setUpClass(Connection con) throws SQLException {
        Statement stmt = con.createStatement();

        String sql;
        ResultSet rs;
        String korisnicko_ime, ime, tekst;
        int kolegij_id, jmbag;

        sql = "SELECT * FROM slapnicar.java_studenti WHERE java_studenti.korisnicko_ime='__test_jure' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        korisnicko_ime = rs.next() ? rs.getString("korisnicko_ime"): "";
        if (korisnicko_ime.isEmpty()) {
            sql = "INSERT INTO slapnicar.java_studenti VALUES(DEFAULT, '__test_jure', 'jurinasifra', 'Jurica', 'Perić');";
            stmt.execute(sql);
        }
        if (! rs.isClosed()) {
            rs.close();
        }

        sql = "SELECT * FROM slapnicar.java_studenti WHERE java_studenti.korisnicko_ime='__test_matea' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        korisnicko_ime = rs.next() ? rs.getString("korisnicko_ime"): "";
        if (korisnicko_ime.isEmpty()) {
            sql = "INSERT INTO slapnicar.java_studenti VALUES(DEFAULT, '__test_matea', 'mateinasifra', 'Matea', 'Kalić');";
            stmt.execute(sql);
        }
        if (! rs.isClosed()) {
            rs.close();
        }

        sql = "SELECT * FROM slapnicar.java_studenti WHERE java_studenti.korisnicko_ime='__test_ana' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        korisnicko_ime = rs.next() ? rs.getString("korisnicko_ime"): "";
        if (korisnicko_ime.isEmpty()) {
            sql = "INSERT INTO slapnicar.java_studenti VALUES(DEFAULT, '__test_ana', 'aninasifra', 'Ana', 'Horvat');";
            stmt.execute(sql);
        }
        if (! rs.isClosed()) {
            rs.close();
        }
        
        sql = "SELECT * FROM slapnicar.java_profesori WHERE java_profesori.korisnicko_ime='__test_ivan' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        korisnicko_ime = rs.next() ? rs.getString("korisnicko_ime"): "";
        if (korisnicko_ime.isEmpty()) {
            sql = "INSERT INTO slapnicar.java_profesori VALUES(DEFAULT, '__test_ivan', 'ivanovasifra', 'Ivan', 'Kovačević');";
            stmt.execute(sql);
        }
        if (! rs.isClosed()) {
            rs.close();
        }
        
        sql = "SELECT * FROM slapnicar.java_profesori WHERE java_profesori.korisnicko_ime='__test_marko' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        korisnicko_ime = rs.next() ? rs.getString("korisnicko_ime"): "";
        if (korisnicko_ime.isEmpty()) {
            sql = "INSERT INTO slapnicar.java_profesori VALUES(DEFAULT, '__test_marko', 'markovasifra', 'Marko', 'Polenko');";
            stmt.execute(sql);
        }
        if (! rs.isClosed()) {
            rs.close();
        }


        sql = "SELECT * FROM slapnicar.java_kolegiji WHERE java_kolegiji.ime='__test_Matematika' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        ime = rs.next() ? rs.getString("ime"): "";
        if (! rs.isClosed()) {
            rs.close();
        }
        if (ime.isEmpty()) {
            sql = "SELECT * FROM slapnicar.java_profesori WHERE java_profesori.korisnicko_ime='__test_ivan' LIMIT 1";
            stmt.execute(sql);
            rs = stmt.getResultSet();
            if (rs.next()) {
                sql = "INSERT INTO slapnicar.java_kolegiji VALUES(DEFAULT, '__test_Matematika', '" 
                    + Integer.toString(rs.getInt("id")) + "', "
                    + "'Realni brojevi i funkcije realne varijable. Limes niza. Limes realne funkcije realne varijable. Derivacija funkcije i primjene. Integralni račun i primjene. Matrični račun, determinante i rješavanje sustava linearnih jednadžbi.',"
                    + "'Elementi ocjenjivanja su dva kolokvija po 30 bodova, kratke provjere znanja po 10 bodova i završni ispit koji nosi 20 bodova.');";
                stmt.execute(sql);
            }
            if (! rs.isClosed()) {
                rs.close();
            }
        }  


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
                if (! ( rs.next() && rs.getInt("kolegij_id") == kolegij_id && rs.getInt("jmbag") == jmbag )) {
                    if (! rs.isClosed()) {
                        rs.close();
                    }
                    
                    sql = "INSERT INTO slapnicar.java_upisi VALUES(" + Integer.toString(kolegij_id) + ", "
                        + Integer.toString(jmbag) + ", TRUE);";
                    stmt.execute(sql);
                }
            }

            sql = "SELECT * FROM slapnicar.java_studenti WHERE java_studenti.korisnicko_ime='__test_ana' LIMIT 1";
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
                if (! ( rs.next() && rs.getInt("kolegij_id") == kolegij_id && rs.getInt("jmbag") == jmbag )) {
                    if (! rs.isClosed()) {
                        rs.close();
                    }
                    
                    sql = "INSERT INTO slapnicar.java_upisi VALUES(" + Integer.toString(kolegij_id) + ", "
                        + Integer.toString(jmbag) + ", TRUE);";
                    stmt.execute(sql);
                }
            }
            
            sql = "SELECT * FROM slapnicar.java_obavijesti WHERE java_obavijesti.kolegij_id='" + Integer.toString(kolegij_id) + "' LIMIT 1";
            stmt.execute(sql);
            rs = stmt.getResultSet();
            tekst = rs.next() ? rs.getString("tekst"): "";
            if (tekst.isEmpty()) {
                sql = "INSERT INTO slapnicar.java_obavijesti VALUES(DEFAULT, " + Integer.toString(kolegij_id) + ", "
                    + "'Na stranicama kolegija je objavljena zadaća.', STR_TO_DATE('15.06.2021. 13:38:26', '%d.%m.%Y. %T'));";
                stmt.execute(sql);
                sql = "INSERT INTO slapnicar.java_obavijesti VALUES(DEFAULT, " + Integer.toString(kolegij_id) + ", "
                    + "'Na stranicama kolegija su objavljeni rezultati prvog kolokvija.', STR_TO_DATE('21.04.2021. 16:43:19', '%d.%m.%Y. %T'));";
                stmt.execute(sql);
            }
            if (! rs.isClosed()) {
                rs.close();
            }
            
        }
        
        
        sql = "SELECT * FROM slapnicar.java_kolegiji WHERE java_kolegiji.ime='__test_Statistika' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        ime = rs.next() ? rs.getString("ime"): "";
        if (! rs.isClosed()) {
            rs.close();
        }
        if (ime.isEmpty()) {
            sql = "SELECT * FROM slapnicar.java_profesori WHERE java_profesori.korisnicko_ime='__test_marko' LIMIT 1";
            stmt.execute(sql);
            rs = stmt.getResultSet();
            if (rs.next()) {
                sql = "INSERT INTO slapnicar.java_kolegiji VALUES(DEFAULT, '__test_Statistika', '" 
                    + Integer.toString(rs.getInt("id")) + "', "
                    + "'Statistika je matematička disciplina koja proučava načine sakupljanja, sažimanja i prikazivanja zaključaka iz nekih podataka. Primjenjuje se u mnogim strukama, kao i u svakodnevnom životu.',"
                    + "'Elementi ocjenjivanja su dva kolokvija po 40 bodova te usmeni ispit koji nosi 20 bodova.');";
                stmt.execute(sql);
            }
            if (! rs.isClosed()) {
                rs.close();
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
                
                sql = "SELECT * FROM slapnicar.java_upisi WHERE java_upisi.kolegij_id=" + Integer.toString(kolegij_id) + " "
                    + "AND java_upisi.jmbag=" + Integer.toString(jmbag) + " LIMIT 1";
                stmt.execute(sql);
                rs = stmt.getResultSet();
                if (! ( rs.next() && rs.getInt("kolegij_id") == kolegij_id && rs.getInt("jmbag") == jmbag )) {
                    if (! rs.isClosed()) {
                        rs.close();
                    }
                    
                    sql = "INSERT INTO slapnicar.java_upisi VALUES(" + Integer.toString(kolegij_id) + ", "
                        + Integer.toString(jmbag) + ", TRUE);";
                    stmt.execute(sql);
                }
            }
            
            sql = "SELECT * FROM slapnicar.java_studenti WHERE java_studenti.korisnicko_ime='__test_ana' LIMIT 1";
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
                if (! ( rs.next() && rs.getInt("kolegij_id") == kolegij_id && rs.getInt("jmbag") == jmbag )) {
                    if (! rs.isClosed()) {
                        rs.close();
                    }
                    
                    sql = "INSERT INTO slapnicar.java_upisi VALUES(" + Integer.toString(kolegij_id) + ", "
                        + Integer.toString(jmbag) + ", TRUE);";
                    stmt.execute(sql);
                }
            }
            
            sql = "SELECT * FROM slapnicar.java_obavijesti WHERE java_obavijesti.kolegij_id='" + Integer.toString(kolegij_id) + "' LIMIT 1";
            stmt.execute(sql);
            rs = stmt.getResultSet();
            tekst = rs.next() ? rs.getString("tekst"): "";
            if (tekst.isEmpty()) {
                sql = "INSERT INTO slapnicar.java_obavijesti VALUES(DEFAULT, " + Integer.toString(kolegij_id) + ", "
                    + "'18.6.2021. će se održati zanimljiva prezentacija o Markovljevim lancima.', STR_TO_DATE('06.05.2021. 10:43:07', '%d.%m.%Y. %T'));";
                stmt.execute(sql);
            }
            if (! rs.isClosed()) {
                rs.close();
            }
            
        }        
        
        if (! stmt.isClosed()) {
            stmt.close();
        }
    }
     
    public static void tearDownClass(Connection con) throws SQLException {
        Statement stmt = con.createStatement();

        String sql;
        ResultSet rs;
        int kolegij_id, jmbag;
        
        sql = "SELECT * FROM slapnicar.java_studenti WHERE java_studenti.korisnicko_ime='__test_jure' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        if (rs.next()) {
            sql = "DELETE FROM slapnicar.java_studenti WHERE java_studenti.korisnicko_ime='__test_jure'";
            stmt.execute(sql);
        }
        if (! rs.isClosed()) {
            rs.close();
        }
        
        sql = "SELECT * FROM slapnicar.java_studenti WHERE java_studenti.korisnicko_ime='__test_ana' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        if (rs.next()) {
            sql = "DELETE FROM slapnicar.java_studenti WHERE java_studenti.korisnicko_ime='__test_ana'";
            stmt.execute(sql);
        }
        if (! rs.isClosed()) {
            rs.close();
        }
        
        sql = "SELECT * FROM slapnicar.java_studenti WHERE java_studenti.korisnicko_ime='__test_matea' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        if (rs.next()) {
            sql = "DELETE FROM slapnicar.java_studenti WHERE java_studenti.korisnicko_ime='__test_matea'";
            stmt.execute(sql);
        }
        if (! rs.isClosed()) {
            rs.close();
        }
        
        sql = "SELECT * FROM slapnicar.java_studenti WHERE java_studenti.korisnicko_ime='__test_marija' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        if (rs.next()) {
            sql = "DELETE FROM slapnicar.java_studenti WHERE java_studenti.korisnicko_ime='__test_marija'";
            stmt.execute(sql);
        }
        if (! rs.isClosed()) {
            rs.close();
        }
        
        sql = "SELECT * FROM slapnicar.java_kolegiji WHERE java_kolegiji.ime='__test_Matematika' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        if (rs.next()) {
            kolegij_id = rs.getInt("id");
            if (! rs.isClosed()) {
                rs.close();
            }
            
            sql = "SELECT * FROM slapnicar.java_upisi WHERE java_upisi.kolegij_id=" + Integer.toString(kolegij_id);
            stmt.execute(sql);
            rs = stmt.getResultSet();
            if (rs.next()) {
                sql = "DELETE FROM slapnicar.java_upisi WHERE java_upisi.kolegij_id=" + Integer.toString(kolegij_id);
                stmt.execute(sql);
            }
            if (! rs.isClosed()) {
                rs.close();
            }
            
            sql = "SELECT * FROM slapnicar.java_obavijesti WHERE java_obavijesti.kolegij_id=" + Integer.toString(kolegij_id);
            stmt.execute(sql);
            rs = stmt.getResultSet();
            if (rs.next()) {
                sql = "DELETE FROM slapnicar.java_obavijesti WHERE java_obavijesti.kolegij_id=" + Integer.toString(kolegij_id);
                stmt.execute(sql);
            }
            if (! rs.isClosed()) {
                rs.close();
            }
            
            sql = "DELETE FROM slapnicar.java_kolegiji WHERE java_kolegiji.ime='__test_Matematika'";
            stmt.execute(sql);
        }
        
        sql = "SELECT * FROM slapnicar.java_kolegiji WHERE java_kolegiji.ime='__test_Statistika' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        if (rs.next()) {
            kolegij_id = rs.getInt("id");
            if (! rs.isClosed()) {
                rs.close();
            }
            
            sql = "SELECT * FROM slapnicar.java_upisi WHERE java_upisi.kolegij_id=" + Integer.toString(kolegij_id);
            stmt.execute(sql);
            rs = stmt.getResultSet();
            if (rs.next()) {
                sql = "DELETE FROM slapnicar.java_upisi WHERE java_upisi.kolegij_id=" + Integer.toString(kolegij_id);
                stmt.execute(sql);
            }
            if (! rs.isClosed()) {
                rs.close();
            }
            
            sql = "SELECT * FROM slapnicar.java_obavijesti WHERE java_obavijesti.kolegij_id=" + Integer.toString(kolegij_id);
            stmt.execute(sql);
            rs = stmt.getResultSet();
            if (rs.next()) {
                sql = "DELETE FROM slapnicar.java_obavijesti WHERE java_obavijesti.kolegij_id=" + Integer.toString(kolegij_id);
                stmt.execute(sql);
            }
            if (! rs.isClosed()) {
                rs.close();
            }
            
            sql = "DELETE FROM slapnicar.java_kolegiji WHERE java_kolegiji.ime='__test_Statistika'";
            stmt.execute(sql);
        }
        
        sql = "SELECT * FROM slapnicar.java_kolegiji WHERE java_kolegiji.ime='__test_Programiranje' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        if (rs.next()) {
            sql = "DELETE FROM slapnicar.java_kolegiji WHERE java_kolegiji.ime='__test_Programiranje'";
            stmt.execute(sql);
        }
        if (! rs.isClosed()) {
            rs.close();
        }
        
        sql = "SELECT * FROM slapnicar.java_profesori WHERE java_profesori.korisnicko_ime='__test_ivan' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        if (rs.next()) {
            sql = "DELETE FROM slapnicar.java_profesori WHERE java_profesori.korisnicko_ime='__test_ivan'";
            stmt.execute(sql);
        }
        if (! rs.isClosed()) {
            rs.close();
        }
        
        sql = "SELECT * FROM slapnicar.java_profesori WHERE java_profesori.korisnicko_ime='__test_marko' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        if (rs.next()) {
            sql = "DELETE FROM slapnicar.java_profesori WHERE java_profesori.korisnicko_ime='__test_marko'";
            stmt.execute(sql);
        }
        if (! rs.isClosed()) {
            rs.close();
        }
        
        sql = "SELECT * FROM slapnicar.java_profesori WHERE java_profesori.korisnicko_ime='__test_maja' LIMIT 1";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        if (rs.next()) {
            sql = "DELETE FROM slapnicar.java_profesori WHERE java_profesori.korisnicko_ime='__test_maja'";
            stmt.execute(sql);
        }
        if (! rs.isClosed()) {
            rs.close();
        }
        
        sql = "SELECT * FROM slapnicar.java_obavijesti WHERE java_obavijesti.tekst='25.05.2021. će se pisati kratki test u predavaonici A101.'";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        if (rs.next()) {
            sql = "DELETE FROM slapnicar.java_obavijesti WHERE java_obavijesti.tekst='25.05.2021. će se pisati kratki test u predavaonici A101.'";
            stmt.execute(sql);
        }
        if (! rs.isClosed()) {
            rs.close();
        }
        
        sql = "SELECT * FROM slapnicar.java_upisi WHERE java_upisi.kolegij_id=1231 AND java_upisi.jmbag=1232";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        if (rs.next()) {
            sql = "DELETE FROM slapnicar.java_upisi WHERE java_upisi.kolegij_id=1231 AND java_upisi.jmbag=1232";
            stmt.execute(sql);
        }
        if (! rs.isClosed()) {
            rs.close();
        }
        
        sql = "SELECT * FROM slapnicar.java_upisi WHERE java_upisi.kolegij_id=1234 AND java_upisi.jmbag=1235";
        stmt.execute(sql);
        rs = stmt.getResultSet();
        if (rs.next()) {
            sql = "DELETE FROM slapnicar.java_upisi WHERE java_upisi.kolegij_id=1234 AND java_upisi.jmbag=1235";
            stmt.execute(sql);
        }
        if (! rs.isClosed()) {
            rs.close();
        }
        
        
        if (! stmt.isClosed()) {
            stmt.close();
        }
    }
    
}
